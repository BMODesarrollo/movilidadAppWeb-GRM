package com.movilidad.jsf;

import com.movilidad.ejb.MttoTareaFacadeLocal;
import com.movilidad.ejb.PrgAsignacionFacadeLocal;
import com.movilidad.model.MttoTarea;
import com.movilidad.model.PrgAsignacion;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "tareasProgramadasBean")
@ViewScoped
public class ReporteTareasProgramadasBean implements Serializable {

    @EJB
    private PrgAsignacionFacadeLocal asignacionEjb;
    @EJB
    private MttoTareaFacadeLocal mttoTareaEjb;

    //
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private Date desde;
    private Date hasta;

    private List<PrgAsignacion> lstPrgAsignacion;
    private List<MttoTarea> lstMttoTareas;
    private int idGopUF;

    @PostConstruct
    public void init() {
        cargarUF();
        lstMttoTareas = mttoTareaEjb.findAllByEstadoReg();

        desde = MovilidadUtil.fechaHoy();
        hasta = MovilidadUtil.fechaHoy();
    }

    public void consultar() {
        cargarUF();
        lstPrgAsignacion = asignacionEjb.getTareasProgramadasMtto(desde, hasta, idGopUF);
        if (lstPrgAsignacion == null || lstPrgAsignacion.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron tareas de Mantenimiento para el rango de fechas seleccionados");
        }
    }

    private void cargarUF() {
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public List<PrgAsignacion> getLstPrgAsignacion() {
        return lstPrgAsignacion;
    }

    public void setLstPrgAsignacion(List<PrgAsignacion> lstPrgAsignacion) {
        this.lstPrgAsignacion = lstPrgAsignacion;
    }

    public List<MttoTarea> getLstMttoTareas() {
        return lstMttoTareas;
    }

    public void setLstMttoTareas(List<MttoTarea> lstMttoTareas) {
        this.lstMttoTareas = lstMttoTareas;
    }

}
