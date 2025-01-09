package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "selectListaEmpleadoBean")
@ViewScoped
public class SelectEmpleadoBean implements Serializable {

    @EJB
    private EmpleadoFacadeLocal emplEJB;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<Empleado> listEmpls;

    private Integer idEmpleado;
    private Integer idParamArea;

    public void cargarListadoEmpleados() {
        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        listEmpls = emplEJB.findEmpleadosByIdGopUnidadFuncional(
                SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_CARGOS_OPERADORES),
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (listEmpls == null || listEmpls.isEmpty()) {
            MovilidadUtil.addErrorMessage("NO se encontraron empleados con la unidad funcional seleccionada");
        }

    }

    public void cargarListadoEmpleadosGenerica() {

        if (idParamArea == null) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar un área");
        }
        
        listEmpls = emplEJB.getEmpledosByIdArea(idParamArea, ConstantsUtil.OFF_INT);

        if (listEmpls == null || listEmpls.isEmpty()) {
            MovilidadUtil.addErrorMessage("NO se encontraron empleados");
        }

    }

    public List<Empleado> getListEmpls() {
        return listEmpls;
    }

    public void setListEmpls(List<Empleado> listEmpls) {
        this.listEmpls = listEmpls;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Integer getIdParamArea() {
        return idParamArea;
    }

    public void setIdParamArea(Integer idParamArea) {
        this.idParamArea = idParamArea;
    }

}
