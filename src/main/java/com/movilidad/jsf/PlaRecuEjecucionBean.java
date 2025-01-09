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
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.PlaRecuEjecucionFacadeLocal;
import com.movilidad.ejb.PlaRecuNovedadFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.planificacion_recursos.PlaRecuNovedad;
import com.movilidad.model.planificacion_recursos.PlaRecuEjecucion;
import static com.movilidad.utils.MovilidadUtil.getProperty;
import java.math.BigDecimal;
import java.util.Objects;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 * Clase control que permite dar gestión a los métodos de la tabla
 * planificacion_recursos_ejecucion
 *
 * @author Omar.beltran
 */
@Named(value = "plaRecuEjecucionBean")
@ViewScoped
public class PlaRecuEjecucionBean implements Serializable {

    @EJB
    private PlaRecuEjecucionFacadeLocal ejecucionEJB;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;
    @EJB
    private PlaRecuNovedadFacadeLocal novedadEjb;
    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTipoDetEJB;
    @EJB
    private ConfigEmpresaFacadeLocal configEmpresaFacadeLocal;
    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    //colecciones
    private List<PlaRecuEjecucion> listPlaRecuEjecucion;
    private List<Empleado> listEmpleados;
    private List<PlaRecuNovedad> listNovedades;
    List<FileLoadError> listaError;

    //atributos
    private PlaRecuEjecucion plaRecuEjecucion;
    private PlaRecuEjecucion plaRecuEjecucionBeforeEdit;
    private Empleado empleadoSelected;
    private PlaRecuNovedad novedadSelected;
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
    private Integer novedadSelectedId;
    String dia_inicio;
    String dia_fin;

    @PostConstruct
    public void init() {
        initPlaRecuEjecucion();
        initPlaRecuEjecucionSelected();
        restringirCalendario();
        novedadSelected = new PlaRecuNovedad();
        uploadedFile = null;
        b_editar = false;
        b_error = false;
        listaError = new ArrayList<>();
        listPlaRecuEjecucion = ejecucionEJB.findAll();
        listNovedades = novedadEjb.findAll();
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
        listEmpleados = empleadoEjb.findAllEmpleadosActivos(0); //cargar empleados activos
        validarRol();
    }

    private void initPlaRecuEjecucion() {
        plaRecuEjecucion = new PlaRecuEjecucion();
        plaRecuEjecucion.setIdNovedad(new PlaRecuNovedad());
        plaRecuEjecucion.setEmpleado(new Empleado());
    }

