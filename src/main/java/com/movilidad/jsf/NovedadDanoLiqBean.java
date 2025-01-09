package com.movilidad.jsf;

import com.movilidad.ejb.NovedadDanoFacadeLocal;
import com.movilidad.ejb.NovedadDanoLiqDetFacadeLocal;
import com.movilidad.ejb.NovedadDanoLiqFacadeLocal;
import com.movilidad.ejb.VehiculoDanoCostoFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.NovedadDano;
import com.movilidad.model.NovedadDanoLiq;
import com.movilidad.model.NovedadDanoLiqDet;
import com.movilidad.model.VehiculoDanoCosto;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "novedadDanoLiqBean")
@ViewScoped
public class NovedadDanoLiqBean implements Serializable {

    @EJB
    private NovedadDanoLiqFacadeLocal novedadDanoLiqEjb;
    @EJB
    private NovedadDanoLiqDetFacadeLocal novedadDanoLiqDetEjb;
    @EJB
    private NovedadDanoFacadeLocal novedadDanoEjb;
    @EJB
    private VehiculoDanoCostoFacadeLocal vehiculoDanoCostoEjb;

    @Inject
    private SelectGopUnidadFuncionalBean selectGopUnidadFuncionalBean;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private Integer sumCostoParam;
    private Integer sumCostoAjustado;
    private Date desde;
    private Date hasta;

    private boolean flagUF;
    private boolean flagNoExisteLiquidacion = false;

    private NovedadDanoLiq novedadDanoLiq;
    private NovedadDanoLiq selected;

