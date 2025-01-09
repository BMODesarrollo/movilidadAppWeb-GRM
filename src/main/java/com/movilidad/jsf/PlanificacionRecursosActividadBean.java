package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.ejb.ActividadColFacadeLocal;
import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.model.error.FileLoadError;
import com.movilidad.model.planificacion_recursos.ActividadCol;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;
import com.movilidad.ejb.PlaRecuLugarFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.planificacion_recursos.PlaRecuLugar;
import static com.movilidad.utils.MovilidadUtil.getProperty;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;

/**
 * Clase control que permite dar gestión a los métodos de las tablas
 * actividad_col
 *
 * @author luisMiguel.Lancheros
 */
@Named(value = "planificacionRecursosActividadBean")
@ViewScoped
public class PlanificacionRecursosActividadBean implements Serializable {

    @EJB
    private ActividadColFacadeLocal actividadEspecificaEJB;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;
    @EJB
    private PlaRecuLugarFacadeLocal lugarEJB;
    @EJB
    private GopUnidadFuncionalFacadeLocal gopUnidadFuncionalFacadeLocal;
    @EJB
    private ConfigEmpresaFacadeLocal configEmpresaFacadeLocal;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;

    //colecciones
    private List<ActividadCol> listActividadesEspecificas;
    private List<ActividadCol> listActividadesEspecificasSelected = new ArrayList<>();
    private List<Empleado> listEmpleados;
    private List<PlaRecuLugar> listPlaRecuLugar;
    List<FileLoadError> listaError;
    private List<GopUnidadFuncional> listaGrupoFuncional;

    //atributos
    private ActividadCol actividadEspecifica;
    private PlaRecuLugar lugarSelected;
    private GopUnidadFuncional uf;
    private int id_lugar_sel;
    private UploadedFile uploadedFile;
    private Date desde, hasta;
    private String codigo_TM;
    private Empleado empleadoSelected;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private ParamAreaUsr pau;
    private boolean b_editar;
    private boolean b_error;
    private ActividadCol ActividadGenerica;
    String dia_inicio;
    String dia_fin;
    private String descripcion;
    private boolean flag_user_cantidad;
    StringBuilder errorMessages = new StringBuilder();

    @PostConstruct
    public void init() {
        listaGrupoFuncional = gopUnidadFuncionalFacadeLocal.findAll();
        actividadEspecifica = new ActividadCol();
        actividadEspecifica.setEmpleado(new Empleado());
        actividadEspecifica.setPlaRecuLugar(new PlaRecuLugar());
        actividadEspecifica.setIdGopUnidadFuncional(new GopUnidadFuncional());
        restringirCalendario();
        uploadedFile = null;
        b_editar = false;
        b_error = false;
        lugarSelected = new PlaRecuLugar();
        listaError = new ArrayList<>();
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
        listEmpleados = empleadoEjb.findAll(); //cargar empleados activos
        listPlaRecuLugar = lugarEJB.findAll();
        uf = new GopUnidadFuncional();
        uf.setIdGopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        listActividadesEspecificas = actividadEspecificaEJB.findAll();
    }
    
