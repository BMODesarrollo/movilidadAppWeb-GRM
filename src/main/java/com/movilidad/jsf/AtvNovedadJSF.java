/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AtvCostoServicioFacadeLocal;
import com.movilidad.ejb.AtvEvidenciaFacadeLocal;
import com.movilidad.ejb.AtvLocationFacadeLocal;
import com.movilidad.ejb.AtvNovedadDocumentoFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.model.AtvCostoServicio;
import com.movilidad.model.AtvEvidencia;
import com.movilidad.model.AtvLocation;
import com.movilidad.model.AtvLocationSugerida;
import com.movilidad.model.AtvNovedadDocumento;
import com.movilidad.model.Novedad;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author soluciones-it
 */
@Named(value = "atvNovedadJSF")
@ViewScoped
public class AtvNovedadJSF implements Serializable {

    @EJB
    private NovedadFacadeLocal novedadEJB;
    @EJB
    private AtvEvidenciaFacadeLocal atvEvidenciaFacadeLocal;
    @EJB
    private AtvLocationFacadeLocal atvLocationFacadeLocal;
    @EJB
    private AtvNovedadDocumentoFacadeLocal atvNovedadDocumentoFacadeLocal;
    @EJB
    private AtvCostoServicioFacadeLocal atvCostoServicioFacadeLocal;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<Novedad> listNovedadATV;
    private Novedad novedadSelect;

    private Date desde;
    private Date hasta;
    private int idGopUF;
    private Map<String, AtvCostoServicio> mapAtvCostoServicio;

    // map
    private MapModel modelMap;
    private String centerMap;
    private int opcionMap;
    private String zoomMap;

    // ubicacion recorrido real
    private Date tiempoUbicacion;
    private Integer holgura;
    private List<AtvLocation> listAtvLocation;
    private AtvLocationSugerida atvLocationSugerida;
    private List<AtvLocationSugerida> atvLocSugeridaList;

    // documento atv
    private UploadedFile file;
    private AtvNovedadDocumento atvNovDoc;

    // evidencias atv
    private List<AtvEvidencia> listEvidencia;
    private AtvEvidencia atvEvidencia;

    // OPCIONES DE ESCRITURA SOLO PARA EL ROL DE MANTENIMIENTO
    private boolean isUserMtto;

    UserExtended user;

    /**
     * Creates a new instance of AtvNovedadJSF
     */
    public AtvNovedadJSF() {

    }

    @PostConstruct
    public void init() {
        desde = new Date();
        hasta = new Date();
        tiempoUbicacion = new Date();
        mapAtvCostoServicio = new HashMap<>();
        holgura = 0;
        listEvidencia = new ArrayList<>();
        consultarNov();
        onCenterMapa(null);
        opcionMap = 0;
        zoomMap = "18";
        isUserMtto = false;
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        validUserMtto();
    }

    void validUserMtto() {
        isUserMtto = user.getAuthorities().stream().anyMatch(rol -> {
            return rol.getAuthority().contains("MTTO");
        });
    }

    public void consultarNov() {
        cargarUF();
        listNovedadATV = novedadEJB.findNovsAfectaATV(desde, hasta, idGopUF);
        cargarMapCostoServicio();
    }

    void cargarMapCostoServicio() {
        List<AtvCostoServicio> listCosServ = atvCostoServicioFacadeLocal.findAllByFechas(desde, hasta);
        mapAtvCostoServicio.clear();
        listCosServ.forEach(cs -> {
            mapAtvCostoServicio.put(cs.getIdAtvPrestador().getIdAtvPrestador() + "-" + cs.getIdVehiculoTipo().getIdVehiculoTipo(), cs);
        });
    }

    /**
     * Permite consultar las evidencias cargadas por el carro taller de una
     * novedad
     *
     */
    public void consularAtvEvidenciaPorNovedad() {
        if (novedadSelect == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una novedad");
            return;
        }
        listEvidencia = atvEvidenciaFacadeLocal.findByIdNovedad(novedadSelect.getIdNovedad());
    }

    public Integer findCostoParam(Novedad nov) {
        try {
            AtvCostoServicio acs = mapAtvCostoServicio.get(nov
                    .getIdAtvVehiculosAtencion()
                    .getIdAtvPrestador()
                    .getIdAtvPrestador()
                    + "-"
                    + nov
                            .getIdVehiculo()
                            .getIdVehiculoTipo()
                            .getIdVehiculoTipo());
            return acs == null ? null : acs.getCosto();
        } catch (Exception e) {
            return null;
        }
    }

