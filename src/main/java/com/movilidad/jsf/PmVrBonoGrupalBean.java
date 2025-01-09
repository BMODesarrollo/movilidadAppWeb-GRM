package com.movilidad.jsf;

import com.movilidad.ejb.PmVrbonoGrupalFacadeLocal;
import com.movilidad.model.PmVrbonoGrupal;
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
@Named(value = "pmVrBonoGrupalBean")
@ViewScoped
public class PmVrBonoGrupalBean implements Serializable {

    @EJB
    private PmVrbonoGrupalFacadeLocal vrbonoGrupalEjb;

    private PmVrbonoGrupal pmVrbonoGrupal;
    private PmVrbonoGrupal selected;

    private Date fechaDesde;
    private Date fechaHasta;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private boolean isEditing;

    private List<PmVrbonoGrupal> lstVrBonoGrupal;

    @PostConstruct
    public void init() {
        lstVrBonoGrupal = vrbonoGrupalEjb.findAllByEstadoReg();
    }

    /**
     * Carga los datos para un nuevo registro
     */
    public void nuevo() {
        isEditing = false;
        fechaDesde = null;
        fechaHasta = null;
        pmVrbonoGrupal = new PmVrbonoGrupal();
        selected = null;
    }

    /**
     * Realiza la carga del registro previo a la edición de éste.
     */
    public void editar() {
        isEditing = true;
        fechaDesde = selected.getDesde();
        fechaHasta = selected.getHasta();
        pmVrbonoGrupal = selected;
    }

    /**
     * Guarda/modifica los datos en la tabla.
     */
    public void guardar() {
        String msgValidacion = validarDatos();

        if (msgValidacion == null) {
            
            pmVrbonoGrupal.setDesde(fechaDesde);
            pmVrbonoGrupal.setHasta(fechaHasta);

            if (isEditing) {

                pmVrbonoGrupal.setUsername(user.getUsername());
                pmVrbonoGrupal.setModificado(MovilidadUtil.fechaCompletaHoy());
                vrbonoGrupalEjb.edit(pmVrbonoGrupal);

                MovilidadUtil.hideModal("wlvPmVrBonoGrupal");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");

            } else {
                pmVrbonoGrupal.setEstadoReg(0);
                pmVrbonoGrupal.setUsername(user.getUsername());
                pmVrbonoGrupal.setCreado(MovilidadUtil.fechaCompletaHoy());
                vrbonoGrupalEjb.create(pmVrbonoGrupal);
                lstVrBonoGrupal.add(pmVrbonoGrupal);

                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }

        } else {
            MovilidadUtil.addErrorMessage(msgValidacion);
        }
    }

    private String validarDatos() {
        
        if (Util.validarFechaCambioEstado(fechaDesde, fechaHasta)) {
            return "La fecha desde DEBE ser menor que la fecha FIN";
        }
        
        if (isEditing) {
            if (vrbonoGrupalEjb.verificarFecha(fechaDesde, selected.getIdPmVrbonoGrupal()) != null) {
                return "YA existe un registro que se encuentra dentro de ese rango de fechas";
            }
            if (vrbonoGrupalEjb.verificarFecha(fechaHasta, selected.getIdPmVrbonoGrupal()) != null) {
                return "YA existe un registro que se encuentra dentro de ese rango de fechas";
            }
        } else {
            if (!lstVrBonoGrupal.isEmpty()) {
                if (vrbonoGrupalEjb.verificarFecha(fechaDesde, 0) != null) {
                    return "YA existe un registro que se encuentra dentro de ese rango de fechas";
                }
                if (vrbonoGrupalEjb.verificarFecha(fechaHasta, 0) != null) {
                    return "YA existe un registro que se encuentra dentro de ese rango de fechas";
                }
            }
        }

        return null;
    }

    public PmVrbonoGrupal getPmVrbonoGrupal() {
        return pmVrbonoGrupal;
    }

    public void setPmVrbonoGrupal(PmVrbonoGrupal pmVrbonoGrupal) {
        this.pmVrbonoGrupal = pmVrbonoGrupal;
    }

    public PmVrbonoGrupal getSelected() {
        return selected;
    }

    public void setSelected(PmVrbonoGrupal selected) {
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

    public List<PmVrbonoGrupal> getLstVrBonoGrupal() {
        return lstVrBonoGrupal;
    }

    public void setLstVrBonoGrupal(List<PmVrbonoGrupal> lstVrBonoGrupal) {
        this.lstVrBonoGrupal = lstVrBonoGrupal;
    }
}
