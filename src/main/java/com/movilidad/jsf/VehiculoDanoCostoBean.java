package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoDanoSeveridadFacadeLocal;
import com.movilidad.ejb.VehiculoDanoCostoFacadeLocal;
import com.movilidad.model.VehiculoDanoSeveridad;
import com.movilidad.model.VehiculoDanoCosto;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "vehiculoDanoCostoBean")
@ViewScoped
public class VehiculoDanoCostoBean implements Serializable {

    @EJB
    private VehiculoDanoCostoFacadeLocal vehiculoDanoCostoEjb;

    @EJB
    private VehiculoDanoSeveridadFacadeLocal severidadEjb;

    private VehiculoDanoCosto danoCosto;
    private VehiculoDanoCosto selected;

    private Integer idDanoSeveridad;
    private Date desde;
    private Date hasta;

    private boolean flagEdit;

    private List<VehiculoDanoCosto> lstDanoCostos;
    private List<VehiculoDanoSeveridad> lstSeveridades;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstDanoCostos = vehiculoDanoCostoEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        danoCosto = new VehiculoDanoCosto();
        lstSeveridades = severidadEjb.findAll();
        desde = null;
        hasta = null;
        selected = null;
        flagEdit = false;
        idDanoSeveridad = null;
    }

    public void editar() {
        flagEdit = true;
        danoCosto = selected;
        desde = selected.getDesde();
        hasta = selected.getHasta();
        lstSeveridades = severidadEjb.findAll();
        idDanoSeveridad = selected.getIdVehiculoDanoSeveridad().getIdVehiculoDanoSeveridad();
    }

    public void guardar() {
        String validacion = validarDatos();

        if (validacion == null) {
            danoCosto.setDesde(desde);
            danoCosto.setHasta(hasta);
            danoCosto.setUsername(user.getUsername());
            danoCosto.setIdVehiculoDanoSeveridad(new VehiculoDanoSeveridad(idDanoSeveridad));

            if (flagEdit) {
                danoCosto.setModificado(MovilidadUtil.fechaCompletaHoy());
                vehiculoDanoCostoEjb.edit(danoCosto);
                PrimeFaces.current().executeScript("PF('wlgVehiculoDanoCosto').hide();");
                MovilidadUtil.addSuccessMessage("Registro modificado éxitosamente");

            } else {
                danoCosto.setCreado(MovilidadUtil.fechaCompletaHoy());
                danoCosto.setEstadoReg(0);
                vehiculoDanoCostoEjb.create(danoCosto);
                lstDanoCostos.add(danoCosto);
                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }

            init();

        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }

    }

    private String validarDatos() {

        if (Util.validarFechaCambioEstado(desde, hasta)) {
            return "La fecha desde NO puede ser MAYOR a la fecha hasta";
        }

        if (flagEdit) {
            if (vehiculoDanoCostoEjb.verificarRegistro(selected.getIdVehiculoDanoCosto(), idDanoSeveridad, desde, hasta) != null) {
                return "Se ha encontrado un costo relacionado a esa severidad. registrado para ese rango de fechas";
            }

        } else {
            if (vehiculoDanoCostoEjb.verificarRegistro(0, idDanoSeveridad, desde, hasta) != null) {
                return "Se ha encontrado un costo relacionado a esa severidad. registrado para ese rango de fechas";
            }

        }

        return null;

    }

    public VehiculoDanoCosto getDanoCosto() {
        return danoCosto;
    }

    public void setDanoCosto(VehiculoDanoCosto danoCosto) {
        this.danoCosto = danoCosto;
    }

    public VehiculoDanoCosto getSelected() {
        return selected;
    }

    public void setSelected(VehiculoDanoCosto selected) {
        this.selected = selected;
    }

    public boolean isFlagEdit() {
        return flagEdit;
    }

    public void setFlagEdit(boolean flagEdit) {
        this.flagEdit = flagEdit;
    }

    public List<VehiculoDanoCosto> getLstDanoCostos() {
        return lstDanoCostos;
    }

    public void setLstDanoCostos(List<VehiculoDanoCosto> lstDanoCostos) {
        this.lstDanoCostos = lstDanoCostos;
    }

    public Integer getIdDanoSeveridad() {
        return idDanoSeveridad;
    }

    public void setIdDanoSeveridad(Integer idDanoSeveridad) {
        this.idDanoSeveridad = idDanoSeveridad;
    }

    public List<VehiculoDanoSeveridad> getLstSeveridades() {
        return lstSeveridades;
    }

    public void setLstSeveridades(List<VehiculoDanoSeveridad> lstSeveridades) {
        this.lstSeveridades = lstSeveridades;
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

}
