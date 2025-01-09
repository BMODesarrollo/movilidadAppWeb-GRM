/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.MttoComponenteFacadeLocal;
import com.movilidad.ejb.MttoComponenteFallaFacadeLocal;
import com.movilidad.ejb.MttoEstadoFacadeLocal;
import com.movilidad.ejb.MttoNovedadFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.ejb.PrgStopPointFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.MttoComponente;
import com.movilidad.model.MttoComponenteFalla;
import com.movilidad.model.MttoCriticidad;
import com.movilidad.model.MttoEstado;
import com.movilidad.model.MttoNovedad;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.model.PrgStopPoint;
import com.movilidad.model.PrgTc;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "novedadMttoMB")
@ViewScoped
public class NovedadMttoJFSMB implements Serializable {

    /**
     * Creates a new instance of NovedadMttoJFSMB
     */
    public NovedadMttoJFSMB() {
    }
    private UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @EJB
    private VehiculoFacadeLocal vehiculoEJB;

    @EJB
    private EmpleadoFacadeLocal emplEJB;

    @EJB
    private PrgStopPointFacadeLocal patioEJB;

    @EJB
    private PrgTcFacadeLocal prgTcEJB;

    @EJB
    private MttoComponenteFallaFacadeLocal compFallaEJB;

    @EJB
    private MttoEstadoFacadeLocal mttoEstadoEJB;

    @EJB
    private MttoNovedadFacadeLocal mttoNovEJB;

    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;
    @EJB
    private MttoComponenteFacadeLocal componenteFacadeLocal;

    private List<PrgTc> listvehiculos;
    private PrgTc tcVehiculo;
    private List<MttoNovedad> listNovedadMtto;
    private List<MttoNovedad> listNovedadMttoByFechas;
    private List<MttoNovedad> listNovedadMttoAbiertasByVehiculo;
    private List<MttoComponente> listCompontes;
    private Empleado empl;
    private MttoComponenteFalla componenteFalla;
    private int filtroV = 0;
    private int i_idPatio = 0;
    private int i_componenteFalla;
    private String componente;
    private String componenteCrit;
    private String codigoTm;
    private String observacion;
    private String observacionCierre;
    private boolean b_entrada = false;
    private boolean b_salida = false;
    private boolean b_revision = false;
    private boolean flagView = false;
    private boolean flagAbierta = false;
    private boolean flagPendiente = false;
    private boolean flagEnEjecucion = false;
    private boolean flagCerrada = false;
    private Date fecha = MovilidadUtil.fechaCompletaHoy();
    private Date desde = MovilidadUtil.sumarDias(MovilidadUtil.fechaHoy(), -1);
    private Date hasta = MovilidadUtil.fechaHoy();
    private MttoNovedad novedadMtto;
    private MttoEstado estadoAbierta = null;
    private String nombreEmpleado = "";
    private String header = "";

    private List<PrgStopPoint> listPatios;
    private List<MttoComponenteFalla> listCompFalla;

    private List<String> listValueCompFalla = new ArrayList<>();
    List<Empleado> listaEmpleados = new ArrayList<>();

    private ParamAreaUsr pau;

    @PostConstruct
    public void init() {
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
        listPatios = patioEJB.getPatios();
        listNovedadMttoByFechas = mttoNovEJB.getNovByfechas(desde, hasta);
        estadoAbierta = mttoEstadoEJB.find(ConstantsUtil.NOV_ESTADO_ABIERTO);
    }

    public void getNovMtto() {
        listNovedadMttoByFechas = mttoNovEJB.getNovByfechas(desde, hasta);
    }

    public void onTabChange(TabChangeEvent event) {
        PrimeFaces pf = PrimeFaces.current();
        if ("id_ver_novedades".equals(event.getTab().getId())) {
            getNovMtto();
            pf.ajax().update("frmNovedadMtto:tabView:tblNovMttoList");

        }
    }

    public void resetListas() {
        listValueCompFalla = new ArrayList<>();
    }

    public void onRowUnselect() {
        novedadMtto = null;
    }

    public void onRowSelect(SelectEvent event) throws ParseException {
        novedadMtto = new MttoNovedad();
        novedadMtto = (MttoNovedad) event.getObject();
        flagCerrada = novedadMtto.getIdMttoEstado().getIdMttoEstado().equals(ConstantsUtil.NOV_ESTADO_CERRADO);
    }

