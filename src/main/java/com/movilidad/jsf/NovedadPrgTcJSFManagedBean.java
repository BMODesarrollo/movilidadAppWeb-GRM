/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.NovedadPrgTcFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.NovedadPrgTc;
import com.movilidad.util.beans.NovedadPrgTcAux;
import com.movilidad.util.beans.NovedadesTQ04;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author solucionesit
 */
@Named(value = "novedadPrgTcJSFMB")
@ViewScoped
public class NovedadPrgTcJSFManagedBean implements Serializable {

    /**
     * Creates a new instance of NovedadPrgTcJSFManagedBean
     */
    public NovedadPrgTcJSFManagedBean() {
    }

    @PostConstruct
    public void init() {
        hasta = MovilidadUtil.fechaHoy();
        desde = MovilidadUtil.sumarDias(hasta, -4);
        list = novTcejb.findAllByFechas(desde, hasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        consultarTq();
    }

    @EJB
    private NovedadPrgTcFacadeLocal novTcejb;

    @EJB
    private PrgTcFacadeLocal prgTcEJB;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<NovedadPrgTc> list;
    private List<NovedadPrgTcAux> listBloqueadoViaOld;
    private List<NovedadesTQ04> listBloqueadoVia;
    private Date desde;
    private Date hasta;

    public void consultar() {
        list = novTcejb.findAllByFechas(desde, hasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void consultarTq() {
        listBloqueadoVia = prgTcEJB.getNovedadesTq04(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

//    public void consultarTqOld() {
//        listBloqueadoVia = new ArrayList<>();
//        List<NovedadPrgTc> listBloqueadoViaAux = novTcejb.findTqByFechas(desde, hasta);
//        for (NovedadPrgTc n : listBloqueadoViaAux) {
//            if (listBloqueadoVia.isEmpty()) {
//                NovedadPrgTcAux nuax = new NovedadPrgTcAux();
//                nuax.setObj(n);
//                if (n.getEstadoOperacion() != null) {
//                    if (n.getEstadoOperacion() == 5 || n.getEstadoOperacion() == 8 || n.getEstadoOperacion() == 99) {
////                    int fromStop = n.getIdPrgTc().getFromStop().getIdPrgStoppoint();
////                    int toStop = n.getIdPrgTc().getToStop().getIdPrgStoppoint();
////                    BigDecimal distancia = prgTcEJB.findDistandeByFromStopAndToStop(fromStop, toStop);
//                        BigDecimal distancia = n.getDistancia();
//                        if (distancia != null) {
//                            nuax.setKmPerdido(distancia);
//                        } else {
//                            nuax.setKmPerdido(BigDecimal.ZERO);
//                        }
//                    } else {
//                        nuax.setKmPerdido(BigDecimal.ZERO);
//                    }
//                } else {
//                    nuax.setKmPerdido(BigDecimal.ZERO);
//                }
//                listBloqueadoVia.add(nuax);
//            } else {
//                boolean ok = true;
//                for (NovedadPrgTcAux np : listBloqueadoVia) {
//                    if (np.getObj().getIdNovedad().getIdNovedad().equals(n.getIdNovedad().getIdNovedad())) {
////                        int fromStop = n.getIdPrgTc().getFromStop().getIdPrgStoppoint();
////                        int toStop = n.getIdPrgTc().getToStop().getIdPrgStoppoint();
////                        BigDecimal distancia = prgTcEJB.findDistandeByFromStopAndToStop(fromStop, toStop);
//                        BigDecimal distancia = n.getDistancia();
//                        if (n.getEstadoOperacion() != null) {
//                            if (n.getEstadoOperacion() == 5 || n.getEstadoOperacion() == 8 || n.getEstadoOperacion() == 99) {
//                                if (distancia != null) {
//                                    int index = listBloqueadoVia.indexOf(np);
//                                    BigDecimal add = listBloqueadoVia.get(index).getKmPerdido().add(distancia);
//                                    listBloqueadoVia.get(index).setKmPerdido(add);
//                                }
//                            }
//                        }
//                        ok = false;
//                    }
//                }
//                if (ok) {
//                    NovedadPrgTcAux nuax = new NovedadPrgTcAux();
//                    nuax.setObj(n);
//                    if (n.getEstadoOperacion() != null) {
//
//                        if (n.getEstadoOperacion() == 5 || n.getEstadoOperacion() == 8 || n.getEstadoOperacion() == 99) {
////                        int fromStop = n.getIdPrgTc().getFromStop().getIdPrgStoppoint();
////                        int toStop = n.getIdPrgTc().getToStop().getIdPrgStoppoint();
////                        BigDecimal distancia = prgTcEJB.findDistandeByFromStopAndToStop(fromStop, toStop);
//                            BigDecimal distancia = n.getDistancia();
//                            if (distancia != null) {
//                                nuax.setKmPerdido(distancia);
//                            } else {
//                                nuax.setKmPerdido(BigDecimal.ZERO);
//                            }
//                        } else {
//                            nuax.setKmPerdido(BigDecimal.ZERO);
//                        }
//                    } else {
//                        nuax.setKmPerdido(BigDecimal.ZERO);
//                    }
//                    listBloqueadoVia.add(nuax);
//                }
//            }
//        }
//    }
    public String alarStyleClass(Integer p) throws ParseException {
        if (p == null) {
            return null;
        }
        if (p == 2) {
            return "rowOrangeStyle";
        }
        if (p == 5 || p == 8) {
            return "rowRedStyle";
        }
        if (p == 7) {
            return "rowGrisOscuroStyle";
        }
        if (p == 6) {
            return "rowTurquesaStyle";
        }
        if (p == 3 || p == 4) {
            return "rowBlueStyle";
        }
        return null;
    }

    public String estadoOperacion(Integer p) throws ParseException {
        if (p == null) {
            return null;
        }
        if (p == 2) {
            return "EJECUTADO PARCIAL";
        }
        if (p == 5 || p == 8) {
            return "ELIMINADO";
        }
        if (p == 7) {
            return "VAC";
        }
        if (p == 6) {
            return "VACCOM";
        }
        if (p == 3 || p == 4) {
            return "ADICIONAL";
        }
        return null;
    }

    public String fecha(int opc) {
        if (opc == 1) {
            return Util.dateFormat(desde);
        } else {
            return Util.dateFormat(hasta);
        }
    }

    public NovedadPrgTcFacadeLocal getNovTcejb() {
        return novTcejb;
    }

    public void setNovTcejb(NovedadPrgTcFacadeLocal novTcejb) {
        this.novTcejb = novTcejb;
    }

    public List<NovedadesTQ04> getListBloqueadoVia() {
        return listBloqueadoVia;
    }

    public void setListBloqueadoVia(List<NovedadesTQ04> listBloqueadoVia) {
        this.listBloqueadoVia = listBloqueadoVia;
    }

    public List<NovedadPrgTc> getList() {
        return list;
    }

    public void setList(List<NovedadPrgTc> list) {
        this.list = list;
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

}
