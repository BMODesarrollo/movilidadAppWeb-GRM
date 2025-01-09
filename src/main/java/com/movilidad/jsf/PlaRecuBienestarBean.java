package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.model.error.FileLoadError;
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
import com.movilidad.ejb.PlaRecuBienestarFacadeLocal;
import com.movilidad.ejb.PlaRecuMotivoFacadeLocal;
import com.movilidad.ejb.PlaRecuTurnoFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.planificacion_recursos.PlaRecuMotivo;
import com.movilidad.model.planificacion_recursos.PlaRecuBienestar;
import com.movilidad.model.planificacion_recursos.PlaRecuTurno;
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
@Named(value = "plaRecuBienestarBean")
@ViewScoped
public class PlaRecuBienestarBean implements Serializable {

    @EJB
    private PlaRecuBienestarFacadeLocal bienestarEJB;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;
    @EJB
    private PlaRecuMotivoFacadeLocal motivoEJB;
    @EJB
    private PlaRecuTurnoFacadeLocal turnoEJB;
    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    //colecciones
    private List<PlaRecuBienestar> listPlaRecuBienestar;
    private List<Empleado> listEmpleados;
    private List<PlaRecuMotivo> listMotivo;
    private List<PlaRecuTurno> listTurno;
    List<FileLoadError> listaError;

    //atributos
    private PlaRecuBienestar plaRecuBienestar;
    private Empleado empleadoSelected;
    private PlaRecuMotivo motivoSelected;
    private PlaRecuTurno turnoSelected;
    private int id_lugar_sel;
    private String codigo_TM;
    private UploadedFile uploadedFile;
    private Date desde, hasta;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private ParamAreaUsr pau;
    private boolean b_editar;
    private boolean b_error;
    private boolean b_otro_turno;
    private Integer motivoSelectedId;
    private Integer turnoSelectedId;

    @PostConstruct
    public void init() {
        initPlaRecuBienestar();
        initPlaRecuBienestarSelected();
        motivoSelected = new PlaRecuMotivo();
        uploadedFile = null;
        b_editar = false;
        b_error = false;
        listaError = new ArrayList<>();
        listPlaRecuBienestar = bienestarEJB.findAll();
        listMotivo = motivoEJB.findAll();
        listTurno = turnoEJB.findAll();
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
        listEmpleados = empleadoEjb.findAllEmpleadosActivos(0); //cargar empleados activos
        b_otro_turno = false;
    }

    private void initPlaRecuBienestar() {
        plaRecuBienestar = new PlaRecuBienestar();
        plaRecuBienestar.setEmpleado(new Empleado());
    }

    private void initPlaRecuBienestarSelected() {
        plaRecuBienestar = new PlaRecuBienestar();
        plaRecuBienestar.setEmpleado(new Empleado());
    }

    public void motivoSeleccionado(SelectEvent event) {
        if (event != null) {
            motivoSelected = (PlaRecuMotivo) event.getObject();
        }
    }

    public void turnoSeleccionado(SelectEvent event) {
        if (event != null) {
            turnoSelected = (PlaRecuTurno) event.getObject();
        }
    }

    /**
     * Método resposable de cargar el archivo Excel seleccionado en la vista
     *
     * @param event Contiene el archivo cargado desde la vista.
     */
    public void cargarBienestar(FileUploadEvent event) {
        try {
            this.uploadedFile = event.getFile();
            if (uploadedFile != null) {
                List<Object> list = new ArrayList<>();
                String path = Util.saveFile(uploadedFile, 0, "planificacionRecursosBienestar");
                FileInputStream fileInputStream = new FileInputStream(new File(path));
                recorrerExcel(fileInputStream, list);
                crearBienestar(list);
            }
            if (!b_error) {
                MovilidadUtil.addSuccessMessage("Archivo de 'Bienestar' cargado correctamente");
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
                plaRecuBienestar.setEmpleado(empleadoSelected);
                return;
            }
        }
        this.empleadoSelected = null;
        this.codigo_TM = "";
        MovilidadUtil.addErrorMessage("'Código TM' no valido");
        MovilidadUtil.updateComponent("form-registrar-bienestar:msgs_create_bienestar");
    }

