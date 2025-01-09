/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccInformeMasterAgentesFacadeLocal;
import com.movilidad.ejb.AccInformeMasterAlbumFacadeLocal;
import com.movilidad.ejb.AccInformeMasterApoyoFacadeLocal;
import com.movilidad.ejb.AccInformeMasterBomberosFacadeLocal;
import com.movilidad.ejb.AccInformeMasterFacadeLocal;
import com.movilidad.ejb.AccInformeMasterInspectoresFacadeLocal;
import com.movilidad.ejb.AccInformeMasterLesionadoFacadeLocal;
import com.movilidad.ejb.AccInformeMasterMedicosFacadeLocal;
import com.movilidad.ejb.AccInformeMasterRecomotosFacadeLocal;
import com.movilidad.ejb.AccInformeMasterTestigoFacadeLocal;
import com.movilidad.ejb.AccInformeMasterVehCondFacadeLocal;
import com.movilidad.ejb.AccInformeMasterVicFacadeLocal;
import com.movilidad.ejb.AccidenteFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.AccInformeMaster;
import com.movilidad.model.AccInformeMasterAgentes;
import com.movilidad.model.AccInformeMasterAlbum;
import com.movilidad.model.AccInformeMasterApoyo;
import com.movilidad.model.AccInformeMasterBomberos;
import com.movilidad.model.AccInformeMasterInspectores;
import com.movilidad.model.AccInformeMasterLesionado;
import com.movilidad.model.AccInformeMasterMedicos;
import com.movilidad.model.AccInformeMasterRecomotos;
import com.movilidad.model.AccInformeMasterTestigo;
import com.movilidad.model.AccInformeMasterVehCond;
import com.movilidad.model.AccInformeMasterVic;
import com.movilidad.model.AccTipoVictima;
import com.movilidad.model.Accidente;
import com.movilidad.model.Empleado;
import com.movilidad.model.EmpleadoTipoIdentificacion;
import com.movilidad.model.PrgStopPoint;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.AccInformeMasterAlbumFoto;
import com.movilidad.util.beans.AccInformeMasterTestigoVideo;
import com.movilidad.utils.DocumentUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "accInformeMasterJSF")
@ViewScoped
public class AccInformeMasterJSF implements Serializable {

    @EJB
    private AccInformeMasterFacadeLocal accInformeMasterFacadeLocal;
    @EJB
    private AccInformeMasterAlbumFacadeLocal accInformeMasterAlbumFacadeLocal;
    @EJB
    private AccInformeMasterAgentesFacadeLocal accInformeMasterAgentesFacadeLocal;
    @EJB
    private AccInformeMasterApoyoFacadeLocal accInformeMasterApoyoFacadeLocal;
    @EJB
    private AccInformeMasterBomberosFacadeLocal accInformeMasterBomberosFacadeLocal;
    @EJB
    private AccInformeMasterInspectoresFacadeLocal accInformeMasterInspectoresFacadeLocal;
    @EJB
    private AccInformeMasterLesionadoFacadeLocal accInformeMasterLesionadoFacadeLocal;
    @EJB
    private AccInformeMasterMedicosFacadeLocal accInformeMasterMedicosFacadeLocal;
    @EJB
    private AccInformeMasterRecomotosFacadeLocal accInformeMasterRecomotosFacadeLocal;
    @EJB
    private AccInformeMasterTestigoFacadeLocal accInformeMasterTestigoFacadeLocal;
    @EJB
    private AccInformeMasterVehCondFacadeLocal accInformeMasterVehCondFacadeLocal;
    @EJB
    private AccInformeMasterVicFacadeLocal accInformeMasterVicFacadeLocal;
    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;
    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    private AccidenteFacadeLocal accidenteFacadeLocal;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private UploadedFile file;
    private UploadedFile videoOperador;

    private AccInformeMaster accInformeMaster;
    private AccInformeMasterAlbum accInformeMasterAlbum;
    private AccInformeMasterAgentes accInformeMasterAgentes;
    private AccInformeMasterApoyo accInformeMasterApoyo;
    private AccInformeMasterBomberos accInformeMasterBomberos;
    private AccInformeMasterInspectores accInformeMasterInspectores;
    private AccInformeMasterLesionado accInformeMasterLesionado;
    private AccInformeMasterMedicos accInformeMasterMedicos;
    private AccInformeMasterRecomotos accInformeMasterRecomotos;
    private AccInformeMasterTestigo accInformeMasterTestigo;
    private AccInformeMasterVehCond accInformeMasterVehCond;
    private AccInformeMasterVic accInformeMasterVic;

    private List<AccInformeMasterAgentes> listAccInformeMasterAgentes;
    private List<AccInformeMasterApoyo> listAccInformeMasterApoyo;
    private List<AccInformeMasterBomberos> listAccInformeMasterBomberos;
    private List<AccInformeMasterInspectores> listAccInformeMasterInspectores;
    private List<AccInformeMasterLesionado> listAccInformeMasterLesionado;
    private List<AccInformeMasterMedicos> listAccInformeMasterMedicos;
    private List<AccInformeMasterRecomotos> listAccInformeMasterRecomotos;
    private List<AccInformeMasterVehCond> listAccInformeMasterVehCond;
    private List<AccInformeMasterAlbumFoto> listAccInformeMasterAlbumFoto;
    private List<AccInformeMasterVic> listAccInformeMasterVic;
    private List<AccInformeMasterTestigoVideo> listAccInformeMasterTestigoVideo;

    private String c_codVehiculo;
    private Integer i_codTm;
    private Integer i_codTmMaster;

    private Integer i_tpVictimaIdentificacion;
    private Integer idTipoVictima;

