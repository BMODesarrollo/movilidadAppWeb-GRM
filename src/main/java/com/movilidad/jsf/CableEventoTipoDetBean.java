package com.movilidad.jsf;

import com.movilidad.ejb.CableEventoTipoDetFacadeLocal;
import com.movilidad.ejb.CableEventoTipoFacadeLocal;
import com.movilidad.ejb.NotificacionProcesosFacadeLocal;
import com.movilidad.model.CableEventoTipo;
import com.movilidad.model.CableEventoTipoDet;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
@Named(value = "cableEventoTipoDetBean")
@ViewScoped
public class CableEventoTipoDetBean implements Serializable {

    @EJB
    private CableEventoTipoDetFacadeLocal cableEventoTipoDetEjb;
    @EJB
    private CableEventoTipoFacadeLocal cableEventoTipoEjb;
    @EJB
    private NotificacionProcesosFacadeLocal notificacionProcesosEjb;

    private CableEventoTipoDet cableEventoTipoDet;
    private CableEventoTipoDet selected;
    private CableEventoTipo cableEventoTipo;
    private NotificacionProcesos notificacionProceso;
    private String nombre;
    private String codigo;

    private boolean isEditing;
    private boolean b_req_ubicacion;
    private boolean b_req_cabina;
    private boolean b_req_hora_evento_parada;
    private boolean b_req_hora_evento_reinicio;
    private boolean b_req_tiempo_operacion_com;
    private boolean b_req_horometro_total;
    private boolean b_req_tiempo_operacion_sistema;
    private boolean b_req_operador;
    private boolean b_req_tramo;
    private boolean b_notifica;
    private boolean b_clase_evento_inicio;
    private boolean b_clase_evento_fin;

    private List<CableEventoTipoDet> lstCableEventoTipoDets;
    private List<CableEventoTipo> lstCableEventoTipo;
    private List<NotificacionProcesos> lstNotificacionProcesos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstCableEventoTipoDets = cableEventoTipoDetEjb.findAllByEstadoReg();
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
     * Carga una lista con las listas de distribución (Correos) antes de
     * registrar/modificar un registro.
     */
    public void prepareListNotificacionProceso() {
        lstNotificacionProcesos = null;

        if (lstNotificacionProcesos == null) {
            lstNotificacionProcesos = notificacionProcesosEjb.findAll(0);
            PrimeFaces.current().ajax().update(":frmNotificaList:dtNotificacionProcesos");
            PrimeFaces.current().executeScript("PF('wlvdtNotificacionProcesos').clearFilters();");
        }
    }

