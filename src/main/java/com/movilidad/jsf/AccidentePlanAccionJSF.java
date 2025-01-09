/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccPlanAccionFacadeLocal;
import com.movilidad.ejb.AccidenteCalificacionFacadeLocal;
import com.movilidad.ejb.AccidenteFacadeLocal;
import com.movilidad.ejb.AccidentePlanAccionFacadeLocal;
import com.movilidad.model.AccPlanAccion;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteCalificacion;
import com.movilidad.model.AccidentePlanAccion;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author cesar
 */
@Named(value = "accidentePlanAccionJSF")
@ViewScoped
public class AccidentePlanAccionJSF implements Serializable {

    @EJB
    private AccidentePlanAccionFacadeLocal accidentePlanAccionFacadeLocal;
    @EJB
    private AccPlanAccionFacadeLocal accPlanAccionFacadeLocal;
    @EJB
    private AccidenteCalificacionFacadeLocal accidenteCalificacionFacadeLocal;
    @EJB
    private AccidenteFacadeLocal accidenteFacadeLocal;

    @Inject
    private AccidenteJSF accidenteJSF;

    private AccidenteCalificacion accidenteCalificacion;
    private List<AccidentePlanAccion> listAccidentePlanAccion;
    private List<AccidenteCalificacion> listAccidenteCalificacion;
    private DualListModel<AccPlanAccion> dualListPlanAccion;
    private HashMap<Integer, AccPlanAccion> mapAccPlanAccion;
    private List<String> planAccionSelect;

    // greenMovel
    private HashMap<String, AccPlanAccion> mapAccPlanAccionGM;

    private Date dFecha;
    private Integer iPin;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccidentePlanAccionJSF() {
    }

    @PostConstruct
    public void init() {
        accidenteCalificacion = null;
        listAccidenteCalificacion = new ArrayList<>();
        dualListPlanAccion = null;
        mapAccPlanAccion = new HashMap<>();
        mapAccPlanAccionGM = new HashMap<>();
        dFecha = new Date();
        iPin = null;
//        selectedPlanAccion = new ArrayList<String>();
    }

    public void selectEventAccClas(SelectEvent event) {
        accidenteCalificacion = new AccidenteCalificacion();
        accidenteCalificacion = (AccidenteCalificacion) event.getObject();
        if (accidenteCalificacion.getCalificado() != null && accidenteCalificacion.getCalificado() != 1) {
            accidenteCalificacion = null;
            dualListPlanAccion = null;
            MovilidadUtil.addErrorMessage("Accidente aún no ha sido valorado.");
            return;
        }
        cargarDualList();
        cargarMap();
    }

    public void guardar() {
        try {
            AccidentePlanAccion accidentePlanAccion;
            if (accidenteCalificacion != null) {
                List<AccPlanAccion> listGuardar = guardarPlanAccion();
                for (AccPlanAccion apa : listGuardar) {
                    accidentePlanAccion = new AccidentePlanAccion();
                    accidentePlanAccion.setCreado(new Date());
                    accidentePlanAccion.setEstadoReg(0);
                    accidentePlanAccion.setIdAccPlanAccion(apa);
                    accidentePlanAccion.setUsername(user.getUsername());
                    accidentePlanAccion.setIdAccidente(accidenteCalificacion.getIdAccidente());
                    accidentePlanAccionFacadeLocal.create(accidentePlanAccion);
                }
            }
            MovilidadUtil.addSuccessMessage("Plan de acción registrado con éxito");
            accidenteCalificacion = null;
            dualListPlanAccion = null;
        } catch (Exception e) {
        }
    }

    public void buscarCasoPorPin() {
        listAccidenteCalificacion = accidenteCalificacionFacadeLocal.findByPin(dFecha, iPin, 1);
        accidenteCalificacion = null;
        dualListPlanAccion = null;
    }

