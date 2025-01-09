/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AuditoriaAreaComunFacadeLocal;
import com.movilidad.ejb.AuditoriaEncabezadoFacadeLocal;
import com.movilidad.ejb.AuditoriaEstacionFacadeLocal;
import com.movilidad.ejb.AuditoriaLugarFacadeLocal;
import com.movilidad.ejb.AuditoriaTipoFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.AuditoriaAreaComun;
import com.movilidad.model.AuditoriaEncabezado;
import com.movilidad.model.AuditoriaEstacion;
import com.movilidad.model.AuditoriaLugar;
import com.movilidad.model.AuditoriaTipo;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Listar y editar los encabezados para las auditorías.
 *
 * @author solucionesit
 */
@Named(value = "audiEncabezadoJSFMB")
@ViewScoped
public class AuditoriaEncabezadoJSFMB implements Serializable {

    /**
     * Creates a new instance of AuditoriaEncabezadoJSFMB
     */
    public AuditoriaEncabezadoJSFMB() {
    }

    private List<AuditoriaEncabezado> list;
    private List<AuditoriaTipo> listTipoAuditoria;
    private List<AuditoriaLugar> listLugarAuditoria;
    private List<Vehiculo> listVehiculo;
    private List<AuditoriaEstacion> listaAuditoriaEstacion;
    private List<AuditoriaAreaComun> listAuditoriaAreaComun;
    private AuditoriaEncabezado auditoriaEncabezado;
    private Map<Integer, AuditoriaLugar> mapaAuditoriaLugar;
    private int i_tipo_auditoria;
    private int i_lugar_auditoria;
    private int id_area;
    private boolean b_activa;
    private boolean b_editar;
    private ParamAreaUsr paramAreaUsr;
    private UserExtended user;

    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUsrEJB;
    @EJB
    private AuditoriaEncabezadoFacadeLocal audiEncabezadoEJB;
    @EJB
    private AuditoriaTipoFacadeLocal audiTipoEJB;
    @EJB
    private AuditoriaLugarFacadeLocal audiLugarEJB;
    @EJB
    private VehiculoFacadeLocal vehiculoEJB;
    @EJB
    private AuditoriaEstacionFacadeLocal audiEstacionEJB;
    @EJB
    private AuditoriaAreaComunFacadeLocal audiAreaComunEJB;

    @PostConstruct
    public void init() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        paramAreaUsr = paramAreaUsrEJB.getByIdUser(user.getUsername());
        id_area = paramAreaUsr.getIdParamArea().getIdParamArea();
        consultar();
    }

    public void consultar() {
        list = audiEncabezadoEJB.findByArea(id_area);
    }

