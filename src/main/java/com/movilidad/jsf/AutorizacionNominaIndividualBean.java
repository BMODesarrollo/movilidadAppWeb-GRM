package com.movilidad.jsf;

import com.movilidad.ejb.AutorizacionNominaKactusFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NominaAutorizacionIndividualFacadeLocal;
import com.movilidad.ejb.ParamReporteHorasFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.NominaAutorizacionIndividual;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.NominaAutorizacion;
import com.movilidad.model.NominaAutorizacionDet;
import com.movilidad.model.NominaAutorizacionDetIndividual;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.ReporteHorasKactus;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "autorizacionNominaIndividualBean")
@ViewScoped
public class AutorizacionNominaIndividualBean implements Serializable {

    @EJB
    private ParamReporteHorasFacadeLocal paramReporteHorasEjb;
    @EJB
    private AutorizacionNominaKactusFacadeLocal autorizacionNominaGeneralEjb;
    @EJB
    private NominaAutorizacionIndividualFacadeLocal autorizacionNominaEjb;
    @EJB
    private PrgSerconFacadeLocal prgSerconEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private SelectEmpleadoBean selectEmpleadoBean;
    @Inject
    private KactusNovedadIndBean kactusNovedadIndBean;

    private final Locale locale = new Locale("es", "CO");
    private final Calendar current = Calendar.getInstance(locale);

    private Date fecha_inicio;
    private Date fecha_fin;
    private Date fechaPago;
    private NominaAutorizacionIndividual autorizacionNomina;

    private boolean flagNoExisteAutorizacion;
    private boolean flagGestor;
    private boolean flagProfOp;
    private boolean flagDirOp;
    private boolean flagGhNomina;

    private List<ReporteHorasKactus> lstReporteHoras;
    private List<NominaAutorizacionIndividual> lista;
    private List<Date> lstFechasConError; // Lista de fechas NO liquidadas

