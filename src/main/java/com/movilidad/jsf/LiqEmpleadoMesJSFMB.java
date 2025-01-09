/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PmPeriodosLiquidacionFacadeLocal;
import com.movilidad.ejb.TblHistoricoIquidacionEmpleadoFacadeLocal;
import com.movilidad.ejb.TblLiquidacionEmpleadoMesFacadeLocal;
import com.movilidad.ejb.TblLiquidacionGrupoMesFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.PmPeriodosLiquidacion;
import com.movilidad.model.TblHistoricoIquidacionEmpleado;
import com.movilidad.model.TblLiquidacionGrupoMes;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.TblHistoricoLiquidacionEmpleadoDTO;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "liqEmplMesJSFMB")
@ViewScoped
public class LiqEmpleadoMesJSFMB implements Serializable {

    /**
     * Creates a new instance of LiqEmpleadoMesJSFMB
     */
    public LiqEmpleadoMesJSFMB() {
    }

    List<TblHistoricoIquidacionEmpleado> listaLiqEmplMes;
    List<TblLiquidacionGrupoMes> listaLiqGrupoMes;
    List<TblHistoricoLiquidacionEmpleadoDTO> listLiqEmplMesResumen;
    private List<PmPeriodosLiquidacion> list;
    private PmPeriodosLiquidacion periodoLiquidacion;

    private String anio;
    private String ultimoTab = "";
    private String mes;
    private boolean b_generarResporte = false;
    private boolean b_eliminarDatos = false;
    private int seleccionPeriodo = 0;

    @EJB
    private TblLiquidacionEmpleadoMesFacadeLocal LiqEmplMesEJB;
    @EJB
    private TblLiquidacionGrupoMesFacadeLocal LiqGrupoMesEJB;
    @EJB
    private TblHistoricoIquidacionEmpleadoFacadeLocal LiqHistoricoEmplMesEJB;
    @EJB
    private PmPeriodosLiquidacionFacadeLocal PmPeriodosLiquidacionFacadeLocal;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    @PostConstruct
    public void init() {
        list = PmPeriodosLiquidacionFacadeLocal.getAllActivo();
    }

    public boolean prepareFechas() {
        if (periodoLiquidacion == null) {
            return true;
        }
        return false;
    }

    public boolean prepareFechasParaValidar() {
        PmPeriodosLiquidacion obj = new PmPeriodosLiquidacion(); 
        obj = PmPeriodosLiquidacionFacadeLocal.findProximoPeriodo(periodoLiquidacion.getIdPmPeriodoLiquidacion());
        if(obj== null){
           return true;
        }else{
            return LiqHistoricoEmplMesEJB.findByRangoFechas(obj.getFechaInicio(), obj.getFechaFin()).isEmpty();
        } 
    }

    public void consultar() {
        listaLiqEmplMes = LiqHistoricoEmplMesEJB.findAllByFechaMes(periodoLiquidacion.getFechaInicio(), periodoLiquidacion.getFechaFin());
        listLiqEmplMesResumen = LiqHistoricoEmplMesEJB.findAllByFechaMesResumenPorGrupo(periodoLiquidacion.getFechaInicio(), periodoLiquidacion.getFechaFin());
        listaLiqGrupoMes = LiqGrupoMesEJB.findAllByRangoFechas(periodoLiquidacion.getFechaInicio(), periodoLiquidacion.getFechaFin());
        if (!ultimoTab.isEmpty()) {
            MovilidadUtil.updateComponent(ultimoTab);
        }
        if (listaLiqEmplMes.isEmpty()) {
            b_generarResporte = true;
            b_eliminarDatos = false;
        } else {
            b_eliminarDatos = true;
            b_generarResporte = false;
        }
    }

    public void reset() {
        b_generarResporte = false;
        b_eliminarDatos = false;
        anio = null;
        mes = null;
    }

    public void elimanarConsultar() {
        eliminarDatos();
        consultar();
    }

