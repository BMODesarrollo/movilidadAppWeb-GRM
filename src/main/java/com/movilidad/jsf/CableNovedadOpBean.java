package com.movilidad.jsf;

import com.movilidad.ejb.CableCabinaFacadeLocal;
import com.movilidad.ejb.CableEventoTipoDetFacadeLocal;
import com.movilidad.ejb.CableNovedadOpFacadeLocal;
import com.movilidad.ejb.CableEventoTipoFacadeLocal;
import com.movilidad.ejb.CableTramoFacadeLocal;
import com.movilidad.ejb.CableEstacionFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.model.CableCabina;
import com.movilidad.model.CableEventoTipo;
import com.movilidad.model.CableEventoTipoDet;
import com.movilidad.model.CableNovedadOp;
import com.movilidad.model.CableTramo;
import com.movilidad.model.CableEstacion;
import com.movilidad.model.Empleado;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.CableNovedadOpUtil;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "cableNovedadOpBean")
@ViewScoped
public class CableNovedadOpBean implements Serializable {

    @EJB
    private CableNovedadOpFacadeLocal cableNovedadOpEjb;
    @EJB
    private CableEventoTipoDetFacadeLocal cableEventoTipoDetEjb;
    @EJB
    private CableEventoTipoFacadeLocal cableEventoTipoEjb;
    @EJB
    private CableEstacionFacadeLocal cableEstacionEjb;
    @EJB
    private CableCabinaFacadeLocal cabinaEjb;
    @EJB
    private CableTramoFacadeLocal cableTramoEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;

    private CableNovedadOp cableNovedadOp;
    private CableNovedadOp selected;
    private CableEventoTipo cableEventoTipo;
    private CableEventoTipoDet cableEventoTipoDet;
    private CableEstacion cableEstacion;
    private CableCabina cableCabina;
    private CableCabina cableCabinaDos;
    private Empleado empleado;

    private Date desde;
    private Date hasta;
    private Integer i_cableTramo;

    private boolean isEditing;

    private int cabinaop;

