package com.movilidad.jsf;

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
import com.movilidad.ejb.PlaRecuCategoriaFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.planificacion_recursos.PlaRecuEntregaOperador;
import com.movilidad.model.planificacion_recursos.PlaRecuCategoria;
import static com.movilidad.utils.MovilidadUtil.getProperty;
import java.math.BigDecimal;
import java.util.Objects;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import com.movilidad.ejb.PlaRecuEntregaOperadorFacadeLocal;

/**
 * Clase control que permite dar gestión a los métodos de la tabla
 * planificacion_recursos_ejecucion
 *
 * @author Omar.beltran
 */
@Named(value = "plaRecuEntregaOperadorBean")
@ViewScoped
public class PlaRecuEntregaOperadorBean implements Serializable {

    @EJB
    private PlaRecuEntregaOperadorFacadeLocal entregaOperadorEJB;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;
    @EJB
    private PlaRecuCategoriaFacadeLocal categoriaEjb;
    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    //colecciones
    private List<PlaRecuEntregaOperador> listEntregaOperadores;
    private List<Empleado> listEmpleados;
    private List<PlaRecuCategoria> listCategoria;
    List<FileLoadError> listaError;

    //atributos
    private PlaRecuEntregaOperador forDesEntregaOperador;
    private Empleado empleadoSelected;
    private PlaRecuCategoria categoriaSelected;
    private int id_lugar_sel;
    private String codigo_TM;
    private String categoriaSel;
    private UploadedFile uploadedFile;
    private Date desde, hasta;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private ParamAreaUsr pau;
    private String rol_user = "";
    private boolean b_editar;
    private boolean b_error;
    private Integer categoriaSelectedId;

    @PostConstruct
    public void init() {
        initForDesEntregaOperador();
        initForDesEntregaOperadorSelected();
        categoriaSelected = new PlaRecuCategoria();
        uploadedFile = null;
        b_editar = false;
        b_error = false;
        listaError = new ArrayList<>();
        listEntregaOperadores = entregaOperadorEJB.findAll();
        listCategoria = categoriaEjb.findAll();
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
        listEmpleados = empleadoEjb.findAllEmpleadosActivos(0); //cargar empleados activos
        validarRol();
    }

    private void initForDesEntregaOperador() {
        forDesEntregaOperador = new PlaRecuEntregaOperador();
        forDesEntregaOperador.setIdPlaRecuCategoria(new PlaRecuCategoria());
        forDesEntregaOperador.setEmpleado(new Empleado());
    }

    private void initForDesEntregaOperadorSelected() {
        forDesEntregaOperador = new PlaRecuEntregaOperador();
        forDesEntregaOperador.setIdPlaRecuCategoria(new PlaRecuCategoria());
        forDesEntregaOperador.setEmpleado(new Empleado());
    }

    public void categoriaSeleccionada(SelectEvent event) {
        if (event != null) {
            categoriaSelected = (PlaRecuCategoria) event.getObject();
        }
    }

    /**
     * Método resposable de cargar el archivo Excel seleccionado en la vista
     *
     * @param event Contiene el archivo cargado desde la vista.
     */
    public void cargarEntregaOperador(FileUploadEvent event) {
        try {
            this.uploadedFile = event.getFile();
            if (uploadedFile != null) {
                List<Object> list = new ArrayList<>();
                String path = Util.saveFile(uploadedFile, 0, "planificacionRecursosEntregaOperadores");
                FileInputStream fileInputStream = new FileInputStream(new File(path));
                recorrerExcel(fileInputStream, list);
                crearEntregaOperadores(list);
            }
            if (!b_error) {
                MovilidadUtil.addSuccessMessage("Archivo 'Entrega Operador' cargado correctamente");
            }
        } catch (Exception ex) {
            System.out.println("Error en la carga de archivo 'Entrega Operador'" + ex.getMessage());
        } finally {
            uploadedFile = null;
        }
    }

