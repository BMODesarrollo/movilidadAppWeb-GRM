/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgDistanceFacadeLocal;
import com.movilidad.ejb.PrgStopPointFacadeLocal;
import com.movilidad.model.PrgDistance;
import com.movilidad.model.PrgStopPoint;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * En este JSFManagedBean se lleva a cabo la persistencia de los Arcos pripios,
 * éstos son distancias entre dos puntos que previamente han sido establecidos
 * con el ente gestor.
 *
 * @author solucionesit
 */
@Named(value = "arcosPropiosJSFMB")
@ViewScoped
public class ArcosPropiosJSFManagedBean implements Serializable {

    /**
     * Creates a new instance of ArcosPropiosJSFManagedBean
     */
    public ArcosPropiosJSFManagedBean() {

    }

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @EJB
    private PrgStopPointFacadeLocal prgSPEJB;

    @EJB
    private PrgDistanceFacadeLocal prgDistEJB;

    @Inject
    private SelectConfigFreewayBean configFreewayBean;
    private List<PrgStopPoint> listParadas;
    private List<PrgStopPoint> listParadasC;
    private List<PrgDistance> listDistance;
    private String nombreParada = "";
    private String punto_inicioString = "";
    private String punto_finString = "";
    private PrgStopPoint punto_finObj;
    private PrgStopPoint punto_inicioObj;
    private int parada_flag;
    private BigDecimal distancia;
    private boolean flagEditarParada = false;
    private boolean flagEditarDistancia = false;
    private PrgStopPoint paradaLocal;
    private PrgDistance distanciaLocal;

    @PostConstruct
    public void init() {
        listarParadasAndDistance();
    }

    public void listarParadasAndDistance() {
        cargarParadas(configFreewayBean.getIdGopUF());
        cargarDistances(configFreewayBean.getIdGopUF());
    }

    void cargarParadas(int idUf) {
        listParadas = prgSPEJB.getParadasPropios(idUf);
    }

    void cargarDistances(int idUf) {
        listDistance = prgDistEJB.findAllPropias(idUf);
    }

    public void editar(PrgStopPoint sp) {
    }

    public void onRowClckSelectParada(final SelectEvent event) throws Exception {
        PrimeFaces current = PrimeFaces.current();
        if (parada_flag == 1) {
            setPunto_inicioObj((PrgStopPoint) event.getObject());
            punto_inicioString = punto_inicioObj.getName();
            current.ajax().update("frmArcospropios:tabArcos:id_pi");
        } else {
            setPunto_finObj((PrgStopPoint) event.getObject());
            punto_finString = punto_finObj.getName();
            current.ajax().update("frmArcospropios:tabArcos:id_pf");
        }
        current.ajax().update(":frmPrincipalParadasList:tablaParadas");
        current.executeScript("PF('wvParadasDialog').hide()");
    }

