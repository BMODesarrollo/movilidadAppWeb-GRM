package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.ejb.IncapacidadDxFacadeLocal;
import com.movilidad.ejb.IncapacidadOrdenaFacadeLocal;
import com.movilidad.ejb.IncapacidadTipoFacadeLocal;
import com.movilidad.ejb.NovedadDocumentosFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.NovedadIncapacidadFacadeLocal;
import com.movilidad.ejb.NovedadTipoDocumentosFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.IncapacidadDx;
import com.movilidad.model.IncapacidadOrdena;
import com.movilidad.model.IncapacidadTipo;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadDocumentos;
import com.movilidad.model.NovedadIncapacidad;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.ReporteIncapacidades;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "ausentismosBean")
@ViewScoped
public class AusentismosManagedBean implements Serializable {

    @EJB
    private NovedadFacadeLocal novedadEjb;
    @EJB
    private NovedadIncapacidadFacadeLocal novedadIncapacidadEjb;
    @EJB
    private NovedadDocumentosFacadeLocal novedadDocumentosEjb;
    @EJB
    private NovedadTipoDocumentosFacadeLocal novedadTipoDocumentosEjb;
    @EJB
    private IncapacidadOrdenaFacadeLocal incapacidadOrdenaEjb;
    @EJB
    private IncapacidadDxFacadeLocal incapacidadDxEjb;
    @EJB
    private IncapacidadTipoFacadeLocal incapacidadTipoEjb;
    @EJB
    private GopUnidadFuncionalFacadeLocal unidadFuncionalEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private ArchivosJSFManagedBean archivosBean;
    @Inject
    private NovedadUtilJSFManagedBean novedadUtilBean;

    private final Locale locale = new Locale("es", "CO");
    private final Calendar current = Calendar.getInstance(locale);
    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private UploadedFile file;
    private Novedad selected;
    private NovedadIncapacidad novedadIncapacidad;
    private NovedadIncapacidad selectedIncapacidad;
    private NovedadDocumentos novedadDocumento;
    private NovedadDocumentos selectedDocumento;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer i_puntosConciliados;
    private int i_Incapacita = 0;
    private int i_Diagnostico = 0;
    private int i_TipoIncapacidad = 0;
    private int height = 0;
    private int width = 0;

    private boolean flagGestor;
    private boolean flagTC;
    private boolean b_OwnerCreate = false;
    
    private List<Novedad> lista;
    private List<NovedadIncapacidad> lstNovedadIncapacidades;
    private List<NovedadDocumentos> lstDocumentos;
    private List<IncapacidadOrdena> lstIncapacidadOrdenas;
    private List<IncapacidadDx> lstIncapacidadDxs;
    private List<IncapacidadTipo> lstIncapacidadTipos;
    private List<String> fotosNovedades;
    private List<GopUnidadFuncional> lstUnidadesFuncionales;
    private StreamedContent archivo;

