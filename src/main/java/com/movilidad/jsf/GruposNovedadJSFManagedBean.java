/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NovedadDocumentosFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.NovedadSeguimientoFacadeLocal;
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.NovedadTipoDocumentosFacadeLocal;
import com.movilidad.ejb.NovedadTipoFacadeLocal;
import com.movilidad.ejb.PmGrupoDetalleFacadeLocal;
import com.movilidad.ejb.PmGrupoFacadeLocal;
import com.movilidad.model.AccPre;
import com.movilidad.model.Empleado;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadDocumentos;
import com.movilidad.model.NovedadSeguimiento;
import com.movilidad.model.NovedadTipo;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.model.NovedadTipoDocumentos;
import com.movilidad.model.PmGrupo;
import com.movilidad.model.PmGrupoDetalle;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.awt.Image;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "gruposNovSFMB")
@ViewScoped
public class GruposNovedadJSFManagedBean implements Serializable {

    private NovedadTipo i_tipoNovedadObj;
    private NovedadTipoDetalles i_tipoNovedaDetObj;

    private List<NovedadTipo> listNovedadT;
    private List<NovedadTipoDetalles> listNovedadTDet;

    private List<PmGrupoDetalle> listPmGrupDet;
    private List<PmGrupo> listPmGrup;
    private List<NovedadDocumentos> lstDocumentos;
    private List<NovedadTipoDocumentos> lstNovedadTipoDocumentos;

    private List<String> fotosNovedades;
    private NovedadDocumentos selectedDocumento;
    private String nombreNovedaTipoDocu;

    private List<NovedadSeguimiento> lstSeguimientos;
    private NovedadSeguimiento selectedSeguimiento;
    private Novedad selectedNovedad;

    private Novedad novedad = new Novedad();
    private List<Novedad> listNovs;

    private PmGrupo pmGrup;
    private Date desde = new Date();
    private Date hasta = new Date();
    private Empleado master;

    private List<UploadedFile> archivos;

    private int i_tipoNovedad = 0;
    private int i_tipoNovedaDet = 0;
    private int i_grupoPm = 0;
    private int i_empleado = 0;

    private boolean flagSegui = false;
    private boolean b_controlAplica = false;
    private boolean flag_dano = true;
    private boolean flag_nov = true;
    private boolean flag_info_master;

    private int height = 0;
    private int width = 0;
    private int sinSeguimiento = 0;
    private int conSeguimiento = 0;
    private int conDocumentos = 0;
    private int sinDocumentos = 0;
    private int aplicadas = 0;
    private int noAplicadas = 0;
    private int totalPuntosAplicadosArt = 0;
    private int totalPuntosAplicadosBiArt = 0;
    private int totalPuntosNoAplicadosArt = 0;
    private int totalPuntosNoAplicadosBiArt = 0;

    private DonutChartModel donutModel;
    private DonutChartModel donutModelTipologia;

    @EJB
    private NovedadTipoFacadeLocal novedadTEJB;
    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTDetEJB;
    @EJB
    private NovedadFacadeLocal novEJB;
    @EJB
    private PmGrupoFacadeLocal pmGrupEJB;
    @EJB
    private EmpleadoFacadeLocal emplEJB;
    @EJB
    private PmGrupoDetalleFacadeLocal pmGrupDTEJB;
    @EJB
    private NovedadSeguimientoFacadeLocal novSeguiEjb;
    @EJB
    private NovedadTipoDocumentosFacadeLocal novedadTipoDocumentosEjb;
    @EJB
    private NovedadDocumentosFacadeLocal novedadDocumentosEjb;

    @Inject
    private ArchivosJSFManagedBean archivosBean;
    @Inject
    private GestionDanoNovedadPuntoBean gestionDanoNovedadPuntoBean;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of GruposNovedadJSFManagedBean
     */
    public GruposNovedadJSFManagedBean() {
    }

    @PostConstruct
    public void init() {
        donutModel = new DonutChartModel();
        this.fotosNovedades = new ArrayList<>();
        archivos = new ArrayList<>();
        validarRolAplica();
        grupoUser();
        createDonutModel(false);

    }

    public void resetByUnidadFuncional() {
        grupoUser();
        createDonutModel(false);
        flag_info_master = false;
        i_empleado = 0;
        listPmGrupDet = null;
        listNovs = null;
        selectedNovedad = null;
        selectedSeguimiento = null;
        flag_dano = true;
        flag_nov = true;
    }

