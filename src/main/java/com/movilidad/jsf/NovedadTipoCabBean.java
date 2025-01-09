package com.movilidad.jsf;

import com.movilidad.ejb.NovedadTipoCabFacadeLocal;
import com.movilidad.model.NovedadTipoCab;
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
@Named(value = "novedadTipoCabBean")
@ViewScoped
public class NovedadTipoCabBean implements Serializable {

    @EJB
    private NovedadTipoCabFacadeLocal novedadTipoEjb;

    private NovedadTipoCab novedadTipoCab;
    private NovedadTipoCab selected;
    private String nombre;

    private boolean isEditing;

    private List<NovedadTipoCab> lstNovedadTipoCabs;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstNovedadTipoCabs = novedadTipoEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        novedadTipoCab = new NovedadTipoCab();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        novedadTipoCab = selected;
        isEditing = true;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            if (isEditing) {

                novedadTipoCab.setNombre(nombre);
                novedadTipoCab.setEstadoReg(0);
                novedadTipoCab.setCreado(new Date());
                novedadTipoCab.setUsername(user.getUsername());
                novedadTipoEjb.edit(novedadTipoCab);

                PrimeFaces.current().executeScript("PF('wlvNovedadTipoCab').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                novedadTipoCab.setNombre(nombre);
                novedadTipoCab.setEstadoReg(0);
                novedadTipoCab.setCreado(new Date());
                novedadTipoCab.setUsername(user.getUsername());
                novedadTipoEjb.create(novedadTipoCab);
                lstNovedadTipoCabs.add(novedadTipoCab);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public String validarDatos() {
        if (isEditing) {
            if (novedadTipoEjb.findByNombre(nombre.trim(), selected.getIdNovedadTipoCab()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstNovedadTipoCabs.isEmpty()) {
                if (novedadTipoEjb.findByNombre(nombre.trim(), 0) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }

        return null;
    }

    public NovedadTipoCab getNovedadTipoCab() {
        return novedadTipoCab;
    }

    public void setNovedadTipoCab(NovedadTipoCab novedadTipoCab) {
        this.novedadTipoCab = novedadTipoCab;
    }

    public NovedadTipoCab getSelected() {
        return selected;
    }

    public void setSelected(NovedadTipoCab selected) {
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

    public List<NovedadTipoCab> getLstNovedadTipoCabs() {
        return lstNovedadTipoCabs;
    }

    public void setLstNovedadTipoCabs(List<NovedadTipoCab> lstNovedadTipoCabs) {
        this.lstNovedadTipoCabs = lstNovedadTipoCabs;
    }

}
