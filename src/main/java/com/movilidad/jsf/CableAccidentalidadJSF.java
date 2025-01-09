/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableAccidentalidadFacadeLocal;
import com.movilidad.ejb.CableCabinaFacadeLocal;
import com.movilidad.ejb.CableEstacionFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.model.CableAccClasificacion;
import com.movilidad.model.CableAccTipo;
import com.movilidad.model.CableAccTipoEvento;
import com.movilidad.model.CableAccidentalidad;
import com.movilidad.model.CableCabina;
import com.movilidad.model.CableEstacion;
import com.movilidad.model.Empleado;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Permite persistir la data relacionada con los objetos CableAccidentalidad
 * Principal tabla afectada cable_accidentalidad
 *
 * @author soluciones-it
 */
@Named(value = "cableAccidentalidadJSF")
@ViewScoped
public class CableAccidentalidadJSF implements Serializable {

    @EJB
    private CableAccidentalidadFacadeLocal cableAccidentalidadFacadeLocal;
    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    private CableCabinaFacadeLocal cableCabinaFacadeLocal;
    @EJB
    private CableEstacionFacadeLocal cableEstacionFacadeLocal;

    private CableAccidentalidad cableAccidentalidad;
    private List<CableAccidentalidad> listCableAccidentalidad;
    private List<Empleado> listEmpleados;
    private List<CableCabina> listCableCabina;
    private List<CableEstacion> listCableEstacion;

    // panel de busqueda
    private Date dDesde;
    private Date dHasta;
    private String cIdentiEmpleado;
    private String cCodCableCabina;
    private Integer idCableAccTipoBq;

    // formulario CableAccidentalidad
    private Integer idCableAccTipo;
    private Integer idCableAccTipoEvento;
    private Integer idCableAccClasificacion;

    //control de reinicio
    @Inject
    private CableAccSiniestradoJSF casJSF;
    @Inject
    private CableAccTestigoJSF catJSF;
    @Inject
    private CableAccInformeRespondienteJSF cairJSF;
    @Inject
    private CableAccPlanAccionJSF capaJSF;
    @Inject
    private CableAccDocumentoJSF cadJSF;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of CableAccidentalidadJSF
     */
    public CableAccidentalidadJSF() {
    }

    @PostConstruct
    public void init() {
        dDesde = new Date();
        dHasta = new Date();
        cIdentiEmpleado = null;
        cCodCableCabina = null;
        idCableAccTipoBq = null;
        listCableAccidentalidad = null;
        cableAccidentalidad = new CableAccidentalidad();
        idCableAccTipo = null;
        idCableAccTipoEvento = null;
        idCableAccClasificacion = null;
    }

    /**
     * Persistir la data del objeto CableAccidentalidad en la base de datos
     */
    public void guardarCableAccidente() {
        cargarObjetos();
        if (validarForm()) {
            return;
        }
        cableAccidentalidad.setUsername(user.getUsername());
        cableAccidentalidad.setModificado(new Date());
        cableAccidentalidad.setCreado(new Date());
        cableAccidentalidad.setEstadoReg(0);
        cableAccidentalidadFacadeLocal.create(cableAccidentalidad);
        listCableAccidentalidad = cableAccidentalidadFacadeLocal.findByArguments(
                null,
                cableAccidentalidad.getIdOperador().getIdEmpleado(),
                Util.dateFormat(cableAccidentalidad.getFechaHora()),
                Util.dateFormat(cableAccidentalidad.getFechaHora()),
                null
        );
        cableAccidentalidad = new CableAccidentalidad();
        idCableAccTipo = null;
        idCableAccTipoEvento = null;
        idCableAccClasificacion = null;
        MovilidadUtil.addSuccessMessage("Accidente guardado con éxito");
    }

    /**
     * Update de la data del objeto CableAccidentalidad en la base de datos
     */
    public void actualizarCableAccidente() {
        cargarObjetos();
        if (validarForm()) {
            return;
        }
        cableAccidentalidad.setModificado(new Date());
        cableAccidentalidadFacadeLocal.edit(cableAccidentalidad);
        listCableAccidentalidad = cableAccidentalidadFacadeLocal.findByArguments(
                null,
                cableAccidentalidad.getIdOperador().getIdEmpleado(),
                Util.dateFormat(cableAccidentalidad.getFechaHora()),
                Util.dateFormat(cableAccidentalidad.getFechaHora()),
                null
        );
        MovilidadUtil.addSuccessMessage("Accidente actualizado con éxito");
    }