    public List<String> llenarCheck(List<String> lista, List<MttoComponenteFalla> listaComp) {
        for (MttoComponenteFalla compF : listaComp) {
            if (listNovedadMttoAbiertasByVehiculo != null) {
                for (MttoNovedad n : listNovedadMttoAbiertasByVehiculo) {
                    if (n.getIdMttoComponenteFalla().getIdMttoComponenteFalla().equals(compF.getIdMttoComponenteFalla())) {
                        lista.add(String.valueOf(compF.getIdMttoComponenteFalla()));
                    }
                }
            }
        }
        return lista;
    }

    public int mttoNovAbiertasByIdCompFalla(int idCompFalla) {
        if (idCompFalla != 0 && listNovedadMttoAbiertasByVehiculo != null) {
            for (MttoNovedad n : listNovedadMttoAbiertasByVehiculo) {
                if (n.getIdMttoComponenteFalla().getIdMttoComponenteFalla() == idCompFalla) {
                    return 1;
                }
            }
        }
        return 0;
    }

    public int mttoNovAbiertasByIdComp(int idComp) {
        if (idComp != 0 && listNovedadMttoAbiertasByVehiculo != null) {
            for (MttoNovedad n : listNovedadMttoAbiertasByVehiculo) {
                if (n.getIdMttoComponente().getIdMttoComponente() == idComp) {
                    return 1;
                }
            }
        }
        return 0;
    }

    public String toString(Integer id) {
        return Integer.toString(id);
    }

    public void onRowlClckSelect(final SelectEvent event) throws Exception {
        setNovedadMtto((MttoNovedad) event.getObject());
        flagView = true;

    }

    @Transactional
    public void guardarNovMtto() {
        if (listNovedadMtto.isEmpty()) {
            MovilidadUtil.addAdvertenciaMessage("No hay noveades para guardar");
            return;
        }
        for (MttoNovedad n : listNovedadMtto) {
            n.setCreado(MovilidadUtil.fechaCompletaHoy());
            n.setUsername(user.getUsername());
            mttoNovEJB.create(n);
        }
        MovilidadUtil.addSuccessMessage("Se agregaron las noveades correctamente.");
        PrimeFaces.current().executeScript("PF('dlgNovMttoWV').hide()");
        tcVehiculo = null;
        observacion = "";
        listNovedadMtto = new ArrayList<>();
        resetListas();
    }

    public void listarComponentes() {
    }

    public void listarComponentesCrit() {
    }

    public void onRowDblClckSelect(final SelectEvent event) throws Exception {
        if (empl == null) {
            empl = new Empleado();
        }
        PrimeFaces current = PrimeFaces.current();
        setEmpl((Empleado) event.getObject());
        nombreEmpleado = empl.getIdentificacion() + " " + empl.getNombres() + " " + empl.getApellidos();
        MovilidadUtil.addSuccessMessage("Empleado Cargado.");
        current.ajax().update("cerrar_novedad_form:codigo_operador");
        current.ajax().update("cerrar_novedad_form:cerrar_novedad_msgs");

        current.executeScript("PF('empleado_list_wv').hide();");
    }

    public void findEmplActivos() {
        listaEmpleados.clear();
        if (pau == null) {
            MovilidadUtil.addErrorMessage("Aun no está asignado a en un área");
            return;
        }
        if (pau.getIdParamArea().getParamAreaCargoList() == null) {
            MovilidadUtil.addErrorMessage("Aun no están cargados los cargos en el área");
            return;
        }
        if (pau.getIdParamArea().getParamAreaCargoList().isEmpty()) {
            MovilidadUtil.addErrorMessage("Aun no están cargados los cargos en el área");
            return;
        }
        listaEmpleados = emplEJB.getEmpledosByIdArea(pau.getIdParamArea().getIdParamArea(), 0);
        if (listaEmpleados.isEmpty()) {
            MovilidadUtil.addErrorMessage("Aun no hay empleado activos en el area");
            return;
        } else {
            PrimeFaces.current().executeScript("PF('empleado_list_wv').show()");
            PrimeFaces.current().ajax().update("formEmpleados:tabla");
        }

    }

    public void marcar(int op) {
        if (op != 0) {
            listValueCompFalla.clear();
        }
    }

