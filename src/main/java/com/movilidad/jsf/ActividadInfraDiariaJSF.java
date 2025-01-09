/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ActividadInfraDiariaFacadeLocal;
import com.movilidad.ejb.CableEstacionFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.ActividadInfraTipoDetFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.NovedadInfrastrucFacadeLocal;
import com.movilidad.ejb.NovedadTipoDetallesInfrastrucFacadeLocal;
import com.movilidad.ejb.NovedadTipoInfrastrucFacadeLocal;
import com.movilidad.ejb.SegPilonaFacadeLocal;
import com.movilidad.model.ActividadInfraDiaria;
import com.movilidad.model.CableEstacion;
import com.movilidad.model.Empleado;
import com.movilidad.model.ActividadInfraTipo;
import com.movilidad.model.ActividadInfraTipoDet;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.NovedadInfrastruc;
import com.movilidad.model.NovedadTipoDetallesInfrastruc;
import com.movilidad.model.NovedadTipoInfrastruc;
import com.movilidad.model.SegPilona;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.NovedadInfraArchivo;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Permite gestionar la dta del objeto ActividadInfraDiariaDiara principal tabla
 * afectada novedad_mtto_diaria
 *
 * @author cesar
 */
@Named(value = "actividadInfraDiariaJSF")
@ViewScoped
public class ActividadInfraDiariaJSF implements Serializable {

    @EJB
    private ActividadInfraDiariaFacadeLocal actividadInfraEJB;
    @EJB
    private CableEstacionFacadeLocal estacionEJB;
    @EJB
    private SegPilonaFacadeLocal segPilonaEJB;
    @EJB
    private EmpleadoFacadeLocal empleadoEJB;
    @EJB
    private ActividadInfraTipoDetFacadeLocal tipoActividadDetEJB;
    @EJB
    private NovedadTipoInfrastrucFacadeLocal novedadTipoInfrastrucEJB;
    @EJB
    private NovedadTipoDetallesInfrastrucFacadeLocal novedadTipoDetallesInfrastrucEJB;
    @EJB
    private NovedadInfrastrucFacadeLocal novedadInfrastrucEJB;

    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;

    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private ActividadInfraDiaria actividadInfraDiaria;
    private List<ActividadInfraDiaria> listActividadInfraDiaria;
    private List<ActividadInfraTipoDet> listActividadInfraTipoDet;
    private List<CableEstacion> listCableEstacion;
    private List<SegPilona> listSegPilona;
    private List<Empleado> listEmpleado;
    private List<UploadedFile> listFiles;
    private List<String> listNombreEmp;
    private List<NovedadTipoInfrastruc> listNovInfraTipo;
    private List<NovedadTipoDetallesInfrastruc> listNovInfraTipoDet;
    private List<UploadedFile> archivosNovInfra;
    private List<NovedadInfraArchivo> listNovedadesInfra;
    private List<String> listFotos = new ArrayList<>();
    private List<String> lista_fotos_remover = new ArrayList<>();

    private NovedadTipoInfrastruc novedadInfraTipo;
    private NovedadTipoDetallesInfrastruc novedadInfraTipoDet;

    private Integer idActividadInfraTipo;
    private Integer idActividadInfraTipoDet;
    private int i_novedad_infra_tipo;
    private int i_novedad_infra_tipo_det;
    private boolean flag_rremove_photo;

    private Date dInicio;
    private NovedadInfrastruc novedad;
    private Date dFin = new Date();

    /**
     * Creates a new instance of ActividadInfraDiariaJSF
     */
    public ActividadInfraDiariaJSF() throws ParseException {
        this.dInicio = MovilidadUtil.dateSinHora(new Date());
    }

    @PostConstruct
    public void init() {
        listActividadInfraTipoDet = null;
        listCableEstacion = null;
        listSegPilona = null;
        listEmpleado = null;
        listNombreEmp = null;
        listFiles = null;
        idActividadInfraTipo = null;
        idActividadInfraTipoDet = null;
        actividadInfraDiaria = null;
        buscar();
    }

    /**
     * Genera la instancia del objeto ActividadInfraDiaria
     */
    public void prepareGuardar() {
        actividadInfraDiaria = new ActividadInfraDiaria();
        actividadInfraDiaria.setFechaHora(MovilidadUtil.fechaCompletaHoy());
        listFiles = new ArrayList<>();
        listNombreEmp = new ArrayList<>();
        listNovedadesInfra = new ArrayList<>();
    }

