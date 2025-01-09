/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccidenteAnalisisFacadeLocal;
import com.movilidad.ejb.AccidenteCostosFacadeLocal;
import com.movilidad.ejb.AccidenteFacadeLocal;
import com.movilidad.ejb.AccidenteLugarFacadeLocal;
import com.movilidad.ejb.AccidenteVehiculoFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.AccInmovilizado;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteAnalisis;
import com.movilidad.model.AccidenteCostos;
import com.movilidad.model.AccidenteLugar;
import com.movilidad.model.AccidenteVehiculo;
import com.movilidad.model.Empleado;
import com.movilidad.model.Vehiculo;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author cesar
 */
@Named(value = "accReporteMaestroJSF")
@ViewScoped
public class AccReporteMaestroJSF implements Serializable {

    @EJB
    private AccidenteFacadeLocal accidenteFacadeLocal;
    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;
    @EJB
    private AccidenteCostosFacadeLocal accidenteCostosFacadeLocal;
    @EJB
    private AccidenteAnalisisFacadeLocal accidenteAnalisisFacadeLocal;
    @EJB
    private AccidenteVehiculoFacadeLocal accidenteVehiculoFacadeLocal;
    @EJB
    private AccidenteLugarFacadeLocal accidenteLugarFacadeLocal;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<Accidente> listAccidente;

    private Date fechaIni;
    private Date fechaFin;
    private String c_codVeh;
    private Integer i_codOpe;
    private int i_auxNovDet;

    public AccReporteMaestroJSF() {
    }

    @PostConstruct
    public void init() {
        fechaIni = new Date();
        fechaFin = new Date();
        c_codVeh = null;
        i_codOpe = null;
        listAccidente = new ArrayList<>();
    }

    public void buscarAccidente() {
        try {
            int i_auxEmp = 0;
            int i_auxVeh = 0;
            if (i_codOpe != null) {
                Empleado empleado = empleadoFacadeLocal.getEmpleadoCodigoTM(i_codOpe);
                if (empleado != null) {
                    i_auxEmp = empleado.getIdEmpleado();
                }
            }
            if (!c_codVeh.isEmpty()) {
                Vehiculo vehiculo = vehiculoFacadeLocal.getVehiculo(c_codVeh, 0);
                if (vehiculo != null) {
                    i_auxVeh = vehiculo.getIdVehiculo();
                }
            }
            if (fechaFin.compareTo(fechaIni) < 0) {
                MovilidadUtil.addErrorMessage("Fecha fin no puede ser inferior a fecha inicio");
                return;
            }
            int idGopUF = unidadFuncionalSessionBean.getIdGopUnidadFuncional();
            listAccidente = accidenteFacadeLocal.findByArguments(i_auxVeh, i_auxEmp, i_auxNovDet, fechaIni, fechaFin, idGopUF);
        } catch (Exception e) {
        }
    }

    public String getAccCostos(Integer idAccidente) {
        if (idAccidente != null) {
            int iTotaDirecto = 0;
            List<AccidenteCostos> listAccidenteCostosDirectos = accidenteCostosFacadeLocal.estadoReg(1, idAccidente);
            if (listAccidenteCostosDirectos != null) {
                for (AccidenteCostos o : listAccidenteCostosDirectos) {
                    if (o.getValor() != null) {
                        iTotaDirecto = iTotaDirecto + o.getValor();
                    }
                }
            }
            int iTotaIndirecto = 0;
            List<AccidenteCostos> listAccidenteCostosIndirectos = accidenteCostosFacadeLocal.estadoReg(2, idAccidente);
            if (listAccidenteCostosIndirectos != null) {
                for (AccidenteCostos o : listAccidenteCostosIndirectos) {
                    if (o.getValor() != null) {
                        iTotaIndirecto = iTotaIndirecto + o.getValor();
                    }
                }
            }
            return String.valueOf(iTotaDirecto + iTotaIndirecto);
        }
        return "";
    }

    public String getAccidenteAnalisis(Integer idAccidente) {
        String a = "";
//        if (idAccidente != null) {
//            List<AccidenteAnalisis> listAccidenteAnalisis = accidenteAnalisisFacadeLocal.estadoReg(idAccidente);
//            for (AccidenteAnalisis aa : listAccidenteAnalisis) {
//                String raiz = aa.getIdCausaRaiz() != null ? aa.getIdCausaRaiz().getCausaRaiz() : "";
//                a = a + "-Arbol: " + aa.getIdCausaSub().getIdCausa().getIdAccArbol().getArbol()
//                        + " -Causa: " + aa.getIdCausaSub().getIdCausa().getCausa()
//                        + " -SubCausa: " + aa.getIdCausaSub().getSubcausa()
//                        + " -Raíz: " + raiz;
//            }
//        }
        return a;
    }

    public String getImovilizadoVehiculo(Integer idAccidente) {
        if (idAccidente != null) {
            List<AccidenteVehiculo> listAccidenteVehiculo = accidenteVehiculoFacadeLocal.estadoReg(idAccidente);
            if (listAccidenteVehiculo != null && !listAccidenteVehiculo.isEmpty()) {
                AccInmovilizado idAccInmovilizado = listAccidenteVehiculo.get(0).getIdAccInmovilizado();
                return idAccInmovilizado != null ? idAccInmovilizado.getInmovilizado() : "NO";
            }
        }
        return "NO";
    }

    public String getCoordenadas(Integer idAccidente) {
        if (idAccidente != null) {
            AccidenteLugar accidenteLugar = accidenteLugarFacadeLocal.buscarPorAccidente(idAccidente);
            if (accidenteLugar != null) {
                return accidenteLugar.getLatitude() != null ? "Lat: " + accidenteLugar.getLatitude() + " Log: " + accidenteLugar.getLongitude() : "NA";
            }
        }
        return "NA";
    }

    public String getTiempoReaccion(Accidente a) {
        return Util.getDiferenciaFechas(a.getFechaAcc(), a.getFechaAsistencia());
    }

    public void limpiar() {
        fechaFin = new Date();
        fechaIni = new Date();
        i_codOpe = null;
        c_codVeh = "";
        i_auxNovDet = 0;
    }

    public List<Accidente> getListAccidente() {
        return listAccidente;
    }

    public void setListAccidente(List<Accidente> listAccidente) {
        this.listAccidente = listAccidente;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getC_codVeh() {
        return c_codVeh;
    }

    public void setC_codVeh(String c_codVeh) {
        this.c_codVeh = c_codVeh;
    }

    public Integer getI_codOpe() {
        return i_codOpe;
    }

    public void setI_codOpe(Integer i_codOpe) {
        this.i_codOpe = i_codOpe;
    }

    public int getI_auxNovDet() {
        return i_auxNovDet;
    }

    public void setI_auxNovDet(int i_auxNovDet) {
        this.i_auxNovDet = i_auxNovDet;
    }

}
