package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.ejb.KmConciliadoFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.PrgTcResumenFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.Vehiculo;
import com.movilidad.model.KmConciliado;
import com.movilidad.model.PrgTc;
import com.movilidad.model.PrgTcResumen;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.KmsComercial;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "kmConciliadoBean")
@ViewScoped
public class KmMttoJSFManagedBean implements Serializable {

    @EJB
    private VehiculoFacadeLocal vehiculoEjb;
    @EJB
    private KmConciliadoFacadeLocal kmConciliadoEjb;
    @EJB
    private PrgTcResumenFacadeLocal prgTcResumenEjb;
    @EJB
    private PrgTcFacadeLocal prgTcEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private KmConciliado kmConciliado;
    private Vehiculo vehiculo;
    private UploadedFile uploadedFile;
    private Date fecha;

    private List<KmConciliado> lstKmConciliado;
    private List<KmConciliado> lstKmHlpArt;
    private List<KmConciliado> lstKmHlpBi;
    private List<KmConciliado> lstKmConsolidadoArt;
    private List<KmConciliado> lstKmConsolidadoBi;
    private StreamedContent file;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        fecha = new Date();
        lstKmConciliado = kmConciliadoEjb.findAll(fecha,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void consultar() {
        lstKmConciliado = kmConciliadoEjb.findAll(fecha,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    @Transactional
    public void cargarKmMtto() throws IOException {
        try {
            int idGopUnidadFuncional = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
            Date auxDate = null;
            PrgTcResumen resumen;
            List<KmConciliado> lstKmConciliadoAux = new ArrayList<>();
            String auxCodigo;
            double auxKm;
            PrimeFaces current = PrimeFaces.current();

            if (uploadedFile != null) {
                String path = Util.saveFile(uploadedFile, 0, "kmMtto");
                FileInputStream file = new FileInputStream(new File(path));

                XSSFWorkbook wb = new XSSFWorkbook(file);
                XSSFSheet sheet = wb.getSheetAt(0);

                int numFilas = sheet.getLastRowNum();

                for (int a = 0; a <= numFilas; a++) {
                    Row fila = sheet.getRow(a);
                    int numCols = fila.getLastCellNum();
                    kmConciliado = new KmConciliado();
                    auxCodigo = null;
                    auxKm = 0;

                    for (int b = 0; b < numCols; b++) {
                        Cell celda = fila.getCell(b);
                        if (celda != null) {

                            switch (celda.getCellTypeEnum().toString()) {
                                case "NUMERIC":
                                    if (DateUtil.isCellDateFormatted(celda)) {
                                        auxDate = Util.toDate(Util.dateFormat(celda.getDateCellValue()));
                                    } else {
                                        auxKm = celda.getNumericCellValue();
                                        System.out.println("KILOMETRAJE: " + auxKm);
                                    }
                                    break;
                                case "STRING":
                                    if (!celda.getStringCellValue().contains("MOVIL") && celda.getStringCellValue() != null) {
                                        auxCodigo = celda.getStringCellValue();
                                        System.out.println("VEHICULO: " + auxCodigo);
                                    }
                                    break;
                            }
                        }
                    }

                    resumen = prgTcResumenEjb.isConciliado(auxDate, idGopUnidadFuncional);
                    if (resumen == null) {
                        if (Util.deleteFile(path)) {
                            kmConciliado = null;
                            auxCodigo = null;
                            auxKm = 0;
                            this.uploadedFile = null;
                            MovilidadUtil.addErrorMessage("No existe programación para el día: " + Util.dateFormat(auxDate));
                            auxDate = null;
                            current.ajax().update(":frmCargaKmConciliado");
                            return;
                        }
                    } else if (resumen.getConciliado() == null || resumen.getConciliado() == 0 || resumen.getConciliado() == 2) {
                        if (Util.deleteFile(path)) {
                            kmConciliado = null;
                            auxCodigo = null;
                            auxKm = 0;
                            this.uploadedFile = null;
                            MovilidadUtil.addErrorMessage("El día: " + Util.dateFormat(auxDate) + " NO ha sido conciliado.");
                            auxDate = null;
                            current.ajax().update(":frmCargaKmConciliado");
                            return;
                        }
                    }

                    if (auxCodigo == null && a > 0) {
                        MovilidadUtil.addErrorMessage("Se han encontrado código de vehículos SIN ingresar. Por favor revise la plantilla.");
                        kmConciliado = null;
                        auxCodigo = null;
                        auxKm = 0;
                        this.uploadedFile = null;
                        auxDate = null;
                        current.ajax().update(":frmCargaKmConciliado");
                        return;
                    }

                    if (auxKm < 0) {
                        MovilidadUtil.addErrorMessage("El kilometraje debe ser mayor a cero. (" + auxCodigo + ")");
                        kmConciliado = null;
                        auxCodigo = null;
                        auxKm = 0;
                        this.uploadedFile = null;
                        auxDate = null;
                        current.ajax().update(":frmCargaKmConciliado");
                        return;
                    }

                    if (!kmConciliadoEjb.verificarSubida(auxDate, idGopUnidadFuncional)) {
                        if (Util.deleteFile(path)) {
                            kmConciliado = null;
                            auxDate = null;
                            auxCodigo = null;
                            auxKm = 0;
                            this.uploadedFile = null;
                            MovilidadUtil.addErrorMessage("Ya existen registros cargados para esa fecha ");
                            current.ajax().update(":frmCargaKmConciliado");
                            return;
                        }
                    }

                    if (auxDate != null && auxCodigo != null && auxKm >= 0) {
                        if (a > 0) {
                            Vehiculo vehiculoExcel = vehiculoEjb.getVehiculo(auxCodigo.trim(), idGopUnidadFuncional);

                            if (vehiculoExcel == null) {
                                if (Util.deleteFile(path)) {
                                    MovilidadUtil.addErrorMessage("Al ingresar kilometraje. Verifique si los valores de kilometraje y códigos de vehículos, tengan el formato ó valores correctos. ( " + auxCodigo + " )");
                                    kmConciliado = null;
                                    this.uploadedFile = null;
                                    auxKm = 0;
                                    auxCodigo = null;
                                    auxDate = null;
                                    current.ajax().update(":frmCargaKmConciliado");
                                    return;
                                }
                            }
                            kmConciliado.setFecha(auxDate);
                            kmConciliado.setKmComercial(0);

                            if (idGopUnidadFuncional > 0) {
                                kmConciliado.setIdGopUnidadFuncional(new GopUnidadFuncional(idGopUnidadFuncional));
                            }

                            kmConciliado.setKmHlp(0);
                            kmConciliado.setKmRecorrido(0);
                            kmConciliado.setIdVehiculo(vehiculoExcel);
                            kmConciliado.setKmMtto((int) auxKm * 1000);
                            kmConciliado.setUsername(user.getUsername());
                            kmConciliado.setCreado(new Date());
                            kmConciliado.setEstadoReg(0);
                            lstKmConciliadoAux.add(kmConciliado);
                        }
                    }
                }
                this.lstKmConciliado = lstKmConciliadoAux;
                current.ajax().update(":frmKmConciliado:dtKmConciliado");
                this.uploadedFile = null;
                if (Util.deleteFile(path)) {
                    for (KmConciliado v : lstKmConciliadoAux) {
                        kmConciliadoEjb.create(v);
                    }
                }
                MovilidadUtil.addSuccessMessage("Archivo cargado correctamente");
                fecha = auxDate;
                current.ajax().update(":frmCargaKmConciliado");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KmMttoJSFManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void postProcessXLS(Object document) {

        XSSFWorkbook wb = (XSSFWorkbook) document;
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow header = sheet.getRow(0);

        Iterator<Row> rowIterator = sheet.iterator();

        if (rowIterator.hasNext()) {
            rowIterator.next();
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getColumnIndex() > 1) {

                    if (!cell.getStringCellValue().isEmpty()
                            && (cell.getColumnIndex() >= 4 && cell.getColumnIndex() <= 5)) {
                        cell.setCellValue(new BigDecimal(cell.getStringCellValue().replace("'", "")).doubleValue());
                    }
                }
            }
        }
    }

    public void generarReporte() throws FileNotFoundException {
        long hlpEjecutadoArt = 0;
        long hlpEjecutadoBi = 0;
        int idGopUnidadFuncional = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
        PrgTcResumen prgTcResumen = prgTcResumenEjb.findByFecha(fecha, idGopUnidadFuncional);
        List<PrgTc> eliminados = prgTcEjb.findEliminadosByFecha(fecha, idGopUnidadFuncional);
        List<PrgTc> adicionales = prgTcEjb.findAdicionalesByFecha(fecha, idGopUnidadFuncional);
        if (prgTcResumen == null) {
            MovilidadUtil.addErrorMessage("No hay programación para " + Util.dateFormat(fecha));
            return;
        }
        if (prgTcResumen.getConciliado() == null || prgTcResumen.getConciliado() == 0 || prgTcResumen.getConciliado() == 2) {
            MovilidadUtil.addErrorMessage("El día: " + Util.dateFormat(fecha) + " NO ha sido conciliado");
            return;
        }
        if (fecha == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una fecha");
            return;
        }
        for (KmConciliado km : lstKmConciliado) {
            if (km.getIdVehiculo().getIdVehiculoTipo().getIdVehiculoTipo() == 1) {
                hlpEjecutadoArt += km.getKmHlp();
            } else {
                hlpEjecutadoBi += km.getKmHlp();
            }
        }

        prgTcResumen.setMhlpArtEje(BigDecimal.valueOf(hlpEjecutadoArt));
        prgTcResumen.setMhlpBiEje(BigDecimal.valueOf(hlpEjecutadoBi));
        prgTcResumenEjb.edit(prgTcResumen);

        Map parametros = new HashMap();
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        plantilla = plantilla + "CumpleDiario.xlsx";
        parametros.put("kmPrgArt", prgTcResumen.getMcomArtPrg());
        parametros.put("kmPrgBi", prgTcResumen.getMcomBiPrg());
        parametros.put("kmEjeArt", prgTcResumen.getMcomArtCon());
        parametros.put("kmEjeBi", prgTcResumen.getMcomBiCon());
        parametros.put("kmHlpPrgArt", prgTcResumen.getMhlpArtPrg());
        parametros.put("kmHlpPrgBi", prgTcResumen.getMhlpBiPrg());
        parametros.put("fecha", Util.dateFormat(fecha));
        parametros.put("kms", lstKmConciliado);
        parametros.put("registros", lstKmConciliado.size());
        parametros.put("perdidos", eliminados.size());
        parametros.put("ganados", adicionales.size());
        parametros.put("prgTcResumen", prgTcResumen);
        destino = destino + "CumpleDiario_" + Util.dateFormat(fecha) + ".xlsx";

        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        InputStream stream = new FileInputStream(excel);
        file = new DefaultStreamedContent(stream, "text/plain", "CumpleDiario_" + Util.dateFormat(fecha) + ".xlsx");
    }

    public void cargarKmsConciliados() {
        lstKmConciliado = kmConciliadoEjb.findAll(fecha, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (lstKmConciliado.isEmpty()) {
            MovilidadUtil.addErrorMessage("No existen registros cargados para esa fecha");
            PrimeFaces.current().ajax().update(":frmCargaKmConciliado:messages");
        }
    }

    @Transactional
    public void cargarKmEjecutado() {
        //Borrar KMComerciales=0,  hlp=0
        int suma = 0;
        int idGopUnidadFuncional = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
        List<KmsComercial> kmComerciales = kmConciliadoEjb.getKmComerciales(fecha, idGopUnidadFuncional);

        if (kmComerciales == null || kmComerciales.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron kms comerciales para la fecha: " + Util.dateFormat(fecha));
            PrimeFaces.current().ajax().update(":msgs");
            return;
        }

        for (KmsComercial km : kmComerciales) {
            kmConciliadoEjb.updateKmComercial(km.getCodigo_vehiculo(), km.getComercial().intValue(), fecha, idGopUnidadFuncional);
            suma += km.getComercial().intValue();
        }
        System.out.println("Total ejecutado: " + suma);
        kmConciliadoEjb.updateKmHlp(fecha, idGopUnidadFuncional);
        cargarKmsConciliados();
        MovilidadUtil.addSuccessMessage("Kilómetros ejecutados cargados éxitosamente");
        PrimeFaces.current().ajax().update("frmCargaKmConciliado");
        PrimeFaces.current().ajax().update(":frmKmConciliado");
    }

    @Transactional
    public void eliminarKmMtto() {
        kmConciliadoEjb.removerKmMtto(fecha, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        lstKmConciliado = null;
        MovilidadUtil.addSuccessMessage("Datos borrados éxitosamente");
    }

    public void calcular() {
        int idGopUnidadFuncional = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
        int kmComRedisArt = 0;
        int kmComRedisBi = 0;
        int kmComTmpArt = 0;
        int kmComTmpBi = 0;
        int contArt = 0;
        int contBi = 0;
        int sumHlpArt = 0;
        int sumHlpBi = 0;

//        int kmHlp235TmpArt=0;
//        int kmHlp235TmpBi=0;
        lstKmHlpArt = kmConciliadoEjb.getKmHlp(fecha, 1, idGopUnidadFuncional);
        lstKmHlpBi = kmConciliadoEjb.getKmHlp(fecha, 2, idGopUnidadFuncional);

        //<editor-fold defaultstate="collapsed" desc="Suavizado HLP Negativo">
        for (KmConciliado art : lstKmHlpArt) {
            if (art.getKmMtto() > 0) {
                int kmComTmp = (int) (art.getKmMtto() * 0.005);
                kmComTmpArt = art.getKmMtto() - kmComTmp;
                kmComRedisArt += (art.getKmComercial() - kmComTmpArt);
                art.setKmComercial(kmComTmpArt);
                art.setKmHlp(art.getKmMtto() - art.getKmComercial());
                kmConciliadoEjb.edit(art);
//                System.out.println("Redis Art - (" + art.getIdVehiculo().getCodigo() + "): " + kmComRedisArt);
            } else {
                kmComRedisArt += art.getKmComercial();
                art.setKmComercial(0);
                art.setKmHlp(0);
                art.setKmCom235(Util.CERO);
                art.setKmHlp235(Util.CERO);
                kmConciliadoEjb.edit(art);
//                System.out.println("Redis Art Km_Mtto (0) - (" + art.getIdVehiculo().getCodigo() + "): " + kmComRedisArt);
            }
        }
        //Final
        for (KmConciliado bi : lstKmHlpBi) {
            if (bi.getKmMtto() > 0) {
                int kmComTmp = (int) (bi.getKmMtto() * 0.005);
                kmComTmpBi = bi.getKmMtto() - kmComTmp;
                kmComRedisBi += (bi.getKmComercial() - kmComTmpBi);
                bi.setKmComercial(kmComTmpBi);
                bi.setKmHlp(bi.getKmMtto() - bi.getKmComercial());
                kmConciliadoEjb.edit(bi);
//                System.out.println("Redis Bi - (" + bi.getIdVehiculo().getCodigo() + "): " + kmComRedisBi);
            } else {
                kmComRedisBi += bi.getKmComercial();
                bi.setKmComercial(0);
                bi.setKmHlp(0);
                bi.setKmCom235(Util.CERO);
                bi.setKmHlp235(Util.CERO);
                kmConciliadoEjb.edit(bi);
//                System.out.println("Redis Bi Km_Mtto (0) - (" + bi.getIdVehiculo().getCodigo() + "): " + kmComRedisBi);
            }
        }

//</editor-fold>
        //Nuevo
        lstKmConsolidadoArt = kmConciliadoEjb.getKmHlpConsolidado(fecha, 1, idGopUnidadFuncional);
        lstKmConsolidadoBi = kmConciliadoEjb.getKmHlpConsolidado(fecha, 2, idGopUnidadFuncional);

//        System.out.println("Tamaño lista :" + lstKmConsolidadoArt.size());
        if (lstKmConsolidadoArt.isEmpty()) {
            return;
        }
        if (lstKmConsolidadoBi.isEmpty()) {
            return;
        }

        kmComTmpArt = 0;
        kmComTmpBi = 0;

//        System.out.println("kmComRedisArt : " + kmComRedisArt);
//        System.out.println("KmComRedisBi : " + kmComRedisBi);
        // 
        while (kmComRedisArt > 0) {
//            System.out.println("En el while");
            for (KmConciliado c_Art : lstKmConsolidadoArt) {

                if (kmComRedisArt > 0) {
//                    System.out.println("En el for Art");
                    kmComTmpArt = ((int) (c_Art.getKmHlp() * 0.7));
//                    System.out.println("KmComTmpArt : " + kmComTmpArt);
                    if (kmComTmpArt < kmComRedisArt && kmComTmpArt > 0) {
                        c_Art.setKmComercial(c_Art.getKmComercial() + kmComTmpArt);
                        kmComRedisArt -= kmComTmpArt;
//                        System.out.println("nuevo comercial - (" + c_Art.getIdVehiculo().getCodigo() + "): " + c_Art.getKmComercial());
                    } else if (kmComTmpArt > kmComRedisArt) {
                        c_Art.setKmComercial(c_Art.getKmComercial() + kmComRedisArt);
//                        System.out.println("nuevo comercial else - (" + c_Art.getIdVehiculo().getCodigo() + "): " + c_Art.getKmComercial());
                        kmComRedisArt = 0;
                    }
                    c_Art.setKmHlp(c_Art.getKmMtto() - c_Art.getKmComercial());
                    kmConciliadoEjb.edit(c_Art);
                }
            }
//            System.out.println("kmComRedisArt : " + kmComRedisArt);
            contArt++;
//            System.out.println("CONT_ART: "+contArt);
            if (contArt == 100) {
                break;
            }
//            System.out.println("Art: " + contArt);
        }

        // final
//        System.out.println("Arranca Bi");
        while (kmComRedisBi > 0) {
            for (KmConciliado c_Bi : lstKmConsolidadoBi) {
                if (kmComRedisBi > 0) {
//                    System.out.println("En el for Bi");
                    kmComTmpBi = ((int) (c_Bi.getKmHlp() * 0.7));
//                    System.out.println("KmComTmpBi : " + kmComTmpBi);
                    if (kmComTmpBi < kmComRedisBi && kmComTmpBi > 0) {
                        c_Bi.setKmComercial(c_Bi.getKmComercial() + kmComTmpBi);
                        kmComRedisBi -= kmComTmpBi;
//                        System.out.println("nuevo comercial - (" + c_Bi.getIdVehiculo().getCodigo() + "): " + c_Bi.getKmComercial());
                    } else if (kmComTmpBi > kmComRedisBi) {
                        c_Bi.setKmComercial(c_Bi.getKmComercial() + kmComRedisBi);
//                        System.out.println("nuevo comercial else - (" + c_Bi.getIdVehiculo().getCodigo() + "): " + c_Bi.getKmComercial());
                        kmComRedisBi = 0;
                    }
                    c_Bi.setKmHlp(c_Bi.getKmMtto() - c_Bi.getKmComercial());
                    kmConciliadoEjb.edit(c_Bi);
                }
            }
//            System.out.println("KmComRedisBi : " + kmComRedisBi);
            contBi++;
//            System.out.println("CONT_BI: "+ contBi);
            if (contBi == 100) {
                break;
            }
//            System.out.println("Bi: " + contBi);
        }

        lstKmConsolidadoArt = kmConciliadoEjb.getKmComConsolidado(fecha, 1, idGopUnidadFuncional);
        lstKmConsolidadoBi = kmConciliadoEjb.getKmComConsolidado(fecha, 2, idGopUnidadFuncional);
        double kmCom235Art = 0;
        double kmCom235Bi = 0;

        PrgTcResumen resumen = prgTcResumenEjb.findByFecha(fecha, idGopUnidadFuncional);
        if (resumen != null) {
            BigDecimal totalConciliado = resumen.getMcomArtCon().add(resumen.getMcomBiCon());
            BigDecimal kmMtto = kmConciliadoEjb.getKmMtto(fecha, idGopUnidadFuncional);

            if (totalConciliado.compareTo(kmMtto) == 1) {
                PrimeFaces.current().ajax().update(":frmCargaKmConciliado:messages");
                MovilidadUtil.addErrorMessage("El kilometraje conciliado NO puede ser mayor al odómetro");
                return;
            }
        }

//        kmComConArt y Bi > kmMttoArt y KmmttoBi;
        // Suavizado 235
        for (KmConciliado art : lstKmConsolidadoArt) {
            if (art.getKmMtto() > 0) {
                // Porción suavizado 235
                if (art.getKmComercial() > 0) {
                    BigDecimal kmCom235TmpArt = BigDecimal.valueOf((art.getKmComercial() * 1.0235));
//                    if (art.getKmHlp() - (art.getKmComercial() * 0.0235) < 0) {
                    if ((BigDecimal.valueOf(art.getKmMtto()).subtract(kmCom235TmpArt)).compareTo(Util.CERO) == -1) {
//                        art.setKmCom235(kmCom235TmpArt);
//                        art.setKmHlp235(BigDecimal.valueOf(1000));
//                        kmHlp235Art += (art.getKmComercial() * 0.0235) - 1000;
                        art.setKmCom235(BigDecimal.valueOf((art.getKmMtto() - 1000)));
                        art.setKmHlp235(BigDecimal.valueOf(1000));
                        kmCom235Art += kmCom235TmpArt.subtract(art.getKmCom235()).doubleValue();
//                        System.out.println("1|" + kmCom235Art + "|" + art.getIdVehiculo().getCodigo() + "|" + art.getKmMtto() + "|" + art.getKmCom235() + "|" + art.getKmHlp235());
                    } else if ((BigDecimal.valueOf(art.getKmMtto()).subtract(kmCom235TmpArt)).compareTo(Util.CERO) == 1) {
                        art.setKmCom235(kmCom235TmpArt);
//                        art.setKmHlp235(BigDecimal.valueOf(art.getKmHlp() - (art.getKmComercial() * 0.0235)));
                        art.setKmHlp235(BigDecimal.valueOf(art.getKmMtto()).subtract(kmCom235TmpArt));
                        if (kmCom235Art > 0 && art.getKmHlp235().compareTo(BigDecimal.valueOf(kmCom235Art)) > 0) {
                            art.setKmCom235(art.getKmCom235().add(BigDecimal.valueOf(kmCom235Art)));//**//
                            art.setKmHlp235(art.getKmHlp235().subtract(BigDecimal.valueOf(kmCom235Art)));
//                            art.setKmHlp235(BigDecimal.valueOf(art.getKmMtto()).subtract(BigDecimal.valueOf(kmCom235Art)));
                            kmCom235Art = 0;
                        }
//                        System.out.println("2|" + kmCom235Art + "|" + art.getIdVehiculo().getCodigo() + "|" + art.getKmMtto() + "|" + art.getKmCom235() + "|" + art.getKmHlp235());
                    }
                } else {
                    art.setKmCom235(Util.CERO);
                    art.setKmHlp235(BigDecimal.valueOf(art.getKmHlp()));
//                    kmConciliadoEjb.edit(art);
                }
                kmConciliadoEjb.edit(art);
//                System.out.println("Redis Art - (" + art.getIdVehiculo().getCodigo() + "): " + kmComRedisArt);
            }
        }
        for (KmConciliado bi : lstKmConsolidadoBi) {
            if (bi.getKmMtto() > 0) {
                if (bi.getKmComercial() > 0) {
                    BigDecimal kmCom235TmpBi = BigDecimal.valueOf(bi.getKmComercial() * 1.0235);
//                    if (bi.getKmHlp() - (bi.getKmComercial() * 0.0235) < 0) {
                    if ((BigDecimal.valueOf(bi.getKmMtto()).subtract(kmCom235TmpBi)).compareTo(Util.CERO) == -1) {
//                        bi.setKmCom235(kmCom235TmpBi);
//                        bi.setKmHlp235(BigDecimal.valueOf(1000));
//                        kmCom235Bi += (bi.getKmComercial() * 0.0235) - 1000;
                        bi.setKmCom235(BigDecimal.valueOf((bi.getKmMtto() - 1000)));
                        bi.setKmHlp235(BigDecimal.valueOf(1000));
                        kmCom235Art += kmCom235TmpBi.subtract(bi.getKmCom235()).doubleValue();
                    } else if ((BigDecimal.valueOf(bi.getKmMtto()).subtract(kmCom235TmpBi)).compareTo(Util.CERO) == 1) {
                        bi.setKmCom235(kmCom235TmpBi);
//                        bi.setKmHlp235(BigDecimal.valueOf(bi.getKmHlp() - (bi.getKmComercial() * 0.0235)));
                        bi.setKmHlp235(BigDecimal.valueOf(bi.getKmMtto()).subtract(kmCom235TmpBi));
                        if (kmCom235Bi > 0 && bi.getKmHlp235().compareTo(BigDecimal.valueOf(kmCom235Bi)) > 0) {
                            bi.setKmCom235(bi.getKmCom235().add(BigDecimal.valueOf(kmCom235Bi)));//**//
                            bi.setKmHlp235(bi.getKmHlp235().subtract(BigDecimal.valueOf(kmCom235Bi)));
                            kmCom235Bi = 0;
                        }
                    }
                } else {
                    bi.setKmCom235(Util.CERO);
                    bi.setKmHlp235(BigDecimal.valueOf(bi.getKmHlp()));
//                    kmConciliadoEjb.edit(bi);
                }
                kmConciliadoEjb.edit(bi);
//                System.out.println("Redis Bi - (" + bi.getIdVehiculo().getCodigo() + "): " + kmComRedisBi);
            }
        }

        if (kmCom235Art > 0) {
            int contador = 0;
            List<KmConciliado> lstKmHlp235 = null;
//            System.out.println("kmCom235Art : " + kmCom235Art);
            while (kmCom235Art > 0 || contador < 100) {
                lstKmHlp235 = kmConciliadoEjb.getKmHlp235(fecha, 1);
                for (KmConciliado kart : lstKmHlp235) {
                    if (kart.getKmHlp235().doubleValue() > kmCom235Art) {
//                        System.out.println("PRIMER IF");
                        kart.setKmCom235(kart.getKmCom235().add(BigDecimal.valueOf(kmCom235Art)));
                        kart.setKmHlp235(kart.getKmHlp235().subtract(BigDecimal.valueOf(kmCom235Art)));
                        kmCom235Art = 0;
                        kmConciliadoEjb.edit(kart);
                    } else if (kart.getKmHlp235().doubleValue() > (kmCom235Art * 0.3)) {
//                        System.out.println("SEGUNDO IF");
                        kart.setKmCom235(kart.getKmCom235().add(BigDecimal.valueOf(kmCom235Art * 0.3)));
                        kart.setKmHlp235(kart.getKmHlp235().subtract(BigDecimal.valueOf(kmCom235Art * 0.3)));
                        kmCom235Art = kmCom235Art - (kmCom235Art * 0.3);
                        kmConciliadoEjb.edit(kart);
                    } else if (kart.getKmHlp235().doubleValue() > (kmCom235Art * 0.2)) {
//                        System.out.println("TERCER IF");
                        kart.setKmCom235(kart.getKmCom235().add(BigDecimal.valueOf(kmCom235Art * 0.2)));
                        kart.setKmHlp235(kart.getKmHlp235().subtract(BigDecimal.valueOf(kmCom235Art * 0.2)));
                        kmCom235Art = kmCom235Art - (kmCom235Art * 0.2);
                        kmConciliadoEjb.edit(kart);
                    } else if (kart.getKmHlp235().doubleValue() > (kmCom235Art * 0.1)) {
//                        System.out.println("CUARTO IF");
                        kart.setKmCom235(kart.getKmCom235().add(BigDecimal.valueOf(kmCom235Art * 0.1)));
                        kart.setKmHlp235(kart.getKmHlp235().subtract(BigDecimal.valueOf(kmCom235Art * 0.1)));
                        kmCom235Art = kmCom235Art - (kmCom235Art * 0.1);
                        kmConciliadoEjb.edit(kart);
                    } else if (kart.getKmHlp235().doubleValue() > (kmCom235Art * 0.05)) {
//                        System.out.println("QUINTO IF");
                        kart.setKmCom235(kart.getKmCom235().add(BigDecimal.valueOf(kmCom235Art * 0.05)));
                        kart.setKmHlp235(kart.getKmHlp235().subtract(BigDecimal.valueOf(kmCom235Art * 0.05)));
                        kmCom235Art = kmCom235Art - (kmCom235Art * 0.05);
                        kmConciliadoEjb.edit(kart);
                    } else if (kart.getKmHlp235().doubleValue() > (kmCom235Art * 0.01)) {
//                        System.out.println("SEXTO IF");
                        kart.setKmCom235(kart.getKmCom235().add(BigDecimal.valueOf(kmCom235Art * 0.01)));
                        kart.setKmHlp235(kart.getKmHlp235().subtract(BigDecimal.valueOf(kmCom235Art * 0.01)));
                        kmCom235Art = kmCom235Art - (kmCom235Art * 0.01);
                        kmConciliadoEjb.edit(kart);
                    } else if (kart.getKmHlp235().doubleValue() > (kmCom235Art * 0.005)) {
//                        System.out.println("SEPTIMO IF");
                        kart.setKmCom235(kart.getKmCom235().add(BigDecimal.valueOf(kmCom235Art * 0.005)));
                        kart.setKmHlp235(kart.getKmHlp235().subtract(BigDecimal.valueOf(kmCom235Art * 0.005)));
                        kmCom235Art = kmCom235Art - (kmCom235Art * 0.005);
                        kmConciliadoEjb.edit(kart);
                    }
                }
                contador++;
//                System.out.println("kmCom235Art : " + kmCom235Art);
//                System.out.println("CONTADOR: " + contador);
            }
        }
        if (kmCom235Bi > 0) {
//            System.out.println("kmCom235Bi : " + kmCom235Bi);
            int contador = 0;
            List<KmConciliado> lstKmHlp235 = null;
//            System.out.println("kmCom235Art : " + kmCom235Art);
            while (kmCom235Bi > 0 || contador < 100) {
                lstKmHlp235 = kmConciliadoEjb.getKmHlp235(fecha, 2);
                for (KmConciliado kbi : lstKmHlp235) {
                    if (kbi.getKmHlp235().doubleValue() > kmCom235Bi) {
//                        System.out.println("PRIMER IF");
                        kbi.setKmCom235(kbi.getKmCom235().add(BigDecimal.valueOf(kmCom235Bi)));
                        kbi.setKmHlp235(kbi.getKmHlp235().subtract(BigDecimal.valueOf(kmCom235Bi)));
                        kmCom235Bi = 0;
                        kmConciliadoEjb.edit(kbi);
                    } else if (kbi.getKmHlp235().doubleValue() > (kmCom235Bi * 0.3)) {
//                        System.out.println("SEGUNDO IF");
                        kbi.setKmCom235(kbi.getKmCom235().add(BigDecimal.valueOf(kmCom235Bi * 0.3)));
                        kbi.setKmHlp235(kbi.getKmHlp235().subtract(BigDecimal.valueOf(kmCom235Bi * 0.3)));
                        kmCom235Bi = kmCom235Bi - (kmCom235Bi * 0.3);
                        kmConciliadoEjb.edit(kbi);
                    } else if (kbi.getKmHlp235().doubleValue() > (kmCom235Bi * 0.2)) {
//                        System.out.println("TERCER IF");
                        kbi.setKmCom235(kbi.getKmCom235().add(BigDecimal.valueOf(kmCom235Bi * 0.2)));
                        kbi.setKmHlp235(kbi.getKmHlp235().subtract(BigDecimal.valueOf(kmCom235Bi * 0.2)));
                        kmCom235Bi = kmCom235Bi - (kmCom235Bi * 0.2);
                        kmConciliadoEjb.edit(kbi);
                    } else if (kbi.getKmHlp235().doubleValue() > (kmCom235Bi * 0.1)) {
//                        System.out.println("CUARTO IF");
                        kbi.setKmCom235(kbi.getKmCom235().add(BigDecimal.valueOf(kmCom235Bi * 0.1)));
                        kbi.setKmHlp235(kbi.getKmHlp235().subtract(BigDecimal.valueOf(kmCom235Bi * 0.1)));
                        kmCom235Bi = kmCom235Bi - (kmCom235Bi * 0.1);
                        kmConciliadoEjb.edit(kbi);
                    } else if (kbi.getKmHlp235().doubleValue() > (kmCom235Art * 0.05)) {
//                        System.out.println("QUINTO IF");
                        kbi.setKmCom235(kbi.getKmCom235().add(BigDecimal.valueOf(kmCom235Art * 0.05)));
                        kbi.setKmHlp235(kbi.getKmHlp235().subtract(BigDecimal.valueOf(kmCom235Art * 0.05)));
                        kmCom235Art = kmCom235Art - (kmCom235Art * 0.05);
                        kmConciliadoEjb.edit(kbi);
                    } else if (kbi.getKmHlp235().doubleValue() > (kmCom235Art * 0.01)) {
//                        System.out.println("SEXTO IF");
                        kbi.setKmCom235(kbi.getKmCom235().add(BigDecimal.valueOf(kmCom235Art * 0.01)));
                        kbi.setKmHlp235(kbi.getKmHlp235().subtract(BigDecimal.valueOf(kmCom235Art * 0.01)));
                        kmCom235Art = kmCom235Art - (kmCom235Art * 0.01);
                        kmConciliadoEjb.edit(kbi);
                    } else if (kbi.getKmHlp235().doubleValue() > (kmCom235Art * 0.005)) {
//                        System.out.println("SEPTIMO IF");
                        kbi.setKmCom235(kbi.getKmCom235().add(BigDecimal.valueOf(kmCom235Art * 0.005)));
                        kbi.setKmHlp235(kbi.getKmHlp235().subtract(BigDecimal.valueOf(kmCom235Art * 0.005)));
                        kmCom235Art = kmCom235Art - (kmCom235Art * 0.005);
                        kmConciliadoEjb.edit(kbi);
                    }
                }
                contador++;
//                System.out.println("kmComBi : " + kmCom235Bi);
//                System.out.println("CONTADOR: " + contador);
            }
        }

        MovilidadUtil.addSuccessMessage("Suavizado realizado éxitosamente");
        PrimeFaces.current().ajax().update(":frmCargaKmConciliado:messages");
        cargarKmsConciliados();
    }
    // CALCULAR ORIGINAL
    //<editor-fold defaultstate="collapsed" desc="Calcular Viejo">

    public void calcular2() {
        int idGopUnidadFuncional = unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional();
        int kmComRedisArt = 0;
        int kmComRedisBi = 0;
        int kmComTmpArt = 0;
        int kmComTmpBi = 0;
        int contArt = 0;
        int contBi = 0;
        int sumHlpArt = 0;
        int sumHlpBi = 0;

        lstKmHlpArt = kmConciliadoEjb.getKmHlp(fecha, 1, idGopUnidadFuncional);
        lstKmHlpBi = kmConciliadoEjb.getKmHlp(fecha, 2, idGopUnidadFuncional);

        //<editor-fold defaultstate="collapsed" desc="Suavizado HLP Negativo">
        for (KmConciliado art : lstKmHlpArt) {
            if (art.getKmMtto() > 0) {
                int kmComTmp = (int) (art.getKmMtto() * 0.005);
                kmComTmpArt = art.getKmMtto() - kmComTmp;
                kmComRedisArt += (art.getKmComercial() - kmComTmpArt);
                art.setKmComercial(kmComTmpArt);
                art.setKmHlp(art.getKmMtto() - art.getKmComercial());
                kmConciliadoEjb.edit(art);
//                System.out.println("Redis Art - (" + art.getIdVehiculo().getCodigo() + "): " + kmComRedisArt);
            } else {
                kmComRedisArt += art.getKmComercial();
                art.setKmComercial(0);
                art.setKmHlp(0);
                kmConciliadoEjb.edit(art);
//                System.out.println("Redis Art Km_Mtto (0) - (" + art.getIdVehiculo().getCodigo() + "): " + kmComRedisArt);
            }
        }
        //Final
        for (KmConciliado bi : lstKmHlpBi) {
            if (bi.getKmMtto() > 0) {
                int kmComTmp = (int) (bi.getKmMtto() * 0.005);
                kmComTmpBi = bi.getKmMtto() - kmComTmp;
                kmComRedisBi += (bi.getKmComercial() - kmComTmpBi);
                bi.setKmComercial(kmComTmpBi);
                bi.setKmHlp(bi.getKmMtto() - bi.getKmComercial());
                kmConciliadoEjb.edit(bi);
//                System.out.println("Redis Bi - (" + bi.getIdVehiculo().getCodigo() + "): " + kmComRedisBi);
            } else {
                kmComRedisBi += bi.getKmComercial();
                bi.setKmComercial(0);
                bi.setKmHlp(0);
                kmConciliadoEjb.edit(bi);
//                System.out.println("Redis Bi Km_Mtto (0) - (" + bi.getIdVehiculo().getCodigo() + "): " + kmComRedisBi);
            }
        }

//</editor-fold>
        //Nuevo
        lstKmConsolidadoArt = kmConciliadoEjb.getKmHlpConsolidado(fecha, 1, idGopUnidadFuncional);
        lstKmConsolidadoBi = kmConciliadoEjb.getKmHlpConsolidado(fecha, 2, idGopUnidadFuncional);

//        System.out.println("Tamaño lista :" + lstKmConsolidadoArt.size());
        if (lstKmConsolidadoArt.isEmpty()) {
            return;
        }

        kmComTmpArt = 0;
        kmComTmpBi = 0;

//        System.out.println("kmComRedisArt : " + kmComRedisArt);
//        System.out.println("KmComRedisBi : " + kmComRedisBi);
        //
        while (kmComRedisArt > 0) {
//            System.out.println("En el while");
            for (KmConciliado c_Art : lstKmConsolidadoArt) {

                if (kmComRedisArt > 0) {
//                    System.out.println("En el for Art");
                    kmComTmpArt = ((int) (c_Art.getKmHlp() * 0.7));
//                    System.out.println("KmComTmpArt : " + kmComTmpArt);
                    if (kmComTmpArt < kmComRedisArt && kmComTmpArt > 0) {
                        c_Art.setKmComercial(c_Art.getKmComercial() + kmComTmpArt);
                        kmComRedisArt -= kmComTmpArt;
//                        System.out.println("nuevo comercial - (" + c_Art.getIdVehiculo().getCodigo() + "): " + c_Art.getKmComercial());
                    } else if (kmComTmpArt > kmComRedisArt) {
                        c_Art.setKmComercial(c_Art.getKmComercial() + kmComRedisArt);
//                        System.out.println("nuevo comercial else - (" + c_Art.getIdVehiculo().getCodigo() + "): " + c_Art.getKmComercial());
                        kmComRedisArt = 0;
                    }
                    c_Art.setKmHlp(c_Art.getKmMtto() - c_Art.getKmComercial());
                    kmConciliadoEjb.edit(c_Art);
                }
            }
//            System.out.println("kmComRedisArt : " + kmComRedisArt);
            contArt++;
            if (contArt == 100) {
                break;
            }
//            System.out.println("Art: " + contArt);
        }

        // final
//        System.out.println("Arranca Bi");
        while (kmComRedisBi > 0) {
            for (KmConciliado c_Bi : lstKmConsolidadoBi) {
                if (kmComRedisBi > 0) {
//                    System.out.println("En el for Bi");
                    kmComTmpBi = ((int) (c_Bi.getKmHlp() * 0.7));
//                    System.out.println("KmComTmpBi : " + kmComTmpBi);
                    if (kmComTmpBi < kmComRedisBi && kmComTmpBi > 0) {
                        c_Bi.setKmComercial(c_Bi.getKmComercial() + kmComTmpBi);
                        kmComRedisBi -= kmComTmpBi;
//                        System.out.println("nuevo comercial - (" + c_Bi.getIdVehiculo().getCodigo() + "): " + c_Bi.getKmComercial());
                    } else if (kmComTmpBi > kmComRedisBi) {
                        c_Bi.setKmComercial(c_Bi.getKmComercial() + kmComRedisBi);
//                        System.out.println("nuevo comercial else - (" + c_Bi.getIdVehiculo().getCodigo() + "): " + c_Bi.getKmComercial());
                        kmComRedisBi = 0;
                    }
                    c_Bi.setKmHlp(c_Bi.getKmMtto() - c_Bi.getKmComercial());
                    kmConciliadoEjb.edit(c_Bi);
                }
            }
//            System.out.println("KmComRedisBi : " + kmComRedisBi);
            contBi++;
            if (contBi == 100) {
                break;
            }
//            System.out.println("Bi: " + contBi);
        }
        MovilidadUtil.addSuccessMessage("Suavizado realizado éxitosamente");
        PrimeFaces.current().ajax().update(":frmCargaKmConciliado:messages");
        cargarKmsConciliados();
    }
//</editor-fold>

    public void handleFileUpload(FileUploadEvent event) {
        try {
            this.uploadedFile = event.getFile();
            cargarKmMtto();
        } catch (IOException ex) {
            Logger.getLogger(KmMttoJSFManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public KmConciliadoFacadeLocal getKmConciliadoEjb() {
        return kmConciliadoEjb;
    }

    public void setKmConciliadoEjb(KmConciliadoFacadeLocal kmConciliadoEjb) {
        this.kmConciliadoEjb = kmConciliadoEjb;
    }

    public VehiculoFacadeLocal getVehiculoEjb() {
        return vehiculoEjb;
    }

    public void setVehiculoEjb(VehiculoFacadeLocal vehiculoEjb) {
        this.vehiculoEjb = vehiculoEjb;
    }

    public KmConciliado getKmConciliado() {
        return kmConciliado;
    }

    public void setKmConciliado(KmConciliado kmConciliado) {
        this.kmConciliado = kmConciliado;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public List<KmConciliado> getLstKmConciliado() {
        return lstKmConciliado;
    }

    public void setLstKmConciliado(List<KmConciliado> lstKmConciliado) {
        this.lstKmConciliado = lstKmConciliado;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public List<KmConciliado> getLstKmHlpArt() {
        return lstKmHlpArt;
    }

    public void setLstKmHlpArt(List<KmConciliado> lstKmHlpArt) {
        this.lstKmHlpArt = lstKmHlpArt;
    }

    public List<KmConciliado> getLstKmHlpBi() {
        return lstKmHlpBi;
    }

    public void setLstKmHlpBi(List<KmConciliado> lstKmHlpBi) {
        this.lstKmHlpBi = lstKmHlpBi;
    }

    public List<KmConciliado> getLstKmConsolidadoArt() {
        return lstKmConsolidadoArt;
    }

    public void setLstKmConsolidadoArt(List<KmConciliado> lstKmConsolidadoArt) {
        this.lstKmConsolidadoArt = lstKmConsolidadoArt;
    }

    public List<KmConciliado> getLstKmConsolidadoBi() {
        return lstKmConsolidadoBi;
    }

    public void setLstKmConsolidadoBi(List<KmConciliado> lstKmConsolidadoBi) {
        this.lstKmConsolidadoBi = lstKmConsolidadoBi;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

}
