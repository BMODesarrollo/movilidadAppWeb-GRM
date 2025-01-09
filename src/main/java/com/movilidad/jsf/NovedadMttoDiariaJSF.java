/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableCabinaFacadeLocal;
import com.movilidad.ejb.CableEstacionFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.NovedadMttoDiariaFacadeLocal;
import com.movilidad.ejb.NovedadMttoDocsFacadeLocal;
import com.movilidad.ejb.NovedadMttoFacadeLocal;
import com.movilidad.ejb.NovedadMttoTipoActividadDetFacadeLocal;
import com.movilidad.ejb.NovedadMttoTipoFacadeLocal;
import com.movilidad.ejb.ParamAreaFacadeLocal;
import com.movilidad.ejb.SegPilonaFacadeLocal;
import com.movilidad.model.CableCabina;
import com.movilidad.model.CableEstacion;
import com.movilidad.model.Empleado;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.NovedadMtto;
import com.movilidad.model.NovedadMttoDiaria;
import com.movilidad.model.NovedadMttoDocs;
import com.movilidad.model.NovedadMttoTipo;
import com.movilidad.model.NovedadMttoTipoActividad;
import com.movilidad.model.NovedadMttoTipoActividadDet;
import com.movilidad.model.NovedadMttoTipoDet;
import com.movilidad.model.SegPilona;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.NovedadMttoArchivo;
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
 * Permite gestionar la dta del objeto NovedadMttoDiara principal tabla afectada
 * novedad_mtto_diaria
 *
 * @author cesar
 */
@Named(value = "novedadMttoDiariaJSF")
@ViewScoped
public class NovedadMttoDiariaJSF implements Serializable {

    @EJB
    private NovedadMttoDiariaFacadeLocal novedadMttoDiariaEJB;
    @EJB
    private NovedadMttoFacadeLocal novedadMttoEJB;
    @EJB
    private CableEstacionFacadeLocal estacionEJB;
    @EJB
    private SegPilonaFacadeLocal segPilonaEJB;
    @EJB
    private CableCabinaFacadeLocal cabinaEJB;
    @EJB
    private ParamAreaFacadeLocal paramAreaEJB;
    @EJB
    private EmpleadoFacadeLocal empleadoEJB;
    @EJB
    private NovedadMttoTipoActividadDetFacadeLocal tipoActividadDetEJB;
    @EJB
    private NovedadMttoTipoFacadeLocal novMttoTipoEJB;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private NovedadMttoDocsFacadeLocal novedadMttoDocsEjb;
    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;

    @Inject
    private UploadFotoJSFManagedBean fotoJSFManagedBean;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private NovedadMttoDiaria novedadMttoDiaria;
    private List<NovedadMttoDiaria> listNovedadMttoDiaria;
    private List<NovedadMttoTipoActividadDet> listNovedadMttoTipoActividadDet;
    private List<CableEstacion> listCableEstacion;
    private List<SegPilona> listSegPilona;
    private List<CableCabina> listCableCabina;
    private List<Empleado> listEmpleado;
    private List<UploadedFile> listFiles;
    private List<UploadedFile> archivosNovMtto;

    private List<String> listFotos = new ArrayList<>();

    private List<String> listNombreEmp;
    private List<NovedadMttoTipo> listNovMttoTipo;
    private List<NovedadMttoTipoDet> listNovMttoTipoDet;
    private List<NovedadMttoArchivo> listNovedadesMtto;

    private List<String> lista_fotos_remover = new ArrayList<>();

    private Integer idNovedadMttoTipoActividad;
    private Integer idNovedadMttoTipoActividadDet;

    private Date dInicio;
    private boolean flag_rremove_photo;
    private int i_novedad_mtto_tipo;
    private int i_novedad_mtto_tipo_det;
    private NovedadMtto novedad;
    private NovedadMttoTipo novedadMttoTipo;
    private NovedadMttoTipoDet novedadMttoTipoDet;
    private Date dFin = MovilidadUtil.fechaCompletaHoy();
    private String tamanoNovedadDocumento;

