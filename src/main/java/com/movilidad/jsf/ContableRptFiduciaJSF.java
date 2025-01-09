/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ContableCtaFacadeLocal;
import com.movilidad.ejb.ContableCtaVehiculoFacadeLocal;
import com.movilidad.ejb.ContableRptFiduciaDetFacadeLocal;
import com.movilidad.ejb.ContableRptFiduciaDistFacadeLocal;
import com.movilidad.ejb.ContableRptFiduciaFacadeLocal;
import com.movilidad.ejb.KmConciliadoFacadeLocal;
import com.movilidad.model.ContableCtaVehiculo;
import com.movilidad.model.ContableRptFiducia;
import com.movilidad.model.ContableRptFiduciaDet;
import com.movilidad.model.ContableRptFiduciaDist;
import com.movilidad.model.KmConciliado;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Permite gestionar toda los datos para el objeto ContableRptFiducia,
 * ContableRptFiduciaDet, ContableRptFiduciaDist. Tablas afectadas
 * contable_rpt_fiducia, contable_rpt_fiducia_det, contable_rpt_fiducia_dist
 *
 * @author cesar
 */
@Named(value = "contableRptFiduciaJSF")
@ViewScoped
public class ContableRptFiduciaJSF implements Serializable {

    @EJB
    private ContableRptFiduciaFacadeLocal contableRptFiduciaFacadeLocal;
    @EJB
    private ContableRptFiduciaDetFacadeLocal contableRptFiduciaDetFacadeLocal;
    @EJB
    private ContableRptFiduciaDistFacadeLocal contableRptFiduciaDistFacadeLocal;
    @EJB
    private ContableCtaFacadeLocal contableCtaFacadeLocal;
    @EJB
    private ContableCtaVehiculoFacadeLocal contableCtaVehiculoFacadeLocal;
    @EJB
    private KmConciliadoFacadeLocal kmConciliadoFacadeLocal;

    private ContableRptFiducia contableRptFiducia;
    private ContableRptFiduciaDet contableRptFiduciaDet;
    private ContableRptFiduciaDist contableRptFiduciaDist;
    private List<ContableRptFiducia> listContableRptFiducia;
    private List<ContableRptFiduciaDet> listContableRptFiduciaDet;
    private List<ContableRptFiduciaDist> listContableRptFiduciaDist;
    private List<String> listErrores;

    private Map<Integer, ContableCtaVehiculo> mapContableCtaVehiculo;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private Date dIni;
    private Date dFin;
    private Integer idContableCta;
    private Integer idContableCtaVeh;
    private boolean bDistribuido;
    private boolean bEditDet;
    private double kmetrosArt;
    private double kmetrosBi;
    private double vlTotalArt;
    private double vlTotalBi;
    // private boolean bEditDist;

    /**
     * Creates a new instance of ContableRptFiduciaJSF
     */
    public ContableRptFiduciaJSF() {
    }

    @PostConstruct
    public void init() {
        dIni = new Date();
        dFin = new Date();
        listContableRptFiduciaDet = new ArrayList<>();
        listContableRptFiduciaDist = new ArrayList<>();
        listErrores = new ArrayList<>();
        idContableCta = null;
        idContableCtaVeh = null;
        contableRptFiducia = null;
        contableRptFiduciaDet = null;
        contableRptFiduciaDist = null;
        bDistribuido = false;
        kmetrosArt = 0;
        kmetrosBi = 0;
        vlTotalArt = 0;
        vlTotalBi = 0;
        bEditDet = false;
        //   bEditDist = false;
    }

