/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GmoNovedadDocumentosFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.GmoNovedadInfrastrucFacadeLocal;
import com.movilidad.ejb.GmoNovedadTipoInfrastrucFacadeLocal;
import com.movilidad.ejb.PrgRouteFacadeLocal;
import com.movilidad.model.GmoNovedadDocumentos;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.GmoNovedadInfrastruc;
import com.movilidad.model.GmoNovedadInfrastrucRutaAfectada;
import com.movilidad.model.GmoNovedadTipoDetallesInfrastruc;
import com.movilidad.model.GmoNovedadTipoInfrastruc;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.PrgRoute;
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
import javax.transaction.Transactional;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "gmoNovInfrastructBean")
@ViewScoped
public class GmoNovedadInfrastructJSFBean implements Serializable {

    /**
     * Creates a new instance of GmoNovedadInfrastructJSFManagedBean
     */
    public GmoNovedadInfrastructJSFBean() {
    }

    @EJB
    private GmoNovedadInfrastrucFacadeLocal novedadInfrastrucEJB;
    @EJB
    private GmoNovedadTipoInfrastrucFacadeLocal novTipoInfrastrucEJB;
    @EJB
    private GmoNovedadDocumentosFacadeLocal documentosEJB;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private PrgRouteFacadeLocal prgRouteEJB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<GmoNovedadInfrastruc> list;
    private List<GmoNovedadTipoInfrastruc> listNovTipoInfrastruc;
    private List<GmoNovedadTipoDetallesInfrastruc> listNovTipoDetInfrastruc;
    private List<GmoNovedadInfrastrucRutaAfectada> listadoRutasEditar;
    private List<PrgRoute> lstRutas;
    private List<PrgRoute> lstRutasAfectadas;

    private GmoNovedadInfrastruc novedadInfrastruc;
    private GmoNovedadTipoInfrastruc novTipoInfrastruc;
    private GmoNovedadTipoDetallesInfrastruc novTipoDetInfrastruc;

    private List<UploadedFile> archivos;
    private List<String> listFotos = new ArrayList<>();

    private Date desde = MovilidadUtil.fechaHoy();
    private Date hasta = MovilidadUtil.fechaHoy();

    //georeferencia
    private String latitud = "4.646189";
    private String logitud = "-74.078540";
    private MapModel simpleModel;

    private boolean isEditing = false;
    private boolean flagListaUF;

    private int i_idNovedadTipoInfraStruct = 0;
    private int i_idNovedaTipoDetallesInfraStruct = 0;

    UserExtended user;

