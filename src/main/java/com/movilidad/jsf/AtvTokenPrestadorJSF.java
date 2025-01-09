/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AtvCostoServicioFacadeLocal;
import com.movilidad.ejb.AtvEvidenciaFacadeLocal;
import com.movilidad.ejb.AtvNovedadDocumentoFacadeLocal;
import com.movilidad.ejb.AtvPrestadorFacadeLocal;
import com.movilidad.ejb.AtvTokenPrestadorFacadeLocal;
import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.model.AtvCostoServicio;
import com.movilidad.model.AtvEvidencia;
import com.movilidad.model.AtvLocation;
import com.movilidad.model.AtvLocationSugerida;
import com.movilidad.model.AtvNovedadDocumento;
import com.movilidad.model.AtvPrestador;
import com.movilidad.model.AtvTokenPrestador;
import com.movilidad.model.ConfigEmpresa;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.Novedad;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.TokenGeneratorUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.text.ParseException;
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
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;

/**
 *
 * @author soluciones-it
 */
@Named(value = "atvTokenPrestadorJSF")
@ViewScoped
public class AtvTokenPrestadorJSF implements Serializable {

    @EJB
    private AtvPrestadorFacadeLocal atvPrestadorFacadeLocal;
    @EJB
    private AtvTokenPrestadorFacadeLocal atvTokenPrestadorFacadeLocal;
    @EJB
    private NovedadFacadeLocal novedadEJB;
    @EJB
    private AtvCostoServicioFacadeLocal atvCostoServicioFacadeLocal;
    @EJB
    private AtvNovedadDocumentoFacadeLocal atvNovedadDocumentoFacadeLocal;
    @EJB
    private AtvEvidenciaFacadeLocal atvEvidenciaFacadeLocal;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private ConfigEmpresaFacadeLocal configEmpresaFacadeLocal;

    private String correo;
    private boolean permittedAccess;
    private String token;
    private AtvTokenPrestador atvTokenPrestador;
    //
    private List<Novedad> listNovedadATV;
    private Novedad novedadSelect;

    private Date fecha;
    private Map<String, AtvCostoServicio> mapAtvCostoServicio;

    // map
    private MapModel modelMap;
    private String centerMap;
    private int opcionMap;
    private String zoomMap;

    // ubicacion recorrido real
    private List<AtvLocation> listAtvLocation;
    private AtvLocationSugerida atvLocationSugerida;
    private List<AtvLocationSugerida> atvLocSugeridaList;

    /**
     * Creates a new instance of AtvTokenPrestadorJSF
     */
    public AtvTokenPrestadorJSF() {
    }

    /**
     * Inicializa las variables del bean, toma un valor de la url.
     */
    @PostConstruct
    public void init() {
        mapAtvCostoServicio = new HashMap<>();
        onCenterMapa(null);
        opcionMap = 0;
        zoomMap = "18";
        atvTokenPrestador = null;
        fecha = new Date();
        denegateAccess();
    }

    public void consultarNov() {
        if (atvTokenPrestador == null) {
            denegateAccess();
            return;
        }
        listNovedadATV = novedadEJB.findNovsAfectaATVByPropietario(fecha, atvTokenPrestador.getIdAtvPrestador().getIdAtvPrestador());

        cargarMapCostoServicio();
    }

    void cargarMapCostoServicio() {
        List<AtvCostoServicio> listCosServ = atvCostoServicioFacadeLocal
                .findAllByFechas(Util.primerDiaMes(fecha), Util.ultimoDiaMes(fecha));
        mapAtvCostoServicio.clear();
        listCosServ.forEach(cs -> {
            mapAtvCostoServicio.put(cs.getIdAtvPrestador().getIdAtvPrestador() + "-" + cs.getIdVehiculoTipo().getIdVehiculoTipo(), cs);
        });
    }