    @Transactional
    public void eliminarDatos() {
        if (prepareFechas()) {
            b_generarResporte = false;
            b_eliminarDatos = false;
            MovilidadUtil.addErrorMessage("Selecione el periodo del cual quiere generar reporte.");
            return;
        }
        
        int resp = 1;
       //int resp = validarAccionAlEliminar(); QUEMADO


        if (resp == 1) {
            int numEliminado = LiqHistoricoEmplMesEJB.eliminarDatos(periodoLiquidacion.getFechaInicio(), periodoLiquidacion.getFechaFin(), user.getUsername());
            int numEliminadoII = LiqEmplMesEJB.eliminarDatos(periodoLiquidacion.getFechaInicio(), periodoLiquidacion.getFechaFin(), user.getUsername());
            int numEliminadoIII = LiqGrupoMesEJB.eliminarDatos(periodoLiquidacion.getFechaInicio(), periodoLiquidacion.getFechaFin(), user.getUsername());
            MovilidadUtil.addSuccessMessage("Se eliminaron " + numEliminado + " datos satisfactoriamente.");
        } else {
            MovilidadUtil.addErrorMessage("No es posible eliminar este mes.");
        }

    }

    /**
     * Ayuda a validar si la acción de generar reporte es posible.
     *
     * @return Decuelve valor entero bandera.
     */
    public int validarAccion() {
        int mesActual = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int annoActual = Calendar.getInstance().get(Calendar.YEAR);
        Calendar mesSelecionado = Calendar.getInstance();
        mesSelecionado.setTime(periodoLiquidacion.getFechaFin());
        int mesSeleccionado = mesSelecionado.get(Calendar.MONTH);
        int annoSeleccionado = mesSelecionado.get(Calendar.YEAR);
        mesSeleccionado = mesSeleccionado + 1;
        /**
         * No es posible generar liquidacion para periodos posteriores al actual.
         */
        if (annoSeleccionado > annoActual) {
            return 0;
        }

        /**
         * Año seleccionado, es anterior al año actual.
         */
        if (annoSeleccionado < annoActual) {
            return 3;
        }
        /**
         * Mes seleccionado es igual al mes actual.
         */
        if (mesSeleccionado == mesActual) {
            return 1;
        }
        /**
         * Mes seleccionado es es menor al mes actual.
         */
        if (mesSeleccionado < mesActual) {
            return 2;
        }

        /**
         * No es posible generar liquidacion para meses posteriores al actual.
         */
        return 0;
    }

    /**
     * Ayuda a validar si la acción de generar reporte es posible.
     *
     * @return Decuelve valor entero bandera.
     */
    public int validarAccionAlEliminar() {
        int mesActual = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int annoActual = Calendar.getInstance().get(Calendar.YEAR);
        Calendar mesSelecionado = Calendar.getInstance();
        mesSelecionado.setTime(periodoLiquidacion.getFechaFin());
        int mesSeleccionado = mesSelecionado.get(Calendar.MONTH) + 1;
        int annoSeleccionado = mesSelecionado.get(Calendar.YEAR);
        mesSeleccionado = mesSeleccionado + 1;

        if (mesSeleccionado == 13) {
            mesSeleccionado = 1;
            annoSeleccionado++;
        }
        /**
         * No es posible eliminar liquidacion para meses posteriores al actual.
         */
        if (annoSeleccionado > annoActual) {
            return 0;
        }

        /**
         * Mes seleccionado, es anterior a los meses del año actual.
         */
        if (annoSeleccionado < annoActual) {
            return 3;
        }
        /**
         * Mes seleccionado es igual al mes actual.
         */
        if (mesSeleccionado == mesActual) {
            return 1;
        }

        /**
         * No es posible generar liquidacion para meses posteriores al actual.
         */
        return 0;
    }

//    @Transactional
    public void generarReporte() {
        if (prepareFechas()) {
            b_generarResporte = false;
            b_eliminarDatos = false;
            MovilidadUtil.addErrorMessage("Selecione el periodo del cual quiere generar reporte.");
            return;
        }
        int resp = validarAccion();
        System.out.println("Respuesta->>" + resp);
        if (resp == 0) {
            MovilidadUtil.addErrorMessage("No es posible gestionar datos para meses en un periodo posterior al mes actual.");
            return;
        }
        if ((resp == 2 || resp == 3) && !prepareFechasParaValidar()) {
            MovilidadUtil.addErrorMessage("No es posible generar el reporte, ya hay datos para meses posteriores al seleccionado.");
            return;
        }

        LiqEmplMesEJB.generarReporte(periodoLiquidacion.getFechaInicio(), periodoLiquidacion.getFechaFin(), user.getUsername()); 
        consultar();
        MovilidadUtil.addSuccessMessage("Se genero reporte para el mes seleccionado.");
    }

