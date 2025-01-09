/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Soluciones IT
 */
@Named(value = "emplListJSFMB")
@ViewScoped
public class EmpleadoListJSFManagedBean implements Serializable {

    @EJB
    private EmpleadoFacadeLocal emplEJB;

    @Inject
    private EmpleadoDocumentoJSFManagedBean emplDocMB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private PlaRecuMedicinaBean medicinaBean;
    @Inject
    private PlaRecuSeguridadBean seguridadBean;
    @Inject
    private PlaRecuBienestarBean bienestarBean;
    private List<Empleado> listEmpls;
    private String form;
    private Empleado empleado;
    private Empleado empl;

    private String modulo;
    private String codigoTransMi;

    /**
     * Creates a new instance of EmpleadoListJSFManagedBean
     */
    public EmpleadoListJSFManagedBean() {

    }

    @PostConstruct
    public void init() {
    }

    public void openModalEmpleadoList(String compUpdate) {
        listarEmpl();
        setForm(compUpdate);
        setModulo(ConstantsUtil.EMPlCONSULTA);
        MovilidadUtil.openModal("dialog_empl_wv");
        MovilidadUtil.clearFilter("dialog_empl_dt_wv");
    }

    public void emplFindByCodigoT() throws Exception {
        if (MovilidadUtil.stringIsEmpty(codigoTransMi)) {
            MovilidadUtil.addAdvertenciaMessage("Digite el codigo del operador");
            return;
        }
        int convertStringToInt = MovilidadUtil.convertStringToInt(codigoTransMi);
        empl = emplEJB.findbyCodigoTmAndIdGopUnidadFuncActivo(convertStringToInt,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (empl == null) {
            MovilidadUtil.addErrorMessage("No existe empleado con el codigo digitado");
            return;
        }
        codigoTransMi = null;
        MovilidadUtil.updateComponent("formConsultaEmpl");
    }

    public void onRowDblClckSelect(final SelectEvent event) throws Exception {
        if (modulo.equals(ConstantsUtil.EMPlDOC)) {
            emplDocMB.setEmpleado((Empleado) event.getObject());
            emplDocMB.cargarTiposCargos();
        }
        if (modulo.equals(ConstantsUtil.EMPlCONSULTA)) {
            empl = (Empleado) event.getObject();
            MovilidadUtil.updateComponent(form);
        }
        if (modulo.equals(ConstantsUtil.EMPLSELECTNOV)) {
        }
        MovilidadUtil.addSuccessMessage("Empleado seleccionado.");
    }

    public boolean cumple() {
        if (empl != null && empl.getFechaNcto() != null) {
            return MovilidadUtil.cumple(empl.getFechaNcto());
        }
        return false;
    }

    public String master() {
        String master = "";
        try {
            if (empl != null) {
                Empleado empleado = empl.getPmGrupoDetalleList().get(0).getIdGrupo().getIdEmpleado();
                master = empl.getPmGrupoDetalleList().get(0).getIdGrupo().getNombreGrupo() + " - " + empleado.getNombres() + " " + empleado.getApellidos() + "-" + empleado.getCodigoTm();
            }
        } catch (Exception e) {
            if (empl.getPmGrupoList() != null) {
                if (empl.getPmGrupoList().size() > 0) {
                    return empl.getPmGrupoList().get(0).getDescripcion();
                }

            }
            return "N/A";
        }

        return master;
    }

    public void listarEmpl() {
        listEmpls = emplEJB.findEmpleadosByIdGopUnidadFuncional(
                SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_CARGOS_PANEL_PRINCIPAL),
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public String obtenerRestriccionMedicinaLaboral() {
        return medicinaBean.obtenerRestriccion(empl.getIdEmpleado());
    }
    
    public String obtenerRestriccionSeguridad() {
        return seguridadBean.obtenerRestriccion(empl.getIdEmpleado());
    }
    
    public String obtenerRestriccionBienestar() {
        return bienestarBean.obtenerRestriccion(empl.getIdEmpleado());
    }
    
    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<Empleado> getListEmpls() {
        return listEmpls;
    }

    public void setListEmpls(List<Empleado> listEmpls) {
        this.listEmpls = listEmpls;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public EmpleadoFacadeLocal getEmplEJB() {
        return emplEJB;
    }

    public void setEmplEJB(EmpleadoFacadeLocal emplEJB) {
        this.emplEJB = emplEJB;
    }

    public EmpleadoDocumentoJSFManagedBean getEmplDocMB() {
        return emplDocMB;
    }

    public void setEmplDocMB(EmpleadoDocumentoJSFManagedBean emplDocMB) {
        this.emplDocMB = emplDocMB;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getCodigoTransMi() {
        return codigoTransMi;
    }

    public void setCodigoTransMi(String codigoTransMi) {
        this.codigoTransMi = codigoTransMi;
    }

    public Empleado getEmpl() {
        return empl;
    }

    public void setEmpl(Empleado empl) {
        this.empl = empl;
    }

}
