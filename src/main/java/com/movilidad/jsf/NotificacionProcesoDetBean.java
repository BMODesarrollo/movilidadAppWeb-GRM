package com.movilidad.jsf;

import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.ejb.NotificacionProcesoDetFacadeLocal;
import com.movilidad.ejb.NotificacionProcesosFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.NotificacionProcesoDet;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "notificacionProcesoDetBean")
@ViewScoped
public class NotificacionProcesoDetBean implements Serializable {

    @EJB
    private NotificacionProcesoDetFacadeLocal notificacionProcesoDetEjb;
    @EJB
    private NotificacionProcesosFacadeLocal notificacionProcesosEjb;
    @EJB
    private GopUnidadFuncionalFacadeLocal unidadFuncionalEjb;

    @Inject
    private NotificacionProcesosJSFManagedBean notificacionProcesosBean;

    private NotificacionProcesoDet notificacionProcesoDet;
    private NotificacionProcesoDet selected;

    private Integer idGopUnidadFuncional;
    private String correos;

    private List<NotificacionProcesoDet> lstDetalles;
    private List<Empleado> lstCorreos;
    private List<Empleado> selectedEmpleados;
    private List<String> selectedCorreos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private boolean isEditingDetalle;

    public void cargarEmails() {

        if (idGopUnidadFuncional == null) {
            MovilidadUtil.addAdvertenciaMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        lstCorreos = notificacionProcesosEjb.getEmployeesEmail(idGopUnidadFuncional);
        
        if (isEditingDetalle) {
            cargarEmailsSeleccionados();
        }
        
        MovilidadUtil.openModal("CorreosListDialogUf");
    }

    public void asignarEmails() {
        Set<String> emails;
        emails = new HashSet<>();

        selectedEmpleados.forEach(e -> {
            emails.add(e.getEmailCorporativo());
        });
        if (emails.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Debe seleccionar al menos 1 correo."));
            return;
        }
        notificacionProcesoDet.setEmails(String.valueOf(emails).replace("[", "").replace("]", "").replaceAll("\\s", ""));
        limpiarEmails();
        MovilidadUtil.hideModal("CorreosListDialogUf");
    }

    public void agregarDetalle() {

        String validacion = validarDatos();

        if (validacion == null) {

            if (validarEnLista()) {
                MovilidadUtil.addAdvertenciaMessage("Ya existe un registro con la unidad funcional a agregar");
                return;
            }

            notificacionProcesoDet.setIdNotificacionProceso(notificacionProcesosBean.getNotificacionProceso());
            notificacionProcesoDet.setUsername(user.getUsername());
            notificacionProcesoDet.setEstadoReg(0);
            notificacionProcesoDet.setCreado(MovilidadUtil.fechaCompletaHoy());
            notificacionProcesoDet.setIdGopUnidadFuncional(unidadFuncionalEjb.find(idGopUnidadFuncional));
            lstDetalles.add(notificacionProcesoDet);

            MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
            limpiarCampos();
        } else {
            MovilidadUtil.addAdvertenciaMessage(validacion);
        }

    }

    public void editar() {
        isEditingDetalle = true;
        selectedCorreos = new ArrayList<>();
        selectedEmpleados = new ArrayList<>();
        notificacionProcesoDet = selected;
        idGopUnidadFuncional = selected.getIdGopUnidadFuncional().getIdGopUnidadFuncional();
    }

    public void actualizarDetalle() {

        String validacion = validarDatos();

        if (validacion == null) {

            notificacionProcesoDet.setIdNotificacionProceso(notificacionProcesosBean.getNotificacionProceso());
            notificacionProcesoDet.setUsername(user.getUsername());
            notificacionProcesoDet.setModificado(MovilidadUtil.fechaCompletaHoy());
            notificacionProcesoDet.setIdGopUnidadFuncional(unidadFuncionalEjb.find(idGopUnidadFuncional));

            MovilidadUtil.addSuccessMessage("Detalle actualizado con éxito");
            limpiarCampos();
        } else {
            MovilidadUtil.addAdvertenciaMessage(validacion);
        }
    }

    public void eliminarRegistro() {

        if (selected.getIdNotificacionProcesoDet() != null) {
            selected.setEstadoReg(1);
            notificacionProcesoDetEjb.edit(selected);
            lstDetalles.remove(selected);
        } else {
            lstDetalles.remove(selected);
        }

        MovilidadUtil.addSuccessMessage("Registro eliminado éxitosamente");
    }

    void limpiarCampos() {
        notificacionProcesoDet = new NotificacionProcesoDet();
        lstCorreos = new ArrayList<>();
        idGopUnidadFuncional = null;
        selected = null;
        isEditingDetalle = false;
    }
    
    private void cargarEmailsSeleccionados() {
        MovilidadUtil.clearFilter("wdtCorreosUf");
        if (selected != null) {
            selectedCorreos = Arrays.asList(notificacionProcesoDet.getEmails().replaceAll("\\s", "").split(","));
            for (String correo : selectedCorreos) {
                Empleado emp = notificacionProcesosEjb.findEmpleadoByEmail(correo);
                if (emp != null) {
                    selectedEmpleados.add(emp);
                }
            }
        } else {
            limpiarEmails();
        }
    }

    private boolean validarEnLista() {

        if (lstDetalles == null || lstDetalles.isEmpty()) {
            return false;
        }

        Integer idGopUF = isEditingDetalle ? selected.getIdGopUnidadFuncional().getIdGopUnidadFuncional() : idGopUnidadFuncional;

        for (NotificacionProcesoDet x : lstDetalles) {
            if (x.getIdGopUnidadFuncional().getIdGopUnidadFuncional().equals(idGopUF)) {
                return true;
            }
        }

        return false;

    }

    private void limpiarEmails() {
        selectedEmpleados = null;
    }

    private String validarDatos() {

        if (idGopUnidadFuncional == null) {
            return "DEBE seleccionar una unidad funcional";
        }

        if (notificacionProcesoDet.getEmails() == null) {
            return "DEBE seleccionar al menos un email";
        }

        return null;
    }

    public NotificacionProcesoDet getNotificacionProcesoDet() {
        return notificacionProcesoDet;
    }

    public void setNotificacionProcesoDet(NotificacionProcesoDet notificacionProcesoDet) {
        this.notificacionProcesoDet = notificacionProcesoDet;
    }

    public NotificacionProcesoDet getSelected() {
        return selected;
    }

    public void setSelected(NotificacionProcesoDet selected) {
        this.selected = selected;
    }

    public Integer getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(Integer idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    public String getCorreos() {
        return correos;
    }

    public void setCorreos(String correos) {
        this.correos = correos;
    }

    public List<NotificacionProcesoDet> getLstDetalles() {
        return lstDetalles;
    }

    public void setLstDetalles(List<NotificacionProcesoDet> lstDetalles) {
        this.lstDetalles = lstDetalles;
    }

    public List<Empleado> getLstCorreos() {
        return lstCorreos;
    }

    public void setLstCorreos(List<Empleado> lstCorreos) {
        this.lstCorreos = lstCorreos;
    }

    public List<Empleado> getSelectedEmpleados() {
        return selectedEmpleados;
    }

    public void setSelectedEmpleados(List<Empleado> selectedEmpleados) {
        this.selectedEmpleados = selectedEmpleados;
    }

    public List<String> getSelectedCorreos() {
        return selectedCorreos;
    }

    public void setSelectedCorreos(List<String> selectedCorreos) {
        this.selectedCorreos = selectedCorreos;
    }

    public boolean isIsEditingDetalle() {
        return isEditingDetalle;
    }

    public void setIsEditingDetalle(boolean isEditingDetalle) {
        this.isEditingDetalle = isEditingDetalle;
    }

}
