/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GenericaPmLiquidacionFacadeLocal;
import com.movilidad.ejb.GenericaPmLiquidacionHistFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.GenericaPmLiquidacionHist;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
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
@Named(value = "liqEmplMesGenJSFMB")
@ViewScoped
public class LiqEmpleadoMesGenericaJSFMB implements Serializable {

    /**
     * Creates a new instance of LiqEmpleadoMesJSFMB
     */
    public LiqEmpleadoMesGenericaJSFMB() {
    }

    List<GenericaPmLiquidacionHist> listaLiqEmplMes;
//    List<TblLiquidacionEmpleadoMesAux> listLiqEmplMes;

    private String anio;
    private String mes;
    private Date desde;
    private Date hasta;
    private boolean b_generarResporte = false;
    private boolean b_eliminarDatos = false;

    private ParamAreaUsr pau;

    @EJB
    private GenericaPmLiquidacionFacadeLocal liqEmplMesEJB;

    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;

    @EJB
    private GenericaPmLiquidacionHistFacadeLocal liqHistoricoEmplMesEJB;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
        consultar();
    }

    public boolean prepareFechas() {
        if (anio == null || mes == null) {
            return true;
        }
        String fecha = anio + "-" + mes + "-01";
        Calendar primerDiaMes = Calendar.getInstance();
        Calendar ultimoDiaMes = Calendar.getInstance();
        primerDiaMes.setTime(Util.toDate(fecha));
        ultimoDiaMes.setTime(Util.toDate(fecha));

        primerDiaMes.set(Calendar.DAY_OF_MONTH, 1);
        ultimoDiaMes.set(Calendar.DAY_OF_MONTH, ultimoDiaMes.getActualMaximum(Calendar.DAY_OF_MONTH));
        desde = primerDiaMes.getTime();
        hasta = ultimoDiaMes.getTime();
        return false;
    }

    public void consultar() {
        if (prepareFechas()) {
            b_generarResporte = false;
            b_eliminarDatos = false;
            return;
        }
        listaLiqEmplMes = liqHistoricoEmplMesEJB.findAllByFechaMes(desde, hasta, pau.getIdParamArea().getIdParamArea());
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

    public void eliminarConsultar() {
        eliminarDatos();
        consultar();
    }

    @Transactional
    public void eliminarDatos() {
        if (prepareFechas()) {
            b_generarResporte = false;
            b_eliminarDatos = false;
            MovilidadUtil.addErrorMessage("Selecione mes y año del cual quiere eliminar datos.");
            return;
        }
        int ok = validarMes();

        if (ok < 0) {
            MovilidadUtil.addErrorMessage("Para fechas anteriores solo es posible gestionar datos, un mes inmediatamenta anterior a hoy.");
            return;
        }
        if (ok > 0) {
            MovilidadUtil.addErrorMessage("No es posible gestionar estos datos para meses posterior al de hoy.");
            return;
        }
        int numEliminado = liqHistoricoEmplMesEJB.eliminarDatos(desde, hasta, user.getUsername(), pau.getIdParamArea().getIdParamArea());
        int numEliminadoII = liqEmplMesEJB.eliminarDatos(desde, hasta, user.getUsername(), pau.getIdParamArea().getIdParamArea());

        MovilidadUtil.addSuccessMessage("Se eliminaron " + numEliminado + " datos satisfactoriamente.");
    }

    public int validarMes() {
        int mesActual = Calendar.getInstance().get(Calendar.MONTH) + 1;
        Calendar mesSelecionado = Calendar.getInstance();
        mesSelecionado.setTime(desde);
        int valor_mesSeleccionado = mesSelecionado.get(Calendar.MONTH) + 1;
        valor_mesSeleccionado = valor_mesSeleccionado + 1;
        if (valor_mesSeleccionado > mesActual) {
            mesActual = mesActual + 1;
            if (valor_mesSeleccionado != mesActual) {
                return 1;
            }
        } else if (mesActual > valor_mesSeleccionado) {
            return -1;
        }
        return 0;
    }

//    @Transactional
    public void generarReporte() {
        if (prepareFechas()) {
            b_generarResporte = false;
            b_eliminarDatos = false;
            MovilidadUtil.addErrorMessage("Selecione mes y año del cual quiere generar reporte.");
            return;
        }
        int ok = validarMes();
        if (ok != 0) {
            if (ok < 0 && !listaLiqEmplMes.isEmpty()) {
                MovilidadUtil.addErrorMessage("Para fechas anteriores solo es posible gestionar datos, un mes inmediatamenta anterior a hoy.");
                return;
            }
            if (ok > 0) {
                MovilidadUtil.addErrorMessage("No es posible gestionar estos datos para meses posterior al de hoy.");
                return;
            }
        }
        liqEmplMesEJB.generarReporte(desde, hasta, pau.getIdParamArea().getIdParamArea(), user.getUsername());
        consultar();
        MovilidadUtil.addSuccessMessage("Se genero reporte para el mes seleccionado.");
    }

    public String master(Empleado empl) {
        String master = "";
        try {
            if (empl != null) {
                master = empl.getPmGrupoDetalleList().get(0).getIdGrupo().getNombreGrupo();
            }
        } catch (Exception e) {
            if (empl.getPmGrupoList() != null) {
                if (empl.getPmGrupoList().size() > 0) {
                    return empl.getPmGrupoList().get(0).getNombreGrupo();
                }

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

    public List<GenericaPmLiquidacionHist> getListaLiqEmplMes() {
        return listaLiqEmplMes;
    }

    public void setListaLiqEmplMes(List<GenericaPmLiquidacionHist> listaLiqEmplMes) {
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

}
