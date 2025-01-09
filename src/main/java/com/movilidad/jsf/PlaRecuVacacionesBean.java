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
import com.movilidad.ejb.PlaRecuVacacionesFacadeLocal;
import com.movilidad.ejb.PlaRecuGrupoVacacionesFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.planificacion_recursos.PlaRecuGrupoVacaciones;
import com.movilidad.model.planificacion_recursos.PlaRecuVacaciones;
import static com.movilidad.utils.MovilidadUtil.getProperty;
import java.math.BigDecimal;
import java.util.Objects;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 * Clase control que permite dar gestión a los métodos de la tabla
 * planificacion_recursos_vacaciones
 *
 * @author Omar.beltran
 */
@Named(value = "plaRecuVacacionesBean")
@ViewScoped
public class PlaRecuVacacionesBean implements Serializable {

    @EJB
    private PlaRecuVacacionesFacadeLocal vacacionesEJB;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;
    @EJB
    private PlaRecuGrupoVacacionesFacadeLocal grupoVacacionesEjb;
    @EJB
    private ConfigEmpresaFacadeLocal configEmpresaFacadeLocal;
    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private GopUnidadFuncionalBean unidadFuncionalBean;
    //colecciones
    private List<PlaRecuVacaciones> listPlaRecuVacaciones;
    private List<Empleado> listEmpleados;
    private List<PlaRecuGrupoVacaciones> listGrupoVacaciones;
    List<FileLoadError> listaError;

    //atributos
    private PlaRecuVacaciones plaRecuVacaciones;
    private PlaRecuVacaciones plaRecuVacacionesBeforeEdit;
    private Empleado empleadoSelected;
    private PlaRecuGrupoVacaciones grupoVacacionesSelected;
    private int id_lugar_sel;
    private String codigo_TM;
    private String grupoVacacionesSel;
    private UploadedFile uploadedFile;
    private Date desde, hasta;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private ParamAreaUsr pau;
    private String rol_user = "";
    private boolean b_editar;
    private boolean b_error;
    private Integer grupoVacacionesSelectedId;
    String dia_inicio;
    String dia_fin;

    @PostConstruct
    public void init() {
        initPlaRecuVacaciones();
        initPlaRecuVacacionesSelected();
        restringirCalendario();
        grupoVacacionesSelected = new PlaRecuGrupoVacaciones();
        uploadedFile = null;
        b_editar = false;
        b_error = false;
        listaError = new ArrayList<>();
        listPlaRecuVacaciones = vacacionesEJB.findAll();
        listGrupoVacaciones = grupoVacacionesEjb.findAll();
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
        listEmpleados = empleadoEjb.findAllEmpleadosActivos(0); //cargar empleados activos
        validarRol();
    }

    private void initPlaRecuVacaciones() {
        plaRecuVacaciones = new PlaRecuVacaciones();
        plaRecuVacaciones.setIdGrupoVacaciones(new PlaRecuGrupoVacaciones());
        plaRecuVacaciones.setEmpleado(new Empleado());
    }

