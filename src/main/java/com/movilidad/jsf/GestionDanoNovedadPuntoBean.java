/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ConfigFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NovedadDanoFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.NovedadTipoFacadeLocal;
import com.movilidad.ejb.VehiculoComponenteDanoFacadeLocal;
import com.movilidad.ejb.VehiculoComponenteFacadeLocal;
import com.movilidad.ejb.VehiculoDanoSeveridadFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.AccPre;
import com.movilidad.model.Empleado;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadDano;
import com.movilidad.model.NovedadTipo;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.model.Vehiculo;
import com.movilidad.model.VehiculoComponente;
import com.movilidad.model.VehiculoComponenteDano;
import com.movilidad.model.VehiculoDanoSeveridad;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "gestionDanoNovPuntBean")
@ViewScoped
public class GestionDanoNovedadPuntoBean implements Serializable {

    /**
     * Creates a new instance of GestionDanoNovedadPuntoBean
     */
    public GestionDanoNovedadPuntoBean() {
    }

    @EJB
    private NovedadDanoFacadeLocal novedadDanoEjb;
    @EJB
    private NovedadFacadeLocal novedadEjb;
    @EJB
    private VehiculoFacadeLocal vehiculoEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private VehiculoComponenteDanoFacadeLocal vehiculoComponenteDanoEjb;
    @EJB
    private VehiculoComponenteFacadeLocal vehiculoComponenteEjb;
    @EJB
    private VehiculoDanoSeveridadFacadeLocal vehiculoDanoSeveridadEjb;
    @EJB
    private NovedadTipoFacadeLocal novedadTipoEjb;
    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTipoDetEjb;

    @EJB
    private ConfigFacadeLocal configEJB;

    @Inject
    private NovedadUtilJSFManagedBean novedadUtil;

    @Inject
    private AccidenteJSF accidenteBean;
    @Inject
    private AccPreManagedBean accPreManagedBean;

    private UserExtended user = null;
    /**
     * para la edicion de daños
     */
    private List<Vehiculo> lstVehiculos;
    private List<VehiculoComponente> lstVehiculoComponente;
    private List<VehiculoDanoSeveridad> lstVehiculoDanoSeveridad;
    private List<VehiculoComponenteDano> lstVehiculoComponenteDano;

    private NovedadDano novedadDano;
    private Novedad novedad;
    private Vehiculo vehiculo;
    private VehiculoComponenteDano vehiculoComponenteDano;
    private VehiculoComponente vehiculoComponente;
    private VehiculoDanoSeveridad vehiculoDanoSeveridad;

    /**
     * para la edicion de daños y novedades
     */
    private List<Empleado> lstEmpleados;
    private Empleado empleado;

    /**
     * para la edición de novedades.
     */
    private List<NovedadTipo> lstNovedadTipos;
    private List<NovedadTipoDetalles> lstNovedadTipoDetalles;
    private NovedadTipo novedadTipo;
    private NovedadTipoDetalles novedadTipoDetalles;
    private String c_vehiculo;

    private boolean flag_prof;
    private boolean controlAccidente = false;
    private Integer i_puntosConciliados;

