/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccVisualInformeFacadeLocal;
import com.movilidad.ejb.AccidenteConductorFacadeLocal;
import com.movilidad.ejb.AccidenteFacadeLocal;
import com.movilidad.ejb.AccidenteLugarDemarFacadeLocal;
import com.movilidad.ejb.AccidenteLugarFacadeLocal;
import com.movilidad.ejb.AccidenteTestigoFacadeLocal;
import com.movilidad.ejb.AccidenteVehiculoFacadeLocal;
import com.movilidad.ejb.AccidenteVictimaFacadeLocal;
import com.movilidad.model.AccInformeMaster;
import com.movilidad.model.AccInformeMasterTestigo;
import com.movilidad.model.AccInformeMasterVehCond;
import com.movilidad.model.AccInformeMasterVic;
import com.movilidad.model.AccInformeOpe;
import com.movilidad.model.AccInformeTestigo;
import com.movilidad.model.AccInformeVehCond;
import com.movilidad.model.AccInformeVic;
import com.movilidad.model.AccVisualInforme;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteConductor;
import com.movilidad.model.AccidenteLugar;
import com.movilidad.model.AccidenteLugarDemar;
import com.movilidad.model.AccidenteTestigo;
import com.movilidad.model.AccidenteVehiculo;
import com.movilidad.model.AccidenteVictima;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "migrarInformeMaestroJSF")
@ViewScoped
public class MigrarInformeMaestroJSF implements Serializable {

    @EJB
    private AccidenteFacadeLocal accidenteFacadeLocal;
    @EJB
    private AccidenteLugarDemarFacadeLocal accidenteLugarDemarFacadeLocal;
    @EJB
    private AccVisualInformeFacadeLocal accVisualInformeFacadeLocal;
    @EJB
    private AccidenteLugarFacadeLocal accidenteLugarFacadeLocal;
    @EJB
    private AccidenteVehiculoFacadeLocal accidenteVehiculoFacadeLocal;
    @EJB
    private AccidenteConductorFacadeLocal accidenteConductorFacadeLocal;
    @EJB
    private AccidenteVictimaFacadeLocal accidenteVictimaFacadeLocal;
    @EJB
    private AccidenteTestigoFacadeLocal accidenteTestigoFacadeLocal;

    @Inject
    private AccImprimirInformeJSF accImprimirInformeJSF;

    private Accidente accidente;
    private AccidenteLugar accidenteLugar;

    //informe del operador
    private AccInformeOpe accInformeOpe;
    private List<AccidenteLugarDemar> listAccidenteLugarDemar;
    private List<AccVisualInforme> listAccVisualInforme;
    private HashMap<String, AccidenteVehiculo> mapAccidenteVehiculo;
    private HashMap<String, AccidenteConductor> mapAccidenteConductor;
    private HashMap<String, AccidenteVictima> mapAccidenteVictima;
    private HashMap<String, AccidenteTestigo> mapAccidenteTestigo;

    //informe Master
    private AccInformeMaster accInformeMaster;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public MigrarInformeMaestroJSF() {
    }

    @PostConstruct
    public void init() {
        accidente = null;
        accidenteLugar = null;
        accInformeOpe = null;
        accInformeMaster = null;
        listAccidenteLugarDemar = null;
        listAccVisualInforme = null;
        mapAccidenteVehiculo = new HashMap<>();
        mapAccidenteConductor = new HashMap<>();
        mapAccidenteVictima = new HashMap<>();
        mapAccidenteTestigo = new HashMap<>();
    }

    public void onSelectAccidente(SelectEvent event) {
        reset();
        accidente = (Accidente) event.getObject();
        accImprimirInformeJSF.cargarTodosInforme(accidente.getIdAccidente());
        cargarAccInformes();
    }

    public void reset() {
        accidente = null;
        accImprimirInformeJSF.resetInformes();
    }

    private void cargarAccInformes() {
        List<AccInformeOpe> accInformeOpeList = accidente.getAccInformeOpeList();
        if (accInformeOpeList != null && !accInformeOpeList.isEmpty()) {
            accInformeOpe = accidente.getAccInformeOpeList().get(0);
            cargarObjetosOperador();
        }
        List<AccInformeMaster> accInformeMasterList = accidente.getAccInformeMasterList();
        if (accInformeMasterList != null && !accInformeMasterList.isEmpty()) {
            accInformeMaster = accInformeMasterList.get(0);
        }
    }

    void cargarObjetosOperador() {
        if (accInformeOpe != null) {
            listAccidenteLugarDemar = accidenteLugarDemarFacadeLocal.getAccidenteLugarDemarInformeOpe(accInformeOpe.getIdAccInformeOpe());
            listAccVisualInforme = accVisualInformeFacadeLocal.getAccVisualInformeOpe(accInformeOpe.getIdAccInformeOpe());
        }
    }