    private void initPlaRecuVacacionesSelected() {
        plaRecuVacaciones = new PlaRecuVacaciones();
        plaRecuVacaciones.setIdGrupoVacaciones(new PlaRecuGrupoVacaciones());
        plaRecuVacaciones.setEmpleado(new Empleado());
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

    public void grupoVacacionesSeleccionada(SelectEvent event) {
        if (event != null) {
            grupoVacacionesSelected = (PlaRecuGrupoVacaciones) event.getObject();
        }
    }

    /**
     * Método resposable de cargar el archivo Excel seleccionado en la vista
     *
     * @param event Contiene el archivo cargado desde la vista.
     */
    public void cargarVacaciones(FileUploadEvent event) {
        try {
            this.uploadedFile = event.getFile();
            if (uploadedFile != null) {
                List<Object> list = new ArrayList<>();
                String path = Util.saveFile(uploadedFile, 0, "planificacionRecursosVacaciones");
                FileInputStream fileInputStream = new FileInputStream(new File(path));
                recorrerExcel(fileInputStream, list);
                crearVacaciones(list);
            }
            if (!b_error) {
                MovilidadUtil.addSuccessMessage("Archivo de 'Vacaciones' cargado correctamente");
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
                plaRecuVacaciones.setEmpleado(empleadoSelected);
                return;
            }
        }
        this.empleadoSelected = null;
        this.codigo_TM = "";
        MovilidadUtil.addErrorMessage("'Código TM' no valido");
        MovilidadUtil.updateComponent("form-registrar-vacaciones:msgs_create_vacaciones");
    }

    public void editar(PlaRecuVacaciones obj) throws Exception {
        this.plaRecuVacaciones = obj;
        //se debe generar nueva instancia y asignar valores irrepetibles 
        plaRecuVacacionesBeforeEdit = new PlaRecuVacaciones();
        plaRecuVacacionesBeforeEdit.setEmpleado(obj.getEmpleado());
        grupoVacacionesSelectedId = obj.getIdGrupoVacaciones().getIdPlaRecuGrupoVacaciones();
        codigo_TM = obj.getEmpleado().getCodigoTm().toString();
        b_editar = true;
    }

    public void editarVacaciones(PlaRecuVacaciones obj) throws Exception {
        this.plaRecuVacaciones = obj;
        b_editar = true;
    }

    public void editar(Event event) throws Exception {
        System.out.println("test");
    }

    public void preGestionar() {
        initPlaRecuVacaciones();
        codigo_TM = "";
        grupoVacacionesSelectedId = 0;
        b_editar = false;
    }

    public void prepDownloadLocal(String path) throws Exception {
        if ("PLANTILLA_CARGA_RECURSOS_VACACIONES".equals(path)) {
            path = getProperty("PLANTILLA_CARGA_RECURSOS_VACACIONES");
        }
        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
    }

    /**
     * Consultar jorndas por rango de fechas y limpiar los filtros de la tabla
     */
    public void consultar() {
        if (pau == null) {
            listPlaRecuVacaciones = new ArrayList<>();
        } else {
            listPlaRecuVacaciones = vacacionesEJB.findAllByFechaRange(desde, hasta);
        }
        PrimeFaces.current().executeScript("PF('w_Vacaciones').clearFilters()");
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
        if (rol_user.equals("ROLE_LIQMTTO")) {//es el usuario que puede autorizar las grupoVacacioneses de marcación en biométricos
//            b_autorizaMarcaciones = true;
        }
    }

    private boolean existeVacaciones(PlaRecuVacaciones obj) {
        return Objects.nonNull(vacacionesEJB.findByEmpleado(obj.getEmpleado().getIdEmpleado()));
    }

    private void crearVacaciones(List<Object> list) {
        for (Object obj : list) {
            PlaRecuVacaciones obj_eje = (PlaRecuVacaciones) obj;
            if (!validarErrorDatosVacaciones(obj_eje)) {
                obj_eje.setCreado(new Date());
                obj_eje.setEstadoReg(0);
                obj_eje.setUsernameCreate(user.getUsername());
                vacacionesEJB.create(obj_eje);//persistir la activivdad
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
    private boolean validarErrorDatosVacaciones(PlaRecuVacaciones before, PlaRecuVacaciones current) {

        boolean flag = false;
        if (current != null) {

            if (validarCambios(before, current)) {
                if (existeVacaciones(current)) { //validar que los datos a insertar no existan en la base de datos
                    MovilidadUtil.addAdvertenciaMessage("El operador con código TM " + current.getEmpleado().getCodigoTm() + " ya tiene Vacaciones cargada para las fechas ingresadas.");
                    b_error = flag = true;
                } else {
                    if (current.getIdGrupoVacaciones() == null || current.getIdGrupoVacaciones().getIdPlaRecuGrupoVacaciones() == 0) {
                        MovilidadUtil.addAdvertenciaMessage("Debe seleccionar un 'Grupo Vacaciones'.");
                        b_error = flag = true;
                    }
                    if (current.getPasivoVacacional() == null) {
                        current.setPasivoVacacional(0);//si viene nulo se deja cero
                    }
                }
            }
        }
        return flag;
    }

    /**
     * Permite validar la integridad de la información contenida en el objeto
     * global. Se invoca al momento de persistir información ingresada de forma
     * manual. de la clase Planificación Recursos Vacaciones
     *
     * @return true si la información contenida tiene error false en cualquier
     * otro caso.
     */
    private boolean validarErrorDatosVacaciones(PlaRecuVacaciones current) {
        boolean flag = false;
        if (current != null) {
            if (existeVacaciones(current)) { //validar que los datos a insertar no existan en la base de datos
                MovilidadUtil.addAdvertenciaMessage("El operador con código TM " + current.getEmpleado().getCodigoTm() + " ya tiene Vacaciones cargada");
                b_error = flag = true;
            } else {
                if (current.getIdGrupoVacaciones() == null || current.getIdGrupoVacaciones().getIdPlaRecuGrupoVacaciones() == 0) {
                    MovilidadUtil.addAdvertenciaMessage("Debe seleccionar un 'Grupo Vacaciones'.");
                    b_error = flag = true;
                }
                if (current.getPasivoVacacional() == null) {
                    current.setPasivoVacacional(0);//si viene nulo se deja cero
                }
            }
        }
        return flag;
    }

    public void crearVacaciones() {
        grupoVacacionesSelected = grupoVacacionesEjb.find(grupoVacacionesSelectedId);
        plaRecuVacaciones.setEmpleado(empleadoSelected);
        plaRecuVacaciones.setIdGrupoVacaciones(grupoVacacionesSelected);
        if (!validarErrorDatosVacaciones(plaRecuVacaciones)) {
            plaRecuVacaciones.setIdGopUnidadFuncional(empleadoSelected.getIdGopUnidadFuncional());
            plaRecuVacaciones.setCreado(new Date());
            plaRecuVacaciones.setEstadoReg(0);
            plaRecuVacaciones.setUsernameCreate(user.getUsername());
            vacacionesEJB.create(plaRecuVacaciones);//persistir la activivdad
            plaRecuVacaciones = new PlaRecuVacaciones();
            actualizarLista();
            empleadoSelected = null;
            grupoVacacionesSelected = null;
            MovilidadUtil.addSuccessMessage("Registro 'Vacaciones' creado con éxito.");
            PrimeFaces.current().executeScript("PF('wvPlaRecuVacaciones').hide();");
        }
    }

    public void editarVacaciones() {
        //el registro viene cargado con el atributo global
        if (!validarErrorDatosVacaciones(plaRecuVacacionesBeforeEdit, plaRecuVacaciones)) {
            plaRecuVacaciones.setIdGrupoVacaciones(grupoVacacionesEjb.find(grupoVacacionesSelectedId));
            plaRecuVacaciones.setEstadoReg(0);
            plaRecuVacaciones.setModificado(new Date());//fecha de creación del registro
            plaRecuVacaciones.setUsernameEdit(user.getUsername());//usuario que modifica el registro
            vacacionesEJB.edit(plaRecuVacaciones);//persistir la infracción
            actualizarLista();
            plaRecuVacacionesBeforeEdit = null;
            MovilidadUtil.addSuccessMessage("Registro 'Vacaciones' modificado con éxito.");
            PrimeFaces.current().executeScript("PF('wvPlaRecuVacaciones').hide();");
        }
    }

    /**
     * Permite identificar si los cambios que se realizan en el registro se
     * deben validar para evitar duplicidad de registros. La validación se debe
     * realizar cuando se ha modificado el empleado, la fecha inicio o la fecha
     * fin.
     */
    private boolean validarCambios(PlaRecuVacaciones before, PlaRecuVacaciones current) {
        return true;
//        return !(before.getFechaInicio().equals(current.getFechaInicio())
//                && before.getFechaFin().equals(current.getFechaFin())
//                && before.getEmpleado().getIdEmpleado().equals(current.getEmpleado().getIdEmpleado()));

    }

    private void actualizarLista() {
        listPlaRecuVacaciones = vacacionesEJB.findAll();
    }

    private void recorrerExcel(FileInputStream inputStream, List<Object> list_obj) throws Exception {
        recorrerExcelPlaRecuVacaciones(inputStream, list_obj);
    }

    private void recorrerExcelPlaRecuVacaciones(FileInputStream inputStream, List<Object> list_obj)
            throws IOException, Exception {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);
        int numFilas = sheet.getLastRowNum();
        boolean error = false;
        GopUnidadFuncional uf = null;
        for (int a = 1; a <= numFilas; a++) {
            PlaRecuVacaciones vacaciones_obj = new PlaRecuVacaciones();
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
                                    vacaciones_obj.setEmpleado(emple);
                                } else {
                                    agregarError((a + 1), "Código TM", "El código TM no existe o no está activo en la BD",
                                            valorDecimal.longValueExact());
                                    error = true;
                                }
                                break;
                            case 1: //Unidad Funcional
                                uf = unidadFuncionalBean.findUnidadFuncionalByCodigo(celda.toString());

                                if (uf != null) {
                                    if (vacaciones_obj.getEmpleado() != null) {
                                        if (Objects.equals(vacaciones_obj.getEmpleado().getIdGopUnidadFuncional().getIdGopUnidadFuncional(), uf.getIdGopUnidadFuncional())) {
                                            grupoVacacionesSelected.setIdGopUnidadFuncional(uf); // el grupo contiene la UF
                                        } else {
                                            agregarError((a + 1), "Unidad Funcional", "La unidad Funcional no corresponde al código del operador", celda.toString());
                                            error = true;
                                        }
                                    }
                                } else {
                                    agregarError((a + 1), "Unidad Funcional", "El nombre de la unidad funcional no existe en la BD", celda.toString());
                                    error = true;
                                }
                                break;
                            case 2: // Grupo Vacaciones
                                if (uf != null) {
                                    grupoVacacionesSelected = grupoVacacionesEjb.findByName(celda.toString().toUpperCase(), uf.getIdGopUnidadFuncional());
                                    if (grupoVacacionesSelected != null) {
                                        vacaciones_obj.setIdGrupoVacaciones(grupoVacacionesSelected);
                                    } else {
                                        agregarError((a + 1), "Grupo Vacaciones", "El nombre del grupo Vacaciones no existe en la BD", celda.toString());
                                        error = true;
                                    }
                                }
                                break;
                            case 3: // pasivo vacacional
                                try {
                                valorDecimal = new BigDecimal(celda.toString());
                                //como Bigdecimal nunca es null se evalua directamente
                                vacaciones_obj.setPasivoVacacional(valorDecimal.intValue());
                            } catch (Exception ex) {
                                agregarError((a + 1), "Pasivo Vacacional", "El valor no es válido", celda.toString());
                                error = true;
                            }
                            break;
                            case 4: //Observaciones
                                vacaciones_obj.setObservaciones(celda.toString());
                                break;
                        }
                    } catch (Exception e) {
                        agregarError((a + 1), "", "Excepción en la columna " + (b + 1), "Corregir e intentar de nuevo");
                    }
                }
            }
            if (!error) {
                list_obj.add(vacaciones_obj);
            }
            error = false;
        }

        wb.close();

        if (listaError.isEmpty()) {
            MovilidadUtil.addSuccessMessage("Proceso 'Carga de Archivo Vacaciones' Finalizado.");
        } else {
            b_error = true;
            PrimeFaces.current().executeScript("PF('cargar_vacaciones_wv').show()");
            PrimeFaces.current().ajax().update("formErrores:erroresList");
        }
    }

    public void filtrarPorUF() {
        listPlaRecuVacaciones = vacacionesEJB.findByIdGopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
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

    public PlaRecuVacaciones getPlaRecuVacaciones() {
        return plaRecuVacaciones;
    }

    public void setPlaRecuVacaciones(PlaRecuVacaciones plaRecuVacaciones) {
        this.plaRecuVacaciones = plaRecuVacaciones;
    }

    public List<PlaRecuVacaciones> getListPlaRecuVacaciones() {
        return listPlaRecuVacaciones;
    }

    public void setListPlaRecuVacaciones(List<PlaRecuVacaciones> listPlaRecuVacaciones) {
        this.listPlaRecuVacaciones = listPlaRecuVacaciones;
    }

    public Empleado getEmpleadoSelected() {
        return empleadoSelected;
    }

    public void setEmpleadoSelected(Empleado empleadoSelected) {
        this.empleadoSelected = empleadoSelected;
    }

    public PlaRecuGrupoVacaciones getGrupoVacacionesSelected() {
        return grupoVacacionesSelected;
    }

    public void setGrupoVacacionesSelected(PlaRecuGrupoVacaciones grupoVacacionesSelected) {
        this.grupoVacacionesSelected = grupoVacacionesSelected;
    }

    public String getCodigo_TM() {
        return codigo_TM;
    }

    public void setCodigo_TM(String codigo_TM) {
        this.codigo_TM = codigo_TM;
    }

    public List<PlaRecuGrupoVacaciones> getListGrupoVacacioneses() {
        return listGrupoVacaciones;
    }

    public void setListGrupoVacacioneses(List<PlaRecuGrupoVacaciones> listGrupoVacacioneses) {
        this.listGrupoVacaciones = listGrupoVacacioneses;
    }

    public String getGrupoVacacionesSel() {
        return grupoVacacionesSel;
    }

    public void setGrupoVacacionesSel(String grupoVacacionesSel) {
        this.grupoVacacionesSel = grupoVacacionesSel;
    }

    public Integer getGrupoVacacionesSelectedId() {
        return grupoVacacionesSelectedId;
    }

    public void setGrupoVacacionesSelectedId(Integer GrupoVacacionesSelectedId) {
        this.grupoVacacionesSelectedId = GrupoVacacionesSelectedId;
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
