package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.AccNovedadInfraestrucDocumentosFacadeLocal;
import com.movilidad.ejb.AccNovedadInfraestrucFacadeLocal;
import com.movilidad.ejb.AccNovedadInfraestrucSeguimientoFacadeLocal;
import com.movilidad.ejb.AccNovedadInfraestrucTipoDocumentosFacadeLocal;
import com.movilidad.ejb.AccNovedadInfrastucEstadoFacadeLocal;
import com.movilidad.ejb.AccNovedadTipoInfrastrucFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.AccNovedadInfraestruc;
import com.movilidad.model.AccNovedadInfraestrucDocumentos;
import com.movilidad.model.AccNovedadInfraestrucSeguimiento;
import com.movilidad.model.AccNovedadInfraestrucTipoDocumentos;
import com.movilidad.model.AccNovedadInfrastucEstado;
import com.movilidad.model.AccNovedadTipoDetallesInfrastruc;
import com.movilidad.model.AccNovedadTipoInfrastruc;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "accNovedadInfraestrucBean")
@ViewScoped
public class AccNovedadInfraestrucBean implements Serializable {

    @EJB
    private AccNovedadInfraestrucFacadeLocal novedadEjb;
    @EJB
    private AccNovedadInfraestrucSeguimientoFacadeLocal novedadSeguimientoEjb;
    @EJB
    private AccNovedadInfraestrucDocumentosFacadeLocal novedadDocumentosEjb;
    @EJB
    private AccNovedadInfraestrucTipoDocumentosFacadeLocal novedadTipoDocumentosEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private AccNovedadInfrastucEstadoFacadeLocal accNovedadInfrastucEstadoEjb;

    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private AccNovedadTipoInfrastrucFacadeLocal novedadTipoEjb;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;

    @Inject
    private ArchivosJSFManagedBean archivosBean;
    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;

    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private UploadedFile file;
    private AccNovedadInfraestruc novedad;
    private AccNovedadInfraestruc selected;
    private AccNovedadInfraestrucSeguimiento novedadSeguimiento;
    private AccNovedadInfraestrucSeguimiento selectedSeguimiento;
    private AccNovedadInfraestrucDocumentos novedadDocumento;
    private AccNovedadInfraestrucDocumentos selectedDocumento;
    private AccNovedadInfraestrucTipoDocumentos novedadTipoDocumentos;
    private AccNovedadTipoInfrastruc novedadTipo;
    private AccNovedadTipoDetallesInfrastruc novedadTipoDetalles;
    private StreamedContent fileDescargar;
    private Empleado empleado;
    private Date fechaInicio;
    private Date fechaFin;
    private int height = 0;
    private int width = 0;
    private Integer i_novedadInfrastucEstado;
    private String c_vehiculo = "";
    private String tamanoAccNovedadInfraestrucSeguimiento;
    private String tamanoAccNovedadInfraestrucDocumento;

    //georeferencia
    private String cLatitud = "4.646189";
    private String cLogitud = "-74.078540";
    private MapModel simpleModel;

    private List<AccNovedadInfraestruc> lista;
    private List<AccNovedadInfraestruc> listaFilter;
    private List<AccNovedadInfraestrucSeguimiento> lstSeguimientos;
    private List<AccNovedadInfraestrucDocumentos> lstDocumentos;
    private List<AccNovedadInfraestrucTipoDocumentos> lstAccNovedadInfraestrucTipoDocumentos;
    private List<AccNovedadTipoInfrastruc> lstAccNovedadTipoInfrastrucs;
    private List<AccNovedadTipoDetallesInfrastruc> lstAccNovedadTipoDetallesInfrastruc;
    private List<AccNovedadInfrastucEstado> lstAccNovedadInfrastucEstados;
    private List<Empleado> lstEmpleados;
    private List<UploadedFile> archivos;
    private List<String> fotosAccNovedadInfraestruces;

    private boolean controlAccidente;
    private boolean flagEditarArchivoSegumiento;

    @PostConstruct
    public void init() {
        lista = novedadEjb.findAllByEstadoReg();
        this.fotosAccNovedadInfraestruces = new ArrayList<>();
        novedad = new AccNovedadInfraestruc();
        novedadTipo = new AccNovedadTipoInfrastruc();
        fechaInicio = new Date();
        fechaFin = new Date();
        novedadSeguimiento = new AccNovedadInfraestrucSeguimiento();
        selectedSeguimiento = null;
        novedadDocumento = new AccNovedadInfraestrucDocumentos();
        novedadTipoDocumentos = new AccNovedadInfraestrucTipoDocumentos();
        selectedDocumento = null;
        archivos = new ArrayList<>();
        selected = null;
        file = null;
        fileDescargar = null;
        controlAccidente = false;
    }

    /**
     * Prepara la lista de empleados antes de registrar/modificar una novedad
     */
    public void prepareListEmpleados() {
        this.empleado = new Empleado();
        lstEmpleados = empleadoEjb.findAll();
    }

    /**
     * Prepara los tipos de novedades antes de registrar/modificar una novedad
     *
     * @param principal especifica si el proceso se hace desde la vista del
     * panel principal ó del maestro de novedades
     *
     */
    public void prepareListAccNovedadTipoInfrastruc(boolean principal) {
        this.novedadTipo = new AccNovedadTipoInfrastruc();
        lstAccNovedadTipoInfrastrucs = novedadTipoEjb.findAllByEstadoReg();

        if (principal) {
            PrimeFaces.current().executeScript("PF('dtNovedadTiposPP').clearFilters();");
            PrimeFaces.current().ajax().update(":frmPmNovedadTipoListPP:dtNovedadTipo");
        } else {
            PrimeFaces.current().executeScript("PF('dtNovedadTipos').clearFilters();");
            PrimeFaces.current().ajax().update(":frmPmNovedadTipoList:dtNovedadTipo");

        }
    }

