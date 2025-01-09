package com.movilidad.jsf;

import com.movilidad.ejb.PdTipoDocumentosFacadeLocal;
import com.movilidad.model.PdTipoDocumentos;
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
@Named(value = "pdTipoDocumentosBean")
@ViewScoped
public class PdTipoDocumentosBean implements Serializable {

    @EJB
    private PdTipoDocumentosFacadeLocal pdTipoDocumentosEjb;

    private PdTipoDocumentos pdTipoDocumentos;
    private PdTipoDocumentos selected;
    private String nombre;

    private boolean isEditing;

    private List<PdTipoDocumentos> lstPdTipoDocumentos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstPdTipoDocumentos = pdTipoDocumentosEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        nombre = "";
        pdTipoDocumentos = new PdTipoDocumentos();
        selected = null;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        pdTipoDocumentos = selected;
        isEditing = true;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            pdTipoDocumentos.setNombre(nombre);
            pdTipoDocumentos.setUsername(user.getUsername());

            if (isEditing) {

                pdTipoDocumentos.setModificado(MovilidadUtil.fechaCompletaHoy());
                pdTipoDocumentosEjb.edit(pdTipoDocumentos);

                PrimeFaces.current().executeScript("PF('wlvTipoDocumentos').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                pdTipoDocumentos.setEstadoReg(0);
                pdTipoDocumentos.setCreado(MovilidadUtil.fechaCompletaHoy());
                pdTipoDocumentosEjb.create(pdTipoDocumentos);
                lstPdTipoDocumentos.add(pdTipoDocumentos);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public String validarDatos() {
        if (isEditing) {
            if (pdTipoDocumentosEjb.findByNombre(selected.getIdPdTipoDocumento(), nombre.trim()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
        } else {
            if (!lstPdTipoDocumentos.isEmpty()) {
                if (pdTipoDocumentosEjb.findByNombre(0, nombre.trim()) != null) {
                    return "YA existe un registro con el nombre a ingresar";
                }
            }
        }

        return null;
    }

    public PdTipoDocumentos getPdTipoDocumentos() {
        return pdTipoDocumentos;
    }

    public void setPdTipoDocumentos(PdTipoDocumentos pdTipoDocumentos) {
        this.pdTipoDocumentos = pdTipoDocumentos;
    }

    public PdTipoDocumentos getSelected() {
        return selected;
    }

    public void setSelected(PdTipoDocumentos selected) {
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

    public List<PdTipoDocumentos> getLstPdTipoDocumentos() {
        return lstPdTipoDocumentos;
    }

    public void setLstPdTipoDocumentos(List<PdTipoDocumentos> lstPdTipoDocumentos) {
        this.lstPdTipoDocumentos = lstPdTipoDocumentos;
    }

}
