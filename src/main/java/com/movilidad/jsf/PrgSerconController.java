/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.ejb.UsersFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.PrgSercon;
import com.movilidad.model.Users;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author alexanderosorio
 */
@Named(value = "prgSerController")
@ViewScoped
public class PrgSerconController implements Serializable {

    private List<PrgSercon> prglist = new ArrayList<>();
    private List<PrgSercon> prgSerbusList = new ArrayList<>();
    private List<GopUnidadFuncional> lstUnidadesFuncionales;

    @EJB
    private PrgSerconFacadeLocal prgSerconFacade;
    @EJB
    private UsersFacadeLocal usersEjb;
    @EJB
    private GopUnidadFuncionalFacadeLocal unidadFuncionalEjb;

    private Date fecha;
    private Integer idGopUnidadFuncional;
    private boolean flagListaUF;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public List<PrgSercon> getPrgSerbusList() {
        return prgSerbusList;
    }

    public void setPrgSerbusList(List<PrgSercon> prgSerbusList) {
        this.prgSerbusList = prgSerbusList;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<PrgSercon> getPrglist() {
        return prglist;
    }

    public void setPrglist(List<PrgSercon> prglist) {
        this.prglist = prglist;
    }

    public List<GopUnidadFuncional> getLstUnidadesFuncionales() {
        return lstUnidadesFuncionales;
    }

    public void setLstUnidadesFuncionales(List<GopUnidadFuncional> lstUnidadesFuncionales) {
        this.lstUnidadesFuncionales = lstUnidadesFuncionales;
    }

    public Integer getIdGopUnidadFuncional() {
        return idGopUnidadFuncional;
    }

    public void setIdGopUnidadFuncional(Integer idGopUnidadFuncional) {
        this.idGopUnidadFuncional = idGopUnidadFuncional;
    }

    @PostConstruct
    public void init() {
        idGopUnidadFuncional = obtenerUnidadFuncional();
        if (idGopUnidadFuncional == null) {
            lstUnidadesFuncionales = unidadFuncionalEjb.findAllByEstadoReg();
            flagListaUF = true;
        }
        fecha = MovilidadUtil.fechaHoy();
        prglist = prgSerconFacade.findByFechaAndIdGopUnidadFunc(fecha, idGopUnidadFuncional);
    }

    public PrgSerconController() {
    }

    public void consultar() {
        prglist = prgSerconFacade.findByFechaAndIdGopUnidadFunc(fecha, idGopUnidadFuncional);
    }

    /*
     * Método que se encarga de obtener el id de la unidad funcional en base
     * al nombre del usuario logueado, devuelve un número si el usuario tiene 
     * unidad funcional asociada, de lo contrario devuelve NULL
     */
    private Integer obtenerUnidadFuncional() {
        Users usuario = usersEjb.findUserForUsername(user.getUsername());

        if (usuario.getIdGopUnidadFuncional() != null) {
            return usuario.getIdGopUnidadFuncional().getIdGopUnidadFuncional();
        }

        return null;

    }

    public boolean isFlagListaUF() {
        return flagListaUF;
    }

    public void setFlagListaUF(boolean flagListaUF) {
        this.flagListaUF = flagListaUF;
    }

}
