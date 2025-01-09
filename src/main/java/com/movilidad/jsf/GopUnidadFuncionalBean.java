package com.movilidad.jsf;

import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.ejb.UsersFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.Users;
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
@Named(value = "uniFuncBean")
@ViewScoped
public class GopUnidadFuncionalBean implements Serializable {

    @EJB
    private GopUnidadFuncionalFacadeLocal gopUniFuncEjb;

    @EJB
    private UsersFacadeLocal userEJB;
    private GopUnidadFuncional gopUnidadFuncional;
    private GopUnidadFuncional gopUnidadFuncGlobal;
    private GopUnidadFuncional selected;
    private String nombre;
    private String codigo;

    private boolean isEditing;

    private List<GopUnidadFuncional> lstGopUnidadFuncionals;

    UserExtended user;

    @PostConstruct
    public void init() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (MovilidadUtil.validarUrl("unidad_funcional")) {
            consultar();
        }
        if (gopUnidadFuncGlobal == null) {
            gopUnidadFuncGlobal = consultarUnidadFuncional();
        }

    }

    void consultar() {
        lstGopUnidadFuncionals = gopUniFuncEjb.findAllByEstadoReg();
    }

    GopUnidadFuncional consultarUnidadFuncional() {
        Users findUserForUsername = userEJB.findUserForUsername(user.getUsername());
        if (findUserForUsername != null) {
            return findUserForUsername.getIdGopUnidadFuncional();
        }
        return null;
    }

    public void nuevo() {
        nombre = "";
        codigo = "";
        gopUnidadFuncional = new GopUnidadFuncional();
        selected = null;
        isEditing = false;
    }

    public void editar(GopUnidadFuncional param) {
        selected = param;
        nombre = selected.getNombre();
        codigo = selected.getCodigo();
        gopUnidadFuncional = selected;
        isEditing = true;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        if (validarDatos()) {
            return;
        }
        gopUnidadFuncional.setNombre(nombre);
        gopUnidadFuncional.setCodigo(codigo);
        gopUnidadFuncional.setUsername(user.getUsername());

        if (isEditing) {

            gopUnidadFuncional.setModificado(MovilidadUtil.fechaCompletaHoy());
            gopUniFuncEjb.edit(gopUnidadFuncional);

            PrimeFaces.current().executeScript("PF('wlvTipoProceso').hide();");
            MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
        } else {
            gopUnidadFuncional.setEstadoReg(0);
            gopUnidadFuncional.setCreado(MovilidadUtil.fechaCompletaHoy());
            gopUniFuncEjb.create(gopUnidadFuncional);
            lstGopUnidadFuncionals.add(gopUnidadFuncional);

            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
        }
    }

    public boolean validarDatos() {
        if (gopUniFuncEjb.findByNombre(nombre.trim(), selected == null ? 0
                : selected.getIdGopUnidadFuncional()) != null) {
            MovilidadUtil.addErrorMessage("Ya existe un registro con el nombre a ingresar");
            return true;
        }
        if (gopUniFuncEjb.findByCodigo(codigo.trim(), selected == null ? 0
                : selected.getIdGopUnidadFuncional()) != null) {
            MovilidadUtil.addErrorMessage("Ya existe un registro con el código a ingresar");
            return true;
        }
        return false;
    }

    public GopUnidadFuncional findUnidadFuncionalByCodigo(String codigo) {
        return gopUniFuncEjb.findByCodigo(codigo);
    }
    
    public GopUnidadFuncional getGopUnidadFuncional() {
        return gopUnidadFuncional;
    }

    public void setGopUnidadFuncional(GopUnidadFuncional gopUnidadFuncional) {
        this.gopUnidadFuncional = gopUnidadFuncional;
    }

    public GopUnidadFuncional getSelected() {
        return selected;
    }

    public void setSelected(GopUnidadFuncional selected) {
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

    public List<GopUnidadFuncional> getLstGopUnidadFuncionals() {
        return lstGopUnidadFuncionals;
    }

    public void setLstGopUnidadFuncionals(List<GopUnidadFuncional> lstGopUnidadFuncionals) {
        this.lstGopUnidadFuncionals = lstGopUnidadFuncionals;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
