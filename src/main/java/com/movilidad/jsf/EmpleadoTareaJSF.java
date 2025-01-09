/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.PrgTc;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author soluciones-it
 */
@Named(value = "empleadoTareaJSF")
@ViewScoped
public class EmpleadoTareaJSF implements Serializable {

    @EJB
    private PrgTcFacadeLocal prgTcFacadeLocal;
    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    // parametros de busqueda
    private String codidoEmp;
    private Date fecha;
    private String hora;
    // respuesta a la busqueda
    private PrgTc prgTc;
    private List<PrgTc> prgTcList;

    /**
     * Creates a new instance of EmpleadoTareaJSF
     */
    public EmpleadoTareaJSF() {
        fecha = new Date();
    }

    public void consultarProximaTareaEmpleado() {
        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar unidad funcional");
            return;
        }
        if (hora == null) {
            MovilidadUtil.addErrorMessage("Hora es requerido");
            return;
        }
        if (hora.isEmpty()) {
            MovilidadUtil.addErrorMessage("Hora es requerido");
            return;
        }
        if (codidoEmp == null) {
            MovilidadUtil.addErrorMessage("Código empleado es requerido");
            return;
        }
        if (codidoEmp.isEmpty()) {
            MovilidadUtil.addErrorMessage("Código empleado es requerido");
            return;
        }
        Empleado emp = empleadoFacadeLocal.findByCodigoTM(Integer.parseInt(codidoEmp));
        if (emp == null) {
            MovilidadUtil.addErrorMessage("Empleado no disponible");
            return;
        }
        String horaAux = hora;
        int toSectToHora = MovilidadUtil.toSecs(hora);
        int holgura = MovilidadUtil.toSecs(ConstantsUtil.HOLGURA_MINUTOS_TAREA_OPERADOR);
        hora = MovilidadUtil.toTimeSec(toSectToHora - holgura);
        prgTc = prgTcFacadeLocal
                .getPrgTcByIdEmpleadoTmAndFechaByIdGopUF(emp.getIdEmpleado(),
                        fecha, hora, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        hora = horaAux;
        if (prgTc == null) {
            MovilidadUtil.addErrorMessage("Empleado no cuenta con servicios");
            return;
        }
        MovilidadUtil.addSuccessMessage("Servicio encontrado");
    }

    public void consultarServiciosEmpleado() {
        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar unidad funcional");
            return;
        }
        if (codidoEmp == null) {
            MovilidadUtil.addErrorMessage("Código empleado es requerido");
            return;
        }
        if (codidoEmp.isEmpty()) {
            MovilidadUtil.addErrorMessage("Código empleado es requerido");
            return;
        }
        Empleado emp = empleadoFacadeLocal.findByCodigoTM(Integer.parseInt(codidoEmp));
        if (emp == null) {
            MovilidadUtil.addErrorMessage("Empleado no disponible");
            return;
        }
        prgTcList = prgTcFacadeLocal.getPrgTcByIdEmpleadoAndFechaAndIdGopUF(emp.getIdEmpleado(),
                fecha, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (prgTcList.isEmpty()) {
            MovilidadUtil.addErrorMessage("Empleado no cuenta con servicios");
            return;
        }
        MovilidadUtil.addSuccessMessage("Servicio encontrado");
    }

    public void reset() {
        codidoEmp = null;
        prgTc = null;
        prgTcList = null;
    }

    public boolean enEjecucion(PrgTc p) throws ParseException {
        if (p == null) {
            return false;
        }
        return MovilidadUtil.fechaBetween(fecha, p.getTimeOrigin(), p.getTimeDestiny()) && p.getEstadoOperacion() != 5;
    }

    public String alarStyleClass(PrgTc p) throws ParseException {
        if (p == null) {
            return null;
        }
        if (MovilidadUtil.fechaBetween(fecha, p.getTimeOrigin(), p.getTimeDestiny())
                && p.getEstadoOperacion() != 5 && p.getEstadoOperacion() != 8) {
            return "rowGrenStyle";
        }
        if (p.getEstadoOperacion() == 2) {
            return "rowOrangeStyle";
        }
        if (p.getEstadoOperacion() == 5 || p.getEstadoOperacion() == 8) {
            return "rowRedStyle";
        }
        if (p.getEstadoOperacion() == 7) {
            return "rowGrisOscuroStyle";
        }
        if (p.getEstadoOperacion() == 6) {
            return "rowTurquesaStyle";
        }
        if (p.getEstadoOperacion() == 3 || p.getEstadoOperacion() == 4) {
            return "rowBlueStyle";
        }
        return null;
    }

    public String getCodidoEmp() {
        return codidoEmp;
    }

    public void setCodidoEmp(String codidoEmp) {
        this.codidoEmp = codidoEmp;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public PrgTc getPrgTc() {
        return prgTc;
    }

    public void setPrgTc(PrgTc prgTc) {
        this.prgTc = prgTc;
    }

    public List<PrgTc> getPrgTcList() {
        return prgTcList;
    }

    public void setPrgTcList(List<PrgTc> prgTcList) {
        this.prgTcList = prgTcList;
    }

}
