package com.movilidad.util.beans;

import com.movilidad.model.PrgTcResumen;
import com.movilidad.utils.Util;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos Ballestas
 */
public class InformeControl {

    private Date fecha;
    private int busesOperando; // count(distinct id_vehiculo) prgtc where fecha = ?1
    private int busesDisponibles; // count(id_vehiculo) vehiculo where estado = activo
    private int inoperables;
    private double porc_buses_op;
    private long count_adicionales_art;
    private long count_adicionales_bi;
    private long count_operaciones_art;
    private long count_mtto_art;
    private long count_otros_art;
    private long count_operaciones_bi;
    private long count_mtto_bi;
    private long count_otros_bi;

    // prgtc resumen
    private BigDecimal kmPrgArt;
    private BigDecimal kmPrgBi;
    private BigDecimal kmHlpPrgArt;
    private BigDecimal kmHlpPrgBi;
    private BigDecimal kmHlpEjeArt;
    private BigDecimal kmHlpEjeBi;
    private BigDecimal total_prog;
    private BigDecimal total_hlp_prog;
    private BigDecimal total_hlp_eje;
    private BigDecimal hlpPagoArt;
    private BigDecimal hlpPagoBi;
    private BigDecimal factorArt;
    private BigDecimal factorBi;

    // count() prgtc where fecha = ?1 and id_prgtc_responsable = ?2
    private BigDecimal perdidosOpeArt;
    private BigDecimal perdidosMttoArt;
    private BigDecimal perdidosOtrosArt;
    private BigDecimal perdidosOpeBi;
    private BigDecimal perdidosMttoBi;
    private BigDecimal perdidosOtrosBi;
    private BigDecimal totalPerdidosArt;
    private BigDecimal totalPerdidosBi;

    // prgtc resumen
    private BigDecimal adicionalArt;
    private BigDecimal adicionalBi;

    public InformeControl() {
    }

    public InformeControl(PrgTcResumen resumen, int busesOperando, int busesDisponibles,
            KmsPerdidosArt perdidosArt, KmsPerdidosBi perdidosBi, KmsAdicionalesCtrl adicionalesCtrl) {
        this.fecha = resumen.getFecha();
        this.kmPrgArt = resumen.getMcomArtPrg() != null ? resumen.getMcomArtPrg().divide(BigDecimal.valueOf(1000)) : Util.CERO;
        this.kmPrgBi = resumen.getMcomBiPrg() != null ? resumen.getMcomBiPrg().divide(BigDecimal.valueOf(1000)) : Util.CERO;
        this.kmHlpPrgArt = resumen.getMhlpArtPrg() != null ? resumen.getMhlpArtPrg().divide(BigDecimal.valueOf(1000)) : Util.CERO;
        this.kmHlpPrgBi = resumen.getMhlpBiPrg() != null ? resumen.getMhlpBiPrg().divide(BigDecimal.valueOf(1000)) : Util.CERO;
        this.kmHlpEjeArt = resumen.getMhlpArtEje() != null ? resumen.getMhlpArtEje().divide(BigDecimal.valueOf(1000)) : Util.CERO;
        this.kmHlpEjeBi = resumen.getMhlpBiEje() != null ? resumen.getMhlpBiEje().divide(BigDecimal.valueOf(1000)) : Util.CERO;
        this.hlpPagoArt = obtenerVacioArt(resumen).divide(BigDecimal.valueOf(1000));
        this.hlpPagoBi = obtenerVacioBi(resumen).divide(BigDecimal.valueOf(1000));
        this.factorArt = resumen.getFactorArt() != null ? resumen.getFactorArt() : Util.CERO;
        this.factorBi = resumen.getFactorBi() != null ? resumen.getFactorBi() : Util.CERO;
        this.adicionalArt = resumen.getMadicArt() != null ? resumen.getMadicArt().divide(BigDecimal.valueOf(1000)) : Util.CERO;
        this.adicionalBi = resumen.getMadicBi() != null ? resumen.getMadicBi().divide(BigDecimal.valueOf(1000)) : Util.CERO;
        this.busesOperando = busesOperando;
        this.busesDisponibles = busesDisponibles;
        this.perdidosOpeArt = perdidosArt.getOperaciones() != null ? perdidosArt.getOperaciones().divide(BigDecimal.valueOf(1000)) : Util.CERO;
        this.perdidosMttoArt = perdidosArt.getMtto() != null ? perdidosArt.getMtto().divide(BigDecimal.valueOf(1000)) : Util.CERO;
        this.perdidosOtrosArt = perdidosArt.getOtros() != null ? perdidosArt.getOtros().divide(BigDecimal.valueOf(1000)) : Util.CERO;
        this.perdidosOpeBi = perdidosBi.getOperaciones() != null ? perdidosBi.getOperaciones().divide(BigDecimal.valueOf(1000)) : Util.CERO;
        this.perdidosMttoBi = perdidosBi.getMtto() != null ? perdidosBi.getMtto().divide(BigDecimal.valueOf(1000)) : Util.CERO;
        this.perdidosOtrosBi = perdidosBi.getOtros() != null ? perdidosBi.getOtros().divide(BigDecimal.valueOf(1000)) : Util.CERO;
        this.totalPerdidosArt = perdidosOpeArt.add(perdidosMttoArt.add(perdidosOtrosArt));
        this.totalPerdidosBi = perdidosOpeBi.add(perdidosMttoBi.add(perdidosOtrosBi));
        this.inoperables = busesDisponibles - busesOperando;
        this.porc_buses_op = busesOperando / busesDisponibles;
        this.total_prog = kmPrgArt.add(kmPrgBi);
        this.total_hlp_prog = kmHlpPrgArt.add(kmHlpPrgBi);
        this.total_hlp_eje = kmHlpEjeArt.add(kmHlpEjeBi);
        this.count_operaciones_art = perdidosArt.getCount_operaciones();
        this.count_mtto_art = perdidosArt.getCount_mtto();
        this.count_otros_art = perdidosArt.getCount_otros();
        this.count_operaciones_bi = perdidosBi.getCount_operaciones();
        this.count_mtto_bi = perdidosBi.getCount_mtto();
        this.count_otros_bi = perdidosBi.getCount_otros();
        this.count_adicionales_art = adicionalesCtrl.getCount_adicionales_art();
        this.count_adicionales_bi = adicionalesCtrl.getCount_adicionales_bi();
    }

