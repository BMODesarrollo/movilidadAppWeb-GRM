/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableCabinaFacadeLocal;
import com.movilidad.ejb.CablePinzaFacadeLocal;
import com.movilidad.model.CableCabina;
import com.movilidad.model.CablePinza;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Named(value = "cableCabinaBean")
@ViewScoped
public class CableCabinaBean implements Serializable {

    @EJB
    private CableCabinaFacadeLocal cabinaEjb;
    @EJB
    private CablePinzaFacadeLocal cablePinzaEJB;

    private List<CablePinza> listCablePinza;
    private CableCabina cableCabina;
    private CableCabina selected;
    private String nombre;
    private String codigo;
    private int i_idCablePinza;

    private boolean isEditing;

    private List<CableCabina> lstCableCabinas;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstCableCabinas = cabinaEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        codigo = "";
        cableCabina = new CableCabina();
        listCablePinza = cablePinzaEJB.findAllByEstadoReg();
        i_idCablePinza = 0;
        selected = null;
        isEditing = false;
    }

    public void editar() {
        listCablePinza = cablePinzaEJB.findAllByEstadoReg();
        if (selected.getIdCablePinza() != null) {
            i_idCablePinza = selected.getIdCablePinza().getIdCablePinza();
        }
        codigo = selected.getCodigo();
        nombre = selected.getNombre();
        cableCabina = selected;
        isEditing = true;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion != null) {
            MovilidadUtil.addErrorMessage(validacion);
            return;
        }
        cableCabina.setNombre(nombre);
        cableCabina.setCodigo(codigo);
        cableCabina.setUsername(user.getUsername());
        if (i_idCablePinza == 0) {
            cableCabina.setIdCablePinza(null);
        } else {
            cableCabina.setIdCablePinza(new CablePinza(i_idCablePinza));
        }

        if (isEditing) {
            cableCabina.setModificado(MovilidadUtil.fechaCompletaHoy());
            cabinaEjb.edit(cableCabina);
            MovilidadUtil.hideModal("wlvCableCabina");
            MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
        } else {
            cableCabina.setHashString(generarHashCodigo());
            cableCabina.setEstadoReg(0);
            cableCabina.setCreado(MovilidadUtil.fechaCompletaHoy());
            cabinaEjb.create(cableCabina);
            lstCableCabinas.add(cableCabina);

            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
        }
    }

    public String validarDatos() {

        if (cableCabina.getFechaFinOp() != null) {
            if (Util.validarFechaCambioEstado(cableCabina.getFechaIniOp(), cableCabina.getFechaFinOp())) {
                return "La fecha inicio NO debe ser mayor a la fecha final";
            }
        }

        if (isEditing) {
            if (cabinaEjb.findByNombre(nombre.trim(), selected.getIdCableCabina()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
            if (cabinaEjb.findByCodigo(codigo.trim(), selected.getIdCableCabina()) != null) {
                return "YA existe un registro con el código a ingresar";
            }
        } else {
            if (!lstCableCabinas.isEmpty()) {
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

    private String generarHashCodigo() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Change this to UTF-16 if needed
            md.update(codigo.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();

            return String.format("%064x", new BigInteger(1, digest));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CableCabinaBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public CableCabina getCableCabina() {
        return cableCabina;
    }

    public void setCableCabina(CableCabina cableCabina) {
        this.cableCabina = cableCabina;
    }

    public CableCabina getSelected() {
        return selected;
    }

    public void setSelected(CableCabina selected) {
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

    public List<CableCabina> getLstCableCabinas() {
        return lstCableCabinas;
    }

    public void setLstCableCabinas(List<CableCabina> lstCableCabinas) {
        this.lstCableCabinas = lstCableCabinas;
    }

    public int getI_idCablePinza() {
        return i_idCablePinza;
    }

    public void setI_idCablePinza(int i_idCablePinza) {
        this.i_idCablePinza = i_idCablePinza;
    }

    public List<CablePinza> getListCablePinza() {
        return listCablePinza;
    }

    public void setListCablePinza(List<CablePinza> listCablePinza) {
        this.listCablePinza = listCablePinza;
    }

}