    public void preGestionar() {
        codigo_TM = "";
        b_editar = false;
        actividadEspecifica = new ActividadCol();
        actividadEspecifica.setEmpleado(new Empleado());
        actividadEspecifica.setPlaRecuLugar(new PlaRecuLugar());
        actividadEspecifica.setIdGopUnidadFuncional(new GopUnidadFuncional());
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

    public void consultar() {
        listActividadesEspecificas = actividadEspecificaEJB.findAllByFechaRange(desde, hasta);
        PrimeFaces.current().executeScript("PF('w_Actividades_especificas').clearFilters()");
    }

    public void cargarDatos() {
        listActividadesEspecificas = actividadEspecificaEJB.findAllByGropUnidadFun(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        actividadEspecifica = new ActividadCol();
        actividadEspecifica.setEmpleado(new Empleado());
        actividadEspecifica.setPlaRecuLugar(new PlaRecuLugar());
        actividadEspecifica.setIdGopUnidadFuncional(new GopUnidadFuncional());
        //uf.setIdGopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        //listActividadesEspecificas = actividadEspecificaEJB.findAllByGropUnidadFun(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void onRowSelect(SelectEvent event) throws ParseException {
        ActividadGenerica = new ActividadCol();
        ActividadGenerica = (ActividadCol) event.getObject();
    }

    public void onToggleSelect(ToggleSelectEvent event) throws ParseException {
        if (!event.isSelected()) {
            listActividadesEspecificasSelected.clear();
        }
    }

    public void guardarDescripcion() {
        if (!listActividadesEspecificasSelected.isEmpty()) {
            String primeraDescripcion = listActividadesEspecificasSelected.get(0).getDescripcion();
            boolean todasIguales = listActividadesEspecificasSelected.stream()
                    .allMatch(obj -> primeraDescripcion.equals(obj.getDescripcion()));

            if (!todasIguales) {
                MovilidadUtil.addErrorMessage("Las descripciones de las actividades seleccionadas no coinciden.");
                return;
            }

            for (ActividadCol obj : listActividadesEspecificasSelected) {
                obj.setDescripcion(descripcion);
                obj.setEstado("1");
                obj.setModificado(new Date());
                obj.setUsernameEdit(user.getUsername());
                actividadEspecificaEJB.edit(obj);
            }

            MovilidadUtil.updateComponent("form-registrar-especifica:msgs_create_act");
            MovilidadUtil.addSuccessMessage("Aprobados Correctamente");
            cargarDatos();
        }
    }

    public void rechazarMasivo() {
        if (!listActividadesEspecificasSelected.isEmpty()) {
            for (ActividadCol obj : listActividadesEspecificasSelected) {
                obj.setEstado("2");
                obj.setModificado(new Date());//fecha de creación del registro
                obj.setUsernameEdit(user.getUsername());//usuario que modifica el registro
                actividadEspecificaEJB.edit(obj);
            }
            MovilidadUtil.updateComponent("form-registrar-especifica:msgs_create_act");
            MovilidadUtil.addSuccessMessage("Rechazados Correctamente");
        }
        cargarDatos();
    }

    public void gestionarMasivo() {
        if (!listActividadesEspecificasSelected.isEmpty()) {
            for (ActividadCol obj : listActividadesEspecificasSelected) {
                obj.setEstado("3");
                obj.setModificado(new Date());//fecha de creación del registro
                obj.setUsernameEdit(user.getUsername());//usuario que modifica el registro
                actividadEspecificaEJB.edit(obj);
            }
            MovilidadUtil.updateComponent("form-registrar-especifica:msgs_create_act");
            MovilidadUtil.addSuccessMessage("Gestionados Correctamente");
        }
        cargarDatos();
    }

    public String getFechaActual() {
        LocalDate fecha = LocalDate.now();
        return fecha.toString();
    }

    public Date getFechaMaximo() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_YEAR, 4);
        return cal.getTime();
    }

    public void prepDownloadLocal(String path) throws Exception {
        if ("PLANTILLA_CARGA_RECURSOS_ACTIVIDADES".equals(path)) {
            path = getProperty("PLANTILLA_CARGA_RECURSOS_ACTIVIDADES");
        }
        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
    }

    /**
     * Método resposable de cargar el archivo Excel seleccionado en la vista
     *
     * @param event Contiene el archivo cargado desde la vista.
     */
    public void cargarActividadesEspecificas(FileUploadEvent event) {
        try {
            this.uploadedFile = event.getFile();
            if (uploadedFile != null) {
                List<Object> list = new ArrayList<>();
                String path = Util.saveFile(uploadedFile, 0, "planificacionRecursosActividades");
                FileInputStream fileInputStream = new FileInputStream(new File(path));
                recorrerExcel(fileInputStream, list);
                crearActividadesEspecificas(list);
            }
            if (!b_error) {
                MovilidadUtil.addSuccessMessage("Archivo de 'Actividades' Se generaron nuevos registros");
            } else {
                String errorMessages = listaError.stream()
                        .map(error -> "\n Línea: " + error.getRow() + ", Valor: " + error.getValue() + ", Mensaje: " + error.getMessage())
                        .collect(Collectors.joining("\n"));
                MovilidadUtil.addFatalMessage("Archivo de 'Actividades' Se encontraron los siguientes errores : <br/>" + errorMessages);
            }
        } catch (Exception ex) {
            System.out.println("Error en la carga de archivo " + ex.getMessage());
        } finally {
            uploadedFile = null;
        }
    }

    public void editarEspecifica(ActividadCol actividad) throws Exception {
        this.actividadEspecifica = actividad;
        b_editar = true;
    }

