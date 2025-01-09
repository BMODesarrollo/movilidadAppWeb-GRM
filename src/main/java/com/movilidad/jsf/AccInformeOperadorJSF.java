/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccCausaFacadeLocal;
import com.movilidad.ejb.AccCausaRaizFacadeLocal;
import com.movilidad.ejb.AccCausaSubFacadeLocal;
import com.movilidad.ejb.AccChoqueInformeFacadeLocal;
import com.movilidad.ejb.AccInformeOpeCausalidadFacadeLocal;
import com.movilidad.ejb.AccInformeOpeFacadeLocal;
import com.movilidad.ejb.AccInformeTestigoFacadeLocal;
import com.movilidad.ejb.AccInformeVehCondFacadeLocal;
import com.movilidad.ejb.AccInformeVicFacadeLocal;
import com.movilidad.ejb.AccObjetoFijoInformeFacadeLocal;
import com.movilidad.ejb.AccVisualInformeFacadeLocal;
import com.movilidad.ejb.AccidenteLugarDemarFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.EmpleadoTipoIdentificacionFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.AccCausa;
import com.movilidad.model.AccCausaRaiz;
import com.movilidad.model.AccCausaSub;
import com.movilidad.model.AccChoqueInforme;
import com.movilidad.model.AccClase;
import com.movilidad.model.AccEmpresaOperadora;
import com.movilidad.model.AccInformeOpe;
import com.movilidad.model.AccInformeOpeCausalidad;
import com.movilidad.model.AccInformeTestigo;
import com.movilidad.model.AccInformeVehCond;
import com.movilidad.model.AccInformeVic;
import com.movilidad.model.AccObjetoFijo;
import com.movilidad.model.AccObjetoFijoInforme;
import com.movilidad.model.AccTipoServ;
import com.movilidad.model.AccTipoVehiculo;
import com.movilidad.model.AccVehFalla;
import com.movilidad.model.AccViaCalzadas;
import com.movilidad.model.AccViaCarriles;
import com.movilidad.model.AccViaClima;
import com.movilidad.model.AccViaCondicion;
import com.movilidad.model.AccViaDemarcacion;
import com.movilidad.model.AccViaDiseno;
import com.movilidad.model.AccViaEstado;
import com.movilidad.model.AccViaGeometrica;
import com.movilidad.model.AccViaIluminacion;
import com.movilidad.model.AccViaMaterial;
import com.movilidad.model.AccViaSector;
import com.movilidad.model.AccViaSemaforo;
import com.movilidad.model.AccViaTroncal;
import com.movilidad.model.AccViaUtilizacion;
import com.movilidad.model.AccViaVisual;
import com.movilidad.model.AccViaZona;
import com.movilidad.model.AccVisualInforme;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteLugarDemar;
import com.movilidad.model.Empleado;
import com.movilidad.model.EmpleadoTipoIdentificacion;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.DocumentUtil;
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
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "accInformeOperadorJSF")
@ViewScoped
public class AccInformeOperadorJSF implements Serializable {

    @EJB
    private EmpleadoTipoIdentificacionFacadeLocal empleadoTipoIdentificacionFacadeLocal;
    @EJB
    private AccInformeOpeFacadeLocal accInformeOpeFacadeLocal;
    @EJB
    private AccInformeVehCondFacadeLocal accInformeVehCondFacadeLocal;
    @EJB
    private AccInformeVicFacadeLocal accInformeVicFacadeLocal;
    @EJB
    private AccInformeTestigoFacadeLocal accInformeTestigoFacadeLocal;
    @EJB
    private AccidenteLugarDemarFacadeLocal accidenteLugarDemarFacadeLocal;
    @EJB
    private AccObjetoFijoInformeFacadeLocal accObjetoFijoInformeFacadeLocal;
    @EJB
    private AccChoqueInformeFacadeLocal accChoqueInformeFacadeLocal;
    @EJB
    private AccVisualInformeFacadeLocal accVisualInformeFacadeLocal;
    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;
    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    private AccCausaFacadeLocal accCausaFacadeLocal;
    @EJB
    private AccCausaSubFacadeLocal accCausaSubFacadeLocal;
    @EJB
    private AccCausaRaizFacadeLocal accCausaRaizFacadeLocal;
    @EJB
    private AccInformeOpeCausalidadFacadeLocal accInformeOpeCausalidadFacadeLocal;

    private AccInformeOpe accInformeOpe;
    private AccInformeVehCond accInformeVehCond;
    private AccInformeVic accInformeVic;
    private AccInformeTestigo accInformeTestigo;
    private AccObjetoFijo accObjetoFijo;
    private AccInformeOpeCausalidad accInformeOpeCausalidad;

    private UploadedFile fileAnexo;
    private UploadedFile fileCroquis;

    private List<EmpleadoTipoIdentificacion> listEmpleadoTipoIdentificaicion;
    private List<AccInformeVehCond> listAccInformeVehCond;
    private List<AccInformeVic> listAccInformeVic;
    private List<AccInformeTestigo> listAccInformeTestigo;
    private List<AccInformeOpeCausalidad> listAccInformeOpeCausalidad;

    private Integer[] accObjetoFijoArray;
    private Integer[] accTipoVehArray;
    private Integer[] accDemarcacionArray;
    private Integer[] accViaVisualArray;

    private String c_latLog;
    private boolean b_agente;
    private String c_codVehiculo;
    private Integer i_codTm;
    private DefaultMapModel simpleModel;

    // private String trazo;
    private Integer i_idAccClase;
    private Integer i_idAccViaTroncal;
    private Integer i_idAccViaSector;
    private Integer i_idAccViaZona;
    private Integer i_idAccViaDiseno;
    private Integer i_idAccViaClima;
    private Integer i_idAccViaGeometrica;
    private Integer i_idAccViaUtilizacion;
    private Integer i_idAccViaCalzada;
    private Integer i_idAccViaMaterial;
    private Integer i_idAccViaCarriles;
    private Integer i_idAccViaEstado;
    private Integer i_idAccViaCondicion;
    private Integer i_idAccViaIluminacion;
    private Integer i_idAccViaSemaforo;
    private Integer i_idEmpresaOperadora;
    private Integer i_idAccTipoVehiculo;
    private Integer i_idTipoServ;
    private Integer i_idAccFalla;

