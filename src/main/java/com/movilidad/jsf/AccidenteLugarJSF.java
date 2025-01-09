/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccViaDemarcacionFacadeLocal;
import com.movilidad.ejb.AccidenteLugarDemarFacadeLocal;
import com.movilidad.ejb.AccidenteLugarFacadeLocal;
import com.movilidad.ejb.PrgStopPointFacadeLocal;
import com.movilidad.model.AccSentido;
import com.movilidad.model.AccViaCalzadas;
import com.movilidad.model.AccViaCarriles;
import com.movilidad.model.AccViaClase;
import com.movilidad.model.AccViaClima;
import com.movilidad.model.AccViaCondicion;
import com.movilidad.model.AccViaDemarcacion;
import com.movilidad.model.AccViaDiseno;
import com.movilidad.model.AccViaEstado;
import com.movilidad.model.AccViaGeometrica;
import com.movilidad.model.AccViaIluminacion;
import com.movilidad.model.AccViaMaterial;
import com.movilidad.model.AccViaMixta;
import com.movilidad.model.AccViaSector;
import com.movilidad.model.AccViaSemaforo;
import com.movilidad.model.AccViaTroncal;
import com.movilidad.model.AccViaUtilizacion;
import com.movilidad.model.AccViaVisual;
import com.movilidad.model.AccViaZona;
import com.movilidad.model.AccViaZonat;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteLugar;
import com.movilidad.model.AccidenteLugarDemar;
import com.movilidad.model.PrgStopPoint;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "accidenteLugarJSF")
@ViewScoped
public class AccidenteLugarJSF implements Serializable {

    @EJB
    private AccidenteLugarFacadeLocal accidenteLugarFacadeLocal;
    @EJB
    private AccViaDemarcacionFacadeLocal accViaDemarcacionFacadeLocal;
    @EJB
    private AccidenteLugarDemarFacadeLocal accidenteLugarDemarFacadeLocal;
    @EJB
    private PrgStopPointFacadeLocal prgStopPointFacadeLocal;

    private AccidenteLugar accidenteLugar;

    private List<AccidenteLugar> listAccidenteLugar;
    private DualListModel<AccViaDemarcacion> dualList;
    private List<AccViaDemarcacion> dualListDatos;
    private List<AccViaDemarcacion> dualListCarga;
    private List<AccViaDemarcacion> listAux;
    private List<PrgStopPoint> listPrgStopPoint;

    private HashMap<Integer, AccViaDemarcacion> mapViaDemarcacion = new HashMap<Integer, AccViaDemarcacion>();

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private int i_idAccViaCarriles = 0;
    private int i_idAccSentido = 0;
    private int i_idAccViaCalzadas = 0;
    private int i_idAccViaClase = 0;
    private int i_idAccViaClima = 0;
    private int i_idAccViaCondiciones = 0;
    private int i_idAccViaSemaforo = 0;
    private int i_idAccViaDiseno = 0;
    private int i_idAccViaEstado = 0;
    private int i_idAccViaSector = 0;
    private int i_idAccViaGeometrica = 0;
    private int i_idAccViaZona = 0;
    private int i_idAccViaIluminacion = 0;
    private int i_idAccViaZonat = 0;
    private int i_idAccViaMixta = 0;
    private int i_idAccViaUtilizacion = 0;
    private int i_idAccViaVisual = 0;
    private int i_idAccViaTroncal = 0;
    private int i_idAccViaMaterial = 0;
    private int i_idAccidente;
    private String c_parada = "";
    private String c_latitud = "4.646189";
    private String c_logitud = "-74.078540";

    private boolean b_flag;
    private boolean b_control;

    private MapModel simpleModel;

    @Inject
    private AccidenteJSF accidenteJSF;

    public AccidenteLugarJSF() {
    }