    /*
    * Para la gestion de documentos de atv documento
     */
    public void prepareGuardarNovDoc() {
        atvNovDoc = new AtvNovedadDocumento();
    }

    public void eliminarNovDoc(AtvNovedadDocumento atvNovDoc) {
        atvNovDoc.setEstadoReg(1);
        atvNovDoc.setUsername(user.getUsername());
        atvNovDoc.setModificado(new Date());
        atvNovedadDocumentoFacadeLocal.edit(atvNovDoc);
        MovilidadUtil.addSuccessMessage("Documento eliminado con éxito");
    }

    public void guardarDocumentoAtv() {
        if (novedadSelect == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una novedad");
            return;
        }
        if (file == null) {
            MovilidadUtil.addErrorMessage("Debe cargar un documento");
            return;
        }
        if (atvNovDoc == null) {
            MovilidadUtil.addErrorMessage("Error");
            return;
        }
        Date d = new Date();
        atvNovDoc.setIdNovedad(novedadSelect);
        atvNovDoc.setCreado(d);
        atvNovDoc.setModificado(d);
        atvNovDoc.setEstadoReg(0);
        atvNovDoc.setUsername(user.getUsername());
        String path = Util.saveFile(file, novedadSelect.getIdNovedad(), ConstantsUtil.KEY_DIR_NOVEDAD_ATV);
        atvNovDoc.setPathDocumento(path);
        atvNovedadDocumentoFacadeLocal.create(atvNovDoc);
        file = null;
        atvNovDoc = null;
        MovilidadUtil.addSuccessMessage("Documento cargado con éxito");
    }