    void cargarDualList() {
        List<AccidentePlanAccion> finAccPlan = accidentePlanAccionFacadeLocal.findAllEstadoReg(accidenteCalificacion.getIdAccidente().getIdAccidente());
        List<AccPlanAccion> findAll = accPlanAccionFacadeLocal.findAll();
        if (finAccPlan != null && !finAccPlan.isEmpty()) {
            List<AccPlanAccion> listApaAuxContiene = new ArrayList<>(); //todos los plan que ya contiene el accidente
            List<AccPlanAccion> listApaAux = new ArrayList<>(findAll); // para quitar los que tiene accidente a acc_plan_accion
            for (AccidentePlanAccion apa : finAccPlan) {
                listApaAuxContiene.add(apa.getIdAccPlanAccion());
            }
            for (AccPlanAccion apa : findAll) {
                for (AccPlanAccion apaCon : listApaAuxContiene) {
                    if (apa.getIdAccPlanAccion().equals(apaCon.getIdAccPlanAccion())) {
                        listApaAux.remove(apa);
                    }
                }
            }
            dualListPlanAccion = new DualListModel<>(listApaAux, listApaAuxContiene);
        } else {
            dualListPlanAccion = new DualListModel<>(findAll, new ArrayList<AccPlanAccion>());
        }
    }

    List<AccPlanAccion> guardarPlanAccion() {
        List<AccPlanAccion> target = dualListPlanAccion.getTarget();
        List<AccPlanAccion> targetAux = new ArrayList<>(target);
        List<AccidentePlanAccion> finAccPlan = accidentePlanAccionFacadeLocal.findAllEstadoReg(accidenteCalificacion.getIdAccidente().getIdAccidente());
        boolean ok = true;
        if (finAccPlan != null) {
            for (AccidentePlanAccion acpa : finAccPlan) {
                for (AccPlanAccion apaTarg : target) {
                    if (acpa.getIdAccPlanAccion().getIdAccPlanAccion().equals(apaTarg.getIdAccPlanAccion())) {
                        ok = false;
                        targetAux.remove(apaTarg);
                    }
                }
                if (ok) {
                    acpa.setEstadoReg(1);
                    accidentePlanAccionFacadeLocal.edit(acpa);
                }
            }
        }
        return targetAux;
    }

    void cargarMap() {
        mapAccPlanAccion.clear();
        accPlanAccionFacadeLocal.findAll().forEach((apa) -> {
            mapAccPlanAccion.put(apa.getIdAccPlanAccion(), apa);
            mapAccPlanAccionGM.put(apa.getIdAccPlanAccion() + "", apa);
        });
    }

    /*
    * Plan accion greem movil
     */
    public void nuevo() {
        int idAcc = accidenteJSF.compartirIdAccidente();
        if (idAcc == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un accidente");
            return;
        }
        cargarMap();
        Accidente acc = accidenteFacadeLocal.find(idAcc);
        List<AccidentePlanAccion> accidentePlanAccionList = acc.getAccidentePlanAccionList();
        if (accidentePlanAccionList != null) {
            planAccionSelect = new ArrayList<>(accidentePlanAccionList
                    .stream()
                    .map(apcl -> apcl.getIdAccPlanAccion().getIdAccPlanAccion() + "")
                    .collect(Collectors.toList()));
        }
    }