    @PostConstruct
    public void init() {
        flagListaUF = unidadFuncionalSessionBean.getIdGopUnidadFuncional() == 0;
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
        list = novedadInfrastrucEJB.findRanfoFechaEstadoReg(desde, hasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
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

    public void onPointSelect(PointSelectEvent event) {
        try {
            simpleModel = new DefaultMapModel();
            LatLng latlng = event.getLatLng();
            novedadInfrastruc.setLatitud(String.valueOf(latlng.getLat()));
            novedadInfrastruc.setLongitud(String.valueOf(latlng.getLng()));
            simpleModel.addOverlay(new Marker(latlng, "Punto Seleccionado"));
            latitud = String.valueOf(latlng.getLat());
            logitud = String.valueOf(latlng.getLng());
            MovilidadUtil.addSuccessMessage("Coordenadas seleccionadas, Longitud:"
                    + logitud + " Latitud:" + latitud);
        } catch (Exception e) {
            System.out.println("Error Evento onpointSelect Novedad");
        }
    }

    public void editar() throws IOException {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        if (!esAlterable()) {
            MovilidadUtil.addErrorMessage("No es posible modificar esta novedad");
            return;
        }

        cargarRutasSeleccionadas();

        isEditing = true;
        listNovTipoInfrastruc = novTipoInfrastrucEJB.findAll();
        i_idNovedadTipoInfraStruct = novedadInfrastruc.getIdGmoNovedadTipoInfrastruc().getIdGmoNovedadTipoInfrastruc();
        i_idNovedaTipoDetallesInfraStruct = novedadInfrastruc.getIdGmoNovedadTipoDetInfrastruc().getIdGmoNovedadTipoDetInfrastruc();

        simpleModel = new DefaultMapModel();
        LatLng latlngNovedad = new LatLng(Double.parseDouble(novedadInfrastruc.getLatitud()), Double.parseDouble(novedadInfrastruc.getLongitud()));
        simpleModel.addOverlay(new Marker(latlngNovedad, "Coordenadas"));

        prepareTipoDetalles(true);
        prepareTipoDetalles(false);
        archivos = new ArrayList<>();
        MovilidadUtil.openModal("nov_novedad_infra_wv");
    }

    public void changeEstatus(int opc) {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        novedadInfrastruc.setUsername(user.getUsername());
        novedadInfrastruc.setModificado(MovilidadUtil.fechaCompletaHoy());
        novedadInfrastruc.setEstado(opc);
        novedadInfrastrucEJB.edit(novedadInfrastruc);
        MovilidadUtil.addSuccessMessage("Accion finalizada con exito.");
        consultar();
    }

    public void guardarCierre() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        novedadInfrastruc.setUsername(user.getUsername());
        novedadInfrastruc.setUsrCierre(user.getUsername());
        novedadInfrastruc.setEstado(2);
        novedadInfrastruc.setModificado(MovilidadUtil.fechaCompletaHoy());
        novedadInfrastrucEJB.edit(novedadInfrastruc);
        MovilidadUtil.addSuccessMessage("Accion finalizada con exito.");
        MovilidadUtil.hideModal("cierre_wv");
        consultar();
    }

    public void prepareTipoDetalles(boolean opc) {
        if (opc) {
            novTipoInfrastruc = null;
            for (GmoNovedadTipoInfrastruc gt : listNovTipoInfrastruc) {
                if (gt.getIdGmoNovedadTipoInfrastruc().equals(i_idNovedadTipoInfraStruct)) {
                    novTipoInfrastruc = gt;
                    listNovTipoDetInfrastruc = gt.getGmoNovedadTipoDetallesInfrastrucList();
                    break;
                }
            }
        } else {
            novTipoDetInfrastruc = null;
            for (GmoNovedadTipoDetallesInfrastruc gtd : listNovTipoDetInfrastruc) {
                if (gtd.getIdGmoNovedadTipoDetInfrastruc().equals(i_idNovedaTipoDetallesInfraStruct)) {
                    novTipoDetInfrastruc = gtd;
                    break;
                }
            }
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        archivos.add(event.getFile());
        MovilidadUtil.addSuccessMessage("Archivo(s) agregados éxitosamente.");
        MovilidadUtil.updateComponent(":msgs");
    }

    public void onCerrar() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        MovilidadUtil.openModal("cierre_wv");

        archivos = new ArrayList<>();
    }

    public void guardar() throws IOException {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        boolean create = false;
        novedadInfrastruc.setUsername(user.getUsername());
        novedadInfrastruc.setIdGmoNovedadTipoInfrastruc(novTipoInfrastruc);
        novedadInfrastruc.setIdGmoNovedadTipoDetInfrastruc(novTipoDetInfrastruc);
        novedadInfrastruc.setIdGopUnidadFuncional(new GopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));
        if (novedadInfrastruc.getIdGmoNovedadInfrastruc() == null) {
            create = true;
            novedadInfrastruc.setCreado(MovilidadUtil.fechaCompletaHoy());
            novedadInfrastruc.setEstado(1);
            novedadInfrastruc.setUserReporta(user.getUsername());

            if (!lstRutasAfectadas.isEmpty()) {
                guardarRutasAfectadas();
            } else {
                novedadInfrastruc.setGmoNovedadInfrastrucRutaAfectadaList(null);
            }

            novedadInfrastrucEJB.create(novedadInfrastruc);
        } else {

            if (!lstRutasAfectadas.isEmpty()) {
                actualizarRutasAfectadas();
            } else {
                novedadInfrastruc.setGmoNovedadInfrastrucRutaAfectadaList(null);
            }
            novedadInfrastruc.setModificado(MovilidadUtil.fechaCompletaHoy());
            novedadInfrastrucEJB.edit(novedadInfrastruc);
        }

        if (!archivos.isEmpty()) {
            String path = "/";
            GmoNovedadDocumentos documento;
            for (UploadedFile f : archivos) {
                documento = new GmoNovedadDocumentos();
                documento.setIdGmoNovedadInfrastruc(novedadInfrastruc);
                documento.setCreado(MovilidadUtil.fechaCompletaHoy());
                documento.setEstadoReg(0);
                documento.setUsuario(user.getUsername());
                documentosEJB.create(documento);

                path = Util.saveFile(f, novedadInfrastruc.getIdGmoNovedadInfrastruc(), "novedadInfrastructGmoGuardar");
                documento.setPathDocumento(path);
                documentosEJB.edit(documento);
            }
//            novedadInfrastruc.setPathFotos(path);
            novedadInfrastrucEJB.edit(novedadInfrastruc);
        }
        if (create && novedadInfrastruc.getIdGmoNovedadTipoDetInfrastruc().getNotifica().equals(1)
                && novedadInfrastruc.getIdGmoNovedadTipoDetInfrastruc().getEmails() != null) {
            notificar(novedadInfrastruc);
        }

        if (create) {
            nuevo();
        }

        archivos.clear();
        MovilidadUtil.addSuccessMessage("Se registro la informacion exitosamente.");
        MovilidadUtil.hideModal("nov_novedad_infra_wv");
        novedadInfrastruc = null;
        consultar();
    }