    public void editar(PlaRecuBienestar obj) throws Exception {
        this.plaRecuBienestar = obj;
        //se debe generar nueva instancia y asignar valores irrepetibles
        motivoSelectedId = obj.getIdPlaRecuMotivo().getIdPlaRecuMotivo();
        turnoSelectedId = obj.getIdPlaRecuTurno().getIdPlaRecuTurno();
        setFlag();// para setear el turno y el horario 
        codigo_TM = obj.getEmpleado().getCodigoTm().toString();
        b_editar = true;
    }

    public void editarBienestar(PlaRecuBienestar obj) throws Exception {
        this.plaRecuBienestar = obj;
        b_editar = true;
    }

    public void preGestionar() {
        initPlaRecuBienestar();
        codigo_TM = "";
        motivoSelectedId = 0;
        turnoSelectedId = 0;
        b_editar = false;
    }

    public void prepDownloadLocal(String path) throws Exception {
        if ("PLANTILLA_CARGA_RECURSOS_BIENESTAR".equals(path)) {
            path = getProperty("PLANTILLA_CARGA_RECURSOS_BIENESTAR");
        }
        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
    }

    /**
     * Consultar registros 'Bienestar' por rango de fechas y limpiar los filtros
     * de la tabla
     */
    public void consultar() {
        if (pau == null) {
            listPlaRecuBienestar = new ArrayList<>();
        } else {
            listPlaRecuBienestar = bienestarEJB.findAllByFechaRange(desde, hasta);
        }
        PrimeFaces.current().executeScript("PF('w_Bienestar').clearFilters()");
    }

    private boolean existeBienestar(PlaRecuBienestar obj) {
        return Objects.nonNull(bienestarEJB.findBienestar(obj.getFechaInicio(), obj.getFechaFin(), obj.getIdPlaRecuTurno().getIdPlaRecuTurno(),
                obj.getEmpleado().getIdEmpleado()));
    }

    private void crearBienestar(List<Object> list) {
        for (Object obj : list) {
            PlaRecuBienestar obj_act = (PlaRecuBienestar) obj;
            if (!validarErrorDatosBienestar(obj_act)) {
                obj_act.setIdGopUnidadFuncional(obj_act.getEmpleado().getIdGopUnidadFuncional());
                obj_act.setVigencia(determinarVigencia(obj_act.getFechaFin()) ? "VIGENTE" : "VENCIDO");
                obj_act.setCreado(new Date());
                obj_act.setEstadoReg(0);
                obj_act.setUsernameCreate(user.getUsername());
                bienestarEJB.create(obj_act);//persistir registro Bienestar
            }
        }
        actualizarLista();
    }

    /**
     * Permite validar la integridad de la información contenida en el objeto
     * global. Se invoca al momento de persistir información ingresada de forma
     * manual. de la clase Planificación Recursos Bienestar
     *
     * @return true si la información contenida tiene error false en cualquier
     * otro caso.
     */
    private boolean validarErrorDatosBienestar(PlaRecuBienestar plaRecuBienestar) {
        boolean flag = false;
        if (plaRecuBienestar != null) {
            if (existeBienestar(plaRecuBienestar)) { //validar que los datos a insertar no existan en la base de datos
                MovilidadUtil.addAdvertenciaMessage("Ya hay un registro de Bienestar para el periodo seleccionado");
                b_error = flag = true;
            } else {
                if (plaRecuBienestar.getIdPlaRecuTurno().getTurno().equalsIgnoreCase("OTRO")) {
                    //solamente cuando se trata de 'OTRO' turno se valida el formato de horas
                    if (!Util.isTimeValidate(turnoSelected.getHoraInicio())) {
                        MovilidadUtil.addAdvertenciaMessage("El formato 'Hora Inicio' no es válido.");
                        b_error = flag = true;
                    }
                    if (!Util.isTimeValidateTo36(turnoSelected.getHoraFin())) {
                        MovilidadUtil.addAdvertenciaMessage("El formato 'Hora Fin' no es válido.");
                        b_error = flag = true;
                    }
                    //si al validar los formatos no hay errorres se asignan los valores de hora al turno
                    if (flag == false) {
//                        plaRecuBienestar.getIdPlaRecuTurno().setHoraInicio(turnoSelected.getHoraInicio());
//                        plaRecuBienestar.getIdPlaRecuTurno().setHoraFin(turnoSelected.getHoraFin());
                        plaRecuBienestar.setHoraInicio(turnoSelected.getHoraInicio());
                        plaRecuBienestar.setHoraFin(turnoSelected.getHoraFin());
                    }
                }
                if (plaRecuBienestar.getFechaInicio().after(plaRecuBienestar.getFechaFin())) {
                    MovilidadUtil.addAdvertenciaMessage("'Fecha Fin' no puede ser menor que 'Fecha Inicio'.");
                    b_error = flag = true;
                }
            }
        }
        return flag;
    }