    public void guardarPlanAccionGM() {
        int idAcc = accidenteJSF.compartirIdAccidente();
        if (idAcc == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un accidente");
            return;
        }
        if (planAccionSelect.isEmpty()) {
            MovilidadUtil.addErrorMessage("Debe seleccionar el plan de acción");
            return;
        }
        Accidente acc = accidenteFacadeLocal.find(idAcc);
        List<AccidentePlanAccion> accidentePlanAccionList = acc.getAccidentePlanAccionList();
        if (accidentePlanAccionList != null && !accidentePlanAccionList.isEmpty()) {

            /*
            * Eliminar planes de accion que se hayan deseleccionado
             */
            List<AccidentePlanAccion> accidentePlanAccionListAux = new ArrayList<>(accidentePlanAccionList);
            accidentePlanAccionListAux
                    .stream()
                    .filter(apal -> !planAccionSelect.contains(apal.getIdAccPlanAccion().getIdAccPlanAccion() + ""))
                    .forEach(apal -> accidentePlanAccionList.remove(apal));

            /*
            * ids plan accion que se encuentran en la bd
             */
            List<String> planAccionBd = accidentePlanAccionListAux
                    .stream()
                    .map(apal -> apal.getIdAccPlanAccion().getIdAccPlanAccion() + "")
                    .collect(Collectors.toList());
            Date d = new Date();
            accidentePlanAccionList.addAll(planAccionSelect.stream()
                    .filter(pas -> !planAccionBd.contains(pas))
                    .map(pas -> {
                        AccidentePlanAccion apa = new AccidentePlanAccion();
                        apa.setCreado(d);
                        apa.setModificado(d);
                        apa.setEstadoReg(0);
                        apa.setIdAccPlanAccion(new AccPlanAccion(Integer.parseInt(pas)));
                        apa.setUsername(user.getUsername());
                        apa.setIdAccidente(acc);
                        return apa;
                    }).collect(Collectors.toList()));
            accidenteFacadeLocal.edit(acc);
        } else {
            Date d = new Date();
            planAccionSelect.stream().map((id) -> {
                AccidentePlanAccion apa = new AccidentePlanAccion();
                apa.setCreado(d);
                apa.setModificado(d);
                apa.setEstadoReg(0);
                apa.setIdAccPlanAccion(new AccPlanAccion(Integer.parseInt(id)));
                apa.setUsername(user.getUsername());
                apa.setIdAccidente(acc);
                return apa;
            }).forEachOrdered((apa) -> {
                accidentePlanAccionFacadeLocal.create(apa);
            });
        }
        MovilidadUtil.addSuccessMessage("Proceso realizado con éxito");
    }

    public String toStringInteger(Integer id) {
        return id + "";
    }

    public List<AccidenteCalificacion> getListAccidenteCalificacion() {
        return listAccidenteCalificacion;
    }

    public void setListAccidenteCalificacion(List<AccidenteCalificacion> listAccidenteCalificacion) {
        this.listAccidenteCalificacion = listAccidenteCalificacion;
    }

    public DualListModel<AccPlanAccion> getDualListPlanAccion() {
        return dualListPlanAccion;
    }

    public void setDualListPlanAccion(DualListModel<AccPlanAccion> dualListPlanAccion) {
        this.dualListPlanAccion = dualListPlanAccion;
    }

    public Date getdFecha() {
        return dFecha;
    }

    public void setdFecha(Date dFecha) {
        this.dFecha = dFecha;
    }

    public Integer getiPin() {
        return iPin;
    }

    public void setiPin(Integer iPin) {
        this.iPin = iPin;
    }

    public AccidenteCalificacion getAccidenteCalificacion() {
        return accidenteCalificacion;
    }

    public void setAccidenteCalificacion(AccidenteCalificacion accidenteCalificacion) {
        this.accidenteCalificacion = accidenteCalificacion;
    }

    public List<AccidentePlanAccion> getListAccidentePlanAccion() {
        int idAcc = accidenteJSF.compartirIdAccidente();
        if (idAcc != 0) {
            listAccidentePlanAccion = accidentePlanAccionFacadeLocal.findAllEstadoReg(idAcc);
        }
        return listAccidentePlanAccion;
    }

    public void setListAccidentePlanAccion(List<AccidentePlanAccion> listAccidentePlanAccion) {
        this.listAccidentePlanAccion = listAccidentePlanAccion;
    }

    public List<String> getPlanAccionSelect() {
        return planAccionSelect;
    }

    public void setPlanAccionSelect(List<String> planAccionSelect) {
        this.planAccionSelect = planAccionSelect;
    }

    @FacesConverter("planConverter")
    public static class AccidentePlanConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AccidentePlanAccionJSF controller = (AccidentePlanAccionJSF) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "accidentePlanAccionJSF");
            return controller.mapAccPlanAccion.get(getKey(value));
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
            if (object instanceof AccPlanAccion) {
                AccPlanAccion o = (AccPlanAccion) object;
                return getStringKey(o.getIdAccPlanAccion());
            } else {
                return null;
            }
        }
    }

}
