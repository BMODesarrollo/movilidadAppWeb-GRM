/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.dto.preCargaErrorPmDTO;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.PmGrupoDetalleFacadeLocal;
import com.movilidad.ejb.PmGrupoFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.Errores;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.PmGrupo;
import com.movilidad.model.PmGrupoDetalle;
import com.movilidad.model.PrgTc;
import com.movilidad.model.Vehiculo;
import com.movilidad.dto.preCargaPmDTO;
import com.movilidad.model.PrgAsignacion;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileNotFoundException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultUploadedFile;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Soluciones IT
 */
@Named(value = "pmGrupJSFMB")
@ViewScoped
public class PmGrupoJSFManagedBean implements Serializable {

    private PmGrupo pmGrup;
    private List<Empleado> listMaster;
    private List<Empleado> listOperadores;
    private PmGrupoDetalle pmGrupDet;
    private List<PmGrupoDetalle> listPmGrupDet;
    private List<PmGrupoDetalle> listPmGrupDetEliminar;
    private List<PmGrupo> listPmGrup;
    private Date fecha_ingreso;
    private boolean flag = false;
    final Set<String> setpreCargaDTO = new HashSet<>();

    HashMap<String, Vehiculo> mapVehiculo;
    HashMap<Object, Integer> mapServbus;
    private UploadedFile file;
    private List<preCargaPmDTO> listPreCarga = new ArrayList<>();
    private List<preCargaErrorPmDTO> listPreCargaError = new ArrayList<>();
    private List<preCargaPmDTO> listCargar = new ArrayList<>();
    private List<preCargaPmDTO> listCargarError = new ArrayList<>();
    private List<preCargaPmDTO> listCargadoCorrecto = new ArrayList<>();
    private List<Errores> listErrores;
    private List<String> listPlacas;
    private List<String> listServbus;
    private String fecha = "";
    private String path = "";
    private String separador = ";";
    private boolean b_permirtirAsignacion = true;
    private List<PrgTc> listPrgTc = new ArrayList<>();
    private Date fechaConsulta;
    private int sumRow = 0;
    private boolean flagDuplicadosFile = false;

    // control del tab que se está visualizando
    private Integer activeIndex;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private List<PrgAsignacion> listAsignacion;

    @EJB
    private EmpleadoFacadeLocal emplEJB;
    @EJB
    private PmGrupoFacadeLocal pmGrupEJB;
    @EJB
    private PmGrupoDetalleFacadeLocal pmGrupDTEJB;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    /**
     * Creates a new instance of PmGrupoJSFManagedBean
     */
    public void PmGrupoJSFManagedBean() {

    }

    @PostConstruct
    public void init() {
        consultar();
        activeIndex = 0;
    }

