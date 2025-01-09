package com.movilidad.jsf;

import com.movilidad.model.OperacionKmTacografo;
import com.movilidad.ejb.OperacionKmTacografoFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.JsfUtil;
import com.movilidad.utils.JsfUtil.PersistAction;
import com.movilidad.utils.MovilidadUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;
//import javax.enterprise.context.SessionScoped;

@Named("operacionKmTacografoController")
@ViewScoped
public class OperacionKmTacografoController implements Serializable {

    @EJB
    private OperacionKmTacografoFacadeLocal operacionKmTacografoEJB;
    @EJB
    private VehiculoFacadeLocal vehiculoFacade;

    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;

    @Inject
    private UploadFotoJSFManagedBean uploadFotoMB;

    private List<OperacionKmTacografo> items;
    private OperacionKmTacografo selected;
    private OperacionKmTacografo verificarKmInicial = null;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //----
    private String c_coVehiculo = "";

    public OperacionKmTacografoController() {
    }

    public void openDialogFileUpLoad() {
        PrimeFaces.current().ajax().update("formPDF");
        uploadFotoMB.setFlag(false);
        uploadFotoMB.setCompoUpdate("OperacionKmTacografoCreateForm:idFULOP");
        uploadFotoMB.setFile(null);
        uploadFotoMB.setModal("PF('UploadPDFDialog').hide();");
        PrimeFaces.current().executeScript("PF('UploadPDFDialog').show();");
    }

    public void prepDownloadLocal(String path) throws Exception {
        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
    }

