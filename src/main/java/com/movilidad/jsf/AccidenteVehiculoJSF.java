/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccTipoVehiculoFacadeLocal;
import com.movilidad.ejb.AccidenteFacadeLocal;
import com.movilidad.ejb.AccidenteVehiculoFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.AccEmpresaOperadora;
import com.movilidad.model.AccInmovilizado;
import com.movilidad.model.AccTipoVehiculo;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteVehiculo;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
@Named(value = "accidenteVehiculoJSF")
@ViewScoped
public class AccidenteVehiculoJSF implements Serializable {

    @EJB
    private AccidenteVehiculoFacadeLocal accidenteVehiculoFacadeLocal;
    @EJB
    private AccTipoVehiculoFacadeLocal accTipoVehiculoFacadeLocal;
    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;
    @EJB
    private AccidenteFacadeLocal accidenteFacadeLocal;

    private AccidenteVehiculo accidenteVehiculo;
    private Accidente o_accidente;

    private List<AccidenteVehiculo> listAccidenteVehiculo;

//    private HashMap<Integer, AccidenteVehiculo> mapAccidenteVehiculo = new HashMap<Integer, AccidenteVehiculo>();
    private int i_idAccEmpresaOperadora = 0;
    private int i_idAccInmovilizado = 0;
    private int i_idAccTipoVehiculo = 0;
    private int i_idAccidente;
    private String c_codigoVehiculo = "";

    private boolean b_control = false;
    private boolean b_flag;

    @Inject
    private AccidenteJSF accidenteJSF;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccidenteVehiculoJSF() {
    }

    @PostConstruct
    public void init() {
        o_accidente = null;
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        b_flag = true;
//        List<AccidenteVehiculo> findAll = accidenteVehiculoFacadeLocal.findAll();
//        for (AccidenteVehiculo av : findAll) {
//            mapAccidenteVehiculo.put(av.getIdAccidenteVehiculo(), av);
//        }
    }

    public void guardar() {
        try {
            if (i_idAccidente != 0) {
                if (accidenteVehiculo != null) {
                    cargarObjetos();
                    if (b_control) {
                        b_control = false;
                        return;
                    }
                    if (o_accidente != null) {
                        accidenteFacadeLocal.edit(o_accidente);
                    }
                    accidenteVehiculo.setIdAccidente(new Accidente(i_idAccidente));
                    accidenteVehiculo.setCreado(new Date());
                    accidenteVehiculo.setModificado(new Date());
                    accidenteVehiculo.setUsername(user.getUsername());
                    accidenteVehiculo.setEstadoReg(0);
                    accidenteVehiculoFacadeLocal.create(accidenteVehiculo);
                    MovilidadUtil.addSuccessMessage("Se guardó el Accidente Vehículo correctamente");
                    reset();
                }
                return;
            }
            MovilidadUtil.addErrorMessage("No se puede realizar esta acción, no se seleccionó un accidente");
        } catch (Exception e) {
            System.out.println("Error en guardar Accidente Vehiculo");
        }
    }

    public void editar() {
        try {
            if (accidenteVehiculo != null) {
                cargarObjetos();
                if (b_control) {
                    b_control = false;
                    return;
                }
                if (o_accidente != null) {
                    accidenteFacadeLocal.edit(o_accidente);
                }
                accidenteVehiculoFacadeLocal.edit(accidenteVehiculo);
                MovilidadUtil.addSuccessMessage("Se actualizó el Accidente Vehículo correctamente");
                reset();
            }
        } catch (Exception e) {
            System.out.println("Error en editar Accidente Vehiculo");
        }
    }

    public void eliminarLista(AccidenteVehiculo av) {
        try {
            av.setEstadoReg(1);
            accidenteVehiculoFacadeLocal.edit(av);
            MovilidadUtil.addSuccessMessage("Se elimino el Accidente Vehículo de la lista");
            reset();
        } catch (Exception e) {
            System.out.println("Error en editar Accidente Vehiculo");
        }
    }

    public void prepareGuardar() {
        accidenteVehiculo = new AccidenteVehiculo();
    }

