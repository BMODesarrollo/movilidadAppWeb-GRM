package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.ConfigFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.GenericaDocumentosFacadeLocal;
import com.movilidad.ejb.GenericaFacadeLocal;
import com.movilidad.ejb.GenericaSeguimientoFacadeLocal;
import com.movilidad.ejb.GenericaTipoDetallesFacadeLocal;
import com.movilidad.ejb.GenericaTipoDocumentosFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.GenericaTipoFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.Generica;
import com.movilidad.model.GenericaDocumentos;
import com.movilidad.model.GenericaSeguimiento;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.GenericaTipo;
import com.movilidad.model.GenericaTipoDetalles;
import com.movilidad.model.GenericaTipoDocumentos;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.Util;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
@Named(value = "genericaBean")
@ViewScoped
public class GenericaManagedBean implements Serializable {

    @EJB
    private GenericaFacadeLocal genericaEjb;
    @EJB
    private GenericaSeguimientoFacadeLocal genericaSeguimientoEjb;
    @EJB
    private GenericaDocumentosFacadeLocal genericaDocumentosEjb;
    @EJB
    private GenericaTipoDocumentosFacadeLocal genericaTipoDocumentosEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;

    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private GenericaTipoFacadeLocal novedadTipoEjb;
    @EJB
    private GenericaTipoDetallesFacadeLocal genericaTipoDetallesEjb;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @Inject
    private ArchivosJSFManagedBean archivosBean;

    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private UploadedFile file;
    private Generica novedad;
    private Generica selected;
    private GenericaTipo novedadTipo;
    private GenericaTipoDetalles genericaTipoDetalles;
    private GenericaSeguimiento genericaSeguimiento;
    private GenericaSeguimiento selectedSeguimiento;
    private GenericaDocumentos genericaDocumento;
    private GenericaDocumentos selectedDocumento;
    private GenericaTipoDocumentos genericaTipoDocumentos;
    private Empleado empleado;
    private ParamAreaUsr paramAreaUsr;
    private Date fechaInicio;
    private Date fechaFin;
    private int usrArea = 0;
    private int height = 0;
    private int width = 0;
    private boolean b_rol = validarRol();
    private boolean b_VerificacionPM = false;
    private boolean b_VerificacionSinFechas = false;
    private boolean b_BtNuevo = false;
    private Integer i_puntosConciliados;
    private String c_vehiculo = "";
    private StreamedContent fileReporte;

    private List<Generica> lista;
    private List<Generica> listaFilter;
    private List<GenericaTipo> lstGenericaTipos;
    private List<GenericaTipoDetalles> lstGenericaTipoDetalles;
    private List<GenericaSeguimiento> lstSeguimientos;
    private List<GenericaDocumentos> lstDocumentos;
    private List<GenericaTipoDocumentos> lstNovedadTipoDocumentos;
    private List<Empleado> lstEmpleados;
    private List<UploadedFile> archivos;
    private List<String> fotosNovedades;

    @EJB
    private ConfigFacadeLocal configEJB;