    public InformeControl(Date fecha) {
        this.fecha = fecha;
        this.busesOperando = 0;
        this.busesDisponibles = 0;
        this.kmPrgArt = Util.CERO;
        this.kmPrgBi = Util.CERO;
        this.kmHlpPrgArt = Util.CERO;
        this.kmHlpPrgBi = Util.CERO;
        this.kmHlpEjeArt = Util.CERO;
        this.kmHlpEjeBi = Util.CERO;
        this.hlpPagoArt = Util.CERO;
        this.hlpPagoBi = Util.CERO;
        this.factorArt = Util.CERO;
        this.factorBi = Util.CERO;
        this.perdidosOpeArt = Util.CERO;
        this.perdidosMttoArt = Util.CERO;
        this.perdidosOtrosArt = Util.CERO;
        this.perdidosOpeBi = Util.CERO;
        this.perdidosMttoBi = Util.CERO;
        this.perdidosOtrosBi = Util.CERO;
        this.totalPerdidosArt = Util.CERO;
        this.totalPerdidosBi = Util.CERO;
        this.inoperables = 0;
        this.porc_buses_op = 0;
        this.total_prog = Util.CERO;
        this.total_hlp_prog = Util.CERO;
        this.total_hlp_eje = Util.CERO;
        this.adicionalArt = Util.CERO;
        this.adicionalBi = Util.CERO;
        this.count_operaciones_art = 0;
        this.count_mtto_art = 0;
        this.count_otros_art = 0;
        this.count_operaciones_bi = 0;
        this.count_mtto_bi = 0;
        this.count_otros_bi = 0;
        this.count_adicionales_art = 0;
        this.count_adicionales_bi = 0;
    }

    private BigDecimal obtenerVacioArt(PrgTcResumen resumen) {
        if (resumen.getHlpOptArt() != null || resumen.getHlpNoptArt() != null) {
            if (resumen.getHlpOptArt().compareTo(Util.CERO) == 1) {
                return resumen.getHlpOptArt().multiply(resumen.getFactorArt());
            } else if (resumen.getHlpNoptArt().compareTo(Util.CERO) == 1) {
                return resumen.getHlpNoptArt().multiply(resumen.getFactorArt());
            }
        }
        return Util.CERO;
    }

