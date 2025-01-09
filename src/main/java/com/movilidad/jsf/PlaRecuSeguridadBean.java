package com.movilidad.jsf;

import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.model.error.FileLoadError;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.awt.Event;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;
import com.movilidad.ejb.PlaRecuSeguridadFacadeLocal;
import com.movilidad.ejb.PlaRecuMotivoFacadeLocal;
import com.movilidad.ejb.PlaRecuRutaFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.planificacion_recursos.PlaRecuMotivo;
import com.movilidad.model.planificacion_recursos.PlaRecuRuta;
import com.movilidad.model.planificacion_recursos.PlaRecuSeguridad;
import static com.movilidad.utils.MovilidadUtil.getProperty;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Inject;

/**
 * Clase control que permite dar gestión a los métodos de la tabla
 * planificacion_recursos_seguridad
 *
 * @author Omar.beltran
 */
@Named(value = "plaRecuSeguridadBean")
@ViewScoped
public class PlaRecuSeguridadBean implements Serializable {

    @EJB
    private PlaRecuSeguridadFacadeLocal seguridadEJB;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;
    @EJB
    private PlaRecuRutaFacadeLocal rutaEjb;
    @EJB
    private PlaRecuMotivoFacadeLocal motivoEJB;
    @EJB
    private ConfigEmpresaFacadeLocal configEmpresaFacadeLocal;
    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    //colecciones
    private List<PlaRecuSeguridad> listPlaRecuSeguridad;
    private List<Empleado> listEmpleados;
    private List<PlaRecuMotivo> listMotivos;
    private List<PlaRecuRuta> listRoute;
    List<FileLoadError> listaError;
    private List<GopUnidadFuncional> listaGrupoFuncional;

    //atributos
    private PlaRecuSeguridad plaRecuSeguridad;
    private Empleado empleadoSelected;
    private PlaRecuMotivo motivoSelected;
    private PlaRecuRuta routeSelected;
    private int id_lugar_sel;
    private String codigo_TM;
    private String novedadSel;
    private UploadedFile uploadedFile;
    private Date desde, hasta;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private ParamAreaUsr pau;
    private String rol_user = "";
    private boolean b_editar;
    private boolean b_error;
    private Integer motivoSelectedId;
    private GopUnidadFuncional uf;
    String dia_inicio;
    String dia_fin;

    @PostConstruct
    public void init() {
        initPlaRecuSeguridad();
        initPlaRecuSeguridadSelected();
        restringirCalendario();
        motivoSelected = new PlaRecuMotivo();
        uploadedFile = null;
        b_editar = false;
        b_error = false;
        listaError = new ArrayList<>();
        listPlaRecuSeguridad = seguridadEJB.findAll();
        listMotivos = motivoEJB.findAll();
        listRoute = rutaEjb.findAll();
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
        listEmpleados = empleadoEjb.findAllEmpleadosActivos(0); //cargar empleados activos
        
        uf = new GopUnidadFuncional();
        uf.setIdGopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        listPlaRecuSeguridad = seguridadEJB.findAll();
        
        plaRecuSeguridad.setIdMotivo(new PlaRecuMotivo());
        plaRecuSeguridad.setIdPlaRecuRuta(new PlaRecuRuta());
        plaRecuSeguridad.setEmpleado(new Empleado());
        
        validarRol();
    }
    
    public void consultarFecha() {
        listPlaRecuSeguridad = seguridadEJB.findAllByFechaRange(desde, hasta);
        PrimeFaces.current().executeScript("PF('w_seguridadList').clearFilters()");
    }
    
    private void restringirCalendario() {
        String inicio = configEmpresaFacadeLocal.findByLlave("DIA_INICIO_SEMANA_PROGRAMACION").getValor();
        String fin = configEmpresaFacadeLocal.findByLlave("DIA_FIN_SEMANA_PROGRAMACION").getValor();
        String corte = configEmpresaFacadeLocal.findByLlave("DIA_CORTE_SEMANA_PROGRAMACION").getValor();
        String hora = configEmpresaFacadeLocal.findByLlave("HORA_CORTE_SEMANA_PROGRAMACION").getValor();
        String[] array = Util.getDateRange(inicio, fin, corte, hora);
        dia_fin = array[1];
        dia_inicio = array[0];
    }