    public void migrarDatosLugarInfoOpe() {
        boolean ok = false;
        accidenteLugar = accidenteLugarFacadeLocal.buscarPorAccidente(accidente.getIdAccidente());
        if (accidenteLugar == null) {
            accidenteLugar = new AccidenteLugar();
            ok = true;
        }
        if (accidenteLugar.getDireccion() != null && accidenteLugar.getDireccion().isEmpty()) {
            if (accInformeOpe.getDireccion() != null) {
                accidenteLugar.setDireccion(accInformeOpe.getDireccion());
            }
        }
        if (accidenteLugar.getDireccion() == null) {
            if (accInformeOpe.getDireccion() != null) {
                accidenteLugar.setDireccion(accInformeOpe.getDireccion());
            }
        }
        if (accidenteLugar.getIdAccViaTroncal() == null) {
            if (accInformeOpe.getIdAccViaTroncal() != null) {
                accidenteLugar.setIdAccViaTroncal(accInformeOpe.getIdAccViaTroncal());
            }
        }
        if (accidenteLugar.getLatitude() == null) {
            if (accInformeOpe.getLatitude() != null) {
                accidenteLugar.setLatitude(accInformeOpe.getLatitude());
            }
        }
        if (accidenteLugar.getLongitude() == null) {
            if (accInformeOpe.getLongitude() != null) {
                accidenteLugar.setLongitude(accInformeOpe.getLongitude());
            }
        }
        if (ok) {
            accidenteLugar.setIdAccidente(accidente);
            accidenteLugarFacadeLocal.create(accidenteLugar);
        } else {
            accidenteLugarFacadeLocal.edit(accidenteLugar);
        }
        MovilidadUtil.addSuccessMessage("Proceso realizado con éxito");
    }

    public void migrarDatosCaractLugar() {
        boolean ok = false;
        accidenteLugar = accidenteLugarFacadeLocal.buscarPorAccidente(accidente.getIdAccidente());
        if (accidenteLugar == null) {
            accidenteLugar = new AccidenteLugar();
            ok = true;
        }
        if (accidenteLugar.getIdAccViaSector() == null) {
            if (accInformeOpe.getIdAccViaSector() != null) {
                accidenteLugar.setIdAccViaSector(accInformeOpe.getIdAccViaSector());
            }
        }
        if (accidenteLugar.getIdAccViaZona() == null) {
            if (accInformeOpe.getIdAccViaZona() != null) {
                accidenteLugar.setIdAccViaZona(accInformeOpe.getIdAccViaZona());
            }
        }
        if (accidenteLugar.getIdAccViaDiseno() == null) {
            if (accInformeOpe.getIdAccViaDiseno() != null) {
                accidenteLugar.setIdAccViaDiseno(accInformeOpe.getIdAccViaDiseno());
            }
        }
        if (accidenteLugar.getIdAccViaClima() == null) {
            if (accInformeOpe.getIdAccViaClima() != null) {
                accidenteLugar.setIdAccViaClima(accInformeOpe.getIdAccViaClima());
            }
        }
        if (ok) {
            accidenteLugar.setIdAccidente(accidente);
            accidenteLugarFacadeLocal.create(accidenteLugar);
        } else {
            accidenteLugarFacadeLocal.edit(accidenteLugar);
        }
        MovilidadUtil.addSuccessMessage("Proceso realizado con éxito");
    }

    public void migrarDatosCaractVia() {
        boolean ok = false;
        accidenteLugar = accidenteLugarFacadeLocal.buscarPorAccidente(accidente.getIdAccidente());
        if (accidenteLugar == null) {
            accidenteLugar = new AccidenteLugar();
            ok = true;
        }
        if (accidenteLugar.getIdAccViaGeometrica() == null) {
            if (accInformeOpe.getIdAccViaGeometrica() != null) {
                accidenteLugar.setIdAccViaGeometrica(accInformeOpe.getIdAccViaGeometrica());
            }
        }
        if (accidenteLugar.getIdAccViaUtilizacion() == null) {
            if (accInformeOpe.getIdAccViaUtilizacion() != null) {
                accidenteLugar.setIdAccViaUtilizacion(accInformeOpe.getIdAccViaUtilizacion());
            }
        }
        if (accidenteLugar.getIdAccViaCalzadas() == null) {
            if (accInformeOpe.getIdAccViaCalzada() != null) {
                accidenteLugar.setIdAccViaCalzadas(accInformeOpe.getIdAccViaCalzada());
            }
        }
        if (accidenteLugar.getIdAccViaCarriles() == null) {
            if (accInformeOpe.getIdAccViaCarriles() != null) {
                accidenteLugar.setIdAccViaCarriles(accInformeOpe.getIdAccViaCarriles());
            }
        }
        if (accidenteLugar.getIdAccViaMaterial() == null) {
            if (accInformeOpe.getIdAccViaMaterial() != null) {
                accidenteLugar.setIdAccViaMaterial(accInformeOpe.getIdAccViaMaterial());
            }
        }
        if (accidenteLugar.getIdAccViaEstado() == null) {
            if (accInformeOpe.getIdAccViaEstado() != null) {
                accidenteLugar.setIdAccViaEstado(accInformeOpe.getIdAccViaEstado());
            }
        }
        if (accidenteLugar.getIdAccViaCondiciones() == null) {
            if (accInformeOpe.getIdAccViaCondicion() != null) {
                accidenteLugar.setIdAccViaCondiciones(accInformeOpe.getIdAccViaCondicion());
            }
        }
        if (accidenteLugar.getIdAccViaIluminacion() == null) {
            if (accInformeOpe.getIdAccViaIluminacion() != null) {
                accidenteLugar.setIdAccViaIluminacion(accInformeOpe.getIdAccViaIluminacion());
            }
        }
        if (ok) {
            accidenteLugar.setIdAccidente(accidente);
            accidenteLugarFacadeLocal.create(accidenteLugar);
        } else {
            accidenteLugarFacadeLocal.edit(accidenteLugar);
        }
        MovilidadUtil.addSuccessMessage("Proceso realizado con éxito");
    }

