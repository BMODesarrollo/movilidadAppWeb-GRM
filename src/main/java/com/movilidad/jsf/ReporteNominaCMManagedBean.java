package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.EmpleadoCargoCostoFacadeLocal;
import com.movilidad.ejb.GenericaJornadaFacadeLocal;
import com.movilidad.ejb.ParamAreaFacadeLocal;
import com.movilidad.ejb.ParamCargoCcFacadeLocal;
import com.movilidad.ejb.ParamReporteHorasFacadeLocal;
import com.movilidad.model.EmpleadoCargoCosto;
import com.movilidad.model.ParamArea;
import com.movilidad.model.ParamCargoCc;
import com.movilidad.model.ParamReporteHoras;
import com.movilidad.util.beans.ReporteHoras;
import com.movilidad.util.beans.ReporteHorasCM;
import com.movilidad.util.beans.ReporteHorasExcel;
import com.movilidad.util.beans.ReporteNominaCM;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "reporteNominaCMBean")
@ViewScoped
public class ReporteNominaCMManagedBean implements Serializable {

    @EJB
    private GenericaJornadaFacadeLocal genericaJornadaEjb;

    @EJB
    private ParamAreaFacadeLocal paramAreaEjb;

    @EJB
    private ParamReporteHorasFacadeLocal paramReporteHorasEjb;

    @EJB
    private ParamCargoCcFacadeLocal paramCargoCcEjb;

    @EJB
    private EmpleadoCargoCostoFacadeLocal empleadoCargoCostoEjb;

    private Locale locale = new Locale("es", "CO");
    private Calendar current = Calendar.getInstance(locale);

    private final String[] meses = {
        "enero", "febrero", "marzo", "abril", "mayo", "junio",
        "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"
    };

    private Date fecha_inicio;
    private Date fecha_fin;
    private Date fecha_movimiento;
    private StreamedContent file;

    private Integer[] areasSeleccionadas;

    private List<ReporteHorasCM> lstNominaCM;
    private List<ReporteNominaCM> lstNominaCMExcel;
    private List<ParamArea> lstAreas;

    public void generarReporte() throws FileNotFoundException {

        file = null;
        int mesIni = obtenerMes(fecha_inicio);
        int mesFin = obtenerMes(fecha_fin);

        if (Util.validarFechaCambioEstado(fecha_inicio, fecha_fin)) {
            MovilidadUtil.addErrorMessage("La fecha de inicio no puede ser mayor a la fecha fin");
            return;
        }
        if (areasSeleccionadas.length > 0) {
            lstNominaCM = genericaJornadaEjb.obtenerDatosNominaCMConArea(fecha_inicio, fecha_fin, areasSeleccionadas);
        } else {
            lstNominaCM = genericaJornadaEjb.obtenerDatosNominaCMSinArea(fecha_inicio, fecha_fin);
        }

        if (lstNominaCM == null || (lstNominaCM != null && lstNominaCM.isEmpty())) {
            MovilidadUtil.addErrorMessage("No se encontraron datos");
            return;
        }

        if (mesIni != mesFin) {
            MovilidadUtil.addErrorMessage("El rango de fechas debe corresponder al mismo mes");
            return;
        }
        cargarDatosAExcel();
        generarExcel();
    }