    /**
     * Carga la variable novTipoInfrastruc de tipo NovedadTipoInfrastruc, la
     * lista listNovTipoDetInfrastruc de tipo NovedadTipoDetallesInfrastruc y la
     * variable novTipoDetInfrastruc de tipo NovedadTipoDetallesInfrastruc.
     *
     * @param opc
     */
    public void prepareTipoDetalles(boolean opc) {
        if (opc) {
            novedadInfraTipo = null;
            for (NovedadTipoInfrastruc gt : listNovInfraTipo) {
                if (gt.getIdNovedadTipoInfrastruc().equals(i_novedad_infra_tipo)) {
                    novedadInfraTipo = gt;
                    listNovInfraTipoDet = gt.getNovedadTipoDetallesInfrastrucList();
                    break;
                }
            }
        } else {
            novedadInfraTipoDet = null;
            for (NovedadTipoDetallesInfrastruc gtd : listNovInfraTipoDet) {
                if (gtd.getIdNovedadTipoDetInfrastruc().equals(i_novedad_infra_tipo_det)) {
                    novedadInfraTipoDet = gtd;
                    break;
                }
            }
        }

    }

    /**
     * Elimnar una novedad de la lista temporal de novedades.
     *
     * @param nov
     */
    public void eliminarNovedad(NovedadInfraArchivo nov) {
        listNovedadesInfra.remove(nov);
        MovilidadUtil.addSuccessMessage("Novedad eliminada");
    }

    public void delete(String url) {
        lista_fotos_remover.add(url);
        listFotos.remove(url);
    }

    /**
     * Prepara las variables para agregar una nueva novedad a la lista temporal
     * de novedades.
     */
    public void preAgregarNov() {
        i_novedad_infra_tipo = 0;
        i_novedad_infra_tipo_det = 0;
        listNovInfraTipo = novedadTipoInfrastrucEJB.findAllByEstadoReg();
        archivosNovInfra = new ArrayList<>();
        novedad = new NovedadInfrastruc();
        novedad.setFechaHoraNov(MovilidadUtil.fechaCompletaHoy());
        novedad.setFechaHoraReg(MovilidadUtil.fechaCompletaHoy());
        MovilidadUtil.openModal("nov_infra_add_wv");
    }

    /**
     * Agrega archivos para novedades al array de tipo UploadedFile qeu luego
     * van a ser persistidos.
     *
     * @param event
     */
    public void handleFileUploadFotosNovedad(FileUploadEvent event) {
        archivosNovInfra.add(event.getFile());
        MovilidadUtil.addSuccessMessage("Foto(s) Cargada(s)");
    }

    /**
     * Agrega las novedad en la lista temporal que luego van a ser persistidos.
     *
     * @throws ParseException
     */
    public void agregarNovedad() throws ParseException {
        if (novedadInfraTipo == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar el tipo de novedad");
            return;
        }
        if (novedadInfraTipoDet == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar el tipo de novedad detalles");
            return;
        }
        if (MovilidadUtil.fechasIgualMenorMayor(novedad.getFechaHoraReg(), MovilidadUtil.fechaHoy(), false) == 2) {
            MovilidadUtil.addErrorMessage("La fecha no debe ser posterior a la fecha actual.");
            return;
        }
        NovedadInfraArchivo novedadInfraArchivo = new NovedadInfraArchivo();
        novedad.setNovedadTipoInfrastruc(novedadInfraTipo);
        novedad.setNovedadTipoDetallesInfrastruc(novedadInfraTipoDet);
        novedad.setUsername(user.getUsername());
        novedadInfraArchivo.setNovedadInfrastruc(novedad);
        novedadInfraArchivo.setListasArchivo(archivosNovInfra);
        listNovedadesInfra.add(novedadInfraArchivo);
        i_novedad_infra_tipo = 0;
        i_novedad_infra_tipo_det = 0;
        novedadInfraTipo = null;
        novedadInfraTipoDet = null;
        MovilidadUtil.hideModal("nov_infra_add_wv");
    }

