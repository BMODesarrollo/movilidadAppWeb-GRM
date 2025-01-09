/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PdResponsablesFacadeLocal;
import com.movilidad.ejb.UsersFacadeLocal;
import com.movilidad.model.PdResponsables;
import com.movilidad.model.Users;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
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
 * @author Julián Arévalo
 */
@Named(value = "PdResponsablesController")
@ViewScoped
public class PdResponsablesController implements Serializable {

    /**
     * Creates a new instance of PmTipoDetalleIncluirJSFMB
     */
    public PdResponsablesController() {
    }

    @EJB
    private PdResponsablesFacadeLocal PdResponsablesFacadeLocal;
    @EJB
    private UsersFacadeLocal UsersFacadeLocal;

    private PdResponsables pdResponsable;
    private int idUserResponsable;
    private List<PdResponsables> listResponsable;
    private List<PdResponsables> listResponsableExiste;
    private List<Users> listUsuarios;
    private Users userResponsable;
    private final PrimeFaces current = PrimeFaces.current();
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    public void consultar() {
        listUsuarios = UsersFacadeLocal.findAllUsersActivos();
        listResponsable = PdResponsablesFacadeLocal.getAllActivo();
    }

    public void activarDesactivar(PdResponsables obj, int opc) {
        if (opc == 0) {
            obj.setActivo(0);
        } else {
            obj.setActivo(1);
        }
        obj.setModificado(MovilidadUtil.fechaCompletaHoy());
        PdResponsablesFacadeLocal.edit(obj);
        MovilidadUtil.addSuccessMessage("Acción completada exitosamente.");
        consultar();
    }

    public void guardar() {
        userResponsable = UsersFacadeLocal.find(idUserResponsable);
        guardarTransactional(userResponsable);
        consultar();
    }

    public void editar() {
//        periodoLiquidacion = PmPeriodosLiquidacionFacadeLocal.find(idPmPeriodoLiquidacion);
//        editarTransactional(periodoLiquidacion);
        consultar();
    }

    @Transactional
    public void guardarTransactional(Users obj) {
        Boolean exist = false;
        String estado = "";
        pdResponsable = new PdResponsables();

        listResponsableExiste = PdResponsablesFacadeLocal.findAll();

        for (PdResponsables objResponsable : listResponsableExiste) {
            if (obj.equals(objResponsable.getUser())) {
                estado = objResponsable.getActivo() == 1 ? " activo" : "inactivo";
                exist = true;
                break;
            }
        }

        if (!exist) {
            pdResponsable.setActivo(1);
            pdResponsable.setUser(obj);
            pdResponsable.setUsername(user.getUsername());
            pdResponsable.setEstadoReg(0);
            pdResponsable.setCreado(MovilidadUtil.fechaCompletaHoy());
            PdResponsablesFacadeLocal.create(pdResponsable);
            MovilidadUtil.addSuccessMessage("Se guardó el registro exitosamente.");
            MovilidadUtil.hideModal("wv_create_dlg");
            limpiarForm();
        } else {
            MovilidadUtil.addErrorMessage("El responsable ya existe y se encuentra " + estado);
            MovilidadUtil.hideModal("wv_create_dlg");
            limpiarForm();
        }

    }

    public void limpiarForm() {
        pdResponsable = new PdResponsables();
        listResponsableExiste = new ArrayList<>();
        idUserResponsable = 0;
    }

    @Transactional
    public void editarTransactional(PdResponsables obj) {
//        periodoLiquidacion.setIdPmPeriodoLiquidacion(obj.getIdPmPeriodoLiquidacion());
//        periodoLiquidacion.setActivo(activo ? 1 : 0);
//        periodoLiquidacion.setFechaInicio(fecha_inicio);
//        periodoLiquidacion.setFechaFin(fecha_fin);
//        periodoLiquidacion.setDescripcion(descripcion);
//        periodoLiquidacion.setUsername(user.getUsername());
//        periodoLiquidacion.setEstadoReg(0);
//        periodoLiquidacion.setModificado(MovilidadUtil.fechaCompletaHoy());
//
//        PmPeriodosLiquidacionFacadeLocal.edit(periodoLiquidacion);
//        MovilidadUtil.addSuccessMessage("Se actualizó el registro exitosamente.");
//        MovilidadUtil.hideModal("wv_create_dlg");

    }

    public PdResponsables getPdResponsable() {
        return pdResponsable;
    }

    public void setPdResponsable(PdResponsables pdResponsable) {
        this.pdResponsable = pdResponsable;
    }

    public List<PdResponsables> getListResponsable() {
        return listResponsable;
    }

    public void setListResponsable(List<PdResponsables> listResponsable) {
        this.listResponsable = listResponsable;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public List<Users> getListUsuarios() {
        return listUsuarios;
    }

    public void setListUsuarios(List<Users> listUsuarios) {
        this.listUsuarios = listUsuarios;
    }

    public int getIdUserResponsable() {
        return idUserResponsable;
    }

    public void setIdUserResponsable(int idUserResponsable) {
        this.idUserResponsable = idUserResponsable;
    }

}