    public String calcularDuracion(ActividadCol actividad) {

        String horaInicioStr = actividad.getHoraIni();
        String horaFinStr = actividad.getHoraFin();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        LocalTime horaInicio = LocalTime.parse(horaInicioStr, formatter);
        LocalTime horaFin = LocalTime.parse(horaFinStr, formatter);

        if (horaInicio != null && horaFin != null) {
            Duration duration = Duration.between(horaInicio, horaFin);
            long horas = duration.toHours();
            long minutos = duration.toMinutes() % 60;
            return String.format("%d:%02d", horas, minutos);
        } else {
            return "00:00";
        }
    }

    public void cargarEmpleado() {
        if (!(codigo_TM.equals("") && codigo_TM.isEmpty())) {
            empleadoSelected = empleadoEjb.getEmpleadoCodigoTM(Integer.parseInt(codigo_TM));
            if (empleadoSelected != null) {
                actividadEspecifica.setEmpleado(empleadoSelected);
                return;
            }
        }
        this.empleadoSelected = null;
        this.codigo_TM = "";
        MovilidadUtil.addErrorMessage("'Código TM' no valido");
        MovilidadUtil.updateComponent("form-registrar-especifica:msgs_create_act");
    }

    private boolean existeActividad(ActividadCol actividad) {
        return Objects.nonNull(actividadEspecificaEJB.findActivity(actividad.getFechaIni(), actividad.getFechaFin(), actividad.getHoraIni(), actividad.getHoraFin(),
                actividad.getDescripcion()));
    }

    private boolean existeActividadEmp(ActividadCol actividad) {
        List<ActividadCol> actividades = actividadEspecificaEJB.findActivityEmp(
                actividad.getFechaIni(),
                actividad.getFechaFin(),
                actividad.getEmpleado().getIdEmpleado());

        return actividades != null && !actividades.isEmpty();
    }

    private void crearActividadesEspecificas(List<Object> list) {
        for (Object obj : list) {
            ActividadCol obj_act = (ActividadCol) obj;
            if (existeActividadEmp(obj_act)) {
                MovilidadUtil.addAdvertenciaMessage("La actividad ya existe.");
            } else {
                obj_act.setCreado(new Date());
                obj_act.setEstado("0");
                obj_act.setIdGopUnidadFuncional(obj_act.getEmpleado().getIdGopUnidadFuncional());
                if (obj_act.getEmpleado().getCodigoTm() == null){
                    obj_act.setEmpleado(null);
                }
                obj_act.setEstadoReg(0);
                obj_act.setUsernameCreate(user.getUsername());
                actividadEspecificaEJB.create(obj_act);//persistir la activivdad
                MovilidadUtil.addSuccessMessage("Registro Creado");
                cargarDatos();
            }
        }

    }

