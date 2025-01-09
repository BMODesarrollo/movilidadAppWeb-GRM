package com.movilidad.jsf;

import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.ejb.PlaRecuConduccionFacadeLocal;
import com.movilidad.ejb.PlaRecuTurnoFacadeLocal;
import com.movilidad.ejb.PlaRecuMedicinaFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.model.error.FileLoadError;
import com.movilidad.model.planificacion_recursos.PlaRecuConduccion;
import com.movilidad.model.planificacion_recursos.PlaRecuTurno;
import com.movilidad.model.planificacion_recursos.PlaRecuMedicina;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import static com.movilidad.utils.MovilidadUtil.getProperty;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author luis.lancheros
 */
@Named(value = "plaRecuMedicinaBean")
@ViewScoped
public class PlaRecuMedicinaBean implements Serializable{
    @EJB
    private PlaRecuMedicinaFacadeLocal medicinaLaboralEJB;
    @EJB
    private GopUnidadFuncionalFacadeLocal gopUnidadFuncionalFacadeLocal;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private PlaRecuConduccionFacadeLocal conduccionEJB;
    @EJB
    private PlaRecuTurnoFacadeLocal turnoEJB;
    @EJB
    private ConfigEmpresaFacadeLocal configEmpresaFacadeLocal;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;
    
    private List<PlaRecuMedicina> listMedicinaLaboral;
    private List<Empleado> listEmpleados;
    private List<PlaRecuConduccion> listPlaRecuConduccion;
    private List<PlaRecuTurno> listPlaRecuTurno;
    List<FileLoadError> listaError;
    private List<GopUnidadFuncional> listaGrupoFuncional;
    
    private PlaRecuMedicina medicinaLaboral;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private UploadedFile uploadedFile;
    private GopUnidadFuncional uf;
    
    private String rol_user = "";
    private boolean b_editar;
    private boolean b_error;
    private ParamAreaUsr pau;
    private String codigo_TM;
    private Empleado empleadoSelected;
    private Date desde, hasta;
    String dia_inicio;
    String dia_fin;
    
    @PostConstruct
    public void init() {
        
        listaGrupoFuncional = gopUnidadFuncionalFacadeLocal.findAll();
        medicinaLaboral = new PlaRecuMedicina();
        medicinaLaboral.setEmpleado(new Empleado());
        medicinaLaboral.setIdGopUnidadFuncional(new GopUnidadFuncional());
        medicinaLaboral.setIdPlaRecuTurno(new PlaRecuTurno());
        medicinaLaboral.setIdPlaRecuConduccion(new PlaRecuConduccion());
        restringirCalendario();
        
        uploadedFile = null;
        b_editar = false;
        b_error = false;
        listaError = new ArrayList<>();
        
        
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
        listEmpleados = empleadoEjb.findAllEmpleadosActivos(0);
        listPlaRecuConduccion = conduccionEJB.findAll();
        listPlaRecuTurno = turnoEJB.findAll();
        
        uf = new GopUnidadFuncional();
        uf.setIdGopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        listMedicinaLaboral = medicinaLaboralEJB.findAll();
    }
    
    public void preGestionar() {
        codigo_TM = "";
        b_editar = false;
        medicinaLaboral = new PlaRecuMedicina();
        medicinaLaboral.setEmpleado(new Empleado());
        medicinaLaboral.setIdGopUnidadFuncional(new GopUnidadFuncional());
        medicinaLaboral.setIdPlaRecuTurno(new PlaRecuTurno());
        medicinaLaboral.setIdPlaRecuConduccion(new PlaRecuConduccion());
    }
    