    private Integer i_tpIdentiVeh;
    private Integer i_tpIdentiPropietario;
    private Integer i_tpIdentiVictima;
    private Integer i_tpIdentiTestigo;

    private Integer i_codTmPrincipal;

    // Árbol de Causalidad
    private List<AccCausa> listAccCausa;
    private List<AccCausaSub> listAccCausaSub;
    private List<AccCausaRaiz> listAccCausaRaiz;

    private Integer i_idAccCausaRaiz;
    private Integer i_idAccCausaSub;
    private Integer i_idAccArbol;
    private Integer i_idAccCausa;
    private String c_tituloPregunta;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccInformeOperadorJSF() {
    }

    /**
     * Inicialiazando las variables dispuestas en esta clase.
     */
    @PostConstruct
    public void init() {
        c_latLog = "4.646189 , -74.078540"; //coordenadas zona El Campin
        simpleModel = new DefaultMapModel();
        listEmpleadoTipoIdentificaicion = empleadoTipoIdentificacionFacadeLocal.findAll();
        accInformeVehCond = new AccInformeVehCond();
        accInformeVic = new AccInformeVic();
        accInformeTestigo = new AccInformeTestigo();
        accObjetoFijo = new AccObjetoFijo();
        accInformeOpeCausalidad = new AccInformeOpeCausalidad();
        listAccInformeVehCond = new ArrayList<>();
        listAccInformeVic = new ArrayList<>();
        listAccInformeTestigo = new ArrayList<>();
        listAccInformeOpeCausalidad = new ArrayList<>();
        b_agente = false;
        c_codVehiculo = "";
        i_idAccClase = null;
        i_idAccViaTroncal = null;
        i_idAccViaSector = null;
        i_idAccViaZona = null;
        i_idAccViaDiseno = null;
        i_idAccViaClima = null;
        i_idAccViaGeometrica = null;
        i_idAccViaUtilizacion = null;
        i_idAccViaCalzada = null;
        i_idAccViaCarriles = null;
        i_idAccViaEstado = null;
        i_idAccViaMaterial = null;
        i_idAccViaCondicion = null;
        i_idAccViaIluminacion = null;
        i_idAccViaSemaforo = null;
        i_idEmpresaOperadora = null;
        i_idAccTipoVehiculo = null;
        i_idTipoServ = null;
        i_idAccFalla = null;
        i_tpIdentiVeh = null;
        i_tpIdentiPropietario = null;
        i_tpIdentiVictima = null;
        i_tpIdentiTestigo = null;
        i_codTmPrincipal = null;
        i_idAccCausaRaiz = null;
        i_idAccCausaSub = null;
        i_idAccCausa = null;
        i_idAccArbol = null;
        c_tituloPregunta = "";
    }

    /**
     * Persistir objeto AccInformeOpe desde un objeto Accidente
     *
     * @param acc Objeto Accidente
     * @return Objeto AccInformeOpe
     */
    public AccInformeOpe setInformeFromAccidente(Accidente acc) {
        AccInformeOpe aio = new AccInformeOpe();
        if (acc != null) {
            aio.setIdAccidente(acc);
            aio.setUsername(acc.getUsername());
            aio.setCreado(acc.getCreado());
            aio.setModificado(acc.getModificado());
            accInformeOpeFacadeLocal.edit(aio);
        }
        return aio;
    }

    /**
     * reinicia variables del bean
     *
     * @param i int
     */
    public void actualizarOpcion(int i) {
        switch (i) {
            case 1: ;
                i_idAccCausaRaiz = null;
                i_idAccCausaSub = null;
                i_idAccCausa = null;
                listAccCausa = null;
                listAccCausaRaiz = null;
                listAccCausaSub = null;
                c_tituloPregunta = "";
                break;
            case 2: ;
                i_idAccCausaRaiz = null;
                i_idAccCausaSub = null;
                listAccCausaRaiz = null;
                listAccCausaSub = null;
                break;
        }
    }

    /**
     * Agrega objeto AccInformeOpeCausalidad a la lista de objetos
     * AccInformeOpeCausalidad
     */
    public void agregarCausalidad() {
        if (i_idAccCausaSub != null) {
            accInformeOpeCausalidad.setIdCausaSub(new AccCausaSub(i_idAccCausaSub));
        } else {
            MovilidadUtil.addErrorMessage("Causa Sub es requerido");
            return;
        }
        if (accInformeOpeCausalidad.getRespuesta() == null) {
            MovilidadUtil.addErrorMessage("Respuesta a la pregunta es requerida");
            return;
        }
        if (accInformeOpeCausalidad.getRespuesta().isEmpty()) {
            MovilidadUtil.addErrorMessage("Respuesta a la pregunta es requerida");
            return;
        }
        if (i_idAccCausaRaiz != null) {
            accInformeOpeCausalidad.setIdCausaRaiz(new AccCausaRaiz(i_idAccCausaRaiz));
        }
        listAccInformeOpeCausalidad.add(accInformeOpeCausalidad);
        accInformeOpeCausalidad = new AccInformeOpeCausalidad();
        MovilidadUtil.addSuccessMessage("Causalidad agregada a la lista");
        actualizarOpcion(1);
    }

