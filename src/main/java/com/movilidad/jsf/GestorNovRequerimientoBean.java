package com.movilidad.jsf;

import com.movilidad.ejb.GestorNovRequerimientoFacadeLocal;
import com.movilidad.model.GestorNovRequerimiento;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
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
@Named(value = "gestorNovRequerimientoBean")
@ViewScoped
public class GestorNovRequerimientoBean implements Serializable {

    @EJB
    private GestorNovRequerimientoFacadeLocal novRequerimientoEjb;

    private GestorNovRequerimiento novRequerimiento;
    private GestorNovRequerimiento selected;
    private String nombre;

    private boolean isEditing;

    private List<GestorNovRequerimiento> lstNovRequerimientos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    public void nuevo() {
        nombre = "";
        novRequerimiento = new GestorNovRequerimiento();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        novRequerimiento = selected;
        isEditing = true;
    }

    public void eliminar() {
        selected.setUsername(user.getUsername());
        selected.setEstadoReg(1);
        selected.setModificado(MovilidadUtil.fechaCompletaHoy());
        novRequerimientoEjb.edit(selected);

        consultar();

        MovilidadUtil.addSuccessMessage("Registro eliminado con éxito");

    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            novRequerimiento.setNombre(nombre);
            novRequerimiento.setEstadoReg(0);
            novRequerimiento.setUsername(user.getUsername());
            if (isEditing) {
                novRequerimiento.setModificado(MovilidadUtil.fechaCompletaHoy());
                novRequerimientoEjb.edit(novRequerimiento);
                PrimeFaces.current().executeScript("PF('wlvGestorNovRequerimiento').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                novRequerimiento.setCreado(MovilidadUtil.fechaCompletaHoy());
                novRequerimientoEjb.create(novRequerimiento);
                lstNovRequerimientos.add(novRequerimiento);
                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {
        if (isEditing) {
            if (novRequerimientoEjb.findByNombre(selected.getIdGestorNovRequerimiento(), nombre.trim()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstNovRequerimientos.isEmpty()) {
                if (novRequerimientoEjb.findByNombre(0, nombre.trim()) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }

        return null;
    }

    private void consultar() {
        lstNovRequerimientos = novRequerimientoEjb.findAllByEstadoReg();
    }

    public GestorNovRequerimiento getNovRequerimiento() {
        return novRequerimiento;
    }

    public void setNovRequerimiento(GestorNovRequerimiento novRequerimiento) {
        this.novRequerimiento = novRequerimiento;
    }

    public GestorNovRequerimiento getSelected() {
        return selected;
    }

    public void setSelected(GestorNovRequerimiento selected) {
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

    public List<GestorNovRequerimiento> getLstNovRequerimientos() {
        return lstNovRequerimientos;
    }

    public void setLstNovRequerimientos(List<GestorNovRequerimiento> lstNovRequerimientos) {
        this.lstNovRequerimientos = lstNovRequerimientos;
    }

}
