package com.movilidad.jsf;

import com.movilidad.ejb.LavadoContratistaFacadeLocal;
import com.movilidad.model.LavadoContratista;
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
@Named(value = "lavadoContratistaBean")
@ViewScoped
public class LavadoContratistaBean implements Serializable {

    @EJB
    private LavadoContratistaFacadeLocal lavadoContratistaEjb;

    private LavadoContratista lavadoContratista;
    private LavadoContratista selected;
    private String nombre;

    private boolean b_activo;
    private boolean isEditing;

    private List<LavadoContratista> lstLavadoContratistas;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstLavadoContratistas = lavadoContratistaEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        lavadoContratista = new LavadoContratista();
        selected = null;
        b_activo = false;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        lavadoContratista = selected;
        b_activo = (selected.getActivo() == 1);
        isEditing = true;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            lavadoContratista.setNombre(nombre);
            lavadoContratista.setUsername(user.getUsername());
            lavadoContratista.setActivo(b_activo ? 1 : 0);

            if (isEditing) {

                lavadoContratista.setModificado(MovilidadUtil.fechaCompletaHoy());
                lavadoContratistaEjb.edit(lavadoContratista);

                PrimeFaces.current().executeScript("PF('wlvTipoEvidencia').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                lavadoContratista.setEstadoReg(0);
                lavadoContratista.setCreado(MovilidadUtil.fechaCompletaHoy());
                lavadoContratistaEjb.create(lavadoContratista);
                lstLavadoContratistas.add(lavadoContratista);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public String validarDatos() {
        if (isEditing) {
            if (lavadoContratistaEjb.findByNombre(selected.getIdLavadoContratista(), nombre.trim(), lavadoContratista.getDesde(), lavadoContratista.getHasta()) != null) {
                return "YA existe un registro con ese nombre para el rango de fechas ingresado. ";
            }
        } else {
            if (!lstLavadoContratistas.isEmpty()) {
                if (lavadoContratistaEjb.findByNombre(0, nombre.trim(), lavadoContratista.getDesde(), lavadoContratista.getHasta()) != null) {
                    return "YA existe un registro con ese nombre para el rango de fechas ingresado. ";
                }
            }
        }

        return null;
    }

    public LavadoContratista getLavadoContratista() {
        return lavadoContratista;
    }

    public void setLavadoContratista(LavadoContratista lavadoContratista) {
        this.lavadoContratista = lavadoContratista;
    }

    public LavadoContratista getSelected() {
        return selected;
    }

    public void setSelected(LavadoContratista selected) {
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

    public List<LavadoContratista> getLstLavadoContratistas() {
        return lstLavadoContratistas;
    }

    public void setLstLavadoContratistas(List<LavadoContratista> lstLavadoContratistas) {
        this.lstLavadoContratistas = lstLavadoContratistas;
    }

    public boolean isB_activo() {
        return b_activo;
    }

    public void setB_activo(boolean b_activo) {
        this.b_activo = b_activo;
    }

}
