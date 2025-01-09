package com.movilidad.jsf;

import com.movilidad.ejb.ConfigFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.NovedadDocumentosFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.ParamCierreAusentismoFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadDocumentos;
import com.movilidad.model.NovedadTipoDocumentos;
import com.movilidad.model.ParamCierreAusentismo;
import com.movilidad.model.PrgSercon;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "novedadIncapacidadBean")
@ViewScoped
public class NovedadIncapacidadBean implements Serializable {

    @EJB
    private NovedadFacadeLocal novedadEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;

    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NovedadDocumentosFacadeLocal novedadDocumentosEjb;
    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    @Inject
    private ControlJornadaController jornadaController;

    @Inject
    private ValidarFinOperacionBean validarFinOperacionBean;
    @Inject
    private AjusteJornadaFromGestionServicio ajusteJornadaFromGestionServicio;
    @EJB
    private PrgSerconFacadeLocal prgSerconEJB;
    @EJB
    private ParamCierreAusentismoFacadeLocal paramCierreAusentismoEjb;

    @Inject
    private novedadTipoAndDetalleBean tipoAndDetalleBean;
    @Inject
    private PrgSerconMotivoJSF prgSerconMotivoBean;
    @Inject
    private AusentismosManagedBean ausentismosBean;

    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private Novedad novedad;
    private Novedad selected;
    private Empleado empleado;
    private boolean b_rol = validarRol();
    private boolean b_VerificacionPM = false;
    private boolean b_VerificacionSinFechas = false;
    private boolean b_laboro;
    private boolean b_laboroParcial;
    private boolean b_Soporte;
    private Integer i_puntosConciliados;
    private String c_vehiculo = "";
    
    private List<Novedad> lista;
    private List<Empleado> lstEmpleados;
    private List<UploadedFile> archivos;

    private boolean inmovilizado;
    private boolean isEditing;
    private boolean b_atv;
    private boolean b_DesasignarServicios = false;
    
    @EJB
    private ConfigFacadeLocal configEJB;

    @PostConstruct
    public void init() {
        tipoAndDetalleBean.setCompUpdateVistaCreateNov("frmNovedadesPm:novedad_tipo,"
                + "frmNovedadesPm:novedad_tipo_detalle,"
                + "frmNovedadesPm:novedad_tipo_detalleBtn,"
                + "frmNovedadesPm:desde_grp,"
                + "frmNovedadesPm:hasta_grp,"
                + "frmNovedadesPm:vehiculo,"
                + "frmNovedadesPm:operador,"
                + "frmNovedadesPm:hora_inicio_grp,"
                + "frmNovedadesPm:hora_fin_grp,"
                + "frmNovedadesPm:sitio_grp,"
                + "frmNovedadesPm:motivo_grp,"
                + "frmNovedadesPm:hora_grp");
    }

    /**
     *
     * Verifica si una novedad_detalle procede de forma automática al
     * agregar/modificar una novedad
     *
     * @return true si el valor es IGUAL a 1, de lo contrario false
     */
    public boolean procedeNovedad() {
        try {
            return configEJB.findByKey("nov").getValue() == ConstantsUtil.ON_INT;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Valida si el operador es tecnico de control.
     *
     */
    public void setTecnicoControl() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            validarFinOperacionBean.setIsTecControl(auth.getAuthority().equals("ROLE_TC"));
        }
    }

    /**
     *
     * Devuelve grupo master de un empleado
     *
     * @param empl Empleado a buscar grupo master
     * @return nombre de grupo master
     */
    public String master(Empleado empl) {
        if (empl == null) {
            return "N/A";
        }
        String master = "";
        try {
            master = empl.getPmGrupoDetalleList().get(0).getIdGrupo().getNombreGrupo();
        } catch (Exception e) {
            return "N/A";
        }

        return master;
    }

