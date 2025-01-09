/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.GenericaJornadaTipoFacadeLocal;
import com.movilidad.ejb.GenericaTurnoFijoFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.GenericaJornadaTipo;
import com.movilidad.model.GenericaTurnoFijo;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
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
 * @author solucionesit
 */
@Named(value = "genTurnoFijoMB")
@ViewScoped
public class GenericaTurnoFijoMB implements Serializable {

    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;

    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUsrFacadeLocal;

    @EJB
    private GenericaJornadaTipoFacadeLocal tipoFacadeLocal;

    @EJB
    private GenericaTurnoFijoFacadeLocal genTurnoFijoEJB;

    /**
     * Creates a new instance of GenericaTurnoFijoMB
     */
    public GenericaTurnoFijoMB() {
    }

    private List<GenericaTurnoFijo> list;
    private GenericaTurnoFijo obj_genericaTurnoFijo;
    private List<GenericaJornadaTipo> listJornadaTipo;
    private List<Empleado> listaEmpleados;
    private ParamAreaUsr paramAreaUsr;
    private String observacion;
    private String nombreEmpleado;
    private UserExtended user;
    private Empleado empl;
    private boolean activo = true;

    private int i_idGenericaJornadaTipo;

    @PostConstruct
    public void init() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        paramAreaUsr = paramAreaUsrFacadeLocal.getByIdUser(user.getUsername());
        consultar();
    }

    public void cargarTiposJornada() {
        listJornadaTipo = tipoFacadeLocal.findAllByArea(paramAreaUsr.getIdParamArea().getIdParamArea());
    }

    public void consultar() {
        list = genTurnoFijoEJB.findByArea(paramAreaUsr.getIdParamArea().getIdParamArea());
    }

    public void preGuardar() {
        obj_genericaTurnoFijo = new GenericaTurnoFijo();
        listaEmpleados = new ArrayList<>();
        cargarTiposJornada();
    }

    public void guardar() {
        guardarTransactional();
        consultar();
    }

    public boolean validarEmpleado(int idEmpleado, int idRegistro) {
        GenericaTurnoFijo genTurnoFijoEmpleado = genTurnoFijoEJB.findTurnoByIdEmpleado(idEmpleado, idRegistro);
        if (genTurnoFijoEmpleado != null) {
            return true;
        }
        return false;
    }

    public boolean validarCampos(int idRegistre) {
        if (empl == null) {
            MovilidadUtil.addErrorMessage("Falta cargar empleado.");
            return true;
        }
        if (i_idGenericaJornadaTipo == 0) {
            MovilidadUtil.addErrorMessage("Falta cargar tipo de jornada.");
            return true;
        }
        if (validarEmpleado(empl.getIdEmpleado(), idRegistre)) {
            MovilidadUtil.addErrorMessage("Ya existe turno fijo registrado para este empleado.");
            return true;
        }

        if (observacion != null) {
            if (observacion.isEmpty()) {
                MovilidadUtil.addErrorMessage("Digite la observación");
                return true;
            }
        } else {
            MovilidadUtil.addErrorMessage("Digite la observación");
            return true;
        }
        return false;
    }

    @Transactional
    public void guardarTransactional() {

        if (validarCampos(0)) {
            return;
        }
        obj_genericaTurnoFijo.setActivo(activo ? 0 : 1);
        obj_genericaTurnoFijo.setCreado(MovilidadUtil.fechaCompletaHoy());
        obj_genericaTurnoFijo.setDescripcion(observacion);
        obj_genericaTurnoFijo.setIdEmpleado(empl);
        obj_genericaTurnoFijo.setEstadoReg(0);
        obj_genericaTurnoFijo.setIdGenericaJornadaTipo(new GenericaJornadaTipo(i_idGenericaJornadaTipo));
        obj_genericaTurnoFijo.setUsername(user.getUsername());
        genTurnoFijoEJB.create(obj_genericaTurnoFijo);
        MovilidadUtil.addSuccessMessage("Acción completado con exito.");

    }

    public void preEdit(GenericaTurnoFijo genTF) {
        listaEmpleados = new ArrayList<>();
        empl = genTF.getIdEmpleado();
        nombreEmpleado = empl.getIdentificacion() + " " + empl.getNombres() + " " + empl.getApellidos();
        observacion = genTF.getDescripcion();
        i_idGenericaJornadaTipo = genTF.getIdGenericaJornadaTipo().getIdGenericaJornadaTipo();
        obj_genericaTurnoFijo = genTF;
        activo = (obj_genericaTurnoFijo.getActivo() == null || obj_genericaTurnoFijo.getActivo() == 0) ? true : false;
        cargarTiposJornada();
    }

    public void edit() {
        editTransactional();
        consultar();
    }

    @Transactional
    public void editTransactional() {
        if (validarCampos(obj_genericaTurnoFijo.getIdGenericaTurnoFijo())) {
            return;
        }
        obj_genericaTurnoFijo.setActivo(activo ? 0 : 1);
        obj_genericaTurnoFijo.setDescripcion(observacion);
        obj_genericaTurnoFijo.setIdEmpleado(empl);
        obj_genericaTurnoFijo.setEstadoReg(0);
        obj_genericaTurnoFijo.setIdGenericaJornadaTipo(new GenericaJornadaTipo(i_idGenericaJornadaTipo));
        obj_genericaTurnoFijo.setUsername(user.getUsername());
        genTurnoFijoEJB.edit(obj_genericaTurnoFijo);
        MovilidadUtil.addSuccessMessage("Acción completado con exito.");
        PrimeFaces.current().executeScript("PF('create_edit_dialog_wv').hide()");

    }

    public void onRowDblClckSelect(final SelectEvent event) throws Exception {
        if (empl == null) {
            empl = new Empleado();
        }
        PrimeFaces current = PrimeFaces.current();
        setEmpl((Empleado) event.getObject());
        nombreEmpleado = empl.getIdentificacion() + " " + empl.getNombres() + " " + empl.getApellidos();
        MovilidadUtil.addSuccessMessage("Empleado Cargado.");

        current.executeScript("PF('empleado_list_wv').hide();");
    }

    public void findEmplActivos() {
        listaEmpleados.clear();
        if (paramAreaUsr == null) {
            MovilidadUtil.addErrorMessage("Aun no está asignado a en un área");
            return;
        }
        if (paramAreaUsr.getIdParamArea().getParamAreaCargoList() == null) {
            MovilidadUtil.addErrorMessage("Aun no están cargados los cargos en el área");
            return;
        }
        if (paramAreaUsr.getIdParamArea().getParamAreaCargoList().isEmpty()) {
            MovilidadUtil.addErrorMessage("Aun no están cargados los cargos en el área");
            return;
        }
        listaEmpleados = empleadoFacadeLocal.getEmpledosByIdArea(paramAreaUsr.getIdParamArea().getIdParamArea(), 0);
        if (listaEmpleados.isEmpty()) {
            MovilidadUtil.addErrorMessage("Aun no hay empleado activos en el area");
        } else {
            PrimeFaces.current().executeScript("PF('empleado_list_wv').show()");
            PrimeFaces.current().ajax().update("formEmpleados:tabla");
        }
    }

    public List<GenericaTurnoFijo> getList() {
        return list;
    }

    public void setList(List<GenericaTurnoFijo> list) {
        this.list = list;
    }

    public GenericaTurnoFijo getObj_genericaTurnoFijo() {
        return obj_genericaTurnoFijo;
    }

    public void setObj_genericaTurnoFijo(GenericaTurnoFijo obj_genericaTurnoFijo) {
        this.obj_genericaTurnoFijo = obj_genericaTurnoFijo;
    }

    public int getI_idGenericaJornadaTipo() {
        return i_idGenericaJornadaTipo;
    }

    public void setI_idGenericaJornadaTipo(int i_idGenericaJornadaTipo) {
        this.i_idGenericaJornadaTipo = i_idGenericaJornadaTipo;
    }

    public List<GenericaJornadaTipo> getListJornadaTipo() {
        return listJornadaTipo;
    }

    public void setListJornadaTipo(List<GenericaJornadaTipo> listJornadaTipo) {
        this.listJornadaTipo = listJornadaTipo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public Empleado getEmpl() {
        return empl;
    }

    public void setEmpl(Empleado empl) {
        this.empl = empl;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
