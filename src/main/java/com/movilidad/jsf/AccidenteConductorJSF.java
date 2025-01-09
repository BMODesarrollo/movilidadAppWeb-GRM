/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccidenteConductorFacadeLocal;
import com.movilidad.ejb.AccidenteFacadeLocal;
import com.movilidad.ejb.AccidenteVehiculoFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.model.AccCondicion;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteConductor;
import com.movilidad.model.AccidenteVehiculo;
import com.movilidad.model.Empleado;
import com.movilidad.model.PrgSercon;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "accidenteConductorJSF")
@ViewScoped
public class AccidenteConductorJSF implements Serializable {

    @EJB
    private AccidenteConductorFacadeLocal accidenteConductorFacadeLocal;
    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    private AccidenteVehiculoFacadeLocal accidenteVehiculoFacadeLocal;
    @EJB
    private AccidenteFacadeLocal accidenteFacadeLocal;
    @EJB
    private PrgSerconFacadeLocal prgSerconFacadeLocal;

    private AccidenteConductor accidenteConductor;
    private Accidente o_accidente;

    private List<AccidenteConductor> listAccidenteConductor;
    private List<AccidenteVehiculo> listAccidenteVehiculo;

//    private HashMap<Integer, AccidenteConductor> mapAccidenteConductor = new HashMap<>();
    private int i_idAccidente;
    private int i_idAccidenteVehiculo;
    private int i_idAccCondicion;
    private Integer i_codigoOpe;
    private boolean b_control;
    private boolean b_flag;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @Inject
    private AccidenteJSF accidenteJSF;
    @Inject
    private AccidenteVictimaJSF accidenteVictimaJSF;

    public AccidenteConductorJSF() {
    }

    @PostConstruct
    public void init() {
        o_accidente = null;
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        b_flag = true;
        i_codigoOpe = null;
//        List<AccidenteConductor> findAll = accidenteConductorFacadeLocal.findAll();
//        for (AccidenteConductor ac : findAll) {
//            mapAccidenteConductor.put(ac.getIdAccidenteConductor(), ac);
//        }
        i_idAccidenteVehiculo = 0;
        i_idAccCondicion = 0;
        b_control = false;
    }

    public void guardar() {
        if (i_idAccidente == 0) {
            MovilidadUtil.addErrorMessage("No se puede realizar esta acción, no se seleccionó un accidente");
            return;
        }
        if (accidenteConductor == null) {
            MovilidadUtil.addErrorMessage("Error");
            return;
        }
        cargarObjetos();
        if (b_control) {
            b_control = false;
            return;
        }
        if (o_accidente != null) {
            accidenteFacadeLocal.edit(o_accidente);
            if (listAccidenteConductor.isEmpty()) {
                accidenteJSF.alertaReincidenciaAcc(o_accidente, 0);
            }
        }
        accidenteConductor.setIdAccidente(new Accidente(i_idAccidente));
        accidenteConductor.setCreado(new Date());
        accidenteConductor.setModificado(new Date());
        accidenteConductor.setUsername(user.getUsername());
        accidenteConductor.setEstadoReg(0);
        accidenteConductorFacadeLocal.create(accidenteConductor);
        crearVictimaFromConductor();
        MovilidadUtil.addSuccessMessage("Se guardó el Accidente Conductor correctamente");
        reset();
    }

    public void editar() {
        if (i_idAccidente == 0) {
            MovilidadUtil.addErrorMessage("No se puede realizar esta acción, no se seleccionó un accidente");
            return;
        }
        if (accidenteConductor == null) {
            MovilidadUtil.addErrorMessage("Error");
            return;
        }
        cargarObjetos();
        if (b_control) {
            b_control = false;
            return;
        }
        if (o_accidente != null) {
            accidenteFacadeLocal.edit(o_accidente);
        }
        accidenteConductorFacadeLocal.edit(accidenteConductor);
        crearVictimaFromConductor();
        MovilidadUtil.addSuccessMessage("Se actualizó el Accidente Conductor correctamente");
        reset();
    }

    public void eliminarLista(AccidenteConductor ac) {
        try {
            ac.setEstadoReg(1);
            accidenteConductorFacadeLocal.edit(ac);
            MovilidadUtil.addSuccessMessage("Se elimino el Accidente Conductor de la lista");
            reset();
        } catch (Exception e) {
        }
    }

    public void prepareGuardar() {
        accidenteConductor = new AccidenteConductor();
    }