    private void initPlaRecuSeguridad() {
        plaRecuSeguridad = new PlaRecuSeguridad();
        plaRecuSeguridad.setIdMotivo(new PlaRecuMotivo());
        plaRecuSeguridad.setIdPlaRecuRuta(new PlaRecuRuta());
        plaRecuSeguridad.setEmpleado(new Empleado());
    }

    private void initPlaRecuSeguridadSelected() {
        plaRecuSeguridad = new PlaRecuSeguridad();
        plaRecuSeguridad.setIdMotivo(new PlaRecuMotivo());
        plaRecuSeguridad.setIdPlaRecuRuta(new PlaRecuRuta());
        plaRecuSeguridad.setEmpleado(new Empleado());
    }

    /**
     * Método resposable de cargar el archivo Excel seleccionado en la vista
     *
     * @param event Contiene el archivo cargado desde la vista.
     */
    public void cargarSeguridad(FileUploadEvent event) {
        try {
            this.uploadedFile = event.getFile();
            if (uploadedFile != null) {
                List<Object> list = new ArrayList<>();
                String path = Util.saveFile(uploadedFile, 0, "planificacionRecursosSeguridad");
                FileInputStream fileInputStream = new FileInputStream(new File(path));
                recorrerExcel(fileInputStream, list);
                crearSeguridades(list);
            }
            if (!b_error) {
                MovilidadUtil.addSuccessMessage("Archivo de 'Seguridad' cargado correctamente");
            }else{
                String errorMessages = listaError.stream()
                .map(error -> "\n Línea: " + error.getRow() + ", Valor: " + error.getValue()+ ", Mensaje: " + error.getMessage())
                .collect(Collectors.joining("\n"));
                MovilidadUtil.addFatalMessage("Archivo de 'Seguridad' Se encontraron los siguientes errores : <br/>"+ errorMessages);
            }
        } catch (Exception ex) {
            System.out.println("Error en la carga de archivo " + ex.getMessage());
        } finally {
            uploadedFile = null;
        }
    }

    public void cargarEmpleado() {
        if (!(codigo_TM.equals("") && codigo_TM.isEmpty())) {
            empleadoSelected = empleadoEjb.getEmpleadoActivoCodigoTM(Integer.parseInt(codigo_TM));
            if (empleadoSelected != null) {
                plaRecuSeguridad.setEmpleado(empleadoSelected);
                return;
            }
        }
        this.empleadoSelected = null;
        this.codigo_TM = "";
        MovilidadUtil.addErrorMessage("'Código TM' no valido");
        MovilidadUtil.updateComponent("form-registrar-seguridad:msgs_create_seguridad");
    }

    public void editar(PlaRecuSeguridad obj) throws Exception {
        this.plaRecuSeguridad = obj;
        //motivoSelectedId = obj.getIdMotivo().getIdPlaRecuMotivo();
        codigo_TM = obj.getEmpleado().getCodigoTm().toString();
        b_editar = true;
    }

    public void editarSeguridad(PlaRecuSeguridad obj) throws Exception {
        this.plaRecuSeguridad = obj;
        b_editar = true;
    }

    public void editar(Event event) throws Exception {
        System.out.println("test");
    }
    
    public String getVigencia(Date fechaFin) {
        Date fechaActual = new Date();
        return (fechaFin != null && fechaFin.before(fechaActual)) ? "VENCIDO" : "VIGENTE";
    }

    public void preGestionar() {
        initPlaRecuSeguridad();
        codigo_TM = "";
        motivoSelectedId = 0;
        b_editar = false;
    }

    public void prepDownloadLocal(String path) throws Exception {
        if ("PLANTILLA_CARGA_RECURSOS_SEGURIDAD".equals(path)) {
            path = getProperty("PLANTILLA_CARGA_RECURSOS_SEGURIDAD");
        }
        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
    }
    
    /**
     * Consultar jorndas por rango de fechas y limpiar los filtros de la tabla
     */
    public void consultar() {
        if (pau == null) {
            listPlaRecuSeguridad = new ArrayList<>();
        } else {
            listPlaRecuSeguridad = seguridadEJB.findAllByFechaRange(desde, hasta);
        }
        PrimeFaces.current().executeScript("PF('w_Actividades').clearFilters()");
    }

