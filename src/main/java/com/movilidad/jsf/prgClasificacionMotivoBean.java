/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AtvPrestadorFacadeLocal;
import com.movilidad.ejb.PrgClasificacionMotivoFacadeLocal;
import com.movilidad.ejb.PrgTcResponsableFacadeLocal;
import com.movilidad.model.PrgClasificacionMotivo;
import com.movilidad.model.PrgTcResponsable;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
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

/**
 *
 * @author solucionesit
 */
@Named(value = "prgClasifMotivoBean")
@ViewScoped
public class prgClasificacionMotivoBean implements Serializable{

    @EJB
    private PrgClasificacionMotivoFacadeLocal prgClasificacionMotivoEJB;
    @EJB
    private PrgTcResponsableFacadeLocal prgTcResponsableEjb;

    private PrgClasificacionMotivo prgClasificacionMotivo;
    private PrgClasificacionMotivo selected;
    private int idPrgResponsable;
    private String nombre;
    private boolean activo;
    private String descripcion;
    private boolean isEditing;

    private PrgTcResponsable prgTcResponsable;

    private List<PrgClasificacionMotivo> lstClasifMotivo;
    private List<PrgTcResponsable> lstResponsables;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    public void consultar() {
        lstClasifMotivo = prgClasificacionMotivoEJB.findByEstadoReg();
    }

    public void nuevo() {
        idPrgResponsable = 0;
        nombre = "";
        descripcion = "";
        activo = false;
        prgTcResponsable = null;
        prgClasificacionMotivo = new PrgClasificacionMotivo();
        cargarResponsables(false);
        selected = null;
        isEditing = false;
    }

    public void onRowSelect(SelectEvent param) {
        setSelected((PrgClasificacionMotivo) param.getObject());
    }

    public void rowUnselect() {
        selected = null;
    }

    public void editar() {
        if (selected == null) {
            MovilidadUtil.addErrorMessage("No se ha selecionado un registro en la tabla.");
            return;
        }
        idPrgResponsable = selected.getIdPrgTcResponsable() != null
                ? selected.getIdPrgTcResponsable().getIdPrgTcResponsable() : 0;
        prgClasificacionMotivo = selected;
        descripcion = selected.getDescripcion();
        nombre = selected.getNombre();
        cargarResponsables(false);
        isEditing = true;
    }

    public void cargarResponsables(boolean goToDb) {
        if (lstResponsables == null) {
            goToDb = true;
        }
        if (goToDb) {
            lstResponsables = prgTcResponsableEjb.getPrgResponsables();
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
            prgClasificacionMotivo.setModificado(MovilidadUtil.fechaCompletaHoy());
            prgClasificacionMotivoEJB.edit(prgClasificacionMotivo);
            MovilidadUtil.hideModal("wv_clasif_motivo");
            MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
        } else {
            prgClasificacionMotivo.setEstadoReg(0);
            prgClasificacionMotivo.setCreado(MovilidadUtil.fechaCompletaHoy());
            prgClasificacionMotivoEJB.create(prgClasificacionMotivo);
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
        }
    }

    public void loadDataToObject() {
        prgClasificacionMotivo.setUsername(user.getUsername());
        prgClasificacionMotivo.setIdPrgTcResponsable(new PrgTcResponsable(idPrgResponsable));
        prgClasificacionMotivo.setNombre(nombre);
        prgClasificacionMotivo.setDescripcion(descripcion);
    }

    public boolean validarDatos() throws ParseException {
        if (idPrgResponsable == 0) {
            MovilidadUtil.addErrorMessage("No se ha seleccionado un responsable");
            return true;
        }
        if (isEditing) {
            if (prgClasificacionMotivoEJB.findBynombreAndIdPrgResponsable(nombre, idPrgResponsable, selected.getIdPrgClasificacionMotivo()) != null) {
                MovilidadUtil.addErrorMessage("Ya existe una clasificación para con el nombre digitado para el responsable seleccionado");
                return true;
            }
        } else {
            if (prgClasificacionMotivoEJB.findBynombreAndIdPrgResponsable(nombre, idPrgResponsable, 0) != null) {
                MovilidadUtil.addErrorMessage("Ya existe una clasificación para con el nombre digitado para el responsable seleccionado");
                return true;
            }
        }

        return false;
    }

    public int getIdPrgResponsable() {
        return idPrgResponsable;
    }

    public void setIdPrgResponsable(int idPrgResponsable) {
        this.idPrgResponsable = idPrgResponsable;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<PrgClasificacionMotivo> getLstClasifMotivo() {
        return lstClasifMotivo;
    }

    public void setLstClasifMotivo(List<PrgClasificacionMotivo> lstClasifMotivo) {
        this.lstClasifMotivo = lstClasifMotivo;
    }

    public List<PrgTcResponsable> getLstResponsables() {
        return lstResponsables;
    }

    public void setLstResponsables(List<PrgTcResponsable> lstResponsables) {
        this.lstResponsables = lstResponsables;
    }

    public PrgClasificacionMotivo getSelected() {
        return selected;
    }

    public void setSelected(PrgClasificacionMotivo selected) {
        this.selected = selected;
    }

}
