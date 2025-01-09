package com.movilidad.jsf;

import com.movilidad.ejb.NotificacionProcesosFacadeLocal;
import com.movilidad.ejb.NovedadClasificacionFacadeLocal;
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.NovedadTipoFacadeLocal;
import com.movilidad.ejb.PrgStopPointFacadeLocal;
import com.movilidad.ejb.PrgTcResponsableFacadeLocal;
import com.movilidad.ejb.SncDetalleFacadeLocal;
import com.movilidad.ejb.VehiculoTipoEstadoDetFacadeLocal;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.model.NovedadClasificacion;
import com.movilidad.model.NovedadTipo;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.model.PrgClasificacionMotivo;
import com.movilidad.model.PrgStopPoint;
import com.movilidad.model.PrgTcResponsable;
import com.movilidad.model.SncDetalle;
import com.movilidad.model.VehiculoTipoEstadoDet;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.JsfUtil;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Luis Vélez
 */
@Named(value = "novedadTipoDetBean")
@ViewScoped
public class NovedadTipoDetallesJSFManagedBean implements Serializable {

    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTipoDetEjb;
    @EJB
    private NovedadTipoFacadeLocal novedadTipoEjb;
    @EJB
    private NovedadClasificacionFacadeLocal novedadClasifEjb;
    @EJB
    private SncDetalleFacadeLocal sncEjb;
    @EJB
    private NotificacionProcesosFacadeLocal notiEjb;
    @EJB
    private VehiculoTipoEstadoDetFacadeLocal vehiculoTipoEstadoDetEjb;
    @EJB
    private PrgTcResponsableFacadeLocal prgTcRespEJB;
    @EJB
    private PrgStopPointFacadeLocal prgSPEJB;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private NovedadTipo nv;
    private NovedadTipoDetalles nvd;
    private NovedadTipoDetalles selected;
    private NovedadClasificacion novedadClasif;
    private SncDetalle sncDetalle;
    private NotificacionProcesos notifica;
    private VehiculoTipoEstadoDet tipoEstadoDet;
    private PrgStopPoint entradaStopPoint;
    private PrgStopPoint salidaStopPoint;

    private List<NovedadTipoDetalles> lista;
    private List<NovedadTipo> listaNovedadesTipo;
    private List<NovedadClasificacion> listaNovedadClasif;
    private List<SncDetalle> listaSncDetalle;
    private List<NotificacionProcesos> listaNotiProcesos;
    private List<VehiculoTipoEstadoDet> listaVehiculoTipoEstadoDetalles;
    private List<PrgTcResponsable> lstResponsables;
    private List<PrgClasificacionMotivo> lstClasificacion;
    private List<PrgStopPoint> listPrgStopPoint;

    private boolean b_fechas, b_prevencion, b_afectaPm, b_afectaProg,
            b_requiereSoporte, b_notificacion, b_reqHoras, b_AfectaGestor,
            b_incluyeReporte, b_requiereSitio, b_AfectaDisponibilidad,
            b_reqHora, b_reqVehiculo, b_mantenimiento, b_atv, b_sumaGestor,
            b_novedadNomina, b_notificaOperador;

    private int i_puntosPm;
    private int entradaSalida = 0;

    private Integer i_Responsable;
    private Integer i_Clasificacion;
    private String destino = "";
    private String entrada;
    private String salida;

    private boolean flag = false;

    @PostConstruct
    public void init() {
        if (lista == null) {
            this.lista = this.novedadTipoDetEjb.findAll();
        }
        reset();
    }

    void consultar() {
        this.lista = this.novedadTipoDetEjb.findAll();
    }

    void reset() {
        this.i_puntosPm = 0;
        b_fechas = false;
        b_prevencion = false;
        b_afectaPm = false;
        b_afectaProg = false;
        b_requiereSoporte = false;
        b_notificacion = false;
        b_afectaPm = false;
        b_reqHoras = false;
        b_AfectaGestor = false;
        b_incluyeReporte = false;
        b_requiereSitio = false;
        b_reqHora = false;
        b_reqVehiculo = false;
        b_mantenimiento = false;
        b_atv = false;
        b_sumaGestor = false;
        b_novedadNomina = false;
        b_notificaOperador = false;
        b_AfectaDisponibilidad = false;
        i_Clasificacion = null;
        i_Responsable = null;
        cargarResponsables();
    }

