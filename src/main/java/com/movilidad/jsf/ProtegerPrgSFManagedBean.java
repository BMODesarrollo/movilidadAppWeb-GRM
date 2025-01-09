/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgTcResumenFacadeLocal;
import com.movilidad.model.PrgTcResumen;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "protegerPrgSFMB")
@ViewScoped
public class ProtegerPrgSFManagedBean implements Serializable {

    private Date fecha = null;
    private int flag = 0;
    private PrgTcResumen obj;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @EJB
    private PrgTcResumenFacadeLocal prgTcResuEJB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    /**
     * Creates a new instance of ProtegerPrgSFManagedBean
     */
    public ProtegerPrgSFManagedBean() {
    }

    @PostConstruct
    public void init() {
        fecha = MovilidadUtil.fechaCompletaHoy();
//        validarPrg();

    }

    public void validarPrg() {
        obj = prgTcResuEJB.findByFecha(fecha, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (obj == null) {
            MovilidadUtil.addAdvertenciaMessage("No hay registro para esta fecha.");
            flag = 0;
            return;
        }
        if (obj.getConciliado() == null) {
            flag = 1;
            return;
        }
        if (obj.getConciliado() == 2) {
            flag = 2;
            return;
        }
        if (obj.getConciliado() == 1) {
            MovilidadUtil.addAdvertenciaMessage("La fecha seleccionada ya está conciliada, no se puede hacer operación sobre esta.");
            flag = 0;
            return;
        }
        flag = 1;
    }

    /**
     * Bloquear el día de operación para no permitir cambios desde el panel
     * principal
     *
     * (conciliado = 1): El día ya esta conciliado desde el módulo de
     * conciliación.
     *
     * (conciliado = 2): El día ya esta bloqueado desde el módulo de bloquear
     * día.
     *
     * (conciliado = 0 || null): El día ya esta desbloqueado.
     */
    @Transactional
    public void bloquear() {
        if (obj == null) {
            MovilidadUtil.addAdvertenciaMessage("No se realizó ningun cambio");
            return;
        }
        if (obj.getConciliado() != null) {
            if (obj.getConciliado() == 1) {
                MovilidadUtil.addAdvertenciaMessage("La fecha seleccionada ya está conciliada, no se puede hacer operación sobre esta.");
                flag = 0;
                return;
            }
        }
        if (obj.getConciliado() != null) {
            if (obj.getConciliado() == 2) {
                obj.setConciliado(0);
                flag = 1;
            } else {
                obj.setConciliado(2);
                flag = 2;
            }
        } else {
            obj.setConciliado(2);
            flag = 2;
        }
        obj.setUsername(user.getUsername());
        obj.setModificado(MovilidadUtil.fechaCompletaHoy());
        prgTcResuEJB.edit(obj);
        MovilidadUtil.addSuccessMessage("Accion realizada exitosamente.");

    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

}