    public void prepareEditar(AccidenteConductor ac) {
        accidenteConductor = ac;
        b_flag = false;
        i_idAccCondicion = accidenteConductor.getIdAccCondicion() != null ? accidenteConductor.getIdAccCondicion().getIdAccCondicion() : 0;
        i_idAccidenteVehiculo = accidenteConductor.getIdAccidenteVehiculo() != null ? accidenteConductor.getIdAccidenteVehiculo().getIdAccidenteVehiculo() : 0;
        // logica aplica solo para green movil
        if (!Util.isStringNullOrEmpty(ac.getCentroAsistencial())) {
            i_codigoOpe = Integer.parseInt(ac.getCentroAsistencial());
        }
    }

    public void reset() {
        accidenteConductor = null;
        i_idAccidenteVehiculo = 0;
        i_idAccCondicion = 0;
        i_codigoOpe = null;
        b_flag = true;
    }

    public void buscarOperador() {
        if (i_codigoOpe == null) {
            MovilidadUtil.addErrorMessage("No se encontraron registros");
            return;
        }
        Empleado empleado = empleadoFacadeLocal.getEmpleadoCodigoTM(i_codigoOpe);
        if (empleado != null) {
            cargarOperador(empleado);
            cargarEmpleadoAccidente(empleado);
            MovilidadUtil.addSuccessMessage("Código Tm correcto para Operador");
        } else {
            MovilidadUtil.addErrorMessage("No se encontraron registros");
        }
    }

    void cargarOperador(Empleado e) {
        if (e.getIdentificacion() != null) {
            accidenteConductor.setCedula(e.getIdentificacion());
        }
        if (e.getNombres() != null) {
            accidenteConductor.setNombres(e.getNombres());
        }
        if (e.getApellidos() != null) {
            accidenteConductor.setApellidos(e.getApellidos());
        }
        if (e.getTelefonoFijo() != null) {
            accidenteConductor.setTelefono(e.getTelefonoFijo());
        }
        if (e.getTelefonoMovil() != null) {
            accidenteConductor.setCelular(e.getTelefonoMovil());
        }
        if (e.getDireccion() != null) {
            accidenteConductor.setDireccion(e.getDireccion());
        }
        if (e.getFechaNcto() != null) {
            accidenteConductor.setFechaNcto(e.getFechaNcto());
        }
        if (e.getGenero() != null) {
            accidenteConductor.setGenero(e.getGenero());
        }
        if (e.getCodigoTm() != null) {
            accidenteConductor.setCentroAsistencial(e.getCodigoTm() + "");
        }
    }

    void cargarObjetos() {
        try {
            if (i_idAccCondicion != 0) {
                accidenteConductor.setIdAccCondicion(new AccCondicion(i_idAccCondicion));
            }
            if (i_idAccidenteVehiculo != 0) {
                accidenteConductor.setIdAccidenteVehiculo(new AccidenteVehiculo(i_idAccidenteVehiculo));
            } else {
                MovilidadUtil.addErrorMessage("Debe seleccionar un vehículo asociado a este accidente");
                b_control = true;
            }
        } catch (Exception e) {
            System.out.println("Error en cargar objetos");
        }
    }

    public void crearVictimaFromConductor() {
        if (accidenteConductor.getVictima() != null && accidenteConductor.getVictima().equals(1)) {
            accidenteVictimaJSF.createVictimaFromConductor(accidenteConductor);
        }
    }

    public void guardarPorAccidente(Accidente accidente, AccidenteVehiculo guardarPorAccidente, Empleado e) {
        if (guardarPorAccidente == null) {
            return;
        }
        if (e == null) {
            return;
        }
        accidenteConductor = new AccidenteConductor();
        cargarOperador(e);
        accidenteConductor.setIdAccidenteVehiculo(guardarPorAccidente);
        accidenteConductor.setIdAccidente(accidente);
        accidenteConductor.setUsername(accidente.getUsername());
        accidenteConductor.setCreado(accidente.getCreado());
        accidenteConductor.setEstadoReg(0);
        accidenteConductorFacadeLocal.create(accidenteConductor);
    }

