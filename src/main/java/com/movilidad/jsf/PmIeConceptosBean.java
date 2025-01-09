package com.movilidad.jsf;

import com.movilidad.ejb.PmIeConceptosFacadeLocal;
import com.movilidad.ejb.PmTipoConceptoFacadeLocal;
import com.movilidad.model.PmIeConceptos;
import com.movilidad.model.PmTipoConcepto;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
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
@Named(value = "pmIeConceptoBean")
@ViewScoped
public class PmIeConceptosBean implements Serializable {

    @EJB
    private PmIeConceptosFacadeLocal conceptosEjb;
    @EJB
    private PmTipoConceptoFacadeLocal tipoConceptoEjb;

    private PmIeConceptos pmIeConcepto;
    private PmIeConceptos selected;
    private Integer i_tipoConcepto;
    private String concepto;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private boolean isEditing;

    private List<PmIeConceptos> lstConceptos;
    private List<PmTipoConcepto> lstTipoConceptos;

    @PostConstruct
    public void init() {
        lstConceptos = conceptosEjb.findAllByEstadoReg();
    }

    /**
     * Carga los datos para un nuevo registro
     */
    public void nuevo() {
        isEditing = false;
        concepto = "";
        pmIeConcepto = new PmIeConceptos();
        i_tipoConcepto = null;
        selected = null;

        lstTipoConceptos = tipoConceptoEjb.findAll();
    }

    /**
     * Realiza la carga del registro previo a la edición de éste.
     */
    public void editar() {
        isEditing = true;
        concepto = selected.getConcepto();
        pmIeConcepto = selected;
        i_tipoConcepto = selected.getIdPmTipoConcepto().getIdPmTipoConcepto();

        lstTipoConceptos = tipoConceptoEjb.findAll();
    }

    /**
     * Guarda/modifica los datos en la tabla.
     */
    public void guardar() {
        String msgValidacion = validarDatos();

        if (msgValidacion == null) {

            pmIeConcepto.setConcepto(concepto);
            pmIeConcepto.setIdPmTipoConcepto(tipoConceptoEjb.find(i_tipoConcepto));

            if (isEditing) {

                pmIeConcepto.setUsername(user.getUsername());
                pmIeConcepto.setModificado(MovilidadUtil.fechaCompletaHoy());
                conceptosEjb.edit(pmIeConcepto);

                MovilidadUtil.hideModal("wlvPmIeConcepto");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");

            } else {
                pmIeConcepto.setEstadoReg(0);
                pmIeConcepto.setUsername(user.getUsername());
                pmIeConcepto.setCreado(MovilidadUtil.fechaCompletaHoy());
                conceptosEjb.create(pmIeConcepto);
                lstConceptos.add(pmIeConcepto);

                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }

        } else {
            MovilidadUtil.addErrorMessage(msgValidacion);
        }
    }

    private String validarDatos() {
        if (isEditing) {
            if (conceptosEjb.findByConcepto(concepto, selected.getIdPmIeConceptos(), i_tipoConcepto) != null) {
                return "YA existe un registro con los parámetros ingresados";
            }
        } else {
            if (!lstConceptos.isEmpty()) {
                if (conceptosEjb.findByConcepto(concepto, 0, i_tipoConcepto) != null) {
                    return "YA existe un registro con los parámetros ingresados";
                }
            }
        }

        return null;
    }

    public PmIeConceptos getPmIeConcepto() {
        return pmIeConcepto;
    }

    public void setPmIeConcepto(PmIeConceptos pmIeConcepto) {
        this.pmIeConcepto = pmIeConcepto;
    }

    public PmIeConceptos getSelected() {
        return selected;
    }

    public void setSelected(PmIeConceptos selected) {
        this.selected = selected;
    }

    public Integer getI_tipoConcepto() {
        return i_tipoConcepto;
    }

    public void setI_tipoConcepto(Integer i_tipoConcepto) {
        this.i_tipoConcepto = i_tipoConcepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<PmIeConceptos> getLstConceptos() {
        return lstConceptos;
    }

    public void setLstConceptos(List<PmIeConceptos> lstConceptos) {
        this.lstConceptos = lstConceptos;
    }

    public List<PmTipoConcepto> getLstTipoConceptos() {
        return lstTipoConceptos;
    }

    public void setLstTipoConceptos(List<PmTipoConcepto> lstTipoConceptos) {
        this.lstTipoConceptos = lstTipoConceptos;
    }

}
