/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgSerconMotivoFacadeLocal;
import com.movilidad.model.PrgSerconMotivo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "motivoPrgSerconController")
@ViewScoped
public class MotivoPrgSerconController implements Serializable {

    @EJB
    private PrgSerconMotivoFacadeLocal serconMotivoFacadeLocal;

    private PrgSerconMotivo prgSerconMotivo;

    private List<PrgSerconMotivo> listPrgSerconMotivo;

    public MotivoPrgSerconController() {
    }

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public void prepareGuardar() {
        prgSerconMotivo = new PrgSerconMotivo();
    }

    public void prepareEditar(PrgSerconMotivo psm) {
        prgSerconMotivo = psm;
    }

    public void guardar() {
        try {
            if (prgSerconMotivo.getDescripcion() != null) {
                prgSerconMotivo.setDescripcion(prgSerconMotivo.getDescripcion().toUpperCase());
                prgSerconMotivo.setCreado(new Date());
                prgSerconMotivo.setModificado(new Date());
                prgSerconMotivo.setEstadoReg(0);
                prgSerconMotivo.setUsername(user.getUsername());
                serconMotivoFacadeLocal.create(prgSerconMotivo);
                MovilidadUtil.addSuccessMessage("Se registró Motivo correctamente");
                prepareGuardar();
            }
        } catch (Exception e) {
            System.out.println("Error al guardar Motivo");
        }
    }

    public void editar() {
        try {
            if (prgSerconMotivo.getDescripcion() != null) {
                prgSerconMotivo.setDescripcion(prgSerconMotivo.getDescripcion().toUpperCase());
                serconMotivoFacadeLocal.edit(prgSerconMotivo);
                MovilidadUtil.addSuccessMessage("Se registró Motivo correctamente");
                MovilidadUtil.hideModal("motivoDlg");
                reset();
            }
        } catch (Exception e) {
            System.out.println("Error al editar Motivo");
        }
    }

    public void eliminar(PrgSerconMotivo psm) {
        prgSerconMotivo = psm;
        try {
            if (prgSerconMotivo != null) {
                prgSerconMotivo.setEstadoReg(1);
                serconMotivoFacadeLocal.edit(prgSerconMotivo);
                MovilidadUtil.addSuccessMessage("Se eliminó Motivo correctamente");
                reset();
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar Motivo");
        }
    }

    public void reset() {
        prgSerconMotivo = null;
    }

    public PrgSerconMotivo getPrgSerconMotivo() {
        return prgSerconMotivo;
    }

    public void setPrgSerconMotivo(PrgSerconMotivo prgSerconMotivo) {
        this.prgSerconMotivo = prgSerconMotivo;
    }

    public List<PrgSerconMotivo> getListPrgSerconMotivo() {
        listPrgSerconMotivo = serconMotivoFacadeLocal.findAllEstadoRegistro();
        return listPrgSerconMotivo;
    }

}
