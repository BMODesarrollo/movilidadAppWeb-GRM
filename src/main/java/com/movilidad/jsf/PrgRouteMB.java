/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.freeway.ArrayOfPatternRow;
import com.freeway.ArrayOfRouteRow;
import com.freeway.ArrayOfStopPointRow;
import com.freeway.ISae;
import com.freeway.PatternRow;
import com.freeway.RouteRow;
import com.freeway.Sae;
import com.freeway.StopPointRow;
import com.movilidad.ejb.PrgPatternFacadeLocal;
import com.movilidad.ejb.PrgRouteFacadeLocal;
import com.movilidad.ejb.PrgStopPointFacadeLocal;
import com.movilidad.model.PrgPattern;
import com.movilidad.model.PrgRoute;
import com.movilidad.model.PrgStopPoint;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author luis
 */
@Named(value = "prgRouteMB")
@ViewScoped
public class PrgRouteMB implements Serializable {

    @EJB
    private PrgRouteFacadeLocal prgRouteEjb;
    @EJB
    private PrgStopPointFacadeLocal prgStopEjb;
    @EJB
    private PrgPatternFacadeLocal prgPatternEjb;
    @Inject
    private SelectConfigFreewayBean configFreewayBean;
    private List<PrgRoute> listRoutes;
    private List<PrgStopPoint> listStop;
    private List<PrgPattern> listPattern;

    HashMap<String, Integer> routes;
    HashMap<String, Integer> stopPoints;

    Sae sae;
    ISae iSae;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of prgRouteMB
     */
    public PrgRouteMB() {
    }

    /**
     * Consume las Routes de Freeway a través del método getRoutes del SAE
     * Service
     */
    @Transactional
    public void consumeRoutes() throws MalformedURLException {
        if (configFreewayBean.getConfigFreeway() == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar Unidad Funcional");
            return;
        }
        setStopPoints();
//        System.out.println("consumeRoutes");
        ArrayOfRouteRow rutas = getiSae(new URL(
                configFreewayBean.getConfigFreeway().getUrlServicio())).getRoutes();
//        System.out.println("Nro de Rutas : " + rutas.getRouteRow().size());

        for (RouteRow route : rutas.getRouteRow()) {
//            System.out.println("Ruta : " + route.getName().getValue() + " " + route.getCode().getValue());
            prgRouteEjb.create(xmlToRoute(route));
        }
        listRoutes = prgRouteEjb.findByUnidadFuncional(configFreewayBean.getIdGopUF());
        MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
    }

    /**
     * Consume los StopPoints de Freeway a través del método getStopPoints del
     * SAE Service
     */
//    @Transactional
    public void consumeStopPoints() throws MalformedURLException {
        if (configFreewayBean.getConfigFreeway() == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar Unidad Funcional");
            return;
        }
//        System.out.println("consumeStopPoints");
        ArrayOfStopPointRow paradas = getiSae(new URL(
                configFreewayBean.getConfigFreeway().getUrlServicio())).getStopPoints();
//        System.out.println("Nro de StopPoints : " + paradas.getStopPointRow().size());

//        PrgStopPoint stopPoint = new PrgStopPoint();
        for (StopPointRow stop : paradas.getStopPointRow()) {
//            System.out.println("Parada : " + stop.getName().getValue() + " " + stop.getCode().getValue());
            PrgStopPoint xmlToStopPoint = xmlToStopPoint(stop);
            prgStopEjb.create(xmlToStopPoint);
        }
        MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
    }

    public boolean verificarStopPoints() {

        if (configFreewayBean.getIdGopUF() == 0) {
            return true;
        }
        return prgStopEjb.count(configFreewayBean.getIdGopUF()).compareTo(Long.valueOf(0)) > 0;

    }

    public boolean verificarRoutes() {
        if (configFreewayBean.getIdGopUF() == 0) {
            return true;
        }
        if (prgStopEjb.count(configFreewayBean.getIdGopUF()).equals(Long.valueOf(0))) {
            return true;
        }
        return prgRouteEjb.count(configFreewayBean.getIdGopUF()).compareTo(Long.valueOf(0)) > 0;
    }

