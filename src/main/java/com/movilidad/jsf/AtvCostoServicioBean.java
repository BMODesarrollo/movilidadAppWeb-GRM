/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AtvCostoServicioFacadeLocal;
import com.movilidad.ejb.AtvPrestadorFacadeLocal;
import com.movilidad.ejb.VehiculoTipoFacadeLocal;
import com.movilidad.model.AtvCostoServicio;
import com.movilidad.model.AtvPrestador;
import com.movilidad.model.VehiculoTipo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "atvCostoServicioBean")
@ViewScoped
public class AtvCostoServicioBean implements Serializable {

    @EJB
    private AtvPrestadorFacadeLocal atvPrestadorEjb;
    @EJB
    private VehiculoTipoFacadeLocal VehiculoTipoEJB;
    @EJB
    private AtvCostoServicioFacadeLocal atvCostoServicioEjb;

    private AtvCostoServicio atvCostoServicio;
    private AtvCostoServicio selected;
    private int idVehiculoTipo;
    private int idAtvPrestador;
    private String descripcion;
    private boolean isEditing;
    private int costo;
    private Date desde;
    private Date hasta;

    private AtvPrestador atvPrestador;
    private VehiculoTipo vehiculoTipo;

    private List<AtvCostoServicio> lstCostoServicios;
    private List<VehiculoTipo> lstVehiculoTipo;
    private List<AtvPrestador> lstPrestadores;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    public void consultar() {
        lstCostoServicios = atvCostoServicioEjb.findByEstadoReg();
    }

    public void nuevo() {
        idVehiculoTipo = 0;
        idAtvPrestador = 0;
        descripcion = "";
        costo = 0;
        atvPrestador = null;
        vehiculoTipo = null;
        desde = MovilidadUtil.fechaHoy();
        hasta = MovilidadUtil.fechaHoy();
        atvCostoServicio = new AtvCostoServicio();
        cargarPrestadores(false);
        cargarVehiculoTipo(false);
        selected = null;
        isEditing = false;
    }

    public void onRowSelect(SelectEvent param) {
        setSelected((AtvCostoServicio) param.getObject());
    }

    public void rowUnselect() {
        selected = null;
    }

    public void editar() {
        if (selected == null) {
            MovilidadUtil.addErrorMessage("No se ha selecionado un registro en la tabla.");
            return;
        }
        idVehiculoTipo = selected.getIdVehiculoTipo() != null
                ? selected.getIdVehiculoTipo().getIdVehiculoTipo() : 0;
        idAtvPrestador = selected.getIdAtvPrestador() != null
                ? selected.getIdAtvPrestador().getIdAtvPrestador() : 0;
        atvCostoServicio = selected;
        descripcion = selected.getDescripcion();
        costo = selected.getCosto();
        desde = selected.getDesde();
        hasta = selected.getHasta();
        cargarPrestadores(false);
        cargarVehiculoTipo(false);
        isEditing = true;
    }

    public void cargarPrestadores(boolean goToDb) {
        if (lstPrestadores == null) {
            goToDb = true;
        }
        if (goToDb) {
            lstPrestadores = atvPrestadorEjb.findAllByEstadoReg();
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

    public String toString(Integer id) {
        return Integer.toString(id);
    }

    public void guardar() throws ParseException {
        guardarTransactional();
        consultar();
    }

    @Transactional
    public void guardarTransactional() throws ParseException {
        if (validarDatos()) {
            return;
        }
        loadDataToObject();
        if (isEditing) {
            atvCostoServicio.setModificado(MovilidadUtil.fechaCompletaHoy());
            atvCostoServicioEjb.edit(atvCostoServicio);
            MovilidadUtil.hideModal("wv_costo_servicio");
            MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
        } else {
            atvCostoServicio.setEstadoReg(0);
            atvCostoServicio.setCreado(MovilidadUtil.fechaCompletaHoy());
            atvCostoServicioEjb.create(atvCostoServicio);
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
        }
    }

    public void loadDataToObject() {
        atvCostoServicio.setUsername(user.getUsername());
        atvCostoServicio.setCosto(costo);
        atvCostoServicio.setIdAtvPrestador(new AtvPrestador(idAtvPrestador));
        atvCostoServicio.setIdVehiculoTipo(new VehiculoTipo(idVehiculoTipo));
        atvCostoServicio.setDesde(desde);
        atvCostoServicio.setHasta(hasta);
        atvCostoServicio.setDescripcion(descripcion);
    }

    public boolean validarDatos() throws ParseException {
        if (idAtvPrestador == 0) {
            MovilidadUtil.addErrorMessage("No se ha cargado un Prestador");
            return true;
        }
        if (idVehiculoTipo == 0) {
            MovilidadUtil.addErrorMessage("No se ha cargado un tipo de Vehículo");
            return true;
        }
        if (isEditing) {
            if (atvCostoServicioEjb.findByCostoAndIdPrestadorAndVehiculoTipoAndRangoFecha(
                    idAtvPrestador,
                    idVehiculoTipo,
                    desde,
                    hasta,
                    atvCostoServicio.getIdAtvCostoServicio()
            ) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un Vehiculo de atención con la placa digitada para el prestador seleccionado");
                return true;
            }
        } else {
            if (atvCostoServicioEjb.findByCostoAndIdPrestadorAndVehiculoTipoAndRangoFecha(
                    idAtvPrestador,
                    idVehiculoTipo,
                    desde,
                    hasta,
                    0) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un Vehiculo de atención con la placa digitada para el prestador seleccionado");
                return true;
            }
        }

        return false;
    }

    public AtvCostoServicio getSelected() {
        return selected;
    }

    public void setSelected(AtvCostoServicio selected) {
        this.selected = selected;
    }

    public int getIdVehiculoTipo() {
        return idVehiculoTipo;
    }

    public void setIdVehiculoTipo(int idVehiculoTipo) {
        this.idVehiculoTipo = idVehiculoTipo;
    }

    public int getIdAtvPrestador() {
        return idAtvPrestador;
    }

    public void setIdAtvPrestador(int idAtvPrestador) {
        this.idAtvPrestador = idAtvPrestador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<AtvPrestador> getLstPrestadores() {
        return lstPrestadores;
    }

    public void setLstPrestadores(List<AtvPrestador> lstPrestadores) {
        this.lstPrestadores = lstPrestadores;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
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

    public List<AtvCostoServicio> getLstCostoServicios() {
        return lstCostoServicios;
    }

    public void setLstCostoServicios(List<AtvCostoServicio> lstCostoServicios) {
        this.lstCostoServicios = lstCostoServicios;
    }

    public List<VehiculoTipo> getLstVehiculoTipo() {
        return lstVehiculoTipo;
    }

    public void setLstVehiculoTipo(List<VehiculoTipo> lstVehiculoTipo) {
        this.lstVehiculoTipo = lstVehiculoTipo;
    }

}
