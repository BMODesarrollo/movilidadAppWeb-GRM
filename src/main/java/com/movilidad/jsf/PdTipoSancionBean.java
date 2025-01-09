package com.movilidad.jsf;

import com.movilidad.ejb.PdTipoSancionFacadeLocal;
import com.movilidad.model.PdTipoSancion;
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
@Named(value = "pdTipoSancionBean")
@ViewScoped
public class PdTipoSancionBean implements Serializable {

    @EJB
    private PdTipoSancionFacadeLocal pdTipoSancionEjb;

    private PdTipoSancion pdTipoSancion;
    private PdTipoSancion selected;
    private String nombre;

    private boolean isEditing;

    private List<PdTipoSancion> lstPdTipoSancion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstPdTipoSancion = pdTipoSancionEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        pdTipoSancion = new PdTipoSancion();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        pdTipoSancion = selected;
        isEditing = true;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            pdTipoSancion.setNombre(nombre);
            pdTipoSancion.setUsername(user.getUsername());

            if (isEditing) {

                pdTipoSancion.setModificado(MovilidadUtil.fechaCompletaHoy());
                pdTipoSancionEjb.edit(pdTipoSancion);

                PrimeFaces.current().executeScript("PF('wlvTipoDocumentos').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                pdTipoSancion.setEstadoReg(0);
                pdTipoSancion.setCreado(MovilidadUtil.fechaCompletaHoy());
                pdTipoSancionEjb.create(pdTipoSancion);
                lstPdTipoSancion.add(pdTipoSancion);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public String validarDatos() {
        if (isEditing) {
            if (pdTipoSancionEjb.findByNombre(selected.getIdPdTipoSancion(), nombre.trim()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstPdTipoSancion.isEmpty()) {
                if (pdTipoSancionEjb.findByNombre(0, nombre.trim()) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }

        return null;
    }

    public PdTipoSancion getPdTipoSancion() {
        return pdTipoSancion;
    }

    public void setPdTipoSancion(PdTipoSancion pdTipoSancion) {
        this.pdTipoSancion = pdTipoSancion;
    }

    public PdTipoSancion getSelected() {
        return selected;
    }

    public void setSelected(PdTipoSancion selected) {
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

    public List<PdTipoSancion> getLstPdTipoSancion() {
        return lstPdTipoSancion;
    }

    public void setLstPdTipoSancion(List<PdTipoSancion> lstPdTipoSancion) {
        this.lstPdTipoSancion = lstPdTipoSancion;
    }

}
