/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableEstacionFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.NovedadInfrastrucFacadeLocal;
import com.movilidad.ejb.NovedadTipoInfrastrucFacadeLocal;
import com.movilidad.ejb.SegPilonaFacadeLocal;
import com.movilidad.model.AseoParamArea;
import com.movilidad.model.CableEstacion;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.NovedadInfrastruc;
import com.movilidad.model.NovedadTipoDetallesInfrastruc;
import com.movilidad.model.NovedadTipoInfrastruc;
import com.movilidad.model.SegPilona;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.IOException;
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
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "novInfrastructBean")
@ViewScoped
public class NovedadInfrastructJSFManagedBean implements Serializable {

    /**
     * Creates a new instance of NovedadInfrastructJSFManagedBean
     */
    public NovedadInfrastructJSFManagedBean() {
    }

    @EJB
    private NovedadInfrastrucFacadeLocal novedadInfrastrucEJB;
    @EJB
    private NovedadTipoInfrastrucFacadeLocal novTipoInfrastrucEJB;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private SegPilonaFacadeLocal segPilonaEJB;
    @EJB
    private CableEstacionFacadeLocal cableEstacionEJB;
    @Inject
    private UploadFotoJSFManagedBean fotoJSFManagedBean;
    private List<NovedadInfrastruc> list;
    private List<NovedadTipoInfrastruc> listNovTipoInfrastruc;
    private List<NovedadTipoDetallesInfrastruc> listNovTipoDetInfrastruc;
    private List<SegPilona> listPilonas;
    private List<AseoParamArea> listAseoParamArea;

    private NovedadInfrastruc novedadInfrastruc;
    private SegPilona pilona;
    private AseoParamArea aseoParamArea;
    private NovedadTipoInfrastruc novTipoInfrastruc;
    private NovedadTipoDetallesInfrastruc novTipoDetInfrastruc;
    private List<CableEstacion> listEstacion;

    private List<UploadedFile> archivos;
    private List<String> listFotos = new ArrayList<>();
    private List<String> lista_fotos_remover = new ArrayList<>();

    private Date desde = MovilidadUtil.fechaHoy();
    private Date hasta = MovilidadUtil.fechaHoy();

    private boolean flag_rremove_photo = false;

    private int i_idNovedadTipoInfraStruct = 0;
    private int i_idNovedaTipoDetallesInfraStruct = 0;
    private int i_idSegPilona = 0;
    private int i_idAseoParamArea = 0;

    private int i_idCableEstacion;

    UserExtended user;

    @PostConstruct
    public void init() {
        consultar();
        getUser();
    }

    public void getUser() {
        try {
            user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            user = null;
        }
    }

    /**
     *
     */
    public void consultar() {
        list = novedadInfrastrucEJB.findRanfoFechaEstadoReg(desde, hasta);
    }

    public boolean esAlterable() {
        if (novedadInfrastruc == null) {
            return false;
        }
        for (GrantedAuthority i : user.getAuthorities()) {
            if (i.getAuthority().equals("PROFGEN")) {
                return true;
            }
        }
        if (novedadInfrastruc.getUsername().equals(user.getUsername())) {
            return true;
        }
        return false;
    }

    public void obtenerFotos(boolean flag) throws IOException {
        if (novedadInfrastruc.getPathFotos() == null
                || (novedadInfrastruc.getPathFotos() != null
                && novedadInfrastruc.getPathFotos().equals("/"))) {
            MovilidadUtil.addAdvertenciaMessage("No hay fotos para visualizar.");
            return;
        }

        if (flag) {
            obtenerFotosReturn();
        } else {
            String path = Util.getProperty("novedadInfrastruct.dir") + novedadInfrastruc.getIdNovedadInfrastruc() + "/cierre/";
            listFotos = Util.getFileListByPath(path);

        }
        fotoJSFManagedBean.setListFotos(listFotos);
        MovilidadUtil.openModal("galeria_foto_dialog_wv");
    }