//    public void cargarComponente() {
//        if (i_lugar_auditoria != 0) {
//            AuditoriaLugar audiLugar = mapaAuditoriaLugar.get(i_lugar_auditoria);
//            auditoriaEncabezado.setIdAuditoriaLugar(audiLugar);
//            if (audiLugar.getBus() == 1) {
//                listVehiculo = vehiculoEJB.getVehiclosActivo();
//            }
//            if (audiLugar.getEstacion() == 1) {
//                listaAuditoriaEstacion = audiEstacionEJB.findByArea(id_area);
//            }
//            if (audiLugar.getAreaComun() == 1) {
//                listAuditoriaAreaComun = audiAreaComunEJB.findByArea(id_area);
//            }
//        } else {
//            auditoriaEncabezado.setIdAuditoriaLugar(null);
//        }
//    }
    public void guardar() {
        if (validarCampos()) {
            return;
        }
        guardarTransction();
        consultar();
        MovilidadUtil.addSuccessMessage("Acción completada con exito.");
        auditoriaEncabezado = new AuditoriaEncabezado();
    }

    public void edit() {
        if (validarCampos()) {
            return;
        }
        editTransction();
        consultar();
        auditoriaEncabezado = null;
        MovilidadUtil.addSuccessMessage("Acción completada con exito.");
        MovilidadUtil.hideModal("crear_audi_encabezado_dialog_wv");
    }

    @Transactional
    private void guardarTransction() {
        auditoriaEncabezado.setIdParamArea(paramAreaUsr.getIdParamArea());
        auditoriaEncabezado.setUsername(user.getUsername());
        auditoriaEncabezado.setCreado(MovilidadUtil.fechaCompletaHoy());
        auditoriaEncabezado.setActiva(b_activa ? 1 : 0);
        auditoriaEncabezado.setEstadoReg(0);
        auditoriaEncabezado.setIdAuditoriaTipo(new AuditoriaTipo(i_tipo_auditoria));
        auditoriaEncabezado.setIdAuditoriaLugar(new AuditoriaLugar(i_lugar_auditoria));
        audiEncabezadoEJB.create(auditoriaEncabezado);
    }

    @Transactional
    private void editTransction() {
        auditoriaEncabezado.setUsername(user.getUsername());
        auditoriaEncabezado.setActiva(b_activa ? 1 : 0);
        auditoriaEncabezado.setModificado(MovilidadUtil.fechaCompletaHoy());
        auditoriaEncabezado.setIdAuditoriaTipo(new AuditoriaTipo(i_tipo_auditoria));
        auditoriaEncabezado.setIdAuditoriaLugar(new AuditoriaLugar(i_lugar_auditoria));
        audiEncabezadoEJB.edit(auditoriaEncabezado);

    }

    public void preGuardar() {
        auditoriaEncabezado = new AuditoriaEncabezado();
        auditoriaEncabezado.setFechaDesde(MovilidadUtil.fechaCompletaHoy());
        auditoriaEncabezado.setFechaHasta(MovilidadUtil.fechaCompletaHoy());

        i_lugar_auditoria = 0;
        i_tipo_auditoria = 0;
        b_activa = false;
        listTipoAuditoria = audiTipoEJB.findByArea(id_area);
        listLugarAuditoria = audiLugarEJB.findByArea(id_area);
        mapaAuditoriaLugar = new HashMap<>();
        for (AuditoriaLugar al : listLugarAuditoria) {
            mapaAuditoriaLugar.put(al.getIdAuditoriaLugar(), al);
        }
    }

    public void preEdit(AuditoriaEncabezado obj) {
        b_editar = false;
        if (audiEncabezadoEJB.findIdAuditoriaEncabezado(obj.getIdAuditoriaEncabezado()) != null) {
            b_editar = true;
        }
        auditoriaEncabezado = obj;
        i_lugar_auditoria = auditoriaEncabezado.getIdAuditoriaLugar().getIdAuditoriaLugar();
        i_tipo_auditoria = auditoriaEncabezado.getIdAuditoriaTipo().getIdAuditoriaTipo();
        b_activa = auditoriaEncabezado.getActiva() != null && auditoriaEncabezado.getActiva() == 1;
        listTipoAuditoria = audiTipoEJB.findByArea(id_area);
        listLugarAuditoria = audiLugarEJB.findByArea(id_area);
        MovilidadUtil.openModal("crear_audi_encabezado_dialog_wv");
    }

    boolean validarCampos() {
        if (auditoriaEncabezado.getTitulo() == null || auditoriaEncabezado.getTitulo().isEmpty()) {
            MovilidadUtil.addErrorMessage("No se ha digitado un nombre.");
            return true;
        }
        if (auditoriaEncabezado.getDescripcion() == null || auditoriaEncabezado.getDescripcion().isEmpty()) {
            MovilidadUtil.addErrorMessage("No se ha digitado un descripción.");
            return true;
        }
        if (auditoriaEncabezado.getIdAuditoriaLugar() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un lugar de auditoría");
            return true;
        }

        if (i_tipo_auditoria == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo de auditoría");
            return true;
        }
        if (auditoriaEncabezado.getFechaDesde() == null) {
            MovilidadUtil.addErrorMessage("Seleccione la fecha desde.");
            return true;
        }
        if (auditoriaEncabezado.getFechaHasta() == null) {
            MovilidadUtil.addErrorMessage("Seleccione la fecha hasta.");
            return true;
        }
        if (auditoriaEncabezado.getFechaDesde().after(auditoriaEncabezado.getFechaHasta())) {
            MovilidadUtil.addErrorMessage("La fecha desde no puede ser mayor a la fecha hasta.");
            return true;
        }

        return false;
    }

    public List<AuditoriaEncabezado> getList() {
        return list;
    }

    public void setList(List<AuditoriaEncabezado> list) {
        this.list = list;
    }

    public AuditoriaEncabezado getAuditoriaEncabezado() {
        return auditoriaEncabezado;
    }

    public void setAuditoriaEncabezado(AuditoriaEncabezado auditoriaEncabezado) {
        this.auditoriaEncabezado = auditoriaEncabezado;
    }

    public int getI_tipo_auditoria() {
        return i_tipo_auditoria;
    }

    public void setI_tipo_auditoria(int i_tipo_auditoria) {
        this.i_tipo_auditoria = i_tipo_auditoria;
    }

    public List<AuditoriaTipo> getListTipoAuditoria() {
        return listTipoAuditoria;
    }

    public void setListTipoAuditoria(List<AuditoriaTipo> listTipoAuditoria) {
        this.listTipoAuditoria = listTipoAuditoria;
    }

    public List<AuditoriaLugar> getListLugarAuditoria() {
        return listLugarAuditoria;
    }

    public void setListLugarAuditoria(List<AuditoriaLugar> listLugarAuditoria) {
        this.listLugarAuditoria = listLugarAuditoria;
    }

    public boolean isB_activa() {
        return b_activa;
    }

    public void setB_activa(boolean b_activa) {
        this.b_activa = b_activa;
    }

    public int getI_lugar_auditoria() {
        return i_lugar_auditoria;
    }

    public void setI_lugar_auditoria(int i_lugar_auditoria) {
        this.i_lugar_auditoria = i_lugar_auditoria;
    }

    public List<Vehiculo> getListVehiculo() {
        return listVehiculo;
    }

    public void setListVehiculo(List<Vehiculo> listVehiculo) {
        this.listVehiculo = listVehiculo;
    }

    public List<AuditoriaEstacion> getListaAuditoriaEstacion() {
        return listaAuditoriaEstacion;
    }

    public void setListaAuditoriaEstacion(List<AuditoriaEstacion> listaAuditoriaEstacion) {
        this.listaAuditoriaEstacion = listaAuditoriaEstacion;
    }

    public List<AuditoriaAreaComun> getListAuditoriaAreaComun() {
        return listAuditoriaAreaComun;
    }

    public void setListAuditoriaAreaComun(List<AuditoriaAreaComun> listAuditoriaAreaComun) {
        this.listAuditoriaAreaComun = listAuditoriaAreaComun;
    }

    public boolean isB_editar() {
        return b_editar;
    }

    public void setB_editar(boolean b_editar) {
        this.b_editar = b_editar;
    }

}
