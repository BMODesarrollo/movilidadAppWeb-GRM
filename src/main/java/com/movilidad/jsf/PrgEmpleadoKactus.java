/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.dbconnection.Common;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.EmpleadoTipoCargoFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.EmpleadoDepartamento;
import com.movilidad.model.EmpleadoEstado;
import com.movilidad.model.EmpleadoMunicipio;
import com.movilidad.model.EmpleadoTipoCargo;
import com.movilidad.model.EmpleadoTipoIdentificacion;
import com.movilidad.model.OperacionPatios;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import kactus.ws.Datos;
import kactus.ws.Datos_Service;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Luis Alberto Vélez
 */
@Named(value = "prgEmpleadoKactus")
@ViewScoped
public class PrgEmpleadoKactus implements Serializable {

    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private EmpleadoTipoCargoFacadeLocal empleadoCargoEjb;

    HashMap<String, String> empleados;
    HashMap<String, EmpleadoTipoCargo> cargos;
    List<Empleado> lstEmpleado;
    List<EmpleadoTipoCargo> lstTipoCargo;
    boolean flag = false;

    Datos_Service serviceKactus;
    Datos datosKactus;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of PrgEmpleadoKactus
     */
    public PrgEmpleadoKactus() {
    }

    @PostConstruct
    public void init() {
    }

    public void setEmpleados() {
        empleados = new HashMap<>();
        for (Empleado e : empleadoEjb.findAll()) {
            empleados.put(e.getIdentificacion(), e.getCodigoTm() + "");
//            System.out.println(e.getIdentificacion() + "  --  " + e.getCodigoTm() + "");
        }
    }

    public void setCargos() {
        cargos = new HashMap<>();
        for (EmpleadoTipoCargo e : empleadoCargoEjb.findAll()) {
            cargos.put(e.getIdEmpleadoTipoCargo() + "", e);
        }
    }

    public void consumeEmployeeKactus() {
        setEmpleados();
        setCargos();
        List<kactus.ws.Empleado> employees = getDatosKactus().empleados();
        lstTipoCargo = new LinkedList<>();
        if (employees.size() > 0) {
            lstEmpleado = new LinkedList<>();
            for (kactus.ws.Empleado e : employees) {
                if (empleados.get(e.getCodEmpl()) == null && e.getIndActi().equals("A")) {
                    lstEmpleado.add(setEmpleado(e));
                }
            }
        }
        if (!lstEmpleado.isEmpty()) {
            flag = true;
        } else {
            MovilidadUtil.addSuccessMessage("No existen empleados nuevos en Kactus");
        }
    }

    public void apply() {
//        applyCargos();
        if (!lstTipoCargo.isEmpty()) {
            load2Db();
        }
        applyEmployees();
    }

    @Transactional
    public void applyEmployees() {
        if (!lstEmpleado.isEmpty()) {
            for (Empleado e : lstEmpleado) {
                empleadoEjb.create(e);
            }
            MovilidadUtil.addSuccessMessage("Se crearon " + lstEmpleado.size() + " empleados");
        }
        flag = false;
    }

    @Transactional
    private void applyCargos() {
        if (!lstTipoCargo.isEmpty()) {
            for (EmpleadoTipoCargo etc : lstTipoCargo) {
                empleadoCargoEjb.edit(etc);
            }
            MovilidadUtil.addSuccessMessage("Se crearon " + lstTipoCargo.size() + " cargos");
        }
    }

