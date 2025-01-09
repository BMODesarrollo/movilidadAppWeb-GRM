package com.movilidad.jsf;

import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionProcesosFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.OperacionPatiosFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.ejb.OperacionGruaFacadeLocal;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.OperacionPatios;
import com.movilidad.model.Vehiculo;
import com.movilidad.model.OperacionGrua;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.Util;
import java.awt.Image;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "operacionGruaBean")
@ViewScoped
public class OperacionGruaJSFManagedBean implements Serializable {
    
    @EJB
    private OperacionGruaFacadeLocal operacionGruaEjb;
    @EJB
    private VehiculoFacadeLocal vehiculoEjb;
    @EJB
    private OperacionPatiosFacadeLocal operacionPatiosEjb;
    @EJB
    private NotificacionProcesosFacadeLocal notificacionProcesosEjb;
    
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    
    @Inject
    private UploadFotoJSFManagedBean fotoJSFManagedBean;
    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    private OperacionGrua operacionGrua;
    private Vehiculo vehiculo;
    private OperacionPatios operacionPatio;
    private Date desde;
    private Date hasta;
    
    private List<OperacionGrua> lista;
    private List<Vehiculo> lstVehiculos;
    private List<OperacionPatios> lstOperacionPatios;
    private OperacionGrua selected;
    private UploadedFile file;
    private List<UploadedFile> archivos;
    private List<String> fotosOperacionGrua;
    
    private boolean modulo;
    
    @PostConstruct
    public void init() {
        desde = MovilidadUtil.fechaHoy();
        hasta = MovilidadUtil.fechaHoy();
        
        this.operacionGrua = new OperacionGrua();
        this.vehiculo = new Vehiculo();
        this.operacionPatio = new OperacionPatios();
        this.selected = null;
        this.fotosOperacionGrua = new ArrayList<>();
        this.archivos = new ArrayList<>();
        getByDateRange();
    }
    
    public void getByDateRange() {
        lista = operacionGruaEjb.findByRangeDates(desde, hasta);
    }
    
    public void prepareListVehiculos() {
        this.vehiculo = new Vehiculo();
    }
    
    public void prepareListOperacionPatios() {
        this.operacionPatio = new OperacionPatios();
    }
    
    public void onVehiculoChosen(SelectEvent event) {
        setVehiculo((Vehiculo) event.getObject());
    }
    
    public void onOperacionPatiosChosen(SelectEvent event) {
        setOperacionPatios((OperacionPatios) event.getObject());
    }
    