    public boolean procedeNovedad() {
        try {
            return configEJB.findByKey("nov").getValue() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostConstruct
    public void init() {
        paramAreaUsr = genericaEjb.findByUsername(user.getUsername());
        if (paramAreaUsr != null) {
            usrArea = paramAreaUsr.getIdParamArea().getIdParamArea();
        } else {
            b_BtNuevo = true;
        }
        if (!MovilidadUtil.validarUrl("panelPrincipal")) {
            if (usrArea > 0) {
                this.lista = genericaEjb.findAllByArea(MovilidadUtil.fechaCompletaHoy(), usrArea, ConstantsUtil.OFF_INT);
            }
        }
        this.fotosNovedades = new ArrayList<>();
        novedad = new Generica();
        novedadTipo = new GenericaTipo();
        fechaInicio = new Date();
        fechaFin = new Date();
        archivos = new ArrayList<>();
        selected = null;
        file = null;
    }

    public String master(Empleado empl) {
        if (empl == null) {
            return "N/A";
        }
        String master = "";
        try {
            master = empl.getPmGrupoDetalleList().get(0).getIdGrupo().getNombreGrupo();
        } catch (Exception e) {
            return "N/A";
        }

        return master;
    }

    public void generarReporte() throws FileNotFoundException {

        fileReporte = null;

        if (Util.validarFechaCambioEstado(fechaInicio, fechaFin)) {
            MovilidadUtil.addErrorMessage("La fecha de inicio no puede ser mayor a la fecha fin");
            return;
        }

        if (paramAreaUsr != null) {
            if (paramAreaUsr.getIdParamArea() != null) {
                if (lista == null || lista.isEmpty()) {
                    MovilidadUtil.addErrorMessage("No se encontraron datos");
                    return;
                }
                generarExcel();
            }

        } else {
            MovilidadUtil.addErrorMessage("usted NO tiene un área asociada.");
        }

    }

    private void generarExcel() throws FileNotFoundException {
        Map parametros = new HashMap();
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        plantilla = plantilla + "Informe Novedades Genericas.xlsx";
        parametros.put("timeFormat", Util.DATE_TO_TIME_FORMAT);
        parametros.put("desde", Util.dateFormat(fechaInicio));
        parametros.put("hasta", Util.dateFormat(fechaFin));
        parametros.put("novedades", lista);

        destino = destino + "REPORTE_NOVEDADES.xls";

        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        InputStream stream = new FileInputStream(excel);
        fileReporte = new DefaultStreamedContent(stream, "text/plain", "REPORTE_NOVEDADES_" + Util.dateFormat(fechaInicio) + "_al_" + Util.dateFormat(fechaFin) + ".xlsx");
    }

    public void prepareListEmpleados() {
        lstEmpleados = null;
        this.empleado = new Empleado();
        if (lstEmpleados == null) {
            if (usrArea > 0) {
//                List<Integer> cargos = genericaEjb.obtenerCargos(usrArea);
//                lstEmpleados = genericaEjb.obtenerEmpleadosByCargo(cargos);
                //Se modifica consulta para traer empledao por area.
                lstEmpleados = empleadoEjb.getEmpledosByIdArea(usrArea, ConstantsUtil.OFF_INT);
            }
        }
    }

    public void prepareListGenericaTipo() {
        lstGenericaTipos = null;
        this.novedadTipo = new GenericaTipo();
        if (lstGenericaTipos == null) {
            if (usrArea > 0) {
                lstGenericaTipos = this.novedadTipoEjb.findAllByArea(usrArea);
            } else {
                lstGenericaTipos = null;
            }
        }
        PrimeFaces.current().executeScript("PF('dtNovedadTipos').clearFilters();");
        PrimeFaces.current().ajax().update(":frmPmNovedadTipoList:dtNovedadTipo");
    }

    public void prepareListGenericaTipoDetalle() {
        lstGenericaTipoDetalles = null;
        this.genericaTipoDetalles = new GenericaTipoDetalles();
        if (novedadTipo != null) {
            lstGenericaTipoDetalles = novedadTipo.getGenericaTipoDetallesList();
        }
        PrimeFaces.current().executeScript("PF('dtNovedadTipoDetalle').clearFilters();");
    }

    public void prepareListGenericaTipoDocumentos() {
        lstNovedadTipoDocumentos = null;
        this.genericaTipoDocumentos = new GenericaTipoDocumentos();

        if (lstNovedadTipoDocumentos == null) {
            if (usrArea > 0) {
                lstNovedadTipoDocumentos = new ArrayList<>();
                lstNovedadTipoDocumentos = genericaTipoDocumentosEjb.findByArea(usrArea);
            }
        }
    }

    public void onRowSelect(SelectEvent event) {
        setSelected((Generica) event.getObject());
    }

    public void onEmpleadoChosen(SelectEvent event) {
        if (event.getObject() instanceof Empleado) {
            setEmpleado((Empleado) event.getObject());
        }
    }

    public void onGenericaTipoChosen(SelectEvent event) {
        if (event.getObject() instanceof GenericaTipo) {
            setGenericaTipo((GenericaTipo) event.getObject());
        }
    }

    public void onGenericaTipoDetalleChosen(SelectEvent event) {
        if (event.getObject() instanceof GenericaTipoDetalles) {
            setGenericaTipoDetalles((GenericaTipoDetalles) event.getObject());
        }
    }

    public void onNovedadTipoDocumentosChosen(SelectEvent event) {
        setGenericaTipoDocumentos((GenericaTipoDocumentos) event.getObject());
    }

    public void onRowGenericaTipoClckSelect(final SelectEvent event) {
        PrimeFaces current = PrimeFaces.current();
        if (event.getObject() instanceof GenericaTipo) {
            setGenericaTipo((GenericaTipo) event.getObject());
        }
        this.genericaTipoDetalles = new GenericaTipoDetalles();
        this.genericaTipoDetalles.setTituloTipoGenerica("");
        current.executeScript("PF('dtNovedadTipos').clearFilters();");
        current.ajax().update(":frmPmNovedadTipoList:dtNovedadTipo");
        current.ajax().update(":frmNovedadesPm:novedad_tipo_detalle");
        current.executeScript("PF('dtNovedadTipoDetalle').clearFilters();");
        current.ajax().update(":frmPmNovedadTipoDetalleList:dtNovedadTipoDetalles");
    }

    public void onRowGenericaTipoDetallesClckSelect(final SelectEvent event) {
        PrimeFaces current = PrimeFaces.current();
        if (event.getObject() instanceof GenericaTipoDetalles) {
            setGenericaTipoDetalles((GenericaTipoDetalles) event.getObject());
        }
        current.executeScript("PF('dtNovedadTipoDetalle').clearFilters();");
        current.ajax().update(":frmPmNovedadTipoDetalleList:dtNovedadTipoDetalles");
    }

    public void onRowEmpleadoClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof Empleado) {
            setEmpleado((Empleado) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('wVPmEmpleadosListDialog').clearFilters();");
        PrimeFaces.current().ajax().update(":frmPmEmpleadoList:dtEmpleados");
    }

    public void onRowDblClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof GenericaTipoDocumentos) {
            setGenericaTipoDocumentos((GenericaTipoDocumentos) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('dtNovedadDocumento').clearFilters();");
        PrimeFaces.current().ajax().update(":frmNovedadTipoDocumentosList:dtNovedadDocumentos");
    }

    public void resetSelected() {
        selected = null;
    }

    public int puntoView(Generica n) {
        return n.getIdGenericaTipoDetalle().getPuntosPm();
    }

    public void postProcessXLS(Object document) {

        XSSFWorkbook wb = (XSSFWorkbook) document;
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow header = sheet.getRow(0);

        Iterator<Row> rowIterator = sheet.iterator();

        if (rowIterator.hasNext()) {
            rowIterator.next();
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getColumnIndex() == 1 && !cell.getStringCellValue().isEmpty()) {
                    XSSFCellStyle cellStyle = wb.createCellStyle();
                    XSSFDataFormat format = wb.createDataFormat();
                    cellStyle.setDataFormat(format.getFormat("DD-MMMM-YYYY"));
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(cell.getStringCellValue().replace("\'", ""));
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                }
                if (cell.getColumnIndex() > 1) {
                    if (!cell.getStringCellValue().isEmpty()
                            && cell.getColumnIndex() == 9) {
                        cell.setCellValue(new BigDecimal(cell.getStringCellValue().replace("'", "")).doubleValue());
                    }
                }
            }
        }
    }

