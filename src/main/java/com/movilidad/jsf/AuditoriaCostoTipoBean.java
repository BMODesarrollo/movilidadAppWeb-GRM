package com.movilidad.jsf;

import com.movilidad.ejb.AuditoriaCostoTipoFacadeLocal;
import com.movilidad.ejb.VehiculoTipoFacadeLocal;
import com.movilidad.model.AuditoriaCostoTipo;
import com.movilidad.model.VehiculoTipo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
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
@Named(value = "auditoriaCostoTipoBean")
@ViewScoped
public class AuditoriaCostoTipoBean implements Serializable {

    @EJB
    private AuditoriaCostoTipoFacadeLocal auditoriaCostoTipoEjb;

    private AuditoriaCostoTipo auditoriaCostoTipo;
    private AuditoriaCostoTipo selected;
    private VehiculoTipo vehiculoTipo;
    private String nombre;

    private boolean isEditing;
    private boolean reqTipoVehiculo;

    private List<AuditoriaCostoTipo> lstAuditoriaCostoTipos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstAuditoriaCostoTipos = auditoriaCostoTipoEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = null;
        auditoriaCostoTipo = new AuditoriaCostoTipo();
        selected = null;
        isEditing = false;
        reqTipoVehiculo = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        auditoriaCostoTipo = selected;
        reqTipoVehiculo = (auditoriaCostoTipo.getRequiereTipoVehiculo() == 1);
        isEditing = true;

    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            auditoriaCostoTipo.setNombre(nombre);
            auditoriaCostoTipo.setUsername(user.getUsername());
            auditoriaCostoTipo.setRequiereTipoVehiculo(reqTipoVehiculo ? 1 : 0);

            if (isEditing) {
                auditoriaCostoTipo.setModificado(MovilidadUtil.fechaCompletaHoy());
                auditoriaCostoTipoEjb.edit(auditoriaCostoTipo);

                PrimeFaces.current().executeScript("PF('wlvTipoEvidencia').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                auditoriaCostoTipo.setEstadoReg(0);
                auditoriaCostoTipo.setCreado(MovilidadUtil.fechaCompletaHoy());
                auditoriaCostoTipoEjb.create(auditoriaCostoTipo);
                lstAuditoriaCostoTipos.add(auditoriaCostoTipo);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }

            init();
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    /**
     * Método que dispara al seleccionar el nombre de tipo 'Unitario por Tipo de
     * Vehículo' y carga la lista de tipos de vehículos (Éste nombre de tipo
     * siempre va a requerir un tipo de vehículo)
     */
    public void verificarTipoVehiculo() {
        if (nombre.trim().equals("Unitario por Tipo de Vehículo")) {
            reqTipoVehiculo = true;
        }
    }

    private String validarDatos() {
        if (isEditing) {
            if (auditoriaCostoTipoEjb.findByNombre(selected.getIdAuditoriaCostoTipo(), nombre.trim()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstAuditoriaCostoTipos.isEmpty()) {
                if (auditoriaCostoTipoEjb.findByNombre(0, nombre.trim()) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }

        return null;
    }

    public AuditoriaCostoTipo getAuditoriaCostoTipo() {
        return auditoriaCostoTipo;
    }

    public void setAuditoriaCostoTipo(AuditoriaCostoTipo auditoriaCostoTipo) {
        this.auditoriaCostoTipo = auditoriaCostoTipo;
    }

    public AuditoriaCostoTipo getSelected() {
        return selected;
    }

    public void setSelected(AuditoriaCostoTipo selected) {
        this.selected = selected;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<AuditoriaCostoTipo> getLstAuditoriaCostoTipos() {
        return lstAuditoriaCostoTipos;
    }

    public void setLstAuditoriaCostoTipos(List<AuditoriaCostoTipo> lstAuditoriaCostoTipos) {
        this.lstAuditoriaCostoTipos = lstAuditoriaCostoTipos;
    }

    public boolean isReqTipoVehiculo() {
        return reqTipoVehiculo;
    }

    public void setReqTipoVehiculo(boolean reqTipoVehiculo) {
        this.reqTipoVehiculo = reqTipoVehiculo;
    }

    public VehiculoTipo getVehiculoTipo() {
        return vehiculoTipo;
    }

    public void setVehiculoTipo(VehiculoTipo vehiculoTipo) {
        this.vehiculoTipo = vehiculoTipo;
    }

}
