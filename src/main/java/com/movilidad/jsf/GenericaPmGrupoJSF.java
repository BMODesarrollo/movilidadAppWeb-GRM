/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.GenericaPmGrupoDetalleFacadeLocal;
import com.movilidad.ejb.GenericaPmGrupoFacadeLocal;
import com.movilidad.ejb.GenericaPmGrupoParamFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.GenericaPmGrupo;
import com.movilidad.model.GenericaPmGrupoDetalle;
import com.movilidad.model.GenericaPmGrupoParam;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "genPmGrupoJSF")
@ViewScoped
public class GenericaPmGrupoJSF implements Serializable {

    private GenericaPmGrupo genPmGrup;
    private List<Empleado> listMaster;
    private List<Empleado> listOperadores;
    private GenericaPmGrupoDetalle genPmGrupDet;
    private List<GenericaPmGrupoDetalle> listGenPmGrupDet;
    private List<GenericaPmGrupoDetalle> listGenPmGrupDetEliminar;
    private List<GenericaPmGrupo> listGenPmGrup;
    private Date fecha_ingreso;
    private boolean flag = false;

    private ParamAreaUsr pau;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @EJB
    private EmpleadoFacadeLocal emplEJB;
    @EJB
    private GenericaPmGrupoFacadeLocal genPmGrupEJB;
    @EJB
    private GenericaPmGrupoDetalleFacadeLocal genPmGrupDTEJB;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;
    @EJB
    private GenericaPmGrupoParamFacadeLocal genericaPmGrupoParamEjb;

    /**
     * Creates a new instance of GenericaPmGrupoJSF
     */
    public GenericaPmGrupoJSF() {
    }

    @PostConstruct
    public void init() {
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
        consultar();
    }

    public void consultar() {
        if (pau != null) {
            listGenPmGrup = genPmGrupEJB.getByIdArea(pau.getIdParamArea().getIdParamArea());
        } else {
            listGenPmGrup = new ArrayList<>();
        }
    }

    public void openDialog() {
        flag = false;
        listGenPmGrupDet = new ArrayList<>();
        genPmGrup = new GenericaPmGrupo();
        listMaster = new ArrayList<>();
        GenericaPmGrupoParam obj = genericaPmGrupoParamEjb.findByIdArea(pau.getIdParamArea().getIdParamArea());
        listOperadores = new ArrayList<>();
        listMaster = emplEJB.findEmpleadosSinGrupoGenerica(obj.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo(), 0);
        listOperadores = emplEJB.findEmpleadosSinGrupoGenerica(obj.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo(), pau.getIdParamArea().getIdParamArea());
    }

    public void editar(GenericaPmGrupo pmg) {
        flag = true;
        genPmGrup = pmg;
        GenericaPmGrupoParam obj = genericaPmGrupoParamEjb.findByIdArea(pau.getIdParamArea().getIdParamArea());
        listMaster = emplEJB.findEmpleadosSinGrupoGenerica(obj.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo(), 0);
        listOperadores = emplEJB.findEmpleadosSinGrupoGenerica(obj.getIdEmpleadoTipoCargo().getIdEmpleadoTipoCargo(), pau.getIdParamArea().getIdParamArea());
        listGenPmGrupDetEliminar = new ArrayList<>();
        listGenPmGrupDet = new ArrayList<>();
        listGenPmGrupDet.addAll(pmg.getGenericaPmGrupoDetalleList());
    }

    public void addOperador() throws Exception {
        listGenPmGrupDet.add(genPmGrupDet);
        listOperadores.remove(genPmGrupDet.getIdEmpleado());
    }

    public void preAddOperador(Empleado o) throws Exception {
        genPmGrupDet = new GenericaPmGrupoDetalle();
        genPmGrupDet.setCreado(new Date());
        genPmGrupDet.setUsername(user.getUsername());
        genPmGrupDet.setEstadoReg(0);
        genPmGrupDet.setIdEmpleado(o);
        genPmGrupDet.setFechaIngreso(o.getFechaIngreso());
        if (flag) {
            GenericaPmGrupoDetalle resp = genPmGrupDTEJB.findByIdEmpleadoAndIdGrupoPm(
                    genPmGrup.getIdGenericaPmGrupo(), o.getIdEmpleado());
            if (resp != null) {
                MovilidadUtil.addAdvertenciaMessage(
                        "Operador ya pertenece al grupo: " + resp.getIdGenericaPmGrupo().getNombreGrupo());
                genPmGrupDet = null;
                return;
            }
        } else {
            GenericaPmGrupoDetalle resp = genPmGrupDTEJB.findByIdEmpleadoAndIdGrupoPm(0, o.getIdEmpleado());
            if (resp != null) {
                MovilidadUtil.addAdvertenciaMessage(
                        "Operador ya pertenece al grupo: " + resp.getIdGenericaPmGrupo().getNombreGrupo());
                genPmGrupDet = null;
                return;
            }
        }
        if (!listGenPmGrupDet.isEmpty()) {
            for (GenericaPmGrupoDetalle pmg : listGenPmGrupDet) {
                if (pmg.getIdEmpleado().getIdEmpleado().equals(o.getIdEmpleado())) {
                    MovilidadUtil.addAdvertenciaMessage("Este operador ya se agrego al grupo");
                    genPmGrupDet = null;
                    return;
                }
            }
        } else {
            PrimeFaces.current().executeScript("PF('pmGrupoFechaIngresoDialog').show()");
            return;
        }
        PrimeFaces.current().executeScript("PF('pmGrupoFechaIngresoDialog').show()");
    }