    private void cargarDatosAExcel() {
        try {
            lstNominaCMExcel = new ArrayList<>();
            // num , obj
            HashMap<String, ParamReporteHoras> hReporte = new HashMap<>();
            HashMap<Integer, String> hCargoCc = new HashMap<>();

            List<ParamReporteHoras> lstParamReporteHoras = paramReporteHorasEjb.findAllActivos(0);
            List<ParamCargoCc> lstParamCargoCc = paramCargoCcEjb.findAllActivos();

            if (lstParamReporteHoras != null) {
                for (ParamReporteHoras rph : lstParamReporteHoras) {
                    hReporte.put(rph.getTipoHora(), rph);
                }

            }

            if (lstParamCargoCc != null) {
                for (ParamCargoCc cargoCc : lstParamCargoCc) {
                    hCargoCc.put(cargoCc.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo(), cargoCc.getCentroCosto());
                }
            }

            for (ReporteHorasCM reporteHora : lstNominaCM) {
                if (reporteHora.getNocturnas().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_NOCTURNAS);
                    EmpleadoCargoCosto cargoCosto = empleadoCargoCostoEjb.findByFechaYTipoCargo(reporteHora.getFecha(), reporteHora.getId_empleado_cargo());
                    if (paramReporteHoras != null && cargoCosto != null) {
                        float salarioAux = (float) cargoCosto.getCosto() / 30 / 8;

                        Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                        float recargo = (float) pRecargo / 100;
                        BigDecimal valor = BigDecimal.valueOf(salarioAux).multiply(reporteHora.getNocturnas()).multiply(BigDecimal.valueOf(recargo));
                        ReporteNominaCM r = new ReporteNominaCM();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setCentroCosto(hCargoCc.get(reporteHora.getId_empleado_cargo()));
                        r.setFechaMovimiento(fecha_movimiento);
                        r.setHoras(reporteHora.getNocturnas());
                        r.setValor(valor);
                        lstNominaCMExcel.add(r);
                    }
                }
                if (reporteHora.getExtra_diurna().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_EXTRA_DIURNA);
                    EmpleadoCargoCosto cargoCosto = empleadoCargoCostoEjb.findByFechaYTipoCargo(reporteHora.getFecha(), reporteHora.getId_empleado_cargo());
                    if (paramReporteHoras != null && cargoCosto != null) {
                        float salarioAux = (float) cargoCosto.getCosto() / 30 / 8;
                        Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                        float recargo = (float) pRecargo / 100;
                        BigDecimal valor = BigDecimal.valueOf(salarioAux).multiply(reporteHora.getExtra_diurna()).multiply(BigDecimal.valueOf(recargo));
                        ReporteNominaCM r = new ReporteNominaCM();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setCentroCosto(hCargoCc.get(reporteHora.getId_empleado_cargo()));
                        r.setFechaMovimiento(fecha_movimiento);
                        r.setHoras(reporteHora.getExtra_diurna());
                        r.setValor(valor);
                        lstNominaCMExcel.add(r);
                    }
                }
                if (reporteHora.getExtra_nocturna().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_EXTRA_NOCTURNA);
                    EmpleadoCargoCosto cargoCosto = empleadoCargoCostoEjb.findByFechaYTipoCargo(reporteHora.getFecha(), reporteHora.getId_empleado_cargo());
                    if (paramReporteHoras != null && cargoCosto != null) {
                        float salarioAux = (float) cargoCosto.getCosto() / 30 / 8;
                        Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                        float recargo = (float) pRecargo / 100;
                        BigDecimal valor = BigDecimal.valueOf(salarioAux).multiply(reporteHora.getExtra_nocturna()).multiply(BigDecimal.valueOf(recargo));
                        ReporteNominaCM r = new ReporteNominaCM();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setCentroCosto(hCargoCc.get(reporteHora.getId_empleado_cargo()));
                        r.setFechaMovimiento(fecha_movimiento);
                        r.setHoras(reporteHora.getExtra_nocturna());
                        r.setValor(valor);
                        lstNominaCMExcel.add(r);
                    }
                }
                if (reporteHora.getFestivo_diurno().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_FESTIVO_DIURNO);
                    EmpleadoCargoCosto cargoCosto = empleadoCargoCostoEjb.findByFechaYTipoCargo(reporteHora.getFecha(), reporteHora.getId_empleado_cargo());
                    if (paramReporteHoras != null && cargoCosto != null) {
                        float salarioAux = (float) cargoCosto.getCosto() / 30 / 8;
                        Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                        float recargo = (float) pRecargo / 100;
                        BigDecimal valor = BigDecimal.valueOf(salarioAux).multiply(reporteHora.getFestivo_diurno()).multiply(BigDecimal.valueOf(recargo));
                        ReporteNominaCM r = new ReporteNominaCM();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setCentroCosto(hCargoCc.get(reporteHora.getId_empleado_cargo()));
                        r.setFechaMovimiento(fecha_movimiento);
                        r.setHoras(reporteHora.getFestivo_diurno());
                        r.setValor(valor);
                        lstNominaCMExcel.add(r);
                    }
                }
                if (reporteHora.getFestivo_nocturno().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_FESTIVO_NOCTURNO);
                    EmpleadoCargoCosto cargoCosto = empleadoCargoCostoEjb.findByFechaYTipoCargo(reporteHora.getFecha(), reporteHora.getId_empleado_cargo());
                    if (paramReporteHoras != null && cargoCosto != null) {
                        float salarioAux = (float) cargoCosto.getCosto() / 30 / 8;
                        Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                        float recargo = (float) pRecargo / 100;
                        BigDecimal valor = BigDecimal.valueOf(salarioAux).multiply(reporteHora.getFestivo_nocturno()).multiply(BigDecimal.valueOf(recargo));
                        ReporteNominaCM r = new ReporteNominaCM();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setCentroCosto(hCargoCc.get(reporteHora.getId_empleado_cargo()));
                        r.setFechaMovimiento(fecha_movimiento);
                        r.setHoras(reporteHora.getFestivo_nocturno());
                        r.setValor(valor);
                        lstNominaCMExcel.add(r);
                    }
                }
                if (reporteHora.getFestivo_extra_diurno().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_FESTIVO_EXTRA_DIURNO);
                    EmpleadoCargoCosto cargoCosto = empleadoCargoCostoEjb.findByFechaYTipoCargo(reporteHora.getFecha(), reporteHora.getId_empleado_cargo());
                    if (paramReporteHoras != null && cargoCosto != null) {
                        float salarioAux = (float) cargoCosto.getCosto() / 30 / 8;
                        Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                        float recargo = (float) pRecargo / 100;
                        BigDecimal valor = BigDecimal.valueOf(salarioAux).multiply(reporteHora.getFestivo_extra_diurno()).multiply(BigDecimal.valueOf(recargo));
                        ReporteNominaCM r = new ReporteNominaCM();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setCentroCosto(hCargoCc.get(reporteHora.getId_empleado_cargo()));
                        r.setFechaMovimiento(fecha_movimiento);
                        r.setHoras(reporteHora.getFestivo_extra_diurno());
                        r.setValor(valor);
                        lstNominaCMExcel.add(r);
                    }
                }
                if (reporteHora.getFestivo_extra_nocturno().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_FESTIVO_EXTRA_NOCTURNO);
                    EmpleadoCargoCosto cargoCosto = empleadoCargoCostoEjb.findByFechaYTipoCargo(reporteHora.getFecha(), reporteHora.getId_empleado_cargo());
                    if (paramReporteHoras != null && cargoCosto != null) {
                        float salarioAux = (float) cargoCosto.getCosto() / 30 / 8;
                        Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                        float recargo = (float) pRecargo / 100;
                        BigDecimal valor = BigDecimal.valueOf(salarioAux).multiply(reporteHora.getFestivo_extra_nocturno()).multiply(BigDecimal.valueOf(recargo));
                        ReporteNominaCM r = new ReporteNominaCM();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setCentroCosto(hCargoCc.get(reporteHora.getId_empleado_cargo()));
                        r.setFechaMovimiento(fecha_movimiento);
                        r.setHoras(reporteHora.getFestivo_extra_nocturno());
                        r.setValor(valor);
                        lstNominaCMExcel.add(r);
                    }
                }
                if (reporteHora.getDominical_comp_diurnas().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_DOMINICAL_DIURNO_COMPENSATORIO);
                    EmpleadoCargoCosto cargoCosto = empleadoCargoCostoEjb.findByFechaYTipoCargo(reporteHora.getFecha(), reporteHora.getId_empleado_cargo());
                    if (paramReporteHoras != null && cargoCosto != null) {
                        float salarioAux = (float) cargoCosto.getCosto() / 30 / 8;
                        Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                        float recargo = (float) pRecargo / 100;
                        BigDecimal valor = BigDecimal.valueOf(salarioAux).multiply(reporteHora.getDominical_comp_diurnas()).multiply(BigDecimal.valueOf(recargo));
                        ReporteNominaCM r = new ReporteNominaCM();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setCentroCosto(hCargoCc.get(reporteHora.getId_empleado_cargo()));
                        r.setFechaMovimiento(fecha_movimiento);
                        r.setHoras(reporteHora.getDominical_comp_diurnas());
                        r.setValor(valor);
                        lstNominaCMExcel.add(r);
                    }
                }
                if (reporteHora.getDominical_comp_nocturnas().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_DOMINICAL_NOCTURNO_COMPENSATORIO);
                    EmpleadoCargoCosto cargoCosto = empleadoCargoCostoEjb.findByFechaYTipoCargo(reporteHora.getFecha(), reporteHora.getId_empleado_cargo());
                    if (paramReporteHoras != null && cargoCosto != null) {
                        float salarioAux = (float) cargoCosto.getCosto() / 30 / 8;
                        Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                        float recargo = (float) pRecargo / 100;
                        BigDecimal valor = BigDecimal.valueOf(salarioAux).multiply(reporteHora.getDominical_comp_nocturnas()).multiply(BigDecimal.valueOf(recargo));
                        ReporteNominaCM r = new ReporteNominaCM();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setCentroCosto(hCargoCc.get(reporteHora.getId_empleado_cargo()));
                        r.setFechaMovimiento(fecha_movimiento);
                        r.setHoras(reporteHora.getDominical_comp_nocturnas());
                        r.setValor(valor);
                        lstNominaCMExcel.add(r);
                    }
                }
                if (reporteHora.getDominical_comp_diurna_extra().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_DOMINICAL_EXTRA_DIURNO_COMPENSATORIO);
                    EmpleadoCargoCosto cargoCosto = empleadoCargoCostoEjb.findByFechaYTipoCargo(reporteHora.getFecha(), reporteHora.getId_empleado_cargo());
                    if (paramReporteHoras != null && cargoCosto != null) {
                        float salarioAux = (float) cargoCosto.getCosto() / 30 / 8;
                        Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                        float recargo = (float) pRecargo / 100;
                        BigDecimal valor = BigDecimal.valueOf(salarioAux).multiply(reporteHora.getDominical_comp_diurna_extra()).multiply(BigDecimal.valueOf(recargo));
                        ReporteNominaCM r = new ReporteNominaCM();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setCentroCosto(hCargoCc.get(reporteHora.getId_empleado_cargo()));
                        r.setFechaMovimiento(fecha_movimiento);
                        r.setHoras(reporteHora.getDominical_comp_diurna_extra());
                        r.setValor(valor);
                        lstNominaCMExcel.add(r);
                    }
                }
                if (reporteHora.getDominical_comp_nocturna_extra().compareTo(Util.CERO) == 1) {
                    ParamReporteHoras paramReporteHoras = hReporte.get(Util.RPH_DOMINICAL_EXTRA_NOCTURNO_COMPENSATORIO);
                    EmpleadoCargoCosto cargoCosto = empleadoCargoCostoEjb.findByFechaYTipoCargo(reporteHora.getFecha(), reporteHora.getId_empleado_cargo());
                    if (paramReporteHoras != null && cargoCosto != null) {
                        float salarioAux = (float) cargoCosto.getCosto() / 30 / 8;
                        Integer pRecargo = paramReporteHoras.getRecargo() != null ? paramReporteHoras.getRecargo() : 0;

                        float recargo = (float) pRecargo / 100;
                        BigDecimal valor = BigDecimal.valueOf(salarioAux).multiply(reporteHora.getDominical_comp_nocturna_extra()).multiply(BigDecimal.valueOf(recargo));
                        ReporteNominaCM r = new ReporteNominaCM();
                        r.setIdentificacion(reporteHora.getIdentificacion());
                        r.setCodigoConcepto(paramReporteHoras.getCodigo());
                        r.setCentroCosto(hCargoCc.get(reporteHora.getId_empleado_cargo()));
                        r.setFechaMovimiento(fecha_movimiento);
                        r.setHoras(reporteHora.getDominical_comp_nocturna_extra());
                        r.setValor(valor);
                        lstNominaCMExcel.add(r);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void generarExcel() throws FileNotFoundException {
        Map parametros = new HashMap();
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        plantilla = plantilla + "Nomina CableMovil.xlsx";
        parametros.put("reporteNomina", lstNominaCMExcel);

        destino = destino + "NÓMINA_CABLEMÓVIL.xlsx";

        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        MovilidadUtil.addSuccessMessage("Reporte generado éxitosamente");
        InputStream stream = new FileInputStream(excel);
        file = new DefaultStreamedContent(stream, "text/plain", "NÓMINA_CABLEMÓVIL_" + Util.dateFormat(fecha_inicio) + "_al_" + Util.dateFormat(fecha_fin) + ".xlsx");
    }

    private int obtenerMes(Date fecha) {
        current.setTime(fecha);
        return current.get(Calendar.MONTH) + 1;
    }

    private String obtenerMesParaInforme(Date fecha) {
        current.setTime(fecha);
        return meses[current.get(Calendar.MONTH)].toUpperCase() + " DEL " + current.get(Calendar.YEAR);
    }

    public int obtenerDia(Date fecha) {
        current.setTime(fecha);
        return current.get(Calendar.DAY_OF_MONTH);
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Calendar getCurrent() {
        return current;
    }

    public void setCurrent(Calendar current) {
        this.current = current;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public Date getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(Date fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public Integer[] getAreasSeleccionadas() {
        return areasSeleccionadas;
    }

    public void setAreasSeleccionadas(Integer[] areasSeleccionadas) {
        this.areasSeleccionadas = areasSeleccionadas;
    }

    public List<ReporteHorasCM> getLstNominaCM() {
        return lstNominaCM;
    }

    public void setLstNominaCM(List<ReporteHorasCM> lstNominaCM) {
        this.lstNominaCM = lstNominaCM;
    }

    public List<ParamArea> getLstAreas() {
        return paramAreaEjb.findAllEstadoReg();
    }

    public void setLstAreas(List<ParamArea> lstAreas) {
        this.lstAreas = lstAreas;
    }

}
