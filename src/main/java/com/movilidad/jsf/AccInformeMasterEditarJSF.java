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
import com.movilidad.util.beans.AccInformeMasterAlbumFoto;
import com.movilidad.util.beans.AccInformeMasterTestigoVideo;
import com.movilidad.utils.DocumentUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@Named(value = "accInformeMasterEditarJSF")
@ViewScoped
public class AccInformeMasterEditarJSF implements Serializable {

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

    private Accidente accidente;

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

    private UploadedFile file;
    private UploadedFile videoOperador;

    public AccInformeMasterEditarJSF() {
    }

    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;

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

    /**
     *
     * @param path ruta en disco del archivo a leer
     * @return StreamedContent del archivo leido
     * @throws Exception Ruta no encontrada o no permitida, arrojará una
     * excepción
     */
    public StreamedContent prepDownloadLocal(String path) throws Exception {
        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
        return MovilidadUtil.prepDownload(path);
    }

    public void actualizar() {
        if (validarDatos()) {
            return;
        }
        accInformeMasterFacadeLocal.edit(accInformeMaster);
        MovilidadUtil.addSuccessMessage("Actualizado con éxito");
    }

    void cargarObjetos() {
        listAccInformeMasterAlbumFoto = new ArrayList<>();
        listAccInformeMasterTestigoVideo = new ArrayList<>();
        listAccInformeMasterAgentes = accInformeMaster.getAccInformeMasterAgentesList();
        listAccInformeMasterApoyo = accInformeMaster.getAccInformeMasterApoyoList();
        listAccInformeMasterBomberos = accInformeMaster.getAccInformeMasterBomberosList();
        listAccInformeMasterInspectores = accInformeMaster.getAccInformeMasterInspectoresList();
        listAccInformeMasterLesionado = accInformeMaster.getAccInformeMasterLesionadoList();
        listAccInformeMasterMedicos = accInformeMaster.getAccInformeMasterMedicosList();
        listAccInformeMasterRecomotos = accInformeMaster.getAccInformeMasterRecomotosList();
        listAccInformeMasterVehCond = accInformeMaster.getAccInformeMasterVehCondList();
        listAccInformeMasterVic = accInformeMaster.getAccInformeMasterVicList();
        if (accInformeMaster.getAccInformeMasterTestigoList() != null) {
            for (AccInformeMasterTestigo aimt : accInformeMaster.getAccInformeMasterTestigoList()) {
                AccInformeMasterTestigoVideo aimtv = new AccInformeMasterTestigoVideo();
                aimtv.setAccInformeMasterTestigo(aimt);
                listAccInformeMasterTestigoVideo.add(aimtv);
            }
        }
        if (accInformeMaster.getAccInformeMasterAlbumList() != null) {
            for (AccInformeMasterAlbum aima : accInformeMaster.getAccInformeMasterAlbumList()) {
                AccInformeMasterAlbumFoto aimaf = new AccInformeMasterAlbumFoto();
                aimaf.setAccInformeMasterAlbum(aima);
                listAccInformeMasterAlbumFoto.add(aimaf);
            }
        }
    }

    /**
     *
     * @param event Evento que permite capturar el objeto Accidente seleccionado
     * por el usuario
     */
    public void obtenerAccidente(SelectEvent event) {
        try {
            accidente = (Accidente) event.getObject();
            if (accidente.getAccInformeMasterList() != null) {
                accInformeMaster = accidente.getAccInformeMasterList().get(0);
                if (accInformeMaster != null) {
                    cargarObjetos();
                }
            }
        } catch (Exception e) {
            System.out.println("Error en obtener parada");
        }
    }