    public void crearBienestar() {

        if (turnoSelected != null) {
            if (motivoSelectedId != null && motivoSelectedId != 0) {
                motivoSelected = motivoEJB.find(motivoSelectedId);
                plaRecuBienestar.setIdPlaRecuMotivo(motivoSelected);

                plaRecuBienestar.setEmpleado(empleadoSelected);
                plaRecuBienestar.setIdGopUnidadFuncional(empleadoSelected.getIdGopUnidadFuncional());
                if (b_otro_turno) {
                    plaRecuBienestar.setHoraInicio(turnoSelected.getHoraInicio());
                    plaRecuBienestar.setHoraFin(turnoSelected.getHoraFin());
                }
                if (!validarErrorDatosBienestar(plaRecuBienestar)) {
                    plaRecuBienestar.setVigencia(determinarVigencia(plaRecuBienestar.getFechaFin()) ? "VIGENTE" : "VENCIDO");
                    plaRecuBienestar.setCreado(new Date());
                    plaRecuBienestar.setEstadoReg(0);
                    plaRecuBienestar.setUsernameCreate(user.getUsername());
                    bienestarEJB.create(plaRecuBienestar);//persistir la activivdad
                    plaRecuBienestar = new PlaRecuBienestar();
                    actualizarLista();
                    empleadoSelected = null;
                    motivoSelected = null;
                    turnoSelected = null;
                    b_otro_turno = false;
                    PrimeFaces.current().executeScript("PF('wvPlaRecuBienestar').hide()");
                }
            } else {
                MovilidadUtil.addAdvertenciaMessage("Debe seleccionar un 'Motivo'.");
            }
        } else {
            MovilidadUtil.addAdvertenciaMessage("Debe seleccionar un 'Turno'.");
        }
    }

    public void editarBienestar() {
        //el registro viene cargado con el atributo global
        if (!validarErrorDatosBienestar(plaRecuBienestar)) {
            plaRecuBienestar.setVigencia(determinarVigencia(plaRecuBienestar.getFechaFin()) ? "VIGENTE" : "VENCIDO");
            plaRecuBienestar.setEstadoReg(0);
            plaRecuBienestar.setModificado(new Date());//fecha de creación del registro
            plaRecuBienestar.setUsernameEdit(user.getUsername());//usuario que modifica el registro
            bienestarEJB.edit(plaRecuBienestar);//persistir la infracción
            actualizarLista();
            PrimeFaces.current().executeScript("PF('wvPlaRecuBienestar').hide()");
        }
    }

    private void actualizarLista() {
        listPlaRecuBienestar = bienestarEJB.findAll();
    }

    private void recorrerExcel(FileInputStream inputStream, List<Object> list_obj) throws Exception {
        recorrerExcelPlaRecuBienestar(inputStream, list_obj);
    }