    public void findDestino(int p) {
        parada_flag = p;
        int idUf = configFreewayBean.getConfigFreeway() == null ? 0 : configFreewayBean.getConfigFreeway().getIdGopUnidadFuncional().getIdGopUnidadFuncional();
        if (idUf == 0) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar una unidad funcional.");
            return;
        }
        if (p == 1) {
            if (!punto_inicioString.isEmpty()) {
                listParadasC = prgSPEJB.getparadasByNombre(punto_inicioString, idUf);
            } else {
                MovilidadUtil.addErrorMessage("Digite nombre del Punto Inicio.");
                return;
            }
        } else {
            if (!punto_finString.isEmpty()) {
                listParadasC = prgSPEJB.getparadasByNombre(punto_finString, idUf);
            } else {
                MovilidadUtil.addErrorMessage("Digite nombre del Punto Fin.");
                return;
            }
        }
        PrimeFaces.current().executeScript("PF('wvParadasDialog').show();");
        PrimeFaces.current().ajax().update("frmPrincipalParadasList:tablaParadas");
    }

    @Transactional
    public void guardarParada() {
        if (validar()) {
            return;
        }
        PrgStopPoint parada = new PrgStopPoint();
        parada.setName(nombreParada);
        parada.setCreado(new Date());
        parada.setPropia(1);
        parada.setIsDepot(100);
        parada.setUsername(user.getUsername());
        parada.setEstadoReg(0);
        parada.setIdGopUnidadFuncional(configFreewayBean.getConfigFreeway().getIdGopUnidadFuncional());
        prgSPEJB.create(parada);
        MovilidadUtil.addSuccessMessage("Exito. Registro exitoso Parada");
        nombreParada = "";

    }

    boolean validar() {
        if (configFreewayBean.getConfigFreeway() == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar una unidad funcional.");
            return true;
        }

        if (nombreParada.isEmpty()) {
            MovilidadUtil.addErrorMessage("Digite nombre de la parada.");
            return true;
        }

        PrgStopPoint sp = prgSPEJB.validarparadaByNombre(nombreParada);
        if (sp != null) {
            MovilidadUtil.addErrorMessage("Ya existe una parada con este nombre");
            return true;
        }
        return false;
    }

    public void editarParada() {
        try {
            if (!nombreParada.isEmpty()) {
                PrgStopPoint sp = prgSPEJB.validarparadaByNombre(nombreParada);
                if (sp == null) {
                    paradaLocal.setName(nombreParada);
                    paradaLocal.setUsername(user.getUsername());
                    paradaLocal.setModificado(new Date());
                    prgSPEJB.edit(paradaLocal);
                    MovilidadUtil.addSuccessMessage("Exito. Actualización exitosa Parada");
                    nombreParada = "";
                    flagEditarParada = false;
                } else {
                    MovilidadUtil.addErrorMessage("Ya existe una parada con este nombre");
                }

            } else {
                MovilidadUtil.addErrorMessage("Digite nombre de la parada.");
            }
        } catch (Exception e) {
        }
    }

    public void prepareEditParada(PrgStopPoint psp) {
        paradaLocal = psp;
        nombreParada = psp.getName();
        flagEditarParada = true;
    }

    @Transactional
    public void guardarDistancia() {
        if (validarDistancia()) {
            return;
        }
        PrgDistance prgDistancia = new PrgDistance();
        prgDistancia.setIdPrgFromStop(punto_inicioObj);
        prgDistancia.setIdPrgToStop(punto_finObj);
        prgDistancia.setDistance(distancia);
        prgDistancia.setUsername(user.getUsername());
        prgDistancia.setCreado(new Date());
        prgDistancia.setVigente(1);
        prgDistancia.setPropia(1);
        prgDistancia.setEstadoReg(0);
        prgDistancia.setIdGopUnidadFuncional(configFreewayBean.getConfigFreeway().getIdGopUnidadFuncional());
        prgDistEJB.create(prgDistancia);
        MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
        punto_finString = "";
        punto_inicioString = "";
        punto_inicioObj = null;
        punto_finObj = null;
        distancia = null;

    }

    boolean validarDistancia() {
        if (configFreewayBean.getConfigFreeway() == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar una unidad funcional.");
            return true;
        }
        if (punto_inicioObj == null) {
            MovilidadUtil.addErrorMessage("Se debe cargar punto inicio.");
            return true;
        }
        if (punto_finObj == null) {
            MovilidadUtil.addErrorMessage("Se debe cargar punto fin.");
            return true;
        }
        if (punto_inicioObj.getIdPrgStoppoint().equals(punto_finObj.getIdPrgStoppoint())) {
            MovilidadUtil.addErrorMessage("El punto inicio no puede ser igual al punto fin.");
            return true;
        }
        if (distancia == null) {
            MovilidadUtil.addErrorMessage("Se debe cargar la distancia.");
            return true;
        }
        PrgDistance result = prgDistEJB.validarDistancia(punto_inicioObj, punto_finObj, 0);
        if (result != null) {
            MovilidadUtil.addErrorMessage("Ya existe registro con los puntos cargados.");
            return true;
        }
        return false;
    }

    public void prepareEditDistancia(PrgDistance pd) {
        distanciaLocal = pd;
        punto_inicioObj = distanciaLocal.getIdPrgFromStop();
        punto_finObj = distanciaLocal.getIdPrgToStop();
        punto_inicioString = distanciaLocal.getIdPrgFromStop().getName();
        punto_finString = distanciaLocal.getIdPrgToStop().getName();
        distancia = distanciaLocal.getDistance();
        flagEditarDistancia = true;
    }

    @Transactional
    public void editDistancia() {
        try {
            if (punto_inicioObj == null) {
                MovilidadUtil.addErrorMessage("Selecciones punto inicio");
                return;
            }
            if (punto_finObj == null) {
                MovilidadUtil.addErrorMessage("Selecciones punto fin");
                return;
            }
            if (punto_inicioObj.getIdPrgStoppoint().equals(punto_finObj.getIdPrgStoppoint())) {
                MovilidadUtil.addErrorMessage("El punto inicio no puede ser igual al punto fin");
                return;
            }
            if (distancia == null) {
                MovilidadUtil.addErrorMessage("Digiste la distancia");
                return;
            }
            PrgDistance result = prgDistEJB.validarDistancia(punto_inicioObj, punto_finObj, distanciaLocal.getIdPrgDistance());
            if (result != null) {
                MovilidadUtil.addErrorMessage("Ya existe registro con estos dos paradas");
                return;
            }
            distanciaLocal.setIdPrgFromStop(punto_inicioObj);
            distanciaLocal.setIdPrgToStop(punto_finObj);
            distanciaLocal.setDistance(distancia);
            distanciaLocal.setUsername(user.getUsername());
            distanciaLocal.setModificado(new Date());
            prgDistEJB.edit(distanciaLocal);
            MovilidadUtil.addSuccessMessage("Exito. Actualización exitosa Distancia");
            punto_finString = "";
            punto_inicioString = "";
            punto_inicioObj = null;
            punto_finObj = null;
            distancia = null;
            flagEditarDistancia = false;

        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error Interno!!!");

        }
    }

    public List<PrgStopPoint> getListParadas() {
        cargarParadas(configFreewayBean.getIdGopUF());
        return listParadas;
    }

    public void setListParadas(List<PrgStopPoint> listParadas) {
        this.listParadas = listParadas;
    }

    public List<PrgDistance> getListDistance() {
        cargarDistances(configFreewayBean.getIdGopUF());
        return listDistance;
    }

    public void setListDistance(List<PrgDistance> listDistance) {
        this.listDistance = listDistance;
    }

    public String getNombreParada() {
        return nombreParada;
    }

    public void setNombreParada(String nombreParada) {
        this.nombreParada = nombreParada;
    }

    public String getPunto_inicioString() {
        return punto_inicioString;
    }

    public void setPunto_inicioString(String punto_inicioString) {
        this.punto_inicioString = punto_inicioString;
    }

    public String getPunto_finString() {
        return punto_finString;
    }

    public void setPunto_finString(String punto_finString) {
        this.punto_finString = punto_finString;
    }

    public List<PrgStopPoint> getListParadasC() {
        return listParadasC;
    }

    public void setListParadasC(List<PrgStopPoint> listParadasC) {
        this.listParadasC = listParadasC;
    }

    public PrgStopPoint getPunto_finObj() {
        return punto_finObj;
    }

    public void setPunto_finObj(PrgStopPoint punto_finObj) {
        this.punto_finObj = punto_finObj;
    }

    public PrgStopPoint getPunto_inicioObj() {
        return punto_inicioObj;
    }

    public void setPunto_inicioObj(PrgStopPoint punto_inicioObj) {
        this.punto_inicioObj = punto_inicioObj;
    }

    public BigDecimal getDistancia() {
        return distancia;
    }

    public void setDistancia(BigDecimal distancia) {
        this.distancia = distancia;
    }

    public boolean isFlagEditarParada() {
        return flagEditarParada;
    }

    public void setFlagEditarParada(boolean flagEditarParada) {
        this.flagEditarParada = flagEditarParada;
    }

    public boolean isFlagEditarDistancia() {
        return flagEditarDistancia;
    }

    public void setFlagEditarDistancia(boolean flagEditarDistancia) {
        this.flagEditarDistancia = flagEditarDistancia;
    }

}