    private void initPlaRecuEjecucionSelected() {
        plaRecuEjecucion = new PlaRecuEjecucion();
        plaRecuEjecucion.setIdNovedad(new PlaRecuNovedad());
        plaRecuEjecucion.setEmpleado(new Empleado());
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

    public void novedadSeleccionada(SelectEvent event) {
        if (event != null) {
            novedadSelected = (PlaRecuNovedad) event.getObject();
        }
    }

    /**
     * Método resposable de cargar el archivo Excel seleccionado en la vista
     *
     * @param event Contiene el archivo cargado desde la vista.
     */
    public void cargarEjecucion(FileUploadEvent event) {
        try {
            this.uploadedFile = event.getFile();
            if (uploadedFile != null) {
                List<Object> list = new ArrayList<>();
                String path = Util.saveFile(uploadedFile, 0, "planificacionRecursosEjecucion");
                FileInputStream fileInputStream = new FileInputStream(new File(path));
                recorrerExcel(fileInputStream, list);
                crearEjecuciones(list);
            }
            if (!b_error) {
                MovilidadUtil.addSuccessMessage("Archivo de 'Ejecución' cargado correctamente");
            }
        } catch (Exception ex) {
            System.out.println("Error en la carga de archivo " + ex.getMessage());
            actualizarLista();//actualizar posibles registros cargados
        } finally {
            uploadedFile = null;
        }
    }

    public void cargarEmpleado() {
        if (!(codigo_TM.equals("") && codigo_TM.isEmpty())) {
            empleadoSelected = empleadoEjb.getEmpleadoActivoCodigoTM(Integer.parseInt(codigo_TM));
            if (empleadoSelected != null) {
                plaRecuEjecucion.setEmpleado(empleadoSelected);
                return;
            }
        }
        this.empleadoSelected = null;
        this.codigo_TM = "";
        MovilidadUtil.addErrorMessage("'Código TM' no valido");
        MovilidadUtil.updateComponent("form-registrar-ejecucion:msgs_create_ejecucion");
    }

    public void editar(PlaRecuEjecucion obj) throws Exception {
        this.plaRecuEjecucion = obj;
        //se debe generar nueva instancia y asignar valores irrepetibles 
        plaRecuEjecucionBeforeEdit = new PlaRecuEjecucion();
        plaRecuEjecucionBeforeEdit.setEmpleado(obj.getEmpleado());
        plaRecuEjecucionBeforeEdit.setFechaInicio(obj.getFechaInicio());
        plaRecuEjecucionBeforeEdit.setFechaFin(obj.getFechaFin());
        novedadSelectedId = obj.getIdNovedad().getIdPlaRecuNovedad();
        codigo_TM = obj.getEmpleado().getCodigoTm().toString();
        b_editar = true;
    }

    public void editarEjecucion(PlaRecuEjecucion obj) throws Exception {
        this.plaRecuEjecucion = obj;
        b_editar = true;
    }

    public void editar(Event event) throws Exception {
        System.out.println("test");
    }

    public void preGestionar() {
        initPlaRecuEjecucion();
        codigo_TM = "";
        novedadSelectedId = 0;
        b_editar = false;
    }

    public void prepDownloadLocal(String path) throws Exception {
        if ("PLANTILLA_CARGA_RECURSOS_EJECUCION".equals(path)) {
            path = getProperty("PLANTILLA_CARGA_RECURSOS_EJECUCION");
        }
        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
    }

    /**
     * Consultar jorndas por rango de fechas y limpiar los filtros de la tabla
     */
    public void consultar() {
        if (pau == null) {
            listPlaRecuEjecucion = new ArrayList<>();
        } else {
            listPlaRecuEjecucion = ejecucionEJB.findAllByFechaRange(desde, hasta);
        }
        PrimeFaces.current().executeScript("PF('w_Ejecucion').clearFilters()");
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

    private boolean existeEjecucion(PlaRecuEjecucion obj) {
        return Objects.nonNull(ejecucionEJB.findExecute(obj.getFechaInicio(), obj.getFechaFin(), obj.getEmpleado().getIdEmpleado()));
    }

    private void crearEjecuciones(List<Object> list) {
        for (Object obj : list) {
            PlaRecuEjecucion obj_eje = (PlaRecuEjecucion) obj;
            if (!validarErrorDatosEjecucion(obj_eje)) {
                obj_eje.setCreado(new Date());
                obj_eje.setEstadoReg(0);
                obj_eje.setUsernameCreate(user.getUsername());
                ejecucionEJB.create(obj_eje);//persistir la activivdad
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
    private boolean validarErrorDatosEjecucion(PlaRecuEjecucion before, PlaRecuEjecucion current) {

        boolean flag = false;
        if (current != null) {
            if (isErrorHoras(current.getHoraInicio(), current.getHoraFin())) {
                b_error = flag = true;
            } else { // si no hay error en las horas procede a validar las demás reglas
                if (validarCambios(before, current)) {
                    if (current.getFechaInicio().after(current.getFechaFin())) {
                        MovilidadUtil.addAdvertenciaMessage("'Fecha Fin' no puede ser menor que 'Fecha Inicio'.");
                        b_error = flag = true;
                    }
                    if (existeEjecucion(current)) { //validar que los datos a insertar no existan en la base de datos
//                MovilidadUtil.addAdvertenciaMessage("El operador con código TM " + plaRecuEjecucion.getEmpleado().getCodigoTm()+ " ya tiene novedad cargada entre " + plaRecuEjecucion.getFechaInicio()+ " y " + plaRecuEjecucion.getFechaFin());
                        MovilidadUtil.addAdvertenciaMessage("El operador con código TM " + current.getEmpleado().getCodigoTm() + " ya tiene novedad cargada para las fechas ingresadas.");
                        b_error = flag = true;
                    } else {
                        if (current.getIdNovedad() == null || current.getIdNovedad().getIdPlaRecuNovedad() == 0) {
                            MovilidadUtil.addAdvertenciaMessage("Debe seleccionar una 'Novedad'.");
                            b_error = flag = true;
                        }
                    }
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
    private boolean validarErrorDatosEjecucion(PlaRecuEjecucion current) {
        boolean flag = false;
        if (current != null) {
            if (current.getFechaInicio().after(current.getFechaFin())) {
                MovilidadUtil.addAdvertenciaMessage("'Fecha Fin' no puede ser menor que 'Fecha Inicio'.");
                b_error = flag = true;
            } else {
                if (existeEjecucion(current)) { //validar que los datos a insertar no existan en la base de datos
//                MovilidadUtil.addAdvertenciaMessage("El operador con código TM " + plaRecuEjecucion.getEmpleado().getCodigoTm()+ " ya tiene novedad cargada entre " + plaRecuEjecucion.getFechaInicio()+ " y " + plaRecuEjecucion.getFechaFin());
                    MovilidadUtil.addAdvertenciaMessage("El operador con código TM " + current.getEmpleado().getCodigoTm() + " ya tiene novedad cargada para las fechas ingresadas.");
                    b_error = flag = true;
                } else {
                    flag = isErrorHoras(current.getHoraInicio(), current.getHoraFin());//valida formato hora, hora inicio menor a hora fin y coloca posibles mensajes de error
                    if (current.getFechaInicio().after(current.getFechaFin())) {
                        MovilidadUtil.addAdvertenciaMessage("'Fecha Fin' no puede ser menor que 'Fecha Inicio'.");
                        b_error = flag = true;
                    }
                    if (current.getIdNovedad() == null || current.getIdNovedad().getIdPlaRecuNovedad() == 0) {
                        MovilidadUtil.addAdvertenciaMessage("Debe seleccionar una 'Novedad'.");
                        b_error = flag = true;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * Permite determinar si hay error de formato en la hora inicio o en la hora fin ingresadas como parametro.
     * También evalúa que la hora inicio esté antes de la hora fin.
     * Los formatos de hora deben ser hh:mm:ss
     * @param horaInicio valor que indica la hora inicial a evaluar
     * @param horaInicio valor que indica la hora final a evaluar
     * @return true si @horaInicio o @horaFin tienen error de formato o si @horaInicio es mayor a @horaFin
     *          false en cualquier otro caso.
     */
    private boolean isErrorHoras(String horaIncio, String horaFin) {
        boolean flag = false;
        if (!Util.isTimeValidate(horaIncio)) {
            MovilidadUtil.addAdvertenciaMessage("El formato 'Hora Inicio' no es válido.");
            b_error = flag = true;
        }
        if (!Util.isTimeValidate(horaFin)) {
            MovilidadUtil.addAdvertenciaMessage("El formato 'Hora Fin' no es válido.");
            b_error = flag = true;
        }
        if (!Util.hour1IsLowerOrEqualsToHour2(horaIncio, horaFin)) {
            MovilidadUtil.addAdvertenciaMessage("La 'Hora Fin' no puede ser menor a la 'Hora Inicio'.");
            b_error = flag = true;
        }
        return flag;
    }

    public void crearEjecucion() {
        novedadSelected = novedadEjb.find(novedadSelectedId);
        plaRecuEjecucion.setEmpleado(empleadoSelected);
        plaRecuEjecucion.setIdNovedad(novedadSelected);
        plaRecuEjecucion.setIdGopUnidadFuncional(empleadoSelected.getIdGopUnidadFuncional());
        if (!validarErrorDatosEjecucion(plaRecuEjecucion)) {
            plaRecuEjecucion.setCreado(new Date());
            plaRecuEjecucion.setEstadoReg(0);
            plaRecuEjecucion.setUsernameCreate(user.getUsername());
            ejecucionEJB.create(plaRecuEjecucion);//persistir la activivdad
            plaRecuEjecucion = new PlaRecuEjecucion();
            actualizarLista();
            empleadoSelected = null;
            novedadSelected = null;
            MovilidadUtil.addSuccessMessage("Registro 'Ejecución' creado con éxito.");
            PrimeFaces.current().executeScript("PF('wvPlaRecuEjecucion').hide();");
        }
    }

    public void editarEjecucion() {
        //el registro viene cargado con el atributo global
        if (!validarErrorDatosEjecucion(plaRecuEjecucionBeforeEdit, plaRecuEjecucion)) {
            plaRecuEjecucion.setIdNovedad(novedadEjb.find(novedadSelectedId));
            plaRecuEjecucion.setEstadoReg(0);
            plaRecuEjecucion.setModificado(new Date());//fecha de creación del registro
            plaRecuEjecucion.setUsernameEdit(user.getUsername());//usuario que modifica el registro
            ejecucionEJB.edit(plaRecuEjecucion);//persistir la infracción
            actualizarLista();
            plaRecuEjecucionBeforeEdit = null;
            MovilidadUtil.addSuccessMessage("Registro 'Ejecución' modificado con éxito.");
            PrimeFaces.current().executeScript("PF('wvPlaRecuEjecucion').hide();");
        }
    }

    /**
     * Permite identificar si los cambios que se realizan en el registro se
     * deben validar para evitar duplicidad de registros. La validación se debe
     * realizar cuando se ha modificado el empleado, la fecha inicio o la fecha
     * fin.
     */
    private boolean validarCambios(PlaRecuEjecucion before, PlaRecuEjecucion current) {

        return !(before.getFechaInicio().equals(current.getFechaInicio())
                && before.getFechaFin().equals(current.getFechaFin())
                && before.getEmpleado().getIdEmpleado().equals(current.getEmpleado().getIdEmpleado()));

    }

    private void actualizarLista() {
        listPlaRecuEjecucion = ejecucionEJB.findAll();
    }

    private void recorrerExcel(FileInputStream inputStream, List<Object> list_obj) throws Exception {
        recorrerExcelPlaRecuEjecucion(inputStream, list_obj);
    }

    private void recorrerExcelPlaRecuEjecucion(FileInputStream inputStream, List<Object> list_obj)
            throws IOException, Exception {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);
        int numFilas = sheet.getLastRowNum();
        boolean error = false;
        for (int a = 1; a <= numFilas; a++) {
            PlaRecuEjecucion ejecucion_obj = new PlaRecuEjecucion();
            Row fila = sheet.getRow(a);
            int numCols = fila.getLastCellNum();
            for (int b = 0; b < numCols; b++) {
                Cell celda = fila.getCell(b);
                if (celda != null) {//el campo descripción es el único que se permite nulo
                    Date parse;
                    try {
                        switch (b) {
                            case 0:// codigo TM
                                BigDecimal valorDecimal = new BigDecimal(celda.toString());
                                //como Bigdecimal nunca es null se evalua directamente
                                Empleado emple = empleadoEjb.getEmpleadoActivoCodigoTM(valorDecimal.intValue());
                                if (emple != null) {
                                    ejecucion_obj.setEmpleado(emple);
                                    ejecucion_obj.setIdGopUnidadFuncional(emple.getIdGopUnidadFuncional());
                                } else {
                                    agregarError((a + 1), "Código TM", "El código TM no existe o no está activo en la BD",
                                            valorDecimal.longValueExact());
                                    error = true;
                                }
                                break;
                            case 1: // Novedad
                                novedadSelected = novedadEjb.findByName(celda.toString());
                                if (novedadSelected != null) {
                                    ejecucion_obj.setIdNovedad(novedadSelected);
                                } else {
                                    agregarError((a + 1), "Novedad", "El nombre de la novedad no existe en la BD", celda.toString());
                                    error = true;
                                }
                                break;
                            case 2: // fecha_inicio
                                try {
                                    parse = Util.toDate(Util.dateFormat(celda.getDateCellValue()));
                                } catch (Exception e) {
                                    parse = Util.toDate(celda.toString());
                                }
                                if (parse == null) {
                                    agregarError((a + 1), "Fecha Inicio", "Formato de fecha erróneo", celda.toString());
                                    error = true;
                                } else {
                                    ejecucion_obj.setFechaInicio(parse);
                                }
                                break;
                            case 3: // fecha_fin
                                try {
                                    parse = Util.toDate(Util.dateFormat(celda.getDateCellValue()));
                                } catch (Exception e) {
                                    parse = Util.toDate(celda.toString());
                                }
                                if (parse == null) {
                                    agregarError((a + 1), "Fecha Fin", "Formato de fecha erróneo", celda.toString());
                                    error = true;
                                } else {
                                    if (ejecucion_obj.getFechaInicio().after(parse)) {
                                        agregarError((a + 1), "Fecha Fin", "No puede ser menor a 'Fecha Inicio'", celda.toString());
                                    } else {
                                        ejecucion_obj.setFechaFin(parse);
                                    }
                                }
                                break;
                            case 4: // hora_inicio
                                if (celda.toString() != null && !celda.toString().isEmpty()) {
                                    if (Util.isTimeValidate(celda.toString())) {
                                        ejecucion_obj.setHoraInicio(celda.toString());
                                    } else {
                                        agregarError((a + 1), "Hora Inicio", "Formato de hora erróneo, debe ser HH:mm:ss", celda.toString());
                                    }
                                }
                                break;
                            case 5: // Hora_fin
                                if (celda.toString() != null && !celda.toString().isEmpty()) {
                                    if (Util.isTimeValidateTo36(celda.toString())) {
                                        ejecucion_obj.setHoraFin(celda.toString());
                                    } else {
                                        agregarError((a + 1), "Hora Fin", "Formato de hora erróneo, debe ser HH:mm:ss", celda.toString());
                                    }
                                } //aca debe validar la hora inicio vs hora fin
                                ejecucion_obj.setHoraFin(celda.toString());
                                break;
                            case 6: // descripción
                                ejecucion_obj.setDescripcion(celda.toString());
                                break;
                        }
                    } catch (Exception e) {
                        agregarError((a + 1), "", "Excepción en la columna " + (b + 1), "Corregir e intentar de nuevo");
                    }
                } else {
                    if(b != 6) {
                        agregarError((a + 1),  b == 0 ? "Código TM" : b == 1 ? "Novedad" : b == 2 ? "Fecha Inicio" : b == 3 ? "Fecha Fin" : b == 4 ? "Hora Inicio" : "Hora Fin"
                            , "El valor de la columna no puede ser vacio", "Corregir e intentar de nuevo");
                    }
                }
            }
            if (!error) {
                list_obj.add(ejecucion_obj);
            }
            error = false;
        }

        wb.close();

        if (listaError.isEmpty()) {
            MovilidadUtil.addSuccessMessage("Proceso 'Carga de Archivo Ejecución' Finalizado.");
        } else {
            b_error = true;
            PrimeFaces.current().executeScript("PF('cargar_ejecucion_wv').show()");
            PrimeFaces.current().ajax().update("formErrores:erroresList");
        }
    }

    public void filtrarPorUF() {
        listPlaRecuEjecucion = ejecucionEJB.findByIdGopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
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

    public PlaRecuEjecucion getPlaRecuEjecucion() {
        return plaRecuEjecucion;
    }

    public void setPlaRecuEjecucion(PlaRecuEjecucion plaRecuEjecucion) {
        this.plaRecuEjecucion = plaRecuEjecucion;
    }

    public List<PlaRecuEjecucion> getListPlaRecuEjecucion() {
        return listPlaRecuEjecucion;
    }

    public void setListPlaRecuEjecucion(List<PlaRecuEjecucion> listPlaRecuEjecucion) {
        this.listPlaRecuEjecucion = listPlaRecuEjecucion;
    }

    public Empleado getEmpleadoSelected() {
        return empleadoSelected;
    }

    public void setEmpleadoSelected(Empleado empleadoSelected) {
        this.empleadoSelected = empleadoSelected;
    }

    public PlaRecuNovedad getNovedadSelected() {
        return novedadSelected;
    }

    public void setNovedadSelected(PlaRecuNovedad novedadSelected) {
        this.novedadSelected = novedadSelected;
    }

    public String getCodigo_TM() {
        return codigo_TM;
    }

    public void setCodigo_TM(String codigo_TM) {
        this.codigo_TM = codigo_TM;
    }

    public List<PlaRecuNovedad> getListNovedades() {
        return listNovedades;
    }

    public void setListNovedades(List<PlaRecuNovedad> listNovedades) {
        this.listNovedades = listNovedades;
    }

    public String getNovedadSel() {
        return novedadSel;
    }

    public void setNovedadSel(String novedadSel) {
        this.novedadSel = novedadSel;
    }

    public Integer getNovedadSelectedId() {
        return novedadSelectedId;
    }

    public void setNovedadSelectedId(Integer NovedadSelectedId) {
        this.novedadSelectedId = NovedadSelectedId;
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

}