    private HashMap<String, NominaAutorizacionDetIndividual> mapNominaAutoDetIndv;

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
        selectEmpleadoBean.setIdEmpleado(null);

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() > 0) {
            selectEmpleadoBean.cargarListadoEmpleados();
        }

    }

    public void enviarNomina() {
        try {

            if (validarRegistroNominaGeneral()) {
                MovilidadUtil.addErrorMessage("YA existe registro en la nómina enviada a KACTUS, para el operador seleccionado.");
                autorizacionNomina.setEnviadoNomina(ConstantsUtil.ON_INT);
                autorizacionNominaEjb.edit(autorizacionNomina);
                return;
            }

            if (fechaPago == null) {
                MovilidadUtil.addErrorMessage("DEBE seleccionar una fecha de pago");
                return;
            }

            if (!kactusNovedadIndBean.sendToKactus(
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

        lstFechasConError = prgSerconEjb.validarDiasLiquidadosByFechasAndUnidadFuncionalAndOperador(fecha_inicio, fecha_fin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), selectEmpleadoBean.getIdEmpleado());

        if (lstFechasConError != null && lstFechasConError.size() > 0) {
            MovilidadUtil.updateComponent(":frmFechasError:pGridDatos");
            MovilidadUtil.openModal("wlgFechasError");
            MovilidadUtil.addErrorMessage("Se han encontrado fechas NO liquidadas en el rango seleccionado");
            return;
        }

        if (validarRegistroNominaGeneralGuardar()) {
            MovilidadUtil.addErrorMessage("YA existe registro en la nómina enviada a KACTUS, para el operador seleccionado.");
            return;
        }

        NominaAutorizacionIndividual obj = autorizacionNominaEjb.findAllByRangoFechasAndUFAndOperador(fecha_inicio, fecha_fin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), selectEmpleadoBean.getIdEmpleado());

        if (obj != null) {
            MovilidadUtil.addErrorMessage("Ya existen registro(s) dentro del rango de fechas indicados");
            return;
        }

        lstReporteHoras = paramReporteHorasEjb.obtenerDatosReporteKactusIndividual(fecha_inicio, fecha_fin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), selectEmpleadoBean.getIdEmpleado());

        if (lstReporteHoras == null || lstReporteHoras.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron datos");
            return;
        }

        cargarMapDetallesIndividual(fecha_inicio, fecha_fin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        guardarDatos();
        consultar();
    }

    public void calcularFecha() {
        fecha_fin = MovilidadUtil.sumarDias(fecha_inicio, ConstantsUtil.PARAM_DIAS_FILTRO);
    }

    @Transactional
    private void guardarDatos() {
        try {

            for (ReporteHorasKactus reporteHora : lstReporteHoras) {

                NominaAutorizacionDetIndividual item = mapNominaAutoDetIndv.get(empleadoEjb.find(selectEmpleadoBean.getIdEmpleado()).getIdentificacion());

                if (item == null) {
                    NominaAutorizacionIndividual r = new NominaAutorizacionIndividual();
                    r.setIdEmpleado(new Empleado(selectEmpleadoBean.getIdEmpleado()));
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

            }
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("Error al guardar datos");
        }
    }

    private void cargarMapDetallesIndividual(Date desde, Date hasta, int idGopUnidadFuncional) {
        mapNominaAutoDetIndv = new HashMap<>();
        List<NominaAutorizacionIndividual> autorizaciones
                = autorizacionNominaEjb.findAllByRangoFechasAndUF(desde, hasta, idGopUnidadFuncional, 0);

        for (NominaAutorizacionIndividual obj : autorizaciones) {

            if (obj.getEnviadoNomina() == 1) {
                if (obj.getNominaAutorizacionDetIndividualList() != null) {

                    for (NominaAutorizacionDetIndividual item : obj.getNominaAutorizacionDetIndividualList()) {
                        mapNominaAutoDetIndv.put(item.getIdentificacion(), item);
                    }
                }
            }

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

    public void cargarListaEmpleados() {
        selectEmpleadoBean.cargarListadoEmpleados();
    }

    public void consultar() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        if (selectEmpleadoBean.getIdEmpleado() == null) {
            MovilidadUtil.addAdvertenciaMessage("DEBE seleccionar un empleado");
            return;
        }

        int mesIni = obtenerMes(fecha_inicio);
        int mesFin = obtenerMes(fecha_fin);

        if (mesIni != mesFin) {
            MovilidadUtil.addErrorMessage("El rango de fechas debe corresponder al mismo mes");
            return;
        }

        lista = autorizacionNominaEjb.findAllByRangoFechasAndUF(fecha_inicio, fecha_fin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), selectEmpleadoBean.getIdEmpleado());

        if (lista == null || lista.isEmpty()) {
            flagNoExisteAutorizacion = true;
            MovilidadUtil.addAdvertenciaMessage("NO se encontraron datos");
            return;
        }

        flagNoExisteAutorizacion = false;
    }

    private boolean validarRegistroNominaGeneral() {
        List<NominaAutorizacion> lstNominaGeneral = autorizacionNominaGeneralEjb.findAllByRangoFechasAndUF(fecha_inicio, fecha_fin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (lstNominaGeneral != null) {
            for (NominaAutorizacion item : lstNominaGeneral) {
                if (item.getEnviadoNomina() == ConstantsUtil.ON_INT) {
                    if (item.getNominaAutorizacionDetList() != null) {
                        for (NominaAutorizacionDet obj : item.getNominaAutorizacionDetList()) {
                            if (obj.getIdentificacion().equals(autorizacionNomina.getIdEmpleado().getIdentificacion())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean validarRegistroNominaGeneralGuardar() {
        List<NominaAutorizacion> lstNominaGeneral = autorizacionNominaGeneralEjb.findAllByRangoFechasAndUF(fecha_inicio, fecha_fin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (lstNominaGeneral != null) {

            Empleado emp = empleadoEjb.find(selectEmpleadoBean.getIdEmpleado());

            for (NominaAutorizacion item : lstNominaGeneral) {
                if (item.getEnviadoNomina() == ConstantsUtil.ON_INT) {
                    if (item.getNominaAutorizacionDetList() != null) {
                        for (NominaAutorizacionDet obj : item.getNominaAutorizacionDetList()) {
                            if (obj.getIdentificacion().equals(emp.getIdentificacion())) {
                                return true;
                            }
                        }
                    }
                }
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

    public List<NominaAutorizacionIndividual> getLista() {
        return lista;
    }

    public void setLista(List<NominaAutorizacionIndividual> lista) {
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

    public NominaAutorizacionIndividual getAutorizacionNomina() {
        return autorizacionNomina;
    }

    public void setAutorizacionNomina(NominaAutorizacionIndividual autorizacionNomina) {
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
