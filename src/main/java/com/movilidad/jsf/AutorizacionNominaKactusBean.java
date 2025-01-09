package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.AutorizacionNominaKactusFacadeLocal;
import com.movilidad.ejb.ParamReporteHorasFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.model.NominaAutorizacion;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.NominaAutorizacionDet;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.ConsolidadoNominaDetallado;
import com.movilidad.util.beans.ReporteHorasKactus;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "autorizacionNominaKactusBean")
@ViewScoped
public class AutorizacionNominaKactusBean implements Serializable {

    @EJB
    private ParamReporteHorasFacadeLocal paramReporteHorasEjb;
    @EJB
    private AutorizacionNominaKactusFacadeLocal autorizacionNominaEjb;
    @EJB
    private PrgSerconFacadeLocal prgSerconEjb;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private KactusNovedadBean kactusNovedadBean;

    private final Locale locale = new Locale("es", "CO");
    private final Calendar current = Calendar.getInstance(locale);

    private Date fecha_inicio;
    private Date fecha_fin;
    private Date fechaPago;
    private NominaAutorizacion autorizacionNomina;

    private StreamedContent file;

    private boolean flagNoExisteAutorizacion;
    private boolean flagGestor;
    private boolean flagProfOp;
    private boolean flagDirOp;
    private boolean flagGhNomina;

    private List<ReporteHorasKactus> lstReporteHoras;
    private List<NominaAutorizacion> lista;
    private List<Date> lstFechasConError; // Lista de fechas NO liquidadas