    public void editar() throws IOException {
        if (esAlterable()) {
            MovilidadUtil.addErrorMessage("No es posible modificar esta novedad");
            return;
        }
        listPilonas = segPilonaEJB.findByEstadoReg();
        listAseoParamArea = new ArrayList<>();
        listEstacion = cableEstacionEJB.findByEstadoReg();
        if (novedadInfrastruc.getIdSegPilona() != null) {
            i_idSegPilona = novedadInfrastruc.getIdSegPilona().getIdSegPilona();
            pilona = novedadInfrastruc.getIdSegPilona();
        } else {
            i_idSegPilona = 0;
            pilona = null;
        }
        if (novedadInfrastruc.getIdAseoParamArea() != null) {
            i_idAseoParamArea = novedadInfrastruc.getIdAseoParamArea().getIdAseoParamArea();
            i_idCableEstacion = novedadInfrastruc.getIdAseoParamArea().getIdCableEstacion().getIdCableEstacion();
            aseoParamArea = novedadInfrastruc.getIdAseoParamArea();
        } else {
            i_idAseoParamArea = 0;
            i_idCableEstacion = 0;
            aseoParamArea = null;
        }
        flag_rremove_photo = user.getUsername().equals(novedadInfrastruc.getUsername());
        listNovTipoInfrastruc = novTipoInfrastrucEJB.findAll();
        i_idNovedadTipoInfraStruct = novedadInfrastruc.getNovedadTipoInfrastruc().getIdNovedadTipoInfrastruc();
        i_idNovedaTipoDetallesInfraStruct = novedadInfrastruc.getNovedadTipoDetallesInfrastruc().getIdNovedadTipoDetInfrastruc();
        prepareTipoDetalles(true);
        prepareTipoDetalles(false);
        archivos = new ArrayList<>();
        lista_fotos_remover = new ArrayList<>();
        listFotos = obtenerFotosReturn();
        selectEstacion();
        MovilidadUtil.openModal("nov_novedad_infra_wv");
    }