    private boolean validarHoras(ActividadCol actividad) {
        boolean val = false;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date horaMaxima = sdf.parse("23:59:59");

            Date horaInicio = sdf.parse(actividad.getHoraIni());
            Date horaFin = sdf.parse(actividad.getHoraFin());

            if (horaInicio.after(horaMaxima) || horaFin.after(horaMaxima)) {
                MovilidadUtil.addErrorMessage("Las horas ingresadas no pueden ser mayores a 23:59:59");
                val = true;
                return val;
            }

            long diferenciaMilisegundos = horaFin.getTime() - horaInicio.getTime();
            long diferenciaHoras = diferenciaMilisegundos / (1000 * 60 * 60);

            if (diferenciaHoras < 1 || diferenciaHoras > 8) {
                val = true;
            }
            return val;

        } catch (ParseException e) {
            MovilidadUtil.addErrorMessage("Eror en el formato");
        }
        return val;
    }

    private boolean validarErrorDatosSeguridad(ActividadCol actividad) {
        boolean flag = false;
        if (actividad != null) {
            if (existeActividad(actividad)) { //validar que los datos a insertar no existan en la base de datos
                MovilidadUtil.addAdvertenciaMessage("El registro 'Actividad' ya existe.");
                b_error = flag = true;
            } else {
                if (actividad.getFechaIni().after(actividad.getFechaFin())) {
                    MovilidadUtil.addAdvertenciaMessage("'Fecha Fin' no puede ser menor que 'Fecha Inicio'.");
                    b_error = flag = true;
                }
                if (validarHoras(actividad)) {
                    MovilidadUtil.addAdvertenciaMessage("La diferencia de horas debe ser entre 1 hora y no más de 8 horas.");
                    b_error = flag = true;
                }
                if (actividad.getPlaRecuLugar() == null || actividad.getPlaRecuLugar().getIdPlaRecuLugar() == 0) {
                    MovilidadUtil.addAdvertenciaMessage("Debe seleccionar un 'Lugar'.");
                    b_error = flag = true;
                }
            }
        }
        return flag;
    }

    private boolean validarErrorDatosSeguridadEditar(ActividadCol actividad) {
        boolean flag = false;
        if (actividad != null) {
            if (actividad.getFechaIni().after(actividad.getFechaFin())) {
                MovilidadUtil.addAdvertenciaMessage("'Fecha Fin' no puede ser menor que 'Fecha Inicio'.");
                b_error = flag = true;
            }
            if (validarHoras(actividad)) {
                MovilidadUtil.addAdvertenciaMessage("La diferencia de horas debe ser entre 1 hora y no más de 8 horas.");
                b_error = flag = true;
            }
            if (actividad.getPlaRecuLugar() == null || actividad.getPlaRecuLugar().getIdPlaRecuLugar() == 0) {
                MovilidadUtil.addAdvertenciaMessage("Debe seleccionar un 'Lugar'.");
                b_error = flag = true;
            }
        }
        return flag;
    }

    public void crearActividadEspecifica() {
        if (!validarErrorDatosSeguridad(actividadEspecifica)) {
            actividadEspecifica.setCreado(new Date());
            actividadEspecifica.setEstado("0");
            actividadEspecifica.setEstadoReg(0);
            actividadEspecifica.setUsernameCreate(user.getUsername());
            actividadEspecifica.setIdGopUnidadFuncional(actividadEspecifica.getEmpleado().getIdGopUnidadFuncional());
            actividadEspecifica.setEmpleado(null);
            actividadEspecificaEJB.create(actividadEspecifica);//persistir la activivdad
            PrimeFaces.current().executeScript("PF('wvPlaRecuActividadesEspecificas').hide();");
            MovilidadUtil.addSuccessMessage("Registro Creado");
            cargarDatos();
        }
    }

    public void editarActividadEspecifica(ActividadCol actividad) {
        actividad.setEstadoReg(0);
        actividad.setModificado(new Date());//fecha de creación del registro
        actividad.setUsernameEdit(user.getUsername());//usuario que modifica el registro
        actividadEspecificaEJB.edit(actividad);//persistir la infracción
    }

    public void editarActividadEspecifica() {
        //la actividadAleatoria viene cargada en el atributo global
        if (!validarErrorDatosSeguridadEditar(actividadEspecifica)) {
            actividadEspecifica.setModificado(new Date());//fecha de creación del registro
            actividadEspecifica.setUsernameEdit(user.getUsername());//usuario que modifica el registro
            if (actividadEspecifica.getEmpleado() != null) {
                Empleado emp = empleadoEjb.find(actividadEspecifica.getEmpleado().getIdEmpleado());
                actividadEspecifica.setEmpleado(emp);
            } else {
                actividadEspecifica.setEmpleado(null);
            }
            actividadEspecificaEJB.edit(actividadEspecifica);//persistir la infracción
            MovilidadUtil.addSuccessMessage("Registro Actualizado");
            PrimeFaces.current().executeScript("PF('wvPlaRecuActividadesEspecificas').hide();");
            cargarDatos();
        }
    }

    private void recorrerExcel(FileInputStream inputStream, List<Object> list_actividades) throws Exception {
        recorrerExcelActEspecificas(inputStream, list_actividades);
    }

    private void recorrerExcelActEspecificas(FileInputStream inputStream, List<Object> list_actividades)
            throws IOException, Exception {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);
        int numFilas = sheet.getLastRowNum();
        boolean error = false;
        for (int a = 1; a <= numFilas; a++) {
            ActividadCol actividad_obj = new ActividadCol();
            Row fila = sheet.getRow(a);
            int numCols = fila.getLastCellNum();
            for (int b = 0; b < numCols; b++) {
                Cell celda = fila.getCell(b);
                Date parse;
                try {
                    switch (b) {
                        case 0:// codigo TM
                            flag_user_cantidad = false;
                            if (celda != null) {
                                BigDecimal valorDecimal = new BigDecimal(celda.toString());
                                Empleado emple = empleadoEjb.findByCodigoTM(valorDecimal.intValue());
                                if (emple != null) {
                                    actividad_obj.setEmpleado(emple);
                                    flag_user_cantidad = true;
                                } else {
                                    agregarError((a + 1), "Código TM", "El código TM no existe en la BD",
                                            valorDecimal.longValueExact());
                                    //error = true;
                                }
                            } else {
                                actividad_obj.setEmpleado(new Empleado());
                            }
                            break;
                        case 1: // fecha_ini
                            try {
                            parse = Util.toDate(Util.dateFormat(celda.getDateCellValue()));
                        } catch (Exception e) {
                            parse = Util.toDate(celda.toString());
                        }
                        if (parse == null) {
                            agregarError((a + 1), "Fecha Fin DP", "Formato de fecha erróneo", celda.toString());
                            error = true;
                        } else {
                            actividad_obj.setFechaIni(parse);
                        }
                        break;
                        case 2: // fecha_fin
                            try {
                            parse = Util.toDate(Util.dateFormat(celda.getDateCellValue()));
                        } catch (Exception e) {
                            parse = Util.toDate(celda.toString());
                        }
                        if (parse == null) {
                            agregarError((a + 1), "Fecha Fin DP", "Formato de fecha erróneo", celda.toString());
                            error = true;
                        } else {
                            actividad_obj.setFechaFin(parse);
                        }
                        break;
                        case 3: // hora_ini
                            if (celda.toString() != null && !celda.toString().isEmpty()) {
                                if (Util.isTimeValidate(celda.toString())) {
                                    actividad_obj.setHoraIni(celda.getStringCellValue());
                                } else {
                                    agregarError((a + 1), "Hora Inicio", "Formato de hora erróneo", celda.toString());
                                    error = true;
                                }
                            }
                            break;
                        case 4: // Hora_fin
                            if (celda.toString() != null && !celda.toString().isEmpty()) {
                                if (Util.isTimeValidateTo36(celda.toString())) {
                                    actividad_obj.setHoraFin(celda.getStringCellValue());
                                } else {
                                    agregarError((a + 1), "Hora Fin", "Formato de hora erróneo", celda.toString());
                                    error = true;
                                }
                            }
                            break;
                        case 5: // descripción
                            actividad_obj.setDescripcion(celda.getStringCellValue());
                            break;
                        case 6: // lugarSelected
                            PlaRecuLugar val_lugar = lugarEJB.findByLugar(celda.getStringCellValue());
                            if (val_lugar != null) {
                                actividad_obj.setPlaRecuLugar(val_lugar);
                            } else {
                                agregarError((a + 1), "Lugar", "El nombre del lugar no existe en la BD", celda.toString());
                                error = true;
                            }
                            break;
                        case 7: // cantidad
                            if (flag_user_cantidad) {
                                actividad_obj.setCantidad(null);
                            } else {
                                actividad_obj.setCantidad((int) celda.getNumericCellValue());
                            }
                            break;
                    }
                } catch (Exception e) {
                    agregarError((a + 1), "", "Excepción en la columna " + (b + 1), "Corregir e intentar de nuevo");
                }

            }
            if (!error) {
                if (actividad_obj.getEmpleado().getCodigoTm() != null) {
                    if (existeActEmpleado(actividad_obj.getFechaIni(), actividad_obj.getFechaFin(), actividad_obj.getEmpleado())) {
                        agregarError((a + 1), "Empleado", "Ya existe un registro con este empleado en el rango de fechas especificado", "");
                        MovilidadUtil.addErrorMessage("Ya existe un registro con el empleado en el rango de fechas especificado");
                        error = true;
                        b_error = true;
                    } else {
                        list_actividades.add(actividad_obj);
                    }
                } else {
                    list_actividades.add(actividad_obj);
                }
            }
            error = false;
        }

        wb.close();

        if (listaError.isEmpty()) {
            MovilidadUtil.addSuccessMessage("Proceso 'Carga de Archivo' Finalizado.");
            cargarDatos();
        } else {
            b_error = true;
//            PrimeFaces.current().executeScript("PF('cargar_infracciones_wv').show()");
//            PrimeFaces.current().ajax().update("formErrores:erroresList");
        }
    }

    private boolean existeActEmpleado(Date fechaInicio, Date fechaFin, Empleado empleado) {
        List<ActividadCol> actividadesCargExistentes = actividadEspecificaEJB.findActivityEmp(fechaInicio, fechaFin, empleado.getIdEmpleado());
        return actividadesCargExistentes != null && !actividadesCargExistentes.isEmpty();
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

    public ActividadCol getActividadEspecifica() {
        return actividadEspecifica;
    }

    public void setActividadEspecifica(ActividadCol actividadEspecifica) {
        this.actividadEspecifica = actividadEspecifica;
    }

    public List<ActividadCol> getListActividadesEspecificas() {
        return listActividadesEspecificas;
    }

    public void setListActividadesEspecificas(List<ActividadCol> listActividadesEspecificas) {
        this.listActividadesEspecificas = listActividadesEspecificas;
    }

    public List<Empleado> getListEmpleados() {
        return listEmpleados;
    }

    public void setListEmpleados(List<Empleado> listEmpleados) {
        this.listEmpleados = listEmpleados;
    }

    public List<PlaRecuLugar> getListPlaRecuLugar() {
        return listPlaRecuLugar;
    }

    public void setListPlaRecuLugar(List<PlaRecuLugar> listPlaRecuLugar) {
        this.listPlaRecuLugar = listPlaRecuLugar;
    }

    public PlaRecuLugar getPlaRecuLugarSelected() {
        return lugarSelected;
    }

    public void setPlaRecuLugarSelected(PlaRecuLugar lugarSelected) {
        this.lugarSelected = lugarSelected;
    }

    public int getId_lugar_sel() {
        return id_lugar_sel;
    }

    public void setId_lugar_sel(int id_lugar_sel) {
        this.id_lugar_sel = id_lugar_sel;
    }

    public List<GopUnidadFuncional> getListaGrupoFuncional() {
        return listaGrupoFuncional;
    }

    public void setListaGrupoFuncional(List<GopUnidadFuncional> listaGrupoFuncional) {
        this.listaGrupoFuncional = listaGrupoFuncional;
    }

    public String getCodigo_TM() {
        return codigo_TM;
    }

    public void setCodigo_TM(String codigo_TM) {
        this.codigo_TM = codigo_TM;
    }

    public Empleado getEmpleadoSelected() {
        return empleadoSelected;
    }

    public void setEmpleadoSelected(Empleado empleadoSelected) {
        this.empleadoSelected = empleadoSelected;
    }

    public List<ActividadCol> getListActividadesEspecificasSelected() {
        return listActividadesEspecificasSelected;
    }

    public void setListActividadesEspecificasSelected(List<ActividadCol> listActividadesEspecificasSelected) {
        this.listActividadesEspecificasSelected = listActividadesEspecificasSelected;
    }

    public ActividadCol getActividadGenerica() {
        return ActividadGenerica;
    }

    public void setActividadGenerica(ActividadCol ActividadGenerica) {
        this.ActividadGenerica = ActividadGenerica;
    }

    public ActividadColFacadeLocal getActividadEspecificaEJB() {
        return actividadEspecificaEJB;
    }

    public void setActividadEspecificaEJB(ActividadColFacadeLocal actividadEspecificaEJB) {
        this.actividadEspecificaEJB = actividadEspecificaEJB;
    }

    public PlaRecuLugarFacadeLocal getLugarEJB() {
        return lugarEJB;
    }

    public void setLugarEJB(PlaRecuLugarFacadeLocal lugarEJB) {
        this.lugarEJB = lugarEJB;
    }

    public GopUnidadFuncionalFacadeLocal getGopUnidadFuncionalFacadeLocal() {
        return gopUnidadFuncionalFacadeLocal;
    }

    public void setGopUnidadFuncionalFacadeLocal(GopUnidadFuncionalFacadeLocal gopUnidadFuncionalFacadeLocal) {
        this.gopUnidadFuncionalFacadeLocal = gopUnidadFuncionalFacadeLocal;
    }

    public GopUnidadFuncionalSessionBean getUnidadFuncionalSessionBean() {
        return unidadFuncionalSessionBean;
    }

    public void setUnidadFuncionalSessionBean(GopUnidadFuncionalSessionBean unidadFuncionalSessionBean) {
        this.unidadFuncionalSessionBean = unidadFuncionalSessionBean;
    }

    public boolean isB_error() {
        return b_error;
    }

    public void setB_error(boolean b_error) {
        this.b_error = b_error;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