    void validarRol() {
        if (rol_user.equals("ROLE_PROFGEN") || rol_user.equals("ROLE_PROFMTTO")
                || rol_user.equals("ROLE_PROFOP")) {
//            b_autoriza = true;
//            b_genera = true;
//            b_generaDelete = true;
//            b_controlAutoriza = true;
//            flag_cargar_jornada = false;
        }
        if (rol_user.equals("ROLE_EMPLGEN") || rol_user.equals("ROLE_MTTO") || rol_user.equals("ROLE_TC")) {
//            b_genera = true;
//            b_generaDelete = false;
//            flag_cargar_jornada = false;
        }
        if (rol_user.equals("ROLE_LIQGEN") || rol_user.equals("ROLE_LIQMTTO")
                || rol_user.equals("ROLE_LIQ")) {
//            b_liquida = true;
//            b_autoriza = true;
//            b_controlAutoriza = true;
//            b_controlSubirArchivo = true;
//            b_genera = true;
//            b_generaDelete = true;
//            b_controlLiquida = true;
//            flag_cargar_jornada = true;
        }
        if (rol_user.equals("ROLE_LIQMTTO")) {//es el usuario que puede autorizar las novedades de marcación en biométricos
//            b_autorizaMarcaciones = true;
        }
    }

    private boolean existeSeguridad(PlaRecuSeguridad obj) {
        return Objects.nonNull(seguridadEJB.findSeguridad(obj.getFechaInicio(), obj.getFechaFin(),
                obj.getDescripcion(), obj.getEmpleado().getIdEmpleado()));
    }

    private void crearSeguridades(List<Object> list) {
        for (Object obj : list) {
            PlaRecuSeguridad obj_act = (PlaRecuSeguridad) obj;
            if (existeSeguridad(obj_act)) {
                MovilidadUtil.addAdvertenciaMessage("Seguridad " + obj_act.getDescripcion() + " para del día " + obj_act.getFechaInicio() + "ya existe.");
            } else {
                obj_act.setCreado(new Date());
                obj_act.setEstadoReg(0);
                obj_act.setUsernameCreate(user.getUsername());
                obj_act.setIdGopUnidadFuncional(obj_act.getEmpleado().getIdGopUnidadFuncional());
                seguridadEJB.create(obj_act);//persistir la activivdad
                MovilidadUtil.addSuccessMessage("Registro Creado");
                actualizarLista();
            }
        }
        
    }

    /**
     * Permite validar la integridad de la información contenida en el objeto global.
     * Se invoca al momento de persistir información ingresada de forma manual.
     * de la clase Planificación Recursos Seguridad
     * @return true si la información contenida tiene error
     *          false en cualquier otro caso.
     */
    private boolean validarErrorDatosSeguridad(PlaRecuSeguridad plaRecuSeguridad) {
        boolean flag = false;
        if (plaRecuSeguridad != null) {
            if (existeSeguridad(plaRecuSeguridad)) { //validar que los datos a insertar no existan en la base de datos
                MovilidadUtil.addAdvertenciaMessage("El registro 'Seguridad' ya existe.");
                b_error = flag = true;
            } else {
                if (plaRecuSeguridad.getFechaInicio().after(plaRecuSeguridad.getFechaFin())) {
                    MovilidadUtil.addAdvertenciaMessage("'Fecha Fin' no puede ser menor que 'Fecha Inicio'.");
                    b_error = flag = true;
                }
                if(plaRecuSeguridad.getIdMotivo() == null || plaRecuSeguridad.getIdMotivo().getIdPlaRecuMotivo() == 0) {
                    MovilidadUtil.addAdvertenciaMessage("Debe seleccionar un 'Motivo'.");
                    b_error = flag = true;
                }
                if(plaRecuSeguridad.getIdPlaRecuRuta()== null || plaRecuSeguridad.getIdPlaRecuRuta().getIdPlaRecuRuta()== 0) {
                    MovilidadUtil.addAdvertenciaMessage("Debe seleccionar una 'Ruta'.");
                    b_error = flag = true;
                }
            }
        }
        return flag;
    }