    public void migrarAgenteControl() {
        boolean ok = false;
        accidenteLugar = accidenteLugarFacadeLocal.buscarPorAccidente(accidente.getIdAccidente());
        if (accidenteLugar == null) {
            accidenteLugar = new AccidenteLugar();
            ok = true;
        }
        if (accidenteLugar.getIdAccViaSemaforo() == null) {
            if (accInformeOpe.getIdAccViaSemaforo() != null) {
                accidenteLugar.setIdAccViaSemaforo(accInformeOpe.getIdAccViaSemaforo());
            }
        }
        if (ok) {
            accidenteLugar.setIdAccidente(accidente);
            accidenteLugarFacadeLocal.create(accidenteLugar);
        } else {
            accidenteLugarFacadeLocal.edit(accidenteLugar);
        }
        MovilidadUtil.addSuccessMessage("Proceso realizado con éxito");
    }

    public void migrarDemarSenali() {
        accidenteLugar = accidenteLugarFacadeLocal.buscarPorAccidente(accidente.getIdAccidente());
        if (accidenteLugar == null) {
            accidenteLugar = new AccidenteLugar();
            accidenteLugarFacadeLocal.create(accidenteLugar);
        }
        List<AccidenteLugarDemar> accidenteLugarDemarList = accidenteLugar.getAccidenteLugarDemarList();
        if (accidenteLugarDemarList != null && !accidenteLugarDemarList.isEmpty()) {
            for (AccidenteLugarDemar ald : accidenteLugarDemarList) {
                for (AccidenteLugarDemar aldAux : listAccidenteLugarDemar) {
                    if (ald.getIdAccViaDemarcacion().getIdAccViaDemarcacion().equals(aldAux.getIdAccViaDemarcacion().getIdAccViaDemarcacion())) {
                        if (ald.getIdAccInformeOpe() == null) {
                            accidenteLugarDemarFacadeLocal.remove(ald);
                            aldAux.setIdAccidenteLugar(accidenteLugar);
                            accidenteLugarDemarFacadeLocal.edit(aldAux);
                        }
                    } else {
                        aldAux.setIdAccidenteLugar(accidenteLugar);
                        accidenteLugarDemarFacadeLocal.edit(aldAux);
                    }
                }
            }
        } else {
            for (AccidenteLugarDemar ald : listAccidenteLugarDemar) {
                ald.setIdAccidenteLugar(accidenteLugar);
                accidenteLugarDemarFacadeLocal.edit(ald);
            }
        }
        MovilidadUtil.addSuccessMessage("Proceso realizado con éxito");
    }

    public void migrarVisual() {
        boolean ok = false;
        accidenteLugar = accidenteLugarFacadeLocal.buscarPorAccidente(accidente.getIdAccidente());
        if (accidenteLugar == null) {
            accidenteLugar = new AccidenteLugar();
            ok = true;
        }
        if (accidenteLugar.getIdAccViaVisual() == null) {
            if (listAccVisualInforme != null && !listAccVisualInforme.isEmpty()) {
                accidenteLugar.setIdAccViaVisual(listAccVisualInforme.get(0).getIdAccViaVisual());
            }
        }
        if (ok) {
            accidenteLugar.setIdAccidente(accidente);
            accidenteLugarFacadeLocal.create(accidenteLugar);
        } else {
            accidenteLugarFacadeLocal.edit(accidenteLugar);
        }
        MovilidadUtil.addSuccessMessage("Proceso realizado con éxito");
    }

