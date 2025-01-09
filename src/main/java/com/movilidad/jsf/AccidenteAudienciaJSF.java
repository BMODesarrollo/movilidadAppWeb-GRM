/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccidenteAudienciaFacadeLocal;
import com.movilidad.ejb.AccidenteFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionProcesosFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;

import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteAudiencia;

import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.model.NotificacionTemplate;

import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "accidenteAudienciaJSF")
@ViewScoped
public class AccidenteAudienciaJSF implements Serializable {

    @EJB
    private AccidenteAudienciaFacadeLocal accidenteAudienciaFacadeLocal;
    @EJB
    private AccidenteFacadeLocal accidenteFacadeLocal;
    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTipoDetalleFacadeLocal;
    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private NotificacionProcesosFacadeLocal notificacionProcesosEjb;
   
    private AccidenteAudiencia accidenteAudiencia;

    private List<AccidenteAudiencia> listAccidenteAudiencia;

    private int i_idAccidente;
    private Accidente accidente;
    private String nombreEmpleado;
    private String tipoNovedad;
    private String placa;
    private String fechaNovedad;
    
    private boolean b_flag;
    
    private NotificacionProcesos notificacionProceso;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @Inject
    private AccidenteJSF accidenteJSF;

    public AccidenteAudienciaJSF() {
    }

    @PostConstruct
    public void init() {
        PrimeFaces.current().executeScript("PF('bui5').show()");
        b_flag = true;
    }

    public void guardar() {
        try {
            if (i_idAccidente != 0) {
                if (accidenteAudiencia != null) {
                    cargarObjetos();
                    accidenteAudiencia.setIdAccidente(new Accidente(i_idAccidente));
                    accidenteAudiencia.setCreado(new Date());
                    accidenteAudiencia.setModificado(new Date());
                    accidenteAudiencia.setUsername(user.getUsername());
                    accidenteAudiencia.setEstadoReg(0);
                    accidenteAudienciaFacadeLocal.create(accidenteAudiencia);
                    i_idAccidente = accidenteJSF.compartirIdAccidente();
                    accidente = accidenteFacadeLocal.find(i_idAccidente);
                    nombreEmpleado = accidente.getIdEmpleado().getNombresApellidos();
                    tipoNovedad = accidente.getIdNovedadTipoDetalle().getTituloTipoNovedad();
                    placa = accidente.getIdVehiculo().getPlaca();
                    fechaNovedad = accidente.getIdNovedad().getFecha().toLocaleString();
                    String email = user.getEmail();
                    envioCorreoNotificacion(accidenteAudiencia, email);
                    MovilidadUtil.addSuccessMessage("Se guardó el Accidente Audiencia correctamente");
                    reset();
                }
                return;
            }
            MovilidadUtil.addErrorMessage("No se puede realizar esta acción, no se seleccionó un accidente");
        } catch (Exception e) {

        }
    }

    public void prepareGuardar() {
        accidenteAudiencia = new AccidenteAudiencia();
        accidenteAudiencia.setFecha(new Date());
    }

    public void editar() {
        try {
            if (accidenteAudiencia != null) {
                cargarObjetos();
                accidenteAudienciaFacadeLocal.edit(accidenteAudiencia);
                MovilidadUtil.addSuccessMessage("Se actualizó el Accidente Audiencia correctamente");
                reset();
            }
        } catch (Exception e) {
            System.out.println("Error en editar Accidente Audiencia");
        }
    }

    public void eliminarLista(AccidenteAudiencia at) {
        try {
            at.setEstadoReg(1);
            accidenteAudienciaFacadeLocal.edit(at);
            MovilidadUtil.addSuccessMessage("Se elimino el Accidente Audiencia de la lista");
            reset();
        } catch (Exception e) {
            System.out.println("Error en editar Accidente Audiencia");
        }
    }

