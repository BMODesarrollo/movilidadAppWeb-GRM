package com.movilidad.jsf;

import com.movilidad.ejb.PrgServbusesFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.PrgTc;
import com.movilidad.model.Vehiculo;
import com.movilidad.util.beans.ReporteServbusesPatioFin;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "reporteServbusesPatioFinBean")
@ViewScoped
public class ReporteServbusesPatioFinManagedBean implements Serializable {

    @EJB
    private PrgServbusesFacadeLocal prgServbusesEjb;
    @EJB
    private VehiculoFacadeLocal vehiculoEjb;

    private List<ReporteServbusesPatioFin> lstReporteServbusesPatioFin;
    private PrgTc prgTcsSinAsignar;
    private List<Vehiculo> lstVehiculos;
    private List<PrgTc> lstSinAsignar;
    private List<String> lstVehiculosString;
    private List<String> lstServbuses;

    private ReporteServbusesPatioFin reporteServbusesPatioFin;
    private Date fecha;

    public void obtenerReporte() {
        lstReporteServbusesPatioFin = new ArrayList<>();
        lstVehiculos = vehiculoEjb.getVehiclosActivo();
        ReporteServbusesPatioFin rspf = null;
        lstSinAsignar = new ArrayList<>();
        for (Vehiculo v : lstVehiculos) {
            prgTcsSinAsignar = new PrgTc();
            prgTcsSinAsignar = prgServbusesEjb.getPrgtcSinAsignar(v.getIdVehiculo());

            if (prgTcsSinAsignar != null) {
                rspf = new ReporteServbusesPatioFin(prgTcsSinAsignar.getFecha(),
                        prgTcsSinAsignar.getIdVehiculo().getCodigo(),
                        prgTcsSinAsignar.getServbus(),
                        prgTcsSinAsignar.getToStop().getName(),
                        prgTcsSinAsignar.getTimeDestiny());
            } else {
                rspf = new ReporteServbusesPatioFin(null, v.getCodigo(), "", "", "");
            }

            lstReporteServbusesPatioFin.add(rspf);
        }
    }

    public List<ReporteServbusesPatioFin> getLstReporteServbusesPatioFin() {
        return lstReporteServbusesPatioFin;
    }

    public void setLstReporteServbusesPatioFin(List<ReporteServbusesPatioFin> lstReporteServbusesPatioFin) {
        this.lstReporteServbusesPatioFin = lstReporteServbusesPatioFin;
    }

    public List<Vehiculo> getLstVehiculos() {
        return lstVehiculos;
    }

    public void setLstVehiculos(List<Vehiculo> lstVehiculos) {
        this.lstVehiculos = lstVehiculos;
    }

    public PrgTc getPrgTcsSinAsignar() {
        return prgTcsSinAsignar;
    }

    public void setPrgTcsSinAsignar(PrgTc PrgTcsSinAsignar) {
        this.prgTcsSinAsignar = PrgTcsSinAsignar;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ReporteServbusesPatioFin getReporteServbusesPatioFin() {
        return reporteServbusesPatioFin;
    }

    public void setReporteServbusesPatioFin(ReporteServbusesPatioFin reporteServbusesPatioFin) {
        this.reporteServbusesPatioFin = reporteServbusesPatioFin;
    }

    public List<String> getLstVehiculosString() {
        return lstVehiculosString;
    }

    public void setLstVehiculosString(List<String> lstVehiculosString) {
        this.lstVehiculosString = lstVehiculosString;
    }

    public List<String> getLstServbuses() {
        return lstServbuses;
    }

    public void setLstServbuses(List<String> lstServbuses) {
        this.lstServbuses = lstServbuses;
    }

    public List<PrgTc> getLstSinAsignar() {
        return lstSinAsignar;
    }

    public void setLstSinAsignar(List<PrgTc> lstSinAsignar) {
        this.lstSinAsignar = lstSinAsignar;
    }
}
