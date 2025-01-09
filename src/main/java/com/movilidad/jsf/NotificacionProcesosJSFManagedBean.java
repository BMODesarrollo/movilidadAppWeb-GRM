package com.movilidad.jsf;

import com.movilidad.ejb.NotificacionProcesoDetFacadeLocal;
import com.movilidad.ejb.NotificacionProcesosFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.NotificacionProcesoDet;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.ToggleEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "notificacionProcesosBean")
@ViewScoped
public class NotificacionProcesosJSFManagedBean implements Serializable {

    @EJB
    private NotificacionProcesosFacadeLocal notificacionProcesosEjb;
    @EJB
    private NotificacionProcesoDetFacadeLocal notificacionProcesoDetEjb;

    @Inject
    private NotificacionProcesoDetBean notificacionProcesosDetBean;

    private NotificacionProcesos notificacionProceso;
    private NotificacionProcesos selected;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private List<NotificacionProcesos> lstNotificacionProcesos;
    private List<Empleado> lstCorreos;
    private List<Empleado> selectedEmpleados;
    private List<String> selectedCorreos;

    private String otrosCorreos = "";

    @PostConstruct
    public void init() {
        lstNotificacionProcesos = notificacionProcesosEjb.findAll(ConstantsUtil.OFF_INT);
    }

    public void nuevo() {
        notificacionProceso = new NotificacionProcesos();
        selected = null;
        lstCorreos = notificacionProcesosEjb.getEmployeesEmail(ConstantsUtil.OFF_INT);
        notificacionProcesosDetBean.setLstDetalles(new ArrayList<>());
        notificacionProcesosDetBean.setIsEditingDetalle(false);
        notificacionProcesosDetBean.limpiarCampos();
        selectedCorreos = new ArrayList<>();
        selectedEmpleados = new ArrayList<>();
        otrosCorreos = "";
    }

    public void editar() {
        notificacionProceso = selected;
        otrosCorreos = "";
        notificacionProcesosDetBean.setLstDetalles(notificacionProceso.getNotificacionProcesoDetList());
        notificacionProcesosDetBean.limpiarCampos();
        notificacionProcesosDetBean.setIsEditingDetalle(false);
        lstCorreos = notificacionProcesosEjb.getEmployeesEmail(ConstantsUtil.OFF_INT);
        selectedCorreos = new ArrayList<>();
        selectedEmpleados = new ArrayList<>();
    }

    public void guardar() {
        if (notificacionProceso.getEmails() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Debe seleccionar al menos 1 correo."));
            return;
        }
        if (!otrosCorreos.equals("")) {
            notificacionProceso.setEmails(notificacionProceso.getEmails() + "," + otrosCorreos);
        }
        notificacionProceso.setUsername(user.getUsername());
        notificacionProceso.setCreado(MovilidadUtil.fechaCompletaHoy());
        notificacionProceso.setEstadoReg(0);

        if ((notificacionProcesosDetBean.getLstDetalles() != null && !notificacionProcesosDetBean.getLstDetalles().isEmpty())) {
            notificacionProceso.setNotificacionProcesoDetList(notificacionProcesosDetBean.getLstDetalles());
        }

        this.notificacionProcesosEjb.create(notificacionProceso);
        lstNotificacionProcesos = notificacionProcesosEjb.findAll(ConstantsUtil.OFF_INT);
        nuevo();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Proceso de notificación guardado éxitosamente."));
    }

    public void actualizar() {
        if (notificacionProceso.getEmails().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe seleccionar al menos 1 correo."));
            return;
        }
        if (!otrosCorreos.equals("")) {
            notificacionProceso.setEmails(notificacionProceso.getEmails() + "," + otrosCorreos);
        }

        notificacionProceso.setUsername(user.getUsername());
        notificacionProceso.setModificado(MovilidadUtil.fechaCompletaHoy());

        if ((notificacionProcesosDetBean.getLstDetalles() != null && !notificacionProcesosDetBean.getLstDetalles().isEmpty())) {
            notificacionProceso.setNotificacionProcesoDetList(notificacionProcesosDetBean.getLstDetalles());
        }

        this.notificacionProcesosEjb.edit(notificacionProceso);
        this.selectedEmpleados = null;
        lstNotificacionProcesos = notificacionProcesosEjb.findAll(ConstantsUtil.OFF_INT);
        PrimeFaces.current().executeScript("PF('notificacionProceso').hide();");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Proceso de notificación actualizado éxitosamente."));
    }

    public void consultar() {
        lstNotificacionProcesos = notificacionProcesosEjb.findAll(ConstantsUtil.OFF_INT);
    }

    public void cargarEmails() {
        MovilidadUtil.clearFilter("wdtCorreos");
        if (selected != null) {
            selectedCorreos = Arrays.asList(notificacionProceso.getEmails().replaceAll("\\s", "").split(","));
            for (String correo : selectedCorreos) {
                Empleado emp = notificacionProcesosEjb.findEmpleadoByEmail(correo);
                if (emp != null) {
                    selectedEmpleados.add(emp);
                } else {
                    otrosCorreos += correo + ",";
                }
            }
        } else {
            limpiarEmails();
        }
    }

    public void limpiarEmails() {
        selectedEmpleados = null;
    }

    public void asignarEmails() {
        PrimeFaces current = PrimeFaces.current();
        Set<String> correos;
        correos = new HashSet<>();

        for (Empleado e : selectedEmpleados) {
            correos.add(e.getEmailCorporativo());
        }
        if (correos.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Debe seleccionar al menos 1 correo."));
            return;
        }
        notificacionProceso.setEmails(String.valueOf(correos).replace("[", "").replace("]", "").replaceAll("\\s", ""));
        limpiarEmails();
        current.executeScript("PF('CorreosListDialog').hide();");
    }

    public void onRowToggle(ToggleEvent event) {
        NotificacionProcesos obj = (NotificacionProcesos) event.getData();

        List<NotificacionProcesoDet> lstDetalles = notificacionProcesoDetEjb.findAllByIdNotificacionProceso(obj.getIdNotificacionProceso());

        obj.setNotificacionProcesoDetList(lstDetalles);
    }

    public NotificacionProcesos getNotificacionProceso() {
        return notificacionProceso;
    }

    public void setNotificacionProceso(NotificacionProcesos notificacionProceso) {
        this.notificacionProceso = notificacionProceso;
    }

    public NotificacionProcesos getSelected() {
        return selected;
    }

    public void setSelected(NotificacionProcesos selected) {
        this.selected = selected;
    }

    public List<NotificacionProcesos> getLstNotificacionProcesos() {
        return lstNotificacionProcesos;
    }

    public void setLstNotificacionProcesos(List<NotificacionProcesos> lstNotificacionProcesos) {
        this.lstNotificacionProcesos = lstNotificacionProcesos;
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

    public NotificacionProcesosFacadeLocal getNotificacionProcesosEjb() {
        return notificacionProcesosEjb;
    }

    public void setNotificacionProcesosEjb(NotificacionProcesosFacadeLocal notificacionProcesosEjb) {
        this.notificacionProcesosEjb = notificacionProcesosEjb;
    }

    public List<String> getSelectedCorreos() {
        return selectedCorreos;
    }

    public void setSelectedCorreos(List<String> selectedCorreos) {
        this.selectedCorreos = selectedCorreos;
    }

    public String getOtrosCorreos() {
        return otrosCorreos;
    }

    public void setOtrosCorreos(String otrosCorreos) {
        this.otrosCorreos = otrosCorreos;
    }

}