    public void onCambiarEstado(int opc) {
        if (novedadMtto == null) {
            return;
        }
//        if (novedadMtto.getIdMttoEstado().getIdMttoEstado().equals(MovilidadUtil.NOV_ESTADO_CERRADO)) {
//            MovilidadUtil.addAdvertenciaMessage("La novedad ya ha sido cerrada.");
//            return;
//        }

        if (opc == 1) {
            novedadMtto.setIdMttoEstado(mttoEstadoEJB.find(ConstantsUtil.NOV_ESTADO_ABIERTO));
        }
        if (opc == 2) {
            novedadMtto.setIdMttoEstado(mttoEstadoEJB.find(ConstantsUtil.NOV_ESTADO_EN_EJECUCION));
        }
        if (opc == 3) {
            novedadMtto.setIdMttoEstado(mttoEstadoEJB.find(ConstantsUtil.NOV_ESTADO_PENDIENTE));
        }
        if (opc == 4) {
            observacionCierre = novedadMtto.getObservacionesCierre();
            PrimeFaces.current().executeScript("PF('cerrar_novedad_wv').show();");
        } else {
            novedadMtto.setUserCierra(null);
            novedadMtto.setFechaCierre(null);
            novedadMtto.setCerrado(0);
            mttoNovEJB.edit(novedadMtto);
            getNovMtto();
            MovilidadUtil.addSuccessMessage("Acción finalizada correctamente.");
        }
    }

    public List<Object> estados() {
        List<Object> aux_list = new ArrayList<>();
        for (MttoNovedad e : listNovedadMttoByFechas) {
            aux_list.add(estado(e.getIdMttoEstado()));
        }
        aux_list = aux_list.stream().distinct().collect(Collectors.toList());
        return aux_list;
    }

    public List<Object> criticidades() {
        List<Object> aux_list = new ArrayList<>();
        for (MttoNovedad c : listNovedadMttoByFechas) {
            aux_list.add(criticidad(c.getIdMttoComponenteFalla().getIdMttoCriticidad()));
        }
        aux_list = aux_list.stream().distinct().collect(Collectors.toList());
        return aux_list;
    }

    public String estado(MttoEstado estado) {
        if (estado == null) {
            return "N/A";
        }
        String master = "";
        try {
            master = estado.getDescripcion();
        } catch (Exception e) {
            return "N/A";
        }

        return master;
    }

    public String criticidad(MttoCriticidad criticidad) {
        if (criticidad == null) {
            return "N/A";
        }
        String master = "";
        try {
            master = criticidad.getDescripcion();
        } catch (Exception e) {
            return "N/A";
        }

        return master;
    }

    public void guardarCierreNovedad() {

        if (empl == null) {
            MovilidadUtil.addErrorMessage("Técnico Asignado es requerido.");
            return;
        }

        if (novedadMtto.getOrdenTrabajo() == null || (novedadMtto.getOrdenTrabajo() != null
                && novedadMtto.getOrdenTrabajo().replaceAll("\\s", "").isEmpty())) {
            MovilidadUtil.addErrorMessage("Digite Orden de trabajo.");
            return;
        }
        if (observacionCierre == null || (observacionCierre != null
                && observacionCierre.replaceAll("\\s", "").isEmpty())) {
            MovilidadUtil.addErrorMessage("Digite la observación de cierre.");
            return;
        }
        novedadMtto.setIdMttoEstado(mttoEstadoEJB.find(ConstantsUtil.NOV_ESTADO_CERRADO));

        novedadMtto.setIdEmpleadoResponsable(empl);
        novedadMtto.setCerrado(1);
        novedadMtto.setFechaCierre(MovilidadUtil.fechaCompletaHoy());
        novedadMtto.setUserCierra(user.getUsername());
        novedadMtto.setObservacionesCierre(observacionCierre);
        mttoNovEJB.edit(novedadMtto);
        PrimeFaces.current().executeScript("PF('cerrar_novedad_wv').hide();");

        getNovMtto();
        MovilidadUtil.addSuccessMessage("Acción finalizada correctamente.");

    }

    public void onDeleteNovMtto(int index) {
        MttoNovedad get = listNovedadMtto.get(index);
        onEliminarListaPadre(get.getIdMttoComponenteFalla().getIdMttoComponenteFalla());
        listNovedadMtto.remove(index);
    }

    public void onEliminarListaPadre(int idComponenteFalla) {
        recorreListaAndEliminar(listValueCompFalla, idComponenteFalla);
    }

