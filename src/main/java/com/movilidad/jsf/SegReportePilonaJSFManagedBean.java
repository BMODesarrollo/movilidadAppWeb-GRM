/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.NovedadInfrastrucFacadeLocal;
import com.movilidad.ejb.NovedadTipoInfrastrucFacadeLocal;
import com.movilidad.ejb.SegPilonaFacadeLocal;
import com.movilidad.ejb.SegReportePilonaFacadeLocal;
import com.movilidad.ejb.SegReportePilonaNovedadFacadeLocal;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.NovedadInfrastruc;
import com.movilidad.model.NovedadTipoDetallesInfrastruc;
import com.movilidad.model.NovedadTipoInfrastruc;
import com.movilidad.model.SegPilona;
import com.movilidad.model.SegReportePilona;
import com.movilidad.model.SegReportePilonaNovedad;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.SegReportePilonaNovedadArchivo;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
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
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "segReportePilonaJSFMB")
@ViewScoped
public class SegReportePilonaJSFManagedBean implements Serializable {

    /**
     * Creates a new instance of SegReportePilonaJSFManagedBean
     */
    public SegReportePilonaJSFManagedBean() {
    }
    @EJB
    private NovedadInfrastrucFacadeLocal novInfrastrucEJB;
    @EJB
    private NovedadTipoInfrastrucFacadeLocal novTipoInfrastrucEJB;
    @EJB
    private SegPilonaFacadeLocal segPilonaEJB;
    @EJB
    private SegReportePilonaFacadeLocal segReportePilonaJEJB;
    @EJB
    private SegReportePilonaNovedadFacadeLocal SegReportePilonaNovJEJB;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @Inject
    private UploadFotoJSFManagedBean fotoJSFManagedBean;
    private SegReportePilona segReportePilona;
    private NovedadInfrastruc novedad;
    private NovedadTipoInfrastruc novTipoInfrastruc;
    private NovedadTipoDetallesInfrastruc novTipoDetInfrastruc;
    private SegReportePilonaNovedad segReportePilonaNovedad;
    private SegPilona pilona;

    private List<SegReportePilona> list;
    private List<SegReportePilonaNovedadArchivo> listNov;
    private List<SegPilona> listPilonas;
    private List<NovedadTipoInfrastruc> listNovTipoInfrastruc;
    private List<NovedadTipoDetallesInfrastruc> listNovTipoDetInfrastruc;
    private List<UploadedFile> archivosNov;
    private List<UploadedFile> archivos;
    private List<UploadedFile> archivosLibro;
    private List<String> listFotos = new ArrayList<>();
    private List<String> listFotosActividad = new ArrayList<>();
    private List<String> listFotosMinuta = new ArrayList<>();
    private List<String> lista_fotos_remover = new ArrayList<>();

    private Date desde = MovilidadUtil.fechaHoy();
    private Date hasta = MovilidadUtil.fechaHoy();

    private int i_idSegPilona = 0;
    private int i_idNovedadTipoInfraStruct = 0;
    private int i_idNovedaTipoDetallesInfraStruct = 0;
    private boolean flag_rremove_photo = false;

    UserExtended user;

    @PostConstruct
    public void init() {
        consultar();
        getUser();
    }

    /**
     * Obtener valor de usuario en sesión.
     */
    public void getUser() {
        try {
            user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            user = null;
        }
    }

    /**
     * Prepara las variables para un nuevo registro de reporte pilona.
     */
    public void nuevo() {
        flag_rremove_photo = false;
        i_idSegPilona = 0;
        segReportePilona = new SegReportePilona();
        segReportePilona.setFechaHora(MovilidadUtil.fechaCompletaHoy());
        listPilonas = segPilonaEJB.findByEstadoReg();
        archivos = new ArrayList<>();
        archivosLibro = new ArrayList<>();
        listNov = new ArrayList<>();
        MovilidadUtil.openModal("create_reporte_pilona_wv");
    }