    /**
     * Capturar objeto CableAccidentalidad
     *
     * @param event Objeto SelectEvent
     */
    public void onRowSelect(SelectEvent event) {
        if (event.getObject() instanceof CableAccidentalidad) {
            cableAccidentalidad = (CableAccidentalidad) event.getObject();
            idCableAccClasificacion = cableAccidentalidad.getIdCableAccClasificacion() != null ? cableAccidentalidad.getIdCableAccClasificacion().getIdCableAccClasificacion() : null;
            idCableAccTipoEvento = cableAccidentalidad.getIdCableAccTipoEvento() != null ? cableAccidentalidad.getIdCableAccTipoEvento().getIdCableAccTipoEvento() : null;
            idCableAccTipo = cableAccidentalidad.getIdCableAccTipo() != null ? cableAccidentalidad.getIdCableAccTipo().getIdCableAccTipo() : null;
        }

        //Reinicio de beans cada vez que se selecciona un objeto CableAccidentalidad
        casJSF.reset();
        catJSF.reset();
        cairJSF.reset();
        capaJSF.reset();
        cadJSF.reset();
    }

    /**
     * Cargar lista de Empleado
     */
    public void cargarListaEmpleado() {
        listEmpleados = empleadoFacadeLocal.findAllEmpleadosActivos(0);
        PrimeFaces.current().executeScript("PF('wvEmpleados').show()");
    }

    /**
     * Cargar lista de CableCabina
     */
    public void cargarListaCabina() {
        listCableCabina = cableCabinaFacadeLocal.findAllByEstadoReg();
        PrimeFaces.current().executeScript("PF('wvCabinas').show()");
    }

    /**
     * Cargar lista de CableEstacion
     */
    public void cargarListaEstacion() {
        listCableEstacion = cableEstacionFacadeLocal.findByEstadoReg();
        PrimeFaces.current().executeScript("PF('wvEstacion').show()");
    }

    /**
     * Capturar objeto CableCabina
     *
     * @param event Objeto CableCabina
     */
    public void onSelectCabina(CableCabina event) {
        cableAccidentalidad.setIdCableCabina(event);
        MovilidadUtil.addSuccessMessage("Cabina cargada correctamente");
        PrimeFaces.current().executeScript("PF('wvCabinas').hide()");
    }

    /**
     * Capturar objeto CableEstacion
     *
     * @param event Objeto CableEstacion
     */
    public void onSelectEstacion(CableEstacion event) {
        cableAccidentalidad.setIdCableEstacion(event);
        MovilidadUtil.addSuccessMessage("Estación cargada correctamente");
        PrimeFaces.current().executeScript("PF('wvEstacion').hide()");
    }

    /**
     * Capturar objeto Empleado
     *
     * @param event Objeto Empleado
     */
    public void onSelectEmpleado(Empleado event) {
        cableAccidentalidad.setIdOperador(event);
        MovilidadUtil.addSuccessMessage("Supervisor cargado correctamente");
        PrimeFaces.current().executeScript("PF('wvEmpleados').hide()");
    }

    /**
     * Realizar la busqueda de los objeros CableAccidentalidad de acuerdo a los
     * parametros de busqueda
     */
    public void buscarAccidente() {
        Integer idEmpleado = null;
        Integer idCabina = null;
        if (dHasta.compareTo(dDesde) < 0) {
            MovilidadUtil.addErrorMessage("Fecha hasta no puede ser inferior a fecha desde");
            return;
        }
        if (cIdentiEmpleado != null) {
            Empleado empleado = empleadoFacadeLocal.findByIdentificacion(cIdentiEmpleado);
            if (empleado != null) {
                idEmpleado = empleado.getIdEmpleado();
            }
        }
        if (cCodCableCabina != null) {
            CableCabina cableCabina = cableCabinaFacadeLocal.findByCodigo(cCodCableCabina, 0);
            if (cableCabina != null) {
                idCabina = cableCabina.getIdCableCabina();
            }
        }
        String cDesde = Util.dateFormat(dDesde);
        String cHasta = Util.dateFormat(dHasta);
        listCableAccidentalidad = cableAccidentalidadFacadeLocal.findByArguments(idCabina, idEmpleado, cDesde, cHasta, idCableAccTipoBq);
    }

    /**
     * Limpiar las variables de realizar la busqueda
     */
    public void limpiar() {
        dDesde = new Date();
        dHasta = new Date();
        cIdentiEmpleado = null;
        cCodCableCabina = null;
        cableAccidentalidad = new CableAccidentalidad();
        listCableAccidentalidad = null;
        idCableAccTipoBq = null;
        idCableAccTipo = null;
        idCableAccTipoEvento = null;
        idCableAccClasificacion = null;
    }

