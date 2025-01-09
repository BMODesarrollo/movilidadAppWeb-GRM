package com.movilidad.jsf;

import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.PrgSercon;
import com.movilidad.model.PrgTc;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.ConsultaAusentismo;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "consultaAusentismoAsignacionBean")
@ViewScoped
public class ConsultaAusentismoAsignacionBean implements Serializable {

    @EJB
    private PrgTcFacadeLocal prgTcEjb;
    @EJB
    private PrgSerconFacadeLocal prgSerconEjb;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private ConsultaAusentismoBean consultaAusentismoBean;

    private ConsultaAusentismo consultaAusentismo;
    private PrgTc prgTc;
    private Date fecha;
    private Date fechaDiaPosterior;
    private String codigoOperador;
    private String sercon;
    private String nombreEmpleadoSercon;
    private String nombreEmpleado;
    private String tabla;
    private String ruta;

    private PrgSercon prgSercon;
    private List<PrgTc> operadoresDisponibles;

    private final UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Método que se encarga de cargar los datos de un modal
     */
    public void prepareAsignarOperador() {

        fechaDiaPosterior = MovilidadUtil.sumarDias(consultaAusentismoBean.getFecha(), 1);

        if (consultaAusentismo == null) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar un registro");
            return;
        }

        codigoOperador = String.valueOf(consultaAusentismo.getDiaSiguiente().getIdEmpleado().getCodigoTm());

        prgSercon = prgSerconEjb.getPrgSerconByCodigoTMAndUnidadFuncional(
                codigoOperador, fechaDiaPosterior,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (prgSercon == null) {
            MovilidadUtil.addAdvertenciaMessage("El operador NO cuenta con sercon para el día siguente");
            return;
        }

        String timeDestiny = prgSercon.getHfinTurno3() != null ? prgSercon.getHfinTurno3()
                : prgSercon.getHfinTurno2() != null ? prgSercon.getHfinTurno2()
                : prgSercon.getTimeDestiny();

        operadoresDisponibles = prgTcEjb.operadoresDisponiblesDiaPosterior(
                fechaDiaPosterior,
                timeDestiny,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()
        );

        if (operadoresDisponibles == null || operadoresDisponibles.isEmpty()) {
            MovilidadUtil.addAdvertenciaMessage("NO se encontraron operadores disponibles para el día siguente");
            return;
        }

        sercon = prgSercon.getSercon();
        fecha = prgSercon.getFecha();
        nombreEmpleadoSercon = prgSercon.getIdEmpleado().getNombres() + " "
                + prgSercon.getIdEmpleado().getApellidos();

        PrgTc prgTcTablaRuta = prgTcEjb.obtenerFechaYRutaDiaPosterior(
                sercon, fechaDiaPosterior,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()
        );

        if (prgTcTablaRuta == null) {
            MovilidadUtil.addAdvertenciaMessage("El operador NO cuenta con tabla y ruta para el día siguente");
            return;
        }

        tabla = String.valueOf(prgTcTablaRuta.getTabla());
        ruta = prgTcTablaRuta.getIdRuta().getName();

        MovilidadUtil.openModal("wlVAsignacionOperador");

    }

    /**
     * Método encargado de cargar en la variables: prgTc al seleccionar un
     * registro el tabla de operadores disponibles.
     *
     * @param event
     * @throws Exception
     */
    public void onRowSelectOpDispo(final SelectEvent event) throws Exception {
        setPrgTc((PrgTc) event.getObject());
        nombreEmpleado = prgTc.getIdEmpleado().getNombres() + " "
                + prgTc.getIdEmpleado().getApellidos();
    }