    @PostConstruct
    public void init() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void onRowDblClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof Vehiculo) {
            setVehiculo((Vehiculo) event.getObject());
        }
        if (event.getObject() instanceof Empleado) {
            setEmpleado((Empleado) event.getObject());
        }
        if (event.getObject() instanceof VehiculoComponenteDano) {
            setVehiculoComponenteDano((VehiculoComponenteDano) event.getObject());
        }
        if (event.getObject() instanceof VehiculoComponente) {
            setVehiculoComponente((VehiculoComponente) event.getObject());
        }
        if (event.getObject() instanceof VehiculoDanoSeveridad) {
            setVehiculoDanoSeveridad((VehiculoDanoSeveridad) event.getObject());
        }
    }

    public void editNovedadDano() {
        this.vehiculo = novedadDano.getIdVehiculo();
        this.empleado = novedadDano.getIdEmpleado();
        this.vehiculoComponenteDano = novedadDano.getIdVehiculoComponenteDano();
        this.vehiculoComponente = novedadDano.getIdVehiculoComponente();
        this.vehiculoDanoSeveridad = novedadDano.getIdVehiculoDanoSeveridad();
    }

    public void actualizar() {

        if (novedadDano.getFecha() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una fecha");
            return;
        }

        if (novedadDano.getDescripcion() == null) {
            MovilidadUtil.addErrorMessage("Debe ingresar una descripción");
            return;
        }

        if (Util.validarFecha(novedadDano.getFecha())) {
            MovilidadUtil.addAdvertenciaMessage("La Fecha de la novedad de daño debe ser igual o menor al dia de hoy");
            return;
        }

        if (empleado.getIdEmpleado() == null) {
            MovilidadUtil.updateComponent(":frmNuevoTipo:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un operador");
            return;
        }

        if (vehiculoComponente.getIdVehiculoComponente() == null) {
            MovilidadUtil.updateComponent(":frmNuevoTipo:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un componente");
            return;
        }

        if (vehiculoComponenteDano.getIdVehiculoComponenteDano() == null) {
            MovilidadUtil.updateComponent(":frmNuevoTipo:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un daño de componente");
            return;
        }

        if (vehiculoDanoSeveridad.getIdVehiculoDanoSeveridad() == null) {
            MovilidadUtil.updateComponent(":frmNuevoTipo:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar la severidad del daño");
            return;
        }

        if (vehiculo.getIdVehiculo() == null) {
            MovilidadUtil.updateComponent(":frmNuevoTipo:messages");
            MovilidadUtil.addErrorMessage("Debe seleccionar un vehículo");
            return;
        }

        novedadDano.setIdVehiculo(vehiculo);
        if (vehiculoDanoSeveridad.getIdVehiculoDanoSeveridad() != null) {
            novedadDano.setIdVehiculoDanoSeveridad(vehiculoDanoSeveridad);
        }
        if (empleado.getIdEmpleado() != null) {
            novedadDano.setIdEmpleado(empleado);
        }
        if (vehiculoComponenteDano.getIdVehiculoComponenteDano() != null) {
            novedadDano.setIdVehiculoComponenteDano(vehiculoComponenteDano);
        }
        if (vehiculoComponente.getIdVehiculoComponente() != null) {
            novedadDano.setIdVehiculoComponente(vehiculoComponente);
        }
        novedadDano.setUsername(user.getUsername());
        this.novedadDanoEjb.edit(novedadDano);

        novedadUtil.actualizarNovedadDano(novedadDano);
        MovilidadUtil.hideModal("gestion_dano_wv");
        MovilidadUtil.addSuccessMessage("Novedad de daño actualizada éxitosamente.");
    }

    /**
     * Prepara los tipos de novedades antes de modificar una novedad
     */
    public void prepareListNovedadTipo() {
        lstNovedadTipos = null;
        this.novedadTipo = new NovedadTipo();

        if (lstNovedadTipos == null) {
            lstNovedadTipos = novedadTipoEjb.obtenerTipos();
        }

        PrimeFaces.current().executeScript("PF('dtNovedadTipos').clearFilters();");
        PrimeFaces.current().ajax().update(":frmPmNovedadTipoList:dtNovedadTipo");
    }

    /**
     * Prepara los detalles del tipo seleccionado antes de modificar una novedad
     *
     */
    public void prepareListNovedadTipoDetalle() {
        lstNovedadTipoDetalles = null;
        this.novedadTipoDetalles = new NovedadTipoDetalles();
        if (novedadTipo != null) {
            lstNovedadTipoDetalles = novedadTipoDetEjb.findByTipo(novedadTipo.getIdNovedadTipo());
        }
        MovilidadUtil.openModal("PmNovedadTipoDetalleListDialog");
        MovilidadUtil.clearFilter("dtNovedadTipoDetalle");

    }

    /**
     * Evento que se dispara al seleccionar un tipo de novedad en el modal que
     * muestra los tipos de novedades, y asigna el tipo de novedad seleccionado
     * al valor para la vista
     *
     * @param event
     */
    public void onRowNovedadTipoClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof NovedadTipo) {
            setNovedadTipo((NovedadTipo) event.getObject());
        }
        this.novedadTipoDetalles = new NovedadTipoDetalles();
        this.novedadTipoDetalles.setTituloTipoNovedad("");
        MovilidadUtil.clearFilter("dtNovedadTipos");
        MovilidadUtil.updateComponent(":frmPmNovedadTipoList:dtNovedadTipo");
        MovilidadUtil.updateComponent(":frmNovedadesPm:novedad_tipo_detalle");

    }

    /**
     * Evento que se dispara al seleccionar un detalle de novedad en el modal
     * que muestra los detalles de tipos
     *
     * @param event
     */
    public void onRowNovedadTipoDetallesClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof NovedadTipoDetalles) {
            setNovedadTipoDetalles((NovedadTipoDetalles) event.getObject());
        }
        MovilidadUtil.clearFilter("dtNovedadTipoDetalle");
        MovilidadUtil.updateComponent("frmPmNovedadTipoDetalleList:dtNovedadTipoDetalles");
    }

    /**
     * Prepara la lista de empleados antes de registrar/modificar una novedad
     */
    public void prepareListEmpleados() {
        lstEmpleados = empleadoEjb.findAll();
        MovilidadUtil.clearFilter("wVPmEmpleadosListDialog");
    }

    public void prepareVehiculos() {
        lstVehiculos = vehiculoEjb.findAll();
    }

    public void prepareDanoSeveridad() {
        lstVehiculoDanoSeveridad = vehiculoDanoSeveridadEjb.findAll();
    }

    public void prepareVehiculoCte() {
        lstVehiculoComponente = vehiculoComponenteEjb.findAll();
    }

    public void prepareVehiculoComponenteDano() {
        if (vehiculoComponente.getIdVehiculoComponenteZona() == null) {
            MovilidadUtil.addErrorMessage("Seleccionar Vehículo Componente.");
            return;
        }
        lstVehiculoComponenteDano = vehiculoComponenteDanoEjb.findByCteGrupo(vehiculoComponente.getIdVehiculoComponenteZona().getIdVehiculoComponenteGrupo());
    }

    public void prepareComponenteDano() {
        if (vehiculoComponente == null) {
            MovilidadUtil.addErrorMessage("Seleccionar Vehículo Componente.");
            return;
        }
        lstVehiculoComponenteDano = vehiculoComponenteDanoEjb
                .findByCteGrupo(vehiculoComponente.getIdVehiculoComponenteZona()
                        .getIdVehiculoComponenteGrupo());

    }

    /**
     * Realiza la búsqueda de un vehículo por código
     */
    public void cargarVehiculo() {
        try {
            if (!(c_vehiculo.equals("") && c_vehiculo.isEmpty())) {
                Vehiculo vehiculo = vehiculoEjb.getVehiculoCodigo(c_vehiculo);
                if (vehiculo != null) {
                    novedad.setIdVehiculo(vehiculo);
                    MovilidadUtil.addSuccessMessage("Vehículo Cargado");
                    PrimeFaces.current().ajax().update("frmNovedadesPm:messages");
                } else {
                    MovilidadUtil.addErrorMessage("Vehículo no valido");
                    PrimeFaces.current().ajax().update("frmNovedadesPm:messages");
                    novedad.setIdVehiculo(null);
                }
            } else {
                MovilidadUtil.addErrorMessage("Vehículo no valido");
                PrimeFaces.current().ajax().update("frmNovedadesPm:messages");
                novedad.setIdVehiculo(null);
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error de sistema");
            PrimeFaces.current().ajax().update("frmNovedadesPm:messages");
        }
    }

    /**
     * Modifica registro novedad, y actualiza los datos en la tabla de
     * accidentes ( Sí la novedad a modificar tiene como tipo ACCIDENTE)
     *
     */
    public void actualizarNovedadPM() {

        if (novedadTipo.getNombreTipoNovedad() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo de novedad");
            return;
        }
        if (novedadTipoDetalles.getTituloTipoNovedad() == null || novedadTipoDetalles.getTituloTipoNovedad().equals("")) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un detalle de tipo de novedad");
            return;
        }
        if (empleado.getNombres() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un operador");
            return;
        }

        if (novedadTipoDetalles.getFechas() == 1) {
            if (novedad.getDesde() == null || novedad.getHasta() == null) {
                MovilidadUtil.addErrorMessage("Fecha Desde y Hasta son necesarias");
                return;
            }
        }

        switch (novedadTipo.getNombreTipoNovedad()) {
            case Util.DANO:
                MovilidadUtil.addErrorMessage("Debe actualizar la novedad a través del módulo: Novedades de daño");
                MovilidadUtil.updateComponent("frmNovedadesPm:messages");
                return;
        }

        novedad.setIdNovedadTipo(novedadTipo);
        novedad.setIdNovedadTipoDetalle(novedadTipoDetalles);
        novedad.setIdEmpleado(empleado);
        novedad.setProcede(0);
        novedad.setPuntosPm(0);
        novedad.setPuntosPmConciliados(0);

        novedad.setLiquidada(0);
        novedad.setUsername(user.getUsername());
        novedad.setModificado(MovilidadUtil.fechaCompletaHoy());
        if (novedad.getIdNovedadTipoDetalle().getAfectaPm() == 1) {
            if (procedeNovedad()) {
                novedad.setProcede(1);
                novedad.setPuntosPm(novedadTipoDetalles.getPuntosPm());
                procedeCociliacion(novedad);
            }
        }
        this.novedadEjb.edit(novedad);

        // ID_ACCIDENTE => id novedad tipo que corresponde a los Accidentes
        if (novedad.getIdNovedadTipo().getIdNovedadTipo() == Util.ID_ACCIDENTE) {
            accPreManagedBean.guardarAccidente(novedad);
            accidenteBean.actualizarAccidente(novedad, false);
        } else {
            if (controlAccidente) {
                if ((novedad.getIdNovedadTipo().getIdNovedadTipo() != Util.ID_ACCIDENTE)) {
                    accidenteBean.actualizarAccidente(novedad, true);
                }
            }
        }
        MovilidadUtil.hideModal("novedadesPM");
        MovilidadUtil.addSuccessMessage("Novedad  actualizada correctamente.");
    }

    /**
     *
     * Verifica si una novedad_detalle procede de forma automática al
     * agregar/modificar una novedad
     *
     * @return true si el valor es IGUAL a 1, de lo contrario false
     */
    public boolean procedeNovedad() {
        try {
            return configEJB.findByKey("nov").getValue() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Realiza la conciliación de una novedad y su asignación de puntos en el
     * programa master
     *
     * @param nov
     */
    public void procedeCociliacion(Novedad nov) {
        nov.setPuntosPmConciliados(puntoView(nov));
        nov.setProcede(1);
        if (flag_prof) {
            MovilidadUtil.addSuccessMessage("Los puntos se han agregado a la Concilicación correctamente");
            PrimeFaces.current().ajax().update("msgs");
            PrimeFaces.current().ajax().update("frmPrincipal:dtTipo");
        } else {
            PrimeFaces.current().ajax().update("msgs");
        }
        novedadEjb.edit(nov);
    }

    /**
     * Verifica si el usuario logueado es un Profesional de operaciones
     *
     * @return true si el usuario logueado es un Profesional de operaciones
     */
    boolean validarRol() {
        for (GrantedAuthority auth : user.getAuthorities()) {
//            System.out.println(auth.getAuthority());
            if (auth.getAuthority().equals("ROLE_PROFOP")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna la cantidad de puntos PM de una novedad
     *
     * @param n
     * @return puntos Programa máster
     */
    public int puntoView(Novedad n) {
        if (n == null) {
            return 0;
        }
        if (n.getIdNovedadDano() != null) {
            return n.getIdNovedadDano().getIdVehiculoDanoSeveridad().getPuntosPm();
        }
        if (n.getIdMulta() != null) {
            return n.getPuntosPm();
        }
        if (n.getIdNovedadDano() == null && n.getIdMulta() == null) {
            return n.getIdNovedadTipoDetalle().getPuntosPm();
        }

        return 0;
    }

    public void editNovedad() {
        this.novedadTipo = this.novedad.getIdNovedadTipo();
        this.novedadTipoDetalles = this.novedad.getIdNovedadTipoDetalle();
        this.empleado = this.novedad.getIdEmpleado();

        if (novedad.getIdVehiculo() != null) {
            c_vehiculo = novedad.getIdVehiculo().getCodigo();
        }
        if (novedad.getIdNovedadTipo().getIdNovedadTipo() == Util.ID_ACCIDENTE) {
            controlAccidente = true;
        }
    }

    /**
     * Asigna los puntos del Programa master conciliados a una novedad
     */
    public void aplicarPuntosPM() {
        if (i_puntosConciliados == null) {
            MovilidadUtil.addErrorMessage("Puntos Conciliados No Valido");
            return;
        }
        novedad.setPuntosPmConciliados(i_puntosConciliados);
        novedad.setProcede(1);
        novedadEjb.edit(novedad);
        MovilidadUtil.hideModal("cambiar_puntos_wv");
        MovilidadUtil.addSuccessMessage("Los puntos se han agregado a la Concilicación correctamente");
    }

    public List<Vehiculo> getLstVehiculos() {
        return lstVehiculos;
    }

    public void setLstVehiculos(List<Vehiculo> lstVehiculos) {
        this.lstVehiculos = lstVehiculos;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public VehiculoComponenteDano getVehiculoComponenteDano() {
        return vehiculoComponenteDano;
    }

    public void setVehiculoComponenteDano(VehiculoComponenteDano vehiculoComponenteDano) {
        this.vehiculoComponenteDano = vehiculoComponenteDano;
    }

    public VehiculoComponente getVehiculoComponente() {
        return vehiculoComponente;
    }

    public void setVehiculoComponente(VehiculoComponente vehiculoComponente) {
        this.vehiculoComponente = vehiculoComponente;
    }

    public VehiculoDanoSeveridad getVehiculoDanoSeveridad() {
        return vehiculoDanoSeveridad;
    }

    public void setVehiculoDanoSeveridad(VehiculoDanoSeveridad vehiculoDanoSeveridad) {
        this.vehiculoDanoSeveridad = vehiculoDanoSeveridad;
    }

    public List<VehiculoComponente> getLstVehiculoComponente() {
        return lstVehiculoComponente;
    }

    public void setLstVehiculoComponente(List<VehiculoComponente> lstVehiculoComponente) {
        this.lstVehiculoComponente = lstVehiculoComponente;
    }

    public List<VehiculoDanoSeveridad> getLstVehiculoDanoSeveridad() {
        return lstVehiculoDanoSeveridad;
    }

    public void setLstVehiculoDanoSeveridad(List<VehiculoDanoSeveridad> lstVehiculoDanoSeveridad) {
        this.lstVehiculoDanoSeveridad = lstVehiculoDanoSeveridad;
    }

    public List<VehiculoComponenteDano> getLstVehiculoComponenteDano() {
        return lstVehiculoComponenteDano;
    }

    public void setLstVehiculoComponenteDano(List<VehiculoComponenteDano> lstVehiculoComponenteDano) {
        this.lstVehiculoComponenteDano = lstVehiculoComponenteDano;
    }

    public List<Empleado> getLstEmpleados() {
        return lstEmpleados;
    }

    public void setLstEmpleados(List<Empleado> lstEmpleados) {
        this.lstEmpleados = lstEmpleados;
    }

    public NovedadDano getNovedadDano() {
        return novedadDano;
    }

    public void setNovedadDano(NovedadDano novedadDano) {
        this.novedadDano = novedadDano;
    }

    public List<NovedadTipo> getLstNovedadTipos() {
        return lstNovedadTipos;
    }

    public void setLstNovedadTipos(List<NovedadTipo> lstNovedadTipos) {
        this.lstNovedadTipos = lstNovedadTipos;
    }

    public List<NovedadTipoDetalles> getLstNovedadTipoDetalles() {
        return lstNovedadTipoDetalles;
    }

    public void setLstNovedadTipoDetalles(List<NovedadTipoDetalles> lstNovedadTipoDetalles) {
        this.lstNovedadTipoDetalles = lstNovedadTipoDetalles;
    }

    public NovedadTipo getNovedadTipo() {
        return novedadTipo;
    }

    public void setNovedadTipo(NovedadTipo novedadTipo) {
        this.novedadTipo = novedadTipo;
    }

    public NovedadTipoDetalles getNovedadTipoDetalles() {
        return novedadTipoDetalles;
    }

    public void setNovedadTipoDetalles(NovedadTipoDetalles novedadTipoDetalles) {
        this.novedadTipoDetalles = novedadTipoDetalles;
    }

    public Novedad getNovedad() {
        return novedad;
    }

    public void setNovedad(Novedad novedad) {
        this.novedad = novedad;
    }

    public String getC_vehiculo() {
        return c_vehiculo;
    }

    public void setC_vehiculo(String c_vehiculo) {
        this.c_vehiculo = c_vehiculo;
    }

    public boolean isFlag_prof() {
        return flag_prof;
    }

    public void setFlag_prof(boolean flag_prof) {
        this.flag_prof = flag_prof;
    }

    public boolean isControlAccidente() {
        return controlAccidente;
    }

    public void setControlAccidente(boolean controlAccidente) {
        this.controlAccidente = controlAccidente;
    }

    public Integer getI_puntosConciliados() {
        return i_puntosConciliados;
    }

    public void setI_puntosConciliados(Integer i_puntosConciliados) {
        this.i_puntosConciliados = i_puntosConciliados;
    }

}