    public void nuevo() {
        novedad = new Generica();
        novedadTipo = new GenericaTipo();
        genericaTipoDetalles = new GenericaTipoDetalles();
        genericaTipoDetalles.setTituloTipoGenerica("");
        empleado = new Empleado();
        genericaSeguimiento = new GenericaSeguimiento();
        selectedSeguimiento = null;
        genericaDocumento = new GenericaDocumentos();
        genericaTipoDocumentos = new GenericaTipoDocumentos();
        selectedDocumento = null;
        archivos = new ArrayList<>();
        this.archivos.clear();
        c_vehiculo = "";
    }

    public void editar() {
        this.novedad = this.selected;
        this.novedadTipo = this.novedad.getIdGenericaTipo();

        if (selectedSeguimiento != null) {
            this.genericaSeguimiento = this.selectedSeguimiento;
        }
        if (selectedDocumento != null) {
            this.genericaDocumento = this.selectedDocumento;
            this.genericaTipoDocumentos = this.genericaDocumento.getIdGenericaTipoDocumento();
        }

        this.genericaTipoDetalles = this.novedad.getIdGenericaTipoDetalle();
        this.empleado = this.novedad.getIdEmpleado();
        this.archivos.clear();
    }

    public void guardarSeguimiento() {
        if (selected == null) {
            MovilidadUtil.addAdvertenciaMessage("Debe seleccionar una novedad para aplicarle seguimiento. ");
            return;
        }

        genericaSeguimiento.setIdGenerica(selected);
        genericaSeguimiento.setUsername(user.getUsername());
        genericaSeguimiento.setCreado(new Date());
        this.genericaSeguimientoEjb.create(genericaSeguimiento);
        this.lista = this.genericaEjb.findByDateRange(fechaInicio, fechaFin, ConstantsUtil.OFF_INT);
        PrimeFaces.current().ajax().update(":frmPrincipal:dtTipo");
        this.lstSeguimientos.add(genericaSeguimiento);

        if (selected.getGenericaSeguimientoList() == null) {
            selected.setGenericaSeguimientoList(new ArrayList<GenericaSeguimiento>());
            selected.getGenericaSeguimientoList().add(genericaSeguimiento);
        } else {
            selected.getGenericaSeguimientoList().add(genericaSeguimiento);
        }

        nuevo();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Seguimiento de novedad registrado éxitosamente."));
    }