    private BigDecimal obtenerVacioBi(PrgTcResumen resumen) {
        if (resumen.getHlpOptBi() != null || resumen.getHlpNoptBi() != null) {
            if (resumen.getHlpOptBi().compareTo(Util.CERO) == 1) {
                return resumen.getHlpOptBi().multiply(resumen.getFactorBi());
            } else if (resumen.getHlpNoptBi().compareTo(Util.CERO) == 1) {
                return resumen.getHlpNoptBi().multiply(resumen.getFactorBi());
            }
        }
        return Util.CERO;

    }

    public int getInoperables() {
        return inoperables;
    }

    public void setInoperables(int inoperables) {
        this.inoperables = inoperables;
    }

    public double getPorc_buses_op() {
        return porc_buses_op;
    }

    public void setPorc_buses_op(double porc_buses_op) {
        this.porc_buses_op = porc_buses_op;
    }

    public BigDecimal getTotal_prog() {
        return total_prog;
    }

    public void setTotal_prog(BigDecimal total_prog) {
        this.total_prog = total_prog;
    }

    public BigDecimal getTotal_hlp_prog() {
        return total_hlp_prog;
    }

    public void setTotal_hlp_prog(BigDecimal total_hlp_prog) {
        this.total_hlp_prog = total_hlp_prog;
    }

    public BigDecimal getTotal_hlp_eje() {
        return total_hlp_eje;
    }

    public void setTotal_hlp_eje(BigDecimal total_hlp_eje) {
        this.total_hlp_eje = total_hlp_eje;
    }

    public int getBusesOperando() {
        return busesOperando;
    }

    public void setBusesOperando(int busesOperando) {
        this.busesOperando = busesOperando;
    }

    public int getBusesDisponibles() {
        return busesDisponibles;
    }