    private List<CableNovedadOp> lstCableNovedadOps;
    private List<CableEventoTipo> lstCableEventoTipo;
    private List<CableEventoTipoDet> lstCableEventoTipoDet;
    private List<CableEstacion> lstCableEstaciones;
    private List<CableCabina> lstCableCabina;
    private List<CableTramo> lstCableTramos;
    private List<Empleado> lstEmpleados;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        desde = MovilidadUtil.fechaHoy();
        hasta = MovilidadUtil.fechaHoy();
        consultar();
    }

    /**
     * Prepara la lista de tipos de eventos antes de registrar/modificar un
     * registro.
     */
    public void prepareListEventoTipo() {
        lstCableEventoTipo = null;

        if (lstCableEventoTipo == null) {
            lstCableEventoTipo = cableEventoTipoEjb.findAllByEstadoReg();
            PrimeFaces.current().ajax().update(":frmCableEventoTipo:dtCableEventoTipo");
            PrimeFaces.current().executeScript("PF('wlVdtCableEventoTipo').clearFilters();");
        }
    }

    /**
     * Evento que se dispara al seleccionar el tipo de evento en el modal que
     * muestra listado de tipos
     *
     * @param event
     */
    public void onRowCableEventoTipoClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof CableEventoTipo) {
            setCableEventoTipo((CableEventoTipo) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wlVdtCableEventoTipo').clearFilters();");
    }

    /**
     * Carga una lista con los detalles de tipos antes de registrar/modificar un
     * registro.
     */
    public void prepareListCableEventoTipoDet() {

        if (cableEventoTipo.getIdCableEventoTipo() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo de evento.");
            return;
        }

        lstCableEventoTipoDet = cableEventoTipoDetEjb.findAllByTipoEvento(cableEventoTipo.getIdCableEventoTipo());

        PrimeFaces.current().ajax().update(":frmCableEventoTipoDetalle:dtCableEventoTipoDetalle");
        PrimeFaces.current().executeScript("PF('wlVdtCableEventoTipoDetalle').clearFilters();");
    }

    /**
     * Evento que se dispara al seleccionar un detalle de tipo. en el modal que
     * se muestra el listado.
     *
     * @param event
     */
    public void onRowCableEventoTipoDetClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof CableEventoTipoDet) {
            setCableEventoTipoDet((CableEventoTipoDet) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wlVdtCableEventoTipoDetalle').clearFilters();");
    }

    /**
     * Prepara la lista de tipos de eventos antes de registrar/modificar un
     * registro.
     */
    public void prepareListCableEstacion() {
        lstCableEstaciones = cableEstacionEjb.findByEstadoReg();
        PrimeFaces.current().ajax().update(":frmCableEstacion:dtCableEstacion");
        PrimeFaces.current().executeScript("PF('wlVdtCableEstacion').clearFilters();");
    }

    /**
     * Evento que se dispara al seleccionar el tipo de evento en el modal que
     * muestra listado de tipos
     *
     * @param event
     */
    public void onRowCableEstacionClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof CableEstacion) {
            setCableEstacion((CableEstacion) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wlVdtCableEstacion').clearFilters();");
    }

    /**
     * Prepara la lista de cabinas antes de registrar/modificar un registro.
     */
    public void prepareListCableCabina(int op) {
        cabinaop = op;
        lstCableCabina = cabinaEjb.findAllByEstadoReg();
        if (op == 1) {
            PrimeFaces.current().ajax().update(":frmCableCabina:dtCableCabina");
            PrimeFaces.current().executeScript("PF('wlVdtCableCabina').clearFilters();");
        } else if (op == 2) {
            PrimeFaces.current().ajax().update(":frmCableCabinaDos:dtCableCabinaDos");
            PrimeFaces.current().executeScript("PF('wlVdtCableCabinaDos').clearFilters();");
        }
    }

    /**
     * Evento que se dispara al seleccionar el tipo de evento en el modal que
     * muestra listado de cabinas
     *
     * @param event
     */
    public void onRowCableCabinaClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof CableCabina) {

            if (cabinaop == 1) {
                setCableCabina((CableCabina) event.getObject());
            } else {
                setCableCabinaDos((CableCabina) event.getObject());
            }

        }
        PrimeFaces.current().executeScript("PF('wlVdtCableCabina').clearFilters();");
        cabinaop = 0;
    }

    /**
     * Prepara la lista de empleados antes de registrar/modificar un registro.
     */
    public void prepareListEmpleado() {
        lstEmpleados = empleadoEjb.findAllEmpleadosByCargos(MovilidadUtil.getProperty("idCargosEmpleadoNov"));
        PrimeFaces.current().ajax().update(":frmPmEmpleadoList:dtEmpleados");
        PrimeFaces.current().executeScript("PF('wVEmpleadosListDialog').clearFilters();");
    }

    /**
     * Evento que se dispara al seleccionar el tipo de evento en el modal que
     * muestra listado de tipos
     *
     * @param event
     */
    public void onRowEmpleadoClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof Empleado) {
            setEmpleado((Empleado) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wVEmpleadosListDialog').clearFilters();");
    }

    public void nuevo() {
        cableNovedadOp = new CableNovedadOp();
        cableNovedadOp.setFecha(MovilidadUtil.fechaCompletaHoy());
        cableEventoTipo = new CableEventoTipo();
        cableEventoTipoDet = new CableEventoTipoDet();
        cableEstacion = new CableEstacion();
        cableCabina = new CableCabina();
        cableCabinaDos = new CableCabina();
        empleado = new Empleado();
        selected = null;
        i_cableTramo = null;
        isEditing = false;

        lstCableTramos = cableTramoEjb.findAllByEstadoReg();

        if (cableNovedadOpEjb.findByFecha(cableNovedadOp.getFecha()) == null) {
            cableEventoTipoDet = cableEventoTipoDetEjb.findByClaseEvento(Util.CLASE_EVENTO_INICIO);
            cableEventoTipo = cableEventoTipoDet.getIdCableEventoTipo();
        }
    }

    public void editar() {
        isEditing = true;
        cableEventoTipo = selected.getIdCableEventoTipo();
        cableEventoTipoDet = selected.getIdCableEventoTipoDet();

        if (cableEventoTipoDet.getReqUbicacion() == 1) {
            cableEstacion = selected.getIdCableEstacion();
        }

        if (cableEventoTipoDet.getReqCabina() == 1) {
            cableCabina = selected.getIdCableCabina();
            cableCabinaDos = selected.getIdCableCabinaDos();
        }

        if (cableEventoTipoDet.getReqOperador() == 1) {
            empleado = selected.getIdEmpleado();
        }

        if (cableEventoTipoDet.getReqTramo() == 1) {
            i_cableTramo = selected.getIdCableTramo().getIdCableTramo();
        }

        cableNovedadOp = selected;
        lstCableTramos = cableTramoEjb.findAllByEstadoReg();
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {

            cableNovedadOp.setIdCableEventoTipo(cableEventoTipo);
            cableNovedadOp.setIdCableEventoTipoDet(cableEventoTipoDet);

            prepareObjetos();

            if (isEditing) {

                cableNovedadOp.setModificado(new Date());
                cableNovedadOp.setUsername(user.getUsername());
                cableNovedadOpEjb.edit(cableNovedadOp);

                if (!calcularHoras()) {
                    MovilidadUtil.addErrorMessage("Error al calcular horas");
                    return;
                }
                PrimeFaces.current().executeScript("PF('wlvCableNovedadOp').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {

                cableNovedadOp.setEstadoReg(0);
                cableNovedadOp.setCreado(new Date());
                cableNovedadOp.setUsername(user.getUsername());
                cableNovedadOpEjb.create(cableNovedadOp);

                if (!calcularHoras()) {
                    MovilidadUtil.addErrorMessage("Error al calcular horas");
                    return;
                }

                if (cableEventoTipoDet.getNotifica() == 1) {
                    notificar();
                }

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }

            consultar();

        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public String validarMaxDate() {
        Date hoy = MovilidadUtil.fechaCompletaHoy();
        return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(hoy);
    }

    public String validarMinDate() {
        Date hoy = Util.toDate(Util.dateFormat(MovilidadUtil.fechaCompletaHoy()));
        return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(hoy);
    }

    /**
     * Realiza la consulta de novedades para las fechas seleccionadas
     */
    public void consultar() {
        lstCableNovedadOps = cableNovedadOpEjb.findByDateRange(desde, hasta);
    }

    /**
     * Realiza la preparación de objetos antes de guardar / modificar
     */
    private void prepareObjetos() {

        if (cableEventoTipoDet.getReqUbicacion() == 1) {
            cableNovedadOp.setIdCableEstacion(cableEstacion.getIdCableEstacion() != null ? cableEstacion : null);
        }

        if (cableEventoTipoDet.getReqCabina() == 1) {
            cableNovedadOp.setIdCableCabina(cableCabina.getIdCableCabina() != null ? cableCabina : null);
            if (!isEditing) {
                cableNovedadOp.setIdCableCabinaDos(cableCabinaDos.getIdCableCabina() != null ? cableCabinaDos : null);
            } else {
                cableNovedadOp.setIdCableCabinaDos(cableCabinaDos != null ? cableCabinaDos : null);
            }
        }

        if (cableEventoTipoDet.getReqTramo() == 1) {
            CableTramo tramo = cableTramoEjb.find(i_cableTramo);
            cableNovedadOp.setIdCableTramo(tramo != null ? tramo : null);
        }

        if (cableEventoTipoDet.getReqOperador() == 1) {
            cableNovedadOp.setIdEmpleado(empleado.getIdEmpleado() != null ? empleado : null);
        }
    }

    private boolean calcularHoras() {
        if (cableEventoTipoDet.getClaseEvento() != 1
                && cableEventoTipoDet.getClaseEvento() != 2) {
            if (MovilidadUtil.toSecs(cableNovedadOp.getTimeIniParada()) > 0 && MovilidadUtil.toSecs(cableNovedadOp.getTimeFinParada()) > 0) {
                cableNovedadOp.setTimeTotalParada(MovilidadUtil.diferenciaHoras(cableNovedadOp.getTimeIniParada(), cableNovedadOp.getTimeFinParada()));
            } else {
                cableNovedadOp.setTimeTotalParada(null);
            }

            if (isEditing) {
                cableNovedadOp.setHorometroTotal(null);
                cableNovedadOp.setTimeOperacionCom(null);
                cableNovedadOp.setTimeOperacionSistema(null);
            }

            cableNovedadOpEjb.edit(cableNovedadOp);
            return true;
        }

        if (cableEventoTipoDet.getClaseEvento() == 1) {
            return true;
        }
        if (cableEventoTipoDet.getClaseEvento() == 2) {
            List<CableNovedadOp> lstNovedades = cableNovedadOpEjb.getListByFecha(cableNovedadOp.getFecha());

            if (lstNovedades.size() == 2) {
                cableNovedadOp.setTimeTotalParada("00:00:00");
                cableNovedadOp.setTimeOperacionSistema(CableNovedadOpUtil.calcularTiempoOperacionSistema(lstNovedades));
                cableNovedadOp.setTimeOperacionCom(CableNovedadOpUtil.calcularTiempoOperacionComercial(lstNovedades));
            } else if (lstNovedades.size() > 2) {
                cableNovedadOp.setTimeTotalParada(CableNovedadOpUtil.calcularTiempoTotalParada(lstNovedades));
                cableNovedadOp.setTimeOperacionSistema(CableNovedadOpUtil.calcularTiempoOperacionSistema(lstNovedades));
                cableNovedadOp.setTimeOperacionCom(CableNovedadOpUtil.calcularTiempoOperacionComercial(lstNovedades));
            } else {
                return false;
            }

            cableNovedadOpEjb.edit(cableNovedadOp);
            return true;
        }

        return false;
    }

    /*
     * Parámetros para el envío de correos
     */
    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(Util.TEMPLATE_NOVEDADES_CABLE);
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
        mailProperties.put("fecha", Util.dateFormat(cableNovedadOp.getFecha()));
        mailProperties.put("tipo", cableNovedadOp.getIdCableEventoTipo().getNombre());
        mailProperties.put("detalle", cableNovedadOp.getIdCableEventoTipoDet().getNombre());
        mailProperties.put("ubicacion", cableEstacion != null ? cableEstacion.getNombre() : "N/A");

        if (cableCabinaDos.getIdCableCabina() != null) {
            mailProperties.put("cabina", cableCabina != null ? cableCabina.getNombre() + " - " + cableCabinaDos.getNombre() : "N/A");
        } else {
            mailProperties.put("cabina", cableCabina != null ? cableCabina.getNombre() : "N/A");
        }

        mailProperties.put("colaborador", empleado != null ? empleado.getCodigoTm() + " - " + empleado.getNombres() + " " + empleado.getApellidos() : "N/A");
        mailProperties.put("username", "");
        mailProperties.put("generada", Util.dateTimeFormat(new Date()));

        CableNovedadOpUtil.notificar(mapa, mailProperties, cableNovedadOp);

    }

    private String validarDatos() {

        if (cableEventoTipo.getIdCableEventoTipo() == null) {
            return "Debe seleccionar un tipo de evento.";
        }

        if (cableEventoTipoDet.getIdCableEventoTipoDet() == null) {
            return "Debe seleccionar un detalle del tipo.";
        }

        if (cableEventoTipoDet.getReqUbicacion() == 1) {
            if (cableEstacion.getIdCableEstacion() == null) {
                return "Debe seleccionar una ubicación.";
            }
        }

        /**
         * TimeIniParada => Hora parada TimeFinParada => Hora reinicio
         */
        if (cableEventoTipoDet.getClaseEvento() == 2) {
            CableNovedadOp novInicioOp = cableNovedadOpEjb.findByClaseEventoAndFecha(Util.CLASE_EVENTO_INICIO, cableNovedadOp.getFecha());

            if (novInicioOp != null) {
                if (cableNovedadOp.getHorometroTotal().compareTo(novInicioOp.getHorometroTotal()) < 0) {
                    return "El horometro total debe ser mayor al horómetro del inicio de operación";
                }
                if (MovilidadUtil.diferencia(novInicioOp.getTimeFinParada(), cableNovedadOp.getTimeIniParada()) < 0) {
                    return "La hora de parada del fin de operación DEBE ser mayor a la hora de inicio de operación";
                }

                if (MovilidadUtil.diferencia(novInicioOp.getTimeFinParada(), cableNovedadOp.getTimeIniParada()) < 0) {
                    return "La hora de parada DEBE ser MAYOR a la hora de reinicio del INICIO de operación";
                }

                if (MovilidadUtil.diferencia(novInicioOp.getTimeFinParada(), cableNovedadOp.getTimeIniParada())
                        > MovilidadUtil.toSecs("23:59:59")) {
                    return "La diferencia de horas entre el inicio y fin de operación NO debe ser mayor a 24 Horas";
                }

                BigDecimal difHorometro = cableNovedadOp.getHorometroTotal().subtract(novInicioOp.getHorometroTotal());
                if (difHorometro.compareTo(BigDecimal.valueOf(24)) > 0) {
                    return "La diferencia entre el horometro de inicio y fin de operación NO debe ser mayor a 24 Horas";
                }
            }

        }

        if (cableEventoTipoDet.getReqHoraEventoParada() == 1
                && cableEventoTipoDet.getReqHoraEventoReinicio() == 1) {
            if (MovilidadUtil.diferencia(cableNovedadOp.getTimeIniParada(), cableNovedadOp.getTimeFinParada()) < 0) {
                return "La hora de parada DEBE ser menor a la hora de reinicio.";
            }

            switch (validarHorasRegistroAnterior()) {
                case 1:
                    return "La hora de parada a ingresar es menor a la del registro anterior";
                case 2:
                    return "La hora de reinicio a ingresar es menor a la del registro anterior";
            }
        }

        if (cableEventoTipoDet.getReqCabina() == 1) {
            if (cableCabina.getIdCableCabina() == null) {
                return "Debe seleccionar una cabina.";
            }
        }

        if (cableEventoTipoDet.getReqOperador() == 1) {
            if (empleado.getIdEmpleado() == null) {
                return "Debe seleccionar un empleado.";
            }
        }

        if (isEditing) {
            if (cableEventoTipoDet.getClaseEvento() == 1) {
                if (cableNovedadOpEjb.findByClaseEventoAndIdRegistro(Util.CLASE_EVENTO_INICIO, cableNovedadOp.getFecha(), selected.getIdCableNovedadOp()) != null) {
                    return "YA existe una novedad con el atributo INICIO de operación";
                }
                if (cableNovedadOp.getHorometroTotal() == null) {
                    return "ESTÁ intentando ingresar una novedad con atributo INICIO de operación SIN horometro total";
                }

                if (validarHorometroDiaAnterior()) {
                    return "El horometro ingresado es MENOR al del día anterior";
                }

                CableNovedadOp novFinOp = cableNovedadOpEjb.findByClaseEventoAndFecha(Util.CLASE_EVENTO_FIN, cableNovedadOp.getFecha());

                if (novFinOp != null) {
                    if (cableNovedadOp.getHorometroTotal().compareTo(novFinOp.getHorometroTotal()) > 0) {
                        return "El horometro total debe ser menor al horómetro del fin de operación";
                    }

                    if (MovilidadUtil.diferencia(novFinOp.getTimeIniParada(), cableNovedadOp.getTimeFinParada()) > 0) {
                        return "La hora de reinicio DEBE ser MENOR a la hora de parada del FIN de operación";
                    }
                }

            } else if (cableEventoTipoDet.getClaseEvento() == 2) {
                if (cableNovedadOpEjb.findByClaseEventoAndIdRegistro(Util.CLASE_EVENTO_FIN, cableNovedadOp.getFecha(), selected.getIdCableNovedadOp()) != null) {
                    return "YA existe una novedad con el atributo FIN de operación";
                }
                if (cableNovedadOp.getHorometroTotal() == null) {
                    return "ESTÁ intentando ingresar una novedad con atributo FIN de operación SIN horometro total";
                }

                if (validarHoraParadaFinOp()) {
                    return "La hora de parada a ingresar es MENOR a la hora de reinicio del registro previo al FIN de operación";
                }

            }
        } else {
            if (!lstCableNovedadOps.isEmpty()) {
                if (cableEventoTipoDet.getClaseEvento() == 1) {
                    if (cableNovedadOpEjb.findByClaseEventoAndIdRegistro(Util.CLASE_EVENTO_INICIO, cableNovedadOp.getFecha(), 0) != null) {
                        return "YA existe una novedad con el atributo INICIO de operación";
                    }
                    if (cableNovedadOp.getHorometroTotal() == null) {
                        return "ESTÁ intentando ingresar una novedad con atributo INICIO de operación SIN horometro total";
                    }

                    if (validarHorometroDiaAnterior()) {
                        return "El horometro ingresado es MENOR al del día anterior";
                    }

                } else if (cableEventoTipoDet.getClaseEvento() == 2) {
                    if (cableNovedadOpEjb.findByClaseEventoAndIdRegistro(Util.CLASE_EVENTO_FIN, cableNovedadOp.getFecha(), 0) != null) {
                        return "YA existe una novedad con el atributo FIN de operación";
                    }
                    if (cableNovedadOp.getHorometroTotal() == null) {
                        return "ESTÁ intentando ingresar una novedad con atributo FIN de operación SIN horometro total";
                    }

                    if (validarHoraParadaFinOp()) {
                        return "La hora de parada a ingresar es MENOR a la hora de reinicio del registro previo al FIN de operación";
                    }
                }
                if (cableNovedadOpEjb.findByFinOperacion(cableNovedadOp.getFecha()) != null) {
                    return "NO se pueden registrar novedades para la fecha  (" + Util.dateFormat(cableNovedadOp.getFecha()) + "), ya que existe un fin de operación";
                }
            }
        }

        return null;
    }

    /**
     * Verifica que la hora de parada a ingresar sea MAYOR que la hora de
     * reinicio del registro previo al FIN de operación
     *
     * @return true ( Si la hora de parada a ingresar sea MAYOR que la hora de
     * reinicio del registro anterior al FIN de operación ) y retorna false ( en
     * caso contario)
     */
    private boolean validarHoraParadaFinOp() {
        CableNovedadOp lastTimeFinParada = cableNovedadOpEjb.findLastTimeParada(cableNovedadOp.getFecha());

        if (lastTimeFinParada != null) {
            if (MovilidadUtil.diferencia(lastTimeFinParada.getTimeFinParada(), cableNovedadOp.getTimeIniParada()) < 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * Verifica que el horometro a ingresar sea mayor a la FECHA anterior a la
     * fecha seleccionada
     *
     * @return true ( Si el horometro a ingresar es menor a la del día anterior)
     * y retorna false ( en caso contario)
     */
    private boolean validarHorometroDiaAnterior() {
        CableNovedadOp novedadOpAux = cableNovedadOpEjb.findByFechaAnterior(MovilidadUtil.sumarDias(cableNovedadOp.getFecha(), -1));

        if (novedadOpAux != null) {
            if (cableNovedadOp.getHorometroTotal().compareTo(novedadOpAux.getHorometroTotal()) < 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * Verifica que el horometro a ingresar sea mayor a la del registro anterior
     * de la FECHA seleccionada
     *
     * @return 1 ( Si alguna de las hora (parada) a ingresar es menor a la del
     * registro anterior)
     * @return 2 ( Si alguna de las hora (reinicio) a ingresar es menor a la del
     * registro anterior)
     */
    private int validarHorasRegistroAnterior() {
        CableNovedadOp novedadOpAux = cableNovedadOpEjb.findByFechaAnterior(cableNovedadOp.getFecha());

        if (novedadOpAux != null) {
            if (MovilidadUtil.diferencia(novedadOpAux.getTimeIniParada(), cableNovedadOp.getTimeIniParada()) < 0) {
                return 1;
            }
            if (MovilidadUtil.diferencia(novedadOpAux.getTimeFinParada(), cableNovedadOp.getTimeFinParada()) < 0) {
                return 2;
            }
        }

        return 0;
    }

    public CableNovedadOp getCableNovedadOp() {
        return cableNovedadOp;
    }

    public void setCableNovedadOp(CableNovedadOp cableEventoTipoDet) {
        this.cableNovedadOp = cableEventoTipoDet;
    }

    public CableNovedadOp getSelected() {
        return selected;
    }

    public void setSelected(CableNovedadOp selected) {
        this.selected = selected;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public CableEventoTipo getCableEventoTipo() {
        return cableEventoTipo;
    }

    public void setCableEventoTipo(CableEventoTipo cableEventoTipo) {
        this.cableEventoTipo = cableEventoTipo;
    }

    public CableEventoTipoDet getCableEventoTipoDet() {
        return cableEventoTipoDet;
    }

    public void setCableEventoTipoDet(CableEventoTipoDet cableEventoTipoDet) {
        this.cableEventoTipoDet = cableEventoTipoDet;
    }

    public CableEstacion getCableEstacion() {
        return cableEstacion;
    }

    public void setCableEstacion(CableEstacion cableEstacion) {
        this.cableEstacion = cableEstacion;
    }

    public CableCabina getCableCabina() {
        return cableCabina;
    }

    public void setCableCabina(CableCabina cableCabina) {
        this.cableCabina = cableCabina;
    }

    public CableCabina getCableCabinaDos() {
        return cableCabinaDos;
    }

    public void setCableCabinaDos(CableCabina cableCabinaDos) {
        this.cableCabinaDos = cableCabinaDos;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<CableEventoTipo> getLstCableEventoTipo() {
        return lstCableEventoTipo;
    }

    public void setLstCableEventoTipo(List<CableEventoTipo> lstCableEventoTipo) {
        this.lstCableEventoTipo = lstCableEventoTipo;
    }

    public List<CableEventoTipoDet> getLstCableEventoTipoDet() {
        return lstCableEventoTipoDet;
    }

    public void setLstCableEventoTipoDet(List<CableEventoTipoDet> lstCableEventoTipoDet) {
        this.lstCableEventoTipoDet = lstCableEventoTipoDet;
    }

    public List<CableEstacion> getLstCableEstaciones() {
        return lstCableEstaciones;
    }

    public void setLstCableEstaciones(List<CableEstacion> lstCableEstaciones) {
        this.lstCableEstaciones = lstCableEstaciones;
    }

    public List<CableCabina> getLstCableCabina() {
        return lstCableCabina;
    }

    public void setLstCableCabina(List<CableCabina> lstCableCabina) {
        this.lstCableCabina = lstCableCabina;
    }

    public List<Empleado> getLstEmpleados() {
        return lstEmpleados;
    }

    public void setLstEmpleados(List<Empleado> lstEmpleados) {
        this.lstEmpleados = lstEmpleados;
    }

    public List<CableNovedadOp> getLstCableNovedadOps() {
        return lstCableNovedadOps;
    }

    public void setLstCableNovedadOps(List<CableNovedadOp> lstCableNovedadOps) {
        this.lstCableNovedadOps = lstCableNovedadOps;
    }

    public List<CableTramo> getLstCableTramos() {
        return lstCableTramos;
    }

    public void setLstCableTramos(List<CableTramo> lstCableTramos) {
        this.lstCableTramos = lstCableTramos;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public int getCabinaop() {
        return cabinaop;
    }

    public void setCabinaop(int cabinaop) {
        this.cabinaop = cabinaop;
    }

    public Integer getI_cableTramo() {
        return i_cableTramo;
    }

    public void setI_cableTramo(Integer i_cableTramo) {
        this.i_cableTramo = i_cableTramo;
    }

}