    public void actualizarSeguimiento() {
        PrimeFaces current = PrimeFaces.current();
        genericaSeguimiento.setIdGenerica(selected);
        genericaSeguimiento.setUsername(user.getUsername());
        genericaSeguimiento.setCreado(new Date());
        this.genericaSeguimientoEjb.edit(genericaSeguimiento);
        current.ajax().update(":frmPrincipal:dtTipo");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Seguimiento de novedad actualizado éxitosamente."));
    }

    public void guardarNovedadDocumento() {
        genericaDocumento.setIdGenerica(selected);
        genericaDocumento.setIdGenericaTipoDocumento(genericaTipoDocumentos);
        genericaDocumento.setUsuario(user.getUsername());
        genericaDocumento.setCreado(new Date());
        if (archivos.isEmpty()) {
            PrimeFaces.current().ajax().update("frmNovedadesPm:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un documento a cargar");
            return;
        } else {
            int idRegistro = 0;
            boolean flagImagen = false;
            String path_documento = " ";
            String path_imagen = " ";
            for (UploadedFile f : archivos) {
                if (f.getContentType().contains("pdf")) {
                    this.genericaDocumentosEjb.create(genericaDocumento);
                    path_documento = Util.saveFile(f, genericaDocumento.getIdGenericaDocumento(), "generica_documento");
                    genericaDocumento.setPathDocumento(path_documento);
                    this.genericaDocumentosEjb.edit(genericaDocumento);
                }
                if (f.getContentType().contains("image")) {
                    if (!flagImagen) {
                        this.genericaDocumentosEjb.create(genericaDocumento);
                        idRegistro = genericaDocumento.getIdGenericaDocumento();
                    }
                    path_imagen = Util.saveFile(f, idRegistro, "generica_documento");
                    genericaDocumento.setPathDocumento(path_imagen);
                    this.genericaDocumentosEjb.edit(genericaDocumento);
                    flagImagen = true;
                }
            }
            archivos.clear();
        }
        this.lista = this.genericaEjb.findByDateRange(fechaInicio, fechaFin, ConstantsUtil.OFF_INT);
        this.lstDocumentos.add(genericaDocumento);

        if (selected.getGenericaDocumentosList() == null) {
            selected.setGenericaDocumentosList(new ArrayList<GenericaDocumentos>());
            selected.getGenericaDocumentosList().add(genericaDocumento);
        } else {
            selected.getGenericaDocumentosList().add(genericaDocumento);
        }

        genericaDocumento = new GenericaDocumentos();
        genericaTipoDocumentos = new GenericaTipoDocumentos();
        selectedDocumento = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Documento anexado a novedad éxitosamente."));
    }

    public void actualizarNovedadDocumento() {
        PrimeFaces current = PrimeFaces.current();
        genericaDocumento.setIdGenerica(selected);
        genericaDocumento.setIdGenericaTipoDocumento(genericaTipoDocumentos);
        genericaDocumento.setUsuario(user.getUsername());
        genericaDocumento.setCreado(new Date());
//        this.novedadDocumentosEjb.edit(novedadDocumento);
        if (!archivos.isEmpty()) {
            String path_documento = " ";
            String path_imagen = " ";
            for (UploadedFile f : archivos) {
                if (selectedDocumento.getPathDocumento().contains("pdf")) {
                    if (Util.deleteFile(selectedDocumento.getPathDocumento())) {
                        if (f.getContentType().contains("pdf")) {
                            path_documento = Util.saveFile(f, genericaDocumento.getIdGenericaDocumento(), "generica_documento");
                            genericaDocumento.setPathDocumento(path_documento);
                            this.genericaDocumentosEjb.edit(genericaDocumento);
                        }
                    }
                } else {
                    if (f.getContentType().contains("image")) {
                        path_imagen = Util.saveFile(f, genericaDocumento.getIdGenericaDocumento(), "generica_documento");
                        genericaDocumento.setPathDocumento(path_imagen);
                        this.genericaDocumentosEjb.edit(genericaDocumento);
                    }
                }
            }
            archivos.clear();
        } else {
            PrimeFaces.current().ajax().update("frmNovedadesPm:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un documento a cargar");
            return;
        }
        selectedDocumento = null;
        current.ajax().update(":frmPrincipal:dtTipo");
        current.executeScript("PF('novedadDocumentos').hide();");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Documento de novedad actualizado éxitosamente."));
    }

