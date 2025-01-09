package com.movilidad.jsf;

import com.movilidad.ejb.GenericaFacadeLocal;
import com.movilidad.ejb.GenericaPmVrbonoGrupalFacadeLocal;
import com.movilidad.model.GenericaPmVrbonoGrupal;
import com.movilidad.model.ParamArea;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "genericaPmVrBonoGrupalBean")
@ViewScoped
public class GenericaPmVrBonoGrupalBean implements Serializable {

    @EJB
    private GenericaPmVrbonoGrupalFacadeLocal vrbonoGrupalEjb;
    @EJB
    private GenericaFacadeLocal genericaEjb;

    private GenericaPmVrbonoGrupal genericaPmVrbonoGrupal;
    private GenericaPmVrbonoGrupal selected;

    private Integer i_idArea;
    private Date fechaDesde;
    private Date fechaHasta;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private boolean isEditing;

    private List<GenericaPmVrbonoGrupal> lstVrBonoGrupal;

    @PostConstruct
    public void init() {
        ParamAreaUsr paramAreaUsr = genericaEjb.findByUsername(user.getUsername());
        i_idArea = null;

        if (paramAreaUsr != null) {
            i_idArea = paramAreaUsr.getIdParamArea().getIdParamArea();
            consultar();
        }
    }

    /**
     * Carga los datos para un nuevo registro
     */
    public void nuevo() {
        isEditing = false;
        fechaDesde = null;
        fechaHasta = null;
        genericaPmVrbonoGrupal = new GenericaPmVrbonoGrupal();
        selected = null;
    }

    /**
     * Realiza la carga del registro previo a la edición de éste.
     */
    public void editar() {
        isEditing = true;
        fechaDesde = selected.getDesde();
        fechaHasta = selected.getHasta();
        genericaPmVrbonoGrupal = selected;
    }

    /**
     * Guarda/modifica los datos en la tabla.
     */
    public void guardar() {
        String msgValidacion = validarDatos();

        if (msgValidacion == null) {

            genericaPmVrbonoGrupal.setDesde(fechaDesde);
            genericaPmVrbonoGrupal.setHasta(fechaHasta);

            if (isEditing) {

                genericaPmVrbonoGrupal.setUsername(user.getUsername());
                genericaPmVrbonoGrupal.setModificado(MovilidadUtil.fechaCompletaHoy());
                vrbonoGrupalEjb.edit(genericaPmVrbonoGrupal);

                MovilidadUtil.hideModal("wlvGenericaPmVrBonoGrupal");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");

            } else {
                genericaPmVrbonoGrupal.setIdParamArea(new ParamArea(i_idArea));
                genericaPmVrbonoGrupal.setEstadoReg(0);
                genericaPmVrbonoGrupal.setUsername(user.getUsername());
                genericaPmVrbonoGrupal.setCreado(MovilidadUtil.fechaCompletaHoy());
                vrbonoGrupalEjb.create(genericaPmVrbonoGrupal);

                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }

            consultar();

        } else {
            MovilidadUtil.addErrorMessage(msgValidacion);
        }
    }

    private String validarDatos() {

        if (i_idArea.equals(null)) {
            return "El usuario logueado NO tiene un área asociada... Acción inválida";
        }

        if (Util.validarFechaCambioEstado(fechaDesde, fechaHasta)) {
            return "La fecha desde DEBE ser menor que la fecha FIN";
        }

        if (isEditing) {
            if (vrbonoGrupalEjb.verificarFecha(fechaDesde, selected.getIdGenericaPmVrbonoGrupal(), i_idArea) != null) {
                return "YA existe un registro que se encuentra dentro de ese rango de fechas";
            }
            if (vrbonoGrupalEjb.verificarFecha(fechaHasta, selected.getIdGenericaPmVrbonoGrupal(), i_idArea) != null) {
                return "YA existe un registro que se encuentra dentro de ese rango de fechas";
            }
        } else {
            if (!lstVrBonoGrupal.isEmpty()) {
                if (vrbonoGrupalEjb.verificarFecha(fechaDesde, 0, i_idArea) != null) {
                    return "YA existe un registro que se encuentra dentro de ese rango de fechas";
                }
                if (vrbonoGrupalEjb.verificarFecha(fechaHasta, 0, i_idArea) != null) {
                    return "YA existe un registro que se encuentra dentro de ese rango de fechas";
                }
            }
        }

        return null;
    }

    private void consultar() {
        if (i_idArea != null) {
            lstVrBonoGrupal = vrbonoGrupalEjb.findAllByEstadoRegAndArea(i_idArea);
        }
    }

    public GenericaPmVrbonoGrupal getGenericaPmVrbonoGrupal() {
        return genericaPmVrbonoGrupal;
    }

    public void setGenericaPmVrbonoGrupal(GenericaPmVrbonoGrupal pmVrbonoGrupal) {
        this.genericaPmVrbonoGrupal = pmVrbonoGrupal;
    }

    public GenericaPmVrbonoGrupal getSelected() {
        return selected;
    }

    public void setSelected(GenericaPmVrbonoGrupal selected) {
        this.selected = selected;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<GenericaPmVrbonoGrupal> getLstVrBonoGrupal() {
        return lstVrBonoGrupal;
    }

    public void setLstVrBonoGrupal(List<GenericaPmVrbonoGrupal> lstVrBonoGrupal) {
        this.lstVrBonoGrupal = lstVrBonoGrupal;
    }

    public Integer getI_idArea() {
        return i_idArea;
    }

    public void setI_idArea(Integer i_idArea) {
        this.i_idArea = i_idArea;
    }

}
