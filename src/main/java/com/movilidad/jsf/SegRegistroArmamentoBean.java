package com.movilidad.jsf;

import com.movilidad.ejb.SegRegistroArmamentoFacadeLocal;
import com.movilidad.model.SegRegistroArmamento;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "registroArmamentoBean")
@ViewScoped
public class SegRegistroArmamentoBean implements Serializable {
    
    @EJB
    private SegRegistroArmamentoFacadeLocal registroArmamentoEjb;
    
    @Inject
    private ArchivosJSFManagedBean archivosBean;
    @Inject
    private UploadFotoJSFManagedBean fotoJSFManagedBean;
    private SegRegistroArmamento registroArmamento;
    private SegRegistroArmamento selected;
    private String serial;
    
    private boolean isEditing;
    
    private int height = 0;
    private int width = 0;
    private boolean flag_rremove_photo = false;
    
    private List<SegRegistroArmamento> lstSegRegistroArmamentos;
    private List<UploadedFile> archivos;
    private List<String> fotosArmamentos;
    private List<String> listFotosActividad;
    private List<String> lista_fotos_remover = new ArrayList<>();
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    @PostConstruct
    public void init() {
        lstSegRegistroArmamentos = registroArmamentoEjb.findByEstadoReg();
    }
    
    public void nuevo() {
        registroArmamento = new SegRegistroArmamento();
        selected = null;
        serial = "";
        isEditing = false;
        archivos = new ArrayList<>();
    }
    
    public void editar() throws IOException {
        flag_rremove_photo = user.getUsername().equals(selected.getUsername());
        isEditing = true;
        serial = selected.getSerial();
        registroArmamento = selected;
        archivos = new ArrayList<>();
        obtenerFotosReturn();
    }
    
    public void guardar() {
        guardarTransactional();
    }
    