    private List<NovedadDanoLiq> novedadDanoLiqList;
    private List<NovedadDanoLiqDet> lstDetalles;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        flagUF = unidadFuncionalSessionBean.getIdGopUnidadFuncional() == 0;
        desde = MovilidadUtil.fechaHoy();
        hasta = MovilidadUtil.fechaHoy();
    }

    public void guardar() {
        guardarTransactional();
        consultar();
    }

    @Transactional
    public void actualizar() {
        actualizarTransactional();
    }

    void consultarDb() {
        novedadDanoLiqList = novedadDanoLiqEjb.findByRangoFechas(desde, hasta,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void onRowSelect(SelectEvent event) {
        setSelected((NovedadDanoLiq) event.getObject());
        cargarDetalles();
    }

    void cargarDetalles() {
        novedadDanoLiq = selected;

        lstDetalles = novedadDanoLiq.getNovedadDanoLiqDetList();
        sumCostoParam = novedadDanoLiq.getSumCostoParam().intValue();
        sumCostoAjustado = novedadDanoLiq.getSumCostoAjustado().intValue();
        lstDetalles = novedadDanoLiq.getNovedadDanoLiqDetList();
    }

    public int isSelected(NovedadDanoLiq param) {
        if (selected == null) {
            return 0;
        }
        if (param.getIdNovedadDanoLiq() == null) {
            return 1;
        }
        return param.getIdNovedadDanoLiq().equals(selected == null ? 0 : selected.getIdNovedadDanoLiq()) ? 2 : 0;
    }

    public void consultar() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        if (Util.validarFechaCambioEstado(desde, hasta)) {
            MovilidadUtil.addErrorMessage("La fecha desde NO puede ser MAYOR a la fecha hasta");
            return;
        }
        consultarDb();
        if (novedadDanoLiqList.isEmpty()) {
            lstDetalles = null;
            novedadDanoLiq = null;
            flagNoExisteLiquidacion = true;
            sumCostoParam = null;
            sumCostoAjustado = null;
            MovilidadUtil.addAdvertenciaMessage("NO se encontraron datos de liquidación para ese rango de fechas");
            return;
        }
        selected = novedadDanoLiq = novedadDanoLiqList.get(0);

        sumCostoParam = novedadDanoLiq.getSumCostoParam().intValue();
        sumCostoAjustado = novedadDanoLiq.getSumCostoAjustado().intValue();

        lstDetalles = novedadDanoLiq.getNovedadDanoLiqDetList();
        flagNoExisteLiquidacion = false;

    }

    public void llenarDatosLiquidacion() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        if (Util.validarFechaCambioEstado(desde, hasta)) {
            MovilidadUtil.addErrorMessage("La fecha desde NO puede ser MAYOR a la fecha hasta");
            return;
        }
        consultarDb();

        if (novedadDanoLiqList != null && !novedadDanoLiqList.isEmpty()) {
            flagNoExisteLiquidacion = true;
            MovilidadUtil.addErrorMessage("YA existe una liquidación para ese rango de fechas");
            return;
        }

        List<NovedadDano> lstDanos = novedadDanoEjb.findByDateRange(desde, hasta,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (lstDanos == null || lstDanos.isEmpty()) {
            MovilidadUtil.addAdvertenciaMessage("NO se encontraron daños registrados para el rango de fechas indicados");
            return;
        }

        // Se asignan valores básicos de la liquidación
        novedadDanoLiq = new NovedadDanoLiq();
        novedadDanoLiq.setDesde(desde);
        novedadDanoLiq.setHasta(hasta);
        novedadDanoLiq.setFechaLiq(MovilidadUtil.fechaCompletaHoy());
        novedadDanoLiq.setLiquidado(0);
        novedadDanoLiq.setCreado(MovilidadUtil.fechaCompletaHoy());
        novedadDanoLiq.setEstadoReg(0);
        novedadDanoLiq.setUsername(user.getUsername());
        novedadDanoLiqList.add(novedadDanoLiq);
        selected = novedadDanoLiq;
        cargarDetallesATabla(lstDanos);

    }

    public void onCellEdit(CellEditEvent event) {
//        Integer costoAntiguo = (Integer) event.getOldValue();
//        Integer nuevoCosto = (Integer) event.getNewValue();

        sumCostoAjustado = 0;

        for (NovedadDanoLiqDet det : lstDetalles) {
            sumCostoAjustado += det.getCostoAjustado();
        }
        novedadDanoLiq.setSumCostoAjustado(BigDecimal.valueOf(sumCostoAjustado));

        MovilidadUtil.addSuccessMessage("Costo ajustado éxitosamente");

    }

    private void cargarDetallesATabla(List<NovedadDano> danos) {
        sumCostoParam = 0;
        sumCostoAjustado = 0;
        lstDetalles = new ArrayList<>();

        for (NovedadDano item : danos) {

            VehiculoDanoCosto costo = vehiculoDanoCostoEjb.findBySeveridadAndFechas(item.getIdVehiculoDanoSeveridad().getIdVehiculoDanoSeveridad(), desde, hasta);

            if (costo == null) {
                sumCostoParam = 0;
                sumCostoAjustado = 0;
                lstDetalles = new ArrayList<>();
                MovilidadUtil.addErrorMessage("NO se ha encontrado un costo asociado a la severidad: ( " + item.getIdVehiculoDanoSeveridad().getNombre().toUpperCase() + " ) para el rango de fechas seleccionado");
                return;
            } else {
                NovedadDanoLiqDet detalle = new NovedadDanoLiqDet();
                detalle.setIdNovedadDano(item);
                detalle.setIdNovedadDanoLiq(novedadDanoLiq);
                detalle.setIdVehiculoDanoCosto(costo);
                detalle.setCostoParam(costo.getValor());
                detalle.setCostoAjustado(costo.getValor());
                detalle.setUsername(user.getUsername());
                detalle.setEstadoReg(0);
                detalle.setCreado(MovilidadUtil.fechaCompletaHoy());

                sumCostoParam += costo.getValor();
                sumCostoAjustado += costo.getValor();

                lstDetalles.add(detalle);

            }

        }
        novedadDanoLiq.setSumCostoParam(BigDecimal.valueOf(sumCostoParam));
        novedadDanoLiq.setSumCostoAjustado(BigDecimal.valueOf(sumCostoAjustado));

        MovilidadUtil.addSuccessMessage("Datos cargados éxitosamente");

    }

    @Transactional
    private void guardarTransactional() {
        try {

            if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
                MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
                return;
            }

            if (lstDetalles == null || lstDetalles.isEmpty()) {
                MovilidadUtil.addErrorMessage("DEBE existir al menos un detalle para realizar la liquidación");
                return;
            }

            novedadDanoLiq.setLiquidado(1);
            novedadDanoLiq.setIdGopUnidadFuncional(new GopUnidadFuncional(
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));
//            novedadDanoLiq.setSumCostoParam(BigDecimal.valueOf(sumCostoParam));
//            novedadDanoLiq.setSumCostoAjustado(BigDecimal.valueOf(sumCostoAjustado));
            novedadDanoLiq.setNovedadDanoLiqDetList(lstDetalles);
            novedadDanoLiqEjb.create(novedadDanoLiq);

            MovilidadUtil.addSuccessMessage("Liquidación generada con éxito");
//            reset();
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar datos de la liquidación");
            throw new Error("Error al guardar datos de la liquidación");
        }
    }

    private void actualizarTransactional() {
        try {

            if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
                MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
                return;
            }

            if (lstDetalles == null || lstDetalles.isEmpty()) {
                MovilidadUtil.addErrorMessage("DEBE existir al menos un detalle para realizar la liquidación");
                return;
            }

            novedadDanoLiq.setModificado(MovilidadUtil.fechaCompletaHoy());
//            novedadDanoLiq.setSumCostoParam(BigDecimal.valueOf(sumCostoParam));
//            novedadDanoLiq.setSumCostoAjustado(BigDecimal.valueOf(sumCostoAjustado));
            novedadDanoLiq.setNovedadDanoLiqDetList(lstDetalles);
            novedadDanoLiqEjb.edit(novedadDanoLiq);
            selected = novedadDanoLiq = null;

            MovilidadUtil.addSuccessMessage("Liquidación actualizada con éxito");
//            reset();
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar datos de la liquidación");
            throw new Error("Error al actualizar datos de la liquidación");
        }
    }

    private void reset() {
        lstDetalles = null;
        novedadDanoLiq = null;
        sumCostoParam = null;
        sumCostoAjustado = null;
        flagNoExisteLiquidacion = false;
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

    public boolean isFlagUF() {
        return flagUF;
    }

    public void setFlagUF(boolean flagUF) {
        this.flagUF = flagUF;
    }

    public NovedadDanoLiq getNovedadDanoLiq() {
        return novedadDanoLiq;
    }

    public void setNovedadDanoLiq(NovedadDanoLiq novedadDanoLiq) {
        this.novedadDanoLiq = novedadDanoLiq;
    }

    public List<NovedadDanoLiqDet> getLstDetalles() {
        return lstDetalles;
    }

    public void setLstDetalles(List<NovedadDanoLiqDet> lstDetalles) {
        this.lstDetalles = lstDetalles;
    }

    public Integer getSumCostoParam() {
        return sumCostoParam;
    }

    public void setSumCostoParam(Integer sumCostoParam) {
        this.sumCostoParam = sumCostoParam;
    }

    public Integer getSumCostoAjustado() {
        return sumCostoAjustado;
    }

    public void setSumCostoAjustado(Integer sumCostoAjustado) {
        this.sumCostoAjustado = sumCostoAjustado;
    }

    public boolean isFlagNoExisteLiquidacion() {
        return flagNoExisteLiquidacion;
    }

    public void setFlagNoExisteLiquidacion(boolean flagNoExisteLiquidacion) {
        this.flagNoExisteLiquidacion = flagNoExisteLiquidacion;
    }

    public List<NovedadDanoLiq> getNovedadDanoLiqList() {
        return novedadDanoLiqList;
    }

    public void setNovedadDanoLiqList(List<NovedadDanoLiq> novedadDanoLiqList) {
        this.novedadDanoLiqList = novedadDanoLiqList;
    }

    public NovedadDanoLiq getSelected() {
        return selected;
    }

    public void setSelected(NovedadDanoLiq selected) {
        this.selected = selected;
    }

}
