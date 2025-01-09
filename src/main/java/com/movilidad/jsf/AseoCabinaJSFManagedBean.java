/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AseoCabinaFacadeLocal;
import com.movilidad.ejb.AseoCabinaNovedadFacadeLocal;
import com.movilidad.ejb.AseoCabinaTipoFacadeLocal;
import com.movilidad.ejb.CableCabinaFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.NovedadCabFacadeLocal;
import com.movilidad.ejb.NovedadTipoCabFacadeLocal;
import com.movilidad.model.AseoCabina;
import com.movilidad.model.AseoCabinaNovedad;
import com.movilidad.model.AseoCabinaTipo;
import com.movilidad.model.CableCabina;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.NovedadCab;
import com.movilidad.model.NovedadTipoCab;
import com.movilidad.model.NovedadTipoDetallesCab;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.AseoCabinaNovedadArchivo;
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
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "aseoCabinaJSFMB")
@ViewScoped
public class AseoCabinaJSFManagedBean implements Serializable {

    /**
     * Creates a new instance of AseoCabinaJSFManagedBean
     */
    public AseoCabinaJSFManagedBean() {
    }
    @EJB
    private CableCabinaFacadeLocal cableCabinaEJB;
    @EJB
    private AseoCabinaTipoFacadeLocal aseoCabinaTipoEJB;
    @EJB
    private AseoCabinaFacadeLocal aseoCabinaEJB;
    @EJB
    private NovedadTipoCabFacadeLocal novedadTipoCabEjb;
    @EJB
    private NovedadCabFacadeLocal novedadCabEjb;
    @EJB
    private AseoCabinaNovedadFacadeLocal aseoCabinaNovedadEJB;
    @Inject
    private AseoLoginJSF aseoJSF;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;

    private Date fecha = MovilidadUtil.fechaHoy();
    private Date fecha_hora;
    private AseoCabina aseoCabina;
    private NovedadCab novedad;
    private AseoCabinaNovedad aseoCabinaNovedad;
    private AseoCabinaTipo aseoCabinaTipo;
    private List<AseoCabina> listAseoCabina;
    private List<CableCabina> listCableCabina;
    private List<AseoCabinaTipo> listAseoCabinaTipo;
    private List<UploadedFile> archivos;
    private List<UploadedFile> archivosNov;
    private List<AseoCabinaNovedadArchivo> listAseoCabinaNov;
    private List<NovedadTipoCab> listNovedadTipoCab;
    private List<NovedadTipoDetallesCab> listNovedadTipoDetallesCab;
    private NovedadTipoDetallesCab novedadTipoDetallesCab;
    private NovedadTipoCab novedadTipoCab;
    private int i_idNovedadTipoCab;
    private int i_idNovedaTipoDetallesCab;
    private int i_aseoCabinaTipo;
    private int filtroV;
    private ParamAreaUsr pau;

    UserExtended user;

