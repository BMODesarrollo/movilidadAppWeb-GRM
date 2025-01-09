/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.NovedadTipoFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.ejb.PmNovedadExcluirFacadeLocal;
import com.movilidad.model.NovedadTipo;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.model.PmNovedadExcluir;
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
@Named(value = "pmTipoDetExcluirJSFMB")
@ViewScoped
public class PmTipoDetalleExcluirJSFMB implements Serializable {

    /**
     * Creates a new instance of NovedadPmTipoDetalleExcluirJSFMB
     */
    public PmTipoDetalleExcluirJSFMB() {
    }

    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;
    @EJB
    private PmNovedadExcluirFacadeLocal pmNovedadExcluirFacadeLocal;

    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTDetEJB;

    @EJB
    private NovedadTipoFacadeLocal noveadadTipoEJB;

    private PmNovedadExcluir genPmNovedadExcluir;
    private List<PmNovedadExcluir> list;

    private int i_tipoNovedad = 0;
    private int i_tipoNovedadDet = 0;
    private boolean activo = true;
    private NovedadTipoDetalles i_tipoNovedadDetObj;

    private List<NovedadTipo> listNovedadT;
    private List<NovedadTipoDetalles> listNovedadTDet;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    public void consultar() {
        list = pmNovedadExcluirFacadeLocal.getAllActivo();
    }

    public void preCreate() {
        i_tipoNovedad = 0;
        i_tipoNovedadDet = 0;
        i_tipoNovedadDetObj = null;
        genPmNovedadExcluir = null;
        listNovedadT = noveadadTipoEJB.findAllAfectaPm(0);
        activo = true;
    }

    public void preEdit(PmNovedadExcluir obj) {
        listNovedadT = noveadadTipoEJB.findAllAfectaPm(0);
        genPmNovedadExcluir = obj;
        activo = obj.getActivo() == 1;
        i_tipoNovedad = obj.getIdNovedadTipoDetalle().getIdNovedadTipo().getIdNovedadTipo();
        findById();
        i_tipoNovedadDetObj = obj.getIdNovedadTipoDetalle();
        i_tipoNovedadDet = obj.getIdNovedadTipoDetalle().getIdNovedadTipoDetalle();
    }

    public void activarDesactivar(PmNovedadExcluir obj, int opc) {
        if (opc == 0) {
            obj.setActivo(0);
        } else {
            obj.setActivo(1);
        }
        obj.setModificado(MovilidadUtil.fechaCompletaHoy());
        pmNovedadExcluirFacadeLocal.edit(obj);
        MovilidadUtil.addSuccessMessage("Acción completada exitosamente.");
        consultar();
    }

    public void guardar() {
        if (i_tipoNovedadDetObj == null) {
            MovilidadUtil.addErrorMessage("Seleccionar el tipo de detalle de novedad.");
            return;
        }
        if (pmNovedadExcluirFacadeLocal.getByIdNovedadTipoDet(i_tipoNovedadDetObj.getIdNovedadTipoDetalle(), 0) != null) {
            MovilidadUtil.addErrorMessage("Ya existe registro con el tipo Detalle indicado.");
            return;
        }
        guardarTransactional();
        consultar();
    }

    public void editar() {
        if (i_tipoNovedadDet == 0) {
            MovilidadUtil.addErrorMessage("Seleccionar el tipo de detalle de novedad.");
            return;
        }
        if (pmNovedadExcluirFacadeLocal.getByIdNovedadTipoDet(i_tipoNovedadDetObj.getIdNovedadTipoDetalle(), genPmNovedadExcluir.getIdPmNovedadExcluir()) != null) {
            MovilidadUtil.addErrorMessage("Ya existe registro con el tipo Detalle indicado.");
            return;
        }
        editarTransactional();
        consultar();
    }

    @Transactional
    public void guardarTransactional() {

        genPmNovedadExcluir = new PmNovedadExcluir();

        genPmNovedadExcluir.setActivo(activo ? 1 : 0);
        genPmNovedadExcluir.setIdNovedadTipoDetalle(i_tipoNovedadDetObj);
        genPmNovedadExcluir.setUsername(user.getUsername());
        genPmNovedadExcluir.setEstadoReg(0);
        genPmNovedadExcluir.setCreado(MovilidadUtil.fechaCompletaHoy());

        pmNovedadExcluirFacadeLocal.create(genPmNovedadExcluir);
        i_tipoNovedad = 0;
        i_tipoNovedadDet = 0;
        i_tipoNovedadDetObj = null;
        genPmNovedadExcluir = null;
        MovilidadUtil.addSuccessMessage("Se guardó el registro exitosamente.");
    }

    @Transactional
    public void editarTransactional() {

        genPmNovedadExcluir.setActivo(activo ? 1 : 0);
        genPmNovedadExcluir.setIdNovedadTipoDetalle(i_tipoNovedadDetObj);
        genPmNovedadExcluir.setUsername(user.getUsername());
        genPmNovedadExcluir.setEstadoReg(0);
        genPmNovedadExcluir.setCreado(MovilidadUtil.fechaCompletaHoy());

        pmNovedadExcluirFacadeLocal.edit(genPmNovedadExcluir);
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
        for (NovedadTipo nt : listNovedadT) {
            if (nt.getIdNovedadTipo() == getI_tipoNovedad()) {
                if (listNovedadTDet == null) {
                    listNovedadTDet = new ArrayList<>();
                } else {
                    listNovedadTDet.clear();
                }
                for (NovedadTipoDetalles g : nt.getNovedadTipoDetallesList()) {
                    if (g.getAfectaPm() == 1) {
                        listNovedadTDet.add(g);
                    }
                }
                break;
            }
        }
    }

    public void setTipoNovedadDet() {
        if (i_tipoNovedadDet != 0) {
            i_tipoNovedadDetObj = novedadTDetEJB.find(i_tipoNovedadDet);
        }
    }

    public PmNovedadExcluir getGenPmNovedadExcluir() {
        return genPmNovedadExcluir;
    }

    public void setGenPmNovedadExcluir(PmNovedadExcluir genPmNovedadExcluir) {
        this.genPmNovedadExcluir = genPmNovedadExcluir;
    }

    public List<PmNovedadExcluir> getList() {
        return list;
    }

    public void setList(List<PmNovedadExcluir> list) {
        this.list = list;
    }

    public int getI_tipoNovedad() {
        return i_tipoNovedad;
    }

    public void setI_tipoNovedad(int i_tipoNovedad) {
        this.i_tipoNovedad = i_tipoNovedad;
    }

    public int getI_tipoNovedadDet() {
        return i_tipoNovedadDet;
    }

    public void setI_tipoNovedadDet(int i_tipoNovedadDet) {
        this.i_tipoNovedadDet = i_tipoNovedadDet;
    }

    public List<NovedadTipo> getListNovedadT() {
        return listNovedadT;
    }

    public void setListNovedadT(List<NovedadTipo> listNovedadT) {
        this.listNovedadT = listNovedadT;
    }

    public List<NovedadTipoDetalles> getListNovedadTDet() {
        return listNovedadTDet;
    }

    public void setListNovedadTDet(List<NovedadTipoDetalles> listNovedadTDet) {
        this.listNovedadTDet = listNovedadTDet;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