    public void prepareEditar(AccidenteAudiencia adj) {
        accidenteAudiencia = adj;
        b_flag = false;
    }

    void cargarObjetos() {
        
    }
    
    public void envioCorreoNotificacion(AccidenteAudiencia accidenteAudiencia, String email) {
        try {
            String destinatarios = "";
            NotificacionCorreoConf conf = NCCEJB.find(1);
            if (conf == null) {
                return;
            }
            
            if(accidenteAudiencia.getFechaConciliacion() != null){
                NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(ConstantsUtil.TEMPLATE_NOTIFICACION_ACC_AUD_CONCILIACION);
                if (template == null) {
                        return;
                }
                
                notificacionProceso = notificacionProcesosEjb.findByCodigo("CONACC");
                destinatarios = notificacionProceso.getEmails();
                String destinatariosByUf = "";
                
                if (notificacionProceso.getNotificacionProcesoDetList() != null) {
                    
                    destinatariosByUf = MovilidadUtil.obtenerCorreosByUf(notificacionProceso.getNotificacionProcesoDetList(), accidente.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
                    destinatarios = destinatarios + "," + destinatariosByUf;    
                }
                
                Map mapa = SendMails.getMailParams(conf, template);
                Map mailProperties = new HashMap();
                mailProperties.put("fechaConciliacion", accidenteAudiencia.getFechaConciliacion().toLocaleString());
                mailProperties.put("valor", accidenteAudiencia.getValor());
                mailProperties.put("fechaPago", accidenteAudiencia.getFechaPago().toLocaleString());
                mailProperties.put("casoAccidente", tipoNovedad);
                mailProperties.put("nombreColaborador", nombreEmpleado);
                mailProperties.put("placa", placa);
                mailProperties.put("fechaNovedad", fechaNovedad);
                SendMails.sendEmail(mapa,
                        mailProperties,
                        "Se genera conciliación "+accidenteAudiencia.getIdAccidenteAudiencia(),
                        "",
                        destinatarios,
                        "Notificaciones Conciliación", null);
            }
            
                NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(ConstantsUtil.TEMPLATE_NOTIFICACION_ACCIDENTE_AUDIENCIA);
                if (template == null) {
                        return;
                }
                Map mapa = SendMails.getMailParams(conf, template);
                Map mailProperties = new HashMap();
                mailProperties.put("fecha", accidenteAudiencia.getFecha().toLocaleString());
                mailProperties.put("lugar", accidenteAudiencia.getLugar());
                mailProperties.put("casoAccidente", tipoNovedad);
                mailProperties.put("nombreColaborador", nombreEmpleado);
                mailProperties.put("placa", placa);
                mailProperties.put("fechaNovedad", fechaNovedad);
                    SendMails.sendEmail(mapa,
                            mailProperties,
                            "Se genera audiencia :            " + accidenteAudiencia.getIdAccidenteAudiencia(),
                            "",
                            email,
                            "Notificaciones Audiencia", null);
            


            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void reset() {
        accidenteAudiencia = null;
        b_flag = true;
        PrimeFaces.current().executeScript("PF('bui5').show()");
    }

    public AccidenteAudiencia getAccidenteAudiencia() {
        return accidenteAudiencia;
    }

    public void setAccidenteAudiencia(AccidenteAudiencia accidenteAudiencia) {
        this.accidenteAudiencia = accidenteAudiencia;
    }

    public List<AccidenteAudiencia> getListAccidenteAudiencia() {
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        if (i_idAccidente != 0) {
            listAccidenteAudiencia = accidenteAudienciaFacadeLocal.estadoReg(i_idAccidente);
        }
        return listAccidenteAudiencia;
    }

    public int getI_idAccidente() {
        return i_idAccidente;
    }

    public void setI_idAccidente(int i_idAccidente) {
        this.i_idAccidente = i_idAccidente;
    }

    public boolean isB_flag() {
        return b_flag;
    }
   
}