    public void obtenerFotosNovedad() throws IOException {
        List<String> lstNombresImg = Util.getFileList(selectedDocumento.getIdGenericaDocumento(), "generica_documento");

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

    public void getDocumento() {
        String ext = "";
        fotosNovedades.clear();
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
            } else {
                width = 700;
                height = 500;
            }
            archivosBean.setExtension(ext);
            archivosBean.setModalHeader(selectedDocumento.getIdGenericaTipoDocumento().getNombreTipoDocumento().toUpperCase() + " (" + selected.getIdEmpleado().getIdentificacion() + ") " + " - " + Util.dateFormat(selectedDocumento.getCreado()));
            archivosBean.setPath(selectedDocumento.getPathDocumento());
        } else {
            try {
                obtenerFotosNovedad();
                archivosBean.setExtension(ext);
                archivosBean.setPath("");
                archivosBean.setGenericaDocumento(selectedDocumento);
                archivosBean.setModalHeader(selectedDocumento.getIdGenericaTipoDocumento().getNombreTipoDocumento().toUpperCase() + " (" + selected.getIdEmpleado().getIdentificacion() + ") " + " - " + Util.dateFormat(selectedDocumento.getCreado()));
            } catch (IOException ex) {
                Logger.getLogger(NovedadJSFManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        PrimeFaces current = PrimeFaces.current();

        archivos.add(event.getFile());

        current.executeScript("PF('AddFilesListDialog').hide()");
        current.ajax().update(":frmNuevoDocumento:messages");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Documento agregado éxitosamente."));
    }

    public void guardarNovedadPM() {
        if (novedadTipo.getNombreTipoNovedad() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo de novedad");
            return;
        }
        if (genericaTipoDetalles.getTituloTipoGenerica() == null || genericaTipoDetalles.getTituloTipoGenerica().equals("")) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un detalle de tipo de novedad");
            return;
        }
        if (empleado.getNombres() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un empleado");
            return;
        }
        if (genericaTipoDetalles.getFechas() == 1) {
            if (novedad.getDesde() == null || novedad.getHasta() == null) {
                MovilidadUtil.addErrorMessage("Fecha Desde y Hasta son necesarias");
                return;
            }
        }
        if (genericaTipoDetalles.getHora() == 1) {
            if (novedad.getHora() == null) {
                MovilidadUtil.addErrorMessage("Debe especificar la hora");
                return;
            }
        }
        novedad.setIdGopUnidadFuncional(empleado.getIdGopUnidadFuncional());
        novedad.setIdGenericaTipo(novedadTipo);
        novedad.setIdGenericaTipoDetalle(genericaTipoDetalles);
        novedad.setIdParamArea(genericaTipoDetalles.getIdParamArea());
        novedad.setIdEmpleado(empleado);

        if (!b_VerificacionPM && genericaTipoDetalles.getFechas() == 1) {
            if (genericaEjb.validarNovedadConFechas(empleado.getIdEmpleado(), novedad.getDesde(), novedad.getHasta()) != null) {
                b_VerificacionPM = true;
                PrimeFaces.current().executeScript("PF('verificarNovedadPM').show()");
                return;
            }
        } else {
            if (!(b_VerificacionSinFechas) && genericaEjb.verificarNovedadPMSinFechas(novedad.getFecha(), empleado.getIdEmpleado(), genericaTipoDetalles.getIdGenericaTipoDetalle()) != null) {
                b_VerificacionSinFechas = true;
                PrimeFaces.current().executeScript("PF('verificarNovedadPM').show()");
                return;
            }
        }

        novedad.setPuntosPm(genericaTipoDetalles.getPuntosPm());
        novedad.setPuntosPmConciliados(0);
        novedad.setLiquidada(0);

        novedad.setUsername(user.getUsername());
        novedad.setCreado(new Date());
        this.genericaEjb.create(novedad);
        if (novedad.getIdGenericaTipoDetalle().getAfectaPm() == 1) {
            if (procedeNovedad()) {
                novedad.setProcede(1);
//                novedad.setPuntosPm(genericaTipoDetalles.getPuntosPm());
                procedeCociliacion(novedad);
            }
        }

        if (genericaTipoDetalles.getNotificacion() == 1) {
            notificar();
        }

        nuevo();
        resetSelected();
        if (usrArea > 0) {
            this.lista = genericaEjb.findByDateRangeAndArea(fechaInicio, fechaFin, usrArea, ConstantsUtil.OFF_INT);
            PrimeFaces.current().executeScript("PF('dtNovedades').clearFilters();");
        }
//        this.lista = this.genericaEjb.findByDateRange(fechaInicio, fechaFin);
        b_VerificacionPM = false;
        b_VerificacionSinFechas = false;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Novedad  registrada correctamente."));
    }

    public void actualizarNovedadPM() {
        PrimeFaces current = PrimeFaces.current();

        if (novedadTipo.getNombreTipoNovedad() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo de novedad");
            return;
        }
        if (genericaTipoDetalles.getTituloTipoGenerica() == null || genericaTipoDetalles.getTituloTipoGenerica().equals("")) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un detalle de tipo de novedad");
            return;
        }
        if (empleado.getNombres() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un empleado");
            return;
        }

        if (genericaTipoDetalles.getFechas() == 1) {
            if (novedad.getDesde() == null || novedad.getHasta() == null) {
                MovilidadUtil.addErrorMessage("Fecha Desde y Hasta son necesarias");
                return;
            }
        }

        if (genericaTipoDetalles.getHora() == 1) {
            if (novedad.getHora() == null) {
                MovilidadUtil.addErrorMessage("Debe especificar la hora");
                return;
            }
        }

        novedad.setIdGopUnidadFuncional(empleado.getIdGopUnidadFuncional());
        novedad.setIdGenericaTipo(novedadTipo);
        novedad.setIdGenericaTipoDetalle(genericaTipoDetalles);
        novedad.setIdParamArea(genericaTipoDetalles.getIdParamArea());
        novedad.setIdEmpleado(empleado);
        novedad.setProcede(0);
        novedad.setPuntosPm(genericaTipoDetalles.getPuntosPm());
//        novedad.setPuntosPmConciliados(0);

        novedad.setLiquidada(0);
        novedad.setUsername(user.getUsername());
        novedad.setModificado(new Date());
        if (novedad.getIdGenericaTipoDetalle().getAfectaPm() == 1) {
            if (procedeNovedad()) {
                novedad.setProcede(1);
//                novedad.setPuntosPm(genericaTipoDetalles.getPuntosPm());
                procedeCociliacion(novedad);
            }
        }
        this.genericaEjb.edit(novedad);

        current.ajax().update("frmPrincipal:dtTipo");
        current.executeScript("PF('novedadesPM').hide()");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Novedad  actualizada correctamente."));
    }