    private List<ConsolidadoNominaDetallado> lstConsolidadoDetallado;

    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        flagGestor = validarRolGestor();
        flagProfOp = validarRolProfOp();
        flagDirOp = validarRolDirOp();
        flagGhNomina = validarRolGh();
        fecha_inicio = MovilidadUtil.fechaHoy();
        fecha_fin = MovilidadUtil.fechaHoy();
        fechaPago = MovilidadUtil.fechaHoy();
        consultar();
    }

    public void enviarNomina() {
        try {

            if (fechaPago == null) {
                MovilidadUtil.addErrorMessage("DEBE seleccionar una fecha de pago");
                return;
            }

            if (!kactusNovedadBean.sendToKactus(
                    autorizacionNomina,
                    Util.dateFormat(fechaPago)
            )) {
                return;
            }

            autorizacionNomina.setEnviadoNomina(ConstantsUtil.ON_INT);
            autorizacionNomina.setFechaPago(fechaPago);
            autorizacionNominaEjb.edit(autorizacionNomina);
            fechaPago = MovilidadUtil.fechaHoy();
            MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("Error al enviar las novedades.");
        }
    }

    public void aprobarAutorizacion() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        String validacion = validarAprobacion();

        if (validacion == null) {
            if (validarRolGestor()) {
                autorizacionNomina.setAprobacionGestor(1);
                autorizacionNomina.setUsrGestor(user.getUsername());
            }
            if (validarRolProfOp()) {
                autorizacionNomina.setAprobacionProfOp(1);
                autorizacionNomina.setUsrProfOp(user.getUsername());
            }
            if (validarRolDirOp()) {
                autorizacionNomina.setAprobacionDirOp(1);
                autorizacionNomina.setUsrDirOp(user.getUsername());
            }

            autorizacionNominaEjb.edit(autorizacionNomina);

            MovilidadUtil.addSuccessMessage("Autorización aprobada con éxito");
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public void reprobarAutorizacion() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        String validacion = validarReprobacionRegistro();

        if (validacion == null) {
            if (validarRolGestor()) {
                autorizacionNomina.setAprobacionGestor(0);
            }
            if (validarRolProfOp()) {
                autorizacionNomina.setAprobacionProfOp(0);
            }
            if (validarRolDirOp()) {
                autorizacionNomina.setAprobacionDirOp(0);
            }

            autorizacionNominaEjb.edit(autorizacionNomina);

            MovilidadUtil.addSuccessMessage("Autorización rechazada con éxito");
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public void generarAutorizacion() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        int mesIni = obtenerMes(fecha_inicio);
        int mesFin = obtenerMes(fecha_fin);

        if (Util.validarFechaCambioEstado(fecha_inicio, fecha_fin)) {
            MovilidadUtil.addErrorMessage("La fecha de inicio no puede ser mayor a la fecha fin");
            return;
        }

        if (mesIni != mesFin) {
            MovilidadUtil.addErrorMessage("El rango de fechas debe corresponder al mismo mes");
            return;
        }

        lstFechasConError = prgSerconEjb.validarDiasLiquidadosByFechasAndUnidadFuncional(fecha_inicio, fecha_fin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (lstFechasConError != null && lstFechasConError.size() > 0) {
            MovilidadUtil.updateComponent(":frmFechasError:pGridDatos");
            MovilidadUtil.openModal("wlgFechasError");
            MovilidadUtil.addErrorMessage("Se han encontrado fechas NO liquidadas en el rango seleccionado");
            return;
        }

        lstReporteHoras = paramReporteHorasEjb.obtenerDatosReporteKactus(fecha_inicio, fecha_fin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (lstReporteHoras == null || lstReporteHoras.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron datos");
            return;
        }

        guardarDatos();
        consultar();
    }

    public void calcularFecha() {
        fecha_fin = MovilidadUtil.sumarDias(fecha_inicio, ConstantsUtil.PARAM_DIAS_FILTRO);
    }

    /**
     * Método Responsable de consultar las jornadas por rango de fechas y
     * incovar al método generarExcel, para construir el reporte detallado de
     * jornadas.
     *
     * @throws FileNotFoundException
     */
    public void generarReporte() throws FileNotFoundException {

        file = null;

        if (Util.validarFechaCambioEstado(fecha_inicio, fecha_fin)) {
            MovilidadUtil.addErrorMessage("La fecha de inicio no puede ser mayor a la fecha fin");
            return;
        }

        lstConsolidadoDetallado = prgSerconEjb.obtenerDatosDetalladoNomina(fecha_inicio, fecha_fin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (lstConsolidadoDetallado == null || lstConsolidadoDetallado.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron datos");
            return;
        }
        generarExcel();

    }

    /**
     * Método Responsable de construir el archivo Excel del detallado de
     * jornadas.
     *
     * @throws FileNotFoundException
     */
    private void generarExcel() throws FileNotFoundException {
        Map parametros = new HashMap();
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        plantilla = plantilla + "Reporte Nomina.xlsx";
        parametros.put("lista", lstConsolidadoDetallado);

        destino = destino + "REPORTE_NOMINA.xlsx";

        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        InputStream stream = new FileInputStream(excel);
        file = new DefaultStreamedContent(stream, "text/plain", "RESUMEN DETALLADO_" + Util.dateFormat(fecha_inicio) + "_al_" + Util.dateFormat(fecha_fin) + ".xlsx");
    }

    @Transactional
    private void guardarDatos() {
        try {

            for (ReporteHorasKactus reporteHora : lstReporteHoras) {

                NominaAutorizacion r = new NominaAutorizacion();
                r.setDesde(fecha_inicio);
                r.setHasta(fecha_fin);
                r.setDiurnas(reporteHora.getDiurnas().intValue());
                r.setNocturnas(reporteHora.getNocturnas().intValue());
                r.setExtraDiurna(reporteHora.getExtra_diurna().intValue());
                r.setExtraNocturna(reporteHora.getExtra_nocturna().intValue());
                r.setFestivoDiurno(reporteHora.getFestivo_diurno().intValue());
                r.setFestivoNocturno(reporteHora.getFestivo_nocturno().intValue());
                r.setFestivoExtraDiurno(reporteHora.getFestivo_extra_diurno().intValue());
                r.setFestivoExtraNocturno(reporteHora.getFestivo_extra_nocturno().intValue());
                r.setDominicalCompDiurnas(reporteHora.getDominical_comp_diurnas().intValue());
                r.setDominicalCompNocturnas(reporteHora.getDominical_comp_nocturnas().intValue());
                r.setDominicalCompDiurnaExtra(reporteHora.getDominical_comp_diurna_extra().intValue());
                r.setDominicalCompNocturnaExtra(reporteHora.getDominical_comp_nocturna_extra().intValue());
                r.setIdGopUnidadFuncional(new GopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));
                r.setAprobacionGestor(0);
                r.setAprobacionProfOp(0);
                r.setAprobacionDirOp(0);
                r.setEnviadoNomina(0);
                r.setUsername(user.getUsername());
                r.setEstadoReg(0);
                r.setCreado(MovilidadUtil.fechaCompletaHoy());
                autorizacionNominaEjb.create(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("Error al guardar datos");
        }
    }

    private String validarAprobacion() {

        if (validarRolProfOp()) {
            if (autorizacionNomina.getAprobacionGestor() == 0) {
                return "NO se puede realizar aprobación YA que el Gestor NO ha realizado la aprobación";
            }
        }
        if (validarRolDirOp()) {
            if (autorizacionNomina.getAprobacionProfOp() == 0) {
                return "NO se puede realizar aprobación YA que el Profesional NO ha realizado la aprobación";
            }
        }

        return null;
    }

    private String validarReprobacionRegistro() {

        if (validarRolGestor()) {
            if (autorizacionNomina.getAprobacionProfOp() == 1) {
                return "NO se puede reprobar registro YA que existe aprobación por parte del Profesional de Operaciones";
            }
        }
        if (validarRolProfOp()) {
            if (autorizacionNomina.getAprobacionDirOp() == 1) {
                return "NO se puede reprobar registro YA que existe aprobación por parte del Director de Operaciones";
            }
        }

        return null;
    }

    /**
     * Valida si el usuario logueado corresponde a un gestor
     *
     * @return true si el usuario tiene rol ROLE_LIQ
     */
    private boolean validarRolGestor() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().contains("LIQ")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Valida si el usuario logueado corresponde al profesional de operaciones
     *
     * @return true si el usuario tiene rol ROLE_PROFOP
     */
    private boolean validarRolProfOp() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_PROFOP")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Valida si el usuario logueado corresponde a un director de operaciones
     *
     * @return true si el usuario tiene rol ROLE_DIROP
     */
    private boolean validarRolDirOp() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_DIROP")) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Valida si el usuario logueado tienen el rol de Gestión humana
     *
     * @return true si el usuario tiene rol ROLE_GH_NOMINA
     */
    private boolean validarRolGh() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().contains("GH")) {
                return true;
            }
        }
        return false;
    }

    public int obtenerMes(Date fecha) {
        current.setTime(fecha);
        return current.get(Calendar.MONTH) + 1;
    }

    public void consultar() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        if (Util.validarFechaCambioEstado(fecha_inicio, fecha_fin)) {
            MovilidadUtil.addErrorMessage("La fecha de inicio DEBE ser menor a la fecha fin");
            return;
        }

        int mesIni = obtenerMes(fecha_inicio);
        int mesFin = obtenerMes(fecha_fin);

        if (mesIni != mesFin) {
            MovilidadUtil.addErrorMessage("El rango de fechas debe corresponder al mismo mes");
            return;
        }

        lista = autorizacionNominaEjb.findAllByRangoFechasAndUF(fecha_inicio, fecha_fin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (lista == null || lista.isEmpty()) {
            flagNoExisteAutorizacion = true;
            MovilidadUtil.addAdvertenciaMessage("NO se encontraron datos");
            return;
        }

        flagNoExisteAutorizacion = false;

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

    public List<NominaAutorizacion> getLista() {
        return lista;
    }

    public void setLista(List<NominaAutorizacion> lista) {
        this.lista = lista;
    }

    public boolean isFlagNoExisteAutorizacion() {
        return flagNoExisteAutorizacion;
    }

    public void setFlagNoExisteAutorizacion(boolean flagNoExisteAutorizacion) {
        this.flagNoExisteAutorizacion = flagNoExisteAutorizacion;
    }

    public List<Date> getLstFechasConError() {
        return lstFechasConError;
    }

    public void setLstFechasConError(List<Date> lstFechasConError) {
        this.lstFechasConError = lstFechasConError;
    }

    public NominaAutorizacion getAutorizacionNomina() {
        return autorizacionNomina;
    }

    public void setAutorizacionNomina(NominaAutorizacion autorizacionNomina) {
        this.autorizacionNomina = autorizacionNomina;
    }

    public boolean isFlagGestor() {
        return flagGestor;
    }

    public void setFlagGestor(boolean flagGestor) {
        this.flagGestor = flagGestor;
    }

    public boolean isFlagProfOp() {
        return flagProfOp;
    }

    public void setFlagProfOp(boolean flagProfOp) {
        this.flagProfOp = flagProfOp;
    }

    public boolean isFlagDirOp() {
        return flagDirOp;
    }

    public void setFlagDirOp(boolean flagDirOp) {
        this.flagDirOp = flagDirOp;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public boolean isFlagGhNomina() {
        return flagGhNomina;
    }

    public void setFlagGhNomina(boolean flagGhNomina) {
        this.flagGhNomina = flagGhNomina;
    }
    
}
