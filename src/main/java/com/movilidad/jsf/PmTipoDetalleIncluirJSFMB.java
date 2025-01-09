/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PmNovedadIncluirFacadeLocal;
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.NovedadTipoFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.PmNovedadIncluir;
import com.movilidad.model.NovedadTipo;
import com.movilidad.model.NovedadTipoDetalles;
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
@Named(value = "pmTipoDetIncluirJSFMB")
@ViewScoped
public class PmTipoDetalleIncluirJSFMB implements Serializable {

    /**
     * Creates a new instance of PmTipoDetalleIncluirJSFMB
     */
    public PmTipoDetalleIncluirJSFMB() {
    }
    
    @EJB
    private PmNovedadIncluirFacadeLocal pmNovedadIncluirFacadeLocal;

    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTDetEJB;

    @EJB
    private NovedadTipoFacadeLocal novedadTipoEJB;

    private PmNovedadIncluir genPmNovedadIncluir;
    private List<PmNovedadIncluir> list;

    private int i_tipo = 0;
    private int i_tipoDet = 0;
    private boolean activo = true;
    private NovedadTipoDetalles i_tipoDetObj;

    private List<NovedadTipo> listT;
    private List<NovedadTipoDetalles> listTDet;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    public void consultar() {
        list = pmNovedadIncluirFacadeLocal.getAllActivo();
    }

    public void preCreate() {
        i_tipo = 0;
        i_tipoDet = 0;
        i_tipoDetObj = null;
        genPmNovedadIncluir = null;
        listT = novedadTipoEJB.findAllAfectaPm(1);
        activo = true;
    }

    public void preEdit(PmNovedadIncluir obj) {
        listT = novedadTipoEJB.findAllAfectaPm(1);
        genPmNovedadIncluir = obj;
        activo = obj.getActivo() == 1;
        i_tipo = obj.getIdNovedadTipoDetalle().getIdNovedadTipo().getIdNovedadTipo();
        findById();
        i_tipoDetObj = obj.getIdNovedadTipoDetalle();
        i_tipoDet = obj.getIdNovedadTipoDetalle().getIdNovedadTipoDetalle();
    }

    public void activarDesactivar(PmNovedadIncluir obj, int opc) {
        if (opc == 0) {
            obj.setActivo(0);
        } else {
            obj.setActivo(1);
        }
        obj.setModificado(MovilidadUtil.fechaCompletaHoy());
        pmNovedadIncluirFacadeLocal.edit(obj);
        MovilidadUtil.addSuccessMessage("Acción completada exitosamente.");
        consultar();
    }

    public void guardar() {
        if (i_tipoDetObj == null) {
            MovilidadUtil.addErrorMessage("Seleccionar el tipo de detalle de novedad.");
            return;
        }
        if (pmNovedadIncluirFacadeLocal.getByIdNovedadTipoDet(i_tipoDetObj.getIdNovedadTipoDetalle(), 0) != null) {
            MovilidadUtil.addErrorMessage("Ya existe registro con el tipo Detalle indicado.");
            return;
        }
        guardarTransactional();
        consultar();
    }

    public void editar() {
        if (i_tipoDet == 0) {
            MovilidadUtil.addErrorMessage("Seleccionar el tipo de detalle de novedad.");
            return;
        }
        if (pmNovedadIncluirFacadeLocal.getByIdNovedadTipoDet(i_tipoDetObj.getIdNovedadTipoDetalle(), genPmNovedadIncluir.getIdPmNovedadIncluir()) != null) {
            MovilidadUtil.addErrorMessage("Ya existe registro con el tipo Detalle indicado.");
            return;
        }
        editarTransactional();
        consultar();
    }

    @Transactional
    public void guardarTransactional() {

        genPmNovedadIncluir = new PmNovedadIncluir();

        genPmNovedadIncluir.setActivo(activo ? 1 : 0);
        genPmNovedadIncluir.setIdNovedadTipoDetalle(i_tipoDetObj);
        genPmNovedadIncluir.setUsername(user.getUsername());
        genPmNovedadIncluir.setEstadoReg(0);
        genPmNovedadIncluir.setCreado(MovilidadUtil.fechaCompletaHoy());

        pmNovedadIncluirFacadeLocal.create(genPmNovedadIncluir);
        i_tipo = 0;
        i_tipoDet = 0;
        i_tipoDetObj = null;
        genPmNovedadIncluir = null;
        MovilidadUtil.addSuccessMessage("Se guardó el registro exitosamente.");
    }

    @Transactional
    public void editarTransactional() {

        genPmNovedadIncluir.setActivo(activo ? 1 : 0);
        genPmNovedadIncluir.setIdNovedadTipoDetalle(i_tipoDetObj);
        genPmNovedadIncluir.setUsername(user.getUsername());
        genPmNovedadIncluir.setEstadoReg(0);
        genPmNovedadIncluir.setCreado(MovilidadUtil.fechaCompletaHoy());

        pmNovedadIncluirFacadeLocal.edit(genPmNovedadIncluir);
        MovilidadUtil.addSuccessMessage("Se actualizó el registro exitosamente.");
        MovilidadUtil.hideModal("wv_create_dlg");

    }

    /**
     * Método responsable de cargar en la lista listNovedadTDet, los detalles
     * del tipo de novedad seleccionada.
     *
     * Es invocado en las vistas adicionarServicios y gestionPrgTc.
     *
     */
    public void findById() {
        for (NovedadTipo nt : listT) {
            if (nt.getIdNovedadTipo() == getI_tipo()) {
                if (listTDet == null) {
                    listTDet = new ArrayList<>();
                } else {
                    listTDet.clear();
                }
                for (NovedadTipoDetalles g : nt.getNovedadTipoDetallesList()) {
                    if (g.getAfectaPm() == 1 && g.getFechas() == 1) {
                        listTDet.add(g);
                    }
                }
                break;
            }
        }
    }

    public void setTipoNovedadDet() {
        if (i_tipoDet != 0) {
            i_tipoDetObj = novedadTDetEJB.find(i_tipoDet);
        }
    }

    public PmNovedadIncluir getGenPmNovedadIncluir() {
        return genPmNovedadIncluir;
    }

    public void setGenPmNovedadIncluir(PmNovedadIncluir genPmNovedadIncluir) {
        this.genPmNovedadIncluir = genPmNovedadIncluir;
    }

    public List<PmNovedadIncluir> getList() {
        return list;
    }

    public void setList(List<PmNovedadIncluir> list) {
        this.list = list;
    }

    public int getI_tipo() {
        return i_tipo;
    }

    public void setI_tipo(int i_tipo) {
        this.i_tipo = i_tipo;
    }

    public int getI_tipoDet() {
        return i_tipoDet;
    }

    public void setI_tipoDet(int i_tipoDet) {
        this.i_tipoDet = i_tipoDet;
    }

    public List<NovedadTipo> getListT() {
        return listT;
    }

    public void setListT(List<NovedadTipo> listT) {
        this.listT = listT;
    }

    public List<NovedadTipoDetalles> getListTDet() {
        return listTDet;
    }

    public void setListTDet(List<NovedadTipoDetalles> listTDet) {
        this.listTDet = listTDet;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