    public void load2Db() {
        Connection con = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO empleado_tipo_cargo "
                + "(id_empleado_tipo_cargo, "
                + "nombre_cargo, "
                + "username, "
                + "creado) values (?,?,?,?) ";
        try {
            con = Common.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            ps = con.prepareStatement(sql);
            int i = 0;
            long start = System.currentTimeMillis();

            for (EmpleadoTipoCargo c : lstTipoCargo) {
                ps.setInt(1, c.getIdEmpleadoTipoCargo());
                ps.setString(2, c.getNombreCargo());
                ps.setString(3, c.getUsername());
                ps.setDate(4, (new java.sql.Date(new Date().getTime())));
                ps.addBatch();
            }
            ps.executeBatch();
            con.commit();
            con.setAutoCommit(true);
//            System.out.println("Time Taken=" + (System.currentTimeMillis() - start));
            MovilidadUtil.addSuccessMessage("Se crearon " + lstTipoCargo.size() + " cargos");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Se presentaron errores durante el procesamiento\n" + ex.getMessage()));
            System.out.println(ex.getMessage());
            Logger.getLogger(PrgEmpleadoKactus.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PrgEmpleadoKactus.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PrgEmpleadoKactus.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private Datos_Service getServiceKactus() {
        if (serviceKactus == null) {
            serviceKactus = new Datos_Service();
        }
        return serviceKactus;
    }

    private Datos getDatosKactus() {
        if (datosKactus == null) {
            datosKactus = getServiceKactus().getDatosPort();
        }
        return datosKactus;
    }

    private Empleado setEmpleado(kactus.ws.Empleado e) {
        Empleado empleado = new Empleado();
        empleado.setIdEmpleadoTipoIdentificacion(new EmpleadoTipoIdentificacion(2));
        empleado.setIdentificacion(e.getCodEmpl());
        empleado.setPathFoto("/rigel/empleado/" + e.getCodEmpl() + ".jpg");
        if (e.getCodCargo() != null
                && (e.getCodCargo().trim().equals("28")
                | e.getCodCargo().trim().equals("29")
                | e.getCodCargo().trim().equals("30")
                | e.getCodCargo().trim().equals("31"))) {
            if (e.getCodInte() != null) {
                empleado.setCodigoTm(new Integer(e.getCodInte().trim()));
            } else {
                empleado.setCodigoTm(new Integer(e.getCodEmpl().trim()));
            }
        }
        empleado.setNombres(e.getNomEmpl().toUpperCase().trim());
        empleado.setApellidos(e.getApeEmpl().toUpperCase().trim());
        empleado.setFechaNcto(Util.toDate(e.getFecNaci().substring(0, 9)));
        if (e.getDtoResi() != null) {
            empleado.setIdEmpleadoDepartamento(new EmpleadoDepartamento(new Integer(e.getDtoResi())));
        } else {
            empleado.setIdEmpleadoDepartamento(new EmpleadoDepartamento(11));
        }
        if (e.getMpiResi() != null) {
            empleado.setIdEmpleadoMunicipio(new EmpleadoMunicipio(new Integer(e.getMpiResi())));
        } else {
            empleado.setIdEmpleadoMunicipio(new EmpleadoMunicipio(151));
        }
        empleado.setDireccion(e.getDirResi() != null ? e.getDirResi().trim() : "SinDefinir");
        empleado.setIdOperacionPatio(new OperacionPatios(1));
        empleado.setTelefonoFijo(e.getTelResi() != null ? e.getTelResi() : "SinDefinir");
        empleado.setTelefonoMovil(e.getTelMovi() != null && e.getTelMovi().length() > 0 ? e.getTelMovi() : "SinDefinir");
        empleado.setEmailPersonal(e.getEeeMail() != null ? e.getEeeMail() : "SinDefinir");
        empleado.setEmailCorporativo(e.getBoxMail() != null ? e.getBoxMail() : "SinDefinir");
        empleado.setNombreContactoEmergencia("SinDefinir");
        empleado.setTelefonoContactoEmergencia(e.getTelEmer() != null ? e.getTelEmer() : "SinDefinir");
        empleado.setMovilContactoEmergencia(e.getTelEmer() != null ? e.getTelEmer() : "SinDefinir");
        empleado.setGenero(e.getSexEmpl() != null ? (e.getSexEmpl().toCharArray())[0] : 'M');
        empleado.setRh(e.getGruSang() != null ? e.getGruSang() : "N/A");
        EmpleadoTipoCargo etc = cargos.get(e.getCodCargo().trim());

        if (etc == null) {
            etc = new EmpleadoTipoCargo();
            etc.setIdEmpleadoTipoCargo(new Integer(e.getCodCargo().trim()));
            etc.setNombreCargo(e.getNomCargo().trim().toUpperCase());
            empleado.setIdEmpleadoCargo(etc);
            System.out.println(e.getCodCargo().trim() + " " + e.getNomCargo());
            lstTipoCargo.add(setEmpTipoCargo(e));
        } else {
            empleado.setIdEmpleadoCargo(etc);
//            System.out.println(etc.getNombreCargo());
        }
//        empleado.setIdEmpleadoCargo(new EmpleadoTipoCargo(new Integer(e.getCodCargo().trim())));
        empleado.setIdEmpleadoCargo(etc);
        empleado.setFechaIngreso(e.getFecCont() != null ? Util.toDate(e.getFecCont().substring(0, 9)) : Util.toDate("1900-01-01"));
        empleado.setIdEmpleadoEstado(e.getIndActi() != null
                ? e.getIndActi().equals("A") ? new EmpleadoEstado(1) : new EmpleadoEstado(2) : new EmpleadoEstado(2));
        empleado.setUsername(user.getUsername());
        empleado.setCreado(new Date());
        return empleado;
    }

    private EmpleadoTipoCargo setEmpTipoCargo(kactus.ws.Empleado e) {
        EmpleadoTipoCargo etc = new EmpleadoTipoCargo();
        etc.setIdEmpleadoTipoCargo(new Integer(e.getCodCargo().trim()));
        etc.setNombreCargo(e.getNomCargo().toUpperCase().trim());
        etc.setUsername(user.getUsername());
        etc.setCreado(new Date());
        return etc;
    }

    public List<Empleado> getLstEmpleado() {
        return lstEmpleado;
    }

    public boolean isFlag() {
        return flag;
    }

}