    public NovedadTipoDetalles prepareCreate() {
        reset();
        this.nv = new NovedadTipo();
        this.nvd = new NovedadTipoDetalles();
        this.novedadClasif = new NovedadClasificacion();
        this.sncDetalle = new SncDetalle();
        this.tipoEstadoDet = new VehiculoTipoEstadoDet();
        i_Clasificacion = null;
        i_Responsable = null;
        cargarResponsables();
        flag = false;
        return this.selected;
    }

    public void prepareUpdate() {
        nvd = this.selected;
        flag = true;
        tipoEstadoDet = selected.getIdVehiculoTipoEstadoDet();
        b_afectaPm = (nvd.getAfectaPm() == 1);
        b_afectaProg = (nvd.getAfectaProgramacion() == 1);
        b_reqHoras = (nvd.getReqHoras() == 1);
        b_AfectaGestor = (nvd.getAfectaGestor() == 1);
        b_incluyeReporte = (nvd.getIncluirReporte() == 1);
        b_requiereSitio = (nvd.getReqSitio() == 1);
        b_reqHora = (nvd.getReqHora() == 1);
        b_reqVehiculo = (nvd.getReqVehiculo() == 1);
        b_mantenimiento = (nvd.getMtto() == 1);
        b_atv = (nvd.getAtv() == 1);
        b_AfectaDisponibilidad = (nvd.getAfectaDisponibilidad() == 1);
        i_puntosPm = nvd.getPuntosPm();
        b_prevencion = (nvd.getAfectaPm() == 1);
        b_notificacion = (nvd.getNotificacion() == 1);
        b_requiereSoporte = (nvd.getRequiereSoporte() == 1);
        b_fechas = (nvd.getFechas() == 1);
        b_sumaGestor = (nvd.getSumaGestor() == 1);
        b_novedadNomina = (nvd.getNovedadNomina() == 1);
        b_notificaOperador = (nvd.getNotificaOperador() == 1);
        salidaStopPoint = nvd.getFromStop() != null ? nvd.getFromStop() : null;
        entradaStopPoint = nvd.getToStop() != null ? nvd.getToStop() : null;
        entrada = nvd.getToStop() != null ? nvd.getToStop().getName() : null;
        salida = nvd.getFromStop() != null ? nvd.getFromStop().getName() : null;
        cargarResponsables();
        i_Clasificacion = nvd.getIdPrgClasificacionMotivo() != null ? nvd.getIdPrgClasificacionMotivo().getIdPrgClasificacionMotivo() : null;
        i_Responsable = nvd.getIdPrgTcResponsable() != null ? nvd.getIdPrgTcResponsable().getIdPrgTcResponsable() : null;

        if (i_Responsable != null) {
            cargarClasificacion();
        }
    }

    public void destroy() {
//        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProductDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            lista = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void prepareListNovedadesTipo() {
        this.nv = new NovedadTipo();
        getLista();
    }

    public void onRowDblClckSelect(final SelectEvent event) {
//        System.out.println("onRowDblClckSelect");
        setNv((NovedadTipo) event.getObject());
        nvd.setIdNovedadTipo((NovedadTipo) event.getObject());
        MovilidadUtil.hideModal("NovedadTipoDetalleListDialog");
    }

    public void onRowDblClckSelectClasif(final SelectEvent event) {
//        System.out.println("onRowDblClckSelectClasif");
        setNovedadClasif((NovedadClasificacion) event.getObject());
        nvd.setIdNovedadClasificacion((NovedadClasificacion) event.getObject());
        MovilidadUtil.hideModal("NovedadClasifListDialog");
    }

