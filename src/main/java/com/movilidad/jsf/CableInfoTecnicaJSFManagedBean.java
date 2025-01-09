/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableInfoTecnicaFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.model.CableInfoTecnica;
import com.movilidad.model.Empleado;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "cableInfoTecnicaJSFMB")
@ViewScoped
public class CableInfoTecnicaJSFManagedBean implements Serializable {

    /**
     * Creates a new instance of CableInfoTecnicaJSFManagedBean
     */
    public CableInfoTecnicaJSFManagedBean() {
    }
    @EJB
    private CableInfoTecnicaFacadeLocal cableInfoTecnicaEJB;
    @EJB
    private EmpleadoFacadeLocal empleadoEJB;
    
    private List<CableInfoTecnica> list;
    private List<Empleado> listEmpleado;
    private CableInfoTecnica cableInfoTecnica;
    private Date fechaDesde = MovilidadUtil.fechaHoy();
    private Date fechaHasta = MovilidadUtil.fechaHoy();
    private int i_idEmpleado;
    
    UserExtended user;
    
    @PostConstruct
    public void init() {
        consultar();
        getUser();
    }

    /**
     * Obtener valor de usuario en sesión.
     */
    public void getUser() {
        try {
            user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            user = null;
        }
    }

    /**
     * Preparar variables para un nuevo registro.
     */
    public void nuevo() {
        cableInfoTecnica = new CableInfoTecnica();
        cableInfoTecnica.setFecha(MovilidadUtil.fechaCompletaHoy());
        cargarEmpleados();
        i_idEmpleado = 0;
        MovilidadUtil.openModal("info_tecnica_wv");
    }

    /**
     * Preparar variables para la edicion de un registro seleccionado.
     *
     * @param cit
     */
    public void editar(CableInfoTecnica cit) {
        cableInfoTecnica = cit;
        i_idEmpleado = cableInfoTecnica.getEmpleado().getIdEmpleado();
        cargarEmpleados();
        MovilidadUtil.openModal("info_tecnica_wv");
        
    }
    
    public void cargarEmpleados() {
        listEmpleado = empleadoEJB.findAllEmpleadosByCargos(MovilidadUtil.getProperty("idCargosEmpleadoInfoTecnica"));
    }

    /**
     * Actualizar un registro en base de datos metodo Transactional
     *
     * @throws ParseException
     */
    @Transactional
    public void actulizarTransactional() throws ParseException {
        if (validar()) {
            return;
        }
        cableInfoTecnica.setModificado(MovilidadUtil.fechaCompletaHoy());
        cableInfoTecnica.setUsername(user.getUsername());
        cableInfoTecnica.setEmpleado(new Empleado(i_idEmpleado));
        cableInfoTecnicaEJB.edit(cableInfoTecnica);
        
        MovilidadUtil.addSuccessMessage("Se actualizó Información Técnica Exitosamente.");
        cableInfoTecnica = null;
        MovilidadUtil.hideModal("info_tecnica_wv");
        
    }

    /**
     * Metodo encargado de invocar los metodos actulizarTransactional y
     * consuiltar para el proceso de edicion de registros.
     *
     * @throws ParseException
     */
    public void actulizar() throws ParseException {
        actulizarTransactional();
        consultar();
    }

    /**
     * Validar que las variables en cuestion esten listas para persistir.
     *
     * @return True si sale algo mal, False si todo esta bien.
     * @throws ParseException
     */
    public boolean validar() throws ParseException {
        if (i_idEmpleado == 0) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar que empleado registra.");
            return true;
        }
        if (cableInfoTecnica.getFecha() == null) {
            MovilidadUtil.addErrorMessage("La fecha es requerida");
            return true;
        }
        if (MovilidadUtil.fechasIgualMenorMayor(cableInfoTecnica.getFecha(), MovilidadUtil.fechaHoy(), false) == 2) {
            MovilidadUtil.addErrorMessage("La fecha de registro no debe ser posterior a la actual.");
            return true;
        }
        return false;
    }

    /**
     * Metodo encargado de persistir en base de datos un nuevo registro de info
     * tecnica, metodo Transactional.
     *
     * @throws ParseException
     */
    @Transactional
    public void guardarTransactional() throws ParseException {
        if (validar()) {
            return;
        }
        cableInfoTecnica.setCreado(MovilidadUtil.fechaCompletaHoy());
        cableInfoTecnica.setUsername(user.getUsername());
        cableInfoTecnica.setEmpleado(new Empleado(i_idEmpleado));
        cableInfoTecnica.setEstadoReg(0);
        cableInfoTecnicaEJB.create(cableInfoTecnica);
        MovilidadUtil.addSuccessMessage("Se registró Información Técnica Exitosamente.");
        cableInfoTecnica = null;
        MovilidadUtil.hideModal("info_tecnica_wv");
        
    }

    /**
     * Metodo encargado de invocar los metodos guardarTransactional y consuiltar
     * para el proceso de nuevos de registros.
     *
     * @throws ParseException
     */
    public void guardar() throws ParseException {
        guardarTransactional();
        consultar();
    }

    /**
     * Consultar registros de cable info tecnica por rango de fecha.
     */
    public void consultar() {
        list = cableInfoTecnicaEJB.findByEstadoReg(fechaDesde, fechaHasta);
    }
    
    public List<CableInfoTecnica> getList() {
        return list;
    }
    
    public void setList(List<CableInfoTecnica> list) {
        this.list = list;
    }
    
    public Date getFechaDesde() {
        return fechaDesde;
    }
    
    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }
    
    public Date getFechaHasta() {
        return fechaHasta;
    }
    
    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }
    
    public CableInfoTecnica getCableInfoTecnica() {
        return cableInfoTecnica;
    }
    
    public void setCableInfoTecnica(CableInfoTecnica cableInfoTecnica) {
        this.cableInfoTecnica = cableInfoTecnica;
    }
    
    public int getI_idEmpleado() {
        return i_idEmpleado;
    }
    
    public void setI_idEmpleado(int i_idEmpleado) {
        this.i_idEmpleado = i_idEmpleado;
    }
    
    public List<Empleado> getListEmpleado() {
        return listEmpleado;
    }
    
    public void setListEmpleado(List<Empleado> listEmpleado) {
        this.listEmpleado = listEmpleado;
    }
    
}