    public boolean recorreListaAndEliminar(List<String> lista, int valor) {
        for (String v : lista) {
            if (v.equals(String.valueOf(valor))) {
                lista.remove(v);
                return true;
            }
        }
        return false;
    }

    public void addNovedad(int op) {
        if (!listValueCompFalla.isEmpty()) {
            for (String s : listValueCompFalla) {
                listNovedadMtto.add(addNoveadadMtto(s));
            }
        }

    }

    public MttoNovedad addNoveadadMtto(String s) {
        MttoNovedad m = new MttoNovedad();
        m.setFecha(fecha);
        m.setIdEmpleado(tcVehiculo.getIdEmpleado());
        m.setIdVehiculo(tcVehiculo.getIdVehiculo());
        MttoComponenteFalla cf = compFallaEJB.find(Integer.parseInt(s));
        m.setIdMttoComponente(cf.getIdMttoComponente());
        m.setIdMttoComponenteFalla(cf);
        m.setObservaciones(observacion);
        m.setEstadoRegistro(0);
        m.setIdMttoEstado(estadoAbierta);
        return m;
    }

    public void entrada() {
        listvehiculos = prgTcEJB.findEntradasMtto(fecha, i_idPatio, filtroV);
        for (PrgTc tc : listvehiculos) {
            tc.setEstadoReg(0);
        }
        Collections.sort(listvehiculos);
        b_entrada = true;
        b_salida = b_revision = false;
    }

    public void salida() {
        listvehiculos = prgTcEJB.findSalidaMtto(fecha, i_idPatio, filtroV);
        for (PrgTc tc : listvehiculos) {
            tc.setEstadoReg(1);
        }
        Collections.sort(listvehiculos);
        b_salida = true;
        b_entrada = b_revision = false;
    }

    public void revision() {
        List<Vehiculo> listaVehiculos;
        if (filtroV == 0) {
            listaVehiculos = vehiculoEJB.getVehiclosActivo();
        } else {
            listaVehiculos = vehiculoEJB.getVehiclosByType(filtroV);
        }
//        List<PrgTc> listaEntradas = prgTcEJB.findEntradasMtto(fecha, i_idPatio, filtroV);
//        for (PrgTc tc : listaEntradas) {
//            tc.setEstadoReg(0);
//        }
//        List<PrgTc> listaSalidas = prgTcEJB.findSalidaMtto(fecha, i_idPatio, filtroV);
//        for (PrgTc tc : listaSalidas) {
//            tc.setEstadoReg(1);
//        }
        listvehiculos = new ArrayList<>();
        PrgTc tc;
        for (Vehiculo v : listaVehiculos) {
            tc = new PrgTc();
            tc.setIdVehiculo(v);
            tc.setEstadoReg(2);
            listvehiculos.add(tc);
        }
//        if (!listaEntradas.isEmpty()) {
//            listvehiculos.addAll(listaEntradas);
//        }
//        if (!listaSalidas.isEmpty()) {
//            listvehiculos.addAll(listaSalidas);
//        }
        Collections.sort(listvehiculos);
        b_revision = true;
        b_entrada = b_salida = false;
    }

    public void filtrar() {
        if (b_entrada) {
            entrada();
            return;
        }
        if (b_salida) {
            salida();
            return;
        }
        if (b_revision) {
            revision();
        }
    }

    public List<PrgTc> getListvehiculos() {
        return listvehiculos;
    }

    public void getComponentesView(MttoComponente param) {
        header = param.getDescripcion();

        listCompFalla = compFallaEJB.getCompFallaByIdComp(param.getIdMttoComponente());
        llenarCheck(listValueCompFalla, listCompFalla);

    }

    public void setListvehiculos(List<PrgTc> listvehiculos) {
        this.listvehiculos = listvehiculos;
    }

    public void createNovedad(int index) {
        tcVehiculo = listvehiculos.get(index);
        if (tcVehiculo.getIdVehiculo() == null) {
            MovilidadUtil.addErrorMessage("No hay vehículo asignado.");
            return;
        }
        listValueCompFalla.clear();
        listCompontes = componenteFacadeLocal.findAll();
        PrimeFaces.current().executeScript("PF('dlgNovMttoWV').show()");
        listNovedadMttoAbiertasByVehiculo = mttoNovEJB.getMttoNovAbiertasByVehiculo(tcVehiculo.getIdVehiculo().getIdVehiculo());
        listNovedadMtto = new ArrayList<>();
    }

