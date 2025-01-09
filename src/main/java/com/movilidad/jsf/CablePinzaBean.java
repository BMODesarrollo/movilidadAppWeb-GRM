/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CablePinzaFacadeLocal;
import com.movilidad.model.CablePinza;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "cablePinzaBean")
@ViewScoped
public class CablePinzaBean implements Serializable {

    @EJB
    private CablePinzaFacadeLocal cabinaEjb;

    private CablePinza cablePinza;
    private CablePinza selected;
    private String nombre;
    private String codigo;

    private boolean isEditing;
    private boolean b_mtto;

    private List<CablePinza> lstCablePinzas;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstCablePinzas = cabinaEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        isEditing = false;
        b_mtto = false;
        nombre = "";
        codigo = "";
        cablePinza = new CablePinza();
        selected = null;
    }

    public void editar() {
        isEditing = true;
        b_mtto = selected.getMtto();
        codigo = selected.getCodigo();
        nombre = selected.getNombre();
        cablePinza = selected;
    }

    /**
     * Permite agregar/modificar un registro en base de datos
     */
    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    private void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            cablePinza.setNombre(nombre);
            cablePinza.setCodigo(codigo);
            cablePinza.setMtto(b_mtto);
            cablePinza.setUsername(user.getUsername());

            if (isEditing) {
                cablePinza.setModificado(MovilidadUtil.fechaCompletaHoy());
                cabinaEjb.edit(cablePinza);

                MovilidadUtil.hideModal("wlvCablePinza");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                cablePinza.setEstadoReg(0);
                cablePinza.setCreado(MovilidadUtil.fechaCompletaHoy());
                cabinaEjb.create(cablePinza);
                lstCablePinzas.add(cablePinza);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public String validarDatos() {

        if (cablePinza.getFechaFinOp() != null) {
            if (Util.validarFechaCambioEstado(cablePinza.getFechaIniOp(), cablePinza.getFechaFinOp())) {
                return "La fecha inicio NO debe ser mayor a la fecha final";
            }
        }

        if (isEditing) {
            if (cabinaEjb.findByNombre(nombre.trim(), selected.getIdCablePinza()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
            if (cabinaEjb.findByCodigo(codigo.trim(), selected.getIdCablePinza()) != null) {
                return "YA existe un registro con el código a ingresar";
            }
        } else {
            if (!lstCablePinzas.isEmpty()) {
                if (cabinaEjb.findByNombre(nombre.trim(), 0) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
                if (cabinaEjb.findByCodigo(codigo.trim(), 0) != null) {
                    return "YA existe un registro con el código a ingresar";
                }
            }
        }

        return null;
    }

    public CablePinza getCablePinza() {
        return cablePinza;
    }

    public void setCablePinza(CablePinza cableCabina) {
        this.cablePinza = cableCabina;
    }

    public CablePinza getSelected() {
        return selected;
    }

    public void setSelected(CablePinza selected) {
        this.selected = selected;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public boolean isB_mtto() {
        return b_mtto;
    }

    public void setB_mtto(boolean b_mtto) {
        this.b_mtto = b_mtto;
    }

    public List<CablePinza> getLstCablePinzas() {
        return lstCablePinzas;
    }

    public void setLstCablePinzas(List<CablePinza> lstCablePinzas) {
        this.lstCablePinzas = lstCablePinzas;
    }

}