    public void consultar() {
        listPmGrup = pmGrupEJB.findAllByUnidadFuncional(
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void addMaster(Empleado m) {
        if (pmGrup.getIdEmpleado() == null) {
            listMaster.remove(m);
        } else {
            listMaster.add(pmGrup.getIdEmpleado());
            listMaster.remove(m);
        }
        pmGrup.setIdEmpleado(m);
    }

    public void preAddOperador(Empleado o) throws Exception {
        pmGrupDet = new PmGrupoDetalle();
        pmGrupDet.setCreado(new Date());
        pmGrupDet.setUsername(user.getUsername());
        pmGrupDet.setEstadoReg(0);
        pmGrupDet.setIdEmpleado(o);
        pmGrupDet.setFechaIngreso(o.getFechaIngreso());
        if (flag) {
            PmGrupoDetalle resp = pmGrupDTEJB.findByIdEmpleadoAndIdGrupoPm(
                    pmGrup.getIdPmGrupo(), o.getIdEmpleado());
            if (resp != null) {
                MovilidadUtil.addAdvertenciaMessage(
                        "Operador ya pertenece al grupo: " + resp.getIdGrupo().getNombreGrupo());
                pmGrupDet = null;
                return;
            }
        } else {
            PmGrupoDetalle resp = pmGrupDTEJB.findByIdEmpleadoAndIdGrupoPm(0, o.getIdEmpleado());
            if (resp != null) {
                MovilidadUtil.addAdvertenciaMessage(
                        "Operador ya pertenece al grupo: " + resp.getIdGrupo().getNombreGrupo());
                pmGrupDet = null;
                return;
            }
        }
        if (!listPmGrupDet.isEmpty()) {
            for (PmGrupoDetalle pmg : listPmGrupDet) {
                if (pmg.getIdEmpleado().getIdEmpleado().equals(o.getIdEmpleado())) {
                    MovilidadUtil.addAdvertenciaMessage("Este operador ya se agrego al grupo");
                    pmGrupDet = null;
                    return;
                }
            }
        } else {
            MovilidadUtil.openModal("pmGrupoFechaIngresoDialog");
            return;
        }
        MovilidadUtil.openModal("pmGrupoFechaIngresoDialog");
    }

    public void addOperador() throws Exception {
        listPmGrupDet.add(pmGrupDet);
        listOperadores.remove(pmGrupDet.getIdEmpleado());
    }

    public void verOperadores(PmGrupo pm) {

    }

    public int nOpe(PmGrupo pm) {
        return pm.getPmGrupoDetalleList().size();
    }

    public void delete(int index) {
        if (flag) {
            listPmGrupDetEliminar.add(listPmGrupDet.get(index));
        }
        listOperadores.add(listPmGrupDet.get(index).getIdEmpleado());
        listPmGrupDet.remove(index);
    }

    public void editar(PmGrupo pmg) {
        flag = true;
        pmGrup = pmg;
        int idUF = 0;
        if (pmGrup.getIdGopUnidadFuncional() != null) {
            idUF = pmGrup.getIdGopUnidadFuncional().getIdGopUnidadFuncional();
        } else if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() > 0) {
            idUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
        } else {
            MovilidadUtil.addErrorMessage("El grupo no tiene unidad funcional.");
            return;
        }
        listOperadores = emplEJB.findEmpleadosSinGrupoByUnidadFuncional(ConstantsUtil.ID_TPC_BIART, ConstantsUtil.ID_TPC_ART, idUF);
        listMaster = emplEJB.findEmpleadosSinGrupoByUnidadFuncional(ConstantsUtil.ID_TPC_MASTER, 0, idUF);
        listPmGrupDetEliminar = new ArrayList<>();
        listPmGrupDet = new ArrayList<>();
        listPmGrupDet.addAll(pmg.getPmGrupoDetalleList());
        MovilidadUtil.openModal("pmGrupoDialog");
    }

    public void openDialog() {
//        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
//            MovilidadUtil.addErrorMessage("Seleccionar la unidad funcional en la que va a trabajar.");
//            return;
//        }
        flag = false;
        listPmGrupDet = new ArrayList<>();
        pmGrup = new PmGrupo();
        pmGrup.setIdGopUnidadFuncional(new GopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));
        listMaster = emplEJB.findEmpleadosSinGrupoByUnidadFuncional(ConstantsUtil.ID_TPC_BIART,
                ConstantsUtil.ID_TPC_ART, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        listOperadores = emplEJB.findEmpleadosSinGrupoByUnidadFuncional(ConstantsUtil.ID_TPC_BIART,
                ConstantsUtil.ID_TPC_ART, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        MovilidadUtil.openModal("pmGrupoDialog");
    }

    public void guardar() {
        if (pmGrup.getIdEmpleado() == null) {
            MovilidadUtil.addAdvertenciaMessage("Error. Se debe agregar un Operador Master al grupo");
            return;
        }
//            if (listPmGrupDet.isEmpty()) {
//                MovilidadUtil.addAdvertenciaMessage("Error. Se debe agregar al menos un Operador al grupo");
//                return;
//            }
        if (flag) {
            pmGrup.setModificado(new Date());
            pmGrup.setPmGrupoDetalleList(new ArrayList<>());
            pmGrupEJB.edit(pmGrup);
            for (PmGrupoDetalle pmgd : listPmGrupDet) {
                pmgd.setIdGrupo(pmGrup);
                pmGrupDTEJB.edit(pmgd);
            }
            for (PmGrupoDetalle pmgdEli : listPmGrupDetEliminar) {
                pmGrupDTEJB.remove(pmgdEli);
            }
            pmGrup.setPmGrupoDetalleList(listPmGrupDet);

            MovilidadUtil.addSuccessMessage("Exito. Se ha modificado el grupo exitosamente");
        } else {
            pmGrup.setCreado(new Date());
            pmGrup.setUsername(user.getUsername());
            pmGrup.setEstadoReg(0);
            pmGrupEJB.create(pmGrup);
            listPmGrup.add(pmGrup);
            for (PmGrupoDetalle pmgd : listPmGrupDet) {
                pmgd.setIdGrupo(pmGrup);
                pmGrupDTEJB.create(pmgd);
            }
            listPmGrupDet.clear();
            pmGrup = new PmGrupo();
            MovilidadUtil.addSuccessMessage("Exito. Se ha registrado el grupo exitosamente");
        }
        MovilidadUtil.hideModal("pmGrupoDialog");
        consultar();
    }

    public void reset() {
        mapVehiculo = new HashMap<>();
        file = new DefaultUploadedFile();
        listErrores = new ArrayList<>();
        listPlacas = new ArrayList<>();
        listServbus = new ArrayList<>();
        listPreCarga = new ArrayList<>();
        fecha = "";
        path = "";
        b_permirtirAsignacion = true;
        sumRow = 0;
        PrimeFaces.current().ajax().update("formAsig:tab:datalistPreC");
        PrimeFaces.current().ajax().update("formAsig:tab:datalistErrs");
    }

    @Transactional
    public void programar() {
        System.out.println("Entra aquí");
        if (!listCargar.isEmpty()) {
            for (preCargaPmDTO p : listCargar) {
                try {
                    PmGrupoDetalle grupoDetalle = new PmGrupoDetalle();
                    grupoDetalle.setIdGrupo(p.getGrupo());
                    grupoDetalle.setIdEmpleado(p.getEmpleado());
                    grupoDetalle.setFechaIngreso(p.getEmpleado().getFechaIngreso());
                    grupoDetalle.setUsername(user.getUsername());
                    grupoDetalle.setEstadoReg(0);
                    grupoDetalle.setCreado(new Date());
                    pmGrupDTEJB.create(grupoDetalle); 
                    listCargadoCorrecto.add(p);
                } catch (Exception e) {
                    listCargarError.add(p);
                }

            }
        }
        PrimeFaces.current().ajax().update("formAsig:tab:datalist");
        if (!listCargadoCorrecto.isEmpty()) {
            MovilidadUtil.addSuccessMessage("La asignación de grupos se ha realizado correctamente");
            PrimeFaces.current().ajax().update("formAsig:upAsig");
            PrimeFaces.current().executeScript("PF('upCsv').hide();");
        } else {
            MovilidadUtil.addErrorMessage("No hay casos asignables a un grupo");
            PrimeFaces.current().ajax().update("formAsig:upAsig");
            PrimeFaces.current().executeScript("PF('reescribirC').hide();");
        }
//        System.out.println(sumRow);
        reset();
    }

    public void handleFileUpload(FileUploadEvent event) throws FileNotFoundException {
        file = event.getFile();
        path = Util.saveFile(file, 0, "asignacion");
        Scanner scar = new Scanner(new File(path));
        int i = 0;
        while (scar.hasNextLine()) {
            String nextLine = scar.nextLine();
            String[] split = nextLine.split(separador);
            fecha = split[0];
            i++;
            if (i == 3) {
                scar.close();
                break;
            }
        }
        prepareCargar();
    }

    public void prepareCargar() throws FileNotFoundException {
        if (file != null) {
            obtenerProgramacion();
            return;
        }
        MovilidadUtil.addErrorMessage("Error al cargar el archivo");
        PrimeFaces.current().ajax().update("formAsig:upAsig");
        PrimeFaces.current().executeScript("PF('upCsv').hide();");
        reset();
    }

    public preCargaErrorPmDTO agregarListaError(preCargaPmDTO obj, String motivo) {
        preCargaErrorPmDTO objError = new preCargaErrorPmDTO();
        objError.setGrupo(obj.getGrupoName());
        objError.setOperador(obj.getOperador());
        objError.setMotivo(motivo);
        return objError;
    }

    void obtenerProgramacion() throws FileNotFoundException {
        try ( Scanner scar = new Scanner(new File(path))) {
            while (scar.hasNextLine()) {
                sumRow = sumRow+1;
                String nextLine = scar.nextLine();
                String[] split = nextLine.split(separador);
                preCargaPmDTO obj = new preCargaPmDTO(split[0], split[1], null, null);
                // Verificar si el objeto ya existe en el set
                if (!setpreCargaDTO.contains(obj.getOperador())) {
                    setpreCargaDTO.add(obj.getOperador()); // Agregar al set para mantener registro de los objetos únicos

                    listPreCarga.add(obj); // Agregar a la lista
                } else {
                    listPreCargaError.add(agregarListaError(obj, "Operador duplicado en el archivo"));
                }
            }
        }
        listPmGrupDet = pmGrupDTEJB.findByActivosGrupo();
        Set<String> setCargadosCodigoDTO = new HashSet<>();
        Set<String> setSeniorCargadoDTO = new HashSet<>();
        listPmGrup = pmGrupEJB.findAllActivos();
        for (PmGrupoDetalle objDetalle : listPmGrupDet) {
            setCargadosCodigoDTO.add(objDetalle.getIdEmpleado().getCodigoTm().toString());
        }
        for (PmGrupo objGrupo : listPmGrup) {
            setSeniorCargadoDTO.add(objGrupo.getIdEmpleado().getCodigoTm().toString());
        }
        for (preCargaPmDTO objPrecarga : listPreCarga) {
            if (!setCargadosCodigoDTO.contains(objPrecarga.getOperador())) {
                if (!setSeniorCargadoDTO.contains(objPrecarga.getOperador())) {
                    PmGrupo grupo = new PmGrupo();
                    Empleado empleado = new Empleado();
                    grupo = pmGrupEJB.findByName(objPrecarga.getGrupoName());
                    empleado = emplEJB.findByCodigoTM(Integer.parseInt(objPrecarga.getOperador()));
                    if (grupo == null) {
                        listPreCargaError.add(agregarListaError(objPrecarga, "El grupo digitado no existe"));
                    } else if (empleado == null) {
                        listPreCargaError.add(agregarListaError(objPrecarga, "El códigoTM del operador no existe"));
                    } else if (!Objects.equals(empleado.getIdGopUnidadFuncional().getIdGopUnidadFuncional(), grupo.getIdGopUnidadFuncional().getIdGopUnidadFuncional())) {
                        listPreCargaError.add(agregarListaError(objPrecarga, "El grupo no corresponde la UF del operador"));
                    } else {
                        objPrecarga.setGrupo(grupo);
                        objPrecarga.setEmpleado(empleado);
                        listCargar.add(objPrecarga); // Agregar a la lista
                    }
                } else {
                    listPreCargaError.add(agregarListaError(objPrecarga, "El operador es senior de un grupo"));
                }
            } else {
                listPreCargaError.add(agregarListaError(objPrecarga, "Operador ya se encuentra asignado a un grupo"));
            }
        }
        updateFileUpload();
    }

    void updateFileUpload() {
        PrimeFaces.current().ajax().update("formAsig:tab:datalistPreC");
        PrimeFaces.current().ajax().update("formAsig:tab:datalistErrs");
        PrimeFaces.current().executeScript("PF('upCsv').hide();");
        PrimeFaces.current().ajax().update("formAsig:upAsig");
    }

    public PmGrupo getPmGrup() {
        return pmGrup;
    }

    public void setPmGrup(PmGrupo pmGrup) {
        this.pmGrup = pmGrup;
    }

    public PmGrupoDetalle getPmGrupDet() {
        return pmGrupDet;
    }

    public void setPmGrupDet(PmGrupoDetalle pmGrupDet) {
        this.pmGrupDet = pmGrupDet;
    }

    public List<PmGrupoDetalle> getListPmGrupDet() {
        return listPmGrupDet;
    }

    public void setListPmGrupDet(List<PmGrupoDetalle> listPmGrupDet) {
        this.listPmGrupDet = listPmGrupDet;
    }

    public List<PmGrupo> getListPmGrup() {
        return listPmGrup;
    }

    public void setListPmGrup(List<PmGrupo> listPmGrup) {
        this.listPmGrup = listPmGrup;
    }

    public List<Empleado> getListMaster() {
        return listMaster;
    }

    public void setListMaster(List<Empleado> listMaster) {
        this.listMaster = listMaster;
    }

    public List<Empleado> getListOperadores() {
        return listOperadores;
    }

    public void setListOperadores(List<Empleado> listOperadores) {
        this.listOperadores = listOperadores;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public Integer getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(Integer activeIndex) {
        this.activeIndex = activeIndex;
    }

    public List<PmGrupoDetalle> getListPmGrupDetEliminar() {
        return listPmGrupDetEliminar;
    }

    public void setListPmGrupDetEliminar(List<PmGrupoDetalle> listPmGrupDetEliminar) {
        this.listPmGrupDetEliminar = listPmGrupDetEliminar;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public HashMap<String, Vehiculo> getMapVehiculo() {
        return mapVehiculo;
    }

    public void setMapVehiculo(HashMap<String, Vehiculo> mapVehiculo) {
        this.mapVehiculo = mapVehiculo;
    }

    public HashMap<Object, Integer> getMapServbus() {
        return mapServbus;
    }

    public void setMapServbus(HashMap<Object, Integer> mapServbus) {
        this.mapServbus = mapServbus;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<preCargaPmDTO> getListPreCarga() {
        return listPreCarga;
    }

    public void setListPreCarga(List<preCargaPmDTO> listPreCarga) {
        this.listPreCarga = listPreCarga;
    }

    public List<Errores> getListErrores() {
        return listErrores;
    }

    public void setListErrores(List<Errores> listErrores) {
        this.listErrores = listErrores;
    }

    public List<String> getListPlacas() {
        return listPlacas;
    }

    public void setListPlacas(List<String> listPlacas) {
        this.listPlacas = listPlacas;
    }

    public List<String> getListServbus() {
        return listServbus;
    }

    public void setListServbus(List<String> listServbus) {
        this.listServbus = listServbus;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSeparador() {
        return separador;
    }

    public void setSeparador(String separador) {
        this.separador = separador;
    }

    public boolean isB_permirtirAsignacion() {
        return b_permirtirAsignacion;
    }

    public void setB_permirtirAsignacion(boolean b_permirtirAsignacion) {
        this.b_permirtirAsignacion = b_permirtirAsignacion;
    }

    public List<PrgTc> getListPrgTc() {
        return listPrgTc;
    }

    public void setListPrgTc(List<PrgTc> listPrgTc) {
        this.listPrgTc = listPrgTc;
    }

    public Date getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(Date fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public int getSumRow() {
        return sumRow;
    }

    public void setSumRow(int sumRow) {
        this.sumRow = sumRow;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public EmpleadoFacadeLocal getEmplEJB() {
        return emplEJB;
    }

    public void setEmplEJB(EmpleadoFacadeLocal emplEJB) {
        this.emplEJB = emplEJB;
    }

    public PmGrupoFacadeLocal getPmGrupEJB() {
        return pmGrupEJB;
    }

    public void setPmGrupEJB(PmGrupoFacadeLocal pmGrupEJB) {
        this.pmGrupEJB = pmGrupEJB;
    }

    public PmGrupoDetalleFacadeLocal getPmGrupDTEJB() {
        return pmGrupDTEJB;
    }

    public void setPmGrupDTEJB(PmGrupoDetalleFacadeLocal pmGrupDTEJB) {
        this.pmGrupDTEJB = pmGrupDTEJB;
    }

    public GopUnidadFuncionalSessionBean getUnidadFuncionalSessionBean() {
        return unidadFuncionalSessionBean;
    }

    public void setUnidadFuncionalSessionBean(GopUnidadFuncionalSessionBean unidadFuncionalSessionBean) {
        this.unidadFuncionalSessionBean = unidadFuncionalSessionBean;
    }

    public List<PrgAsignacion> getListAsignacion() {
        return listAsignacion;
    }

    public void setListAsignacion(List<PrgAsignacion> listAsignacion) {
        this.listAsignacion = listAsignacion;
    }

    public List<preCargaErrorPmDTO> getListPreCargaError() {
        return listPreCargaError;
    }

    public void setListPreCargaError(List<preCargaErrorPmDTO> listPreCargaError) {
        this.listPreCargaError = listPreCargaError;
    }

    public boolean isFlagDuplicadosFile() {
        return flagDuplicadosFile;
    }

    public void setFlagDuplicadosFile(boolean flagDuplicadosFile) {
        this.flagDuplicadosFile = flagDuplicadosFile;
    }

    public List<preCargaPmDTO> getListCargar() {
        return listCargar;
    }

    public void setListCargar(List<preCargaPmDTO> listCargar) {
        this.listCargar = listCargar;
    }

    public List<preCargaPmDTO> getListCargarError() {
        return listCargarError;
    }

    public void setListCargarError(List<preCargaPmDTO> listCargarError) {
        this.listCargarError = listCargarError;
    }

    public List<preCargaPmDTO> getListCargadoCorrecto() {
        return listCargadoCorrecto;
    }

    public void setListCargadoCorrecto(List<preCargaPmDTO> listCargadoCorrecto) {
        this.listCargadoCorrecto = listCargadoCorrecto;
    }
    
}