    public boolean verificarRedTransporte() {
        return configFreewayBean.getIdGopUF() == 0;
//        if (prgStopEjb.count(configFreewayBean.getIdGopUF()).equals(Long.valueOf(0))) {
//            return true;
//        }
//        return prgRouteEjb.count(configFreewayBean.getIdGopUF()).equals(Long.valueOf(0));
    }

    /**
     * Consume los StopPoints de Freeway a través del método getStopPoints del
     * SAE Service
     */
    @Transactional
    public void consumePattern() throws MalformedURLException {
//        System.out.println("consumePattern");
        if (configFreewayBean.getConfigFreeway() == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar Unidad Funcional");
            return;
        }
        ArrayOfPatternRow patterns;
        setRoutes();
        setStopPoints();
        PrgPattern p;
//        System.out.println("Nro Rutas :" + getListRoutes().size());
        for (PrgRoute ruta : getListRoutes()) {
            patterns = getiSae(new URL(
                    configFreewayBean.getConfigFreeway().getUrlServicio())).getPattern(ruta.getCode());
//            System.out.println("Ruta : " + ruta.getName());
//            System.out.println("Paradas : " + patterns.getPatternRow().size());
            for (PatternRow pattern : patterns.getPatternRow()) {
                p = xmlToPattern(pattern);
//                System.out.println("Secuencia : " + p.getSecuenceNumber() + " Parada: " + p.getStopPoint()+ " StopID: " + p.getIdPrgStoppoint());
                prgPatternEjb.create(p);
            }
//            break;
        }

    }

    @PostConstruct
    public void init() {

    }

    public void validateAllNet() throws MalformedURLException {
        if (configFreewayBean.getConfigFreeway() == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar Unidad Funcional");
            return;
        }
        List<String> nuevasRutas = new LinkedList<>();
        setRoutes();
        setStopPoints();
        int newStops = 0;
        int inactiveStops = 0;
        int newRoute = 0;
        int inactiveRoute = 0;
        //Valida las paradas del sistema
//        System.out.println("Consumiendo Paradas del sistema");
        //Consumiendo Paradas del sistema
        ArrayOfStopPointRow paradas = getiSae(new URL(
                configFreewayBean.getConfigFreeway().getUrlServicio())).getStopPoints();
//        System.out.println("Nro de StopPoints : " + paradas.getStopPointRow().size());
        for (StopPointRow stop : paradas.getStopPointRow()) {
//            System.out.println("Parada : " + stop.getName().getValue() + " " + stop.getCode().getValue());
            if (stopPoints.get(stop.getCode().getValue()) == null) {
                prgStopEjb.create(xmlToStopPoint(stop));
//                System.out.println("Parada Creada : " + stop.getName().getValue());
            } else if (stop.getIsActive().intValue() == 0) {
                //Hacer el Edit en la Tabla
                inactiveStops += prgStopEjb.desactivarStopPoints(stop.getStopPointId().getValue());
//                prgStopEjb.desactivarStopPoints(stop.getCode().getValue());
            }
        }
        if (newStops > 0) {
            MovilidadUtil.addSuccessMessage("Paradas Comprobadas, se crearon " + newStops + " paradas");
        } else {
            MovilidadUtil.addAdvertenciaMessage("No se encontraron nuevas paradas");
        }
        if (inactiveStops > 0) {
            MovilidadUtil.addSuccessMessage("Se inactivaron " + inactiveStops + " paradas");
        }
        //Valida las rutas del sistema
        setStopPoints();
//        System.out.println("consumeRoutes");
//        System.out.println("Consumiendo Rutas del sistema");
        ArrayOfRouteRow rutas = getiSae(new URL(
                configFreewayBean.getConfigFreeway().getUrlServicio())).getRoutes();

        for (RouteRow route : rutas.getRouteRow()) {
//            System.out.println("Ruta : " + route.getName().getValue() + " " + route.getCode().getValue());
            if (routes.get(route.getCode().getValue()) == null) {
                prgRouteEjb.create(xmlToRoute(route));
                if (route.getIsActive().intValue() != 0) {
                    nuevasRutas.add(route.getCode().getValue());
                }
//                System.out.println("Ruta Creada : " + route.getName().getValue());
            } else if (route.getIsActive().intValue() == 0) {
                //Hacer el Edit en la Tabla
                inactiveRoute += prgRouteEjb.desactivarRoute(route.getCode().getValue());
            }
        }
        if (newRoute > 0) {
            MovilidadUtil.addSuccessMessage("Rutas Comprobadas, se crearon " + newRoute + " rutas2");
        } else {
            MovilidadUtil.addAdvertenciaMessage("No se encontraron nuevas rutas");
        }
        if (inactiveStops > 0) {
            MovilidadUtil.addSuccessMessage("Se inactivaron " + inactiveRoute + " rutas");
        }
        if (!nuevasRutas.isEmpty()) {
//            System.out.println("Hay Rutas Nuevas : " + nuevasRutas.size());
            ArrayOfPatternRow patterns;
            setRoutes();
//            setStopPoints();
            PrgPattern p;
//        System.out.println("Nro Rutas :" + getListRoutes().size());
            for (String ruta : nuevasRutas) {
//                System.out.println("Consumiendo Pattern de Ruta : "+ruta);
                patterns = getiSae(new URL(
                        configFreewayBean.getConfigFreeway().getUrlServicio())).getPattern(ruta);
                for (PatternRow pattern : patterns.getPatternRow()) {
                    p = xmlToPattern(pattern);
//                System.out.println("Secuencia : " + p.getSecuenceNumber() + " Parada: " + p.getStopPoint()+ " StopID: " + p.getIdPrgStoppoint());
                    prgPatternEjb.create(p);
                }
//            break;
            }
            MovilidadUtil.addSuccessMessage("Pattern Actualizado");
        }
    }

