package com.movilidad.jsf;

import com.movilidad.ejb.InformeSeguridadFacadeLocal;
import com.movilidad.ejb.SstEmpresaFacadeLocal;
import com.movilidad.ejb.SstEmpresaVisitanteFacadeLocal;
import com.movilidad.model.InformeSeguridad;
import com.movilidad.model.SstEmpresa;
import com.movilidad.model.SstEmpresaVisitante;
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
@Named(value = "informeSeguridadBean")
@ViewScoped
public class InformeSeguridadBean implements Serializable {

    @EJB
    private InformeSeguridadFacadeLocal informeSeguridadEjb;
    @EJB
    private SstEmpresaVisitanteFacadeLocal empresaVisitanteEjb;
    @EJB
    private SstEmpresaFacadeLocal empresaEjb;

    @Inject
    private ArchivosJSFManagedBean archivosBean;

    private InformeSeguridad informeSeguridad;
    private InformeSeguridad selected;
    private SstEmpresaVisitante visitante;
    private UploadedFile file;
    private Integer i_sstEmpresa;

    private boolean isEditing;

    private List<InformeSeguridad> lstInformeSeguridad;
    private List<SstEmpresa> lstEmpresas;
    private List<SstEmpresaVisitante> lstEmpresaVisitantes;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstInformeSeguridad = informeSeguridadEjb.findAllByEstadoReg();
    }

    /**
     * Prepara la lista de visitantes antes de registrar/modificar un registro
     */
    public void prepareListVisitantes() {
        lstEmpresaVisitantes = null;

        if (i_sstEmpresa == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una empresa");
            return;
        }
        
        lstEmpresaVisitantes = empresaVisitanteEjb.findAllAprobadosByEmpresa(i_sstEmpresa);
        PrimeFaces.current().executeScript("PF('wVVisitantesListDialog').clearFilters();");
        
        if (lstEmpresaVisitantes == null) {
            MovilidadUtil.addErrorMessage("No se encontaron empleados");
        }
        
    }

    /**
     * Evento que se dispara al seleccionar el responsable del visitante en el
     * modal que muestra listado de empleados
     *
     * @param event
     */
    public void onRowVisitanteClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof SstEmpresaVisitante) {
            setVisitante((SstEmpresaVisitante) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wVVisitantesListDialog').clearFilters();");
        PrimeFaces.current().ajax().update(":frmVisitanteList:dtVisitantes");
    }

    public void nuevo() {
        isEditing = false;
        i_sstEmpresa = null;
        informeSeguridad = new InformeSeguridad();
        visitante = new SstEmpresaVisitante();
        selected = null;
        lstEmpresas = empresaEjb.findAllBySeguridad();
    }

    public void editar() {
        isEditing = true;
        i_sstEmpresa = selected.getIdSstEmpresa().getIdSstEmpresa();
        visitante = selected.getIdSstEmpresaVisitante();
        informeSeguridad = selected;
        lstEmpresas = empresaEjb.findAllBySeguridad();
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    private void guardarTransactional() {
        String validacion = validarDatos();

        if (validacion == null) {
            if (isEditing) {
                informeSeguridad.setIdSstEmpresa(empresaEjb.find(i_sstEmpresa));
                informeSeguridad.setIdSstEmpresaVisitante(visitante);
                informeSeguridad.setUsername(user.getUsername());
                informeSeguridad.setModificado(new Date());
                informeSeguridadEjb.edit(informeSeguridad);

                if (file != null) {
                    cargarArchivoAlServidor();
                }

                PrimeFaces.current().executeScript("PF('wlvInformeSeguridad').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                informeSeguridad.setIdSstEmpresa(empresaEjb.find(i_sstEmpresa));
                informeSeguridad.setIdSstEmpresaVisitante(visitante);
                informeSeguridad.setUsername(user.getUsername());
                informeSeguridad.setEstadoReg(0);
                informeSeguridad.setCreado(new Date());
                informeSeguridadEjb.create(informeSeguridad);

                cargarArchivoAlServidor();

                lstInformeSeguridad.add(informeSeguridad);
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public void cargarRecurso() {
        if (selected.getPathDocumento() != null) {
            PrimeFaces current = PrimeFaces.current();

            archivosBean.setExtension(".pdf");
            archivosBean.setPath(selected.getPathDocumento());
            archivosBean.setModalHeader("INFORME DE SEGURIDAD");
            current.executeScript("PF('DocumentoListDialog').show()");
        } else {
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("El registro NO tiene documento asociados");
        }
    }

    private void cargarArchivoAlServidor() {
        String path_documento;
        if (isEditing) {
            if (informeSeguridad.getPathDocumento() != null) {
                if (Util.deleteFile(informeSeguridad.getPathDocumento())) {
                    path_documento = Util.saveFile(file, informeSeguridad.getIdInformeSeguridad(), "informe_seguridad");
                    informeSeguridad.setPathDocumento(path_documento);
                    informeSeguridadEjb.edit(informeSeguridad);
                }
            }

        } else {
            path_documento = Util.saveFile(file, informeSeguridad.getIdInformeSeguridad(), "informe_seguridad");
            informeSeguridad.setPathDocumento(path_documento);
            informeSeguridadEjb.edit(informeSeguridad);
        }
        file = null;
    }

    private String validarDatos() {

        if (Util.validarFechaCambioEstado(informeSeguridad.getFechaIni(), informeSeguridad.getFechaFin())) {
            return "La fecha inicio NO debe ser mayor a la fecha final";
        }

        if (visitante == null) {
            return "DEBE seleccionar un empleado";
        }

        if (!isEditing) {
            if (file == null) {
                return "DEBE cargar un documento";
            }
        }
        return null;
    }

    /**
     *
     * Evento que dispara la subida de archivos para la anexarlos al registro
     * del informe de seguridad
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        PrimeFaces current = PrimeFaces.current();

        file = event.getFile();

        MovilidadUtil.addSuccessMessage("Documento agregado éxitosamente");
        current.executeScript("PF('AddFilesListDialog').hide()");
    }

    public InformeSeguridad getInformeSeguridad() {
        return informeSeguridad;
    }

    public void setInformeSeguridad(InformeSeguridad informeSeguridad) {
        this.informeSeguridad = informeSeguridad;
    }

    public InformeSeguridad getSelected() {
        return selected;
    }

    public void setSelected(InformeSeguridad selected) {
        this.selected = selected;
    }

    public SstEmpresaVisitante getVisitante() {
        return visitante;
    }

    public void setVisitante(SstEmpresaVisitante visitante) {
        this.visitante = visitante;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public Integer getI_sstEmpresa() {
        return i_sstEmpresa;
    }

    public void setI_sstEmpresa(Integer i_sstEmpresa) {
        this.i_sstEmpresa = i_sstEmpresa;
    }

    public List<InformeSeguridad> getLstInformeSeguridad() {
        return lstInformeSeguridad;
    }

    public void setLstInformeSeguridad(List<InformeSeguridad> lstInformeSeguridad) {
        this.lstInformeSeguridad = lstInformeSeguridad;
    }

    public List<SstEmpresa> getLstEmpresas() {
        return lstEmpresas;
    }

    public void setLstEmpresas(List<SstEmpresa> lstEmpresas) {
        this.lstEmpresas = lstEmpresas;
    }

    public List<SstEmpresaVisitante> getLstEmpresaVisitantes() {
        return lstEmpresaVisitantes;
    }

    public void setLstEmpresaVisitantes(List<SstEmpresaVisitante> lstEmpresaVisitantes) {
        this.lstEmpresaVisitantes = lstEmpresaVisitantes;
    }

}
