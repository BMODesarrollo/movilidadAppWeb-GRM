/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.GenericaDocumentosFacadeLocal;
import com.movilidad.ejb.GenericaFacadeLocal;
import com.movilidad.ejb.GenericaPmGrupoDetalleFacadeLocal;
import com.movilidad.ejb.GenericaPmGrupoFacadeLocal;
import com.movilidad.ejb.GenericaSeguimientoFacadeLocal;
import com.movilidad.ejb.GenericaTipoDetallesFacadeLocal;
import com.movilidad.ejb.GenericaTipoDocumentosFacadeLocal;
import com.movilidad.ejb.GenericaTipoFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.Generica;
import com.movilidad.model.GenericaDocumentos;
import com.movilidad.model.GenericaPmGrupo;
import com.movilidad.model.GenericaPmGrupoDetalle;
import com.movilidad.model.GenericaSeguimiento;
import com.movilidad.model.GenericaTipo;
import com.movilidad.model.GenericaTipoDetalles;
import com.movilidad.model.GenericaTipoDocumentos;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.awt.Image;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Named(value = "genGruposNovMB")
@ViewScoped
public class GenericaGrupoNovedadMB implements Serializable {

    private GenericaTipo i_tipoNovedadObj;
    private GenericaTipoDetalles i_tipoNovedaDetObj;

    private List<GenericaTipo> listNovedadT;
    private List<GenericaTipoDetalles> listNovedadTDet;

    private List<GenericaPmGrupoDetalle> listPmGrupDet;
    private List<GenericaPmGrupo> listPmGrup;
    private List<GenericaDocumentos> lstDocumentos;
    private List<GenericaTipoDocumentos> lstNovedadTipoDocumentos;

    private List<String> fotosNovedades;
    private GenericaDocumentos selectedDocumento;
    private String nombreNovedaTipoDocu;

    private List<GenericaSeguimiento> lstSeguimientos;
    private GenericaSeguimiento selectedSeguimiento;
    private Generica selectedNovedad;

    private Generica novedad = new Generica();
    private List<Generica> listNovs;

    private GenericaPmGrupo pmGrup;
    private Date desde = new Date();
    private Date hasta = new Date();
    private Empleado lider;

    private List<UploadedFile> archivos;

    private int i_tipoNovedad = 0;
    private int i_tipoNovedaDet = 0;
    private int i_grupoPm = 0;

    private int height = 0;
    private int width = 0;
    private boolean flagSegui = false;
    private boolean b_controlAplica = false;

    int sinSeguimiento = 0;
    int conSeguimiento = 0;
    int conDocumentos = 0;
    int sinDocumentos = 0;
    int aplicadas = 0;
    int noAplicadas = 0;
    int totalPuntosAplicados = 0;
    int totalPuntosNoAplicados = 0;

    private ParamAreaUsr pau;

    private DonutChartModel donutModel;

    @EJB
    private GenericaTipoFacadeLocal novedadTEJB;

    @EJB
    private GenericaTipoDetallesFacadeLocal novedadTDetEJB;

    @EJB
    private GenericaFacadeLocal novEJB;

    @EJB
    private GenericaPmGrupoFacadeLocal pmGrupEJB;

    @EJB
    private EmpleadoFacadeLocal emplEJB;

    @EJB
    private GenericaPmGrupoDetalleFacadeLocal pmGrupDTEJB;

    @EJB
    private GenericaSeguimientoFacadeLocal novSeguiEjb;

    @EJB
    private GenericaTipoDocumentosFacadeLocal novedadTipoDocumentosEjb;

    @EJB
    private GenericaDocumentosFacadeLocal novedadDocumentosEjb;

    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;

    @Inject
    private ArchivosJSFManagedBean archivosBean;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of GruposNovedadJSFManagedBean
     */
    public GenericaGrupoNovedadMB() {
    }

    @PostConstruct
    public void init() {
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());