    public int nOpe(GenericaPmGrupo pm) {
        return pm.getGenericaPmGrupoDetalleList().size();
    }

    public void delete(int index) {
        if (flag) {
            listGenPmGrupDetEliminar.add(listGenPmGrupDet.get(index));
        }
        listOperadores.add(listGenPmGrupDet.get(index).getIdEmpleado());
        listGenPmGrupDet.remove(index);
    }

    public void addMaster(Empleado m) {
        if (genPmGrup.getIdEmpleado() == null) {
            listMaster.remove(m);
        } else {
            listMaster.add(genPmGrup.getIdEmpleado());
            listMaster.remove(m);
        }
        genPmGrup.setIdEmpleado(m);
        PrimeFaces.current().ajax().update("frmGenPmgrupoCreate:genPmGrupoDescricion");
    }

    public void guardar() {
        if (flag) {
            if (genPmGrup.getIdEmpleado() == null) {
                MovilidadUtil.addErrorMessage("Se debe agregar un Operador Master al grupo");
                return;
            }
            if (listGenPmGrupDet.isEmpty()) {
                MovilidadUtil.addErrorMessage("Se debe agregar al menos un Operador al grupo");
                return;
            }
            genPmGrup.setModificado(MovilidadUtil.fechaCompletaHoy());
            genPmGrup.setGenericaPmGrupoDetalleList(new ArrayList<GenericaPmGrupoDetalle>());
            genPmGrupEJB.edit(genPmGrup);
            for (GenericaPmGrupoDetalle pmgd : listGenPmGrupDet) {
                pmgd.setIdGenericaPmGrupo(genPmGrup);
                genPmGrupDTEJB.edit(pmgd);
            }
            for (GenericaPmGrupoDetalle pmgdEli : listGenPmGrupDetEliminar) {
                genPmGrupDTEJB.remove(pmgdEli);
            }
            genPmGrup.setGenericaPmGrupoDetalleList(listGenPmGrupDet);

            MovilidadUtil.addSuccessMessage("Se ha modificado el grupo exitosamente");
            PrimeFaces.current().executeScript("PF('genPmGrupoDialog').hide()");
        } else {
            if (genPmGrup.getIdEmpleado() == null) {
                MovilidadUtil.addErrorMessage("Se debe agregar un Operador Master al grupo");
                return;
            }
            if (listGenPmGrupDet.isEmpty()) {
                MovilidadUtil.addErrorMessage("Se debe agregar al menos un Operador al grupo");
                return;
            }
            genPmGrup.setCreado(MovilidadUtil.fechaCompletaHoy());
            genPmGrup.setIdParamArea(pau.getIdParamArea());
            genPmGrup.setUsername(user.getUsername());
            genPmGrup.setEstadoReg(0);
            genPmGrupEJB.create(genPmGrup);
            listGenPmGrup.add(genPmGrup);
            for (GenericaPmGrupoDetalle pmgd : listGenPmGrupDet) {
                pmgd.setIdGenericaPmGrupo(genPmGrup);
                genPmGrupDTEJB.create(pmgd);
            }
            listGenPmGrupDet.clear();
            genPmGrup = new GenericaPmGrupo();
            MovilidadUtil.addSuccessMessage("Se ha registrado el grupo exitosamente");
        }
        consultar();
        PrimeFaces.current().ajax().update("formTablaGenPmGrupos:tblOPkM");
    }

    public GenericaPmGrupo getGenPmGrup() {
        return genPmGrup;
    }

    public void setGenPmGrup(GenericaPmGrupo genPmGrup) {
        this.genPmGrup = genPmGrup;
    }

    public List<Empleado> getListMaster() {
        return listMaster;
    }

    public void setListMaster(List<Empleado> listMaster) {
        this.listMaster = listMaster;
    }

    public List<Empleado> getListOperadores() {
        return listOperadores;
    }

    public void setListOperadores(List<Empleado> listOperadores) {
        this.listOperadores = listOperadores;
    }

    public GenericaPmGrupoDetalle getGenPmGrupDet() {
        return genPmGrupDet;
    }

    public void setGenPmGrupDet(GenericaPmGrupoDetalle genPmGrupDet) {
        this.genPmGrupDet = genPmGrupDet;
    }

    public List<GenericaPmGrupoDetalle> getListGenPmGrupDet() {
        return listGenPmGrupDet;
    }

    public void setListGenPmGrupDet(List<GenericaPmGrupoDetalle> listGenPmGrupDet) {
        this.listGenPmGrupDet = listGenPmGrupDet;
    }

    public List<GenericaPmGrupoDetalle> getListGenPmGrupDetEliminar() {
        return listGenPmGrupDetEliminar;
    }

    public void setListGenPmGrupDetEliminar(List<GenericaPmGrupoDetalle> listGenPmGrupDetEliminar) {
        this.listGenPmGrupDetEliminar = listGenPmGrupDetEliminar;
    }

    public List<GenericaPmGrupo> getListGenPmGrup() {
        return listGenPmGrup;
    }

    public void setListGenPmGrup(List<GenericaPmGrupo> listGenPmGrup) {
        this.listGenPmGrup = listGenPmGrup;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
