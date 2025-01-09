/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableCabinaFacadeLocal;
import com.movilidad.ejb.CableEstacionFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.NovedadMttoDocsFacadeLocal;
import com.movilidad.ejb.NovedadMttoFacadeLocal;
import com.movilidad.ejb.NovedadMttoTipoFacadeLocal;
import com.movilidad.ejb.SegPilonaFacadeLocal;
import com.movilidad.model.CableCabina;
import com.movilidad.model.CableEstacion;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.NovedadMtto;
import com.movilidad.model.NovedadMttoDocs;
import com.movilidad.model.NovedadMttoTipo;
import com.movilidad.model.NovedadMttoTipoDet;
import com.movilidad.model.SegPilona;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "novMttoBean")
@ViewScoped
public class NovedadMttoJSFMB implements Serializable {

    /**
     * Creates a new instance of NovedadMttoJSFMB
     */
    public NovedadMttoJSFMB() {
    }

    @EJB
    private NovedadMttoFacadeLocal selectedEJB;
    @EJB
    private NovedadMttoTipoFacadeLocal novMttoTipoEJB;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private SegPilonaFacadeLocal segPilonaEJB;
    @EJB
    private CableEstacionFacadeLocal cableEstacionEJB;
    @EJB
    private CableCabinaFacadeLocal cableCabinaEJB;
    @EJB
    private NovedadMttoDocsFacadeLocal novedadMttoDocsEjb;
    @Inject
    private UploadFotoJSFManagedBean fotoJSFManagedBean;
    private List<NovedadMtto> list;
    private List<NovedadMttoTipo> listNovMttoTipo;
    private List<NovedadMttoTipoDet> listNovTipoDetMtto;
    private List<SegPilona> listPilonas;
    private List<CableCabina> listCableCabina;
    private List<CableEstacion> listCableEstacion;
    private List<NovedadMttoDocs> listVideos;

    private NovedadMtto selected;
    private SegPilona pilona;
    private CableCabina cabina;
    private CableEstacion estacion;
    private NovedadMttoTipo novMttoTipo;
    private NovedadMttoTipoDet novTipoDetMtto;
    private List<CableEstacion> listEstacion;

    private List<UploadedFile> archivos;
    private List<String> listFotos = new ArrayList<>();
    private List<String> lista_fotos_remover = new ArrayList<>();

    private Date desde = MovilidadUtil.fechaHoy();
    private Date hasta = MovilidadUtil.fechaHoy();

    private boolean flag_rremove_photo = false;

    private int i_idNovedadMttoTipo = 0;
    private int i_idNovedaMttoTipoDet = 0;
    private int i_idSegPilona = 0;
    private int i_cabina = 0;
    private int i_estacion = 0;
    private String tamanoNovedadDocumento;

    private StreamedContent fileDescargar;

    UserExtended user;

    @PostConstruct
    public void init() {
        consultar();
        getUser();
    }

    public void getUser() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean esAlterable() {
        if (selected == null) {
            return false;
        }
        for (GrantedAuthority i : user.getAuthorities()) {
            if (i.getAuthority().equals("PROFGEN")) {
                return true;
            }
        }
        return selected.getUsername().equals(user.getUsername());
    }

    /**
     *
     */
    public void consultar() {
        list = selectedEJB.findRanfoFechaEstadoReg(desde, hasta);
    }

    public void obtenerVideos() {
        listVideos = novedadMttoDocsEjb.findAllByNovMtto(selected.getIdNovedadMtto());

        if (listVideos == null || listVideos.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron videos asociados a la novedad");
            return;
        }

        MovilidadUtil.openModal("NovedadDocumentosListDialog");
    }

    public void eliminarArchivo(NovedadMttoDocs doc) {
        doc.setEstadoReg(1);
        doc.setUsername(user.getUsername());
        doc.setModificado(MovilidadUtil.fechaCompletaHoy());
        Util.deleteFile(doc.getPath());
        novedadMttoDocsEjb.edit(doc);
        listVideos.remove(doc);
    }

