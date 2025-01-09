/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GenericaPmNovedadExcluirFacadeLocal;
import com.movilidad.ejb.GenericaTipoDetallesFacadeLocal;
import com.movilidad.ejb.GenericaTipoFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.GenericaPmNovedadExcluir;
import com.movilidad.model.GenericaTipo;
import com.movilidad.model.GenericaTipoDetalles;
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
@Named(value = "genPmTipoDetExcluirJSFMB")
@ViewScoped
public class GenericaPmTipoDetalleExcluirJSFMB implements Serializable {

    /**
     * Creates a new instance of GenericaPmTipoDetalleExcluirJSFMB
     */
    public GenericaPmTipoDetalleExcluirJSFMB() {
    }

    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;
    @EJB
    private GenericaPmNovedadExcluirFacadeLocal GenPmNovedadExcluirFacadeLocal;

    @EJB
    private GenericaTipoDetallesFacadeLocal novedadTDetEJB;

    @EJB
    private GenericaTipoFacadeLocal genericaTipoEJB;

    private ParamAreaUsr pau;
    private GenericaPmNovedadExcluir genPmNovedadExcluir;
    private List<GenericaPmNovedadExcluir> list;

    private int i_tipoGenerica = 0;
    private int i_tipoGenericaDet = 0;
    private boolean activo = true;
    private GenericaTipoDetalles i_tipoGenericaDetObj;

