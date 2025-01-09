package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.ejb.MultaFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.PrgTcResumenFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.PrgTcResumen;
import com.movilidad.util.beans.AccidenteCtrl;
import com.movilidad.util.beans.InformeControl;
import com.movilidad.util.beans.KmsAdicionalesCtrl;
import com.movilidad.util.beans.KmsPerdidosArt;
import com.movilidad.util.beans.KmsPerdidosBi;
import com.movilidad.util.beans.MultasCtrl;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "informeControlBean")
@ViewScoped
public class InformeControlManagedBean implements Serializable {

    @EJB
    private PrgTcResumenFacadeLocal prgTcResumenEjb;
    @EJB
    private MultaFacadeLocal multaEjb;
    @EJB
    private NovedadFacadeLocal novedadEjb;
    @EJB
    private PrgTcFacadeLocal prgTcEjb;
    @EJB
    private VehiculoFacadeLocal vehiculoEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private String mes;
    private String anio;
    private StreamedContent file;

    @PostConstruct
    public void init() {
    }

    public void generarReporte() throws FileNotFoundException, ParseException, IOException {
        Map parametros = new HashMap();
        int errores = 0;
        int busesOperando = 0;
        int busesDisponibles = 0;
        int contador;
        int mc_contador;
        PrgTcResumen resumen;
        String fecha = anio + "-" + mes + "-01";
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        destino = destino + "Informe_Control.xlsx";
        String ac_codigo_operador;
        String ac_nombre_operador;
        String ac_bus;
        long incidente;
        long percance;
        long TM01;
        long TM02;
        long TM16;
        String mc_codigo_operador;
        String mc_codigo_vehiculo;
        String mc_nombre_operador;
        long mc_operaciones;
        long mc_mantenimiento;
        long mc_lavado;
        long mc_seguridad_vial;
        BigDecimal mc_metros_operaciones;
        BigDecimal mc_metros_mantenimiento;
        BigDecimal mc_metros_lavado;
        BigDecimal mc_metros_seguridad_vial;

        Calendar c = Calendar.getInstance();
        c.setTime(Util.toDate(fecha));
        int numberOfDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (compararFecha(c)) {
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("El año seleccionado es mayor al actual");
            return;
        }

        switch (numberOfDays) {
            case 28:
                plantilla = plantilla + "InformeControl28.xlsx";
                break;
            case 29:
                plantilla = plantilla + "InformeControl29.xlsx";
                break;
            case 30:
                plantilla = plantilla + "InformeControl30.xlsx";
                break;
            case 31:
                plantilla = plantilla + "InformeControl31.xlsx";
                break;
        }

        for (int i = 1; i <= numberOfDays; i++) {
            resumen = prgTcResumenEjb.findByFecha(Util.toDate(Util.dateFormat(c.getTime())), unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            if (resumen == null) {
                parametros.put(("resumen" + i), new InformeControl(Util.toDate(Util.dateFormat(c.getTime()))));
                parametros.put(("ac_codigo_operador" + i), "N/A");
                parametros.put(("ac_nombre_operador" + i), "N/A");
                parametros.put(("ac_bus" + i), "N/A");
                parametros.put(("ac_percance" + i), 0);
                parametros.put(("ac_incidente" + i), 0);
                parametros.put(("ac_TM01_" + i), 0);
                parametros.put(("ac_TM02_" + i), 0);
                parametros.put(("ac_TM16_" + i), 0);
                parametros.put(("mc_codigo_operador" + i), "N/A");
                parametros.put(("mc_nombre_operador" + i), "N/A");
                parametros.put(("mc_bus" + i), "N/A");
                parametros.put(("mc_operaciones" + i), 0);
                parametros.put(("mc_mantenimiento" + i), 0);
                parametros.put(("mc_lavado" + i), 0);
                parametros.put(("mc_seguridad_vial_" + i), 0);
                errores++;
            } else {
                mc_contador = 0;
                contador = 0;
                mc_codigo_operador = "";
                mc_codigo_vehiculo = "";
                mc_nombre_operador = "";
                mc_operaciones = 0;
                mc_mantenimiento = 0;
                mc_lavado = 0;
                mc_seguridad_vial = 0;
                mc_metros_operaciones = Util.CERO;
                mc_metros_mantenimiento = Util.CERO;
                mc_metros_lavado = Util.CERO;
                mc_metros_seguridad_vial = Util.CERO;
                ac_codigo_operador = "";
                ac_nombre_operador = "";
                ac_bus = "";
                incidente = 0;
                percance = 0;
                TM01 = 0;
                TM02 = 0;
                TM16 = 0;
                int idGopUnidadFuncional = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
                busesOperando = vehiculoEjb.getVehiculosOperando(resumen.getFecha(), idGopUnidadFuncional);
                busesDisponibles = vehiculoEjb.getVehiculosDisponibles(idGopUnidadFuncional);
                KmsPerdidosArt perdidosArt = prgTcEjb.getEliminadosArtCtrl(resumen.getFecha(), idGopUnidadFuncional);
                KmsPerdidosBi perdidosBi = prgTcEjb.getEliminadosBiCtrl(resumen.getFecha(), idGopUnidadFuncional);
                KmsAdicionalesCtrl adicionalesCtrl = prgTcEjb.getAdicionalesCtrl(resumen.getFecha(), idGopUnidadFuncional);
                List<AccidenteCtrl> accidenteCtrl = novedadEjb.obtenerDetalleAccidente(resumen.getFecha(), idGopUnidadFuncional);
                List<MultasCtrl> multasCtrl = multaEjb.findMultasCtrl(resumen.getFecha(), idGopUnidadFuncional);
                if (!accidenteCtrl.isEmpty()) {
                    for (AccidenteCtrl ac : accidenteCtrl) {
                        if (ac != null) {
                            ac_codigo_operador += ac.getCodigo_operador() + "\n";
                            ac_nombre_operador += ac.getNombre_operador() + "\n";
                            ac_bus += ac.getCodigo_vehiculo() + "/";
                            incidente += ac.getIncidente();
                            percance += ac.getPercance();
                            TM01 += ac.getTM01();
                            TM02 += ac.getTM02();
                            TM16 += ac.getTM16();
                            contador++;
                            if (contador == accidenteCtrl.size()) {
                                parametros.put(("ac_codigo_operador" + i), ac_codigo_operador);
                                parametros.put(("ac_nombre_operador" + i), ac_nombre_operador);
                                parametros.put(("ac_bus" + i), ac_bus);
                                parametros.put(("ac_percance" + i), percance);
                                parametros.put(("ac_incidente" + i), incidente);
                                parametros.put(("ac_TM01_" + i), TM01);
                                parametros.put(("ac_TM02_" + i), TM02);
                                parametros.put(("ac_TM16_" + i), TM16);
                            }
                        } else {
                            parametros.put(("ac_codigo_operador" + i), "N/A");
                            parametros.put(("ac_nombre_operador" + i), "N/A");
                            parametros.put(("ac_bus" + i), "N/A");
                            parametros.put(("ac_percance" + i), 0);
                            parametros.put(("ac_incidente" + i), 0);
                            parametros.put(("ac_TM01_" + i), 0);
                            parametros.put(("ac_TM02_" + i), 0);
                            parametros.put(("ac_TM16_" + i), 0);
                        }
                    }
                } else {
                    parametros.put(("ac_codigo_operador" + i), "N/A");
                    parametros.put(("ac_nombre_operador" + i), "N/A");
                    parametros.put(("ac_bus" + i), "N/A");
                    parametros.put(("ac_percance" + i), 0);
                    parametros.put(("ac_incidente" + i), 0);
                    parametros.put(("ac_TM01_" + i), 0);
                    parametros.put(("ac_TM02_" + i), 0);
                    parametros.put(("ac_TM16_" + i), 0);
                }
                if (!multasCtrl.isEmpty()) {
                    for (MultasCtrl mc : multasCtrl) {
                        if (mc != null) {
                            mc_codigo_operador += mc.getCodigo_operador() + "\n";
                            mc_nombre_operador += mc.getNombre_operador() + "\n";
                            mc_codigo_vehiculo += mc.getCodigo_vehiculo() + "/";
                            mc_operaciones += mc.getOperaciones();
                            mc_mantenimiento += mc.getMantenimiento();
                            mc_lavado += mc.getLavado();
                            mc_seguridad_vial += mc.getSeguridad_vial();
                            mc_metros_operaciones = mc_metros_operaciones.add(mc.getKms_operaciones());
                            mc_metros_mantenimiento = mc_metros_mantenimiento.add(mc.getKms_mantenimiento());
                            mc_metros_lavado = mc_metros_lavado.add(mc.getKms_lavado());
                            mc_metros_seguridad_vial = mc_metros_seguridad_vial.add(mc.getKms_seguridad_vial());
                            mc_contador++;
                            if (mc_contador == multasCtrl.size()) {
                                parametros.put(("mc_codigo_operador" + i), mc_codigo_operador);
                                parametros.put(("mc_nombre_operador" + i), mc_nombre_operador);
                                parametros.put(("mc_bus" + i), mc_codigo_vehiculo);
                                parametros.put(("mc_operaciones" + i), mc_operaciones);
                                parametros.put(("mc_mantenimiento" + i), mc_mantenimiento);
                                parametros.put(("mc_lavado" + i), mc_lavado);
                                parametros.put(("mc_seguridad_vial_" + i), mc_seguridad_vial);
                                parametros.put(("mc_kms_operaciones" + i), mc_metros_operaciones);
                                parametros.put(("mc_kms_mantenimiento" + i), mc_metros_mantenimiento);
                                parametros.put(("mc_kms_lavado" + i), mc_metros_lavado);
                                parametros.put(("mc_kms_seguridad_vial" + i), mc_metros_seguridad_vial);
                                parametros.put(("mc_total_kms" + i), (mc_metros_operaciones.add(mc_metros_mantenimiento.add(mc_metros_lavado.add(mc_metros_seguridad_vial)))));
                                parametros.put(("mc_total_multas" + i), (mc_operaciones + mc_mantenimiento + mc_lavado + mc_seguridad_vial));
                            }
                        } else {
                            parametros.put(("mc_codigo_operador" + i), "N/A");
                            parametros.put(("mc_nombre_operador" + i), "N/A");
                            parametros.put(("mc_bus" + i), "N/A");
                            parametros.put(("mc_operaciones" + i), 0);
                            parametros.put(("mc_mantenimiento" + i), 0);
                            parametros.put(("mc_lavado" + i), 0);
                            parametros.put(("mc_seguridad_vial_" + i), 0);
                            parametros.put(("mc_kms_operaciones" + i), 0);
                            parametros.put(("mc_kms_mantenimiento" + i), 0);
                            parametros.put(("mc_kms_lavado" + i), 0);
                            parametros.put(("mc_kms_seguridad_vial" + i), 0);
                            parametros.put(("mc_total_kms" + i), 0);
                            parametros.put(("mc_total_multas" + i), 0);
                        }
                    }
                } else {
                    parametros.put(("mc_codigo_operador" + i), "N/A");
                    parametros.put(("mc_nombre_operador" + i), "N/A");
                    parametros.put(("mc_bus" + i), "N/A");
                    parametros.put(("mc_operaciones" + i), 0);
                    parametros.put(("mc_mantenimiento" + i), 0);
                    parametros.put(("mc_lavado" + i), 0);
                    parametros.put(("mc_seguridad_vial_" + i), 0);
                    parametros.put(("mc_kms_operaciones" + i), 0);
                    parametros.put(("mc_kms_mantenimiento" + i), 0);
                    parametros.put(("mc_kms_lavado" + i), 0);
                    parametros.put(("mc_kms_seguridad_vial" + i), 0);
                    parametros.put(("mc_total_kms" + i), 0);
                    parametros.put(("mc_total_multas" + i), 0);
                }
                parametros.put(("resumen" + i), new InformeControl(resumen, busesOperando, busesDisponibles, perdidosArt, perdidosBi, adicionalesCtrl));
            }
            c.add(Calendar.DATE, 1);
        }

        parametros.put("mes", getMonth(mes, anio));
        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        readFormula(destino);
        InputStream stream = new FileInputStream(excel);
        file = new DefaultStreamedContent(stream, "text/plain", "Informe_Control.xlsx");
        PrimeFaces.current().ajax().update("frmInfoControl");
        MovilidadUtil.addSuccessMessage("Reporte generado exitósamente");
    }

    public void readFormula(String destino) throws IOException {
        FileInputStream fis = new FileInputStream(destino);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
        for (Sheet sheet : wb) {
            for (Row r : sheet) {
                for (Cell c : r) {
                    if (c.getCellType() == Cell.CELL_TYPE_FORMULA) {
//                        System.out.println(c.getAddress() + ":" + c.getCellFormula());
                        evaluator.evaluateFormulaCell(c);
                        c.setCellFormula(c.getCellFormula());
                    }
                }
            }
        }

    }

    private static String getMonth(String mes, String anio) throws ParseException {
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        int mes_Aux = Integer.parseInt(mes);
        return meses[mes_Aux - 1].toUpperCase() + "\n" + anio;
    }

    private boolean compararFecha(Calendar calendar) {
        int anioAct = new Date().getYear();
        int anioFecha = calendar.get(Calendar.YEAR) - 1900;
        return anioFecha > anioAct;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

}
