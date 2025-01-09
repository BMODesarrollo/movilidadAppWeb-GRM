/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpresaFacadeLocal;
import com.movilidad.ejb.OperacionPatiosFacadeLocal;
import com.movilidad.model.Empresa;
import com.movilidad.model.OperacionPatios;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Soluciones IT
 */
@Named(value = "operPatJSFMB")
@ViewScoped
public class OperacionPatioJSFManagedBean implements Serializable {

    private OperacionPatios opPatios;
    private int i_idEmpresa = 0;

    @EJB
    private OperacionPatiosFacadeLocal opPatiosEJB;
    @EJB
    private EmpresaFacadeLocal empresatEJB;

    private List<OperacionPatios> listOperacionPatios;
    private List<OperacionPatios> listOperacionPatiosDialog;
    private List<Empresa> listEmpresas;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    //georeferencia
    private String cLatitud = "4.646189";
    private String cLogitud = "-74.078540";
    private MapModel simpleModel;

    /**
     * Creates a new instance of EmpleadoMunicipioJSFManagedBean
     */
    public OperacionPatioJSFManagedBean() {
        opPatios = new OperacionPatios();
        this.listOperacionPatios = new ArrayList();
        this.listEmpresas = new ArrayList();
        this.listOperacionPatiosDialog = new ArrayList();
    }

    @PostConstruct
    public void init() {
        ListarOpPatios();
        simpleModel = new DefaultMapModel();
    }

    public void openDialog() {
        i_idEmpresa = 0;
        opPatios = new OperacionPatios();
        ListarEmpresas();
        simpleModel = new DefaultMapModel();
    }

    public void ListarOpPatios() {
        listOperacionPatios = opPatiosEJB.findAll();
    }

    public void ListarEmpresas() {
        listEmpresas = empresatEJB.findAll();
    }

    public void guardar() {
        if (listOperacionPatiosDialog.isEmpty()) {
            MovilidadUtil.addAdvertenciaMessage("Debe agregar al menos un Patio a la tabla");
            return;
        }
        for (OperacionPatios op : listOperacionPatiosDialog) {
            try {
                opPatiosEJB.create(op);
                MovilidadUtil.addSuccessMessage("Exito. Registro exitoso Patio ");
            } catch (Exception e) {
                MovilidadUtil.addErrorMessage(e.getMessage());
            }
        }
        listOperacionPatiosDialog.clear();
        init();
    }

    public void agregar() {
        if (this.i_idEmpresa == 0) {
            MovilidadUtil.addAdvertenciaMessage("Debe seleccionar la Empresa");
            return;
        }
        if (opPatiosEJB.findByCodigo(opPatios.getCodigoPatio(), 0) != null) {
            MovilidadUtil.addErrorMessage("Ya existe Tipo de Cargo con este Codígo");
            return;
        }

        for (Empresa e : listEmpresas) {
            if (e.getIdEmpresa().equals(i_idEmpresa)) {
                opPatios.setIdEmpresa(e);
            }
        }
        opPatios.setCreado(new Date());
        opPatios.setUsername(user.getUsername());
        opPatios.setEstadoReg(0);
        listOperacionPatiosDialog.add(opPatios);
        opPatios = new OperacionPatios();
        simpleModel = new DefaultMapModel();
    }

    public void guardarEdit() {
        if (opPatiosEJB.findByCodigo(opPatios.getCodigoPatio(), opPatios.getIdOperacionPatios()) != null) {
            MovilidadUtil.addErrorMessage("Ya existe Tipo de Cargo con este Nombre");
            return;
        }
        if (this.i_idEmpresa == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar la Empresa");
            return;
        }
        opPatios.setIdEmpresa(new Empresa(i_idEmpresa));

        opPatios.setUsername(user.getUsername());
        opPatios.setModificado(new Date());

        try {
            opPatiosEJB.edit(opPatios);
            MovilidadUtil.addSuccessMessage("Exito. Se modificó el registro exitosamente");
            closeDialog();
        } catch (Exception e) {
        }
    }

    public void closeDialog() {
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dialogOPP').hide();");
        init();
    }

    public void editar(OperacionPatios op) {
        if (listEmpresas.isEmpty()) {
            ListarEmpresas();
        }
        opPatios = op;
        i_idEmpresa = op.getIdEmpresa().getIdEmpresa();
        if (op.getLongitud() != null && op.getLatitud() != null) {
            cLogitud = op.getLongitud();
            cLatitud = op.getLatitud();
            LatLng latlng = new LatLng(Double.parseDouble(cLatitud), Double.parseDouble(cLogitud));
            simpleModel.addOverlay(new Marker(latlng, "Punto Seleccionado"));
        } else {
            cLatitud = "4.646189";
            cLogitud = "-74.078540";
        }

    }

    public void eliminar(OperacionPatios opp) {
        opp.setEstadoReg(1);
        try {
            opPatiosEJB.edit(opp);
            MovilidadUtil.addSuccessMessage("Eliminado. Se eliminó el registro exitosamente");
            init();
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    public void onPointSelect(PointSelectEvent event) {
        try {
            simpleModel = new DefaultMapModel();
            LatLng latlng = event.getLatLng();
            opPatios.setLatitud(String.valueOf(latlng.getLat()));
            opPatios.setLongitud(String.valueOf(latlng.getLng()));
            simpleModel.addOverlay(new Marker(latlng, "Punto Seleccionado"));
            cLatitud = String.valueOf(latlng.getLat());
            cLogitud = String.valueOf(latlng.getLng());
            MovilidadUtil.addSuccessMessage("Coordenadas seleccionadas, Longitud:"
                    + cLogitud + " Latitud:" + cLatitud);
        } catch (Exception e) {
            System.out.println("Error Evento onpointSelect AccidenteLugar");
        }
    }

    public void eliminarListaDialog(OperacionPatios objt) {
        listOperacionPatiosDialog.remove(objt);
    }

    public OperacionPatios getOpPatios() {
        return opPatios;
    }

    public void setOpPatios(OperacionPatios opPatios) {
        this.opPatios = opPatios;
    }

    public List<OperacionPatios> getListOperacionPatios() {
        return listOperacionPatios;
    }

    public void setListOperacionPatios(List<OperacionPatios> listOperacionPatios) {
        this.listOperacionPatios = listOperacionPatios;
    }

    public List<Empresa> getListEmpresas() {
        return listEmpresas;
    }

    public void setListEmpresas(List<Empresa> listEmpresas) {
        this.listEmpresas = listEmpresas;
    }

    public List<OperacionPatios> getListOperacionPatiosDialog() {
        return listOperacionPatiosDialog;
    }

    public void setListOperacionPatiosDialog(List<OperacionPatios> listOperacionPatiosDialog) {
        this.listOperacionPatiosDialog = listOperacionPatiosDialog;
    }

    public int getI_idEmpresa() {
        return i_idEmpresa;
    }

    public void setI_idEmpresa(int i_idEmpresa) {
        this.i_idEmpresa = i_idEmpresa;
    }

    public String getcLatitud() {
        return cLatitud;
    }

    public void setcLatitud(String cLatitud) {
        this.cLatitud = cLatitud;
    }

    public String getcLogitud() {
        return cLogitud;
    }

    public void setcLogitud(String cLogitud) {
        this.cLogitud = cLogitud;
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public void setSimpleModel(MapModel simpleModel) {
        this.simpleModel = simpleModel;
    }

}