    /**
     * Agrega objeto AccInformeVehCond a la lista de objetos AccInformeVehCond
     */
    public void agregarAccInformeVehCond() {
        if (accInformeVehCond.getNombres() == null) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeVehCond.getNombres().isEmpty()) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeVehCond.getPlaca() == null) {
            MovilidadUtil.addErrorMessage("Placa es requerida");
            return;
        }
        if (accInformeVehCond.getPlaca().isEmpty()) {
            MovilidadUtil.addErrorMessage("Placa es requerida");
            return;
        }
        cargarObjVehCond();
        listAccInformeVehCond.add(accInformeVehCond);
        accInformeVehCond = new AccInformeVehCond();
        resetId();
        MovilidadUtil.addSuccessMessage("Vehículo-Conductor agregado a la lista");
    }

    /**
     * Agrega objeto AccInformeVic a la lista de objetos AccInformeVic
     */
    public void agregarAccInformeVic() {
        if (accInformeVic.getNombres() == null) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeVic.getNombres().isEmpty()) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeVic.getSexo() == null) {
            MovilidadUtil.addErrorMessage("Sexo de la victima es requerido");
            return;
        }
        if (i_tpIdentiVictima != null) {
            accInformeVic.setIdTipoIdentificacion(new EmpleadoTipoIdentificacion(i_tpIdentiVictima));
        }
        if (accInformeVic.getCondicion() == null) {
            MovilidadUtil.addErrorMessage("Condición de la víctima es requerido");
            return;
        }
        listAccInformeVic.add(accInformeVic);
        accInformeVic = new AccInformeVic();
        i_tpIdentiVictima = null;
        MovilidadUtil.addSuccessMessage("Victima agregada a la lista");
    }

    /**
     * Agrega objeto AccInformeTestigo a la lista de objetos AccInformeTestigo
     */
    public void agregarAccInformeTestigo() {
        if (accInformeTestigo.getNombres() == null) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeTestigo.getNombres().isEmpty()) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeTestigo.getTelefono() == null) {
            MovilidadUtil.addErrorMessage("Teléfono es requerido");
            return;
        }
        if (accInformeTestigo.getTelefono().isEmpty()) {
            MovilidadUtil.addErrorMessage("Teléfono es requerido");
            return;
        }
        if (i_tpIdentiTestigo != null) {
            accInformeTestigo.setIdTipoIdentificacion(new EmpleadoTipoIdentificacion(i_tpIdentiTestigo));
        }
        listAccInformeTestigo.add(accInformeTestigo);
        accInformeTestigo = new AccInformeTestigo();
        i_tpIdentiTestigo = null;
        MovilidadUtil.addSuccessMessage("Testigo agregado a la lista");
    }

    /**
     * Persiste en base de datos toda la data obtenida en el formulario.
     */
    public void guardarTodo() {
        try {
            cargarObjetos();
            if (validarDatos()) {
                return;
            }
            List<AccInformeOpe> accInformeOpeList = accInformeOpe.getIdAccidente().getAccInformeOpeList();
            if (accInformeOpeList != null && !accInformeOpeList.isEmpty()) {
                MovilidadUtil.addErrorMessage("Error Accidente ya cuenta con informe de operador registrado en el sistema");
                return;
            }
            AccInformeOpe aio = guardarAccInformeOpe();
            persistirAccInformeTestigo(aio);
            persistirAccInformeVehCond(aio);
            persistirAccInformeVic(aio);
            persistirDemarcacion(aio);
            persistirObjetoFijo(aio);
            persistirTipoVehiculo(aio);
            persistrVisual(aio);
            persistirArbolCausalidad(aio);
            guardarArchivoAnexo(aio);
            guardarCroquis(aio);
            cancelarTodo();
            generarCopiaPdf(aio.getIdAccInformeOpe());
            MovilidadUtil.addSuccessMessage("Informe Operador registrado Exitosamente");
            PrimeFaces.current().executeScript("location.reload()");
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("Error al procesar la información");
        }
    }

    @Transactional
    AccInformeOpe guardarAccInformeOpe() {
        accInformeOpe.setEstadoReg(0);
        accInformeOpe.setAgente(b_agente ? 1 : 0);
        accInformeOpe.setUsername(user.getUsername());
        accInformeOpe.setCreado(new Date());
        accInformeOpe.setModificado(new Date());
        accInformeOpeFacadeLocal.create(accInformeOpe);
        return accInformeOpe;
    }

    /**
     * Reinicia todo el bean en su estado inicial.
     */
    public void cancelarTodo() {
        init();
        resetId();
        accObjetoFijoArray = null;
        accTipoVehArray = null;
        accDemarcacionArray = null;
        accViaVisualArray = null;
        accInformeOpe = null;
    }

    /**
     * Permite capturar el evento, entregando un objeto con las coordendadas
     * seleccionadas.
     *
     * @param event
     */
    public void onPointSelect(PointSelectEvent event) {
        simpleModel = new DefaultMapModel();
        LatLng latLng = event.getLatLng();
        c_latLog = event.getLatLng().getLat() + " , " + event.getLatLng().getLng();
        simpleModel.addOverlay(new Marker(latLng, "Punto Seleccionado"));
        accInformeOpe.setLatitude(BigDecimal.valueOf(latLng.getLat()));
        accInformeOpe.setLongitude(BigDecimal.valueOf(latLng.getLng()));
    }

    void guardarCroquis(AccInformeOpe aio) {
        if (fileCroquis != null) {
            if (fileCroquis.getContents().length > 0) {
                String path = MovilidadUtil.cargarArchivosAccidentalidad(fileCroquis, aio.getIdAccidente().getIdAccidente(), "InformeOperador", aio.getIdAccInformeOpe(), "croquis");
                if (path != null) {
                    aio.setPathCroquis(path);
                    accInformeOpeFacadeLocal.edit(aio);
                }
            }
        }
//        try {
//            if (!trazo.equals("")) {
//                String base64Image = trazo.split(",")[1];
//                byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
//             String guardarFoto = MovilidadUtil.guardarFoto(imageBytes, aio.getIdAccidente().getIdAccidente(), "InformeOperador", aio.getIdAccInformeOpe());
//                if (guardarFoto != null) {
//                    aio.setPathCroquis(guardarFoto);
//                    accInformeOpeFacadeLocal.edit(aio);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            MovilidadUtil.addErrorMessage("No se registró el croquis en el sistema - comunicar al departamento de sistemas");
//        }
    }

    void guardarArchivoAnexo(AccInformeOpe aio) {
        if (fileAnexo != null) {
            if (fileAnexo.getContents().length > 0) {
                String path = MovilidadUtil.cargarArchivosAccidentalidad(fileAnexo, aio.getIdAccidente().getIdAccidente(), "InformeOperador", aio.getIdAccInformeOpe(), "anexo");
                if (path != null) {
                    aio.setPathAnexo(path);
                    accInformeOpeFacadeLocal.edit(aio);
                }
            }
        }
    }

    void persistirAccInformeVehCond(AccInformeOpe aio) {
        for (AccInformeVehCond aivc : listAccInformeVehCond) {
            aivc.setIdAccInformeOpe(aio);
            accInformeVehCondFacadeLocal.edit(aivc);
        }
    }

    void persistirAccInformeVic(AccInformeOpe aio) {
        if (!listAccInformeVic.isEmpty()) {
            for (AccInformeVic aiv : listAccInformeVic) {
                aiv.setIdAccInformeOpe(aio);
                accInformeVicFacadeLocal.edit(aiv);
            }
        }
    }

    void persistirArbolCausalidad(AccInformeOpe aio) {
        for (AccInformeOpeCausalidad aioc : listAccInformeOpeCausalidad) {
            aioc.setIdAccInformeOpe(aio);
            accInformeOpeCausalidadFacadeLocal.edit(aioc);
        }
    }

    void persistirAccInformeTestigo(AccInformeOpe aio) {
        if (!listAccInformeTestigo.isEmpty()) {
            for (AccInformeTestigo ait : listAccInformeTestigo) {
                ait.setIdAccInformeOpe(aio);
                accInformeTestigoFacadeLocal.edit(ait);
            }
        }
    }

    void persistirObjetoFijo(AccInformeOpe aio) {
        if (accObjetoFijoArray != null) {
            for (Integer i : accObjetoFijoArray) {
                AccObjetoFijoInforme aofi = new AccObjetoFijoInforme();
                aofi.setIdAccInformeOpe(aio);
                aofi.setIdAccObjetoFijo(new AccObjetoFijo(i));
                accObjetoFijoInformeFacadeLocal.edit(aofi);
            }
        }
    }

    void persistirDemarcacion(AccInformeOpe aio) {
        if (accDemarcacionArray != null) {
            for (Integer i : accDemarcacionArray) {
                AccidenteLugarDemar ald = new AccidenteLugarDemar();
                ald.setIdAccViaDemarcacion(new AccViaDemarcacion(i));
                ald.setIdAccInformeOpe(aio.getIdAccInformeOpe());
                accidenteLugarDemarFacadeLocal.edit(ald);
            }
        }
    }

    void persistrVisual(AccInformeOpe aio) {
        if (accViaVisualArray != null) {
            for (Integer i : accViaVisualArray) {
                AccVisualInforme avi = new AccVisualInforme();
                avi.setIdAccInformeOpe(aio);
                avi.setIdAccViaVisual(new AccViaVisual(i));
                accVisualInformeFacadeLocal.edit(avi);
            }
        }

    }

    void persistirTipoVehiculo(AccInformeOpe aio) {
        if (accTipoVehArray != null) {
            for (Integer i : accTipoVehArray) {
                AccChoqueInforme aci = new AccChoqueInforme();
                aci.setIdAccInformeOpe(aio);
                aci.setIdAccTipoVehiculo(new AccTipoVehiculo(i));
                accChoqueInformeFacadeLocal.edit(aci);
            }
        }
    }

    void cargarObjVehCond() {
        if (i_idEmpresaOperadora != null) {
            accInformeVehCond.setIdEmpresaOperadora(new AccEmpresaOperadora(i_idEmpresaOperadora));
        }
        if (i_idAccTipoVehiculo != null) {
            accInformeVehCond.setIdAccTipoVehiculo(new AccTipoVehiculo(i_idAccTipoVehiculo));
        }
        if (i_idTipoServ != null) {
            accInformeVehCond.setIdTipoServ(new AccTipoServ(i_idTipoServ));
        }
        if (i_idAccFalla != null) {
            accInformeVehCond.setIdAccFalla(new AccVehFalla(i_idAccFalla));
        }
        if (i_tpIdentiVeh != null) {
            accInformeVehCond.setIdTipoIdentificacion(new EmpleadoTipoIdentificacion(i_tpIdentiVeh));
        }
        if (i_tpIdentiPropietario != null) {
            accInformeVehCond.setIdTipoIdenProp(new EmpleadoTipoIdentificacion(i_tpIdentiPropietario));
        }
    }

    void resetId() {
        i_idEmpresaOperadora = null;
        i_idAccTipoVehiculo = null;
        i_idTipoServ = null;
        i_idAccFalla = null;
        i_tpIdentiVeh = null;
        i_tpIdentiPropietario = null;
        i_codTm = null;
        c_codVehiculo = "";
    }

    /**
     * Permite buscar objeto Vehiculo de acuerdo al codigo de este.
     */
    public void buscarVehiculo() {
        if (c_codVehiculo.isEmpty() | c_codVehiculo.equals("")) {
            MovilidadUtil.addErrorMessage("No se encontraron registros");
            return;
        }
        Vehiculo vehiculoCodigo = vehiculoFacadeLocal.getVehiculoCodigo(c_codVehiculo);
        if (vehiculoCodigo != null) {
            cargarVehiculo(vehiculoCodigo);
            MovilidadUtil.addSuccessMessage("Código correcto para vehículo");
        } else {
            MovilidadUtil.addErrorMessage("No se encontraron registros");
        }
    }

    void cargarVehiculo(Vehiculo vehiculo) {
        if (vehiculo.getModelo() != null) {
            accInformeVehCond.setModelo(String.valueOf(vehiculo.getModelo()));
        }
        if (vehiculo.getPlaca() != null) {
            accInformeVehCond.setPlaca(vehiculo.getPlaca());
        }
        if (vehiculo.getColor() != null) {
            accInformeVehCond.setColor(vehiculo.getColor());
        }
        if (vehiculo.getIdVehiculoChasis() != null) {
            if (vehiculo.getIdVehiculoChasis().getNombreTipoChasis() != null) {
                accInformeVehCond.setMarca(vehiculo.getIdVehiculoChasis().getNombreTipoChasis());
            }
        }
    }

    /**
     * Permite buscar objeto Empleado de acuerdo al codigo de este.
     */
    public void buscarOperador() {
        if (i_codTm == null) {
            MovilidadUtil.addErrorMessage("No se encontraron registros");
            return;
        }
        Empleado empleado = empleadoFacadeLocal.getEmpleadoCodigoTM(i_codTm);
        if (empleado != null) {
            cargarOperador(empleado);
            MovilidadUtil.addSuccessMessage("Código Tm correcto para Operador");
        } else {
            MovilidadUtil.addErrorMessage("No se encontraron registros");
        }
        i_codTm = null;
    }

    void cargarOperador(Empleado e) {
        if (e.getIdentificacion() != null) {
            accInformeVehCond.setNroDocumento(e.getIdentificacion());
        }
        if (e.getNombres() != null) {
            accInformeVehCond.setNombres(e.getApellidos() + " " + e.getNombres());
        }
        if (e.getTelefonoFijo() != null) {
            accInformeVehCond.setTelefono(e.getTelefonoMovil());
        }
        if (e.getDireccion() != null) {
            accInformeVehCond.setDireccion(e.getDireccion());
        }
        if (e.getFechaNcto() != null) {
            accInformeVehCond.setFechaNac(e.getFechaNcto());
        }
        if (e.getGenero() != null) {
            accInformeVehCond.setSexo(e.getGenero());
        }
        if (e.getIdEmpleadoTipoIdentificacion() != null) {
            accInformeVehCond.setIdTipoIdentificacion(e.getIdEmpleadoTipoIdentificacion());
            i_tpIdentiVeh = e.getIdEmpleadoTipoIdentificacion().getIdEmpleadoTipoIdentificacion();
        }
        if (e.getIdEmpleadoMunicipio() != null) {
            accInformeVehCond.setCiudad(e.getIdEmpleadoMunicipio().getNombre());
        }
    }

    /**
     * Asigna null a la variable de tipo UploadedFile
     */
    public void cancelarCarga() {
        fileAnexo = null;
        MovilidadUtil.addSuccessMessage("Usted canceló la carga del archivo");
    }

    void cargarObjetos() {
        if (i_idAccClase != null) {
            accInformeOpe.setIdAccClase(new AccClase(i_idAccClase));
        }
        if (i_idAccViaTroncal != null) {
            accInformeOpe.setIdAccViaTroncal(new AccViaTroncal(i_idAccViaTroncal));
        }
        if (i_idAccViaSector != null) {
            accInformeOpe.setIdAccViaSector(new AccViaSector(i_idAccViaSector));
        }
        if (i_idAccViaZona != null) {
            accInformeOpe.setIdAccViaZona(new AccViaZona(i_idAccViaZona));
        }
        if (i_idAccViaDiseno != null) {
            accInformeOpe.setIdAccViaDiseno(new AccViaDiseno(i_idAccViaDiseno));
        }
        if (i_idAccViaClima != null) {
            accInformeOpe.setIdAccViaClima(new AccViaClima(i_idAccViaClima));
        }
        if (i_idAccViaGeometrica != null) {
            accInformeOpe.setIdAccViaGeometrica(new AccViaGeometrica(i_idAccViaGeometrica));
        }
        if (i_idAccViaUtilizacion != null) {
            accInformeOpe.setIdAccViaUtilizacion(new AccViaUtilizacion(i_idAccViaUtilizacion));
        }
        if (i_idAccViaCalzada != null) {
            accInformeOpe.setIdAccViaCalzada(new AccViaCalzadas(i_idAccViaCalzada));
        }
        if (i_idAccViaCarriles != null) {
            accInformeOpe.setIdAccViaCarriles(new AccViaCarriles(i_idAccViaCarriles));
        }
        if (i_idAccViaEstado != null) {
            accInformeOpe.setIdAccViaEstado(new AccViaEstado(i_idAccViaEstado));
        }
        if (i_idAccViaMaterial != null) {
            accInformeOpe.setIdAccViaMaterial(new AccViaMaterial(i_idAccViaMaterial));
        }
        if (i_idAccViaCondicion != null) {
            accInformeOpe.setIdAccViaCondicion(new AccViaCondicion(i_idAccViaCondicion));
        }
        if (i_idAccViaSemaforo != null) {
            accInformeOpe.setIdAccViaSemaforo(new AccViaSemaforo(i_idAccViaSemaforo));
        }
        if (i_idAccViaIluminacion != null) {
            accInformeOpe.setIdAccViaIluminacion(new AccViaIluminacion(i_idAccViaIluminacion));
        }
    }

    boolean validarDatos() {
        if (accInformeOpe.getCondicion() == null) {
            MovilidadUtil.addErrorMessage("Gravedad del accidente es requerido - Ventana #1");
            return true;
        }
        if (accInformeOpe.getIdAccClase() == null) {
            MovilidadUtil.addErrorMessage("Clase de accidente es requerido - Ventana #2");
            return true;
        }
        if (accInformeOpe.getLatitude() == null) {
            MovilidadUtil.addErrorMessage("Coordenadas son requeridas - Ventana #3");
            return true;
        }
        if (accInformeOpe.getDireccion() == null) {
            MovilidadUtil.addErrorMessage("Dirección es requerido - Ventana #3");
            return true;
        }
        if (accInformeOpe.getIdAccViaTroncal() == null) {
            MovilidadUtil.addErrorMessage("Troncal es requerido - Ventana #3");
            return true;
        }
        if (accInformeOpe.getHoraOcurrencia() == null) {
            MovilidadUtil.addErrorMessage("Hora de ocurrencia es requerido - Ventana #4");
            return true;
        }
        if (accInformeOpe.getHoraOcurrencia() == null) {
            MovilidadUtil.addErrorMessage("Hora de levantamiento es requerido - Ventana #4");
            return true;
        }
        if (accInformeOpe.getArea() == null) {
            MovilidadUtil.addErrorMessage("Área es requerido - Ventana #5");
            return true;
        }
        if (accDemarcacionArray == null) {
            MovilidadUtil.addErrorMessage("DEMARCACIÓN Y SEÑALES es requerido - Ventana #6");
            return true;
        }
        if (listAccInformeVehCond.isEmpty()) {
            MovilidadUtil.addErrorMessage("No a agregado Conductores, Vehículos y Propietarios - Ventana #7");
            return true;
        }
        if (accInformeOpe.getObservaciones() == null) {
            MovilidadUtil.addErrorMessage("Observación es requerida - Ventana #13");
            return true;
        }

        if (fileAnexo != null) {
            if (accInformeOpe.getAnexos() == null) {
                MovilidadUtil.addErrorMessage("Usted realizó la carga de un documento como anexo"
                        + " La descripción de este es requerido - Ventana #14");
                return true;
            }
            if (accInformeOpe.getAnexoNombres() == null) {
                MovilidadUtil.addErrorMessage("Usted realizó la carga de un documento como anexo"
                        + " Nombres y Apellidos es requerido - Ventana #14");
                return true;
            }
            if (accInformeOpe.getAnexoPlaca() == null) {
                MovilidadUtil.addErrorMessage("Usted realizó la carga de un documento como anexo"
                        + " Placa es requerido - Ventana #14");
                return true;
            }
            if (accInformeOpe.getAnexoEntidad() == null) {
                MovilidadUtil.addErrorMessage("Usted realizó la carga de un documento como anexo"
                        + " Entidad es requerido - Ventana #14");
                return true;
            }
            if (accInformeOpe.getAnexoFirma() == null) {
                MovilidadUtil.addErrorMessage("Usted realizó la carga de un documento como anexo"
                        + " Firma es requerido - Ventana #14");
                return true;
            }
        }
        if (listAccInformeOpeCausalidad.isEmpty()) {
            MovilidadUtil.addErrorMessage("Árbol de Causalidad es requerido - Ventana #15");
            return true;
        }
        if (accInformeOpe.getVersionOperador() == null) {
            MovilidadUtil.addErrorMessage("Versión del operador es requerida - Ventana #16");
            return true;
        }
        if (accInformeOpe.getFirma() == null) {
            MovilidadUtil.addErrorMessage("Firma es requerida - Ventana #17");
            return true;
        }
        if (accInformeOpe.getFirma().isEmpty() || accInformeOpe.getFirma().equals("")) {
            MovilidadUtil.addErrorMessage("Firma es requerida - Ventana #17");
            return true;
        }
        if (fileCroquis == null) {
            MovilidadUtil.addErrorMessage("Croquis del accidente es requerido - Ventana #9");
            return true;
        }
        return false;
    }

    /**
     * Permite validar que los datos requeridos en el formulario cumplan con
     * esta funcion.
     */
    public void validarFormulario() {
        cargarObjetos();
        if (validarDatos()) {
            return;
        }
        MovilidadUtil.addSuccessMessage("Todos los datos requeridos por el informe fueron diligenciados, puede proceder a guardar");
    }

    /**
     * Permite capturar el objeto que contiene la data con el documento
     * seleccioando por el usuario
     *
     * @param event Event
     */
    public void handleFileUpload(FileUploadEvent event) {
        fileAnexo = event.getFile();
        PrimeFaces.current().executeScript("PF('anexosDlg').hide();");
        MovilidadUtil.addSuccessMessage("Archivo cargado correctamente");
    }

     /**
     * Permite capturar el objeto que contiene la data con el documento
     * seleccioando por el usuario
     *
     * @param event Event
     */
    public void handleFileUploadCroquis(FileUploadEvent event) {
        fileCroquis = event.getFile();
        PrimeFaces.current().executeScript("PF('croquisDlg').hide();");
        MovilidadUtil.addSuccessMessage("Archivo cargado correctamente");
    }

    /**
     * Permite capturar el objeto AccInformeOpe
     *
     * @param event AccInformeOpe
     */
    public void cargarAccidente(SelectEvent event) {
        try {
            accInformeOpe = new AccInformeOpe();
            Accidente a = (Accidente) event.getObject();
            if (a.getIdVehiculo() == null) {
                MovilidadUtil.addErrorMessage("Este accidente aún no cuenta con vehículo asociado");
                return;
            }
            if (a.getIdEmpleado() == null) {
                MovilidadUtil.addErrorMessage("Este accidente aún no cuenta con empleado asociado");
                return;
            }
            accInformeOpe.setIdAccidente(a);
            accInformeOpe.setIdOperadorPrincipal(a.getIdEmpleado());
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al cargar Accidnete");
        }
    }

    void generarCopiaPdf(Integer id) {
        AccInformeOpe aioAux = accInformeOpeFacadeLocal.find(id);
        if (aioAux != null) {
            List<AccidenteLugarDemar> accidenteLugarDemarInformeOpe = accidenteLugarDemarFacadeLocal.getAccidenteLugarDemarInformeOpe(aioAux.getIdAccInformeOpe());
            List<AccObjetoFijoInforme> accObjetoFijoInformeInformeOpe = accObjetoFijoInformeFacadeLocal.getAccObjetoFijoInformeInformeOpe(aioAux.getIdAccInformeOpe());
            List<AccChoqueInforme> accChoqueInformeOpe = accChoqueInformeFacadeLocal.getAccChoqueInformeOpe(aioAux.getIdAccInformeOpe());
            List<AccVisualInforme> accVisualInformeOpe = accVisualInformeFacadeLocal.getAccVisualInformeOpe(aioAux.getIdAccInformeOpe());
            DocumentUtil.generarInformeOperaodor(aioAux, accidenteLugarDemarInformeOpe, accObjetoFijoInformeInformeOpe, accChoqueInformeOpe, accVisualInformeOpe);
        }
    }

    public List<EmpleadoTipoIdentificacion> getListEmpleadoTipoIdentificaicion() {
        return listEmpleadoTipoIdentificaicion;
    }

    public void setListEmpleadoTipoIdentificaicion(List<EmpleadoTipoIdentificacion> listEmpleadoTipoIdentificaicion) {
        this.listEmpleadoTipoIdentificaicion = listEmpleadoTipoIdentificaicion;
    }

    public String getC_latLog() {
        return c_latLog;
    }

    public void setC_latLog(String c_latLog) {
        this.c_latLog = c_latLog;
    }

    public DefaultMapModel getSimpleModel() {
        return simpleModel;
    }

    public void setSimpleModel(DefaultMapModel simpleModel) {
        this.simpleModel = simpleModel;
    }