    //  @Transactional
    public void guardar() throws ParseException {
        if (contableRptFiducia.getDesde().after(contableRptFiducia.getHasta())) {
            MovilidadUtil.addErrorMessage("Fecha Desde no puede ser superior a Fecha Hasta");
            return;
        }
        if (verificarProceso(contableRptFiducia.getDesde(), 0)) {
            MovilidadUtil.addErrorMessage("Ya existe Reporte Fiducia con esta fecha");
            return;
        }
        if (verificarProceso(contableRptFiducia.getHasta(), 0)) {
            MovilidadUtil.addErrorMessage("Ya existe Reporte Fiducia con esta fecha");
            return;
        }
        if (listContableRptFiduciaDist.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se ha realizado el proceso de distribución");
            return;
        }
        contableRptFiducia.setDistribuido(bDistribuido ? 1 : 0);
        contableRptFiducia.setCreado(new Date());
        contableRptFiducia.setModificado(new Date());
        contableRptFiducia.setEstadoReg(0);
        contableRptFiducia.setUsername(user.getUsername());
        contableRptFiducia
                .setTotal(contableRptFiducia.getIngresosArt()
                        .add(contableRptFiducia.getIngresosBi())
                        .subtract((contableRptFiducia.getEgresosArt()
                                .add(contableRptFiducia.getEgresosBi()))));
        listContableRptFiduciaDet.forEach(crfd -> {
            crfd.setIdContableRptFiducia(contableRptFiducia);
        });
        listContableRptFiduciaDist.forEach(crfdit -> {
            crfdit.setIdContableRptFiducia(contableRptFiducia);
        });
        contableRptFiducia.setContableRptFiduciaDetList(listContableRptFiduciaDet);
        contableRptFiducia.setContableRptFiduciaDistList(listContableRptFiduciaDist);
        contableRptFiduciaFacadeLocal.create(contableRptFiducia);
        listContableRptFiduciaDet.clear();
        listContableRptFiduciaDist.clear();
        prepareGuardar();
        bDistribuido = false;
        MovilidadUtil.addSuccessMessage("Reporte Fiducia Contable creado correctamente");
    }

    @Transactional
    public void actualizar() throws ParseException {
        if (contableRptFiducia.getDesde().after(contableRptFiducia.getHasta())) {
            MovilidadUtil.addErrorMessage("Fecha Desde no puede ser superior a Fecha Hasta");
            return;
        }
        if (verificarProceso(contableRptFiducia.getDesde(), contableRptFiducia.getIdContableRptFiducia())) {
            MovilidadUtil.addErrorMessage("Ya existe Reporte Fiducia con esta fecha");
            return;
        }
        if (verificarProceso(contableRptFiducia.getHasta(), contableRptFiducia.getIdContableRptFiducia())) {
            MovilidadUtil.addErrorMessage("Ya existe Reporte Fiducia con esta fecha");
            return;
        }
        contableRptFiducia.setDistribuido(bDistribuido ? 1 : 0);
        contableRptFiducia.setModificado(new Date());
        contableRptFiducia
                .setTotal(contableRptFiducia.getIngresosArt()
                        .add(contableRptFiducia.getIngresosBi())
                        .subtract((contableRptFiducia.getEgresosArt()
                                .add(contableRptFiducia.getEgresosBi()))));
        listContableRptFiduciaDet.forEach(crfd -> {
            crfd.setIdContableRptFiducia(contableRptFiducia);
        });
//        listContableRptFiduciaDist.forEach(crfdit -> {
//            crfdit.setIdContableRptFiducia(contableRptFiducia);
//        });
        contableRptFiducia.setContableRptFiduciaDetList(listContableRptFiduciaDet);
//        contableRptFiducia.setContableRptFiduciaDistList(listContableRptFiduciaDist);
        contableRptFiduciaFacadeLocal.edit(contableRptFiducia);
        listContableRptFiduciaDet.clear();
        listContableRptFiduciaDist.clear();
        kmetrosArt = 0;
        kmetrosBi = 0;
        vlTotalArt = 0;
        vlTotalBi = 0;
        bDistribuido = false;
        MovilidadUtil.addSuccessMessage("Reporte Fiducia Contable actualizado correctamente");
        PrimeFaces.current().executeScript("PF('modalDlg').hide()");
    }

    public void onContableRptFiducia(ContableRptFiducia crf) {
        contableRptFiducia = crf;
        listContableRptFiduciaDist = crf.getContableRptFiduciaDistList();
        listContableRptFiduciaDet = crf.getContableRptFiduciaDetList();
        bDistribuido = crf.getDistribuido().equals(1);
    }

    public void prepareGuardar() {
        init();
        contableRptFiducia = new ContableRptFiducia();
        mapContableCtaVehiculo = new HashMap<>();
        List<ContableCtaVehiculo> listContableCtaVehiculo = contableCtaVehiculoFacadeLocal.findAllEstadoReg();
        listContableCtaVehiculo.forEach(lccv -> {
            mapContableCtaVehiculo.put(lccv.getIdVehiculo().getIdVehiculo(), lccv);
        });
    }