    /**
     * Permite persistir la data del objeto ActividadInfraDiaria
     */
    public void guardar() throws IOException {
        if (actividadInfraDiaria != null) {
            if (actividadInfraDiaria.getIdEstacion() == null
                    && actividadInfraDiaria.getIdPilona() == null) {
                MovilidadUtil.addErrorMessage("Debe seleccionar Estación o Pilona");
                return;
            }
            if (idActividadInfraTipoDet == null) {
                MovilidadUtil.addErrorMessage("Novedad Mantenimineto Tipo Actividad Detalle es requerido");
                return;
            }
            if (idActividadInfraTipo == null) {
                MovilidadUtil.addErrorMessage("Novedad Mantenimineto Tipo Actividad es requerido");
                return;
            }
            actividadInfraDiaria.setNombresEmpleados(getStringNombres());
            actividadInfraDiaria.setIdActividadInfraTipoDet(new ActividadInfraTipoDet(idActividadInfraTipoDet));
            actividadInfraDiaria.setIdActividadInfraTipo(new ActividadInfraTipo(idActividadInfraTipo));
            actividadInfraDiaria.setCreado(new Date());
            actividadInfraDiaria.setModificado(new Date());
            actividadInfraDiaria.setUsername(user.getUsername());
            actividadInfraDiaria.setEstadoReg(0);
            actividadInfraEJB.create(actividadInfraDiaria);
            actividadInfraDiaria.setPathFotos(guardarFotos(actividadInfraDiaria.getIdActividadInfraDiaria()));
            actividadInfraEJB.edit(actividadInfraDiaria);
            if (!listNovedadesInfra.isEmpty()) {
                for (NovedadInfraArchivo nov : listNovedadesInfra) {
                    nov.getNovedadInfrastruc().setIdActividadInfraDiaria(actividadInfraDiaria);
                    guardarNovedad(nov);
                }
            }
            init();
            prepareGuardar();
            MovilidadUtil.addSuccessMessage("Novedad Mantenimiento creada con éxito");
            buscar();
        }
    }

    public void guardarNovedad(NovedadInfraArchivo param) throws IOException {
        param.getNovedadInfrastruc().setIdSegPilona(param.getNovedadInfrastruc().getIdSegPilona());
        param.getNovedadInfrastruc().setCreado(MovilidadUtil.fechaCompletaHoy());
        param.getNovedadInfrastruc().setEstado(1);
        param.getNovedadInfrastruc().setUserReporta(user.getUsername());
        novedadInfrastrucEJB.create(param.getNovedadInfrastruc());

        if (!param.getListasArchivo().isEmpty()) {
            String path = "/";
            for (UploadedFile f : param.getListasArchivo()) {
                path = Util.saveFile(f, param.getNovedadInfrastruc().getIdNovedadInfrastruc(), "novedadInfrastruct");
            }
            param.getNovedadInfrastruc().setPathFotos(path);
            novedadInfrastrucEJB.edit(param.getNovedadInfrastruc());
        }
        if (param.getNovedadInfrastruc().getNovedadTipoDetallesInfrastruc().getNotifica().equals(1)
                && param.getNovedadInfrastruc().getNovedadTipoDetallesInfrastruc().getEmails() != null) {
            notificar(param.getNovedadInfrastruc());
        }
    }

    private void notificar(NovedadInfrastruc novedad) throws IOException {
        List<String> adjuntos;
        adjuntos = MovilidadUtil.getListasFotos(novedad.getIdNovedadInfrastruc(),
                "novedadMtto", novedad.getPathFotos());
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("fecha", Util.dateFormat(novedad.getFechaHoraNov()));
        mailProperties.put("pilona", novedad.getIdSegPilona() != null
                ? novedad.getIdSegPilona().getNombre() : "N/A");
        mailProperties.put("tipo", novedad.getNovedadTipoInfrastruc().getNombre());
        mailProperties.put("detalle", novedad.getNovedadTipoDetallesInfrastruc().getNombre());
        mailProperties.put("username", novedad.getUsername());
        mailProperties.put("generada", Util.dateTimeFormat(novedad.getCreado()));
        mailProperties.put("descripcion", novedad.getDescripcion());
        String subject = "Novedad Componente";
        String destinatarios = novedad.getNovedadTipoDetallesInfrastruc().getEmails();

        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", adjuntos != null ? adjuntos : null);
    }

    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate("novedadInfraTemplate");
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    /**
     * Permite capturar el objeto ActividadInfraDiaria seleccionado por el
     * usuario
     *
     * @param event Objeto ActividadInfraDiaria
     */
    public void onActividadInfraDiaria(ActividadInfraDiaria event) throws IOException {
        actividadInfraDiaria = event;
        idActividadInfraTipo = event.getIdActividadInfraTipo().getIdActividadInfraTipo();
        idActividadInfraTipoDet = event.getIdActividadInfraTipoDet().getIdActividadInfraTipoDet();
        cargarListaActividadInfraTipoDet();
        convertirStringLista();
        flag_rremove_photo = esAlterable();
        listFiles = new ArrayList<>();
        listFotos = MovilidadUtil.getListasFotos(actividadInfraDiaria.getIdActividadInfraDiaria(),
                "actividadInfraDiario", actividadInfraDiaria.getPathFotos());
        listNovedadesInfra = new ArrayList<>();
        for (NovedadInfrastruc obj : event.getnovedadInfrastrucList()) {
            NovedadInfraArchivo archivoNov = new NovedadInfraArchivo();
            archivoNov.setNovedadInfrastruc(obj);
            listNovedadesInfra.add(archivoNov);
        }
    }

