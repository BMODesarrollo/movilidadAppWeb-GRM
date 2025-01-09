package com.movilidad.jsf;

import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.ejb.ConfigFacadeLocal;
import com.movilidad.ejb.DispClasificacionNovedadFacadeLocal;
import com.movilidad.ejb.DispSistemaFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.InfraccionesFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionProcesosFacadeLocal;
import com.movilidad.ejb.NotificacionTelegramDetFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.NovedadDocumentosFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.NovedadSeguimientoDocsFacadeLocal;
import com.movilidad.ejb.NovedadSeguimientoFacadeLocal;
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.NovedadTipoDocumentosFacadeLocal;
import com.movilidad.ejb.NovedadTipoFacadeLocal;
import com.movilidad.ejb.NovedadTipoInfraccionFacadeLocal;
import com.movilidad.ejb.ParamCierreAusentismoFacadeLocal;
import com.movilidad.ejb.PqrMaestroFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.ConfigEmpresa;
import com.movilidad.model.DispClasificacionNovedad;
import com.movilidad.model.DispEstadoPendActual;
import com.movilidad.model.DispSistema;
import com.movilidad.model.Empleado;
import com.movilidad.model.Infracciones;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTelegramDet;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadDocumentos;
import com.movilidad.model.NovedadSeguimiento;
import com.movilidad.model.NovedadSeguimientoDocs;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.model.NovedadTipoDocumentos;
import com.movilidad.model.ParamCierreAusentismo;
import com.movilidad.model.PqrMaestro;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.CurrentLocation;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import com.movlidad.httpUtil.GeoService;
import com.movlidad.httpUtil.SenderNotificacionTelegram;
import java.awt.Image;
import java.io.IOException;
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
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
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
import org.primefaces.json.JSONObject;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "novedadBean")
@ViewScoped
public class NovedadJSFManagedBean implements Serializable {

    @EJB
    private NovedadFacadeLocal novedadEjb;
    @EJB
    private NovedadSeguimientoFacadeLocal novedadSeguimientoEjb;
    @EJB
    private NovedadDocumentosFacadeLocal novedadDocumentosEjb;
    @EJB
    private NovedadSeguimientoDocsFacadeLocal novedadSeguimientoDocsEjb;
    @EJB
    private NovedadTipoDocumentosFacadeLocal novedadTipoDocumentosEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;

    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NovedadTipoFacadeLocal novedadTipoEjb;
    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;
    @EJB
    private PqrMaestroFacadeLocal pqrMaestroEjb;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private NotificacionProcesosFacadeLocal notificacionProcesoEjb;
    @EJB
    private ConfigEmpresaFacadeLocal configEmpresaFacadeLocal;
    @EJB
    private NotificacionTelegramDetFacadeLocal notificacionTelegramDetEjb;
    @EJB
    private ParamCierreAusentismoFacadeLocal paramCierreAusentismoEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @EJB
    private PrgTcFacadeLocal prgTcEJB;
    @Inject
    private ArchivosJSFManagedBean archivosBean;
    @Inject
    private AccidenteJSF accidenteBean;
    @Inject
    private AccPreManagedBean accPreManagedBean;
    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;
    @Inject
    private MaximoBean maximoBean;
    @EJB
    private InfraccionesFacadeLocal infraccionesEJB;

    @Inject
    private PrgTcJSFManagedBean PrgTcJSFMB;

    @Inject
    private ValidarFinOperacionBean validarFinOperacionBean;

    @Inject
    private UploadFotoJSFManagedBean fotoJSFManagedBean;

    @Inject
    private NovedadDuplicadaBean novedadDuplicadaBean;

    @Inject
    private VehiculoEstadoHistoricoSaveBean vehiculoEstadoHistoricoSaveBean;

    @Inject
    private novedadTipoAndDetalleBean tipoAndDetalleBean;

    @Inject
    private NovedadDanoJSFManagedBean novedaDanoBean;
    @Inject
    private AtvNovedadBean atvNovedadBean;
    @Inject
    private SelectDispSistemaBean selectDispSistemaBean;
    @Inject
    private SelectNovedadTipoInfraccionBean selectNovedadTipoInfraccionBean;
    @EJB
    private DispClasificacionNovedadFacadeLocal clasificacionNovedadEJB;
    @EJB
    private DispSistemaFacadeLocal dispSistemaEjb;

    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTipoDetEjb;

    @EJB
    private NovedadTipoInfraccionFacadeLocal infraccionEJB;

    @EJB
    private PrgSerconFacadeLocal prgSerconEJB;
    @EJB
    private VehiculoFacadeLocal vehEJB;

    @Inject
    private AjusteJornadaFromGestionServicio ajusteJornadaFromGestionServicio;
    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private UploadedFile file;
    private Novedad novedad;
    private Novedad selected;

    // Objeto que guarda los datos de la novedad inicial reportada (Verificación Novedad)
    private Novedad novedadVerificacion;
    private NovedadSeguimientoDocs novedadSeguimientoDoc;
    private NovedadSeguimiento novedadSeguimiento;
    private NovedadSeguimiento selectedSeguimiento;
    private NovedadDocumentos novedadDocumento;
    private NovedadDocumentos selectedDocumento;
    private NovedadTipoDocumentos novedadTipoDocumentos;
    private StreamedContent fileDescargar;
    private Empleado empleado;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean b_rol = validarRol();
    private boolean b_VerificacionPM = false;
    private boolean b_VerificacionSinFechas = false;
    private int height = 0;
    private int width = 0;
    private Integer i_puntosConciliados;
    private String c_vehiculo = "";
    private String tamanoNovedadSeguimiento;
    private PqrMaestro pqrmaestro;

    private List<Novedad> lista;
    private List<Novedad> listaFilter;
    private List<NovedadSeguimiento> lstSeguimientos;
    private List<NovedadDocumentos> lstDocumentos;
    private List<NovedadTipoDocumentos> lstNovedadTipoDocumentos;
    private List<Empleado> lstEmpleados;
    private List<PqrMaestro> lstpqrMaestro;
    private List<UploadedFile> archivos;
    private List<String> fotosNovedades;

    private boolean controlAccidente;
    private boolean flagEditarArchivoSegumiento;
    private boolean viewCreateNovedadPP;
    private boolean createNovedadPP;
    private boolean inmovilizado;
    private boolean edit;
    private boolean flagDesasignarServicios;
    private boolean b_OwnerCreate = false;
    private boolean flagTC;

    @EJB
    private ConfigFacadeLocal configEJB;

