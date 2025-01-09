package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoEstadoFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.PrgOperadorInactivoFacadeLocal;
import com.movilidad.model.Accidente;
import com.movilidad.model.Empleado;
import com.movilidad.model.EmpleadoEstado;
import com.movilidad.model.PrgOperadorInactivo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "cambioOperadorBean")
@ViewScoped
public class CambioOperadorJSFManagedBean implements Serializable {

    @EJB
    private PrgOperadorInactivoFacadeLocal operadorInactivoEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private EmpleadoEstadoFacadeLocal empleadoEstadoEjb;

    private List<PrgOperadorInactivo> lstOperadoresInactivos;
    private List<EmpleadoEstado> lstEstadoEmpleados;

    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private PrgOperadorInactivo operadorInactivo;
    private PrgOperadorInactivo selected;

    private int s_codOperador;
    private int i_EstadoOperador;

    public void nuevo() {
        operadorInactivo = new PrgOperadorInactivo();
        s_codOperador = 0;
        i_EstadoOperador = 0;
        selected = null;
    }

    public void editar() {
        operadorInactivo = selected;
        s_codOperador = selected.getIdEmpleado().getCodigoTm();
        i_EstadoOperador = selected.getIdEmpleadoEstado().getIdEmpleadoEstado();
    }

    public void guardar() {
        if (operadorInactivo.getIdEmpleado() == null) {
            PrimeFaces.current().ajax().update("frmCambioOperador:messages");
            MovilidadUtil.addErrorMessage("Debe cargar un empleado");
            return;
        }
        if (Util.validarFechaCambioEstado(operadorInactivo.getFromDate(), operadorInactivo.getToDate())) {
            PrimeFaces.current().ajax().update("frmCambioOperador:messages");
            MovilidadUtil.addErrorMessage("La fecha inicio debe ser menor a la fecha fin");
            return;
        }

        cambiarEstadoEmpleado(operadorInactivo.getIdEmpleado());
        operadorInactivo.setIdEmpleadoEstado(new EmpleadoEstado(i_EstadoOperador));
        operadorInactivo.setUsername(user.getUsername());
        operadorInactivo.setCreado(new Date());
        operadorInactivo.setActivo(0);
        operadorInactivoEjb.create(operadorInactivo);
        nuevo();
        MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
    }

    public void actualizar() {
        if (operadorInactivo.getIdEmpleado() == null) {
            PrimeFaces.current().ajax().update("frmCambioOperador:messages");
            MovilidadUtil.addErrorMessage("Debe ingresar un operador");
            return;
        }
        if (i_EstadoOperador == 1) {
            PrimeFaces.current().ajax().update("frmCambioOperador:messages");
            MovilidadUtil.addErrorMessage("Para activar el operador debe seleccionar el botón en la columna de acciones");
            return;
        }

        EmpleadoEstado empleadoEstado = empleadoEstadoEjb.find(i_EstadoOperador);

        cambiarEstadoEmpleado(operadorInactivo.getIdEmpleado());
        operadorInactivo.setActivo(0);
        operadorInactivo.setIdEmpleadoEstado(empleadoEstado);
        operadorInactivo.setUsername(user.getUsername());
        operadorInactivo.setModificado(new Date());
        operadorInactivoEjb.edit(operadorInactivo);
        MovilidadUtil.addSuccessMessage("Registro cambiado éxitosamente");
        MovilidadUtil.hideModal("cambioOperador");
    }

    public void cambiarEstadoEmpleado(Empleado empleado) {
        empleado.setIdEmpleadoEstado(empleadoEstadoEjb.find(i_EstadoOperador));
        empleadoEjb.edit(empleado);
    }

    public void prepareActivarOperador() {
        selected.setObservaciones("");
        s_codOperador = selected.getIdEmpleado().getCodigoTm();
        i_EstadoOperador = selected.getIdEmpleado().getIdEmpleadoEstado().getIdEmpleadoEstado();
    }

    public void activarOperador() {
        Empleado empleado = selected.getIdEmpleado();
        empleado.setIdEmpleadoEstado(new EmpleadoEstado(1));
        empleadoEjb.edit(empleado);

        selected.setActivo(1);
        selected.setIdEmpleadoEstado(new EmpleadoEstado(1));
        selected.setUsrHabilita(user.getUsername());
        selected.setModificado(new Date());
        operadorInactivoEjb.edit(selected);
        PrimeFaces.current().executeScript("PF('activarOperador').hide();");
        MovilidadUtil.addSuccessMessage("Operador activado éxitosamente");
        selected = null;
    }

    public boolean verificarEstado(Date f1) {
        Date f2 = new Date();
        return f2.compareTo(f1) > 0;
    }

    public void buscarOperador() {
        PrimeFaces current = PrimeFaces.current();
        if (s_codOperador != 0) {
            Empleado e = empleadoEjb.getEmpleadoCodigoTM(s_codOperador);
            if (e != null) {
                operadorInactivo.setIdEmpleado(e);
                operadorInactivo.setIdEmpleadoEstado(e.getIdEmpleadoEstado());
                current.ajax().update("frmCambioOperador:messages");
                MovilidadUtil.addSuccessMessage("Operador encontrado");
            } else {
                operadorInactivo.setIdEmpleado(null);
                current.ajax().update("frmCambioOperador:messages");
                MovilidadUtil.addErrorMessage("Operador no encontrado");
            }
        } else {
            operadorInactivo.setIdEmpleado(null);
            current.ajax().update("frmCambioOperador:messages");
            MovilidadUtil.addErrorMessage("Ingrese el código del operador");
        }
    }

    public List<PrgOperadorInactivo> getLstOperadoresInactivos() {
        lstOperadoresInactivos = operadorInactivoEjb.findAll();
        return lstOperadoresInactivos;
    }

    public void setLstOperadoresInactivos(List<PrgOperadorInactivo> lstOperadoresInactivos) {
        this.lstOperadoresInactivos = lstOperadoresInactivos;
    }

    public PrgOperadorInactivo getOperadorInactivo() {
        return operadorInactivo;
    }

    public void setOperadorInactivo(PrgOperadorInactivo operadorInactivo) {
        this.operadorInactivo = operadorInactivo;
    }

    public PrgOperadorInactivo getSelected() {
        return selected;
    }

    public void setSelected(PrgOperadorInactivo selected) {
        this.selected = selected;
    }

    public int getS_codOperador() {
        return s_codOperador;
    }

    public void setS_codOperador(int s_codOperador) {
        this.s_codOperador = s_codOperador;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public int getI_EstadoOperador() {
        return i_EstadoOperador;
    }

    public void setI_EstadoOperador(int i_EstadoOperador) {
        this.i_EstadoOperador = i_EstadoOperador;
    }

    public List<EmpleadoEstado> getLstEstadoEmpleados() {
        lstEstadoEmpleados = empleadoEstadoEjb.findAll();
        return lstEstadoEmpleados;
    }

    public void setLstEstadoEmpleados(List<EmpleadoEstado> lstEstadoEmpleados) {
        this.lstEstadoEmpleados = lstEstadoEmpleados;
    }
}
