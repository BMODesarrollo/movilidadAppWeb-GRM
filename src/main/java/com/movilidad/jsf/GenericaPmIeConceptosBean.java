package com.movilidad.jsf;

import com.movilidad.ejb.GenericaFacadeLocal;
import com.movilidad.ejb.GenericaPmIeConceptosFacadeLocal;
import com.movilidad.ejb.GenericaPmTipoConceptoFacadeLocal;
import com.movilidad.model.GenericaPmIeConceptos;
import com.movilidad.model.GenericaPmTipoConcepto;
import com.movilidad.model.ParamArea;
import com.movilidad.model.ParamAreaUsr;
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
@Named(value = "genericaPmIeConceptoBean")
@ViewScoped
public class GenericaPmIeConceptosBean implements Serializable {

    @EJB
    private GenericaPmIeConceptosFacadeLocal conceptosEjb;
    @EJB
    private GenericaPmTipoConceptoFacadeLocal tipoConceptoEjb;
    @EJB
    private GenericaFacadeLocal genericaEjb;

    private GenericaPmIeConceptos genericaPmIeConcepto;
    private GenericaPmIeConceptos selected;
    private Integer i_idArea;
    private Integer i_tipoConcepto;
    private String concepto;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private boolean isEditing;

    private List<GenericaPmIeConceptos> lstConceptos;
    private List<GenericaPmTipoConcepto> lstTipoConceptos;

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
        concepto = "";
        genericaPmIeConcepto = new GenericaPmIeConceptos();
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
        genericaPmIeConcepto = selected;
        i_tipoConcepto = selected.getIdGenericaPmTipoConcepto().getIdGenericaPmTipoConcepto();

        lstTipoConceptos = tipoConceptoEjb.findAll();
    }

    /**
     * Guarda/modifica los datos en la tabla.
     */
    public void guardar() {
        String msgValidacion = validarDatos();

        if (msgValidacion == null) {

            genericaPmIeConcepto.setConcepto(concepto);
            genericaPmIeConcepto.setIdGenericaPmTipoConcepto(new GenericaPmTipoConcepto(i_tipoConcepto));

            if (isEditing) {

                genericaPmIeConcepto.setUsername(user.getUsername());
                genericaPmIeConcepto.setModificado(MovilidadUtil.fechaCompletaHoy());
                conceptosEjb.edit(genericaPmIeConcepto);

                MovilidadUtil.hideModal("wlvGenericaPmIeConcepto");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");

            } else {
                genericaPmIeConcepto.setIdParamArea(new ParamArea(i_idArea));
                genericaPmIeConcepto.setEstadoReg(0);
                genericaPmIeConcepto.setUsername(user.getUsername());
                genericaPmIeConcepto.setCreado(MovilidadUtil.fechaCompletaHoy());
                conceptosEjb.create(genericaPmIeConcepto);

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

        if (isEditing) {
            if (conceptosEjb.findByConcepto(concepto, selected.getIdGenericaPmIeConceptos(), i_tipoConcepto, i_idArea) != null) {
                return "YA existe un registro con los parámetros ingresados";
            }
        } else {
            if (!lstConceptos.isEmpty()) {
                if (conceptosEjb.findByConcepto(concepto, 0, i_tipoConcepto, i_idArea) != null) {
                    return "YA existe un registro con los parámetros ingresados";
                }
            }
        }

        return null;
    }

    private void consultar() {
        if (i_idArea != null) {
            lstConceptos = conceptosEjb.findAllByEstadoRegAndArea(i_idArea);
        }
    }

    public GenericaPmIeConceptos getGenericaPmIeConcepto() {
        return genericaPmIeConcepto;
    }

    public void setGenericaPmIeConcepto(GenericaPmIeConceptos pmIeConcepto) {
        this.genericaPmIeConcepto = pmIeConcepto;
    }

    public GenericaPmIeConceptos getSelected() {
        return selected;
    }

    public void setSelected(GenericaPmIeConceptos selected) {
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

    public List<GenericaPmIeConceptos> getLstConceptos() {
        return lstConceptos;
    }

    public void setLstConceptos(List<GenericaPmIeConceptos> lstConceptos) {
        this.lstConceptos = lstConceptos;
    }

    public List<GenericaPmTipoConcepto> getLstTipoConceptos() {
        return lstTipoConceptos;
    }

    public void setLstTipoConceptos(List<GenericaPmTipoConcepto> lstTipoConceptos) {
        this.lstTipoConceptos = lstTipoConceptos;
    }

    public Integer getI_idArea() {
        return i_idArea;
    }

    public void setI_idArea(Integer i_idArea) {
        this.i_idArea = i_idArea;
    }
}