    /**
     * Agregar o Actualizar un objeto AccInformeMasterVic
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
        accInformeMasterVic.setIdAccInformeMaster(accInformeMaster);
        if (accInformeMasterVic.getIdAccInformeMasterVic() != null) {
            accInformeMasterVicFacadeLocal.edit(accInformeMasterVic);
            listAccInformeMasterVic.remove(accInformeMasterVic);
        } else {
            accInformeMasterVicFacadeLocal.create(accInformeMasterVic);
        }
        listAccInformeMasterVic.add(accInformeMasterVic);
        accInformeMasterVic = new AccInformeMasterVic();
        i_tpVictimaIdentificacion = null;
        idTipoVictima = null;
        MovilidadUtil.addSuccessMessage("Victima agregada a la lista");
    }

    /**
     * Agregar o Actualizar un objeto AccInformeMasterAlbum
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
     * Agregar o Actualizar un objeto AccInformeMasterApoyo
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
        accInformeMasterApoyo.setIdAccInformeMaster(accInformeMaster);
        accInformeMasterApoyoFacadeLocal.create(accInformeMasterApoyo);
        listAccInformeMasterApoyo.add(accInformeMasterApoyo);
        accInformeMasterApoyo = new AccInformeMasterApoyo();
        i_codTmMaster = null;
        MovilidadUtil.addSuccessMessage("Máster de apoyo agregado a la lista");
    }

    /**
     * Agregar o Actualizar un objeto AccInformeMasterBomberos
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
        accInformeMasterBomberos.setIdAccInformeMaster(accInformeMaster);
        if (accInformeMasterBomberos.getIdAccInformeMasterBomberos() != null) {
            accInformeMasterBomberosFacadeLocal.edit(accInformeMasterBomberos);
            listAccInformeMasterBomberos.remove(accInformeMasterBomberos);
        } else {
            accInformeMasterBomberosFacadeLocal.create(accInformeMasterBomberos);
        }
        listAccInformeMasterBomberos.add(accInformeMasterBomberos);
        accInformeMasterBomberos = new AccInformeMasterBomberos();
        MovilidadUtil.addSuccessMessage("Unidad de Bomberos agregado a la lista");
    }

    /**
     * Agregar o Actualizar un objeto AccInformeMasterAgentes
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
        accInformeMasterAgentes.setIdAccInformeMaster(accInformeMaster);
        if (accInformeMasterAgentes.getIdAccInformeMasterAgentes() != null) {
            accInformeMasterAgentesFacadeLocal.edit(accInformeMasterAgentes);
            listAccInformeMasterAgentes.remove(accInformeMasterAgentes);
        } else {
            accInformeMasterAgentesFacadeLocal.create(accInformeMasterAgentes);
        }
        listAccInformeMasterAgentes.add(accInformeMasterAgentes);
        accInformeMasterAgentes = new AccInformeMasterAgentes();
        MovilidadUtil.addSuccessMessage("Unidad de Policia o Agente de transito agregado a la lista");
    }

    /**
     * Agregar o Actualizar un objeto AccInformeMasterInspectores
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
        accInformeMasterInspectores.setIdAccInformeMaster(accInformeMaster);
        if (accInformeMasterInspectores.getIdAccInformeMasterInspectores() != null) {
            accInformeMasterInspectoresFacadeLocal.edit(accInformeMasterInspectores);
            listAccInformeMasterInspectores.remove(accInformeMasterInspectores);
        } else {
            accInformeMasterInspectoresFacadeLocal.create(accInformeMasterInspectores);
        }
        listAccInformeMasterInspectores.add(accInformeMasterInspectores);
        accInformeMasterInspectores = new AccInformeMasterInspectores();
        MovilidadUtil.addSuccessMessage("Unidad de Inspectores agregado a la lista");
    }

    /**
     * Agregar o Actualizar un objeto AccInformeMasterLesionado
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
        accInformeMasterLesionado.setIdAccInformeMaster(accInformeMaster);
        if (accInformeMasterLesionado.getIdAccInformeMasterLesionado() != null) {
            accInformeMasterLesionadoFacadeLocal.edit(accInformeMasterLesionado);
            listAccInformeMasterLesionado.remove(accInformeMasterLesionado);
        } else {
            accInformeMasterLesionadoFacadeLocal.create(accInformeMasterLesionado);
        }
        listAccInformeMasterLesionado.add(accInformeMasterLesionado);
        accInformeMasterLesionado = new AccInformeMasterLesionado();
        MovilidadUtil.addSuccessMessage("Lesionado agregado a la lista");
    }

    /**
     * Agregar o Actualizar un objeto AccInformeMasterMedico
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
        accInformeMasterMedicos.setIdAccInformeMaster(accInformeMaster);
        if (accInformeMasterMedicos.getIdAccInformeMasterMedicos() != null) {
            accInformeMasterMedicosFacadeLocal.edit(accInformeMasterMedicos);
            listAccInformeMasterMedicos.remove(accInformeMasterMedicos);
        } else {
            accInformeMasterMedicosFacadeLocal.create(accInformeMasterMedicos);
        }
        listAccInformeMasterMedicos.add(accInformeMasterMedicos);
        accInformeMasterMedicos = new AccInformeMasterMedicos();
        MovilidadUtil.addSuccessMessage("Unidad de Médicos y Paramédicos agregado a la lista");
    }

    /**
     * Agregar o Actualizar un objeto AccInformeMasterRecomoto
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
        accInformeMasterRecomotos.setIdAccInformeMaster(accInformeMaster);
        if (accInformeMasterRecomotos.getIdAccInformeMasterRecomotos() != null) {
            accInformeMasterRecomotosFacadeLocal.edit(accInformeMasterRecomotos);
            listAccInformeMasterRecomotos.remove(accInformeMasterRecomotos);
        } else {
            accInformeMasterRecomotosFacadeLocal.create(accInformeMasterRecomotos);
        }
        listAccInformeMasterRecomotos.add(accInformeMasterRecomotos);
        accInformeMasterRecomotos = new AccInformeMasterRecomotos();
        MovilidadUtil.addSuccessMessage("Unidad de Recomotos agregado a la lista");
    }

    /**
     * Agregar o Actualizar un objeto AccInformeMasterTestigo
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
        if (accInformeMasterTestigo.getIdAccInformeMasterTestigo() != null) { //condicional para determinar si se trata de una instancia nueva, o actualizar un objeto.
            if (accInformeMasterTestigo.getVideo() == 0) { // permite conocer si el usuario cargará al sistema un archivo. 0 para NO, 1 para SI
                if (accInformeMasterTestigo.getPathVideo() != null) {
                    //  Util.deleteFile(accInformeMasterTestigo.getPathVideo());
                    accInformeMasterTestigo.setPathVideo(null);
                    accInformeMasterTestigo.setHoraVideo(null);
                }
                listAccInformeMasterTestigoVideo.add(new AccInformeMasterTestigoVideo(accInformeMasterTestigo, null));
                accInformeMasterTestigoFacadeLocal.edit(accInformeMasterTestigo);
                accInformeMasterTestigo = new AccInformeMasterTestigo();
                MovilidadUtil.addSuccessMessage("Testigo agregado a la lista");
            } else {
                PrimeFaces.current().executeScript("PF('videoTesDlg').show()");
            }
        } else {
            if (accInformeMasterTestigo.getVideo() == 0) {
                listAccInformeMasterTestigoVideo.add(new AccInformeMasterTestigoVideo(accInformeMasterTestigo, null));
                accInformeMasterTestigo.setIdAccInformeMaster(accInformeMaster);
                accInformeMasterTestigoFacadeLocal.create(accInformeMasterTestigo);
                accInformeMasterTestigo = new AccInformeMasterTestigo();
                MovilidadUtil.addSuccessMessage("Testigo agregado a la lista");
            } else {
                PrimeFaces.current().executeScript("PF('videoTesDlg').show()");
            }
        }
    }

    /**
     * Agregar o Actualizar un objeto InformeMasterTestigo, a demas, habilita un
     * nuevo formulario donde el usuario puede cargar un nuevo archivo
     */
    public void actualizarVideoTestigo() {
        accInformeMasterTestigoFacadeLocal.edit(accInformeMasterTestigo);
        listAccInformeMasterTestigoVideo.add(new AccInformeMasterTestigoVideo(accInformeMasterTestigo, null));
        accInformeMasterTestigo = new AccInformeMasterTestigo();
        MovilidadUtil.addSuccessMessage("Registro actualizado");
        PrimeFaces.current().executeScript("PF('videoTesDlg').hide();");
        PrimeFaces.current().ajax().update("form-info-master:acc-panel:panel-tes");
    }