    public void prepareEditar(AccidenteVehiculo av) {
        b_flag = false;
        accidenteVehiculo = av;
        i_idAccEmpresaOperadora = accidenteVehiculo.getIdAccEmpresaOperadora() != null ? accidenteVehiculo.getIdAccEmpresaOperadora().getIdAccEmpresaOperadora() : 0;
        i_idAccInmovilizado = accidenteVehiculo.getIdAccInmovilizado() != null ? accidenteVehiculo.getIdAccInmovilizado().getIdAccInmovilizado() : 0;
        i_idAccTipoVehiculo = accidenteVehiculo.getIdAccTipoVehiculo() != null ? accidenteVehiculo.getIdAccTipoVehiculo().getIdAccTipoVehiculo() : 0;
        c_codigoVehiculo = av.getCodigoVehiculo();
    }

    public void reset() {
        accidenteVehiculo = null;
        i_idAccEmpresaOperadora = 0;
        i_idAccInmovilizado = 0;
        i_idAccTipoVehiculo = 0;
        c_codigoVehiculo = "";
        b_flag = true;
    }

    public void buscarVehiculo() {
        if (Util.isStringNullOrEmpty(c_codigoVehiculo)) {
            MovilidadUtil.addErrorMessage("No se encontraron registros");
            return;
        }
        Vehiculo vehiculoCodigo = vehiculoFacadeLocal.getVehiculoCodigo(c_codigoVehiculo);
        if (vehiculoCodigo == null) {
            MovilidadUtil.addErrorMessage("No se encontraron registros");
            return;
        }
        c_codigoVehiculo = vehiculoCodigo.getCodigo();
        cargarVehiculo(vehiculoCodigo);
        cargarVeiculoAccidente(vehiculoCodigo);
        MovilidadUtil.addSuccessMessage("Código correcto para vehículo");
    }

    void cargarVehiculo(Vehiculo vehiculo) {
//        if (vehiculo.getModelo() != null) {
//            accidenteVehiculo.setModelo(vehiculo.getModelo());
//        }
        if (!Util.isStringNullOrEmpty(vehiculo.getPlaca())) {
            accidenteVehiculo.setPlaca(vehiculo.getPlaca());
        }
        if (!Util.isStringNullOrEmpty(vehiculo.getCodigo())) {
            accidenteVehiculo.setCodigoVehiculo(vehiculo.getCodigo());
        }
        if (vehiculo.getIdVehiculoTipo() != null) {
            List<AccTipoVehiculo> list = accTipoVehiculoFacadeLocal.estadoReg();
            Optional<AccTipoVehiculo> op = list.stream().filter(atv -> {
                return atv.getTipoVehiculo().equalsIgnoreCase(vehiculo.getIdVehiculoTipo().getNombreTipoVehiculo());
            }).findFirst();
            if (op.isPresent()) {
                accidenteVehiculo.setIdAccTipoVehiculo(op.get());
            }
        }
    }

    void cargarObjetos() {
        if (i_idAccEmpresaOperadora != 0) {
            accidenteVehiculo.setIdAccEmpresaOperadora(new AccEmpresaOperadora(i_idAccEmpresaOperadora));
        }
        if (i_idAccInmovilizado != 0) {
            accidenteVehiculo.setIdAccInmovilizado(new AccInmovilizado(i_idAccInmovilizado));
        }
//            } else {
//                MovilidadUtil.addErrorMessage("Acc Inmovilizado es requerido");
//                b_control = true;
//            }
        if (i_idAccTipoVehiculo != 0) {
            accidenteVehiculo.setIdAccTipoVehiculo(new AccTipoVehiculo(i_idAccTipoVehiculo));
        }
//            } else {
//                MovilidadUtil.addErrorMessage("Acc Tipo de Vehículo es requerido");
//                b_control = true;
//            }
    }

    public AccidenteVehiculo guardarPorAccidente(Accidente accidente, Vehiculo v) {
        if (v == null) {
            return null;
        }
        accidenteVehiculo = new AccidenteVehiculo();
        cargarVehiculo(v);
        accidenteVehiculo.setIdAccidente(accidente);
        accidenteVehiculo.setUsername(accidente.getUsername());
        accidenteVehiculo.setCreado(accidente.getCreado());
        accidenteVehiculo.setEstadoReg(0);
        accidenteVehiculoFacadeLocal.create(accidenteVehiculo);
        return accidenteVehiculo;
    }