    public void crearSeguridad() {
        if (!validarErrorDatosSeguridad(plaRecuSeguridad)) {
            plaRecuSeguridad.setCreado(new Date());
            plaRecuSeguridad.setEstadoReg(0);
            plaRecuSeguridad.setUsernameCreate(user.getUsername());
            plaRecuSeguridad.setIdGopUnidadFuncional(plaRecuSeguridad.getEmpleado().getIdGopUnidadFuncional());
            seguridadEJB.create(plaRecuSeguridad);
            MovilidadUtil.addSuccessMessage("El registro se creó correctamente.");
            PrimeFaces.current().executeScript("PF('wvPlaRecuSeguridad').hide();");
            actualizarLista();
        }
    }

    public void editarSeguridad() {
        plaRecuSeguridad.setEstadoReg(0);
        plaRecuSeguridad.setModificado(new Date());//fecha de creación del registro
        plaRecuSeguridad.setUsernameEdit(user.getUsername());//usuario que modifica el registro
        Empleado emp = empleadoEjb.find(plaRecuSeguridad.getEmpleado().getIdEmpleado());
        plaRecuSeguridad.setEmpleado(emp);
        PlaRecuMotivo con = motivoEJB.find(plaRecuSeguridad.getIdMotivo().getIdPlaRecuMotivo());
        plaRecuSeguridad.setIdMotivo(con);
        PlaRecuRuta rou = rutaEjb.find(plaRecuSeguridad.getIdPlaRecuRuta().getIdPlaRecuRuta());
        plaRecuSeguridad.setIdPlaRecuRuta(rou);
        seguridadEJB.edit(plaRecuSeguridad);//persistir la infracción
        PrimeFaces.current().executeScript("PF('wvPlaRecuSeguridad').hide();");
        MovilidadUtil.addSuccessMessage("Registro Editado");
        actualizarLista();
    }

    private void actualizarLista() {
        uf.setIdGopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        listPlaRecuSeguridad = seguridadEJB.findByIdGopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        plaRecuSeguridad = new PlaRecuSeguridad();
        plaRecuSeguridad.setIdMotivo(new PlaRecuMotivo());
        plaRecuSeguridad.setIdPlaRecuRuta(new PlaRecuRuta());
        plaRecuSeguridad.setEmpleado(new Empleado());
        plaRecuSeguridad.setIdGopUnidadFuncional(new GopUnidadFuncional());
    }

    private void recorrerExcel(FileInputStream inputStream, List<Object> list_obj) throws Exception {
        recorrerExcelPlaRecuSeguridad(inputStream, list_obj);
    }

    private void recorrerExcelPlaRecuSeguridad(FileInputStream inputStream, List<Object> list_obj)
            throws IOException, Exception {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);
        int numFilas = sheet.getLastRowNum();
        boolean error = false;
        for (int a = 1; a <= numFilas; a++) {
            PlaRecuSeguridad seguridad_obj = new PlaRecuSeguridad();
            Row fila = sheet.getRow(a);
            int numCols = fila.getLastCellNum();
            for (int b = 0; b < numCols; b++) {
                Cell celda = fila.getCell(b);
                if (celda != null) {
                    Date parse;
                    try {
                        switch (b) {
                            case 0:// codigo TM
                                BigDecimal valorDecimal = new BigDecimal(celda.toString());
                                //como Bigdecimal nunca es null se evalua directamente
                                Empleado emple = empleadoEjb.getEmpleadoActivoCodigoTM(valorDecimal.intValue());
                                if (emple != null) {
                                    seguridad_obj.setEmpleado(emple);
                                } else {
                                    agregarError((a + 1), "Código TM", "El código TM no existe en la BD",
                                            valorDecimal.longValueExact());
                                    error = true;
                                }
                                break;
                            
                            case 1: // fecha_inicio
                                try {
                                    parse = Util.toDate(Util.dateFormat(celda.getDateCellValue()));
                                } catch (Exception e) {
                                    parse = Util.toDate(celda.toString());
                                }
                                if (parse == null) {
                                    agregarError((a + 1), "Fecha Inicio", "Formato de fecha erróneo", celda.toString());
                                    error = true;
                                } else {
                                    seguridad_obj.setFechaInicio(parse);
                                }
                                break;
                            case 2: // fecha_fin
                                try {
                                    parse = Util.toDate(Util.dateFormat(celda.getDateCellValue()));
                                } catch (Exception e) {
                                    parse = Util.toDate(celda.toString());
                                }
                                if (parse == null) {
                                    agregarError((a + 1), "Fecha Fin", "Formato de fecha erróneo", celda.toString());
                                    error = true;
                                } else {
                                    seguridad_obj.setFechaFin(parse);
                                }
                                break;
                            case 3: // Motivo
                                motivoSelected = motivoEJB.findByName(celda.toString());
                                if (motivoSelected != null) {
                                    seguridad_obj.setIdMotivo(motivoSelected);
                                } else {
                                    agregarError((a + 1), "Motivo", "El nombre de la novedad no existe en la BD", celda.toString());
                                    error = true;
                                }
                                break;
                            case 4: //ruta
                                routeSelected = rutaEjb.findByRuta(celda.toString());
                                if (routeSelected != null) {
                                    seguridad_obj.setIdPlaRecuRuta(routeSelected);
                                } else {
                                    agregarError((a + 1), "Ruta", "El nombre de la ruta no existe en la BD", celda.toString());
                                    error = true;
                                }
                                break;
                            case 5: // DESCRIPCION
                                seguridad_obj.setDescripcion(celda.toString());
                                break;
                            
                        }
                    } catch (Exception e) {
                        agregarError((a + 1), "", "Excepción en la columna " + (b + 1), "Corregir e intentar de nuevo");
                    }
                }
            }
            if (!error) {
                list_obj.add(seguridad_obj);
            }
            error = false;
        }

