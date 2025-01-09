package com.movilidad.jsf;

import com.movilidad.ejb.AseoParamAreaFacadeLocal;
import com.movilidad.ejb.CableEstacionFacadeLocal;
import com.movilidad.model.AseoParamArea;
import com.movilidad.model.CableEstacion;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "aseoParamAreaBean")
@ViewScoped
public class AseoParamAreaBean implements Serializable {

    @EJB
    private AseoParamAreaFacadeLocal aseoTipoEjb;
    @EJB
    private CableEstacionFacadeLocal cableEstacionEjb;

    private AseoParamArea aseoParamArea;
    private AseoParamArea selected;
    private String nombre;
    private String codigo;
    private Integer i_CableEstacion;

    private boolean isEditing;

    private List<AseoParamArea> lstAseoParamAreas;
    private List<CableEstacion> lstCableEstacion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstAseoParamAreas = aseoTipoEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        codigo = "";
        i_CableEstacion = null;
        aseoParamArea = new AseoParamArea();
        selected = null;
        isEditing = false;

        lstCableEstacion = cableEstacionEjb.findByEstadoReg();
    }

    public void editar() {
        codigo = selected.getCodigo();
        nombre = selected.getNombre();
        i_CableEstacion = selected.getIdCableEstacion().getIdCableEstacion();
        aseoParamArea = selected;
        isEditing = true;

        lstCableEstacion = cableEstacionEjb.findByEstadoReg();
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            if (isEditing) {

                aseoParamArea.setNombre(nombre);
                aseoParamArea.setCodigo(codigo);
//                aseoParamArea.setHashString(generarHashCodigo());
                aseoParamArea.setIdCableEstacion(cableEstacionEjb.find(i_CableEstacion));
                aseoParamArea.setEstadoReg(0);
                aseoParamArea.setCreado(MovilidadUtil.fechaCompletaHoy());
                aseoParamArea.setUsername(user.getUsername());
                aseoTipoEjb.edit(aseoParamArea);

                PrimeFaces.current().executeScript("PF('wlvAseoParamArea').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                aseoParamArea.setNombre(nombre);
                aseoParamArea.setCodigo(codigo);
                aseoParamArea.setHashString(generarHashCodigo());
                aseoParamArea.setIdCableEstacion(cableEstacionEjb.find(i_CableEstacion));
                aseoParamArea.setEstadoReg(0);
                aseoParamArea.setCreado(MovilidadUtil.fechaCompletaHoy());
                aseoParamArea.setUsername(user.getUsername());
                aseoTipoEjb.create(aseoParamArea);
                lstAseoParamAreas.add(aseoParamArea);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public String validarDatos() {
        if (isEditing) {
            if (aseoTipoEjb.findByNombre(nombre.trim(), selected.getIdAseoParamArea()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
            if (aseoTipoEjb.findByCodigo(codigo.trim(), selected.getIdAseoParamArea()) != null) {
                return "YA existe un registro con el código a ingresar";
            }
        } else {
            if (!lstAseoParamAreas.isEmpty()) {
                if (aseoTipoEjb.findByNombre(nombre.trim(), 0) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
                if (aseoTipoEjb.findByCodigo(codigo.trim(), 0) != null) {
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
            Logger.getLogger(AseoParamAreaBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public AseoParamArea getAseoParamArea() {
        return aseoParamArea;
    }

    public void setAseoParamArea(AseoParamArea aseoParamArea) {
        this.aseoParamArea = aseoParamArea;
    }

    public AseoParamArea getSelected() {
        return selected;
    }

    public void setSelected(AseoParamArea selected) {
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

    public Integer getI_CableEstacion() {
        return i_CableEstacion;
    }

    public void setI_CableEstacion(Integer i_CableEstacion) {
        this.i_CableEstacion = i_CableEstacion;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<AseoParamArea> getLstAseoParamAreas() {
        return lstAseoParamAreas;
    }

    public void setLstAseoParamAreas(List<AseoParamArea> lstAseoParamAreas) {
        this.lstAseoParamAreas = lstAseoParamAreas;
    }

    public List<CableEstacion> getLstCableEstacion() {
        return lstCableEstacion;
    }

    public void setLstCableEstacion(List<CableEstacion> lstCableEstacion) {
        this.lstCableEstacion = lstCableEstacion;
    }

}
