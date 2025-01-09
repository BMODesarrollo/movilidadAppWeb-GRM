/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableCabinaFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.NovedadCabFacadeLocal;
import com.movilidad.ejb.NovedadTipoCabFacadeLocal;
import com.movilidad.model.CableCabina;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.NovedadCab;
import com.movilidad.model.NovedadTipoCab;
import com.movilidad.model.NovedadTipoDetallesCab;
import com.movilidad.security.UserExtended;
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
@Named(value = "aseoCabinaNovJSFMB")
@ViewScoped
public class AseoCabinaNovJSFMB implements Serializable {

    /**
     * Creates a new instance of AseoCabinaNovJSFMB
     */
    public AseoCabinaNovJSFMB() {
    }

    @EJB
    private NovedadCabFacadeLocal NovedadCabEJB;

    @Inject
    private AseoLoginJSF aseoJSF;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private NovedadTipoCabFacadeLocal novedadTipoCabEjb;
    @EJB
    private CableCabinaFacadeLocal cableCabinaEJB;
    @EJB
    private NovedadCabFacadeLocal novedadCabEJB;

    @Inject
    private UploadFotoJSFManagedBean fotoJSFManagedBean;
    private Date hasta = MovilidadUtil.fechaHoy();
    private Date desde = MovilidadUtil.fechaHoy();
    private List<NovedadCab> list;
    private NovedadCab novedadCab;
    private List<String> listFotos = new ArrayList<>();
    private NovedadCab novedad = new NovedadCab();
    private int i_idNovedadTipoCab;
    private int i_cabina;
    private NovedadTipoDetallesCab novedadTipoDetallesCab;

    private NovedadTipoCab novedadTipoCab;
    private int i_idNovedaTipoDetallesCab;

    private List<NovedadTipoCab> listNovedadTipoCab;
    private List<NovedadTipoDetallesCab> listNovedadTipoDetallesCab;
    private List<UploadedFile> archivosNov;
    private List<CableCabina> listCableCabina;
    private List<String> listFotospath_fotos = new ArrayList<>();
    private List<String> lista_fotos_remover;

    UserExtended user;

    @PostConstruct
    public void init() {
        consultar();
        getUser();
    }

    /**
     * Obtener valor de usuario en sesión
     */
    public void getUser() {
        try {
            user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            user = null;
        }
    }

    public void listaCabinas() {
        listCableCabina = cableCabinaEJB.findAllByEstadoRegAndNombreOrderBy("ASC");
    }

    public void listarTipoNovedad() {
        listNovedadTipoCab = novedadTipoCabEjb.findAllByEstadoReg();

    }

    public void obtenerFotos() throws IOException {
        this.listFotos.clear();
        List<String> lstNombresImg = Util.getFileList(novedadCab.getIdNovedadCab(), "novedadCab");

        for (String f : lstNombresImg) {
            f = novedadCab.getPathFoto() + f;
            listFotos.add(f);
        }
        fotoJSFManagedBean.setListFotos(listFotos);
    }

    public void obtenerFotosAseoCabina(NovedadCab nov) throws IOException {
        this.listFotos.clear();
        List<String> lstNombresImg = Util.getFileList(nov.getAseoCabinaNovedadList().get(0).getAseoCabina().getIdAseoCabina(), "aseoCabina");

        for (String f : lstNombresImg) {
            f = nov.getAseoCabinaNovedadList().get(0).getAseoCabina().getPathFotos() + f;
            listFotos.add(f);
        }
        fotoJSFManagedBean.setListFotos(listFotos);
    }

    public String returnPathFotosAseoCabina(NovedadCab nov) {
        if (nov.getAseoCabinaNovedadList() != null && !nov.getAseoCabinaNovedadList().isEmpty()) {
            String path = nov.getAseoCabinaNovedadList().get(0).getAseoCabina().getPathFotos();
            if (path != null && !path.equals("/")) {
                return path;
            }
        }
        return "";
    }

    /**
     * prepara la lista de listNovedadTipoDetallesCab despues de selccionar un
     * tipo de novedad. Carga tambien los objetos novedadTipoDetallesCab y
     * novedadTipoCab.
     *
     * @param opc
     */
    public void prepareTipoDetalles(boolean opc) {
        if (opc) {
            novedadTipoCab = null;
            for (NovedadTipoCab gt : listNovedadTipoCab) {
                if (gt.getIdNovedadTipoCab().equals(i_idNovedadTipoCab)) {
                    novedadTipoCab = gt;
                    listNovedadTipoDetallesCab = gt.getNovedadTipoDetallesCabList();
                    break;
                }
            }
        } else {
            novedadTipoDetallesCab = null;
            for (NovedadTipoDetallesCab gtd : novedadTipoCab.getNovedadTipoDetallesCabList()) {
                if (gtd.getIdNovedadTipoDetCab().equals(i_idNovedaTipoDetallesCab)) {
                    novedadTipoDetallesCab = gtd;
                    break;
                }
            }
        }
    }

