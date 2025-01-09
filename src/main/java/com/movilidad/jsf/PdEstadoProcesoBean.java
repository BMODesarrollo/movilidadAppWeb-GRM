package com.movilidad.jsf;

import com.movilidad.ejb.PdEstadoProcesoFacadeLocal;
import com.movilidad.model.PdEstadoProceso;
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
@Named(value = "pdEstadoProcesoBean")
@ViewScoped
public class PdEstadoProcesoBean implements Serializable {

    @EJB
    private PdEstadoProcesoFacadeLocal pdEstadoProcesoEjb;

    private PdEstadoProceso pdEstadoProceso;
    private PdEstadoProceso selected;
    private String nombre;

    private boolean b_cierraProceso;
    private boolean isEditing;

    private List<PdEstadoProceso> lstPdEstadoProcesos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstPdEstadoProcesos = pdEstadoProcesoEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        pdEstadoProceso = new PdEstadoProceso();
        selected = null;
        b_cierraProceso = false;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        pdEstadoProceso = selected;
        b_cierraProceso = (selected.getCierraProceso() == 1);
        isEditing = true;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            pdEstadoProceso.setNombre(nombre);
            pdEstadoProceso.setUsername(user.getUsername());
            pdEstadoProceso.setCierraProceso(b_cierraProceso ? 1 : 0);

            if (isEditing) {

                pdEstadoProceso.setModificado(MovilidadUtil.fechaCompletaHoy());
                pdEstadoProcesoEjb.edit(pdEstadoProceso);

                PrimeFaces.current().executeScript("PF('wlvEstadoProceso').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                pdEstadoProceso.setEstadoReg(0);
                pdEstadoProceso.setCreado(MovilidadUtil.fechaCompletaHoy());
                pdEstadoProcesoEjb.create(pdEstadoProceso);
                lstPdEstadoProcesos.add(pdEstadoProceso);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public String validarDatos() {
        if (isEditing) {
            if (pdEstadoProcesoEjb.findByNombre(selected.getIdPdEstadoProceso(), nombre.trim()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
            if (pdEstadoProcesoEjb.verificarCierreProceso(selected.getIdPdEstadoProceso()) != null && b_cierraProceso) {
                return "YA existe un registro con cierre de proceso";
            }
        } else {
            if (!lstPdEstadoProcesos.isEmpty()) {
                if (pdEstadoProcesoEjb.findByNombre(0, nombre.trim()) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
                if (pdEstadoProcesoEjb.verificarCierreProceso(0) != null && b_cierraProceso) {
                    return "YA existe un registro con cierre de proceso";
                }
            }
        }

        return null;
    }

    public PdEstadoProceso getPdEstadoProceso() {
        return pdEstadoProceso;
    }

    public void setPdEstadoProceso(PdEstadoProceso pdEstadoProceso) {
        this.pdEstadoProceso = pdEstadoProceso;
    }

    public PdEstadoProceso getSelected() {
        return selected;
    }

    public void setSelected(PdEstadoProceso selected) {
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

    public List<PdEstadoProceso> getLstPdEstadoProcesos() {
        return lstPdEstadoProcesos;
    }

    public void setLstPdEstadoProcesos(List<PdEstadoProceso> lstPdEstadoProcesos) {
        this.lstPdEstadoProcesos = lstPdEstadoProcesos;
    }

    public boolean isB_cierraProceso() {
        return b_cierraProceso;
    }

    public void setB_cierraProceso(boolean b_cierraProceso) {
        this.b_cierraProceso = b_cierraProceso;
    }

}
