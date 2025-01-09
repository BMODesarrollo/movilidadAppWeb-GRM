package com.movilidad.jsf;

import com.movilidad.ejb.ParamCierreAusentismoFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.ParamCierreAusentismo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "paramTiempoCierreBean")
@ViewScoped
public class ParamTiempoCierreAusentismoBean implements Serializable {

    @EJB
    private ParamCierreAusentismoFacadeLocal paramCierreAusentismoEjb;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private ParamCierreAusentismo paramCierreAusentismo;

    private Date fechaDesde;
    private Date fechaHasta;

    private Date fechaDesdeBusqueda;
    private Date fechaHastaBusqueda;

    private boolean estaAbierto = false;

    private List<ParamCierreAusentismo> cierresAusentismo;

    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();

    }

    public void consultar() {
        fechaDesde = MovilidadUtil.fechaHoy();
        fechaHasta = MovilidadUtil.fechaHoy();

        if (Util.validarFechaCambioEstado(fechaDesde, fechaHasta)) {
            MovilidadUtil.addErrorMessage("Fecha desde NO debe ser mayor a la fecha fin");
            return;
        }

        cierresAusentismo = paramCierreAusentismoEjb
                .obtenerListaCierresPorRangoFechasYUnidadFuncional(
                        fechaDesdeBusqueda, fechaHastaBusqueda,
                        unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        paramCierreAusentismo = paramCierreAusentismoEjb
                .buscarPorRangoFechasYUnidadFuncional(
                        fechaDesde, fechaHasta,
                        unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()
                );

        if (paramCierreAusentismo == null) {
            estaAbierto = true;
        } else {
            if (paramCierreAusentismo.getBloqueado() == 1) {
                estaAbierto = false;
            } else {
                estaAbierto = true;
            }
        }
    }

    public void bloquearAusentismos() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        if (Util.validarFechaCambioEstado(fechaDesde, fechaHasta)) {
            MovilidadUtil.addErrorMessage("Fecha desde NO debe ser mayor a la fecha fin");
            return;
        }

        if (paramCierreAusentismo != null) {

            if (paramCierreAusentismoEjb.verificarRegistro(
                    fechaDesde, fechaHasta,
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(),
                    paramCierreAusentismo.getIdParamCierreAusentismo()
            ) != null) {
                MovilidadUtil.addErrorMessage("YA existe un cierre dentro del rango de fechas indicado");
                return;
            }

            paramCierreAusentismo.setDesde(fechaDesde);
            paramCierreAusentismo.setHasta(fechaHasta);
            paramCierreAusentismo.setUsername(user.getUsername());
            paramCierreAusentismo.setBloqueado(ConstantsUtil.ON_INT);
            paramCierreAusentismo.setModificado(MovilidadUtil.fechaCompletaHoy());

            paramCierreAusentismoEjb.edit(paramCierreAusentismo);
        } else {

            if (paramCierreAusentismoEjb.verificarRegistro(
                    fechaDesde, fechaHasta,
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(),
                    0
            ) != null) {
                MovilidadUtil.addErrorMessage("YA existe un cierre dentro del rango de fechas indicado");
                return;
            }

            paramCierreAusentismo = new ParamCierreAusentismo();

            paramCierreAusentismo.setDesde(fechaDesde);
            paramCierreAusentismo.setHasta(fechaHasta);
            paramCierreAusentismo.setUsername(user.getUsername());
            paramCierreAusentismo.setIdGopUnidadFuncional(new GopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));
            paramCierreAusentismo.setBloqueado(ConstantsUtil.ON_INT);
            paramCierreAusentismo.setEstadoReg(ConstantsUtil.OFF_INT);
            paramCierreAusentismo.setCreado(MovilidadUtil.fechaCompletaHoy());

            paramCierreAusentismoEjb.create(paramCierreAusentismo);
        }

        consultar();
        MovilidadUtil.addSuccessMessage("Cierre de ausenstismos realizado con éxito.");

    }

    public void desbloquearAusentismos() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        if (Util.validarFechaCambioEstado(fechaDesde, fechaHasta)) {
            MovilidadUtil.addErrorMessage("Fecha desde NO debe ser mayor a la fecha fin");
            return;
        }

        paramCierreAusentismo.setBloqueado(ConstantsUtil.OFF_INT);
        paramCierreAusentismo.setModificado(MovilidadUtil.fechaCompletaHoy());

        paramCierreAusentismoEjb.edit(paramCierreAusentismo);

        consultar();
        MovilidadUtil.addSuccessMessage("Desbloqueo de ausenstismos realizado con éxito.");
    }

    public void bloquearAusentismo(ParamCierreAusentismo cierreAusentismo) {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        if (Util.validarFechaCambioEstado(cierreAusentismo.getDesde(), cierreAusentismo.getHasta())) {
            MovilidadUtil.addErrorMessage("Fecha desde NO debe ser mayor a la fecha fin");
            return;
        }

        if (paramCierreAusentismoEjb.verificarRegistro(
                cierreAusentismo.getDesde(), cierreAusentismo.getHasta(),
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(),
                cierreAusentismo.getIdParamCierreAusentismo()
        ) != null) {
            MovilidadUtil.addErrorMessage("YA existe un cierre dentro del rango de fechas indicado");
            return;
        }

        cierreAusentismo.setUsername(user.getUsername());
        cierreAusentismo.setBloqueado(ConstantsUtil.ON_INT);
        cierreAusentismo.setModificado(MovilidadUtil.fechaCompletaHoy());

        paramCierreAusentismoEjb.edit(cierreAusentismo);

        consultar();
        MovilidadUtil.addSuccessMessage("Cierre de ausenstismos realizado con éxito.");

    }

    public void desbloquearAusentismo(ParamCierreAusentismo cierreAusentismo) {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        if (Util.validarFechaCambioEstado(cierreAusentismo.getDesde(), cierreAusentismo.getHasta())) {
            MovilidadUtil.addErrorMessage("Fecha desde NO debe ser mayor a la fecha fin");
            return;
        }

        cierreAusentismo.setBloqueado(ConstantsUtil.OFF_INT);
        cierreAusentismo.setModificado(MovilidadUtil.fechaCompletaHoy());

        paramCierreAusentismoEjb.edit(cierreAusentismo);

        consultar();
        MovilidadUtil.addSuccessMessage("Desbloqueo de ausenstismos realizado con éxito.");
    }

    public void prepareModificarRangoFechas() {
        fechaDesde = paramCierreAusentismo.getDesde();
        fechaHasta = paramCierreAusentismo.getHasta();
    }

    public void modificarFechas() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        if (Util.validarFechaCambioEstado(fechaDesde, fechaHasta)) {
            MovilidadUtil.addErrorMessage("Fecha desde NO debe ser mayor a la fecha fin");
            return;
        }

        if (paramCierreAusentismoEjb.verificarRegistro(
                fechaDesde, fechaHasta,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(),
                paramCierreAusentismo.getIdParamCierreAusentismo()
        ) != null) {
            MovilidadUtil.addErrorMessage("YA existe un cierre dentro del rango de fechas indicado");
            return;
        }

        paramCierreAusentismo.setDesde(fechaDesde);
        paramCierreAusentismo.setHasta(fechaHasta);
        paramCierreAusentismo.setUsername(user.getUsername());
        paramCierreAusentismo.setModificado(MovilidadUtil.fechaCompletaHoy());

        paramCierreAusentismoEjb.edit(paramCierreAusentismo);

        consultar();
        MovilidadUtil.addSuccessMessage("Rango de fechas modificado con éxito.");

    }

    public ParamCierreAusentismo getParamCierreAusentismo() {
        return paramCierreAusentismo;
    }

    public void setParamCierreAusentismo(ParamCierreAusentismo paramCierreAusentismo) {
        this.paramCierreAusentismo = paramCierreAusentismo;
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

    public boolean isEstaAbierto() {
        return estaAbierto;
    }

    public void setEstaAbierto(boolean estaAbierto) {
        this.estaAbierto = estaAbierto;
    }

    public List<ParamCierreAusentismo> getCierresAusentismo() {
        return cierresAusentismo;
    }

    public void setCierresAusentismo(List<ParamCierreAusentismo> cierresAusentismo) {
        this.cierresAusentismo = cierresAusentismo;
    }

    public Date getFechaDesdeBusqueda() {
        return fechaDesdeBusqueda;
    }

    public void setFechaDesdeBusqueda(Date fechaDesdeBusqueda) {
        this.fechaDesdeBusqueda = fechaDesdeBusqueda;
    }

    public Date getFechaHastaBusqueda() {
        return fechaHastaBusqueda;
    }

    public void setFechaHastaBusqueda(Date fechaHastaBusqueda) {
        this.fechaHastaBusqueda = fechaHastaBusqueda;
    }

}
