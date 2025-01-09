package com.movilidad.jsf;

import com.movilidad.ejb.RegistroEstadoArmamentoFacadeLocal;
import com.movilidad.ejb.CableUbicacionFacadeLocal;
import com.movilidad.ejb.SegRegistroArmamentoDocFacadeLocal;
import com.movilidad.model.RegistroEstadoArmamento;
import com.movilidad.model.CableUbicacion;
import com.movilidad.model.SegRegistroArmamentoDoc;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.awt.Image;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "registroEstadoArmamentoBean")
@ViewScoped
public class RegistroEstadoArmamentoBean implements Serializable {

    @EJB
    private RegistroEstadoArmamentoFacadeLocal registroEstadoArmamentoEjb;
    @EJB
    private CableUbicacionFacadeLocal cableUbicacionEjb;
    @EJB
    private SegRegistroArmamentoDocFacadeLocal armamentoDocEjb;

    @Inject
    private ArchivosJSFManagedBean archivosBean;
    @Inject
    private UploadFotoJSFManagedBean fotoJSFManagedBean;
    private RegistroEstadoArmamento registroEstadoArmamento;
    private RegistroEstadoArmamento selected;
    private CableUbicacion cableUbicacion;

    private boolean isEditing;

    private boolean b_serial;
    private boolean b_municion;
    private boolean b_salvoConducto;
    private boolean b_estado;
    private boolean flag_rremove_photo = false;

    private int height = 0;
    private int width = 0;