    @PostConstruct
    public void init() {
        llenarFechas();
        this.lista = novedadEjb.findAusentismosByDateRange(fechaInicio, fechaFin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        this.fotosNovedades = new ArrayList<>();
        flagGestor = validarRolGestor();
        flagTC = validarRolTC();
    }

    public void getByDateRange() {
        PrimeFaces.current().executeScript("PF('dtNovedades').clearFilters()");
        this.lista = novedadEjb.findAusentismosByDateRange(fechaInicio, fechaFin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void addObservacion() {
        IncapacidadDx dx = incapacidadDxEjb.find(i_Diagnostico);
        novedadIncapacidad.setObservaciones(dx.getCodigo() + " - " + dx.getDescripcion());
    }

    public void generarReporte() throws FileNotFoundException {
        List<ReporteIncapacidades> lstIncapacidades;
        archivo = null;

        if (Util.validarFechaCambioEstado(fechaInicio, fechaFin)) {
            MovilidadUtil.addErrorMessage("La fecha de inicio no puede ser mayor a la fecha fin");
            return;
        }

        if (lista == null || lista.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron datos");
            return;
        }

        lstIncapacidades = new ArrayList<>();

        for (Novedad n : lista) {
            ReporteIncapacidades reporte = new ReporteIncapacidades();
            reporte.setNovedad(n);
            reporte.setDias(MovilidadUtil.getDiferenciaDia(n.getDesde(), n.getHasta()));
            lstIncapacidades.add(reporte);
        }

        generarExcel(lstIncapacidades);

    }

    private void generarExcel(List<ReporteIncapacidades> lstIncapacidades) throws FileNotFoundException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        Map parametros = new HashMap();
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        plantilla = plantilla + "Informe Incapacidades.xlsx";
        parametros.put("dateFormat", dateFormat);
        parametros.put("desde", Util.dateFormat(fechaInicio));
        parametros.put("hasta", Util.dateFormat(fechaFin));
        parametros.put("novedades", lstIncapacidades);

        destino = destino + "Informe_Incapacidades.xlsx";

        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        InputStream stream = new FileInputStream(excel);
        archivo = new DefaultStreamedContent(stream, "text/plain", "Informe_Incapacidades_" + Util.dateFormat(fechaInicio) + "_al_" + Util.dateFormat(fechaFin) + ".xlsx");
    }

    public int calcularDias() {

        if (selected.getDesde() == null && selected.getHasta() == null) {
            return 0;
        }

        long startTime = selected.getDesde().getTime();
        long endTime = selected.getHasta().getTime();
        long diffTime = endTime - startTime;
        long diffDays = diffTime / (1000 * 60 * 60 * 24) + 1;
        return (int) diffDays;
    }

    public int calcularDias(Date desde, Date hasta) {

        if (desde == null || hasta == null) {
            return 0;
        }

        long startTime = desde.getTime();
        long endTime = hasta.getTime();
        long diffTime = endTime - startTime;
        long diffDays = diffTime / (1000 * 60 * 60 * 24) + 1;
        return (int) diffDays;
    }

    public void onRowSelect(SelectEvent event) {
        setSelected((Novedad) event.getObject());
        b_OwnerCreate = flagTC ? isOwnerCreate(selected.getIdNovedad(), user.getUsername()) : true;
    }

    public void nuevo() {
        cargarListas();
        selectedDocumento = null;
        novedadIncapacidad = new NovedadIncapacidad();
        selectedIncapacidad = null;
        i_Diagnostico = 0;
        i_Incapacita = 0;
        i_TipoIncapacidad = 0;
    }

    /**
     * Deshace la conciliación de una novedad y su asignación de puntos en el
     * programa master
     *
     * @param nov
     */
    public void noProcedeConciliacion(Novedad nov) {
        selected = nov;
        selected.setPuntosPmConciliados(0);
        selected.setProcede(0);
        MovilidadUtil.addSuccessMessage("Se ha realizado la acción correctamente");
        PrimeFaces.current().ajax().update("msgs");
        PrimeFaces.current().ajax().update("frmPrincipal:dtTipo");
        novedadEjb.edit(selected);
        selected = null;
        getByDateRange();
    }

    /**
     * Realiza la conciliación de una novedad y su asignación de puntos en el
     * programa master
     *
     * @param nov
     */
    public void procedeCociliacion(Novedad nov) {
        selected = nov;
        selected.setPuntosPmConciliados(puntoView(nov));
        selected.setProcede(1);
        MovilidadUtil.addSuccessMessage("Los puntos se han agregado a la Concilicación correctamente");
        MovilidadUtil.updateComponent("frmPrincipal:dtTipo");
        MovilidadUtil.updateComponent("msgs");
        novedadEjb.edit(selected);
        selected = null;
        getByDateRange();
    }

    /**
     * Retorna la cantidad de puntos PM de una novedad
     *
     * @param n
     * @return puntos Programa máster
     */
    public int puntoView(Novedad n) {
        if (n == null) {
            return 0;
        }
        if (n.getIdNovedadDano() != null) {
            return n.getIdNovedadDano().getIdVehiculoDanoSeveridad().getPuntosPm();
        }
        if (n.getIdMulta() != null) {
            return n.getPuntosPm();
        }
        if (n.getIdNovedadDano() == null && n.getIdMulta() == null) {
            return n.getIdNovedadTipoDetalle().getPuntosPm();
        }

        return 0;
    }

    public void postProcessXLS(Object document) {
        XSSFWorkbook wb = (XSSFWorkbook) document;
        wb.setSheetName(0, "BITACORA DE AUSENTISMOS");
    }

    public void postProcessXLSSeguimiento(Object document) {
        XSSFWorkbook wb = (XSSFWorkbook) document;
        wb.setSheetName(0, "SEGUIMIENTO DE INCAPACIDADES");
    }

    public List<NovedadIncapacidad> getListIncapacidades(Novedad novedad) {
        return novedad.getNovedadIncapacidadList();
    }

    public String formatDate(Date fecha) {
        return Util.dateFormat(fecha);
    }

    public void editarSeguimientoIncapacidad() {
        file = null;
        cargarListas();
        novedadIncapacidad = selectedIncapacidad;
        i_Diagnostico = novedadIncapacidad.getIdIncapacidadDx().getIdIncapacidadDx();
        i_Incapacita = novedadIncapacidad.getIdIncapacidadOrdena().getIdIncapacidadOrdena();
        i_TipoIncapacidad = novedadIncapacidad.getIdIncapacidadTipo().getIdIncapacidadTipo();
    }

    public void guardarSeguimientoIncapacidad() {

        novedadIncapacidad.setCreado(MovilidadUtil.fechaCompletaHoy());
        novedadIncapacidad.setUsername(user.getUsername());
        novedadIncapacidad.setIdNovedad(selected);
        novedadIncapacidad.setIdIncapacidadDx(incapacidadDxEjb.find(i_Diagnostico));
        novedadIncapacidad.setIdIncapacidadOrdena(incapacidadOrdenaEjb.find(i_Incapacita));
        novedadIncapacidad.setIdIncapacidadTipo(incapacidadTipoEjb.find(i_TipoIncapacidad));
        novedadIncapacidad.setEstadoReg(0);
        novedadIncapacidadEjb.create(novedadIncapacidad);

        novedadDocumento = new NovedadDocumentos();
        novedadDocumento.setIdNovedad(selected);
        novedadDocumento.setIdNovedadTipoDocumento(novedadTipoDocumentosEjb.find(7));
        novedadDocumento.setUsuario(user.getUsername());
        novedadDocumento.setCreado(novedadIncapacidad.getFecha());
        novedadDocumento.setEstadoReg(0);

        novedadEjb.edit(selected);

        if (file == null) {
            PrimeFaces.current().ajax().update("frmIncapacidad:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un documento ó imágen a cargar");
            return;
        } else {
            String path_documento = "";
            this.novedadDocumentosEjb.create(novedadDocumento);
            path_documento = Util.saveFile(file, novedadDocumento.getIdNovedadDocumento(), "incapacidad");
            novedadDocumento.setPathDocumento(path_documento);
            this.novedadDocumentosEjb.edit(novedadDocumento);
            file = null;
            PrimeFaces.current().ajax().update("frmAddFileIncapacidad:content");
        }
        this.lista = novedadEjb.findAusentismosByDateRange(fechaInicio, fechaFin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        PrimeFaces.current().executeScript("PF('dtNovedades').filter();");
        this.lstNovedadIncapacidades.add(novedadIncapacidad);
        nuevo();
        MovilidadUtil.addSuccessMessage("Seguimiento agregado éxitosamente");
    }

    public void actualizarSeguimientoIncapacidad() {
        novedadIncapacidad.setModificado(MovilidadUtil.fechaCompletaHoy());
        novedadIncapacidad.setUsername(user.getUsername());
        novedadIncapacidad.setIdNovedad(selected);
        novedadIncapacidad.setIdIncapacidadDx(incapacidadDxEjb.find(i_Diagnostico));
        novedadIncapacidad.setIdIncapacidadOrdena(incapacidadOrdenaEjb.find(i_Incapacita));
        novedadIncapacidad.setIdIncapacidadTipo(incapacidadTipoEjb.find(i_TipoIncapacidad));
        novedadIncapacidadEjb.edit(novedadIncapacidad);

        novedadEjb.edit(selected);

        if (file != null) {
            NovedadDocumentos documento = novedadDocumentosEjb.findByCreadoAndIdNovedad(novedadIncapacidad.getFecha(), selected.getIdNovedad());
            String path_documento = "";
            if (Util.deleteFile(documento.getPathDocumento())) {
                path_documento = Util.saveFile(file, documento.getIdNovedadDocumento(), "incapacidad");
                documento.setModificado(novedadIncapacidad.getModificado());
                documento.setPathDocumento(path_documento);
                this.novedadDocumentosEjb.edit(documento);
                file = null;
                PrimeFaces.current().ajax().update("frmAddFileIncapacidad:content");
            } else {
                System.out.println("NO BORRÓ");
            }
        }
        this.lista = this.novedadEjb.findByDateRange(fechaInicio, fechaFin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        MovilidadUtil.addSuccessMessage("Seguimiento actualizado éxitosamente");
    }

    public void getDocumento() {
        String ext = "";
        width = 700;
        height = 500;
        fotosNovedades.clear();
        archivosBean.reset();
        if (!selectedDocumento.getPathDocumento().endsWith("/")) {
            ext = selectedDocumento.getPathDocumento().substring(selectedDocumento.getPathDocumento().lastIndexOf('.'), selectedDocumento.getPathDocumento().length());
            if (!ext.equals(".pdf")) {
                try {
                    Image i = Util.mostrarImagenN(selectedDocumento.getPathDocumento());
                    if (i != null) {
                        if (width < i.getWidth(null)) {
                            width = i.getWidth(null);
                        }
                        if (height < i.getHeight(null)) {
                            height = i.getHeight(null);
                        }
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            archivosBean.setExtension(ext);
            archivosBean.setModalHeader(selectedDocumento.getIdNovedadTipoDocumento().getNombreTipoDocumento().toUpperCase() + " (" + selected.getIdEmpleado().getCodigoTm() + ") " + " - " + Util.dateFormat(selectedDocumento.getCreado()));
            archivosBean.setPath(selectedDocumento.getPathDocumento());
        } else {
            try {
                obtenerFotosNovedad();
                archivosBean.setExtension(ext);
                archivosBean.setPath("");
                archivosBean.setSelectedDocumento(selectedDocumento);
                archivosBean.setModalHeader(selectedDocumento.getIdNovedadTipoDocumento().getNombreTipoDocumento().toUpperCase() + " (" + selected.getIdEmpleado().getCodigoTm() + ") " + " - " + Util.dateFormat(selectedDocumento.getCreado()));
            } catch (IOException ex) {
                Logger.getLogger(NovedadJSFManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void obtenerFotosNovedad() throws IOException {
        List<String> lstNombresImg = Util.getFileList(selectedDocumento.getIdNovedadDocumento(), "novedad_documentos");

        if (lstNombresImg != null) {
            width = 100;
            height = 100;
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

    public void cambiarPuntos() {
        if (selected == null) {
            MovilidadUtil.addErrorMessage("Seleccionar una novedad.");
            return;
        }

        i_puntosConciliados = selected.getPuntosPmConciliados();
        MovilidadUtil.openModal("apli-pm");
    }

    /**
     * Asigna los puntos del Programa master conciliados a una novedad
     */
    public void aplicarPuntosPM() {
        if (i_puntosConciliados == null) {
            MovilidadUtil.addErrorMessage("Digite un valor para puntos conciliados");
            return;
        }
        selected.setPuntosPmConciliados(i_puntosConciliados);
        selected.setProcede(1);
        novedadEjb.edit(selected);
        MovilidadUtil.addSuccessMessage("Los puntos se han agregado a la Concilicación correctamente");
        MovilidadUtil.hideModal("apli-pm");
        MovilidadUtil.updateComponent("frmPrincipal:dtTipo");
        selected = null;
    }

    public void handleFileIncapacidad(FileUploadEvent event) {
        PrimeFaces current = PrimeFaces.current();
        this.file = event.getFile();
        current.executeScript("PF('AddFileIncapacidadDialog').hide()");
        current.ajax().update(":frmIncapacidad:messages");
        MovilidadUtil.addSuccessMessage("Archivo agregado éxitosamente");
    }

    public boolean verificarIncapacidad() {
//        if (validarRolAnalista()) {
        if (selected != null) {
            return novedadUtilBean.isIncapacidad(selected);
        }
//        }
        return false;
    }

    /**
     * Valida si el usuario logueado corresponde a un gestor
     *
     * @return true si el usuario tiene rol ROLE_LIQ
     */
    private boolean validarRolGestor() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().contains("LIQ")) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Valida si el usuario logueado corresponde a un TC
     *
     * @return true si el usuario tiene rol ROLE_TC
     */
    private boolean validarRolTC() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_TC")) {
                return true;
            }
        }
        return false;
    }
    
    private void llenarFechas() {
        fechaFin = MovilidadUtil.fechaCompletaHoy();
        current.setTime(fechaFin);
        current.add(Calendar.DATE, -1);
        fechaInicio = current.getTime();
    }

    private void cargarListas() {
        lstIncapacidadTipos = incapacidadTipoEjb.findAll();
        lstIncapacidadOrdenas = incapacidadOrdenaEjb.findAll();
        lstIncapacidadDxs = incapacidadDxEjb.findAll();
    }

    /**
     * Valida si el usuario logueado corresponde al Técnico de control que 
     * creó la novedad seleccionada 
     *
     * @return 
     */
    private boolean isOwnerCreate(int idNovedad, String username){
        return novedadEjb.findNovedadByUserCreate(idNovedad, username, MovilidadUtil.fechaCompletaHoy());
    }
    
    public Novedad getSelected() {
        return selected;
    }

    public void setSelected(Novedad selected) {
        this.selected = selected;
    }

    public NovedadIncapacidad getNovedadIncapacidad() {
        return novedadIncapacidad;
    }

    public void setNovedadIncapacidad(NovedadIncapacidad novedadIncapacidad) {
        this.novedadIncapacidad = novedadIncapacidad;
    }

    public NovedadDocumentos getNovedadDocumento() {
        return novedadDocumento;
    }

    public void setNovedadDocumento(NovedadDocumentos novedadDocumento) {
        this.novedadDocumento = novedadDocumento;
    }

    public NovedadDocumentos getSelectedDocumento() {
        return selectedDocumento;
    }

    public void setSelectedDocumento(NovedadDocumentos selectedDocumento) {
        this.selectedDocumento = selectedDocumento;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getI_Incapacita() {
        return i_Incapacita;
    }

    public void setI_Incapacita(int i_Incapacita) {
        this.i_Incapacita = i_Incapacita;
    }

    public int getI_Diagnostico() {
        return i_Diagnostico;
    }

    public void setI_Diagnostico(int i_Diagnostico) {
        this.i_Diagnostico = i_Diagnostico;
    }

    public int getI_TipoIncapacidad() {
        return i_TipoIncapacidad;
    }

    public void setI_TipoIncapacidad(int i_TipoIncapacidad) {
        this.i_TipoIncapacidad = i_TipoIncapacidad;
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

    public List<Novedad> getLista() {
        return lista;
    }

    public void setLista(List<Novedad> lista) {
        this.lista = lista;
    }

    public NovedadIncapacidad getSelectedIncapacidad() {
        return selectedIncapacidad;
    }

    public void setSelectedIncapacidad(NovedadIncapacidad selectedIncapacidad) {
        this.selectedIncapacidad = selectedIncapacidad;
    }

    public List<NovedadIncapacidad> getLstNovedadIncapacidades() {
        if (selected != null) {
            lstNovedadIncapacidades = novedadIncapacidadEjb.findByNovedad(selected.getIdNovedad());
        }
        return lstNovedadIncapacidades;
    }

    public void setLstNovedadIncapacidades(List<NovedadIncapacidad> lstNovedadIncapacidades) {
        this.lstNovedadIncapacidades = lstNovedadIncapacidades;
    }

    public List<IncapacidadOrdena> getLstIncapacidadOrdenas() {
        return lstIncapacidadOrdenas;
    }

    public void setLstIncapacidadOrdenas(List<IncapacidadOrdena> lstIncapacidadOrdenas) {
        this.lstIncapacidadOrdenas = lstIncapacidadOrdenas;
    }

    public List<IncapacidadDx> getLstIncapacidadDxs() {
        return lstIncapacidadDxs;
    }

    public void setLstIncapacidadDxs(List<IncapacidadDx> lstIncapacidadDxs) {
        this.lstIncapacidadDxs = lstIncapacidadDxs;
    }

    public List<IncapacidadTipo> getLstIncapacidadTipos() {
        return lstIncapacidadTipos;
    }

    public List<String> getFotosNovedades() {
        return fotosNovedades;
    }

    public void setFotosNovedades(List<String> fotosNovedades) {
        this.fotosNovedades = fotosNovedades;
    }

    public void setLstIncapacidadTipos(List<IncapacidadTipo> lstIncapacidadTipos) {
        this.lstIncapacidadTipos = lstIncapacidadTipos;
    }

    public List<NovedadDocumentos> getLstDocumentos() {
        if (selected != null) {
            lstDocumentos = novedadDocumentosEjb.findByIdNovedad(selected.getIdNovedad());
        }
        return lstDocumentos;
    }

    public void setLstDocumentos(List<NovedadDocumentos> lstDocumentos) {
        this.lstDocumentos = lstDocumentos;
    }

    public StreamedContent getArchivo() {
        return archivo;
    }

    public void setArchivo(StreamedContent archivo) {
        this.archivo = archivo;
    }

    public boolean isFlagGestor() {
        return flagGestor;
    }

    public void setFlagGestor(boolean flagGestor) {
        this.flagGestor = flagGestor;
    }

    public Integer getI_puntosConciliados() {
        return i_puntosConciliados;
    }

    public void setI_puntosConciliados(Integer i_puntosConciliados) {
        this.i_puntosConciliados = i_puntosConciliados;
    }

    public boolean isB_OwnerCreate() {
        return b_OwnerCreate;
    }

    public void setB_OwnerCreate(boolean b_OwnerCreate) {
        this.b_OwnerCreate = b_OwnerCreate;
    }
    
}