    public OperacionKmTacografo prepareCreate() {
        selected = new OperacionKmTacografo();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "Kilometraje Tacógrafo se creó correctamente");
        if (JsfUtil.isValidationFailed()) {
            MovilidadUtil.addErrorMessage("Kilometraje Tacógrafo no se creó correctamente");
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Kilometraje Tacógrafo se actualizó correctamente");
        if (JsfUtil.isValidationFailed()) {
            MovilidadUtil.addErrorMessage("Kilometraje Tacógrafo no se actualió correctamente");
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Kilometraje Tacógrafo se eliminó correctamente");
        if (JsfUtil.isValidationFailed()) {
            MovilidadUtil.addErrorMessage("Kilometraje Tacógrafo no se eliminó correctamente");
        }
    }

    public void reset() {
        selected = new OperacionKmTacografo();
        c_coVehiculo = "";
        items = getOperacionKmTacografoEJB().findEstRegis();
    }

    public void prepareEditar() {
        c_coVehiculo = getSelected().getIdVehiculo().getCodigo();
    }

    public String verificacionVeh() {
        if (!(getC_coVehiculo().equals(""))) {
            Vehiculo vehiculo = vehiculoFacade.getVehiculoCodigo(getC_coVehiculo());
            if (vehiculo != null) {
                getSelected().setIdVehiculo(vehiculo);
                return "Vehículo valido";
            } else {
                return "Vehículo no valido";
            }
        } else {
            return "Vehículo no valido";
        }
    }

    boolean verificarUnicoRegistroDia() {
        return operacionKmTacografoEJB.fechaRegistro(getSelected().getFecha(), getSelected().getIdVehiculo().getIdVehiculo());
    }
//    
//    boolean editarRegistro(){
//        return false;
//    }

    public String validarFecha() {
        Date hoy = new Date();
        return new SimpleDateFormat("yyyy/MM/dd").format(hoy);
    }

    void verificarKmInicial() {
        int i_aux = 0;
        i_aux = getSelected().getIdVehiculo().getIdVehiculo();
        verificarKmInicial = operacionKmTacografoEJB.verificarKmInicial(i_aux);
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                switch (persistAction) {
                    case CREATE:
                        getSelected().setCreado(new Date());
                        getSelected().setEstadoReg(0);
                        getSelected().setModificado(new Date());
                        getSelected().setUsername(user.getUsername());
                        if (uploadFotoMB.getFile() == null) {
                            MovilidadUtil.addAdvertenciaMessage("Se debe cargar un archivo");
                            return;
                        }
                        if (!(verificacionVeh().equals("Vehículo valido"))) {
                            MovilidadUtil.addErrorMessage(verificacionVeh());
                            c_coVehiculo = "";
                            return;
                        }
                        if (getSelected().getKmFinal() < getSelected().getKmInicial()) {
                            MovilidadUtil.addErrorMessage("Km Inicial debe ser menor a Km Final");
                            return;
                        }
                        if (verificarUnicoRegistroDia()) {
                            MovilidadUtil.addErrorMessage("Este Vehículo se le registró información del Tacógrafo el día de hoy");
                            return;
                        }
                        verificarKmInicial();
                        if (verificarKmInicial != null) {
                            if (getVerificarKmInicial().getKmFinal() > getSelected().getKmInicial()) {
                                MovilidadUtil.addErrorMessage("Km Inicial es menor al ultimo registro"
                                        + " de Tacógrafo para este Vehículo, debe ser mayor a " + getVerificarKmInicial().getKmFinal() + "");
                                return;
                            }
                        }

                        getSelected().setPathTacografo("/");
                        operacionKmTacografoEJB.create(selected);
                        String path_documento = " ";
                        path_documento = uploadFotoMB.GuardarFoto(selected.getIdOperacionKmTacografo(), "op_km_tacografo", "");
                        selected.setPathTacografo(path_documento);
                        operacionKmTacografoEJB.edit(selected);
                        reset();
                        JsfUtil.addSuccessMessage(successMessage);
                        break;
                    case DELETE:
                        getSelected().setEstadoReg(1);
                        getSelected().setModificado(new Date());
                        operacionKmTacografoEJB.edit(selected);
                        reset();
                        JsfUtil.addSuccessMessage(successMessage);
                        break;
                    case UPDATE:
                        getSelected().setModificado(new Date());
                        if (!(verificacionVeh().equals("Vehículo valido"))) {
                            MovilidadUtil.addErrorMessage("El Vehículo no es valido");
                            c_coVehiculo = "";
                            return;
                        }

                        if (getSelected().getKmFinal() < getSelected().getKmInicial()) {
                            MovilidadUtil.addAdvertenciaMessage("Km Inicial debe ser menor a Km Final");
                            return;
                        }
                        verificarKmInicial();
                        if (verificarKmInicial != null) {
                            if (getVerificarKmInicial().getKmFinal() > getSelected().getKmInicial()) {
                                MovilidadUtil.addErrorMessage("Km Inicial es menor al ultimo registro"
                                        + " de Tacógrafo para este Vehículo, debe ser mayor a " + getVerificarKmInicial().getKmFinal() + "");
                                return;
                            }
                        }

                        if (uploadFotoMB.getFile() != null) {
                            String path_documentoEdit = " ";
                            path_documentoEdit = uploadFotoMB.GuardarFoto(selected.getIdOperacionKmTacografo(), "op_km_tacografo", "");
                            selected.setPathTacografo(path_documentoEdit);
                        }
                        selected.setUsername(user.getUsername());
                        operacionKmTacografoEJB.edit(selected);
                        JsfUtil.addSuccessMessage(successMessage);
                        reset();
                        PrimeFaces current = PrimeFaces.current();
                        current.executeScript("PF('OperacionKmTacografoEditDialog').hide();");
                        break;
                    default:
                        break;
                }
            } catch (EJBException ex) {
                JsfUtil.addErrorMessage("Error del sistema EJB");
            } catch (Exception ex) {
                JsfUtil.addErrorMessage("Error del sistema");
            }
        }
    }

    public OperacionKmTacografo getSelected() {
        return selected;
    }

    public void setSelected(OperacionKmTacografo selected) {
        this.selected = selected;
    }

    public OperacionKmTacografoFacadeLocal getOperacionKmTacografoEJB() {
        return operacionKmTacografoEJB;
    }

    public List<OperacionKmTacografo> getItems() {
        items = getOperacionKmTacografoEJB().findEstRegis();
        return items;
    }

    public String getC_coVehiculo() {
        return c_coVehiculo;
    }

    public void setC_coVehiculo(String c_coVehiculo) {
        this.c_coVehiculo = c_coVehiculo;
    }

    public OperacionKmTacografo getVerificarKmInicial() {
        return verificarKmInicial;
    }

    public void setVerificarKmInicial(OperacionKmTacografo verificarKmInicial) {
        this.verificarKmInicial = verificarKmInicial;
    }

}