    public boolean esAlterable() {
        if (actividadInfraDiaria == null) {
            return false;
        }
        for (GrantedAuthority i : user.getAuthorities()) {
            if (i.getAuthority().equals("PROFGEN")) {
                return true;
            }
        }
        if (actividadInfraDiaria.getUsername().equals(user.getUsername())) {
            return true;
        }
        return false;
    }

    /**
     * Permite actualizar la data del objeto ActividadInfraDiaria
     */
    public void actualizar() throws IOException {
        if (actividadInfraDiaria != null) {
            if (actividadInfraDiaria.getIdEstacion() == null
                    && actividadInfraDiaria.getIdPilona() == null) {
                MovilidadUtil.addErrorMessage("Debe seleccionar Estación o Pilona");
                return;
            }
            if (idActividadInfraTipoDet == null) {
                MovilidadUtil.addErrorMessage("Novedad Mantenimineto Tipo Actividad Detalle es requerido");
                return;
            }
            if (idActividadInfraTipo == null) {
                MovilidadUtil.addErrorMessage("Novedad Mantenimineto Tipo Actividad es requerido");
                return;
            }
            actividadInfraDiaria.setNombresEmpleados(getStringNombres());
            actividadInfraDiaria.setIdActividadInfraTipoDet(new ActividadInfraTipoDet(idActividadInfraTipoDet));
            actividadInfraDiaria.setIdActividadInfraTipo(new ActividadInfraTipo(idActividadInfraTipo));
            actividadInfraDiaria.setModificado(new Date());
            if (!listFiles.isEmpty()) {
                actividadInfraDiaria.setPathFotos(guardarFotos(actividadInfraDiaria.getIdActividadInfraDiaria()));
            }
            for (String url : lista_fotos_remover) {
                MovilidadUtil.eliminarFichero(url);
            }
            actividadInfraEJB.edit(actividadInfraDiaria);
            if (!listNovedadesInfra.isEmpty()) {
                for (NovedadInfraArchivo nov : listNovedadesInfra) {
                    if (nov.getNovedadInfrastruc().getIdNovedadInfrastruc() == null) {
                        nov.getNovedadInfrastruc().setIdActividadInfraDiaria(actividadInfraDiaria);
                        guardarNovedad(nov);
                    }
                }
            }
            init();
            MovilidadUtil.addSuccessMessage("Novedad Mantenimiento actualizada con éxito");
            MovilidadUtil.hideModal("modalDlg");
            buscar();
        }
    }

    /**
     * Permite capturar el objeto CableEstacion seleccionado por el usuario
     *
     * @param event Objeto CableEstacion
     */
    public void onGetEstacion(SelectEvent event) {
        CableEstacion ce = (CableEstacion) event.getObject();
        actividadInfraDiaria.setIdEstacion(ce);
        MovilidadUtil.addSuccessMessage("Estación seleccionada con éxito");
        MovilidadUtil.hideModal("estacionDlg");
    }

    /**
     * Permite capturar el objeto SegPilona seleccionado por el usuario
     *
     * @param event Objeto SegPilona
     */
    public void onGetPilona(SelectEvent event) {
        SegPilona sp = (SegPilona) event.getObject();
        actividadInfraDiaria.setIdPilona(sp);
        MovilidadUtil.addSuccessMessage("Pilona seleccionada con éxito");
        MovilidadUtil.hideModal("pilonaDlg");
    }

    /**
     * Permite hacer null actividadInfraDiaria.setIdEstacion(null);
     */
    public void onNullEstacion() {
        actividadInfraDiaria.setIdEstacion(null);
        MovilidadUtil.addSuccessMessage("Estación null");
        MovilidadUtil.hideModal("estacionDlg");
    }

    /**
     * Permite hacer null actividadInfraDiaria.setIdPilona(null);
     */
    public void onNullPilona() {
        actividadInfraDiaria.setIdPilona(null);
        MovilidadUtil.addSuccessMessage("Pilona null");
        MovilidadUtil.hideModal("pilonaDlg");
    }

