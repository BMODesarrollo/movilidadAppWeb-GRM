package com.movilidad.jsf;

import com.movilidad.ejb.SstEmpresaVisitanteFacadeLocal;
import com.movilidad.ejb.SstEsMatEquiDetFacadeLocal;
import com.movilidad.ejb.SstEsMatEquiFacadeLocal;
import com.movilidad.ejb.SstMatEquiFacadeLocal;
import com.movilidad.model.SstEmpresaVisitante;
import com.movilidad.model.SstEsMatEqui;
import com.movilidad.model.SstEsMatEquiDet;
import com.movilidad.model.SstMatEqui;
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
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "sstEsMatEquiBean")
@ViewScoped
public class SstEsMatEquiBean implements Serializable {

    @EJB
    private SstEsMatEquiFacadeLocal esMatEquiEjb;
    @EJB
    private SstEmpresaVisitanteFacadeLocal empresaVisitanteEjb;
    @EJB
    private SstEsMatEquiDetFacadeLocal esMatEquiDetEjb;
    @EJB
    private SstMatEquiFacadeLocal matEquiEjb;

    private boolean isEditing;
    private boolean isEditingDetalle;

    private SstEsMatEqui sstEsMatEqui;
    private SstEsMatEquiDet sstEsMatEquiDet;
    private SstEsMatEqui selected;
    private SstMatEqui sstMatEqui;
    private Integer i_sstEmpresaVisitante;
    private Integer i_sstMatEqui;
    private Integer i_tipoAcceso;
    private String material;