    /**
     * Prepara los detalles del tipo seleccionado antes de registrar/modificar
     * una novedad
     *
     * @param principal especifica si el proceso se hace desde la vista del
     * panel principal ó del maestro de novedades
     */
    public void prepareListAccNovedadTipoInfrastrucDetalle(boolean principal) {
        lstAccNovedadTipoDetallesInfrastruc = null;
        this.novedadTipoDetalles = new AccNovedadTipoDetallesInfrastruc();

        if (novedadTipo != null) {
            lstAccNovedadTipoDetallesInfrastruc = new ArrayList<>();
            lstAccNovedadTipoDetallesInfrastruc = novedadTipo.getAccNovedadTipoDetallesInfrastrucList();
        }
        if (principal) {
            PrimeFaces.current().executeScript("PF('dtNovedadTipoDetallePP').clearFilters();");
        } else {
            PrimeFaces.current().executeScript("PF('dtNovedadTipoDetalle').clearFilters();");
        }

    }

    /**
     * Prepara la lista de tipos de documentos antes de anexar un documento a
     * una novedad
     */
    public void prepareListAccNovedadInfraestrucTipoDocumentos() {
        this.novedadTipoDocumentos = new AccNovedadInfraestrucTipoDocumentos();
        lstAccNovedadInfraestrucTipoDocumentos = novedadTipoDocumentosEjb.findAll();
    }

    /**
     * Evento que se dispara al seleccionar una novedad de la bitácora
     *
     * @param event
     */
    public void onRowSelect(SelectEvent event) {
        setSelected((AccNovedadInfraestruc) event.getObject());
    }

    /**
     * Evento que se dispara al seleccionar un empleado en el
     * registro/modificación de una novedad
     *
     * @param event
     */
    public void onEmpleadoChosen(SelectEvent event) {
        if (event.getObject() instanceof Empleado) {
            setEmpleado((Empleado) event.getObject());
        }
    }

    /**
     * Evento que se dispara al seleccionar un tipo de novedad en el
     * registro/modificación de una novedad
     *
     * @param event
     */
    public void onAccNovedadTipoInfrastrucChosen(SelectEvent event) {
        if (event.getObject() instanceof AccNovedadTipoInfrastruc) {
            setAccNovedadTipoInfrastruc((AccNovedadTipoInfrastruc) event.getObject());
        }
    }

    /**
     * Evento que se dispara al seleccionar un detalle de tipo en el
     * registro/modificación de una novedad
     *
     * @param event
     */
    public void onAccNovedadTipoInfrastrucDetalleChosen(SelectEvent event) {
        if (event.getObject() instanceof AccNovedadTipoDetallesInfrastruc) {
            setAccNovedadTipoDetallesInfrastruc((AccNovedadTipoDetallesInfrastruc) event.getObject());
        }
    }

    /**
     * Evento que se dispara al seleccionar un tipo de documento al anexar
     * documento de una novedad
     *
     * @param event
     */
    public void onAccNovedadInfraestrucTipoDocumentosChosen(SelectEvent event) {
        setAccNovedadInfraestrucTipoDocumentos((AccNovedadInfraestrucTipoDocumentos) event.getObject());
    }

    /**
     * Evento que se dispara al seleccionar un tipo de novedad en el modal que
     * muestra los tipos de novedades, y asigna el tipo de novedad seleccionado
     * al valor para la vista
     *
     * @param event
     */
    public void onRowAccNovedadTipoInfrastrucClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof AccNovedadTipoInfrastruc) {
            setAccNovedadTipoInfrastruc((AccNovedadTipoInfrastruc) event.getObject());
        }
        this.novedadTipoDetalles = new AccNovedadTipoDetallesInfrastruc();
        this.novedadTipoDetalles.setNombre("");
        MovilidadUtil.clearFilter("dtNovedadTipos");
        MovilidadUtil.updateComponent("frmPmNovedadTipoList:dtNovedadTipo");
        MovilidadUtil.updateComponent("frmNovedadesPm:novedad_tipo_detalle");
        MovilidadUtil.clearFilter("dtNovedadTipoDetalle");
        MovilidadUtil.updateComponent("frmPmNovedadTipoDetalleList:dtNovedadTipoDetalles");
    }

    /**
     * Evento que se dispara al seleccionar un detalle de novedad en el modal
     * que muestra los detalles de tipos
     *
     * @param event
     */
    public void onRowAccNovedadTipoDetallesInfrastrucClckSelect(final SelectEvent event) {
        PrimeFaces current = PrimeFaces.current();
        if (event.getObject() instanceof AccNovedadTipoDetallesInfrastruc) {
            setAccNovedadTipoDetallesInfrastruc((AccNovedadTipoDetallesInfrastruc) event.getObject());
        }
        current.executeScript("PF('dtNovedadTipoDetalle').clearFilters();");
        current.ajax().update(":frmPmNovedadTipoDetalleList:dtNovedadTipoDetalles");
    }

    /**
     * Evento que se dispara al seleccionar un empleado en el modal que muestra
     * listado de empleados
     *
     * @param event
     */
    public void onRowEmpleadoClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof Empleado) {
            setEmpleado((Empleado) event.getObject());
        }