    private PrgPattern xmlToPattern(PatternRow p) {
        PrgPattern pattern = new PrgPattern();
        PrgStopPoint parada;
        pattern.setIdPattern(p.getPatternId().getValue());
        pattern.setPattern(p.getPattern().getValue());
        //pattern. //id_prg_route
        pattern.setIdPrgRoute(new PrgRoute(routes.get(p.getRoute().getValue())));
        pattern.setIdRoute(p.getRouteId().getValue());
        pattern.setRoute(p.getRoute().getValue());
        pattern.setSecuenceNumber(p.getSequenceNumber());
        pattern.setDistance(p.getDistance());
        pattern.setIsActive(Integer.valueOf(p.getIsActive() + ""));
        pattern.setIsCheckStopPoint(Integer.valueOf(p.getIsCheckStopPoint() + ""));
        parada = (new PrgStopPoint(stopPoints.get(p.getStopPoint().getValue())));
        pattern.setIdPrgStoppoint(parada != null ? parada : null);
        pattern.setIdStopPoint(p.getStopPointID().getValue());
        pattern.setStopPoint(p.getStopPoint().getValue());
        pattern.setUsername(user.getUsername());
        pattern.setIdGopUnidadFuncional(configFreewayBean.getConfigFreeway().getIdGopUnidadFuncional());
//        pattern.setUsername("freeway");
        pattern.setCreado(new Date());
        return pattern;
    }

    private PrgRoute xmlToRoute(RouteRow route) {
        PrgRoute ruta = new PrgRoute();
        PrgStopPoint stop = new PrgStopPoint();
        ruta.setIdRoute(route.getRouteId().getValue());
        ruta.setName(route.getName().getValue());
        ruta.setCode(route.getCode().getValue());
        ruta.setDescription(route.getDescription().getValue());
        ruta.setIdLine(route.getLineId().getValue());
        ruta.setIdGopUnidadFuncional(configFreewayBean.getConfigFreeway().getIdGopUnidadFuncional());
        ruta.setLine(route.getLine().getValue());

//        stop = prgStopEjb.find("id_stop_point", route.getFromStopPointID().getValue());
        if (stopPoints.get(route.getFromStopPoint().getValue()) == null) {
            System.out.println(route.getFromStopPointID().getValue());
        }
        stop = new PrgStopPoint(stopPoints.get(route.getFromStopPoint().getValue()));
//        if (stop == null) {
//            System.out.println("Parada ini " + route.getFromStopPointID().getValue());
//            System.out.println("Parada ini " + route.getCode().getValue());
//        }
        ruta.setIdPrgFromStoppoint(stop);

        ruta.setIdFromStopPoint(route.getFromStopPointID().getValue());
        ruta.setFromStopPoint(route.getFromStopPoint().getValue());
        ruta.setIdToStopPoint(route.getToStopPointID().getValue());

//        stop = prgStopEjb.find("id_stop_point", route.getFromStopPointID().getValue());
        stop = new PrgStopPoint(stopPoints.get(route.getToStopPoint().getValue()));
//        System.out.println("Parada Fin " + route.getToStopPoint().getValue());
//        System.out.println("Parada Fin " + route.getToStopPointID().getValue());

        ruta.setIdPrgToStoppoint(stop);

        ruta.setToStopPoint(route.getToStopPoint().getValue());
        ruta.setIsActive(route.getIsActive().intValue());
        ruta.setIsCircular(route.getIsCircular().intValue());
        ruta.setIsCommercial(route.getIsCommercial().intValue());
        ruta.setUsername(user.getUsername());
//        ruta.setUsername("freeway");
        ruta.setCreado(new Date());
        return ruta;
    }

