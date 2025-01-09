package com.movilidad.jsf;

import com.movilidad.ejb.GenericaFacadeLocal;
import com.movilidad.ejb.GenericaPmTablaPremiosFacadeLocal;
import com.movilidad.model.GenericaPmTablaPremios;
import com.movilidad.model.ParamArea;
import com.movilidad.model.ParamAreaUsr;
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
@Named(value = "genericaPmTablaPremiosBean")
@ViewScoped
public class GenericaPmTablaPremiosBean implements Serializable {

    @EJB
    private GenericaPmTablaPremiosFacadeLocal tablaPremiosEjb;
    @EJB
    private GenericaFacadeLocal genericaEjb;

    private GenericaPmTablaPremios genericaPmTablaPremios;
    private GenericaPmTablaPremios selected;

    private Integer i_idArea;
    private Date fechaDesde;
    private Date fechaHasta;

    private boolean b_otorgado;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private boolean isEditing;

    private List<GenericaPmTablaPremios> lstTablaPremios;

    @PostConstruct
    public void init() {
        ParamAreaUsr paramAreaUsr = genericaEjb.findByUsername(user.getUsername());
        i_idArea = null;

        if (paramAreaUsr != null) {
            i_idArea = paramAreaUsr.getIdParamArea().getIdParamArea();
            consultar();
        }
    }

    /**
     * Carga los datos para un nuevo registro
     */
    public void nuevo() {
        b_otorgado = false;
        isEditing = false;
        fechaDesde = null;
        fechaHasta = null;
        genericaPmTablaPremios = new GenericaPmTablaPremios();
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
        genericaPmTablaPremios = selected;
    }

    /**
     * Guarda/modifica los datos en la tabla.
     */
    public void guardar() {
        String msgValidacion = validarDatos();

        if (msgValidacion == null) {

            genericaPmTablaPremios.setDesde(fechaDesde);
            genericaPmTablaPremios.setHasta(fechaHasta);
            genericaPmTablaPremios.setOtorgado(b_otorgado ? 1 : 0);

            if (isEditing) {

                genericaPmTablaPremios.setUsername(user.getUsername());
                genericaPmTablaPremios.setModificado(MovilidadUtil.fechaCompletaHoy());
                tablaPremiosEjb.edit(genericaPmTablaPremios);

                MovilidadUtil.hideModal("wlvGenericaPmTablaPremios");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");

            } else {
                genericaPmTablaPremios.setIdParamArea(new ParamArea(i_idArea));
                genericaPmTablaPremios.setEstadoReg(0);
                genericaPmTablaPremios.setUsername(user.getUsername());
                genericaPmTablaPremios.setCreado(MovilidadUtil.fechaCompletaHoy());
                tablaPremiosEjb.create(genericaPmTablaPremios);

                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }

            consultar();

        } else {
            MovilidadUtil.addErrorMessage(msgValidacion);
        }
    }

    private String validarDatos() {

        if (i_idArea == null) {
            return "El usuario logueado NO tiene un área asociada... Acción inválida";
        }

        if (Util.validarFechaCambioEstado(fechaDesde, fechaHasta)) {
            return "La fecha desde DEBE ser menor que la fecha FIN";
        }

        if (genericaPmTablaPremios.getPuntoMin() > genericaPmTablaPremios.getPuntoMax()) {
            return "El punto mínimo DEBE ser MENOR al punto máximo";
        }

        if (isEditing) {
            if (tablaPremiosEjb.verificarFecha(fechaDesde, selected.getIdGenericaPmTablaPremios(), i_idArea, genericaPmTablaPremios.getPuntoMin(), genericaPmTablaPremios.getPuntoMax()) != null) {
                return "YA existe un registro que se encuentra dentro de ese rango de fechas";
            }
            if (tablaPremiosEjb.verificarFecha(fechaHasta, selected.getIdGenericaPmTablaPremios(), i_idArea, genericaPmTablaPremios.getPuntoMin(), genericaPmTablaPremios.getPuntoMax()) != null) {
                return "YA existe un registro que se encuentra dentro de ese rango de fechas";
            }
            if (tablaPremiosEjb.verificarPosicion(fechaDesde, selected.getIdGenericaPmTablaPremios(), i_idArea, genericaPmTablaPremios.getPosicion()) != null) {
                return "La posición a ingresar YA existe dentro de ese rango de fechas";
            }
            if (tablaPremiosEjb.verificarPosicion(fechaHasta, selected.getIdGenericaPmTablaPremios(), i_idArea, genericaPmTablaPremios.getPosicion()) != null) {
                return "La posición a ingresar YA existe dentro de ese rango de fechas";
            }
        } else {
            if (!lstTablaPremios.isEmpty()) {
                if (tablaPremiosEjb.verificarFecha(fechaDesde, 0, i_idArea, genericaPmTablaPremios.getPuntoMin(), genericaPmTablaPremios.getPuntoMax()) != null) {
                    return "YA existe un registro que se encuentra dentro de ese rango de fechas";
                }
                if (tablaPremiosEjb.verificarFecha(fechaHasta, 0, i_idArea, genericaPmTablaPremios.getPuntoMin(), genericaPmTablaPremios.getPuntoMax()) != null) {
                    return "YA existe un registro que se encuentra dentro de ese rango de fechas";
                }
                if (tablaPremiosEjb.verificarPosicion(fechaDesde, 0, i_idArea, genericaPmTablaPremios.getPosicion()) != null) {
                    return "La posición a ingresar YA existe dentro de ese rango de fechas";
                }
                if (tablaPremiosEjb.verificarPosicion(fechaHasta, 0, i_idArea, genericaPmTablaPremios.getPosicion()) != null) {
                    return "La posición a ingresar YA existe dentro de ese rango de fechas";
                }
            }
        }

        return null;
    }

    private void consultar() {
        if (i_idArea != null) {
            lstTablaPremios = tablaPremiosEjb.findAllByEstadoReg(i_idArea);
        }
    }

    public GenericaPmTablaPremios getGenericaPmTablaPremios() {
        return genericaPmTablaPremios;
    }

    public void setGenericaPmTablaPremios(GenericaPmTablaPremios pmVrbonoGrupal) {
        this.genericaPmTablaPremios = pmVrbonoGrupal;
    }

    public GenericaPmTablaPremios getSelected() {
        return selected;
    }

    public void setSelected(GenericaPmTablaPremios selected) {
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

    public List<GenericaPmTablaPremios> getLstTablaPremios() {
        return lstTablaPremios;
    }

    public void setLstTablaPremios(List<GenericaPmTablaPremios> lstTablaPremios) {
        this.lstTablaPremios = lstTablaPremios;
    }

    public Integer getI_idArea() {
        return i_idArea;
    }

    public void setI_idArea(Integer i_idArea) {
        this.i_idArea = i_idArea;
    }

    public boolean isB_otorgado() {
        return b_otorgado;
    }

    public void setB_otorgado(boolean b_otorgado) {
        this.b_otorgado = b_otorgado;
    }

}