    /**
     *
     * Verifica si una novedad_detalle procede de forma automática al
     * agregar/modificar una novedad
     *
     * @return true si el valor es IGUAL a 1, de lo contrario false
     */
    public boolean procedeNovedad() {
        try {
            return configEJB.findByKey("nov").getValue() == ConstantsUtil.ON_INT;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostConstruct
    public void init() {
        if (MovilidadUtil.validarUrl("/novedades/bitacora")) {
            fechaInicio = MovilidadUtil.fechaHoy();
            fechaFin = MovilidadUtil.fechaHoy();
            consultar();
        } else if (MovilidadUtil.validarUrl("panelPrincipal")) {
            if (SingletonConfigEmpresa.getMapConfiMapEmpresa().get("viewCreateNovedadPP") != null) {
                viewCreateNovedadPP = SingletonConfigEmpresa.getMapConfiMapEmpresa().get("viewCreateNovedadPP").equals(ConstantsUtil.ON_STRING);
            }
        }
        this.fotosNovedades = null;
        novedad = null;
        tipoAndDetalleBean.setNovedadTipo(null);
        tipoAndDetalleBean.setNovedadTipoDet(null);
        novedadSeguimiento = null;
        selectedSeguimiento = null;
        novedadDocumento = null;
        novedadTipoDocumentos = null;
        selectedDocumento = null;
        archivos = null;
        selected = null;
        file = null;
        fileDescargar = null;
        controlAccidente = false;
        flagDesasignarServicios = false;
        setTecnicoControl();
        flagTC = validarFinOperacionBean.isIsTecControl();
    }

    public void consultar() {
        this.lista = this.novedadEjb.findByDateRange(fechaInicio, fechaFin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    void cargarMapConfigEmpresa() {
        List<ConfigEmpresa> listCe = configEmpresaFacadeLocal.findEstadoReg();
        SingletonConfigEmpresa.setMapConfiMapEmpresa(new HashMap());
        for (ConfigEmpresa ce : listCe) {
            SingletonConfigEmpresa.getMapConfiMapEmpresa().put(ce.getLlave(), ce.getValor());
        }
    }

    /**
     * Valida si el operador es tecnico de control.
     *
     */
    public void setTecnicoControl() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            validarFinOperacionBean.setIsTecControl(auth.getAuthority().equals("ROLE_TC"));
        }
    }

    /**
     *
     * Devuelve grupo master de un empleado
     *
     * @param empl Empleado a buscar grupo master
     * @return nombre de grupo master
     */
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

    /**
     * Prepara la lista de empleados antes de registrar/modificar una novedad
     */
    public void prepareListEmpleados() {
        this.empleado = new Empleado();
        lstEmpleados = empleadoEjb.findAllByUnidadFuncacional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    /**
     * Prepara la lista de pqr abiertos
     */
    public void prepareListPqr() {
        this.pqrmaestro = new PqrMaestro();
        lstpqrMaestro = pqrMaestroEjb.findAllOpen(edit, novedad.getIdPqr());
    }

    /**
     * Prepara la lista de tipos de documentos antes de anexar un documento a
     * una novedad
     */
    public void prepareListNovedadTipoDocumentos() {
//        this.novedadTipoDocumentos = new NovedadTipoDocumentos();
        lstNovedadTipoDocumentos = novedadTipoDocumentosEjb.findAll();
    }

    /**
     * Evento que se dispara al seleccionar una novedad de la bitácora
     *
     * @param event
     */
    public void onRowSelect(SelectEvent event) {
        setSelected((Novedad) event.getObject());
        b_OwnerCreate = flagTC ? isOwnerCreate(selected.getIdNovedad(), user.getUsername(), MovilidadUtil.fechaCompletaHoy()) : true;
    }

    /**
     * Valida si el usuario logueado corresponde al Técnico de control que creó
     * la novedad seleccionada
     *
     * @return
     */
    private boolean isOwnerCreate(int idNovedad, String username, Date fecha) {
        return novedadEjb.findNovedadByUserCreate(idNovedad, username, fecha);
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
     * Evento que se dispara al seleccionar un tipo de documento al anexar
     * documento de una novedad
     *
     * @param event
     */
    public void onNovedadTipoDocumentosChosen(SelectEvent event) {
        setNovedadTipoDocumentos((NovedadTipoDocumentos) event.getObject());
    }

    /**
     * Evento que se dispara al seleccionar un tipo de documento en el modal que
     * muestra listado de tipos de documentos
     *
     * @param event
     */
    public void onRowTipoDocumentoClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof NovedadTipoDocumentos) {
            novedadTipoDocumentos = (NovedadTipoDocumentos) event.getObject();
        }
        MovilidadUtil.clearFilter("NovedadTipoDocumentosListDialog");
        MovilidadUtil.updateComponent("frmNovedadTipoDocumentosList:dtNovedadDocumentos");
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
            if (validarUF()) {
                setEmpleado(null);
            }
        }
//        System.out.println("Empleado: " + empleado.getNombres() + " " + empleado.getApellidos());
        MovilidadUtil.clearFilter("wVPmEmpleadosListDialog");
        MovilidadUtil.updateComponent("frmPmEmpleadoList:dtEmpleados");
    }

    /**
     * Evento que se dispara al seleccionar un empleado en el modal que muestra
     * listado de empleados
     *
     * @param event
     */
    public void onRowPqrClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof PqrMaestro) {
            setPqrmaestro((PqrMaestro) event.getObject());
            novedad.setIdPqr(pqrmaestro.getIdPqrMaestro());
        }
//        System.out.println("Empleado: " + empleado.getNombres() + " " + empleado.getApellidos());
        MovilidadUtil.clearFilter("dialog_pqr_dt_wv");
        MovilidadUtil.updateComponent("dialog_pqr_form:dialog_pqr_dt");
    }

    /**
     * Evento que se dispara al seleccionar un empleado en el modal que muestra
     * listado de empleados ( Panel principal )
     *
     * @param event
     */
    public void onRowEmpleadoClckSelectPP(final SelectEvent event) {
        if (event.getObject() instanceof Empleado) {
            setEmpleado((Empleado) event.getObject());
            if (validarUF()) {
                setEmpleado(null);
            }
        }
    }

    /**
     * Evento que se dispara al seleccionar tipo de documento en el modal que
     * muestra listado de tipos de documentos
     *
     * @param event
     */
    public void onRowDblClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof NovedadTipoDocumentos) {
            setNovedadTipoDocumentos((NovedadTipoDocumentos) event.getObject());
        }
        PrimeFaces.current().ajax().update(":frmNovedadTipoDocumentosList:dtNovedadDocumentos");
    }

    /**
     * Setea en NULL el objeto seleccionado
     */
    public void resetSelected() {
        selected = null;
        edit = false;
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

    /**
     * Realiza el cambio de formato a los campos: fecha y puntos PM antes de
     * exportar excel de bitácora de novedades
     *
     * @param document
     */
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
                if (cell.getColumnIndex() == 2 && !cell.getStringCellValue().isEmpty()) {
                    XSSFCellStyle cellStyle = wb.createCellStyle();
                    XSSFDataFormat format = wb.createDataFormat();
                    cellStyle.setDataFormat(format.getFormat("DD-MMMM-YYYY"));
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(cell.getStringCellValue().replace("\'", ""));
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                }
                if (cell.getColumnIndex() > 1) {
                    if (!cell.getStringCellValue().isEmpty()
                            && cell.getColumnIndex() == 15) {
                        cell.setCellValue(new BigDecimal(cell.getStringCellValue().replace("'", "")).doubleValue());
                    }
                }
            }
        }
    }

    public void obtenerFotosNovedadDano() throws IOException {
        if (fotosNovedades == null) {
            fotosNovedades = new ArrayList<>();
        } else {
            fotosNovedades.clear();
        }
        List<String> lstNombresImg = Util.getFileList(selected.getIdNovedadDano().getIdNovedadDano(), "danos");

        if (lstNombresImg != null) {
            for (String f : lstNombresImg) {
                f = selected.getIdNovedadDano().getPathFotos() + f;
                fotosNovedades.add(f);
            }
        }
        fotoJSFManagedBean.setListFotos(fotosNovedades);
        MovilidadUtil.openModal("galeria_foto_dialog_wv");
    }

    /**
     * Devuelve nombres de fotos pertenecientes a una novedad de daño, de la
     * novedad seleccionada
     *
     * @throws IOException
     */