    public void nuevoContRptFidDet() {
        contableRptFiduciaDet = new ContableRptFiduciaDet();
        idContableCta = null;
        bEditDet = false;
    }

    public void nuevoContRptFidDist() {
        contableRptFiduciaDist = new ContableRptFiduciaDist();
        idContableCtaVeh = null;
    }

    public void agregarContRptFidDet() {
        if (idContableCta == null) {
            MovilidadUtil.addErrorMessage("Cuenta es requerido");
            return;
        }
        if (existCuentaContableByDest(idContableCta)) {
            MovilidadUtil.addErrorMessage("La Cuenta ya está registrada");
            return;
        }
        contableRptFiduciaDet.setIdContableCta(contableCtaFacadeLocal.find(idContableCta));
        contableRptFiduciaDet.setCreado(new Date());
        contableRptFiduciaDet.setModificado(new Date());
        contableRptFiduciaDet.setEstadoReg(0);
        contableRptFiduciaDet.setUsername(user.getUsername());
        listContableRptFiduciaDet.add(contableRptFiduciaDet);
        nuevoContRptFidDet();
        MovilidadUtil.addSuccessMessage("Detalle Reporte Fiducia agregado a la lista correctamente");
    }

    public void prepareEditarContRptFidDet(ContableRptFiduciaDet crfd) {
        contableRptFiduciaDet = crfd;
        idContableCta = crfd.getIdContableCta().getIdContableCta();
        bEditDet = true;
    }

    public void editarContRptFidDet() {
        if (idContableCta == null) {
            MovilidadUtil.addErrorMessage("Cuenta es requerido");
            return;
        }
        if (!contableRptFiduciaDet.getIdContableCta().getIdContableCta().equals(idContableCta)) {
            if (existCuentaContableByDest(idContableCta)) {
                MovilidadUtil.addErrorMessage("La Cuenta ya está registrada");
                return;
            }
        }
        contableRptFiduciaDet.setIdContableCta(contableCtaFacadeLocal.find(idContableCta));
        contableRptFiduciaDet.setModificado(new Date());
        if (contableRptFiduciaDet.getIdContableRptFiduciaDet() != null) {
            contableRptFiduciaDetFacadeLocal.edit(contableRptFiduciaDet);
        }
        listContableRptFiduciaDet.remove(contableRptFiduciaDet);
        listContableRptFiduciaDet.add(contableRptFiduciaDet);
        MovilidadUtil.addSuccessMessage("Detalle Reporte Fiducia agregado a la lista correctamente");
        PrimeFaces.current().executeScript("PF('modalDetDlg').hide()");
    }

    public void prepareEditarContRptFidDist(ContableRptFiduciaDist crfd) {
        contableRptFiduciaDist = crfd;
        idContableCtaVeh = crfd.getIdContableCtaVehiculo().getIdContableCtaVehiculo();
    }

    public void onEliminarContRptFidDet(ContableRptFiduciaDet crfd) {
        if (crfd.getIdContableRptFiduciaDet() != null) {
            contableRptFiduciaDetFacadeLocal.remove(crfd);
        }
        listContableRptFiduciaDet.remove(crfd);
        MovilidadUtil.addSuccessMessage("Detalle Reporte Fiducia eliminado correctamente");
    }

    public void onEliminarContRptFidDist(ContableRptFiduciaDist crfd) {
        if (crfd.getIdContableRptFiduciaDist() != null) {
            contableRptFiduciaDistFacadeLocal.remove(crfd);
        }
        listContableRptFiduciaDist.remove(crfd);
        MovilidadUtil.addSuccessMessage("Detalle Fiducia Vehículo eliminado correctamente");
    }

