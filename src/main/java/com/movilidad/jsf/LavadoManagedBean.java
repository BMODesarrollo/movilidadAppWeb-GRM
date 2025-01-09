package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.LavadoFacadeLocal;
import com.movilidad.ejb.LavadoNOFacadeLocal;
import com.movilidad.ejb.LavadoNoConformeFacadeLocal;
import com.movilidad.ejb.LavadoResponsableFacadeLocal;
import com.movilidad.ejb.LavadoTipoFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.Lavado;
import com.movilidad.model.LavadoNO;
import com.movilidad.model.LavadoNoConforme;
import com.movilidad.model.LavadoTipo;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "lavadoBean")
@ViewScoped
public class LavadoManagedBean implements Serializable {

    @EJB
    private LavadoFacadeLocal lavadoEjb;
    @EJB
    private LavadoTipoFacadeLocal lavadoTipoEjb;
    @EJB
    private LavadoNOFacadeLocal lavadoNOEjb;
    @EJB
    private LavadoNoConformeFacadeLocal lavadoNoConformeEjb;
    @EJB
    private LavadoResponsableFacadeLocal lavadoResponsableEjb;
    @EJB
    private VehiculoFacadeLocal vehiculoEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;

    private Lavado lavado;
    private LavadoNO lavadoNO;
    private LavadoNoConforme lavadoNoConforme;
    private LavadoNoConforme selectedNoConforme;
    private LavadoNO selectedNO;
    private Lavado selected;

    private List<String> lstVehiculos;
    private List<String> lstResponsable;
    private List<String> lstActividades;
    private String[] lstSelectedAct;
    private String[] lstSelectedVeh;
    private String[] lstSelectedResp;
    private List<String> lstActividadesPreseleccionadas;
    private List<String> lstResponsablePreseleccionadas;
    private List<String> lstVehiculosPreseleccionadas;
    private List<Lavado> lstLavados;
    private List<LavadoNO> lstNOLavados;
    private List<LavadoNoConforme> lstLavadoNoConforme;
    private List<LavadoTipo> lstLavadoTipos;
    private List<Empleado> lstLavadoResponsables;

    private int idLavadoTipo;
    private int idLavadoMotivo;
    private int idLavadoResponsable;
    private boolean solucionado;
    private String codigoVehiculo;

    private Date fechaDesde;
    private Date fechaHasta;

    private String observación;
    private Vehiculo vehiculo;

