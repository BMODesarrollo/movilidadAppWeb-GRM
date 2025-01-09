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
import com.movilidad.model.Empleado;
import com.movilidad.model.planificacion_recursos.PlaRecuAscensoPadron;
import static com.movilidad.utils.MovilidadUtil.getProperty;
import java.math.BigDecimal;
import java.util.Objects;
import javax.inject.Inject;
import com.movilidad.ejb.PlaRecuAscensoPadronFacadeLocal;

/**
 * Clase control que permite dar gestión a los métodos de la tabla
 * planificacion_recursos_ejecucion
 *
 * @author Omar.beltran
 */
@Named(value = "plaRecuAscensoPadronBean")
@ViewScoped
public class PlaRecuAscensoPadronBean implements Serializable {

    @EJB
    private PlaRecuAscensoPadronFacadeLocal ascensoPadronEJB;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;
    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    //colecciones
    private List<PlaRecuAscensoPadron> listAscensoPadrones;
    private List<Empleado> listEmpleados;
    List<FileLoadError> listaError;

    //atributos
    private PlaRecuAscensoPadron forDesAscensoPadron;
    private Empleado empleadoSelected;
    private String codigo_TM;
    private UploadedFile uploadedFile;
    private Date desde, hasta;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private ParamAreaUsr pau;
    private String rol_user = "";
    private boolean b_editar;
    private boolean b_error;

    @PostConstruct
    public void init() {
        initForDesAscensoPadron();
        initForDesAscensoPadronSelected();
        uploadedFile = null;
        b_editar = false;
        b_error = false;
        listaError = new ArrayList<>();
        listAscensoPadrones = ascensoPadronEJB.findAll();
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
        listEmpleados = empleadoEjb.findAllEmpleadosActivos(0); //cargar empleados activos
        validarRol();
    }

    private void initForDesAscensoPadron() {
        forDesAscensoPadron = new PlaRecuAscensoPadron();
        forDesAscensoPadron.setEmpleado(new Empleado());
    }

    private void initForDesAscensoPadronSelected() {
        forDesAscensoPadron = new PlaRecuAscensoPadron();
        forDesAscensoPadron.setEmpleado(new Empleado());
    }

    /**
     * Método resposable de cargar el archivo Excel seleccionado en la vista
     *
     * @param event Contiene el archivo cargado desde la vista.
     */
    public void cargarAscensoPadron(FileUploadEvent event) {
        try {
            this.uploadedFile = event.getFile();
            if (uploadedFile != null) {
                List<Object> list = new ArrayList<>();
                String path = Util.saveFile(uploadedFile, 0, "FormacionyDesarrolloAscensoPadron");
                FileInputStream fileInputStream = new FileInputStream(new File(path));
                recorrerExcel(fileInputStream, list);
                crearAscensoPadrones(list);
            }
            if (!b_error) {
                MovilidadUtil.addSuccessMessage("Archivo de 'Ascenso Padron' cargado correctamente");
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
                forDesAscensoPadron.setEmpleado(empleadoSelected);
                return;
            } else {
                errorEmpleado("'Código TM' no está activo");
                return;
            }
        }
        errorEmpleado("Verificar valor del campo 'Código TM'");
        return;
    }

    private void errorEmpleado(String mensaje) {
        MovilidadUtil.addErrorMessage(mensaje);
        MovilidadUtil.updateComponent("form-registrar-ascenso-padron:msgs_create_ascenso_padron");
        this.empleadoSelected = null;
        this.codigo_TM = "";
    }

    public void editar(PlaRecuAscensoPadron obj) throws Exception {
        this.forDesAscensoPadron = obj;
        codigo_TM = obj.getEmpleado().getCodigoTm().toString();
        b_editar = true;
    }

    public void editarAscensoPadron(PlaRecuAscensoPadron obj) throws Exception {
        this.forDesAscensoPadron = obj;
        b_editar = true;
    }

    public void preGestionar() {
        initForDesAscensoPadron();
        codigo_TM = "";
        b_editar = false;
    }