    /*
     * Obtener novedades por rango de fechas 
     */
    public void getByDateRange() {
        resetSelected();
        PrimeFaces.current().executeScript("PF('dtNovedades').clearFilters()");
        if (usrArea > 0) {
            this.lista = genericaEjb.findByDateRangeAndArea(fechaInicio, fechaFin, usrArea, ConstantsUtil.OFF_INT);
        }

        if (lista == null || lista.isEmpty()) {
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("No se encontraron novedades para éste rango de fechas");
        }
    }

    /*
     * Parámetros para el envío de correos (Novedades PM)
     */
    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.find(Util.ID_NOVEDAD_GEN_TEMPLATE);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    private void notificar() {
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("fecha", Util.dateFormat(novedad.getFecha()));
        mailProperties.put("tipo", novedad.getIdGenericaTipo().getNombreTipoNovedad());
        mailProperties.put("detalle", novedad.getIdGenericaTipoDetalle().getTituloTipoGenerica());
        mailProperties.put("fechas", novedad.getDesde() != null && novedad.getHasta() != null ? Util.dateFormat(novedad.getDesde()) + " hasta " + Util.dateFormat(novedad.getHasta()) : "");
        mailProperties.put("hora", novedad.getHora() != null ? Util.dateToTime(novedad.getHora()) : "");
        mailProperties.put("operador", empleado != null ? empleado.getIdentificacion() + " - " + empleado.getNombres() + " " + empleado.getApellidos() : "");
        mailProperties.put("username", "");
        mailProperties.put("generada", Util.dateTimeFormat(novedad.getCreado()));
        mailProperties.put("observaciones", novedad.getObservaciones());
        String subject = "";
        String destinatarios = "";

        if (novedad.getIdEmpleado() != null) {
            destinatarios = novedad.getIdEmpleado() != null ? novedad.getIdEmpleado().getEmailCorporativo() : "";
        }
        if (novedad.getIdGenericaTipoDetalle().getIdGenericaNotifProcesos() != null) {
            subject = novedad.getIdGenericaTipoDetalle().getIdGenericaNotifProcesos().getMensaje();
            if (destinatarios != null) {
                destinatarios = destinatarios + "," + novedad.getIdGenericaTipoDetalle().getIdGenericaNotifProcesos().getEmails();
            } else {
                destinatarios = novedad.getIdGenericaTipoDetalle().getIdGenericaNotifProcesos().getEmails();
            }
        }
        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", null);
    }

