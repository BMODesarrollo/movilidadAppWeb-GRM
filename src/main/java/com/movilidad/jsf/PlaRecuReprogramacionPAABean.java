package com.movilidad.jsf;

import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
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
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.PlaRecuModalidadFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.planificacion_recursos.PlaRecuModalidad;
import com.movilidad.model.planificacion_recursos.PlaRecuReprogramacionPAA;
import static com.movilidad.utils.MovilidadUtil.getProperty;
import java.math.BigDecimal;
import java.util.Objects;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import com.movilidad.ejb.PlaRecuReprogramacionPAAFacadeLocal;

/**
 * Clase control que permite dar gestión a los métodos de la tabla
 * planificacion_recursos_reprogramacionPAA
 *
 * @author Omar.beltran
 */
@Named(value = "plaRecuReprogramacionPAABean")
@ViewScoped
public class PlaRecuReprogramacionPAABean implements Serializable {

    @EJB
    private PlaRecuReprogramacionPAAFacadeLocal reprogramacionPAAEJB;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private PlaRecuModalidadFacadeLocal modalidadEjb;
    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTipoDetEJB;
    @EJB
    private ConfigEmpresaFacadeLocal configEmpresaFacadeLocal;
    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    //colecciones
    private List<PlaRecuReprogramacionPAA> listPlaRecuReprogramacionPAA;
    private List<Empleado> listEmpleados;
    private List<PlaRecuModalidad> listModalidad;
    List<FileLoadError> listaError;

    //atributos
    private PlaRecuReprogramacionPAA plaRecuReprogramacionPAA;
    private PlaRecuReprogramacionPAA plaRecuReprogramacionPAABeforeEdit;
    private Empleado empleadoSelected;
    private PlaRecuModalidad modalidadSelected;
    private int id_lugar_sel;
    private String codigo_TM;
    private String modalidadSel;
    private UploadedFile uploadedFile;
    private Date desde, hasta;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private String rol_user = "";
    private boolean b_editar;
    private boolean b_error;
    private Integer modalidadSelectedId;
    String dia_inicio;
    String dia_fin;
    int lunes;
    int martes;
    int miercoles;
    int jueves;
    int viernes;

    @PostConstruct
    public void init() {
        initPlaRecuReprogramacionPAA();
        initPlaRecuReprogramacionPAASelected();
        restringirCalendario();
        modalidadSelected = new PlaRecuModalidad();
        uploadedFile = null;
        b_editar = false;
        b_error = false;
        listaError = new ArrayList<>();
        listPlaRecuReprogramacionPAA = reprogramacionPAAEJB.findAllActiveDays();
        listModalidad = modalidadEjb.findAll();
        listEmpleados = empleadoEjb.findAllEmpleadosActivos(0); //cargar empleados activos
        validarRol();
    }

    private void initPlaRecuReprogramacionPAA() {
        plaRecuReprogramacionPAA = new PlaRecuReprogramacionPAA();
        plaRecuReprogramacionPAA.setLunes(1);
        plaRecuReprogramacionPAA.setMartes(1);
        plaRecuReprogramacionPAA.setMiercoles(1);
        plaRecuReprogramacionPAA.setJueves(1);
        plaRecuReprogramacionPAA.setViernes(1);
        lunes = martes = miercoles = jueves = viernes = 1;
        plaRecuReprogramacionPAA.setEmpleado(new Empleado());
    }