//        System.out.println("Empleado: " + empleado.getNombres() + " " + empleado.getApellidos());
        PrimeFaces.current().executeScript("PF('wVPmEmpleadosListDialog').clearFilters();");
        PrimeFaces.current().ajax().update(":frmPmEmpleadoList:dtEmpleados");
    }

    /**
     * Evento que se dispara al seleccionar tipo de documento en el modal que
     * muestra listado de tipos de documentos
     *
     * @param event
     */
    public void onRowDblClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof AccNovedadInfraestrucTipoDocumentos) {
            setAccNovedadInfraestrucTipoDocumentos((AccNovedadInfraestrucTipoDocumentos) event.getObject());
        }
        PrimeFaces.current().executeScript("PF('dtNovedadDocumento').clearFilters();");
        PrimeFaces.current().ajax().update(":frmNovedadTipoDocumentosList:dtNovedadDocumentos");
    }

    /**
     * Setea en NULL el objeto seleccionado
     */
    public void resetSelected() {
        selected = null;
    }

    /**
     * Carga los datos antes de crear una novedad
     */
    public void nuevo() {
        novedad = new AccNovedadInfraestruc();
        novedadTipo = new AccNovedadTipoInfrastruc();
        novedadTipoDetalles = new AccNovedadTipoDetallesInfrastruc();
        novedadTipoDetalles.setNombre("");
        empleado = new Empleado();
        novedadSeguimiento = new AccNovedadInfraestrucSeguimiento();
        selectedSeguimiento = null;
        novedadDocumento = new AccNovedadInfraestrucDocumentos();
        novedadTipoDocumentos = new AccNovedadInfraestrucTipoDocumentos();
        selectedDocumento = null;
        flagEditarArchivoSegumiento = false;
        lstAccNovedadInfrastucEstados = accNovedadInfrastucEstadoEjb.findAllByEstadoReg();
        tamanoAccNovedadInfraestrucSeguimiento = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_NOVEDAD_INFRA_SEG_TAMANO);
        tamanoAccNovedadInfraestrucDocumento = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_NOVEDAD_INFRA_DOC_TAMANO);
        archivos = new ArrayList<>();
        c_vehiculo = "";
    }

    /**
     * Persiste en base de datos un seguimiento realizado a una novedad, y lo
     * agrega al listado de seguimientos
     */
    public void guardarSeguimiento() {
        if (selected == null) {
            MovilidadUtil.addAdvertenciaMessage("Debe seleccionar una novedad para aplicarle seguimiento. ");
            return;
        }
        novedadSeguimiento.setEstadoReg(0);
        novedadSeguimiento.setIdAccNovedadInfraestruc(selected);
        novedadSeguimiento.setUsername(user.getUsername());
        novedadSeguimiento.setCreado(new Date());
        this.novedadSeguimientoEjb.create(novedadSeguimiento);

        if (archivos.size() > 0) {
            String pathArchivo;
            for (UploadedFile f : archivos) {
                pathArchivo = Util.saveFile(f, novedadSeguimiento.getIdAccNovedadInfraestrucSeguimiento(), "novedad_infra_seg");
                novedadSeguimiento.setPath(pathArchivo);
                novedadSeguimientoEjb.edit(novedadSeguimiento);
            }
            archivos.clear();
        }

        this.lista = this.novedadEjb.findAllByEstadoReg();
        PrimeFaces.current().ajax().update(":frmPrincipal:dtTipo");
        this.lstSeguimientos.add(novedadSeguimiento);

        if (selected.getAccNovedadInfraestrucSeguimientoList() == null) {
            selected.setAccNovedadInfraestrucSeguimientoList(new ArrayList<>());
            selected.getAccNovedadInfraestrucSeguimientoList().add(novedadSeguimiento);
        } else {
            selected.getAccNovedadInfraestrucSeguimientoList().add(novedadSeguimiento);
        }

        nuevo();
        MovilidadUtil.addSuccessMessage("Seguimiento de novedad registrado éxitosamente.");
        MovilidadUtil.hideModal("novedadSeguimiento");
    }

    /**
     * Carga datos de una novedad en la vista de edición
     */
    public void editar() {
        this.novedad = this.selected;
        tamanoAccNovedadInfraestrucSeguimiento = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_NOVEDAD_INFRA_SEG_TAMANO);
        tamanoAccNovedadInfraestrucDocumento = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_NOVEDAD_INFRA_DOC_TAMANO);
        this.novedadTipo = this.novedad.getIdAccNovedadTipoInfrastruc();
        this.novedadTipoDetalles = this.novedad.getIdAccNovedadTipoDetalleInfrastruc();
        this.empleado = this.novedad.getIdEmpleado();
        i_novedadInfrastucEstado = selected.getIdAccNovedadInfrastucEstado().getIdAccNovedadInfrastucEstado();
        lstAccNovedadInfrastucEstados = accNovedadInfrastucEstadoEjb.findAllByEstadoReg();

        simpleModel = new DefaultMapModel();
        LatLng latlngNovedad = new LatLng(novedad.getLatitud().doubleValue(), novedad.getLongitud().doubleValue());
        simpleModel.addOverlay(new Marker(latlngNovedad, "Coordenadas"));

        if (selectedSeguimiento != null) {
            this.novedadSeguimiento = this.selectedSeguimiento;
        }
        if (selectedDocumento != null) {
            this.novedadDocumento = this.selectedDocumento;
            this.novedadTipoDocumentos = this.novedadDocumento.getIdAccNovedadInfraestrucTipoDocumentos();
        }

        this.archivos.clear();

    }

    /**
     * Método que se encarga de mostrar mapa en base a las coordenadas guardadas
     */
    public void cargarMapa() {
        simpleModel = new DefaultMapModel();
        LatLng latlngNovedad = new LatLng(selected.getLatitud().doubleValue(), selected.getLongitud().doubleValue());
        simpleModel.addOverlay(new Marker(latlngNovedad, "Coordenadas"));
    }

    /**
     * Modifica el registro de un seguimiento realizado a una novedad
     */
    public void actualizarSeguimiento() {
        novedadSeguimiento.setIdAccNovedadInfraestruc(selected);
        novedadSeguimiento.setUsername(user.getUsername());
        novedadSeguimiento.setCreado(new Date());
        this.novedadSeguimientoEjb.edit(novedadSeguimiento);

        if (archivos.size() > 0) {
            String pathArchivo;
            if (novedadSeguimiento.getPath() != null) {
                if (novedadSeguimiento.getPath().endsWith("/")) {
                    borrarImagenesSeguimiento(novedadSeguimiento.getIdAccNovedadInfraestrucSeguimiento(), novedadSeguimiento.getPath());
                } else {
                    Util.deleteFile(novedadSeguimiento.getPath());
                }
            }
            for (UploadedFile f : archivos) {
                pathArchivo = Util.saveFile(f, novedadSeguimiento.getIdAccNovedadInfraestrucSeguimiento(), "novedad_infra_seg");
                novedadSeguimiento.setPath(pathArchivo);
                novedadSeguimientoEjb.edit(novedadSeguimiento);
            }
            archivos.clear();
        }

        MovilidadUtil.hideModal("novedadSeguimiento");
        MovilidadUtil.addSuccessMessage("Seguimiento de novedad actualizado éxitosamente.");
    }

    /**
     * Realiza la descarga del archivo para mostrar el archivo en la vista
     *
     * @param path
     * @param nombreArchivo
     * @throws Exception
     */
    public void prepDownload(String path, String nombreArchivo) throws Exception {
        String fe = "";
        InputStream stream;
        File archivo = new File(path);
        String tipoArchivo = Files.probeContentType(archivo.toPath());
        int i = path.lastIndexOf('.');
        if (i > 0) {
            fe = path.substring(i + 1).toLowerCase();
            switch (fe) {
                case "mp4":
                    stream = new FileInputStream(archivo);
                    fileDescargar = new DefaultStreamedContent(stream, "video/mp4", nombreArchivo);
                    break;
                default:
                    stream = new FileInputStream(archivo);
                    fileDescargar = new DefaultStreamedContent(stream, tipoArchivo, nombreArchivo);
                    break;
            }
        }
    }

    /**
     * Método que se encarga de retornar el nombre original de un documento
     *
     * @param path
     * @return
     */
    public String obtenerNombreDocumento(String path) {
        String[] pathArr = path.split("_");
        return pathArr[pathArr.length - 1];
    }

    /**
     * Persiste registro de documento anexado a una novedad a la base de datos,
     * y luego lo agrega a la lista de documentos ya registrados
     */
    @Transactional
    public void guardarAccNovedadInfraestrucDocumentoTransactional() {
        novedadDocumento.setIdAccNovedadInfraestruc(selected);
        novedadDocumento.setIdAccNovedadInfraestrucTipoDocumentos(novedadTipoDocumentos);
        novedadDocumento.setUsuario(user.getUsername());
        novedadDocumento.setCreado(new Date());
        if (archivos.isEmpty()) {
            PrimeFaces.current().ajax().update("frmAccNovedadInfraestrucesPm:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un documento a cargar");
            return;
        } else {
            String path_documento = "";
            for (UploadedFile f : archivos) {
                this.novedadDocumentosEjb.create(novedadDocumento);
                path_documento = Util.saveFile(f, novedadDocumento.getIdAccNovedadInfraestrucDocumento(), "novedad_infra_docs");
                novedadDocumento.setPathDocumento(path_documento);
                this.novedadDocumentosEjb.edit(novedadDocumento);
            }
            archivos.clear();
        }
        this.lista = this.novedadEjb.findAllByEstadoReg();
        this.lstDocumentos.add(novedadDocumento);

        if (selected.getAccNovedadInfraestrucDocumentosList() == null) {
            selected.setAccNovedadInfraestrucDocumentosList(new ArrayList<>());
            selected.getAccNovedadInfraestrucDocumentosList().add(novedadDocumento);
        } else {
            selected.getAccNovedadInfraestrucDocumentosList().add(novedadDocumento);
        }

        nuevo();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Documento anexado a novedad éxitosamente."));
    }

    public void guardarAccNovedadInfraestrucDocumento() {
        guardarAccNovedadInfraestrucDocumentoTransactional();
    }

    /**
     * Actualiza registro de documento anexado a una novedad
     */
    @Transactional
    public void actualizarAccNovedadInfraestrucDocumentoTransactional() {
        PrimeFaces current = PrimeFaces.current();
        novedadDocumento.setIdAccNovedadInfraestruc(selected);
        novedadDocumento.setIdAccNovedadInfraestrucTipoDocumentos(novedadTipoDocumentos);
        novedadDocumento.setUsuario(user.getUsername());
        novedadDocumento.setModificado(new Date());
        this.novedadDocumentosEjb.edit(novedadDocumento);

        if (!archivos.isEmpty()) {
            String path_documento;
            if (novedadDocumento.getPathDocumento() != null) {
                if (novedadDocumento.getPathDocumento().endsWith("/")) {
                    borrarImagenesAccNovedadInfraestruc(novedadDocumento.getIdAccNovedadInfraestrucDocumento(), novedadDocumento.getPathDocumento());
                } else {
                    Util.deleteFile(novedadDocumento.getPathDocumento());
                }
            }
            for (UploadedFile f : archivos) {
                path_documento = Util.saveFile(f, novedadDocumento.getIdAccNovedadInfraestrucDocumento(), "novedad_infra_docs");
                novedadDocumento.setPathDocumento(path_documento);
                this.novedadDocumentosEjb.edit(novedadDocumento);
            }
            archivos.clear();
        } else {
            PrimeFaces.current().ajax().update("frmAccNovedadInfraestrucesPm:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un documento a cargar");
            return;
        }
//        selected = null;
        current.executeScript("PF('novedadDocumentos').hide();");
        current.ajax().update(":frmPrincipal:dtTipo");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Documento de novedad actualizado éxitosamente."));
    }

    private void borrarImagenesAccNovedadInfraestruc(Integer idDocumento, String path) {
        List<String> lstImagenes = Util.getFileList(idDocumento, "novedad_infra_docs");

        if (lstImagenes != null) {
            for (String imagen : lstImagenes) {
                String ruta = path + imagen;
                Util.deleteFile(ruta);
            }
        }

    }

    private void borrarImagenesSeguimiento(Integer idDocumento, String path) {
        List<String> lstImagenes = Util.getFileList(idDocumento, "novedad_infra_seg");

        if (lstImagenes != null) {
            for (String imagen : lstImagenes) {
                String ruta = path + imagen;
                Util.deleteFile(ruta);
            }
        }

    }

    public void actualizarAccNovedadInfraestrucDocumento() {
        actualizarAccNovedadInfraestrucDocumentoTransactional();
    }

    /**
     * Persiste en base de datos el registro de una nueva novedad, y lo agrega a
     * la lista de novedades ( Bitácora). Además si la novedad pertenece al tipo
     * ACCIDENTE se persiste en la tabla de accidentes
     */
    public void guardarAccNovedadInfraestrucPM() {
        if (novedadTipo.getNombre() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo de novedad");
            return;
        }
        if (novedadTipoDetalles.getNombre() == null || novedadTipoDetalles.getNombre().equals("")) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un detalle de tipo de novedad");
            return;
        }
        if (empleado.getNombres() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un colaborador");
            return;
        }
        if (novedad.getFechaHoraCierre() != null) {
            if (Util.validarFechaCambioEstado(novedad.getFechaHoraReporte(), novedad.getFechaHoraCierre())) {
                MovilidadUtil.addErrorMessage("La fecha de cierre NO debe ser mayor a la fecha de reporte");
                return;
            }
        }
        novedad.setIdAccNovedadTipoInfrastruc(novedadTipo);
        novedad.setIdAccNovedadTipoDetalleInfrastruc(novedadTipoDetalles);
        novedad.setIdEmpleado(empleado);
        novedad.setIdAccNovedadInfrastucEstado(new AccNovedadInfrastucEstado(i_novedadInfrastucEstado));

        novedad.setUsername(user.getUsername());
        novedad.setEstadoReg(0);
        novedad.setCreado(MovilidadUtil.fechaCompletaHoy());
        this.novedadEjb.create(novedad);

        if (archivos.size() > 0) {
            String pathArchivo;
            novedadDocumento.setIdAccNovedadInfraestruc(novedad);
            novedadDocumento.setCreado(MovilidadUtil.fechaCompletaHoy());
            novedadDocumento.setUsuario(user.getUsername());
            novedadDocumento.setEstadoReg(0);
            novedadDocumentosEjb.create(novedadDocumento);

            for (UploadedFile f : archivos) {
                pathArchivo = Util.saveFile(f, novedadDocumento.getIdAccNovedadInfraestrucDocumento(), "novedad_infra_docs");
                novedadDocumento.setPathDocumento(pathArchivo);
                novedadDocumentosEjb.edit(novedadDocumento);
            }
            archivos.clear();
        }

        if (novedadTipoDetalles.getNotifica() == 1) {
            notificar();
        }

        nuevo();
        resetSelected();
        this.lista = this.novedadEjb.findAllByEstadoReg();
        PrimeFaces.current().executeScript("PF('dtAccNovedadInfraestruces').clearFilters();");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "AccNovedadInfraestruc  registrada correctamente."));
    }

    /**
     * Obtiene fotos de una novedad
     *
     * @param id
     * @param op
     * @param path
     * @throws IOException
     */
    public void obtenerFotosAccNovedadInfraestruc(Integer id, String op, String path) throws IOException {
        List<String> lstNombresImg = Util.getFileList(id, op);

        if (lstNombresImg != null) {
            width = 100;
            height = 100;
            for (String f : lstNombresImg) {
                fotosAccNovedadInfraestruces.add(f);
                Image i = Util.mostrarImagenN(f, path);
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

    /**
     * Modifica registro novedad, y actualiza los datos en la tabla de
     * accidentes ( Sí la novedad a modificar tiene como tipo ACCIDENTE)
     *
     */
    public void actualizarAccNovedadInfraestrucPM() {
        PrimeFaces current = PrimeFaces.current();

        if (novedadTipo.getNombre() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo de novedad");
            return;
        }
        if (novedadTipoDetalles.getNombre() == null || novedadTipoDetalles.getNombre().equals("")) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un detalle de tipo de novedad");
            return;
        }
        if (empleado.getNombres() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un operador");
            return;
        }

        if (novedad.getFechaHoraCierre() != null) {
            if (Util.validarFechaCambioEstado(novedad.getFechaHoraReporte(), novedad.getFechaHoraCierre())) {
                MovilidadUtil.addErrorMessage("La fecha de cierre NO debe ser mayor a la fecha de reporte");
                return;
            }
        }

        novedad.setIdAccNovedadTipoInfrastruc(novedadTipo);
        novedad.setIdAccNovedadTipoDetalleInfrastruc(novedadTipoDetalles);
        novedad.setIdEmpleado(empleado);
        novedad.setIdAccNovedadInfrastucEstado(new AccNovedadInfrastucEstado(i_novedadInfrastucEstado));

        novedad.setUsername(user.getUsername());
        novedad.setModificado(MovilidadUtil.fechaCompletaHoy());
        this.novedadEjb.edit(novedad);
        current.ajax().update("frmPrincipal:dtTipo");
        current.executeScript("PF('novedadesPM').hide()");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Novedad actualizada correctamente."));
    }

    /**
     * Realiza la muestra de pdf,imágenes,lista de imágenes de un documento que
     * halla sido anexado a una novedad
     */
    public void getDocumento() {
        String ext = "";
        fotosAccNovedadInfraestruces.clear();
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
            archivosBean.setModalHeader(selectedDocumento.getIdAccNovedadInfraestrucTipoDocumentos() != null ? selectedDocumento.getIdAccNovedadInfraestrucTipoDocumentos().getNombre().toUpperCase() : "DOCUMENTO");
            archivosBean.setPath(selectedDocumento.getPathDocumento());
        } else {
            try {
                obtenerFotosAccNovedadInfraestruc(selectedDocumento.getIdAccNovedadInfraestrucDocumento(), "novedad_infra_docs", selectedDocumento.getPathDocumento());
                archivosBean.setExtension(ext);
                archivosBean.setPath(selectedDocumento.getPathDocumento());
                archivosBean.setModalHeader(selectedDocumento.getIdAccNovedadInfraestrucTipoDocumentos() != null ? selectedDocumento.getIdAccNovedadInfraestrucTipoDocumentos().getNombre().toUpperCase() : "DOCUMENTO");
            } catch (IOException ex) {
                Logger.getLogger(AccNovedadInfraestrucBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Realiza la muestra de pdf,imágenes,lista de imágenes de un documento que
     * halla sido anexado a una novedad
     */
    public void getDocumentoSeg() {
        String ext = "";
        fotosAccNovedadInfraestruces.clear();
        if (!selectedSeguimiento.getPath().endsWith("/")) {
            ext = selectedSeguimiento.getPath().substring(selectedSeguimiento.getPath().lastIndexOf('.'), selectedSeguimiento.getPath().length());
            if (!ext.equals(".pdf")) {
                try {
                    Image i = Util.mostrarImagenN(selectedSeguimiento.getPath());
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
            archivosBean.setModalHeader("ARCHIVO SEGUIMIENTO");
            archivosBean.setPath(selectedSeguimiento.getPath());
        } else {
            try {
                obtenerFotosAccNovedadInfraestruc(selectedSeguimiento.getIdAccNovedadInfraestrucSeguimiento(), "novedad_infra_seg", selectedSeguimiento.getPath());
                archivosBean.setExtension(ext);
                archivosBean.setPath(selectedSeguimiento.getPath());
                archivosBean.setModalHeader("ARCHIVO SEGUIMIENTO");
            } catch (IOException ex) {
                Logger.getLogger(AccNovedadInfraestrucBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void onPointSelect(PointSelectEvent event) {
        try {
            simpleModel = new DefaultMapModel();
            LatLng latlng = event.getLatLng();
            novedad.setLatitud(BigDecimal.valueOf(latlng.getLat()));
            novedad.setLongitud(BigDecimal.valueOf(latlng.getLng()));
            simpleModel.addOverlay(new Marker(latlng, "Punto Seleccionado"));
            cLatitud = String.valueOf(latlng.getLat());
            cLogitud = String.valueOf(latlng.getLng());
            MovilidadUtil.addSuccessMessage("Coordenadas seleccionadas, Longitud:"
                    + cLogitud + " Latitud:" + cLatitud);
        } catch (Exception e) {
            System.out.println("Error Evento onpointSelect Novedad");
        }
    }

    /**
     *
     * Evento que dispara la subida de archivos para la anexarlos de documentos
     * de una novedad
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        PrimeFaces current = PrimeFaces.current();

        archivos.add(event.getFile());

        current.executeScript("PF('AddFilesListDialog').hide()");
        current.ajax().update(":frmNuevoDocumento:messages");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Documento agregado éxitosamente."));
    }

    /**
     *
     * Evento que dispara la subida de archivos para anexarlos a un seguimiento
     * de novedad
     *
     * @param event
     */
    public void handleFileUploadSeguimiento(FileUploadEvent event) {

        if (event.getFile().getFileName().length() > 50) {
            MovilidadUtil.updateComponent(":msgs");
            MovilidadUtil.updateComponent("frmAddFilesSeguimientos:messages");
            MovilidadUtil.addErrorMessage("El nombre de archivo DEBE ser MENOR 50 a caracteres");
            return;
        }

        archivos.add(event.getFile());

        MovilidadUtil.hideModal("AddFilesSeguimientoDialog");
        MovilidadUtil.hideModal("AddFileSeguimientosDialog");
        MovilidadUtil.updateComponent(":msgs");
        MovilidadUtil.addSuccessMessage("Archivo(s) agregado(s) éxitosamente");
    }

    /*
     * Parámetros para el envío de correos (AccNovedadInfraestruces PM)
     */
    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(Util.TEMPLATE_NOVEDADES_INFRAESTRUCTURA);
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
     * Realiza el envío de correo de las novedad registrada a las partes
     * interesadas
     */
    private void notificar() {
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("fecha_reporte", Util.dateTimeFormat(novedad.getFechaHoraReporte()));
        mailProperties.put("fecha_cierre", novedad.getFechaHoraCierre() != null ? Util.dateTimeFormat(novedad.getFechaHoraCierre()) : "");
        mailProperties.put("tipo", novedad.getIdAccNovedadTipoInfrastruc().getNombre());
        mailProperties.put("detalle", novedad.getIdAccNovedadTipoDetalleInfrastruc().getNombre());
        mailProperties.put("reportado_por", empleado != null ? empleado.getNombres() + " " + empleado.getApellidos() : "");
        mailProperties.put("estado", i_novedadInfrastucEstado != null ? novedad.getIdAccNovedadInfrastucEstado().getNombre() : "");
        mailProperties.put("username", "");
        mailProperties.put("generada", Util.dateTimeFormat(novedad.getCreado()));
        mailProperties.put("descripcion", novedad.getDescripcion());
        mailProperties.put("latitud", novedad.getLatitud());
        mailProperties.put("longitud", novedad.getLongitud());
        String subject = "Se ha registrado novedad de infraestructura";
        String destinatarios;

        destinatarios = novedad.getIdAccNovedadTipoDetalleInfrastruc().getEmails();

        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", null);
    }

    /**
     *
     */
    public void reset() {
        selected = new AccNovedadInfraestruc();
        i_novedadInfrastucEstado = 0;
//        PrimeFaces.current().ajax().update(":frmPrincipal:dtTipo");
    }

    public AccNovedadInfraestrucFacadeLocal getAccNovedadInfraestrucEjb() {
        return novedadEjb;
    }

    public void setAccNovedadInfraestrucEjb(AccNovedadInfraestrucFacadeLocal novedadEjb) {
        this.novedadEjb = novedadEjb;
    }

    public List<AccNovedadInfraestruc> getLista() {
        return lista;
    }

    public void setLista(List<AccNovedadInfraestruc> lista) {
        this.lista = lista;
    }

    public AccNovedadInfraestruc getAccNovedadInfraestruc() {
        return novedad;
    }

    public void setAccNovedadInfraestruc(AccNovedadInfraestruc novedad) {
        this.novedad = novedad;
    }

    public AccNovedadInfraestruc getSelected() {
        return selected;
    }

    public void setSelected(AccNovedadInfraestruc selected) {
        this.selected = selected;
    }

    public AccNovedadInfraestrucSeguimiento getAccNovedadInfraestrucSeguimiento() {
        return novedadSeguimiento;
    }

    public void setAccNovedadInfraestrucSeguimiento(AccNovedadInfraestrucSeguimiento novedadSeguimiento) {
        this.novedadSeguimiento = novedadSeguimiento;
    }

    public List<AccNovedadInfraestrucSeguimiento> getLstSeguimientos() {
        if (selected != null) {
            lstSeguimientos = novedadSeguimientoEjb.findByNovedad(selected.getIdAccNovedadInfraestruc());
        }
        return lstSeguimientos;
    }

    public void setLstSeguimientos(List<AccNovedadInfraestrucSeguimiento> lstSeguimientos) {
        this.lstSeguimientos = lstSeguimientos;
    }

    public AccNovedadInfraestrucSeguimiento getSelectedSeguimiento() {
        return selectedSeguimiento;
    }

    public void setSelectedSeguimiento(AccNovedadInfraestrucSeguimiento selectedSeguimiento) {
        this.selectedSeguimiento = selectedSeguimiento;
    }

    public AccNovedadInfraestrucDocumentos getAccNovedadInfraestrucDocumento() {
        return novedadDocumento;
    }

    public void setAccNovedadInfraestrucDocumento(AccNovedadInfraestrucDocumentos novedadDocumento) {
        this.novedadDocumento = novedadDocumento;
    }

    public AccNovedadInfraestrucDocumentos getSelectedDocumento() {
        return selectedDocumento;
    }

    public void setSelectedDocumento(AccNovedadInfraestrucDocumentos selectedDocumento) {
        this.selectedDocumento = selectedDocumento;
    }

    public List<AccNovedadInfraestrucDocumentos> getLstDocumentos() {
        if (selected != null) {
            lstDocumentos = novedadDocumentosEjb.findByNovedad(selected.getIdAccNovedadInfraestruc());
        }
        return lstDocumentos;
    }

    public void setLstDocumentos(List<AccNovedadInfraestrucDocumentos> lstDocumentos) {
        this.lstDocumentos = lstDocumentos;
    }

    public AccNovedadInfraestrucTipoDocumentos getAccNovedadInfraestrucTipoDocumentos() {
        return novedadTipoDocumentos;
    }

    public void setAccNovedadInfraestrucTipoDocumentos(AccNovedadInfraestrucTipoDocumentos novedadTipoDocumentos) {
        this.novedadTipoDocumentos = novedadTipoDocumentos;
    }

    public List<AccNovedadInfraestrucTipoDocumentos> getLstAccNovedadInfraestrucTipoDocumentos() {
        return lstAccNovedadInfraestrucTipoDocumentos;
    }

    public void setLstAccNovedadInfraestrucTipoDocumentos(List<AccNovedadInfraestrucTipoDocumentos> lstAccNovedadInfraestrucTipoDocumentos) {
        this.lstAccNovedadInfraestrucTipoDocumentos = lstAccNovedadInfraestrucTipoDocumentos;
    }

    public AccNovedadTipoInfrastruc getAccNovedadTipoInfrastruc() {
        return novedadTipo;
    }

    public void setAccNovedadTipoInfrastruc(AccNovedadTipoInfrastruc novedadTipo) {
        this.novedadTipo = novedadTipo;
    }

    public AccNovedadTipoDetallesInfrastruc getAccNovedadTipoDetallesInfrastruc() {
        if (novedadTipoDetalles == null) {
            novedadTipoDetalles = new AccNovedadTipoDetallesInfrastruc();
        }
        return novedadTipoDetalles;
    }

    public void setAccNovedadTipoDetallesInfrastruc(AccNovedadTipoDetallesInfrastruc novedadTipoDetalles) {
        this.novedadTipoDetalles = novedadTipoDetalles;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<AccNovedadTipoInfrastruc> getLstAccNovedadTipoInfrastrucs() {
        return lstAccNovedadTipoInfrastrucs;
    }

    public void setLstAccNovedadTipoInfrastrucs(List<AccNovedadTipoInfrastruc> lstAccNovedadTipoInfrastrucs) {
        this.lstAccNovedadTipoInfrastrucs = lstAccNovedadTipoInfrastrucs;
    }

    public List<AccNovedadTipoDetallesInfrastruc> getLstAccNovedadTipoDetallesInfrastruc() {
        return lstAccNovedadTipoDetallesInfrastruc;
    }

    public void setLstAccNovedadTipoDetallesInfrastruc(List<AccNovedadTipoDetallesInfrastruc> lstAccNovedadTipoDetallesInfrastruc) {
        this.lstAccNovedadTipoDetallesInfrastruc = lstAccNovedadTipoDetallesInfrastruc;
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

    public Integer getI_novedadInfrastucEstado() {
        return i_novedadInfrastucEstado;
    }

    public void setI_novedadInfrastucEstado(Integer i_novedadInfrastucEstado) {
        this.i_novedadInfrastucEstado = i_novedadInfrastucEstado;
    }

    public String getC_vehiculo() {
        return c_vehiculo;
    }

    public void setC_vehiculo(String c_vehiculo) {
        this.c_vehiculo = c_vehiculo;
    }

    public List<String> getFotosAccNovedadInfraestruces() {
        return fotosAccNovedadInfraestruces;
    }

    public void setFotosAccNovedadInfraestruces(List<String> fotosAccNovedadInfraestruces) {
        this.fotosAccNovedadInfraestruces = fotosAccNovedadInfraestruces;
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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<AccNovedadInfraestruc> getListaFilter() {
        return listaFilter;
    }

    public void setListaFilter(List<AccNovedadInfraestruc> listaFilter) {
        this.listaFilter = listaFilter;
    }

    public String getTamanoAccNovedadInfraestrucSeguimiento() {
        return tamanoAccNovedadInfraestrucSeguimiento;
    }

    public void setTamanoAccNovedadInfraestrucSeguimiento(String tamanoAccNovedadInfraestrucSeguimiento) {
        this.tamanoAccNovedadInfraestrucSeguimiento = tamanoAccNovedadInfraestrucSeguimiento;
    }

    public boolean isFlagEditarArchivoSegumiento() {
        return flagEditarArchivoSegumiento;
    }

    public void setFlagEditarArchivoSegumiento(boolean flagEditarArchivoSegumiento) {
        this.flagEditarArchivoSegumiento = flagEditarArchivoSegumiento;
    }

    public StreamedContent getFileDescargar() {
        return fileDescargar;
    }

    public void setFileDescargar(StreamedContent fileDescargar) {
        this.fileDescargar = fileDescargar;
    }

    public String getTamanoAccNovedadInfraestrucDocumento() {
        return tamanoAccNovedadInfraestrucDocumento;
    }

    public void setTamanoAccNovedadInfraestrucDocumento(String tamanoAccNovedadInfraestrucDocumento) {
        this.tamanoAccNovedadInfraestrucDocumento = tamanoAccNovedadInfraestrucDocumento;
    }

    public List<AccNovedadInfrastucEstado> getLstAccNovedadInfrastucEstados() {
        return lstAccNovedadInfrastucEstados;
    }

    public void setLstAccNovedadInfrastucEstados(List<AccNovedadInfrastucEstado> lstAccNovedadInfrastucEstados) {
        this.lstAccNovedadInfrastucEstados = lstAccNovedadInfrastucEstados;
    }

    public String getcLatitud() {
        return cLatitud;
    }

    public void setcLatitud(String cLatitud) {
        this.cLatitud = cLatitud;
    }

    public String getcLogitud() {
        return cLogitud;
    }

    public void setcLogitud(String cLogitud) {
        this.cLogitud = cLogitud;
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public void setSimpleModel(MapModel simpleModel) {
        this.simpleModel = simpleModel;
    }

}