    public void nuevo() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        isEditing = false;
        i_idNovedaTipoDetallesInfraStruct = 0;
        i_idNovedadTipoInfraStruct = 0;
        simpleModel = new DefaultMapModel();
        latitud = "4.646189";
        logitud = "-74.078540";
        listNovTipoInfrastruc = novTipoInfrastrucEJB.findAll();
        lstRutas = prgRouteEJB.findByUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        lstRutasAfectadas = new ArrayList<>();
        novedadInfrastruc = new GmoNovedadInfrastruc();
        archivos = new ArrayList<>();
        novedadInfrastruc.setFechaHoraNov(MovilidadUtil.fechaCompletaHoy());
        novedadInfrastruc.setFechaHoraReg(MovilidadUtil.fechaCompletaHoy());
        MovilidadUtil.openModal("nov_novedad_infra_wv");
    }

    private void guardarRutasAfectadas() {

        List<GmoNovedadInfrastrucRutaAfectada> lista = new ArrayList<>();

        GmoNovedadInfrastrucRutaAfectada rutaAfectada;

        for (PrgRoute ruta : lstRutasAfectadas) {
            rutaAfectada = new GmoNovedadInfrastrucRutaAfectada();
            rutaAfectada.setIdPrgRoute(ruta);
            rutaAfectada.setIdGmoNovedadInfrastruc(novedadInfrastruc);
            rutaAfectada.setEstadoReg(0);
            rutaAfectada.setCreado(MovilidadUtil.fechaCompletaHoy());
            rutaAfectada.setUsername(user.getUsername());
            lista.add(rutaAfectada);
        }

        novedadInfrastruc.setGmoNovedadInfrastrucRutaAfectadaList(lista);

    }

    @Transactional
    private void actualizarRutasAfectadas() {

        List<GmoNovedadInfrastrucRutaAfectada> listadoRutasEditarAux = new ArrayList<>();
        listadoRutasEditarAux.addAll(listadoRutasEditar);

        for (GmoNovedadInfrastrucRutaAfectada rutaAfectada : listadoRutasEditarAux) {
            boolean aux_ok = true;
            for (PrgRoute item : lstRutasAfectadas) {
                if (rutaAfectada.getIdPrgRoute().getIdPrgRoute().equals(item.getIdPrgRoute())) {
                    aux_ok = false;
                }
                if (aux_ok) {
                    listadoRutasEditar.remove(rutaAfectada);
                }
            }
        }

        for (PrgRoute item : lstRutasAfectadas) {
            boolean ok = false;
            for (GmoNovedadInfrastrucRutaAfectada item2 : listadoRutasEditar) {
                if (item.getIdPrgRoute().equals(item2.getIdPrgRoute().getIdPrgRoute())) {
                    ok = true;
                }
            }
            if (!ok) {
                GmoNovedadInfrastrucRutaAfectada rutaAfectada = new GmoNovedadInfrastrucRutaAfectada();
                rutaAfectada.setIdPrgRoute(item);
                rutaAfectada.setIdGmoNovedadInfrastruc(novedadInfrastruc);
                rutaAfectada.setEstadoReg(0);
                rutaAfectada.setCreado(MovilidadUtil.fechaCompletaHoy());
                rutaAfectada.setUsername(user.getUsername());
                listadoRutasEditar.add(rutaAfectada);
            }
        }

        novedadInfrastruc.setGmoNovedadInfrastrucRutaAfectadaList(listadoRutasEditar);
    }

    private void cargarRutasSeleccionadas() {

        lstRutas = prgRouteEJB.findByUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        lstRutasAfectadas = new ArrayList<>();

        listadoRutasEditar = novedadInfrastruc.getGmoNovedadInfrastrucRutaAfectadaList();

        if (listadoRutasEditar != null) {
            for (GmoNovedadInfrastrucRutaAfectada item : listadoRutasEditar) {
                lstRutasAfectadas.add(item.getIdPrgRoute());
            }
        }

    }

    private void notificar(GmoNovedadInfrastruc novedad) throws IOException {
        List<String> adjuntos = null;
        Map mapa = getMailParams();

        if (mapa.get("template") == null) {
            MovilidadUtil.addErrorMessage("La plantilla de correo para Novedades infraestructura (GMO) NO se encuentra en la tabla de configuración");
            return;
        }

        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("fecha", Util.dateFormat(novedad.getFechaHoraNov()));
        mailProperties.put("tipo", novedad.getIdGmoNovedadTipoInfrastruc().getNombre());
        mailProperties.put("detalle", novedad.getIdGmoNovedadTipoDetInfrastruc().getNombre());
        mailProperties.put("direccion", novedad.getDireccion());
        mailProperties.put("latitud", novedad.getLatitud());
        mailProperties.put("longitud", novedad.getLongitud());
        mailProperties.put("estado", "Abierta");
        mailProperties.put("username", novedad.getUsername());
//        mailProperties.put("username", "");
        mailProperties.put("generada", Util.dateTimeFormat(novedad.getCreado()));
        mailProperties.put("descripcion", novedad.getDescripcion());
        String subject = "Novedad de Infraestructura";
        String destinatarios = novedad.getIdGmoNovedadTipoDetInfrastruc().getEmails();

        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", adjuntos != null ? adjuntos : null);
    }

    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(Util.TEMPLATE_NOVEDAD_INFRA_GMO);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    public List<GmoNovedadInfrastruc> getList() {
        return list;
    }

    public void setList(List<GmoNovedadInfrastruc> list) {
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

    public GmoNovedadInfrastruc getNovedadInfrastruc() {
        return novedadInfrastruc;
    }

    public void setNovedadInfrastruc(GmoNovedadInfrastruc novedadInfrastruc) {
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

    public List<GmoNovedadTipoInfrastruc> getListNovTipoInfrastruc() {
        return listNovTipoInfrastruc;
    }

    public void setListNovTipoInfrastruc(List<GmoNovedadTipoInfrastruc> listNovTipoInfrastruc) {
        this.listNovTipoInfrastruc = listNovTipoInfrastruc;
    }

    public List<GmoNovedadTipoDetallesInfrastruc> getListNovTipoDetInfrastruc() {
        return listNovTipoDetInfrastruc;
    }

    public void setListNovTipoDetInfrastruc(List<GmoNovedadTipoDetallesInfrastruc> listNovTipoDetInfrastruc) {
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

    public GmoNovedadTipoInfrastruc getNovTipoInfrastruc() {
        return novTipoInfrastruc;
    }

    public void setNovTipoInfrastruc(GmoNovedadTipoInfrastruc novTipoInfrastruc) {
        this.novTipoInfrastruc = novTipoInfrastruc;
    }

    public GmoNovedadTipoDetallesInfrastruc getNovTipoDetInfrastruc() {
        return novTipoDetInfrastruc;
    }

    public void setNovTipoDetInfrastruc(GmoNovedadTipoDetallesInfrastruc novTipoDetInfrastruc) {
        this.novTipoDetInfrastruc = novTipoDetInfrastruc;
    }

    public boolean isFlagListaUF() {
        return flagListaUF;
    }

    public void setFlagListaUF(boolean flagListaUF) {
        this.flagListaUF = flagListaUF;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLogitud() {
        return logitud;
    }

    public void setLogitud(String logitud) {
        this.logitud = logitud;
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public void setSimpleModel(MapModel simpleModel) {
        this.simpleModel = simpleModel;
    }

    public List<PrgRoute> getLstRutas() {
        return lstRutas;
    }

    public void setLstRutas(List<PrgRoute> lstRutas) {
        this.lstRutas = lstRutas;
    }

    public List<PrgRoute> getLstRutasAfectadas() {
        return lstRutasAfectadas;
    }

    public void setLstRutasAfectadas(List<PrgRoute> lstRutasAfectadas) {
        this.lstRutasAfectadas = lstRutasAfectadas;
    }

}
