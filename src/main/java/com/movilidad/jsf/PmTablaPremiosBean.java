package com.movilidad.jsf;

import com.movilidad.ejb.PmTablaPremiosFacadeLocal;
import com.movilidad.model.PmTablaPremios;
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
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "pmTablaPremiosBean")
@ViewScoped
public class PmTablaPremiosBean implements Serializable {

    @EJB
    private PmTablaPremiosFacadeLocal tablaPremiosEjb;

    private PmTablaPremios pmTablaPremios;
    private PmTablaPremios selected;

    private Date fechaDesde;
    private Date fechaHasta;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private boolean isEditing;
    private boolean b_otorgado;

    private List<PmTablaPremios> lstTablaPremios;

    @PostConstruct
    public void init() {
        lstTablaPremios = tablaPremiosEjb.findAllByEstadoReg();
    }

    /**
     * Carga los datos para un nuevo registro
     */
    public void nuevo() {
        b_otorgado = false;
        isEditing = false;
        fechaDesde = null;
        fechaHasta = null;
        pmTablaPremios = new PmTablaPremios();
        selected = null;
    }

    /**
     * Realiza la carga del registro previo a la edición de éste.
     */
    public void editar() {
        isEditing = true;
        b_otorgado = (selected.getOtorgado() == 1);
        fechaDesde = selected.getDesde();
        fechaHasta = selected.getHasta();
        pmTablaPremios = selected;
    }

    /**
     * Guarda/modifica los datos en la tabla.
     */
    public void guardar() {
        String msgValidacion = validarDatos();

        if (msgValidacion == null) {

            pmTablaPremios.setDesde(fechaDesde);
            pmTablaPremios.setHasta(fechaHasta);
            pmTablaPremios.setOtorgado(b_otorgado ? 1 : 0);

            if (isEditing) {

                pmTablaPremios.setUsername(user.getUsername());
                pmTablaPremios.setModificado(MovilidadUtil.fechaCompletaHoy());
                tablaPremiosEjb.edit(pmTablaPremios);

                MovilidadUtil.hideModal("wlvPmTablaPremios");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");

            } else {
                pmTablaPremios.setEstadoReg(0);
                pmTablaPremios.setUsername(user.getUsername());
                pmTablaPremios.setCreado(MovilidadUtil.fechaCompletaHoy());
                tablaPremiosEjb.create(pmTablaPremios);
                lstTablaPremios.add(pmTablaPremios);

                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }

        } else {
            MovilidadUtil.addErrorMessage(msgValidacion);
        }
    }

    private String validarDatos() {

        if (Util.validarFechaCambioEstado(fechaDesde, fechaHasta)) {
            return "La fecha desde DEBE ser menor que la fecha FIN";
        }

        if (pmTablaPremios.getPuntoMin() > pmTablaPremios.getPuntoMax()) {
            return "El punto mínimo DEBE ser MENOR al punto máximo";
        }

        if (isEditing) {
            if (tablaPremiosEjb.verificarFecha(fechaDesde, selected.getIdPmTablaPremios(), pmTablaPremios.getPuntoMin(), pmTablaPremios.getPuntoMax()) != null) {
                return "YA existe un registro que se encuentra dentro de ese rango de fechas";
            }
            if (tablaPremiosEjb.verificarFecha(fechaHasta, selected.getIdPmTablaPremios(), pmTablaPremios.getPuntoMin(), pmTablaPremios.getPuntoMax()) != null) {
                return "YA existe un registro que se encuentra dentro de ese rango de fechas";
            }
            if (tablaPremiosEjb.verificarPosicion(fechaDesde, selected.getIdPmTablaPremios(), pmTablaPremios.getPosicion()) != null) {
                return "La posición a ingresar YA existe dentro de ese rango de fechas";
            }
            if (tablaPremiosEjb.verificarPosicion(fechaHasta, selected.getIdPmTablaPremios(), pmTablaPremios.getPosicion()) != null) {
                return "La posición a ingresar YA existe dentro de ese rango de fechas";
            }
        } else {
            if (!lstTablaPremios.isEmpty()) {
                if (tablaPremiosEjb.verificarFecha(fechaDesde, 0, pmTablaPremios.getPuntoMin(), pmTablaPremios.getPuntoMax()) != null) {
                    return "YA existe un registro que se encuentra dentro de ese rango de fechas";
                }
                if (tablaPremiosEjb.verificarFecha(fechaHasta, 0, pmTablaPremios.getPuntoMin(), pmTablaPremios.getPuntoMax()) != null) {
                    return "YA existe un registro que se encuentra dentro de ese rango de fechas";
                }
                if (tablaPremiosEjb.verificarPosicion(fechaDesde, 0, pmTablaPremios.getPosicion()) != null) {
                    return "La posición a ingresar YA existe dentro de ese rango de fechas";
                }
                if (tablaPremiosEjb.verificarPosicion(fechaHasta, 0, pmTablaPremios.getPosicion()) != null) {
                    return "La posición a ingresar YA existe dentro de ese rango de fechas";
                }
            }
        }

        return null;
    }

    public PmTablaPremios getPmTablaPremios() {
        return pmTablaPremios;
    }

    public void setPmTablaPremios(PmTablaPremios pmVrbonoGrupal) {
        this.pmTablaPremios = pmVrbonoGrupal;
    }

    public PmTablaPremios getSelected() {
        return selected;
    }

    public void setSelected(PmTablaPremios selected) {
        this.selected = selected;
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

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<PmTablaPremios> getLstTablaPremios() {
        return lstTablaPremios;
    }

    public void setLstTablaPremios(List<PmTablaPremios> lstTablaPremios) {
        this.lstTablaPremios = lstTablaPremios;
    }

    public boolean isB_otorgado() {
        return b_otorgado;
    }

    public void setB_otorgado(boolean b_otorgado) {
        this.b_otorgado = b_otorgado;
    }
}