    public void onRowDblClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof Vehiculo) {
            setVehiculo((Vehiculo) event.getObject());
            PrimeFaces.current().executeScript("PF('wVdtVehiculo').clearFilters();");
            PrimeFaces.current().ajax().update(":frmPrincipalListVehiculo:dtVehiculos");
        }
        
        if (event.getObject() instanceof OperacionPatios) {
            setOperacionPatios((OperacionPatios) event.getObject());
            PrimeFaces.current().executeScript("PF('wVdtOperacionPatio').clearFilters();");
            PrimeFaces.current().ajax().update(":frmOperacionPatioList:dtOperacionPatios");
        }
    }
    
    public void guardar() {

//        if (Util.validarFecha(operacionGrua.getFecha())) {
//            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
//                    "Info", "La Fecha de la novedad de daño debe ser igual o menor al dia de hoy");
//            FacesContext.getCurrentInstance().addMessage(null, message);
//            return;
//        }
        if (vehiculo.getCodigo() != null) {
            operacionGrua.setIdVehiculo(vehiculo);
        }
        if (operacionPatio.getIdOperacionPatios() != null) {
            operacionGrua.setIdOperacionPatio(operacionPatio);
        }
        operacionGrua.setUsername(user.getUsername());
        operacionGrua.setCreado(new Date());
        this.operacionGruaEjb.create(operacionGrua);
        
        if (!archivos.isEmpty()) {
            String path_documento = " ";
            for (UploadedFile f : archivos) {
                path_documento = Util.saveFile(f, operacionGrua.getIdOperacionGrua(), "operacion_grua");
            }
            operacionGrua.setPathFotos(path_documento);
            this.operacionGruaEjb.edit(operacionGrua);
            archivos.clear();
        }
        notificar();
        this.lista.add(operacionGrua);
        
        if (modulo) {
            PrimeFaces.current().ajax().update(":frmPrincipalOperacionGrua:dtOperacionGruas");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Operación registrada éxitosamente."));
        }
        nuevo(true);
    }
    
    public void actualizar() {

//        if (Util.validarFecha(operacionGrua.getFecha())) {
//            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
//                    "Info", "La Fecha de la novedad de daño debe ser igual o menor al dia de hoy");
//            FacesContext.getCurrentInstance().addMessage(null, message);
//            return;
//        }
        if (vehiculo.getCodigo() != null) {
            operacionGrua.setIdVehiculo(vehiculo);
        }
        if (operacionPatio.getIdOperacionPatios() != null) {
            operacionGrua.setIdOperacionPatio(operacionPatio);
        }
        operacionGrua.setUsername(user.getUsername());
        operacionGrua.setCreado(new Date());
        this.operacionGruaEjb.edit(operacionGrua);
        
        if (!archivos.isEmpty()) {
            String path_documento = " ";
            for (UploadedFile f : archivos) {
                path_documento = Util.saveFile(f, operacionGrua.getIdOperacionGrua(), "operacion_grua");
            }
            operacionGrua.setPathFotos(path_documento);
            this.operacionGruaEjb.edit(operacionGrua);
            archivos.clear();
        }
//        nuevo(true);
        MovilidadUtil.hideModal("mtipo");
        MovilidadUtil.addSuccessMessage("Operación actualizada éxitosamente.");
    }
    
    public void editar() {
        this.operacionGrua = this.selected;
        this.vehiculo = operacionGrua.getIdVehiculo();
        this.operacionPatio = operacionGrua.getIdOperacionPatio();
        this.archivos.clear();
    }
    
    public StreamedContent getImagenDinamica() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        if (selected != null) {
            String nombre_imagen;
            String path = selected.getPathFotos();
            
            if (path != null) {
                if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                    return new DefaultStreamedContent();
                } else {
                    nombre_imagen = context.getExternalContext().getRequestParameterMap().get("nombre_imagen");
                    try {
                        return Util.mostrarImagen(nombre_imagen, path);
                    } catch (Exception e) {
//                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        return new DefaultStreamedContent();
    }
    
    public void obtenerFotos() throws IOException {
        this.fotosOperacionGrua.clear();
        List<String> lstNombresImg = Util.getFileList(selected.getIdOperacionGrua(), "operacion_grua");
        
        if (lstNombresImg != null) {
            for (String f : lstNombresImg) {
                f = selected.getPathFotos() + f;
                fotosOperacionGrua.add(f);
            }
        }
        fotoJSFManagedBean.setListFotos(fotosOperacionGrua);
    }
    
    public void cambiarEstado() {
        this.selected.setEstadoReg(1);
        this.operacionGruaEjb.edit(selected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "El estado de la operación fue cambiado éxitosamente."));
    }
    
    public void nuevo(boolean mdlo) {
        setModulo(mdlo);
        this.operacionGrua = new OperacionGrua();
        this.vehiculo = new Vehiculo();
        this.operacionPatio = new OperacionPatios();
        this.selected = null;
        this.archivos.clear();
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        PrimeFaces current = PrimeFaces.current();
        
        archivos.add(event.getFile());
        
        current.executeScript("PF('AddFilesListDialog').hide()");
        current.ajax().update(":frmNuevaOperacionGrua:messages");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Fotos agregadas éxitosamente."));
    }
    
    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.find(Util.ID_OPERACION_GRUAS_TEMPLATE);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }
    
    private void notificar() {
        List<String> lstNombresImg = Util.getFileList(operacionGrua.getIdOperacionGrua(), "operacion_grua");
        List<String> adjuntos = new ArrayList<>();
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("fecha", Util.dateFormat(operacionGrua.getFecha()));
        mailProperties.put("placa_grua", operacionGrua.getPlacaGrua() != null ? operacionGrua.getPlacaGrua() : "");
        mailProperties.put("cc_operador", operacionGrua.getCcOperador() != null ? operacionGrua.getCcOperador() : "");
        mailProperties.put("operador_grua", operacionGrua.getOperadorGrua() != null ? operacionGrua.getOperadorGrua() : "");
        mailProperties.put("vehiculo", operacionGrua.getIdVehiculo() != null ? operacionGrua.getIdVehiculo().getCodigo() : "");
        mailProperties.put("patio", operacionGrua.getIdOperacionPatio() != null ? operacionGrua.getIdOperacionPatio().getNombrePatio() : "");
        mailProperties.put("observaciones", operacionGrua.getObservaciones() != null ? operacionGrua.getObservaciones() : "");
        mailProperties.put("username", "");
        mailProperties.put("generada", Util.dateTimeFormat(operacionGrua.getCreado()));
        String subject = "Operación grúa";
        String destinatarios = "";
        NotificacionProcesos notificacionProcesos = notificacionProcesosEjb.find(Util.ID_OPGRUAS_NOTI_PROC);
        if (notificacionProcesos != null) {
            destinatarios = notificacionProcesos.getEmails() != null ? notificacionProcesos.getEmails() : "";
        }
        
        if (lstNombresImg != null) {
            for (String f : lstNombresImg) {
                adjuntos.add(operacionGrua.getPathFotos() + f);
            }
        }
        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", adjuntos != null ? adjuntos : null);
        
    }
    
    public OperacionGruaFacadeLocal getOperacionGruaEjb() {
        return operacionGruaEjb;
    }
    
    public void setOperacionGruaEjb(OperacionGruaFacadeLocal operacionGruaEjb) {
        this.operacionGruaEjb = operacionGruaEjb;
    }
    
    public OperacionGrua getOperacionGrua() {
        return operacionGrua;
    }
    
    public void setOperacionGrua(OperacionGrua operacionGrua) {
        this.operacionGrua = operacionGrua;
    }
    
    public OperacionPatios getOperacionPatios() {
        return operacionPatio;
    }
    
    public void setOperacionPatios(OperacionPatios operacionPatio) {
        this.operacionPatio = operacionPatio;
    }
    
    public Vehiculo getVehiculo() {
        return vehiculo;
    }
    
    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
    
    public List<OperacionGrua> getLista() {
        return lista;
    }
    
    public void setLista(List<OperacionGrua> lista) {
        this.lista = lista;
    }
    
    public OperacionGrua getSelected() {
        return selected;
    }
    
    public void setSelected(OperacionGrua selected) {
        this.selected = selected;
    }
    
    public UploadedFile getFile() {
        return file;
    }
    
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public List<UploadedFile> getArchivos() {
        return archivos;
    }
    
    public void setArchivos(List<UploadedFile> archivos) {
        this.archivos = archivos;
    }
    
    public List<Vehiculo> getLstVehiculos() {
        if (lstVehiculos == null) {
            lstVehiculos = new ArrayList<>();
            lstVehiculos = vehiculoEjb.findAll();
        }
        return lstVehiculos;
    }
    
    public void setLstVehiculos(List<Vehiculo> lstVehiculos) {
        this.lstVehiculos = lstVehiculos;
    }
    
    public List<OperacionPatios> getLstOperacionPatios() {
        if (lstOperacionPatios == null) {
            lstOperacionPatios = new ArrayList<>();
            lstOperacionPatios = operacionPatiosEjb.findAll();
        }
        return lstOperacionPatios;
    }
    
    public void setLstOperacionPatios(List<OperacionPatios> lstOperacionPatios) {
        this.lstOperacionPatios = lstOperacionPatios;
    }
    
    public List<String> getFotosOperacionGrua() {
        return fotosOperacionGrua;
    }
    
    public void setFotosNovedades(List<String> fotosOperacionGrua) {
        this.fotosOperacionGrua = fotosOperacionGrua;
    }
    
    public boolean isModulo() {
        return modulo;
    }
    
    public void setModulo(boolean modulo) {
        this.modulo = modulo;
    }
    
    public UserExtended getUser() {
        return user;
    }
    
    public void setUser(UserExtended user) {
        this.user = user;
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
    
}