    public List<String> obtenerFotosReturn() throws IOException {
        this.listFotos = new ArrayList<>();
        List<String> lstNombresImg;
        String path;
        lstNombresImg = Util.getFileList(novedadInfrastruc.getIdNovedadInfrastruc(), "novedadInfrastruct");
        path = novedadInfrastruc.getPathFotos();
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

    public void selectAseoParamArea() {
        aseoParamArea = null;
        for (AseoParamArea obj : listAseoParamArea) {
            if (obj.getIdAseoParamArea().equals(i_idAseoParamArea)) {
                aseoParamArea = obj;
                break;
            }
        }
    }

    public void selectEstacion() {
        listAseoParamArea.clear();
        aseoParamArea = null;
        for (CableEstacion obj : listEstacion) {
            if (obj.getIdCableEstacion().equals(i_idCableEstacion)) {
                if (obj.getAseoParamAreaList() != null) {
                    for (AseoParamArea ap : obj.getAseoParamAreaList()) {
                        if (ap.getEstadoReg() == 0) {
                            listAseoParamArea.add(ap);
                        }
                    }
                }
                break;
            }
        }
    }

    public void changeEstatus(int opc) {
        novedadInfrastruc.setUsername(user.getUsername());
        novedadInfrastruc.setModificado(MovilidadUtil.fechaCompletaHoy());
        novedadInfrastruc.setEstado(opc);
        novedadInfrastrucEJB.edit(novedadInfrastruc);
        MovilidadUtil.addSuccessMessage("Accion finalizada con exito.");
        consultar();
    }

    public void guardarSeguimiento() {
        novedadInfrastruc.setUsername(user.getUsername());
        novedadInfrastruc.setUsrSeguimiento(user.getUsername());
        novedadInfrastruc.setModificado(MovilidadUtil.fechaCompletaHoy());
        novedadInfrastrucEJB.edit(novedadInfrastruc);
        MovilidadUtil.addSuccessMessage("Accion finalizada con exito.");
        MovilidadUtil.hideModal("seguimiento_wv");
        consultar();
    }

    public void guardarCierre() {
        novedadInfrastruc.setUsername(user.getUsername());
        novedadInfrastruc.setUsrCierre(user.getUsername());
        novedadInfrastruc.setEstado(4);
        novedadInfrastruc.setModificado(MovilidadUtil.fechaCompletaHoy());

        if (!archivos.isEmpty()) {

            for (UploadedFile f : archivos) {
                String path = Util.getProperty("novedadInfrastruct.dir")
                        + novedadInfrastruc.getIdNovedadInfrastruc() + "/";

                String pathCierre = path + "cierre/";
                if (Util.crearDirectorio(pathCierre)) {
                    pathCierre = pathCierre + Util.generarNombre(f.getFileName());
                    Util.saveFile(f, pathCierre, false);
                }
                novedadInfrastruc.setPathFotos(path);
            }
        }
        novedadInfrastrucEJB.edit(novedadInfrastruc);
        MovilidadUtil.addSuccessMessage("Accion finalizada con exito.");
        MovilidadUtil.hideModal("cierre_wv");
        consultar();
    }

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

    public void handleFileUpload(FileUploadEvent event) {
        archivos.add(event.getFile());
    }

    public void onCerrar() {
        archivos = new ArrayList<>();
    }

    public void delete(String url) {
        lista_fotos_remover.add(url);
        listFotos.remove(url);
    }

    public void guardar() throws IOException {
        boolean create = false;
        novedadInfrastruc.setUsername(user.getUsername());
        novedadInfrastruc.setNovedadTipoInfrastruc(novTipoInfrastruc);
        novedadInfrastruc.setNovedadTipoDetallesInfrastruc(novTipoDetInfrastruc);
        novedadInfrastruc.setIdSegPilona(pilona);
        novedadInfrastruc.setIdAseoParamArea(aseoParamArea);
        if (novedadInfrastruc.getIdNovedadInfrastruc() == null) {
            create = true;
            novedadInfrastruc.setCreado(MovilidadUtil.fechaCompletaHoy());
            novedadInfrastruc.setEstado(1);
            novedadInfrastruc.setUserReporta(user.getUsername());
            novedadInfrastrucEJB.create(novedadInfrastruc);
        } else {
            for (String url : lista_fotos_remover) {
                MovilidadUtil.eliminarFichero(url);
            }
            novedadInfrastruc.setModificado(MovilidadUtil.fechaCompletaHoy());
            novedadInfrastrucEJB.edit(novedadInfrastruc);
        }

        if (!archivos.isEmpty()) {
            String path = "/";
            for (UploadedFile f : archivos) {
                path = Util.saveFile(f, novedadInfrastruc.getIdNovedadInfrastruc(), "novedadInfrastruct");
            }
            novedadInfrastruc.setPathFotos(path);
            novedadInfrastrucEJB.edit(novedadInfrastruc);
        }
        if (create && novedadInfrastruc.getNovedadTipoDetallesInfrastruc().getNotifica().equals(1)
                && novedadInfrastruc.getNovedadTipoDetallesInfrastruc().getEmails() != null) {
            notificar(novedadInfrastruc);
        }
        archivos.clear();
        MovilidadUtil.addSuccessMessage("Se registro la informacion exitosamente.");
        MovilidadUtil.hideModal("nov_novedad_infra_wv");
        novedadInfrastruc = null;
        consultar();
    }

    public void nuevo() {
        flag_rremove_photo = false;
        i_idNovedaTipoDetallesInfraStruct = 0;
        i_idNovedadTipoInfraStruct = 0;
        i_idSegPilona = 0;
        i_idAseoParamArea = 0;
        i_idCableEstacion = 0;
        listNovTipoInfrastruc = novTipoInfrastrucEJB.findAll();
        novedadInfrastruc = new NovedadInfrastruc();
        archivos = new ArrayList<>();
        listPilonas = segPilonaEJB.findByEstadoReg();
        listAseoParamArea = new ArrayList<>();
        listEstacion = cableEstacionEJB.findByEstadoReg();
        novedadInfrastruc.setFechaHoraNov(MovilidadUtil.fechaCompletaHoy());
        novedadInfrastruc.setFechaHoraReg(MovilidadUtil.fechaCompletaHoy());
        MovilidadUtil.openModal("nov_novedad_infra_wv");
    }

    private void notificar(NovedadInfrastruc novedad) throws IOException {
        List<String> adjuntos;
        adjuntos = obtenerFotosReturn();
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("fecha", Util.dateFormat(novedad.getFechaHoraNov()));
        mailProperties.put("pilona", novedad.getIdSegPilona() != null
                ? novedad.getIdSegPilona().getNombre() : "N/A");
        mailProperties.put("area", novedad.getIdAseoParamArea() != null
                ? novedad.getIdAseoParamArea().getNombre() : "N/A");
        mailProperties.put("tipo", novedad.getNovedadTipoInfrastruc().getNombre());
        mailProperties.put("detalle", novedad.getNovedadTipoDetallesInfrastruc().getNombre());
        mailProperties.put("username", novedad.getUsername());
        mailProperties.put("generada", Util.dateTimeFormat(novedad.getCreado()));
        mailProperties.put("descripcion", novedad.getDescripcion());
        String subject = "Novedad de Infraestructura";
        String destinatarios = novedad.getNovedadTipoDetallesInfrastruc().getEmails();

        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", adjuntos != null ? adjuntos : null);
    }

    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate("novedadInfraestructuraTemplate");
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    public List<NovedadInfrastruc> getList() {
        return list;
    }

    public void setList(List<NovedadInfrastruc> list) {
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

    public NovedadInfrastruc getNovedadInfrastruc() {
        return novedadInfrastruc;
    }

    public void setNovedadInfrastruc(NovedadInfrastruc novedadInfrastruc) {
        this.novedadInfrastruc = novedadInfrastruc;
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

    public List<AseoParamArea> getListAseoParamArea() {
        return listAseoParamArea;
    }

    public void setListAseoParamArea(List<AseoParamArea> listAseoParamArea) {
        this.listAseoParamArea = listAseoParamArea;
    }

    public int getI_idAseoParamArea() {
        return i_idAseoParamArea;
    }

    public void setI_idAseoParamArea(int i_idAseoParamArea) {
        this.i_idAseoParamArea = i_idAseoParamArea;
    }

    public List<CableEstacion> getListEstacion() {
        return listEstacion;
    }

    public void setListEstacion(List<CableEstacion> listEstacion) {
        this.listEstacion = listEstacion;
    }

    public int getI_idCableEstacion() {
        return i_idCableEstacion;
    }

    public void setI_idCableEstacion(int i_idCableEstacion) {
        this.i_idCableEstacion = i_idCableEstacion;
    }

}