    /**
     * Elimina la url de la lista y la agrega en una lista temporal.
     *
     * @param url
     */
    public void delete(String url) {
        lista_fotos_remover.add(url);
        listFotosActividad.remove(url);
        listFotosMinuta.remove(url);
    }

    /**
     * Obtener lista SegReportePilonaNovedad con cada una de las novedades de
     * una reporte pilona.
     *
     * @param obj
     * @return retorna lista de tipo SegReportePilonaNovedad.
     */
    public List<SegReportePilonaNovedad> obtenerNovedades(SegReportePilona obj) {
        if (obj.getSegReportePilonaNovedadList() != null) {
            return obj.getSegReportePilonaNovedadList();
        }
        return new ArrayList<>();
    }

    /**
     * Prepara las variables para el proceso de edición.
     *
     * @throws IOException
     */
    public void editar() throws IOException {
        flag_rremove_photo = user.getUsername().equals(segReportePilona.getUsername());
        listPilonas = segPilonaEJB.findByEstadoReg();
        i_idSegPilona = segReportePilona.getIdSegPilona().getIdSegPilona();
        selectPilona();
        archivos = new ArrayList<>();
        archivosLibro = new ArrayList<>();
        listNov = new ArrayList<>();
        listFotosActividad = obtenerFotosReturn(1);
        listFotosMinuta = obtenerFotosReturn(2);
    }

    /**
     * Prepara las variables para el proceso de edicion de novedad.
     *
     * @throws IOException
     */
    public void editarNovedad() throws IOException {
        flag_rremove_photo = user.getUsername().equals(novedad.getUsername());
        listNovTipoInfrastruc = novTipoInfrastrucEJB.findAllByEstadoReg();
        i_idNovedadTipoInfraStruct = novedad.getNovedadTipoInfrastruc().getIdNovedadTipoInfrastruc();
        i_idNovedaTipoDetallesInfraStruct = novedad.getNovedadTipoDetallesInfrastruc().getIdNovedadTipoDetInfrastruc();
        prepareTipoDetalles(true);
        prepareTipoDetalles(false);
        archivosNov = new ArrayList<>();
        lista_fotos_remover = new ArrayList<>();
        listFotosActividad = obtenerFotosReturn(3);
    }

    /*
     * Parámetros para el envío de correos (Novedades PM)
     */
    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.find(Util.ID_NOVEDAD_INFRAESTRUCTURA);
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
     * Notifica via correo la novedad registrada por base de datos.
     *
     * @param novedad
     * @throws IOException
     */
    private void notificar(NovedadInfrastruc novedad) throws IOException {
        List<String> adjuntos;
        this.novedad = novedad;
        adjuntos = obtenerFotosReturn(3);
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("fecha", Util.dateFormat(novedad.getFechaHoraNov()));
        mailProperties.put("pilona", novedad.getIdSegPilona().getNombre());
        mailProperties.put("area", "N/A");
        mailProperties.put("tipo", novedad.getNovedadTipoInfrastruc().getNombre());
        mailProperties.put("detalle", novedad.getNovedadTipoDetallesInfrastruc().getNombre());
        mailProperties.put("username", novedad.getUsername());
        mailProperties.put("generada", Util.dateTimeFormat(novedad.getCreado()));
        mailProperties.put("descripcion", novedad.getDescripcion());
        String subject = "Novedad Revisión Pilona: " + novedad.getIdSegPilona().getNombre();
        String destinatarios = novedad.getNovedadTipoDetallesInfrastruc().getEmails();

        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", adjuntos != null ? adjuntos : null);
    }

