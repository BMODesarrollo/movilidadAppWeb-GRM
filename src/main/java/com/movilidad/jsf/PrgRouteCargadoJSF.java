/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.freeway.ArrayOfPatternRow;
import com.freeway.ISae;
import com.freeway.PatternRow;
import com.freeway.Sae;
import com.movilidad.ejb.PrgPatternFacadeLocal;
import com.movilidad.ejb.PrgRouteFacadeLocal;
import com.movilidad.ejb.PrgStopPointFacadeLocal;
import com.movilidad.model.PrgPattern;
import com.movilidad.model.PrgRoute;
import com.movilidad.model.PrgStopPoint;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
 * @author cesar
 */
@Named(value = "prgRouteCargadoJSF")
@ViewScoped
public class PrgRouteCargadoJSF implements Serializable {

    @EJB
    private PrgRouteFacadeLocal prgRouteEjb;
    @EJB
    private PrgStopPointFacadeLocal prgStopEjb;
    @EJB
    private PrgPatternFacadeLocal prgPatternEjb;

    @Inject
    private SelectConfigFreewayBean configFreewayBean;
    private List<PrgPattern> listPrgPattern;
    private String cCodRuta;
    private PrgRoute prgRoute;
    private boolean bFlag;

    HashMap<String, PrgRoute> routes;
    HashMap<String, PrgStopPoint> stopPoints;

    Sae sae;
    ISae iSae;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public PrgRouteCargadoJSF() {
    }

    @PostConstruct
    public void init() {
        routes = new HashMap<>();
        stopPoints = new HashMap<>();
        listPrgPattern = new ArrayList<>();
        cCodRuta = null;
        prgRoute = null;
        bFlag = false;
    }

    public void buscar() {
        if (configFreewayBean.getConfigFreeway() == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar Unidad Funcional");
            return;
        }
        if (cCodRuta == null || cCodRuta.isEmpty()) {
            MovilidadUtil.addErrorMessage("Código es requerido");
            return;
        }
        listPrgPattern.clear();
        prgRoute = prgRouteEjb.find("code", cCodRuta, configFreewayBean.getIdGopUF());
        if (prgRoute == null) {
            MovilidadUtil.addErrorMessage("No se encontró Route para el código ingresado");
            return;
        }
        listPrgPattern = prgPatternEjb.findAllOrderedByIdRoute(prgRoute.getIdPrgRoute());
        if (!listPrgPattern.isEmpty()) {
            MovilidadUtil.addSuccessMessage("Registros encontrados");
            return;
        }
        configFreewayBean.setDisabled(true);
        MovilidadUtil.addAdvertenciaMessage("No se encontrarón Pattern, Puede proceder a consumir el servicio");
        bFlag = true;
    }

    @Transactional
    public void consumePattern() throws MalformedURLException {
        ArrayOfPatternRow patterns;
        setRoutes();
        setStopPoints();
        PrgPattern p;
        patterns = getiSae(new URL(
                configFreewayBean.getConfigFreeway().getUrlServicio())).getPattern(cCodRuta);
        int i = patterns.getPatternRow().size();
        for (PatternRow pattern : patterns.getPatternRow()) {
            p = xmlToPattern(pattern);
            prgPatternEjb.create(p);
            listPrgPattern.add(p);
        }
        prgRoute = null;
        bFlag = false;
        configFreewayBean.setDisabled(false);
        MovilidadUtil.addSuccessMessage("Proceso realizado con éxito, " + i + " Pattern encontrados y agregados al sistema");
    }

    private void setStopPoints() {
//        if (this.stopPoints == null) {
        //stopPoints = new HashMap<>();
        stopPoints.clear();
        for (PrgStopPoint s : prgStopEjb.findAllByUnidadFuncional(configFreewayBean.getIdGopUF())) {
            stopPoints.put(s.getCode(), s);
//            stopPoints.put(s.getName(), s.getIdPrgStoppoint());
        }

    }

    private void setRoutes() {
//        if (this.routes == null) {
        //       routes = new HashMap<>();
        routes.clear();
        for (PrgRoute r : prgRouteEjb.findByUnidadFuncional(configFreewayBean.getIdGopUF())) {
            routes.put(r.getCode(), r);
        }
//        }
    }

    private PrgPattern xmlToPattern(PatternRow p) {
        PrgPattern pattern = new PrgPattern();
        PrgStopPoint parada;
        pattern.setIdPattern(p.getPatternId().getValue());
        pattern.setPattern(p.getPattern().getValue());
        //pattern. //id_prg_route
        pattern.setIdPrgRoute(routes.get(p.getRoute().getValue()));
        pattern.setIdRoute(p.getRouteId().getValue());
        pattern.setRoute(p.getRoute().getValue());
        pattern.setSecuenceNumber(p.getSequenceNumber());
        pattern.setDistance(p.getDistance());
        pattern.setIsActive(Integer.valueOf(p.getIsActive() + ""));
        pattern.setIsCheckStopPoint(Integer.valueOf(p.getIsCheckStopPoint() + ""));
        parada = stopPoints.get(p.getStopPoint().getValue());
        pattern.setIdPrgStoppoint(parada != null ? parada : null);
        pattern.setIdStopPoint(p.getStopPointID().getValue());
        pattern.setStopPoint(p.getStopPoint().getValue());
        pattern.setUsername(user.getUsername());
        pattern.setIdGopUnidadFuncional(configFreewayBean.getConfigFreeway().getIdGopUnidadFuncional());
//        pattern.setUsername("freeway");
        pattern.setCreado(new Date());
        return pattern;
    }

    public String getcCodRuta() {
        return cCodRuta;
    }

    public void setcCodRuta(String cCodRuta) {
        this.cCodRuta = cCodRuta;
    }

    public Sae getSae(URL url) {
        sae = new Sae(url);
        return sae;
    }

    public ISae getiSae(URL url) {
        iSae = getSae(url).getHttpSaeService();
        return iSae;
    }

    public List<PrgPattern> getListPrgPattern() {
        return listPrgPattern;
    }

    public void setListPrgPattern(List<PrgPattern> listPrgPattern) {
        this.listPrgPattern = listPrgPattern;
    }

    public boolean isbFlag() {
        return bFlag;
    }

    public void setbFlag(boolean bFlag) {
        this.bFlag = bFlag;
    }

}