    /**
     * Agregar o Actualizar un objeto AccInformeMasterVehCond
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
        accInformeMasterVehCond.setIdAccInformeMaster(accInformeMaster);
        if (accInformeMasterVehCond.getIdAccInformeMasterVehCond() != null) {
            accInformeMasterVehCondFacadeLocal.edit(accInformeMasterVehCond);
            listAccInformeMasterVehCond.remove(accInformeMasterVehCond);
        } else {
            accInformeMasterVehCondFacadeLocal.create(accInformeMasterVehCond);
        }
        listAccInformeMasterVehCond.add(accInformeMasterVehCond);
        accInformeMasterVehCond = new AccInformeMasterVehCond();
        c_codVehiculo = null;
        i_codTm = null;
        MovilidadUtil.addSuccessMessage("Vehículo - Conductor agregado a la lista");
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
        String ruta = "";
        String oldRuta = accInformeMaster.getPathVideoOpe() != null ? accInformeMaster.getPathVideoOpe() : null;
        try {
            if (videoOperador != null) {
                ruta = MovilidadUtil.cargarArchivosAccidentalidad(videoOperador, accInformeMaster.getIdAccidente().getIdAccidente(), "InformeMaster", accInformeMaster.getIdAccInformeMaster(), "videoOpe");
                accInformeMaster.setPathVideoOpe(ruta);
                accInformeMasterFacadeLocal.edit(accInformeMaster);
                if (oldRuta != null) {
                    Util.deleteFile(oldRuta);
                }
            }
        } catch (Exception e) {
            Util.deleteFile(ruta);
        } finally {
            videoOperador = null;
        }
        PrimeFaces.current().executeScript("PF('videoDlg').hide();");
        MovilidadUtil.addSuccessMessage("Vídeo cargado correctamente");
    }

    /**
     * Permite escribir en disco el archivo cargado por el usuario
     *
     * @param event Evento que permite obtener el archivo cargado por el usuario
     */
    public void cargaBosquejo(FileUploadEvent event) {
        file = event.getFile();
        String ruta = "";
        String oldRuta = accInformeMaster.getPathBosquejo() != null ? accInformeMaster.getPathBosquejo() : null;
        try {
            if (file != null) {
                ruta = MovilidadUtil.cargarArchivosAccidentalidad(file, accInformeMaster.getIdAccidente().getIdAccidente(), "/InformeMaster", accInformeMaster.getIdAccInformeMaster(), "bosquejo");
                accInformeMaster.setPathBosquejo(ruta);
                accInformeMasterFacadeLocal.edit(accInformeMaster);
                if (oldRuta != null) {
                    Util.deleteFile(oldRuta);
                }
                file = null;
            }
        } catch (Exception e) {
            Util.deleteFile(ruta);
        }
        PrimeFaces.current().executeScript("PF('bosquejoDlg').hide();");
        MovilidadUtil.addSuccessMessage("Archivo cargado correctamente");
    }