    public PrgStopPoint xmlToStopPoint(StopPointRow sp) {
        PrgStopPoint stopPoint = new PrgStopPoint();
        stopPoint.setIdStopPoint(sp.getStopPointId().getValue());
        stopPoint.setName(sp.getName().getValue().trim());
        stopPoint.setCode(sp.getCode().getValue().trim());
        stopPoint.setDescription(sp.getDescription().getValue());
        stopPoint.setIsActive(sp.getIsActive().intValue());
        stopPoint.setIsDepot(sp.getIsDepot().intValue());
        stopPoint.setIsFuelAvaible(sp.getIsFuelAvailable().intValue());
        stopPoint.setIsRelayPosible(sp.getIsRelayPossible().intValue());
        stopPoint.setVehicleCapacity(sp.getVehicleCapacity());
        stopPoint.setUsername(user.getUsername());
//        stopPoint.setUsername("freeway");
        stopPoint.setCreado(MovilidadUtil.fechaCompletaHoy());
        stopPoint.setEstadoReg(0);
        stopPoint.setIdGopUnidadFuncional(configFreewayBean.getConfigFreeway().getIdGopUnidadFuncional());
//        System.out.println("stopPoint->" + stopPoint);

        return stopPoint;
    }

    public Sae getSae(URL url) {
        sae = new Sae(url);
        return sae;
    }

    public ISae getiSae(URL url) {
        iSae = getSae(url).getHttpSaeService();
        return iSae;
    }

    public void setSae(Sae sae) {
        this.sae = sae;
    }

    public void setiSae(ISae iSae) {
        this.iSae = iSae;
    }

    public List<PrgRoute> getListRoutes() {
        listRoutes = prgRouteEjb.findByUnidadFuncional(configFreewayBean.getIdGopUF());
        return listRoutes;
    }

    public void setListRoutes(List<PrgRoute> listRoutes) {
        this.listRoutes = listRoutes;
    }

    public List<PrgStopPoint> getListStop() {
        listStop = prgStopEjb.findAllByUnidadFuncional(configFreewayBean.getIdGopUF());
        return listStop;
    }

    public List<PrgPattern> getListPattern() {
        listPattern = prgPatternEjb.findAllByidGopUnidadFuncOrdered(configFreewayBean.getIdGopUF());
        return listPattern;
    }

    public void setListPattern(List<PrgPattern> listPattern) {
        this.listPattern = listPattern;
    }

    public void setListStop(List<PrgStopPoint> listStop) {
        this.listStop = listStop;
    }

    private void setRoutes() {
//        if (this.routes == null) {
        routes = new HashMap<>();
        for (PrgRoute r : prgRouteEjb.findByUnidadFuncional(configFreewayBean.getIdGopUF())) {
            routes.put(r.getCode(), r.getIdPrgRoute());
        }
//        }
    }

    private void setStopPoints() {
//        if (this.stopPoints == null) {
        stopPoints = new HashMap<>();
        for (PrgStopPoint s : prgStopEjb.findAllByUnidadFuncional(configFreewayBean.getIdGopUF())) {
            stopPoints.put(s.getCode(), s.getIdPrgStoppoint());
//            stopPoints.put(s.getName(), s.getIdPrgStoppoint());
        }

    }

}

//}
