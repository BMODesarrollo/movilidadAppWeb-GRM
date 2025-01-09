package com.movilidad.jsf;

import com.movilidad.ejb.NominaServerParamFacadeLocal;
import com.movilidad.model.NominaServerParam;
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
@Named(value = "nominaServerParamBean")
@ViewScoped
public class NominaServerParamBean implements Serializable {

    @EJB
    private NominaServerParamFacadeLocal nominaServerParamEjb;

    private NominaServerParam nominaServerParam;
    private NominaServerParam selected;

    private List<NominaServerParam> lstNominaServerParams;

    private boolean isEditing;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    public void nuevo() {

        if (lstNominaServerParams.size() > 0) {
            MovilidadUtil.addErrorMessage("SOLO debe existir un registro en éste módulo");
            return;
        }

        isEditing = false;
        nominaServerParam = new NominaServerParam();
        selected = null;

        MovilidadUtil.openModal("wlvNominaServerParam");
    }

    public void editar() {
        isEditing = true;
        nominaServerParam = selected;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    private void guardarTransactional() {
        String validacion = validarDatos();

        if (validacion == null) {
            nominaServerParam.setUsername(user.getUsername());
            if (isEditing) {
                nominaServerParam.setModificado(MovilidadUtil.fechaCompletaHoy());
                nominaServerParamEjb.edit(nominaServerParam);

                MovilidadUtil.hideModal("wlvNominaServerParam");
                MovilidadUtil.addSuccessMessage("Registro editado con éxito");
            } else {
                nominaServerParam.setCreado(MovilidadUtil.fechaCompletaHoy());
                nominaServerParam.setEstadoReg(0);
                nominaServerParamEjb.create(nominaServerParam);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado con éxito");
            }

            consultar();
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {
        return null;
    }

    private void consultar() {
        lstNominaServerParams = nominaServerParamEjb.findAllByEstadoReg();
    }

    public NominaServerParam getNominaServerParam() {
        return nominaServerParam;
    }

    public void setNominaServerParam(NominaServerParam nominaServerParam) {
        this.nominaServerParam = nominaServerParam;
    }

    public NominaServerParam getSelected() {
        return selected;
    }

    public void setSelected(NominaServerParam selected) {
        this.selected = selected;
    }

    public List<NominaServerParam> getLstNominaServerParams() {
        return lstNominaServerParams;
    }

    public void setLstNominaServerParams(List<NominaServerParam> lstNominaServerParams) {
        this.lstNominaServerParams = lstNominaServerParams;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

}