    /**
     * Responsable de persistir en base de datos la informacion corespondiente a
     * una novedad de infraestructura.
     */
    public void guardarNovedad() {
        novedad.setModificado(MovilidadUtil.fechaCompletaHoy());
        novedad.setUsername(user.getUsername());
        novedad.setNovedadTipoInfrastruc(novTipoInfrastruc);
        novedad.setNovedadTipoDetallesInfrastruc(novTipoDetInfrastruc);
        for (String url : lista_fotos_remover) {
            MovilidadUtil.eliminarFichero(url);
        }
        novInfrastrucEJB.edit(novedad);
        if (!archivosNov.isEmpty()) {
            String path = "/";
            for (UploadedFile f : archivosNov) {
                path = Util.saveFile(f, novedad.getIdNovedadInfrastruc(), "novedadInfrastruct");
            }
            novedad.setPathFotos(path);
            novInfrastrucEJB.edit(novedad);
        }
        archivosNov.clear();
        MovilidadUtil.addSuccessMessage("Se registro la informacion exitosamente.");
        MovilidadUtil.hideModal("nov_edit_reporte_pilona_wv");
        consultar();
    }

    /**
     * Consulotar en base de datos repostes de pilona por rango de fechas.
     */
    public void consultar() {
        list = segReportePilonaJEJB.findRanfoFechaEstadoReg(desde, hasta);
    }

    /**
     * Validar que las variables en cuestion esten listas para persistir.
     *
     * @return True si sale algo mal, False si todo esta bien.
     * @throws ParseException
     */
    public boolean validar() throws ParseException {
        if (i_idSegPilona == 0) {
            MovilidadUtil.addErrorMessage("Se debe seleecionar una pilona.");
            return true;
        }
        if (MovilidadUtil.fechasIgualMenorMayor(segReportePilona.getFechaHora(), MovilidadUtil.fechaCompletaHoy(), true) == 2) {
            MovilidadUtil.addErrorMessage("La fecha no deve ser posterior a la del día actual");
            return true;
        }
        return false;
    }

    /**
     * invocar al metodo obtenerFotosReturn;
     *
     * @param opc
     * @throws IOException
     */
    public void obtenerFotos(int opc) throws IOException {
        fotoJSFManagedBean.setListFotos(obtenerFotosReturn(opc));
    }

    /**
     * Obtener lista de string con todas las rutas de imagenes registradas para
     * reporte de pilona.
     *
     * @param opc
     * @return
     * @throws IOException
     */
    public List<String> obtenerFotosReturn(int opc) throws IOException {
        this.listFotos = new ArrayList<>();
        List<String> lstNombresImg;
        String path;
        switch (opc) {
            case 1:
                lstNombresImg = Util.getFileList(segReportePilona.getIdSegReportePilona(), "segReportePilona");
                path = segReportePilona.getPathFotos();
                break;
            case 2:
                lstNombresImg = Util.getFileList(segReportePilona.getIdSegReportePilona(), "segReportePilonaMinuta");
                path = segReportePilona.getPathFotoMinuta();
                break;
            default:
                lstNombresImg = Util.getFileList(novedad.getIdNovedadInfrastruc(), "novedadInfrastruct");
                path = novedad.getPathFotos();
                break;
        }
        for (String f : lstNombresImg) {
            f = path + f;
            listFotos.add(f);
        }
        return listFotos;

    }

    /**
     * Cargar objeto de SegPilona en la variable pilona.
     */
    public void selectPilona() {
        pilona = null;
        for (SegPilona obj : listPilonas) {
            if (obj.getIdSegPilona().equals(i_idSegPilona)) {
                pilona = obj;
                break;
            }
        }
    }