    public String retornarNombreVideo(String path) {
        int tamanoPath = path.split("/").length;
        return path.split("/")[tamanoPath - 1];
    }

    public void obtenerFotos(boolean flag) throws IOException {
        if (selected.getPathFotos() == null || (selected.getPathFotos() != null && selected.getPathFotos().equals("/"))) {
            MovilidadUtil.addAdvertenciaMessage("No hay fotos para visualizar.");
            return;
        }
        if (flag) {
            obtenerFotosReturn();
        } else {
            String path = Util.getProperty("novedadMtto.dir") + selected.getIdNovedadMtto() + "/" + "cierre/";
            listFotos = Util.getFileListByPath(path);

        }
        fotoJSFManagedBean.setListFotos(listFotos);
        MovilidadUtil.openModal("galeria_foto_dialog_wv");
    }

    public void editar() throws IOException {
        if (!esAlterable()) {
            MovilidadUtil.addErrorMessage("No es posible modificar esta novedad");
            return;
        }
        listPilonas = segPilonaEJB.findByEstadoReg();
        listCableCabina = cableCabinaEJB.findAllByEstadoRegAndNombreOrderBy("asc");
        listEstacion = cableEstacionEJB.findByEstadoReg();
        tamanoNovedadDocumento = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_NOVEDAD_MTTO_ARCHIVO_TAMANO);
        if (selected.getIdSegPilona() != null) {
            i_idSegPilona = selected.getIdSegPilona().getIdSegPilona();
            pilona = selected.getIdSegPilona();
        } else {
            i_idSegPilona = 0;
            pilona = null;
        }
        if (selected.getIdCableCabina() != null) {
            i_cabina = selected.getIdCableCabina().getIdCableCabina();
            cabina = selected.getIdCableCabina();
        } else {
            i_cabina = 0;
            cabina = null;
        }
        if (selected.getIdCableEstacion() != null) {
            i_estacion = selected.getIdCableEstacion().getIdCableEstacion();
            estacion = selected.getIdCableEstacion();
        } else {
            i_estacion = 0;
            cabina = null;
        }
        flag_rremove_photo = esAlterable();
        listNovMttoTipo = novMttoTipoEJB.findAll();
        i_idNovedadMttoTipo = selected.getIdNovedadMttoTipo().getIdNovedadMttoTipo();
        i_idNovedaMttoTipoDet = selected.getIdNovedadMttoTipoDet().getIdNovedadMttoTipoDet();
        prepareTipoDetalles(true);
        prepareTipoDetalles(false);
        archivos = new ArrayList<>();
        lista_fotos_remover = new ArrayList<>();
        listFotos = obtenerFotosReturn();
        selectEstacion();
        MovilidadUtil.openModal("nov_novedad_mtto_wv");
    }

    public List<String> obtenerFotosReturn() throws IOException {
        this.listFotos = new ArrayList<>();
        List<String> lstNombresImg;
        String path;
        lstNombresImg = Util.getFileList(selected.getIdNovedadMtto(), "novedadMtto");
        path = selected.getPathFotos();
        for (String f : lstNombresImg) {
            f = path + f;
            listFotos.add(f);
        }
        return listFotos;

    }

    public void selectPilona() {
        pilona = null;
        for (SegPilona obj : listPilonas) {
            if (obj.getIdSegPilona().equals(i_idSegPilona)) {
                pilona = obj;
                break;
            }
        }
    }

    public void onCerrar() {
        archivos = new ArrayList<>();
    }

    public void selectCabina() {
        cabina = null;
        for (CableCabina obj : listCableCabina) {
            if (obj.getIdCableCabina().equals(i_cabina)) {
                cabina = obj;
                break;
            }
        }
    }

    public void selectEstacion() {
        estacion = null;
        for (CableEstacion obj : listEstacion) {
            if (obj.getIdCableEstacion().equals(i_estacion)) {
                estacion = obj;
                break;
            }
        }
    }

    public void changeEstatus(int opc) {
        selected.setUsername(user.getUsername());
        selected.setModificado(MovilidadUtil.fechaCompletaHoy());
        selected.setEstado(opc);
        selectedEJB.edit(selected);
        MovilidadUtil.addSuccessMessage("Accion finalizada con exito.");
        consultar();
    }

    public void guardarSeguimiento() {
        selected.setUsrSeguimiento(user.getUsername());
        selected.setModificado(MovilidadUtil.fechaCompletaHoy());
        selectedEJB.edit(selected);
        MovilidadUtil.addSuccessMessage("Accion finalizada con exito.");
        MovilidadUtil.hideModal("seguimiento_wv");
        consultar();
    }

    public void guardarCierre() {
        selected.setUsrCierre(user.getUsername());
        selected.setEstado(4);
        selected.setModificado(MovilidadUtil.fechaCompletaHoy());

        if (!archivos.isEmpty()) {

            for (UploadedFile f : archivos) {
                String path = Util.getProperty("novedadMtto.dir")
                        + selected.getIdNovedadMtto() + "/";

                String pathCierre = path + "cierre/";
                if (Util.crearDirectorio(pathCierre)) {
                    pathCierre = pathCierre + Util.generarNombre(f.getFileName());
                    Util.saveFile(f, pathCierre, false);
                }
                selected.setPathFotos(path);
            }
        }
        selectedEJB.edit(selected);
        MovilidadUtil.addSuccessMessage("Accion finalizada con exito.");
        MovilidadUtil.hideModal("cierre_wv");
        consultar();
    }

    public void prepareTipoDetalles(boolean opc) {
        if (opc) {
            novMttoTipo = null;
            for (NovedadMttoTipo gt : listNovMttoTipo) {
                if (gt.getIdNovedadMttoTipo().equals(i_idNovedadMttoTipo)) {
                    novMttoTipo = gt;
                    listNovTipoDetMtto = gt.getNovedadMttoTipoDetList();
                    break;
                }
            }
        } else {
            novTipoDetMtto = null;
            for (NovedadMttoTipoDet gtd : listNovTipoDetMtto) {
                if (gtd.getIdNovedadMttoTipoDet().equals(i_idNovedaMttoTipoDet)) {
                    novTipoDetMtto = gtd;
                    break;
                }
            }
        }
    }

    /**
     * Realiza la carga del archivo para mostrar el documento en la vista
     *
     * @param path
     * @throws Exception
     */
    public void prepDownloadLocal(String path) throws Exception {
        InputStream stream;
        File archivo = new File(path);
        stream = new FileInputStream(archivo);
        fileDescargar = new DefaultStreamedContent(stream, "video/mp4", retornarNombreVideo(path));
    }

    public void handleFileUpload(FileUploadEvent event) {
        archivos.add(event.getFile());
        MovilidadUtil.addSuccessMessage("Archivo(s) Cargado(s)");
    }

    public void delete(String url) {
        lista_fotos_remover.add(url);
        listFotos.remove(url);
    }

    public void guardar() throws IOException {
        boolean create = false;
        selected.setUsername(user.getUsername());
        selected.setIdNovedadMttoTipo(novMttoTipo);
        selected.setIdNovedadMttoTipoDet(novTipoDetMtto);
        selected.setIdSegPilona(pilona);
        selected.setIdCableCabina(cabina);
        selected.setIdCableEstacion(estacion);
        if (selected.getIdNovedadMtto() == null) {
            create = true;
            selected.setCreado(MovilidadUtil.fechaCompletaHoy());
            selected.setEstado(1);
            selected.setUserReporta(user.getUsername());
            selectedEJB.create(selected);
        } else {
            for (String url : lista_fotos_remover) {
                MovilidadUtil.eliminarFichero(url);
            }
            selected.setModificado(MovilidadUtil.fechaCompletaHoy());
            selectedEJB.edit(selected);
        }

        if (!archivos.isEmpty()) {
            String path = "/";
            for (UploadedFile f : archivos) {
                path = Util.saveFile(f, selected.getIdNovedadMtto(), "novedadMtto");
                if (path.contains("mp4") || path.contains("MP4")) {
                    NovedadMttoDocs doc = new NovedadMttoDocs();
                    doc.setIdNovedadMtto(selected);
                    doc.setPath(path);
                    doc.setUsername(user.getUsername());
                    doc.setCreado(MovilidadUtil.fechaCompletaHoy());
                    doc.setEstadoReg(0);
                    novedadMttoDocsEjb.create(doc);
                } else {
                    selected.setPathFotos(path);
                    selectedEJB.edit(selected);
                }
            }
        }
        if (create && selected.getIdNovedadMttoTipoDet().getNotifica().equals(1)
                && selected.getIdNovedadMttoTipoDet().getEmails() != null) {
            notificar(selected);
        }
        archivos.clear();
        MovilidadUtil.addSuccessMessage("Se registro la informacion exitosamente.");
        MovilidadUtil.hideModal("nov_novedad_mtto_wv");
        selected = null;
        consultar();
    }

    public void nuevo() {
        flag_rremove_photo = false;
        i_idNovedaMttoTipoDet = 0;
        i_idNovedadMttoTipo = 0;
        i_idSegPilona = 0;
        i_cabina = 0;
        i_estacion = 0;
        tamanoNovedadDocumento = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_NOVEDAD_MTTO_ARCHIVO_TAMANO);
        listNovMttoTipo = novMttoTipoEJB.findAll();
        selected = new NovedadMtto();
        archivos = new ArrayList<>();
        listPilonas = segPilonaEJB.findByEstadoReg();
        listCableCabina = cableCabinaEJB.findAllByEstadoRegAndNombreOrderBy("asc");
        listEstacion = cableEstacionEJB.findByEstadoReg();
        selected.setFechaHoraNov(MovilidadUtil.fechaCompletaHoy());
        selected.setFechaHoraReg(MovilidadUtil.fechaCompletaHoy());
        MovilidadUtil.openModal("nov_novedad_mtto_wv");
    }

    private void notificar(NovedadMtto novedad) throws IOException {
        List<String> adjuntos;
        adjuntos = obtenerFotosReturn();
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("fecha", Util.dateFormat(novedad.getFechaHoraNov()));
        mailProperties.put("pilona", novedad.getIdSegPilona() != null
                ? novedad.getIdSegPilona().getNombre() : "N/A");
        mailProperties.put("cabina", novedad.getIdCableCabina() != null
                ? novedad.getIdCableCabina().getNombre() : "");
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

    public List<NovedadMtto> getList() {
        return list;
    }

    public void setList(List<NovedadMtto> list) {
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

    public NovedadMtto getNovedadMtto() {
        return selected;
    }

    public void setNovedadMtto(NovedadMtto selected) {
        this.selected = selected;
    }

    public List<UploadedFile> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<UploadedFile> archivos) {
        this.archivos = archivos;
    }

    public List<String> getListFotos() {
        return listFotos;
    }

    public void setListFotos(List<String> listFotos) {
        this.listFotos = listFotos;
    }

    public List<NovedadMttoTipo> getListNovMttoTipo() {
        return listNovMttoTipo;
    }

    public void setListNovMttoTipo(List<NovedadMttoTipo> listNovMttoTipo) {
        this.listNovMttoTipo = listNovMttoTipo;
    }

    public List<NovedadMttoTipoDet> getListNovTipoDetMtto() {
        return listNovTipoDetMtto;
    }

    public void setListNovTipoDetMtto(List<NovedadMttoTipoDet> listNovTipoDetMtto) {
        this.listNovTipoDetMtto = listNovTipoDetMtto;
    }

    public int getI_idNovedadTipoInfraStruct() {
        return i_idNovedadMttoTipo;
    }

    public void setI_idNovedadTipoInfraStruct(int i_idNovedadMttoTipo) {
        this.i_idNovedadMttoTipo = i_idNovedadMttoTipo;
    }

    public int getI_idNovedaTipoDetallesInfraStruct() {
        return i_idNovedaMttoTipoDet;
    }

    public void setI_idNovedaTipoDetallesInfraStruct(int i_idNovedaMttoTipoDet) {
        this.i_idNovedaMttoTipoDet = i_idNovedaMttoTipoDet;
    }

    public NovedadMttoTipo getNovMttoTipo() {
        return novMttoTipo;
    }

    public void setNovMttoTipo(NovedadMttoTipo novMttoTipo) {
        this.novMttoTipo = novMttoTipo;
    }

    public NovedadMttoTipoDet getNovTipoDetMtto() {
        return novTipoDetMtto;
    }

    public void setNovTipoDetMtto(NovedadMttoTipoDet novTipoDetMtto) {
        this.novTipoDetMtto = novTipoDetMtto;
    }

    public boolean isFlag_rremove_photo() {
        return flag_rremove_photo;
    }

    public void setFlag_rremove_photo(boolean flag_rremove_photo) {
        this.flag_rremove_photo = flag_rremove_photo;
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

    public List<CableEstacion> getListEstacion() {
        return listEstacion;
    }

    public void setListEstacion(List<CableEstacion> listEstacion) {
        this.listEstacion = listEstacion;
    }

    public List<CableCabina> getListCableCabina() {
        return listCableCabina;
    }

    public void setListCableCabina(List<CableCabina> listCableCabina) {
        this.listCableCabina = listCableCabina;
    }

    public List<CableEstacion> getListCableEstacion() {
        return listCableEstacion;
    }

    public void setListCableEstacion(List<CableEstacion> listCableEstacion) {
        this.listCableEstacion = listCableEstacion;
    }

    public List<NovedadMttoDocs> getListVideos() {
        return listVideos;
    }

    public void setListVideos(List<NovedadMttoDocs> listVideos) {
        this.listVideos = listVideos;
    }

    public int getI_idNovedadMttoTipo() {
        return i_idNovedadMttoTipo;
    }

    public void setI_idNovedadMttoTipo(int i_idNovedadMttoTipo) {
        this.i_idNovedadMttoTipo = i_idNovedadMttoTipo;
    }

    public int getI_idNovedaMttoTipoDet() {
        return i_idNovedaMttoTipoDet;
    }

    public void setI_idNovedaMttoTipoDet(int i_idNovedaMttoTipoDet) {
        this.i_idNovedaMttoTipoDet = i_idNovedaMttoTipoDet;
    }

    public int getI_cabina() {
        return i_cabina;
    }

    public void setI_cabina(int i_cabina) {
        this.i_cabina = i_cabina;
    }

    public int getI_estacion() {
        return i_estacion;
    }

    public void setI_estacion(int i_estacion) {
        this.i_estacion = i_estacion;
    }

    public NovedadMtto getSelected() {
        return selected;
    }

    public void setSelected(NovedadMtto selected) {
        this.selected = selected;
    }

    public StreamedContent getFileDescargar() {
        return fileDescargar;
    }

    public void setFileDescargar(StreamedContent fileDescargar) {
        this.fileDescargar = fileDescargar;
    }

    public String getTamanoNovedadDocumento() {
        return tamanoNovedadDocumento;
    }

    public void setTamanoNovedadDocumento(String tamanoNovedadDocumento) {
        this.tamanoNovedadDocumento = tamanoNovedadDocumento;
    }

}
