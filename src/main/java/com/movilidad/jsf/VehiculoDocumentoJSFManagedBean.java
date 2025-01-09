package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.ejb.VehiculoDocumentoFacadeLocal;
import com.movilidad.ejb.VehiculoTipoDocumentoFacadeLocal;
import com.movilidad.model.Vehiculo;
import com.movilidad.model.VehiculoDocumentos;
import com.movilidad.model.VehiculoTipoDocumentos;
import com.movilidad.utils.Util;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "vehiculoDocumentoBean")
@ViewScoped
public class VehiculoDocumentoJSFManagedBean implements Serializable {

    @EJB
    private VehiculoDocumentoFacadeLocal vehiculoDocumentoEjb;
    @EJB
    private VehiculoFacadeLocal vehiculoEjb;
    @EJB
    private VehiculoTipoDocumentoFacadeLocal vehiculoTipoDocumentoEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private VehiculoDocumentos vehiculoDocumento;
    private List<VehiculoDocumentos> lista;
    private List<Vehiculo> lstVehiculo;
    private List<VehiculoTipoDocumentos> lstvehiculoTipoDocumento;
    private VehiculoDocumentos selected;
    private UploadedFile file;

    private boolean isEditing;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    public void consultar() {
        this.lista = this.vehiculoDocumentoEjb.findAllByActivos(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        MovilidadUtil.clearFilter("wlvDtVehiculoDoc");
    }

    public void nuevo() {
        isEditing = false;
        vehiculoDocumento = new VehiculoDocumentos();
        selected = null;
        file = null;

        lstvehiculoTipoDocumento = this.vehiculoTipoDocumentoEjb.findAll();
        lstVehiculo = this.vehiculoEjb.findAllVehiculosByidGopUnidadFuncional(
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void guardar() {
        String validacion = validarDatos();

        if (validacion == null) {

            if (vehiculoDocumento.getVigenteDesde() != null) {
                VehiculoDocumentos documentoAnterior = vehiculoDocumentoEjb.obtenerDocumentoAnterior(vehiculoDocumento.getIdVehiculo().getIdVehiculo(), vehiculoDocumento.getIdVehiculoTipoDocumento().getIdVehiculoTipoDocumento());

                if (documentoAnterior != null) {
                    if (documentoAnterior.getVigenteHasta() != null) {
                        if (Util.validarFechaCambioEstado(vehiculoDocumento.getVigenteDesde(), documentoAnterior.getVigenteHasta())) {
                            documentoAnterior.setActivo(0);
                        }
                    } else {
                        documentoAnterior.setActivo(0);
                    }
                    vehiculoDocumentoEjb.edit(documentoAnterior);
                }

            }

            vehiculoDocumento.setActivo(1);
            vehiculoDocumento.setEstadoReg(0);
            vehiculoDocumento.setUsuario(user.getUsername());
            vehiculoDocumento.setCreado(new Date());
            vehiculoDocumentoEjb.create(vehiculoDocumento);

            if (file != null) {
                String path_documento;
                path_documento = Util.saveFile(file, vehiculoDocumento.getIdVehiculo().getIdVehiculo(), "vehiculo_documentos");
                vehiculoDocumento.setPathDocumento(path_documento);
                vehiculoDocumentoEjb.edit(vehiculoDocumento);
                file = null;
            }
            nuevo();
            MovilidadUtil.addSuccessMessage("Documento registrado éxitosamente.");
            consultar();
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }

    }

    public void actualizar() {

        String validacion = validarDatos();

        if (validacion == null) {
            vehiculoDocumento.setUsuario(user.getUsername());
            vehiculoDocumentoEjb.edit(vehiculoDocumento);

            if (file != null) {
                if (selected.getPathDocumento() != null) {
                    Util.deleteFile(selected.getPathDocumento());
                }
                String path_documento;
                path_documento = Util.saveFile(file, vehiculoDocumento.getIdVehiculo().getIdVehiculo(), "vehiculo_documentos");
                vehiculoDocumento.setPathDocumento(path_documento);
                vehiculoDocumentoEjb.edit(vehiculoDocumento);
                file = null;
            }
            MovilidadUtil.hideModal("mtipo");
            MovilidadUtil.addSuccessMessage("Documento actualizado éxitosamente.");
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    /**
     * Realiza la carga previa de los datos antes de actualizar
     */
    public void editar() {
        this.vehiculoDocumento = this.selected;
        lstvehiculoTipoDocumento = this.vehiculoTipoDocumentoEjb.findAll();
        lstVehiculo = this.vehiculoEjb.findAll();
        isEditing = true;
    }

    private String validarDatos() {

        if (vehiculoDocumento.getVigenteHasta() != null) {
            if (Util.validarFechaCambioEstado(vehiculoDocumento.getVigenteDesde(), vehiculoDocumento.getVigenteHasta())) {
                return "La fecha de inicio no puede ser mayor a la fecha fin";
            }
        }

        if (isEditing) {

            // Validaciones en caso de que se vayan a actualizar datos
            if (vehiculoDocumento.getVigenteHasta() != null) {
                if (vehiculoDocumentoEjb.findByVehiculoAndTipoDocumento(vehiculoDocumento.getIdVehiculo().getIdVehiculo(), vehiculoDocumento.getIdVehiculoTipoDocumento().getIdVehiculoTipoDocumento(), selected.getIdVehiculoDocumento(), vehiculoDocumento.getVigenteDesde(), vehiculoDocumento.getVigenteHasta()) != null) {
                    return "YA existe un documento con los parámetros ingresados";
                }
            } else {
                if (vehiculoDocumentoEjb.verificarRegistroDesde(vehiculoDocumento.getIdVehiculo().getIdVehiculo(), vehiculoDocumento.getIdVehiculoTipoDocumento().getIdVehiculoTipoDocumento(), vehiculoDocumento.getVigenteDesde(), selected.getIdVehiculoDocumento()) != null) {
                    return "YA existe un documento con los parámetros ingresados.";
                }
            }

        } else {

            // Validaciones en caso de que se vayan a registrar datos
            if (vehiculoDocumento.getVigenteHasta() != null) {
                if (vehiculoDocumentoEjb.findByVehiculoAndTipoDocumento(vehiculoDocumento.getIdVehiculo().getIdVehiculo(), vehiculoDocumento.getIdVehiculoTipoDocumento().getIdVehiculoTipoDocumento(), 0, vehiculoDocumento.getVigenteDesde(), vehiculoDocumento.getVigenteHasta()) != null) {
                    return "YA existe un documento con los parámetros ingresados";
                }
                if (vehiculoDocumentoEjb.verificarRegistro(vehiculoDocumento.getIdVehiculo().getIdVehiculo(), vehiculoDocumento.getIdVehiculoTipoDocumento().getIdVehiculoTipoDocumento(), vehiculoDocumento.getVigenteDesde(), vehiculoDocumento.getVigenteHasta(), 0) != null) {
                    return "EL documento a registrar se encuentra dentro del rango de fechas de un DOCUMENTO ACTIVO.";
                }
            } else {
                if (vehiculoDocumentoEjb.verificarRegistroDesde(vehiculoDocumento.getIdVehiculo().getIdVehiculo(), vehiculoDocumento.getIdVehiculoTipoDocumento().getIdVehiculoTipoDocumento(), vehiculoDocumento.getVigenteDesde(), 0) != null) {
                    return "YA existe un documento con los parámetros ingresados.";
                }
            }

            if (file == null) {
                return "DEBE cargar un archivo ANTES de registrar el documento";
            }

        }
        return null;
    }

    /**
     * Realiza el cambio de estado de un documento
     *
     * Activo => 1 Inactivo => 0
     *
     */
    public void cambiarEstado() {
        this.selected.setActivo(selected.getActivo() == 1 ? 0 : 1);
        this.vehiculoDocumentoEjb.edit(selected);
        MovilidadUtil.addSuccessMessage("Estado de documento cambiado éxitosamente.");
    }

    /**
     * Evento que se dispara al realizar la carga de un archivo
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        PrimeFaces current = PrimeFaces.current();
        this.file = event.getFile();
        current.executeScript("PF('AddFilesListDialog').hide()");
        current.ajax().update(":frmNuevoTipo:messages");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Documento agregado éxitosamente."));
    }

    /**
     * Permite validar si se debe mostrar el campo vigente desde.
     *
     * @return true si el tipo de documento seleccionado requiere una vigencia ó
     * corresponde a una licencia de transporte. en caso contrario, retorna
     * false.
     */
    public boolean validarRenderFechaDesde() {
        if (vehiculoDocumento != null) {
            if (vehiculoDocumento.getIdVehiculoTipoDocumento() != null) {
                return vehiculoDocumento.getIdVehiculoTipoDocumento().getVencimiento() == 1
                        || vehiculoDocumento.getIdVehiculoTipoDocumento().getCodigo().equals("LT");
            }
        }

        return false;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<Vehiculo> getLstVehiculo() {
        return lstVehiculo;
    }

    public void setLstVehiculo(List<Vehiculo> lstVehiculo) {
        this.lstVehiculo = lstVehiculo;
    }

    public VehiculoDocumentos getVehiculoDocumento() {
        return vehiculoDocumento;
    }

    public void setVehiculoDocumento(VehiculoDocumentos vehiculoDocumento) {
        this.vehiculoDocumento = vehiculoDocumento;
    }

    public List<VehiculoDocumentos> getLista() {
        return lista;
    }

    public void setLista(List<VehiculoDocumentos> lista) {
        this.lista = lista;
    }

    public List<VehiculoTipoDocumentos> getLstvehiculoTipoDocumento() {
        return lstvehiculoTipoDocumento;
    }

    public void setLstvehiculoTipoDocumento(List<VehiculoTipoDocumentos> lstvehiculoTipoDocumento) {
        this.lstvehiculoTipoDocumento = lstvehiculoTipoDocumento;
    }

    public VehiculoDocumentos getSelected() {
        return selected;
    }

    public void setSelected(VehiculoDocumentos selected) {
        this.selected = selected;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

}