    /**
     * Metodo encargado de persistir en base de datos un nuevo registro de
     * reporte pilona.
     *
     * @throws ParseException
     * @throws java.io.IOException
     */
    public void guardar() throws ParseException, IOException {
        if (validar()) {
            return;
        }
        segReportePilona.setUsername(user.getUsername());
        segReportePilona.setIdSegPilona(pilona);
        if (segReportePilona.getIdSegReportePilona() == null) {
            segReportePilona.setEstadoReg(0);
            segReportePilona.setCreado(MovilidadUtil.fechaCompletaHoy());
            segReportePilonaJEJB.create(segReportePilona);
        } else {
            for (String url : lista_fotos_remover) {
                MovilidadUtil.eliminarFichero(url);
            }
            segReportePilona.setModificado(MovilidadUtil.fechaCompletaHoy());
            segReportePilonaJEJB.edit(segReportePilona);
        }
        /**
         * Validar si hay imagenes para guardar.
         */
        if (!archivos.isEmpty() || !archivosLibro.isEmpty()) {
            String pathLibro = segReportePilona.getPathFotoMinuta() == null ? "/" : segReportePilona.getPathFotoMinuta();
            String path = segReportePilona.getPathFotos() == null ? "/" : segReportePilona.getPathFotos();
            for (UploadedFile f : archivos) {
                path = Util.saveFile(f, segReportePilona.getIdSegReportePilona(), "segReportePilona");
            }
            for (UploadedFile f : archivosLibro) {
                pathLibro = Util.saveFile(f, segReportePilona.getIdSegReportePilona(), "segReportePilonaMinuta");
            }
            segReportePilona.setPathFotos(path);
            segReportePilona.setPathFotoMinuta(pathLibro);
            this.segReportePilonaJEJB.edit(segReportePilona);
            archivos.clear();
        }
        /**
         * Validar si hay novedades para persistir.
         */
        if (!listNov.isEmpty()) {
            for (SegReportePilonaNovedadArchivo obj : listNov) {
                SegReportePilonaNovedad novReporte = obj.getSegReportePilonaNovedad();
                novReporte.setCreado(MovilidadUtil.fechaCompletaHoy());
                novReporte.setEstadoReg(0);
                novReporte.setPathFoto("/");
                novReporte.setSegReportePilona(segReportePilona);
                novReporte.setUsername(user.getUsername());
                novReporte.getNovedadInfrastruc().setCreado(MovilidadUtil.fechaCompletaHoy());
                novReporte.getNovedadInfrastruc().setUsername(user.getUsername());
                novReporte.getNovedadInfrastruc().setEstadoReg(0);
                novReporte.getNovedadInfrastruc().setIdSegPilona(segReportePilona.getIdSegPilona());
                SegReportePilonaNovJEJB.create(novReporte);
                /**
                 * Validar si hay imagenes para guardar.
                 */
                if (!obj.getArchivos().isEmpty()) {
                    String path = "/";
                    for (UploadedFile f : obj.getArchivos()) {
                        path = Util.saveFile(f, novReporte.getNovedadInfrastruc().getIdNovedadInfrastruc(), "novedadInfrastruct");
                    }
                    novReporte.getNovedadInfrastruc().setPathFotos(path);
                    novInfrastrucEJB.edit(novReporte.getNovedadInfrastruc());
                }
                /**
                 * Validar si el tipo de detalle de novedad notifica.
                 */
                if (novReporte.getNovedadInfrastruc().getNovedadTipoDetallesInfrastruc().getNotifica().equals(1)
                        && novReporte.getNovedadInfrastruc().getNovedadTipoDetallesInfrastruc().getEmails() != null) {
                    notificar(novReporte.getNovedadInfrastruc());
                }
            }
        }
        listNov.clear();
        MovilidadUtil.addSuccessMessage("Se registro la informacion exitosamente.");
        MovilidadUtil.hideModal("create_reporte_pilona_wv");
        listNovTipoInfrastruc = null;
        listNovTipoDetInfrastruc = null;
        consultar();
        segReportePilona = null;
    }

    /**
     * Elimnar una novedad de la lista temporal de novedades.
     *
     * @param aseoCA
     */
    public void eliminarNovedad(SegReportePilonaNovedadArchivo novedadArchivo) {
        listNov.remove(novedadArchivo);
        MovilidadUtil.addSuccessMessage("Novedad eliminada");
    }

    /**
     * Prepara las variables para agregar una nueva novedad a la lista temporal
     * de novedades.
     */
    public void preAgregarNov() {
        i_idNovedaTipoDetallesInfraStruct = 0;
        i_idNovedadTipoInfraStruct = 0;
        listNovTipoInfrastruc = novTipoInfrastrucEJB.findAllByEstadoReg();
        listNovTipoDetInfrastruc = null;
        novedad = new NovedadInfrastruc();
        archivosNov = new ArrayList<>();
        novedad.setFechaHoraNov(MovilidadUtil.fechaCompletaHoy());
        novedad.setFechaHoraReg(MovilidadUtil.fechaCompletaHoy());
        MovilidadUtil.openModal("nov_reporte_pilona_wv");
    }

