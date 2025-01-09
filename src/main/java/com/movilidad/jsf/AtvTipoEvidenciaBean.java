package com.movilidad.jsf;

import com.movilidad.ejb.AtvTipoEvidenciaFacadeLocal;
import com.movilidad.model.AtvTipoEvidencia;
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
@Named(value = "atvTipoEvidenciaBean")
@ViewScoped
public class AtvTipoEvidenciaBean implements Serializable {

    @EJB
    private AtvTipoEvidenciaFacadeLocal atvTipoEvidenciaEjb;

    private AtvTipoEvidencia atvTipoEvidencia;
    private AtvTipoEvidencia selected;
    private String nombre;

    private boolean isEditing;

    private List<AtvTipoEvidencia> lstAtvTipoEvidencias;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstAtvTipoEvidencias = atvTipoEvidenciaEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        atvTipoEvidencia = new AtvTipoEvidencia();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        atvTipoEvidencia = selected;
        isEditing = true;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            if (isEditing) {

                atvTipoEvidencia.setNombre(nombre);
                atvTipoEvidencia.setModificado(MovilidadUtil.fechaCompletaHoy());
                atvTipoEvidencia.setUsername(user.getUsername());
                atvTipoEvidenciaEjb.edit(atvTipoEvidencia);

                PrimeFaces.current().executeScript("PF('wlvTipoEvidencia').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                atvTipoEvidencia.setNombre(nombre);
                atvTipoEvidencia.setEstadoReg(0);
                atvTipoEvidencia.setCreado(MovilidadUtil.fechaCompletaHoy());
                atvTipoEvidencia.setUsername(user.getUsername());
                atvTipoEvidenciaEjb.create(atvTipoEvidencia);
                lstAtvTipoEvidencias.add(atvTipoEvidencia);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public String validarDatos() {
        if (isEditing) {
            if (atvTipoEvidenciaEjb.findByNombre(selected.getIdAtvTipoEvidencia(), nombre.trim()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstAtvTipoEvidencias.isEmpty()) {
                if (atvTipoEvidenciaEjb.findByNombre(0, nombre.trim()) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }

        return null;
    }

    public AtvTipoEvidencia getAtvTipoEvidencia() {
        return atvTipoEvidencia;
    }

    public void setAtvTipoEvidencia(AtvTipoEvidencia atvTipoEvidencia) {
        this.atvTipoEvidencia = atvTipoEvidencia;
    }

    public AtvTipoEvidencia getSelected() {
        return selected;
    }

    public void setSelected(AtvTipoEvidencia selected) {
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

    public List<AtvTipoEvidencia> getLstAtvTipoEvidencias() {
        return lstAtvTipoEvidencias;
    }

    public void setLstAtvTipoEvidencias(List<AtvTipoEvidencia> lstAtvTipoEvidencias) {
        this.lstAtvTipoEvidencias = lstAtvTipoEvidencias;
    }

}
