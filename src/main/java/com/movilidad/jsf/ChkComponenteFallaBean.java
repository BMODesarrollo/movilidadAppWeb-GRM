package com.movilidad.jsf;

import com.movilidad.ejb.ChkComponenteFacadeLocal;
import com.movilidad.ejb.ChkComponenteFallaFacadeLocal;
import com.movilidad.model.ChkComponente;
import com.movilidad.model.ChkComponenteFalla;
import com.movilidad.model.DispSistema;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "chkComponenteFallaBean")
@ViewScoped
public class ChkComponenteFallaBean implements Serializable {

    @EJB
    private ChkComponenteFallaFacadeLocal componenteFallaEjb;

    @EJB
    private ChkComponenteFacadeLocal componenteEjb;

    @Inject
    private novedadTipoAndDetalleBean tipoAndDetalleBean;
    @Inject
    private SelectDispSistemaBean selectDispSistemaBean;

    private ChkComponenteFalla falla;
    private ChkComponenteFalla selected;

    private String nombre;
    private Integer idComponente;

    private boolean flagEdit;
    private boolean b_afectaDisponibilidad;

    private List<ChkComponenteFalla> lstFallas;
    private List<ChkComponente> lstComponentes;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstFallas = componenteFallaEjb.findAllByEstadoReg();
        tipoAndDetalleBean.setCompUpdateVistaCreateNov("frmChkComponenteFalla:novedad_tipo_detalle,frmChkComponenteFalla:novedad_tipo,frmChkComponenteFalla:sistema_grp");
    }

    public void nuevo() {
        falla = new ChkComponenteFalla();
        lstComponentes = componenteEjb.findAllByEstadoReg();
        tipoAndDetalleBean.setNovedadTipo(null);
        tipoAndDetalleBean.setNovedadTipoDet(null);
        selectDispSistemaBean.setId_dis_sistema(null);
        selected = null;
        flagEdit = false;
        nombre = null;
        b_afectaDisponibilidad = false;
        idComponente = null;
    }

    public void editar() {
        flagEdit = true;
        falla = selected;
        nombre = selected.getNombre();
        lstComponentes = componenteEjb.findAllByEstadoReg();
        b_afectaDisponibilidad = (selected.getAfectaDisponibilidad() == 1);
        idComponente = selected.getIdChkComponente().getIdChkComponente();

        if (b_afectaDisponibilidad) {
            selectDispSistemaBean.consultarSistema();
            selectDispSistemaBean.setId_dis_sistema(selected.getIdDispSistema().getIdDispSistema());
            tipoAndDetalleBean.setNovedadTipo(falla.getIdNovedadTipo());
            tipoAndDetalleBean.setNovedadTipoDet(falla.getIdNovedadTipoDetalle());
        } else {
            tipoAndDetalleBean.setNovedadTipo(null);
            tipoAndDetalleBean.setNovedadTipoDet(null);
            selectDispSistemaBean.setId_dis_sistema(null);
        }

    }

    public void guardar() {
        String validacion = validarDatos();

        if (validacion == null) {
            falla.setNombre(nombre);
            falla.setUsername(user.getUsername());
            falla.setIdChkComponente(new ChkComponente(idComponente));
            falla.setAfectaDisponibilidad(b_afectaDisponibilidad ? 1 : 0);

            if (b_afectaDisponibilidad) {
                falla.setIdDispSistema(new DispSistema(selectDispSistemaBean.getId_dis_sistema()));
                falla.setIdNovedadTipo(tipoAndDetalleBean.getNovedadTipo());
                falla.setIdNovedadTipoDetalle(tipoAndDetalleBean.getNovedadTipoDet());
            }

            if (flagEdit) {
                falla.setModificado(MovilidadUtil.fechaCompletaHoy());
                componenteFallaEjb.edit(falla);
                PrimeFaces.current().executeScript("PF('wlgChkComponenteFalla').hide();");
                MovilidadUtil.addSuccessMessage("Registro modificado éxitosamente");

            } else {
                falla.setCreado(MovilidadUtil.fechaCompletaHoy());
                falla.setEstadoReg(0);
                componenteFallaEjb.create(falla);
                lstFallas.add(falla);
                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }

            init();

        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }

    }

    private String validarDatos() {

        if (b_afectaDisponibilidad) {
            if (tipoAndDetalleBean.getNovedadTipo() == null) {
                return "Debe seleccionar un tipo de novedad";
            }
            if (tipoAndDetalleBean.getNovedadTipoDet() == null) {
                return "Debe seleccionar un detalle de tipo de novedad";
            }
            if (tipoAndDetalleBean.getNovedadTipoDet().getAfectaDisponibilidad() == 1) {
                if (selectDispSistemaBean.getId_dis_sistema() == null) {
                    return "Se debe seleccionar un sistema.";
                }
            }
        }

        if (flagEdit) {
            if (componenteFallaEjb.findByNombreAndComponente(selected.getIdChkComponenteFalla(), nombre, idComponente) != null) {
                return "La falla a registrar YA SE ENCUENTRA REGISTRDADA";
            }

            if (b_afectaDisponibilidad) {
                if (componenteFallaEjb.verificarAfectaDisponibilidad(selected.getIdChkComponenteFalla(), nombre, idComponente, tipoAndDetalleBean.getNovedadTipo().getIdNovedadTipo(), tipoAndDetalleBean.getNovedadTipoDet().getIdNovedadTipoDetalle(), selectDispSistemaBean.getId_dis_sistema()) != null) {
                    return "YA se encuentra un registro con los parámetros ingresados";
                }
            }

        } else {
            if (componenteFallaEjb.findByNombreAndComponente(0, nombre, idComponente) != null) {
                return "La falla a registrar YA SE ENCUENTRA REGISTRDADA";
            }

            if (b_afectaDisponibilidad) {
                if (componenteFallaEjb.verificarAfectaDisponibilidad(0, nombre, idComponente, tipoAndDetalleBean.getNovedadTipo().getIdNovedadTipo(), tipoAndDetalleBean.getNovedadTipoDet().getIdNovedadTipoDetalle(), selectDispSistemaBean.getId_dis_sistema()) != null) {
                    return "YA se encuentra un registro con los parámetros ingresados";
                }
            }

        }

        return null;

    }

    public ChkComponenteFalla getFalla() {
        return falla;
    }

    public void setFalla(ChkComponenteFalla falla) {
        this.falla = falla;
    }

    public ChkComponenteFalla getSelected() {
        return selected;
    }

    public void setSelected(ChkComponenteFalla selected) {
        this.selected = selected;
    }

    public boolean isFlagEdit() {
        return flagEdit;
    }

    public void setFlagEdit(boolean flagEdit) {
        this.flagEdit = flagEdit;
    }

    public List<ChkComponenteFalla> getLstFallas() {
        return lstFallas;
    }

    public void setLstFallas(List<ChkComponenteFalla> lstFallas) {
        this.lstFallas = lstFallas;
    }

    public boolean isB_afectaDisponibilidad() {
        return b_afectaDisponibilidad;
    }

    public void setB_afectaDisponibilidad(boolean b_afectaDisponibilidad) {
        this.b_afectaDisponibilidad = b_afectaDisponibilidad;
    }

    public Integer getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Integer idComponente) {
        this.idComponente = idComponente;
    }

    public List<ChkComponente> getLstComponentes() {
        return lstComponentes;
    }

    public void setLstComponentes(List<ChkComponente> lstComponentes) {
        this.lstComponentes = lstComponentes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