    /**
     * Permite capturar el objeto Empleado seleccionado por el usuario
     *
     * @param event Objeto Empleado
     */
    public void onSelectEmpleado(SelectEvent event) {
        Empleado emp = (Empleado) event.getObject();
        listNombreEmp.add(emp.getNombres() + " " + emp.getApellidos());
        MovilidadUtil.addSuccessMessage("Empleado agregado");
        MovilidadUtil.hideModal("listEmpDlg");
    }

    /**
     * Permite consultar los registros de CableEstacion
     */
    public void cargarListaEstacion() {
        listCableEstacion = estacionEJB.findByEstadoReg();
    }

    /**
     * Permite consultar los registros de SegPilona
     */
    public void cargarListaPilona() {
        listSegPilona = segPilonaEJB.findByEstadoReg();
    }

    /**
     * Permite consultar los registros de Empleado
     */
    public void cargarListaEmpleado() {
        listEmpleado = empleadoEJB.findAllEmpleadosActivos(0);
    }

    /**
     * Permite consultar los registros de ActividadInfraTipoDet
     */
    public void cargarListaActividadInfraTipoDet() {
        listActividadInfraTipoDet = tipoActividadDetEJB.findByActividadInfraTipo(idActividadInfraTipo);
    }

    /**
     * Evento que permite capturar el archivo subido por el usuario
     *
     * @param event Objeto FileUploadEvent
     */
    public void handleFileUpload(FileUploadEvent event) {
        listFiles.add(event.getFile());
    }

    /**
     * Elimina un objeto UploadedFile que se encuentre en el objeto List de
     * UploadedFile
     *
     * @param path ruta en memoria
     */
    public void eliminarFotoMM(String path) {
        UploadedFile upAux = null;
        for (UploadedFile up : listFiles) {
            if (up.getFileName().equals(path)) {
                upAux = up;
                break;
            }
        }
        if (upAux != null) {
            listFiles.remove(upAux);
        }
    }