    /**
     * Agrega en la lista lista_fotos_remover que luego va a ser elimnada y
     * removerlas de las listas listFotospath_fotos o listFotospath_planilla
     * Segun el caso.
     *
     * @param url
     */
    public void delete(String url) {
        lista_fotos_remover.add(url);
        listFotospath_fotos.remove(url);
    }

    /**
     * Agregar las iomagenes de novedades en la lista archivos, para
     * posteriormente ser alamcenadas
     *
     * @param event
     */
    public void handleFileUploadFotosNovedad(FileUploadEvent event) {
        archivosNov.add(event.getFile());
    }

    /**
     * Agregar una nueva novedad a la lista tempóral listAseoCabinaNov.
     *
     * @throws ParseException
     */
    public void guardar() throws ParseException, IOException {
        if (i_cabina == 0) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar una cabina");
            return;
        }
        if (novedadTipoCab == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar el tipo de novedad");
            return;
        }
        if (novedadTipoDetallesCab == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar el tipo de novedad detalles");
            return;
        }
        if (MovilidadUtil.fechasIgualMenorMayor(novedad.getFechaHora(), MovilidadUtil.fechaHoy(), false) == 2) {
            MovilidadUtil.addErrorMessage("La fecha no debe ser posterior a la fecha actual.");
            return;
        }
        novedad.setIdNovedadTipoCab(novedadTipoCab);
        novedad.setIdNovedadTipoDetCab(novedadTipoDetallesCab);
        novedad.setUsername(user != null ? user.getUsername() : aseoJSF.getResponsable().getNumeroDocumento());
        novedad.setIdCableCabina(cableCabinaEJB.find(i_cabina));

        /**
         * Variable para saber si se esta creando un nuevo registro o editando
         */
        boolean nuevo = false;
        if (novedad.getIdNovedadCab() == null) {
            novedad.setCreado(MovilidadUtil.fechaCompletaHoy());
            novedad.setEstadoReg(0);
            novedad.setEstadoNov(0);
            novedadCabEJB.create(novedad);
            nuevo = true;
        } else {
            /**
             * elimina fotos.
             */
            for (String url : lista_fotos_remover) {
                MovilidadUtil.eliminarFichero(url);
            }
            novedad.setModificado(MovilidadUtil.fechaCompletaHoy());
            novedadCabEJB.edit(novedad);
        }

        /**
         * Validar si hay imagenes para el registro de novedad para guardar
         */
        if (!archivosNov.isEmpty()) {
            String path = novedad.getPathFoto() == null ? "/" : novedad.getPathFoto();
            for (UploadedFile f : archivosNov) {
                path = Util.saveFile(f, novedad.getIdNovedadCab(), "novedadCab");
            }
            novedad.setPathFoto(path);
            this.novedadCabEJB.edit(novedad);
            archivosNov.clear();
        }
        if (nuevo) {
            if (novedad.getIdNovedadTipoDetCab().getNotifica().equals(1)
                    && novedad.getIdNovedadTipoDetCab().getEmails() != null) {
                notificar(novedad);
            }
        }
        prepareCreateEdit();

