/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AuditoriaCostoFacadeLocal;
import com.movilidad.ejb.AuditoriaCostoTipoFacadeLocal;
import com.movilidad.ejb.AuditoriaFacadeLocal;
import com.movilidad.ejb.VehiculoTipoFacadeLocal;
import com.movilidad.model.Auditoria;
import com.movilidad.model.AuditoriaCosto;
import com.movilidad.model.AuditoriaCostoTipo;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.model.VehiculoTipo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author solucionesit
 */
@Named(value = "audiCostoBean")
@ViewScoped
public class AuditoriaCostoBean implements Serializable {

    /**
     * Creates a new instance of AuditoriaCostoBean
     */
    public AuditoriaCostoBean() {
    }

    @EJB
    private AuditoriaFacadeLocal auditoriaEjb;
    @EJB
    private VehiculoTipoFacadeLocal VehiculoTipoEJB;
    @EJB
    private AuditoriaCostoFacadeLocal auditoriaCostoEjb;
    @EJB
    private AuditoriaCostoTipoFacadeLocal auditoriaCostoTipoEjb;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private AuditoriaCosto auditoriaCosto;
    private AuditoriaCosto selected;
    private int idVehiculoTipo;
    private int idAuditoriaCostoTipo;
    private int idAuditoria;
    private boolean isEditing;
    private int valor;
    private int i_idArea;
    private Date desde;
    private Date hasta;
    private boolean reqTipoVehiculo;
    private boolean b_vehiculoTipo;
    private boolean b_addOrUpdate;
    private AuditoriaCostoTipo auditoriaCostoTipo;
    private VehiculoTipo vehiculoTipo;
    private Auditoria auditoria;

    private List<AuditoriaCosto> lstAutidoriaCosto;
    private List<Auditoria> lstAutidoria;
    private List<VehiculoTipo> lstVehiculoTipo;
    private List<AuditoriaCostoTipo> lstCostoTipo;

    private ParamAreaUsr paramAreaUsr;
    private UserExtended user;

    public void addToListOrUpdate() throws ParseException {
        //True para agregar.
        //False para acualizar.
        if (MovilidadUtil.dateSinHora(desde).after(MovilidadUtil.dateSinHora(hasta))) {
            MovilidadUtil.addErrorMessage("La fecha desde debe ser menor a la fecha hasta");
            return;
        }
        if (b_addOrUpdate) {
            loadDataToObject();
        }
        if (existsSimilar(auditoriaCosto)) {
            return;
        }
        if (b_addOrUpdate) {
            lstAutidoriaCosto.add(auditoriaCosto);
        } else {
            b_addOrUpdate = true;
            loadDataToObject();
        }
    }