//    public String getTrazo() {
//        return trazo;
//    }
//
//    public void setTrazo(String trazo) {
//        this.trazo = trazo;
//    }
    public AccInformeOpe getAccInformeOpe() {
        return accInformeOpe;
    }

    public void setAccInformeOpe(AccInformeOpe accInformeOpe) {
        this.accInformeOpe = accInformeOpe;
    }

    public AccInformeVehCond getAccInformeVehCond() {
        return accInformeVehCond;
    }

    public void setAccInformeVehCond(AccInformeVehCond accInformeVehCond) {
        this.accInformeVehCond = accInformeVehCond;
    }

    public AccInformeVic getAccInformeVic() {
        return accInformeVic;
    }

    public void setAccInformeVic(AccInformeVic accInformeVic) {
        this.accInformeVic = accInformeVic;
    }

    public AccInformeTestigo getAccInformeTestigo() {
        return accInformeTestigo;
    }

    public void setAccInformeTestigo(AccInformeTestigo accInformeTestigo) {
        this.accInformeTestigo = accInformeTestigo;
    }

    public AccObjetoFijo getAccObjetoFijo() {
        return accObjetoFijo;
    }

    public void setAccObjetoFijo(AccObjetoFijo accObjetoFijo) {
        this.accObjetoFijo = accObjetoFijo;
    }

    public List<AccInformeVehCond> getListAccInformeVehCond() {
        return listAccInformeVehCond;
    }

    public void setListAccInformeVehCond(List<AccInformeVehCond> listAccInformeVehCond) {
        this.listAccInformeVehCond = listAccInformeVehCond;
    }

    public List<AccInformeVic> getListAccInformeVic() {
        return listAccInformeVic;
    }

    public void setListAccInformeVic(List<AccInformeVic> listAccInformeVic) {
        this.listAccInformeVic = listAccInformeVic;
    }

    public List<AccInformeTestigo> getListAccInformeTestigo() {
        return listAccInformeTestigo;
    }

    public void setListAccInformeTestigo(List<AccInformeTestigo> listAccInformeTestigo) {
        this.listAccInformeTestigo = listAccInformeTestigo;
    }

    public Integer[] getAccObjetoFijoArray() {
        return accObjetoFijoArray;
    }

    public void setAccObjetoFijoArray(Integer[] accObjetoFijoArray) {
        this.accObjetoFijoArray = accObjetoFijoArray;
    }

    public Integer[] getAccTipoVehArray() {
        return accTipoVehArray;
    }

    public void setAccTipoVehArray(Integer[] accTipoVehArray) {
        this.accTipoVehArray = accTipoVehArray;
    }

    public Integer[] getAccDemarcacionArray() {
        return accDemarcacionArray;
    }

    public void setAccDemarcacionArray(Integer[] accDemarcacionArray) {
        this.accDemarcacionArray = accDemarcacionArray;
    }

    public Integer[] getAccViaVisualArray() {
        return accViaVisualArray;
    }

    public void setAccViaVisualArray(Integer[] accViaVisualArray) {
        this.accViaVisualArray = accViaVisualArray;
    }

    public boolean isB_agente() {
        return b_agente;
    }

    public void setB_agente(boolean b_agente) {
        this.b_agente = b_agente;
    }

    public String getC_codVehiculo() {
        return c_codVehiculo;
    }

    public void setC_codVehiculo(String c_codVehiculo) {
        this.c_codVehiculo = c_codVehiculo;
    }

    public Integer getI_codTm() {
        return i_codTm;
    }

    public void setI_codTm(Integer i_codTm) {
        this.i_codTm = i_codTm;
    }

    public Integer getI_idAccClase() {
        return i_idAccClase;
    }

    public void setI_idAccClase(Integer i_idAccClase) {
        this.i_idAccClase = i_idAccClase;
    }

    public Integer getI_idAccViaTroncal() {
        return i_idAccViaTroncal;
    }

    public void setI_idAccViaTroncal(Integer i_idAccViaTroncal) {
        this.i_idAccViaTroncal = i_idAccViaTroncal;
    }

    public Integer getI_idAccViaSector() {
        return i_idAccViaSector;
    }

    public void setI_idAccViaSector(Integer i_idAccViaSector) {
        this.i_idAccViaSector = i_idAccViaSector;
    }

    public Integer getI_idAccViaZona() {
        return i_idAccViaZona;
    }

    public void setI_idAccViaZona(Integer i_idAccViaZona) {
        this.i_idAccViaZona = i_idAccViaZona;
    }

    public Integer getI_idAccViaDiseno() {
        return i_idAccViaDiseno;
    }

    public void setI_idAccViaDiseno(Integer i_idAccViaDiseno) {
        this.i_idAccViaDiseno = i_idAccViaDiseno;
    }

    public Integer getI_idAccViaClima() {
        return i_idAccViaClima;
    }

    public void setI_idAccViaClima(Integer i_idAccViaClima) {
        this.i_idAccViaClima = i_idAccViaClima;
    }

    public Integer getI_idAccViaGeometrica() {
        return i_idAccViaGeometrica;
    }

    public void setI_idAccViaGeometrica(Integer i_idAccViaGeometrica) {
        this.i_idAccViaGeometrica = i_idAccViaGeometrica;
    }

    public Integer getI_idAccViaUtilizacion() {
        return i_idAccViaUtilizacion;
    }

    public void setI_idAccViaUtilizacion(Integer i_idAccViaUtilizacion) {
        this.i_idAccViaUtilizacion = i_idAccViaUtilizacion;
    }

    public Integer getI_idAccViaCalzada() {
        return i_idAccViaCalzada;
    }

    public void setI_idAccViaCalzada(Integer i_idAccViaCalzada) {
        this.i_idAccViaCalzada = i_idAccViaCalzada;
    }

    public Integer getI_idAccViaCarriles() {
        return i_idAccViaCarriles;
    }

    public void setI_idAccViaCarriles(Integer i_idAccViaCarriles) {
        this.i_idAccViaCarriles = i_idAccViaCarriles;
    }

    public Integer getI_idAccViaEstado() {
        return i_idAccViaEstado;
    }

    public void setI_idAccViaEstado(Integer i_idAccViaEstado) {
        this.i_idAccViaEstado = i_idAccViaEstado;
    }

    public Integer getI_idAccViaCondicion() {
        return i_idAccViaCondicion;
    }

    public void setI_idAccViaCondicion(Integer i_idAccViaCondicion) {
        this.i_idAccViaCondicion = i_idAccViaCondicion;
    }

    public Integer getI_idAccViaIluminacion() {
        return i_idAccViaIluminacion;
    }

    public void setI_idAccViaIluminacion(Integer i_idAccViaIluminacion) {
        this.i_idAccViaIluminacion = i_idAccViaIluminacion;
    }

    public Integer getI_idAccViaSemaforo() {
        return i_idAccViaSemaforo;
    }

    public void setI_idAccViaSemaforo(Integer i_idAccViaSemaforo) {
        this.i_idAccViaSemaforo = i_idAccViaSemaforo;
    }

    public Integer getI_idEmpresaOperadora() {
        return i_idEmpresaOperadora;
    }

    public void setI_idEmpresaOperadora(Integer i_idEmpresaOperadora) {
        this.i_idEmpresaOperadora = i_idEmpresaOperadora;
    }

    public Integer getI_idAccTipoVehiculo() {
        return i_idAccTipoVehiculo;
    }

    public void setI_idAccTipoVehiculo(Integer i_idAccTipoVehiculo) {
        this.i_idAccTipoVehiculo = i_idAccTipoVehiculo;
    }

    public Integer getI_idTipoServ() {
        return i_idTipoServ;
    }

    public void setI_idTipoServ(Integer i_idTipoServ) {
        this.i_idTipoServ = i_idTipoServ;
    }

    public Integer getI_idAccFalla() {
        return i_idAccFalla;
    }

    public void setI_idAccFalla(Integer i_idAccFalla) {
        this.i_idAccFalla = i_idAccFalla;
    }

    public Integer getI_tpIdentiVeh() {
        return i_tpIdentiVeh;
    }

    public void setI_tpIdentiVeh(Integer i_tpIdentiVeh) {
        this.i_tpIdentiVeh = i_tpIdentiVeh;
    }

    public Integer getI_tpIdentiPropietario() {
        return i_tpIdentiPropietario;
    }

    public void setI_tpIdentiPropietario(Integer i_tpIdentiPropietario) {
        this.i_tpIdentiPropietario = i_tpIdentiPropietario;
    }

    public Integer getI_tpIdentiVictima() {
        return i_tpIdentiVictima;
    }

    public void setI_tpIdentiVictima(Integer i_tpIdentiVictima) {
        this.i_tpIdentiVictima = i_tpIdentiVictima;
    }

    public Integer getI_tpIdentiTestigo() {
        return i_tpIdentiTestigo;
    }

    public void setI_tpIdentiTestigo(Integer i_tpIdentiTestigo) {
        this.i_tpIdentiTestigo = i_tpIdentiTestigo;
    }

    public Integer getI_codTmPrincipal() {
        return i_codTmPrincipal;
    }

    public void setI_codTmPrincipal(Integer i_codTmPrincipal) {
        this.i_codTmPrincipal = i_codTmPrincipal;
    }

    public Integer getI_idAccViaMaterial() {
        return i_idAccViaMaterial;
    }

    public void setI_idAccViaMaterial(Integer i_idAccViaMaterial) {
        this.i_idAccViaMaterial = i_idAccViaMaterial;
    }

    public List<AccCausa> getListAccCausa() {
        if (i_idAccArbol != null) {
            listAccCausa = accCausaFacadeLocal.findByArbol(i_idAccArbol);
            if (i_idAccCausa != null) {
                for (AccCausa a : listAccCausa) {
                    if (a.getIdAccCausa().equals(i_idAccCausa)) {
                        c_tituloPregunta = a.getDescripcion();
                        break;
                    }
                }
            }
        }
        return listAccCausa;
    }

    public List<AccCausaSub> getListAccCausaSub() {
        if (i_idAccCausa != null) {
            listAccCausaSub = accCausaSubFacadeLocal.findByCausa(i_idAccCausa);
            if (i_idAccCausaSub != null) {
                for (AccCausaSub a : listAccCausaSub) {
                    if (a.getIdAccSubcausa().equals(i_idAccCausaSub)) {
                        c_tituloPregunta = a.getDescripcion();
                        break;
                    }
                }
            }
        }
        return listAccCausaSub;
    }

    public List<AccCausaRaiz> getListAccCausaRaiz() {
        if (i_idAccCausaSub != null) {
            listAccCausaRaiz = accCausaRaizFacadeLocal.findByCausaSub(i_idAccCausaSub);
            if (i_idAccCausaRaiz != null) {
                for (AccCausaRaiz a : listAccCausaRaiz) {
                    if (a.getIdAccCausaRaiz().equals(i_idAccCausaRaiz)) {
                        c_tituloPregunta = a.getDescripcion();
                        break;
                    }
                }
            }
        }
        return listAccCausaRaiz;
    }

    public Integer getI_idAccCausaRaiz() {
        return i_idAccCausaRaiz;
    }

    public void setI_idAccCausaRaiz(Integer i_idAccCausaRaiz) {
        this.i_idAccCausaRaiz = i_idAccCausaRaiz;
    }

    public Integer getI_idAccCausaSub() {
        return i_idAccCausaSub;
    }

    public void setI_idAccCausaSub(Integer i_idAccCausaSub) {
        this.i_idAccCausaSub = i_idAccCausaSub;
    }

    public Integer getI_idAccArbol() {
        return i_idAccArbol;
    }

    public void setI_idAccArbol(Integer i_idAccArbol) {
        this.i_idAccArbol = i_idAccArbol;
    }

    public Integer getI_idAccCausa() {
        return i_idAccCausa;
    }

    public void setI_idAccCausa(Integer i_idAccCausa) {
        this.i_idAccCausa = i_idAccCausa;
    }

    public String getC_tituloPregunta() {
        return c_tituloPregunta;
    }

    public void setC_tituloPregunta(String c_tituloPregunta) {
        this.c_tituloPregunta = c_tituloPregunta;
    }

    public AccInformeOpeCausalidad getAccInformeOpeCausalidad() {
        return accInformeOpeCausalidad;
    }

    public void setAccInformeOpeCausalidad(AccInformeOpeCausalidad accInformeOpeCausalidad) {
        this.accInformeOpeCausalidad = accInformeOpeCausalidad;
    }

    public List<AccInformeOpeCausalidad> getListAccInformeOpeCausalidad() {
        return listAccInformeOpeCausalidad;
    }

    public void setListAccInformeOpeCausalidad(List<AccInformeOpeCausalidad> listAccInformeOpeCausalidad) {
        this.listAccInformeOpeCausalidad = listAccInformeOpeCausalidad;
    }

    public UploadedFile getFileCroquis() {
        return fileCroquis;
    }

    public void setFileCroquis(UploadedFile fileCroquis) {
        this.fileCroquis = fileCroquis;
    }

    public UploadedFile getFileAnexo() {
        return fileAnexo;
    }

    public void setFileAnexo(UploadedFile fileAnexo) {
        this.fileAnexo = fileAnexo;
    }

}
