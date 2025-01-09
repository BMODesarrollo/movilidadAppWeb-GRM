/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AtvPrestadorFacadeLocal;
import com.movilidad.model.AtvPrestador;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "atvPrestadorBean")
@ViewScoped
public class AtvPrestadorBean implements Serializable {

    @EJB
    private AtvPrestadorFacadeLocal atvPrestadorEJB;

    private AtvPrestador atvPrestador;
    private AtvPrestador selected;
    private String nombre;
    private String correo;
    private String descripcion;
    private boolean activo;
    private Date desde;
    private Date hasta;

    private boolean isEditing;

    private List<AtvPrestador> lstAtvPrestadores;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    private void consultar() {
        lstAtvPrestadores = atvPrestadorEJB.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        descripcion = "";
        desde = MovilidadUtil.fechaHoy();
        hasta = MovilidadUtil.fechaHoy();
        activo = false;
        atvPrestador = new AtvPrestador();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        descripcion = selected.getDescripcion();
        atvPrestador = selected;
        activo = selected.getActivo() == 1;
        desde = selected.getDesde();
        hasta = selected.getHasta();
        correo = selected.getCorreo();
        isEditing = true;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        if (validarDatos()) {
            return;
        }
        atvPrestador.setNombre(nombre);
        atvPrestador.setCorreo(correo);
        atvPrestador.setDescripcion(descripcion);
        atvPrestador.setActivo(activo ? 1 : 0);
        atvPrestador.setUsername(user.getUsername());
        atvPrestador.setDesde(desde);
        atvPrestador.setHasta(hasta);
        if (isEditing) {
            atvPrestador.setModificado(MovilidadUtil.fechaCompletaHoy());
            atvPrestadorEJB.edit(atvPrestador);
            MovilidadUtil.hideModal("wv_atv_prestador");
            MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
        } else {
            atvPrestador.setEstadoReg(0);
            atvPrestador.setCreado(MovilidadUtil.fechaCompletaHoy());
            atvPrestadorEJB.create(atvPrestador);
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
        }
        consultar();

    }

    public boolean validarDatos() {
        if (isEditing) {
            if (atvPrestadorEJB.findByNombreAndRangoFecha(nombre.trim(), desde, hasta, selected.getIdAtvPrestador()) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un registro con el nombre ingresado para el rango de fechas indicado");
                return true;
            }
        } else {
            if (atvPrestadorEJB.findByNombreAndRangoFecha(nombre.trim(), desde, hasta, 0) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un registro con el nombre ingresado para el rango de fechas indicado");
                return true;
            }
        }

        return false;
    }

    public String getUrl() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return Util.getUrlContext(request) + "/public/atv/login.jsf";
    }

    public AtvPrestador getAtvPrestador() {
        return atvPrestador;
    }

    public void setAtvPrestador(AtvPrestador atvPrestador) {
        this.atvPrestador = atvPrestador;
    }

    public AtvPrestador getSelected() {
        return selected;
    }

    public void setSelected(AtvPrestador selected) {
        this.selected = selected;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<AtvPrestador> getLstAtvPrestadores() {
        return lstAtvPrestadores;
    }

    public void setLstAtvPrestadores(List<AtvPrestador> lstAtvPrestadores) {
        this.lstAtvPrestadores = lstAtvPrestadores;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isReqFecha() {
        return activo;
    }

    public void setReqFecha(boolean activo) {
        this.activo = activo;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
