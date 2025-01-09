package com.movilidad.jsf;

import com.movilidad.ejb.CableRevisionDiaHorarioFacadeLocal;
import com.movilidad.model.CableRevisionDiaHorario;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "cableRevisionDiaHorarioBean")
@ViewScoped
public class CableRevisionDiaHorarioBean implements Serializable {

    @EJB
    private CableRevisionDiaHorarioFacadeLocal cableEventoTipoEjb;

    private CableRevisionDiaHorario cableRevisionDiaHorario;
    private CableRevisionDiaHorario selected;
    private String hora;

    private boolean isEditing;

    private List<CableRevisionDiaHorario> lstCableRevisionDiaHorarios;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstCableRevisionDiaHorarios = cableEventoTipoEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        hora = "";
        cableRevisionDiaHorario = new CableRevisionDiaHorario();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        isEditing = true;
        hora = selected.getHora();
        cableRevisionDiaHorario = selected;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {

            cableRevisionDiaHorario.setHora(hora);
            if (isEditing) {
                cableRevisionDiaHorario.setModificado(new Date());
                cableRevisionDiaHorario.setUsername(user.getUsername());
                cableEventoTipoEjb.edit(cableRevisionDiaHorario);

                PrimeFaces.current().executeScript("PF('wlvCableRevisionDiaHorario').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                cableRevisionDiaHorario.setEstadoReg(0);
                cableRevisionDiaHorario.setCreado(new Date());
                cableRevisionDiaHorario.setUsername(user.getUsername());
                cableEventoTipoEjb.create(cableRevisionDiaHorario);
                lstCableRevisionDiaHorarios.add(cableRevisionDiaHorario);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {
        if (isEditing) {
            if (cableEventoTipoEjb.findByHora(hora.trim(), selected.getIdCableRevisionDiaHorario()) != null) {
                return "YA existe un registro con la hora a ingresar";
            }
        } else {
            if (!lstCableRevisionDiaHorarios.isEmpty()) {
                if (cableEventoTipoEjb.findByHora(hora.trim(), 0) != null) {
                    return "YA existe un registro con la hora a ingresar";
                }
            }
        }

        return null;
    }

    public CableRevisionDiaHorario getCableRevisionDiaHorario() {
        return cableRevisionDiaHorario;
    }

    public void setCableRevisionDiaHorario(CableRevisionDiaHorario cableEventoTipo) {
        this.cableRevisionDiaHorario = cableEventoTipo;
    }

    public CableRevisionDiaHorario getSelected() {
        return selected;
    }

    public void setSelected(CableRevisionDiaHorario selected) {
        this.selected = selected;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<CableRevisionDiaHorario> getLstCableRevisionDiaHorarios() {
        return lstCableRevisionDiaHorarios;
    }

    public void setLstCableRevisionDiaHorarios(List<CableRevisionDiaHorario> lstCableRevisionDiaHorarios) {
        this.lstCableRevisionDiaHorarios = lstCableRevisionDiaHorarios;
    }

}