    public void onRowDblClckSelectSnc(final SelectEvent event) {
//        System.out.println("onRowDblClckSelectSnc");
        setSncDetalle((SncDetalle) event.getObject());
        nvd.setIdSncDetalle((SncDetalle) event.getObject());
        MovilidadUtil.hideModal("SncListDialog");
    }

    public void onRowDblClckSelectNotifica(final SelectEvent event) {
//        System.out.println("onRowDblClckSelectNotifica");
        nvd.setIdNotificacionProcesos((NotificacionProcesos) event.getObject());
        MovilidadUtil.hideModal("NotificaListDialog");
    }

    public void onRowDblClckTipoEstadoDet(final SelectEvent event) {
//        System.out.println("onRowDblClckSelectNotifica");
        nvd.setIdVehiculoTipoEstadoDet((VehiculoTipoEstadoDet) event.getObject());
        MovilidadUtil.hideModal("TipoEstadoDetDialog");
    }

    public void onTipoNovedadChosen(SelectEvent event) {
//        NovedadTipo car = (NovedadTipo) event.getObject();
//        System.out.println(((NovedadTipo) event.getObject()).getNombreTipoNovedad());
        setNv((NovedadTipo) event.getObject());

    }

    public void onClasifNovedadChosen(SelectEvent event) {
//        NovedadTipo car = (NovedadTipo) event.getObject();
//        System.out.println(((NovedadClasificacion) event.getObject()).getNombre());
        setNovedadClasif((NovedadClasificacion) event.getObject());

    }

    public void buscaTipoNovedad() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("closable", true);
        options.put("appendTo", "@{body}");
        options.put("modal", true);
//        System.out.println("buscaTipoNovedad");
        PrimeFaces.current().dialog().openDynamic("/novedades/novedadTipoList", options, null);
    }

    public void buscaClasifNovedad() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("closable", true);
        options.put("appendTo", "@{body}");
        options.put("modal", true);
//        System.out.println("buscaClasifNovedad");
        PrimeFaces.current().dialog().openDynamic("/novedades/novedadClasifList", options, null);
    }

    public void buscaTipoNovedad2() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("closable", true);
        options.put("appendTo", "@{body}");
        options.put("modal", true);