    @PostConstruct
    public void init() {
        try {
            resetIds();
            c_latitud = "4.646189";
            c_logitud = "-74.078540";
            simpleModel = new DefaultMapModel();
            b_control = false;
            b_flag = true;
            i_idAccidente = accidenteJSF.compartirIdAccidente();
//            listPrgStopPoint = prgStopPointFacadeLocal.findAll();
            dualListDatos = new ArrayList<>();
            listAux = new ArrayList<>();
            dualListDatos = accViaDemarcacionFacadeLocal.estadoReg();
            for (AccViaDemarcacion avg : dualListDatos) {
                mapViaDemarcacion.put(avg.getIdAccViaDemarcacion(), avg);
            }
            if (i_idAccidente != 0) {
                accidenteLugar = accidenteLugarFacadeLocal.buscarPorAccidente(i_idAccidente);
                if (accidenteLugar != null) {
                    b_flag = false;
                    List<AccidenteLugarDemar> objetosSelect = accidenteLugarDemarFacadeLocal.objetosSelect(accidenteLugar.getIdAccidenteLugar());
                    if (objetosSelect != null) {
                        for (AccidenteLugarDemar ald : objetosSelect) {
                            listAux.add(ald.getIdAccViaDemarcacion());
                        }
                        dualListDatos.removeAll(listAux);
                        dualListCarga = listAux;
                    } else {
                        dualListCarga = new ArrayList<>();
                    }
                    prepareEditar();
                } else {
                    accidenteLugar = new AccidenteLugar();
                    dualListCarga = new ArrayList<>();
                }
            } else {
                accidenteLugar = new AccidenteLugar();
                dualListCarga = new ArrayList<>();
            }
            dualList = new DualListModel<>(dualListDatos, dualListCarga);
        } catch (Exception e) {
            System.out.println("Error en el metodo postConstructor AccidenteLugar");
        }
    }

    public void prepareGuardar() {
        accidenteLugar = new AccidenteLugar();
    }