    public List<AtvNovedadDocumento> consultarAtvNovedadDocumentos() {
        if (novedadSelect == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una novedad");
            return null;
        }
        return atvNovedadDocumentoFacadeLocal.findAllByIdNovedadAndActivos(novedadSelect.getIdNovedad());
    }

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        MovilidadUtil.hideModal("documentoDlg");
        MovilidadUtil.addSuccessMessage("Documento cargado con éxito");
    }

    /*
    * Para la liquidacion y aprobacion de pagos
     */
    public void liquidarNovedadAtv() throws ParseException {
        cargarUF();
        if (idGopUF == 0) {
            MovilidadUtil.addErrorMessage("Las liquidaciones se deben realizar por unidad funcional, debe seleccionar una");
            return;
        }
        if (MovilidadUtil.fechasIgualMenorMayor(desde, hasta, false) == 2) {
            MovilidadUtil.addErrorMessage("Fecha desde no puede mayor a fecha hasta");
            return;
        }
        Date d = MovilidadUtil.fechaHoy();
        if (MovilidadUtil.fechasIgualMenorMayor(desde, d, false) != 0) {
            MovilidadUtil.addErrorMessage("Fecha desde no puede ser igual o mayor a la actual");
            return;
        }
        if (MovilidadUtil.fechasIgualMenorMayor(hasta, d, false) != 0) {
            MovilidadUtil.addErrorMessage("Fecha hasta no puede ser igual o mayor a la actual");
            return;
        }
        String s1 = Util.dateFormatMM(desde);
        String s2 = Util.dateFormatMM(hasta);

        if (!s1.equals(s2)) {
            MovilidadUtil.addErrorMessage("No esta permitido liquidar fechas "
                    + "de meses distintos, se debe liquidar masivo solo en rangos del mismo mes");
            return;
        }
        novedadEJB.liquidarNovedadAtvByIdGopUFAndFechas(idGopUF, desde, hasta);
        MovilidadUtil.addSuccessMessage("Proceso realizado con éxito");
    }

    /**
     * Para aceptar (0) / rechazar (1) el pago del servicio, solo se puede hacer
     * si la novedad no está liquidada
     *
     */
    public void guardarGestionPago() {
        Novedad nov = novedadEJB.find(novedadSelect.getIdNovedad());
        if (nov.getLiquidadoAtv() != null && nov.getLiquidadoAtv().equals(1)) {
            MovilidadUtil.addErrorMessage("Novedad ha sido liquidada");
            return;
        }
        if (nov.getCierreAppAtv() != null && nov.getCierreAppAtv().equals(0)) {
            MovilidadUtil.addErrorMessage("Novedad no ha sido finalizada por parte del vehículo de atención en vía");
            return;
        }
        if (nov.getFechaRecibidoAtv() == null) {
            MovilidadUtil.addErrorMessage("Novedad no ha sido recibida por parte del técnico de patio");
            return;
        }
//        if (novedadSelect.getCostoLiquidadoAtv() != null) {
//            
//            novedadSelect.setAceptarPagoAtv(0);
//        }
        // no aprobado para pago
        if (novedadSelect.getAceptarPagoAtv().equals(1)) {
            novedadSelect.setCostoLiquidadoAtv(null);
        }
        novedadSelect.setLiquidadoAtv(1);
        novedadEJB.edit(novedadSelect);
        MovilidadUtil.addSuccessMessage("Novedad ha sido actualizada con éxito");
        MovilidadUtil.hideModal("modalDlg");
    }

    // ------------------------------------------------
    /**
     * Para realizar la buqueda de la informacion que se quiere realizar.
     *
     * 1 para realizar la busqueda general (ruta sugerida, ruta real). 2 para
     * realizar la busqueda que permite donde estuvo el vehículo atencion en el
     * momento de la fecha que se suministra. 3 para realizar la busqueda ruta
     * sugerida. 4 para realizar la busqueda ruta real
     *
     * @param op Int
     */
    public void construirModelMap(int op) {
        modelMap = new DefaultMapModel();
        reset();
//        LatLng coord1 = new LatLng(36.879466, 30.667648);
        opcionMap = op;
        if (op == 1) {
            if (consularUbicacionRecorridoReal()) {
                return;
            }
            if (consultarUbicacionSugerida()) {
                return;
            }
            construirRutaReal();
            construirRutaSugerida();
        }
        if (op == 2) {
            if (consularUbicacionRecorridoRealByFecha()) {
                return;
            }
            construirRutaReal();
        }
        if (op == 3) {
            if (consultarUbicacionSugerida()) {
                return;
            }
            construirRutaSugerida();
        }
        if (op == 4) {
            onActualizarRutaReal();
        }
    }

    // solo valido cuando se visualice la ruta real solamente
    public void onActualizarRutaReal() {
        if (opcionMap == 4) {
            if (consularUbicacionRecorridoReal()) {
                return;
            }
            construirRutaReal();
            AtvLocation startLoc = listAtvLocation.get(0);
            AtvLocation endLoc = listAtvLocation.get(listAtvLocation.size() - 1);
            LatLng latLngStart = new LatLng(startLoc.getLatitud(), startLoc.getLongitud());
            LatLng latLngEnd = new LatLng(endLoc.getLatitud(), endLoc.getLongitud());
            modelMap.addOverlay(new Marker(latLngStart,
                    "Ubicación Actual", null,
                    "http://www.google.com/mapfiles/dd-start.png"));
            modelMap.addOverlay(new Marker(latLngEnd,
                    "Punto de partida", null,
                    "http://maps.google.com/mapfiles/arrow.png"));
            onCenterMapa(endLoc.getLatitud().toString() + "," + endLoc.getLongitud().toString());
            MovilidadUtil.updateComponent("gmapAtv");
        }
    }

    private boolean consularUbicacionRecorridoRealByFecha() {
        if (novedadSelect == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una novedad");
            resetUbicacionReal();
            return true;
        }
        Date inicio = MovilidadUtil.sumarMinutos(tiempoUbicacion, (holgura * -1));
        Date fin = MovilidadUtil.sumarMinutos(tiempoUbicacion, holgura);
        listAtvLocation = atvLocationFacadeLocal
                .findByIdNovedadAndInicioBetweenFin(novedadSelect.getIdNovedad(), inicio, fin);
        if (listAtvLocation.isEmpty()) {
            MovilidadUtil.addErrorMessage("Atención no cuenta con recorrido real");
            resetUbicacionReal();
            return true;
        }
        return false;
    }

    private boolean consultarUbicacionSugerida() {
        if (novedadSelect == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una novedad");
            resetUbicacionSugerida();
            return true;
        }
        atvLocSugeridaList = novedadSelect.getAtvLocationSugeridaList();
        if (atvLocSugeridaList == null) {
            MovilidadUtil.addErrorMessage("Atención no cuenta con ruta sugerida");
            resetUbicacionSugerida();
            return true;
        }
        if (atvLocSugeridaList.isEmpty()) {
            MovilidadUtil.addErrorMessage("Atención no cuenta con ruta sugerida");
            resetUbicacionSugerida();
            return true;
        }
        return false;
    }

    private boolean consularUbicacionRecorridoReal() {
        if (novedadSelect == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una novedad");
            resetUbicacionSugerida();
            return true;
        }
        listAtvLocation = novedadEJB.find(novedadSelect.getIdNovedad()).getAtvLocationList();
        if (listAtvLocation.isEmpty()) {
            MovilidadUtil.addErrorMessage("Atención no cuenta con ruta sugerida");
            resetUbicacionReal();
            return true;
        }
        return false;
    }

    void construirRutaReal() {

        // recorrido real
        List<LatLng> listLatLng = listAtvLocation.stream().map(lr -> {
            return new LatLng(lr.getLatitud(), lr.getLongitud());
        }).collect(Collectors.toList());
        Polyline polyReal = new Polyline();
        polyReal.setPaths(listLatLng);
        polyReal.setStrokeWeight(8);
        polyReal.setStrokeColor("#FF9900");
        polyReal.setStrokeOpacity(0.7);
        modelMap.addOverlay(polyReal);
        LatLng get = listLatLng.get(0);
        onCenterMapa(String
                .valueOf(get.getLat())
                .concat(",")
                .concat(String
                        .valueOf(get.getLng())));
        construirMarkerEstados();
    }

    void construirRutaSugerida() {
        Supplier<Stream<AtvLocationSugerida>> ssa = () -> atvLocSugeridaList.stream();
        Predicate<AtvLocationSugerida> pdct = als -> als.getTipoRuta().equals(0);
        AtvLocationSugerida atvLocReccInicial = ssa.get().filter(pdct).findFirst().orElse(null);
        AtvLocationSugerida atvLocReccFinal = ssa.get().filter(pdct.negate()).findFirst().orElse(null);

        if (atvLocReccInicial == null) {
            MovilidadUtil.addErrorMessage("No se han guardado las rutas sugeridad en el sistema");
            return;
        }

        // dibujar el recorrido inicial
        Polyline polySugBq = new Polyline();
        // recorrido sugerida
        polySugBq.setPaths(atvLocReccInicial
                .getAtvLocationSugeridaDetList()
                .stream().map(ls -> {
                    return new LatLng(ls.getLatitud(), ls.getLongitud());
                }).collect(Collectors.toList()));
        polySugBq.setStrokeWeight(8);
        polySugBq.setStrokeColor("#338AFF");
        polySugBq.setStrokeOpacity(0.7);

        // informacion de la ruta sugerida
        // punto de inicio
        // solo para mostrar en el mapa
        atvLocReccInicial.setUsername("Ruta Sugerida Dest. Inicial");
        modelMap.addOverlay(new Marker(new LatLng(atvLocReccInicial.getLatitudStart(),
                atvLocReccInicial.getLongitudStart()),
                "Ruta Sugerida Dest. Inicial", atvLocReccInicial,
                "https://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
        // center map, punto de inciio
        onCenterMapa(atvLocReccInicial.getLatitudStart().toString() + "," + atvLocReccInicial.getLongitudStart().toString());
        // punto fin
        modelMap.addOverlay(new Marker(new LatLng(atvLocReccInicial.getLatitudEnd(),
                atvLocReccInicial.getLongitudEnd()),
                "Servicio Final/Recodida", atvLocReccInicial,
                "https://maps.google.com/mapfiles/ms/micons/yellow-dot.png"));
        // agregar al modelMap
        modelMap.addOverlay(polySugBq);

        if (atvLocReccFinal != null) {
            // dibujar el recorrido final
            Polyline polySugFn = new Polyline();
            // recorrido sugerida
            polySugFn.setPaths(atvLocReccFinal
                    .getAtvLocationSugeridaDetList()
                    .stream().map(ls -> {
                        return new LatLng(ls.getLatitud(), ls.getLongitud());
                    }).collect(Collectors.toList()));
            polySugFn.setStrokeWeight(8);
            polySugFn.setStrokeColor("#5BFF33");
            polySugFn.setStrokeOpacity(0.7);

            // punto inicial serial el final del recorrido inicial
            // punto fin
            // solo para mostrar en el mapa
            atvLocReccFinal.setUsername("Ruta Sugerida Dest. Final");
            modelMap.addOverlay(new Marker(new LatLng(atvLocReccFinal.getLatitudEnd(),
                    atvLocReccFinal.getLongitudEnd()),
                    "Ruta Sugerida Dest. Final", atvLocReccFinal,
                    "https://maps.google.com/mapfiles/ms/micons/pink-dot.png"));
            // agregar al modelMap
            modelMap.addOverlay(polySugFn);
        }
    }

    void construirMarkerEstados() {
        consularAtvEvidenciaPorNovedad();
        Predicate<AtvEvidencia> predCoordNotNull = ev -> ev.getLatitud() != null && ev.getLongitud() != null;
        listEvidencia.stream().filter(predCoordNotNull).forEachOrdered(ev -> {
            modelMap.addOverlay(new Marker(new LatLng(ev.getLatitud(),
                    ev.getLongitud()),
                    ev.getIdAtvTipoEstado().getTipoEstado(), ev,
                    "https://www.google.com/mapfiles/markerE.png"));
        });
    }

    void onCenterMapa(String coord) {
        if (coord == null) {
            centerMap = "4.6458, -74.0785";
            return;
        }
        centerMap = coord;
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        atvLocationSugerida = null;
        atvEvidencia = null;
        if (event.getOverlay() instanceof Marker) {
            Marker marker = (Marker) event.getOverlay();
            if (marker.getData() instanceof AtvLocationSugerida) {
                atvLocationSugerida = (AtvLocationSugerida) marker.getData();
            }
            if (marker.getData() instanceof AtvEvidencia) {
                atvEvidencia = (AtvEvidencia) marker.getData();
            }
        }
    }

    public void onStateChange(StateChangeEvent event) {
        zoomMap = event.getZoomLevel() + "";
    }

    public StreamedContent prepDownloadLocal(String path) throws Exception {
//        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
        return MovilidadUtil.prepDownload(path);
    }

    void resetUbicacionReal() {
        listAtvLocation = null;
    }

    void resetUbicacionSugerida() {
        atvLocationSugerida = null;
    }

    void reset() {
        atvEvidencia = null;
        listAtvLocation = null;
        atvLocationSugerida = null;
        onCenterMapa(null);
    }

    void cargarUF() {
        idGopUF = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
    }

    public List<Novedad> getListNovedadATV() {
        return listNovedadATV;
    }

    public void setListNovedadATV(List<Novedad> listNovedadATV) {
        this.listNovedadATV = listNovedadATV;
    }

    public Novedad getNovedadSelect() {
        return novedadSelect;
    }

    public void setNovedadSelect(Novedad novedadSelect) {
        this.novedadSelect = novedadSelect;
        this.modelMap = null;
        this.atvNovDoc = null;
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

    public Date getTiempoUbicacion() {
        return tiempoUbicacion;
    }

    public void setTiempoUbicacion(Date tiempoUbicacion) {
        this.tiempoUbicacion = tiempoUbicacion;
    }

    public Integer getHolgura() {
        return holgura;
    }

    public void setHolgura(Integer holgura) {
        this.holgura = holgura;
    }

    public List<AtvLocation> getListAtvLocation() {
        return listAtvLocation;
    }

    public void setListAtvLocation(List<AtvLocation> listAtvLocation) {
        this.listAtvLocation = listAtvLocation;
    }

    public AtvLocationSugerida getAtvLocationSugerida() {
        return atvLocationSugerida;
    }

    public void setAtvLocationSugerida(AtvLocationSugerida atvLocationSugerida) {
        this.atvLocationSugerida = atvLocationSugerida;
    }

    public MapModel getModelMap() {
        return modelMap;
    }

    public void setModelMap(MapModel modelMap) {
        this.modelMap = modelMap;
    }

    public String getCenterMap() {
        return centerMap;
    }

    public void setCenterMap(String centerMap) {
        this.centerMap = centerMap;
    }

    public AtvNovedadDocumento getAtvNovDoc() {
        return atvNovDoc;
    }

    public void setAtvNovDoc(AtvNovedadDocumento atvNovDoc) {
        this.atvNovDoc = atvNovDoc;
    }

    public String getZoomMap() {
        return zoomMap;
    }

    public void setZoomMap(String zoomMap) {
        this.zoomMap = zoomMap;
    }

    public List<AtvEvidencia> getListEvidencia() {
        return listEvidencia;
    }

    public void setListEvidencia(List<AtvEvidencia> listEvidencia) {
        this.listEvidencia = listEvidencia;
    }

    public AtvEvidencia getAtvEvidencia() {
        return atvEvidencia;
    }

    public void setAtvEvidencia(AtvEvidencia atvEvidencia) {
        this.atvEvidencia = atvEvidencia;
    }

    public boolean isIsUserMtto() {
        return isUserMtto;
    }

    public void setIsUserMtto(boolean isUserMtto) {
        this.isUserMtto = isUserMtto;
    }

}
