package com.movilidad.jsf;

import com.movilidad.ejb.CableEventoTipoFacadeLocal;
import com.movilidad.model.CableEventoTipo;
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
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "cableEventoTipoBean")
@ViewScoped
public class CableEventoTipoBean implements Serializable {

    @EJB
    private CableEventoTipoFacadeLocal cableEventoTipoEjb;

    private CableEventoTipo cableEventoTipo;
    private CableEventoTipo selected;
    private String nombre;
    private String codigo;

    private boolean isEditing;

    private List<CableEventoTipo> lstCableEventoTipos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstCableEventoTipos = cableEventoTipoEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        codigo = "";
        cableEventoTipo = new CableEventoTipo();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        isEditing = true;
        nombre = selected.getNombre();
        codigo = selected.getCodigo();
        cableEventoTipo = selected;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            if (isEditing) {

                cableEventoTipo.setCodigo(codigo);
                cableEventoTipo.setNombre(nombre);
                cableEventoTipo.setModificado(new Date());
                cableEventoTipo.setUsername(user.getUsername());
                cableEventoTipoEjb.edit(cableEventoTipo);

                PrimeFaces.current().executeScript("PF('wlvCableEventoTipo').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                cableEventoTipo.setCodigo(codigo);
                cableEventoTipo.setNombre(nombre);
                cableEventoTipo.setEstadoReg(0);
                cableEventoTipo.setCreado(new Date());
                cableEventoTipo.setUsername(user.getUsername());
                cableEventoTipoEjb.create(cableEventoTipo);
                lstCableEventoTipos.add(cableEventoTipo);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {
        if (isEditing) {
            if (cableEventoTipoEjb.findByNombre(nombre.trim(), selected.getIdCableEventoTipo()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
            if (cableEventoTipoEjb.findByCodigo(codigo.trim(), selected.getIdCableEventoTipo()) != null) {
                return "YA existe un registro con el código a ingresar";
            }
        } else {
            if (!lstCableEventoTipos.isEmpty()) {
                if (cableEventoTipoEjb.findByNombre(nombre.trim(), 0) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
                if (cableEventoTipoEjb.findByCodigo(codigo.trim(), 0) != null) {
                    return "YA existe un registro con el código a ingresar";
                }
            }
        }

        return null;
    }

    public CableEventoTipo getCableEventoTipo() {
        return cableEventoTipo;
    }

    public void setCableEventoTipo(CableEventoTipo cableEventoTipo) {
        this.cableEventoTipo = cableEventoTipo;
    }

    public CableEventoTipo getSelected() {
        return selected;
    }

    public void setSelected(CableEventoTipo selected) {
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

    public List<CableEventoTipo> getLstCableEventoTipos() {
        return lstCableEventoTipos;
    }

    public void setLstCableEventoTipos(List<CableEventoTipo> lstCableEventoTipos) {
        this.lstCableEventoTipos = lstCableEventoTipos;
    }

}