    public PrgTc getTcVehiculo() {
        return tcVehiculo;
    }

    public void setTcVehiculo(PrgTc tcVehiculo) {
        this.tcVehiculo = tcVehiculo;
    }

    public int getFiltroV() {
        return filtroV;
    }

    public boolean tipo(PrgTc tc) {
        return tc.getIdVehiculo().getIdVehiculoTipo().getIdVehiculoTipo() == 1;
    }

    public void setFiltroV(int filtroV) {
        this.filtroV = filtroV;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getComponenteCrit() {
        return componenteCrit;
    }

    public void setComponenteCrit(String componenteCrit) {
        this.componenteCrit = componenteCrit;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodigoTm() {
        return codigoTm;
    }

    public void setCodigoTm(String codigoTm) {
        this.codigoTm = codigoTm;
    }

    public List<PrgStopPoint> getListPatios() {
        return listPatios;
    }

    public void setListPatios(List<PrgStopPoint> listPatios) {
        this.listPatios = listPatios;
    }

    public int getI_idPatio() {
        return i_idPatio;
    }

    public void setI_idPatio(int i_idPatio) {
        this.i_idPatio = i_idPatio;
    }

    public List<MttoNovedad> getListNovedadMtto() {
        return listNovedadMtto;
    }

    public void setListNovedadMtto(List<MttoNovedad> listNovedadMtto) {
        this.listNovedadMtto = listNovedadMtto;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public MttoComponenteFalla getComponenteFalla() {
        return componenteFalla;
    }

    public void setComponenteFalla(MttoComponenteFalla componenteFalla) {
        this.componenteFalla = componenteFalla;
    }

    public int getI_componenteFalla() {
        return i_componenteFalla;
    }

    public void setI_componenteFalla(int i_componenteFalla) {
        this.i_componenteFalla = i_componenteFalla;
    }

    public List<MttoNovedad> getListNovedadMttoByFechas() {
        return listNovedadMttoByFechas;
    }

    public void setListNovedadMttoByFechas(List<MttoNovedad> listNovedadMttoByFechas) {
        this.listNovedadMttoByFechas = listNovedadMttoByFechas;
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

    public MttoNovedad getNovedadMtto() {
        return novedadMtto;
    }

    public void setNovedadMtto(MttoNovedad novedadMtto) {
        this.novedadMtto = novedadMtto;
    }

    public boolean isFlagView() {
        return flagView;
    }

    public void setFlagView(boolean flagView) {
        this.flagView = flagView;
    }

    public boolean isFlagAbierta() {
        return flagAbierta;
    }

    public void setFlagAbierta(boolean flagAbierta) {
        this.flagAbierta = flagAbierta;
    }

    public boolean isFlagPendiente() {
        return flagPendiente;
    }

    public void setFlagPendiente(boolean flagPendiente) {
        this.flagPendiente = flagPendiente;
    }

    public boolean isFlagEnEjecucion() {
        return flagEnEjecucion;
    }

    public void setFlagEnEjecucion(boolean flagEnEjecucion) {
        this.flagEnEjecucion = flagEnEjecucion;
    }

    public boolean isFlagCerrada() {
        return flagCerrada;
    }

    public void setFlagCerrada(boolean flagCerrada) {
        this.flagCerrada = flagCerrada;
    }

    public String getObservacionCierre() {
        return observacionCierre;
    }

    public void setObservacionCierre(String observacionCierre) {
        this.observacionCierre = observacionCierre;
    }

    public Empleado getEmpl() {
        return empl;
    }

    public void setEmpl(Empleado empl) {
        this.empl = empl;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public List<MttoComponente> getListCompontes() {
        return listCompontes;
    }

    public void setListCompontes(List<MttoComponente> listCompontes) {
        this.listCompontes = listCompontes;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public List<MttoComponenteFalla> getListCompFalla() {
        return listCompFalla;
    }

    public void setListCompFalla(List<MttoComponenteFalla> listCompFalla) {
        this.listCompFalla = listCompFalla;
    }

    public List<String> getListValueCompFalla() {
        return listValueCompFalla;
    }

    public void setListValueCompFalla(List<String> listValueCompFalla) {
        this.listValueCompFalla = listValueCompFalla;
    }

}