    public void prepDownloadLocal(String path) throws Exception {
        if ("PLANTILLA_CARGA_RECURSOS_ASCENSO_PADRON".equals(path)) {
            path = getProperty("PLANTILLA_CARGA_RECURSOS_ASCENSO_PADRON");
        }
        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
    }

    /**
     * Consultar por rango de fechas y limpiar los filtros de la tabla
     */
    public void consultar() {
        if (pau == null) {
            listAscensoPadrones = new ArrayList<>();
        } else {
//            listAscensoPadrones = ascensoPadronEJB.findAllByFechaRange(desde, hasta);
        }
        PrimeFaces.current().executeScript("PF('w_ascenso_padron').clearFilters()");
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
        if (rol_user.equals("ROLE_LIQMTTO")) {//es el usuario que puede autorizar las categoriaes de marcación en biométricos
//            b_autorizaMarcaciones = true;
        }
    }

    private boolean existeAscensoPadron(PlaRecuAscensoPadron obj) {
        return Objects.nonNull(ascensoPadronEJB.find(obj.getEmpleado().getIdEmpleado(), obj.getFechaAscensoNomina()));
    }

    private void crearAscensoPadrones(List<Object> list) {
        for (Object obj : list) {
            PlaRecuAscensoPadron obj_act = (PlaRecuAscensoPadron) obj;
            if (existeAscensoPadron(obj_act)) {
                MovilidadUtil.addAdvertenciaMessage("Operador " + obj_act.getEmpleado().getNombresApellidos() + " ya tiene un registro para la fecha ascenso nómina" + obj_act.getFechaAscensoNomina());
            } else {
                obj_act.setCreado(new Date());
                obj_act.setEstadoReg(0);
                obj_act.setUsernameCreate(user.getUsername());
                ascensoPadronEJB.create(obj_act);//persistir la activivdad
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
    private boolean validarErrorDatosAscensoPadron(PlaRecuAscensoPadron forDesAscensoPadron) {
        boolean flag = false;
        if (forDesAscensoPadron != null) {
            if (existeAscensoPadron(forDesAscensoPadron)) { //validar que los datos a insertar no existan en la base de datos
                MovilidadUtil.addAdvertenciaMessage("El colaborador " + forDesAscensoPadron.getEmpleado().getNombresApellidosCodigo() +" ya tiene registrada la fecha de ascenso seleccionada");
                b_error = flag = true;
            }
        }
        return flag;
    }

    public void crearAscensoPadron() {
        forDesAscensoPadron.setEmpleado(empleadoSelected);
        forDesAscensoPadron.setIdGopUnidadFuncional(empleadoSelected.getIdGopUnidadFuncional());
        if (!validarErrorDatosAscensoPadron(forDesAscensoPadron)) {
            forDesAscensoPadron.setCreado(new Date());
            forDesAscensoPadron.setEstadoReg(0);
            forDesAscensoPadron.setUsernameCreate(user.getUsername());
            ascensoPadronEJB.create(forDesAscensoPadron);//persistir registro
            forDesAscensoPadron = new PlaRecuAscensoPadron();
            actualizarLista();
            empleadoSelected = null;
        }
    }

    public void editarAscensoPadron() {
        //el registro viene cargado con el atributo global
        forDesAscensoPadron.setEstadoReg(0);
        forDesAscensoPadron.setModificado(new Date());//fecha de creación del registro
        forDesAscensoPadron.setUsernameEdit(user.getUsername());//usuario que modifica el registro
        ascensoPadronEJB.edit(forDesAscensoPadron);//persistir la información
        actualizarLista();
    }

    private void actualizarLista() {
        listAscensoPadrones = ascensoPadronEJB.findAll();
    }

    private void recorrerExcel(FileInputStream inputStream, List<Object> list_obj) throws Exception {
        recorrerExcelForDesAscensoPadron(inputStream, list_obj);
    }

    private void recorrerExcelForDesAscensoPadron(FileInputStream inputStream, List<Object> list_obj)
            throws IOException, Exception {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);
        int numFilas = sheet.getLastRowNum();
        boolean error = false;
        for (int a = 1; a <= numFilas; a++) {
            PlaRecuAscensoPadron ascenso_obj = new PlaRecuAscensoPadron();
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
                                    ascenso_obj.setEmpleado(emple);
                                    ascenso_obj.setIdGopUnidadFuncional(emple.getIdGopUnidadFuncional());
                                } else {
                                    agregarError((a + 1), "Código TM", "El código TM no existe en la BD",
                                            valorDecimal.longValueExact());
                                    error = true;
                                }
                                break;
                            case 1: //estado operaciones
                                ascenso_obj.setEstadoOperaciones(obtenerEstado(celda.toString().toUpperCase()));
                                ascenso_obj.setFechaOperaciones(new Date());
                                ascenso_obj.setUsernameOperaciones(user.getUsername());
                                ascenso_obj.setObservacion("Cargado desde archivo");
                                break;
                            case 2: // estado seguridad operacional
                                ascenso_obj.setEstadoSegop(obtenerEstado(celda.toString().toUpperCase()));
                                ascenso_obj.setFechaSegop(new Date());
                                ascenso_obj.setUsernameSegop(user.getUsername());
                                ascenso_obj.setObservacion("Cargado desde archivo");
                                break;
                            case 3: // estado Formación y Programacion
                                ascenso_obj.setEstadoFYD(obtenerEstado(celda.toString().toUpperCase()));
                                ascenso_obj.setFechaFYD(new Date());
                                ascenso_obj.setUsernameFYD(user.getUsername());
                                ascenso_obj.setObservacion("Cargado desde archivo");
                                break;
                            case 4: // estado Formación y Programacion
                                ascenso_obj.setEstadoProgramacion(obtenerEstadoProgramacion(celda.toString().toUpperCase()));
                                ascenso_obj.setFechaProgramacion(new Date());
                                ascenso_obj.setUsernameProgramacion(user.getUsername());
                                ascenso_obj.setObservacion("Cargado desde archivo");
                                break;
                                
                        }
                    } catch (Exception e) {
                        agregarError((a + 1), "", "Excepción en la columna " + (b + 1), "Corregir e intentar de nuevo");
                    }
                }
            }
            if (!error) {
                list_obj.add(ascenso_obj);
            }
            error = false;
        }

        wb.close();

        if (listaError.isEmpty()) {
            MovilidadUtil.addSuccessMessage("Proceso 'Carga de Archivo' Finalizado.");
        } else {
//            PrimeFaces.current().executeScript("PF('cargar_infracciones_wv').show()");
//            PrimeFaces.current().ajax().update("formErrores:erroresList");
        }
    }

    private int obtenerEstado(String estado) {
        return "APROBADO".equals(estado) ? 1 : "RECHAZADO".equals(estado) ? 2 : 0;
    }
    
    private int obtenerEstadoProgramacion(String estado) {
        return "PROGRAMADO".equals(estado) ? 1 : "PENDIENTE".equals(estado) ? 2 : 0;
    }
    
    public void filtrarPorUF() {
        listAscensoPadrones = ascensoPadronEJB.findByIdGopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
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

    public PlaRecuAscensoPadron getForDesAscensoPadron() {
        return forDesAscensoPadron;
    }

    public void setForDesAscensoPadron(PlaRecuAscensoPadron forDesAscensoPadron) {
        this.forDesAscensoPadron = forDesAscensoPadron;
    }

    public List<PlaRecuAscensoPadron> getListForDesAscensoPadron() {
        return listAscensoPadrones;
    }

    public void setListForDesAscensoPadron(List<PlaRecuAscensoPadron> listAscensoPadrones) {
        this.listAscensoPadrones = listAscensoPadrones;
    }

    public Empleado getEmpleadoSelected() {
        return empleadoSelected;
    }

    public void setEmpleadoSelected(Empleado empleadoSelected) {
        this.empleadoSelected = empleadoSelected;
    }

    public String getCodigo_TM() {
        return codigo_TM;
    }

    public void setCodigo_TM(String codigo_TM) {
        this.codigo_TM = codigo_TM;
    }

}