    private List<GenericaTipo> listGenericaT;
    private List<GenericaTipoDetalles> listGenericaTDet;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
        if (pau != null) {

        }
        consultar();
    }

    public void consultar() {
        if (pau == null) {
            list = new ArrayList<>();
        } else {
            list = GenPmNovedadExcluirFacadeLocal.getByIdArea(pau.getIdParamArea().getIdParamArea());
        }
    }

    public void preCreate() {
        i_tipoGenerica = 0;
        i_tipoGenericaDet = 0;
        i_tipoGenericaDetObj = null;
        genPmNovedadExcluir = null;
        listGenericaT = genericaTipoEJB.findAllByAreaAfectaPm(pau.getIdParamArea().getIdParamArea(), 0);
        activo = true;
    }

    public void preEdit(GenericaPmNovedadExcluir obj) {
        listGenericaT = genericaTipoEJB.findAllByAreaAfectaPm(pau.getIdParamArea().getIdParamArea(), 0);
        genPmNovedadExcluir = obj;
        activo = obj.getActivo() == 1;
        i_tipoGenerica = obj.getIdGenericaTipoDetalle().getIdGenericaTipo().getIdGenericaTipo();
        findById();
        i_tipoGenericaDetObj = obj.getIdGenericaTipoDetalle();
        i_tipoGenericaDet = obj.getIdGenericaTipoDetalle().getIdGenericaTipoDetalle();
    }

    public void activarDesactivar(GenericaPmNovedadExcluir obj, int opc) {
        if (opc == 0) {
            obj.setActivo(0);
        } else {
            obj.setActivo(1);
        }
        obj.setModificado(MovilidadUtil.fechaCompletaHoy());
        GenPmNovedadExcluirFacadeLocal.edit(obj);
        MovilidadUtil.addSuccessMessage("Acción completada exitosamente.");
        consultar();
    }

    public void guardar() {
        if (i_tipoGenericaDetObj == null) {
            MovilidadUtil.addErrorMessage("Seleccionar el tipo de detalle de novedad.");
            return;
        }
        if (GenPmNovedadExcluirFacadeLocal.getByIdNovedadTipoDet(i_tipoGenericaDetObj.getIdGenericaTipoDetalle(), 0) != null) {
            MovilidadUtil.addErrorMessage("Ya existe registro con el tipo Detalle indicado.");
            return;
        }
        guardarTransactional();
        consultar();
    }

    public void editar() {
        if (i_tipoGenericaDet == 0) {
            MovilidadUtil.addErrorMessage("Seleccionar el tipo de detalle de novedad.");
            return;
        }
        if (GenPmNovedadExcluirFacadeLocal.getByIdNovedadTipoDet(i_tipoGenericaDetObj.getIdGenericaTipoDetalle(), genPmNovedadExcluir.getIdgenericaPmNovedadExcluir()) != null) {
            MovilidadUtil.addErrorMessage("Ya existe registro con el tipo Detalle indicado.");
            return;
        }
        editarTransactional();
        consultar();
    }

    @Transactional
    public void guardarTransactional() {

        genPmNovedadExcluir = new GenericaPmNovedadExcluir();

        genPmNovedadExcluir.setActivo(activo ? 1 : 0);
        genPmNovedadExcluir.setIdGenericaTipoDetalle(i_tipoGenericaDetObj);
        genPmNovedadExcluir.setIdParamArea(pau.getIdParamArea());
        genPmNovedadExcluir.setUsername(user.getUsername());
        genPmNovedadExcluir.setEstadoReg(0);
        genPmNovedadExcluir.setCreado(MovilidadUtil.fechaCompletaHoy());

        GenPmNovedadExcluirFacadeLocal.create(genPmNovedadExcluir);
        i_tipoGenerica = 0;
        i_tipoGenericaDet = 0;
        i_tipoGenericaDetObj = null;
        genPmNovedadExcluir = null;
        MovilidadUtil.addSuccessMessage("Se guardó el registro exitosamente.");
    }

    @Transactional
    public void editarTransactional() {

        genPmNovedadExcluir.setActivo(activo ? 1 : 0);
        genPmNovedadExcluir.setIdGenericaTipoDetalle(i_tipoGenericaDetObj);
        genPmNovedadExcluir.setIdParamArea(pau.getIdParamArea());
        genPmNovedadExcluir.setUsername(user.getUsername());
        genPmNovedadExcluir.setEstadoReg(0);
        genPmNovedadExcluir.setCreado(MovilidadUtil.fechaCompletaHoy());

        GenPmNovedadExcluirFacadeLocal.edit(genPmNovedadExcluir);
        MovilidadUtil.addSuccessMessage("Se actualizó el registro exitosamente.");
        MovilidadUtil.hideModal("wv_create_dlg");

    }

    /**
     * Método responsable de cargar en la lista listNovedadTDet, los detalles
     * del tipo de novedad seleccionada.
     *
     * Es invocado en las vistas adicionarServicios y gestionPrgTc.
     */
    public void findById() {
        for (GenericaTipo nt : listGenericaT) {
            if (nt.getIdGenericaTipo() == getI_tipoGenerica()) {
                if (listGenericaTDet == null) {
                    listGenericaTDet = new ArrayList<>();
                } else {
                    listGenericaTDet.clear();
                }
                for (GenericaTipoDetalles g : nt.getGenericaTipoDetallesList()) {
                    if (g.getAfectaPm() == 1) {
                        listGenericaTDet.add(g);
                    }
                }
                break;
            }
        }
    }

    public void setTipoNovedadDet() {
        if (i_tipoGenericaDet != 0) {
            i_tipoGenericaDetObj = novedadTDetEJB.find(i_tipoGenericaDet);
        }
    }

    public GenericaPmNovedadExcluir getGenPmNovedadExcluir() {
        return genPmNovedadExcluir;
    }

    public void setGenPmNovedadExcluir(GenericaPmNovedadExcluir genPmNovedadExcluir) {
        this.genPmNovedadExcluir = genPmNovedadExcluir;
    }

    public List<GenericaPmNovedadExcluir> getList() {
        return list;
    }

    public void setList(List<GenericaPmNovedadExcluir> list) {
        this.list = list;
    }

    public int getI_tipoGenerica() {
        return i_tipoGenerica;
    }

    public void setI_tipoGenerica(int i_tipoGenerica) {
        this.i_tipoGenerica = i_tipoGenerica;
    }

    public int getI_tipoGenericaDet() {
        return i_tipoGenericaDet;
    }

    public void setI_tipoGenericaDet(int i_tipoGenericaDet) {
        this.i_tipoGenericaDet = i_tipoGenericaDet;
    }

    public List<GenericaTipo> getListGenericaT() {
        return listGenericaT;
    }

    public void setListGenericaT(List<GenericaTipo> listGenericaT) {
        this.listGenericaT = listGenericaT;
    }

    public List<GenericaTipoDetalles> getListGenericaTDet() {
        return listGenericaTDet;
    }

    public void setListGenericaTDet(List<GenericaTipoDetalles> listGenericaTDet) {
        this.listGenericaTDet = listGenericaTDet;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
