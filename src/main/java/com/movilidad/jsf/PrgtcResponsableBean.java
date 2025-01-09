package com.movilidad.jsf;

import com.movilidad.ejb.PrgTcResponsableFacadeLocal;
import com.movilidad.model.PrgTcResponsable;
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
@Named(value = "prgTcResponsableBean")
@ViewScoped
public class PrgtcResponsableBean implements Serializable {

    @EJB
    private PrgTcResponsableFacadeLocal prgTcResponsableEjb;

    private PrgTcResponsable prgTcResponsable;
    private PrgTcResponsable selected;
    private String responsable;

    private boolean isEditing;

    private List<PrgTcResponsable> lstPrgTcResponsables;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstPrgTcResponsables = prgTcResponsableEjb.getPrgResponsables();
    }

    public void nuevo() {
        responsable = "";
        prgTcResponsable = new PrgTcResponsable();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        responsable = selected.getResponsable();
        prgTcResponsable = selected;
        isEditing = true;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            prgTcResponsable.setResponsable(responsable);
            prgTcResponsable.setUsername(user.getUsername());

            if (isEditing) {

                prgTcResponsable.setModificado(MovilidadUtil.fechaCompletaHoy());
                prgTcResponsableEjb.edit(prgTcResponsable);

                PrimeFaces.current().executeScript("PF('wlvPrgTcResponsable').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                prgTcResponsable.setEstadoReg(0);
                prgTcResponsable.setCreado(MovilidadUtil.fechaCompletaHoy());
                prgTcResponsableEjb.create(prgTcResponsable);
                lstPrgTcResponsables.add(prgTcResponsable);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public String validarDatos() {
        if (isEditing) {
            if (prgTcResponsableEjb.findByResponsable(selected.getIdPrgTcResponsable(), responsable.trim()) != null) {
                return "YA existe un registro con el responsable a ingresar";
            }
        } else {
            if (!lstPrgTcResponsables.isEmpty()) {
                if (prgTcResponsableEjb.findByResponsable(0, responsable.trim()) != null) {
                    return "YA existe un registro con el responsable a ingresar";
                }
            }
        }

        return null;
    }

    public PrgTcResponsable getPrgTcResponsable() {
        return prgTcResponsable;
    }

    public void setPrgTcResponsable(PrgTcResponsable prgTcResponsable) {
        this.prgTcResponsable = prgTcResponsable;
    }

    public PrgTcResponsable getSelected() {
        return selected;
    }

    public void setSelected(PrgTcResponsable selected) {
        this.selected = selected;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<PrgTcResponsable> getLstPrgTcResponsables() {
        return lstPrgTcResponsables;
    }

    public void setLstPrgTcResponsables(List<PrgTcResponsable> lstPrgTcResponsables) {
        this.lstPrgTcResponsables = lstPrgTcResponsables;
    }

}
