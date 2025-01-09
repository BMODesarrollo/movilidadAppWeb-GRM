/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccidenteTestigoFacadeLocal;
import com.movilidad.model.AccProfesion;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteTestigo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
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
@Named(value = "accidenteTestigoJSF")
@ViewScoped
public class AccidenteTestigoJSF implements Serializable {

    @EJB
    private AccidenteTestigoFacadeLocal accidenteTestigoFacadeLocal;

    private AccidenteTestigo accidenteTestigo;

    private List<AccidenteTestigo> listAccidenteTestigo;

//    private HashMap<Integer, AccidenteTestigo> mapAccidenteTestigo = new HashMap<Integer, AccidenteTestigo>();

    private int i_idAccidente;
    private int i_idAccProfesion;

    private boolean b_flag;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @Inject
    private AccidenteJSF accidenteJSF;

    public AccidenteTestigoJSF() {
    }

    @PostConstruct
    public void init() {
        i_idAccProfesion = 0;
        b_flag = true;
        i_idAccidente = accidenteJSF.compartirIdAccidente();
//        List<AccidenteTestigo> findAll = accidenteTestigoFacadeLocal.findAll();
//        for (AccidenteTestigo at : findAll) {
//            mapAccidenteTestigo.put(at.getIdAccidenteTestigo(), at);
//        }
    }

    public void guardar() {
        try {
            if (i_idAccidente != 0) {
                if (accidenteTestigo != null) {
                    cargarObjetos();
                    accidenteTestigo.setIdAccidente(new Accidente(i_idAccidente));
                    accidenteTestigo.setCreado(new Date());
                    accidenteTestigo.setModificado(new Date());
                    accidenteTestigo.setUsername(user.getUsername());
                    accidenteTestigo.setEstadoReg(0);
                    accidenteTestigoFacadeLocal.create(accidenteTestigo);
                    MovilidadUtil.addSuccessMessage("Se guardó el Accidente Testigo correctamente");
                    reset();
                }
                return;
            }
            MovilidadUtil.addErrorMessage("No se puede realizar esta acción, no se seleccionó un accidente");
        } catch (Exception e) {

        }
    }

    public void prepareGuardar() {
        accidenteTestigo = new AccidenteTestigo();
    }

    public void editar() {
        try {
            if (accidenteTestigo != null) {
                cargarObjetos();
                accidenteTestigoFacadeLocal.edit(accidenteTestigo);
                MovilidadUtil.addSuccessMessage("Se actualizó el Accidente Testigo correctamente");
                reset();
            }
        } catch (Exception e) {
            System.out.println("Error en editar Accidente Testigo");
        }
    }

    public void eliminarLista(AccidenteTestigo at) {
        try {
            at.setEstadoReg(1);
            accidenteTestigoFacadeLocal.edit(at);
            MovilidadUtil.addSuccessMessage("Se elimino el Accidente Testigo de la lista");
            reset();
        } catch (Exception e) {
            System.out.println("Error en editar Accidente Testigo");
        }
    }

    public void prepareEditar(AccidenteTestigo at) {
        accidenteTestigo = at;
        b_flag = false;
        if (at.getIdAccProfesion() != null) {
            i_idAccProfesion = at.getIdAccProfesion().getIdAccProfesion();
        }
    }

    void cargarObjetos() {
        if (i_idAccProfesion != 0) {
            accidenteTestigo.setIdAccProfesion(new AccProfesion(i_idAccProfesion));
        }
    }

    public void reset() {
        accidenteTestigo = null;
        i_idAccProfesion = 0;
        b_flag = true;
    }

    public AccidenteTestigo getAccidenteTestigo() {
        return accidenteTestigo;
    }

    public void setAccidenteTestigo(AccidenteTestigo accidenteTestigo) {
        this.accidenteTestigo = accidenteTestigo;
    }

    public List<AccidenteTestigo> getListAccidenteTestigo() {
        i_idAccidente = accidenteJSF.compartirIdAccidente();
        listAccidenteTestigo = accidenteTestigoFacadeLocal.estadoReg(i_idAccidente);
        return listAccidenteTestigo;
    }

    public int getI_idAccidente() {
        return i_idAccidente;
    }

    public void setI_idAccidente(int i_idAccidente) {
        this.i_idAccidente = i_idAccidente;
    }

    public int getI_idAccProfesion() {
        return i_idAccProfesion;
    }

    public void setI_idAccProfesion(int i_idAccProfesion) {
        this.i_idAccProfesion = i_idAccProfesion;
    }

    public boolean isB_flag() {
        return b_flag;
    }

    @FacesConverter("testigoConverter")
    public static class AccidenteTestigoConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AccidenteTestigoJSF controller = (AccidenteTestigoJSF) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "accidenteTestigoJSF");
            return controller.accidenteTestigoFacadeLocal.find(getKey(value));
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
            if (object instanceof AccidenteTestigo) {
                AccidenteTestigo o = (AccidenteTestigo) object;
                return getStringKey(o.getIdAccidenteTestigo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AccidenteTestigo.class.getName()});
                return null;
            }
        }

    }
}
