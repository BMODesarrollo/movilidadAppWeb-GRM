package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.GenericaJornadaFacadeLocal;
import com.movilidad.ejb.GenericaNominaAutorizacionFacadeLocal;
import com.movilidad.ejb.ParamAreaFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.ejb.ParamReporteHorasFacadeLocal;
import com.movilidad.model.GenericaNominaAutorizacion;
import com.movilidad.model.ParamArea;
import com.movilidad.model.ParamAreaUsr;
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
@Named(value = "autorizacionNominaGenericaBean")
@ViewScoped
public class GenericaNominaAutorizacionBean implements Serializable {

    @EJB
    private ParamReporteHorasFacadeLocal paramReporteHorasEjb;
    @EJB
    private GenericaNominaAutorizacionFacadeLocal genericaAutorizacionNominaEjb;
    @EJB
    private GenericaJornadaFacadeLocal genericaJornadaEjb;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUsrEjb;
    @EJB
    private ParamAreaFacadeLocal paramAreaEjb;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private KactusNovedadGenBean kactusNovedadBean;

    private final Locale locale = new Locale("es", "CO");
    private final Calendar current = Calendar.getInstance(locale);

    private Date fecha_inicio;
    private Date fecha_fin;
    private Date fechaPago;
    private Integer idParamArea;
    private GenericaNominaAutorizacion autorizacionNomina;

    private StreamedContent file;

    private boolean flagNoExisteAutorizacion;
    private boolean flagGestor;
    private boolean flagProfOp;
    private boolean flagDirOp;
    private boolean flagGhNomina;

    private List<ReporteHorasKactus> lstReporteHoras;
    private List<GenericaNominaAutorizacion> lista;
    private List<ParamArea> lstAreas; //Listado de áreas
    private List<Date> lstFechasConError; // Lista de fechas NO liquidadas

    private List<ConsolidadoNominaDetallado> lstConsolidadoDetallado;

