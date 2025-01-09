/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.PrgTc;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
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
 * @author solucionesit
 */
@Named(value = "rutasEjecuJSFMB")
@ViewScoped
public class RutasEjecutadasJSFManagedBean implements Serializable {

    private List<PrgTc> prglist;
    private List<PrgTc> prglistFiltre;
    private Date fechaDesde;
    private Date fechaHasta;
    private int totalEliminados;

    @EJB
    private PrgTcFacadeLocal prgTcEJB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    /**
     * Creates a new instance of RutasEjecutadasJSFManagedBean
     */
    public RutasEjecutadasJSFManagedBean() {
    }

    @PostConstruct
    public void init() {
        fechaDesde = MovilidadUtil.fechaHoy();
        fechaHasta = MovilidadUtil.fechaHoy();
    }

    public void consultar() {
        prglist = prgTcEJB.rutasEjecutadas(fechaDesde, fechaHasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public boolean filtroEliminado(PrgTc tc) {
        return tc.getEstadoOperacion() == 5
                && tc.getIdTaskType() != null
                && !isVac(tc.getIdTaskType().getIdPrgTarea())
                && !isVaccom(tc.getIdTaskType().getIdPrgTarea())
                && !isVacPrg(tc.getIdTaskType().getIdPrgTarea())
                && tc.getIdTaskType().getSumDistancia().equals(1)
                && tc.getDistance().doubleValue() > 0
                && tc.getIdTaskType().getComercial().equals(1);
    }

    public void totalEliminadosCount() {
        totalEliminados = 0;
        if (prglistFiltre != null) {
            for (PrgTc tc : prglistFiltre) {
                if (filtroEliminado(tc)) {
                    totalEliminados++;
                }
            }
        } else {
            if (prglist != null) {
                for (PrgTc tc : prglist) {
                    if (filtroEliminado(tc)) {
                        totalEliminados++;
                    }
                }
            }
        }
    }

    public String fecha(int opc) {
        if (opc == 1) {
            return Util.dateFormat(fechaDesde);
        } else {
            return Util.dateFormat(fechaHasta);
        }
    }

    private boolean isVacPrg(Integer idTarea) {
        return validVacPrg(idTarea + "");
    }

    private boolean isVaccom(Integer idTarea) {
        return validVaccom(idTarea + "");
    }

    private boolean isVac(Integer idTarea) {
        return validVac(idTarea + "");
    }

    private boolean validVaccom(String id) {
        for (String c : cargarArrayVaccoms()) {
            if (c.equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean validVac(String id) {
        for (String c : cargarArrayVac()) {
            if (c.equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean validVacPrg(String id) {
        for (String c : cargarArrayVacPrg()) {
            if (c.equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    private String[] cargarArrayVaccoms() {
        return SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_VACCOMS).split(",");
    }

    private String[] cargarArrayVac() {
        return SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_VAC).split(",");
    }

    private String[] cargarArrayVacPrg() {
        return SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_VAC_PRG).split(",");
    }

    public List<PrgTc> getPrglist() {
        return prglist;
    }

    public void setPrglist(List<PrgTc> prglist) {
        this.prglist = prglist;
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

    public List<PrgTc> getPrglistFiltre() {
        return prglistFiltre;
    }

    public void setPrglistFiltre(List<PrgTc> prglistFiltre) {
        this.prglistFiltre = prglistFiltre;
    }

    public int getTotalEliminados() {
        return totalEliminados;
    }

    public void setTotalEliminados(int totalEliminados) {
        this.totalEliminados = totalEliminados;
    }

}