    /**
     * Creates a new instance of NovedadMttoDiariaJSF
     */
    public NovedadMttoDiariaJSF() throws ParseException {
        this.dInicio = MovilidadUtil.dateSinHora(new Date());
    }

    @PostConstruct
    public void init() {
        listNovedadMttoTipoActividadDet = null;
        listCableEstacion = null;
        listSegPilona = null;
        listCableCabina = null;
        listEmpleado = null;
        listNombreEmp = null;
        listFiles = null;
        idNovedadMttoTipoActividad = null;
        idNovedadMttoTipoActividadDet = null;
        novedadMttoDiaria = null;
        buscar();
    }

    /**
     * Genera la instancia del objeto NovedadMttoDiaria
     */
    public void prepareGuardar() {
        listNovedadesMtto = new ArrayList<>();
        novedadMttoDiaria = new NovedadMttoDiaria();
        novedadMttoDiaria.setFechaHora(MovilidadUtil.fechaCompletaHoy());
        listFiles = new ArrayList<>();
        listNombreEmp = new ArrayList<>();
    }

    /**
     * Prepara las variables para agregar una nueva novedad a la lista temporal
     * de novedades.
     */
    public void preAgregarNov() {
        i_novedad_mtto_tipo = 0;
        i_novedad_mtto_tipo_det = 0;
        tamanoNovedadDocumento = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_NOVEDAD_MTTO_ARCHIVO_TAMANO);
        listNovMttoTipo = novMttoTipoEJB.findAll();
        archivosNovMtto = new ArrayList<>();
        novedad = new NovedadMtto();
        novedad.setFechaHoraNov(MovilidadUtil.fechaCompletaHoy());
        novedad.setFechaHoraReg(MovilidadUtil.fechaCompletaHoy());
        MovilidadUtil.openModal("nov_mtto_add_wv");
    }

    /**
     * Permite persistir la data del objeto NovedadMttoDiaria
     */
    public void guardar() throws IOException {
        if (novedadMttoDiaria != null) {
            if (novedadMttoDiaria.getIdCabina() == null
                    && novedadMttoDiaria.getIdEstacion() == null
                    && novedadMttoDiaria.getIdPilona() == null) {
                MovilidadUtil.addErrorMessage("Debe seleccionar Cabina, Estación o Pilona");
                return;
            }
            if (idNovedadMttoTipoActividadDet == null) {
                MovilidadUtil.addErrorMessage("Novedad Mantenimineto Tipo Actividad Detalle es requerido");
                return;
            }
            if (idNovedadMttoTipoActividad == null) {
                MovilidadUtil.addErrorMessage("Novedad Mantenimineto Tipo Actividad es requerido");
                return;
            }
            novedadMttoDiaria.setNombresEmpleados(getStringNombres());
            novedadMttoDiaria.setIdNovedadMttoTipoActDet(new NovedadMttoTipoActividadDet(idNovedadMttoTipoActividadDet));
            novedadMttoDiaria.setIdNovedadMttoTipoActividad(new NovedadMttoTipoActividad(idNovedadMttoTipoActividad));
            novedadMttoDiaria.setCreado(new Date());
            novedadMttoDiaria.setModificado(new Date());
            novedadMttoDiaria.setUsername(user.getUsername());
            novedadMttoDiaria.setEstadoReg(0);
            novedadMttoDiariaEJB.create(novedadMttoDiaria);
            novedadMttoDiaria.setPathFotos(guardarFotos(novedadMttoDiaria.getIdNovedadMttoDiaria()));
            novedadMttoDiariaEJB.edit(novedadMttoDiaria);
            if (!listNovedadesMtto.isEmpty()) {
                for (NovedadMttoArchivo nov : listNovedadesMtto) {
                    nov.getNovedadMtto().setIdNovedadMttoDiaria(novedadMttoDiaria);
                    guardarNovedad(nov);
                }
            }
            init();
            prepareGuardar();
            MovilidadUtil.addSuccessMessage("Novedad Mantenimiento creada con éxito");
            buscar();
        }
    }

    public void guardarNovedad(NovedadMttoArchivo param) throws IOException {
        param.getNovedadMtto().setIdSegPilona(param.getNovedadMtto().getIdNovedadMttoDiaria().getIdPilona());
        param.getNovedadMtto().setIdCableCabina(param.getNovedadMtto().getIdNovedadMttoDiaria().getIdCabina());
        param.getNovedadMtto().setIdCableEstacion(param.getNovedadMtto().getIdNovedadMttoDiaria().getIdEstacion());
        param.getNovedadMtto().setCreado(MovilidadUtil.fechaCompletaHoy());
        param.getNovedadMtto().setEstado(1);
        param.getNovedadMtto().setUserReporta(user.getUsername());
        novedadMttoEJB.create(param.getNovedadMtto());

        if (!param.getListasArchivo().isEmpty()) {
            String path = "/";
            for (UploadedFile f : param.getListasArchivo()) {
                path = Util.saveFile(f, param.getNovedadMtto().getIdNovedadMtto(), "novedadMtto");
            }

            if (path.contains("mp4") || path.contains("MP4")) {
                NovedadMttoDocs doc = new NovedadMttoDocs();
                doc.setIdNovedadMtto(param.getNovedadMtto());
                doc.setPath(path);
                doc.setUsername(user.getUsername());
                doc.setCreado(MovilidadUtil.fechaCompletaHoy());
                doc.setEstadoReg(0);
                novedadMttoDocsEjb.create(doc);
            } else {
                param.getNovedadMtto().setPathFotos(path);
                novedadMttoEJB.edit(param.getNovedadMtto());
            }

        }
        if (param.getNovedadMtto().getIdNovedadMttoTipoDet().getNotifica().equals(1)
                && param.getNovedadMtto().getIdNovedadMttoTipoDet().getEmails() != null) {
            notificar(param.getNovedadMtto());
        }
    }

    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate("novedadMttoTemplate");
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    public void delete(String url) {
        lista_fotos_remover.add(url);
        listFotos.remove(url);
    }

    private void notificar(NovedadMtto novedad) throws IOException {
        List<String> adjuntos;
        adjuntos = MovilidadUtil.getListasFotos(novedad.getIdNovedadMtto(),
                "novedadMtto", novedad.getPathFotos());
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("fecha", Util.dateFormat(novedad.getFechaHoraNov()));
        mailProperties.put("pilona", novedad.getIdSegPilona() != null
                ? novedad.getIdSegPilona().getNombre() : "N/A");
        mailProperties.put("cabina", novedad.getIdCableCabina() != null
                ? novedad.getIdCableCabina().getNombre() : "");
        mailProperties.put("estacion", novedad.getIdCableEstacion() != null
                ? novedad.getIdCableEstacion().getNombre() : "");
        mailProperties.put("tipo", novedad.getIdNovedadMttoTipo().getNombre());
        mailProperties.put("detalle", novedad.getIdNovedadMttoTipoDet().getNombre());
        mailProperties.put("username", novedad.getUsername());
        mailProperties.put("generada", Util.dateTimeFormat(novedad.getCreado()));
        mailProperties.put("descripcion", novedad.getDescripcion());
        String subject = "Novedad Componente";
        String destinatarios = novedad.getIdNovedadMttoTipoDet().getEmails();

        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", adjuntos != null ? adjuntos : null);
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
            novedadMttoTipo = null;
            for (NovedadMttoTipo gt : listNovMttoTipo) {
                if (gt.getIdNovedadMttoTipo().equals(i_novedad_mtto_tipo)) {
                    novedadMttoTipo = gt;
                    listNovMttoTipoDet = gt.getNovedadMttoTipoDetList();
                    break;
                }
            }
        } else {
            novedadMttoTipoDet = null;
            for (NovedadMttoTipoDet gtd : listNovMttoTipoDet) {
                if (gtd.getIdNovedadMttoTipoDet().equals(i_novedad_mtto_tipo_det)) {
                    novedadMttoTipoDet = gtd;
                    break;
                }
            }
        }
    }

    /**
     * Agrega archivos para novedades al array de tipo UploadedFile qeu luego
     * van a ser persistidos.
     *
     * @param event
     */
    public void handleFileUploadFotosNovedad(FileUploadEvent event) {
        archivosNovMtto.add(event.getFile());
        MovilidadUtil.addSuccessMessage("Archivo(s) Cargado(s)");
    }

    /**
     * Permite capturar el objeto NovedadMttoDiaria seleccionado por el usuario
     *
     * @param event Objeto NovedadMttoDiaria
     */
    public void onNovedadMttoDiaria(NovedadMttoDiaria event) throws IOException {

        novedadMttoDiaria = event;
        listNovedadesMtto = new ArrayList<>();
        idNovedadMttoTipoActividad = event.getIdNovedadMttoTipoActividad().getIdNovedadMttoTipoActividad();
        idNovedadMttoTipoActividadDet = event.getIdNovedadMttoTipoActDet().getIdNovedadMttoTipoActividadDet();
        cargarListaNovedadMttoTipoActividadDet();
        convertirStringLista();
        flag_rremove_photo = esAlterable();
        listFotos = MovilidadUtil.getListasFotos(novedadMttoDiaria.getIdNovedadMttoDiaria(),
                "novedadMttoDiario", novedadMttoDiaria.getPathFotos());
        listFiles = new ArrayList<>();
        List<NovedadMtto> list = novedadMttoDiaria.getNovedadMttoList();
        if (list != null) {
            for (NovedadMtto s : list) {
                NovedadMttoArchivo obj = new NovedadMttoArchivo();
                obj.setNovedadMtto(s);
                listNovedadesMtto.add(obj);
            }
        }
    }

    public boolean esAlterable() {
        if (novedadMttoDiaria == null) {
            return false;
        }
        for (GrantedAuthority i : user.getAuthorities()) {
            if (i.getAuthority().equals("PROFGEN")) {
                return true;
            }
        }
        if (novedadMttoDiaria.getUsername().equals(user.getUsername())) {
            return true;
        }
        return false;
    }

    /**
     * Permite actualizar la data del objeto NovedadMttoDiaria
     */
    public void actualizar() throws IOException {
        if (novedadMttoDiaria != null) {
            if (novedadMttoDiaria.getIdCabina() == null
                    && novedadMttoDiaria.getIdEstacion() == null
                    && novedadMttoDiaria.getIdPilona() == null) {
                MovilidadUtil.addErrorMessage("Debe seleccionar Cabina, Estación o Pilona");
                return;
            }
            if (idNovedadMttoTipoActividadDet == null) {
                MovilidadUtil.addErrorMessage("Novedad Mantenimineto Tipo Actividad Detalle es requerido");
                return;
            }
            if (idNovedadMttoTipoActividad == null) {
                MovilidadUtil.addErrorMessage("Novedad Mantenimineto Tipo Actividad es requerido");
                return;
            }
            novedadMttoDiaria.setNombresEmpleados(getStringNombres());
            novedadMttoDiaria.setIdNovedadMttoTipoActDet(new NovedadMttoTipoActividadDet(idNovedadMttoTipoActividadDet));
            novedadMttoDiaria.setIdNovedadMttoTipoActividad(new NovedadMttoTipoActividad(idNovedadMttoTipoActividad));
            novedadMttoDiaria.setModificado(new Date());
            if (!listFiles.isEmpty()) {
                novedadMttoDiaria.setPathFotos(guardarFotos(novedadMttoDiaria.getIdNovedadMttoDiaria()));
            }
            for (String url : lista_fotos_remover) {
                MovilidadUtil.eliminarFichero(url);
            }
            novedadMttoDiariaEJB.edit(novedadMttoDiaria);

            if (!listNovedadesMtto.isEmpty()) {
                for (NovedadMttoArchivo nov : listNovedadesMtto) {
                    if (nov.getNovedadMtto().getIdNovedadMtto() == null) {
                        nov.getNovedadMtto().setIdNovedadMttoDiaria(novedadMttoDiaria);
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
        CableEstacion est = (CableEstacion) event.getObject();
        novedadMttoDiaria.setIdEstacion(est);
        MovilidadUtil.addSuccessMessage("Estación seleccionada con éxito");
        MovilidadUtil.hideModal("estacionDlg");
    }

    /**
     * Permite capturar el objeto SegPilona seleccionado por el usuario
     *
     * @param event Objeto SegPilona
     */
    public void onGetPilona(SelectEvent event) {
        SegPilona pilona = (SegPilona) event.getObject();
        novedadMttoDiaria.setIdPilona(pilona);
        MovilidadUtil.addSuccessMessage("Pilona seleccionada con éxito");
        MovilidadUtil.hideModal("pilonaDlg");
    }

    /**
     * Permite capturar el objeto CableCabina seleccionado por el usuario
     *
     * @param event Objeto CableCabina
     */
    public void onGetCabina(SelectEvent event) {
        CableCabina cabina = (CableCabina) event.getObject();
        novedadMttoDiaria.setIdCabina(cabina);
        MovilidadUtil.addSuccessMessage("Cabina seleccionada con éxito");
        MovilidadUtil.hideModal("cabinaDlg");
    }

    /**
     * Permite hacer null novedadMttoDiaria.setIdEstacion(null);
     */
    public void onNullEstacion() {
        novedadMttoDiaria.setIdEstacion(null);
        MovilidadUtil.addSuccessMessage("Estación null");
        MovilidadUtil.hideModal("estacionDlg");
    }

    /**
     * Permite hacer null novedadMttoDiaria.setIdPilona(null);
     */
    public void onNullPilona() {
        novedadMttoDiaria.setIdPilona(null);
        MovilidadUtil.addSuccessMessage("Pilona null");
        MovilidadUtil.hideModal("pilonaDlg");
    }

    /**
     * Permite hacer null novedadMttoDiaria.setIdCabina(null);
     */
    public void onNullCabina() {
        novedadMttoDiaria.setIdCabina(null);
        MovilidadUtil.addSuccessMessage("Cabina null");
        MovilidadUtil.hideModal("cabinaDlg");
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
     * Permite consultar los registros de CableCabina
     */
    public void cargarListaCabina() {
        listCableCabina = cabinaEJB.findAllByEstadoReg();
    }

    /**
     * Permite consultar los registros de Empleado
     */
    public void cargarListaEmpleado() {
        listEmpleado = empleadoEJB.findAllEmpleadosByCargos(MovilidadUtil.getProperty("idCargosEmpleadoNov"));
    }

    /**
     * Permite consultar los registros de NovedadMttoTipoActividadDet
     */
    public void cargarListaNovedadMttoTipoActividadDet() {
        listNovedadMttoTipoActividadDet = tipoActividadDetEJB.findAllEstadoRegByIdNovMttoTpAct(idNovedadMttoTipoActividad);
    }

    /**
     * Evento que permite capturar el archivo subido por el usuario
     *
     * @param event Objeto FileUploadEvent
     */
    public void handleFileUpload(FileUploadEvent event) {
        listFiles.add(event.getFile());
        MovilidadUtil.addSuccessMessage("Foto(s) Cargada(s)");
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

    public void obtenerFotos(NovedadMttoDiaria param) throws IOException {
        if (param.getPathFotos() == null
                || (param.getPathFotos() != null
                && param.getPathFotos().equals("/"))) {
            MovilidadUtil.addAdvertenciaMessage("No hay fotos para visualizar.");
            return;
        }
        listFotos = MovilidadUtil.getListasFotos(param.getIdNovedadMttoDiaria(), "novedadMttoDiario", param.getPathFotos());
        fotoJSFManagedBean.setListFotos(listFotos);
        MovilidadUtil.openModal("galeria_foto_dialog_wv");
    }

    public void obtenerFotosNovedad(NovedadMtto param) throws IOException {
        if (param.getPathFotos() == null
                || (param.getPathFotos() != null
                && param.getPathFotos().equals("/"))) {
            MovilidadUtil.addAdvertenciaMessage("No hay fotos para visualizar ó videos a descargar.");
            return;
        }
        listFotos = MovilidadUtil.getListasFotos(param.getIdNovedadMtto(), "novedadMtto", param.getPathFotos());
        MovilidadUtil.openModal("galeria_foto_dialog_wv");
    }

    /**
     * Agrega las novedad en la lista temporal que luego van a ser persistidos.
     *
     * @throws ParseException
     */
    public void agregarNovedad() throws ParseException {
        if (novedadMttoTipo == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar el tipo de novedad");
            return;
        }
        if (novedadMttoTipoDet == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar el tipo de novedad detalles");
            return;
        }
        if (MovilidadUtil.fechasIgualMenorMayor(novedad.getFechaHoraReg(), MovilidadUtil.fechaHoy(), false) == 2) {
            MovilidadUtil.addErrorMessage("La fecha no debe ser posterior a la fecha actual.");
            return;
        }
        NovedadMttoArchivo novedadMttoArchivo = new NovedadMttoArchivo();
        novedad.setIdNovedadMttoTipo(novedadMttoTipo);
        novedad.setIdNovedadMttoTipoDet(novedadMttoTipoDet);
        novedad.setUsername(user.getUsername());
        novedadMttoArchivo.setNovedadMtto(novedad);
        novedadMttoArchivo.setListasArchivo(archivosNovMtto);
        listNovedadesMtto.add(novedadMttoArchivo);
        i_novedad_mtto_tipo = 0;
        i_novedad_mtto_tipo_det = 0;
        novedadMttoTipo = null;
        novedadMttoTipoDet = null;
        MovilidadUtil.hideModal("nov_mtto_add_wv");
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
            path = Util.saveFile(uf, id, "novedadMttoDiario");
        }
        return path;
    }

    void convertirStringLista() {
        if (novedadMttoDiaria.getNombresEmpleados() != null) {
            listNombreEmp = new ArrayList<>(Arrays.asList(novedadMttoDiaria.getNombresEmpleados().split("-")));
            return;
        }
        listNombreEmp = new ArrayList<>();
    }

    /**
     * Permite obtener un objeto List String que cuenta con las rutas en disco
     * de las fotos registradas para el objeto NovedadMttoDiaria
     *
     * @param nmd Objeto NovedadMttoDiaria
     * @return
     */
    public List<String> getPathFotos(NovedadMttoDiaria nmd) {
        return Util.getFileList(nmd.getIdNovedadMttoDiaria(), "novedadMttoDiario");
    }

    /**
     * Permite realizar la busqueda de objeto List NovedadMttoDiaria de acuerdi
     * a los parametros de fechas seleccionados
     */
    public void buscar() {
        if (Util.validarFechaCambioEstado(dInicio, dFin)) {
            MovilidadUtil.addErrorMessage("Fecha inicio no puede ser mayor a fecha fin");
            return;
        }
        listNovedadMttoDiaria = novedadMttoDiariaEJB.findAllByFechaHora(dInicio, dFin);
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

    /**
     * Elimnar una novedad de la lista temporal de novedades.
     *
     * @param nov
     */
    public void eliminarNovedad(NovedadMttoArchivo nov) {
        listNovedadesMtto.remove(nov);
        MovilidadUtil.addSuccessMessage("Novedad eliminada");
    }

    public NovedadMttoDiaria getNovedadMttoDiaria() {
        return novedadMttoDiaria;
    }

    public void setNovedadMttoDiaria(NovedadMttoDiaria novedadMttoDiaria) {
        this.novedadMttoDiaria = novedadMttoDiaria;
    }

    public List<NovedadMttoTipoActividadDet> getListNovedadMttoTipoActividadDet() {
        return listNovedadMttoTipoActividadDet;
    }

    public void setListNovedadMttoTipoActividadDet(List<NovedadMttoTipoActividadDet> listNovedadMttoTipoActividadDet) {
        this.listNovedadMttoTipoActividadDet = listNovedadMttoTipoActividadDet;
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

    public List<CableCabina> getListCableCabina() {
        return listCableCabina;
    }

    public void setListCableCabina(List<CableCabina> listCableCabina) {
        this.listCableCabina = listCableCabina;
    }

    public List<UploadedFile> getListFiles() {
        return listFiles;
    }

    public void setListFiles(List<UploadedFile> listFiles) {
        this.listFiles = listFiles;
    }

    public Integer getIdNovedadMttoTipoActividad() {
        return idNovedadMttoTipoActividad;
    }

    public void setIdNovedadMttoTipoActividad(Integer idNovedadMttoTipoActividad) {
        this.idNovedadMttoTipoActividad = idNovedadMttoTipoActividad;
    }

    public Integer getIdNovedadMttoTipoActividadDet() {
        return idNovedadMttoTipoActividadDet;
    }

    public void setIdNovedadMttoTipoActividadDet(Integer idNovedadMttoTipoActividadDet) {
        this.idNovedadMttoTipoActividadDet = idNovedadMttoTipoActividadDet;
    }

    public List<NovedadMttoDiaria> getListNovedadMttoDiaria() {
        return listNovedadMttoDiaria;
    }

    public void setListNovedadMttoDiaria(List<NovedadMttoDiaria> listNovedadMttoDiaria) {
        this.listNovedadMttoDiaria = listNovedadMttoDiaria;
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

    public List<NovedadMttoTipo> getListNovMttoTipo() {
        return listNovMttoTipo;
    }

    public void setListNovMttoTipo(List<NovedadMttoTipo> listNovMttoTipo) {
        this.listNovMttoTipo = listNovMttoTipo;
    }

    public List<NovedadMttoTipoDet> getListNovMttoTipoDet() {
        return listNovMttoTipoDet;
    }

    public void setListNovMttoTipoDet(List<NovedadMttoTipoDet> listNovMttoTipoDet) {
        this.listNovMttoTipoDet = listNovMttoTipoDet;
    }

    public List<NovedadMttoArchivo> getListNovedadesMtto() {
        return listNovedadesMtto;
    }

    public void setListNovedadesMtto(List<NovedadMttoArchivo> listNovedadesMtto) {
        this.listNovedadesMtto = listNovedadesMtto;
    }

    public int getI_novedad_mtto_tipo() {
        return i_novedad_mtto_tipo;
    }

    public void setI_novedad_mtto_tipo(int i_novedad_mtto_tipo) {
        this.i_novedad_mtto_tipo = i_novedad_mtto_tipo;
    }

    public int getI_novedad_mtto_tipo_det() {
        return i_novedad_mtto_tipo_det;
    }

    public void setI_novedad_mtto_tipo_det(int i_novedad_mtto_tipo_det) {
        this.i_novedad_mtto_tipo_det = i_novedad_mtto_tipo_det;
    }

    public NovedadMtto getNovedad() {
        return novedad;
    }

    public void setNovedad(NovedadMtto novedad) {
        this.novedad = novedad;
    }

    public List<String> getListFotos() {
        return listFotos;
    }

    public void setListFotos(List<String> listFotos) {
        this.listFotos = listFotos;
    }

    public boolean isFlag_rremove_photo() {
        return flag_rremove_photo;
    }

    public void setFlag_rremove_photo(boolean flag_rremove_photo) {
        this.flag_rremove_photo = flag_rremove_photo;
    }

    public String getTamanoNovedadDocumento() {
        return tamanoNovedadDocumento;
    }

    public void setTamanoNovedadDocumento(String tamanoNovedadDocumento) {
        this.tamanoNovedadDocumento = tamanoNovedadDocumento;
    }

}