    private List<RegistroEstadoArmamento> lstRegistroEstadoArmamentos;
    private List<CableUbicacion> lstCableUbicacion;
    private List<UploadedFile> archivos;
    private List<String> fotosArmamentos;
    private List<String> listFotosActividad;
    private List<String> lista_fotos_remover = new ArrayList<>();

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstRegistroEstadoArmamentos = registroEstadoArmamentoEjb.findAllByEstadoReg();
    }

    /**
     * Prepara la lista de tipos de eventos antes de registrar/modificar un
     * registro.
     */
    public void prepareListCableUbicacion() {
        lstCableUbicacion = cableUbicacionEjb.getUbicacionesConArmamento();
        PrimeFaces.current().ajax().update(":frmCableUbicacion:dtCableUbicacion");
        PrimeFaces.current().executeScript("PF('wlVdtCableUbicacion').clearFilters();");
    }

    /**
     * Evento que se dispara al seleccionar el tipo de evento en el modal que
     * muestra listado de tipos
     *
     * @param event
     */
    public void onRowCableUbicacionClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof CableUbicacion) {
            setCableUbicacion((CableUbicacion) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wlVdtCableUbicacion').clearFilters();");
    }

    public void nuevo() {
        registroEstadoArmamento = new RegistroEstadoArmamento();
        registroEstadoArmamento.setFechaHora(MovilidadUtil.fechaCompletaHoy());
        cableUbicacion = new CableUbicacion();
        selected = null;
        isEditing = false;
        b_serial = false;
        b_municion = false;
        b_salvoConducto = false;
        b_estado = false;
        flag_rremove_photo = false;
        archivos = new ArrayList<>();
    }

    public void editar() throws IOException {
        flag_rremove_photo = user.getUsername().equals(selected.getUsername());
        isEditing = true;
        b_serial = (selected.getSerial() == 1);
        b_municion = (selected.getMunicion() == 1);
        b_salvoConducto = (selected.getSalvoConducto() == 1);
        b_estado = (selected.getEstado() == 1);
        cableUbicacion = selected.getIdCableUbicacion();
        registroEstadoArmamento = selected;
        archivos = new ArrayList<>();
        obtenerFotosReturn();

    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {

            registroEstadoArmamento.setIdCableUbicacion(cableUbicacion);
            registroEstadoArmamento.setSerial(b_serial ? 1 : 0);
            registroEstadoArmamento.setMunicion(b_municion ? 1 : 0);
            registroEstadoArmamento.setSalvoConducto(b_salvoConducto ? 1 : 0);
            registroEstadoArmamento.setEstado(b_estado ? 1 : 0);

            if (isEditing) {

                for (String url : lista_fotos_remover) {
                    MovilidadUtil.eliminarFichero(url);
                }

                registroEstadoArmamento.setModificado(new Date());
                registroEstadoArmamento.setUsername(user.getUsername());
                registroEstadoArmamentoEjb.edit(registroEstadoArmamento);

                if (!archivos.isEmpty()) {
                    cargarFotosAlServidor();
                }

                PrimeFaces.current().executeScript("PF('wlvRegistroEstadoArmamento').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {

                registroEstadoArmamento.setEstadoReg(0);
                registroEstadoArmamento.setCreado(new Date());
                registroEstadoArmamento.setUsername(user.getUsername());
                registroEstadoArmamentoEjb.create(registroEstadoArmamento);
                lstRegistroEstadoArmamentos.add(registroEstadoArmamento);

                cargarFotosAlServidor();

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public void delete(String url) {
        lista_fotos_remover.add(url);
        listFotosActividad.remove(url);
    }

    /**
     * Obtiene el listado de fotos de registro estado armamento con su
     * respectivo path.
     *
     * @throws IOException
     */
    public void obtenerFotosReturn() throws IOException {
        this.listFotosActividad = new ArrayList<>();
        List<String> lstNombresImg;
        String path;

        lstNombresImg = Util.getFileList(registroEstadoArmamento.getIdRegistroEstadoArmamento(), "registro_estado_armamento");
        path = registroEstadoArmamento.getPathFotos();

        for (String f : lstNombresImg) {
            f = path + f;
            listFotosActividad.add(f);
        }
    }

    /**
     * Realiza la carga de fotos del registro de estado que halla sido
     * seleccionado.
     */
    public void obtenerFotosRegistroEstado() {
        try {
            if (selected.getPathFotos() != null) {
                cargarNombreFotos();
            } else {
                PrimeFaces.current().ajax().update(":msgs");
                MovilidadUtil.addErrorMessage("El registro NO tiene imágenes asociadas");
            }
        } catch (IOException ex) {
            Logger.getLogger(NovedadJSFManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Obtener lista de las rutas de cada una de las imagenes por registro
     *
     * @throws IOException
     */
    public void obtenerFotos() throws IOException {
        this.fotosArmamentos = new ArrayList<>();
        List<String> lstNombresImg = Util.getFileList(selected.getIdRegistroEstadoArmamento(), "registro_estado_armamento");

        for (String f : lstNombresImg) {
            f = selected.getPathFotos() + f;
            fotosArmamentos.add(f);
        }
        fotoJSFManagedBean.setListFotos(fotosArmamentos);
        MovilidadUtil.openModal("galeria_foto_dialog_wv");
    }

    /**
     * Devuelve nombres de fotos pertenecientes a un registro estado armamento,
     * y muestra las fotos en una vista
     *
     * @throws IOException
     */
    public void cargarNombreFotos() throws IOException {
        PrimeFaces current = PrimeFaces.current();
        fotosArmamentos = new ArrayList<>();
        List<String> lstNombresImg = Util.getFileList(selected.getIdRegistroEstadoArmamento(), "registro_estado_armamento");

        if (lstNombresImg.size() > 0) {
            width = 100;
            height = 100;
            for (String f : lstNombresImg) {
                fotosArmamentos.add(f);
                Image i = Util.mostrarImagenN(f, selected.getPathFotos());
                if (i != null) {
                    if (width < i.getWidth(null)) {
                        width = i.getWidth(null);
                    }
                    if (height < i.getHeight(null)) {
                        height = i.getHeight(null);
                    }
                }
            }
            archivosBean.setPath(selected.getPathFotos());
            archivosBean.setModalHeader("FOTOS REGISTRO ESTADO ARMAMENTO");
            current.executeScript("PF('FotosListDialog').show()");
        } else {
            width = 100;
            height = 100;
            current.ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("El registro NO tiene imágenes asociadas");
        }
    }

    public void cargarRecurso() {

        if (!cableUbicacion.getIdSegRegistroArmamento().getSegRegistroArmamentoDocList().isEmpty()) {
            cargarDocumento();
        } else {
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("El registro NO tiene documentos asociados");
        }
    }

    private void cargarDocumento() {
        PrimeFaces current = PrimeFaces.current();
        SegRegistroArmamentoDoc armamentoDoc = armamentoDocEjb.findByActivo(cableUbicacion.getIdSegRegistroArmamento().getIdSegRegistroArmamento());

        if (armamentoDoc == null) {
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("No se encontraron documentos activos");
            return;
        }

        String path = armamentoDoc.getPathDocumento();
        String ext = path.substring(path.lastIndexOf('.'), path.length());

        if (ext.equals(".pdf")) {
            width = 700;
            height = 500;
            archivosBean.setExtension(ext);
            archivosBean.setPath(path);
            archivosBean.setModalHeader("DOCUMENTO ARMAMENTO");
            current.executeScript("PF('DocumentoListDialog').show()");
        } else {

            try {
                Image i = Util.mostrarImagenN(armamentoDoc.getPathDocumento());
                if (i != null) {
                    if (width < i.getWidth(null)) {
                        width = i.getWidth(null);
                    }
                    if (height < i.getHeight(null)) {
                        height = i.getHeight(null);
                    }
                }

                archivosBean.setExtension(ext);
                archivosBean.setPath(armamentoDoc.getPathDocumento());
                archivosBean.setModalHeader("FOTO DOCUMENTO");
                current.executeScript("PF('DocumentoListDialog').show()");
            } catch (IOException ex) {
                Logger.getLogger(SegRegistroArmamentoDocBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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

    private void cargarFotosAlServidor() {
        for (UploadedFile f : archivos) {
            String path_imagen = Util.saveFile(f, registroEstadoArmamento.getIdRegistroEstadoArmamento(), "registro_estado_armamento");
            registroEstadoArmamento.setPathFotos(path_imagen);
            registroEstadoArmamentoEjb.edit(registroEstadoArmamento);
        }
        archivos.clear();
    }

    private String validarDatos() {

        if (cableUbicacion.getIdCableUbicacion() == null) {
            return "Debe seleccionar una ubicación.";
        }

        if (isEditing) {

        } else {
            if (lstRegistroEstadoArmamentos != null) {
                if (archivos.isEmpty()) {
                    return "DEBE seleccionar al menos una foto";
                }
            }
        }

        return null;
    }

    public RegistroEstadoArmamento getRegistroEstadoArmamento() {
        return registroEstadoArmamento;
    }

    public void setRegistroEstadoArmamento(RegistroEstadoArmamento registroEstadoArmamento) {
        this.registroEstadoArmamento = registroEstadoArmamento;
    }

    public RegistroEstadoArmamento getSelected() {
        return selected;
    }

    public void setSelected(RegistroEstadoArmamento selected) {
        this.selected = selected;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public boolean isB_serial() {
        return b_serial;
    }

    public void setB_serial(boolean b_serial) {
        this.b_serial = b_serial;
    }

    public boolean isB_municion() {
        return b_municion;
    }

    public void setB_municion(boolean b_municion) {
        this.b_municion = b_municion;
    }

    public boolean isB_salvoConducto() {
        return b_salvoConducto;
    }

    public void setB_salvoConducto(boolean b_salvoConducto) {
        this.b_salvoConducto = b_salvoConducto;
    }

    public boolean isB_estado() {
        return b_estado;
    }

    public void setB_estado(boolean b_estado) {
        this.b_estado = b_estado;
    }

    public CableUbicacion getCableUbicacion() {
        return cableUbicacion;
    }

    public void setCableUbicacion(CableUbicacion cableUbicacion) {
        this.cableUbicacion = cableUbicacion;
    }

    public List<CableUbicacion> getLstCableUbicacion() {
        return lstCableUbicacion;
    }

    public void setLstCableUbicacion(List<CableUbicacion> lstCableUbicacion) {
        this.lstCableUbicacion = lstCableUbicacion;
    }

    public List<RegistroEstadoArmamento> getLstRegistroEstadoArmamentos() {
        return lstRegistroEstadoArmamentos;
    }

    public void setLstRegistroEstadoArmamentos(List<RegistroEstadoArmamento> lstRegistroEstadoArmamentos) {
        this.lstRegistroEstadoArmamentos = lstRegistroEstadoArmamentos;
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
