/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.dbconnection.Common;
import com.freeway.ArrayOfDetailDistanceTimeLogicDetailDistanceTimeRow;
import com.freeway.DetailDistanceTimeLogicDetailDistanceTimeRow;
import com.freeway.ISae;
import com.freeway.Sae;
import com.movilidad.ejb.PrgDistanceFacadeLocal;
import com.movilidad.ejb.PrgStopPointFacadeLocal;
import com.movilidad.model.PrgDistance;
import com.movilidad.model.PrgStopPoint;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author luis
 */
@Named(value = "prgDistanceMB")
@ViewScoped
public class PrgDistanceMB implements Serializable {

    @EJB
    private PrgStopPointFacadeLocal prgStopEjb;
    @EJB
    private PrgDistanceFacadeLocal prgDistanceEjb;
    private List<PrgStopPoint> listStop;
    private List<PrgDistance> listDistance;
    private List<PrgDistance> listDist;
    @Inject
    private SelectConfigFreewayBean configFreewayBean;
    HashMap<String, PrgStopPoint> stopPoints;
    private Date creado;
    Sae sae;
    ISae iSae;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of prgRouteMB
     */
    public PrgDistanceMB() {
    }

    public void consumeDistance() throws MalformedURLException {
        if (configFreewayBean.getConfigFreeway() == null) {
            MovilidadUtil.addErrorMessage("Se debe seleccionar Unidad Funcional");
            return;
        }
        creado = MovilidadUtil.fechaCompletaHoy();
//        listDistance = new LinkedList<>();
        listDist = new LinkedList<>();
        setStopPoints();
//        long start = System.currentTimeMillis();
        ArrayOfDetailDistanceTimeLogicDetailDistanceTimeRow distancias = getiSae(new URL(
                configFreewayBean.getConfigFreeway().getUrlServicio())).getDistanceTime();
//        System.out.println("Time Taken SAE = " + (System.currentTimeMillis() - start));
//        start = System.currentTimeMillis();
        for (DetailDistanceTimeLogicDetailDistanceTimeRow distance : distancias.getDetailDistanceTimeLogicDetailDistanceTimeRow()) {
//            System.out.println("Parada : " + stop.getName().getValue() + " " + stop.getCode().getValue());
//            prgStopEjb.create(xmlToStopPoint(stop));
            listDist.add(xmlToDistance(distance));
        }
//        System.out.println("Time Taken xmlToPrgDistance = " + (System.currentTimeMillis() - start));
        load2Db();
    }

    public void load2Db() {
        Connection con = null;
        PreparedStatement ps = null;
//        System.out.println("Load2DB");    
        String sql = "INSERT INTO prg_distance(id_prg_from_stop, "
                + "from_stop_point, "
                + "id_prg_to_stop, "
                + "to_stop_point, "
                + "distance, "
                + "origin, "
                + "vigente, "
                + "username, "
                + "id_gop_unidad_funcional, "
                + "creado) values (?,?,?,?,?,?,?,?,?,?) ";
        try {
            con = Common.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            ps = con.prepareStatement(sql);
            int i = 0;
            long start = System.currentTimeMillis();
            for (PrgDistance d : listDist) {
                ps.setInt(1, d.getIdPrgFromStop().getIdPrgStoppoint());
                ps.setString(2, d.getFromStopPoint());
                ps.setInt(3, d.getIdPrgToStop().getIdPrgStoppoint());
                ps.setString(4, d.getToStopPoint());
                ps.setString(5, d.getDistance() != null ? d.getDistance().doubleValue() + "" : null);
                ps.setInt(6, d.getOrigin());
                ps.setInt(7, d.getVigente());
                ps.setString(8, d.getUsername());
                ps.setInt(9, d.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
                ps.setString(10, Util.dateFormat(d.getCreado()));
                ps.addBatch();
                i++;
                if (i == 10000) {
                    ps.executeBatch();
                    con.commit();
                    i = 0;
                }
            }
            if (i > 0) {
                int[] results = ps.executeBatch();
                con.commit();
            }
            con.setAutoCommit(true);
//            System.out.println("Time Taken=" + (System.currentTimeMillis() - start));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Datos procesados con Éxito!"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Se presentaron errores durante el procesamiento\n" + ex.getMessage()));
            System.out.println(ex.getMessage());
            Logger.getLogger(PrgControlTableMB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PrgControlTableMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PrgControlTableMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @PostConstruct
    public void init() {
    }

    private PrgDistance xmlToDistance(DetailDistanceTimeLogicDetailDistanceTimeRow dis) {
        PrgDistance distance = new PrgDistance();
        PrgStopPoint parada;
        parada = (stopPoints.get(dis.getFromStopPoint().getValue()));
        distance.setIdPrgFromStop(parada);
        distance.setFromStopPoint(dis.getFromStopPoint().getValue());
        parada = (stopPoints.get(dis.getToStopPoint().getValue()));
        distance.setIdPrgToStop(parada);
        distance.setFromStopPoint(dis.getToStopPoint().getValue());
        distance.setDistance(dis.getDistance() != null ? new BigDecimal(dis.getDistance().getValue().replace(",", ".")) : null);
        distance.setOrigin(dis.isOrigin() ? 1 : 0);
        distance.setVigente(1);
        distance.setCreado(creado);
        distance.setIdGopUnidadFuncional(configFreewayBean.getConfigFreeway().getIdGopUnidadFuncional());
        distance.setUsername(user.getUsername());
        return distance;
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

    public List<PrgStopPoint> getListStop() {
        listStop = prgStopEjb.findAllByUnidadFuncional(configFreewayBean.getIdGopUF());
        return listStop;
    }

    public void setListStop(List<PrgStopPoint> listStop) {
        this.listStop = listStop;
    }

    public List<PrgDistance> getListDistance() {
        return prgDistanceEjb.findAll();
    }

    public void setListDistance(List<PrgDistance> listDistance) {
        this.listDistance = listDistance;
    }

    private void setStopPoints() {
//        if (this.stopPoints == null) {
        stopPoints = new HashMap<>();
        for (PrgStopPoint s : prgStopEjb.findAllByUnidadFuncional(configFreewayBean.getIdGopUF())) {
            stopPoints.put(s.getCode(), s);
        }

//        }
    }

}
