/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GenericaJornadaMotivoFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.GenericaJornadaMotivo;
import com.movilidad.model.ParamAreaUsr;
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
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "genMotJSFMB")
@ViewScoped
public class GenericaMotivoMB implements Serializable {

    /**
     * Creates a new instance of GenericaJornadaJSFManagedBean
     */
    public GenericaMotivoMB() {
    }
    @EJB
    private GenericaJornadaMotivoFacadeLocal jornadaMotivoEJB;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;

    private GenericaJornadaMotivo obj;

    private ParamAreaUsr pau;

    private List<GenericaJornadaMotivo> listJornadaMotivo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public void prepareGuardar() {
        obj = new GenericaJornadaMotivo();
    }

    public void prepareEditar(GenericaJornadaMotivo gjm) {
        obj = gjm;
    }

    @PostConstruct
    public void init() {
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());

    }

    @Transactional
    public void guardar() {
        try {
            if (pau == null) {
                MovilidadUtil.addErrorMessage("Usuario no asignado a una area");
                return;
            }
            if (obj.getDescripcion() != null) {
                obj.setDescripcion(obj.getDescripcion());
                obj.setCreado(MovilidadUtil.fechaCompletaHoy());
                obj.setModificado(MovilidadUtil.fechaCompletaHoy());
                obj.setEstadoReg(0);
                obj.setIdParamArea(pau.getIdParamArea());
                obj.setUsername(user.getUsername());
                jornadaMotivoEJB.create(obj);
                MovilidadUtil.addSuccessMessage("Se registró Motivo correctamente");
                prepareGuardar();
            }
        } catch (Exception e) {
            System.out.println("Error al guardar Motivo");
        }
    }

    public void editar() {
        try {
            if (obj.getDescripcion() != null) {
                obj.setDescripcion(obj.getDescripcion());
                obj.setModificado(MovilidadUtil.fechaCompletaHoy());
                jornadaMotivoEJB.edit(obj);
                MovilidadUtil.hideModal("motivoDlg");
                MovilidadUtil.addSuccessMessage("Se registró Motivo correctamente");
                reset();
            }
        } catch (Exception e) {
            System.out.println("Error al editar Motivo");
        }
    }

    public void eliminar(GenericaJornadaMotivo psm) {
        obj = psm;
        try {
            if (obj != null) {
                obj.setEstadoReg(1);
                jornadaMotivoEJB.edit(obj);
                MovilidadUtil.addSuccessMessage("Se eliminó Motivo correctamente");
                reset();
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar Motivo");
        }
    }

    public void reset() {
        obj = null;
    }

    public GenericaJornadaMotivo getObj() {
        return obj;
    }

    public void setObj(GenericaJornadaMotivo obj) {
        this.obj = obj;
    }

    public List<GenericaJornadaMotivo> getListJornadaMotivo() {
        if (pau == null) {
            return new ArrayList<>();
        }
        listJornadaMotivo = jornadaMotivoEJB.findByArea(pau.getIdParamArea().getIdParamArea());
        return listJornadaMotivo;
    }

    public void setListJornadaMotivo(List<GenericaJornadaMotivo> listJornadaMotivo) {
        this.listJornadaMotivo = listJornadaMotivo;
    }

}
