/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PmPeriodosLiquidacionFacadeLocal;
import com.movilidad.model.PmPeriodosLiquidacion;
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
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Julián Arévalo
 */
@Named(value = "pmPeriodosLiquidacionJSFMB")
@ViewScoped
public class PmPeriodosLiquidacionJSFMB implements Serializable {

    /**
     * Creates a new instance of PmTipoDetalleIncluirJSFMB
     */
    public PmPeriodosLiquidacionJSFMB() {
    }
    
    @EJB
    private PmPeriodosLiquidacionFacadeLocal PmPeriodosLiquidacionFacadeLocal;

    private PmPeriodosLiquidacion periodoLiquidacion;
    private List<PmPeriodosLiquidacion> list;
    private Date fecha_inicio;
    private Date fecha_fin;
    private String descripcion;
    private Boolean activo;
    private int idPmPeriodoLiquidacion;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    public void consultar() {
        idPmPeriodoLiquidacion = 0;
        list = PmPeriodosLiquidacionFacadeLocal.getAllActivo();
    }

    public void activarDesactivar(PmPeriodosLiquidacion obj, int opc) {
        if (opc == 0) {
            obj.setActivo(0);
        } else {
            obj.setActivo(1);
        }
        obj.setModificado(MovilidadUtil.fechaCompletaHoy());
        PmPeriodosLiquidacionFacadeLocal.edit(obj);
        MovilidadUtil.addSuccessMessage("Acción completada exitosamente.");
        consultar();
    }

    public void guardar() {
        guardarTransactional();
        consultar();
    }

    public void editar() {
        periodoLiquidacion = PmPeriodosLiquidacionFacadeLocal.find(idPmPeriodoLiquidacion);
        editarTransactional(periodoLiquidacion);
        consultar();
    }

    @Transactional
    public void guardarTransactional() {

        periodoLiquidacion = new PmPeriodosLiquidacion();

        periodoLiquidacion.setActivo(1);
        periodoLiquidacion.setFechaInicio(fecha_inicio);
        periodoLiquidacion.setFechaFin(fecha_fin);
        periodoLiquidacion.setDescripcion(descripcion);
        periodoLiquidacion.setUsername(user.getUsername());
        periodoLiquidacion.setEstadoReg(0);
        periodoLiquidacion.setCreado(MovilidadUtil.fechaCompletaHoy());

        PmPeriodosLiquidacionFacadeLocal.create(periodoLiquidacion);
        descripcion = "";
        fecha_inicio = new Date();
        fecha_fin = new Date();
        periodoLiquidacion = null;
        MovilidadUtil.addSuccessMessage("Se guardó el registro exitosamente.");
        MovilidadUtil.hideModal("wv_create_dlg");
    }

    @Transactional
    public void editarTransactional(PmPeriodosLiquidacion obj) {
        periodoLiquidacion.setIdPmPeriodoLiquidacion(obj.getIdPmPeriodoLiquidacion());
        periodoLiquidacion.setActivo(activo ? 1 : 0);
        periodoLiquidacion.setFechaInicio(fecha_inicio);
        periodoLiquidacion.setFechaFin(fecha_fin);
        periodoLiquidacion.setDescripcion(descripcion);
        periodoLiquidacion.setUsername(user.getUsername());
        periodoLiquidacion.setEstadoReg(0);
        periodoLiquidacion.setModificado(MovilidadUtil.fechaCompletaHoy());

        PmPeriodosLiquidacionFacadeLocal.edit(periodoLiquidacion);
        MovilidadUtil.addSuccessMessage("Se actualizó el registro exitosamente.");
        MovilidadUtil.hideModal("wv_create_dlg");

    }

    public List<PmPeriodosLiquidacion> getList() {
        return list;
    }

    public void setList(List<PmPeriodosLiquidacion> list) {
        this.list = list;
    }

    public PmPeriodosLiquidacionFacadeLocal getPmPeriodosLiquidacionFacadeLocal() {
        return PmPeriodosLiquidacionFacadeLocal;
    }

    public void setPmPeriodosLiquidacionFacadeLocal(PmPeriodosLiquidacionFacadeLocal PmPeriodosLiquidacionFacadeLocal) {
        this.PmPeriodosLiquidacionFacadeLocal = PmPeriodosLiquidacionFacadeLocal;
    }

    public PmPeriodosLiquidacion getPeriodoLiquidacion() {
        return periodoLiquidacion;
    }

    public void setPeriodoLiquidacion(PmPeriodosLiquidacion periodoLiquidacion) {
        this.periodoLiquidacion = periodoLiquidacion;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public int getIdPmPeriodoLiquidacion() {
        return idPmPeriodoLiquidacion;
    }

    public void setIdPmPeriodoLiquidacion(int idPmPeriodoLiquidacion) {
        this.idPmPeriodoLiquidacion = idPmPeriodoLiquidacion;
    }

}
