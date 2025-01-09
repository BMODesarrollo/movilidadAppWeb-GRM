package com.movilidad.jsf;

import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.ejb.NotificacionTelegramDetFacadeLocal;
import com.movilidad.ejb.NotificacionTelegramFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.NotificacionTelegram;
import com.movilidad.model.NotificacionTelegramDet;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.primefaces.event.ToggleEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "notificacionTelegramBean")
@ViewScoped
public class NotificacionTelegramBean implements Serializable {
    
    @EJB
    private NotificacionTelegramFacadeLocal notificacionTelegramEjb;
    @EJB
    private NotificacionTelegramDetFacadeLocal notificacionTelegramDetEjb;
    @EJB
    private GopUnidadFuncionalFacadeLocal unidadFuncionalEjb;
    
    @Inject
    private NotificaListComponentBean notificaListComponentBean;
    
    private NotificacionTelegram notificacionTelegram;
    private NotificacionTelegram selected;
    private NotificacionTelegramDet selectedDetalle;
    
    private List<NotificacionTelegramDet> lstNotificacionTelegramDet;
    
    private boolean isEditing;
    private boolean isEditingDetalle; // Flag Edición detalles

    private String chatId;
    private String token;
    private String nombreBot;
    private Integer idGopUnidadFuncional;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    @PostConstruct
    public void init() {
        consultar();
    }
    
    public void editar() {
        isEditing = true;
        notificacionTelegram = selected;
        token = selected.getToken();
        nombreBot = selected.getNombreBot();
        lstNotificacionTelegramDet = notificacionTelegramDetEjb.findByIdNotifTelegramAndUf(notificacionTelegram.getIdNotificacionTelegram());
        limpiarCampos();
    }
    
    public void editarDetalle() {
        isEditingDetalle = true;
        chatId = selectedDetalle.getChatId();
        idGopUnidadFuncional = selectedDetalle.getIdGopUnidadFuncional().getIdGopUnidadFuncional();
        notificaListComponentBean.setProceso(selectedDetalle.getIdNotificacionProceso());
    }

    /**
     * Método que se encarga de guardar/modificar los registros en base de datos
     */
    public void guardar() {
        guardarTransactional();
    }