    private final UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        flagGestor = validarRolGestor();
        flagProfOp = validarRolProfOp();
        flagDirOp = validarRolDirOp();
        flagGhNomina = validarRolGh();
        fechaPago = MovilidadUtil.fechaHoy();
        fecha_inicio = MovilidadUtil.fechaHoy();
        fecha_fin = MovilidadUtil.fechaHoy();
        consultar();
    }

    public void enviarNomina() {
        try {

            if (fechaPago == null) {
                MovilidadUtil.addErrorMessage("DEBE seleccionar una fecha de pago");
                return;
            }

            kactusNovedadBean.sendToKactus(
                    autorizacionNomina, Util.dateFormat(fechaPago)
            );
            autorizacionNomina.setEnviadoNomina(ConstantsUtil.ON_INT);
            autorizacionNomina.setFechaPago(fechaPago);
            genericaAutorizacionNominaEjb.edit(autorizacionNomina);
            fechaPago = MovilidadUtil.fechaHoy();
            MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("Error al enviar las novedades.");
        }
    }

    public void aprobarAutorizacion() {

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

            genericaAutorizacionNominaEjb.edit(autorizacionNomina);

            MovilidadUtil.addSuccessMessage("Autorización aprobada con éxito");
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public void reprobarAutorizacion() {

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

            genericaAutorizacionNominaEjb.edit(autorizacionNomina);

            MovilidadUtil.addSuccessMessage("Autorización rechazada con éxito");
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public void generarAutorizacion() {

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

        lstFechasConError = genericaJornadaEjb.validarDiasLiquidadosByFechasAndIdArea(fecha_inicio, fecha_fin, idParamArea);

        if (lstFechasConError != null && lstFechasConError.size() > 0) {
            MovilidadUtil.updateComponent(":frmFechasError:pGridDatos");
            MovilidadUtil.openModal("wlgFechasError");
            MovilidadUtil.addErrorMessage("Se han encontrado fechas NO liquidadas en el rango seleccionado");
            return;
        }

        lstReporteHoras = paramReporteHorasEjb.obtenerDatosReporteKactusGenericas(fecha_inicio, fecha_fin, idParamArea);

        if (lstReporteHoras == null || lstReporteHoras.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron datos");
            return;
        }

        if (idParamArea == null) {
            MovilidadUtil.addAdvertenciaMessage("El usuario logueado NO tiene un área asociada");
            return;
        }

        guardarDatos();
        consultar();
    }

    public int obtenerMes(Date fecha) {
        current.setTime(fecha);
        return current.get(Calendar.MONTH) + 1;
    }

    public void consultar() {

        int mesIni = obtenerMes(fecha_inicio);
        int mesFin = obtenerMes(fecha_fin);

        if (mesIni != mesFin) {
            MovilidadUtil.addErrorMessage("El rango de fechas debe corresponder al mismo mes");
            return;
        }

        if (flagGhNomina) {
            lstAreas = paramAreaEjb.findAllEstadoReg();
        }

        if (!validarArea()) {
            MovilidadUtil.addAdvertenciaMessage("El usuario logueado NO tiene un área asociada");
            return;
        }

        lista = genericaAutorizacionNominaEjb.findAllByRangoFechasAndArea(fecha_inicio, fecha_fin, idParamArea);

        if (lista == null || lista.isEmpty()) {
            flagNoExisteAutorizacion = true;
            MovilidadUtil.addAdvertenciaMessage("NO se encontraron datos");
            return;
        }

        flagNoExisteAutorizacion = false;

    }

    public void consultarGh() {

        int mesIni = obtenerMes(fecha_inicio);
        int mesFin = obtenerMes(fecha_fin);

        if (idParamArea == null) {
            MovilidadUtil.addAdvertenciaMessage("DEBE seleccionar un área");
            lista = null;
            return;
        }

        if (mesIni != mesFin) {
            MovilidadUtil.addErrorMessage("El rango de fechas debe corresponder al mismo mes");
            return;
        }

        lista = genericaAutorizacionNominaEjb.findAllByRangoFechasAndArea(fecha_inicio, fecha_fin, idParamArea);

        if (lista == null || lista.isEmpty()) {
            flagNoExisteAutorizacion = true;
            MovilidadUtil.addAdvertenciaMessage("NO se encontraron datos");
            return;
        }

        flagNoExisteAutorizacion = false;

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

        lstConsolidadoDetallado = genericaJornadaEjb.obtenerDatosDetalladoNomina(fecha_inicio, fecha_fin, idParamArea);

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

        destino = destino + "REPORTE_NOMINA_GEN.xlsx";

        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        InputStream stream = new FileInputStream(excel);
        file = new DefaultStreamedContent(stream, "text/plain", "RESUMEN DETALLADO_" + Util.dateFormat(fecha_inicio) + "_al_" + Util.dateFormat(fecha_fin) + ".xlsx");
    }

    @Transactional
    private void guardarDatos() {
        try {

            for (ReporteHorasKactus reporteHora : lstReporteHoras) {

                GenericaNominaAutorizacion r = new GenericaNominaAutorizacion();
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
                r.setIdParamArea(new ParamArea(idParamArea));
                r.setAprobacionGestor(0);
                r.setAprobacionProfOp(0);
                r.setAprobacionDirOp(0);
                r.setEnviadoNomina(0);
                r.setUsername(user.getUsername());
                r.setEstadoReg(0);
                r.setCreado(MovilidadUtil.fechaCompletaHoy());
                genericaAutorizacionNominaEjb.create(r);
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
            if (auth.getAuthority().contains("PROF")) {
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
            if (auth.getAuthority().contains("DIR")) {
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

    private boolean validarArea() {
        ParamAreaUsr paramAreaUsr = paramAreaUsrEjb.getByIdUser(user.getUsername());

        if (paramAreaUsr != null) {
            if (paramAreaUsr.getIdParamArea() != null) {
                idParamArea = paramAreaUsr.getIdParamArea().getIdParamArea();
                return true;
            }
        }

        return false;
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

    public List<GenericaNominaAutorizacion> getLista() {
        return lista;
    }

    public void setLista(List<GenericaNominaAutorizacion> lista) {
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

    public GenericaNominaAutorizacion getAutorizacionNomina() {
        return autorizacionNomina;
    }

    public void setAutorizacionNomina(GenericaNominaAutorizacion autorizacionNomina) {
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

    public boolean isFlagGhNomina() {
        return flagGhNomina;
    }

    public void setFlagGhNomina(boolean flagGhNomina) {
        this.flagGhNomina = flagGhNomina;
    }

    public List<ParamArea> getLstAreas() {
        return lstAreas;
    }

    public void setLstAreas(List<ParamArea> lstAreas) {
        this.lstAreas = lstAreas;
    }

    public Integer getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(Integer idParamArea) {
        this.idParamArea = idParamArea;
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

}