    @PostConstruct
    public void init() {
        consultar(0);
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

    /**
     * Consultar cabinas (Todas, lavadas, no lavadas)
     */
    public void consultar(int opc) {
        if (opc == 0) {
            listCableCabina = cableCabinaEJB.findAllByEstadoRegAndNombreOrderBy("ASC");
        } else if (opc == 1) {
            listCableCabina = cableCabinaEJB.findByLavadas(fecha, "ASC");
        }
        if (opc == 2) {
            listCableCabina = cableCabinaEJB.findAllByEstadoRegAndNombreOrderBy("ASC");
            List<CableCabina> listaAux = new ArrayList<>();
            listaAux.addAll(listCableCabina);
            for (CableCabina cc : listaAux) {
                AseoCabina ac = aseoCabinaEJB.findLastByIdAndFecha(cc.getIdCableCabina(), fecha);
                if (ac != null) {
                    listCableCabina.remove(cc);
                }
            }
        }
    }

    /**
     * Preparar variables para un nuevo registro
     */
    public void registrarLavado(CableCabina cabina) {
        aseoCabinaTipo = new AseoCabinaTipo();
        aseoCabina = new AseoCabina();
        aseoCabina.setCableCabina(cabina);
        aseoCabina.setFechaHora(MovilidadUtil.fechaCompletaHoy());
        listAseoCabinaTipo = aseoCabinaTipoEJB.findAllByEstadoReg();
        archivos = new ArrayList<>();
        listAseoCabinaNov = new ArrayList<>();
        i_aseoCabinaTipo = 1;
        selectAseoCabinaTipo();
        MovilidadUtil.openModal("create_aseo_cabina_wv");
    }

    /**
     * Preparar el metodo consultar para obtener las cabinas segun el radio
     * button selecionado en vista
     */
    public void filtrar() {
        if (filtroV == 0) {
            consultar(0);
            consultar(filtroV);
        } else {
            consultar(filtroV);
        }
    }

    /**
     * Agregar las iomagenes en la lista archivos, para posteriormente ser
     * alamcenadas
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        archivos.add(event.getFile());
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
     * igualar a null la variable aseoCabinaTipo, metodo invocado luego de hacer
     * Ctrl+clic sobre el un registro en la vista principal
     *
     */
    public void onRowUnselect() {
        aseoCabinaTipo = null;
    }

    /**
     * persistir en base de datos cada uno de los registros de aseo cabina con
     * el atributo de estadoReg en 2, esto quiere decir que el registro no se va
     * a visualizar conmo registro de aseo cabina activo
     */
    public void limpiarTodosAseoCabina() {
        aseoCabinaEJB.limpiarTodoAseoCabinaByFecha(fecha);
        MovilidadUtil.addSuccessMessage("Se finalizó tarea con exito.");
    }

    public void borrarArchivosCargados() {
        archivos.clear();
    }

    /**
     * Cargar objeto a aseoCabinaTipo luego de selcionarlo desde la vista.
     */
    public void selectAseoCabinaTipo() {
        aseoCabinaTipo = null;
        for (AseoCabinaTipo obj : listAseoCabinaTipo) {
            if (obj.getIdAseoCabinaTipo().equals(i_aseoCabinaTipo)) {
                aseoCabinaTipo = obj;
                break;
            }
        }
    }

    /**
     * Metodo responsable de persistir en base de datos un nuevo registro de
     * aseo cabina
     *
     * @throws IOException
     */
    public void guardarAseoCabina() throws IOException {
        if (validar()) {
            return;
        }
        aseoCabinaEJB.limpiarAseoCabinaByIdAseoCabinaAndFecha(aseoCabina.getCableCabina().getIdCableCabina(), aseoCabina.getFechaHora());
        aseoCabina.setCreado(MovilidadUtil.fechaCompletaHoy());
        aseoCabina.setUsername(user != null ? user.getUsername() : aseoJSF.getResponsable().getNumeroDocumento());
        aseoCabina.setEstadoReg(0);
        aseoCabina.setAseoCabinaTipo(aseoCabinaTipo);
        aseoCabinaEJB.create(aseoCabina);
        /**
         * Validar si hay imagenes para el registro de aseo cabina para guardar
         */
        if (!archivos.isEmpty()) {
            String path = " ";
            for (UploadedFile f : archivos) {
                path = Util.saveFile(f, aseoCabina.getIdAseoCabina(), "aseoCabina");
            }
            aseoCabina.setPathFotos(path);
            this.aseoCabinaEJB.edit(aseoCabina);
            archivos.clear();
        }
        /**
         * Validar si hay novedades para persistir
         */
        if (!listAseoCabinaNov.isEmpty()) {
            for (AseoCabinaNovedadArchivo obj : listAseoCabinaNov) {
                AseoCabinaNovedad acNovedad = obj.getAseoCabinaNovedad();
                acNovedad.setCreado(MovilidadUtil.fechaCompletaHoy());
                acNovedad.setEstadoReg(0);
                acNovedad.setUsername(user != null ? user.getUsername() : aseoJSF.getResponsable().getNumeroDocumento());
                acNovedad.setAseoCabina(aseoCabina);
                acNovedad.getNovedadCab().setCreado(MovilidadUtil.fechaCompletaHoy());
                acNovedad.getNovedadCab().setUsername(acNovedad.getUsername());
                acNovedad.getNovedadCab().setEstadoReg(0);
                acNovedad.getNovedadCab().setPathFoto("/");
                acNovedad.getNovedadCab().setIdCableCabina(aseoCabina.getCableCabina());
                aseoCabinaNovedadEJB.create(acNovedad);
                /**
                 * Validar si hay imagenes para el registro de novedad para
                 * guardar
                 */
                if (!obj.getArchivos().isEmpty()) {
                    String path = " ";
                    for (UploadedFile f : obj.getArchivos()) {
                        path = Util.saveFile(f, acNovedad.getNovedadCab().getIdNovedadCab(), "novedadCab");
                    }
                    acNovedad.getNovedadCab().setPathFoto(path);
                    novedadCabEjb.edit(acNovedad.getNovedadCab());
                }
                if (acNovedad.getNovedadCab().getIdNovedadTipoDetCab().getNotifica().equals(1)
                        && acNovedad.getNovedadCab().getIdNovedadTipoDetCab().getEmails() != null) {
                    notificar(acNovedad.getNovedadCab());
                }
            }
        }
        listAseoCabinaNov.clear();
        MovilidadUtil.addSuccessMessage("Se registro la informacion exitosamente.");
        MovilidadUtil.hideModal("create_aseo_cabina_wv");
        aseoCabina = null;
        aseoCabinaTipo = null;
    }

    /**
     * Validar que las variables en cuestion esten listas para persistir.
     *
     * @return True si sale algo mal, False si todo esta bien.
     */
    private boolean validar() {
        if (i_aseoCabinaTipo == 0) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar el tipo de lavado.");
            return true;
        }
        if (aseoCabina.getFechaHora() == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar la fecha y hora de la actividad");
            return true;
        }
        return false;

    }