    public String master(Empleado empl) {
        String master = "";
        try {
            if (empl != null) {
                master = empl.getGenericaPmGrupoDetalleList().get(0).getIdGenericaPmGrupo().getNombreGrupo();
            }
        } catch (Exception e) {
            if (empl.getPmGrupoList() != null && empl.getGenericaPmGrupoList().size() > 0) {
                return empl.getGenericaPmGrupoList().get(0).getNombreGrupo();
            }
            return "N/A";
        }
        return master;
    }

    public BigDecimal withoutDecimal(BigDecimal valor) {
        if (valor != null) {
            return valor.setScale(0, BigDecimal.ROUND_DOWN);
        }
        return new BigDecimal(BigInteger.ZERO).setScale(0, BigDecimal.ROUND_DOWN);

    }
    
    public void seleccion() {
        periodoLiquidacion = PmPeriodosLiquidacionFacadeLocal.find(seleccionPeriodo); 
        consultar();
    }

    public List<TblHistoricoIquidacionEmpleado> getListaLiqEmplMes() {
        return listaLiqEmplMes;
    }

    public void setListaLiqEmplMes(List<TblHistoricoIquidacionEmpleado> listaLiqEmplMes) {
        this.listaLiqEmplMes = listaLiqEmplMes;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public boolean isB_generarResporte() {
        return b_generarResporte;
    }

    public void setB_generarResporte(boolean b_generarResporte) {
        this.b_generarResporte = b_generarResporte;
    }

    public boolean isB_eliminarDatos() {
        return b_eliminarDatos;
    }

    public void setB_eliminarDatos(boolean b_eliminarDatos) {
        this.b_eliminarDatos = b_eliminarDatos;
    }

    public List<TblHistoricoLiquidacionEmpleadoDTO> getListLiqEmplMesResumen() {
        return listLiqEmplMesResumen;
    }

    public void setListLiqEmplMesResumen(List<TblHistoricoLiquidacionEmpleadoDTO> listLiqEmplMesResumen) {
        this.listLiqEmplMesResumen = listLiqEmplMesResumen;
    }

    public List<TblLiquidacionGrupoMes> getListaLiqGrupoMes() {
        return listaLiqGrupoMes;
    }

    public void setListaLiqGrupoMes(List<TblLiquidacionGrupoMes> listaLiqGrupoMes) {
        this.listaLiqGrupoMes = listaLiqGrupoMes;
    }

    public List<PmPeriodosLiquidacion> getList() {
        return list;
    }

    public void setList(List<PmPeriodosLiquidacion> list) {
        this.list = list;
    }

    public PmPeriodosLiquidacion getPeriodoLiquidacion() {
        return periodoLiquidacion;
    }

    public void setPeriodoLiquidacion(PmPeriodosLiquidacion periodoLiquidacion) {
        this.periodoLiquidacion = periodoLiquidacion;
    }

    public int getSeleccionPeriodo() {
        return seleccionPeriodo;
    }

    public void setSeleccionPeriodo(int seleccionPeriodo) {
        this.seleccionPeriodo = seleccionPeriodo;
    }
    
        
}
