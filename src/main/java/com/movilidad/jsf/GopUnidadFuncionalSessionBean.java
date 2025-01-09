package com.movilidad.jsf;

import com.movilidad.ejb.UsersFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.Users;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "uniFuncSessionBean")
@SessionScoped
public class GopUnidadFuncionalSessionBean implements Serializable {

    @EJB
    private UsersFacadeLocal userEJB;
    private GopUnidadFuncional gopUnidadFuncional;

    private Integer i_unidad_funcional;
    private boolean filtradoUF;

    @PostConstruct
    public void init() {
        filtradoUF = getIdGopUnidadFuncional() == 0;
    }

    int getIdGopUnidadFuncional() {
        return getGopUnidadFuncional() == null ? 0
                : getGopUnidadFuncional().getIdGopUnidadFuncional();
    }

    public int obtenerIdGopUnidadFuncional() {
        int idUF = getIdGopUnidadFuncional();
        if (idUF == 0) {
            idUF = getI_unidad_funcional() == null ? 0 : getI_unidad_funcional();
        }
        return idUF;
    }

    public void cargarUnidadFuncional(String username) {
        gopUnidadFuncional = consultarUnidadFuncional(username);
    }

    GopUnidadFuncional consultarUnidadFuncional(String username) {
        Users findUserForUsername = userEJB.findUserForUsername(username);
        return findUserForUsername == null ? null : findUserForUsername.getIdGopUnidadFuncional();
    }

    public GopUnidadFuncional getGopUnidadFuncional() {
        return gopUnidadFuncional;
    }

    public void setGopUnidadFuncional(GopUnidadFuncional gopUnidadFuncional) {
        this.gopUnidadFuncional = gopUnidadFuncional;
    }

    public Integer getI_unidad_funcional() {
        return i_unidad_funcional;
    }

    public void setI_unidad_funcional(Integer i_unidad_funcional) {
        this.i_unidad_funcional = i_unidad_funcional;
    }

    public boolean isFiltradoUF() {
        return filtradoUF;
    }

    public void setFiltradoUF(boolean filtradoUF) {
        this.filtradoUF = filtradoUF;
    }

}