    public void consultarSercon() {
        prgSercon = prgSerconEjb.getPrgSerconByCodigoTMAndUnidadFuncional(
                codigoOperador, fechaDiaPosterior,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (prgSercon == null) {
            MovilidadUtil.addAdvertenciaMessage("El operador NO cuenta con sercon para el día siguente");
            return;
        }

        String timeDestiny = prgSercon.getHfinTurno3() != null ? prgSercon.getHfinTurno3()
                : prgSercon.getHfinTurno2() != null ? prgSercon.getHfinTurno2()
                : prgSercon.getTimeDestiny();

        operadoresDisponibles = prgTcEjb.operadoresDisponiblesDiaPosterior(
                fechaDiaPosterior,
                timeDestiny,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()
        );

        if (operadoresDisponibles == null || operadoresDisponibles.isEmpty()) {
            MovilidadUtil.addAdvertenciaMessage("NO se encontraron operadores disponibles para el día siguente");
            return;
        }

        sercon = prgSercon.getSercon();
        nombreEmpleadoSercon = prgSercon.getIdEmpleado().getNombres() + " "
                + prgSercon.getIdEmpleado().getApellidos();
        
        PrgTc prgTcTablaRuta = prgTcEjb.obtenerFechaYRutaDiaPosterior(
                sercon, fechaDiaPosterior,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()
        );

        if (prgTcTablaRuta == null) {
            MovilidadUtil.addAdvertenciaMessage("El operador NO cuenta con tabla y ruta para el día siguente");
            return;
        }

        
        tabla = String.valueOf(prgTcTablaRuta.getTabla());
        ruta = prgTcTablaRuta.getIdRuta().getName();
        
        MovilidadUtil.updateComponent("frmAsignacionAusentismo:pnlDatosSercon");
        MovilidadUtil.updateComponent("frmAsignacionAusentismo:tbOpDispo");
    }

    public void asignarOperador() {
        prgTcEjb.realizarCambioOperacion(
                prgTc.getIdEmpleado().getIdEmpleado(),
                prgSercon.getIdEmpleado().getIdEmpleado(), sercon, fechaDiaPosterior
        );

        /**
         * TODO: realizar cambios en prg_sercon del empleado seleccionado
         * agregar las horas de los turnos a las horas reales del operador
         * disponible
         */
        PrgSercon prgSerconDispo = prgSerconEjb.getPrgSerconByCodigoTMAndUnidadFuncional(
                String.valueOf(prgTc.getIdEmpleado().getCodigoTm()),
                fechaDiaPosterior,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (prgSerconDispo == null) {
            MovilidadUtil.addAdvertenciaMessage("NO se encontró sercon registrado para el día siguente");
            return;
        }

        prgSerconDispo.setRealTimeOrigin(prgSercon.getTimeOrigin());
        prgSerconDispo.setRealTimeDestiny(prgSercon.getTimeDestiny());
        prgSerconDispo.setRealHiniTurno2(prgSercon.getHiniTurno2());
        prgSerconDispo.setRealHfinTurno2(prgSercon.getHfinTurno2());
        prgSerconDispo.setRealHiniTurno3(prgSercon.getHiniTurno3());
        prgSerconDispo.setRealHfinTurno3(prgSercon.getHfinTurno3());

        prgSerconDispo.setDiurnas(prgSercon.getDiurnas());
        prgSerconDispo.setNocturnas(prgSercon.getNocturnas());
        prgSerconDispo.setExtraDiurna(prgSercon.getExtraDiurna());
        prgSerconDispo.setExtraNocturna(prgSercon.getExtraNocturna());
        prgSerconDispo.setFestivoDiurno(prgSercon.getFestivoDiurno());
        prgSerconDispo.setFestivoNocturno(prgSercon.getFestivoNocturno());
        prgSerconDispo.setFestivoExtraDiurno(prgSercon.getFestivoExtraDiurno());
        prgSerconDispo.setFestivoExtraNocturno(prgSercon.getFestivoExtraNocturno());
        prgSerconDispo.setDominicalCompDiurnas(prgSercon.getDominicalCompDiurnas());
        prgSerconDispo.setDominicalCompNocturnas(prgSercon.getDominicalCompNocturnas());
        prgSerconDispo.setDominicalCompDiurnaExtra(prgSercon.getDominicalCompDiurnaExtra());
        prgSerconDispo.setDominicalCompNocturnaExtra(prgSercon.getDominicalCompNocturnaExtra());
        prgSerconDispo.setModificado(MovilidadUtil.fechaCompletaHoy());
        prgSerconDispo.setPrgModificada(1);
        prgSerconDispo.setUserGenera(user.getUsername());
        prgSerconDispo.setFechaGenera(MovilidadUtil.fechaCompletaHoy());
        prgSerconDispo.setAutorizado(-1);

        prgSerconEjb.edit(prgSerconDispo);

        MovilidadUtil.hideModal("wlVAsignacionOperador");
        MovilidadUtil.addSuccessMessage("Operador asignado éxitosamente");
        MovilidadUtil.runScript("cargarListadoConsultaAusentismo();");
    }

    public ConsultaAusentismo getConsultaAusentismo() {
        return consultaAusentismo;
    }

    public void setConsultaAusentismo(ConsultaAusentismo consultaAusentismo) {
        this.consultaAusentismo = consultaAusentismo;
    }

    public PrgSercon getPrgSercon() {
        return prgSercon;
    }

    public void setPrgSercon(PrgSercon prgSercon) {
        this.prgSercon = prgSercon;
    }

    public List<PrgTc> getOperadoresDisponibles() {
        return operadoresDisponibles;
    }

    public void setOperadoresDisponibles(List<PrgTc> operadoresDisponibles) {
        this.operadoresDisponibles = operadoresDisponibles;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSercon() {
        return sercon;
    }

    public void setSercon(String sercon) {
        this.sercon = sercon;
    }

    public PrgTc getPrgTc() {
        return prgTc;
    }

    public void setPrgTc(PrgTc prgTc) {
        this.prgTc = prgTc;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getNombreEmpleadoSercon() {
        return nombreEmpleadoSercon;
    }

    public void setNombreEmpleadoSercon(String nombreEmpleadoSercon) {
        this.nombreEmpleadoSercon = nombreEmpleadoSercon;
    }

    public String getCodigoOperador() {
        return codigoOperador;
    }

    public void setCodigoOperador(String codigoOperador) {
        this.codigoOperador = codigoOperador;
    }

    public Date getFechaDiaPosterior() {
        return fechaDiaPosterior;
    }

    public void setFechaDiaPosterior(Date fechaDiaPosterior) {
        this.fechaDiaPosterior = fechaDiaPosterior;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

}
