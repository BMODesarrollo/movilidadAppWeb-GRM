package com.movilidad.jsf;

import com.movilidad.ejb.NovedadAutorizacionAusentismosFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.model.NovedadAutorizacionAusentismos;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.Novedad;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
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
@Named(value = "autorizacionNovedadAusentismosBean")
@ViewScoped
public class AutorizacionNovedadAusentismoBean implements Serializable {

    @EJB
    private NovedadFacadeLocal novedadEjb;
    @EJB
    private NovedadAutorizacionAusentismosFacadeLocal autorizacionNovedadEjb;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private KactusNovedadAusentismoBean kactusNovedadBean;

    private final Locale locale = new Locale("es", "CO");
    private final Calendar current = Calendar.getInstance(locale);

    private Date fecha_inicio;
    private Date fecha_fin;
    private NovedadAutorizacionAusentismos autorizacionNovedad;

    private boolean flagNoExisteAutorizacion;
    private boolean flagGestor;
    private boolean flagProfOp;
    private boolean flagDirOp;
    private boolean flagGhNomina;

    private List<Novedad> lstNovedades;
    private List<NovedadAutorizacionAusentismos> lista;

    private final UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        flagGestor = validarRolGestor();
        flagProfOp = validarRolProfOp();
        flagDirOp = validarRolDirOp();
        flagGhNomina = validarRolGh();
        fecha_inicio = MovilidadUtil.fechaHoy();
        fecha_fin = MovilidadUtil.fechaHoy();
        consultar();
    }

    @Transactional
    public void enviarNomina() {
        try {
            if (!kactusNovedadBean.sendToKactus(autorizacionNovedad)) {
                return;
            }
            autorizacionNovedad.setEnviadoNomina(ConstantsUtil.ON_INT);
            autorizacionNovedadEjb.edit(autorizacionNovedad);
            MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("Error al enviar las novedades.");
        }
    }

    @Transactional
    public void enviarNominaTodos() {

        try {
            for (NovedadAutorizacionAusentismos item : lista) {

                String validacion = validarEnvioTodos(item);

                if (validacion != null) {
                    MovilidadUtil.addErrorMessage(validacion);
                    return;
                }

                if (!kactusNovedadBean.sendToKactus(item)) {
                    return;
                }
                item.setEnviadoNomina(ConstantsUtil.ON_INT);
                autorizacionNovedadEjb.edit(item);
            }
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
                autorizacionNovedad.setAprobacionGestor(1);
            }
            if (validarRolProfOp()) {
                autorizacionNovedad.setAprobacionProfOp(1);
            }
            if (validarRolDirOp()) {
                autorizacionNovedad.setAprobacionDirOp(1);
            }

            autorizacionNovedadEjb.edit(autorizacionNovedad);

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
                autorizacionNovedad.setAprobacionGestor(0);
            }
            if (validarRolProfOp()) {
                autorizacionNovedad.setAprobacionProfOp(0);
            }
            if (validarRolDirOp()) {
                autorizacionNovedad.setAprobacionDirOp(0);
            }

            autorizacionNovedadEjb.edit(autorizacionNovedad);

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

        lstNovedades = novedadEjb.findAusentismosAutorizacionNovedadByDateRange(fecha_inicio, fecha_fin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (lstNovedades == null || lstNovedades.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron Ausentismos registrados para el rango de fechas seleccionado.");
            return;
        }

        guardarDatos();
        consultar();
    }

    public void aprobarTodos() {

        boolean flagError = false;

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        for (NovedadAutorizacionAusentismos item : lista) {

            String validacion = validarAprobacionTodos(item);

            if (validacion == null) {
                if (validarRolGestor()) {
                    item.setAprobacionGestor(ConstantsUtil.ON_INT);
                }
                if (validarRolProfOp()) {
                    item.setAprobacionProfOp(ConstantsUtil.ON_INT);
                }
                if (validarRolDirOp()) {
                    item.setAprobacionDirOp(ConstantsUtil.ON_INT);
                }

                autorizacionNovedadEjb.edit(item);
            } else {
                MovilidadUtil.addErrorMessage(validacion);
                flagError = true;
            }
        }
        if (!flagError) {
            MovilidadUtil.addSuccessMessage("Autorizaciones aprobadas con éxito");
        }
    }

    @Transactional
    private void guardarDatos() {
        try {

            for (Novedad novedad : lstNovedades) {

                NovedadAutorizacionAusentismos r = new NovedadAutorizacionAusentismos();
                r.setIdNovedad(novedad);
                r.setIdGopUnidadFuncional(new GopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));
                r.setAprobacionGestor(0);
                r.setAprobacionProfOp(0);
                r.setAprobacionDirOp(0);
                r.setEnviadoNomina(0);
                r.setUsername(user.getUsername());
                r.setEstadoReg(0);
                r.setCreado(MovilidadUtil.fechaCompletaHoy());
                autorizacionNovedadEjb.create(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("Error al guardar datos");
        }
    }

    private String validarAprobacion() {

        if (validarRolProfOp()) {
            if (autorizacionNovedad.getAprobacionGestor() == 0) {
                return "NO se puede realizar aprobación YA que el Gestor NO ha realizado la aprobación";
            }
        }
        if (validarRolDirOp()) {
            if (autorizacionNovedad.getAprobacionProfOp() == 0) {
                return "NO se puede realizar aprobación YA que el Profesional NO ha realizado la aprobación";
            }
        }

        return null;
    }

    private String validarAprobacionTodos(NovedadAutorizacionAusentismos item) {

        if (validarRolProfOp()) {
            if (item.getAprobacionGestor() == 0) {
                return "NO se puede realizar aprobación YA que el Gestor NO ha realizado la aprobación";
            }
        }
        if (validarRolDirOp()) {
            if (item.getAprobacionProfOp() == 0) {
                return "NO se puede realizar aprobación YA que el Profesional NO ha realizado la aprobación";
            }
        }

        return null;
    }

    private String validarEnvioTodos(NovedadAutorizacionAusentismos item) {

        if (item.getAprobacionGestor() == 0) {
            return "NO se pudo realizar el envío de novedades, se han encontrado novedades SIN la aprobación del Gestor";
        }
        if (item.getAprobacionProfOp() == 0) {
            return "NO se pudo realizar el envío de novedades, se han encontrado novedades SIN la aprobación del Profesional de operaciones";
        }
        if (item.getAprobacionDirOp() == 0) {
            return "NO se pudo realizar el envío de novedades, se han encontrado novedades SIN la aprobación del Director de operaciones";
        }

        return null;
    }

    private String validarReprobacionRegistro() {

        if (validarRolGestor()) {
            if (autorizacionNovedad.getAprobacionProfOp() == 1) {
                return "NO se puede reprobar registro YA que existe aprobación por parte del Profesional de Operaciones";
            }
        }
        if (validarRolProfOp()) {
            if (autorizacionNovedad.getAprobacionDirOp() == 1) {
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

        int mesIni = obtenerMes(fecha_inicio);
        int mesFin = obtenerMes(fecha_fin);

        if (mesIni != mesFin) {
            MovilidadUtil.addErrorMessage("El rango de fechas debe corresponder al mismo mes");
            return;
        }

        lista = autorizacionNovedadEjb.findAllByRangoFechasAndUF(fecha_inicio, fecha_fin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

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

    public List<NovedadAutorizacionAusentismos> getLista() {
        return lista;
    }

    public void setLista(List<NovedadAutorizacionAusentismos> lista) {
        this.lista = lista;
    }

    public boolean isFlagNoExisteAutorizacion() {
        return flagNoExisteAutorizacion;
    }

    public void setFlagNoExisteAutorizacion(boolean flagNoExisteAutorizacion) {
        this.flagNoExisteAutorizacion = flagNoExisteAutorizacion;
    }

    public NovedadAutorizacionAusentismos getAutorizacionNovedad() {
        return autorizacionNovedad;
    }

    public void setAutorizacionNovedad(NovedadAutorizacionAusentismos autorizacionNovedad) {
        this.autorizacionNovedad = autorizacionNovedad;
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

}