        consultar();
        MovilidadUtil.hideModal("nov_dialog_wv");
        MovilidadUtil.addSuccessMessage("Acción realizada con exito.");
    }

    public void consultar() {
        list = NovedadCabEJB.findEstadoReg(desde, hasta);
    }

    public void estadoNov(NovedadCab param, int opc) {
        param.setEstadoNov(opc);
        param.setUsernameEstado(user != null ? user.getUsername() : aseoJSF.getResponsable().getNumeroDocumento());
        param.setFechaEstado(MovilidadUtil.fechaCompletaHoy());
        novedadCabEJB.edit(param);
        param = novedadCabEJB.find(param.getIdNovedadCab());
        MovilidadUtil.addSuccessMessage("Acción completada exitosamente.");
    }

    public void editar(NovedadCab param) throws IOException {
        novedad = param;
        archivosNov = new ArrayList<>();
        lista_fotos_remover = new ArrayList<>();
        listarTipoNovedad();
        listaCabinas();
        i_cabina = novedad.getIdCableCabina().getIdCableCabina();
        i_idNovedadTipoCab = novedad.getIdNovedadTipoCab().getIdNovedadTipoCab();
        i_idNovedaTipoDetallesCab = novedad.getIdNovedadTipoDetCab().getIdNovedadTipoDetCab();
        listFotospath_fotos = obtenerFotosReturn();
        prepareTipoDetalles(true);
        prepareTipoDetalles(false);
        MovilidadUtil.openModal("nov_dialog_wv");
    }

    /**
     * obtener los parametros utilizados para enviar la notificaicon via correo
     *
     * @return
     */
    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.find(Util.ID_NOVEDAD_CABINA);
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
     * Responsable de notificar via correo la novedad indicada.
     *
     * @param novedad
     * @throws IOException
     */
    private void notificar(NovedadCab novedad) throws IOException {
        List<String> adjuntos;
        this.novedad = novedad;
        adjuntos = obtenerFotosReturn();
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("fecha", Util.dateFormat(novedad.getFechaHora()));
        mailProperties.put("cabina", novedad.getIdCableCabina().getNombre());
        mailProperties.put("tipo", novedad.getIdNovedadTipoCab().getNombre());
        mailProperties.put("detalle", novedad.getIdNovedadTipoDetCab().getNombre());
        mailProperties.put("username", novedad.getUsername());
        mailProperties.put("generada", Util.dateTimeFormat(novedad.getCreado()));
        mailProperties.put("observacion", novedad.getObservacion());
        String subject = "Novedad Cabina " + novedad.getIdCableCabina().getNombre();
        String destinatarios = novedad.getIdNovedadTipoDetCab().getEmails();

        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", adjuntos != null ? adjuntos : null);
    }

    /**
     * Obtener lista de las rutas de cada una de las imagenes por registro
     *
     * @return retorna la lista de las rutas obtenidas
     * @throws IOException
     */
    public List<String> obtenerFotosReturn() throws IOException {
        List<String> listFotos = new ArrayList<>();
        List<String> lstNombresImg;
        String path;
        lstNombresImg = Util.getFileList(novedad.getIdNovedadCab(), "novedadCab");
        path = novedad.getPathFoto();
        for (String f : lstNombresImg) {
            f = path + f;
            listFotos.add(f);
        }
        return listFotos;

    }

    public void nuevo() {
        novedad = new NovedadCab();
        novedad.setFechaHora(MovilidadUtil.fechaCompletaHoy());
        MovilidadUtil.openModal("nov_dialog_wv");
        prepareCreateEdit();
        archivosNov = new ArrayList<>();
        listarTipoNovedad();
        listaCabinas();
    }

    private void prepareCreateEdit() {
        i_idNovedadTipoCab = 0;
        i_idNovedaTipoDetallesCab = 0;
        i_cabina = 0;
        novedadTipoCab = null;
        novedadTipoDetallesCab = null;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public List<NovedadCab> getList() {
        return list;
    }

    public void setList(List<NovedadCab> list) {
        this.list = list;
    }

    public List<String> getListFotos() {
        return listFotos;
    }

    public void setListFotos(List<String> listFotos) {
        this.listFotos = listFotos;
    }

    public NovedadCab getNovedadCab() {
        return novedadCab;
    }

    public void setNovedadCab(NovedadCab novedadCab) {
        this.novedadCab = novedadCab;
    }

    public NovedadCab getNovedad() {
        return novedad;
    }

    public void setNovedad(NovedadCab novedad) {
        this.novedad = novedad;
    }

    public int getI_idNovedadTipoCab() {
        return i_idNovedadTipoCab;
    }

    public void setI_idNovedadTipoCab(int i_idNovedadTipoCab) {
        this.i_idNovedadTipoCab = i_idNovedadTipoCab;
    }

    public NovedadTipoDetallesCab getNovedadTipoDetallesCab() {
        return novedadTipoDetallesCab;
    }

    public void setNovedadTipoDetallesCab(NovedadTipoDetallesCab novedadTipoDetallesCab) {
        this.novedadTipoDetallesCab = novedadTipoDetallesCab;
    }

    public NovedadTipoCab getNovedadTipoCab() {
        return novedadTipoCab;
    }

    public void setNovedadTipoCab(NovedadTipoCab novedadTipoCab) {
        this.novedadTipoCab = novedadTipoCab;
    }

    public int getI_idNovedaTipoDetallesCab() {
        return i_idNovedaTipoDetallesCab;
    }

    public void setI_idNovedaTipoDetallesCab(int i_idNovedaTipoDetallesCab) {
        this.i_idNovedaTipoDetallesCab = i_idNovedaTipoDetallesCab;
    }

    public List<NovedadTipoCab> getListNovedadTipoCab() {
        return listNovedadTipoCab;
    }

    public void setListNovedadTipoCab(List<NovedadTipoCab> listNovedadTipoCab) {
        this.listNovedadTipoCab = listNovedadTipoCab;
    }

    public List<NovedadTipoDetallesCab> getListNovedadTipoDetallesCab() {
        return listNovedadTipoDetallesCab;
    }

    public void setListNovedadTipoDetallesCab(List<NovedadTipoDetallesCab> listNovedadTipoDetallesCab) {
        this.listNovedadTipoDetallesCab = listNovedadTipoDetallesCab;
    }

    public List<CableCabina> getListCableCabina() {
        return listCableCabina;
    }

    public void setListCableCabina(List<CableCabina> listCableCabina) {
        this.listCableCabina = listCableCabina;
    }

    public int getI_cabina() {
        return i_cabina;
    }

    public void setI_cabina(int i_cabina) {
        this.i_cabina = i_cabina;
    }

    public List<String> getListFotospath_fotos() {
        return listFotospath_fotos;
    }

    public void setListFotospath_fotos(List<String> listFotospath_fotos) {
        this.listFotospath_fotos = listFotospath_fotos;
    }

}