    /**
     * Evento que se dispara al seleccionar la lista de distribución en el modal
     * que muestran.
     *
     * @param event
     */
    public void onRowNotificacionProcesoClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof NotificacionProcesos) {
            setNotificacionProceso((NotificacionProcesos) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wlvdtNotificacionProcesos').clearFilters();");
    }

    public void nuevo() {
        nombre = "";
        codigo = "";
        cableEventoTipoDet = new CableEventoTipoDet();
        cableEventoTipo = new CableEventoTipo();
        selected = null;
        isEditing = false;
        b_req_ubicacion = false;
        b_req_cabina = false;
        b_req_hora_evento_parada = false;
        b_req_hora_evento_reinicio = false;
        b_req_tiempo_operacion_com = false;
        b_req_horometro_total = false;
        b_req_tiempo_operacion_sistema = false;
        b_req_operador = false;
        b_req_tramo = false;
        b_notifica = false;
        b_clase_evento_inicio = false;
        b_clase_evento_fin = false;
    }

    public void editar() {
        isEditing = true;
        cableEventoTipo = selected.getIdCableEventoTipo();
        notificacionProceso = selected.getIdNotificacionProcesos();
        b_req_ubicacion = (selected.getReqUbicacion() == 1);
        b_req_cabina = (selected.getReqCabina() == 1);
        b_req_hora_evento_parada = (selected.getReqHoraEventoParada() == 1);
        b_req_hora_evento_reinicio = (selected.getReqHoraEventoReinicio() == 1);
        b_req_tiempo_operacion_com = (selected.getReqTiempoOperacionCom() == 1);
        b_req_horometro_total = (selected.getReqHorometroTotal() == 1);
        b_req_tiempo_operacion_sistema = (selected.getReqTiempoOperacionSistema() == 1);
        b_req_operador = (selected.getReqOperador() == 1);
        b_req_tramo = (selected.getReqTramo() == 1);
        b_notifica = (selected.getNotifica() == 1);
        b_clase_evento_inicio = (selected.getClaseEvento() == 1);
        b_clase_evento_fin = (selected.getClaseEvento() == 2);
        nombre = selected.getNombre();
        codigo = selected.getCodigo();
        cableEventoTipoDet = selected;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {

            if (b_notifica) {
                cableEventoTipoDet.setIdNotificacionProcesos(notificacionProceso.getIdNotificacionProceso() != null ? notificacionProceso : null);
            }

            if (b_clase_evento_inicio) {
                cableEventoTipoDet.setClaseEvento(1);
            } else if (b_clase_evento_fin) {
                cableEventoTipoDet.setClaseEvento(2);
            } else {
                cableEventoTipoDet.setClaseEvento(0);
            }

            cableEventoTipoDet.setIdCableEventoTipo(cableEventoTipo);
            cableEventoTipoDet.setReqUbicacion(b_req_ubicacion ? 1 : 0);
            cableEventoTipoDet.setReqCabina(b_req_cabina ? 1 : 0);
            cableEventoTipoDet.setReqHoraEventoParada(b_req_hora_evento_parada ? 1 : 0);
            cableEventoTipoDet.setReqHoraEventoReinicio(b_req_hora_evento_reinicio ? 1 : 0);
            cableEventoTipoDet.setReqTiempoOperacionCom(b_req_tiempo_operacion_com ? 1 : 0);
            cableEventoTipoDet.setReqHorometroTotal(b_req_horometro_total ? 1 : 0);
            cableEventoTipoDet.setReqTiempoOperacionSistema(b_req_tiempo_operacion_sistema ? 1 : 0);
            cableEventoTipoDet.setReqOperador(b_req_operador ? 1 : 0);
            cableEventoTipoDet.setReqTramo(b_req_tramo ? 1 : 0);
            cableEventoTipoDet.setNotifica(b_notifica ? 1 : 0);

            cableEventoTipoDet.setCodigo(codigo);
            cableEventoTipoDet.setNombre(nombre);

            if (isEditing) {

                cableEventoTipoDet.setModificado(new Date());
                cableEventoTipoDet.setUsername(user.getUsername());
                cableEventoTipoDetEjb.edit(cableEventoTipoDet);

                PrimeFaces.current().executeScript("PF('wlvCableEventoTipoDet').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {

                cableEventoTipoDet.setEstadoReg(0);
                cableEventoTipoDet.setCreado(new Date());
                cableEventoTipoDet.setUsername(user.getUsername());
                cableEventoTipoDetEjb.create(cableEventoTipoDet);
                lstCableEventoTipoDets.add(cableEventoTipoDet);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public void verificarSiNotifica() {
        if (b_notifica) {
            notificacionProceso = new NotificacionProcesos();
        } else {
            notificacionProceso = null;
        }
    }

    public void verificarOperacion(int flag) {

        if (flag == 0 && b_clase_evento_inicio) {
            b_clase_evento_fin = false;
        }

        if (flag == 1 && b_clase_evento_fin) {
            b_clase_evento_inicio = false;
        }
        verificarReqHorometroTotal();
    }

    public void verificarReqHorometroTotal() {
        if (b_clase_evento_inicio || b_clase_evento_fin) {
            b_req_horometro_total = true;
        } else {
            b_req_horometro_total = false;
        }
    }

    private String validarDatos() {

        if (cableEventoTipo.getIdCableEventoTipo() == null) {
            return "Debe seleccionar un tipo de evento";
        }

        if (isEditing) {
//            if (cableEventoTipoDetEjb.findByNombre(nombre.trim(), selected.getIdCableEventoTipoDet()) != null) {
//                return "YA existe un registro con el nombre a ingresar";
//            }
            if (cableEventoTipoDetEjb.findByCodigo(codigo.trim(), selected.getIdCableEventoTipoDet()) != null) {
                return "YA existe un registro con el código a ingresar";
            }
            if (b_clase_evento_inicio) {
                if (cableEventoTipoDetEjb.verifyByClaseEvento(Util.CLASE_EVENTO_INICIO, selected.getIdCableEventoTipoDet()) != null) {
                    return "YA existe un registro con Inicio de operación";
                }
            }
            if (b_clase_evento_fin) {
                if (cableEventoTipoDetEjb.verifyByClaseEvento(Util.CLASE_EVENTO_FIN, selected.getIdCableEventoTipoDet()) != null) {
                    return "YA existe un registro con Fin de operación";
                }
            }
        } else {
            if (!lstCableEventoTipoDets.isEmpty()) {
//                if (cableEventoTipoDetEjb.findByNombre(nombre.trim(), 0) != null) {
//                    return "YA existe un registro con el nombre a ingresar";
//                }
                if (cableEventoTipoDetEjb.findByCodigo(codigo.trim(), 0) != null) {
                    return "YA existe un registro con el código a ingresar";
                }
                if (b_clase_evento_inicio) {
                    if (cableEventoTipoDetEjb.verifyByClaseEvento(Util.CLASE_EVENTO_INICIO, 0) != null) {
                        return "YA existe un registro con el atributo INICIO de operación";
                    }
                }
                if (b_clase_evento_fin) {
                    if (cableEventoTipoDetEjb.verifyByClaseEvento(Util.CLASE_EVENTO_FIN, 0) != null) {
                        return "YA existe un registro con el atributo de FIN de operación";
                    }
                }
            }
        }

        return null;
    }

    public CableEventoTipoDet getCableEventoTipoDet() {
        return cableEventoTipoDet;
    }

    public void setCableEventoTipoDet(CableEventoTipoDet cableEventoTipoDet) {
        this.cableEventoTipoDet = cableEventoTipoDet;
    }

    public CableEventoTipoDet getSelected() {
        return selected;
    }

    public void setSelected(CableEventoTipoDet selected) {
        this.selected = selected;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public boolean isB_req_ubicacion() {
        return b_req_ubicacion;
    }

    public void setB_req_ubicacion(boolean b_req_ubicacion) {
        this.b_req_ubicacion = b_req_ubicacion;
    }

    public boolean isB_req_cabina() {
        return b_req_cabina;
    }

    public void setB_req_cabina(boolean b_req_cabina) {
        this.b_req_cabina = b_req_cabina;
    }

    public boolean isB_req_hora_evento_parada() {
        return b_req_hora_evento_parada;
    }

    public void setB_req_hora_evento_parada(boolean b_req_hora_evento_parada) {
        this.b_req_hora_evento_parada = b_req_hora_evento_parada;
    }

    public boolean isB_req_hora_evento_reinicio() {
        return b_req_hora_evento_reinicio;
    }

    public void setB_req_hora_evento_reinicio(boolean b_req_hora_evento_reinicio) {
        this.b_req_hora_evento_reinicio = b_req_hora_evento_reinicio;
    }

    public boolean isB_req_tiempo_operacion_com() {
        return b_req_tiempo_operacion_com;
    }

    public void setB_req_tiempo_operacion_com(boolean b_req_tiempo_operacion_com) {
        this.b_req_tiempo_operacion_com = b_req_tiempo_operacion_com;
    }

    public boolean isB_req_horometro_total() {
        return b_req_horometro_total;
    }

    public void setB_req_horometro_total(boolean b_req_horometro_total) {
        this.b_req_horometro_total = b_req_horometro_total;
    }

    public boolean isB_req_tiempo_operacion_sistema() {
        return b_req_tiempo_operacion_sistema;
    }

    public void setB_req_tiempo_operacion_sistema(boolean b_req_tiempo_operacion_sistema) {
        this.b_req_tiempo_operacion_sistema = b_req_tiempo_operacion_sistema;
    }

    public boolean isB_req_operador() {
        return b_req_operador;
    }

    public void setB_req_operador(boolean b_req_operador) {
        this.b_req_operador = b_req_operador;
    }

    public boolean isB_req_tramo() {
        return b_req_tramo;
    }

    public void setB_req_tramo(boolean b_req_tramo) {
        this.b_req_tramo = b_req_tramo;
    }

    public boolean isB_notifica() {
        return b_notifica;
    }

    public void setB_notifica(boolean b_notifica) {
        this.b_notifica = b_notifica;
    }

    public boolean isB_clase_evento_inicio() {
        return b_clase_evento_inicio;
    }

    public void setB_clase_evento_inicio(boolean b_clase_evento_inicio) {
        this.b_clase_evento_inicio = b_clase_evento_inicio;
    }

    public boolean isB_clase_evento_fin() {
        return b_clase_evento_fin;
    }

    public void setB_clase_evento_fin(boolean b_clase_evento_fin) {
        this.b_clase_evento_fin = b_clase_evento_fin;
    }

    public CableEventoTipo getCableEventoTipo() {
        return cableEventoTipo;
    }

    public void setCableEventoTipo(CableEventoTipo cableEventoTipo) {
        this.cableEventoTipo = cableEventoTipo;
    }

    public NotificacionProcesos getNotificacionProceso() {
        return notificacionProceso;
    }

    public void setNotificacionProceso(NotificacionProcesos notificacionProceso) {
        this.notificacionProceso = notificacionProceso;
    }

    public List<CableEventoTipo> getLstCableEventoTipo() {
        return lstCableEventoTipo;
    }

    public void setLstCableEventoTipo(List<CableEventoTipo> lstCableEventoTipo) {
        this.lstCableEventoTipo = lstCableEventoTipo;
    }

    public List<NotificacionProcesos> getLstNotificacionProcesos() {
        return lstNotificacionProcesos;
    }

    public void setLstNotificacionProcesos(List<NotificacionProcesos> lstNotificacionProcesos) {
        this.lstNotificacionProcesos = lstNotificacionProcesos;
    }

    public List<CableEventoTipoDet> getLstCableEventoTipoDets() {
        return lstCableEventoTipoDets;
    }

    public void setLstCableEventoTipoDets(List<CableEventoTipoDet> lstCableEventoTipoDets) {
        this.lstCableEventoTipoDets = lstCableEventoTipoDets;
    }

}