    public void cargarEmpleado() {
        if (!(codigo_TM.equals("") && codigo_TM.isEmpty())) {
            empleadoSelected = empleadoEjb.getEmpleadoActivoCodigoTM(Integer.parseInt(codigo_TM));
            if (empleadoSelected != null) {
                forDesEntregaOperador.setEmpleado(empleadoSelected);
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
        MovilidadUtil.updateComponent("form-registrar-entrega-operador:msgs_create_entrega_operador");
        this.empleadoSelected = null;
        this.codigo_TM = "";
    }

    public void editar(PlaRecuEntregaOperador obj) throws Exception {
        this.forDesEntregaOperador = obj;
        categoriaSelectedId = obj.getIdPlaRecuCategoria().getIdPlaRecuCategoria();
        forDesEntregaOperador.setIdPlaRecuCategoria(new PlaRecuCategoria());
        codigo_TM = obj.getEmpleado().getCodigoTm().toString();
        b_editar = true;
    }

    public void editarEntregaOperador(PlaRecuEntregaOperador obj) throws Exception {
        this.forDesEntregaOperador = obj;
        b_editar = true;
    }

    public void editar(Event event) throws Exception {
        System.out.println("test");
    }

    public void preGestionar() {
        initForDesEntregaOperador();
        codigo_TM = "";
        categoriaSelectedId = 0;
        b_editar = false;
    }

    public void prepDownloadLocal(String path) throws Exception {
        if ("PLANTILLA_CARGA_RECURSOS_ENTREGA_OPERADOR".equals(path)) {
            path = getProperty("PLANTILLA_CARGA_RECURSOS_ENTREGA_OPERADOR");
        }
        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
    }

    /**
     * Consultar por rango de fechas y limpiar los filtros de la tabla
     */
    public void consultar() {
        if (pau == null) {
            listEntregaOperadores = new ArrayList<>();
        } else {
//            listEntregaOperadores = entregaOperadorEJB.findAllByFechaRange(desde, hasta);
        }
        PrimeFaces.current().executeScript("PF('w_entrega_operadores').clearFilters()");
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

    private boolean existeEntregaOperador(PlaRecuEntregaOperador obj) {
        return Objects.nonNull(entregaOperadorEJB.find(obj.getEmpleado().getIdEmpleado(), obj.getFechaAscensoNomina()));
    }

    private void crearEntregaOperadores(List<Object> list) {
        for (Object obj : list) {
            PlaRecuEntregaOperador obj_act = (PlaRecuEntregaOperador) obj;
            if (existeEntregaOperador(obj_act)) {
                MovilidadUtil.addAdvertenciaMessage("Operador " + obj_act.getEmpleado().getNombresApellidos() + " ya tiene un registro para la fecha ascenso nómina" + obj_act.getFechaAscensoNomina());
            } else {
                obj_act.setCreado(new Date());
                obj_act.setEstadoReg(0);
                obj_act.setUsernameCreate(user.getUsername());
                entregaOperadorEJB.create(obj_act);//persistir la activivdad
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
    private boolean validarErrorDatosEntregaOperador(PlaRecuEntregaOperador forDesEntregaOperador) {
        boolean flag = false;
        if (forDesEntregaOperador != null) {
            if (existeEntregaOperador(forDesEntregaOperador)) { //validar que los datos a insertar no existan en la base de datos, se valida código TM y fecha ascenso nómina
                MovilidadUtil.addAdvertenciaMessage("El colaborador " + forDesEntregaOperador.getEmpleado().getNombresApellidosCodigo() +" ya tiene registrada la fecha de ascenso seleccionada");
                b_error = flag = true;
            } else { //si no está repetido se validan los demás campos y reglas
                //Actualmente las validaciones de obligatieridad de información se relizan en el front, 
                //tampoco hay reglas o restricciones que implementar
            }
        }
        return flag;
    }

    public void crearEntregaOperador() {
        //no hay necesidad de validar la categoria dado que se ha validado 
        categoriaSelected = categoriaEjb.find(categoriaSelectedId);
        forDesEntregaOperador.setEmpleado(empleadoSelected);
        forDesEntregaOperador.setIdPlaRecuCategoria(categoriaSelected);
        forDesEntregaOperador.setIdGopUnidadFuncional(empleadoSelected.getIdGopUnidadFuncional());
        if (!validarErrorDatosEntregaOperador(forDesEntregaOperador)) {
            forDesEntregaOperador.setCreado(new Date());
            forDesEntregaOperador.setEstadoReg(0);
            forDesEntregaOperador.setUsernameCreate(user.getUsername());
            entregaOperadorEJB.create(forDesEntregaOperador);//persistir la activivdad
            forDesEntregaOperador = new PlaRecuEntregaOperador();
            actualizarLista();
            empleadoSelected = null;
            categoriaSelected = null;
            MovilidadUtil.addSuccessMessage("Registro 'Entrega Operador' creado con éxito.");
            PrimeFaces.current().executeScript("PF('wvForDesEntregaOperador').hide();");
        }
    }

    public void editarEntregaOperador() {
        //el registro viene cargado con el atributo global
        forDesEntregaOperador.setIdPlaRecuCategoria(categoriaEjb.find(categoriaSelectedId));
        if (!validarErrorDatosEntregaOperador(forDesEntregaOperador)) {
            forDesEntregaOperador.setIdGopUnidadFuncional(forDesEntregaOperador.getEmpleado().getIdGopUnidadFuncional());
            forDesEntregaOperador.setEstadoReg(0);
            forDesEntregaOperador.setModificado(new Date());//fecha de creación del registro
            forDesEntregaOperador.setUsernameEdit(user.getUsername());//usuario que modifica el registro
            entregaOperadorEJB.edit(forDesEntregaOperador);//persistir la infracción
            actualizarLista();
            MovilidadUtil.addSuccessMessage("Registro 'Entrega Operador' modificado con éxito.");
            PrimeFaces.current().executeScript("PF('wvForDesEntregaOperador').hide();");
        }
    }

    private void actualizarLista() {
        listEntregaOperadores = entregaOperadorEJB.findAll();
    }

    private void recorrerExcel(FileInputStream inputStream, List<Object> list_obj) throws Exception {
        recorrerExcelForDesEntregaOperador(inputStream, list_obj);
    }

    private void recorrerExcelForDesEntregaOperador(FileInputStream inputStream, List<Object> list_obj)
            throws IOException, Exception {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);
        int numFilas = sheet.getLastRowNum();
        boolean error = false;
        for (int a = 1; a <= numFilas; a++) {
            PlaRecuEntregaOperador ejecucion_obj = new PlaRecuEntregaOperador();
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
                                    ejecucion_obj.setEmpleado(emple);
                                    ejecucion_obj.setIdGopUnidadFuncional(emple.getIdGopUnidadFuncional());
                                } else {
                                    agregarError((a + 1), "Código TM", "El código TM no existe en la BD",
                                            valorDecimal.longValueExact());
                                    error = true;
                                }
                                break;
                            case 1: // Categoria
                                categoriaSelected = categoriaEjb.findByName(celda.toString());
                                if (categoriaSelected != null) {
                                    ejecucion_obj.setIdPlaRecuCategoria(categoriaSelected);
                                } else {
                                    agregarError((a + 1), "Categoria", "El nombre de la categoria no existe en la BD", celda.toString());
                                    error = true;
                                }
                                break;
                            case 2: // fecha entrega operaciones
                                try {
                                    parse = Util.toDate(Util.dateFormat(celda.getDateCellValue()));
                                } catch (Exception e) {
                                    parse = Util.toDate(celda.toString());
                                }
                                if (parse == null) {
                                    agregarError((a + 1), "Fecha Entrega Programación", "Formato de fecha erróneo", celda.toString());
                                    error = true;
                                } else {
                                    ejecucion_obj.setFechaEntregaProgramacion(parse);
                                }
                                break;
                            case 3: // fecha ascenso nómina
                                try {
                                    parse = Util.toDate(Util.dateFormat(celda.getDateCellValue()));
                                } catch (Exception e) {
                                    parse = Util.toDate(celda.toString());
                                }
                                if (parse == null) {
                                    agregarError((a + 1), "Fecha Ascenso Nómina", "Formato de fecha erróneo", celda.toString());
                                    error = true;
                                } else {
                                    ejecucion_obj.setFechaAscensoNomina(parse);
                                }
                                break;
                        }
                    } catch (Exception e) {
                        agregarError((a + 1), "", "Excepción en la columna " + (b + 1), "Corregir e intentar de nuevo");
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
            MovilidadUtil.addSuccessMessage("Proceso 'Carga de Archivo' Finalizado.");
        } else {
            PrimeFaces.current().executeScript("PF('cargar_entrega_operador_wv').show()");
            PrimeFaces.current().ajax().update("formErrores:erroresList");
        }
    }

    public void filtrarPorUF() {
        listEntregaOperadores = entregaOperadorEJB.findByIdGopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
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

    public PlaRecuEntregaOperador getForDesEntregaOperador() {
        return forDesEntregaOperador;
    }

    public void setForDesEntregaOperador(PlaRecuEntregaOperador forDesEntregaOperador) {
        this.forDesEntregaOperador = forDesEntregaOperador;
    }

    public List<PlaRecuEntregaOperador> getListForDesEntregaOperador() {
        return listEntregaOperadores;
    }

    public void setListForDesEntregaOperador(List<PlaRecuEntregaOperador> listEntregaOperadores) {
        this.listEntregaOperadores = listEntregaOperadores;
    }

    public Empleado getEmpleadoSelected() {
        return empleadoSelected;
    }

    public void setEmpleadoSelected(Empleado empleadoSelected) {
        this.empleadoSelected = empleadoSelected;
    }

    public PlaRecuCategoria getCategoriaSelected() {
        return categoriaSelected;
    }

    public void setCategoriaSelected(PlaRecuCategoria categoriaSelected) {
        this.categoriaSelected = categoriaSelected;
    }

    public String getCodigo_TM() {
        return codigo_TM;
    }

    public void setCodigo_TM(String codigo_TM) {
        this.codigo_TM = codigo_TM;
    }

    public List<PlaRecuCategoria> getListCategoria() {
        return listCategoria;
    }

    public void setListCategoria(List<PlaRecuCategoria> listCategoriaes) {
        this.listCategoria = listCategoriaes;
    }

    public String getCategoriaSel() {
        return categoriaSel;
    }

    public void setCategoriaSel(String categoriaSel) {
        this.categoriaSel = categoriaSel;
    }

    public Integer getCategoriaSelectedId() {
        return categoriaSelectedId;
    }

    public void setCategoriaSelectedId(Integer CategoriaSelectedId) {
        this.categoriaSelectedId = CategoriaSelectedId;
    }

}