    public boolean validarRol() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_SENIOR")) {
                return true;
            }
        }
        return false;
    }

    public void findById() {
        for (NovedadTipo nt : listNovedadT) {
            if (nt.getIdNovedadTipo() == getI_tipoNovedad()) {
                i_tipoNovedadObj = nt;
                novedad.setIdNovedadTipo(i_tipoNovedadObj);
                listNovedadTDet = nt.getNovedadTipoDetallesList();
                break;
            }
        }
    }

    public void onRowDblClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof NovedadTipoDocumentos) {
            selectedDocumento.setIdNovedadTipoDocumento((NovedadTipoDocumentos) event.getObject());
            nombreNovedaTipoDocu = selectedDocumento.getIdNovedadTipoDocumento().getNombreTipoDocumento();
        }
        PrimeFaces.current().executeScript("PF('dtNovedadDocumento').clearFilters();");
        PrimeFaces.current().ajax().update(":frmNovedadTipoDocumentosList:dtNovedadDocumentos");
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    public void handleFileUpload(FileUploadEvent event) {
        PrimeFaces current = PrimeFaces.current();
        archivos.add(event.getFile());
        current.executeScript("PF('AddFilesListDialog').hide()");
        current.ajax().update(":frmNuevoDocumento:messages");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Documento agregado éxitosamente."));
    }

    public void nuevoDocu() {
        selectedDocumento = new NovedadDocumentos();
        nombreNovedaTipoDocu = "";
        selectedDocumento.setIdNovedad(selectedNovedad);
    }

    public void findDetalle() {
        flag_info_master = false;
        i_empleado = 0;
        listPmGrupDet = new ArrayList<>();
        listNovs = new ArrayList<>();
        selectedNovedad = null;
        selectedSeguimiento = null;
        flag_dano = true;
        flag_nov = true;
        for (PmGrupo gpm : listPmGrup) {
            if (gpm.getIdPmGrupo() == getI_grupoPm()) {
                pmGrup = gpm;
                listPmGrupDet.addAll(pmGrupDTEJB.findByIdPmGrupo(pmGrup.getIdPmGrupo()));
                List<Novedad> lista = novEJB.findByDateRangeAndIdEmpleadoAndIdGopUnidadFunc(desde,
                        hasta, pmGrup.getIdEmpleado().getIdEmpleado(),
                        unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), pmGrup.getIdPmGrupo());
                listNovs.addAll(lista);

//                for (PmGrupoDetalle p : listPmGrupDet) {
//                    List<Novedad> listad = novEJB.findByDateRangeAndIdEmpleado(desde, hasta, p.getIdEmpleado().getIdEmpleado());
//                    listNovs.addAll(listad);
//                }
                break;
            }
        }
        Collections.sort(listNovs);
        createDonutModel(false);
    }

    public void masterGrupo() {
        for (PmGrupo gpm : listPmGrup) {
            if (gpm.getIdPmGrupo() == getI_grupoPm()) {
                pmGrup = gpm;
                break;
            }
        }
    }

    public void updateInfo() {
        if (i_empleado == 0) {
            findDetalle();
        } else {
            novedadesView(i_empleado, flagSegui);
        }
        flag_dano = true;
        flag_nov = true;

    }

    public void editDano() {
        gestionDanoNovedadPuntoBean.setNovedadDano(selectedNovedad.getIdNovedadDano());
        gestionDanoNovedadPuntoBean.editNovedadDano();
        MovilidadUtil.openModal("gestion_dano_wv");
    }

    public String tipologia(Empleado empl) {
        if (empl == null) {
            return "N/A";
        }
        String tipologia = "";
        try {
            tipologia = empl.getIdEmpleadoCargo().getNombreCargo();
            if (empl.getIdEmpleadoCargo().getIdEmpleadoTipoCargo().equals(new Integer(28))) {
                tipologia = "ART";
            }
            if (empl.getIdEmpleadoCargo().getIdEmpleadoTipoCargo().equals(new Integer(29))
                    || empl.getIdEmpleadoCargo().getIdEmpleadoTipoCargo().equals(new Integer(30))) {
                tipologia = "BIART";
            }
        } catch (Exception e) {
            return "N/A";
        }

        return tipologia;
    }

    public List<Object> tipolofiaList() {
        List<Object> aux_list = new ArrayList<>();
        if (listNovs != null) {
            for (Novedad d : listNovs) {
                aux_list.add(tipologia(d.getIdEmpleado()));
            }
            aux_list = aux_list.stream().distinct().collect(Collectors.toList());
        }
        return aux_list;
    }

    public void aplicar(int op) {
        if (selectedNovedad == null) {
            MovilidadUtil.addErrorMessage("No se ha seleccionado una novedad.");
            return;
        }
        selectedNovedad.setProcede(op);
        selectedNovedad.setModificado(MovilidadUtil.fechaCompletaHoy());
        if (op == 0) {
            selectedNovedad.setPuntosPmConciliados(0);
        } else {
            selectedNovedad.setPuntosPmConciliados(selectedNovedad.getPuntosPm());
        }
        novEJB.edit(selectedNovedad);
        MovilidadUtil.addSuccessMessage("Acción realizada exitosamente");
    }

    public void validarRolAplica() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_PROFOP")) {
                b_controlAplica = true;
            }
        }
    }

    public void guardarNovedadDocumento() {
        selectedDocumento.setUsuario(user.getUsername());
        selectedDocumento.setCreado(new Date());
        if (selectedDocumento.getIdNovedadTipoDocumento() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo documento");
            return;
        }
        if (archivos.isEmpty()) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un documento a cargar");
            return;
        }
        int idRegistro = 0;
        boolean flagImagen = false;
        String path_documento = " ";
        String path_imagen = " ";
        for (UploadedFile f : archivos) {
            if (f.getContentType().contains("pdf")) {
                this.novedadDocumentosEjb.create(selectedDocumento);
                path_documento = Util.saveFile(f, selectedDocumento.getIdNovedadDocumento(), "novedad_documento");
                selectedDocumento.setPathDocumento(path_documento);
                this.novedadDocumentosEjb.edit(selectedDocumento);
            }
            if (f.getContentType().contains("image")) {
                if (!flagImagen) {
                    this.novedadDocumentosEjb.create(selectedDocumento);
                    idRegistro = selectedDocumento.getIdNovedadDocumento();
                }
                path_imagen = Util.saveFile(f, idRegistro, "novedad_documento");
                selectedDocumento.setPathDocumento(path_imagen);
                this.novedadDocumentosEjb.edit(selectedDocumento);
                flagImagen = true;
            }
        }
        archivos.clear();
        this.lstDocumentos.add(selectedDocumento);
        if (selectedNovedad.getNovedadDocumentosList() == null) {
            selectedNovedad.setNovedadDocumentosList(lstDocumentos);
        } else {
            selectedNovedad.getNovedadDocumentosList().add(selectedDocumento);
        }
        PrimeFaces.current().executeScript("PF('novedadDocumentos').hide()");

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Documento anexado a novedad éxitosamente."));
    }

    public void actualizarNovedadDocumento() {
        selectedDocumento.setUsuario(user.getUsername());
        selectedDocumento.setCreado(new Date());
        if (archivos.isEmpty()) {
            PrimeFaces.current().ajax().update("frmNuevoDocumento:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un documento a cargar");
            return;
        } else {
            int idRegistro = 0;
            boolean flagImagen = false;
            boolean flagPDF = false;
            String path_documento = " ";
            String path_imagen = " ";
            if (selectedDocumento.getPathDocumento().contains("pdf")) {
                Util.deleteFile(selectedDocumento.getPathDocumento());
            }
            for (UploadedFile f : archivos) {
                if (selectedDocumento.getPathDocumento().contains("pdf")) {
                    if (f.getContentType().contains("pdf") && !flagPDF && !flagImagen) {
                        path_documento = Util.saveFile(f, selectedDocumento.getIdNovedadDocumento(), "novedad_documento");
                        selectedDocumento.setPathDocumento(path_documento);
                        this.novedadDocumentosEjb.edit(selectedDocumento);
                        flagPDF = true;
                    }
                    if (f.getContentType().contains("pdf") && flagPDF) {
                        path_documento = Util.saveFile(f, selectedDocumento.getIdNovedadDocumento(), "novedad_documento");
                        selectedDocumento.setPathDocumento(path_documento);
                        this.novedadDocumentosEjb.create(selectedDocumento);
                        flagPDF = true;
                    }
                    if (f.getContentType().contains("image")) {
                        if (!flagImagen && flagPDF) {
                            this.novedadDocumentosEjb.create(selectedDocumento);
                            idRegistro = selectedDocumento.getIdNovedadDocumento();
                        }
                        path_imagen = Util.saveFile(f, idRegistro, "novedad_documento");
                        selectedDocumento.setPathDocumento(path_imagen);
                        this.novedadDocumentosEjb.edit(selectedDocumento);
                        flagImagen = true;
                    }
                }

                if (f.getContentType().contains("image")) {
                    if (!flagImagen) {
                        this.novedadDocumentosEjb.create(selectedDocumento);
                        idRegistro = selectedDocumento.getIdNovedadDocumento();
                    }
                    path_imagen = Util.saveFile(f, idRegistro, "novedad_documento");
                    selectedDocumento.setPathDocumento(path_imagen);
                    this.novedadDocumentosEjb.edit(selectedDocumento);
                    flagImagen = true;
                }
            }
        }
        PrimeFaces.current().executeScript("PF('novedadDocumentos').hide()");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Documento anexado a novedad éxitosamente."));
    }

    public void getDocumento(NovedadDocumentos nd) {
        selectedDocumento = nd;
        String ext = "";
        fotosNovedades.clear();
        width = 800;
        height = 600;
        if (selectedDocumento.getPathDocumento().contains("pdf")) {
            ext = selectedDocumento.getPathDocumento().substring(selectedDocumento.getPathDocumento().lastIndexOf('.'), selectedDocumento.getPathDocumento().length());
        }
        if (ext.contains("pdf")) { // Si el path del documento es un pdf
            archivosBean.setExtension(ext);
            archivosBean.setModalHeader(selectedDocumento.getIdNovedadTipoDocumento().getNombreTipoDocumento().toUpperCase());
            archivosBean.setPath(selectedDocumento.getPathDocumento());
        } else {
            try {
                obtenerFotosNovedad();
                archivosBean.setExtension(ext);
                archivosBean.setPath("");
                archivosBean.setSelectedDocumento(selectedDocumento);
                archivosBean.setModalHeader(selectedDocumento.getIdNovedadTipoDocumento().getNombreTipoDocumento().toUpperCase());
            } catch (IOException ex) {
                Logger.getLogger(NovedadJSFManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void obtenerFotosNovedad() throws IOException {
        List<String> lstNombresImg = Util.getFileList(selectedDocumento.getIdNovedadDocumento(), "novedad_documentos");

        if (lstNombresImg != null) {
            width = 0;
            height = 0;
            for (String f : lstNombresImg) {
                fotosNovedades.add(f);
                Image i = Util.mostrarImagenN(f, selectedDocumento.getPathDocumento());
                if (i != null) {
                    if (width < i.getWidth(null)) {
                        width = i.getWidth(null);
                    }
                    if (height < i.getHeight(null)) {
                        height = i.getHeight(null);
                    }
                }
            }
        }
    }

    public int numeroNovedades(int idEmpleado, boolean procede) {
        return novEJB.findByDateRangeAndIdEmpleadoSenior(desde, hasta, idEmpleado,procede).size();
    }

    public String color(int idEmpleado, boolean procede) {

        int i = novEJB.findByDateRangeAndIdEmpleadoSenior(desde, hasta, idEmpleado,procede).size();
        if (i == 0) {
            return "alertBubbleGreen";
        }
        if (i > 0 && i <= 3) {
            return "alertBubbleYellow";
        }
        if (i > 3) {
            return "alertBubbleRed";
        }
        return "";
    }

    public int numeroNovedadesSeguim(int idEmpleado) {
        return novEJB.findByDateRangeAndIdEmpleadoSeguimSenior(desde, hasta, idEmpleado).size();
    }

    public int numeroNovedadesDocu(int idEmpleado) {
        return novEJB.findByDateRangeAndIdEmpleadoDocuSenior(desde, hasta, idEmpleado).size();
    }

    public void seguimiento() {
        lstSeguimientos = new ArrayList<>();
        lstSeguimientos.addAll(selectedNovedad.getNovedadSeguimientoList());
    }

    public void onRowSelect(SelectEvent event) throws ParseException {
        selectedNovedad = (Novedad) event.getObject();
        flag_dano = !selectedNovedad.getIdNovedadTipo().getIdNovedadTipo().equals(Util.ID_NOVEDAD_DANO);
        flag_nov = !flag_dano;

        if (selectedNovedad.getAccPreList() != null) {
            for (AccPre obj : selectedNovedad.getAccPreList()) {
                if (obj.getEstado() == 1) {
                    MovilidadUtil.addAdvertenciaMessage("Esta novedad no puede ser modificada, ya que, fue aprobada por Seguridad Vial");
                    flag_nov = true;
                    break;
                }
            }
        }
    }

    /**
     * Método resposnable de hacer null la variable selectedNovedad luego de
     * desseleccionar la fila previamente seleccionada en la tabla de la vista
     * grupoPMNovedades.
     *
     * Para desseleccionar un registro de la table se debe presionar la tecla
     * Ctrl+Clic sobre le registro.
     */
    public void onRowUnselect() {
        selectedNovedad = null;
        flag_dano = true;
        flag_dano = true;
    }

    public String viewSeguimientos(Novedad n) {
        String seguimientos = "";
        if (n.getNovedadSeguimientoList() != null) {
            if (!n.getNovedadSeguimientoList().isEmpty()) {
                for (NovedadSeguimiento ns : n.getNovedadSeguimientoList()) {
                    seguimientos = seguimientos + Util.dateFormat(ns.getFecha()) + " - " + ns.getSeguimiento() + " - " + ns.getUsername() + "\n";
                }
            }
        }
        return seguimientos;
    }

    public void documentos() {
        fotosNovedades = new ArrayList<>();
        lstDocumentos = new ArrayList<>();
        lstDocumentos.addAll(selectedNovedad.getNovedadDocumentosList());

    }

    public void editarDocumento(NovedadDocumentos nd) {
        nombreNovedaTipoDocu = nd.getIdNovedadTipoDocumento().getNombreTipoDocumento();
        selectedNovedad = nd.getIdNovedad();
        selectedDocumento = nd;
    }

    public boolean validarEditarSeguimiento(String userName) {
        if (user.getUsername().equals(userName)) {
            return false;
        }
        for (GrantedAuthority g : user.getAuthorities()) {
            if (g.getAuthority().equals("ROLE_PROFOP")) {
                return false;
            }
        }
        return true;
    }

    public void nuevoseguimiento() {
        flagSegui = true;
        selectedSeguimiento = new NovedadSeguimiento();
        selectedSeguimiento.setIdNovedad(selectedNovedad);
    }

    public void editarSeguimiento(NovedadSeguimiento sn) {
        flagSegui = false;
        selectedSeguimiento = sn;
    }

    public void guardarSeguimiento() {

        if (selectedSeguimiento.getFecha() == null) {
            MovilidadUtil.addErrorMessage("Seleccione la fecha.");
            return;
        }

        selectedSeguimiento.setCreado(new Date());
        selectedSeguimiento.setUsername(user.getUsername());
        selectedSeguimiento.setEstadoReg(0);

        novSeguiEjb.create(selectedSeguimiento);

        if (selectedNovedad.getNovedadSeguimientoList() == null) {
            selectedNovedad.setNovedadSeguimientoList(new ArrayList<NovedadSeguimiento>());
            selectedNovedad.getNovedadSeguimientoList().add(selectedSeguimiento);
            lstSeguimientos.add(selectedSeguimiento);
        } else {
            selectedNovedad.getNovedadSeguimientoList().add(selectedSeguimiento);
            lstSeguimientos.add(selectedSeguimiento);

        }
        MovilidadUtil.addSuccessMessage("Seguimiento de novedad registrado éxitosamente.");
        PrimeFaces.current().executeScript("PF('seguiNewWV').hide()");

    }

    public void createDonutModel(boolean infoMaster) {
        if (!b_controlAplica) {
            return;
        }
        sinSeguimiento = 0;
        conSeguimiento = 0;
        conDocumentos = 0;
        sinDocumentos = 0;
        aplicadas = 0;
        noAplicadas = 0;
        totalPuntosAplicadosArt = 0;
        totalPuntosAplicadosBiArt = 0;
        totalPuntosNoAplicadosArt = 0;
        totalPuntosNoAplicadosBiArt = 0;

        if (listNovs != null) {
            for (Novedad n : listNovs) {
                boolean flag_nov_master
                        = pmGrup.getIdEmpleado().getIdEmpleado()
                                .equals(n.getIdEmpleado().getIdEmpleado());
                if (infoMaster) {
                    flag_nov_master = false;
                }
                if (!flag_nov_master) {
                    if (n.getNovedadDocumentosList() != null) {
                        if (n.getNovedadDocumentosList().size() > 0) {
                            conDocumentos++;
                        } else {
                            sinDocumentos++;
                        }
                    }
                    if (n.getNovedadSeguimientoList() != null) {
                        if (n.getNovedadSeguimientoList().size() > 0) {
                            conSeguimiento++;
                        } else {
                            sinSeguimiento++;
                        }
                    }
                    if (n.getProcede() == 1) {
                        aplicadas++;
                        if (n.getIdEmpleado().getIdEmpleadoCargo()
                                .getIdEmpleadoTipoCargo()
                                .equals(ConstantsUtil.ID_TPC_ART)) {
                            totalPuntosAplicadosArt = totalPuntosAplicadosArt
                                    + (n.getPuntosPmConciliados() == null ? 0 : n.getPuntosPmConciliados());
                        } else if (n.getIdEmpleado().getIdEmpleadoCargo()
                                .getIdEmpleadoTipoCargo()
                                .equals(ConstantsUtil.ID_TPC_BIART)) {
                            totalPuntosAplicadosBiArt = totalPuntosAplicadosBiArt
                                    + (n.getPuntosPmConciliados() == null ? 0 : n.getPuntosPmConciliados());
                        }
                    } else {
                        noAplicadas++;
                        if (n.getIdEmpleado().getIdEmpleadoCargo()
                                .getIdEmpleadoTipoCargo()
                                .equals(ConstantsUtil.ID_TPC_ART)) {
                            totalPuntosNoAplicadosArt = totalPuntosNoAplicadosArt
                                    + (n.getPuntosPm() == null ? 0 : n.getPuntosPm());
                        } else if (n.getIdEmpleado().getIdEmpleadoCargo()
                                .getIdEmpleadoTipoCargo()
                                .equals(ConstantsUtil.ID_TPC_BIART)) {
                            totalPuntosNoAplicadosBiArt = totalPuntosNoAplicadosBiArt
                                    + (n.getPuntosPm() == null ? 0 : n.getPuntosPm());
                        }
                    }
                }
            }
        }
        donutModel = new DonutChartModel();
        ChartData data = new ChartData();

        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();

        values.add(totalPuntosAplicadosArt + totalPuntosAplicadosBiArt);
        values.add(totalPuntosNoAplicadosArt + totalPuntosNoAplicadosBiArt);

        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(61, 129, 25)");
        bgColors.add("rgb(255, 0, 0)");

        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();

        labels.add("Pts. Aplicados: " + (totalPuntosAplicadosArt + totalPuntosAplicadosBiArt));
        labels.add("Pts. no Aplicados: " + (totalPuntosNoAplicadosArt + totalPuntosNoAplicadosBiArt));
        data.setLabels(labels);

        donutModel.setData(data);
    }

    public void actualizarSeguimiento() {
        if (selectedSeguimiento.getFecha() == null) {
            MovilidadUtil.addErrorMessage("Seleccione la fecha.");
            return;
        }
        selectedSeguimiento.setModificado(new Date());
        selectedSeguimiento.setUsername(user.getUsername());
        novSeguiEjb.edit(selectedSeguimiento);

        MovilidadUtil.addSuccessMessage("Seguimiento de novedad Actualizó éxitosamente.");
        PrimeFaces.current().executeScript("PF('seguiNewWV').hide()");
    }

    public void novedadesView(int idEmpleado, boolean infoMaster) {
        flag_info_master = infoMaster;
        i_empleado = idEmpleado;
        if (idEmpleado != 0) {
            listNovs.clear();
            listNovs = novEJB.findByDateRangeAndIdEmpleadoSenior(desde, hasta, idEmpleado,false);
        }
        createDonutModel(flag_info_master);
        selectedNovedad = null;
        selectedSeguimiento = null;
    }

    public void setTipoNovedadDet() {
        if (i_tipoNovedaDet != 0) {
            i_tipoNovedaDetObj = novedadTDetEJB.find(i_tipoNovedaDet);
            novedad.setIdNovedadTipoDetalle(i_tipoNovedaDetObj);
        }
    }

    public void editNovedad() {
        gestionDanoNovedadPuntoBean.setNovedad(selectedNovedad);
        gestionDanoNovedadPuntoBean.editNovedad();
        MovilidadUtil.openModal("novedadesPM");
    }

    public void cambiarPuntos() {
        gestionDanoNovedadPuntoBean.setNovedad(selectedNovedad);
        MovilidadUtil.openModal("cambiar_puntos_wv");
        MovilidadUtil.updateComponent("cambiar_puntos_form");
    }

    public NovedadTipo getI_tipoNovedadObj() {
        return i_tipoNovedadObj;
    }

    public void setI_tipoNovedadObj(NovedadTipo i_tipoNovedadObj) {
        this.i_tipoNovedadObj = i_tipoNovedadObj;
    }

    public NovedadTipoDetalles getI_tipoNovedaDetObj() {
        return i_tipoNovedaDetObj;
    }

    public void setI_tipoNovedaDetObj(NovedadTipoDetalles i_tipoNovedaDetObj) {
        this.i_tipoNovedaDetObj = i_tipoNovedaDetObj;
    }

    public List<NovedadTipo> getListNovedadT() {
        if (listNovedadT == null) {
            listNovedadT = novedadTEJB.obtenerTipos();
        }
        return listNovedadT;
    }

    public void setListNovedadT(List<NovedadTipo> listNovedadT) {
        this.listNovedadT = listNovedadT;
    }

    public List<NovedadTipoDetalles> getListNovedadTDet() {
        return listNovedadTDet;
    }

    public void setListNovedadTDet(List<NovedadTipoDetalles> listNovedadTDet) {
        this.listNovedadTDet = listNovedadTDet;
    }

    public int getI_tipoNovedad() {
        return i_tipoNovedad;
    }

    public void setI_tipoNovedad(int i_tipoNovedad) {
        this.i_tipoNovedad = i_tipoNovedad;
    }

    public int getI_tipoNovedaDet() {
        return i_tipoNovedaDet;
    }

    public void setI_tipoNovedaDet(int i_tipoNovedaDet) {
        this.i_tipoNovedaDet = i_tipoNovedaDet;
    }

    public Novedad getNovedad() {
        return novedad;
    }

    public void setNovedad(Novedad novedad) {
        this.novedad = novedad;
    }

    public List<PmGrupoDetalle> getListPmGrupDet() {
        return listPmGrupDet;
    }

    public void setListPmGrupDet(List<PmGrupoDetalle> listPmGrupDet) {
        this.listPmGrupDet = listPmGrupDet;
    }

    public void grupoUser() {
        if (!validarRol()) {
            listPmGrup = pmGrupEJB.findAllByUnidadFuncional(
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        } else if (validarRol()) {
            master = emplEJB.getEmpleadoByUsername(user.getUsername());
            if (master != null) {
                listPmGrup = new ArrayList<>();
                if (master.getPmGrupoList() != null) {
                    i_grupoPm = master.getPmGrupoList().get(0).getIdPmGrupo();
                    listPmGrup.add(master.getPmGrupoList().get(0));
                    findDetalle();
                }
            }
        }
    }

    public void cargarTDocu() {
        lstNovedadTipoDocumentos = novedadTipoDocumentosEjb.findAll();
        // System.out.println("TOTAL-->" + lstNovedadTipoDocumentos.size());
    }

    public List<PmGrupo> getListPmGrup() {
        return listPmGrup;
    }

    public void setListPmGrup(List<PmGrupo> listPmGrup) {
        this.listPmGrup = listPmGrup;
    }

    public PmGrupo getPmGrup() {
        return pmGrup;
    }

    public void setPmGrup(PmGrupo pmGrup) {
        this.pmGrup = pmGrup;
    }

    public int getI_grupoPm() {
        return i_grupoPm;
    }

    public void setI_grupoPm(int i_grupoPm) {
        this.i_grupoPm = i_grupoPm;
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

    public List<Novedad> getListNovs() {
        return listNovs;
    }

    public void setListNovs(List<Novedad> listNovs) {
        this.listNovs = listNovs;
    }

    public List<NovedadSeguimiento> getLstSeguimientos() {
        return lstSeguimientos;
    }

    public void setLstSeguimientos(List<NovedadSeguimiento> lstSeguimientos) {
        this.lstSeguimientos = lstSeguimientos;
    }

    public NovedadSeguimiento getSelectedSeguimiento() {
        return selectedSeguimiento;
    }

    public void setSelectedSeguimiento(NovedadSeguimiento selectedSeguimiento) {
        this.selectedSeguimiento = selectedSeguimiento;
    }

    public Novedad getSelectedNovedad() {
        return selectedNovedad;
    }

    public void setSelectedNovedad(Novedad selectedNovedad) {
        this.selectedNovedad = selectedNovedad;
    }

    public boolean isFlagSegui() {
        return flagSegui;
    }

    public void setFlagSegui(boolean flagSegui) {
        this.flagSegui = flagSegui;
    }

    public List<NovedadDocumentos> getLstDocumentos() {
        return lstDocumentos;
    }

    public void setLstDocumentos(List<NovedadDocumentos> lstDocumentos) {
        this.lstDocumentos = lstDocumentos;
    }

    public List<NovedadTipoDocumentos> getLstNovedadTipoDocumentos() {
        return lstNovedadTipoDocumentos;
    }

    public void setLstNovedadTipoDocumentos(List<NovedadTipoDocumentos> lstNovedadTipoDocumentos) {
        this.lstNovedadTipoDocumentos = lstNovedadTipoDocumentos;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public NovedadDocumentos getSelectedDocumento() {
        return selectedDocumento;
    }

    public void setSelectedDocumento(NovedadDocumentos selectedDocumento) {
        this.selectedDocumento = selectedDocumento;
    }

    public String getNombreNovedaTipoDocu() {
        return nombreNovedaTipoDocu;
    }

    public void setNombreNovedaTipoDocu(String nombreNovedaTipoDocu) {
        this.nombreNovedaTipoDocu = nombreNovedaTipoDocu;
    }

    public List<String> getFotosNovedades() {
        return fotosNovedades;
    }

    public void setFotosNovedades(List<String> fotosNovedades) {
        this.fotosNovedades = fotosNovedades;
    }

    public boolean isB_controlAplica() {
        return b_controlAplica;
    }

    public void setB_controlAplica(boolean b_controlAplica) {
        this.b_controlAplica = b_controlAplica;
    }

    public DonutChartModel getDonutModel() {
        return donutModel;
    }

    public void setDonutModel(DonutChartModel donutModel) {
        this.donutModel = donutModel;
    }

    public DonutChartModel getDonutModelTipologia() {
        return donutModelTipologia;
    }

    public void setDonutModelTipologia(DonutChartModel donutModelTipologia) {
        this.donutModelTipologia = donutModelTipologia;
    }

    public int getSinSeguimiento() {
        return sinSeguimiento;
    }

    public void setSinSeguimiento(int sinSeguimiento) {
        this.sinSeguimiento = sinSeguimiento;
    }

    public int getConSeguimiento() {
        return conSeguimiento;
    }

    public void setConSeguimiento(int conSeguimiento) {
        this.conSeguimiento = conSeguimiento;
    }

    public int getConDocumentos() {
        return conDocumentos;
    }

    public void setConDocumentos(int conDocumentos) {
        this.conDocumentos = conDocumentos;
    }

    public int getSinDocumentos() {
        return sinDocumentos;
    }

    public void setSinDocumentos(int sinDocumentos) {
        this.sinDocumentos = sinDocumentos;
    }

    public int getAplicadas() {
        return aplicadas;
    }

    public void setAplicadas(int aplicadas) {
        this.aplicadas = aplicadas;
    }

    public int getNoAplicadas() {
        return noAplicadas;
    }

    public void setNoAplicadas(int noAplicadas) {
        this.noAplicadas = noAplicadas;
    }

    public boolean isFlag_dano() {
        return flag_dano;
    }

    public void setFlag_dano(boolean flag_dano) {
        this.flag_dano = flag_dano;
    }

    public boolean isFlag_nov() {
        return flag_nov;
    }

    public void setFlag_nov(boolean flag_nov) {
        this.flag_nov = flag_nov;
    }

    public int getTotalPuntosAplicadosArt() {
        return totalPuntosAplicadosArt;
    }

    public void setTotalPuntosAplicadosArt(int totalPuntosAplicadosArt) {
        this.totalPuntosAplicadosArt = totalPuntosAplicadosArt;
    }

    public int getTotalPuntosAplicadosBiArt() {
        return totalPuntosAplicadosBiArt;
    }

    public void setTotalPuntosAplicadosBiArt(int totalPuntosAplicadosBiArt) {
        this.totalPuntosAplicadosBiArt = totalPuntosAplicadosBiArt;
    }

    public int getTotalPuntosNoAplicadosArt() {
        return totalPuntosNoAplicadosArt;
    }

    public void setTotalPuntosNoAplicadosArt(int totalPuntosNoAplicadosArt) {
        this.totalPuntosNoAplicadosArt = totalPuntosNoAplicadosArt;
    }

    public int getTotalPuntosNoAplicadosBiArt() {
        return totalPuntosNoAplicadosBiArt;
    }

    public void setTotalPuntosNoAplicadosBiArt(int totalPuntosNoAplicadosBiArt) {
        this.totalPuntosNoAplicadosBiArt = totalPuntosNoAplicadosBiArt;
    }

}