        wb.close();

        if (listaError.isEmpty()) {
            MovilidadUtil.addSuccessMessage("Proceso 'Carga de Archivo' Finalizado.");
        } else {
            b_error = true;
//            PrimeFaces.current().executeScript("PF('cargar_infracciones_wv').show()");
//            PrimeFaces.current().ajax().update("formErrores:erroresList");
        }
    }

    public void filtrarPorUF() {
        uf.setIdGopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        listPlaRecuSeguridad = seguridadEJB.findByIdGopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }
    
    private void agregarError(int fila, String columna, String error, Object value) {
        listaError.add(new FileLoadError(fila, columna, error, value));
    }

    private String concatenarRutas(int idEmpleado) {
        List<PlaRecuSeguridad> list = seguridadEJB.findByEmpleado(idEmpleado);
        if (list != null && !list.isEmpty()) {
            String rutas="";
            for (PlaRecuSeguridad obj : list) {
                rutas+=obj.getIdPlaRecuRuta().getRuta()+", ";
            }
            return rutas;
        } else {
            return "N.A";
        } 
    }
    
    public String obtenerRestriccion(int idEmpleado) {
        return concatenarRutas(idEmpleado);
    }
    
    public EmpleadoFacadeLocal getEmpleadoEjb() {
        return empleadoEjb;
    }

    public void setEmpleadoEjb(EmpleadoFacadeLocal empleadoEjb) {
        this.empleadoEjb = empleadoEjb;
    }

    public List<FileLoadError> getListaError() {
        return listaError;
    }

    public void setListaError(List<FileLoadError> listaError) {
        this.listaError = listaError;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
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

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public boolean isB_editar() {
        return b_editar;
    }

    public void setB_editar(boolean b_editar) {
        this.b_editar = b_editar;
    }

    public List<Empleado> getListEmpleados() {
        return listEmpleados;
    }

    public void setListEmpleados(List<Empleado> listEmpleados) {
        this.listEmpleados = listEmpleados;
    }

    public int getId_lugar_sel() {
        return id_lugar_sel;
    }

    public void setId_lugar_sel(int id_lugar_sel) {
        this.id_lugar_sel = id_lugar_sel;
    }

    public PlaRecuSeguridadFacadeLocal getSeguridadEJB() {
        return seguridadEJB;
    }

    public void setSeguridadEJB(PlaRecuSeguridadFacadeLocal seguridadEJB) {
        this.seguridadEJB = seguridadEJB;
    }

    public PlaRecuMotivoFacadeLocal getMotivoEJB() {
        return motivoEJB;
    }

    public void setMotivoEJB(PlaRecuMotivoFacadeLocal motivoEJB) {
        this.motivoEJB = motivoEJB;
    }

    public PlaRecuMotivo getNovedadSelected() {
        return motivoSelected;
    }

    public void setNovedadSelected(PlaRecuMotivo motivoSelected) {
        this.motivoSelected = motivoSelected;
    }

    public String getNovedadSel() {
        return novedadSel;
    }

    public void setNovedadSel(String novedadSel) {
        this.novedadSel = novedadSel;
    }

    public Integer getNovedadSelectedId() {
        return motivoSelectedId;
    }

    public void setNovedadSelectedId(Integer motivoSelectedId) {
        this.motivoSelectedId = motivoSelectedId;
    }


    public PlaRecuSeguridad getPlaRecuSeguridad() {
        return plaRecuSeguridad;
    }

    public void setPlaRecuSeguridad(PlaRecuSeguridad plaRecuSeguridad) {
        this.plaRecuSeguridad = plaRecuSeguridad;
    }

    public List<PlaRecuSeguridad> getListPlaRecuSeguridad() {
        return listPlaRecuSeguridad;
    }

    public void setListPlaRecuSeguridad(List<PlaRecuSeguridad> listPlaRecuSeguridad) {
        this.listPlaRecuSeguridad = listPlaRecuSeguridad;
    }

    public Empleado getEmpleadoSelected() {
        return empleadoSelected;
    }

    public void setEmpleadoSelected(Empleado empleadoSelected) {
        this.empleadoSelected = empleadoSelected;
    }

    public PlaRecuMotivo getMotivoSelected() {
        return motivoSelected;
    }

    public void setMotivoSelected(PlaRecuMotivo motivoSelected) {
        this.motivoSelected = motivoSelected;
    }

    public String getCodigo_TM() {
        return codigo_TM;
    }

    public void setCodigo_TM(String codigo_TM) {
        this.codigo_TM = codigo_TM;
    }

    public List<PlaRecuMotivo> getListMotivoes() {
        return listMotivos;
    }

    public void setListMotivoes(List<PlaRecuMotivo> listMotivos) {
        this.listMotivos = listMotivos;
    }

    public String getMotivoSel() {
        return novedadSel;
    }

    public void setMotivoSel(String novedadSel) {
        this.novedadSel = novedadSel;
    }

    public Integer getMotivoSelectedId() {
        return motivoSelectedId;
    }

    public void setMotivoSelectedId(Integer MotivoSelectedId) {
        this.motivoSelectedId = MotivoSelectedId;
    }
    
    public GopUnidadFuncionalSessionBean getUnidadFuncionalSessionBean() {
        return unidadFuncionalSessionBean;
    }

    public void setUnidadFuncionalSessionBean(GopUnidadFuncionalSessionBean unidadFuncionalSessionBean) {
        this.unidadFuncionalSessionBean = unidadFuncionalSessionBean;
    }

    public List<PlaRecuRuta> getListRoute() {
        return listRoute;
    }

    public List<PlaRecuMotivo> getListMotivos() {
        return listMotivos;
    }

    public void setListMotivos(List<PlaRecuMotivo> listMotivos) {
        this.listMotivos = listMotivos;
    }
    
    

    public void setListRoute(List<PlaRecuRuta> listRoute) {
        this.listRoute = listRoute;
    }

    public List<GopUnidadFuncional> getListaGrupoFuncional() {
        return listaGrupoFuncional;
    }

    public void setListaGrupoFuncional(List<GopUnidadFuncional> listaGrupoFuncional) {
        this.listaGrupoFuncional = listaGrupoFuncional;
    }

    public PlaRecuRuta getRouteSelected() {
        return routeSelected;
    }

    public void setRouteSelected(PlaRecuRuta routeSelected) {
        this.routeSelected = routeSelected;
    }

    public boolean isB_error() {
        return b_error;
    }

    public void setB_error(boolean b_error) {
        this.b_error = b_error;
    }

    public GopUnidadFuncional getUf() {
        return uf;
    }

    public void setUf(GopUnidadFuncional uf) {
        this.uf = uf;
    }

    public String getDia_inicio() {
        return dia_inicio;
    }

    public void setDia_inicio(String dia_inicio) {
        this.dia_inicio = dia_inicio;
    }

    public String getDia_fin() {
        return dia_fin;
    }

    public void setDia_fin(String dia_fin) {
        this.dia_fin = dia_fin;
    }

}

