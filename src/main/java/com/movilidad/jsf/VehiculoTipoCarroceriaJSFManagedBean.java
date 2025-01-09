package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoTipoCarroceriaFacadeLocal;
import com.movilidad.model.VehiculoTipoCarroceria;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "vehiculoTipoCarroceriaBean")
@ViewScoped
public class VehiculoTipoCarroceriaJSFManagedBean implements Serializable {
    
    @EJB
    private VehiculoTipoCarroceriaFacadeLocal vehiculoTipoCarroceriaEjb;
    private VehiculoTipoCarroceria vehiculoTipoCarroceria;
    private List<VehiculoTipoCarroceria> lista;
    private VehiculoTipoCarroceria selected;
    private List<UploadedFile> archivos;
    private List<String> listFotos;
    
    @Inject
    private UploadFotoJSFManagedBean fotoJSFManagedBean;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    @PostConstruct
    public void init() {
        this.lista = this.vehiculoTipoCarroceriaEjb.findAll();
        this.vehiculoTipoCarroceria = new VehiculoTipoCarroceria();
        this.selected = null;
    }
    
    public void guardar() {
        vehiculoTipoCarroceria.setCreado(new Date());
        vehiculoTipoCarroceria.setUsername(user.getUsername());
        this.vehiculoTipoCarroceriaEjb.create(vehiculoTipoCarroceria);
        if (!archivos.isEmpty()) {
            String path = vehiculoTipoCarroceria.getPathLayout() == null ? "/" : vehiculoTipoCarroceria.getPathLayout();
            
            for (UploadedFile f : archivos) {
                path = Util.saveFile(f, vehiculoTipoCarroceria.getIdVehiculoTipoCarroceria(), "layoutTipoCarroceria");
            }
            vehiculoTipoCarroceria.setPathLayout(path);
            this.vehiculoTipoCarroceriaEjb.edit(vehiculoTipoCarroceria);
            archivos.clear();
        }
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Tipo de carrocería agregado."));
    }
    
    public void actualizar() {
        vehiculoTipoCarroceria.setUsername(user.getUsername());
        this.vehiculoTipoCarroceriaEjb.edit(vehiculoTipoCarroceria);
        if (!archivos.isEmpty()) {
            String path = vehiculoTipoCarroceria.getPathLayout() == null ? "/" : vehiculoTipoCarroceria.getPathLayout();
            
            for (UploadedFile f : archivos) {
                path = Util.saveFile(f, vehiculoTipoCarroceria.getIdVehiculoTipoCarroceria(), "layoutTipoCarroceria");
            }
            vehiculoTipoCarroceria.setPathLayout(path);
            this.vehiculoTipoCarroceriaEjb.edit(vehiculoTipoCarroceria);
            archivos.clear();
        }
        MovilidadUtil.hideModal("mtipo");
        MovilidadUtil.addSuccessMessage("Tipo de carrocería actualizado.");
    }
    
    public void editar() {
        archivos = new ArrayList<>();
        this.vehiculoTipoCarroceria = this.selected;
    }
    
    public void cambiarEstado() {
        this.selected.setEstadoReg(1);
        this.vehiculoTipoCarroceriaEjb.edit(selected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Estado Tipo de carrocería cambiado éxitosamente."));
    }
    
    public void nuevo() {
        archivos = new ArrayList<>();
        this.vehiculoTipoCarroceria = new VehiculoTipoCarroceria();
        this.selected = null;
    }

    /**
     * Obtener lista de las rutas de cada una de las imagenes por registro.
     *
     * @throws IOException
     */
    public void obtenerFotos() throws IOException {
        listFotos = new ArrayList<>();
        List<String> lstNombresImg = Util.getFileList(vehiculoTipoCarroceria.getIdVehiculoTipoCarroceria(), "layoutTipoCarroceria");
        
        for (String f : lstNombresImg) {
            f = vehiculoTipoCarroceria.getPathLayout() + f;
            listFotos.add(f);
        }
        fotoJSFManagedBean.setListFotos(listFotos);
    }

    /**
     * Agregar las imagenes en la lista archivos, para posteriormente ser
     * alamcenadas
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        archivos.add(event.getFile());
        MovilidadUtil.addSuccessMessage("Archivos agregados.");
    }
    
    public VehiculoTipoCarroceriaFacadeLocal getVehiculoTipoCarroceriaEjb() {
        return vehiculoTipoCarroceriaEjb;
    }
    
    public void setVehiculoTipoCarroceriaEjb(VehiculoTipoCarroceriaFacadeLocal vehiculoTipoCarroceriaEjb) {
        this.vehiculoTipoCarroceriaEjb = vehiculoTipoCarroceriaEjb;
    }
    
    public VehiculoTipoCarroceria getVehiculoTipoCarroceria() {
        return vehiculoTipoCarroceria;
    }
    
    public void setVehiculoTipoCarroceria(VehiculoTipoCarroceria vehiculoTipoCarroceria) {
        this.vehiculoTipoCarroceria = vehiculoTipoCarroceria;
    }
    
    public List<VehiculoTipoCarroceria> getLista() {
        return lista;
    }
    
    public void setLista(List<VehiculoTipoCarroceria> lista) {
        this.lista = lista;
    }
    
    public VehiculoTipoCarroceria getSelected() {
        return selected;
    }
    
    public void setSelected(VehiculoTipoCarroceria selected) {
        this.selected = selected;
    }
    
    public List<String> getListFotos() {
        return listFotos;
    }
    
    public void setListFotos(List<String> listFotos) {
        this.listFotos = listFotos;
    }
    
}