    private boolean validarRolAnalista() {
        for (GrantedAuthority auth : user.getAuthorities()) {
//            System.out.println(auth.getAuthority());
            if (auth.getAuthority().equals("ROLE_LIQMTTO") || auth.getAuthority().equals("ROLE_LIQGEN")) {
                return true;
            }
        }
        return false;
    }

    boolean validarRol() {
        for (GrantedAuthority auth : user.getAuthorities()) {
//            System.out.println(auth.getAuthority());
            if (auth.getAuthority().equals("ROLE_PROFMTTO") || auth.getAuthority().equals("ROLE_PROFGEN") || auth.getAuthority().equals("ROLE_LIQMTTO")) {
                return true;
            }

        }
        return false;
    }

    public boolean validarRolSeg() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_SEG")) {
                return true;
            }
        }
        return false;
    }

    public void procedeCociliacion(Generica nov) {
        nov.setPuntosPmConciliados(puntoView(nov));
        nov.setProcede(1);
        if (validarRol()) {
            MovilidadUtil.addSuccessMessage("Los puntos se han agregado a la Concilicación correctamente");
            PrimeFaces.current().ajax().update("msgs");
            PrimeFaces.current().ajax().update("frmPrincipal:dtTipo");
        } else {
            PrimeFaces.current().ajax().update("msgs");
        }
        genericaEjb.edit(nov);
        reset();
    }

    public void noProcedeConciliacion(Generica nov) {
        nov.setPuntosPmConciliados(0);
        nov.setProcede(0);
        MovilidadUtil.addSuccessMessage("Se ha realizado la acción correctamente");
        PrimeFaces.current().ajax().update("msgs");
        PrimeFaces.current().ajax().update("frmPrincipal:dtTipo");
        genericaEjb.edit(nov);
        reset();
    }

    public void aplicarPuntosPM() {
        if (selected == null) {
            MovilidadUtil.addErrorMessage("Error al seleccionar la Novedad");
            PrimeFaces.current().executeScript("PF('apli-pm').hide();");
            PrimeFaces.current().ajax().update("msgs");
            PrimeFaces.current().ajax().update("frmPrincipal:dtTipo");
            reset();
            return;
        }
        if (i_puntosConciliados >= 0 && i_puntosConciliados <= 100) {
            selected.setPuntosPmConciliados(i_puntosConciliados);
            selected.setProcede(1);
            MovilidadUtil.addSuccessMessage("Los puntos se han agregado a la Concilicación correctamente");
            PrimeFaces.current().ajax().update("msgs");
            PrimeFaces.current().executeScript("PF('apli-pm').hide();");
            PrimeFaces.current().ajax().update("frmPrincipal:dtTipo");
            genericaEjb.edit(selected);
            reset();
            return;
        }
        MovilidadUtil.addErrorMessage("Puntos Conciliados No Valido");
        PrimeFaces.current().ajax().update("msgs");
    }

    public void reset() {
        selected = null;
        i_puntosConciliados = 0;
//        PrimeFaces.current().ajax().update(":frmPrincipal:dtTipo");
    }

    public List<Generica> getLista() {
        return lista;
    }

    public void setLista(List<Generica> lista) {
        this.lista = lista;
    }

    public Generica getNovedad() {
        return novedad;
    }

    public void setNovedad(Generica novedad) {
        this.novedad = novedad;
    }

    public Generica getSelected() {
        return selected;
    }

    public void setSelected(Generica selected) {
        this.selected = selected;
    }

    public GenericaTipo getGenericaTipo() {
        return novedadTipo;
    }

    public void setGenericaTipo(GenericaTipo novedadTipo) {
        this.novedadTipo = novedadTipo;
    }

    public GenericaTipoDetalles getGenericaTipoDetalles() {
        if (genericaTipoDetalles == null) {
            genericaTipoDetalles = new GenericaTipoDetalles();
        }
        return genericaTipoDetalles;
    }

    public void setGenericaTipoDetalles(GenericaTipoDetalles genericaTipoDetalles) {
        this.genericaTipoDetalles = genericaTipoDetalles;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<GenericaTipo> getLstGenericaTipos() {
        return lstGenericaTipos;
    }

    public void setLstGenericaTipos(List<GenericaTipo> lstGenericaTipos) {
        this.lstGenericaTipos = lstGenericaTipos;
    }

    public List<GenericaTipoDetalles> getLstGenericaTipoDetalles() {
        return lstGenericaTipoDetalles;
    }

    public void setLstGenericaTipoDetalles(List<GenericaTipoDetalles> lstGenericaTipoDetalles) {
        this.lstGenericaTipoDetalles = lstGenericaTipoDetalles;
    }

    public List<Empleado> getLstEmpleados() {
        return lstEmpleados;
    }

    public void setLstEmpleados(List<Empleado> lstEmpleados) {
        this.lstEmpleados = lstEmpleados;
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

    public boolean isB_rol() {
        return b_rol;
    }

    public void setB_rol(boolean b_rol) {
        this.b_rol = b_rol;
    }

    public Integer getI_puntosConciliados() {
        return i_puntosConciliados;
    }

    public void setI_puntosConciliados(Integer i_puntosConciliados) {
        this.i_puntosConciliados = i_puntosConciliados;
    }

    public String getC_vehiculo() {
        return c_vehiculo;
    }

    public void setC_vehiculo(String c_vehiculo) {
        this.c_vehiculo = c_vehiculo;
    }

    public boolean isB_VerificacionSinFechas() {
        return b_VerificacionSinFechas;
    }

    public void setB_VerificacionSinFechas(boolean b_VerificacionSinFechas) {
        this.b_VerificacionSinFechas = b_VerificacionSinFechas;
    }

    public boolean isB_VerificacionPM() {
        return b_VerificacionPM;
    }

    public void setB_VerificacionPM(boolean b_VerificacionPM) {
        this.b_VerificacionPM = b_VerificacionPM;
    }

    public List<String> getFotosNovedades() {
        return fotosNovedades;
    }

    public void setFotosNovedades(List<String> fotosNovedades) {
        this.fotosNovedades = fotosNovedades;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<Generica> getListaFilter() {
        return listaFilter;
    }

    public void setListaFilter(List<Generica> listaFilter) {
        this.listaFilter = listaFilter;
    }

    public int getUsrArea() {
        return usrArea;
    }

    public ParamAreaUsr getParamAreaUsr() {
        return paramAreaUsr;
    }

    public void setParamAreaUsr(ParamAreaUsr paramAreaUsr) {
        this.paramAreaUsr = paramAreaUsr;
    }

    public void setUsrArea(int usrArea) {
        this.usrArea = usrArea;
    }

    public boolean isB_BtNuevo() {
        return b_BtNuevo;
    }

    public void setB_BtNuevo(boolean b_BtNuevo) {
        this.b_BtNuevo = b_BtNuevo;
    }

    public List<UploadedFile> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<UploadedFile> archivos) {
        this.archivos = archivos;
    }

    public GenericaSeguimiento getGenericaSeguimiento() {
        return genericaSeguimiento;
    }

    public void setGenericaSeguimiento(GenericaSeguimiento genericaSeguimiento) {
        this.genericaSeguimiento = genericaSeguimiento;
    }

    public GenericaSeguimiento getSelectedSeguimiento() {
        return selectedSeguimiento;
    }

    public void setSelectedSeguimiento(GenericaSeguimiento selectedSeguimiento) {
        this.selectedSeguimiento = selectedSeguimiento;
    }

    public GenericaDocumentos getGenericaDocumento() {
        return genericaDocumento;
    }

    public void setGenericaDocumento(GenericaDocumentos genericaDocumento) {
        this.genericaDocumento = genericaDocumento;
    }

    public GenericaDocumentos getSelectedDocumento() {
        return selectedDocumento;
    }

    public void setSelectedDocumento(GenericaDocumentos selectedDocumento) {
        this.selectedDocumento = selectedDocumento;
    }

    public GenericaTipoDocumentos getGenericaTipoDocumentos() {
        return genericaTipoDocumentos;
    }

    public void setGenericaTipoDocumentos(GenericaTipoDocumentos genericaTipoDocumentos) {
        this.genericaTipoDocumentos = genericaTipoDocumentos;
    }

    public GenericaTipo getNovedadTipo() {
        return novedadTipo;
    }

    public void setNovedadTipo(GenericaTipo novedadTipo) {
        this.novedadTipo = novedadTipo;
    }

    public List<GenericaSeguimiento> getLstSeguimientos() {
        if (selected != null) {
            lstSeguimientos = genericaSeguimientoEjb.findByNovedad(selected.getIdGenerica());
        }
        return lstSeguimientos;
    }

    public void setLstSeguimientos(List<GenericaSeguimiento> lstSeguimientos) {
        this.lstSeguimientos = lstSeguimientos;
    }

    public List<GenericaDocumentos> getLstDocumentos() {
        if (selected != null) {
            lstDocumentos = genericaDocumentosEjb.findByIdNovedad(selected.getIdGenerica());
        }
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

    public StreamedContent getFileReporte() {
        return fileReporte;
    }

    public void setFileReporte(StreamedContent fileReporte) {
        this.fileReporte = fileReporte;
    }

}