    public void onRowlClckSelect(final SelectEvent event) throws Exception {
        contableRptFiducia = (ContableRptFiducia) event.getObject();
    }

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        if (contableRptFiducia != null) {
            if (contableRptFiducia.getPathDocumento() != null) {
                Util.deleteFile(contableRptFiducia.getPathDocumento());
            }
            String path = Util.saveFile(file, contableRptFiducia.getIdContableRptFiducia(), "reporteFiducia");
            contableRptFiducia.setPathDocumento(path);
            contableRptFiduciaFacadeLocal.edit(contableRptFiducia);
            contableRptFiducia = null;
            MovilidadUtil.addSuccessMessage("Documento cargado correctamente");
        } else {
            MovilidadUtil.addErrorMessage("No ha realizado la carga del documento");
        }
        MovilidadUtil.hideModal("upDoc");
    }

    public void distribuirKmVehiculos() {
        kmetrosArt = 0;
        kmetrosBi = 0;
        vlTotalArt = 0;
        vlTotalBi = 0;
        if (contableRptFiducia.getDesde().after(contableRptFiducia.getHasta())) {
            MovilidadUtil.addErrorMessage("Fecha Desde no puede ser superior a Fecha Hasta");
            return;
        }
        listErrores.clear();
        listContableRptFiduciaDist.clear();
        List<ContableRptFiduciaDist> listContaRptFidDistArt = new ArrayList<>();
        List<ContableRptFiduciaDist> listContaRptFidDistBi = new ArrayList<>();
        boolean bEmpresa = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_NOMBRE_EMPRESA).equals("BMO"); // true BMO, false CNX
        if (contableRptFiducia != null) {
            List<KmConciliado> listKmConciliados = kmConciliadoFacadeLocal
                    .findAllByFechas(contableRptFiducia.getDesde(),
                            contableRptFiducia.getHasta());
            if (listKmConciliados != null && !listKmConciliados.isEmpty()) {
                BigDecimal ingresosArt = contableRptFiducia.getIngresosArt();
                BigDecimal ingresosBi = contableRptFiducia.getIngresosBi();
                // ARTICULADO
                List<KmConciliado> listKmConciliadosArt = listKmConciliados
                        .stream()
                        .filter(kmc -> kmc.getIdVehiculo().getIdVehiculoTipo().getIdVehiculoTipo().equals(1))
                        .collect(Collectors.toList());
                //   System.out.println(listKmConciliadosArt.size());
                //   double kmsArt;
                if (bEmpresa) {
                    kmetrosArt = listKmConciliadosArt
                            .stream()
                            .mapToInt(kmc -> kmc.getKmComercial()) // ---------------
                            .sum();
                } else {
                    kmetrosArt = listKmConciliadosArt
                            .stream()
                            .mapToDouble(kmc -> kmc.getKmCom235().doubleValue()) // ---------------
                            .sum();
                }
                //    System.out.println(kmsArt);
                BigDecimal valorKmArt = ingresosArt
                        .divide(new BigDecimal(kmetrosArt), MathContext.DECIMAL128
                        );
                if (kmetrosArt > 0) {
                    kmetrosArt = kmetrosArt / 1000;
                }
                //      System.out.println(valorKmArt);
                contableRptFiducia.setVrKmArt(valorKmArt);
                listKmConciliadosArt.forEach(kmArt -> {
                    ContableCtaVehiculo ccv = mapContableCtaVehiculo.get(kmArt.getIdVehiculo().getIdVehiculo());
                    if (ccv != null) {
                        ContableRptFiduciaDist crfd = new ContableRptFiduciaDist();
                        if (bEmpresa) {
                            crfd.setValor(new BigDecimal(kmArt.getKmComercial()).multiply(valorKmArt)); // ---------------
                            crfd.setKmAplicado(new BigDecimal(kmArt.getKmComercial())); // ---------------
                        } else {
                            crfd.setValor(kmArt.getKmCom235().multiply(valorKmArt)); // ---------------
                            crfd.setKmAplicado(kmArt.getKmCom235()); // ---------------
                        }
                        crfd.setIdContableCtaVehiculo(ccv);
                        crfd.setIdContableRptFiducia(contableRptFiducia);
                        listContaRptFidDistArt.add(crfd);
                    } else {
                        listErrores.add("Vehículo " + kmArt.getIdVehiculo().getCodigo() + " no tiene una cuenta asociada");
                    }
                });
                double sumDistArt = listContaRptFidDistArt
                        .stream()
                        .mapToDouble(tdf -> tdf.getValor().doubleValue())
                        .sum();
                if (!listContaRptFidDistArt.isEmpty()) {
                    if (sumDistArt != ingresosArt.doubleValue()) {
                        BigDecimal subtract = ingresosArt.subtract(new BigDecimal(sumDistArt)).abs();
                        ContableRptFiduciaDist get = listContaRptFidDistArt.get(listContaRptFidDistArt.size() - 1);
                        get.setValor(get.getValor().add(subtract));
                    }
                    vlTotalArt = listContaRptFidDistArt
                            .stream()
                            .mapToDouble(tdf -> tdf.getValor().doubleValue())
                            .sum();
                }

                // BIARTICULADO
                List<KmConciliado> listKmConciliadosBi = listKmConciliados
                        .stream()
                        .filter(kmc -> kmc.getIdVehiculo().getIdVehiculoTipo().getIdVehiculoTipo().equals(2))
                        .collect(Collectors.toList());
                //    double kmsBi;
                if (bEmpresa) {
                    kmetrosBi = listKmConciliadosBi
                            .stream()
                            .mapToInt(kmc -> kmc.getKmComercial()) // ---------------
                            .sum();
                } else {
                    kmetrosBi = listKmConciliadosBi
                            .stream()
                            .mapToDouble(kmc -> kmc.getKmCom235().doubleValue()) // ---------------
                            .sum();
                }
                BigDecimal valorKmBi = ingresosBi
                        .divide(new BigDecimal(kmetrosBi), MathContext.DECIMAL128
                        );
                if (kmetrosBi > 0) {
                    kmetrosBi = kmetrosBi / 1000;
                }
                contableRptFiducia.setVrKmBi(valorKmBi);
                listKmConciliadosBi.forEach(kmBi -> {
                    ContableCtaVehiculo ccv = mapContableCtaVehiculo.get(kmBi.getIdVehiculo().getIdVehiculo());
                    if (ccv != null) {
                        ContableRptFiduciaDist crfd = new ContableRptFiduciaDist();
                        if (bEmpresa) {
                            crfd.setValor(new BigDecimal(kmBi.getKmComercial()).multiply(valorKmBi)); // ---------------
                            crfd.setKmAplicado(new BigDecimal(kmBi.getKmComercial())); // ---------------
                        } else {
                            crfd.setValor(kmBi.getKmCom235().multiply(valorKmBi)); // ---------------
                            crfd.setKmAplicado(kmBi.getKmCom235()); // ---------------
                        }
                        crfd.setIdContableCtaVehiculo(ccv);
                        crfd.setIdContableRptFiducia(contableRptFiducia);
                        listContaRptFidDistBi.add(crfd);
                    } else {
                        listErrores.add("Vehículo " + kmBi.getIdVehiculo().getCodigo() + " no tiene una cuenta asociada");
                    }
                });
                double sumDistBi = listContaRptFidDistBi
                        .stream()
                        .mapToDouble(tdf -> tdf.getValor().doubleValue())
                        .sum();
                if (!listContaRptFidDistBi.isEmpty()) {
                    if (sumDistBi != ingresosBi.doubleValue()) {
                        BigDecimal subtract = ingresosBi.subtract(new BigDecimal(sumDistBi)).abs();
                        ContableRptFiduciaDist get = listContaRptFidDistBi.get(listContaRptFidDistBi.size() - 1);
                        get.setValor(get.getValor().add(subtract));
                    }
                    vlTotalBi = listContaRptFidDistBi
                            .stream()
                            .mapToDouble(tdf -> tdf.getValor().doubleValue())
                            .sum();
                }
                listContableRptFiduciaDist = Stream
                        .concat(listContaRptFidDistArt.stream(), listContaRptFidDistBi.stream())
                        .collect(Collectors.toList());
                listContableRptFiduciaDist = listContableRptFiduciaDist.stream()
                        .sorted((o1, o2) -> o1.getIdContableCtaVehiculo()
                        .getIdVehiculo()
                        .getIdVehiculo()
                        .compareTo(o2.getIdContableCtaVehiculo()
                                .getIdVehiculo()
                                .getIdVehiculo()))
                        .collect(Collectors.toList());
            }
        }
    }

    boolean verificarProceso(Date d, Integer idContRptFid) {
        List<ContableRptFiducia> ltsContablesRptFiducia = contableRptFiduciaFacadeLocal.getContablesRptFiduciaByDate(d, idContRptFid);
        return ltsContablesRptFiducia != null && !ltsContablesRptFiducia.isEmpty();
    }

    boolean existCuentaContableByDest(Integer id) {
        return listContableRptFiduciaDet
                .stream()
                .filter((crfd) -> (crfd.getIdContableCta() != null))
                .anyMatch((crfd) -> (crfd.getIdContableCta().getIdContableCta().equals(id)));
    }

    boolean existCuentaContableByDist(Integer id) {
        return listContableRptFiduciaDist
                .stream()
                .filter((crfd) -> (crfd.getIdContableCtaVehiculo() != null))
                .anyMatch((crfd) -> (crfd.getIdContableCtaVehiculo().getIdContableCtaVehiculo().equals(id)));
    }

    public void buacarReportesFiducia() throws ParseException {
//        if (MovilidadUtil.fechasIgualMenorMayor(dIni, dFin) == 0) {
//            MovilidadUtil.addErrorMessage("Fecha Inicio no puede ser superior a Fecha Fin");
//            return;
//        }
        listContableRptFiducia = contableRptFiduciaFacadeLocal.findAllRangoFechaEstadoReg(dIni, dFin);
    }

    public ContableRptFiducia getContableRptFiducia() {
        return contableRptFiducia;
    }

    public void setContableRptFiducia(ContableRptFiducia contableRptFiducia) {
        this.contableRptFiducia = contableRptFiducia;
    }

    public ContableRptFiduciaDet getContableRptFiduciaDet() {
        return contableRptFiduciaDet;
    }

    public void setContableRptFiduciaDet(ContableRptFiduciaDet contableRptFiduciaDet) {
        this.contableRptFiduciaDet = contableRptFiduciaDet;
    }

    public ContableRptFiduciaDist getContableRptFiduciaDist() {
        return contableRptFiduciaDist;
    }

    public void setContableRptFiduciaDist(ContableRptFiduciaDist contableRptFiduciaDist) {
        this.contableRptFiduciaDist = contableRptFiduciaDist;
    }

    public List<ContableRptFiducia> getListContableRptFiducia() {
        return listContableRptFiducia;
    }

    public void setListContableRptFiducia(List<ContableRptFiducia> listContableRptFiducia) {
        this.listContableRptFiducia = listContableRptFiducia;
    }

    public List<ContableRptFiduciaDet> getListContableRptFiduciaDet() {
        return listContableRptFiduciaDet;
    }

    public void setListContableRptFiduciaDet(List<ContableRptFiduciaDet> listContableRptFiduciaDet) {
        this.listContableRptFiduciaDet = listContableRptFiduciaDet;
    }

    public List<ContableRptFiduciaDist> getListContableRptFiduciaDist() {
        return listContableRptFiduciaDist;
    }

    public void setListContableRptFiduciaDist(List<ContableRptFiduciaDist> listContableRptFiduciaDist) {
        this.listContableRptFiduciaDist = listContableRptFiduciaDist;
    }

    public Date getdIni() {
        return dIni;
    }

    public void setdIni(Date dIni) {
        this.dIni = dIni;
    }

    public Date getdFin() {
        return dFin;
    }

    public void setdFin(Date dFin) {
        this.dFin = dFin;
    }

    public Integer getIdContableCta() {
        return idContableCta;
    }

    public void setIdContableCta(Integer idContableCta) {
        this.idContableCta = idContableCta;
    }

    public Integer getIdContableCtaVeh() {
        return idContableCtaVeh;
    }

    public void setIdContableCtaVeh(Integer idContableCtaVeh) {
        this.idContableCtaVeh = idContableCtaVeh;
    }

    public boolean isbDistribuido() {
        return bDistribuido;
    }

    public void setbDistribuido(boolean bDistribuido) {
        this.bDistribuido = bDistribuido;
    }

    public boolean isbEditDet() {
        return bEditDet;
    }

    public void setbEditDet(boolean bEditDet) {
        this.bEditDet = bEditDet;
    }

    public List<String> getListErrores() {
        return listErrores;
    }

    public void setListErrores(List<String> listErrores) {
        this.listErrores = listErrores;
    }

    public double getKmetrosArt() {
        return kmetrosArt;
    }

    public void setKmetrosArt(double kmetrosArt) {
        this.kmetrosArt = kmetrosArt;
    }

    public double getKmetrosBi() {
        return kmetrosBi;
    }

    public void setKmetrosBi(double kmetrosBi) {
        this.kmetrosBi = kmetrosBi;
    }

    public double getVlTotalArt() {
        return vlTotalArt;
    }

    public void setVlTotalArt(double vlTotalArt) {
        this.vlTotalArt = vlTotalArt;
    }

    public double getVlTotalBi() {
        return vlTotalBi;
    }

    public void setVlTotalBi(double vlTotalBi) {
        this.vlTotalBi = vlTotalBi;
    }

}
