package com.movilidad.jsf;

import com.movilidad.ejb.ParamFeriadoFacadeLocal;
import com.movilidad.ejb.PrgTcResumenVrConciliadosFacadeLocal;
import com.movilidad.ejb.VehiculoTipoFacadeLocal;
import com.movilidad.model.ParamFeriado;
import com.movilidad.model.VehiculoTipo;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.PrgTcResumenVrConciliados;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "registroVrConciliadoBean")
@ViewScoped
public class RegistroVrConciliadoBean implements Serializable {

    @EJB
    private PrgTcResumenVrConciliadosFacadeLocal vrConciliadoEjb;

    @EJB
    private VehiculoTipoFacadeLocal vehiculoTipoEjb;
    @EJB
    private ParamFeriadoFacadeLocal paramFeriadoEjb;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private PrgTcResumenVrConciliados vrConciliado;
    private PrgTcResumenVrConciliados selected;

    private Integer i_VehiculoTipo;
    private Date desde;
    private Date hasta;
    private Date fecha;
    private String tipoDia;

    private boolean flagEdit;

    private List<PrgTcResumenVrConciliados> lstRegistros;
    private List<VehiculoTipo> lstTipoVehiculos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        desde = MovilidadUtil.fechaHoy();
        hasta = MovilidadUtil.fechaHoy();
        consultar();
    }

    public void consultar() {
        lstRegistros = vrConciliadoEjb.findAllByFechasAndUnidadFuncional(desde, hasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void nuevo() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }
        fecha = MovilidadUtil.fechaHoy();
        asignarTipoDia();
        vrConciliado = new PrgTcResumenVrConciliados();
        lstTipoVehiculos = vehiculoTipoEjb.findAll();
        selected = null;
        flagEdit = false;
        i_VehiculoTipo = null;

        MovilidadUtil.openModal("wlgValoresConciliados");

    }

    public void editar() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }
        vrConciliado = selected;
        if (!esAlterable()) {
            MovilidadUtil.addErrorMessage("No es posible modificar éste registro");
            return;
        }

        flagEdit = true;
        fecha = selected.getFecha();
        asignarTipoDia();
        lstTipoVehiculos = vehiculoTipoEjb.findAll();
        i_VehiculoTipo = selected.getIdVehiculoTipo().getIdVehiculoTipo();

        MovilidadUtil.openModal("wlgValoresConciliados");

    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();

        if (validacion == null) {
            vrConciliado.setFecha(fecha);
            vrConciliado.setUsername(user.getUsername());
            vrConciliado.setIdVehiculoTipo(new VehiculoTipo(i_VehiculoTipo));
            vrConciliado.setIdGopUnidadFuncional(new GopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));

            if (flagEdit) {
                vrConciliado.setModificado(MovilidadUtil.fechaCompletaHoy());
                vrConciliadoEjb.edit(vrConciliado);
                MovilidadUtil.hideModal("wlgValoresConciliados");
                MovilidadUtil.addSuccessMessage("Registro modificado éxitosamente");

            } else {
                vrConciliado.setCreado(MovilidadUtil.fechaCompletaHoy());
                vrConciliado.setEstadoReg(0);
                vrConciliadoEjb.create(vrConciliado);
                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }

            consultar();

        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public void asignarTipoDia() {

        ParamFeriado paramFeriado = paramFeriadoEjb.findByFecha(fecha);

        if (paramFeriado != null) {
            tipoDia = "D/F";
            return;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        int diaSemana = c.get(Calendar.DAY_OF_WEEK);

        if (diaSemana >= 2 && diaSemana <= 6) {
            tipoDia = "H";
        } else if (diaSemana == 7) {
            tipoDia = "S";
        } else {
            tipoDia = "D/F";
        }

    }

    public void calcularTotalConciliacion() {
        vrConciliado.setTotalConciliacion(BigDecimal.valueOf(vrConciliado.getVrKmComercial() + vrConciliado.getVrKmVacio()));
    }

    private boolean esAlterable() {
        if (vrConciliado == null) {
            return false;
        }
        if (vrConciliado.getUsername().equals(user.getUsername())) {
            return true;
        }

        return false;

    }

    private String validarDatos() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            return "DEBE seleccionar una unidad funcional";
        }

        if (flagEdit) {
            if (vrConciliadoEjb.verificarRegistro(selected.getIdPrgTcResumenVrConciliado(), unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), fecha, i_VehiculoTipo) != null) {
                return "Ya existe un registro del tipo de vehículo seleccionado en la fecha escogida";
            }

            if (vrConciliadoEjb.findAllByFechaAndUnidadFuncional(fecha, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), selected.getIdPrgTcResumenVrConciliado()).size() >= 2) {
                return "SOLO deben existir dos registros por unidad funcional para la fecha seleccionada";
            }

        } else {

            if (vrConciliadoEjb.verificarRegistro(0, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), fecha, i_VehiculoTipo) != null) {
                return "Ya existe un registro del tipo de vehículo seleccionado en la fecha escogida";
            }

            if (vrConciliadoEjb.findAllByFechaAndUnidadFuncional(fecha, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), 0).size() >= 2) {
                return "SOLO deben existir dos registros por unidad funcional para la fecha seleccionada";
            }

        }

        return null;

    }

    public PrgTcResumenVrConciliados getVrConciliado() {
        return vrConciliado;
    }

    public void setVrConciliado(PrgTcResumenVrConciliados vrConciliado) {
        this.vrConciliado = vrConciliado;
    }

    public PrgTcResumenVrConciliados getSelected() {
        return selected;
    }

    public void setSelected(PrgTcResumenVrConciliados selected) {
        this.selected = selected;
    }

    public boolean isFlagEdit() {
        return flagEdit;
    }

    public void setFlagEdit(boolean flagEdit) {
        this.flagEdit = flagEdit;
    }

    public List<PrgTcResumenVrConciliados> getLstRegistros() {
        return lstRegistros;
    }

    public void setLstRegistros(List<PrgTcResumenVrConciliados> lstRegistros) {
        this.lstRegistros = lstRegistros;
    }

    public Integer getI_VehiculoTipo() {
        return i_VehiculoTipo;
    }

    public void setI_VehiculoTipo(Integer i_VehiculoTipo) {
        this.i_VehiculoTipo = i_VehiculoTipo;
    }

    public List<VehiculoTipo> getLstTipoVehiculos() {
        return lstTipoVehiculos;
    }

    public void setLstTipoVehiculos(List<VehiculoTipo> lstTipoVehiculos) {
        this.lstTipoVehiculos = lstTipoVehiculos;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoDia() {
        return tipoDia;
    }

    public void setTipoDia(String tipoDia) {
        this.tipoDia = tipoDia;
    }

}
