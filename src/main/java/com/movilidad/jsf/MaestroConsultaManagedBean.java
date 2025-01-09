package com.movilidad.jsf;

import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.NovedadTipoFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadTipo;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "maestroConsultaBean")
@ViewScoped
public class MaestroConsultaManagedBean implements Serializable {

    @EJB
    private NovedadFacadeLocal novedadEjb;
    @EJB
    private NovedadTipoFacadeLocal novedadTipoEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private final Locale locale = new Locale("es", "CO");
    private final Calendar current = Calendar.getInstance(locale);

    private List<Novedad> lista;
    private List<NovedadTipo> lstTipos;
    private List<NovedadTipoDetalles> lstdetaDetalles;
    private List<Integer> detallesSeleccionados;

    private Date fechaIni;
    private Date fechaFin;
    private int tipo;
    private boolean flag;

    @PostConstruct
    public void init() {
        fechaFin = new Date();
        current.setTime(fechaFin);
        current.add(Calendar.DATE, -1);
        fechaIni = current.getTime();
        flag = true;
        tipo = 0;
    }

    public void cargarDetalles() {
        PrimeFaces.current().executeScript("PF('dtNovedades').clearFilters()");
        lista = null;

        if (tipo == 0) {
            flag = true;
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo de novedad");
            return;
        }

        detallesSeleccionados = null;
        PrimeFaces.current().ajax().update("frmPrincipal:dtNovedad");
        lstdetaDetalles = novedadTipoEjb.find(tipo).getNovedadTipoDetallesList();
        flag = false;
    }

    public void getByDateRange() {

        if (tipo == 0) {
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo de novedad");
            return;
        }

        if (detallesSeleccionados == null || detallesSeleccionados.isEmpty()) {
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("Debe seleccionar al menos un detalle de novedad");
            return;
        }

        if (fechaIni == null) {
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("Debe seleccionar una fecha de inicio");
            return;
        }

        if (fechaFin == null) {
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("Debe seleccionar una fecha fin");
            return;
        }

        if (Util.validarFechaCambioEstado(fechaIni, fechaFin)) {
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("La fecha inicio no puede ser mayor a la fecha fin");
            return;
        }

        PrimeFaces.current().executeScript("PF('dtNovedades').clearFilters()");
        lista = novedadEjb.getNovedadesMaestroConsulta(fechaIni, fechaFin, detallesSeleccionados,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (lista == null || lista.isEmpty()) {
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("No se encuentran novedades registradas para ese rango de fechas.");
        } else {
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addSuccessMessage("Novedades cargadas éxitosamente");
        }
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

    public List<Novedad> getLista() {
        return lista;
    }

    public void setLista(List<Novedad> lista) {
        this.lista = lista;
    }

    public List<NovedadTipo> getLstTipos() {
        if (lstTipos == null) {
            lstTipos = novedadTipoEjb.findAll();
        }
        return lstTipos;
    }

    public void setLstTipos(List<NovedadTipo> lstTipos) {
        this.lstTipos = lstTipos;
    }

    public List<NovedadTipoDetalles> getLstdetaDetalles() {
        return lstdetaDetalles;
    }

    public void setLstdetaDetalles(List<NovedadTipoDetalles> lstdetaDetalles) {
        this.lstdetaDetalles = lstdetaDetalles;
    }

    public List<Integer> getDetallesSeleccionados() {
        return detallesSeleccionados;
    }

    public void setDetallesSeleccionados(List<Integer> detallesSeleccionados) {
        this.detallesSeleccionados = detallesSeleccionados;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

}