    @Transactional
    private void guardarTransactional() {
        String validacion = validarDatos();
        
        if (validacion == null) {
            if (isEditing) {
                registroArmamento.setSerial(serial);
                registroArmamento.setUsername(user.getUsername());
                registroArmamento.setModificado(new Date());
                registroArmamentoEjb.edit(registroArmamento);
                
                for (String url : lista_fotos_remover) {
                    MovilidadUtil.eliminarFichero(url);
                }
                
                if (!archivos.isEmpty()) {
                    cargarFotosAlServidor();
                }
                
                PrimeFaces.current().executeScript("PF('wvArmamento').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                registroArmamento.setSerial(serial);
                registroArmamento.setUsername(user.getUsername());
                registroArmamento.setEstadoReg(0);
                registroArmamento.setCreado(new Date());
                registroArmamentoEjb.create(registroArmamento);
                
                cargarFotosAlServidor();
                
                lstSegRegistroArmamentos.add(registroArmamento);
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    /**
     * Obtiene el listado de fotos del armamento con su respectivo path.
     *
     * @throws IOException
     */
    public void obtenerFotosReturn() throws IOException {
        this.listFotosActividad = new ArrayList<>();
        List<String> lstNombresImg;
        String path;
        
        lstNombresImg = Util.getFileList(registroArmamento.getIdSegRegistroArmamento(), "armamento");
        path = registroArmamento.getPathFoto();
        
        for (String f : lstNombresImg) {
            f = path + f;
            listFotosActividad.add(f);
        }
    }

    /**
     * Elimina una foto del armamento de la lista de fotos
     *
     * @param url
     */
    public void delete(String url) {
        lista_fotos_remover.add(url);
        listFotosActividad.remove(url);
    }

//    /**
//     * Realiza la carga de fotos de un armamento que halla sido seleccionada
//     */
//    public void obtenerFotosArmamento() {
//        try {
//            if (selected.getPathFoto() != null) {
//                obtenerFotos();
//            } else {
//                PrimeFaces.current().ajax().update(":msgs");
//                MovilidadUtil.addErrorMessage("El registro NO tiene imágenes asociadas");
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(NovedadJSFManagedBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    /**
     * Devuelve nombres de fotos pertenecientes a un armamento, y muestra las
     * fotos en una vista
     *
     * @throws IOException
     */
//    public void cargarNombreFotos() throws IOException {
//        PrimeFaces current = PrimeFaces.current();
//        fotosArmamentos = new ArrayList<>();
//        List<String> lstNombresImg = Util.getFileList(selected.getIdSegRegistroArmamento(), "armamento");
//
//        if (lstNombresImg.size() > 0) {
//            width = 100;
//            height = 100;
//            for (String f : lstNombresImg) {
//                fotosArmamentos.add(f);
//            }
//            archivosBean.setPath("");
//            archivosBean.setSelectedArmamento(selected);
//            archivosBean.setModalHeader("FOTOS ARMAMENTO");
//            current.executeScript("PF('FotosListDialog').show()");
//        } else {
//            width = 100;
//            height = 100;
//            current.ajax().update(":msgs");
//            MovilidadUtil.addErrorMessage("El registro NO tiene imágenes asociadas");
//        }
//    }
    /**
     * Obtener lista de las rutas de cada una de las imagenes por registro
     *
     * @throws IOException
     */
    public void obtenerFotos() throws IOException {
        this.fotosArmamentos = new ArrayList<>();
        List<String> lstNombresImg = Util.getFileList(selected.getIdSegRegistroArmamento(), "armamento");
        
        for (String f : lstNombresImg) {
            f = selected.getPathFoto() + f;
            fotosArmamentos.add(f);
        }
        fotoJSFManagedBean.setListFotos(fotosArmamentos);
        MovilidadUtil.openModal("galeria_foto_dialog_wv");
    }
    
    private void cargarFotosAlServidor() {
        for (UploadedFile f : archivos) {
            String path_imagen = Util.saveFile(f, registroArmamento.getIdSegRegistroArmamento(), "armamento");
            registroArmamento.setPathFoto(path_imagen);
            registroArmamentoEjb.edit(registroArmamento);
        }
        archivos.clear();
    }
    
    private String validarDatos() {
        
        if (isEditing) {
            if (!registroArmamento.getSerial().equals(serial)) {
                if (registroArmamentoEjb.findBySerial(serial.trim()) != null) {
                    return "YA existe un registro con el serial a ingresar";
                }
            }
        } else {
            
            if (lstSegRegistroArmamentos != null) {
                if (registroArmamentoEjb.findBySerial(serial.trim()) != null) {
                    return "YA existe un registro con el serial a ingresar";
                }
                if (archivos.isEmpty()) {
                    return "DEBE seleccionar al menos una foto";
                }
            }
        }
        return null;
    }

    /**
     *
     * Evento que dispara la subida de archivos para la anexarlos al registro de
     * un armamento
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        PrimeFaces current = PrimeFaces.current();
        
        archivos.add(event.getFile());
        
        MovilidadUtil.addSuccessMessage("Foto(s) agregada(s) éxitosamente");
        current.executeScript("PF('AddFilesListDialog').hide()");
    }
    
    public SegRegistroArmamento getRegistroArmamento() {
        return registroArmamento;
    }
    
    public void setRegistroArmamento(SegRegistroArmamento registroArmamento) {
        this.registroArmamento = registroArmamento;
    }
    
    public SegRegistroArmamento getSelected() {
        return selected;
    }
    
    public void setSelected(SegRegistroArmamento selected) {
        this.selected = selected;
    }
    
    public String getSerial() {
        return serial;
    }
    
    public void setSerial(String serial) {
        this.serial = serial;
    }
    
    public List<SegRegistroArmamento> getLstSegRegistroArmamentos() {
        return lstSegRegistroArmamentos;
    }
    
    public void setLstSegRegistroArmamentos(List<SegRegistroArmamento> lstSegRegistroArmamentos) {
        this.lstSegRegistroArmamentos = lstSegRegistroArmamentos;
    }
    
    public boolean isIsEditing() {
        return isEditing;
    }
    
    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }
    
    public List<UploadedFile> getArchivos() {
        return archivos;
    }
    
    public void setArchivos(List<UploadedFile> archivos) {
        this.archivos = archivos;
    }
    
    public List<String> getFotosArmamentos() {
        return fotosArmamentos;
    }
    
    public void setFotosArmamentos(List<String> fotosArmamentos) {
        this.fotosArmamentos = fotosArmamentos;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public boolean isFlag_rremove_photo() {
        return flag_rremove_photo;
    }
    
    public void setFlag_rremove_photo(boolean flag_rremove_photo) {
        this.flag_rremove_photo = flag_rremove_photo;
    }
    
    public List<String> getListFotosActividad() {
        return listFotosActividad;
    }
    
    public void setListFotosActividad(List<String> listFotosActividad) {
        this.listFotosActividad = listFotosActividad;
    }
    
    public List<String> getLista_fotos_remover() {
        return lista_fotos_remover;
    }
    
    public void setLista_fotos_remover(List<String> lista_fotos_remover) {
        this.lista_fotos_remover = lista_fotos_remover;
    }
    
}