    private void recorrerExcelPlaRecuBienestar(FileInputStream inputStream, List<Object> list_obj)
            throws IOException, Exception {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);
        int numFilas = sheet.getLastRowNum();
        boolean error = false;
        for (int a = 1; a <= numFilas; a++) {
            PlaRecuBienestar bienestar_obj = new PlaRecuBienestar();
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
                                Empleado emple = empleadoEjb.findByCodigoTM(valorDecimal.intValue());
                                if (emple != null) {
                                    bienestar_obj.setEmpleado(emple);
                                } else {
                                    agregarError((a + 1), "Código TM", "El código TM no existe en la BD",
                                            valorDecimal.longValueExact());
                                    error = true;
                                }
                                break;
                            case 1: // Motivo
                                motivoSelected = motivoEJB.findByName(celda.toString());
                                if (motivoSelected != null) {
                                    bienestar_obj.setIdPlaRecuMotivo(motivoSelected);
                                } else {
                                    agregarError((a + 1), "Motivo", "El nombre del motivo no existe en la BD", celda.toString());
                                    error = true;
                                }
                                break;
                            case 2: // Turno
                                turnoSelected = turnoEJB.findByName(celda.toString());
                                if (turnoSelected != null) {
                                    bienestar_obj.setIdPlaRecuTurno(turnoSelected);
                                    if (turnoSelected.getIdPlaRecuTurno() == 1) { //se ha seleccionado OTRO se debe ingresar los valores de hora inicio y fin establecidos en el archivo
                                        celda = fila.getCell(5);// hora_inicio  
                                        if (celda.toString() != null && !celda.toString().isEmpty()) {
                                            if (Util.isTimeValidate(celda.toString())) {
                                                bienestar_obj.setHoraInicio(celda.toString());
                                            } else {
                                                agregarError((a + 1), "Hora Inicio", "Formato de hora erróneo, debe ser HH:mm:ss", celda.toString());
                                            }
                                        }
                                        celda = fila.getCell(6);// Hora_fin
                                        if (celda.toString() != null && !celda.toString().isEmpty()) {
                                            if (Util.isTimeValidateTo36(celda.toString())) {
                                                bienestar_obj.setHoraFin(celda.toString());
                                            } else {
                                                agregarError((a + 1), "Hora Fin", "Formato de hora erróneo, debe ser HH:mm:ss", celda.toString());
                                            }
                                        }
                                        bienestar_obj.setHoraFin(celda.toString());
                                    } else { //asignar hora inicio y hora fin de acuerdo a las reglas definidas
                                        obtenerHorasTurno(turnoSelected, bienestar_obj);
                                    }
                                } else {
                                    agregarError((a + 1), "Turno", "El nombre del turno no existe en la BD", celda.toString());
                                    error = true;
                                }
                                break;
                            case 3: // fecha_inicio
                                try {
                                parse = Util.toDate(Util.dateFormat(celda.getDateCellValue()));
                            } catch (Exception e) {
                                parse = Util.toDate(celda.toString());
                            }
                            if (parse == null) {
                                agregarError((a + 1), "Fecha Inicio", "Formato de fecha erróneo", celda.toString());
                                error = true;
                            } else {
                                bienestar_obj.setFechaInicio(parse);
                            }
                            break;
                            case 4: // fecha_fin
                                try {
                                parse = Util.toDate(Util.dateFormat(celda.getDateCellValue()));
                            } catch (Exception e) {
                                parse = Util.toDate(celda.toString());
                            }
                            if (parse == null) {
                                agregarError((a + 1), "Fecha Fin", "Formato de fecha erróneo", celda.toString());
                                error = true;
                            } else {
                                bienestar_obj.setFechaFin(parse);
                            }
                            break;
                        }
                    } catch (Exception e) {
                        agregarError((a + 1), "", "Excepción en la columna " + (b + 1), "Corregir e intentar de nuevo");
                    }
                }
            }
            if (!error) {
                list_obj.add(bienestar_obj);
            }
            error = false;
        }

        wb.close();

        if (listaError.isEmpty()) {
            MovilidadUtil.addSuccessMessage("Proceso 'Carga de Archivo Bienestar' Finalizado.");
        } else {
            b_error = true;
            PrimeFaces.current().executeScript("PF('cargar_bienestar_wv').show()");
            PrimeFaces.current().ajax().update("formErrores:erroresList");
        }
    }

    private void obtenerHorasTurno(PlaRecuTurno turno_obj, PlaRecuBienestar bienestar_obj) {
        switch (turno_obj.getTurno()) {
            case "MAÑANA": {
                bienestar_obj.setHoraInicio("02:00:00");
                bienestar_obj.setHoraFin("14:00:00");
                break;
            }
            case "INTERMEDIO": {
                bienestar_obj.setHoraInicio("06:00:00");
                bienestar_obj.setHoraFin("21:00:00");
                break;
            }
            case "TARDE": {
                bienestar_obj.setHoraInicio("14:00:00");
                bienestar_obj.setHoraFin("26:00:00");
                break;
            }
        }
    }

    public void setFlag() {
        turnoSelected = turnoEJB.find(turnoSelectedId);
        b_otro_turno = turnoSelected.getTurno().equalsIgnoreCase("OTRO");
        plaRecuBienestar.setIdPlaRecuTurno(turnoSelected);
        plaRecuBienestar.setHoraInicio(turnoSelected.getHoraInicio());
        plaRecuBienestar.setHoraFin(turnoSelected.getHoraFin());
    }

    private boolean determinarVigencia(Date fechaFin) {
        return !fechaFin.before(new Date());
    }

    public void filtrarPorUF() {
        listPlaRecuBienestar = bienestarEJB.findByIdGopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    private void agregarError(int fila, String columna, String error, Object value) {
        listaError.add(new FileLoadError(fila, columna, error, value));
    }

    public String obtenerRestriccion(int idEmpleado) {
        return bienestarEJB.findRestrictionByIdEmployee(idEmpleado);
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

    public PlaRecuBienestar getPlaRecuBienestar() {
        return plaRecuBienestar;
    }

    public void setPlaRecuBienestar(PlaRecuBienestar plaRecuBienestar) {
        this.plaRecuBienestar = plaRecuBienestar;
    }

    public List<PlaRecuBienestar> getListPlaRecuBienestar() {
        return listPlaRecuBienestar;
    }

    public void setListPlaRecuBienestar(List<PlaRecuBienestar> listPlaRecuBienestar) {
        this.listPlaRecuBienestar = listPlaRecuBienestar;
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

    public void setMotivoSelected(PlaRecuMotivo novedadSelected) {
        this.motivoSelected = novedadSelected;
    }

    public PlaRecuTurno getTurnoSelected() {
        return turnoSelected;
    }

    public void setTurnoSelected(PlaRecuTurno turnoSelected) {
        this.turnoSelected = turnoSelected;
    }

    public String getCodigo_TM() {
        return codigo_TM;
    }

    public void setCodigo_TM(String codigo_TM) {
        this.codigo_TM = codigo_TM;
    }

    public List<PlaRecuMotivo> getListMotivo() {
        return listMotivo;
    }

    public void setListMotivo(List<PlaRecuMotivo> listMotivo) {
        this.listMotivo = listMotivo;
    }

    public Integer getMotivoSelectedId() {
        return motivoSelectedId;
    }

    public void setMotivoSelectedId(Integer MotivoSelectedId) {
        this.motivoSelectedId = MotivoSelectedId;
    }

    public Integer getTurnoSelectedId() {
        return turnoSelectedId;
    }

    public void setTurnoSelectedId(Integer turnoSelectedId) {
        this.turnoSelectedId = turnoSelectedId;
    }

    public List<PlaRecuTurno> getListTurno() {
        return listTurno;
    }

    public void setListTurno(List<PlaRecuTurno> listTurno) {
        this.listTurno = listTurno;
    }

    public boolean isB_otro_turno() {
        return b_otro_turno;
    }

    public void setB_otro_turno(boolean b_otro_turno) {
        this.b_otro_turno = b_otro_turno;
    }

}