    public AccInformeMasterJSF() {
    }

    @PostConstruct
    public void init() {
        accInformeMasterAlbum = new AccInformeMasterAlbum();
        accInformeMasterApoyo = new AccInformeMasterApoyo();
        accInformeMasterAgentes = new AccInformeMasterAgentes();
        accInformeMasterBomberos = new AccInformeMasterBomberos();
        accInformeMasterInspectores = new AccInformeMasterInspectores();
        accInformeMasterLesionado = new AccInformeMasterLesionado();
        accInformeMasterMedicos = new AccInformeMasterMedicos();
        accInformeMasterRecomotos = new AccInformeMasterRecomotos();
        accInformeMasterTestigo = new AccInformeMasterTestigo();
        accInformeMasterVehCond = new AccInformeMasterVehCond();
        accInformeMasterVic = new AccInformeMasterVic();
        listAccInformeMasterAlbumFoto = new ArrayList<>();
        listAccInformeMasterApoyo = new ArrayList<>();
        listAccInformeMasterAgentes = new ArrayList<>();
        listAccInformeMasterBomberos = new ArrayList<>();
        listAccInformeMasterInspectores = new ArrayList<>();
        listAccInformeMasterLesionado = new ArrayList<>();
        listAccInformeMasterMedicos = new ArrayList<>();
        listAccInformeMasterRecomotos = new ArrayList<>();
        listAccInformeMasterVehCond = new ArrayList<>();
        listAccInformeMasterVic = new ArrayList<>();
        listAccInformeMasterTestigoVideo = new ArrayList<>();
        c_codVehiculo = "";
        i_codTm = null;
        file = null;
        videoOperador = null;
        i_tpVictimaIdentificacion = null;
        idTipoVictima = null;
    }

    public AccInformeMaster setInformeFromAccidente(Accidente acc) {
        AccInformeMaster aim = new AccInformeMaster();
        if (acc != null) {
            aim.setIdAccidente(acc);
            accInformeMaster.setUsername(acc.getUsername());
            aim.setCreado(acc.getCreado());
            aim.setModificado(acc.getModificado());
            accInformeMasterFacadeLocal.edit(aim);
        }
        return aim;
    }

