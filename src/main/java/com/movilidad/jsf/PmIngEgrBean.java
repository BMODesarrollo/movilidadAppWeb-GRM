/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.PmIeConceptosFacadeLocal;
import com.movilidad.ejb.PmIngEgrFacadeLocal;
import com.movilidad.ejb.PmTipoConceptoFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.PmIeConceptos;
import com.movilidad.model.PmIngEgr;
import com.movilidad.model.PmTipoConcepto;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "pmIngEgrBean")
@ViewScoped
public class PmIngEgrBean implements Serializable {

    @EJB
    private PmIngEgrFacadeLocal pmIngEgrEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private PmIeConceptosFacadeLocal conceptosEjb;
    @EJB
    private PmTipoConceptoFacadeLocal tipoConceptoEjb;

    private PmIngEgr pmIngEgr;
    private PmIngEgr selected;
    private Empleado empleado;
    private Integer i_Concepto;

    private Date desde;
    private Date hasta;

    private boolean isEditing;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private List<PmIngEgr> lstPmIngEgresos;
    private List<PmIeConceptos> lstConceptos;
    private List<PmTipoConcepto> lstTipoConceptos;
    private List<Empleado> lstEmpleados;

    @PostConstruct
    public void init() {
        desde = MovilidadUtil.fechaHoy();
        hasta = MovilidadUtil.fechaHoy();
        lstTipoConceptos = tipoConceptoEjb.findAll();
        consultar();
    }

    /**
     * Prepara la lista de empleados antes de registrar/modificar un registro.
     */
    public void prepareListEmpleado() {
        lstEmpleados = empleadoEjb.findAllEmpleadosActivos(0);
        PrimeFaces.current().ajax().update(":frmPmEmpleadoList:dtEmpleados");
        PrimeFaces.current().executeScript("PF('wVEmpleadosListDialog').clearFilters();");
    }

    /**
     * Evento que se dispara al seleccionar un empleado en el modal que
     * muestra listado
     *
     * @param event
     */
    public void onRowEmpleadoClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof Empleado) {
            setEmpleado((Empleado) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wVEmpleadosListDialog').clearFilters();");
    }

    public void consultar() {
        lstPmIngEgresos = pmIngEgrEjb.findAllByDateRange(desde, hasta);
    }

    public void nuevo() {
        isEditing = false;
        pmIngEgr = new PmIngEgr();
        empleado = new Empleado();
        i_Concepto = null;
        selected = null;

        lstConceptos = conceptosEjb.findAllByEstadoReg();
    }

    public void editar() {
        isEditing = true;
        empleado = selected.getIdEmpleado();
        i_Concepto = selected.getIdPmIeConceptos().getIdPmIeConceptos();
        pmIngEgr = selected;

        lstConceptos = conceptosEjb.findAllByEstadoReg();
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String msgValidacion = validarDatos();

        if (msgValidacion == null) {

            pmIngEgr.setIdEmpleado(empleado);
            pmIngEgr.setIdPmIeConceptos(new PmIeConceptos(i_Concepto));

            if (isEditing) {
                pmIngEgr.setModificado(MovilidadUtil.fechaCompletaHoy());
                pmIngEgr.setUsername(user.getUsername());
                pmIngEgrEjb.edit(pmIngEgr);

                PrimeFaces.current().executeScript("PF('wlvPmIngEgr').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                pmIngEgr.setEstadoReg(0);
                pmIngEgr.setLiquidado(0);
                pmIngEgr.setCreado(MovilidadUtil.fechaCompletaHoy());
                pmIngEgr.setUsername(user.getUsername());
                pmIngEgrEjb.create(pmIngEgr);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro guardado éxitosamente");
            }

            consultar();
        } else {
            MovilidadUtil.addErrorMessage(msgValidacion);
        }
    }

    private String validarDatos() {

        if (empleado.getIdEmpleado() == null) {
            return "DEBE seleccionar un empleado";
        }

        if (i_Concepto == null) {
            return "DEBE seleccionar un concepto";
        }

        if (isEditing) {
            if (pmIngEgrEjb.verificarRegistro(selected.getIdPmIngEgr(), empleado.getIdEmpleado(), pmIngEgr.getFecha(), i_Concepto) != null) {
                return "YA existe un registro con los parámetros indicados";
            }
        } else {
            if (!lstPmIngEgresos.isEmpty()) {
                if (pmIngEgrEjb.verificarRegistro(0, empleado.getIdEmpleado(), pmIngEgr.getFecha(), i_Concepto) != null) {
                    return "YA existe un registro con los parámetros indicados";
                }
            }
        }

        return null;
    }

    public PmIngEgr getPmIngEgr() {
        return pmIngEgr;
    }

    public void setPmIngEgr(PmIngEgr pmIngEgr) {
        this.pmIngEgr = pmIngEgr;
    }

    public PmIngEgr getSelected() {
        return selected;
    }

    public void setSelected(PmIngEgr selected) {
        this.selected = selected;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Integer getI_Concepto() {
        return i_Concepto;
    }

    public void setI_Concepto(Integer i_Concepto) {
        this.i_Concepto = i_Concepto;
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

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<PmIngEgr> getLstPmIngEgresos() {
        return lstPmIngEgresos;
    }

    public void setLstPmIngEgresos(List<PmIngEgr> lstPmIngEgresos) {
        this.lstPmIngEgresos = lstPmIngEgresos;
    }

    public List<PmIeConceptos> getLstConceptos() {
        return lstConceptos;
    }

    public void setLstConceptos(List<PmIeConceptos> lstConceptos) {
        this.lstConceptos = lstConceptos;
    }

    public List<Empleado> getLstEmpleados() {
        return lstEmpleados;
    }

    public void setLstEmpleados(List<Empleado> lstEmpleados) {
        this.lstEmpleados = lstEmpleados;
    }

    public List<PmTipoConcepto> getLstTipoConceptos() {
        return lstTipoConceptos;
    }

    public void setLstTipoConceptos(List<PmTipoConcepto> lstTipoConceptos) {
        this.lstTipoConceptos = lstTipoConceptos;
    }

}