    void cargarObjetos() {
        if (idCableAccClasificacion != null) {
            cableAccidentalidad.setIdCableAccClasificacion(new CableAccClasificacion(idCableAccClasificacion));
        }
        if (idCableAccTipoEvento != null) {
            cableAccidentalidad.setIdCableAccTipoEvento(new CableAccTipoEvento(idCableAccTipoEvento));
        }
        if (idCableAccTipo != null) {
            cableAccidentalidad.setIdCableAccTipo(new CableAccTipo(idCableAccTipo));
        }
    }

    boolean validarForm() {
        if (cableAccidentalidad.getIdOperador() == null) {
            MovilidadUtil.addErrorMessage("Operador es requerido");
            return true;
        }
        if (cableAccidentalidad.getIdCableAccTipo() == null) {
            MovilidadUtil.addErrorMessage("Tipo Acc es requerido");
            return true;
        }
        if (cableAccidentalidad.getIdCableAccClasificacion() == null) {
            MovilidadUtil.addErrorMessage("Clasificación es requerido");
            return true;
        }
        if (cableAccidentalidad.getIdCableCabina() == null && cableAccidentalidad.getIdCableEstacion() == null) {
            if (cableAccidentalidad.getOtroLugar() == null) {
                MovilidadUtil.addErrorMessage("Cabina o Otro lugar es requerido");
                return true;
            } else if (cableAccidentalidad.getOtroLugar().isEmpty()) {
                MovilidadUtil.addErrorMessage("Cabina o Otro lugar es requerido");
                return true;
            }
        }
        return false;
    }

    /**
     * Obtener el idCableAccidentalidad que se encuentre seleccionado por el
     * usuario
     *
     * @return Integer idCableAccidentalidad, 0 si es null CableAccidentalidad
     */
    public Integer compartirIdAccidente() {
        if (cableAccidentalidad != null && cableAccidentalidad.getIdCableAccidentalidad() != null) {
            return cableAccidentalidad.getIdCableAccidentalidad();
        }
        return null;
    }

    public CableAccidentalidad getCableAccidentalidad() {
        return cableAccidentalidad;
    }

    public void setCableAccidentalidad(CableAccidentalidad cableAccidentalidad) {
        this.cableAccidentalidad = cableAccidentalidad;
    }

    public List<CableAccidentalidad> getListCableAccidentalidad() {
        return listCableAccidentalidad;
    }

    public void setListCableAccidentalidad(List<CableAccidentalidad> listCableAccidentalidad) {
        this.listCableAccidentalidad = listCableAccidentalidad;
    }

    public List<Empleado> getListEmpleados() {
        return listEmpleados;
    }

    public void setListEmpleados(List<Empleado> listEmpleados) {
        this.listEmpleados = listEmpleados;
    }

    public List<CableCabina> getListCableCabina() {
        return listCableCabina;
    }

    public void setListCableCabina(List<CableCabina> listCableCabina) {
        this.listCableCabina = listCableCabina;
    }

    public Date getdDesde() {
        return dDesde;
    }

    public void setdDesde(Date dDesde) {
        this.dDesde = dDesde;
    }

    public Date getdHasta() {
        return dHasta;
    }

    public void setdHasta(Date dHasta) {
        this.dHasta = dHasta;
    }

    public String getcIdentiEmpleado() {
        return cIdentiEmpleado;
    }

    public void setcIdentiEmpleado(String cIdentiEmpleado) {
        this.cIdentiEmpleado = cIdentiEmpleado;
    }

    public String getcCodCableCabina() {
        return cCodCableCabina;
    }

    public void setcCodCableCabina(String cCodCableCabina) {
        this.cCodCableCabina = cCodCableCabina;
    }

    public Integer getIdCableAccTipo() {
        return idCableAccTipo;
    }

    public void setIdCableAccTipo(Integer idCableAccTipo) {
        this.idCableAccTipo = idCableAccTipo;
    }

    public Integer getIdCableAccTipoBq() {
        return idCableAccTipoBq;
    }

    public void setIdCableAccTipoBq(Integer idCableAccTipoBq) {
        this.idCableAccTipoBq = idCableAccTipoBq;
    }

    public Integer getIdCableAccTipoEvento() {
        return idCableAccTipoEvento;
    }

    public void setIdCableAccTipoEvento(Integer idCableAccTipoEvento) {
        this.idCableAccTipoEvento = idCableAccTipoEvento;
    }

    public Integer getIdCableAccClasificacion() {
        return idCableAccClasificacion;
    }

    public void setIdCableAccClasificacion(Integer idCableAccClasificacion) {
        this.idCableAccClasificacion = idCableAccClasificacion;
    }

    public List<CableEstacion> getListCableEstacion() {
        return listCableEstacion;
    }

    public void setListCableEstacion(List<CableEstacion> listCableEstacion) {
        this.listCableEstacion = listCableEstacion;
    }

}
