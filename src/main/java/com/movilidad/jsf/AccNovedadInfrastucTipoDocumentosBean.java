package com.movilidad.jsf;

import com.movilidad.ejb.AccNovedadInfraestrucTipoDocumentosFacadeLocal;
import com.movilidad.model.AccNovedadInfraestrucTipoDocumentos;
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
@Named(value = "accNovedadInfrastucTipoDocumentosBean")
@ViewScoped
public class AccNovedadInfrastucTipoDocumentosBean implements Serializable {

    @EJB
    private AccNovedadInfraestrucTipoDocumentosFacadeLocal accNovedadInfraestrucTipoDocumentosEjb;

    private AccNovedadInfraestrucTipoDocumentos accNovedadInfraestrucTipoDocumentos;
    private AccNovedadInfraestrucTipoDocumentos selected;
    private String nombre;

    private boolean isEditing;
    private boolean b_oligatorio;

    private List<AccNovedadInfraestrucTipoDocumentos> lstAccNovedadInfraestrucTipoDocumentos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstAccNovedadInfraestrucTipoDocumentos = accNovedadInfraestrucTipoDocumentosEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        b_oligatorio = false;
        accNovedadInfraestrucTipoDocumentos = new AccNovedadInfraestrucTipoDocumentos();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        isEditing = true;
        b_oligatorio = (selected.getObligatorio() == 1);
        nombre = selected.getNombre();
        accNovedadInfraestrucTipoDocumentos = selected;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            if (isEditing) {
                accNovedadInfraestrucTipoDocumentos.setObligatorio(b_oligatorio ? 1 : 0);
                accNovedadInfraestrucTipoDocumentos.setNombre(nombre);
                accNovedadInfraestrucTipoDocumentos.setModificado(new Date());
                accNovedadInfraestrucTipoDocumentos.setUsername(user.getUsername());
                accNovedadInfraestrucTipoDocumentosEjb.edit(accNovedadInfraestrucTipoDocumentos);

                PrimeFaces.current().executeScript("PF('wlvCableEventoTipo').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                accNovedadInfraestrucTipoDocumentos.setObligatorio(b_oligatorio ? 1 : 0);
                accNovedadInfraestrucTipoDocumentos.setNombre(nombre);
                accNovedadInfraestrucTipoDocumentos.setEstadoReg(0);
                accNovedadInfraestrucTipoDocumentos.setCreado(new Date());
                accNovedadInfraestrucTipoDocumentos.setUsername(user.getUsername());
                accNovedadInfraestrucTipoDocumentosEjb.create(accNovedadInfraestrucTipoDocumentos);
                lstAccNovedadInfraestrucTipoDocumentos.add(accNovedadInfraestrucTipoDocumentos);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {
        if (isEditing) {
            if (accNovedadInfraestrucTipoDocumentosEjb.findByNombre(nombre.trim(), selected.getIdAccNovedadInfraestrucTipoDocumentos()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstAccNovedadInfraestrucTipoDocumentos.isEmpty()) {
                if (accNovedadInfraestrucTipoDocumentosEjb.findByNombre(nombre.trim(), 0) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }

        return null;
    }

    public AccNovedadInfraestrucTipoDocumentos getAccNovedadInfraestrucTipoDocumentos() {
        return accNovedadInfraestrucTipoDocumentos;
    }

    public void setAccNovedadInfraestrucTipoDocumentos(AccNovedadInfraestrucTipoDocumentos accNovedadInfraestrucTipoDocumentos) {
        this.accNovedadInfraestrucTipoDocumentos = accNovedadInfraestrucTipoDocumentos;
    }

    public AccNovedadInfraestrucTipoDocumentos getSelected() {
        return selected;
    }

    public void setSelected(AccNovedadInfraestrucTipoDocumentos selected) {
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

    public List<AccNovedadInfraestrucTipoDocumentos> getLstAccNovedadInfraestrucTipoDocumentos() {
        return lstAccNovedadInfraestrucTipoDocumentos;
    }

    public void setLstAccNovedadInfraestrucTipoDocumentos(List<AccNovedadInfraestrucTipoDocumentos> lstAccNovedadInfraestrucTipoDocumentos) {
        this.lstAccNovedadInfraestrucTipoDocumentos = lstAccNovedadInfraestrucTipoDocumentos;
    }

    public boolean isB_oligatorio() {
        return b_oligatorio;
    }

    public void setB_oligatorio(boolean b_oligatorio) {
        this.b_oligatorio = b_oligatorio;
    }

}
