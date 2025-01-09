package com.movilidad.jsf;

import com.movilidad.ejb.PdTipoProcesoFacadeLocal;
import com.movilidad.model.PdTipoProceso;
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
@Named(value = "pdTipoProcesoBean")
@ViewScoped
public class PdTipoProcesoBean implements Serializable {

    @EJB
    private PdTipoProcesoFacadeLocal pdTipoProcesoEjb;

    private PdTipoProceso pdTipoProceso;
    private PdTipoProceso selected;
    private String nombre;

    private boolean isEditing;

    private List<PdTipoProceso> lstPdTipoProcesos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstPdTipoProcesos = pdTipoProcesoEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        pdTipoProceso = new PdTipoProceso();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        pdTipoProceso = selected;
        isEditing = true;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            pdTipoProceso.setNombre(nombre);
            pdTipoProceso.setUsername(user.getUsername());

            if (isEditing) {

                pdTipoProceso.setModificado(MovilidadUtil.fechaCompletaHoy());
                pdTipoProcesoEjb.edit(pdTipoProceso);

                PrimeFaces.current().executeScript("PF('wlvTipoProceso').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                pdTipoProceso.setEstadoReg(0);
                pdTipoProceso.setCreado(MovilidadUtil.fechaCompletaHoy());
                pdTipoProcesoEjb.create(pdTipoProceso);
                lstPdTipoProcesos.add(pdTipoProceso);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public String validarDatos() {
        if (isEditing) {
            if (pdTipoProcesoEjb.findByNombre(selected.getIdPdTipoProceso(), nombre.trim()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstPdTipoProcesos.isEmpty()) {
                if (pdTipoProcesoEjb.findByNombre(0, nombre.trim()) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }

        return null;
    }

    public PdTipoProceso getPdTipoProceso() {
        return pdTipoProceso;
    }

    public void setPdTipoProceso(PdTipoProceso pdTipoProceso) {
        this.pdTipoProceso = pdTipoProceso;
    }

    public PdTipoProceso getSelected() {
        return selected;
    }

    public void setSelected(PdTipoProceso selected) {
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

    public List<PdTipoProceso> getLstPdTipoProcesos() {
        return lstPdTipoProcesos;
    }

    public void setLstPdTipoProcesos(List<PdTipoProceso> lstPdTipoProcesos) {
        this.lstPdTipoProcesos = lstPdTipoProcesos;
    }

}