    /**
     * Eliminar una novedad de la lista temporal a la hora de persistir un nuevo
     * registro o al modificar.
     *
     * @param aseoCA
     */
    public void eliminarNovedad(AseoCabinaNovedadArchivo aseoCA) {
        listAseoCabinaNov.remove(aseoCA);
        MovilidadUtil.addSuccessMessage("Novedad eliminada");
    }

    /**
     * Agregar una nueva novedad a la lista tempóral listAseoCabinaNov.
     *
     * @throws ParseException
     */
    public void agregarNovedad() throws ParseException {
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
        AseoCabinaNovedad acn = new AseoCabinaNovedad();
        AseoCabinaNovedadArchivo acna = new AseoCabinaNovedadArchivo();
        novedad.setIdNovedadTipoCab(novedadTipoCab);
        novedad.setIdNovedadTipoDetCab(novedadTipoDetallesCab);
        acn.setNovedadCab(novedad);
        acn.setUsername(user != null ? user.getUsername() : aseoJSF.getResponsable().getNumeroDocumento());
        acna.setAseoCabinaNovedad(acn);
        acna.setArchivos(archivosNov);
        listAseoCabinaNov.add(acna);
        i_idNovedadTipoCab = 0;
        i_idNovedaTipoDetallesCab = 0;
        novedadTipoCab = null;
        novedadTipoDetallesCab = null;
        MovilidadUtil.hideModal("nov_dialog_wv");
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
     * Prepara las variables para agregar una nueva novedad.
     */
    public void preAgregarNov() {
        listNovedadTipoCab = novedadTipoCabEjb.findAllByEstadoReg();
        novedad = new NovedadCab();
        novedad.setEstadoNov(0);
        archivosNov = new ArrayList<>();
        novedad.setFechaHora(MovilidadUtil.fechaCompletaHoy());
        MovilidadUtil.openModal("nov_dialog_wv");
    }

    /**
     * Metodo responsable de consulatar un registro de aseo cabina y alterar la
     * ubicacion que ocupa en la vista, cambia el fondo a verde
     *
     * @param cabina
     * @return returna el nombre del tipo de aseo cabina
     */
    public String tipoLavado(CableCabina cabina) {
        AseoCabina ac = aseoCabinaEJB.findLastByIdAndFecha(cabina.getIdCableCabina(), fecha);
        String id = "form_aseo_cabina:dg_cabina_id:" + listCableCabina.indexOf(cabina) + ":cb";
        if (ac != null) {
            PrimeFaces.current().executeScript("document.getElementById('" + id + "').style.backgroundColor = 'green';");
            return ac.getAseoCabinaTipo().getNombre();
        }
        return "-";
    }

    public String color(CableCabina cabina) {
        return "greenBG";
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
        String subject = "Novedad Aseo Cabina " + novedad.getIdCableCabina().getNombre();
        String destinatarios = novedad.getIdNovedadTipoDetCab().getEmails();

        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", adjuntos != null ? adjuntos : null);
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

    public NovedadTipoCabFacadeLocal getNovedadTipoCabEjb() {
        return novedadTipoCabEjb;
    }

    public void setNovedadTipoCabEjb(NovedadTipoCabFacadeLocal novedadTipoCabEjb) {
        this.novedadTipoCabEjb = novedadTipoCabEjb;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(Date fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public AseoCabina getAseoCabina() {
        return aseoCabina;
    }

    public void setAseoCabina(AseoCabina aseoCabina) {
        this.aseoCabina = aseoCabina;
    }

    public NovedadCab getNovedad() {
        return novedad;
    }

    public void setNovedad(NovedadCab novedad) {
        this.novedad = novedad;
    }

    public AseoCabinaTipo getAseoCabinaTipo() {
        return aseoCabinaTipo;
    }

    public void setAseoCabinaTipo(AseoCabinaTipo aseoCabinaTipo) {
        this.aseoCabinaTipo = aseoCabinaTipo;
    }

    public List<AseoCabina> getListAseoCabina() {
        return listAseoCabina;
    }

    public void setListAseoCabina(List<AseoCabina> listAseoCabina) {
        this.listAseoCabina = listAseoCabina;
    }

    public List<CableCabina> getListCableCabina() {
        return listCableCabina;
    }

    public void setListCableCabina(List<CableCabina> listCableCabina) {
        this.listCableCabina = listCableCabina;
    }

    public List<AseoCabinaTipo> getListAseoCabinaTipo() {
        return listAseoCabinaTipo;
    }

    public void setListAseoCabinaTipo(List<AseoCabinaTipo> listAseoCabinaTipo) {
        this.listAseoCabinaTipo = listAseoCabinaTipo;
    }

    public List<UploadedFile> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<UploadedFile> archivos) {
        this.archivos = archivos;
    }

    public List<UploadedFile> getArchivosNov() {
        return archivosNov;
    }

    public void setArchivosNov(List<UploadedFile> archivosNov) {
        this.archivosNov = archivosNov;
    }

    public List<AseoCabinaNovedadArchivo> getListAseoCabinaNov() {
        return listAseoCabinaNov;
    }

    public void setListAseoCabinaNov(List<AseoCabinaNovedadArchivo> listAseoCabinaNov) {
        this.listAseoCabinaNov = listAseoCabinaNov;
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

    public int getI_idNovedadTipoCab() {
        return i_idNovedadTipoCab;
    }

    public void setI_idNovedadTipoCab(int i_idNovedadTipoCab) {
        this.i_idNovedadTipoCab = i_idNovedadTipoCab;
    }

    public int getI_idNovedaTipoDetallesCab() {
        return i_idNovedaTipoDetallesCab;
    }

    public void setI_idNovedaTipoDetallesCab(int i_idNovedaTipoDetallesCab) {
        this.i_idNovedaTipoDetallesCab = i_idNovedaTipoDetallesCab;
    }

    public int getFiltroV() {
        return filtroV;
    }

    public void setFiltroV(int filtroV) {
        this.filtroV = filtroV;
    }

    public int getI_aseoCabinaTipo() {
        return i_aseoCabinaTipo;
    }

    public void setI_aseoCabinaTipo(int i_aseoCabinaTipo) {
        this.i_aseoCabinaTipo = i_aseoCabinaTipo;
    }

}