    /**
     * Agrega objetos AccInformeVic a una lista en memoria
     */
    public void agregarVictima() {
        if (accInformeMasterVic.getNombres() == null) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeMasterVic.getNombres().isEmpty()) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeMasterVic.getSexo() == null) {
            MovilidadUtil.addErrorMessage("Sexo de la victima es requerido");
            return;
        }
        if (i_tpVictimaIdentificacion != null) {
            accInformeMasterVic.setIdTipoIdentificacion(new EmpleadoTipoIdentificacion(i_tpVictimaIdentificacion));
        }
        if (idTipoVictima != null) {
            accInformeMasterVic.setIdAccTipoVictima(new AccTipoVictima(idTipoVictima));
        }
        listAccInformeMasterVic.add(accInformeMasterVic);
        accInformeMasterVic = new AccInformeMasterVic();
        i_tpVictimaIdentificacion = null;
        idTipoVictima = null;
        MovilidadUtil.addSuccessMessage("Victima agregada a la lista");
    }

    /**
     * Agrega objetos AccInformeAlbum a una lista en memoria
     */
    public void agregarAlbum() {
        if (accInformeMasterAlbum.getTecnologia() == null) {
            MovilidadUtil.addErrorMessage("Tecnologia es requerido");
            return;
        }
        if (accInformeMasterAlbum.getInstrumentos() == null) {
            MovilidadUtil.addErrorMessage("Instrumentos es requerido");
            return;
        }
        if (accInformeMasterAlbum.getInstrumentos().isEmpty()) {
            MovilidadUtil.addErrorMessage("Instrumentos es requerido");
            return;
        }
        if (accInformeMasterAlbum.getMarca() == null) {
            MovilidadUtil.addErrorMessage("Marca es requerido");
            return;
        }
        if (accInformeMasterAlbum.getMarca().isEmpty()) {
            MovilidadUtil.addErrorMessage("Marca es requerido");
            return;
        }
        if (accInformeMasterAlbum.getReferencia() == null) {
            MovilidadUtil.addErrorMessage("Referencia es requerido");
            return;
        }
        if (accInformeMasterAlbum.getReferencia().isEmpty()) {
            MovilidadUtil.addErrorMessage("Referencia es requerido");
            return;
        }
        if (accInformeMasterAlbum.getTipoFoto() == null) {
            MovilidadUtil.addErrorMessage("Tipo de fotografía es requerido");
            return;
        }
        if (accInformeMasterAlbum.getTipoFoto().isEmpty()) {
            MovilidadUtil.addErrorMessage("Tipo de fotografía es requerido");
            return;
        }
        if (accInformeMasterAlbum.getDescripcion() == null) {
            MovilidadUtil.addErrorMessage("Descripción de la imagen es requerido");
            return;
        }
        if (accInformeMasterAlbum.getDescripcion().isEmpty()) {
            MovilidadUtil.addErrorMessage("Descripción de la imagen es requerido");
            return;
        }
        PrimeFaces.current().executeScript("PF('fotoDlg').show()");
    }

    /**
     * Agrega objetos AccInformeApoyo a una lista en memoria
     */
    public void agregarApoyo() {
        if (accInformeMasterApoyo.getIdMaster() == null) {
            MovilidadUtil.addErrorMessage("Máster de apoyo es requerido");
            return;
        }
        if (accInformeMasterApoyo.getFirma() == null) {
            MovilidadUtil.addErrorMessage("Firma es requerido");
            return;
        }
        if (accInformeMasterApoyo.getFirma().isEmpty()) {
            MovilidadUtil.addErrorMessage("Firma es requerido");
            return;
        }
        listAccInformeMasterApoyo.add(accInformeMasterApoyo);
        accInformeMasterApoyo = new AccInformeMasterApoyo();
        i_codTmMaster = null;
        MovilidadUtil.addSuccessMessage("Máster de apoyo agregado a la lista");
    }

    /**
     * Agrega objetos AccInformeBomberos a una lista en memoria
     */
    public void agregarBomberos() {
        if (accInformeMasterBomberos.getNombres() == null) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeMasterBomberos.getNombres().isEmpty()) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeMasterBomberos.getPlacaUnidadMovil() == null) {
            MovilidadUtil.addErrorMessage("Placa U.M es requerido");
            return;
        }
        if (accInformeMasterBomberos.getPlacaUnidadMovil().isEmpty()) {
            MovilidadUtil.addErrorMessage("Placa U.M es requerido");
            return;
        }
        listAccInformeMasterBomberos.add(accInformeMasterBomberos);
        accInformeMasterBomberos = new AccInformeMasterBomberos();
        MovilidadUtil.addSuccessMessage("Unidad de Bomberos agregado a la lista");
    }

    /**
     * Agrega objetos AccInformeAgentes a una lista en memoria
     */
    public void agregarAgentes() {
        if (accInformeMasterAgentes.getNombres() == null) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeMasterAgentes.getNombres().isEmpty()) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeMasterAgentes.getChaleco() == null) {
            MovilidadUtil.addErrorMessage("Número de chaleco es requerido");
            return;
        }
        if (accInformeMasterAgentes.getChaleco().isEmpty()) {
            MovilidadUtil.addErrorMessage("Número de chaleco es requerido");
            return;
        }
        listAccInformeMasterAgentes.add(accInformeMasterAgentes);
        accInformeMasterAgentes = new AccInformeMasterAgentes();
        MovilidadUtil.addSuccessMessage("Unidad de Policia o Agente de transito agregado a la lista");
    }

    /**
     * Agrega objetos AccInformeInspectores a una lista en memoria
     */
    public void agregarInspectores() {
        if (accInformeMasterInspectores.getNombres() == null) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeMasterInspectores.getNombres().isEmpty()) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeMasterInspectores.getChaleco() == null) {
            MovilidadUtil.addErrorMessage("Num. Chaleco es requerido");
            return;
        }
        if (accInformeMasterInspectores.getChaleco().isEmpty()) {
            MovilidadUtil.addErrorMessage("Num. Chaleco es requerido");
            return;
        }
        listAccInformeMasterInspectores.add(accInformeMasterInspectores);
        accInformeMasterInspectores = new AccInformeMasterInspectores();
        MovilidadUtil.addSuccessMessage("Unidad de Inspectores agregado a la lista");
    }

    /**
     * Agrega objetos AccInformeLesionado a una lista en memoria
     */
    public void agregarLesionado() {
        if (accInformeMasterLesionado.getNombres() == null) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeMasterLesionado.getNombres().isEmpty()) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeMasterLesionado.getIdentificacion() == null) {
            MovilidadUtil.addErrorMessage("Identificación es requerido");
            return;
        }
        if (accInformeMasterLesionado.getIdentificacion().isEmpty()) {
            MovilidadUtil.addErrorMessage("Identificación es requerido");
            return;
        }
        listAccInformeMasterLesionado.add(accInformeMasterLesionado);
        accInformeMasterLesionado = new AccInformeMasterLesionado();
        MovilidadUtil.addSuccessMessage("Lesionado agregado a la lista");
    }

    /**
     * Agrega objetos AccInformeMedico a una lista en memoria
     */
    public void agregarMedico() {
        if (accInformeMasterMedicos.getNombres() == null) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeMasterMedicos.getNombres().isEmpty()) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeMasterMedicos.getPlacaAmbulancia() == null) {
            MovilidadUtil.addErrorMessage("Placa Amb es requerido");
            return;
        }
        if (accInformeMasterMedicos.getPlacaAmbulancia().isEmpty()) {
            MovilidadUtil.addErrorMessage("Placa Amb es requerido");
            return;
        }
        listAccInformeMasterMedicos.add(accInformeMasterMedicos);
        accInformeMasterMedicos = new AccInformeMasterMedicos();
        MovilidadUtil.addSuccessMessage("Unidad de Médicos y Paramédicos agregado a la lista");
    }

    /**
     * Agrega objetos AccInformeRecomoto a una lista en memoria
     */
    public void agregarRecomoto() {
        if (accInformeMasterRecomotos.getNombres() == null) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeMasterRecomotos.getNombres().isEmpty()) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeMasterRecomotos.getChaleco() == null) {
            MovilidadUtil.addErrorMessage("Identificación es requerido");
            return;
        }
        if (accInformeMasterRecomotos.getChaleco().isEmpty()) {
            MovilidadUtil.addErrorMessage("Identificación es requerido");
            return;
        }
        listAccInformeMasterRecomotos.add(accInformeMasterRecomotos);
        accInformeMasterRecomotos = new AccInformeMasterRecomotos();
        MovilidadUtil.addSuccessMessage("Unidad de Recomotos agregado a la lista");
    }

    /**
     * Agrega objetos AccInformeMasterTestigo a una lista en memoria
     */
    public void agregarTestigo() {
        if (accInformeMasterTestigo.getNombres() == null) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeMasterTestigo.getNombres().isEmpty()) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeMasterTestigo.getVideo() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar si el testigo aporta vídeo");
            return;
        }
        if (accInformeMasterTestigo.getVideo() == 1) {
            if (accInformeMasterTestigo.getHoraVideo() == null) {
                MovilidadUtil.addErrorMessage("Hora y Minuros de vídeo es requerido");
                return;
            }
            if (accInformeMasterTestigo.getHoraVideo().isEmpty()) {
                MovilidadUtil.addErrorMessage("Hora y Minuros de vídeo es requerido");
                return;
            }
        }
        if (accInformeMasterTestigo.getTelefono() == null) {
            MovilidadUtil.addErrorMessage("Teléfono es requerido");
            return;
        }
        if (accInformeMasterTestigo.getTelefono().isEmpty()) {
            MovilidadUtil.addErrorMessage("Teléfono es requerido");
            return;
        }
        // si el usuario indica carga de archivo relacionado al objeto AccInformeMasterTestigo
        // se habilita un formulario para la carga de este, y en una clase de control
        // AccInformeMasterTestigoVideo se relaciona el objeto AccInformeMasterTestigo con el archivo cargado
        if (accInformeMasterTestigo.getVideo() == 0) { // 0 si no relaciona archivo, 1 si lo relaciona
            AccInformeMasterTestigoVideo aimtv = new AccInformeMasterTestigoVideo();
            aimtv.setAccInformeMasterTestigo(accInformeMasterTestigo);
            listAccInformeMasterTestigoVideo.add(aimtv);
            accInformeMasterTestigo = new AccInformeMasterTestigo();
            MovilidadUtil.addSuccessMessage("Testigo agregado a la lista");
        } else {
            PrimeFaces.current().executeScript("PF('videoTesDlg').show()");
        }
    }

    /**
     * Agrega objetos AccInformeMasterVehCond a una lista en memoria
     */
    public void agregarVehCond() {
        if (accInformeMasterVehCond.getNombres() == null) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeMasterVehCond.getNombres().isEmpty()) {
            MovilidadUtil.addErrorMessage("Nombres es requerido");
            return;
        }
        if (accInformeMasterVehCond.getPlaca() == null) {
            MovilidadUtil.addErrorMessage("Placa es requerido");
            return;
        }
        if (accInformeMasterVehCond.getPlaca().isEmpty()) {
            MovilidadUtil.addErrorMessage("Placa es requerido");
            return;
        }
        if (accInformeMasterVehCond.getInmovilizado() == null) {
            MovilidadUtil.addErrorMessage("Inmoilizadoes requerido");
            return;
        }
        listAccInformeMasterVehCond.add(accInformeMasterVehCond);
        accInformeMasterVehCond = new AccInformeMasterVehCond();
        c_codVehiculo = null;
        i_codTm = null;
        MovilidadUtil.addSuccessMessage("Vehículo - Conductor agregado a la lista");
    }

    /**
     * Permite persistir todos los datos que se encuentran en memoria, de todas
     * las listas relacionadas con este controlador
     */
    public void guardarTodo() {
        if (validarDatos()) {
            return;
        }
        AccInformeMaster aim = persistirAccInformeMaster();
        if (accInformeMaster.getIdAccInformeMaster() == null) {
            return;
        }
        persistirVehCond(aim);
        persistirAlbum(aim);
        persistirApoyo(aim);
        persistirBomberos(aim);
        persistirInspectores(aim);
        persistirVictimas(aim);
        persistirAgentes(aim);
//        persistirLesionado(aim); se fucionó víctimas y lesionados
        persistirMedico(aim);
        persistirRecomoto(aim);
        persistirTestigo(aim);
        cargarBosquejoTopografico(aim);
        cargarVideoOpe(aim);
        generarInformePdf(aim.getIdAccInformeMaster());
        MovilidadUtil.addSuccessMessage("Informe de máster registrado con exito");
        cancelarTodo();
        PrimeFaces.current().executeScript("location.reload()");
    }

    public void cancelarTodo() {
        init();
    }

    AccInformeMaster persistirAccInformeMaster() {
        accInformeMaster.setEstadoReg(0);
        accInformeMaster.setCreado(new Date());
        accInformeMaster.setModificado(new Date());
        accInformeMaster.setUsername(user.getUsername());
        accInformeMasterFacadeLocal.create(accInformeMaster);
        return accInformeMaster;
    }

    void generarInformePdf(Integer id) {
        AccInformeMaster find = accInformeMasterFacadeLocal.find(id);
        if (find != null) {
            DocumentUtil.generarInformeMaster(find);
        }
    }

    void cargarVideoOpe(AccInformeMaster aim) {
        String ruta = "";
        try {
            if (videoOperador != null) {
                ruta = MovilidadUtil.cargarArchivosAccidentalidad(videoOperador, aim.getIdAccidente().getIdAccidente(), "/InformeMaster", aim.getIdAccInformeMaster(), "videoOpe");
                accInformeMaster.setPathVideoOpe(ruta);
                accInformeMasterFacadeLocal.edit(accInformeMaster);
            }
        } catch (Exception e) {
            Util.deleteFile(ruta);
        }
    }

    void cargarBosquejoTopografico(AccInformeMaster aim) {
        String ruta = "";
        try {
            if (file != null) {
                ruta = MovilidadUtil.cargarArchivosAccidentalidad(file, aim.getIdAccidente().getIdAccidente(), "/InformeMaster", aim.getIdAccInformeMaster(), "bosquejo");
                accInformeMaster.setPathBosquejo(ruta);
                accInformeMasterFacadeLocal.edit(accInformeMaster);
            }
        } catch (Exception e) {
            Util.deleteFile(ruta);
        }
    }

    void persistirAlbum(AccInformeMaster aim) {
        String ruta = "";
        try {
            if (!listAccInformeMasterAlbumFoto.isEmpty()) {
                int i = 0;
                for (AccInformeMasterAlbumFoto aimaf : listAccInformeMasterAlbumFoto) {
                    i++;
                    AccInformeMasterAlbum albumAux = aimaf.getAccInformeMasterAlbum();
                    albumAux.setIdAccInformeMaster(aim);
                    ruta = MovilidadUtil.cargarArchivosAccidentalidad(aimaf.getUploadedFile(), aim.getIdAccidente().getIdAccidente(), "InformeMaster", aim.getIdAccInformeMaster(), "album-" + String.valueOf(i));
                    albumAux.setPathFoto(ruta);
                    accInformeMasterAlbumFacadeLocal.edit(albumAux);
                }
            }
        } catch (Exception e) {
            Util.deleteFile(ruta);
        }
    }

    void persistirApoyo(AccInformeMaster aim) {
        if (!listAccInformeMasterApoyo.isEmpty()) {
            for (AccInformeMasterApoyo aima : listAccInformeMasterApoyo) {
                aima.setIdAccInformeMaster(aim);
                accInformeMasterApoyoFacadeLocal.edit(aima);
            }
        }
    }

    void persistirAgentes(AccInformeMaster aim) {
        if (!listAccInformeMasterAgentes.isEmpty()) {
            for (AccInformeMasterAgentes aima : listAccInformeMasterAgentes) {
                aima.setIdAccInformeMaster(aim);
                accInformeMasterAgentesFacadeLocal.edit(aima);
            }
        }
    }

    void persistirVictimas(AccInformeMaster aim) {
        if (!listAccInformeMasterVic.isEmpty()) {
            for (AccInformeMasterVic aimv : listAccInformeMasterVic) {
                aimv.setIdAccInformeMaster(aim);
                accInformeMasterVicFacadeLocal.edit(aimv);
            }
        }
    }

    void persistirBomberos(AccInformeMaster aim) {
        if (!listAccInformeMasterBomberos.isEmpty()) {
            for (AccInformeMasterBomberos aimb : listAccInformeMasterBomberos) {
                aimb.setIdAccInformeMaster(aim);
                accInformeMasterBomberosFacadeLocal.edit(aimb);
            }
        }
    }

    void persistirInspectores(AccInformeMaster aim) {
        if (!listAccInformeMasterInspectores.isEmpty()) {
            for (AccInformeMasterInspectores aimi : listAccInformeMasterInspectores) {
                aimi.setIdAccInformeMaster(aim);
                accInformeMasterInspectoresFacadeLocal.edit(aimi);
            }
        }
    }

    void persistirLesionado(AccInformeMaster aim) {
        if (!listAccInformeMasterLesionado.isEmpty()) {
            for (AccInformeMasterLesionado aiml : listAccInformeMasterLesionado) {
                aiml.setIdAccInformeMaster(aim);
                accInformeMasterLesionadoFacadeLocal.edit(aiml);
            }
        }
    }

    void persistirMedico(AccInformeMaster aim) {
        if (!listAccInformeMasterMedicos.isEmpty()) {
            for (AccInformeMasterMedicos aimm : listAccInformeMasterMedicos) {
                aimm.setIdAccInformeMaster(aim);
                accInformeMasterMedicosFacadeLocal.edit(aimm);
            }
        }
    }

    void persistirRecomoto(AccInformeMaster aim) {
        if (!listAccInformeMasterRecomotos.isEmpty()) {
            for (AccInformeMasterRecomotos aimr : listAccInformeMasterRecomotos) {
                aimr.setIdAccInformeMaster(aim);
                accInformeMasterRecomotosFacadeLocal.edit(aimr);
            }
        }
    }

    void persistirTestigo(AccInformeMaster aim) {
        String ruta = "";
        try {
            if (!listAccInformeMasterTestigoVideo.isEmpty()) {
                int i = 0;
                for (AccInformeMasterTestigoVideo aimtv : listAccInformeMasterTestigoVideo) {
                    i++;
                    AccInformeMasterTestigo aimt = aimtv.getAccInformeMasterTestigo();
                    aimt.setIdAccInformeMaster(aim);
                    if (aimtv.getUploadedFile() != null) {
                        ruta = MovilidadUtil.cargarArchivosAccidentalidad(aimtv.getUploadedFile(), aim.getIdAccidente().getIdAccidente(), "InformeMaster", aim.getIdAccInformeMaster(), "videoTestigo-" + String.valueOf(i));
                        aimt.setPathVideo(ruta);
                    }
                    accInformeMasterTestigoFacadeLocal.edit(aimt);
                }
            }
        } catch (Exception e) {
            Util.deleteFile(ruta);
            System.out.println("Error en persistir testigos");
        }
    }

    void persistirVehCond(AccInformeMaster aim) {
        if (!listAccInformeMasterVehCond.isEmpty()) {
            for (AccInformeMasterVehCond aimvc : listAccInformeMasterVehCond) {
                aimvc.setIdAccInformeMaster(aim);
                accInformeMasterVehCondFacadeLocal.edit(aimvc);
            }
        }
    }

    /**
     * Permite realizar la búsqueda de un objeto Vehiculo, con respecto a su
     * codigo
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
            accInformeMasterVehCond.setModelo(String.valueOf(vehiculo.getModelo()));
        }
        if (vehiculo.getPlaca() != null) {
            accInformeMasterVehCond.setPlaca(vehiculo.getPlaca());
        }
        if (vehiculo.getColor() != null) {
            accInformeMasterVehCond.setColor(vehiculo.getColor());
        }
        if (vehiculo.getColor() != null) {
            accInformeMasterVehCond.setColor(vehiculo.getColor());
        }
        if (vehiculo.getCodigo() != null) {
            accInformeMasterVehCond.setCodigo(vehiculo.getCodigo());
        }
    }

    /**
     * Realiza la búsqueda de un objeto Empleado de acuerdo a su código tm y lo
     * relaciona con el objeto AccInformeMaster o AccInformeMasterApoyo
     *
     * @param ok Determina si la acción va dirigida a setear el objeto Empleado
     * a el objeto AccInformeMaster o AccInformeMasterApoyo
     */
    public void buscarMaster(boolean ok) {
        if (i_codTmMaster == null) {
            MovilidadUtil.addErrorMessage("No se encontraron registros");
            return;
        }
        Empleado empleado = empleadoFacadeLocal.getEmpleadoCodigoTM(i_codTmMaster);
        if (empleado != null) {
            if (empleado.getIdEmpleadoCargo().getIdEmpleadoTipoCargo() == Util.ID_OPE_MASTER || empleado.getIdEmpleadoCargo().getNombreCargo().equalsIgnoreCase(Util.OPE_MASTER)) {
                if (ok) {
                    accInformeMaster.setIdEmpleadoMaster(empleado);
                    MovilidadUtil.addSuccessMessage("Máster cargado");
                    return;
                }
                accInformeMasterApoyo.setIdMaster(empleado);
                MovilidadUtil.addSuccessMessage("Máster de apoyo cargado");
                return;
            }
            MovilidadUtil.addErrorMessage("El código ingresado no corresponde a Operador Master");
        } else {
            MovilidadUtil.addErrorMessage("No se encontraron registros");
        }
    }

    /**
     * Permite buscar un objeto Empleado por su atributo codigo tm, y setearlo
     * en el objeto AccInformeMasterVehCond
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
    }

    void cargarOperador(Empleado e) {
        if (e.getIdentificacion() != null) {
            accInformeMasterVehCond.setIdentificacion(e.getIdentificacion());
        }
        if (e.getNombres() != null) {
            accInformeMasterVehCond.setNombres(e.getApellidos() + " " + e.getNombres());
        }
        if (e.getTelefonoFijo() != null && e.getTelefonoMovil() != null) {
            accInformeMasterVehCond.setTelefono(e.getTelefonoFijo() + " - " + e.getTelefonoMovil());
        }
        if (e.getGenero() != null) {
            accInformeMasterVehCond.setSexo(e.getGenero());
        }
        if (e.getCodigoTm() != null) {
            accInformeMasterVehCond.setCodigoTm(String.valueOf(e.getCodigoTm()));
        }
    }

    /**
     * Permite escribir en disco el archivo cargado por el usuario
     *
     * @param event Evento que permite obtener el archivo cargado por el usuario
     */
    public void cargaVideoOpe(FileUploadEvent event) {
        videoOperador = event.getFile();
        PrimeFaces.current().executeScript("PF('videoDlg').hide();");
        MovilidadUtil.addSuccessMessage("Vídeo cargado correctamente");
    }

    /**
     *
     * @param event Evento que permite obtener el archivo cargado por el usuario
     */
    public void cargaBosquejo(FileUploadEvent event) {
        file = event.getFile();
        PrimeFaces.current().executeScript("PF('bosquejoDlg').hide();");
        MovilidadUtil.addSuccessMessage("Archivo cargado correctamente");
    }

    /**
     * Permite relacionar el archivo cargado por el usuario con el objeto
     * AccInformeMasterAlbum
     *
     * @param event Evento que permite obtener el archivo cargado por el usuario
     */
    public void cargaFotoAlbum(FileUploadEvent event) {
        AccInformeMasterAlbumFoto aifmafo = new AccInformeMasterAlbumFoto();
        aifmafo.setAccInformeMasterAlbum(accInformeMasterAlbum);
        aifmafo.setUploadedFile(event.getFile());
        listAccInformeMasterAlbumFoto.add(aifmafo);
        accInformeMasterAlbum = new AccInformeMasterAlbum();
        MovilidadUtil.addSuccessMessage("Fotografía agregada a la lista");
        PrimeFaces.current().executeScript("PF('fotoDlg').hide();");
        PrimeFaces.current().ajax().update(":form-info-master:acc-panel:tabla-fotos");
    }

    /**
     * Permite relacionar el archivo cargado por el usuario con el objeto
     * AccInformeMasterTestigo
     *
     * @param event Evento que permite obtener el archivo cargado por el usuario
     */
    public void cargaVideoTestigo(FileUploadEvent event) {
        AccInformeMasterTestigoVideo aimtv = new AccInformeMasterTestigoVideo();
        aimtv.setAccInformeMasterTestigo(accInformeMasterTestigo);
        aimtv.setUploadedFile(event.getFile());
        listAccInformeMasterTestigoVideo.add(aimtv);
        accInformeMasterTestigo = new AccInformeMasterTestigo();
        MovilidadUtil.addSuccessMessage("Testigo agregado a la lista");
        PrimeFaces.current().executeScript("PF('videoTesDlg').hide();");
        PrimeFaces.current().ajax().update("form-info-master:acc-panel:panel-tes");
    }

    /**
     * Permite relacionar el objeto PrgStopPoint seleccionado por el usuario con
     * el objeto AccInformeMaster
     *
     * @param event Evento que captura el objeto PrgStopPoint
     */
    public void obtenerParada(SelectEvent event) {
        try {
            PrgStopPoint name = (PrgStopPoint) event.getObject();
            accInformeMaster.setIdPrgStoppoint(name);
            MovilidadUtil.addSuccessMessage("Parada cercana cargada correctamente");
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al cargar estación cerca, intentelo nuevamente.");
            System.out.println("Error en obtener parada");
        }
        PrimeFaces.current().executeScript("PF('dlgStopPoint').hide();");
    }

    /**
     * Permite instanciar el objeto AccInformeMaster de acuerdo si el objeto
     * Accidente cuenta con Vehiculo y Empleado
     *
     * @param event Evento que captura el objeto Accidente seleccionado por el
     * usuario.
     */
    public void cargarAccidente(SelectEvent event) {
        try {
            accInformeMaster = new AccInformeMaster();
            Accidente a = (Accidente) event.getObject();
            if (a.getIdVehiculo() == null) {
                MovilidadUtil.addErrorMessage("Este accidente aún no cuenta con vehículo asociado");
                return;
            }
            if (a.getIdEmpleado() == null) {
                MovilidadUtil.addErrorMessage("Este accidente aún no cuenta con empleado asociado");
                return;
            }
            accInformeMaster.setIdAccidente(a);
            accInformeMaster.setFechaInforme(new Date());
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al cargar Accidnete");
        }
    }

    boolean validarDatos() {
        if (accInformeMaster.getLugar() == null) {
            MovilidadUtil.addErrorMessage("Lugar es requerido - Ventana #1");
            return true;
        }
        if (accInformeMaster.getLugar().isEmpty()) {
            MovilidadUtil.addErrorMessage("Lugar es requerido - Ventana #1");
            return true;
        }
        if (listAccInformeMasterVehCond.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se puede generar el informe si no hay vehículos - conductores involucrados");
            return true;
        }
        if (accInformeMaster.getVersionMaster() == null) {
            MovilidadUtil.addErrorMessage("Version del operador es requerido - Venntana #10");
            return true;
        }
        if (accInformeMaster.getVersionMaster().isEmpty()) {
            MovilidadUtil.addErrorMessage("Version del operador es requerido - Venntana #10");
            return true;
        }
        if (accInformeMaster.getIdEmpleadoMaster() == null) {
            MovilidadUtil.addErrorMessage("Máster primer responsable es requerido - Ventana #12");
            return true;
        }
        if (accInformeMaster.getFirmaMaster() == null) {
            MovilidadUtil.addErrorMessage("Firma de máster es requerido - Ventana #12");
            return true;
        }
        if (accInformeMaster.getFirmaMaster().isEmpty()) {
            MovilidadUtil.addErrorMessage("Firma de máster es requerido - Ventana #12");
            return true;
        }
        if (file == null) {
            MovilidadUtil.addErrorMessage("Cargar el bosquejo al sistema es requerido - Ventana #15");
            return true;
        }
        if (accInformeMaster.getVideoOperador() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar si el operaodr otorga vídeo - Ventana #15");
            return true;
        }
        if (accInformeMaster.getVideoOperador() == 1) {
            if (videoOperador == null) {
                MovilidadUtil.addErrorMessage("Usted seleccionó que el operador otorga vídeo, pero no realizó la carga del archivo - Ventana #11");
                return true;
            }
        }
        return false;
    }

    public AccInformeMaster getAccInformeMaster() {
        return accInformeMaster;
    }

    public void setAccInformeMaster(AccInformeMaster accInformeMaster) {
        this.accInformeMaster = accInformeMaster;
    }

    public AccInformeMasterApoyo getAccInformeMasterApoyo() {
        return accInformeMasterApoyo;
    }

    public void setAccInformeMasterApoyo(AccInformeMasterApoyo accInformeMasterApoyo) {
        this.accInformeMasterApoyo = accInformeMasterApoyo;
    }

    public AccInformeMasterBomberos getAccInformeMasterBomberos() {
        return accInformeMasterBomberos;
    }

    public void setAccInformeMasterBomberos(AccInformeMasterBomberos accInformeMasterBomberos) {
        this.accInformeMasterBomberos = accInformeMasterBomberos;
    }

    public AccInformeMasterInspectores getAccInformeMasterInspectores() {
        return accInformeMasterInspectores;
    }

    public void setAccInformeMasterInspectores(AccInformeMasterInspectores accInformeMasterInspectores) {
        this.accInformeMasterInspectores = accInformeMasterInspectores;
    }

    public AccInformeMasterLesionado getAccInformeMasterLesionado() {
        return accInformeMasterLesionado;
    }

    public void setAccInformeMasterLesionado(AccInformeMasterLesionado accInformeMasterLesionado) {
        this.accInformeMasterLesionado = accInformeMasterLesionado;
    }

    public AccInformeMasterMedicos getAccInformeMasterMedicos() {
        return accInformeMasterMedicos;
    }

    public void setAccInformeMasterMedicos(AccInformeMasterMedicos accInformeMasterMedicos) {
        this.accInformeMasterMedicos = accInformeMasterMedicos;
    }

    public AccInformeMasterRecomotos getAccInformeMasterRecomotos() {
        return accInformeMasterRecomotos;
    }

    public void setAccInformeMasterRecomotos(AccInformeMasterRecomotos accInformeMasterRecomotos) {
        this.accInformeMasterRecomotos = accInformeMasterRecomotos;
    }

    public AccInformeMasterTestigo getAccInformeMasterTestigo() {
        return accInformeMasterTestigo;
    }

    public void setAccInformeMasterTestigo(AccInformeMasterTestigo accInformeMasterTestigo) {
        this.accInformeMasterTestigo = accInformeMasterTestigo;
    }

    public AccInformeMasterVehCond getAccInformeMasterVehCond() {
        return accInformeMasterVehCond;
    }

    public void setAccInformeMasterVehCond(AccInformeMasterVehCond accInformeMasterVehCond) {
        this.accInformeMasterVehCond = accInformeMasterVehCond;
    }

    public List<AccInformeMasterAlbumFoto> getListAccInformeMasterAlbumFoto() {
        return listAccInformeMasterAlbumFoto;
    }

    public void setListAccInformeMasterAlbumFoto(List<AccInformeMasterAlbumFoto> listAccInformeMasterAlbumFoto) {
        this.listAccInformeMasterAlbumFoto = listAccInformeMasterAlbumFoto;
    }

    public List<AccInformeMasterApoyo> getListAccInformeMasterApoyo() {
        return listAccInformeMasterApoyo;
    }

    public void setListAccInformeMasterApoyo(List<AccInformeMasterApoyo> listAccInformeMasterApoyo) {
        this.listAccInformeMasterApoyo = listAccInformeMasterApoyo;
    }

    public List<AccInformeMasterBomberos> getListAccInformeMasterBomberos() {
        return listAccInformeMasterBomberos;
    }

    public void setListAccInformeMasterBomberos(List<AccInformeMasterBomberos> listAccInformeMasterBomberos) {
        this.listAccInformeMasterBomberos = listAccInformeMasterBomberos;
    }

    public List<AccInformeMasterInspectores> getListAccInformeMasterInspectores() {
        return listAccInformeMasterInspectores;
    }

    public void setListAccInformeMasterInspectores(List<AccInformeMasterInspectores> listAccInformeMasterInspectores) {
        this.listAccInformeMasterInspectores = listAccInformeMasterInspectores;
    }

    public List<AccInformeMasterLesionado> getListAccInformeMasterLesionado() {
        return listAccInformeMasterLesionado;
    }

    public void setListAccInformeMasterLesionado(List<AccInformeMasterLesionado> listAccInformeMasterLesionado) {
        this.listAccInformeMasterLesionado = listAccInformeMasterLesionado;
    }

    public List<AccInformeMasterMedicos> getListAccInformeMasterMedicos() {
        return listAccInformeMasterMedicos;
    }

    public void setListAccInformeMasterMedicos(List<AccInformeMasterMedicos> listAccInformeMasterMedicos) {
        this.listAccInformeMasterMedicos = listAccInformeMasterMedicos;
    }

    public List<AccInformeMasterRecomotos> getListAccInformeMasterRecomotos() {
        return listAccInformeMasterRecomotos;
    }

    public void setListAccInformeMasterRecomotos(List<AccInformeMasterRecomotos> listAccInformeMasterRecomotos) {
        this.listAccInformeMasterRecomotos = listAccInformeMasterRecomotos;
    }

    public List<AccInformeMasterTestigoVideo> getListAccInformeMasterTestigoVideo() {
        return listAccInformeMasterTestigoVideo;
    }

    public void setListAccInformeMasterTestigoVideo(List<AccInformeMasterTestigoVideo> listAccInformeMasterTestigoVideo) {
        this.listAccInformeMasterTestigoVideo = listAccInformeMasterTestigoVideo;
    }

    public List<AccInformeMasterVehCond> getListAccInformeMasterVehCond() {
        return listAccInformeMasterVehCond;
    }

    public void setListAccInformeMasterVehCond(List<AccInformeMasterVehCond> listAccInformeMasterVehCond) {
        this.listAccInformeMasterVehCond = listAccInformeMasterVehCond;
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

    public AccInformeMasterAgentes getAccInformeMasterAgentes() {
        return accInformeMasterAgentes;
    }

    public void setAccInformeMasterAgentes(AccInformeMasterAgentes accInformeMasterAgentes) {
        this.accInformeMasterAgentes = accInformeMasterAgentes;
    }

    public List<AccInformeMasterAgentes> getListAccInformeMasterAgentes() {
        return listAccInformeMasterAgentes;
    }

    public void setListAccInformeMasterAgentes(List<AccInformeMasterAgentes> listAccInformeMasterAgentes) {
        this.listAccInformeMasterAgentes = listAccInformeMasterAgentes;
    }

    public Integer getI_codTmMaster() {
        return i_codTmMaster;
    }

    public void setI_codTmMaster(Integer i_codTmMaster) {
        this.i_codTmMaster = i_codTmMaster;
    }

    public AccInformeMasterAlbum getAccInformeMasterAlbum() {
        return accInformeMasterAlbum;
    }

    public void setAccInformeMasterAlbum(AccInformeMasterAlbum accInformeMasterAlbum) {
        this.accInformeMasterAlbum = accInformeMasterAlbum;
    }

    public AccInformeMasterVic getAccInformeMasterVic() {
        return accInformeMasterVic;
    }

    public void setAccInformeMasterVic(AccInformeMasterVic accInformeMasterVic) {
        this.accInformeMasterVic = accInformeMasterVic;
    }

    public List<AccInformeMasterVic> getListAccInformeMasterVic() {
        return listAccInformeMasterVic;
    }

    public void setListAccInformeMasterVic(List<AccInformeMasterVic> listAccInformeMasterVic) {
        this.listAccInformeMasterVic = listAccInformeMasterVic;
    }

    public Integer getI_tpVictimaIdentificacion() {
        return i_tpVictimaIdentificacion;
    }

    public void setI_tpVictimaIdentificacion(Integer i_tpVictimaIdentificacion) {
        this.i_tpVictimaIdentificacion = i_tpVictimaIdentificacion;
    }

    public Integer getIdTipoVictima() {
        return idTipoVictima;
    }

    public void setIdTipoVictima(Integer idTipoVictima) {
        this.idTipoVictima = idTipoVictima;
    }

}