    public void setBusesDisponibles(int busesDisponibles) {
        this.busesDisponibles = busesDisponibles;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getKmPrgArt() {
        return kmPrgArt;
    }

    public void setKmPrgArt(BigDecimal kmPrgArt) {
        this.kmPrgArt = kmPrgArt;
    }

    public BigDecimal getKmPrgBi() {
        return kmPrgBi;
    }

    public void setKmPrgBi(BigDecimal kmPrgBi) {
        this.kmPrgBi = kmPrgBi;
    }

    public BigDecimal getKmHlpPrgArt() {
        return kmHlpPrgArt;
    }

    public void setKmHlpPrgArt(BigDecimal kmHlpPrgArt) {
        this.kmHlpPrgArt = kmHlpPrgArt;
    }

    public BigDecimal getKmHlpPrgBi() {
        return kmHlpPrgBi;
    }

    public void setKmHlpPrgBi(BigDecimal kmHlpPrgBi) {
        this.kmHlpPrgBi = kmHlpPrgBi;
    }

    public BigDecimal getKmHlpEjeArt() {
        return kmHlpEjeArt;
    }

    public void setKmHlpEjeArt(BigDecimal kmHlpEjeArt) {
        this.kmHlpEjeArt = kmHlpEjeArt;
    }

    public BigDecimal getKmHlpEjeBi() {
        return kmHlpEjeBi;
    }

    public void setKmHlpEjeBi(BigDecimal kmHlpEjeBi) {
        this.kmHlpEjeBi = kmHlpEjeBi;
    }

    public BigDecimal getHlpPagoArt() {
        return hlpPagoArt;
    }

    public void setHlpPagoArt(BigDecimal hlpPagoArt) {
        this.hlpPagoArt = hlpPagoArt;
    }

    public BigDecimal getHlpPagoBi() {
        return hlpPagoBi;
    }

    public void setHlpPagoBi(BigDecimal hlpPagoBi) {
        this.hlpPagoBi = hlpPagoBi;
    }

    public BigDecimal getFactorArt() {
        return factorArt;
    }

    public void setFactorArt(BigDecimal factorArt) {
        this.factorArt = factorArt;
    }

    public BigDecimal getFactorBi() {
        return factorBi;
    }

    public void setFactorBi(BigDecimal factorBi) {
        this.factorBi = factorBi;
    }

    public BigDecimal getPerdidosOpeArt() {
        return perdidosOpeArt;
    }

    public void setPerdidosOpeArt(BigDecimal perdidosOpeArt) {
        this.perdidosOpeArt = perdidosOpeArt;
    }

    public BigDecimal getPerdidosMttoArt() {
        return perdidosMttoArt;
    }

    public void setPerdidosMttoArt(BigDecimal perdidosMttoArt) {
        this.perdidosMttoArt = perdidosMttoArt;
    }

    public BigDecimal getPerdidosOtrosArt() {
        return perdidosOtrosArt;
    }

    public void setPerdidosOtrosArt(BigDecimal perdidosOtrosArt) {
        this.perdidosOtrosArt = perdidosOtrosArt;
    }

    public BigDecimal getPerdidosOpeBi() {
        return perdidosOpeBi;
    }

    public void setPerdidosOpeBi(BigDecimal perdidosOpeBi) {
        this.perdidosOpeBi = perdidosOpeBi;
    }

    public BigDecimal getPerdidosMttoBi() {
        return perdidosMttoBi;
    }

    public void setPerdidosMttoBi(BigDecimal perdidosMttoBi) {
        this.perdidosMttoBi = perdidosMttoBi;
    }

    public BigDecimal getPerdidosOtrosBi() {
        return perdidosOtrosBi;
    }

    public void setPerdidosOtrosBi(BigDecimal perdidosOtrosBi) {
        this.perdidosOtrosBi = perdidosOtrosBi;
    }

    public BigDecimal getTotalPerdidosArt() {
        return totalPerdidosArt;
    }

    public void setTotalPerdidosArt(BigDecimal totalPerdidosArt) {
        this.totalPerdidosArt = totalPerdidosArt;
    }

    public BigDecimal getTotalPerdidosBi() {
        return totalPerdidosBi;
    }

    public void setTotalPerdidosBi(BigDecimal totalPerdidosBi) {
        this.totalPerdidosBi = totalPerdidosBi;
    }

    public BigDecimal getAdicionalArt() {
        return adicionalArt;
    }

    public void setAdicionalArt(BigDecimal adicionalArt) {
        this.adicionalArt = adicionalArt;
    }

    public BigDecimal getAdicionalBi() {
        return adicionalBi;
    }

    public void setAdicionalBi(BigDecimal adicionalBi) {
        this.adicionalBi = adicionalBi;
    }

    public long getCount_adicionales_art() {
        return count_adicionales_art;
    }

    public void setCount_adicionales_art(long count_adicionales_art) {
        this.count_adicionales_art = count_adicionales_art;
    }

    public long getCount_adicionales_bi() {
        return count_adicionales_bi;
    }

    public void setCount_adicionales_bi(long count_adicionales_bi) {
        this.count_adicionales_bi = count_adicionales_bi;
    }

    public long getCount_operaciones_art() {
        return count_operaciones_art;
    }

    public void setCount_operaciones_art(long count_operaciones_art) {
        this.count_operaciones_art = count_operaciones_art;
    }

    public long getCount_mtto_art() {
        return count_mtto_art;
    }

    public void setCount_mtto_art(long count_mtto_art) {
        this.count_mtto_art = count_mtto_art;
    }

    public long getCount_otros_art() {
        return count_otros_art;
    }

    public void setCount_otros_art(long count_otros_art) {
        this.count_otros_art = count_otros_art;
    }

    public long getCount_operaciones_bi() {
        return count_operaciones_bi;
    }

    public void setCount_operaciones_bi(long count_operaciones_bi) {
        this.count_operaciones_bi = count_operaciones_bi;
    }

    public long getCount_mtto_bi() {
        return count_mtto_bi;
    }

    public void setCount_mtto_bi(long count_mtto_bi) {
        this.count_mtto_bi = count_mtto_bi;
    }

    public long getCount_otros_bi() {
        return count_otros_bi;
    }

    public void setCount_otros_bi(long count_otros_bi) {
        this.count_otros_bi = count_otros_bi;
    }
}
