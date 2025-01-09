/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AtvPrestadorFacadeLocal;
import com.movilidad.ejb.AtvTipoAtencionFacadeLocal;
import com.movilidad.ejb.AtvVehiculosAtencionFacadeLocal;
import com.movilidad.model.AtvPrestador;
import com.movilidad.model.AtvTipoAtencion;
import com.movilidad.model.AtvVehiculosAtencion;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author solucionesit
 */
@Named(value = "atvVehiculoAtencionBean")
@ViewScoped
public class VehiculoAtencionBean implements Serializable {

    @EJB
    private AtvPrestadorFacadeLocal atvPrestadorEjb;
    @EJB
    private AtvTipoAtencionFacadeLocal atvTipoAtencionEjb;
    @EJB
    private AtvVehiculosAtencionFacadeLocal atvVehiculosAtencionEjb;

    private AtvVehiculosAtencion atvVehiculosAtencion;
    private AtvVehiculosAtencion selected;
    private int idAtvTipoAtencion;
    private int idAtvPrestador;
    private String placa;
    private boolean activo;
    private String descripcion;
    private boolean isEditing;
    private String contrasena;
    private String telefono;

    private AtvPrestador atvPrestador;
    private AtvTipoAtencion atvTipoAtencion;

    private List<AtvVehiculosAtencion> lstVehiculosAtencion;
    private List<AtvTipoAtencion> lstTiposAtencion;
    private List<AtvPrestador> lstPrestadores;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public void consultar() {
        lstVehiculosAtencion = atvVehiculosAtencionEjb.findByEstadoReg();
    }

    public void nuevo() {
        idAtvTipoAtencion = 0;
        idAtvPrestador = 0;
        placa = "";
        descripcion = "";
        activo = false;
        atvPrestador = null;
        atvTipoAtencion = null;
        atvVehiculosAtencion = new AtvVehiculosAtencion();
        cargarPrestadores(false);
        cargarTiposAtencion(false);
        selected = null;
        isEditing = false;
        contrasena = null;
        telefono = null;
    }

    public void onRowSelect(SelectEvent param) {
        setSelected((AtvVehiculosAtencion) param.getObject());
        contrasena = null;
    }

    public void rowUnselect() {
        selected = null;
        contrasena = null;
    }

    public void editar() {
        if (selected == null) {
            MovilidadUtil.addErrorMessage("No se ha selecionado un registro en la tabla.");
            return;
        }
        idAtvTipoAtencion = selected.getIdAtvTipoAtencion() != null
                ? selected.getIdAtvTipoAtencion().getIdAtvTipoAtencion() : 0;
        idAtvPrestador = selected.getIdAtvPrestador() != null
                ? selected.getIdAtvPrestador().getIdAtvPrestador() : 0;
        atvVehiculosAtencion = selected;
        activo = selected.getActivo() == 1;
        descripcion = selected.getDescripcion();
        placa = selected.getPlaca();
        telefono = selected.getTelefono();
        cargarPrestadores(false);
        cargarTiposAtencion(false);
        isEditing = true;
        contrasena = null;
    }

    public void cargarPrestadores(boolean goToDb) {
        if (lstPrestadores == null) {
            goToDb = true;
        }
        if (goToDb) {
            lstPrestadores = atvPrestadorEjb.findAllByEstadoReg();
        }
    }

    public void cargarTiposAtencion(boolean goToDb) {
        if (lstTiposAtencion == null) {
            goToDb = true;
        }
        if (goToDb) {
            lstTiposAtencion = atvTipoAtencionEjb.findAllByEstadoReg();
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
        if (!Util.isStringNullOrEmpty(contrasena)) {
            String encode = bCryptPasswordEncoder.encode(contrasena);
            atvVehiculosAtencion.setContrasenaUsuario(encode);
        }
        if (isEditing) {
            atvVehiculosAtencion.setModificado(MovilidadUtil.fechaCompletaHoy());
            atvVehiculosAtencionEjb.edit(atvVehiculosAtencion);
            MovilidadUtil.hideModal("wv_vehiculo_atencion");
            MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
        } else {
            atvVehiculosAtencion.setEstadoReg(0);
            atvVehiculosAtencion.setCreado(MovilidadUtil.fechaCompletaHoy());
            atvVehiculosAtencionEjb.create(atvVehiculosAtencion);
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
        }
    }

    public void loadDataToObject() {
        atvVehiculosAtencion.setUsername(user.getUsername());
        atvVehiculosAtencion.setActivo(activo ? 1 : 0);
        atvVehiculosAtencion.setIdAtvPrestador(new AtvPrestador(idAtvPrestador));
        atvVehiculosAtencion.setIdAtvTipoAtencion(new AtvTipoAtencion(idAtvTipoAtencion));
        atvVehiculosAtencion.setPlaca(placa);
        atvVehiculosAtencion.setDescripcion(descripcion);
        atvVehiculosAtencion.setTelefono(telefono);
    }

    public boolean validarDatos() throws ParseException {
        if (idAtvPrestador == 0) {
            MovilidadUtil.addErrorMessage("No se ha cargado un Prestador");
            return true;
        }
        if (idAtvTipoAtencion == 0) {
            MovilidadUtil.addErrorMessage("No se ha cargado un tipo de Atención");
            return true;
        }
        if (isEditing) {
            if (atvVehiculosAtencionEjb.findByplacaAndIdPrestador(placa, idAtvPrestador, selected.getIdAtvVehiculosAtencion()) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un Vehiculo de atención con la placa digitada para el prestador seleccionado");
                return true;
            }
        } else {
            if (atvVehiculosAtencionEjb.findByplacaAndIdPrestador(placa, idAtvPrestador, 0) != null) {
                MovilidadUtil.addErrorMessage("Ya existe un Vehiculo de atención con la placa digitada para el prestador seleccionado");
                return true;
            }
        }

        return false;
    }

    public AtvVehiculosAtencion getSelected() {
        return selected;
    }

    public void setSelected(AtvVehiculosAtencion selected) {
        this.selected = selected;
    }

    public int getIdAtvTipoAtencion() {
        return idAtvTipoAtencion;
    }

    public void setIdAtvTipoAtencion(int idAtvTipoAtencion) {
        this.idAtvTipoAtencion = idAtvTipoAtencion;
    }

    public int getIdAtvPrestador() {
        return idAtvPrestador;
    }

    public void setIdAtvPrestador(int idAtvPrestador) {
        this.idAtvPrestador = idAtvPrestador;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<AtvVehiculosAtencion> getLstVehiculosAtencion() {
        return lstVehiculosAtencion;
    }

    public void setLstVehiculosAtencion(List<AtvVehiculosAtencion> lstVehiculosAtencion) {
        this.lstVehiculosAtencion = lstVehiculosAtencion;
    }

    public List<AtvTipoAtencion> getLstTiposAtencion() {
        return lstTiposAtencion;
    }

    public void setLstTiposAtencion(List<AtvTipoAtencion> lstTiposAtencion) {
        this.lstTiposAtencion = lstTiposAtencion;
    }

    public List<AtvPrestador> getLstPrestadores() {
        return lstPrestadores;
    }

    public void setLstPrestadores(List<AtvPrestador> lstPrestadores) {
        this.lstPrestadores = lstPrestadores;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