    /**
     * Elimina un registro que se encuentre en disco
     *
     * @param path ruta en disco
     */
    public void eliminarFotoDD(String path) {
        Util.deleteFile(path);
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

    String getStringNombres() {
        String nb = "";
        if (listNombreEmp != null && !listNombreEmp.isEmpty()) {
            for (String a : listNombreEmp) {
                nb = nb + "-" + a;
            }
            nb = nb.substring(1);
        }
        return nb;
    }

    String guardarFotos(Integer id) {
        String path = "/";
        for (UploadedFile uf : listFiles) {
            path = Util.saveFile(uf, id, "actividadInfraDiario");
        }
        return path;
    }

    void convertirStringLista() {
        if (actividadInfraDiaria.getNombresEmpleados() != null) {
            listNombreEmp = new ArrayList<>(Arrays.asList(actividadInfraDiaria.getNombresEmpleados().split("-")));
            return;
        }
        listNombreEmp = new ArrayList<>();
    }

    /**
     * Permite obtener un objeto List String que cuenta con las rutas en disco
     * de las fotos registradas para el objeto ActividadInfraDiaria
     *
     * @param nmd Objeto ActividadInfraDiaria
     * @return
     */
    public List<String> getPathFotos(ActividadInfraDiaria nmd) {
        return Util.getFileList(nmd.getIdActividadInfraDiaria(), "actividadInfraDiario");
    }

    /**
     * Permite realizar la busqueda de objeto List ActividadInfraDiaria de
     * acuerdi a los parametros de fechas seleccionados
     */
    public void buscar() {
        if (Util.validarFechaCambioEstado(dInicio, dFin)) {
            MovilidadUtil.addErrorMessage("Fecha inicio no puede ser mayor a fecha fin");
            return;
        }
        listActividadInfraDiaria = actividadInfraEJB.findAllByFechaHora(dInicio, dFin);
    }

    /**
     * Retorna true si el parametro recibido es igual al usuario en sesion
     *
     * @param usern String
     * @return boolean
     */
    public boolean userRegistro(String usern) {
        return user.getUsername().equals(usern);
    }

    public ActividadInfraDiaria getActividadInfraDiaria() {
        return actividadInfraDiaria;
    }

    public void setActividadInfraDiaria(ActividadInfraDiaria actividadInfraDiaria) {
        this.actividadInfraDiaria = actividadInfraDiaria;
    }

    public List<ActividadInfraTipoDet> getListActividadInfraTipoDet() {
        return listActividadInfraTipoDet;
    }

    public void setListActividadInfraTipoDet(List<ActividadInfraTipoDet> listActividadInfraTipoDet) {
        this.listActividadInfraTipoDet = listActividadInfraTipoDet;
    }

    public List<CableEstacion> getListCableEstacion() {
        return listCableEstacion;
    }

    public void setListCableEstacion(List<CableEstacion> listCableEstacion) {
        this.listCableEstacion = listCableEstacion;
    }

    public List<SegPilona> getListSegPilona() {
        return listSegPilona;
    }

    public void setListSegPilona(List<SegPilona> listSegPilona) {
        this.listSegPilona = listSegPilona;
    }

    public List<UploadedFile> getListFiles() {
        return listFiles;
    }

    public void setListFiles(List<UploadedFile> listFiles) {
        this.listFiles = listFiles;
    }

    public Integer getIdActividadInfraTipo() {
        return idActividadInfraTipo;
    }

    public void setIdActividadInfraTipo(Integer idActividadInfraTipo) {
        this.idActividadInfraTipo = idActividadInfraTipo;
    }

    public Integer getIdActividadInfraTipoDet() {
        return idActividadInfraTipoDet;
    }

    public void setIdActividadInfraTipoDet(Integer idActividadInfraTipoDet) {
        this.idActividadInfraTipoDet = idActividadInfraTipoDet;
    }

    public List<ActividadInfraDiaria> getListActividadInfraDiaria() {
        return listActividadInfraDiaria;
    }

    public void setListActividadInfraDiaria(List<ActividadInfraDiaria> listActividadInfraDiaria) {
        this.listActividadInfraDiaria = listActividadInfraDiaria;
    }

    public List<String> getListNombreEmp() {
        return listNombreEmp;
    }

    public void setListNombreEmp(List<String> listNombreEmp) {
        this.listNombreEmp = listNombreEmp;
    }

    public Date getdInicio() {
        return dInicio;
    }

    public void setdInicio(Date dInicio) {
        this.dInicio = dInicio;
    }

    public Date getdFin() {
        return dFin;
    }

    public void setdFin(Date dFin) {
        this.dFin = dFin;
    }

    public List<Empleado> getListEmpleado() {
        return listEmpleado;
    }

    public void setListEmpleado(List<Empleado> listEmpleado) {
        this.listEmpleado = listEmpleado;
    }

    public NovedadInfrastruc getNovedad() {
        return novedad;
    }

    public void setNovedad(NovedadInfrastruc novedad) {
        this.novedad = novedad;
    }

    public List<NovedadTipoInfrastruc> getListNovInfraTipo() {
        return listNovInfraTipo;
    }

    public void setListNovInfraTipo(List<NovedadTipoInfrastruc> listNovInfraTipo) {
        this.listNovInfraTipo = listNovInfraTipo;
    }

    public List<NovedadTipoDetallesInfrastruc> getListNovInfraTipoDet() {
        return listNovInfraTipoDet;
    }

    public void setListNovInfraTipoDet(List<NovedadTipoDetallesInfrastruc> listNovInfraTipoDet) {
        this.listNovInfraTipoDet = listNovInfraTipoDet;
    }

    public List<NovedadInfraArchivo> getListNovedadesInfra() {
        return listNovedadesInfra;
    }

    public void setListNovedadesInfra(List<NovedadInfraArchivo> listNovedadesInfra) {
        this.listNovedadesInfra = listNovedadesInfra;
    }

    public int getI_novedad_infra_tipo() {
        return i_novedad_infra_tipo;
    }

    public void setI_novedad_infra_tipo(int i_novedad_infra_tipo) {
        this.i_novedad_infra_tipo = i_novedad_infra_tipo;
    }

    public int getI_novedad_infra_tipo_det() {
        return i_novedad_infra_tipo_det;
    }

    public void setI_novedad_infra_tipo_det(int i_novedad_infra_tipo_det) {
        this.i_novedad_infra_tipo_det = i_novedad_infra_tipo_det;
    }

    public boolean isFlag_rremove_photo() {
        return flag_rremove_photo;
    }

    public void setFlag_rremove_photo(boolean flag_rremove_photo) {
        this.flag_rremove_photo = flag_rremove_photo;
    }

    public List<String> getListFotos() {
        return listFotos;
    }

    public void setListFotos(List<String> listFotos) {
        this.listFotos = listFotos;
    }

}