    private List<SstEsMatEqui> lstEsMatEqui;
    private List<SstMatEqui> lstMatEqui;
    private List<SstEsMatEquiDet> lstEsMatEquiDet;
    private List<SstEmpresaVisitante> lstEmpresaVisitante;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstEsMatEqui = esMatEquiEjb.findAllEstadoReg();
    }

    public void nuevo() {
        i_sstEmpresaVisitante = null;
        i_sstMatEqui = null;
        i_tipoAcceso = null;
        sstEsMatEqui = new SstEsMatEqui();
        isEditing = false;
        selected = null;
        lstEsMatEquiDet = new ArrayList<>();
        lstMatEqui = matEquiEjb.findAllEstadoReg();
        lstEmpresaVisitante = empresaVisitanteEjb.findAllAprobados();
    }

    public void editar() {
        isEditing = true;
        i_tipoAcceso = selected.getTipoAcceso();
        i_sstEmpresaVisitante = selected.getIdSstEmpresaVisitante().getIdSstEmpresaVisitante();
        i_sstMatEqui = null;
        sstEsMatEqui = selected;
        lstEsMatEquiDet = sstEsMatEqui.getSstEsMatEquiDetList();
        lstMatEqui = matEquiEjb.findAllEstadoReg();
        lstEmpresaVisitante = empresaVisitanteEjb.findAllAprobados();
    }

    public void guardar() {
        String validacion = validarDatos(isEditing);
        if (isEditing) {
            if (validacion == null) {
                sstEsMatEqui.setTipoAcceso(i_tipoAcceso);
                sstEsMatEqui.setModificado(new Date());
                sstEsMatEqui.setIdSstEmpresaVisitante(empresaVisitanteEjb.find(i_sstEmpresaVisitante));
                esMatEquiEjb.edit(sstEsMatEqui);
                PrimeFaces.current().executeScript("PF('wlvEsMatEqui').hide();");
                MovilidadUtil.addSuccessMessage("Registro modificado éxitosamente");
            } else {
                MovilidadUtil.addErrorMessage(validacion);
            }
        } else {
            if (validacion == null) {
                sstEsMatEqui.setTipoAcceso(i_tipoAcceso);
                sstEsMatEqui.setUserApruebaEntrada("N/A");
                sstEsMatEqui.setUserApruebaSalida("N/A");
                sstEsMatEqui.setSalidaAprobada(0);
                sstEsMatEqui.setEntradaAprobada(0);
                sstEsMatEqui.setCreado(new Date());
                sstEsMatEqui.setUsername(user.getUsername());
                sstEsMatEqui.setEstadoReg(0);
                sstEsMatEqui.setIdSstEmpresaVisitante(empresaVisitanteEjb.find(i_sstEmpresaVisitante));
                esMatEquiEjb.create(sstEsMatEqui);

                for (SstEsMatEquiDet detalle : lstEsMatEquiDet) {
                    detalle.setIdSstEsMatEqui(sstEsMatEqui);
                    detalle.setCreado(new Date());
                    detalle.setEstadoReg(0);
                    detalle.setUsername(user.getUsername());
                    esMatEquiDetEjb.create(detalle);
                }
                lstEsMatEqui.add(sstEsMatEqui);
                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            } else {
                MovilidadUtil.addErrorMessage(validacion);
            }
        }
    }

    public void nuevoDetalle() {
        isEditingDetalle = false;
        sstEsMatEquiDet = new SstEsMatEquiDet();
        i_sstMatEqui = null;
    }

    public void editarDetalle(SstEsMatEquiDet det) {
        isEditingDetalle = true;
        material = det.getIdSstMatEqui().getNombre();
        sstEsMatEquiDet = det;
        i_sstMatEqui = sstEsMatEquiDet.getIdSstMatEqui().getIdSstMatEqui();
    }

    public void guardarDetalleEnLista() {
        if (isEditingDetalle) {
            if (!sstEsMatEquiDet.getIdSstMatEqui().getNombre().equals(material)) {
                for (SstEsMatEquiDet mat : lstEsMatEquiDet) {
                    if (mat.getIdSstMatEqui().getIdSstMatEqui().equals(i_sstMatEqui)) {
                        MovilidadUtil.addErrorMessage("El material a modificar YA se encuentra en registrado");
                        return;
                    }
                }
            }

            sstEsMatEquiDet.setModificado(new Date());
            sstEsMatEquiDet.setIdSstMatEqui(matEquiEjb.find(i_sstMatEqui));
            esMatEquiDetEjb.edit(sstEsMatEquiDet);
            PrimeFaces.current().executeScript("PF('wlvEsMatEquiDet').hide();");
            MovilidadUtil.addSuccessMessage("Datos actualizados éxitosamente");
        } else {
            for (SstEsMatEquiDet mat : lstEsMatEquiDet) {
                if (mat.getIdSstMatEqui().getIdSstMatEqui().equals(i_sstMatEqui)) {
                    MovilidadUtil.addErrorMessage("El material a registrar YA se encuentra en registrado");
                    return;
                }
            }
            sstEsMatEquiDet.setIdSstMatEqui(matEquiEjb.find(i_sstMatEqui));
            lstEsMatEquiDet.add(sstEsMatEquiDet);
            nuevoDetalle();
            PrimeFaces.current().executeScript("PF('wlvEsMatEquiDet').hide();");
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
        }
    }

    public void eliminarDetalle(SstEsMatEquiDet detalle) {
        lstEsMatEquiDet.remove(detalle);
        MovilidadUtil.addSuccessMessage("Detalle eliminado éxitosamente");
    }

    private String validarDatos(boolean flagEdit) {
        if (flagEdit) {
            if (lstEsMatEquiDet == null) {
                return "DEBE agregar al menos 1 material/herramienta";
            }
        } else {
            if (lstEsMatEquiDet == null) {
                return "DEBE agregar al menos 1 material/herramienta";
            }
        }

        return null;
    }

    public void aprobarEntrada() {
        selected.setFechaAprobacionEntrada(new Date());
        selected.setEntradaAprobada(1);
        selected.setEstado(0);
        selected.setUserApruebaEntrada(user.getUsername());
        esMatEquiEjb.edit(selected);
        MovilidadUtil.addSuccessMessage("Entrada aprobada éxitosamente");
    }

    public void aprobarSalida() {
        selected.setFechaAprobacionSalida(new Date());
        selected.setSalidaAprobada(1);
        selected.setEstado(1);
        selected.setUserApruebaSalida(user.getUsername());
        esMatEquiEjb.edit(selected);
        MovilidadUtil.addSuccessMessage("Salida aprobada éxitosamente");
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public SstEsMatEqui getSstEsMatEqui() {
        return sstEsMatEqui;
    }

    public void setSstEsMatEqui(SstEsMatEqui sstEsMatEqui) {
        this.sstEsMatEqui = sstEsMatEqui;
    }

    public SstEsMatEqui getSelected() {
        return selected;
    }

    public void setSelected(SstEsMatEqui selected) {
        this.selected = selected;
    }

    public List<SstEsMatEqui> getLstEsMatEqui() {
        return lstEsMatEqui;
    }

    public void setLstEsMatEqui(List<SstEsMatEqui> lstEsMatEqui) {
        this.lstEsMatEqui = lstEsMatEqui;
    }

    public List<SstEsMatEquiDet> getLstEsMatEquiDet() {
        return lstEsMatEquiDet;
    }

    public void setLstEsMatEquiDet(List<SstEsMatEquiDet> lstEsMatEquiDet) {
        this.lstEsMatEquiDet = lstEsMatEquiDet;
    }

    public List<SstMatEqui> getLstMatEqui() {
        return lstMatEqui;
    }

    public void setLstMatEqui(List<SstMatEqui> lstMatEqui) {
        this.lstMatEqui = lstMatEqui;
    }

    public Integer getI_sstEmpresaVisitante() {
        return i_sstEmpresaVisitante;
    }

    public void setI_sstEmpresaVisitante(Integer i_sstEmpresaVisitante) {
        this.i_sstEmpresaVisitante = i_sstEmpresaVisitante;
    }

    public Integer getI_sstMatEqui() {
        return i_sstMatEqui;
    }

    public void setI_sstMatEqui(Integer i_sstMatEqui) {
        this.i_sstMatEqui = i_sstMatEqui;
    }

    public SstMatEqui getSstMatEqui() {
        return sstMatEqui;
    }

    public void setSstMatEqui(SstMatEqui sstMatEqui) {
        this.sstMatEqui = sstMatEqui;
    }

    public SstEsMatEquiDet getSstEsMatEquiDet() {
        return sstEsMatEquiDet;
    }

    public void setSstEsMatEquiDet(SstEsMatEquiDet sstEsMatEquiDet) {
        this.sstEsMatEquiDet = sstEsMatEquiDet;
    }

    public List<SstEmpresaVisitante> getLstEmpresaVisitante() {
        return lstEmpresaVisitante;
    }

    public void setLstEmpresaVisitante(List<SstEmpresaVisitante> lstEmpresaVisitante) {
        this.lstEmpresaVisitante = lstEmpresaVisitante;
    }

    public boolean isIsEditingDetalle() {
        return isEditingDetalle;
    }

    public void setIsEditingDetalle(boolean isEditingDetalle) {
        this.isEditingDetalle = isEditingDetalle;
    }

    public Integer getI_tipoAcceso() {
        return i_tipoAcceso;
    }

    public void setI_tipoAcceso(Integer i_tipoAcceso) {
        this.i_tipoAcceso = i_tipoAcceso;
    }
}