    public void actualizarPorAccidente(Accidente accidente, AccidenteVehiculo accidenteVehiculo, String cedula) {
        try {
            accidenteConductor = accidenteConductorFacadeLocal.findByVehiculo(accidente.getIdAccidente(), accidenteVehiculo.getIdAccidenteVehiculo(), cedula);

            if (accidenteConductor != null) {
                accidenteConductor.setIdAccidente(accidente);

                if (accidente.getIdEmpleado() != null) {
                    cargarOperador(accidente.getIdEmpleado());
                }
                accidenteConductorFacadeLocal.edit(accidenteConductor);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void cargarEmpleadoAccidente(Empleado empleado) { //el primer conductor responsable se registra en la tabla accidente
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        if (i_idAccidente == 0) {
            return;
        }
        o_accidente = accidenteFacadeLocal.find(i_idAccidente);
        if (o_accidente == null) {
            return;
        }
        if (empleado.getFechaIngreso() != null) {
            int i_mesesOperando = cargarMesesOperando(empleado.getFechaIngreso(), o_accidente.getFechaAcc());
            o_accidente.setMesesOperando(i_mesesOperando);
            o_accidente.setFechaIngresoEmpleado(empleado.getFechaIngreso());
        }
        if (listAccidenteConductor == null) {
            o_accidente.setIdEmpleado(empleado);
            datosPrgSerconAccidente(o_accidente);
            return;
        }
        if (listAccidenteConductor.isEmpty()) {
            o_accidente.setIdEmpleado(empleado);
            datosPrgSerconAccidente(o_accidente);
            return;
        }
        AccidenteConductor acAux = listAccidenteConductor.get(0);
        if (accidenteConductor.getIdAccidenteConductor() != null) {
            if (accidenteConductor.getIdAccidenteConductor().equals(acAux.getIdAccidenteConductor())) {
                o_accidente.setIdEmpleado(empleado);
                datosPrgSerconAccidente(o_accidente);
            }
        }
    }

    private void datosPrgSerconAccidente(Accidente a) {
        if (a.getIdEmpleado() == null) {
            return;
        }

        PrgSercon prgSerconActual = prgSerconFacadeLocal
                .getByIdEmpleadoAndFecha(a.getIdEmpleado().getIdEmpleado(),
                        a.getFechaAcc());
        PrgSercon prgSerconPass = prgSerconFacadeLocal
                .getByIdEmpleadoAndFecha(a.getIdEmpleado().getIdEmpleado(),
                        MovilidadUtil.sumarDias(a.getFechaAcc(), -1));

        a.setIdPrgSerconActual(prgSerconActual);
        a.setIdPrgSerconPass(prgSerconPass);
    }

    int cargarMesesOperando(Date fechaEmp, Date fechaAcc) {
        Calendar ingresoEmp = Calendar.getInstance();
        Calendar fechaAccidente = Calendar.getInstance();
        ingresoEmp.setTime(fechaEmp); //fecha de ingreso del empleado
        fechaAccidente.setTime(fechaAcc); // fecha actual del accidente
        int difA = fechaAccidente.get(Calendar.YEAR) - ingresoEmp.get(Calendar.YEAR);
        int difM = difA * 12 + fechaAccidente.get(Calendar.MONTH) - ingresoEmp.get(Calendar.MONTH);
        return difM;
    }

    public AccidenteConductor getAccidenteConductor() {
        return accidenteConductor;
    }

    public void setAccidenteConductor(AccidenteConductor accidenteConductor) {
        this.accidenteConductor = accidenteConductor;
    }

    public List<AccidenteConductor> getListAccidenteConductor() {
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        listAccidenteConductor = accidenteConductorFacadeLocal.estadoReg(i_idAccidente);
        return listAccidenteConductor;
    }

    public List<AccidenteVehiculo> getListAccidenteVehiculo() {
        listAccidenteVehiculo = accidenteVehiculoFacadeLocal.estadoReg(i_idAccidente);
        return listAccidenteVehiculo;
    }

    public int getI_idAccidente() {
        return i_idAccidente;
    }

    public void setI_idAccidente(int i_idAccidente) {
        this.i_idAccidente = i_idAccidente;
    }

    public int getI_idAccidenteVehiculo() {
        return i_idAccidenteVehiculo;
    }

    public void setI_idAccidenteVehiculo(int i_idAccidenteVehiculo) {
        this.i_idAccidenteVehiculo = i_idAccidenteVehiculo;
    }

    public int getI_idAccCondicion() {
        return i_idAccCondicion;
    }

    public void setI_idAccCondicion(int i_idAccCondicion) {
        this.i_idAccCondicion = i_idAccCondicion;
    }

    public Integer getI_codigoOpe() {
        return i_codigoOpe;
    }

    public void setI_codigoOpe(Integer i_codigoOpe) {
        this.i_codigoOpe = i_codigoOpe;
    }

    public boolean isB_flag() {
        return b_flag;
    }

    @FacesConverter("testigoConverter")
    public static class AccidenteConductorConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AccidenteConductorJSF controller = (AccidenteConductorJSF) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "accidenteConductorJSF");
            return controller.accidenteConductorFacadeLocal.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof AccidenteConductor) {
                AccidenteConductor o = (AccidenteConductor) object;
                return getStringKey(o.getIdAccidenteConductor());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AccidenteConductor.class.getName()});
                return null;
            }
        }

    }

}