    /**
     * Prepara la lista de empleados antes de registrar/modificar una novedad
     */
    public void prepareListEmpleados() {
        this.empleado = new Empleado();
        lstEmpleados = empleadoEjb.findAllByUnidadFuncacional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    /**
     * Evento que se dispara al seleccionar un empleado en el
     * registro/modificación de una novedad
     *
     * @param event
     */
    public void onEmpleadoChosen(SelectEvent event) {
        if (event.getObject() instanceof Empleado) {
            setEmpleado((Empleado) event.getObject());
        }
    }

    /**
     * Evento que se dispara al seleccionar un empleado en el modal que muestra
     * listado de empleados
     *
     * @param event
     */
    public void onRowEmpleadoClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof Empleado) {
            setEmpleado((Empleado) event.getObject());
            if (validarUF()) {
                setEmpleado(null);
            }
        }
//        System.out.println("Empleado: " + empleado.getNombres() + " " + empleado.getApellidos());
        MovilidadUtil.clearFilter("wVPmEmpleadosListDialog");
        MovilidadUtil.updateComponent("frmPmEmpleadoList:dtEmpleados");
    }

    /**
     * Carga los datos antes de crear una novedad
     *
     * @param fecha
     */
    public void nuevo(Date fecha) {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una unidad funcional");
            return;
        }

        if (fecha != null && validarFinOperacionBean.validarDiaBloqueado(fecha,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional())) {
            return;
        }
        resetNovedad();
        novedad.setFecha(MovilidadUtil.fechaHoy());
        archivos = new ArrayList<>();
        tipoAndDetalleBean.setCompUpdateVistaCreateNov("frmNovedadesPm:novedad_tipo,"
                + "frmNovedadesPm:novedad_tipo_detalle,"
                + "frmNovedadesPm:novedad_tipo_detalleBtn,"
                + "frmNovedadesPm:desde_grp,"
                + "frmNovedadesPm:hasta_grp,"
                + "frmNovedadesPm:vehiculo,"
                + "frmNovedadesPm:operador,"
                + "frmNovedadesPm:hora_inicio_grp,"
                + "frmNovedadesPm:hora_fin_grp,"
                + "frmNovedadesPm:sitio_grp,"
                + "frmNovedadesPm:hora_grp,"
                + "frmNovedadesPm:novedad_tipo_detalle");
        c_vehiculo = "";
        MovilidadUtil.openModal("novedadesPM");
    }

    /**
     * Carga los datos antes de crear una novedad
     *
     */
    public void resetNovedad() {
        selected = null;
        novedad = new Novedad();
        tipoAndDetalleBean.setNovedadTipoDet(null);
        empleado = null;
        archivos = new ArrayList<>();
        c_vehiculo = "";
        inmovilizado = false;
        isEditing = false;
        b_atv = false;
        prgSerconMotivoBean.setI_prgSerconMotivo(null);
    }

    /**
     * Realiza la búsqueda de un vehículo por código
     */
    public void cargarVehiculo() {
        try {
            if (!(c_vehiculo.equals("") && c_vehiculo.isEmpty())) {
                Vehiculo vehiculo = vehiculoFacadeLocal.getVehiculo(c_vehiculo, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                if (vehiculo != null) {
                    novedad.setIdVehiculo(vehiculo);
                    if (validarUF()) {
                        novedad.setIdVehiculo(null);
                        return;
                    }
                    MovilidadUtil.addSuccessMessage("Vehículo valido");
                    PrimeFaces.current().ajax().update("frmNovedadesPm:messages");
                } else {
                    MovilidadUtil.addErrorMessage("Vehículo no valido");
                    PrimeFaces.current().ajax().update("frmNovedadesPm:messages");
                    novedad.setIdVehiculo(null);
                }
            } else {
                MovilidadUtil.addErrorMessage("Vehículo no valido");
                PrimeFaces.current().ajax().update("frmNovedadesPm:messages");
                novedad.setIdVehiculo(null);
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error de sistema");
            PrimeFaces.current().ajax().update("frmNovedadesPm:messages");
        }
    }

    boolean validarUF() {
        if (novedad.getIdVehiculo() != null && empleado != null) {
            if (novedad.getIdVehiculo().getIdGopUnidadFuncional() != null && empleado.getIdGopUnidadFuncional() != null) {
                if (!novedad.getIdVehiculo().getIdGopUnidadFuncional().getIdGopUnidadFuncional()
                        .equals(empleado.getIdGopUnidadFuncional().getIdGopUnidadFuncional())) {
                    MovilidadUtil.addErrorMessage("Vehículo y Operador no comparten la misma unidad funcional.");
                    MovilidadUtil.updateComponent("msgs");
                    MovilidadUtil.updateComponent("frmNovedadesPm:messages");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Carga datos de una novedad en la vista de edición
     */
    public void editar() {
        isEditing = true;
        this.novedad = this.selected;
        b_Soporte = (novedad.getSoporteAusentismo() == 1);
        b_laboro = (novedad.getLaboro() == 1);
        b_laboroParcial = (novedad.getLaboroParcial() == 1);
        tipoAndDetalleBean.setNovedadTipo(this.novedad.getIdNovedadTipo());
        tipoAndDetalleBean.setNovedadTipoDet(this.novedad.getIdNovedadTipoDetalle());
        this.empleado = this.novedad.getIdEmpleado();

        inmovilizado = novedad.getInmovilizado() == 1;

        prgSerconMotivoBean.setI_prgSerconMotivo(null);

        if (novedad.getIdNovedadTipo().getNombreTipoNovedad().contains(Util.DANO)) {
            MovilidadUtil.addErrorMessage("Debe actualizar la novedad a través del módulo: Novedades de daño");
            PrimeFaces.current().ajax().update("frmNovedadesPm:messages");
        }

//        this.archivos.clear();
        c_vehiculo = "";
        if (novedad.getIdVehiculo() != null) {
            c_vehiculo = novedad.getIdVehiculo().getCodigo();
        }
        MovilidadUtil.openModal("novedadesPM");
    }

    /**
     * Desactiva la verificación de novedades que coincidan para la misma fecha
     * ó rango de fechas
     */
    public void confirmarNo() {
        if (tipoAndDetalleBean.getNovedadTipoDet().getFechas() == ConstantsUtil.ON_INT) {
            b_VerificacionPM = false;
        } else {
            b_VerificacionSinFechas = false;
        }
    }

    boolean validarDatosNovedad() {
        if (tipoAndDetalleBean.getNovedadTipo() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo de novedad");
            return true;
        }
        if (tipoAndDetalleBean.getNovedadTipoDet() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un detalle de tipo de novedad");
            return true;
        }
        if (tipoAndDetalleBean.getNovedadTipoDet().getReqHoras() == ConstantsUtil.ON_INT) {
            if (MovilidadUtil.toSecs(novedad.getHoraInicio()) > MovilidadUtil.toSecs(novedad.getHoraFin())) {
                MovilidadUtil.addErrorMessage("La hora fin NO debe ser mayor a la hora inicio");
                return true;
            }
        }
        if (tipoAndDetalleBean.getNovedadTipoDet().getReqVehiculo() == ConstantsUtil.ON_INT) {
            if (novedad.getIdVehiculo() == null) {
                MovilidadUtil.addErrorMessage("DEBE asignar un vehículo a la novedad");
                return true;
            }
        }
        if (tipoAndDetalleBean.getNovedadTipoDet().getAfectaDisponibilidad() == ConstantsUtil.ON_INT) {
            MovilidadUtil.addErrorMessage("No es posible registrar una novedad que afecte la disponibilidad de los vehículos, desde este módulo.");
            return true;
        }

        if (empleado == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un operador");
            return true;
        }
        if (tipoAndDetalleBean.getNovedadTipo().getIdNovedadTipo() == Util.ID_ACCIDENTE) {
            MovilidadUtil.addErrorMessage("No es posible registrar una novedad de accidente desde este módulo.");
            return true;
        }
        if (tipoAndDetalleBean.getNovedadTipoDet().getFechas() == ConstantsUtil.ON_INT) {
            if (novedad.getDesde() == null || novedad.getHasta() == null) {
                MovilidadUtil.addErrorMessage("Fecha Desde y Hasta son necesarias");
                return true;
            }
        }
        if (tipoAndDetalleBean.getNovedadTipo().getIdNovedadTipo().equals(
                Integer.parseInt(
                        SingletonConfigEmpresa.getMapConfiMapEmpresa()
                                .get(ConstantsUtil.KEY_ID_NOV_AUSENTISMO)))) {

            ParamCierreAusentismo cierreAusentismo = paramCierreAusentismoEjb.buscarPorRangoFechasYUnidadFuncional(
                    novedad.getFecha(), novedad.getFecha(),
                    empleado.getIdGopUnidadFuncional().getIdGopUnidadFuncional());

            if (cierreAusentismo != null) {

                if (cierreAusentismo.getBloqueado() == 1) {
                    MovilidadUtil.addErrorMessage("Se ha realizado el cierre de ausentismos para la fecha seleccionada.");
                    return true;
                }

            }

        }

        return false;
    }

    /**
     * Persiste en base de datos el registro de una nueva novedad, y lo agrega a
     * la lista de novedades ( Bitácora).Además si la novedad pertenece al tipo
     * ACCIDENTE se persiste en la tabla de accidentes
     *
     * @throws java.io.IOException
     */
    public void guardarNovedadPM() throws IOException {

        if (validarDatosNovedad()) {
            return;
        }

        novedad.setIdGopUnidadFuncional(empleado.getIdGopUnidadFuncional());
        novedad.setIdNovedadTipo(tipoAndDetalleBean.getNovedadTipo());
        novedad.setIdNovedadTipoDetalle(tipoAndDetalleBean.getNovedadTipoDet());
        novedad.setIdEmpleado(empleado);
        novedad.setUsername(user.getUsername());

        if (!b_VerificacionPM && tipoAndDetalleBean.getNovedadTipoDet().getFechas() == ConstantsUtil.ON_INT) {
            if (novedadEjb.validarNovedadConFechas(empleado.getIdEmpleado(), novedad.getDesde(), novedad.getHasta()) != null) {
                b_VerificacionPM = true;
                PrimeFaces.current().executeScript("PF('verificarNovedadPM').show()");
                return;
            }
        }
        if (!(b_VerificacionSinFechas) && tipoAndDetalleBean.getNovedadTipoDet().getFechas() == ConstantsUtil.OFF_INT
                && novedadEjb.verificarNovedadPMSinFechas(novedad.getFecha(), empleado.getIdEmpleado(),
                        tipoAndDetalleBean.getNovedadTipoDet().getIdNovedadTipoDetalle()) != null) {
            b_VerificacionSinFechas = true;
            PrimeFaces.current().executeScript("PF('verificarNovedadPM').show()");
            return;
        }

        if (tipoAndDetalleBean.getNovedadTipoDet().getAfectaProgramacion() == ConstantsUtil.ON_INT
                && tipoAndDetalleBean.getNovedadTipoDet().getFechas() == ConstantsUtil.ON_INT) {

            /**
             * Fecha desde de la novedad es mayor a hoy.
             */
            if (novedad.getDesde().after(MovilidadUtil.fechaHoy())) {
                novedadEjb.desasignarOperador(novedad.getDesde(), novedad.getHasta(),
                        novedad.getIdEmpleado().getIdEmpleado());
                if (SingletonConfigEmpresa.getMapConfiMapEmpresa()
                        .get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
                    /**
                     * Ajuste de jornada al desasignar operador de servicios.
                     */
                    prgSerconEJB.ajustarJornadaCero(novedad.getIdEmpleado().getIdEmpleado(),
                            ajusteJornadaFromGestionServicio.getPrgSerconMotivoJSF().getI_prgSerconMotivo(),
                            novedad.getDesde(), novedad.getHasta(), novedad.getObservaciones(), user.getUsername());
                }

                /**
                 * Fecha desde es menor a hoy o es hoy y fecha hasta es mayor a
                 * hoy.
                 */
            } else if ((novedad.getDesde().before(MovilidadUtil.fechaHoy())
                    || MovilidadUtil.dateSinHora(novedad.getDesde())
                            .equals(MovilidadUtil.dateSinHora(MovilidadUtil.fechaHoy())))
                    && novedad.getHasta().after(MovilidadUtil.fechaHoy())) {

                novedadEjb.desasignarOperador(MovilidadUtil.sumarDias(MovilidadUtil.fechaHoy(), b_DesasignarServicios ? 0 : 1),//si flagDesasignarServicios significa que se inhabilitan desde el mismo día de la novedad
                        novedad.getHasta(), novedad.getIdEmpleado().getIdEmpleado());

                if (SingletonConfigEmpresa.getMapConfiMapEmpresa()
                        .get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
                    /**
                     * Ajuste de jornada al desasignar operador de servicios.
                     */
                    prgSerconEJB.ajustarJornadaCero(novedad.getIdEmpleado().getIdEmpleado(),
                            ajusteJornadaFromGestionServicio.getPrgSerconMotivoJSF().getI_prgSerconMotivo(),
                            MovilidadUtil.sumarDias(MovilidadUtil.fechaHoy(), b_DesasignarServicios ? 0 : 1),//si flagDesasignarServicios significa que se inhabilitan desde el mismo día de la novedad
                            novedad.getHasta(), novedad.getObservaciones(), user.getUsername());
                }
            }

        }

        novedad.setPuntosPm(0);
        novedad.setPuntosPmConciliados(0);
        novedad.setLiquidada(0);
//        novedad.setPuntosPmConciliados(puntoView(novedad));
        novedad.setUsername(user.getUsername());
        novedad.setCreado(MovilidadUtil.fechaCompletaHoy());
        novedadEjb.create(novedad);

        if (!archivos.isEmpty()) {
            String path;
            NovedadDocumentos documento;
            for (UploadedFile f : archivos) {
                documento = new NovedadDocumentos();
                documento.setIdNovedad(novedad);
                documento.setIdNovedadTipoDocumento(new NovedadTipoDocumentos(ConstantsUtil.ID_NOVEDAD_TIPO_DOCUMENTO_INCAPACIDAD));
                documento.setCreado(MovilidadUtil.fechaCompletaHoy());
                documento.setEstadoReg(0);
                documento.setUsuario(user.getUsername());
                novedadDocumentosEjb.create(documento);

                path = Util.saveFile(f, documento.getIdNovedadDocumento(), "novedad_documento");
                documento.setPathDocumento(path);
                novedadDocumentosEjb.edit(documento);
            }
        }

        Date fechaDesde = (novedad.getDesde() == null || novedad.getHasta() == null) ? novedad.getFecha() : novedad.getDesde();
        Date fechaHasta = (novedad.getDesde() == null || novedad.getHasta() == null) ? novedad.getFecha() : novedad.getHasta();

        /**
         * Se buscan los ausentimos que tiene el operador para el rango de
         * fechas indicado (Excepto la novedad que está editando) y se coloca
         * procede en CERO ( es decir, la novedad NO procede) a las demás
         * novedades.
         */
        List<Novedad> ausentismos = novedadEjb.getAusentismosByRangoFechasAndIdEmpleado(
                fechaDesde, fechaHasta,
                novedad.getIdEmpleado().getIdEmpleado(),
                0
        );

        if (ausentismos != null) {
            // Se colocan como NO procede a los ausentismos encontrados
            ausentismos.forEach(ausentismo -> {
                ausentismo.setProcede(0);
                ausentismo.setModificado(MovilidadUtil.fechaCompletaHoy());

                novedadEjb.edit(ausentismo);
            });
        }

        /**
         * Si laboró de manera parcial ó NO laboró se desasigna el operador
         * desde la fecha DESDE y se coloca la nómina borrada para el mismo
         * rango de fechas.
         */
        if (b_laboroParcial) {
            novedadEjb.desasignarOperadorAusentismo(fechaDesde, fechaHasta,
                    novedad.getIdEmpleado().getIdEmpleado());

            prgSerconEJB.eliminarJornadaAusentismo(
                    novedad.getIdEmpleado().getIdEmpleado(),
                    fechaDesde, fechaHasta, user.getUsername(),
                    prgSerconMotivoBean.getI_prgSerconMotivo(),
                    novedad.getObservaciones()
            );

            novedad.setLaboro(0);
            novedad.setLaboroParcial(1);
            novedad.setNoLaboro(0);

        } else if (b_laboro) {

            /**
             * En caso de que halla laborado se realiza la desasignación y
             * borrado de la jornada desde el día siguiente a la fecha DESDE
             */
            if (!(fechaDesde.compareTo(fechaHasta) == 0)) {
                novedadEjb.desasignarOperadorAusentismo(MovilidadUtil.sumarDias(fechaDesde, 1),
                        fechaHasta, novedad.getIdEmpleado().getIdEmpleado());

                //TODO: Agregar idPrgSerconMotivo y observaciones
                prgSerconEJB.eliminarJornadaAusentismo(
                        novedad.getIdEmpleado().getIdEmpleado(),
                        MovilidadUtil.sumarDias(fechaDesde, 1),
                        fechaHasta, user.getUsername(),
                        prgSerconMotivoBean.getI_prgSerconMotivo(),
                        novedad.getObservaciones()
                );

                novedad.setLaboro(1);
                novedad.setLaboroParcial(0);
                novedad.setNoLaboro(0);

            } else {
                autorizarJornada(novedad);
            }

        } else {
            novedadEjb.desasignarOperadorAusentismo(fechaDesde, fechaHasta,
                    novedad.getIdEmpleado().getIdEmpleado());

            prgSerconEJB.eliminarJornadaAusentismo(
                    novedad.getIdEmpleado().getIdEmpleado(),
                    fechaDesde, fechaHasta, user.getUsername(),
                    prgSerconMotivoBean.getI_prgSerconMotivo(),
                    novedad.getObservaciones()
            );

            novedad.setLaboro(0);
            novedad.setLaboroParcial(0);
            novedad.setNoLaboro(1);
        }

        if (novedad.getIdNovedadTipoDetalle().getAfectaPm() == ConstantsUtil.ON_INT) {
            if (procedeNovedad()) {
                novedad.setProcede(1);
                novedad.setPuntosPm(tipoAndDetalleBean.getNovedadTipoDet().getPuntosPm());
                procedeCociliacion(novedad);
            }
        }
        notificar();
        b_VerificacionPM = false;
        b_VerificacionSinFechas = false;
        MovilidadUtil.addSuccessMessage("Novedad  registrada correctamente.");
        resetNovedad();
    }

    /**
     * Modifica registro novedad, y actualiza los datos en la tabla de
     * accidentes ( Sí la novedad a modificar tiene como tipo ACCIDENTE)
     *
     * @throws java.io.IOException
     */
    public void actualizarNovedadPM() throws IOException {
        if (tipoAndDetalleBean.getNovedadTipo() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo de novedad");
            return;
        }
        if (tipoAndDetalleBean.getNovedadTipoDet() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un detalle de tipo de novedad");
            return;
        }
        if (empleado == null || empleado.getNombres() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un operador");
            return;
        }

        if (tipoAndDetalleBean.getNovedadTipoDet().getFechas() == ConstantsUtil.ON_INT) {
            if (novedad.getDesde() == null || novedad.getHasta() == null) {
                MovilidadUtil.addErrorMessage("Fecha Desde y Hasta son necesarias");
                return;
            }
        }

        if (tipoAndDetalleBean.getNovedadTipoDet().getReqHoras() == ConstantsUtil.ON_INT) {
            if (MovilidadUtil.toSecs(novedad.getHoraInicio()) > MovilidadUtil.toSecs(novedad.getHoraFin())) {
                MovilidadUtil.addErrorMessage("La hora fin NO debe ser mayor a la hora inicio");
                return;
            }
        }

        if (tipoAndDetalleBean.getNovedadTipoDet().getReqVehiculo() == ConstantsUtil.ON_INT) {
            if (novedad.getIdVehiculo() == null) {
                MovilidadUtil.addErrorMessage("DEBE asignar un vehículo a la novedad");
                return;
            }
        }

        if (tipoAndDetalleBean.getNovedadTipo().getNombreTipoNovedad().equals(Util.DANO)) {
            MovilidadUtil.addErrorMessage("Debe actualizar la novedad a través del módulo: Novedades de daño");
            MovilidadUtil.updateComponent("frmNovedadesPm:messages");
            return;
        }

        if (tipoAndDetalleBean.getNovedadTipo().getIdNovedadTipo().equals(
                Integer.parseInt(
                        SingletonConfigEmpresa.getMapConfiMapEmpresa()
                                .get(ConstantsUtil.KEY_ID_NOV_AUSENTISMO)))) {

            ParamCierreAusentismo cierreAusentismo = paramCierreAusentismoEjb.buscarPorRangoFechasYUnidadFuncional(
                    novedad.getFecha(), novedad.getFecha(),
                    empleado.getIdGopUnidadFuncional().getIdGopUnidadFuncional());

            if (cierreAusentismo != null) {

                if (cierreAusentismo.getBloqueado() == 1) {
                    MovilidadUtil.addErrorMessage("Se ha realizado el cierre de ausentismos para la fecha seleccionada.");
                    return;
                }

            }

        }

        novedad.setIdNovedadTipo(tipoAndDetalleBean.getNovedadTipo());
        novedad.setIdNovedadTipoDetalle(tipoAndDetalleBean.getNovedadTipoDet());
        novedad.setIdEmpleado(empleado);
        novedad.setProcede(0);
        novedad.setPuntosPm(0);
        novedad.setPuntosPmConciliados(0);
        novedad.setSoporteAusentismo(b_Soporte ? 1 : 0);

        novedad.setLiquidada(0);
        novedad.setUsername(user.getUsername());
        novedad.setModificado(MovilidadUtil.fechaCompletaHoy());
        if (novedad.getIdNovedadTipoDetalle().getAfectaDisponibilidad() == ConstantsUtil.ON_INT) {
            novedad.setInmovilizado(inmovilizado ? 1 : 0);
        }

        Date fechaDesde = (novedad.getDesde() == null || novedad.getHasta() == null) ? novedad.getFecha() : novedad.getDesde();
        Date fechaHasta = (novedad.getDesde() == null || novedad.getHasta() == null) ? novedad.getFecha() : novedad.getHasta();

        /**
         * Se buscan los ausentimos que tiene el operador para el rango de
         * fechas indicado (Excepto la novedad que está editando) y se coloca
         * procede en CERO ( es decir, la novedad NO procede) a las demás
         * novedades.
         */
        List<Novedad> ausentismos = novedadEjb.getAusentismosByRangoFechasAndIdEmpleado(
                fechaDesde, fechaHasta,
                novedad.getIdEmpleado().getIdEmpleado(),
                novedad.getIdNovedad()
        );

        if (ausentismos != null) {
            // Se colocan como NO procede a los ausentismos encontrados
            ausentismos.forEach(ausentismo -> {
                ausentismo.setProcede(0);
                ausentismo.setModificado(MovilidadUtil.fechaCompletaHoy());

                novedadEjb.edit(ausentismo);
            });
        }

        /**
         * Si laboró de manera parcial ó NO laboró se desasigna el operador
         * desde la fecha DESDE y se coloca la nómina borrada para el mismo
         * rango de fechas.
         */
        if (b_laboroParcial) {
            novedadEjb.desasignarOperadorAusentismo(fechaDesde, fechaHasta,
                    novedad.getIdEmpleado().getIdEmpleado());

            prgSerconEJB.eliminarJornadaAusentismo(
                    novedad.getIdEmpleado().getIdEmpleado(),
                    fechaDesde, fechaHasta, user.getUsername(),
                    prgSerconMotivoBean.getI_prgSerconMotivo(),
                    novedad.getObservaciones()
            );

            novedad.setLaboro(0);
            novedad.setLaboroParcial(1);
            novedad.setNoLaboro(0);

        } else if (b_laboro) {

            /**
             * En caso de que halla laborado se realiza la desasignación y
             * borrado de la jornada desde el día siguiente a la fecha DESDE
             */
            if (!(fechaDesde.compareTo(fechaHasta) == 0)) {
                novedadEjb.desasignarOperadorAusentismo(MovilidadUtil.sumarDias(fechaDesde, 1),
                        fechaHasta, novedad.getIdEmpleado().getIdEmpleado());

                //TODO: Agregar idPrgSerconMotivo y observaciones
                prgSerconEJB.eliminarJornadaAusentismo(
                        novedad.getIdEmpleado().getIdEmpleado(),
                        MovilidadUtil.sumarDias(fechaDesde, 1),
                        fechaHasta, user.getUsername(),
                        prgSerconMotivoBean.getI_prgSerconMotivo(),
                        novedad.getObservaciones()
                );

                novedad.setLaboro(1);
                novedad.setLaboroParcial(0);
                novedad.setNoLaboro(0);

            } else {
                autorizarJornada(novedad);
            }

        } else {
            novedadEjb.desasignarOperadorAusentismo(fechaDesde, fechaHasta,
                    novedad.getIdEmpleado().getIdEmpleado());

            prgSerconEJB.eliminarJornadaAusentismo(
                    novedad.getIdEmpleado().getIdEmpleado(),
                    fechaDesde, fechaHasta, user.getUsername(),
                    prgSerconMotivoBean.getI_prgSerconMotivo(),
                    novedad.getObservaciones()
            );

            novedad.setLaboro(0);
            novedad.setLaboroParcial(0);
            novedad.setNoLaboro(1);
        }

        if (novedad.getIdNovedadTipoDetalle().getAfectaPm() == ConstantsUtil.ON_INT) {
            if (procedeNovedad()) {
                novedad.setProcede(1);
                novedad.setPuntosPm(tipoAndDetalleBean.getNovedadTipoDet().getPuntosPm());
                procedeCociliacion(novedad);
            }
        }

        novedadEjb.edit(novedad);
        selected = null;
        ausentismosBean.setSelected(null);
        MovilidadUtil.runScript("actualizarIncapacidades()");
        MovilidadUtil.clearFilter("dtNovedades");
        MovilidadUtil.hideModal("novedadesPM");
        MovilidadUtil.addSuccessMessage("Novedad actualizada correctamente.");
    }

    /**
     *
     * Evento que dispara la subida de archivos para la anexarlos de documentos
     * de una novedad
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        archivos.add(event.getFile());

        MovilidadUtil.addSuccessMessage("Archivo(s) agregados éxitosamente.");
        MovilidadUtil.updateComponent(":msgs");
    }

    /*
     * Parámetros para el envío de correos (Novedades PM)
     */
    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.find(Util.ID_NOVEDAD_PM_TEMPLATE);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    /*
     * Parámetros para el envío de correos de fallas a Mantenimiento
     */
    private Map getMailParamsMTTO() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(Util.TEMPLATE_NOVEDADES_MTTO);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    /**
     * Realiza el envío de correo de las novedad registrada a las partes
     * interesadas
     */
    private void notificar() {
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("fecha", Util.dateFormat(novedad.getFecha()));
        mailProperties.put("tipo", novedad.getIdNovedadTipo().getNombreTipoNovedad());
        mailProperties.put("detalle", novedad.getIdNovedadTipoDetalle().getTituloTipoNovedad());
        mailProperties.put("fechas", novedad.getDesde() != null && novedad.getHasta() != null ? Util.dateFormat(novedad.getDesde()) + " hasta " + Util.dateFormat(novedad.getHasta()) : "");
//        mailProperties.put("operador", empleado != null ? empleado.getCodigoTm() + " - " + empleado.getNombres() + " " + empleado.getApellidos() : "");
        mailProperties.put("operador", novedad.getIdEmpleado() != null ? novedad.getIdEmpleado().getNombres() + " " + novedad.getIdEmpleado().getApellidos() : "");
        mailProperties.put("vehiculo", novedad.getIdVehiculo() != null ? novedad.getIdVehiculo().getCodigo() : "");
        mailProperties.put("username", "");
        mailProperties.put("generada", Util.dateTimeFormat(novedad.getCreado()));
        mailProperties.put("observaciones", novedad.getObservaciones());
        String subject = "Novedad " + novedad.getIdNovedadTipo().getNombreTipoNovedad();
        String destinatarios = "";

        //Busqueda Operador Máster
        if (novedad.getIdEmpleado() != null) {
            String correoMaster = "";
            if (novedad.getIdEmpleado().getPmGrupoDetalleList().size() == 1) {
                correoMaster = novedad.getIdEmpleado().getPmGrupoDetalleList().get(0).getIdGrupo().getIdEmpleado().getEmailCorporativo();
            }
            destinatarios = novedad.getIdEmpleado() != null ? correoMaster + "," + novedad.getIdEmpleado().getEmailCorporativo() : "";
        }
        if (novedad.getIdNovedadTipoDetalle().getNotificacion() == 1) {
            if (novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos() != null) {
                if (destinatarios != null) {
                    destinatarios = destinatarios + "," + novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getEmails();
                } else {
                    destinatarios = novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getEmails();
                }

                if (novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getNotificacionProcesoDetList() != null) {
                    String destinatariosByUf = "";

                    destinatariosByUf = MovilidadUtil.obtenerCorreosByUf(novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getNotificacionProcesoDetList(), novedad.getIdGopUnidadFuncional().getIdGopUnidadFuncional());

                    if (destinatariosByUf != null) {
                        destinatarios = destinatarios + "," + destinatariosByUf;
                    }
                }

            }
            SendMails.sendEmail(mapa, mailProperties, subject,
                    "",
                    destinatarios,
                    "Notificaciones RIGEL", null);
        }
    }

    /**
     * Verifica si el usuario logueado es un Profesional de operaciones
     *
     * @return true si el usuario logueado es un Profesional de operaciones
     */
    boolean validarRol() {
        for (GrantedAuthority auth : user.getAuthorities()) {
//            System.out.println(auth.getAuthority());
            if (auth.getAuthority().equals("ROLE_PROFOP")) {
                return true;
            }

        }
        return false;
    }

    /**
     * Valida si el usuario logueado corresponde al área de Seguridad Vial
     *
     * @return true si el usuario tiene rol ROLE_SEG
     */
    public boolean validarRolSeg() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_SEG")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Realiza la conciliación de una novedad y su asignación de puntos en el
     * programa master
     *
     * @param nov
     */
    public void procedeCociliacion(Novedad nov) {
        nov.setPuntosPmConciliados(puntoView(nov));
        nov.setProcede(1);
        if (validarRol()) {
            MovilidadUtil.addSuccessMessage("Los puntos se han agregado a la Concilicación correctamente");
            MovilidadUtil.updateComponent("frmPrincipal:dtTipo");
        }
        MovilidadUtil.updateComponent("msgs");
        novedadEjb.edit(nov);
    }

    /**
     * Retorna la cantidad de puntos PM de una novedad
     *
     * @param n
     * @return puntos Programa máster
     */
    public int puntoView(Novedad n) {
        if (n == null) {
            return 0;
        }
        if (n.getIdNovedadDano() != null) {
            return n.getIdNovedadDano().getIdVehiculoDanoSeveridad().getPuntosPm();
        }
        if (n.getIdMulta() != null) {
            return n.getPuntosPm();
        }
        if (n.getIdNovedadDano() == null && n.getIdMulta() == null) {
            return n.getIdNovedadTipoDetalle().getPuntosPm();
        }

        return 0;
    }

    /**
     * Deshace la conciliación de una novedad y su asignación de puntos en el
     * programa master
     *
     * @param nov
     */
    public void noProcedeConciliacion(Novedad nov) {
        nov.setPuntosPmConciliados(0);
        nov.setProcede(0);
        MovilidadUtil.addSuccessMessage("Se ha realizado la acción correctamente");
        PrimeFaces.current().ajax().update("msgs");
        PrimeFaces.current().ajax().update("frmPrincipal:dtTipo");
        novedadEjb.edit(nov);
    }

    public void validarOperacionCerrada() {
        if (novedad.getFecha() != null && validarFinOperacionBean.validarDiaBloqueado(novedad.getFecha(), unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional())) {
            novedad.setFecha(MovilidadUtil.fechaHoy());
        }
    }

    public boolean validarEditarSeguimiento(String userName) {
        if (user.getUsername().equals(userName)) {
            return false;
        }
        for (GrantedAuthority g : user.getAuthorities()) {
            if (g.getAuthority().equals("ROLE_PROFOP")) {
                return false;
            }
        }
        return true;
    }

    public void autorizarJornada(Novedad novedad) {
        PrgSercon prgSercon;
        if (novedad.getDesde() == null && novedad.getHasta() == null) {
            prgSercon = prgSerconEJB.getByIdEmpleadoAndFecha(novedad.getIdEmpleado().getIdEmpleado(), novedad.getFecha());
        } else {
            prgSercon = prgSerconEJB.getByIdEmpleadoAndFecha(novedad.getIdEmpleado().getIdEmpleado(), novedad.getDesde());
        }
        if (prgSercon != null) {
            prgSercon.setAutorizado(1);
            prgSercon.setUserAutorizado(novedad.getUsername());
            prgSercon.setObservaciones(novedad.getObservaciones());
            jornadaController.autorizar(prgSercon, 1);
        }
    }

    public NovedadFacadeLocal getNovedadEjb() {
        return novedadEjb;
    }

    public void setNovedadEjb(NovedadFacadeLocal novedadEjb) {
        this.novedadEjb = novedadEjb;
    }

    public List<Novedad> getLista() {
        return lista;
    }

    public void setLista(List<Novedad> lista) {
        this.lista = lista;
    }

    public Novedad getNovedad() {
        return novedad;
    }

    public void setNovedad(Novedad novedad) {
        this.novedad = novedad;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<Empleado> getLstEmpleados() {
        return lstEmpleados;
    }

    public void setLstEmpleados(List<Empleado> lstEmpleados) {
        this.lstEmpleados = lstEmpleados;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public boolean isB_rol() {
        return b_rol;
    }

    public void setB_rol(boolean b_rol) {
        this.b_rol = b_rol;
    }

    public Integer getI_puntosConciliados() {
        return i_puntosConciliados;
    }

    public void setI_puntosConciliados(Integer i_puntosConciliados) {
        this.i_puntosConciliados = i_puntosConciliados;
    }

    public String getC_vehiculo() {
        return c_vehiculo;
    }

    public void setC_vehiculo(String c_vehiculo) {
        this.c_vehiculo = c_vehiculo;
    }

    public boolean isB_VerificacionSinFechas() {
        return b_VerificacionSinFechas;
    }

    public void setB_VerificacionSinFechas(boolean b_VerificacionSinFechas) {
        this.b_VerificacionSinFechas = b_VerificacionSinFechas;
    }

    public boolean isB_VerificacionPM() {
        return b_VerificacionPM;
    }

    public void setB_VerificacionPM(boolean b_VerificacionPM) {
        this.b_VerificacionPM = b_VerificacionPM;
    }

    public boolean isInmovilizado() {
        return inmovilizado;
    }

    public void setInmovilizado(boolean inmovilizado) {
        this.inmovilizado = inmovilizado;
    }

    public boolean isB_atv() {
        return b_atv;
    }

    public void setB_atv(boolean b_atv) {
        this.b_atv = b_atv;
    }

    public Novedad getSelected() {
        return selected;
    }

    public void setSelected(Novedad selected) {
        this.selected = selected;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public boolean isB_laboro() {
        return b_laboro;
    }

    public void setB_laboro(boolean b_laboro) {
        this.b_laboro = b_laboro;
    }

    public boolean isB_laboroParcial() {
        return b_laboroParcial;
    }

    public void setB_laboroParcial(boolean b_laboroParcial) {
        this.b_laboroParcial = b_laboroParcial;
    }

    public boolean isB_Soporte() {
        return b_Soporte;
    }

    public void setB_Soporte(boolean b_Soporte) {
        this.b_Soporte = b_Soporte;
    }

    public boolean isB_DesasignarServicios() {
        return b_DesasignarServicios;
    }

    public void setB_DesasignarServicios(boolean b_DesasignarServicios) {
        this.b_DesasignarServicios = b_DesasignarServicios;
    }
    
}