        donutModel = new DonutChartModel();
        this.fotosNovedades = new ArrayList<>();
        archivos = new ArrayList<>();
        validarRolAplica();
        grupoUser();
        createDonutModel();
    }

    public int validarRol() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_EMPLGEN")
                    || auth.getAuthority().equals("ROLE_MTTO")) {
                return 2;
            }
        }
        return 1;
    }

    public void findById() {
        for (GenericaTipo nt : listNovedadT) {
            if (nt.getIdGenericaTipo() == getI_tipoNovedad()) {
                i_tipoNovedadObj = nt;
                novedad.setIdGenericaTipo(i_tipoNovedadObj);
                listNovedadTDet = nt.getGenericaTipoDetallesList();
                break;
            }
        }
    }

    public void onRowDblClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof GenericaTipoDocumentos) {
            selectedDocumento.setIdGenericaTipoDocumento((GenericaTipoDocumentos) event.getObject());
            nombreNovedaTipoDocu = selectedDocumento.getIdGenericaTipoDocumento().getNombreTipoDocumento();
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
        selectedDocumento = new GenericaDocumentos();
        nombreNovedaTipoDocu = "";
        selectedDocumento.setIdGenerica(selectedNovedad);
    }

    public void findDetalle() {
        listPmGrupDet = new ArrayList<>();
        listNovs = new ArrayList<>();
        for (GenericaPmGrupo gpm : listPmGrup) {
            if (gpm.getIdGenericaPmGrupo() == getI_grupoPm()) {
                pmGrup = gpm;
                listPmGrupDet.addAll(gpm.getGenericaPmGrupoDetalleList());
                List<Generica> lista = novEJB.findByDateRangeAndIdEmpleado(desde, hasta, pmGrup.getIdEmpleado().getIdEmpleado());
                listNovs.addAll(lista);

                for (GenericaPmGrupoDetalle p : listPmGrupDet) {
                    List<Generica> listad = novEJB.findByDateRangeAndIdEmpleado(desde, hasta, p.getIdEmpleado().getIdEmpleado());
                    listNovs.addAll(listad);
                }
                break;
            }
        }
        Collections.sort(listNovs);
        createDonutModel();
    }

    public void masterGrupo() {
        for (GenericaPmGrupo gpm : listPmGrup) {
            if (gpm.getIdGenericaPmGrupo() == getI_grupoPm()) {
                pmGrup = gpm;
                break;
            }
        }
    }