//        System.out.println("buscaTipoNovedad2");
        PrimeFaces.current().dialog().openDynamic("/novedades/novedadTipoDetalleNew", options, null);
    }

    public void guardar() {

        if (validar()) {
            return;
        }

        nvd.setAfectaPm(b_afectaPm ? 1 : 0);
//        System.out.println("Afecta PM :" + b_afectaPm);
//        System.out.println("Puntos : " + b_puntosPm);
        nvd.setPuntosPm(b_afectaPm ? i_puntosPm : 0);
        nvd.setAfectaProgramacion(b_afectaProg ? 1 : 0);
        nvd.setPrevencionVial(b_prevencion ? 1 : 0);
        nvd.setNotificacion(b_notificacion ? 1 : 0);
        nvd.setReqHoras(b_reqHoras ? 1 : 0);
        nvd.setAfectaGestor(b_AfectaGestor ? 1 : 0);
        nvd.setIncluirReporte(b_incluyeReporte ? 1 : 0);
        nvd.setReqSitio(b_requiereSitio ? 1 : 0);
        nvd.setReqHora(b_reqHora ? 1 : 0);
        nvd.setReqVehiculo(b_reqVehiculo || b_mantenimiento ? 1 : 0);
        nvd.setMtto(b_mantenimiento ? 1 : 0);
        nvd.setAtv(b_atv ? 1 : 0);
        nvd.setSumaGestor(b_sumaGestor ? 1 : 0);
        nvd.setNovedadNomina(b_novedadNomina ? 1 : 0);
        nvd.setNotificaOperador(b_notificaOperador ? 1 : 0);
        nvd.setAfectaDisponibilidad(b_AfectaDisponibilidad ? 1 : 0);
        nvd.setRequiereSoporte(b_requiereSoporte ? 1 : 0);
        nvd.setFechas(b_fechas ? 1 : 0);
        nvd.setIdPrgTcResponsable(i_Responsable != null ? new PrgTcResponsable(i_Responsable) : null);
        nvd.setIdPrgClasificacionMotivo(i_Clasificacion != null ? new PrgClasificacionMotivo(i_Clasificacion) : null);
        nvd.setFromStop((!MovilidadUtil.stringIsEmpty(salida) && salidaStopPoint != null) ? salidaStopPoint : null);
        nvd.setToStop((!MovilidadUtil.stringIsEmpty(entrada) && entradaStopPoint != null) ? entradaStopPoint : null);
        nvd.setNombreAccesoRapido(!MovilidadUtil.stringIsEmpty(nvd.getNombreAccesoRapido()) ? nvd.getNombreAccesoRapido() : null);
        nvd.setIdVehiculoTipoEstadoDet(b_AfectaDisponibilidad ? tipoEstadoDet : null);
        nvd.setUsername(user.getUsername());
        if (!b_afectaPm) {
            nvd.setPuntosPm(0);
        }
        if (!b_AfectaGestor) {
            nvd.setIncluirReporte(0);
        }
        if (!flag) {
            nvd.setCreado(MovilidadUtil.fechaCompletaHoy());
            this.novedadTipoDetEjb.create(nvd);
        } else {
            nvd.setModificado(MovilidadUtil.fechaCompletaHoy());
            MovilidadUtil.clearFilter("novTD");
            this.novedadTipoDetEjb.edit(nvd);
        }
        MovilidadUtil.hideModal("NovedadTipoDetalleCreateDialog");
        consultar();
        MovilidadUtil.addSuccessMessage("Detalle de Novedad " + (flag ? "Actualizada." : "Registrada."));
    }

    boolean validar() {
        boolean ok = false;
        if (nvd.getIdNovedadTipo() == null) {
            MovilidadUtil.addErrorMessage("Novedad Tipo es requerida.");
            ok = true;
        }
//        if (nvd.getIdNovedadClasificacion() == null) {
//            MovilidadUtil.addErrorMessage("Clasificación novedad es requerida.");
//            ok = true;
//        }
        if (b_AfectaDisponibilidad) {
            if (tipoEstadoDet == null || tipoEstadoDet.getIdVehiculoTipoEstadoDet() == null) {
                MovilidadUtil.addErrorMessage("Tipo Estado Detalle es requerida.");
                ok = true;
            }
        }
        if (b_notificacion) {
            if (nvd.getIdNotificacionProcesos() == null) {
                MovilidadUtil.addErrorMessage("Área a notificar es requerida.");
                ok = true;
            }
        }
        return ok;
    }

    public void actualizar() {
        nvd.setUsername(user.getUsername());
        this.novedadTipoDetEjb.edit(nvd);
        consultar();
        MovilidadUtil.addSuccessMessage("Tipo de novedad actualizado.");
    }

    public void editar() {
        this.nvd = this.selected;
    }

    public void cambiarEstado() {
        this.selected.setEstadoReg(1);
        this.novedadTipoDetEjb.edit(selected);
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Estado Tipo de vehículo cambiado éxitosamente."));
    }

    public void cambiarEstadoSumaGestor() {
        if (b_fechas) {
            b_sumaGestor = true;
        }
    }

    public void addMessage() {
        String summary = b_fechas ? "Checked" : "Unchecked";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }

    public void consultarNovedaTipo() {
        listaNovedadesTipo = novedadTipoEjb.findAllEstadoReg();
        MovilidadUtil.openModal("NovedadTipoDetalleListDialog");
    }

    public void consultarSNC() {
        listaSncDetalle = sncEjb.findallEst();
        MovilidadUtil.openModal("SncListDialog");
    }

    public void consultarNovedadClasif() {
        listaNovedadClasif = novedadClasifEjb.findAllEstadoReg();
        MovilidadUtil.openModal("NovedadClasifListDialog");
    }

    public void consultarEstadoVehiculoDet() {
        listaVehiculoTipoEstadoDetalles = vehiculoTipoEstadoDetEjb.findAllByEstadoReg();
        MovilidadUtil.openModal("TipoEstadoDetDialog");
    }

    public void consultarAreaNotifica() {
        listaNotiProcesos = notiEjb.findAll(0);
        MovilidadUtil.openModal("NotificaListDialog");
    }

    public void changeReqVehiculo() {
        if (b_mantenimiento) {
            b_reqVehiculo = true;
        } else {
            b_reqVehiculo = false;
        }
    }

    public void cargarClasificacion() {
        lstClasificacion = lstClasificacionSingleton();
        Optional<PrgTcResponsable> findFirst = lstResponsables.stream().filter((obj)
                -> (obj.getIdPrgTcResponsable().equals(i_Responsable))).findFirst();
        if (findFirst.isPresent()) {
            for (PrgClasificacionMotivo s : findFirst.get().getPrgClasificacionMotivoList()) {
                if (s.getEstadoReg().equals(0)) {
                    lstClasificacion.add(s);
                }
            }
        }
    }

    /**
     * Método responsable de cargar la lista listPrgStopPoint con los puntos o
     * paradas-patios, según el texto indicado desde la vista.
     *
     * @param comp este parametro es un identificar para saber desde que
     * componente primeface se esta realizando la operación.
     *
     * Es invocado en al vista gestionPrgTc
     */
    public void findPunto(int comp) {
        entradaSalida = comp;
        if (comp == 1) {
            if (MovilidadUtil.stringIsEmpty(entrada)) {
                MovilidadUtil.addErrorMessage("Digíte una valor para Entra a.");
                return;
            }
            destino = entrada;
        } else if (comp == 2) {
            if (MovilidadUtil.stringIsEmpty(salida)) {
                MovilidadUtil.addErrorMessage("Digíte una valor para Sale de.");
                return;
            }
            destino = salida;
        }
        if (listPrgStopPoint != null) {
            listPrgStopPoint.clear();
        }

        listPrgStopPoint = prgSPEJB.findStopPointByName(destino, 0);

        if (listPrgStopPoint.isEmpty()) {
            MovilidadUtil.addErrorMessage("No hay Paradas con este nombre");
            return;
        }
        MovilidadUtil.clearFilter("tablaEntradaSalidaWV");
        PrimeFaces.current().executeScript("PF('wvEntradaSalidaListDialog').show()");
        PrimeFaces.current().ajax().update(":frmPrincipalEntradaSalidaList:tablaEntradaSalida");

    }

    public void onRowClckSelectEntradaSalida(final SelectEvent event) throws Exception {
        if (entradaSalida == 1) {
            setEntradaStopPoint((PrgStopPoint) event.getObject());
            entrada = entradaStopPoint.getName();
            MovilidadUtil.updateComponent("NovedadTipoDetalleCreateForm:txtEntra");
        } else {
            setSalidaStopPoint((PrgStopPoint) event.getObject());
            salida = salidaStopPoint.getName();
            MovilidadUtil.updateComponent("NovedadTipoDetalleCreateForm:txtSale");
        }
        MovilidadUtil.updateComponent(":frmPrincipalEntradaSalidaList:tablaEntradaSalida");
        MovilidadUtil.clearFilter("tablaEntradaSalidaWV");
        MovilidadUtil.runScript("PF('tablaEntradaSalidaWV').unselectAllRows();");
        MovilidadUtil.hideModal("wvEntradaSalidaListDialog");
    }

    private List<PrgClasificacionMotivo> lstClasificacionSingleton() {
        if (lstClasificacion != null) {
            lstClasificacion.clear();
            return lstClasificacion;
        } else {
            return new ArrayList<>();
        }
    }

    private void cargarResponsables() {
        lstResponsables = prgTcRespEJB.getPrgResponsables();
    }

    public NovedadTipoDetalles getNvd() {
        return nvd;
    }

    public void setNvd(NovedadTipoDetalles nvd) {
        this.nvd = nvd;
    }

    public NovedadTipo getNv() {
        return nv;
    }

    public void setNv(NovedadTipo nv) {
        this.nv = nv;
    }

    public List<NovedadTipoDetalles> getLista() {
        return lista;
    }

    public void setLista(List<NovedadTipoDetalles> lista) {
        this.lista = lista;
    }

    public NovedadClasificacion getNovedadClasif() {
        return novedadClasif;
    }

    public void setNovedadClasif(NovedadClasificacion novedadClasif) {
        this.novedadClasif = novedadClasif;
    }

    public boolean isB_fechas() {
        return b_fechas;
    }

    public void setB_fechas(boolean b_fechas) {
        this.b_fechas = b_fechas;
    }

    public boolean isB_prevencion() {
        return b_prevencion;
    }

    public void setB_prevencion(boolean b_prevencion) {
        this.b_prevencion = b_prevencion;
    }

    public boolean isB_afectaPm() {
        return b_afectaPm;
    }

    public void setB_afectaPm(boolean b_afectaPm) {
        this.b_afectaPm = b_afectaPm;
    }

    public int getI_puntosPm() {
        return i_puntosPm;
    }

    public void setI_puntosPm(int i_puntosPm) {
        this.i_puntosPm = i_puntosPm;
    }

    public boolean isB_afectaProg() {
        return b_afectaProg;
    }

    public void setB_afectaProg(boolean b_afectaProg) {
        this.b_afectaProg = b_afectaProg;
    }

    public boolean isB_requiereSoporte() {
        return b_requiereSoporte;
    }

    public void setB_requiereSoporte(boolean b_requiereSoporte) {
        this.b_requiereSoporte = b_requiereSoporte;
    }

    public boolean isB_notificacion() {
        return b_notificacion;
    }

    public void setB_notificacion(boolean b_notificacion) {
        this.b_notificacion = b_notificacion;
    }

    public boolean isB_reqHoras() {
        return b_reqHoras;
    }

    public void setB_reqHoras(boolean b_reqHoras) {
        this.b_reqHoras = b_reqHoras;
    }

    public boolean isB_AfectaGestor() {
        return b_AfectaGestor;
    }

    public void setB_AfectaGestor(boolean b_AfectaGestor) {
        this.b_AfectaGestor = b_AfectaGestor;
    }

    public boolean isB_incluyeReporte() {
        return b_incluyeReporte;
    }

    public void setB_incluyeReporte(boolean b_incluyeReporte) {
        this.b_incluyeReporte = b_incluyeReporte;
    }

    public boolean isB_requiereSitio() {
        return b_requiereSitio;
    }

    public void setB_requiereSitio(boolean b_requiereSitio) {
        this.b_requiereSitio = b_requiereSitio;
    }

    public boolean isB_AfectaDisponibilidad() {
        return b_AfectaDisponibilidad;
    }

    public void setB_AfectaDisponibilidad(boolean b_AfectaDisponibilidad) {
        this.b_AfectaDisponibilidad = b_AfectaDisponibilidad;
    }

    public boolean isB_reqHora() {
        return b_reqHora;
    }

    public void setB_reqHora(boolean b_reqHora) {
        this.b_reqHora = b_reqHora;
    }

    public boolean isB_reqVehiculo() {
        return b_reqVehiculo;
    }

    public void setB_reqVehiculo(boolean b_reqVehiculo) {
        this.b_reqVehiculo = b_reqVehiculo;
    }

    public boolean isB_sumaGestor() {
        return b_sumaGestor;
    }

    public void setB_sumaGestor(boolean b_sumaGestor) {
        this.b_sumaGestor = b_sumaGestor;
    }

    public NovedadTipoDetalles getSelected() {
        return selected;
    }

    public void setSelected(NovedadTipoDetalles selected) {
        this.selected = selected;
    }

    public SncDetalle getSncDetalle() {
        return sncDetalle;
    }

    public void setSncDetalle(SncDetalle sncDetalle) {
        this.sncDetalle = sncDetalle;
    }

    public NotificacionProcesos getNotifica() {
        return notifica;
    }

    public void setNotifica(NotificacionProcesos notifica) {
        this.notifica = notifica;
    }

    public List<NovedadTipo> getListaNovedadesTipo() {
        return listaNovedadesTipo;
    }

    public void setListaNovedadesTipo(List<NovedadTipo> listaNovedadesTipo) {
        this.listaNovedadesTipo = listaNovedadesTipo;
    }

    public List<NovedadClasificacion> getListaNovedadClasif() {
        return listaNovedadClasif;
    }

    public void setListaNovedadClasif(List<NovedadClasificacion> listaNovedadClasif) {
        this.listaNovedadClasif = listaNovedadClasif;
    }

    public List<SncDetalle> getListaSncDetalle() {
        return listaSncDetalle;
    }

    public void setListaSncDetalle(List<SncDetalle> listaSncDetalle) {
        this.listaSncDetalle = listaSncDetalle;
    }

    public List<NotificacionProcesos> getListaNotiProcesos() {
        return listaNotiProcesos;
    }

    public List<VehiculoTipoEstadoDet> getListaVehiculoTipoEstadoDetalles() {
        return listaVehiculoTipoEstadoDetalles;
    }

    public void setListaVehiculoTipoEstadoDetalles(List<VehiculoTipoEstadoDet> listaVehiculoTipoEstadoDetalles) {
        this.listaVehiculoTipoEstadoDetalles = listaVehiculoTipoEstadoDetalles;
    }

    public void setListaNotiProcesos(List<NotificacionProcesos> listaNotiProcesos) {
        this.listaNotiProcesos = listaNotiProcesos;
    }

    public VehiculoTipoEstadoDet getTipoEstadoDet() {
        return tipoEstadoDet;
    }

    public void setTipoEstadoDet(VehiculoTipoEstadoDet tipoEstadoDet) {
        this.tipoEstadoDet = tipoEstadoDet;
    }

    public boolean isB_mantenimiento() {
        return b_mantenimiento;
    }

    public void setB_mantenimiento(boolean b_mantenimiento) {
        this.b_mantenimiento = b_mantenimiento;
    }

    public boolean isB_atv() {
        return b_atv;
    }

    public void setB_atv(boolean b_atv) {
        this.b_atv = b_atv;
    }

    public boolean isB_novedadNomina() {
        return b_novedadNomina;
    }

    public void setB_novedadNomina(boolean b_novedadNomina) {
        this.b_novedadNomina = b_novedadNomina;
    }

    public boolean isB_notificaOperador() {
        return b_notificaOperador;
    }

    public void setB_notificaOperador(boolean b_notificaOperador) {
        this.b_notificaOperador = b_notificaOperador;
    }

    public Integer getI_Responsable() {
        return i_Responsable;
    }

    public void setI_Responsable(Integer i_Responsable) {
        this.i_Responsable = i_Responsable;
    }

    public Integer getI_Clasificacion() {
        return i_Clasificacion;
    }

    public void setI_Clasificacion(Integer i_Clasificacion) {
        this.i_Clasificacion = i_Clasificacion;
    }

    public List<PrgTcResponsable> getLstResponsables() {
        return lstResponsables;
    }

    public void setLstResponsables(List<PrgTcResponsable> lstResponsables) {
        this.lstResponsables = lstResponsables;
    }

    public List<PrgClasificacionMotivo> getLstClasificacion() {
        return lstClasificacion;
    }

    public void setLstClasificacion(List<PrgClasificacionMotivo> lstClasificacion) {
        this.lstClasificacion = lstClasificacion;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public PrgStopPoint getEntradaStopPoint() {
        return entradaStopPoint;
    }

    public void setEntradaStopPoint(PrgStopPoint entradaStopPoint) {
        this.entradaStopPoint = entradaStopPoint;
    }

    public PrgStopPoint getSalidaStopPoint() {
        return salidaStopPoint;
    }

    public void setSalidaStopPoint(PrgStopPoint salidaStopPoint) {
        this.salidaStopPoint = salidaStopPoint;
    }

    public List<PrgStopPoint> getListPrgStopPoint() {
        return listPrgStopPoint;
    }

    public void setListPrgStopPoint(List<PrgStopPoint> listPrgStopPoint) {
        this.listPrgStopPoint = listPrgStopPoint;
    }

}
