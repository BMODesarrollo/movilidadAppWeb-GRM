package com.movilidad.jsf;

import com.movilidad.ejb.GenericaNotifProcesosFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.GenericaNotifProcesos;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "genericaNotifProcesosBean")
@ViewScoped
public class GenericaNotifProcesosBean implements Serializable {

    @EJB
    private GenericaNotifProcesosFacadeLocal genericaNotifProcesossEjb;

    private GenericaNotifProcesos genericaNotifProcesos;
    private GenericaNotifProcesos selected;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private List<GenericaNotifProcesos> lstGenericaNotifProcesos;
    private List<Empleado> lstCorreos;
    private List<Empleado> selectedEmpleados;
    private List<String> selectedCorreos;
    private List<GopUnidadFuncional> lstUnidadesFuncionales;

    private String otrosCorreos = "";

    @PostConstruct
    public void init() {
        lstGenericaNotifProcesos = genericaNotifProcesossEjb.findAll(
                ConstantsUtil.OFF_INT);
    }

    public void nuevo() {
        genericaNotifProcesos = new GenericaNotifProcesos();
        selected = null;
        lstCorreos = genericaNotifProcesossEjb.getEmployeesEmail(ConstantsUtil.OFF_INT);
        selectedCorreos = new ArrayList<>();
        selectedEmpleados = new ArrayList<>();
        otrosCorreos = "";
    }

    public void editar() {
        genericaNotifProcesos = selected;
        otrosCorreos = "";
        lstCorreos = genericaNotifProcesossEjb.getEmployeesEmail(
                ConstantsUtil.OFF_INT);
        selectedCorreos = new ArrayList<>();
        selectedEmpleados = new ArrayList<>();
    }

    public void guardar() {
        if (genericaNotifProcesos.getEmails() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Debe seleccionar al menos 1 correo."));
            return;
        }
        if (!otrosCorreos.equals("")) {
            genericaNotifProcesos.setEmails(genericaNotifProcesos.getEmails() + "," + otrosCorreos);
        }
        genericaNotifProcesos.setUsername(user.getUsername());
        genericaNotifProcesos.setCreado(new Date());
        this.genericaNotifProcesossEjb.create(genericaNotifProcesos);
        lstGenericaNotifProcesos = genericaNotifProcesossEjb.findAll(ConstantsUtil.OFF_INT);
        nuevo();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Proceso de notificación guardado éxitosamente."));
    }

    public void actualizar() {
        if (genericaNotifProcesos.getEmails().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe seleccionar al menos 1 correo."));
            return;
        }
        if (!otrosCorreos.equals("")) {
            genericaNotifProcesos.setEmails(genericaNotifProcesos.getEmails() + "," + otrosCorreos);
        }
        genericaNotifProcesos.setUsername(user.getUsername());
        genericaNotifProcesos.setModificado(new Date());
        this.genericaNotifProcesossEjb.edit(genericaNotifProcesos);
        this.selectedEmpleados = null;
        lstGenericaNotifProcesos = genericaNotifProcesossEjb.findAll(ConstantsUtil.OFF_INT);
        PrimeFaces.current().executeScript("PF('genericaNotifProcesos').hide();");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Proceso de notificación actualizado éxitosamente."));
    }

    public void consultar() {
        lstGenericaNotifProcesos = genericaNotifProcesossEjb.findAll(
                ConstantsUtil.OFF_INT);
    }

    public void cargarEmails() {
        MovilidadUtil.clearFilter("wdtCorreos");
        if (selected != null) {
            selectedCorreos = Arrays.asList(genericaNotifProcesos.getEmails().replaceAll("\\s", "").split(","));
            for (String correo : selectedCorreos) {
                Empleado emp = genericaNotifProcesossEjb.findEmpleadoByEmail(correo);
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
        genericaNotifProcesos.setEmails(String.valueOf(correos).replace("[", "").replace("]", "").replaceAll("\\s", ""));
        limpiarEmails();
        current.executeScript("PF('CorreosListDialog').hide();");
    }
    
    public GenericaNotifProcesos getGenericaNotifProcesos() {
        return genericaNotifProcesos;
    }

    public void setGenericaNotifProcesos(GenericaNotifProcesos genericaNotifProcesos) {
        this.genericaNotifProcesos = genericaNotifProcesos;
    }

    public GenericaNotifProcesos getSelected() {
        return selected;
    }

    public void setSelected(GenericaNotifProcesos selected) {
        this.selected = selected;
    }

    public List<GenericaNotifProcesos> getLstGenericaNotifProcesos() {
        return lstGenericaNotifProcesos;
    }

    public void setLstGenericaNotifProcesos(List<GenericaNotifProcesos> lstGenericaNotifProcesos) {
        this.lstGenericaNotifProcesos = lstGenericaNotifProcesos;
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

    public GenericaNotifProcesosFacadeLocal getGenericaNotifProcesosEjb() {
        return genericaNotifProcesossEjb;
    }

    public void setGenericaNotifProcesosEjb(GenericaNotifProcesosFacadeLocal genericaNotifProcesossEjb) {
        this.genericaNotifProcesossEjb = genericaNotifProcesossEjb;
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

    public List<GopUnidadFuncional> getLstUnidadesFuncionales() {
        return lstUnidadesFuncionales;
    }

    public void setLstUnidadesFuncionales(List<GopUnidadFuncional> lstUnidadesFuncionales) {
        this.lstUnidadesFuncionales = lstUnidadesFuncionales;
    }

}
