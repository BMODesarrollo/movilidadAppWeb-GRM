/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.GenericaFacadeLocal;
import com.movilidad.ejb.GenericaPmIeConceptosFacadeLocal;
import com.movilidad.ejb.GenericaPmIngEgrFacadeLocal;
import com.movilidad.ejb.GenericaPmTipoConceptoFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.GenericaPmIeConceptos;
import com.movilidad.model.GenericaPmIngEgr;
import com.movilidad.model.GenericaPmTipoConcepto;
import com.movilidad.model.ParamArea;
import com.movilidad.model.ParamAreaUsr;
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
@Named(value = "genericaPmIngEgrBean")
@ViewScoped
public class GenericaPmIngEgrBean implements Serializable {

    @EJB
    private GenericaPmIngEgrFacadeLocal pmIngEgrEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private GenericaPmIeConceptosFacadeLocal conceptosEjb;
    @EJB
    private GenericaPmTipoConceptoFacadeLocal tipoConceptoEjb;
    @EJB
    private GenericaFacadeLocal genericaEjb;

    private GenericaPmIngEgr genericaPmIngEgr;
    private GenericaPmIngEgr selected;
    private Empleado empleado;
    private Integer i_Concepto;
    private Integer i_idArea;

    private Date desde;
    private Date hasta;

    private boolean isEditing;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private List<GenericaPmIngEgr> lstGenericaPmIngEgresos;
    private List<GenericaPmIeConceptos> lstConceptos;
    private List<GenericaPmTipoConcepto> lstTipoConceptos;
    private List<Empleado> lstEmpleados;

    @PostConstruct
    public void init() {
        i_idArea = null;
        ParamAreaUsr paramAreaUsr = genericaEjb.findByUsername(user.getUsername());

        if (paramAreaUsr != null) {
            i_idArea = paramAreaUsr.getIdParamArea().getIdParamArea();
            desde = MovilidadUtil.fechaHoy();
            hasta = MovilidadUtil.fechaHoy();
            lstTipoConceptos = tipoConceptoEjb.findAll();
            consultar();
        }
    }

    /**
     * Prepara la lista de empleados antes de registrar/modificar un registro.
     */
    public void prepareListEmpleado() {
        if (i_idArea != null) {
            lstEmpleados = empleadoEjb.getEmpledosByIdArea(i_idArea, 0);
        } else {
            MovilidadUtil.addErrorMessage("El usuario logueado NO tiene un área relacionada");
        }
        PrimeFaces.current().ajax().update(":frmPmEmpleadoList:dtEmpleados");
        PrimeFaces.current().executeScript("PF('wVEmpleadosListDialog').clearFilters();");
    }

    /**
     * Evento que se dispara al seleccionar un empleado en el modal que muestra
     * listado
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
        if (i_idArea != null) {
            lstGenericaPmIngEgresos = pmIngEgrEjb.findAllByDateRange(desde, hasta, i_idArea);
        }
    }

    public void nuevo() {
        isEditing = false;
        genericaPmIngEgr = new GenericaPmIngEgr();
        empleado = new Empleado();
        i_Concepto = null;
        selected = null;

        lstConceptos = i_idArea != null ? conceptosEjb.findAllByEstadoRegAndArea(i_idArea) : null;
    }

    public void editar() {
        isEditing = true;
        empleado = selected.getIdEmpleado();
        i_Concepto = selected.getIdGenericaPmIeConceptos().getIdGenericaPmIeConceptos();
        genericaPmIngEgr = selected;

        lstConceptos = i_idArea != null ? conceptosEjb.findAllByEstadoRegAndArea(i_idArea) : null;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String msgValidacion = validarDatos();

        if (msgValidacion == null) {

            genericaPmIngEgr.setIdEmpleado(empleado);
            genericaPmIngEgr.setIdGenericaPmIeConceptos(new GenericaPmIeConceptos(i_Concepto));

            if (isEditing) {
                genericaPmIngEgr.setModificado(MovilidadUtil.fechaCompletaHoy());
                genericaPmIngEgr.setUsername(user.getUsername());
                pmIngEgrEjb.edit(genericaPmIngEgr);

                PrimeFaces.current().executeScript("PF('wlvGenericaPmIngEgr').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                genericaPmIngEgr.setIdParamArea(new ParamArea(i_idArea));
                genericaPmIngEgr.setEstadoReg(0);
                genericaPmIngEgr.setLiquidado(0);
                genericaPmIngEgr.setCreado(MovilidadUtil.fechaCompletaHoy());
                genericaPmIngEgr.setUsername(user.getUsername());
                pmIngEgrEjb.create(genericaPmIngEgr);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro guardado éxitosamente");
            }

            consultar();
        } else {
            MovilidadUtil.addErrorMessage(msgValidacion);
        }
    }

    private String validarDatos() {

        if (i_idArea.equals(null)) {
            return "El usuario logueado NO tiene un área asociada... Acción inválida";
        }

        if (empleado.getIdEmpleado() == null) {
            return "DEBE seleccionar un empleado";
        }

        if (i_Concepto == null) {
            return "DEBE seleccionar un concepto";
        }

        if (isEditing) {
            if (pmIngEgrEjb.verificarRegistro(selected.getIdGenericaPmIngEgr(), empleado.getIdEmpleado(), genericaPmIngEgr.getFecha(), i_Concepto, i_idArea) != null) {
                return "YA existe un registro con los parámetros indicados";
            }
        } else {
            if (!lstGenericaPmIngEgresos.isEmpty()) {
                if (pmIngEgrEjb.verificarRegistro(0, empleado.getIdEmpleado(), genericaPmIngEgr.getFecha(), i_Concepto, i_idArea) != null) {
                    return "YA existe un registro con los parámetros indicados";
                }
            }
        }

        return null;
    }

    public GenericaPmIngEgr getGenericaPmIngEgr() {
        return genericaPmIngEgr;
    }

    public void setGenericaPmIngEgr(GenericaPmIngEgr pmIngEgr) {
        this.genericaPmIngEgr = pmIngEgr;
    }

    public GenericaPmIngEgr getSelected() {
        return selected;
    }

    public void setSelected(GenericaPmIngEgr selected) {
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

    public List<GenericaPmIngEgr> getLstGenericaPmIngEgresos() {
        return lstGenericaPmIngEgresos;
    }

    public void setLstGenericaPmIngEgresos(List<GenericaPmIngEgr> lstGenericaPmIngEgresos) {
        this.lstGenericaPmIngEgresos = lstGenericaPmIngEgresos;
    }

    public List<GenericaPmIeConceptos> getLstConceptos() {
        return lstConceptos;
    }

    public void setLstConceptos(List<GenericaPmIeConceptos> lstConceptos) {
        this.lstConceptos = lstConceptos;
    }

    public List<Empleado> getLstEmpleados() {
        return lstEmpleados;
    }

    public void setLstEmpleados(List<Empleado> lstEmpleados) {
        this.lstEmpleados = lstEmpleados;
    }

    public List<GenericaPmTipoConcepto> getLstTipoConceptos() {
        return lstTipoConceptos;
    }

    public void setLstTipoConceptos(List<GenericaPmTipoConcepto> lstTipoConceptos) {
        this.lstTipoConceptos = lstTipoConceptos;
    }

    public Integer getI_idArea() {
        return i_idArea;
    }

    public void setI_idArea(Integer i_idArea) {
        this.i_idArea = i_idArea;
    }
}
