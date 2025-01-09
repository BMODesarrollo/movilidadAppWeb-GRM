package com.movilidad.jsf;

import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.PrgTc;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "reporteConsultaProgramacionBean")
@ViewScoped
public class ReporteConsultaProgramacionBean implements Serializable {

    @EJB
    private PrgTcFacadeLocal prgTcEjb;

    private Date fechaDesde = MovilidadUtil.fechaCompletaHoy();
    private Date fechaHasta = MovilidadUtil.fechaCompletaHoy();
    private String codOperador;

    private List<PrgTc> lstPrgTcs;

    public void consultar() {
        lstPrgTcs = prgTcEjb.obtenerConsultaProgramacion(fechaDesde, fechaHasta, codOperador);
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getCodOperador() {
        return codOperador;
    }

    public void setCodOperador(String codOperador) {
        this.codOperador = codOperador;
    }

    public List<PrgTc> getLstPrgTcs() {
        return lstPrgTcs;
    }

    public void setLstPrgTcs(List<PrgTc> lstPrgTcs) {
        this.lstPrgTcs = lstPrgTcs;
    }

}
