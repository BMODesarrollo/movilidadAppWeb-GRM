package com.movilidad.jsf;

import com.movilidad.ejb.PrgServbusesFacadeLocal;
import com.movilidad.util.beans.ReporteServbuses;
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
@Named(value = "reporteServbusesBean")
@ViewScoped
public class ReporteServbusesManagedBean implements Serializable {
    
    @EJB
    private PrgServbusesFacadeLocal prgServbusesEjb;

    private List<ReporteServbuses> lstReporteServbuses;

    private Date fecha;
    
    public void obtenerReporte(){
        lstReporteServbuses = prgServbusesEjb.getReporteServbuses(fecha);
    }
    
    public List<ReporteServbuses> getLstReporteServbuses() {
        return lstReporteServbuses;
    }

    public void setLstReporteServbuses(List<ReporteServbuses> lstReporteServbuses) {
        this.lstReporteServbuses = lstReporteServbuses;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