    public void migrarVictimas(AccInformeVic aiv) {
        try {
            mapAccidenteVictima.clear();
            List<AccidenteVictima> victimaAll = accidenteVictimaFacadeLocal.estadoReg(accidente.getIdAccidente());
            for (AccidenteVictima avic : victimaAll) {
                mapAccidenteVictima.put(avic.getCedula(), avic);
            }
            if (aiv.getNroDoc() != null && !aiv.getNroDoc().isEmpty()) {
                AccidenteVictima accVic = mapAccidenteVictima.get(aiv.getNroDoc());
                if (accVic != null) {
                    if (accVic.getNombres() != null && accVic.getNombres().isEmpty()) {
                        accVic.setNombres(aiv.getNombres());
                    }
                    if (accVic.getNombres() == null) {
                        accVic.setNombres(aiv.getNombres());
                    }
                    if (accVic.getFechaNcto() == null) {
                        accVic.setFechaNcto(aiv.getFechaNac());
                    }
                    if (accVic.getGenero() == null) {
                        accVic.setGenero(aiv.getSexo());
                    }
                    if (accVic.getDireccion() != null && accVic.getDireccion().isEmpty()) {
                        accVic.setDireccion(aiv.getDireccion());
                    }
                    if (accVic.getDireccion() == null) {
                        accVic.setDireccion(aiv.getDireccion());
                    }
                    if (accVic.getCelular() != null && accVic.getCelular().isEmpty()) {
                        accVic.setCelular(aiv.getTelefono());
                    }
                    if (accVic.getCelular() == null) {
                        accVic.setCelular(aiv.getTelefono());
                    }
                    if (accVic.getCentroAsistencial() != null && accVic.getCentroAsistencial().isEmpty()) {
                        accVic.setCentroAsistencial(aiv.getSitioAtencion());
                    }
                    if (accVic.getCentroAsistencial() == null) {
                        accVic.setCentroAsistencial(aiv.getSitioAtencion());
                    }
                    accVic.setModificado(new Date());
                    accidenteVictimaFacadeLocal.edit(accVic);
                } else {
                    accVic = new AccidenteVictima();
                    accVic.setNombres(aiv.getNombres());
                    accVic.setCedula(aiv.getNroDoc());
                    accVic.setFechaNcto(aiv.getFechaNac());
                    accVic.setGenero(aiv.getSexo());
                    accVic.setDireccion(aiv.getDireccion());
                    accVic.setCelular(aiv.getTelefono());
                    accVic.setCentroAsistencial(aiv.getSitioAtencion());
                    accVic.setIdAccidente(accidente);
                    accVic.setCreado(new Date());
                    accVic.setUsername(user.getUsername());
                    accVic.setEstadoReg(0);
                    accidenteVictimaFacadeLocal.create(accVic);
                }
                MovilidadUtil.addSuccessMessage("Proceso finalizado con éxito");
            } else {
                MovilidadUtil.addErrorMessage("No se puede realizar esta operación");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("No se puede realizar esta operación");
        }
    }

    public void migrarTestigo(AccInformeTestigo ait) {
        try {
            mapAccidenteTestigo.clear();
            List<AccidenteTestigo> findAll = accidenteTestigoFacadeLocal.estadoReg(accidente.getIdAccidente());
            for (AccidenteTestigo at : findAll) {
                mapAccidenteTestigo.put(at.getCedula(), at);
            }
            if (ait.getNroDoc() != null && !ait.getNroDoc().isEmpty()) {
                AccidenteTestigo accT = mapAccidenteTestigo.get(ait.getNroDoc());
                if (accT != null) {
                    if (accT.getNombres() != null && accT.getNombres().isEmpty()) {
                        accT.setNombres(ait.getNombres());
                    }
                    if (accT.getNombres() == null) {
                        accT.setNombres(ait.getNombres());
                    }
                    if (accT.getDireccion() != null && accT.getDireccion().isEmpty()) {
                        accT.setDireccion(ait.getDireccion());
                    }
                    if (accT.getDireccion() == null) {
                        accT.setDireccion(ait.getDireccion());
                    }
                    if (accT.getCelular() != null && accT.getCelular().isEmpty()) {
                        accT.setCelular(ait.getTelefono());
                    }
                    if (accT.getCelular() == null) {
                        accT.setCelular(ait.getTelefono());
                    }
                    accT.setModificado(new Date());
                    accidenteTestigoFacadeLocal.edit(accT);
                } else {
                    accT = new AccidenteTestigo();
                    accT.setNombres(ait.getNombres());
                    accT.setDireccion(ait.getDireccion());
                    accT.setCelular(ait.getTelefono());
                    accT.setCedula(ait.getNroDoc());
                    accT.setIdAccidente(accidente);
                    accT.setCreado(new Date());
                    accT.setEstadoReg(0);
                    accT.setUsername(user.getUsername());
                    accidenteTestigoFacadeLocal.create(accT);
                }
                MovilidadUtil.addSuccessMessage("Proceso finalizado con éxito");
            } else {
                MovilidadUtil.addErrorMessage("No se puede realizar esta operación");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("No se puede realizar esta operación");
        }
    }

    public void migrarVehCond(AccInformeVehCond aivc) {
        try {
            mapAccidenteVehiculo.clear();
            mapAccidenteConductor.clear();
            List<AccidenteVehiculo> vehiculoAll = accidenteVehiculoFacadeLocal.estadoReg(accidente.getIdAccidente());
            for (AccidenteVehiculo av : vehiculoAll) {
                mapAccidenteVehiculo.put(av.getPlaca(), av);
            }
            List<AccidenteConductor> empleadosAll = accidenteConductorFacadeLocal.estadoReg(accidente.getIdAccidente());
            for (AccidenteConductor ac : empleadosAll) {
                mapAccidenteConductor.put(ac.getCedula(), ac);
            }
            if (aivc.getPlaca() != null && !String.valueOf(aivc.getPlaca()).isEmpty()) {
                Integer idAccV = null;
                AccidenteVehiculo accV = mapAccidenteVehiculo.get(aivc.getPlaca());
                if (accV != null) {
                    if (accV.getMarca() != null && accV.getMarca().isEmpty()) {
                        accV.setMarca(aivc.getMarca());
                    }
                    if (accV.getMarca() == null) {
                        accV.setMarca(aivc.getMarca());
                    }
                    try {
                        if (accV.getModelo() == null) {
                            accV.setModelo(aivc.getModelo() != null ? Integer.valueOf(aivc.getModelo()) : null);
                        }
                    } catch (NumberFormatException e) {
                    }
                    if (accV.getIdAccTipoVehiculo() == null) {
                        accV.setIdAccTipoVehiculo(aivc.getIdAccTipoVehiculo() != null ? aivc.getIdAccTipoVehiculo() : null);
                    }
                    if (accV.getIdAccEmpresaOperadora() == null) {
                        accV.setIdAccEmpresaOperadora(aivc.getIdEmpresaOperadora() != null ? aivc.getIdEmpresaOperadora() : null);
                    }
                    if (accV.getObservaccion() != null && accV.getObservaccion().length() < 60000) {
                        String inmo = aivc.getInmovilizado() != null ? "Inmovilizado en: " + aivc.getInmovilizado() : "";
                        String disp = aivc.getDisposicion() != null ? "A Disposición de: " + aivc.getDisposicion() : "";
                        String color = aivc.getColor() != null ? "Color: " + aivc.getColor() : "";
                        String cTotal = "\n" + inmo + "\n" + disp + "\n" + color;
                        if (!accV.getObservaccion().contains(cTotal)) {
                            accV.setObservaccion(accV.getObservaccion() + cTotal);
                        }
                    } else {
                        String inmo = aivc.getInmovilizado() != null ? "Inmovilizado en: " + aivc.getInmovilizado() : "";
                        String disp = aivc.getDisposicion() != null ? "A Disposición de: " + aivc.getDisposicion() : "";
                        String color = aivc.getColor() != null ? "Color: " + aivc.getColor() : "";
                        accV.setObservaccion(inmo + "\n" + disp + "\n" + color);
                    }
                    accV.setModificado(new Date());
                    accidenteVehiculoFacadeLocal.edit(accV);
                } else {
                    accV = new AccidenteVehiculo();
                    accV.setIdAccidente(accidente);
                    accV.setPlaca(aivc.getPlaca());
                    accV.setMarca(aivc.getMarca());
                    try {
                        accV.setModelo(aivc.getModelo() != null ? Integer.valueOf(aivc.getModelo()) : null);
                    } catch (NumberFormatException e) {
                    }
                    accV.setIdAccTipoVehiculo(aivc.getIdAccTipoVehiculo() != null ? aivc.getIdAccTipoVehiculo() : null);
                    accV.setIdAccEmpresaOperadora(aivc.getIdEmpresaOperadora() != null ? aivc.getIdEmpresaOperadora() : null);
                    String inmo = aivc.getInmovilizado() != null ? "Inmovilizado en: " + aivc.getInmovilizado() : "";
                    String disp = aivc.getDisposicion() != null ? "A Disposición de: " + aivc.getDisposicion() : "";
                    String color = aivc.getColor() != null ? "Color: " + aivc.getColor() : "";
                    accV.setObservaccion(inmo + "\n" + disp + "\n" + color);
                    accV.setCreado(new Date());
                    accV.setUsername(user.getUsername());
                    accV.setEstadoReg(0);
                    accidenteVehiculoFacadeLocal.create(accV);
                    idAccV = accV.getIdAccidenteVehiculo();
                }
                AccidenteConductor accC = mapAccidenteConductor.get(aivc.getNroDocumento());
                if (accC != null) {
                    if (accC.getNombres() != null && accC.getNombres().isEmpty()) {
                        accC.setNombres(aivc.getNombres());
                    }
                    if (accC.getNombres() == null) {
                        accC.setNombres(aivc.getNombres());
                    }
                    if (accC.getFechaNcto() == null) {
                        accC.setFechaNcto(aivc.getFechaNac());
                    }
                    if (accC.getGenero() == null) {
                        accC.setGenero(aivc.getSexo());
                    }
                    if (accC.getDireccion() != null && accC.getDireccion().isEmpty()) {
                        accC.setDireccion(aivc.getDireccion());
                    }
                    if (accC.getDireccion() == null) {
                        accC.setDireccion(aivc.getDireccion());
                    }
                    if (accC.getCelular() != null && accC.getCelular().isEmpty()) {
                        accC.setCelular(aivc.getTelefono());
                    }
                    if (accC.getCelular() == null) {
                        accC.setCelular(aivc.getTelefono());
                    }
                    if (accC.getCentroAsistencial() != null && accC.getCentroAsistencial().isEmpty()) {
                        accC.setCentroAsistencial(aivc.getSitioAtencion());
                    }
                    if (accC.getCentroAsistencial() == null) {
                        accC.setCentroAsistencial(aivc.getSitioAtencion());
                    }
                    if (accC.getVictima() == null) {
                        accC.setVictima(aivc.getCondicion() != null ? 1 : null);
                    }
                    accC.setModificado(new Date());
                    accidenteConductorFacadeLocal.edit(accC);
                } else {
                    if (idAccV != null) {
                        accC = new AccidenteConductor();
                        accC.setIdAccidente(accidente);
                        accC.setIdAccidenteVehiculo(new AccidenteVehiculo(idAccV));
                        accC.setNombres(aivc.getNombres());
                        accC.setCedula(aivc.getNroDocumento());
                        accC.setFechaNcto(aivc.getFechaNac());
                        accC.setGenero(aivc.getSexo());
                        accC.setDireccion(aivc.getDireccion());
                        accC.setCelular(aivc.getTelefono());
                        accC.setTelefono(aivc.getTelefono());
                        accC.setCentroAsistencial(aivc.getSitioAtencion());
                        accC.setVictima(aivc.getCondicion() != null ? 1 : null);
                        accC.setCreado(new Date());
                        accC.setUsername(user.getUsername());
                        accC.setEstadoReg(0);
                        accidenteConductorFacadeLocal.create(accC);
                    }
                }
                MovilidadUtil.addSuccessMessage("Proceso finalizado con éxito");
            } else {
                MovilidadUtil.addErrorMessage("No se puede realizar esta operación");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("No se puede realizar esta operación");
        }
    }

    public void migrarVersionOpe() {
        if (accidente.getIdEmpleado() != null) {
            mapAccidenteConductor.clear();
            List<AccidenteConductor> empleadosAll = accidenteConductorFacadeLocal.estadoReg(accidente.getIdAccidente());
            for (AccidenteConductor ac : empleadosAll) {
                mapAccidenteConductor.put(ac.getCedula(), ac);
            }
            AccidenteConductor accC = mapAccidenteConductor.get(accidente.getIdEmpleado().getIdentificacion());
            if (accC != null) {
                if (accC.getVersion() != null) {
                    if (accInformeOpe.getVersionOperador() != null && !accC.getVersion().contains(accInformeOpe.getVersionOperador())) {
                        accC.setVersion(accC.getVersion() + "\n" + accInformeOpe.getVersionOperador());
                    }
                } else {
                    accC.setVersion(accInformeOpe.getVersionOperador());
                }
                accidenteConductorFacadeLocal.edit(accC);
            }
            MovilidadUtil.addSuccessMessage("Proceso finalizado con éxito");
            return;
        }
        MovilidadUtil.addErrorMessage("No se puede realizar esta operación");
    }

    public void migrarClaseAcc() {
        if (accidente != null) {
            if (accidente.getIdClase() == null) {
                accidente.setIdClase(accInformeOpe.getIdAccClase());
                accidenteFacadeLocal.edit(accidente);
            }
            MovilidadUtil.addSuccessMessage("Proceso finalizado con éxito");
            return;
        }
        MovilidadUtil.addErrorMessage("No se puede realizar esta operación");
    }

    public void migrarLugarMaster() {
        if (accInformeMaster != null) {
            boolean ok = false;
            accidenteLugar = accidenteLugarFacadeLocal.buscarPorAccidente(accidente.getIdAccidente());
            if (accidenteLugar == null) {
                accidenteLugar = new AccidenteLugar();
                ok = true;
            }
            if (accidenteLugar.getDireccion() != null && accidenteLugar.getDireccion().isEmpty()) {
                accidenteLugar.setDireccion(accInformeMaster.getLugar());
            }
            if (accidenteLugar.getDireccion() == null) {
                accidenteLugar.setDireccion(accInformeMaster.getLugar());
            }
            if (accidenteLugar.getIdPrgStoppoint() == null) {
                if (accInformeMaster.getIdPrgStoppoint() != null) {
                    accidenteLugar.setIdPrgStoppoint(accInformeMaster.getIdPrgStoppoint());
                }
            }
            if (accInformeMaster.getHoraEvento() != null && !accInformeMaster.getHoraEvento().isEmpty()) {
                Date dateEvento = Util.dateTimeFormat(Util.dateFormat(accidente.getFechaAcc())
                        + " "
                        + accInformeMaster.getHoraEvento()
                        + ":00");
                if (accidente.getFechaAsistencia() == null && dateEvento != null) {
                    accidente.setFechaAsistencia(dateEvento);
                    accidenteFacadeLocal.edit(accidente);
                }
            }
            if (accInformeMaster.getHoraFinEvento() != null && !accInformeMaster.getHoraFinEvento().isEmpty()) {
                Date dateEvento = Util.dateTimeFormat(Util.dateFormat(accidente.getFechaAcc())
                        + " "
                        + accInformeMaster.getHoraFinEvento()
                        + ":00");
                if (accidente.getFechaCierre() == null && dateEvento != null) {
                    accidente.setFechaCierre(dateEvento);
                    accidenteFacadeLocal.edit(accidente);
                }
            }
            if (ok) {
                accidenteLugar.setIdAccidente(accidente);
                accidenteLugarFacadeLocal.create(accidenteLugar);
            } else {
                accidenteLugarFacadeLocal.edit(accidenteLugar);
            }
            MovilidadUtil.addSuccessMessage("Proceso realizado con éxito");
        } else {
            MovilidadUtil.addErrorMessage("No se puede realizar esta operación");
        }
    }

    // informe master gestión
    public void migrarMasterVehCond(AccInformeMasterVehCond aivc) {
        try {
            mapAccidenteVehiculo.clear();
            mapAccidenteConductor.clear();
            List<AccidenteVehiculo> vehiculoAll = accidenteVehiculoFacadeLocal.estadoReg(accidente.getIdAccidente());
            for (AccidenteVehiculo av : vehiculoAll) {
                mapAccidenteVehiculo.put(av.getPlaca(), av);
            }
            List<AccidenteConductor> empleadosAll = accidenteConductorFacadeLocal.estadoReg(accidente.getIdAccidente());
            for (AccidenteConductor ac : empleadosAll) {
                mapAccidenteConductor.put(ac.getCedula(), ac);
            }
            if (aivc.getPlaca() != null && !String.valueOf(aivc.getPlaca()).isEmpty()) {
                Integer idAccV = null;
                AccidenteVehiculo accV = mapAccidenteVehiculo.get(aivc.getPlaca());
                if (accV != null) {
                    try {
                        if (accV.getModelo() == null) {
                            accV.setModelo(aivc.getModelo() != null ? Integer.valueOf(aivc.getModelo()) : null);
                        }
                    } catch (NumberFormatException e) {
                    }
                    if (accV.getCodigoVehiculo() != null && accV.getCodigoVehiculo().isEmpty()) {
                        accV.setCodigoVehiculo(aivc.getCodigo());
                    }
                    if (accV.getCodigoVehiculo() == null) {
                        accV.setCodigoVehiculo(aivc.getCodigo());
                    }
                    if (accV.getObservaccion() != null && accV.getObservaccion().length() < 60000) {
                        String inmo = aivc.getInmovilizado() != null ? (aivc.getInmovilizado().equals(1) ? "Inmovilizado: Sí" : "Inmovilizado: NO") : "";
                        String color = aivc.getColor() != null ? "Color: " + aivc.getColor() : "";
                        String codHipo = aivc.getCodigoHipotesis() != null ? "Código de Hipotesis: " + aivc.getCodigoHipotesis() : "";
                        String hipo = aivc.getHipotesis() != null ? "Hipotesis: " + aivc.getHipotesis() : "";
                        String cTotal = "\n" + inmo + "\n" + color + "\n" + codHipo + "\n" + hipo;
                        if (!accV.getObservaccion().contains(cTotal)) {
                            accV.setObservaccion(accV.getObservaccion() + cTotal);
                        }
                    } else {
                        String inmo = aivc.getInmovilizado() != null ? (aivc.getInmovilizado().equals(1) ? "Inmovilizado: Sí" : "Inmovilizado: NO") : "";
                        String color = aivc.getColor() != null ? "Color: " + aivc.getColor() : "";
                        String codHipo = aivc.getCodigoHipotesis() != null ? "Código de Hipotesis: " + aivc.getCodigoHipotesis() : "";
                        String hipo = aivc.getHipotesis() != null ? "Hipotesis: " + aivc.getHipotesis() : "";
                        accV.setObservaccion(inmo + "\n" + color + "\n" + codHipo + "\n" + hipo);
                    }
                    accV.setModificado(new Date());
                    accidenteVehiculoFacadeLocal.edit(accV);
                } else {
                    accV = new AccidenteVehiculo();
                    accV.setIdAccidente(accidente);
                    accV.setPlaca(aivc.getPlaca());
                    accV.setCodigoVehiculo(aivc.getCodigo());
                    try {
                        accV.setModelo(aivc.getModelo() != null ? Integer.valueOf(aivc.getModelo()) : null);
                    } catch (NumberFormatException e) {
                    }
                    String inmo = aivc.getInmovilizado() != null ? (aivc.getInmovilizado().equals(1) ? "Inmovilizado: Sí" : "Inmovilizado: NO") : "";
                    String color = aivc.getColor() != null ? "Color: " + aivc.getColor() : "";
                    String codHipo = aivc.getCodigoHipotesis() != null ? "Código de Hipotesis: " + aivc.getCodigoHipotesis() : "";
                    String hipo = aivc.getHipotesis() != null ? "Hipotesis: " + aivc.getHipotesis() : "";
                    accV.setObservaccion(inmo + "\n" + color + "\n" + codHipo + "\n" + hipo);
                    accV.setCreado(new Date());
                    accV.setUsername(user.getUsername());
                    accV.setEstadoReg(0);
                    accidenteVehiculoFacadeLocal.create(accV);
                    idAccV = accV.getIdAccidenteVehiculo();
                }
                AccidenteConductor accC = mapAccidenteConductor.get(aivc.getIdentificacion());
                if (accC != null) {
                    if (accC.getNombres() != null && accC.getNombres().isEmpty()) {
                        accC.setNombres(aivc.getNombres());
                    }
                    if (accC.getNombres() == null) {
                        accC.setNombres(aivc.getNombres());
                    }
                    if (accC.getGenero() == null) {
                        accC.setGenero(aivc.getSexo());
                    }
                    if (accC.getCelular() != null && accC.getCelular().isEmpty()) {
                        accC.setCelular(aivc.getTelefono());
                    }
                    if (accC.getCelular() == null) {
                        accC.setCelular(aivc.getTelefono());
                    }
                    accC.setModificado(new Date());
                    accidenteConductorFacadeLocal.edit(accC);
                } else {
                    if (idAccV != null) {
                        accC = new AccidenteConductor();
                        accC.setIdAccidente(accidente);
                        accC.setIdAccidenteVehiculo(new AccidenteVehiculo(idAccV));
                        accC.setNombres(aivc.getNombres());
                        accC.setCedula(aivc.getIdentificacion());
                        accC.setGenero(aivc.getSexo());
                        accC.setCelular(aivc.getTelefono());
                        accC.setTelefono(aivc.getTelefono());
                        accC.setCreado(new Date());
                        accC.setUsername(user.getUsername());
                        accC.setEstadoReg(0);
                        accidenteConductorFacadeLocal.create(accC);
                    }
                }
                MovilidadUtil.addSuccessMessage("Proceso finalizado con éxito");
            } else {
                MovilidadUtil.addErrorMessage("No se puede realizar esta operación");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("No se puede realizar esta operación");
        }
    }

    public void migrarMasterVictimas(AccInformeMasterVic aiv) {
        try {
            mapAccidenteVictima.clear();
            List<AccidenteVictima> victimaAll = accidenteVictimaFacadeLocal.estadoReg(accidente.getIdAccidente());
            for (AccidenteVictima avic : victimaAll) {
                mapAccidenteVictima.put(avic.getCedula(), avic);
            }
            if (aiv.getNroDoc() != null && !aiv.getNroDoc().isEmpty()) {
                AccidenteVictima accVic = mapAccidenteVictima.get(aiv.getNroDoc());
                if (accVic != null) {
                    if (accVic.getNombres() != null && accVic.getNombres().isEmpty()) {
                        accVic.setNombres(aiv.getNombres());
                    }
                    if (accVic.getNombres() == null) {
                        accVic.setNombres(aiv.getNombres());
                    }
                    if (accVic.getFechaNcto() == null) {
                        accVic.setFechaNcto(aiv.getFechaNac());
                    }
                    if (accVic.getGenero() == null) {
                        accVic.setGenero(aiv.getSexo());
                    }
                    if (accVic.getDireccion() != null && accVic.getDireccion().isEmpty()) {
                        accVic.setDireccion(aiv.getDireccion());
                    }
                    if (accVic.getDireccion() == null) {
                        accVic.setDireccion(aiv.getDireccion());
                    }
                    if (accVic.getCelular() != null && accVic.getCelular().isEmpty()) {
                        accVic.setCelular(aiv.getTelefono());
                    }
                    if (accVic.getCelular() == null) {
                        accVic.setCelular(aiv.getTelefono());
                    }
                    if (accVic.getCentroAsistencial() != null && accVic.getCentroAsistencial().isEmpty()) {
                        accVic.setCentroAsistencial(aiv.getSitioAtencion());
                    }
                    if (accVic.getCentroAsistencial() == null) {
                        accVic.setCentroAsistencial(aiv.getSitioAtencion());
                    }
                    if (accVic.getLesiones() != null && accVic.getLesiones().length() < 60000) {
                        String diag = aiv.getDiagnostico() != null ? (aiv.getDiagnostico().length() < 5000 ? "\n" + aiv.getDiagnostico() : "") : "";
                        if (!accVic.getLesiones().contains(diag)) {
                            accVic.setLesiones(accVic.getLesiones() + diag);
                        }
                    }
                    if (accVic.getLesiones() == null) {
                        accVic.setLesiones(aiv.getDiagnostico());
                    }
                    if (accVic.getObservacion() != null) {
                        String eps = aiv.getEps() != null ? "\nEps: " + aiv.getEps() : "";
                        if (!accVic.getObservacion().contains(eps)) {
                            accVic.setObservacion(accVic.getObservacion() + eps);
                        }
                    } else {
                        accVic.setObservacion(aiv.getEps());
                    }
                    accVic.setModificado(new Date());
                    accidenteVictimaFacadeLocal.edit(accVic);
                } else {
                    accVic = new AccidenteVictima();
                    accVic.setNombres(aiv.getNombres());
                    accVic.setCedula(aiv.getNroDoc());
                    accVic.setFechaNcto(aiv.getFechaNac());
                    accVic.setGenero(aiv.getSexo());
                    accVic.setDireccion(aiv.getDireccion());
                    accVic.setCelular(aiv.getTelefono());
                    accVic.setCentroAsistencial(aiv.getSitioAtencion());
                    accVic.setLesiones(aiv.getDiagnostico());
                    accVic.setObservacion(aiv.getEps());
                    accVic.setIdAccidente(accidente);
                    accVic.setCreado(new Date());
                    accVic.setUsername(user.getUsername());
                    accVic.setEstadoReg(0);
                    accidenteVictimaFacadeLocal.create(accVic);
                }
                MovilidadUtil.addSuccessMessage("Proceso finalizado con éxito");
            } else {
                MovilidadUtil.addErrorMessage("No se puede realizar esta operación");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("No se puede realizar esta operación");
        }
    }

    public void migrarMasterTestigo(AccInformeMasterTestigo ait) {
        try {
            mapAccidenteTestigo.clear();
            List<AccidenteTestigo> findAll = accidenteTestigoFacadeLocal.estadoReg(accidente.getIdAccidente());
            for (AccidenteTestigo at : findAll) {
                mapAccidenteTestigo.put(at.getCedula(), at);
            }
            if (ait.getIdentificacion() != null && !ait.getIdentificacion().isEmpty()) {
                AccidenteTestigo accT = mapAccidenteTestigo.get(ait.getIdentificacion());
                if (accT != null) {
                    if (accT.getNombres() != null && accT.getNombres().isEmpty()) {
                        accT.setNombres(ait.getNombres());
                    }
                    if (accT.getNombres() == null) {
                        accT.setNombres(ait.getNombres());
                    }
                    if (accT.getDireccion() != null && accT.getDireccion().isEmpty()) {
                        accT.setDireccion(ait.getDireccion());
                    }
                    if (accT.getDireccion() == null) {
                        accT.setDireccion(ait.getDireccion());
                    }
                    if (accT.getCelular() != null && accT.getCelular().isEmpty()) {
                        accT.setCelular(ait.getTelefono());
                    }
                    if (accT.getCelular() == null) {
                        accT.setCelular(ait.getTelefono());
                    }
                    accT.setModificado(new Date());
                    accidenteTestigoFacadeLocal.edit(accT);
                } else {
                    accT = new AccidenteTestigo();
                    accT.setNombres(ait.getNombres());
                    accT.setDireccion(ait.getDireccion());
                    accT.setCelular(ait.getTelefono());
                    accT.setCedula(ait.getIdentificacion());
                    accT.setIdAccidente(accidente);
                    accT.setCreado(new Date());
                    accT.setEstadoReg(0);
                    accT.setUsername(user.getUsername());
                    accidenteTestigoFacadeLocal.create(accT);
                }
                MovilidadUtil.addSuccessMessage("Proceso finalizado con éxito");
            } else {
                MovilidadUtil.addErrorMessage("No se puede realizar esta operación");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("No se puede realizar esta operación");
        }
    }
}