    public AccidenteVehiculo actualizarPorAccidente(Accidente accidente, String codVehiculo) {
        try {
            accidenteVehiculo = accidenteVehiculoFacadeLocal.findByVehiculo(accidente.getIdAccidente(), codVehiculo);

            if (accidenteVehiculo != null) {
                accidenteVehiculo.setIdAccidente(accidente);

                if (accidente.getIdVehiculo() != null) {
                    cargarVehiculo(accidente.getIdVehiculo());
                }
                accidenteVehiculoFacadeLocal.edit(accidenteVehiculo);
            }
            return accidenteVehiculo;
        } catch (Exception e) {
            return null;
        }
    }

    void cargarVeiculoAccidente(Vehiculo vehiculo) { //el primer conductor responsable se registra en la tabla accidente
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        if (i_idAccidente == 0) {
            return;
        }
        o_accidente = accidenteFacadeLocal.find(i_idAccidente);
        if (o_accidente == null) {
            return;
        }
        if (listAccidenteVehiculo != null) {
            o_accidente.setIdVehiculo(vehiculo);
            return;
        }
        if (listAccidenteVehiculo.isEmpty()) {
            o_accidente.setIdVehiculo(vehiculo);
            return;
        }
        AccidenteVehiculo avAux = listAccidenteVehiculo.get(0);
        if (accidenteVehiculo.getIdAccidenteVehiculo() != null) {
            if (accidenteVehiculo.getIdAccidenteVehiculo().equals(avAux.getIdAccidenteVehiculo())) {
                o_accidente.setIdVehiculo(vehiculo);
            }
        }
    }

    public AccidenteVehiculo getAccidenteVehiculo() {
        return accidenteVehiculo;
    }

    public void setAccidenteVehiculo(AccidenteVehiculo accidenteVehiculo) {
        this.accidenteVehiculo = accidenteVehiculo;
    }

    public List<AccidenteVehiculo> getListAccidenteVehiculo() {
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        listAccidenteVehiculo = accidenteVehiculoFacadeLocal.estadoReg(i_idAccidente);
        return listAccidenteVehiculo;
    }

    public int getI_idAccEmpresaOperadora() {
        return i_idAccEmpresaOperadora;
    }

    public void setI_idAccEmpresaOperadora(int i_idAccEmpresaOperadora) {
        this.i_idAccEmpresaOperadora = i_idAccEmpresaOperadora;
    }

    public int getI_idAccInmovilizado() {
        return i_idAccInmovilizado;
    }

    public void setI_idAccInmovilizado(int i_idAccInmovilizado) {
        this.i_idAccInmovilizado = i_idAccInmovilizado;
    }

    public int getI_idAccTipoVehiculo() {
        return i_idAccTipoVehiculo;
    }

    public void setI_idAccTipoVehiculo(int i_idAccTipoVehiculo) {
        this.i_idAccTipoVehiculo = i_idAccTipoVehiculo;
    }

    public int getI_idAccidente() {
        return i_idAccidente;
    }

    public void setI_idAccidente(int i_idAccidente) {
        this.i_idAccidente = i_idAccidente;
    }

    public String getC_codigoVehiculo() {
        return c_codigoVehiculo;
    }

    public void setC_codigoVehiculo(String c_codigoVehiculo) {
        this.c_codigoVehiculo = c_codigoVehiculo;
    }

    public boolean isB_flag() {
        return b_flag;
    }

    @FacesConverter("vehiculoConverter")
    public static class AccidenteVehiculoConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AccidenteVehiculoJSF controller = (AccidenteVehiculoJSF) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "accidenteVehiculoJSF");
            return controller.accidenteVehiculoFacadeLocal.find(getKey(value));
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
            if (object instanceof AccidenteVehiculo) {
                AccidenteVehiculo o = (AccidenteVehiculo) object;
                return getStringKey(o.getIdAccidenteVehiculo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AccidenteVehiculo.class.getName()});
                return null;
            }
        }

    }
}
