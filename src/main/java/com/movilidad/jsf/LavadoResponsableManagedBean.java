package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.LavadoResponsableFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.LavadoResponsable;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CloseEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "lavadoResponsableBean")
@ViewScoped
public class LavadoResponsableManagedBean implements Serializable {

    @EJB
    private LavadoResponsableFacadeLocal lavadoResponsableEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;

    private LavadoResponsable lavadoResponsable;
    private LavadoResponsable selected;

    private List<LavadoResponsable> lstLavadoResponsables;
    private List<Empleado> lstEmpleados;

    private final PrimeFaces current = PrimeFaces.current();
    private int idEmpleado;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public void nuevo() {
        idEmpleado = 0;
        lavadoResponsable = new LavadoResponsable();
        selected = null;
        current.focus("frmParametro:responsable");
    }

    public void editar() {
        if (selected == null) {
            current.ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("Debe seleccionar un registro");
            return;
        }

        lavadoResponsable = selected;
        idEmpleado = lavadoResponsable.getIdEmpleado().getIdEmpleado();
        current.executeScript("PF('parametroDlg').show();");
        current.focus("frmParametro:responsable");
    }

    public void guardar() {
        if (!validarDatos()) {
            if (lavadoResponsableEjb.find(idEmpleado) == null) {
                lavadoResponsable.setIdEmpleado(empleadoEjb.find(idEmpleado));
                lavadoResponsable.setCreado(new Date());
                lavadoResponsable.setEstadoReg(0);
                lavadoResponsable.setUsername(user.getUsername());
                lavadoResponsableEjb.create(lavadoResponsable);
                lstLavadoResponsables.add(lavadoResponsable);
                nuevo();
                MovilidadUtil.addSuccessMessage("Responsable registrado éxitosamente");
            } else {
                MovilidadUtil.addErrorMessage("El responsable que intenta registrar ya existe");
            }
        }

    }

    public void actualizar() {
        if (!validarDatos()) {
            lavadoResponsable.setIdEmpleado(empleadoEjb.find(idEmpleado));
            lavadoResponsable.setModificado(new Date());
            lavadoResponsable.setUsername(user.getUsername());
            lavadoResponsableEjb.edit(lavadoResponsable);
            current.ajax().update(":frmParametroList:dtParametros");
            current.executeScript("PF('parametroDlg').hide();");
//            selected = null;
            MovilidadUtil.addSuccessMessage("Responsable actualizado éxitosamente");
        }
    }

    public boolean validarDatos() {
        if (idEmpleado == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar responsable");
            return true;
        }
        return false;
    }

    public void handleClose(CloseEvent event) {
        selected = null;
    }

    public LavadoResponsable getLavadoResponsable() {
        return lavadoResponsable;
    }

    public void setLavadoResponsable(LavadoResponsable lavadoResponsable) {
        this.lavadoResponsable = lavadoResponsable;
    }

    public LavadoResponsable getSelected() {
        return selected;
    }

    public void setSelected(LavadoResponsable selected) {
        this.selected = selected;
    }

    public List<LavadoResponsable> getLstLavadoResponsables() {
        if (lstLavadoResponsables == null) {
            lstLavadoResponsables = lavadoResponsableEjb.findAll();
        }
        return lstLavadoResponsables;
    }

    public void setLstLavadoResponsables(List<LavadoResponsable> lstLavadoResponsables) {
        this.lstLavadoResponsables = lstLavadoResponsables;
    }

    public List<Empleado> getLstEmpleados() {
        lstEmpleados = empleadoEjb.findAll();
        return lstEmpleados;
    }

    public void setLstEmpleados(List<Empleado> lstEmpleados) {
        this.lstEmpleados = lstEmpleados;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }
}