    /**
     * Agrega archivos para novedades al array de tipo UploadedFile qeu luego
     * van a ser persistidos.
     *
     * @param event
     */
    public void handleFileUploadFotosNovedad(FileUploadEvent event) {
        archivosNov.add(event.getFile());
        MovilidadUtil.addSuccessMessage("Foto(s) para novedad agregada(s).");
    }

    /**
     * Agrega archivos para reporte de pilona al array de tipo UploadedFile qeu
     * luego van a ser persistidos.
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        archivos.add(event.getFile());
        MovilidadUtil.addSuccessMessage("Foto(s) para reporte agregada(s).");
    }

    /**
     * Agrega archivos para reporte pilona del libro al array de tipo
     * UploadedFile qeu luego van a ser persistidos.
     *
     * @param event
     */
    public void handleFileUploadLibro(FileUploadEvent event) {
        archivosLibro.add(event.getFile());
        MovilidadUtil.addSuccessMessage("Foto(s) para minuta agregada(s).");
    }

    /**
     * Agrega las novedad en la lista temporal que luego van a ser persistidos.
     *
     * @throws ParseException
     */
    public void agregarNovedad() throws ParseException {
        if (novTipoInfrastruc == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar el tipo de novedad");
            return;
        }
        if (novTipoDetInfrastruc == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar el tipo de novedad detalles");
            return;
        }
        if (MovilidadUtil.fechasIgualMenorMayor(novedad.getFechaHoraReg(), MovilidadUtil.fechaHoy(), false) == 2) {
            MovilidadUtil.addErrorMessage("La fecha no debe ser posterior a la fecha actual.");
            return;
        }
        SegReportePilonaNovedad reporteNov = new SegReportePilonaNovedad();
        SegReportePilonaNovedadArchivo reporteNova = new SegReportePilonaNovedadArchivo();
        novedad.setNovedadTipoInfrastruc(novTipoInfrastruc);
        novedad.setNovedadTipoDetallesInfrastruc(novTipoDetInfrastruc);
        reporteNov.setNovedadInfrastruc(novedad);
        reporteNov.setUsername(user.getUsername());
        reporteNova.setSegReportePilonaNovedad(reporteNov);
        reporteNova.setArchivos(archivosNov);
        listNov.add(reporteNova);
        i_idNovedadTipoInfraStruct = 0;
        i_idNovedaTipoDetallesInfraStruct = 0;
        novTipoInfrastruc = null;
        novTipoDetInfrastruc = null;
        listNovTipoDetInfrastruc = null;
        MovilidadUtil.hideModal("nov_reporte_pilona_wv");
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
            novTipoInfrastruc = null;
            for (NovedadTipoInfrastruc gt : listNovTipoInfrastruc) {
                if (gt.getIdNovedadTipoInfrastruc().equals(i_idNovedadTipoInfraStruct)) {
                    novTipoInfrastruc = gt;
                    listNovTipoDetInfrastruc = gt.getNovedadTipoDetallesInfrastrucList();
                    break;
                }
            }
        } else {
            novTipoDetInfrastruc = null;
            for (NovedadTipoDetallesInfrastruc gtd : listNovTipoDetInfrastruc) {
                if (gtd.getIdNovedadTipoDetInfrastruc().equals(i_idNovedaTipoDetallesInfraStruct)) {
                    novTipoDetInfrastruc = gtd;
                    break;
                }
            }
        }
    }

    public void eliminarNovedad(SegReportePilonaNovedad obj) {
    }

    public SegReportePilona getSegReportePilona() {
        return segReportePilona;
    }

    public void setSegReportePilona(SegReportePilona segReportePilona) {
        this.segReportePilona = segReportePilona;
    }

    public List<SegReportePilona> getList() {
        return list;
    }

    public void setList(List<SegReportePilona> list) {
        this.list = list;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public List<SegPilona> getListPilonas() {
        return listPilonas;
    }

    public void setListPilonas(List<SegPilona> listPilonas) {
        this.listPilonas = listPilonas;
    }

    public int getI_idSegPilona() {
        return i_idSegPilona;
    }

    public void setI_idSegPilona(int i_idSegPilona) {
        this.i_idSegPilona = i_idSegPilona;
    }

    public List<SegReportePilonaNovedadArchivo> getListNov() {
        return listNov;
    }

    public void setListNov(List<SegReportePilonaNovedadArchivo> listNov) {
        this.listNov = listNov;
    }

    public NovedadInfrastruc getNovedad() {
        return novedad;
    }

    public void setNovedad(NovedadInfrastruc novedad) {
        this.novedad = novedad;
    }

    public int getI_idNovedadTipoInfraStruct() {
        return i_idNovedadTipoInfraStruct;
    }

    public void setI_idNovedadTipoInfraStruct(int i_idNovedadTipoInfraStruct) {
        this.i_idNovedadTipoInfraStruct = i_idNovedadTipoInfraStruct;
    }

    public int getI_idNovedaTipoDetallesInfraStruct() {
        return i_idNovedaTipoDetallesInfraStruct;
    }

    public void setI_idNovedaTipoDetallesInfraStruct(int i_idNovedaTipoDetallesInfraStruct) {
        this.i_idNovedaTipoDetallesInfraStruct = i_idNovedaTipoDetallesInfraStruct;
    }

    public NovedadTipoInfrastruc getNovTipoInfrastruc() {
        return novTipoInfrastruc;
    }

    public void setNovTipoInfrastruc(NovedadTipoInfrastruc novTipoInfrastruc) {
        this.novTipoInfrastruc = novTipoInfrastruc;
    }

    public NovedadTipoDetallesInfrastruc getNovTipoDetInfrastruc() {
        return novTipoDetInfrastruc;
    }

    public void setNovTipoDetInfrastruc(NovedadTipoDetallesInfrastruc novTipoDetInfrastruc) {
        this.novTipoDetInfrastruc = novTipoDetInfrastruc;
    }

    public List<NovedadTipoInfrastruc> getListNovTipoInfrastruc() {
        return listNovTipoInfrastruc;
    }

    public void setListNovTipoInfrastruc(List<NovedadTipoInfrastruc> listNovTipoInfrastruc) {
        this.listNovTipoInfrastruc = listNovTipoInfrastruc;
    }

    public List<NovedadTipoDetallesInfrastruc> getListNovTipoDetInfrastruc() {
        return listNovTipoDetInfrastruc;
    }

    public void setListNovTipoDetInfrastruc(List<NovedadTipoDetallesInfrastruc> listNovTipoDetInfrastruc) {
        this.listNovTipoDetInfrastruc = listNovTipoDetInfrastruc;
    }

    public List<String> getListFotos() {
        return listFotos;
    }

    public void setListFotos(List<String> listFotos) {
        this.listFotos = listFotos;
    }

    public SegReportePilonaNovedad getSegReportePilonaNovedad() {
        return segReportePilonaNovedad;
    }

    public void setSegReportePilonaNovedad(SegReportePilonaNovedad segReportePilonaNovedad) {
        this.segReportePilonaNovedad = segReportePilonaNovedad;
    }

    public List<String> getListFotosActividad() {
        return listFotosActividad;
    }

    public void setListFotosActividad(List<String> listFotosActividad) {
        this.listFotosActividad = listFotosActividad;
    }

    public List<String> getListFotosMinuta() {
        return listFotosMinuta;
    }

    public void setListFotosMinuta(List<String> listFotosMinuta) {
        this.listFotosMinuta = listFotosMinuta;
    }

    public boolean isFlag_rremove_photo() {
        return flag_rremove_photo;
    }

    public void setFlag_rremove_photo(boolean flag_rremove_photo) {
        this.flag_rremove_photo = flag_rremove_photo;
    }

}
