/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableAccInformePreguntaFacadeLocal;
import com.movilidad.ejb.CableAccInformeRespondienteFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.model.CableAccInformePregunta;
import com.movilidad.model.CableAccInformeRespondiente;
import com.movilidad.model.CableAccInformeRespondienteDet;
import com.movilidad.model.CableAccidentalidad;
import com.movilidad.model.Empleado;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Permite gestionar toda los datos para el objeto CableAccRespondiente
 * principal tabla afectada cable_acc_respondiente
 *
 * @author soluciones-it
 */
@Named(value = "cableAccInformeRespondienteJSF")
@ViewScoped
public class CableAccInformeRespondienteJSF implements Serializable {

    @EJB
    private CableAccInformeRespondienteFacadeLocal cblAccInfRespFacadeLocal;
    @EJB
    private CableAccInformePreguntaFacadeLocal cbAccInfoPreguntaFacadeLocal;
    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private List<CableAccInformeRespondienteDet> listCableAccInformeRespondienteDet;
    private CableAccInformeRespondiente cableAccInformeRespondiente;
    private CableAccInformeRespondienteDet cableAccInformeRespondienteDet;

    private List<Empleado> listEmpleados;
    private Integer idCableAccidentalidad;

    @Inject
    private CableAccidentalidadJSF caJSF;

    /**
     * Creates a new instance of CableAccRespondienteJSF
     */
    public CableAccInformeRespondienteJSF() {
    }

    // 
    public void init() {
        cableAccInformeRespondienteDet = null;
        idCableAccidentalidad = caJSF.compartirIdAccidente();
        if (idCableAccidentalidad != null) {
            cableAccInformeRespondiente = cblAccInfRespFacadeLocal.findByCableAccidentalidad(idCableAccidentalidad);
            if (cableAccInformeRespondiente != null) {
                listCableAccInformeRespondienteDet = cableAccInformeRespondiente.getCableAccInformeRespondienteDetList();
            }
        }
    }

    /**
     * Permite generar la nueva instancia para gestionar la data del objeto
     * CableAccInformeRespondiente
     */
    public void prepareGuardar() {
        cableAccInformeRespondiente = new CableAccInformeRespondiente();
        listCableAccInformeRespondienteDet = new ArrayList<>();
        CableAccInformeRespondienteDet caird;
        List<CableAccInformePregunta> listPreguntas = cbAccInfoPreguntaFacadeLocal.findAllEstadoReg();
        if (listPreguntas != null) {
            for (CableAccInformePregunta cip : listPreguntas) {
                caird = new CableAccInformeRespondienteDet();
                caird.setIdCableAccInformePregunta(cip);
                listCableAccInformeRespondienteDet.add(caird);
            }
        }
        PrimeFaces.current().executeScript("PF('wvRespuesta').show()");
    }

    /**
     * Permite persistir la data del objeto CableAccInformeRespondiente
     */
    public void guardar() {
        idCableAccidentalidad = caJSF.compartirIdAccidente();
        if (idCableAccidentalidad == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un caso de accidentaldad");
            return;
        }
        if (cableAccInformeRespondiente != null) {
            cableAccInformeRespondiente.setIdCableAccidentalidad(new CableAccidentalidad(idCableAccidentalidad));
            cableAccInformeRespondiente.setCreado(new Date());
            cableAccInformeRespondiente.setModificado(new Date());
            cableAccInformeRespondiente.setEstadoReg(0);
            cableAccInformeRespondiente.setUsername(user.getUsername());
            for (CableAccInformeRespondienteDet caird : listCableAccInformeRespondienteDet) {
                caird.setIdCableAccInformeRespondiente(cableAccInformeRespondiente);
            }
            cableAccInformeRespondiente.setCableAccInformeRespondienteDetList(listCableAccInformeRespondienteDet);
            cblAccInfRespFacadeLocal.create(cableAccInformeRespondiente);
            MovilidadUtil.addSuccessMessage("Informe Respondiente creado con éxito");
            MovilidadUtil.hideModal("wvRespuesta");
        }
    }

    /**
     * Permite update la data del objeto CableAccInformeRespondiente
     */
    public void actualizar() {
        if (idCableAccidentalidad == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un caso de accidentaldad");
            return;
        }
        if (cableAccInformeRespondiente != null) {
            for (CableAccInformeRespondienteDet caird : listCableAccInformeRespondienteDet) {
                caird.setIdCableAccInformeRespondiente(cableAccInformeRespondiente);
            }
            cableAccInformeRespondiente.setCableAccInformeRespondienteDetList(listCableAccInformeRespondienteDet);
            cblAccInfRespFacadeLocal.edit(cableAccInformeRespondiente);
            MovilidadUtil.addSuccessMessage("Informe Respondiente actualizado con éxito");
            MovilidadUtil.hideModal("wvRespuesta");
        }
    }

    public void reset() {
        init();
    }

    /**
     * Cargar lista de Empleado
     */
    public void cargarListaEmpleado() {
        listEmpleados = empleadoFacadeLocal.findAllEmpleadosActivos(0);
        PrimeFaces.current().executeScript("PF('wvSupervisor').show()");
    }

    public List<CableAccInformeRespondienteDet> getListCableAccInformeRespondienteDet() {
        return listCableAccInformeRespondienteDet;
    }

    public void setListCableAccInformeRespondienteDet(List<CableAccInformeRespondienteDet> listCableAccInformeRespondienteDet) {
        this.listCableAccInformeRespondienteDet = listCableAccInformeRespondienteDet;
    }

    public CableAccInformeRespondiente getCableAccInformeRespondiente() {
        return cableAccInformeRespondiente;
    }

    public void setCableAccInformeRespondiente(CableAccInformeRespondiente cableAccInformeRespondiente) {
        this.cableAccInformeRespondiente = cableAccInformeRespondiente;
    }

    public List<Empleado> getListEmpleados() {
        return listEmpleados;
    }

    public void setListEmpleados(List<Empleado> listEmpleados) {
        this.listEmpleados = listEmpleados;
    }

    public CableAccInformeRespondienteDet getCableAccInformeRespondienteDet() {
        return cableAccInformeRespondienteDet;
    }

    public void setCableAccInformeRespondienteDet(CableAccInformeRespondienteDet cableAccInformeRespondienteDet) {
        this.cableAccInformeRespondienteDet = cableAccInformeRespondienteDet;
    }

}