    private final PrimeFaces current = PrimeFaces.current();

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        lstNOLavados = lavadoNOEjb.findAll();
        lstLavadoNoConforme = lavadoNoConformeEjb.findAll();
        solucionado = false;
        lavadoNoConforme = new LavadoNoConforme();
        lstActividades = cargarActividades();
        lstVehiculos = cargarVehiculos();
        lstResponsable = cargarResponsable();
        lstActividadesPreseleccionadas = new ArrayList<>();
        lstResponsablePreseleccionadas = new ArrayList<>();
        lstVehiculosPreseleccionadas = new ArrayList<>();
    }

    public void nuevo() {
        idLavadoTipo = 0;
        idLavadoMotivo = 0;
        idLavadoResponsable = 0;
        codigoVehiculo = "";
        lavado = new Lavado();
        lavadoNO = new LavadoNO();
        lavadoNoConforme = new LavadoNoConforme();
        selected = null;
        selectedNO = null;
        selectedNoConforme = null;
        lstSelectedAct = null;
        lstSelectedVeh = null;
        lstSelectedResp = null;
    }

    private List<String> cargarActividades() {
        List<String> list = new ArrayList<>();
        list.add("Lavado Piso");
        list.add("Lavado Techo");
        list.add("Lavado Vidrios");
        list.add("Lavado Puertas");
        return list;
    }

    private List<String> cargarVehiculos() {
        List<Vehiculo> list = vehiculoEjb.findAll();
        List<String> listCod = new ArrayList<>();
        for (Vehiculo v : list) {
            listCod.add(v.getCodigo());
        }
        return listCod;
    }

    private List<String> cargarResponsable() {
        return lavadoEjb.getNameListEmployee();
    }

    public String obtenerVehMulti(String idMulti) {
        String cadena = "";
        String[] list = idMulti.split(",");
        for (String s : list) {
            try {
                cadena += vehiculoEjb.find(Integer.valueOf(s)).getCodigo() + ", " + '\n';
            } catch (NumberFormatException e) {
                System.out.println("Error al obtener el código del vehículo");
            }
        }
        return cadena;
    }

    public String obtenerRespMulti(String idMulti) {
        String cadena = "";
        String[] list = idMulti.split(",");
        Empleado emp;
        for (String s : list) {
            try {
                emp = empleadoEjb.find(Integer.valueOf(s));
                cadena += emp.getNombres() + " " + emp.getApellidos() + ", " + '\n';
            } catch (NumberFormatException e) {
                System.out.println("Error al obtener el nombre del responsable");
            }
        }
        return cadena;
    }

    public void editar() {
        if (selected == null) {
            current.ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("Debe seleccionar un registro");
            return;
        }

        lavado = selected;
        idLavadoTipo = lavado.getIdLavadoTipo().getIdLavadoTipo();
        idLavadoResponsable = lavado.getIdLavadoResponsable().getIdLavadoResponsable();
        codigoVehiculo = lavado.getIdVehiculo().getCodigo();
        current.executeScript("PF('parametroDlg').show();");
    }

    public void editarNoConforme() {
        if (selectedNoConforme == null) {
            current.ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("Debe seleccionar un registro");
            return;
        }
        lavadoNoConforme = selectedNoConforme;
        fillPreselected(selectedNoConforme.getIdActividadMulti(), 1);
//        
//        lstActividadesPreseleccionadas.add("Lavado Techo");
        fillPreselected(selectedNoConforme.getIdResponsableMulti(), 2);
        fillPreselected(selectedNoConforme.getIdVehiculoMulti(), 3);

    }

    public void editarNOLavado() {
        if (selectedNO == null) {
            current.ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("Debe seleccionar un registro");
            return;
        }
        lavadoNO = selectedNO;
        idLavadoMotivo = selectedNO.getIdLavadoMotivo();
        codigoVehiculo = selectedNO.getIdVehiculo().getCodigo();
    }

    private void fillPreselected(String cadena, int opt) {
        String[] list = cadena.split(",");
        switch (opt) {
            case 1: //actividades
                lstActividadesPreseleccionadas.addAll(Arrays.asList(list));
//                    switch (s) {
//                        case "1":
//                            lstActividadesPreseleccionadas.add("Lavado Piso");
//                            break;
//                        case "2":
//                            lstActividadesPreseleccionadas.add("Lavado Techo");
//                            break;
//                        case "3":
//                            lstActividadesPreseleccionadas.add("Lavado Vidrios");
//                            break;
//                        case "4":
//                            lstActividadesPreseleccionadas.add("Lavado Puertas");
//                            break;
//                    }
                break;

            case 2://responsables
                for (String s : list) {
                    Empleado emp = empleadoEjb.find(Integer.valueOf(s));
                    lstResponsablePreseleccionadas.add(emp.getNombres() + " " + emp.getApellidos());
                }
                break;
            case 3://vehiculos
                for (String s : list) {
                    lstVehiculosPreseleccionadas.add(vehiculoEjb.find(Integer.valueOf(s)).getCodigo());
                }
                break;
        }
    }

    public void guardar() {
        if (!validarDatos()) {
            lavado.setIdLavadoTipo(lavadoTipoEjb.find(idLavadoTipo));
            lavado.setIdLavadoResponsable(lavadoResponsableEjb.find(idLavadoResponsable));
            lavado.setCreado(new Date());
            lavado.setEstadoReg(0);
            lavado.setUsername(user.getUsername());
            lavadoEjb.create(lavado);
            lstLavados.add(lavado);
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro registrado éxitosamente");
        }
    }

    public void guardarNoLavado() {
        if (!validarDatosNOLavado()) {
            lavadoNO.setIdLavadoMotivo(idLavadoMotivo);
//            lavadoNO.setIdVehiculo(vehiculo);
            lavadoNO.setFechaHora(new Date());
            lavadoNO.setCreado(new Date());
            lavadoNO.setEstadoReg(0);
            lavadoNO.setUsername(user.getUsername());
            lavadoNOEjb.create(lavadoNO);
            lstNOLavados.add(lavadoNO);
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro registrado éxitosamente");
        }
    }

    public void guardarNoConforme() {
        if (!errorDatosNoConforme(1)) {
            lavadoNoConforme.setIdActividadMulti(obtenerString(lstSelectedAct, 1));
            lavadoNoConforme.setIdResponsableMulti(obtenerString(lstSelectedResp, 2));
            lavadoNoConforme.setIdVehiculoMulti(obtenerString(lstSelectedVeh, 3));
            lavadoNoConforme.setFechaHora(new Date());
            lavadoNoConforme.setCreado(new Date());
            lavadoNoConforme.setEstadoReg(0);
            lavadoNoConforme.setUsername(user.getUsername());
            lavadoNoConformeEjb.create(lavadoNoConforme);
            lstLavadoNoConforme.add(lavadoNoConforme);
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro no conforme registrado éxitosamente");
        }
    }

    public void actualizarNoLavado() {
        try {
            if (!validarDatosNOLavado()) {
                selectedNO.setIdLavadoMotivo(idLavadoMotivo);
                selectedNO.setIdVehiculo(lavadoNO.getIdVehiculo());
                selectedNO.setObservacion(lavadoNO.getObservacion());
                lavadoNOEjb.edit(selectedNO);
                MovilidadUtil.addSuccessMessage("Se a actualizado registro de lavado correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('noLavadoDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar registro lavado");
        }
    }

    public void actualizarNoConforme() {
        try {
            if (!errorDatosNoConforme(2)) {
                selectedNoConforme.setIdActividadMulti(obtenerString(lstActividadesPreseleccionadas, 1));
                selectedNoConforme.setIdResponsableMulti(obtenerString(lstResponsablePreseleccionadas, 2));
                selectedNoConforme.setIdVehiculoMulti(obtenerString(lstVehiculosPreseleccionadas, 3));
                lavadoNoConformeEjb.edit(selectedNoConforme);
                MovilidadUtil.addSuccessMessage("Se a actualizado registro de lavado no conforme correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('noConformeDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar registro lavado no conforme");
        }
    }

    public void reset() {
        selectedNoConforme = null;
        selectedNO = null;
        selected = null;
        lstActividadesPreseleccionadas = new ArrayList<>();
        lstResponsablePreseleccionadas = new ArrayList<>();
        lstVehiculosPreseleccionadas = new ArrayList<>();
    }

    /**
     *
     * @param event Objeto AccClase seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        reset();
        try {
            selectedNoConforme = (LavadoNoConforme) event.getObject();
        } catch (Exception e) {
            System.out.println("Error en llamado metodo onRowSelect para objeto de no conforme");
        }
        try {
            selectedNO = (LavadoNO) event.getObject();
        } catch (Exception e) {
            System.out.println("Error en llamado metodo onRowSelect para objeto de no lavado");
        }

    }

    public String obtenerMotivo(int id) {
        return lavadoTipoEjb.find(id).getTipoLavado();
    }

    private String obtenerString(String[] list, int opt) {
        String cadena = "";
        switch (opt) {
            case 1://Actividades
                for (String e : list) {
                    cadena += e + ",";
                }
                break;
            case 2://Responsables
                for (String e : list) {
                    cadena += empleadoEjb.getEmpleadoByFullname(e) + ",";
                }
                break;
            case 3://Vehículos
                for (String e : list) {
                    cadena += vehiculoEjb.getVehiculoCodigo(e).getIdVehiculo() + ",";
                }
                break;
        }
        return cadena;
    }
    
    private String obtenerString(List<String> list, int opt) {
        String cadena = "";
        switch (opt) {
            case 1://Actividades
                for (String e : list) {
                    cadena += e + ",";
                }
                break;
            case 2://Responsables
                for (String e : list) {
                    cadena += empleadoEjb.getEmpleadoByFullname(e) + ",";
                }
                break;
            case 3://Vehículos
                for (String e : list) {
                    cadena += vehiculoEjb.getVehiculoCodigo(e).getIdVehiculo() + ",";
                }
                break;
        }
        return cadena;
    }

    public void actualizar() {
        if (!validarDatos()) {
            lavado.setIdLavadoTipo(lavadoTipoEjb.find(idLavadoTipo));
            lavado.setIdLavadoResponsable(lavadoResponsableEjb.find(idLavadoResponsable));
            lavado.setModificado(new Date());
            lavado.setUsername(user.getUsername());
            lavadoEjb.edit(lavado);
            current.ajax().update(":frmParametroList:dtParametros");
            current.executeScript("PF('parametroDlg').hide();");
            MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
        }
    }

    public String validarFecha(int op) {
        Date hoy = new Date();
        if (op == 0) {
            return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(hoy);
        } else {
            return new SimpleDateFormat("yyyy/MM/dd - HH:mm").format(hoy);
        }
    }

    public void buscarVehiculo() {
        if (codigoVehiculo == null || codigoVehiculo.equals("")) {
            MovilidadUtil.addErrorMessage("Debe ingresar código de vehículo");
            return;
        }
        Vehiculo vehiculo = vehiculoEjb.getVehiculoCodigo(codigoVehiculo);
        if (vehiculo != null) {
            lavado.setIdVehiculo(vehiculo);
            MovilidadUtil.addSuccessMessage("Vehículo válido");
        } else {
            MovilidadUtil.addErrorMessage("Vehículo NO válido");
        }
    }

    public void buscarVehiculoNOLavado() {
        if (codigoVehiculo == null || codigoVehiculo.equals("")) {
            MovilidadUtil.addErrorMessage("Debe ingresar código de vehículo");
            return;
        }
        Vehiculo vehiculo = vehiculoEjb.getVehiculoCodigo(codigoVehiculo);
        if (vehiculo != null) {
            lavadoNO.setIdVehiculo(vehiculo);
            selectedNO.setIdVehiculo(vehiculo);
            MovilidadUtil.addSuccessMessage("Vehículo válido");
        } else {
            MovilidadUtil.addErrorMessage("Vehículo NO válido");
        }
    }

    public boolean validarDatos() {
        if (lavado.getFechaHora() == null) {
            MovilidadUtil.addErrorMessage("Debe especificar fecha y hora");
            return true;
        }
        if (idLavadoTipo == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar tipo de lavado");
            return true;
        }
        if (idLavadoResponsable == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar responsable");
            return true;
        }
        if (codigoVehiculo == null || codigoVehiculo.equals("")) {
            MovilidadUtil.addErrorMessage("Debe ingresar código de vehículo");
            return true;
        }
        return false;
    }

    public boolean validarDatosNOLavado() {
//        if (lavadoNO.getFechaHora() == null) {
//            MovilidadUtil.addErrorMessage("Debe especificar fecha y hora");
//            return true;
//        }
        if (idLavadoMotivo == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar motivo de no lavado");
            return true;
        }
//        if (idLavadoResponsable == 0) {
//            MovilidadUtil.addErrorMessage("Debe seleccionar responsable");
//            return true;
//        }
        if (codigoVehiculo == null || codigoVehiculo.equals("")) {
            MovilidadUtil.addErrorMessage("Debe ingresar código de vehículo");
            return true;
        }
        return false;
    }

    public boolean validarDatosNoConforme() {
//        if (lavadoNO.getFechaHora() == null) {
//            MovilidadUtil.addErrorMessage("Debe especificar fecha y hora");
//            return true;
//        }
        if (idLavadoMotivo == 0) {
            MovilidadUtil.addErrorMessage("Debe seleccionar motivo de no lavado");
            return true;
        }
//        if (idLavadoResponsable == 0) {
//            MovilidadUtil.addErrorMessage("Debe seleccionar responsable");
//            return true;
//        }
        if (codigoVehiculo == null || codigoVehiculo.equals("")) {
            MovilidadUtil.addErrorMessage("Debe ingresar código de vehículo");
            return true;
        }
        return false;
    }

    public void consultar() {
        if (fechaHasta != null) {
            fechaHasta.setHours(23);
            fechaHasta.setMinutes(59);
            fechaHasta.setSeconds(59);
        }
        lstNOLavados = lavadoNOEjb.findByDateRange(fechaDesde, fechaHasta);
        PrimeFaces.current().executeScript("PF('noLavadoDlg').clearFilters()");
    }

    public void consultarNoConforme() {
        if (fechaHasta != null) {
            fechaHasta.setHours(23);
            fechaHasta.setMinutes(59);
            fechaHasta.setSeconds(59);
        }
        lstLavadoNoConforme = lavadoNoConformeEjb.findByDateRange(fechaDesde, fechaHasta);
        PrimeFaces.current().executeScript("PF('noLavadoDlg').clearFilters()");
    }

    public void solucionar(LavadoNoConforme obj) {
//        obj.setSolucionado(solucionado);
        obj.setModificado(new Date());
        lavadoNoConformeEjb.edit(obj);
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public boolean errorDatosNoConforme(int opt) {
        switch (opt) {
            case 1://nuevo registro no conforme
                if (lstSelectedAct == null || lstSelectedAct.length == 0) {
                    MovilidadUtil.addErrorMessage("Debe seleccionar 'Actividad(es)' ");
                    return true;
                }
                if (lstSelectedResp == null && lstSelectedResp.length == 0) {
                    MovilidadUtil.addErrorMessage("Debe seleccionar 'Responsable(s)' ");
                    return true;
                }
                if (lstSelectedVeh == null && lstSelectedVeh.length == 0) {
                    MovilidadUtil.addErrorMessage("Debe seleccionar 'Vehículo(s)' ");
                    return true;
                }
                break;
                
            case 2://edición registro no conforme
                if (lstActividadesPreseleccionadas == null || lstActividadesPreseleccionadas.isEmpty()) {
                    MovilidadUtil.addErrorMessage("Debe seleccionar 'Actividad(es)' ");
                    return true;
                }
                if (lstResponsablePreseleccionadas == null && lstResponsablePreseleccionadas.isEmpty()) {
                    MovilidadUtil.addErrorMessage("Debe seleccionar 'Responsable(s)' ");
                    return true;
                }
                if (lstVehiculosPreseleccionadas == null && lstVehiculosPreseleccionadas.isEmpty()) {
                    MovilidadUtil.addErrorMessage("Debe seleccionar 'Vehículo(s)' ");
                    return true;
                }
                break;
        }

        return false;
    }

    public void handleClose(CloseEvent event) {
        selected = null;
    }

    public List<Lavado> getLstLavados() {
        lstLavados = lavadoEjb.findAll();
        return lstLavados;
    }

    public void setLstLavados(List<Lavado> lstLavados) {
        this.lstLavados = lstLavados;
    }

    public List<LavadoTipo> getLstLavadoTipos() {
        lstLavadoTipos = lavadoTipoEjb.findAll();
        return lstLavadoTipos;
    }

    public void setLstLavadoTipos(List<LavadoTipo> lstLavadoTipos) {
        this.lstLavadoTipos = lstLavadoTipos;
    }

    public List<Empleado> getLstLavadoResponsables() {
        lstLavadoResponsables = lavadoEjb.findEmployee();
        return lstLavadoResponsables;
    }

    public void setLstLavadoResponsables(List<Empleado> lstLavadoResponsables) {
        this.lstLavadoResponsables = lstLavadoResponsables;
    }

    public int getIdLavadoTipo() {
        return idLavadoTipo;
    }

    public void setIdLavadoTipo(int idLavadoTipo) {
        this.idLavadoTipo = idLavadoTipo;
    }

    public int getIdLavadoResponsable() {
        return idLavadoResponsable;
    }

    public void setIdLavadoResponsable(int idLavadoResponsable) {
        this.idLavadoResponsable = idLavadoResponsable;
    }

    public String getCodigoVehiculo() {
        return codigoVehiculo;
    }

    public void setCodigoVehiculo(String codigoVehiculo) {
        this.codigoVehiculo = codigoVehiculo;
    }

    public Lavado getLavado() {
        return lavado;
    }

    public void setLavado(Lavado lavado) {
        this.lavado = lavado;
    }

    public Lavado getSelected() {
        return selected;
    }

    public void setSelected(Lavado selected) {
        this.selected = selected;
    }

    public List<Lavado> getLstTipoLavados() {
        lstLavados = lavadoEjb.findAll();
        return lstLavados;
    }

    public void setLstTipoLavados(List<Lavado> lstLavados) {
        this.lstLavados = lstLavados;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public LavadoNO getLavadoNO() {
        return lavadoNO;
    }

    public void setLavadoNO(LavadoNO lavadoNO) {
        this.lavadoNO = lavadoNO;
    }

    public LavadoNO getSelectedNO() {
        return selectedNO;
    }

    public void setSelectedNO(LavadoNO selectedNO) {
        this.selectedNO = selectedNO;
    }

    public List<LavadoNO> getLstNOLavados() {
        return lstNOLavados;
    }

    public void setLstNOLavados(List<LavadoNO> lstNOLavados) {
        this.lstNOLavados = lstNOLavados;
    }

    public int getIdLavadoMotivo() {
        return idLavadoMotivo;
    }

    public void setIdLavadoMotivo(int idLavadoMotivo) {
        this.idLavadoMotivo = idLavadoMotivo;
    }

    public List<LavadoNoConforme> getLstLavadoNoConforme() {
        return lstLavadoNoConforme;
    }

    public void setLstLavadoNoConforme(List<LavadoNoConforme> lstLavadoNoConforme) {
        this.lstLavadoNoConforme = lstLavadoNoConforme;
    }

    public LavadoNoConforme getLavadoNoConforme() {
        return lavadoNoConforme;
    }

    public void setLavadoNoConforme(LavadoNoConforme lavadoNoConforme) {
        this.lavadoNoConforme = lavadoNoConforme;
    }

    public LavadoNoConforme getSelectedNoConforme() {
        return selectedNoConforme;
    }

    public void setSelectedNoConforme(LavadoNoConforme selectedNoConforme) {
        this.selectedNoConforme = selectedNoConforme;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getObservación() {
        return observación;
    }

    public void setObservación(String observación) {
        this.observación = observación;
    }

    public List<String> getLstVehiculos() {
        return lstVehiculos;
    }

    public void setLstVehiculos(List<String> lstVehiculos) {
        this.lstVehiculos = lstVehiculos;
    }

    public List<String> getLstResponsable() {
        return lstResponsable;
    }

    public void setLstResponsable(List<String> lstResponsable) {
        this.lstResponsable = lstResponsable;
    }

    public List<String> getLstActividades() {
        return lstActividades;
    }

    public void setLstActividades(List<String> lstActividades) {
        this.lstActividades = lstActividades;
    }

    public String[] getLstSelectedAct() {
        return lstSelectedAct;
    }

    public void setLstSelectedAct(String[] lstSelectedAct) {
        this.lstSelectedAct = lstSelectedAct;
    }

    public String[] getLstSelectedVeh() {
        return lstSelectedVeh;
    }

    public void setLstSelectedVeh(String[] lstSelectedVeh) {
        this.lstSelectedVeh = lstSelectedVeh;
    }

    public String[] getLstSelectedResp() {
        return lstSelectedResp;
    }

    public void setLstSelectedResp(String[] lstSelectedResp) {
        this.lstSelectedResp = lstSelectedResp;
    }

    public boolean isSolucionado() {
        return solucionado;
    }

    public void setSolucionado(boolean solucionado) {
        this.solucionado = solucionado;
    }

    public List<String> getLstActividadesPreseleccionadas() {
        return lstActividadesPreseleccionadas;
    }

    public void setLstActividadesPreseleccionadas(List<String> lstActividadesPreseleccionadas) {
        this.lstActividadesPreseleccionadas = lstActividadesPreseleccionadas;
    }

    public List<String> getLstResponsablePreseleccionadas() {
        return lstResponsablePreseleccionadas;
    }

    public void setLstResponsablePreseleccionadas(List<String> lstResponsablePreseleccionadas) {
        this.lstResponsablePreseleccionadas = lstResponsablePreseleccionadas;
    }

    public List<String> getLstVehiculosPreseleccionadas() {
        return lstVehiculosPreseleccionadas;
    }

    public void setLstVehiculosPreseleccionadas(List<String> lstVehiculosPreseleccionadas) {
        this.lstVehiculosPreseleccionadas = lstVehiculosPreseleccionadas;
    }

}