    public void consultar() {
        listMedicinaLaboral = medicinaLaboralEJB.findAllByFechaRange(desde, hasta);
        PrimeFaces.current().executeScript("PF('w_Medicina').clearFilters()");
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
    
    public void cargarEmpleado() {
        
        codigo_TM = medicinaLaboral.getEmpleado().getIdentificacion();
        empleadoSelected = empleadoEjb.findByIdentificacion(codigo_TM);
        if (empleadoSelected != null) {
            medicinaLaboral.setEmpleado(empleadoSelected);
            return;
        }
        this.empleadoSelected = null;
        this.codigo_TM = "";
        MovilidadUtil.addErrorMessage("'Num Identificacion' no valido");
        MovilidadUtil.updateComponent("form-registrar-medicina:msgs_create_medicina");
    }
    
    public void cargarDatos() {
        uf.setIdGopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        listMedicinaLaboral = medicinaLaboralEJB.findAllByGropUnidadFun(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        medicinaLaboral = new PlaRecuMedicina();
        medicinaLaboral.setEmpleado(new Empleado());
        medicinaLaboral.setIdPlaRecuTurno(new PlaRecuTurno());
        medicinaLaboral.setIdPlaRecuConduccion(new PlaRecuConduccion());
        medicinaLaboral.setIdGopUnidadFuncional(new GopUnidadFuncional());
    }
    
    public void prepDownloadLocal(String path) throws Exception {
        if ("PLANTILLA_CARGA_RECURSOS_MEDICINA".equals(path)) {
            path = getProperty("PLANTILLA_CARGA_RECURSOS_MEDICINA");
        }
        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
    }
    
    public void cargarMedicina(FileUploadEvent event) {
        try {
            this.uploadedFile = event.getFile();
            if (uploadedFile != null) {
                List<Object> list = new ArrayList<>();
                String path = Util.saveFile(uploadedFile, 0, "planificacionRecursosMedicina");
                FileInputStream fileInputStream = new FileInputStream(new File(path));
                recorrerExcel(fileInputStream, list);
                crearMedicinasLaborales(list);
            }
            if (!b_error) {
                MovilidadUtil.addSuccessMessage("Archivo de 'Medicina Laboral' cargado correctamente");
            }else{
                String errorMessages = listaError.stream()
                .map(error -> "\n Línea: " + error.getRow() + ", Valor: " + error.getValue()+ ", Mensaje: " + error.getMessage())
                .collect(Collectors.joining("\n"));
                MovilidadUtil.addFatalMessage("Archivo de 'Medicina Laboral' Se encontraron los siguientes errores : <br/>"+ errorMessages);
            }
        } catch (Exception ex) {
            System.out.println("Error en la carga de archivo " + ex.getMessage());
        } finally {
            uploadedFile = null;
        }
    }
    
    public String getVigencia(Date fechaFin) {
        Date fechaActual = new Date();
        return (fechaFin != null && fechaFin.before(fechaActual)) ? "VENCIDO" : "VIGENTE";
    }
        
    public void editarMedicina(PlaRecuMedicina medicina) throws Exception {
        this.medicinaLaboral = medicina;
        b_editar = true;
    }
        
    private boolean existeMedicina(PlaRecuMedicina medicina) {
        return Objects.nonNull(medicinaLaboralEJB.findMedicina(medicina.getFechaInicio(), 
                medicina.getFechaFin(),medicina.getEmpleado().getIdEmpleado()));
    }
    
    private boolean validarErrorDatosMedicina(PlaRecuMedicina plaRecuMedicina) {
        boolean flag = false;
        if (plaRecuMedicina != null) {
            if (existeMedicina(plaRecuMedicina)) { //validar que los datos a insertar no existan en la base de datos
                MovilidadUtil.addAdvertenciaMessage("El registro 'Seguridad' ya existe.");
                b_error = flag = true;
            } else {
                if (plaRecuMedicina.getFechaInicio().after(plaRecuMedicina.getFechaFin())) {
                    MovilidadUtil.addAdvertenciaMessage("'Fecha Fin' no puede ser menor que 'Fecha Inicio'.");
                    b_error = flag = true;
                }
                if(plaRecuMedicina.getIdPlaRecuConduccion()== null || plaRecuMedicina.getIdPlaRecuConduccion().getIdPlaRecuConduccion() == 0) {
                    MovilidadUtil.addAdvertenciaMessage("Debe seleccionar un tipo de 'Conduccion'.");
                    b_error = flag = true;
                }
            }
        }
        return flag;
    }
    
    private void crearMedicinasLaborales(List<Object> list) {
        for (Object obj : list) {
            PlaRecuMedicina obj_act = (PlaRecuMedicina)obj;
            if (existeMedicina(obj_act)) {
                MovilidadUtil.addAdvertenciaMessage("El registro ya existe.");
            } else {
                obj_act.setCreado(new Date());
                obj_act.setIdGopUnidadFuncional(obj_act.getEmpleado().getIdGopUnidadFuncional());
                obj_act.setEstadoReg(0);
                obj_act.setUsernameCreate(user.getUsername());
               medicinaLaboralEJB.create(obj_act);//persistir la activivdad
            }
        }
        MovilidadUtil.addSuccessMessage("Registro Creado");
        cargarDatos();
    }
    
    public void crearMedicinaLaboral() {
        if (!validarErrorDatosMedicina(medicinaLaboral)) {
                medicinaLaboral.setCreado(new Date());
                medicinaLaboral.setEstadoReg(0);
                medicinaLaboral.setUsernameCreate(user.getUsername());
                medicinaLaboral.setIdGopUnidadFuncional(medicinaLaboral.getEmpleado().getIdGopUnidadFuncional());
                medicinaLaboralEJB.create(medicinaLaboral);//persistir la activivdad
                MovilidadUtil.addSuccessMessage("Registro Creado");
                PrimeFaces.current().executeScript("PF('wvPlaRecuMedicina').hide();");
                cargarDatos();
        }
        
    }
    
    public void editarMedicinaLaboral(PlaRecuMedicina medicina) {
        medicina.setEstadoReg(0);
        medicina.setModificado(new Date());//fecha de creación del registro
        medicina.setUsernameEdit(user.getUsername());//usuario que modifica el registro
        medicinaLaboralEJB.edit(medicina);//persistir la infracción
    }
    
    public void prueba(){
        System.out.println("ENTRA");
    }
    
    public void editarMedicinaLaboral(){
        //la actividadAleatoria viene cargada en el atributo global
        medicinaLaboral.setEstadoReg(0);
        medicinaLaboral.setModificado(new Date());//fecha de creación del registro
        medicinaLaboral.setUsernameEdit(user.getUsername());//usuario que modifica el registro
        Empleado emp = empleadoEjb.find(medicinaLaboral.getEmpleado().getIdEmpleado());
        medicinaLaboral.setEmpleado(emp);
        PlaRecuConduccion con = conduccionEJB.find(medicinaLaboral.getIdPlaRecuConduccion().getIdPlaRecuConduccion());
        medicinaLaboral.setIdPlaRecuConduccion(con);
        PlaRecuTurno tur = turnoEJB.find(medicinaLaboral.getIdPlaRecuTurno().getIdPlaRecuTurno());
        medicinaLaboral.setIdPlaRecuTurno(tur);
        medicinaLaboralEJB.edit(medicinaLaboral);//persistir la infracción
        MovilidadUtil.addSuccessMessage("Registro Actualizado");
        PrimeFaces.current().executeScript("PF('wvPlaRecuMedicina').hide();");
        cargarDatos();
    }
    
    private void recorrerExcel(FileInputStream inputStream, List<Object> list_registros) throws Exception {
        recorrerExcelML(inputStream, list_registros);
    }
    
    private void recorrerExcelML(FileInputStream inputStream, List<Object> list_medicina)
            throws IOException, Exception {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);
        int numFilas = sheet.getLastRowNum();
        boolean error = false;
        for (int a = 1; a <= numFilas; a++) {
            PlaRecuMedicina actividad_obj = new PlaRecuMedicina();
            Row fila = sheet.getRow(a);
            int numCols = fila.getLastCellNum();
            for (int b = 0; b < numCols; b++) {
                Cell celda = fila.getCell(b);
                if (celda != null) {
                    Date parse;
                    try {
                        switch (b) {
                            case 0:// Codigo TM
                                //BigDecimal valorDecimal = new BigDecimal(celda.toString());
                                //como Bigdecimal nunca es null se evalua directamente
                                long identificacion = Math.round(Double.parseDouble(celda.toString()));
                                Empleado emple = empleadoEjb.findByIdentificacion(String.valueOf(identificacion));
                                if (emple != null) {
                                    actividad_obj.setEmpleado(emple);
                                } else {
                                    agregarError((a + 1), "Identificacion", "El Numero de identificacion no existe en la BD",celda.toString());
                                    error = true;
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
                                    actividad_obj.setFechaInicio(parse);
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
                            case 3: // conduccion
                                PlaRecuConduccion val_conduccion = conduccionEJB.findByDescripcion(celda.getStringCellValue());
                                if (val_conduccion != null){
                                   actividad_obj.setIdPlaRecuConduccion(val_conduccion); 
                                }
                                else {
                                    agregarError((a + 1), "Conduccion", "El nombre del tipo conduccion no existe en la BD", celda.toString());
                                    error = true;
                                }
                                break;    
                            case 4: // turno
                                PlaRecuTurno val_Turno = turnoEJB.findByName(celda.getStringCellValue());
                                if (val_Turno != null){
                                   actividad_obj.setIdPlaRecuTurno(val_Turno); 
                                }
                                else {
                                    agregarError((a + 1), "Turno", "El nombre del tipo turno no existe en la BD", celda.toString());
                                    error = true;
                                }
                                break;
                            case 5: // observacion
                                actividad_obj.setObservaciones(celda.getStringCellValue());
                                break;
                        }
                    } catch (Exception e) {
                        agregarError((a + 1), "", "Excepción en la columna " + (b + 1), "Corregir e intentar de nuevo");
                    }
                }
            }
            if (!error) {
                list_medicina.add(actividad_obj);
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
    
    private void agregarError(int fila, String columna, String error, Object value) {
        listaError.add(new FileLoadError(fila, columna, error, value));
    }
    
    public String obtenerRestriccion(int idEmpleado) {
        return medicinaLaboralEJB.findRestrictionByIdEmployee(idEmpleado);
    }
    
    public PlaRecuMedicina getMedicinaLaboral() {
        return medicinaLaboral;
    }

    public void setMedicinaLaboral(PlaRecuMedicina medicinaLaboral) {
        this.medicinaLaboral = medicinaLaboral;
    }

    public PlaRecuMedicinaFacadeLocal getMedicinaLaboralEJB() {
        return medicinaLaboralEJB;
    }

    public void setMedicinaLaboralEJB(PlaRecuMedicinaFacadeLocal medicinaLaboralEJB) {
        this.medicinaLaboralEJB = medicinaLaboralEJB;
    }

    public GopUnidadFuncionalFacadeLocal getGopUnidadFuncionalFacadeLocal() {
        return gopUnidadFuncionalFacadeLocal;
    }

    public void setGopUnidadFuncionalFacadeLocal(GopUnidadFuncionalFacadeLocal gopUnidadFuncionalFacadeLocal) {
        this.gopUnidadFuncionalFacadeLocal = gopUnidadFuncionalFacadeLocal;
    }

    public EmpleadoFacadeLocal getEmpleadoEjb() {
        return empleadoEjb;
    }

    public void setEmpleadoEjb(EmpleadoFacadeLocal empleadoEjb) {
        this.empleadoEjb = empleadoEjb;
    }

    public PlaRecuConduccionFacadeLocal getConduccionEJB() {
        return conduccionEJB;
    }

    public void setConduccionEJB(PlaRecuConduccionFacadeLocal conduccionEJB) {
        this.conduccionEJB = conduccionEJB;
    }

    public PlaRecuTurnoFacadeLocal getTurnoEJB() {
        return turnoEJB;
    }

    public void setTurnoEJB(PlaRecuTurnoFacadeLocal turnoEJB) {
        this.turnoEJB = turnoEJB;
    }

    public GopUnidadFuncionalSessionBean getUnidadFuncionalSessionBean() {
        return unidadFuncionalSessionBean;
    }

    public void setUnidadFuncionalSessionBean(GopUnidadFuncionalSessionBean unidadFuncionalSessionBean) {
        this.unidadFuncionalSessionBean = unidadFuncionalSessionBean;
    }

    public List<PlaRecuMedicina> getListMedicinaLaboral() {
        return listMedicinaLaboral;
    }

    public void setListMedicinaLaboral(List<PlaRecuMedicina> listMedicinaLaboral) {
        this.listMedicinaLaboral = listMedicinaLaboral;
    }

    public List<Empleado> getListEmpleados() {
        return listEmpleados;
    }

    public void setListEmpleados(List<Empleado> listEmpleados) {
        this.listEmpleados = listEmpleados;
    }

    public List<PlaRecuConduccion> getListPlaRecuConduccion() {
        return listPlaRecuConduccion;
    }

    public void setListPlaRecuConduccion(List<PlaRecuConduccion> listPlaRecuConduccion) {
        this.listPlaRecuConduccion = listPlaRecuConduccion;
    }

    public List<PlaRecuTurno> getListPlaRecuTurno() {
        return listPlaRecuTurno;
    }

    public void setListPlaRecuTurno(List<PlaRecuTurno> listPlaRecuTurno) {
        this.listPlaRecuTurno = listPlaRecuTurno;
    }

    public List<GopUnidadFuncional> getListaGrupoFuncional() {
        return listaGrupoFuncional;
    }

    public void setListaGrupoFuncional(List<GopUnidadFuncional> listaGrupoFuncional) {
        this.listaGrupoFuncional = listaGrupoFuncional;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public GopUnidadFuncional getUf() {
        return uf;
    }

    public void setUf(GopUnidadFuncional uf) {
        this.uf = uf;
    }

    public boolean isB_editar() {
        return b_editar;
    }

    public void setB_editar(boolean b_editar) {
        this.b_editar = b_editar;
    }

    public boolean isB_error() {
        return b_error;
    }

    public void setB_error(boolean b_error) {
        this.b_error = b_error;
    }

    public ParamAreaUsr getPau() {
        return pau;
    }

    public void setPau(ParamAreaUsr pau) {
        this.pau = pau;
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
