package com.movilidad.jsf;

import com.movilidad.ejb.HallazgosParamAreaFacadeLocal;
import com.movilidad.model.HallazgosParamArea;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
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
@Named(value = "hallazgosParamAreaBean")
@ViewScoped
public class HallazgosParamAreaBean implements Serializable {

    @EJB
    private HallazgosParamAreaFacadeLocal paramAreaEjb;

    private HallazgosParamArea hallazgosParamArea;
    private HallazgosParamArea selected;
    private String nombre;

    private boolean isEditing;

    private List<HallazgosParamArea> lista;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    public void nuevo() {
        nombre = "";
        hallazgosParamArea = new HallazgosParamArea();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        isEditing = true;
        nombre = selected.getNombre();
        hallazgosParamArea = selected;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {

            hallazgosParamArea.setNombre(nombre);
            hallazgosParamArea.setUsername(user.getUsername());

            if (isEditing) {
                hallazgosParamArea.setModificado(MovilidadUtil.fechaCompletaHoy());
                paramAreaEjb.edit(hallazgosParamArea);

                MovilidadUtil.hideModal("wlvParamAreaHallazgos");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                hallazgosParamArea.setEstadoReg(0);
                hallazgosParamArea.setCreado(MovilidadUtil.fechaCompletaHoy());
                paramAreaEjb.create(hallazgosParamArea);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }

            consultar();

        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private void consultar() {
        lista = paramAreaEjb.findAllByEstadoReg();
    }

    private String validarDatos() {
        if (isEditing) {
            if (paramAreaEjb.findByNombre(nombre.trim(), selected.getIdHallazgosParamArea()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lista.isEmpty()) {
                if (paramAreaEjb.findByNombre(nombre.trim(), 0) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }

        return null;
    }

    public HallazgosParamArea getHallazgosParamArea() {
        return hallazgosParamArea;
    }

    public void setHallazgosParamArea(HallazgosParamArea hallazgosParamArea) {
        this.hallazgosParamArea = hallazgosParamArea;
    }

    public HallazgosParamArea getSelected() {
        return selected;
    }

    public void setSelected(HallazgosParamArea selected) {
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

    public List<HallazgosParamArea> getLista() {
        return lista;
    }

    public void setLista(List<HallazgosParamArea> lista) {
        this.lista = lista;
    }

}
