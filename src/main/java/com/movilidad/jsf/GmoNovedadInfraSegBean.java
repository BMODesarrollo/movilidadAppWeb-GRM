package com.movilidad.jsf;

import com.movilidad.ejb.GmoNovedadInfrastrucSeguimientoFacadeLocal;
import com.movilidad.model.GmoNovedadInfrastruc;
import com.movilidad.model.GmoNovedadInfrastrucSeguimiento;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "gmoNovedadInfraSegBean")
@ViewScoped
public class GmoNovedadInfraSegBean implements Serializable {

    @EJB
    private GmoNovedadInfrastrucSeguimientoFacadeLocal seguimientoEjb;

    @Inject
    private GmoNovedadInfrastructJSFBean novedadInfraBean;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private GmoNovedadInfrastrucSeguimiento seguimiento;
    private GmoNovedadInfrastrucSeguimiento selected;
    private GmoNovedadInfrastruc novedad;

    private boolean isEditing;

    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private List<GmoNovedadInfrastrucSeguimiento> lstSeguimientos;

    public void nuevo() {
        isEditing = false;
        seguimiento = new GmoNovedadInfrastrucSeguimiento();
        selected = null;
    }

    public void editar() {
        isEditing = true;
        seguimiento = selected;
    }

    public void prepararModulo() {
        
        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }
        
        MovilidadUtil.openModal("NovedadSeguimientoListDialog");
        
        novedad = novedadInfraBean.getNovedadInfrastruc();

        lstSeguimientos = seguimientoEjb.findAllByIdNovedad(novedad.getIdGmoNovedadInfrastruc());
    }

    public void guardar() {
        seguimiento.setIdGmoNovedadInfrastruc(novedad);
        seguimiento.setUsername(user.getUsername());

        if (isEditing) {
            seguimiento.setModificado(MovilidadUtil.fechaCompletaHoy());
            seguimientoEjb.edit(seguimiento);
            MovilidadUtil.addSuccessMessage("Registro modificado con éxito");
            MovilidadUtil.hideModal("novedadSeguimiento");
        } else {
            seguimiento.setEstadoReg(0);
            seguimiento.setCreado(MovilidadUtil.fechaCompletaHoy());
            seguimientoEjb.create(seguimiento);
            lstSeguimientos.add(seguimiento);
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado con éxito");
        }

        novedadInfraBean.consultar();
    }

    public boolean validarEditarSeguimiento(String userName) {
        if (user.getUsername().equals(userName)) {
            return false;
        }
//        for (GrantedAuthority g : user.getAuthorities()) {
//            if (g.getAuthority().equals("ROLE_PROFOP")) {
//                return false;
//            }
//        }
        return true;
    }

    public GmoNovedadInfrastrucSeguimiento getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(GmoNovedadInfrastrucSeguimiento seguimiento) {
        this.seguimiento = seguimiento;
    }

    public GmoNovedadInfrastrucSeguimiento getSelected() {
        return selected;
    }

    public void setSelected(GmoNovedadInfrastrucSeguimiento selected) {
        this.selected = selected;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<GmoNovedadInfrastrucSeguimiento> getLstSeguimientos() {
        return lstSeguimientos;
    }

    public void setLstSeguimientos(List<GmoNovedadInfrastrucSeguimiento> lstSeguimientos) {
        this.lstSeguimientos = lstSeguimientos;
    }

    public GmoNovedadInfrastruc getNovedad() {
        return novedad;
    }

    public void setNovedad(GmoNovedadInfrastruc novedad) {
        this.novedad = novedad;
    }

}
