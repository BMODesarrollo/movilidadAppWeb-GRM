package com.movilidad.jsf;

import com.movilidad.ejb.AccNovedadTipoDetallesInfrastrucFacadeLocal;
import com.movilidad.ejb.AccNovedadTipoInfrastrucFacadeLocal;
import com.movilidad.model.AccNovedadTipoDetallesInfrastruc;
import com.movilidad.model.AccNovedadTipoInfrastruc;
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
@Named(value = "accNovedadTipoDetallesInfrastuc")
@ViewScoped
public class AccNovedadTipoDetallesInfrastucBean implements Serializable {

    @EJB
    private AccNovedadTipoDetallesInfrastrucFacadeLocal accNovedadTipoDetallesInfrastrucEjb;
    @EJB
    private AccNovedadTipoInfrastrucFacadeLocal accNovedadTipoInfrastrucEjb;

    private AccNovedadTipoDetallesInfrastruc accNovedadTipoDetallesInfrastruc;
    private AccNovedadTipoDetallesInfrastruc selected;
    private String nombre;
    private Integer idTipoNovedad;

    private boolean isEditing;
    private boolean b_notifica;

    private List<AccNovedadTipoDetallesInfrastruc> lstAccNovedadTipoDetallesInfrastruc;
    private List<AccNovedadTipoInfrastruc> lstAccNovedadTipoInfrastrucs;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstAccNovedadTipoDetallesInfrastruc = accNovedadTipoDetallesInfrastrucEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        isEditing = false;
        b_notifica = false;
        nombre = "";
        idTipoNovedad = null;
        lstAccNovedadTipoInfrastrucs = accNovedadTipoInfrastrucEjb.findAllByEstadoReg();
        accNovedadTipoDetallesInfrastruc = new AccNovedadTipoDetallesInfrastruc();
        selected = null;
    }

    public void editar() {
        isEditing = true;
        b_notifica = (selected.getNotifica() == 1);
        lstAccNovedadTipoInfrastrucs = accNovedadTipoInfrastrucEjb.findAllByEstadoReg();
        idTipoNovedad = selected.getIdAccNovedadTipoInfrastruc().getIdAccNovedadTipoInfrastruc();
        nombre = selected.getNombre();
        accNovedadTipoDetallesInfrastruc = selected;
    }

    public void guardar() {
        guardarTransactional();
    }

    public void cargarEmails() {
        if (b_notifica) {
            accNovedadTipoDetallesInfrastruc.setEmails("");
        }
    }

    @Transactional
    private void guardarTransactional() {
        String validacion = validarDatos();

        if (validacion == null) {
            if (isEditing) {
                accNovedadTipoDetallesInfrastruc.setIdAccNovedadTipoInfrastruc(new AccNovedadTipoInfrastruc(idTipoNovedad));
                accNovedadTipoDetallesInfrastruc.setNotifica(b_notifica ? 1 : 0);
                accNovedadTipoDetallesInfrastruc.setNombre(nombre);
                accNovedadTipoDetallesInfrastruc.setUsername(user.getUsername());
                accNovedadTipoDetallesInfrastruc.setModificado(new Date());
                accNovedadTipoDetallesInfrastrucEjb.edit(accNovedadTipoDetallesInfrastruc);

                PrimeFaces.current().executeScript("PF('wlvAccNovedadTipoDetallesInfrastruc').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                accNovedadTipoDetallesInfrastruc.setIdAccNovedadTipoInfrastruc(new AccNovedadTipoInfrastruc(idTipoNovedad));
                accNovedadTipoDetallesInfrastruc.setNotifica(b_notifica ? 1 : 0);
                accNovedadTipoDetallesInfrastruc.setNombre(nombre);
                accNovedadTipoDetallesInfrastruc.setUsername(user.getUsername());
                accNovedadTipoDetallesInfrastruc.setEstadoReg(0);
                accNovedadTipoDetallesInfrastruc.setCreado(new Date());
                accNovedadTipoDetallesInfrastrucEjb.create(accNovedadTipoDetallesInfrastruc);

                lstAccNovedadTipoDetallesInfrastruc.add(accNovedadTipoDetallesInfrastruc);
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
                nuevo();
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
        init();
    }

    private String validarDatos() {

        if (isEditing) {
            if (accNovedadTipoDetallesInfrastrucEjb.findByNombre(nombre.trim(), selected.getIdAccNovedadTipoDetalleInfrastruc(), idTipoNovedad) != null) {
                return "YA existe un registro con el nombre ingresado";
            }
        } else {
            if (!lstAccNovedadTipoDetallesInfrastruc.isEmpty()) {
                if (accNovedadTipoDetallesInfrastrucEjb.findByNombre(nombre.trim(), 0, idTipoNovedad) != null) {
                    return "YA existe un registro con el nombre ingresado";
                }
            }
        }
        return null;
    }

    public AccNovedadTipoDetallesInfrastruc getAccNovedadTipoDetallesInfrastruc() {
        return accNovedadTipoDetallesInfrastruc;
    }

    public void setAccNovedadTipoDetallesInfrastruc(AccNovedadTipoDetallesInfrastruc cableRevisionActividad) {
        this.accNovedadTipoDetallesInfrastruc = cableRevisionActividad;
    }

    public AccNovedadTipoDetallesInfrastruc getSelected() {
        return selected;
    }

    public void setSelected(AccNovedadTipoDetallesInfrastruc selected) {
        this.selected = selected;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<AccNovedadTipoDetallesInfrastruc> getLstAccNovedadTipoDetallesInfrastruc() {
        return lstAccNovedadTipoDetallesInfrastruc;
    }

    public void setLstAccNovedadTipoDetallesInfrastruc(List<AccNovedadTipoDetallesInfrastruc> lstAccNovedadTipoDetallesInfrastruc) {
        this.lstAccNovedadTipoDetallesInfrastruc = lstAccNovedadTipoDetallesInfrastruc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isB_notifica() {
        return b_notifica;
    }

    public void setB_notifica(boolean b_notifica) {
        this.b_notifica = b_notifica;
    }

    public Integer getIdTipoNovedad() {
        return idTipoNovedad;
    }

    public void setIdTipoNovedad(Integer idTipoNovedad) {
        this.idTipoNovedad = idTipoNovedad;
    }

    public List<AccNovedadTipoInfrastruc> getLstAccNovedadTipoInfrastrucs() {
        return lstAccNovedadTipoInfrastrucs;
    }

    public void setLstAccNovedadTipoInfrastrucs(List<AccNovedadTipoInfrastruc> lstAccNovedadTipoInfrastrucs) {
        this.lstAccNovedadTipoInfrastrucs = lstAccNovedadTipoInfrastrucs;
    }

}