//    public void obtenerFotosNovedadDano() throws IOException {
//        PrimeFaces current = PrimeFaces.current();
//        this.fotosNovedades.clear();
//        List<String> lstNombresImg = Util.getFileList(selected.getIdNovedadDano().getIdNovedadDano(), "danos");
//
//        if (lstNombresImg.size() > 0) {
//            width = 100;
//            height = 100;
//            for (String f : lstNombresImg) {
//                fotosNovedades.add(f);
//                Image i = Util.mostrarImagenN(f, selected.getIdNovedadDano().getPathFotos());
//                if (i != null) {
//                    if (width < i.getWidth(null)) {
//                        width = i.getWidth(null);
//                    }
//                    if (height < i.getHeight(null)) {
//                        height = i.getHeight(null);
//                    }
//                }
//            }
//            current.executeScript("PF('FotosListDialog').show()");
//        } else {
//            width = 100;
//            height = 100;
//            current.ajax().update("msgs");
//            MovilidadUtil.addErrorMessage("La novedad de daño no tiene imágenes asociadas");
//        }
//    }
    /**
     * Carga los datos antes de crear una novedad Documento
     *
     * @param fecha
     */
    public void nuevoDocumento() {
        novedadDocumento = new NovedadDocumentos();
        novedadTipoDocumentos = null;
        tamanoNovedadSeguimiento = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_NOVEDAD_SEGUIMIENTO_TAMANO);
        archivos = new ArrayList<>();
        selectedDocumento = null;
    }

    /**
     * Carga los datos antes de crear una novedad Documento
     *
     * @param fecha
     */
    public void nuevoSeguimiento() {
        novedadSeguimiento = new NovedadSeguimiento();
        novedadTipoDocumentos = null;
        tamanoNovedadSeguimiento = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_NOVEDAD_SEGUIMIENTO_TAMANO);
        archivos = new ArrayList<>();
    }

    /**
     * Carga los datos antes de crear una novedad
     *
     * @param fecha
     */
    public void nuevo(Date fecha) {
        if (fecha != null && validarFinOperacionBean.validarDiaBloqueado(fecha,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional())) {
            return;
        }
        resetNovedad();
        tamanoNovedadSeguimiento = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_NOVEDAD_SEGUIMIENTO_TAMANO);
        archivos = new ArrayList<>();
        tipoAndDetalleBean.setCompUpdateVistaCreateNov("frmNovedadesPm:novedad_tipo_detalle,frmNovedadesPm:SOMAtv_lbl,frmNovedadesPm:motivo_grp");
        c_vehiculo = "";
        createNovedadPP = true;
        novedad.setFecha(fecha);
        MovilidadUtil.openModal("novedadesPM");
    }

    /**
     * Carga los datos antes de crear una novedad
     *
     * @param fecha
     */
    public void resetNovedad() {
        novedad = new Novedad();
        tipoAndDetalleBean.setNovedadTipo(null);
        tipoAndDetalleBean.setNovedadTipoDet(null);
        empleado = null;
        novedadSeguimiento = null;
        selectedSeguimiento = null;
        novedadDocumento = null;
        novedadTipoDocumentos = null;
        selectedDocumento = null;
        flagEditarArchivoSegumiento = false;
        archivos = new ArrayList<>();
        c_vehiculo = "";
        inmovilizado = false;
        selectDispSistemaBean.setId_dis_sistema(null);
        atvNovedadBean.setB_atv(false);
        tipoAndDetalleBean.setPqrVisible(false);
        pqrmaestro = null;

    }

    /**
     *
     */
    public void nuevoAcc() {
        if (PrgTcJSFMB.getPrgTc() == null) {
            MovilidadUtil.addErrorMessage("Seleccione un servicio en la tabla");
            return;
        }
        novedad = new Novedad();
        empleado = PrgTcJSFMB.getPrgTc().getIdEmpleado();
        tipoAndDetalleBean.setNovedadTipoDet(null);
        selectedSeguimiento = null;
        selectedDocumento = null;
        tipoAndDetalleBean.setNovedadTipo(novedadTipoEjb.find(ConstantsUtil.ID_TIPO_NOVEDAD_ACC));

        if (PrgTcJSFMB.getPrgTc().getIdEmpleado() != null) {
            empleado.setNombres(PrgTcJSFMB.getPrgTc().getIdEmpleado().getNombres());
            empleado.setApellidos(PrgTcJSFMB.getPrgTc().getIdEmpleado().getApellidos());
            empleado.setIdEmpleado(PrgTcJSFMB.getPrgTc().getIdEmpleado().getIdEmpleado());
        }

        if (PrgTcJSFMB.getPrgTc().getIdVehiculo() != null) {
            c_vehiculo = PrgTcJSFMB.getPrgTc().getIdVehiculo().getCodigo();
            novedad.setIdVehiculo(PrgTcJSFMB.getPrgTc().getIdVehiculo());
        } else {
            c_vehiculo = "";
        }

        PrimeFaces.current().executeScript("PF('novedadesPMPP').show()");

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
        novedadSeguimiento.setIdNovedad(selected);
        novedadSeguimiento.setUsername(user.getUsername());
        novedadSeguimiento.setCreado(new Date());
        this.novedadSeguimientoEjb.create(novedadSeguimiento);

        if (archivos.size() > 0) {
            NovedadSeguimientoDocs doc;
            String pathArchivo;
            for (UploadedFile f : archivos) {
                doc = new NovedadSeguimientoDocs();
                doc.setNombreArchivo(f.getFileName());
                doc.setIdNovedadSeguimiento(novedadSeguimiento);
                doc.setUsername(user.getUsername());
                doc.setEstadoReg(0);
                doc.setCreado(MovilidadUtil.fechaCompletaHoy());
                pathArchivo = Util.saveFile(f, novedadSeguimiento.getIdNovedadSeguimiento(), "seguimiento_archivo");
                doc.setPathArchivo(pathArchivo);
                this.novedadSeguimientoDocsEjb.create(doc);
            }
            archivos.clear();
        }

        if (lstSeguimientos == null || lstSeguimientos.isEmpty()) {
            selected.setNovedadSeguimientoList(new ArrayList<>());
            selected.getNovedadSeguimientoList().add(novedadSeguimiento);
        } else {
            selected.getNovedadSeguimientoList().add(novedadSeguimiento);
        }

        this.lstSeguimientos.add(novedadSeguimiento);
        this.lista = this.novedadEjb.findByDateRange(fechaInicio, fechaFin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        nuevoSeguimiento();
        MovilidadUtil.addSuccessMessage("Seguimiento de novedad registrado éxitosamente.");
        MovilidadUtil.hideModal("novedadSeguimiento");
    }

    /**
     * Carga datos de una novedad en la vista de edición
     */
    public void editar() {
        this.novedad = this.selected;
        edit = true;
        tamanoNovedadSeguimiento = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_NOVEDAD_SEGUIMIENTO_TAMANO);
        tipoAndDetalleBean.setNovedadTipo(this.novedad.getIdNovedadTipo());
        tipoAndDetalleBean.setNovedadTipoDet(this.novedad.getIdNovedadTipoDetalle());
        this.empleado = this.novedad.getIdEmpleado();
        prepareListPqr();
        this.pqrmaestro.setIdPqrMaestro(this.novedad.getIdPqr());

        inmovilizado = novedad.getInmovilizado() == 1 ? true : false;
        if (selectedSeguimiento != null) {
            this.novedadSeguimiento = this.selectedSeguimiento;
        }
        if (selectedDocumento != null) {
            this.novedadDocumento = this.selectedDocumento;
            this.novedadTipoDocumentos = this.novedadDocumento.getIdNovedadTipoDocumento();
        }

        if (novedad.getIdNovedadTipo().getIdNovedadTipo() == 13) {
            tipoAndDetalleBean.setPqrVisible(true);
        } else {
            tipoAndDetalleBean.setPqrVisible(false);
        }

        if (novedad.getIdNovedadDano() != null) {
            novedaDanoBean.setSelected(novedad.getIdNovedadDano());
            novedaDanoBean.editar();
            MovilidadUtil.updateComponent("frmDano");
            MovilidadUtil.updateComponent("modalDano");
            MovilidadUtil.openModal("mtipo");
            return;
        }

//        this.archivos.clear();
        c_vehiculo = "";
        if (novedad.getIdVehiculo() != null) {
            c_vehiculo = novedad.getIdVehiculo().getCodigo();
        }
        if (novedad.getIdNovedadTipo() != null) {
            if (novedad.getIdNovedadTipo().getIdNovedadTipo().equals(Util.ID_ACCIDENTE)) {
                controlAccidente = true;
            }
        }
        selectDispSistemaBean.setId_dis_sistema(novedad.getIdDispClasificacionNovedad() == null ? null
                : novedad.getIdDispClasificacionNovedad().getIdDispSistema().getIdDispSistema());
        if (novedad.getIdNovedadTipoInfraccion() != null) {
            selectNovedadTipoInfraccionBean.setId_infraccion(novedad.getIdNovedadTipoInfraccion().getIdNovedadTipoInfraccion());
        }
        MovilidadUtil.openModal("novedadesPM");
    }

    /**
     * Modifica el registro de un seguimiento realizado a una novedad
     */
    public void actualizarSeguimiento() {
        novedadSeguimiento.setIdNovedad(selected);
        novedadSeguimiento.setUsername(user.getUsername());
        novedadSeguimiento.setCreado(new Date());
        this.novedadSeguimientoEjb.edit(novedadSeguimiento);

        if (archivos.size() > 0) {
            NovedadSeguimientoDocs doc;
            String pathArchivo;
            for (UploadedFile f : archivos) {
                doc = new NovedadSeguimientoDocs();
                doc.setNombreArchivo(f.getFileName());
                doc.setIdNovedadSeguimiento(novedadSeguimiento);
                doc.setUsername(user.getUsername());
                doc.setCreado(MovilidadUtil.fechaCompletaHoy());
                pathArchivo = Util.saveFile(f, novedadSeguimiento.getIdNovedadSeguimiento(), "seguimiento_archivo");
                doc.setPathArchivo(pathArchivo);
                this.novedadSeguimientoDocsEjb.create(doc);
            }
            archivos.clear();
        }

        MovilidadUtil.hideModal("novedadSeguimiento");
        MovilidadUtil.addSuccessMessage("Seguimiento de novedad actualizado éxitosamente.");
    }

    /**
     * Persiste registro de documento anexado a una novedad a la base de datos,
     * y luego lo agrega a la lista de documentos ya registrados
     */
    @Transactional
    public void guardarNovedadDocumentoTransactional() {
        novedadDocumento.setIdNovedad(selected);
        novedadDocumento.setIdNovedadTipoDocumento(novedadTipoDocumentos);
        novedadDocumento.setUsuario(user.getUsername());
        novedadDocumento.setCreado(new Date());
        if (archivos.isEmpty()) {
            PrimeFaces.current().ajax().update("frmNovedadesPm:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un documento a cargar");
            return;
        } else {
            int idRegistro = 0;
            boolean flagImagen = false;
            String path_documento = "";
            String path_imagen = "";
            for (UploadedFile f : archivos) {
//                System.out.println("F->" + f);
                if (f.getContentType().contains("pdf")) {
                    this.novedadDocumentosEjb.create(novedadDocumento);
                    path_documento = Util.saveFile(f, novedadDocumento.getIdNovedadDocumento(), "novedad_documento");
                    novedadDocumento.setPathDocumento(path_documento);
                    this.novedadDocumentosEjb.edit(novedadDocumento);
                }
                if (f.getContentType().contains("image")) {
                    if (!flagImagen) {
                        this.novedadDocumentosEjb.create(novedadDocumento);
                        idRegistro = novedadDocumento.getIdNovedadDocumento();
                    }
                    path_imagen = Util.saveFile(f, idRegistro, "novedad_documento");
                    novedadDocumento.setPathDocumento(path_imagen);
                    this.novedadDocumentosEjb.edit(novedadDocumento);
                    flagImagen = true;
                }
            }
            archivos.clear();
        }
        this.lista = this.novedadEjb.findByDateRange(fechaInicio, fechaFin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        this.lstDocumentos.add(novedadDocumento);

        if (selected.getNovedadDocumentosList() == null) {
            selected.setNovedadDocumentosList(new ArrayList<NovedadDocumentos>());
            selected.getNovedadDocumentosList().add(novedadDocumento);
        } else {
            selected.getNovedadDocumentosList().add(novedadDocumento);
        }

        nuevoDocumento();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Documento anexado a novedad éxitosamente."));
    }

    public void guardarNovedadDocumento() {
        guardarNovedadDocumentoTransactional();
    }

    /**
     * Actualiza registro de documento anexado a una novedad
     */
    @Transactional
    public void actualizarNovedadDocumentoTransactional() {
        PrimeFaces current = PrimeFaces.current();
        novedadDocumento.setIdNovedad(selected);
        novedadDocumento.setIdNovedadTipoDocumento(novedadTipoDocumentos);
        novedadDocumento.setUsuario(user.getUsername());
        novedadDocumento.setModificado(new Date());
//        this.novedadDocumentosEjb.edit(novedadDocumento);
        if (!archivos.isEmpty()) {
            String path_documento = "";
            String path_imagen = "";
            for (UploadedFile f : archivos) {
                if (selectedDocumento.getPathDocumento().contains("pdf")) {
                    if (Util.deleteFile(selectedDocumento.getPathDocumento())) {
                        if (f.getContentType().contains("pdf")) {
                            path_documento = Util.saveFile(f, novedadDocumento.getIdNovedadDocumento(), "novedad_documento");
                            novedadDocumento.setPathDocumento(path_documento);
                            this.novedadDocumentosEjb.edit(novedadDocumento);
                        }
                        if (f.getContentType().contains("image")) {
                            path_imagen = Util.saveFile(f, novedadDocumento.getIdNovedadDocumento(), "novedad_documento");
                            novedadDocumento.setPathDocumento(path_imagen);
                            this.novedadDocumentosEjb.edit(novedadDocumento);
                        }
                    } else if (f.getContentType().contains("pdf")) {
                        path_documento = Util.saveFile(f, novedadDocumento.getIdNovedadDocumento(), "novedad_documento");
                        novedadDocumento.setPathDocumento(path_documento);
                        this.novedadDocumentosEjb.edit(novedadDocumento);
                    }
                } else {
                    if (f.getContentType().contains("image")) {
                        path_imagen = Util.saveFile(f, novedadDocumento.getIdNovedadDocumento(), "novedad_documento");
                        novedadDocumento.setPathDocumento(path_imagen);
                        this.novedadDocumentosEjb.edit(novedadDocumento);
                    }
                    if (f.getContentType().contains("pdf")) {

                        borrarImagenesNovedad(novedadDocumento.getIdNovedadDocumento(), novedadDocumento.getPathDocumento());

                        path_documento = Util.saveFile(f, novedadDocumento.getIdNovedadDocumento(), "novedad_documento");
                        novedadDocumento.setPathDocumento(path_documento);
                        this.novedadDocumentosEjb.edit(novedadDocumento);
                    }
                }
            }
            archivos.clear();
        } else {
            PrimeFaces.current().ajax().update("frmNovedadesPm:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un documento a cargar");
            return;
        }
//        selected = null;
        current.executeScript("PF('novedadDocumentos').hide();");
        current.ajax().update(":frmPrincipal:dtTipo");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Documento de novedad actualizado éxitosamente."));
    }

    private void borrarImagenesNovedad(Integer idDocumento, String path) {
        List<String> lstImagenes = Util.getFileList(idDocumento, "novedad_documentos");

        if (lstImagenes != null) {
            for (String imagen : lstImagenes) {
                String ruta = path + imagen;
                Util.deleteFile(ruta);
            }
        }

    }

    public void actualizarNovedadDocumento() {
        actualizarNovedadDocumentoTransactional();
    }

    /**
     * Realiza la búsqueda de un vehículo por código
     */
    public void cargarVehiculo() {
        try {
            if (!(c_vehiculo.equals("") && c_vehiculo.isEmpty())) {
                Vehiculo vehiculo = vehiculoFacadeLocal.getVehiculo(c_vehiculo, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                if (vehiculo != null) {
                    //validación de carácter informativo, indica si el móvil es requerido por mantenimiento
                    Vehiculo vehiculoExiste = vehEJB.findVehiculoExist(c_vehiculo, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
                    String fecha1 = Util.dateFormat(novedad.getFecha()) + " " + "00:00:00";
//                    String mensaje = vehEJB.esVehiculoRequerido(vehiculoExiste.getIdVehiculo(), Util.dateTimeFormat(fecha1));
//                    if (!mensaje.isEmpty()) {
//                        MovilidadUtil.addAdvertenciaMessage(mensaje);
//                    }
                    novedad.setIdVehiculo(vehiculo);
                    if (validarUF()) {
                        novedad.setIdVehiculo(null);
                        return;
                    }
                    c_vehiculo = vehiculo.getCodigo();
                    MovilidadUtil.addSuccessMessage("Vehículo valido");
                    PrimeFaces.current().ajax().update("frmNovedadesPm:messages");
                } else {
                    MovilidadUtil.addErrorMessage("Vehículo no valido");
                    PrimeFaces.current().ajax().update("frmNovedadesPm:messages");
                    novedad.setIdVehiculo(null);
                }
            } else {
                MovilidadUtil.addErrorMessage("Vehículo no valido");
                PrimeFaces.current().ajax().update("frmNovedadesPm:messages");
                novedad.setIdVehiculo(null);
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error de sistema");
            PrimeFaces.current().ajax().update("frmNovedadesPm:messages");
        }
        validarNovedadDuplicada();
    }

    boolean validarUF() {
        if (novedad.getIdVehiculo() != null && empleado != null) {
            if (novedad.getIdVehiculo().getIdGopUnidadFuncional() != null && empleado.getIdGopUnidadFuncional() != null) {
                if (!novedad.getIdVehiculo().getIdGopUnidadFuncional().getIdGopUnidadFuncional()
                        .equals(empleado.getIdGopUnidadFuncional().getIdGopUnidadFuncional())) {
                    MovilidadUtil.addErrorMessage("Vehículo y Operador no comparten la misma unidad funcional.");
                    MovilidadUtil.updateComponent("msgs");
                    MovilidadUtil.updateComponent("frmNovedadesPm:messages");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Desactiva la verificación de novedades que coincidan para la misma fecha
     * ó rango de fechas
     */
    public void confirmarNo() {
        if (tipoAndDetalleBean.getNovedadTipoDet().getFechas() == ConstantsUtil.ON_INT) {
            b_VerificacionPM = false;
        } else {
            b_VerificacionSinFechas = false;
        }
    }

    boolean validarDatosNovedad() {
        if (tipoAndDetalleBean.getNovedadTipo() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo de novedad");
            return true;
        }
        if (tipoAndDetalleBean.getNovedadTipoDet() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un detalle de tipo de novedad");
            return true;
        }
        if (tipoAndDetalleBean.getNovedadTipoDet().getReqHoras() == ConstantsUtil.ON_INT
                && (MovilidadUtil.toSecs(novedad.getHoraInicio())
                > MovilidadUtil.toSecs(novedad.getHoraFin()))) {
            MovilidadUtil.addErrorMessage("La hora fin NO debe ser mayor a la hora inicio");
            return true;
        }
        if (tipoAndDetalleBean.getNovedadTipoDet().getReqVehiculo() == ConstantsUtil.ON_INT
                && novedad.getIdVehiculo() == null) {
            MovilidadUtil.addErrorMessage("DEBE asignar un vehículo a la novedad");
            return true;
        }

        if (empleado == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un operador");
            return true;
        }
        if (tipoAndDetalleBean.getNovedadTipoDet().getFechas() == ConstantsUtil.ON_INT
                && (novedad.getDesde() == null || novedad.getHasta() == null)) {
            MovilidadUtil.addErrorMessage("Fecha Desde y Hasta son necesarias");
            return true;
        }
        if (SingletonConfigEmpresa.getMapConfiMapEmpresa()
                .get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {

            if (tipoAndDetalleBean.getNovedadTipoDet().getAfectaProgramacion() == ConstantsUtil.ON_INT
                    && tipoAndDetalleBean.getNovedadTipoDet().getFechas() == ConstantsUtil.ON_INT) {
                boolean ok = false;
                /**
                 * Fecha desde de la novedad es mayo a hoy.
                 */
                if (novedad.getDesde().after(MovilidadUtil.fechaHoy())) {
                    ok = true;
                    /**
                     * Fecha desde es menor a hoy o es hoy y fecha hasta es
                     * mayor a hoy.
                     */
                } else if ((novedad.getDesde().before(MovilidadUtil.fechaHoy())
                        || MovilidadUtil.dateSinHora(novedad.getDesde())
                                .equals(MovilidadUtil.dateSinHora(MovilidadUtil.fechaHoy())))
                        && novedad.getHasta().after(MovilidadUtil.fechaHoy())) {
                    ok = true;
                }
                if (ok) {
                    if (ajusteJornadaFromGestionServicio.getPrgSerconMotivoJSF().getI_prgSerconMotivo() == null) {
                        MovilidadUtil.addErrorMessage("Se debe seleccionar un motivo para el ajuste de jornada.");
                        return true;
                    }
                }
            }
        }

        if (tipoAndDetalleBean.getNovedadTipoDet().getAtv() == ConstantsUtil.ON_INT //                && atvNovedadBean.isB_atv()
                ) {
            if (atvNovedadBean.getAtvTipoAtencion() == null) {
                MovilidadUtil.addErrorMessage("Se debe seleccionar un tipo de atención");
                return true;
            }
            if (atvNovedadBean.isRequeridoDestino() && atvNovedadBean.getOperacionPatios() == null) {
                MovilidadUtil.addErrorMessage("Se debe seleccionar un destino.");
                return true;
            }
            if (novedad.getIdVehiculo() == null) {
                MovilidadUtil.addErrorMessage("Se debe cargar un vehículo.");
                return true;
            }
        }
        if (tipoAndDetalleBean.getNovedadTipoDet().getAfectaDisponibilidad() == ConstantsUtil.ON_INT
                && (selectDispSistemaBean.getId_dis_sistema() == null)) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar un sistema.");
            return true;
        }
        if (tipoAndDetalleBean.getNovedadTipo().getIdNovedadTipo().equals(
                Integer.parseInt(
                        SingletonConfigEmpresa.getMapConfiMapEmpresa()
                                .get(ConstantsUtil.KEY_ID_NOV_AUSENTISMO)))) {

            ParamCierreAusentismo cierreAusentismo = paramCierreAusentismoEjb.buscarPorRangoFechasYUnidadFuncional(
                    novedad.getDesde(), novedad.getHasta(),
                    empleado.getIdGopUnidadFuncional().getIdGopUnidadFuncional());

            if (cierreAusentismo != null) {

                if (cierreAusentismo.getBloqueado() == 1) {
                    MovilidadUtil.addErrorMessage("Se ha realizado el cierre de ausentismos para la fecha seleccionada.");
                    return true;
                }

            }

        }

        return false;
    }

    public void validarNovedadDuplicada() {
        if (tipoAndDetalleBean.getNovedadTipoDet() != null && novedad.getIdVehiculo() != null) {
            if (tipoAndDetalleBean.getNovedadTipoDet().getAfectaDisponibilidad() == ConstantsUtil.ON_INT) {
                if (novedadDuplicadaBean.validarDuplicidadNovConVehiculo(novedad.getObservaciones(), user.getUsername(),
                        novedad.getIdVehiculo().getIdVehiculo(), true)) {
                    novedadDuplicadaBean.setDialogToCerrar("novedadesPM");
                }
            }
        }
    }

    /**
     * Persiste en base de datos el registro de una nueva novedad, y lo agrega a
     * la lista de novedades ( Bitácora). Además si la novedad pertenece al tipo
     * ACCIDENTE se persiste en la tabla de accidentes
     */
    public void guardarNovedadPM() throws IOException {

        if (validarDatosNovedad()) {
            return;
        }

        novedad.setIdGopUnidadFuncional(empleado.getIdGopUnidadFuncional());
        novedad.setIdNovedadTipo(tipoAndDetalleBean.getNovedadTipo());
        novedad.setIdNovedadTipoDetalle(tipoAndDetalleBean.getNovedadTipoDet());
        novedad.setIdEmpleado(empleado);
        novedad.setUsername(user.getUsername());
        if (novedad.getIdNovedadTipoDetalle().getAtv() == ConstantsUtil.ON_INT
                //                && atvNovedadBean.isB_atv()
                && novedad.getIdVehiculo() != null) {
            novedad.setEstadoNovedad(ConstantsUtil.NOV_ESTADO_ABIERTO);
            novedad.setIdAtvTipoAtencion(atvNovedadBean.getAtvTipoAtencion());
        }
        if (novedad.getIdNovedadTipoDetalle().getAfectaDisponibilidad() == ConstantsUtil.ON_INT && novedad.getIdVehiculo() != null) {
            if (novedadDuplicadaBean.validarDuplicidadNovConVehiculo(novedad.getObservaciones(), novedad.getUsername(),
                    novedad.getIdVehiculo().getIdVehiculo(), true)) {
                return;
            }
        }

        if (novedad.getIdNovedadTipo().getIdNovedadTipo() == Util.ID_ACCIDENTE
                && !tipoAndDetalleBean.getNovedadTipoDet().getIdNovedadTipoDetalle()
                        .equals(Util.ID_ACCIDENTE_LABORAL)) {
            if (novedad.getIdVehiculo() == null || c_vehiculo.equals("")) {
                MovilidadUtil.addErrorMessage("Debe ingresar un vehículo");
                return;
            }
        }

        if (!b_VerificacionPM && tipoAndDetalleBean.getNovedadTipoDet().getFechas() == ConstantsUtil.ON_INT) {
            novedadVerificacion = novedadEjb.validarNovedadConFechas(empleado.getIdEmpleado(), novedad.getDesde(), novedad.getHasta());
            if (novedadVerificacion != null) {
                b_VerificacionPM = true;
                MovilidadUtil.updateComponent("frmVerificarNovedadPM");
                PrimeFaces.current().executeScript("PF('verificarNovedadPM').show()");
                return;
            }
        }

        novedadVerificacion = novedadEjb.verificarNovedadPMSinFechas(novedad.getFecha(), empleado.getIdEmpleado(),
                tipoAndDetalleBean.getNovedadTipoDet().getIdNovedadTipoDetalle());

        if (!(b_VerificacionSinFechas) && tipoAndDetalleBean.getNovedadTipoDet().getFechas()
                == ConstantsUtil.OFF_INT && novedadVerificacion != null) {
            b_VerificacionSinFechas = true;
            MovilidadUtil.updateComponent("frmVerificarNovedadPM");
            PrimeFaces.current().executeScript("PF('verificarNovedadPM').show()");
            return;
        }

        if (tipoAndDetalleBean.getNovedadTipoDet().getAfectaProgramacion() == ConstantsUtil.ON_INT
                && tipoAndDetalleBean.getNovedadTipoDet().getFechas() == ConstantsUtil.ON_INT) {

            /**
             * Fecha desde de la novedad es mayo a hoy.
             */
            if (novedad.getDesde().after(MovilidadUtil.fechaHoy())) {
                novedadEjb.desasignarOperador(novedad.getDesde(), novedad.getHasta(),
                        novedad.getIdEmpleado().getIdEmpleado());
                if (SingletonConfigEmpresa.getMapConfiMapEmpresa()
                        .get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
                    /**
                     * Ajuste de jornada al desasignar operador de servicios.
                     */
                    prgSerconEJB.ajustarJornadaCero(novedad.getIdEmpleado().getIdEmpleado(),
                            ajusteJornadaFromGestionServicio.getPrgSerconMotivoJSF().getI_prgSerconMotivo(),
                            novedad.getDesde(), novedad.getHasta(), novedad.getObservaciones(), user.getUsername());
                }

                /**
                 * Fecha desde es menor a hoy o es hoy y fecha hasta es mayor a
                 * hoy.
                 */
            } else if ((novedad.getDesde().before(MovilidadUtil.fechaHoy())
                    || MovilidadUtil.dateSinHora(novedad.getDesde())
                            .equals(MovilidadUtil.dateSinHora(MovilidadUtil.fechaHoy())))
                    && novedad.getHasta().after(MovilidadUtil.fechaHoy())) {

                novedadEjb.desasignarOperador(MovilidadUtil.sumarDias(MovilidadUtil.fechaHoy(), flagDesasignarServicios ? 0 : 1),//si flagDesasignarServicios significa que se inhabilitan desde el mismo día de la novedad
                        novedad.getHasta(), novedad.getIdEmpleado().getIdEmpleado());

                if (SingletonConfigEmpresa.getMapConfiMapEmpresa()
                        .get(ConstantsUtil.AFECTAR_JORNADA_FROM_PRG_TC).equals(ConstantsUtil.ON_STRING)) {
                    /**
                     * Ajuste de jornada al desasignar operador de servicios.
                     */
                    prgSerconEJB.ajustarJornadaCero(novedad.getIdEmpleado().getIdEmpleado(),
                            ajusteJornadaFromGestionServicio.getPrgSerconMotivoJSF().getI_prgSerconMotivo(),
                            MovilidadUtil.sumarDias(MovilidadUtil.fechaHoy(), flagDesasignarServicios ? 0 : 1),//si flagDesasignarServicios significa que se inhabilitan desde el mismo día de la novedad
                            novedad.getHasta(), novedad.getObservaciones(), user.getUsername());
                }
            }

        }

        novedad.setPuntosPm(0);
        novedad.setPuntosPmConciliados(0);
        novedad.setLiquidada(0);
        novedad.setEstadoNovedad(ConstantsUtil.NOV_ESTADO_ABIERTO);

//        novedad.setPuntosPmConciliados(puntoView(novedad));
        novedad.setUsername(user.getUsername());
        novedad.setCreado(MovilidadUtil.fechaCompletaHoy());
        if (novedad.getIdNovedadTipoDetalle().getAfectaDisponibilidad() == ConstantsUtil.ON_INT) {
            novedad.setInmovilizado(inmovilizado ? 1 : 0);
        }
        // Se agrega infracción
        if (selectNovedadTipoInfraccionBean.getId_infraccion() != null) {
            novedad.setIdNovedadTipoInfraccion(infraccionEJB.find(selectNovedadTipoInfraccionBean.getId_infraccion()));
        }

        this.novedadEjb.create(novedad);

        /*
         * Si el detalle afecta disponibilidad, se modifica el estado del
         * vehículo por el estado parametrizado en el módulo Tipo Estado 
         * Detalle (Vehículos)
         */
        if (novedad.getIdNovedadTipoDetalle().getAfectaDisponibilidad() == ConstantsUtil.ON_INT) {
            Vehiculo vehiculo = novedad.getIdVehiculo();
            if (vehiculo != null) {
                vehiculo.setIdVehiculoTipoEstado(tipoAndDetalleBean.getNovedadTipoDet()
                        .getIdVehiculoTipoEstadoDet().getIdVehiculoTipoEstado());
                vehiculoFacadeLocal.edit(vehiculo);
                vehiculoEstadoHistoricoSaveBean.guardarEstadoVehiculoHistorico(
                        tipoAndDetalleBean.getNovedadTipoDet().getIdVehiculoTipoEstadoDet(), vehiculo, null,
                        tipoAndDetalleBean.getNovedadTipoDet().getIdNovedadTipoDetalle(), null, novedad.getObservaciones(),
                        tipoAndDetalleBean.getNovedadTipoDet().getIdVehiculoTipoEstadoDet().getIdVehiculoTipoEstado(), true);
                DispClasificacionNovedad result = guardarClasificacion(novedad.getObservaciones(), novedad.getIdNovedadTipoDetalle());
                novedad.setIdDispClasificacionNovedad(result);
                this.novedadEjb.edit(novedad);
            }
        }

        if (novedad.getIdNovedadTipoDetalle().getAfectaPm() == ConstantsUtil.ON_INT) {
            if (procedeNovedad()) {
                novedad.setProcede(1);
                novedad.setPuntosPm(tipoAndDetalleBean.getNovedadTipoDet().getPuntosPm());
                procedeCociliacion(novedad);
            }
        }
        // ID_ACCIDENTE => id novedad tipo que corresponde a los Accidentes
        if (novedad.getIdNovedadTipo().getIdNovedadTipo() == Util.ID_ACCIDENTE) {
            accPreManagedBean.guardarAccidente(novedad);
        }
        if (novedad.getIdNovedadTipoDetalle().getNotificacion() == ConstantsUtil.ON_INT) {
            notificar();
        }
        if (novedad.getIdNovedadTipoDetalle().getAfectaDisponibilidad() == ConstantsUtil.ON_INT) {
            notificarMtto();

            /**
             * Se envía notificación telegram a procesos que se encuentren
             * parametrizados en el módulo parametrización telegram
             */
            if (novedad.getIdNovedadTipoDetalle().getNotificacion() == ConstantsUtil.ON_INT
                    && novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos() != null) {

                String codigoProcesoMtto = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.NOTIFICACION_PROCESO_MTTO);

                boolean flag = true;

                if (codigoProcesoMtto.equals(novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getCodigoProceso())) {
                    flag = false;
                }
                if (novedad.getIdNovedadTipo().getIdNovedadTipo() == Util.ID_ACCIDENTE) {
                    flag = false;
                }

                if (flag) {
                    enviarNotificacionTelegramMtto(novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos());
                }

            }

        }
//        nuevo(MovilidadUtil.fechaHoy());
        consultar();
//        PrimeFaces.current().executeScript("PF('dtNovedades').clearFilters();");
        b_VerificacionPM = false;
        b_VerificacionSinFechas = false;
        System.out.println("NOVEDAD CREADA CON ÉXITO");
        MovilidadUtil.addSuccessMessage("Novedad  registrada correctamente.");
        if (novedad.getIdNovedadTipoDetalle().getAtv() == ConstantsUtil.ON_INT
                //                && atvNovedadBean.isB_atv()
                && novedad.getIdVehiculo() != null) {
            atvNovedadBean.sendAtvNovedad(novedad);
        }
        resetNovedad();
        resetSelected();
        flagDesasignarServicios = false;
    }

    DispClasificacionNovedad guardarClasificacion(String observacion, NovedadTipoDetalles novedadTipoDetalle) {
        DispClasificacionNovedad clasificacionNovedad = new DispClasificacionNovedad();
        clasificacionNovedad.setCreado(MovilidadUtil.fechaCompletaHoy());
        clasificacionNovedad.setUsername(user.getUsername());
        clasificacionNovedad.setIdDispSistema(new DispSistema(selectDispSistemaBean.getId_dis_sistema()));
        int idVehiculoTipoEstadoDet = novedadTipoDetalle.getIdVehiculoTipoEstadoDet() == null ? 0
                : novedadTipoDetalle.getIdVehiculoTipoEstadoDet().getIdVehiculoTipoEstadoDet();
        if (idVehiculoTipoEstadoDet > 0) {
            DispEstadoPendActual primerEstadoPendienteActual
                    = vehiculoEstadoHistoricoSaveBean.getPrimerEstadoPendienteActual(idVehiculoTipoEstadoDet);
            clasificacionNovedad.setIdDispEstadoPendActual(primerEstadoPendienteActual);
        }
        clasificacionNovedad.setObservacion(observacion);
        clasificacionNovedadEJB.create(clasificacionNovedad);
        return clasificacionNovedad;
    }

    /**
     * Obtiene fotos de una novedad
     *
     * @throws IOException
     */
    public void obtenerFotosNovedad() throws IOException {
        List<String> lstNombresImg = Util.getFileList(selectedDocumento.getIdNovedadDocumento(), "novedad_documentos");
        if (fotosNovedades == null) {
            fotosNovedades = new ArrayList<>();
        } else {
            fotosNovedades.clear();
        }
        if (lstNombresImg != null) {
            for (String f : lstNombresImg) {
                f = selectedDocumento.getPathDocumento() + f;
                fotosNovedades.add(f);
            }
        }
        fotoJSFManagedBean.setListFotos(fotosNovedades);
    }

    /**
     * Modifica registro novedad, y actualiza los datos en la tabla de
     * accidentes ( Sí la novedad a modificar tiene como tipo ACCIDENTE)
     *
     */
    public void actualizarNovedadPM() {
        PrimeFaces current = PrimeFaces.current();

        if (tipoAndDetalleBean.getNovedadTipo().getIdNovedadTipo() != 13) {
            novedad.setIdPqr(0);
        }

        if (tipoAndDetalleBean.getNovedadTipo() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo de novedad");
            return;
        }
        if (tipoAndDetalleBean.getNovedadTipoDet() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un detalle de tipo de novedad");
            return;
        }
        if (empleado.getIdEmpleado() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un operador");
            return;
        }

        if (tipoAndDetalleBean.getNovedadTipoDet().getFechas() == ConstantsUtil.ON_INT) {
            if (novedad.getDesde() == null || novedad.getHasta() == null) {
                MovilidadUtil.addErrorMessage("Fecha Desde y Hasta son necesarias");
                return;
            }
        }

        if (tipoAndDetalleBean.getNovedadTipoDet().getReqHoras() == ConstantsUtil.ON_INT) {
            if (MovilidadUtil.toSecs(novedad.getHoraInicio()) > MovilidadUtil.toSecs(novedad.getHoraFin())) {
                MovilidadUtil.addErrorMessage("La hora fin NO debe ser mayor a la hora inicio");
                return;
            }
        }

        if (tipoAndDetalleBean.getNovedadTipoDet().getReqVehiculo() == ConstantsUtil.ON_INT) {
            if (novedad.getIdVehiculo() == null) {
                MovilidadUtil.addErrorMessage("DEBE asignar un vehículo a la novedad");
                return;
            }
        }

//        if (novedad.getDesde() != null && novedad.getHasta() != null) {
//            if (Util.validarFechaPM(novedad.getDesde(), novedad.getHasta())) {
//                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                        "Error", "Error al establecer el rango de fechas");
//                FacesContext.getCurrentInstance().addMessage(null, message);
//                return;
//            }
//        }
        if (tipoAndDetalleBean.getNovedadTipo().getNombreTipoNovedad().equals(Util.DANO)) {
            MovilidadUtil.addErrorMessage("Debe actualizar la novedad a través del módulo: Novedades de daño");
            current.ajax().update("frmNovedadesPm:messages");
            return;
        }

        if (tipoAndDetalleBean.getNovedadTipo().getIdNovedadTipo().equals(
                Integer.parseInt(
                        SingletonConfigEmpresa.getMapConfiMapEmpresa()
                                .get(ConstantsUtil.KEY_ID_NOV_AUSENTISMO)))) {

            ParamCierreAusentismo cierreAusentismo = paramCierreAusentismoEjb.buscarPorRangoFechasYUnidadFuncional(
                    novedad.getFecha(), novedad.getFecha(),
                    empleado.getIdGopUnidadFuncional().getIdGopUnidadFuncional());

            if (cierreAusentismo != null) {

                if (cierreAusentismo.getBloqueado() == 1) {
                    MovilidadUtil.addErrorMessage("Se ha realizado el cierre de ausentismos para la fecha seleccionada.");
                    return;
                }

            }

        }

        NovedadTipoDetalles detalleTemp = selected.getIdNovedadTipoDetalle();

        novedad.setIdNovedadTipo(tipoAndDetalleBean.getNovedadTipo());
        novedad.setIdNovedadTipoDetalle(tipoAndDetalleBean.getNovedadTipoDet());
        novedad.setIdEmpleado(empleado);
        novedad.setProcede(0);
        novedad.setPuntosPm(0);
        novedad.setPuntosPmConciliados(0);

        novedad.setLiquidada(0);
        novedad.setUsername(user.getUsername());
        novedad.setModificado(new Date());

        // Se agrega infracción
        if (selectNovedadTipoInfraccionBean.getId_infraccion() != null) {
            novedad.setIdNovedadTipoInfraccion(infraccionEJB.find(selectNovedadTipoInfraccionBean.getId_infraccion()));
        }

        this.novedadEjb.edit(novedad);

        if (novedad.getIdNovedadTipoDetalle().getAfectaDisponibilidad() == ConstantsUtil.ON_INT) {
            novedad.setInmovilizado(inmovilizado ? 1 : 0);
        }

        if (novedad.getIdNovedadTipoDetalle().getAfectaPm() == ConstantsUtil.ON_INT) {
            if (procedeNovedad()) {
                novedad.setProcede(1);
                novedad.setPuntosPm(tipoAndDetalleBean.getNovedadTipoDet().getPuntosPm());
                procedeCociliacion(novedad);
            }
        }

        // ID_ACCIDENTE => id novedad tipo que corresponde a los Accidentes
        if (novedad.getIdNovedadTipo().getIdNovedadTipo().equals(Util.ID_ACCIDENTE)) {

            if (detalleTemp.getIdNovedadTipo().getIdNovedadTipo().equals(Util.ID_ACCIDENTE)) {
                System.out.println("Era accidente y sigue siendo accidente - " + dataNovedad(novedad));
                accidenteBean.actualizarAccidente(novedad, false);
            } else {
                System.out.println("NO era accidente y se convierte a accidente - " + dataNovedad(novedad));
                accPreManagedBean.guardarAccidente(novedad);
            }
        } else {
            if (detalleTemp.getIdNovedadTipo().getIdNovedadTipo().equals(Util.ID_ACCIDENTE)) {
                if (!(novedad.getIdNovedadTipo().getIdNovedadTipo().equals(Util.ID_ACCIDENTE))) {
                    System.out.println("Era un accidente y se cambió a no accidente - " + dataNovedad(novedad));
                    accidenteBean.actualizarAccidente(novedad, true);
                }
            }
        }

        edit = false;

        MovilidadUtil.hideModal("novedadesPM");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Novedad  actualizada correctamente."));
    }

    private String dataNovedad(Novedad param) {
        return param.getIdVehiculo().getCodigo() + " - " + Util.dateFormat(param.getFecha()) + " - " + param.getIdNovedad();
    }

    /**
     * Realiza la muestra de pdf,imágenes,lista de imágenes de un documento que
     * halla sido anexado a una novedad
     */
    public void getDocumento() {
        String ext = "";
        if (fotosNovedades == null) {
            fotosNovedades = new ArrayList<>();
        } else {
            fotosNovedades.clear();
        }
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
            archivosBean.setModalHeader(selectedDocumento.getIdNovedadTipoDocumento().getNombreTipoDocumento().toUpperCase()
                    + " (" + selected.getIdEmpleado() == null ? "" : selected.getIdEmpleado().getCodigoTm() + ") "
                    + " - " + Util.dateFormat(selectedDocumento.getCreado()));
            archivosBean.setPath(selectedDocumento.getPathDocumento());
            MovilidadUtil.openModal("DocumentoListDialog");
        } else {
            try {
                obtenerFotosNovedad();
                archivosBean.setExtension(ext);
                archivosBean.setPath("");
                archivosBean.setSelectedDocumento(selectedDocumento);

                archivosBean.setModalHeader(selectedDocumento.getIdNovedadTipoDocumento().
                        getNombreTipoDocumento().toUpperCase()
                        + " (" + selected.getIdEmpleado() == null ? "" : selected.getIdEmpleado().getCodigoTm() + ") "
                        + " - " + Util.dateFormat(selectedDocumento.getCreado()));
                MovilidadUtil.openModal("galeria_foto_dialog_wv");
            } catch (IOException ex) {
                Logger.getLogger(NovedadJSFManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Realiza la carga de fotos de una novedad de daño que halla sido
     * seleccionada
     */
//    public void obtenerFotosDano() {
//        try {
//            if (selected.getIdNovedadDano().getPathFotos() != null) {
//                obtenerFotosNovedadDano();
//                archivosBean.setPath("");
//                archivosBean.setSelectedNovedad(selected);
//                archivosBean.setModalHeader("FOTOS NOVEDAD DE DAÑO");
//            } else {
//                PrimeFaces.current().ajax().update(":msgs");
//                MovilidadUtil.addErrorMessage("La novedad de deño no tiene imágenes asociadas");
//                return;
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(NovedadJSFManagedBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
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

        if (event.getFile() != null) {
            if (flagEditarArchivoSegumiento) {
                if (novedadSeguimientoDoc.getPathArchivo() != null) {
                    Util.deleteFile(novedadSeguimientoDoc.getPathArchivo());
                    String pathArchivo;
                    novedadSeguimientoDoc.setNombreArchivo(event.getFile().getFileName());
                    novedadSeguimientoDoc.setIdNovedadSeguimiento(selectedSeguimiento);
                    novedadSeguimientoDoc.setUsername(user.getUsername());
                    novedadSeguimientoDoc.setModificado(MovilidadUtil.fechaCompletaHoy());
                    pathArchivo = Util.saveFile(event.getFile(), selectedSeguimiento.getIdNovedadSeguimiento(), "seguimiento_archivo");
                    novedadSeguimientoDoc.setPathArchivo(pathArchivo);
                    this.novedadSeguimientoDocsEjb.edit(novedadSeguimientoDoc);
                    flagEditarArchivoSegumiento = false;
                    MovilidadUtil.updateComponent("frmNovedadSeguimientoList:dtNovedadSeguimiento");
                }
            } else {
                archivos.add(event.getFile());
            }
        }

        MovilidadUtil.hideModal("AddFilesSeguimientoDialog");
        MovilidadUtil.updateComponent(":msgs");
        MovilidadUtil.addSuccessMessage("Archivo(s) agregado(s) éxitosamente");
    }

    /*
     * Método que obtiene el listado de novedades por rango de fechas 
     */
    public void getByDateRange() {
        resetSelected();
        PrimeFaces.current().executeScript("PF('dtNovedades').clearFilters()");
        consultar();
        if (lista == null || lista.isEmpty()) {
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addAdvertenciaMessage("No se encontraron novedades para éste rango de fechas");
        }
    }

    /*
     * Parámetros para el envío de correos (Novedades PM)
     */
    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.find(Util.ID_NOVEDAD_PM_TEMPLATE);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    /*
     * Parámetros para el envío de correos de fallas a Mantenimiento
     */
    private Map getMailParamsMTTO() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(Util.TEMPLATE_NOVEDADES_MTTO);
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
     * Realiza el envío de correo de las fallas registradas a las partes
     * interesadas ( Mantenimiento )
     */
    private void notificarMtto() {
        Map mapa = getMailParamsMTTO();
        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("tipoNovedad", novedad.getIdNovedadTipo().getNombreTipoNovedad());
        mailProperties.put("tipoDetalle", novedad.getIdNovedadTipoDetalle().getTituloTipoNovedad());
        mailProperties.put("fechaHora", Util.dateFormat(novedad.getFecha()));
        mailProperties.put("empleado", empleado != null ? empleado.getCodigoTm() + " - " + empleado.getNombres() + " " + empleado.getApellidos() : "");
        mailProperties.put("vehiculo", novedad.getIdVehiculo() != null ? novedad.getIdVehiculo().getCodigo() : "");
        mailProperties.put("sistema", selectDispSistemaBean.getId_dis_sistema() != null ? dispSistemaEjb.find(selectDispSistemaBean.getId_dis_sistema()).getNombre() : "");
        mailProperties.put("estado", novedad.getIdDispClasificacionNovedad().getIdDispEstadoPendActual() != null ? novedad.getIdDispClasificacionNovedad().getIdDispEstadoPendActual().getNombre() : "");
        mailProperties.put("reportadoPor", "");
        mailProperties.put("observacion", novedad.getObservaciones());
        String subject;
        String destinatarios;

        String codigoProcesoMtto = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.NOTIFICACION_PROCESO_MTTO);

        if (codigoProcesoMtto == null) {
            MovilidadUtil.addErrorMessage("NO se encontró parametrizada la lista de distribución correspondiente al área de MANTENIMIENTO");
            return;
        }

        NotificacionProcesos procesoMtto = notificacionProcesoEjb.findByCodigo(codigoProcesoMtto);

        if (procesoMtto != null) {
            destinatarios = procesoMtto.getEmails();
            subject = procesoMtto.getMensaje();

            if (procesoMtto.getNotificacionProcesoDetList() != null) {
                String destinatariosByUf = "";

                destinatariosByUf = MovilidadUtil.obtenerCorreosByUf(procesoMtto.getNotificacionProcesoDetList(), novedad.getIdGopUnidadFuncional().getIdGopUnidadFuncional());

                if (destinatariosByUf != null) {
                    destinatarios = destinatarios + "," + destinatariosByUf;
                }
            }

            enviarNotificacionTelegramMtto(procesoMtto);

            SendMails.sendEmail(mapa, mailProperties, subject,
                    "",
                    destinatarios,
                    "Notificaciones RIGEL", null);
        }
    }

    private void enviarNotificacionTelegramMtto(NotificacionProcesos proceso) {

        try {
            NotificacionTelegramDet detalle = notificacionTelegramDetEjb.findByIdNotifProcesoAndUf(proceso.getIdNotificacionProceso(), novedad.getIdGopUnidadFuncional().getIdGopUnidadFuncional());

            if (detalle != null) {
                String urlPost = SingletonConfigEmpresa.getMapConfiMapEmpresa()
                        .get(ConstantsUtil.URL_NOTIF_TELEGRAM_AFECTA_DISPO);

                if (urlPost != null) {

                    Empleado emp = null;
                    if (empleado.getIdEmpleado() != null) {
                        emp = empleadoEjb.find(empleado.getIdEmpleado());
                    }

                    JSONObject objeto = SenderNotificacionTelegram.getObjeto();
                    objeto.put("chatId", detalle.getChatId());
                    objeto.put("token", detalle.getIdNotificacionTelegram().getToken());
                    objeto.put("nombreBot", detalle.getIdNotificacionTelegram().getNombreBot());
                    objeto.put("tipoNovedad", novedad.getIdNovedadTipo().getNombreTipoNovedad());
                    objeto.put("tipoDetalle", novedad.getIdNovedadTipoDetalle().getTituloTipoNovedad());
                    objeto.put("fechaHora", Util.dateFormat(novedad.getFecha()));
                    objeto.put("operador", emp != null ? emp.getNombresApellidos() : "");
//                    objeto.put("operador", empleado != null ? empleado.getCodigoTm() + " - " + empleado.getNombres() + " " + empleado.getApellidos() : "");
                    objeto.put("vehiculo", novedad.getIdVehiculo() != null ? novedad.getIdVehiculo().getCodigo() : "");

                    if (novedad.getIdVehiculo() != null) {

                        String urlServicio = SingletonConfigEmpresa.getMapConfiMapEmpresa()
                                .get(ConstantsUtil.URL_SERVICE_LOCATION_VEHICLE);
                        if (urlServicio == null) {
                            MovilidadUtil.addErrorMessage("El sistema no cuenta con el endpoint que busca la ubicación de los vehículos");
                            return;
                        }

                        CurrentLocation ubicacionVeh = GeoService.getCurrentPosition(urlServicio + novedad.getIdVehiculo().getCodigo());

                        if (ubicacionVeh == null) {
                            objeto.put("latitud", "");
                            objeto.put("longitud", "");
                        } else {
                            objeto.put("latitud", ubicacionVeh.get_Latitude());
                            objeto.put("longitud", ubicacionVeh.get_Longitude());
                        }

                    } else {
                        objeto.put("latitud", "");
                        objeto.put("longitud", "");
                    }

                    objeto.put("sistema", selectDispSistemaBean.getId_dis_sistema() != null ? dispSistemaEjb.find(selectDispSistemaBean.getId_dis_sistema()).getNombre() : "");
                    objeto.put("estado", novedad.getIdDispClasificacionNovedad().getIdDispEstadoPendActual() != null ? novedad.getIdDispClasificacionNovedad().getIdDispEstadoPendActual().getNombre() : "");
                    objeto.put("generadoPor", novedad.getUsername());
                    objeto.put("observacion", novedad.getObservaciones());
                    boolean sent = SenderNotificacionTelegram.send(objeto, urlPost);
                    System.out.println("sent-" + sent);

                }
            }

        } catch (Exception e) {
        }
    }

    /**
     * Realiza el envío de correo de las novedad registrada a las partes
     * interesadas
     */
    private void notificar() {
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("fecha", Util.dateFormat(novedad.getFecha()));
        mailProperties.put("tipo", novedad.getIdNovedadTipo().getNombreTipoNovedad());
        mailProperties.put("detalle", novedad.getIdNovedadTipoDetalle().getTituloTipoNovedad());
        mailProperties.put("fechas", novedad.getDesde() != null && novedad.getHasta() != null ? Util.dateFormat(novedad.getDesde()) + " hasta " + Util.dateFormat(novedad.getHasta()) : "");
        mailProperties.put("operador", empleado != null ? empleado.getCodigoTm() + " - " + empleado.getNombres() + " " + empleado.getApellidos() : "");
//        mailProperties.put("operador", novedad.getIdEmpleado() != null ? novedad.getIdEmpleado().getNombres() + " " + novedad.getIdEmpleado().getApellidos() : "");
        mailProperties.put("vehiculo", novedad.getIdVehiculo() != null ? novedad.getIdVehiculo().getCodigo() : "");
        mailProperties.put("username", "");
        mailProperties.put("generada", Util.dateTimeFormat(novedad.getCreado()));
        mailProperties.put("observaciones", novedad.getObservaciones());
        String subject = "Novedad " + novedad.getIdNovedadTipo().getNombreTipoNovedad();
        String destinatarios = "";

        //Busqueda Operador Máster
        if (novedad.getIdEmpleado() != null) {
            String correoMaster = "";
            if (novedad.getIdEmpleado().getPmGrupoDetalleList().size() == 1) {
                correoMaster = novedad.getIdEmpleado().getPmGrupoDetalleList().get(0).getIdGrupo().getIdEmpleado().getEmailCorporativo();
            }
            if (novedad.getIdNovedadTipoDetalle().getNotificaOperador() == 1) {//si en el tipo detalle de la novedad se ha indicado que se debe notificar al operador, se agrega el respectivo correo
                destinatarios = novedad.getIdEmpleado() != null ? correoMaster + "," + novedad.getIdEmpleado().getEmailCorporativo() : "";
            }
        }
        if (novedad.getIdNovedadTipoDetalle().getNotificacion() == 1) {
            if (novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos() != null) {
                if (destinatarios != null) {
                    destinatarios = destinatarios + "," + novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getEmails();
                } else {
                    destinatarios = novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getEmails();
                }

                if (novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getNotificacionProcesoDetList() != null) {
                    String destinatariosByUf = "";
                    destinatariosByUf = MovilidadUtil.obtenerCorreosByUf(novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getNotificacionProcesoDetList(), novedad.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
                    if (destinatariosByUf != null) {
                        destinatarios = destinatarios + "," + destinatariosByUf;
                    }
                }
            }
            SendMails.sendEmail(mapa, mailProperties, subject,
                    "",
                    destinatarios,
                    "Notificaciones RIGEL", null);
        }
    }

    /**
     * Verifica si el usuario logueado es un Profesional de operaciones
     *
     * @return true si el usuario logueado es un Profesional de operaciones
     */
    public boolean validarRol() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (SingletonConfigEmpresa.getMapConfiMapEmpresa()
                    .get(ConstantsUtil.ROLE_PROF_NOV_JORNADA)
                    .contains(auth.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Valida si el usuario logueado corresponde al área de Seguridad Vial
     *
     * @return true si el usuario tiene rol ROLE_SEG
     */
    public boolean validarRolSeg() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_SEG")) {
                return true;
            }
        }
        return false;
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
        if (validarRol()) {
            MovilidadUtil.addSuccessMessage("Los puntos se han agregado a la Concilicación correctamente");
            MovilidadUtil.updateComponent("frmPrincipal:dtTipo");
        }
        MovilidadUtil.updateComponent("msgs");
        novedadEjb.edit(selected);
        validarInfraccion(selected);
        reset();
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
        validarInfraccion(selected);
        reset();
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
        validarInfraccion(selected);
        MovilidadUtil.addSuccessMessage("Los puntos se han agregado a la Concilicación correctamente");
        MovilidadUtil.hideModal("apli-pm");
        MovilidadUtil.updateComponent("frmPrincipal:dtTipo");
        reset();
    }

    private void validarInfraccion(Novedad n) {
        Infracciones infraccion = infraccionesEJB.findByIdNovedad(n.getIdNovedad());
        if (infraccion != null) {
            infraccion.setPuntosPMConciliados(n.getPuntosPmConciliados());
            infraccion.setUsernameEdit(user.getUsername());
            infraccionesEJB.edit(infraccion);
        }
    }

    /**
     *
     */
    public void reset() {
        selected = new Novedad();
        i_puntosConciliados = 0;
//        PrimeFaces.current().ajax().update(":frmPrincipal:dtTipo");
    }

    /**
     * Devuelve la lista de grupos del PM
     *
     * @return lista grupos PM
     */
    public List<Object> grupoPM() {
        List<Object> aux_list = new ArrayList<>();
        if (lista != null) {
            for (Novedad d : lista) {
                aux_list.add(master(d.getIdEmpleado()));
            }
            aux_list = aux_list.stream().distinct().collect(Collectors.toList());
        }
        return aux_list;
    }

    public void cambiarPuntos() {
        if (selected == null) {
            MovilidadUtil.addErrorMessage("Seleccionar una novedad.");
            return;
        }
        MovilidadUtil.openModal("apli-pm");
    }

    public void validarOperacionCerrada() {
        if (novedad.getFecha() != null && validarFinOperacionBean.validarDiaBloqueado(novedad.getFecha(), unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional())) {
            novedad.setFecha(MovilidadUtil.fechaHoy());
        }
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

    public NovedadFacadeLocal getNovedadEjb() {
        return novedadEjb;
    }

    public void setNovedadEjb(NovedadFacadeLocal novedadEjb) {
        this.novedadEjb = novedadEjb;
    }

    public List<Novedad> getLista() {
        return lista;
    }

    public void setLista(List<Novedad> lista) {
        this.lista = lista;
    }

    public Novedad getNovedad() {
        return novedad;
    }

    public void setNovedad(Novedad novedad) {
        this.novedad = novedad;
    }

    public Novedad getSelected() {
        return selected;
    }

    public void setSelected(Novedad selected) {
        this.selected = selected;
    }

    public NovedadSeguimiento getNovedadSeguimiento() {
        return novedadSeguimiento;
    }

    public void setNovedadSeguimiento(NovedadSeguimiento novedadSeguimiento) {
        this.novedadSeguimiento = novedadSeguimiento;
    }

    public List<NovedadSeguimiento> getLstSeguimientos() {
        if (selected != null) {
            lstSeguimientos = novedadSeguimientoEjb.findByNovedad(selected.getIdNovedad());
        }
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

    public List<NovedadDocumentos> getLstDocumentos() {
        if (selected != null) {
            lstDocumentos = novedadDocumentosEjb.findByIdNovedad(selected.getIdNovedad());
        }
        return lstDocumentos;
    }

    public void setLstDocumentos(List<NovedadDocumentos> lstDocumentos) {
        this.lstDocumentos = lstDocumentos;
    }

    public NovedadTipoDocumentos getNovedadTipoDocumentos() {
        return novedadTipoDocumentos;
    }

    public void setNovedadTipoDocumentos(NovedadTipoDocumentos novedadTipoDocumentos) {
        this.novedadTipoDocumentos = novedadTipoDocumentos;
    }

    public List<NovedadTipoDocumentos> getLstNovedadTipoDocumentos() {
        return lstNovedadTipoDocumentos;
    }

    public void setLstNovedadTipoDocumentos(List<NovedadTipoDocumentos> lstNovedadTipoDocumentos) {
        this.lstNovedadTipoDocumentos = lstNovedadTipoDocumentos;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
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

    public List<Novedad> getListaFilter() {
        return listaFilter;
    }

    public void setListaFilter(List<Novedad> listaFilter) {
        this.listaFilter = listaFilter;
    }

    public String getTamanoNovedadSeguimiento() {
        return tamanoNovedadSeguimiento;
    }

    public void setTamanoNovedadSeguimiento(String tamanoNovedadSeguimiento) {
        this.tamanoNovedadSeguimiento = tamanoNovedadSeguimiento;
    }

    public boolean isFlagEditarArchivoSegumiento() {
        return flagEditarArchivoSegumiento;
    }

    public void setFlagEditarArchivoSegumiento(boolean flagEditarArchivoSegumiento) {
        this.flagEditarArchivoSegumiento = flagEditarArchivoSegumiento;
    }

    public NovedadSeguimientoDocs getNovedadSeguimientoDoc() {
        return novedadSeguimientoDoc;
    }

    public void setNovedadSeguimientoDoc(NovedadSeguimientoDocs novedadSeguimientoDoc) {
        this.novedadSeguimientoDoc = novedadSeguimientoDoc;
    }

    public StreamedContent getFileDescargar() {
        return fileDescargar;
    }

    public void setFileDescargar(StreamedContent fileDescargar) {
        this.fileDescargar = fileDescargar;
    }

    public boolean isViewCreateNovedadPP() {
        return viewCreateNovedadPP;
    }

    public void setViewCreateNovedadPP(boolean viewCreateNovedadPP) {
        this.viewCreateNovedadPP = viewCreateNovedadPP;
    }

    public boolean isInmovilizado() {
        return inmovilizado;
    }

    public void setInmovilizado(boolean inmovilizado) {
        this.inmovilizado = inmovilizado;
    }

    public Novedad getNovedadVerificacion() {
        return novedadVerificacion;
    }

    public void setNovedadVerificacion(Novedad novedadVerificacion) {
        this.novedadVerificacion = novedadVerificacion;
    }

    public boolean isCreateNovedadPP() {
        return createNovedadPP;
    }

    public void setCreateNovedadPP(boolean createNovedadPP) {
        this.createNovedadPP = createNovedadPP;
    }

    public List<PqrMaestro> getLstpqrMaestro() {
        return lstpqrMaestro;
    }

    public void setLstpqrMaestro(List<PqrMaestro> lstpqrMaestro) {
        this.lstpqrMaestro = lstpqrMaestro;
    }

    public PqrMaestro getPqrmaestro() {
        return pqrmaestro;
    }

    public void setPqrmaestro(PqrMaestro pqrmaestro) {
        this.pqrmaestro = pqrmaestro;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isFlagDesasignarServicios() {
        return flagDesasignarServicios;
    }

    public void setFlagDesasignarServicios(boolean flagDesasignarServicios) {
        this.flagDesasignarServicios = flagDesasignarServicios;
    }

    public boolean isB_OwnerCreate() {
        return b_OwnerCreate;
    }

    public void setB_OwnerCreate(boolean b_OwnerCreate) {
        this.b_OwnerCreate = b_OwnerCreate;
    }

    public boolean isFlagTC() {
        return flagTC;
    }

    public void setFlagTC(boolean flagTC) {
        this.flagTC = flagTC;
    }

}