    public void prepareEditar() {
        if (accidenteLugar != null) {
            i_idAccViaCarriles = accidenteLugar.getIdAccViaCarriles() != null ? accidenteLugar.getIdAccViaCarriles().getIdAccViaCarriles() : 0;
            i_idAccSentido = accidenteLugar.getIdAccSentido() != null ? accidenteLugar.getIdAccSentido().getIdAccSentido() : 0;
            i_idAccViaCalzadas = accidenteLugar.getIdAccViaCalzadas() != null ? accidenteLugar.getIdAccViaCalzadas().getIdAccViaCalzadas() : 0;
            i_idAccViaClase = accidenteLugar.getIdAccViaClase() != null ? accidenteLugar.getIdAccViaClase().getIdAccViaClase() : 0;
            i_idAccViaClima = accidenteLugar.getIdAccViaClima() != null ? accidenteLugar.getIdAccViaClima().getIdAccViaClima() : 0;
            i_idAccViaCondiciones = accidenteLugar.getIdAccViaCondiciones() != null ? accidenteLugar.getIdAccViaCondiciones().getIdAccViaCondicion() : 0;
            i_idAccViaSemaforo = accidenteLugar.getIdAccViaSemaforo() != null ? accidenteLugar.getIdAccViaSemaforo().getIdAccViaSemaforo() : 0;
            i_idAccViaDiseno = accidenteLugar.getIdAccViaDiseno() != null ? accidenteLugar.getIdAccViaDiseno().getIdAccViaDiseno() : 0;
            i_idAccViaEstado = accidenteLugar.getIdAccViaEstado() != null ? accidenteLugar.getIdAccViaEstado().getIdAccViaEstado() : 0;
            i_idAccViaSector = accidenteLugar.getIdAccViaSector() != null ? accidenteLugar.getIdAccViaSector().getIdAccViaSector() : 0;
            i_idAccViaGeometrica = accidenteLugar.getIdAccViaGeometrica() != null ? accidenteLugar.getIdAccViaGeometrica().getIdAccViaGeometrica() : 0;
            i_idAccViaZona = accidenteLugar.getIdAccViaZona() != null ? accidenteLugar.getIdAccViaZona().getIdAccViaZona() : 0;
            i_idAccViaIluminacion = accidenteLugar.getIdAccViaIluminacion() != null ? accidenteLugar.getIdAccViaIluminacion().getIdAccViaIluminacion() : 0;
            i_idAccViaZonat = accidenteLugar.getIdAccViaZonat() != null ? accidenteLugar.getIdAccViaZonat().getIdAccViaZonat() : 0;
            i_idAccViaMixta = accidenteLugar.getIdAccViaMixta() != null ? accidenteLugar.getIdAccViaMixta().getIdAccViaMixta() : 0;
            i_idAccViaUtilizacion = accidenteLugar.getIdAccViaUtilizacion() != null ? accidenteLugar.getIdAccViaUtilizacion().getIdAccViaUtilizacion() : 0;
            i_idAccViaVisual = accidenteLugar.getIdAccViaVisual() != null ? accidenteLugar.getIdAccViaVisual().getIdAccViaVisual() : 0;
            i_idAccViaTroncal = accidenteLugar.getIdAccViaTroncal() != null ? accidenteLugar.getIdAccViaTroncal().getIdAccViaTroncal() : 0;
            i_idAccViaMaterial = accidenteLugar.getIdAccViaMaterial() != null ? accidenteLugar.getIdAccViaMaterial().getIdAccViaMaterial() : 0;
            c_parada = accidenteLugar.getIdPrgStoppoint() != null ? accidenteLugar.getIdPrgStoppoint().getName() : "";
            try {
                if (accidenteLugar.getLongitude() != null || accidenteLugar.getLatitude() != null) {
                    c_logitud = String.valueOf(accidenteLugar.getLongitude());
                    c_latitud = String.valueOf(accidenteLugar.getLatitude());
                    LatLng latlng = new LatLng(Double.parseDouble(c_latitud), Double.parseDouble(c_logitud));
                    simpleModel.addOverlay(new Marker(latlng, "Punto Seleccionado"));
                } else {
                    c_latitud = "4.646189";
                    c_logitud = "-74.078540";
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                c_latitud = "4.646189";
                c_logitud = "-74.078540";
            }
        }
    }

    public void guardar() {
        try {
            if (i_idAccidente != 0) {
                AccidenteLugar alAux = accidenteLugarFacadeLocal.buscarPorAccidente(i_idAccidente);
                if (alAux != null) {
                    PrimeFaces.current().executeScript("location.reload()");
                    return;
                }
                if (accidenteLugar != null) {
                    cargarObjetos();
                    if (b_control) {
                        b_control = false;
                        return;
                    }
                    accidenteLugar.setIdAccidente(new Accidente(i_idAccidente));
                    accidenteLugar.setCreado(new Date());
                    accidenteLugar.setModificado(new Date());
                    accidenteLugar.setUsername(user.getUsername());
                    accidenteLugar.setEstadoReg(0);
                    accidenteLugarFacadeLocal.create(accidenteLugar);
                    persistirAccLugarDemar();
                    MovilidadUtil.addSuccessMessage("Se guardó accidente lugar correctamente");
                    reset();
                }
            } else {
                MovilidadUtil.addErrorMessage("No se puede registrar lugar de los hecho, sin antes seleccionar un registro de accidente");
                reset();
            }
        } catch (Exception e) {
            System.out.println("Error guardar Accidente Lugar");
        }
    }

    public void editar() {
        try {
            if (accidenteLugar != null) {
                cargarObjetos();
                if (b_control) {
                    b_control = false;
                    return;
                }
                persistirAccLugarDemar();
                accidenteLugarFacadeLocal.edit(accidenteLugar);
                MovilidadUtil.addSuccessMessage("Se actualizó accidente lugar correctamente");
            }
        } catch (Exception e) {
            System.out.println("Error guardar Accidente Lugar");
        }
    }

    public void onPointSelect(PointSelectEvent event) {
        try {
            simpleModel = new DefaultMapModel();
            LatLng latlng = event.getLatLng();
            accidenteLugar.setLatitude(BigDecimal.valueOf(latlng.getLat()));
            accidenteLugar.setLongitude(BigDecimal.valueOf(latlng.getLng()));
            simpleModel.addOverlay(new Marker(latlng, "Punto Seleccionado"));
            c_latitud = String.valueOf(event.getLatLng().getLat());
            c_logitud = String.valueOf(event.getLatLng().getLng());
            MovilidadUtil.addSuccessMessage("Coordenadas seleccionadas, Longitud:"
                    + accidenteLugar.getLatitude() + " Latitud:" + accidenteLugar.getLongitude());
        } catch (Exception e) {
            System.out.println("Error Evento onpointSelect AccidenteLugar");
        }
    }

    public void reset() {
        accidenteLugar = new AccidenteLugar();
        dualListCarga.clear();
        dualListDatos.clear();
        listAux.clear();
        dualListDatos = accViaDemarcacionFacadeLocal.estadoReg();
        dualList = new DualListModel<>(dualListDatos, dualListCarga);
        resetIds();
        b_flag = true;
        c_parada = "";
        init();
    }

    void resetIds() {
        i_idAccViaCarriles = 0;
        i_idAccSentido = 0;
        i_idAccViaCalzadas = 0;
        i_idAccViaClase = 0;
        i_idAccViaClima = 0;
        i_idAccViaCondiciones = 0;
        i_idAccViaSemaforo = 0;
        i_idAccViaDiseno = 0;
        i_idAccViaEstado = 0;
        i_idAccViaSector = 0;
        i_idAccViaGeometrica = 0;
        i_idAccViaZona = 0;
        i_idAccViaIluminacion = 0;
        i_idAccViaZonat = 0;
        i_idAccViaMixta = 0;
        i_idAccViaUtilizacion = 0;
        i_idAccViaVisual = 0;
        i_idAccViaTroncal = 0;
        i_idAccViaMaterial = 0;
        c_parada = "";
        c_latitud = "";
        c_logitud = "";
    }

    void cargarObjetos() {
        try {
            if (i_idAccViaCarriles != 0) {
                accidenteLugar.setIdAccViaCarriles(new AccViaCarriles(i_idAccViaCarriles));
            }

            if (i_idAccSentido != 0) {
                accidenteLugar.setIdAccSentido(new AccSentido(i_idAccSentido));
            }

            if (i_idAccViaCalzadas != 0) {
                accidenteLugar.setIdAccViaCalzadas(new AccViaCalzadas(i_idAccViaCalzadas));
            }

            if (i_idAccViaClase != 0) {
                accidenteLugar.setIdAccViaClase(new AccViaClase(i_idAccViaClase));
            }
            if (i_idAccViaClima != 0) {
                accidenteLugar.setIdAccViaClima(new AccViaClima(i_idAccViaClima));
            }

            if (i_idAccViaCondiciones != 0) {
                accidenteLugar.setIdAccViaCondiciones(new AccViaCondicion(i_idAccViaCondiciones));
            }

            if (i_idAccViaSemaforo != 0) {
                accidenteLugar.setIdAccViaSemaforo(new AccViaSemaforo(i_idAccViaSemaforo));
            }

            if (i_idAccViaDiseno != 0) {
                accidenteLugar.setIdAccViaDiseno(new AccViaDiseno(i_idAccViaDiseno));
            }
            if (i_idAccViaEstado != 0) {
                accidenteLugar.setIdAccViaEstado(new AccViaEstado(i_idAccViaEstado));
            }

            if (i_idAccViaSector != 0) {
                accidenteLugar.setIdAccViaSector(new AccViaSector(i_idAccViaSector));
            }

            if (i_idAccViaGeometrica != 0) {
                accidenteLugar.setIdAccViaGeometrica(new AccViaGeometrica(i_idAccViaGeometrica));
            }

            if (i_idAccViaZona != 0) {
                accidenteLugar.setIdAccViaZona(new AccViaZona(i_idAccViaZona));
            }

            if (i_idAccViaIluminacion != 0) {
                accidenteLugar.setIdAccViaIluminacion(new AccViaIluminacion(i_idAccViaIluminacion));
            }

            if (i_idAccViaZonat != 0) {
                accidenteLugar.setIdAccViaZonat(new AccViaZonat(i_idAccViaZonat));
            }
            if (i_idAccViaMixta != 0) {
                accidenteLugar.setIdAccViaMixta(new AccViaMixta(i_idAccViaMixta));
            }

            if (i_idAccViaUtilizacion != 0) {
                accidenteLugar.setIdAccViaUtilizacion(new AccViaUtilizacion(i_idAccViaUtilizacion));
            }

            if (i_idAccViaVisual != 0) {
                accidenteLugar.setIdAccViaVisual(new AccViaVisual(i_idAccViaVisual));
            }

            if (i_idAccViaTroncal != 0) {
                accidenteLugar.setIdAccViaTroncal(new AccViaTroncal(i_idAccViaTroncal));
            }
            if (i_idAccViaMaterial != 0) {
                accidenteLugar.setIdAccViaMaterial(new AccViaMaterial(i_idAccViaMaterial));
            }
        } catch (Exception e) {
            System.out.println("Error cargar objetos");
        }
    }

    void persistirAccLugarDemar() {
        try {
            AccidenteLugarDemar accidenteLugarDemar = new AccidenteLugarDemar();
            accidenteLugarDemar.setIdAccidenteLugar(accidenteLugar);
            List<AccViaDemarcacion> listDelete = new ArrayList<>(listAux);
            List<AccViaDemarcacion> listTarget = new ArrayList<>(dualList.getTarget());
            if (!listAux.isEmpty()) {
                dualList.getTarget().removeAll(listAux);
                listDelete.removeAll(listTarget);
                for (AccViaDemarcacion avd : listDelete) {
                    AccidenteLugarDemar ald = accidenteLugarDemarFacadeLocal.findByAccLugarAndViaDemar(accidenteLugar.getIdAccidenteLugar(), avd.getIdAccViaDemarcacion());
                    if (ald != null) {
                        if (ald.getIdAccInformeOpe() == null) {
                            accidenteLugarDemarFacadeLocal.remove(ald);
                        } else {
                            ald.setIdAccidenteLugar(null);
                            accidenteLugarDemarFacadeLocal.edit(ald);
                        }
                    }
                }
            }
            for (AccViaDemarcacion avd : dualList.getTarget()) {
                accidenteLugarDemar.setIdAccViaDemarcacion(avd);
                accidenteLugarDemarFacadeLocal.edit(accidenteLugarDemar);
            }
            dualList.setTarget(listTarget);
            dualListDatos = accViaDemarcacionFacadeLocal.estadoReg();
            dualListDatos.removeAll(listTarget);
            dualList.setSource(dualListDatos);
            List<AccidenteLugarDemar> objetosSelect = accidenteLugarDemarFacadeLocal.objetosSelect(accidenteLugar.getIdAccidenteLugar());
            if (objetosSelect != null) {
                for (AccidenteLugarDemar ald : objetosSelect) {
                    listAux.add(ald.getIdAccViaDemarcacion());
                }
            }
        } catch (Exception e) {
            System.out.println("Error en Persistir AccLugar");
        }
    }

    public void obtenerParada(SelectEvent event) {
        try {
            PrgStopPoint name = (PrgStopPoint) event.getObject();
            accidenteLugar.setIdPrgStoppoint(name);
            c_parada = name.getName();
            MovilidadUtil.addSuccessMessage("Parada cercana cargada correctamente");
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al cargar estación cerca, intentelo nuevamente.");
            System.out.println("Error en obtener parada");
        }
        PrimeFaces.current().executeScript("PF('dlgStopPoint').hide();");
    }

    public AccidenteLugar getAccidenteLugar() {
        return accidenteLugar;
    }

    public List<AccidenteLugar> getListAccidenteLugar() {
        return listAccidenteLugar;
    }

    public int getI_idAccViaCarriles() {
        return i_idAccViaCarriles;
    }

    public int getI_idAccSentido() {
        return i_idAccSentido;
    }

    public int getI_idAccViaCalzadas() {
        return i_idAccViaCalzadas;
    }

    public int getI_idAccViaClase() {
        return i_idAccViaClase;
    }

    public int getI_idAccViaClima() {
        return i_idAccViaClima;
    }

    public int getI_idAccViaCondiciones() {
        return i_idAccViaCondiciones;
    }

    public int getI_idAccViaSemaforo() {
        return i_idAccViaSemaforo;
    }

    public int getI_idAccViaDiseno() {
        return i_idAccViaDiseno;
    }

    public int getI_idAccViaEstado() {
        return i_idAccViaEstado;
    }

    public int getI_idAccViaSector() {
        return i_idAccViaSector;
    }

    public int getI_idAccViaGeometrica() {
        return i_idAccViaGeometrica;
    }

    public int getI_idAccViaZona() {
        return i_idAccViaZona;
    }

    public int getI_idAccViaIluminacion() {
        return i_idAccViaIluminacion;
    }

    public int getI_idAccViaZonat() {
        return i_idAccViaZonat;
    }

    public int getI_idAccViaMixta() {
        return i_idAccViaMixta;
    }

    public int getI_idAccViaUtilizacion() {
        return i_idAccViaUtilizacion;
    }

    public int getI_idAccViaVisual() {
        return i_idAccViaVisual;
    }

    public int getI_idAccViaTroncal() {
        return i_idAccViaTroncal;
    }

    public int getI_idAccViaMaterial() {
        return i_idAccViaMaterial;
    }

    public void setAccidenteLugar(AccidenteLugar accidenteLugar) {
        this.accidenteLugar = accidenteLugar;
    }

    public void setI_idAccViaCarriles(int i_idAccViaCarriles) {
        this.i_idAccViaCarriles = i_idAccViaCarriles;
    }

    public void setI_idAccSentido(int i_idAccSentido) {
        this.i_idAccSentido = i_idAccSentido;
    }

    public void setI_idAccViaCalzadas(int i_idAccViaCalzadas) {
        this.i_idAccViaCalzadas = i_idAccViaCalzadas;
    }

    public void setI_idAccViaClase(int i_idAccViaClase) {
        this.i_idAccViaClase = i_idAccViaClase;
    }

    public void setI_idAccViaClima(int i_idAccViaClima) {
        this.i_idAccViaClima = i_idAccViaClima;
    }

    public void setI_idAccViaCondiciones(int i_idAccViaCondiciones) {
        this.i_idAccViaCondiciones = i_idAccViaCondiciones;
    }

    public void setI_idAccViaSemaforo(int i_idAccViaSemaforo) {
        this.i_idAccViaSemaforo = i_idAccViaSemaforo;
    }

    public void setI_idAccViaDiseno(int i_idAccViaDiseno) {
        this.i_idAccViaDiseno = i_idAccViaDiseno;
    }

    public void setI_idAccViaEstado(int i_idAccViaEstado) {
        this.i_idAccViaEstado = i_idAccViaEstado;
    }

    public void setI_idAccViaSector(int i_idAccViaSector) {
        this.i_idAccViaSector = i_idAccViaSector;
    }

    public void setI_idAccViaGeometrica(int i_idAccViaGeometrica) {
        this.i_idAccViaGeometrica = i_idAccViaGeometrica;
    }

    public void setI_idAccViaZona(int i_idAccViaZona) {
        this.i_idAccViaZona = i_idAccViaZona;
    }

    public void setI_idAccViaIluminacion(int i_idAccViaIluminacion) {
        this.i_idAccViaIluminacion = i_idAccViaIluminacion;
    }

    public void setI_idAccViaZonat(int i_idAccViaZonat) {
        this.i_idAccViaZonat = i_idAccViaZonat;
    }

    public void setI_idAccViaMixta(int i_idAccViaMixta) {
        this.i_idAccViaMixta = i_idAccViaMixta;
    }

    public void setI_idAccViaUtilizacion(int i_idAccViaUtilizacion) {
        this.i_idAccViaUtilizacion = i_idAccViaUtilizacion;
    }

    public void setI_idAccViaVisual(int i_idAccViaVisual) {
        this.i_idAccViaVisual = i_idAccViaVisual;
    }

    public void setI_idAccViaTroncal(int i_idAccViaTroncal) {
        this.i_idAccViaTroncal = i_idAccViaTroncal;
    }

    public void setI_idAccViaMaterial(int i_idAccViaMaterial) {
        this.i_idAccViaMaterial = i_idAccViaMaterial;
    }

    public DualListModel<AccViaDemarcacion> getDualList() {
        return dualList;
    }

    public void setDualList(DualListModel<AccViaDemarcacion> dualList) {
        this.dualList = dualList;
    }

    public List<AccViaDemarcacion> getDualListDatos() {
        return dualListDatos;
    }

    public void setDualListDatos(List<AccViaDemarcacion> dualListDatos) {
        this.dualListDatos = dualListDatos;
    }

    public List<AccViaDemarcacion> getDualListCarga() {
        return dualListCarga;
    }

    public void setDualListCarga(List<AccViaDemarcacion> dualListCarga) {
        this.dualListCarga = dualListCarga;
    }

    public boolean isB_flag() {
        return b_flag;
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public List<PrgStopPoint> getListPrgStopPoint() {
        return listPrgStopPoint;
    }

    public void setListPrgStopPoint(List<PrgStopPoint> listPrgStopPoint) {
        this.listPrgStopPoint = listPrgStopPoint;
    }

    public String getC_parada() {
        return c_parada;
    }

    public void setC_parada(String c_parada) {
        this.c_parada = c_parada;
    }

    public String getC_latitud() {
        return c_latitud;
    }

    public void setC_latitud(String c_latitud) {
        this.c_latitud = c_latitud;
    }

    public String getC_logitud() {
        return c_logitud;
    }

    public void setC_logitud(String c_logitud) {
        this.c_logitud = c_logitud;

    }

    @FacesConverter("viaConverter")
    public static class AccidenteLugarConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AccidenteLugarJSF controller = (AccidenteLugarJSF) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "accidenteLugarJSF");
            return controller.mapViaDemarcacion.get(getKey(value));
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
            if (object instanceof AccViaDemarcacion) {
                AccViaDemarcacion o = (AccViaDemarcacion) object;
                return getStringKey(o.getIdAccViaDemarcacion());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AccViaDemarcacion.class.getName()});
                return null;
            }
        }

    }
}