    /**
     * Método que agrega un detalle (en memoria) a la lista de detalles
     */
    public void agregarDetalle() {
        
        if (token == null || token.isEmpty()) {
            MovilidadUtil.addErrorMessage("DEBE ingresar un token");
            return;
        }
        if (chatId == null || chatId.isEmpty()) {
            MovilidadUtil.addErrorMessage("DEBE ingresar un Chat ID");
            return;
        }
        if (idGopUnidadFuncional == null) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }
        if (notificaListComponentBean.getProceso() == null) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar un nombre de grupo");
            return;
        }
        
        if (!isEditingDetalle) {
            if (verificarDetalle(idGopUnidadFuncional)) {
                MovilidadUtil.addErrorMessage("YA existe un registro con los parámetros ingresados");
                return;
            }
        }
        
        if (isEditingDetalle) {
            selectedDetalle.setChatId(chatId);
            selectedDetalle.setIdNotificacionTelegram(notificacionTelegram);
            selectedDetalle.setIdNotificacionProceso(notificaListComponentBean.getProceso());
            selectedDetalle.setIdGopUnidadFuncional(unidadFuncionalEjb.find(idGopUnidadFuncional));
            selectedDetalle.setUsername(user.getUsername());
            selectedDetalle.setEstadoReg(0);
            selectedDetalle.setCreado(MovilidadUtil.fechaCompletaHoy());
            
            notificacionTelegramDetEjb.edit(selectedDetalle);
            isEditingDetalle = false;
            
            MovilidadUtil.addSuccessMessage("Detalle actualizado con éxito");
        } else {
            NotificacionTelegramDet obj = new NotificacionTelegramDet();
            obj.setChatId(chatId);
            obj.setIdNotificacionTelegram(notificacionTelegram);
            obj.setIdNotificacionProceso(notificaListComponentBean.getProceso());
            obj.setIdGopUnidadFuncional(unidadFuncionalEjb.find(idGopUnidadFuncional));
            obj.setUsername(user.getUsername());
            obj.setEstadoReg(0);
            obj.setCreado(MovilidadUtil.fechaCompletaHoy());
            
            lstNotificacionTelegramDet.add(obj);
            MovilidadUtil.addSuccessMessage("Detalle agregado con éxito");
            
        }
        
        limpiarCampos();
    }

    /**
     * Evento que se dispara para cargar los detalles registrados
     *
     * @param event
     */
    public void onRowToggleDetalle(ToggleEvent event) {
        NotificacionTelegram obj = ((NotificacionTelegram) event.getData());
        lstNotificacionTelegramDet = notificacionTelegramDetEjb.findByIdNotifTelegramAndUf(obj.getIdNotificacionTelegram());
    }
    
    public void eliminarRegistro() {
        
        if (selectedDetalle.getIdNotificacionTelegramDet() == null) {
            lstNotificacionTelegramDet.remove(selectedDetalle);
        } else {
            selectedDetalle.setEstadoReg(1);
            notificacionTelegramDetEjb.edit(selectedDetalle);
            lstNotificacionTelegramDet.remove(selectedDetalle);
        }
        
        consultar();
        MovilidadUtil.addSuccessMessage("Registro eliminado éxitosamente");
    }
    
    private void nuevo() {
        isEditing = false;
        notificacionTelegram = new NotificacionTelegram();
        lstNotificacionTelegramDet = new ArrayList<>();
        token = null;
        nombreBot = null;
        selected = null;
        
        limpiarCampos();
    }
    
    @Transactional
    private void guardarTransactional() {
        try {
            String msgValidacion = validarDatos();
            
            if (msgValidacion == null) {
                notificacionTelegram.setToken(token);
                notificacionTelegram.setNombreBot(nombreBot);
                notificacionTelegram.setUsername(user.getUsername());
                notificacionTelegram.setNotificacionTelegramDetList(lstNotificacionTelegramDet);
                if (isEditing) {
                    notificacionTelegram.setModificado(MovilidadUtil.fechaCompletaHoy());
                    notificacionTelegramEjb.edit(notificacionTelegram);
                    nuevo();
                    MovilidadUtil.addSuccessMessage("Registro actualizado con éxito");
                } else {
                    notificacionTelegram.setCreado(MovilidadUtil.fechaCompletaHoy());
                    notificacionTelegram.setEstadoReg(0);
                    notificacionTelegramEjb.create(notificacionTelegram);
                    nuevo();
                    MovilidadUtil.addSuccessMessage("Registro guardado con éxito");
                }
                
                consultar();
            } else {
                MovilidadUtil.addErrorMessage(msgValidacion);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("Error al guardar datos");
        }
    }

    /**
     * Método que se encarga de validar si YA existe un detalle con la unidad
     * funcional seleccionada en la lista de detalles.
     *
     * @param idGopUnidadFuncional
     * @return true si existe un registro que cumpla con los parámetros, de lo
     * contrario false
     */
    private boolean verificarDetalle(Integer idGopUnidadFuncional) {
        
        if (lstNotificacionTelegramDet == null || lstNotificacionTelegramDet.isEmpty()) {
            return false;
        }
        
        Predicate<NotificacionTelegramDet> igualUf = x -> x.getIdGopUnidadFuncional().getIdGopUnidadFuncional().equals(idGopUnidadFuncional);
        Predicate<NotificacionTelegramDet> igualProceso = y -> y.getIdNotificacionProceso().getIdNotificacionProceso().equals(notificaListComponentBean.getProceso().getIdNotificacionProceso());
        Optional<NotificacionTelegramDet> detalle = lstNotificacionTelegramDet
                .stream()
                .filter(igualUf.and(igualProceso))
                .findFirst();
        
        return detalle.isPresent();
    }
    
    private void limpiarCampos() {
        notificaListComponentBean.setProceso(null);
        chatId = null;
        idGopUnidadFuncional = null;
    }
    
    private String validarDatos() {
        if (lstNotificacionTelegramDet == null || lstNotificacionTelegramDet.isEmpty()) {
            return "DEBE ingresar al menos un detalle";
        }
        
        if (!isEditing) {
            if (notificacionTelegram.getIdNotificacionTelegram() != null) {
                if (notificacionTelegram.getIdNotificacionTelegram() > 0) {
                    return "SOLO debe existir un registro";
                }
            }
        }
        
        return null;
    }
    
    private void consultar() {
        notificacionTelegram = notificacionTelegramEjb.findAllByEstadoReg();
        
        if (notificacionTelegram != null) {
            isEditing = true;
            token = notificacionTelegram.getToken();
            nombreBot = notificacionTelegram.getNombreBot();
            lstNotificacionTelegramDet = notificacionTelegramDetEjb.findByIdNotifTelegramAndUf(notificacionTelegram.getIdNotificacionTelegram());
        } else {
            nuevo();
        }
        
    }
    
    public NotificacionTelegram getNotificacionTelegram() {
        return notificacionTelegram;
    }
    
    public void setNotificacionTelegram(NotificacionTelegram notificacionTelegram) {
        this.notificacionTelegram = notificacionTelegram;
    }
    
    public NotificacionTelegram getSelected() {
        return selected;
    }
    
    public void setSelected(NotificacionTelegram selected) {
        this.selected = selected;
    }
    
    public List<NotificacionTelegramDet> getLstNotificacionTelegramDet() {
        return lstNotificacionTelegramDet;
    }
    
    public void setLstNotificacionTelegramDet(List<NotificacionTelegramDet> lstNotificacionTelegramDet) {
        this.lstNotificacionTelegramDet = lstNotificacionTelegramDet;
    }
    
    public boolean isIsEditing() {
        return isEditing;
    }
    
    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }
    
    public String getChatId() {
        return chatId;
    }
    
    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
    
    public Integer getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }
    
    public void setIdGopUnidadFuncional(Integer idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }
    
    public NotificacionTelegramDet getSelectedDetalle() {
        return selectedDetalle;
    }
    
    public void setSelectedDetalle(NotificacionTelegramDet selectedDetalle) {
        this.selectedDetalle = selectedDetalle;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getNombreBot() {
        return nombreBot;
    }
    
    public void setNombreBot(String nombreBot) {
        this.nombreBot = nombreBot;
    }
    
    public boolean isIsEditingDetalle() {
        return isEditingDetalle;
    }
    
    public void setIsEditingDetalle(boolean isEditingDetalle) {
        this.isEditingDetalle = isEditingDetalle;
    }
    
}