//    public String tipologia(Empleado empl) {
//        if (empl == null) {
//            return "N/A";
//        }
//        String tipologia = "";
//        try {
//            tipologia = empl.getIdEmpleadoCargo().getNombreCargo();
//            if (empl.getIdEmpleadoCargo().getIdEmpleadoTipoCargo().equals(new Integer(28))) {
//                tipologia = "ART";
//            }
//            if (empl.getIdEmpleadoCargo().getIdEmpleadoTipoCargo().equals(new Integer(29))
//                    || empl.getIdEmpleadoCargo().getIdEmpleadoTipoCargo().equals(new Integer(30))) {
//                tipologia = "BIART";
//            }
//        } catch (Exception e) {
//            return "N/A";
//        }
//
//        return tipologia;
//    }
//    public List<Object> tipolofiaList() {
//        List<Object> aux_list = new ArrayList<>();
//        if (listNovs != null) {
//            for (Generica d : listNovs) {
//                aux_list.add(tipologia(d.getIdEmpleado()));
//            }
//            aux_list = aux_list.stream().distinct().collect(Collectors.toList());
//        }
//        return aux_list;
//    }
    public void aplicar(Generica nov, int op) {
        try {
            nov.setProcede(op);
            nov.setModificado(MovilidadUtil.fechaCompletaHoy());
            if (op == 0) {
                nov.setPuntosPmConciliados(0);
            } else {
                nov.setPuntosPmConciliados(nov.getPuntosPm());
            }
            novEJB.edit(nov);
            MovilidadUtil.addSuccessMessage("Acción realizada exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("No se realizó ningula acción");

        }
    }

    public void validarRolAplica() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_PROFGEN")
                    || auth.getAuthority().equals("ROLE_DIRGEN")
                    || auth.getAuthority().equals("ROLE_PROFMTTO")) {
                b_controlAplica = true;
            }
        }
    }

    public void guardarNovedadDocumento() {
        selectedDocumento.setUsuario(user.getUsername());
        selectedDocumento.setCreado(new Date());
        if (archivos.isEmpty()) {
            PrimeFaces.current().ajax().update("frmNuevoDocumento:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un documento a cargar");
            return;
        } else {
            int idRegistro = 0;
            boolean flagImagen = false;
            String path_documento = " ";
            String path_imagen = " ";
            for (UploadedFile f : archivos) {
                if (f.getContentType().contains("pdf")) {
                    this.novedadDocumentosEjb.create(selectedDocumento);
                    path_documento = Util.saveFile(f, selectedDocumento.getIdGenericaDocumento(), "generica_documento");
                    selectedDocumento.setPathDocumento(path_documento);
                    this.novedadDocumentosEjb.edit(selectedDocumento);
                }
                if (f.getContentType().contains("image")) {
                    if (!flagImagen) {
                        this.novedadDocumentosEjb.create(selectedDocumento);
                        idRegistro = selectedDocumento.getIdGenericaDocumento();
                    }
                    path_imagen = Util.saveFile(f, idRegistro, "generica_documento");
                    selectedDocumento.setPathDocumento(path_imagen);
                    this.novedadDocumentosEjb.edit(selectedDocumento);
                    flagImagen = true;
                }
            }
            archivos.clear();
        }
        this.lstDocumentos.add(selectedDocumento);
        if (selectedNovedad.getGenericaDocumentosList() == null) {
            selectedNovedad.setGenericaDocumentosList(lstDocumentos);
        } else {
            selectedNovedad.getGenericaDocumentosList().add(selectedDocumento);
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
                        path_documento = Util.saveFile(f, selectedDocumento.getIdGenericaDocumento(), "generica_documento");
                        selectedDocumento.setPathDocumento(path_documento);
                        this.novedadDocumentosEjb.edit(selectedDocumento);
                        flagPDF = true;
                    }
                    if (f.getContentType().contains("pdf") && flagPDF) {
                        path_documento = Util.saveFile(f, selectedDocumento.getIdGenericaDocumento(), "generica_documento");
                        selectedDocumento.setPathDocumento(path_documento);
                        this.novedadDocumentosEjb.create(selectedDocumento);
                        flagPDF = true;
                    }
                    if (f.getContentType().contains("image")) {
                        if (!flagImagen && flagPDF) {
                            this.novedadDocumentosEjb.create(selectedDocumento);
                            idRegistro = selectedDocumento.getIdGenericaDocumento();
                        }
                        path_imagen = Util.saveFile(f, idRegistro, "generica_documento");
                        selectedDocumento.setPathDocumento(path_imagen);
                        this.novedadDocumentosEjb.edit(selectedDocumento);
                        flagImagen = true;
                    }
                }

                if (f.getContentType().contains("image")) {
                    if (!flagImagen) {
                        this.novedadDocumentosEjb.create(selectedDocumento);
                        idRegistro = selectedDocumento.getIdGenericaDocumento();
                    }
                    path_imagen = Util.saveFile(f, idRegistro, "generica_documento");
                    selectedDocumento.setPathDocumento(path_imagen);
                    this.novedadDocumentosEjb.edit(selectedDocumento);
                    flagImagen = true;
                }
            }
        }
        PrimeFaces.current().executeScript("PF('novedadDocumentos').hide()");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Documento anexado a novedad éxitosamente."));
    }

    public void getDocumento(GenericaDocumentos nd) {
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
            archivosBean.setModalHeader(selectedDocumento.getIdGenericaTipoDocumento().getNombreTipoDocumento().toUpperCase());
            archivosBean.setPath(selectedDocumento.getPathDocumento());
        } else {
            try {
                obtenerFotosNovedad();
                archivosBean.setExtension(ext);
                archivosBean.setPath("");
                archivosBean.setGenericaDocumento(selectedDocumento);
                archivosBean.setModalHeader(selectedDocumento.getIdGenericaTipoDocumento().getNombreTipoDocumento().toUpperCase());
            } catch (IOException ex) {
                Logger.getLogger(GenericaGrupoNovedadMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void obtenerFotosNovedad() throws IOException {
        List<String> lstNombresImg = Util.getFileList(selectedDocumento.getIdGenericaDocumento(), "generica_documento");

        if (lstNombresImg != null) {
            width = 0;
            height = 0;
            for (String f : lstNombresImg) {
                fotosNovedades.add(f);
                System.out.println("IMG->>" + f);
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

    public int numeroNovedades(int idEmpleado) {
        return novEJB.findByDateRangeAndIdEmpleado(desde, hasta, idEmpleado).size();
    }

    public String color(int idEmpleado) {

        int i = novEJB.findByDateRangeAndIdEmpleado(desde, hasta, idEmpleado).size();
        if (i == 0) {
            return "alertBubbleGreen";
        }
        if (i > 0 && i < 3) {
            return "alertBubbleYellow";
        }
        if (i > 2) {
            return "alertBubbleRed";
        }
        return "";
    }

    public int numeroNovedadesSeguim(int idEmpleado) {
        return novEJB.findByDateRangeAndIdEmpleadoSeguim(desde, hasta, idEmpleado).size();
    }

    public int numeroNovedadesDocu(int idEmpleado) {
        return novEJB.findByDateRangeAndIdEmpleadoDocu(desde, hasta, idEmpleado).size();
    }

    public void seguimiento(Generica n) {
        lstSeguimientos = new ArrayList<>();
        lstSeguimientos.addAll(n.getGenericaSeguimientoList());
        selectedNovedad = n;
    }

    public String viewSeguimientos(Generica n) {
        String seguimientos = "";
        if (n.getGenericaSeguimientoList() != null) {
            if (!n.getGenericaSeguimientoList().isEmpty()) {
                for (GenericaSeguimiento ns : n.getGenericaSeguimientoList()) {
                    seguimientos = seguimientos + Util.dateFormat(ns.getFecha()) + " - " + ns.getSeguimiento() + " - " + ns.getUsername() + "\n";
                }
            }
        }
        return seguimientos;
    }

    public void documentos(Generica n) {
        fotosNovedades = new ArrayList<>();
        lstDocumentos = new ArrayList<>();
        lstDocumentos.addAll(n.getGenericaDocumentosList());
        selectedNovedad = n;

    }

    public void editarDocumento(GenericaDocumentos nd) {
        nombreNovedaTipoDocu = nd.getIdGenericaTipoDocumento().getNombreTipoDocumento();
        selectedNovedad = nd.getIdGenerica();
        selectedDocumento = nd;
    }

    public boolean validarEditarSeguimiento(String userName) {
        if (user.getUsername().equals(userName)) {
            return false;
        }
        for (GrantedAuthority g : user.getAuthorities()) {
            if (g.getAuthority().equals("ROLE_PROFGEN")
                    || g.getAuthority().equals("ROLE_DIRGEN")
                    || g.getAuthority().equals("ROLE_PROFMTTO")) {

                return false;
            }
        }
        return true;
    }

    public void nuevoseguimiento() {
        flagSegui = true;
        selectedSeguimiento = new GenericaSeguimiento();
        selectedSeguimiento.setIdGenerica(selectedNovedad);
    }

    public void editarSeguimiento(GenericaSeguimiento sn) {
        flagSegui = false;
        selectedSeguimiento = sn;
    }

    public void guardarSeguimiento() {

        selectedSeguimiento.setCreado(new Date());
        selectedSeguimiento.setUsername(user.getUsername());
        selectedSeguimiento.setEstadoReg(0);

        novSeguiEjb.create(selectedSeguimiento);

        if (selectedNovedad.getGenericaSeguimientoList() == null) {
            selectedNovedad.setGenericaSeguimientoList(new ArrayList<GenericaSeguimiento>());
            selectedNovedad.getGenericaSeguimientoList().add(selectedSeguimiento);
            lstSeguimientos.add(selectedSeguimiento);
        } else {
            selectedNovedad.getGenericaSeguimientoList().add(selectedSeguimiento);
            lstSeguimientos.add(selectedSeguimiento);

        }
        MovilidadUtil.addSuccessMessage("Seguimiento de novedad registrado éxitosamente.");
        PrimeFaces.current().executeScript("PF('seguiNewWV').hide()");

    }

    public void createDonutModel() {

        sinSeguimiento = 0;
        conSeguimiento = 0;
        conDocumentos = 0;
        sinDocumentos = 0;
        aplicadas = 0;
        noAplicadas = 0;
        totalPuntosAplicados = 0;
        totalPuntosNoAplicados = 0;

        if (listNovs != null) {
            for (Generica n : listNovs) {
                if (n.getGenericaDocumentosList() != null) {
                    if (n.getGenericaDocumentosList().size() > 0) {
                        conDocumentos++;
                    } else {
                        sinDocumentos++;
                    }
                }
                if (n.getGenericaSeguimientoList() != null) {
                    if (n.getGenericaSeguimientoList().size() > 0) {
                        conSeguimiento++;
                    } else {
                        sinSeguimiento++;
                    }
                }
                if (n.getProcede() == 1) {
                    aplicadas++;
                    totalPuntosAplicados = totalPuntosAplicados
                            + (n.getPuntosPmConciliados() == null ? 0 : n.getPuntosPmConciliados());
                } else {
                    noAplicadas++;
                    totalPuntosNoAplicados = totalPuntosNoAplicados
                            + (n.getPuntosPm() == null ? 0 : n.getPuntosPm());
                }
            }
        }
        donutModel = new DonutChartModel();
        ChartData data = new ChartData();

        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
//        values.add(conSeguimiento);
//        values.add(sinSeguimiento);
//        values.add(conDocumentos);
//        values.add(sinDocumentos);
//        values.add(aplicadas);
//        values.add(noAplicadas);
        values.add(totalPuntosAplicados);
        values.add(totalPuntosNoAplicados);
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
//        bgColors.add("rgb(54, 162, 235)");
//        bgColors.add("rgb(119, 136, 153)");
//        bgColors.add("rgb(238, 130, 238)");
//        bgColors.add("rgb(255, 255, 0)");
//        bgColors.add("rgb(255, 0, 0)");
//        bgColors.add("rgb(61, 129, 23)");

        bgColors.add("rgb(61, 129, 25)");
        bgColors.add("rgb(255, 0, 0)");

        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
//        labels.add("Con Seguimientos: " + conSeguimiento);
//        labels.add("Sin Seguimientos: " + sinSeguimiento);
//        labels.add("Con Documentos: " + conDocumentos);
//        labels.add("Sin Documentos: " + sinDocumentos);
//        labels.add("Aplicadas: " + aplicadas);
//        labels.add("No Aplicadas: " + noAplicadas);
        labels.add("Pts. Aplicados: " + totalPuntosAplicados);
        labels.add("Pts. no Aplicados: " + totalPuntosNoAplicados);
        data.setLabels(labels);

        donutModel.setData(data);
    }

    public void actualizarSeguimiento() {

        selectedSeguimiento.setModificado(new Date());
        selectedSeguimiento.setUsername(user.getUsername());
        novSeguiEjb.edit(selectedSeguimiento);

        MovilidadUtil.addSuccessMessage("Seguimiento de novedad Actualizó éxitosamente.");
        PrimeFaces.current().executeScript("PF('seguiNewWV').hide()");
    }

    public void novedadesView(int idEmpleado) {
        if (idEmpleado != 0) {
            listNovs.clear();
            listNovs = novEJB.findByDateRangeAndIdEmpleado(desde, hasta, idEmpleado);
        }
        createDonutModel();
        selectedNovedad = null;
        selectedSeguimiento = null;
    }

    public void setTipoNovedadDet() {
        if (i_tipoNovedaDet != 0) {
            i_tipoNovedaDetObj = novedadTDetEJB.find(i_tipoNovedaDet);
            novedad.setIdGenericaTipoDetalle(i_tipoNovedaDetObj);
        }
    }

    public GenericaTipo getI_tipoNovedadObj() {
        return i_tipoNovedadObj;
    }

    public void setI_tipoNovedadObj(GenericaTipo i_tipoNovedadObj) {
        this.i_tipoNovedadObj = i_tipoNovedadObj;
    }

    public GenericaTipoDetalles getI_tipoNovedaDetObj() {
        return i_tipoNovedaDetObj;
    }

    public void setI_tipoNovedaDetObj(GenericaTipoDetalles i_tipoNovedaDetObj) {
        this.i_tipoNovedaDetObj = i_tipoNovedaDetObj;
    }

    public List<GenericaTipo> getListNovedadT() {
        if (listNovedadT == null) {
            listNovedadT = novedadTEJB.obtenerTipos();
        }
        return listNovedadT;
    }

    public void setListNovedadT(List<GenericaTipo> listNovedadT) {
        this.listNovedadT = listNovedadT;
    }

    public List<GenericaTipoDetalles> getListNovedadTDet() {
        return listNovedadTDet;
    }

    public void setListNovedadTDet(List<GenericaTipoDetalles> listNovedadTDet) {
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

    public Generica getNovedad() {
        return novedad;
    }

    public void setNovedad(Generica novedad) {
        this.novedad = novedad;
    }

    public List<GenericaPmGrupoDetalle> getListPmGrupDet() {
        return listPmGrupDet;
    }

    public void setListPmGrupDet(List<GenericaPmGrupoDetalle> listPmGrupDet) {
        this.listPmGrupDet = listPmGrupDet;
    }

    public void grupoUser() {
        if (validarRol() == 1) {
            if (listPmGrup == null) {
                listPmGrup = pmGrupEJB.getByIdArea(pau.getIdParamArea().getIdParamArea());
            }
        } else if (validarRol() == 2) {
            lider = emplEJB.getEmpleadoByUsername(user.getUsername());
            if (lider != null) {
                listPmGrup = new ArrayList<>();
                if (lider.getGenericaPmGrupoList() != null && !lider.getGenericaPmGrupoList().isEmpty()) {
                    i_grupoPm = lider.getGenericaPmGrupoList().get(0).getIdGenericaPmGrupo();
                    listPmGrup.add(lider.getGenericaPmGrupoList().get(0));
                    findDetalle();
                }
            }
        }
    }

    public void cargarTDocu() {
        lstNovedadTipoDocumentos = novedadTipoDocumentosEjb.findByArea(pau.getIdParamArea().getIdParamArea());
        // System.out.println("TOTAL-->" + lstNovedadTipoDocumentos.size());
    }

    public List<GenericaPmGrupo> getListPmGrup() {
        return listPmGrup;
    }

    public void setListPmGrup(List<GenericaPmGrupo> listPmGrup) {
        this.listPmGrup = listPmGrup;
    }

    public GenericaPmGrupo getPmGrup() {
        return pmGrup;
    }

    public void setPmGrup(GenericaPmGrupo pmGrup) {
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

    public List<Generica> getListNovs() {
        return listNovs;
    }

    public void setListNovs(List<Generica> listNovs) {
        this.listNovs = listNovs;
    }

    public List<GenericaSeguimiento> getLstSeguimientos() {
        return lstSeguimientos;
    }

    public void setLstSeguimientos(List<GenericaSeguimiento> lstSeguimientos) {
        this.lstSeguimientos = lstSeguimientos;
    }

    public GenericaSeguimiento getSelectedSeguimiento() {
        return selectedSeguimiento;
    }

    public void setSelectedSeguimiento(GenericaSeguimiento selectedSeguimiento) {
        this.selectedSeguimiento = selectedSeguimiento;
    }

    public Generica getSelectedNovedad() {
        return selectedNovedad;
    }

    public void setSelectedNovedad(Generica selectedNovedad) {
        this.selectedNovedad = selectedNovedad;
    }

    public boolean isFlagSegui() {
        return flagSegui;
    }

    public void setFlagSegui(boolean flagSegui) {
        this.flagSegui = flagSegui;
    }

    public List<GenericaDocumentos> getLstDocumentos() {
        return lstDocumentos;
    }

    public void setLstDocumentos(List<GenericaDocumentos> lstDocumentos) {
        this.lstDocumentos = lstDocumentos;
    }

    public List<GenericaTipoDocumentos> getLstNovedadTipoDocumentos() {
        return lstNovedadTipoDocumentos;
    }

    public void setLstNovedadTipoDocumentos(List<GenericaTipoDocumentos> lstNovedadTipoDocumentos) {
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

    public GenericaDocumentos getSelectedDocumento() {
        return selectedDocumento;
    }

    public void setSelectedDocumento(GenericaDocumentos selectedDocumento) {
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

    
}