    /**
     * Permite escribir en disco el archivo cargado por el usuario
     *
     * @param event Evento que permite obtener el archivo cargado por el usuario
     */
    public void cargaFotoAlbum(FileUploadEvent event) {
        String rutaOld = accInformeMasterAlbum.getPathFoto() != null ? accInformeMasterAlbum.getPathFoto() : null;
        try {
            String ruta;
            int i = (int) (Math.random() * 1000 + 25);
            ruta = MovilidadUtil.cargarArchivosAccidentalidad(event.getFile(), accInformeMaster.getIdAccidente().getIdAccidente(), "InformeMaster", accInformeMaster.getIdAccInformeMaster(), "album-" + String.valueOf(i));
            accInformeMasterAlbum.setPathFoto(ruta);
            accInformeMasterAlbum.setIdAccInformeMaster(accInformeMaster);
            accInformeMasterAlbumFacadeLocal.edit(accInformeMasterAlbum);
            listAccInformeMasterAlbumFoto.add(new AccInformeMasterAlbumFoto(accInformeMasterAlbum, null));
            accInformeMasterAlbum = new AccInformeMasterAlbum();
            if (rutaOld != null) {
                Util.deleteFile(rutaOld);
            }
            MovilidadUtil.addSuccessMessage("Registro agregada a la lista");
            PrimeFaces.current().executeScript("PF('fotoDlg').hide();");
            PrimeFaces.current().ajax().update(":form-info-master:acc-panel:tabla-fotos");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Permite actualizar un archivo o las caracteristicas del objeto
     * AccInformeMasterAlbum
     */
    public void actualizarFotoAlbum() {
        if (accInformeMasterAlbum.getPathFoto() != null) {
            accInformeMasterAlbumFacadeLocal.edit(accInformeMasterAlbum);
            listAccInformeMasterAlbumFoto.add(new AccInformeMasterAlbumFoto(accInformeMasterAlbum, null));
            accInformeMasterAlbum = new AccInformeMasterAlbum();
            MovilidadUtil.addSuccessMessage("Registro actualizado");
            PrimeFaces.current().executeScript("PF('fotoDlg').hide();");
            PrimeFaces.current().ajax().update(":form-info-master:acc-panel:album");
        } else {
            MovilidadUtil.addErrorMessage("Opción no valida, No puede agregar un registro sin fotografía");
        }
    }

    /**
     * Permite escribir en disco el archivo cargado por el usuario
     *
     * @param event Evento que permite obtener el archivo cargado por el usuario
     */
    public void cargaVideoTestigo(FileUploadEvent event) {
        String ruta = "";
        try {
            int i = (int) (Math.random() * 1000 + 25);
            listAccInformeMasterTestigoVideo.add(new AccInformeMasterTestigoVideo(accInformeMasterTestigo, null));
            ruta = MovilidadUtil.cargarArchivosAccidentalidad(event.getFile(), accInformeMaster.getIdAccidente().getIdAccidente(), "InformeMaster", accInformeMaster.getIdAccInformeMaster(), "videoTestigo-" + String.valueOf(i));
            accInformeMasterTestigo.setPathVideo(ruta);
            if (accInformeMasterTestigo.getIdAccInformeMaster() != null) {
                accInformeMasterTestigoFacadeLocal.edit(accInformeMasterTestigo);
            } else {
                accInformeMasterTestigo.setIdAccInformeMaster(accInformeMaster);
                accInformeMasterTestigoFacadeLocal.create(accInformeMasterTestigo);
            }
            accInformeMasterTestigo = new AccInformeMasterTestigo();
            MovilidadUtil.addSuccessMessage("Testigo agregado a la lista");
            PrimeFaces.current().executeScript("PF('videoTesDlg').hide();");
            PrimeFaces.current().ajax().update("form-info-master:acc-panel:panel-tes");
        } catch (Exception e) {
            Util.deleteFile(ruta);
        }
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

    public void eventVictima(AccInformeMasterVic event) {
        try {
            accInformeMasterVic = event;
            i_tpVictimaIdentificacion = accInformeMasterVic.getIdTipoIdentificacion() != null
                    ? accInformeMasterVic.getIdTipoIdentificacion().getIdEmpleadoTipoIdentificacion()
                    : null;
            idTipoVictima = accInformeMasterVic.getIdAccTipoVictima() != null
                    ? accInformeMasterVic.getIdAccTipoVictima().getIdAccTipoVictima()
                    : null;
        } catch (Exception e) {
        }
    }

    public void eventLesionado(AccInformeMasterLesionado event) {
        try {
            accInformeMasterLesionado = event;
        } catch (Exception e) {
        }
    }

    public void eventAlbum(AccInformeMasterAlbumFoto event) {
        accInformeMasterAlbum = event.getAccInformeMasterAlbum();
        listAccInformeMasterAlbumFoto.remove(event);
    }

    public void eventTestigo(AccInformeMasterTestigoVideo event) {
        accInformeMasterTestigo = event.getAccInformeMasterTestigo();
        listAccInformeMasterTestigoVideo.remove(event);
    }

    public void eventAlbumCancel() {
        if (accInformeMasterAlbum.getPathFoto() != null) {
            listAccInformeMasterAlbumFoto.add(new AccInformeMasterAlbumFoto(accInformeMasterAlbum, null));
        }
        accInformeMasterAlbum = new AccInformeMasterAlbum();
    }

    public void eventVehiculoConductor(AccInformeMasterVehCond event) {
        try {
            accInformeMasterVehCond = event;
            c_codVehiculo = accInformeMasterVehCond.getCodigo() != null ? accInformeMasterVehCond.getCodigo() : null;
            i_codTm = accInformeMasterVehCond.getCodigoTm() != null ? Integer.parseInt(accInformeMasterVehCond.getCodigoTm()) : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eventAgentes(AccInformeMasterAgentes event) {
        try {
            accInformeMasterAgentes = event;
        } catch (Exception e) {
        }
    }

    public void eventRecomotos(AccInformeMasterRecomotos event) {
        try {
            accInformeMasterRecomotos = event;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eventMedicos(AccInformeMasterMedicos event) {
        try {
            accInformeMasterMedicos = event;
        } catch (Exception e) {
        }
    }

    public void eventBomberos(AccInformeMasterBomberos event) {
        try {
            accInformeMasterBomberos = event;
        } catch (Exception e) {
        }
    }

    public void eventInspectores(AccInformeMasterInspectores event) {
        try {
            accInformeMasterInspectores = event;
        } catch (Exception e) {
        }
    }

    public void eventMasterApoyo(AccInformeMasterApoyo event) {
        try {
            accInformeMasterApoyoFacadeLocal.remove(event);
            listAccInformeMasterApoyo.remove(event);
            accInformeMasterApoyo = new AccInformeMasterApoyo();
            MovilidadUtil.addSuccessMessage("Master desvindulado con éxito");
        } catch (Exception e) {
            e.printStackTrace();
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
            MovilidadUtil.addErrorMessage("Versión del operador es requerido - Venntana #10");
            return true;
        }
        if (accInformeMaster.getVersionMaster().isEmpty()) {
            MovilidadUtil.addErrorMessage("Versión del operador es requerido - Venntana #10");
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
        if (accInformeMaster.getVideoOperador() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar si el operaodr otorga vídeo - Ventana #15");
            return true;
        }
//        if (accInformeMaster.getVideoOperador() == 1) {
//            if (videoOperador == null) {
//                MovilidadUtil.addErrorMessage("Usted seleccionó que el operador otorga vídeo, pero no realizó la carga del archivo - Ventana #11");
//                return true;
//            }
//        }
        return false;
    }

    public Accidente getAccidente() {
        return accidente;
    }

    public void setAccidente(Accidente accidente) {
        this.accidente = accidente;
    }

    public AccInformeMaster getAccInformeMaster() {
        return accInformeMaster;
    }

    public void setAccInformeMaster(AccInformeMaster accInformeMaster) {
        this.accInformeMaster = accInformeMaster;
    }

    public AccInformeMasterAlbum getAccInformeMasterAlbum() {
        return accInformeMasterAlbum;
    }

    public void setAccInformeMasterAlbum(AccInformeMasterAlbum accInformeMasterAlbum) {
        this.accInformeMasterAlbum = accInformeMasterAlbum;
    }

    public AccInformeMasterAgentes getAccInformeMasterAgentes() {
        return accInformeMasterAgentes;
    }

    public void setAccInformeMasterAgentes(AccInformeMasterAgentes accInformeMasterAgentes) {
        this.accInformeMasterAgentes = accInformeMasterAgentes;
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

    public AccInformeMasterVic getAccInformeMasterVic() {
        return accInformeMasterVic;
    }

    public void setAccInformeMasterVic(AccInformeMasterVic accInformeMasterVic) {
        this.accInformeMasterVic = accInformeMasterVic;
    }

    public List<AccInformeMasterAgentes> getListAccInformeMasterAgentes() {
        return listAccInformeMasterAgentes;
    }

    public void setListAccInformeMasterAgentes(List<AccInformeMasterAgentes> listAccInformeMasterAgentes) {
        this.listAccInformeMasterAgentes = listAccInformeMasterAgentes;
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

    public List<AccInformeMasterVehCond> getListAccInformeMasterVehCond() {
        return listAccInformeMasterVehCond;
    }

    public void setListAccInformeMasterVehCond(List<AccInformeMasterVehCond> listAccInformeMasterVehCond) {
        this.listAccInformeMasterVehCond = listAccInformeMasterVehCond;
    }

    public List<AccInformeMasterAlbumFoto> getListAccInformeMasterAlbumFoto() {
        return listAccInformeMasterAlbumFoto;
    }

    public void setListAccInformeMasterAlbumFoto(List<AccInformeMasterAlbumFoto> listAccInformeMasterAlbumFoto) {
        this.listAccInformeMasterAlbumFoto = listAccInformeMasterAlbumFoto;
    }

    public List<AccInformeMasterVic> getListAccInformeMasterVic() {
        return listAccInformeMasterVic;
    }

    public void setListAccInformeMasterVic(List<AccInformeMasterVic> listAccInformeMasterVic) {
        this.listAccInformeMasterVic = listAccInformeMasterVic;
    }

    public List<AccInformeMasterTestigoVideo> getListAccInformeMasterTestigoVideo() {
        return listAccInformeMasterTestigoVideo;
    }

    public void setListAccInformeMasterTestigoVideo(List<AccInformeMasterTestigoVideo> listAccInformeMasterTestigoVideo) {
        this.listAccInformeMasterTestigoVideo = listAccInformeMasterTestigoVideo;
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

    public Integer getI_codTmMaster() {
        return i_codTmMaster;
    }

    public void setI_codTmMaster(Integer i_codTmMaster) {
        this.i_codTmMaster = i_codTmMaster;
    }

    public Integer getI_tpVictimaIdentificacion() {
        return i_tpVictimaIdentificacion;
    }

    public void setI_tpVictimaIdentificacion(Integer i_tpVictimaIdentificacion) {
        this.i_tpVictimaIdentificacion = i_tpVictimaIdentificacion;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getVideoOperador() {
        return videoOperador;
    }

    public void setVideoOperador(UploadedFile videoOperador) {
        this.videoOperador = videoOperador;
    }

    public Integer getIdTipoVictima() {
        return idTipoVictima;
    }

    public void setIdTipoVictima(Integer idTipoVictima) {
        this.idTipoVictima = idTipoVictima;
    }

}