    /**
     * Verifica que el token se encuentre valido en el momento.
     */
    public void validarToken() {
        try {
            if (Util.isStringNullOrEmpty(token)) {
                MovilidadUtil.addErrorMessage("Token no valido");
                resetToken();
                dirLogin();
                return;
            }
            AtvTokenPrestador atp = atvTokenPrestadorFacadeLocal.findByTokenAndActivo(token);
            if (atp == null) {
                MovilidadUtil.addErrorMessage("Token no valido");
                resetToken();
                dirLogin();
                return;
            }
            Date d = new Date();
            if (MovilidadUtil.fechasIgualMenorMayor(d, atp.getCreado(), false) != 1) {
                atp.setActivo(ConstantsUtil.OFF_INT);
                atvTokenPrestadorFacadeLocal.edit(atp);
                MovilidadUtil.addErrorMessage("Token no valido, solicite uno nuevamente");
                resetToken();
                return;
            }
            MovilidadUtil.runScript("sessionStorage.setItem('rgAtvKey', '" + atp.getToken() + "');");
            HttpServletRequest request = (HttpServletRequest) FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getRequest();
            String url = Util.getUrlContext(request) + "/public/atv/bitacora/list.jsf";
            MovilidadUtil.onLocationHref(url);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    public void crearToken() {
        if (Util.isStringNullOrEmpty(correo)) {
            MovilidadUtil.addErrorMessage("Debe suministrar un correo");
            return;
        }
        AtvPrestador atvPrestador = atvPrestadorFacadeLocal.findByCorreoAndActivo(correo);
        if (atvPrestador == null) {
            MovilidadUtil.addErrorMessage("Correo errado");
            return;
        }
        AtvTokenPrestador atp = new AtvTokenPrestador();
        atp.setToken(TokenGeneratorUtil.nextToken());
        atp.setCreado(new Date());
        atp.setIdAtvPrestador(atvPrestador);
        atp.setActivo(ConstantsUtil.ON_INT);
        atvTokenPrestadorFacadeLocal.create(atp);
        correo = null;
        enviarCorreo(atp);
        MovilidadUtil.addSuccessMessage("Token enviado al correo registrado del prestador");
    }

    /**
     * Permite obtener el token que se encuentra en el localStorage y validarlo
     * en el sistema
     */
    public void obtenerToken() {
        try {
            if (Util.isStringNullOrEmpty(token)) {
                MovilidadUtil.addErrorMessage("Token no valido");
                resetToken();
                return;
            }
            AtvTokenPrestador atp = atvTokenPrestadorFacadeLocal.findByTokenAndActivo(token);
            if (atp == null) {
                MovilidadUtil.addErrorMessage("Token no valido");
                resetToken();
                return;
            }
            Date d = new Date();
            if (MovilidadUtil.fechasIgualMenorMayor(d, atp.getCreado(), false) != 1) {
                atp.setActivo(ConstantsUtil.OFF_INT);
                atvTokenPrestadorFacadeLocal.edit(atp);
                MovilidadUtil.addErrorMessage("Token no valido, solicite uno nuevamente");
                resetToken();
                return;
            }
            atvTokenPrestador = atp;
            permittedAccess = true;
            consultarNov();
        } catch (Exception e) {
            resetToken();
        }
    }

    public void cierreSeguro() {
        if (atvTokenPrestador != null) {
            atvTokenPrestador.setActivo(ConstantsUtil.OFF_INT);
            atvTokenPrestadorFacadeLocal.edit(atvTokenPrestador);
            resetToken();
        }
        dirLogin();
    }

    void resetToken() {
        token = null;
        denegateAccess();
        atvTokenPrestador = null;
        MovilidadUtil.runScript("sessionStorage.removeItem('rgKey')");
    }

    /*
    * Permite dirigir al login de ATV public
     */
    void dirLogin() {
        HttpServletRequest request = (HttpServletRequest) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequest();
        String url = Util.getUrlContext(request) + "/public/atv/login.jsf";
        MovilidadUtil.onLocationHref(url);
    }

    private void denegateAccess() {
        permittedAccess = false;
    }

    // mapa
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
//        if (op == 2) {
//            if (consularUbicacionRecorridoRealByFecha()) {
//                return;
//            }
//            construirRutaReal();
//        }
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
                    "", null,
                    "http://www.google.com/mapfiles/dd-start.png"));
            modelMap.addOverlay(new Marker(latLngEnd,
                    "", null,
                    "http://maps.google.com/mapfiles/arrow.png"));
            onCenterMapa(endLoc.getLatitud().toString() + "," + endLoc.getLongitud().toString());
            MovilidadUtil.updateComponent("gmapAtv");
        }
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
                "", atvLocReccInicial,
                "https://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
        // center map, punto de inciio
        onCenterMapa(atvLocReccInicial.getLatitudStart().toString() + "," + atvLocReccInicial.getLongitudStart().toString());
        // punto fin
        modelMap.addOverlay(new Marker(new LatLng(atvLocReccInicial.getLatitudEnd(),
                atvLocReccInicial.getLongitudEnd()),
                "", atvLocReccInicial,
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
                    "", atvLocReccFinal,
                    "https://maps.google.com/mapfiles/ms/micons/pink-dot.png"));
            // agregar al modelMap
            modelMap.addOverlay(polySugFn);
        }
    }

    void onCenterMapa(String coord) {
        if (coord == null) {
            centerMap = "4.6458, -74.0785";
            return;
        }
        centerMap = coord;
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        if (event.getOverlay() instanceof Marker) {
            Marker marker = (Marker) event.getOverlay();
            if (marker.getData() instanceof AtvLocationSugerida) {
                atvLocationSugerida = (AtvLocationSugerida) marker.getData();
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
        listAtvLocation = null;
        atvLocationSugerida = null;
        onCenterMapa(null);
    }

    /**
     * Permite consultar las evidencias cargadas por el carro taller de una
     * novedas
     *
     * @param nov Novedad
     * @return
     */
    public List<AtvEvidencia> consularAtvEvidenciaPorNovedad(Novedad nov) {
        return atvEvidenciaFacadeLocal.findByIdNovedad(nov.getIdNovedad());
    }

    // documentos 
    public List<AtvNovedadDocumento> consultarAtvNovedadDocumentos() {
        if (novedadSelect == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una novedad");
            return null;
        }
        return atvNovedadDocumentoFacadeLocal.findAllByIdNovedadAndActivos(novedadSelect.getIdNovedad());
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

    private Map getMailParams() {
        String templ = Util.ID_TOKEN_TEMPLATE;
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(templ);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    private void enviarCorreo(AtvTokenPrestador atp) {
        ConfigEmpresa ce = configEmpresaFacadeLocal.findByLlave(ConstantsUtil.ID_LOGO);
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("codigo", atp.getToken());
        mailProperties.put("modulo", "NOVEDAD ATENCIÓN EN VÍA");
        mailProperties.put("logo", ce != null ? ce.getValor() : "");
        String subject = Util.ID_ESTADO_SOLICITUDES;
        String destinatarios = atp.getIdAtvPrestador().getCorreo();
        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", null);
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isPermittedAccess() {
        return permittedAccess;
    }

    public void setPermittedAccess(boolean permittedAccess) {
        this.permittedAccess = permittedAccess;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public MapModel getModelMap() {
        return modelMap;
    }

    public void setModelMap(MapModel modelMap) {
        this.modelMap = modelMap;
    }

    public String getZoomMap() {
        return zoomMap;
    }

    public void setZoomMap(String zoomMap) {
        this.zoomMap = zoomMap;
    }

    public AtvLocationSugerida getAtvLocationSugerida() {
        return atvLocationSugerida;
    }

    public void setAtvLocationSugerida(AtvLocationSugerida atvLocationSugerida) {
        this.atvLocationSugerida = atvLocationSugerida;
    }

    public String getCenterMap() {
        return centerMap;
    }

    public void setCenterMap(String centerMap) {
        this.centerMap = centerMap;
    }

    public AtvTokenPrestador getAtvTokenPrestador() {
        return atvTokenPrestador;
    }

    public void setAtvTokenPrestador(AtvTokenPrestador atvTokenPrestador) {
        this.atvTokenPrestador = atvTokenPrestador;
    }

}