    private void initPlaRecuReprogramacionPAASelected() {
        plaRecuReprogramacionPAA = new PlaRecuReprogramacionPAA();
        plaRecuReprogramacionPAA.setLunes(1);
        plaRecuReprogramacionPAA.setMartes(1);
        plaRecuReprogramacionPAA.setMiercoles(1);
        plaRecuReprogramacionPAA.setJueves(1);
        plaRecuReprogramacionPAA.setViernes(1);
        plaRecuReprogramacionPAA.setEmpleado(new Empleado());
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

    public void modalidadSeleccionada(SelectEvent event) {
        if (event != null) {
            modalidadSelected = (PlaRecuModalidad) event.getObject();
        }
    }

    /**
     * Método resposable de cargar el archivo Excel seleccionado en la vista
     *
     * @param event Contiene el archivo cargado desde la vista.
     */
    public void cargarReprogramacionPAA(FileUploadEvent event) {
        try {
            this.uploadedFile = event.getFile();
            if (uploadedFile != null) {
                List<Object> list = new ArrayList<>();
                String path = Util.saveFile(uploadedFile, 0, "planificacionRecursosReprogramacionPAA");
                FileInputStream fileInputStream = new FileInputStream(new File(path));
                recorrerExcel(fileInputStream, list);
                crearReprogramacionPAAes(list);
            }
            if (!b_error) {
                MovilidadUtil.addSuccessMessage("Archivo de 'Reprogramación PAA' cargado correctamente");
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
                plaRecuReprogramacionPAA.setEmpleado(empleadoSelected);
                return;
            }
        }
        this.empleadoSelected = null;
        this.codigo_TM = "";
        plaRecuReprogramacionPAA.setEmpleado(empleadoSelected);
        MovilidadUtil.addErrorMessage("'Código TM' no valido");
        MovilidadUtil.updateComponent("form-registrar-reprogramacionPAA:msgs_create_reprogramacionPAA");
    }

    public void editar(PlaRecuReprogramacionPAA obj) throws Exception {
        this.plaRecuReprogramacionPAA = obj;
        //se debe generar nueva instancia y asignar valores irrepetibles 
        plaRecuReprogramacionPAABeforeEdit = new PlaRecuReprogramacionPAA();
        plaRecuReprogramacionPAABeforeEdit.setEmpleado(obj.getEmpleado());
        codigo_TM = obj.getEmpleado().getCodigoTm().toString();
        modalidadSelectedId = obj.getIdPlaRecuModalidad().getIdPlaRecuModalidad();
        lunes = obj.getLunes();
        martes = obj.getMartes();
        miercoles = obj.getMiercoles();
        jueves = obj.getJueves();
        viernes = obj.getViernes();
        b_editar = true;
    }

    public void editarReprogramacionPAA(PlaRecuReprogramacionPAA obj) throws Exception {
        this.plaRecuReprogramacionPAA = obj;
        b_editar = true;
    }

    public void editar(Event event) throws Exception {
        System.out.println("test");
    }

    public void preGestionar() {
        initPlaRecuReprogramacionPAA();
        codigo_TM = "";
        modalidadSelectedId = 0;
        b_editar = false;
    }

    public void prepDownloadLocal(String path) throws Exception {
        if ("PLANTILLA_CARGA_RECURSOS_REPROGRAMACION_PAA".equals(path)) {   
            path = getProperty("PLANTILLA_CARGA_RECURSOS_REPROGRAMACION_PAA");
        }
        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
    }

    public void reprogramarDia(PlaRecuReprogramacionPAA obj, String dia) throws Exception {
        switch (dia) {
            case "L":
                obj.setLunes(obj.getLunes() == 0 ? 1 : 0);
                break;
            case "M":
                obj.setMartes(obj.getMartes() == 0 ? 1 : 0);
                break;
            case "X":
                obj.setMiercoles(obj.getMiercoles() == 0 ? 1 : 0);
                break;
            case "J":
                obj.setJueves(obj.getJueves() == 0 ? 1 : 0);
                break;
            case "V":
                obj.setViernes(obj.getViernes() == 0 ? 1 : 0);
                break;
        }
        obj.setUsernameEdit(user.getUsername());
        obj.setModificado(new Date());
        reprogramacionPAAEJB.edit(obj);
        MovilidadUtil.addSuccessMessage("Cambio realizado.");
    }

    public void reprogramarDia(String dia) throws Exception {
        switch (dia) {
            case "L":

                plaRecuReprogramacionPAA.setLunes(plaRecuReprogramacionPAA.getLunes() == 0 ? 1 : 0);
                lunes = plaRecuReprogramacionPAA.getLunes();
                break;
            case "M":
                plaRecuReprogramacionPAA.setMartes(plaRecuReprogramacionPAA.getMartes() == 0 ? 1 : 0);
                martes = plaRecuReprogramacionPAA.getMartes();
                break;
            case "X":
                plaRecuReprogramacionPAA.setMiercoles(plaRecuReprogramacionPAA.getMiercoles() == 0 ? 1 : 0);
                miercoles = plaRecuReprogramacionPAA.getMiercoles();
                break;
            case "J":
                plaRecuReprogramacionPAA.setJueves(plaRecuReprogramacionPAA.getJueves() == 0 ? 1 : 0);
                jueves = plaRecuReprogramacionPAA.getJueves();
                break;
            case "V":
                plaRecuReprogramacionPAA.setViernes(plaRecuReprogramacionPAA.getViernes() == 0 ? 1 : 0);
                viernes = plaRecuReprogramacionPAA.getViernes();
                break;
        }
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

    private boolean existeReprogramacionPAA(PlaRecuReprogramacionPAA obj) {
        return Objects.nonNull(reprogramacionPAAEJB.findReprogramacionPAA(obj.getEmpleado().getIdEmpleado()));
    }

    private void crearReprogramacionPAAes(List<Object> list) {
        for (Object obj : list) {
            PlaRecuReprogramacionPAA obj_ausPAA = (PlaRecuReprogramacionPAA) obj;
            if (existeReprogramacionPAA(obj_ausPAA)) {
                MovilidadUtil.addAdvertenciaMessage("El operador identificado con código TM " + obj_ausPAA.getEmpleado().getCodigoTm() + "ya tiene un registro 'Reprogramacion Plan Actualización Anual' activo");
            } else {
                obj_ausPAA.setCreado(new Date());
                obj_ausPAA.setEstadoReg(0);
                obj_ausPAA.setUsernameCreate(user.getUsername());
                reprogramacionPAAEJB.create(obj_ausPAA);//persistir el registro en la tabla
            }
        }
        actualizarLista();
    }

    /**
     * Permite validar la integridad de la información contenida en el objeto
     * global. Se invoca al momento de persistir información ingresada de forma
     * manual. de la clase Planificación Recursos Ejecución
     *
     * @return true si la información contenida tiene error false en cualquier
     * otro caso.
     */
    private boolean validarErrorDatosReprogramacionPAA(PlaRecuReprogramacionPAA current) {

        boolean flag = false;
        if (current != null) {
            if (current.getIdPlaRecuModalidad() == null || current.getIdPlaRecuModalidad().getIdPlaRecuModalidad() == 0) {
                MovilidadUtil.addAdvertenciaMessage("Debe seleccionar una 'Modalidad'.");
                b_error = flag = true;
            } else {
                if (existeReprogramacionPAA(current)) { //validar que los datos a insertar no existan en la base de datos
                    MovilidadUtil.addAdvertenciaMessage("El operador con código TM " + current.getEmpleado().getCodigoTm() + " ya tiene reprogramación activa.");
                    b_error = flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * Permite validar la integridad de la información contenida en el objeto
     * global. Se invoca al momento de persistir información ingresada de forma
     * manual. de la clase Planificación Recursos Ejecución
     *
     * @return true si la información contenida tiene error false en cualquier
     * otro caso.
     */
    private boolean validarErrorDatosReprogramacionPAA(PlaRecuReprogramacionPAA before, PlaRecuReprogramacionPAA current) {

        boolean flag = false;
        if (current != null) {
            if (validarCambios(before, current)) {
                if (current.getIdPlaRecuModalidad() == null || current.getIdPlaRecuModalidad().getIdPlaRecuModalidad() == 0) {
                    MovilidadUtil.addAdvertenciaMessage("Debe seleccionar una 'Modalidad'.");
                    b_error = flag = true;
                } else {
                    if (existeReprogramacionPAA(current)) { //validar que los datos a insertar no existan en la base de datos
                        MovilidadUtil.addAdvertenciaMessage("El operador con código TM " + current.getEmpleado().getCodigoTm() + " ya tiene una reprogramación activa.");
                        b_error = flag = true;
                    }
                }
            }
        }
        return flag;
    }

    public void crearReprogramacionPAA() {
        modalidadSelected = modalidadEjb.find(modalidadSelectedId);
        plaRecuReprogramacionPAA.setEmpleado(empleadoSelected);
        plaRecuReprogramacionPAA.setIdPlaRecuModalidad(modalidadSelected);
        plaRecuReprogramacionPAA.setIdGopUnidadFuncional(empleadoSelected.getIdGopUnidadFuncional());
        if (!validarErrorDatosReprogramacionPAA(plaRecuReprogramacionPAA)) {
            if (!existeReprogramacionPAA(plaRecuReprogramacionPAA)) {
                plaRecuReprogramacionPAA.setCreado(new Date());
                plaRecuReprogramacionPAA.setEstadoReg(0);
                plaRecuReprogramacionPAA.setUsernameCreate(user.getUsername());
                reprogramacionPAAEJB.create(plaRecuReprogramacionPAA);//persistir la activivdad
                plaRecuReprogramacionPAA = new PlaRecuReprogramacionPAA();
                actualizarLista();
                empleadoSelected = null;
                modalidadSelected = null;
                MovilidadUtil.addSuccessMessage("Registro creado con éxito.");
                PrimeFaces.current().executeScript("PF('wvPlaRecuReprogramacionPAA').hide();");
            } else {
                MovilidadUtil.addAdvertenciaMessage("El colaborador con código TM " + plaRecuReprogramacionPAA.getEmpleado().getCodigoTm() + " ya tiene una reprogramación activa");
            }
        }
    }

    public void editarReprogramacionPAA() {
        //el registro viene cargado con el atributo global
        if (!validarErrorDatosReprogramacionPAA(plaRecuReprogramacionPAABeforeEdit, plaRecuReprogramacionPAA)) {
            plaRecuReprogramacionPAA.setIdPlaRecuModalidad(modalidadEjb.find(modalidadSelectedId));
            plaRecuReprogramacionPAA.setEstadoReg(0);
            plaRecuReprogramacionPAA.setModificado(new Date());//fecha de creación del registro
            plaRecuReprogramacionPAA.setUsernameEdit(user.getUsername());//usuario que modifica el registro
            reprogramacionPAAEJB.edit(plaRecuReprogramacionPAA);//persistir la infracción
            actualizarLista();
            plaRecuReprogramacionPAABeforeEdit = null;
            MovilidadUtil.addSuccessMessage("Registro modificado con éxito.");
            PrimeFaces.current().executeScript("PF('wvPlaRecuReprogramacionPAA').hide();");
        }
    }

    /**
     * Permite identificar si los cambios que se realizan en el registro se
     * deben validar para evitar duplicidad de registros. La validación se debe
     * realizar cuando se ha modificado el empleado, la fecha inicio PAA
     */
    private boolean validarCambios(PlaRecuReprogramacionPAA before, PlaRecuReprogramacionPAA current) {
        /// validar cambios

        return !before.getEmpleado().getCodigoTm().equals(current.getEmpleado().getCodigoTm());

    }

    private void actualizarLista() {
        //cargar solo los registros que tienen días por reprogramar
        listPlaRecuReprogramacionPAA = reprogramacionPAAEJB.findAllActiveDays();
    }

    private void recorrerExcel(FileInputStream inputStream, List<Object> list_obj) throws Exception {
        recorrerExcelPlaRecuReprogramacionPAA(inputStream, list_obj);
    }

    private void recorrerExcelPlaRecuReprogramacionPAA(FileInputStream inputStream, List<Object> list_obj)
            throws IOException, Exception {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);
        int numFilas = sheet.getLastRowNum();
        boolean error = false;
        for (int a = 1; a <= numFilas; a++) {
            PlaRecuReprogramacionPAA reprogramacionPAA_obj = new PlaRecuReprogramacionPAA();
            Row fila = sheet.getRow(a);
            int numCols = fila.getLastCellNum();
            for (int b = 0; b < numCols; b++) {
                Cell celda = fila.getCell(b);
                if (celda != null) {
                    try {
                        switch (b) {
                            case 0:// codigo TM
                                BigDecimal valorDecimal = new BigDecimal(celda.toString());
                                //como Bigdecimal nunca es null se evalua directamente
                                Empleado emple = empleadoEjb.findByCodigoTM(valorDecimal.intValue());
                                if (emple != null) {
                                    reprogramacionPAA_obj.setEmpleado(emple);
                                    reprogramacionPAA_obj.setIdGopUnidadFuncional(emple.getIdGopUnidadFuncional());
                                } else {
                                    agregarError((a + 1), "Código TM", "El código TM no existe en la BD",
                                            valorDecimal.longValueExact());
                                    error = true;
                                }
                                break;
                            case 1: // Modalidad
                                modalidadSelected = modalidadEjb.findByName(celda.toString());
                                if (modalidadSelected != null) {
                                    reprogramacionPAA_obj.setIdPlaRecuModalidad(modalidadSelected);
                                } else {
                                    agregarError((a + 1), "Modalidad", "El nombre de la modalidad no existe en la BD", celda.toString());
                                    error = true;
                                }
                                break;
                            case 2: // lunes
                                try {
                                    valorDecimal = new BigDecimal(celda.toString());
                                    //como Bigdecimal nunca es null se evalua directamente
                                    reprogramacionPAA_obj.setLunes(valorDecimal.intValue());
                                } catch (Exception e) {
                                    agregarError((a + 1), "Lunes", "Debe ser un valor numérico (0 o 1)", celda.toString());
                                    error = true;
                                }
                                break;
                            case 3: // martes  
                                try {
                                    valorDecimal = new BigDecimal(celda.toString());
                                    //como Bigdecimal nunca es null se evalua directamente
                                    reprogramacionPAA_obj.setMartes(valorDecimal.intValue());
                                } catch (Exception e) {
                                    agregarError((a + 1), "Martes", "Debe ser un valor numérico (0 o 1)", celda.toString());
                                    error = true;
                                }
                                break;
                            case 4: // miercoles  
                                try {
                                    valorDecimal = new BigDecimal(celda.toString());
                                    //como Bigdecimal nunca es null se evalua directamente
                                    reprogramacionPAA_obj.setMiercoles(valorDecimal.intValue());
                                } catch (Exception e) {
                                    agregarError((a + 1), "Miércoles", "Debe ser un valor numérico (0 o 1)", celda.toString());
                                    error = true;
                                }
                                break;
                            case 5: // jueves 
                                try {
                                    valorDecimal = new BigDecimal(celda.toString());
                                    //como Bigdecimal nunca es null se evalua directamente
                                    reprogramacionPAA_obj.setJueves(valorDecimal.intValue());
                                } catch (Exception e) {
                                    agregarError((a + 1), "Jueves", "Debe ser un valor numérico (0 o 1)", celda.toString());
                                    error = true;
                                }
                                break;
                            case 6: // viernes  
                                try {
                                    valorDecimal = new BigDecimal(celda.toString());
                                    //como Bigdecimal nunca es null se evalua directamente
                                    reprogramacionPAA_obj.setViernes(valorDecimal.intValue());
                                } catch (Exception e) {
                                    agregarError((a + 1), "Viernes", "Debe ser un valor numérico (0 o 1)", celda.toString());
                                    error = true;
                                }
                                break;
                            case 7: // descripción
                                reprogramacionPAA_obj.setObservaciones(celda.toString());
                                break;
                        }
                    } catch (Exception e) {
                        agregarError((a + 1), "", "Excepción en la columna " + (b + 1), "Corregir e intentar de nuevo");
                    }
                }
            }
            if (!error) {
                list_obj.add(reprogramacionPAA_obj);
            }
            error = false;
        }

        wb.close();

        if (listaError.isEmpty()) {
            MovilidadUtil.addSuccessMessage("Proceso 'Carga de Archivo Reprogramación PAA' Finalizado.");
        } else {
            b_error = true;
            PrimeFaces.current().executeScript("PF('cargar_reprogramacionPAA_wv').show()");
            PrimeFaces.current().ajax().update("formErrores:erroresList");
        }
    }

    public void filtrarPorUF() {
        listPlaRecuReprogramacionPAA = reprogramacionPAAEJB.findByIdGopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    private void agregarError(int fila, String columna, String error, Object value) {
        listaError.add(new FileLoadError(fila, columna, error, value));
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

    public NovedadTipoDetallesFacadeLocal getNovedadTipoDetEJB() {
        return novedadTipoDetEJB;
    }

    public void setNovedadTipoDetEJB(NovedadTipoDetallesFacadeLocal novedadTipoDetEJB) {
        this.novedadTipoDetEJB = novedadTipoDetEJB;
    }

    public PlaRecuReprogramacionPAA getPlaRecuReprogramacionPAA() {
        return plaRecuReprogramacionPAA;
    }

    public void setPlaRecuReprogramacionPAA(PlaRecuReprogramacionPAA plaRecuReprogramacionPAA) {
        this.plaRecuReprogramacionPAA = plaRecuReprogramacionPAA;
    }

    public List<PlaRecuReprogramacionPAA> getListPlaRecuReprogramacionPAA() {
        return listPlaRecuReprogramacionPAA;
    }

    public void setListPlaRecuReprogramacionPAA(List<PlaRecuReprogramacionPAA> listPlaRecuReprogramacionPAA) {
        this.listPlaRecuReprogramacionPAA = listPlaRecuReprogramacionPAA;
    }

    public Empleado getEmpleadoSelected() {
        return empleadoSelected;
    }

    public void setEmpleadoSelected(Empleado empleadoSelected) {
        this.empleadoSelected = empleadoSelected;
    }

    public PlaRecuModalidad getModalidadSelected() {
        return modalidadSelected;
    }

    public void setModalidadSelected(PlaRecuModalidad modalidadSelected) {
        this.modalidadSelected = modalidadSelected;
    }

    public String getCodigo_TM() {
        return codigo_TM;
    }

    public void setCodigo_TM(String codigo_TM) {
        this.codigo_TM = codigo_TM;
    }

    public List<PlaRecuModalidad> getListModalidad() {
        return listModalidad;
    }

    public void setListModalidad(List<PlaRecuModalidad> listModalidad) {
        this.listModalidad = listModalidad;
    }

    public String getModalidadSel() {
        return modalidadSel;
    }

    public void setModalidadSel(String modalidadSel) {
        this.modalidadSel = modalidadSel;
    }

    public Integer getModalidadSelectedId() {
        return modalidadSelectedId;
    }

    public void setModalidadSelectedId(Integer modalidadSelectedId) {
        this.modalidadSelectedId = modalidadSelectedId;
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

    public boolean isB_error() {
        return b_error;
    }

    public void setB_error(boolean b_error) {
        this.b_error = b_error;
    }

    public int getLunes() {
        return lunes;
    }

    public void setLunes(int lunes) {
        this.lunes = lunes;
    }

    public int getMartes() {
        return martes;
    }

    public void setMartes(int martes) {
        this.martes = martes;
    }

    public int getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(int miercoles) {
        this.miercoles = miercoles;
    }

    public int getJueves() {
        return jueves;
    }

    public void setJueves(int jueves) {
        this.jueves = jueves;
    }

    public int getViernes() {
        return viernes;
    }

    public void setViernes(int viernes) {
        this.viernes = viernes;
    }

}