    private boolean existsSimilar(AuditoriaCosto param) throws ParseException {
        for (AuditoriaCosto obj : lstAutidoriaCosto) {
            if (!(obj.getIdAuditoriaCostoTipo().getIdAuditoriaCostoTipo()
                    .equals(b_addOrUpdate ? param.getIdAuditoriaCostoTipo().getIdAuditoriaCostoTipo()
                            : auditoriaCostoTipo.getIdAuditoriaCostoTipo()))) {
                continue;
            }
            if (param.getIdVehiculoTipo() != null) {
                if (obj.getIdVehiculoTipo() == null) {
                    continue;
                }
                if (obj.getIdVehiculoTipo().getIdVehiculoTipo()
                        .equals(b_addOrUpdate ? param.getIdVehiculoTipo().getIdVehiculoTipo() : vehiculoTipo.getIdVehiculoTipo())) {
                    if (MovilidadUtil.betweenSinHora(b_addOrUpdate ? param.getDesde() : desde, obj.getDesde(), obj.getHasta())) {
                        if (b_addOrUpdate) {
                            MovilidadUtil.addErrorMessage("Ya existe un registro "
                                    + "con el tipo de costo y tipo de vehiculo "
                                    + "seleccionado, para el rango de fechas (Fecha Desde)");
                            return true;
                        } else if (!param.getIdAuditoriaCosto().equals(obj.getIdAuditoriaCosto())) {
                            MovilidadUtil.addErrorMessage("Ya existe un registro "
                                    + "con el tipo de costo y tipo de vehiculo "
                                    + "seleccionado, para el rango de fechas (Fecha Desde)");
                            return true;
                        }
                    }
                    if (MovilidadUtil.betweenSinHora(b_addOrUpdate ? param.getHasta() : desde, obj.getDesde(), obj.getHasta())) {
                        if (b_addOrUpdate) {
                            MovilidadUtil.addErrorMessage("Ya existe un registro "
                                    + "con el tipo de costo y tipo de vehiculo "
                                    + "seleccionado, para el rango de fechas (Fecha Hasta)");
                            return true;
                        } else if (!param.getIdAuditoriaCosto().equals(obj.getIdAuditoriaCosto())) {
                            MovilidadUtil.addErrorMessage("Ya existe un registro "
                                    + "con el tipo de costo y tipo de vehiculo "
                                    + "seleccionado, para el rango de fechas (Fecha Hasta)");
                            return true;
                        }
                    }
                }
            } else {
                if (MovilidadUtil.betweenSinHora(b_addOrUpdate ? param.getDesde() : desde, obj.getDesde(), obj.getHasta())) {
                    if (b_addOrUpdate) {
                        MovilidadUtil.addErrorMessage("Ya existe un registro "
                                + "con el tipo de costo seleccionado, para el rango de fechas (Fecha Desde)");
                        return true;
                    } else if (!param.getIdAuditoriaCosto().equals(obj.getIdAuditoriaCosto())) {

                        MovilidadUtil.addErrorMessage("Ya existe un registro "
                                + "con el tipo de costo seleccionado, para el rango de fechas (Fecha Desde)");
                        return true;
                    }
                }
                if (MovilidadUtil.betweenSinHora(b_addOrUpdate ? param.getHasta() : hasta, obj.getDesde(), obj.getHasta())) {
                    if (b_addOrUpdate) {
                        MovilidadUtil.addErrorMessage("Ya existe un registro "
                                + "con el tipo de costo seleccionado, para el rango de fechas (Fecha Hasta)");
                        return true;
                    } else if (!param.getIdAuditoriaCosto().equals(obj.getIdAuditoriaCosto())) {
                        MovilidadUtil.addErrorMessage("Ya existe un registro "
                                + "con el tipo de costo seleccionado, para el rango de fechas (Fecha Hasta)");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void deleteFromList(AuditoriaCosto param) {
        int indexOf = lstAutidoriaCosto.indexOf(param);
        lstAutidoriaCosto.remove(indexOf);
    }

    public void prepareEdit(AuditoriaCosto param) {
        auditoriaCosto = param;
        desde = param.getDesde();
        hasta = param.getHasta();
        valor = param.getValor();
        idAuditoriaCostoTipo = param.getIdAuditoriaCostoTipo().getIdAuditoriaCostoTipo();
        setAuditoriaCostoTipo(param.getIdAuditoriaCostoTipo());
        idVehiculoTipo = param.getIdVehiculoTipo().getIdVehiculoTipo();
        setVehiculoTipo(param.getIdVehiculoTipo());
        b_addOrUpdate = false;
    }

    private void resetValiables() {
        idAuditoriaCostoTipo = 0;
        idVehiculoTipo = 0;
        vehiculoTipo = null;
        auditoriaCostoTipo = null;
        valor = 0;
    }

    public String dateString(Date fecha) {
        return Util.dateFormat(fecha);
    }

    public void lstAutidoriaCostoSingleton() {
        if (lstAutidoriaCosto == null) {
            lstAutidoriaCosto = new ArrayList<>();
        } else {
            lstAutidoriaCosto.clear();
        }
    }

    public void cargarCostosByAuditoria(Auditoria param) {
        lstAutidoriaCostoSingleton();
        if (param.getIdAuditoria() != null) {
            lstAutidoriaCosto = auditoriaCostoEjb.findByIdAuditoria(param.getIdAuditoria());
        }

    }

    public void cargarVehiculoTipo() {
        for (AuditoriaCostoTipo obj : lstCostoTipo) {
            if (obj.getIdAuditoriaCostoTipo().equals(idAuditoriaCostoTipo)) {
                if (obj.getRequiereTipoVehiculo() == 1) {
                    reqTipoVehiculo = false;
                    break;
                }
            }
        }
    }

    public void cargarAuditorias(boolean goToDb) {
        if (lstAutidoria == null) {
            goToDb = true;
        }
        if (goToDb) {
            lstAutidoria = auditoriaEjb.findByAreaAndIdGopUnidadFuncional(i_idArea, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        }
    }

    public void cargarVehiculoTipo(boolean goToDb) {
        if (lstVehiculoTipo == null) {
            goToDb = true;
        }
        if (goToDb) {
            lstVehiculoTipo = VehiculoTipoEJB.findAllEstadoR();
        }
    }

    public void cargarCostoTipo(boolean goToDb) {
        if (lstCostoTipo == null) {
            goToDb = true;
        }
        if (goToDb) {
            lstCostoTipo = auditoriaCostoTipoEjb.findAllByEstadoReg();
        }
    }

    public String toString(Integer id) {
        return Integer.toString(id);
    }

    public void cargarVehiculoTipoFor() {
        Optional<VehiculoTipo> findFirst = lstVehiculoTipo.stream().filter((obj) -> (obj.getIdVehiculoTipo().equals(idVehiculoTipo))).findFirst();
        if (findFirst.isPresent()) {
            setVehiculoTipo(findFirst.get());
        } else {
            setVehiculoTipo(null);
        }
    }

    public void cargarCostoTipoFor() {
        Optional<AuditoriaCostoTipo> findFirst = lstCostoTipo.stream().filter((obj) -> (obj.getIdAuditoriaCostoTipo().equals(idAuditoriaCostoTipo))).findFirst();
        if (findFirst.isPresent()) {
            setAuditoriaCostoTipo(findFirst.get());
        } else {
            setAuditoriaCostoTipo(null);
        }
    }

    public void loadDataToObject() {
        if (b_addOrUpdate) {
            auditoriaCosto = new AuditoriaCosto();
        }
        auditoriaCosto.setUsername(user.getUsername());
        auditoriaCosto.setValor(valor);
        auditoriaCosto.setIdAuditoriaCostoTipo(auditoriaCostoTipo);
        auditoriaCosto.setIdVehiculoTipo(vehiculoTipo);
        auditoriaCosto.setDesde(desde);
        auditoriaCosto.setHasta(hasta);
        auditoriaCosto.setEstadoReg(0);
    }

    public boolean validarDatos() throws ParseException {
        if (idAuditoria == 0) {
            MovilidadUtil.addErrorMessage("No se ha cargado una auditoría");
            return true;
        }
        if (!reqTipoVehiculo && idVehiculoTipo == 0) {
            MovilidadUtil.addErrorMessage("No se ha cargado un tipo de Vehículo");
            return true;
        }
        if (idAuditoriaCostoTipo == 0) {
            MovilidadUtil.addErrorMessage("No se ha cargado un tipo de costo de auditoría");
            return true;
        }
        auditoria = auditoriaEjb.find(idAuditoria);
        if (auditoria != null) {
            if (!MovilidadUtil.betweenSinHora(desde, auditoria.getIdAuditoriaEncabezado().getFechaDesde(), auditoria.getIdAuditoriaEncabezado().getFechaHasta())) {
                MovilidadUtil.addErrorMessage("La Fecha desde no está en el rango de fechas de la auditoría.");
                return true;
            }
            if (!MovilidadUtil.betweenSinHora(hasta, auditoria.getIdAuditoriaEncabezado().getFechaDesde(), auditoria.getIdAuditoriaEncabezado().getFechaHasta())) {
                MovilidadUtil.addErrorMessage("La Fecha Hasta no está en el rango de fechas de la auditoría.");
                return true;
            }
        }
        if (isEditing) {
            if (auditoriaCostoEjb.validar(
                    idAuditoria,
                    idVehiculoTipo,
                    idAuditoriaCostoTipo,
                    desde,
                    hasta,
                    auditoriaCosto.getIdAuditoriaCosto()) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un costo de auditoría con el tipo de costo y auditoría para el rango de fecha establecido.");
                return true;
            }
        } else {
            if (auditoriaCostoEjb.validar(
                    idAuditoria,
                    idVehiculoTipo,
                    idAuditoriaCostoTipo,
                    desde,
                    hasta,
                    0) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un costo de auditoría con el tipo de costo y auditoría para el rango de fecha establecido.");
                return true;
            }
        }

        return false;
    }

    public AuditoriaCosto getSelected() {
        return selected;
    }

    public void setSelected(AuditoriaCosto selected) {
        this.selected = selected;
    }

    public int getIdVehiculoTipo() {
        return idVehiculoTipo;
    }

    public void setIdVehiculoTipo(int idVehiculoTipo) {
        this.idVehiculoTipo = idVehiculoTipo;
    }

    public int getCosto() {
        return valor;
    }

    public void setCosto(int valor) {
        this.valor = valor;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public List<VehiculoTipo> getLstVehiculoTipo() {
        return lstVehiculoTipo;
    }

    public void setLstVehiculoTipo(List<VehiculoTipo> lstVehiculoTipo) {
        this.lstVehiculoTipo = lstVehiculoTipo;
    }

    public int getIdAuditoriaCostoTipo() {
        return idAuditoriaCostoTipo;
    }

    public void setIdAuditoriaCostoTipo(int idAuditoriaCostoTipo) {
        this.idAuditoriaCostoTipo = idAuditoriaCostoTipo;
    }

    public int getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(int idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public List<AuditoriaCosto> getLstAutidoriaCosto() {
        return lstAutidoriaCosto;
    }

    public void setLstAutidoriaCosto(List<AuditoriaCosto> lstAutidoriaCosto) {
        this.lstAutidoriaCosto = lstAutidoriaCosto;
    }

    public List<Auditoria> getLstAutidoria() {
        return lstAutidoria;
    }

    public void setLstAutidoria(List<Auditoria> lstAutidoria) {
        this.lstAutidoria = lstAutidoria;
    }

    public List<AuditoriaCostoTipo> getLstCostoTipo() {
        return lstCostoTipo;
    }

    public void setLstCostoTipo(List<AuditoriaCostoTipo> lstCostoTipo) {
        this.lstCostoTipo = lstCostoTipo;
    }

    public boolean isReqTipoVehiculo() {
        return reqTipoVehiculo;
    }

    public void setReqTipoVehiculo(boolean reqTipoVehiculo) {
        this.reqTipoVehiculo = reqTipoVehiculo;
    }

    public int getI_idArea() {
        return i_idArea;
    }

    public void setI_idArea(int i_idArea) {
        this.i_idArea = i_idArea;
    }

    public ParamAreaUsr getParamAreaUsr() {
        return paramAreaUsr;
    }

    public void setParamAreaUsr(ParamAreaUsr paramAreaUsr) {
        this.paramAreaUsr = paramAreaUsr;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public boolean isB_vehiculoTipo() {
        return b_vehiculoTipo;
    }

    public void setB_vehiculoTipo(boolean b_vehiculoTipo) {
        this.b_vehiculoTipo = b_vehiculoTipo;
    }

    public VehiculoTipo getVehiculoTipo() {
        return vehiculoTipo;
    }

    public void setVehiculoTipo(VehiculoTipo vehiculoTipo) {
        this.vehiculoTipo = vehiculoTipo;
    }

    public AuditoriaCostoTipo getAuditoriaCostoTipo() {
        return auditoriaCostoTipo;
    }

    public void setAuditoriaCostoTipo(AuditoriaCostoTipo auditoriaCostoTipo) {
        this.auditoriaCostoTipo = auditoriaCostoTipo;
    }

    public boolean isB_addOrUpdate() {
        return b_addOrUpdate;
    }

    public void setB_addOrUpdate(boolean b_addOrUpdate) {
        this.b_addOrUpdate = b_addOrUpdate;
    }

}
