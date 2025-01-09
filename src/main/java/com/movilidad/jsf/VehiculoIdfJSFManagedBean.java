package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.ejb.VehiculoIdfFacadeLocal;
import com.movilidad.model.Vehiculo;
import com.movilidad.model.VehiculoIdf;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "vehiculoIdfJSFManagedBean")
@ViewScoped
public class VehiculoIdfJSFManagedBean implements Serializable {

    @EJB
    private VehiculoFacadeLocal vehiculoEjb;
    @EJB
    private VehiculoIdfFacadeLocal vehiculoIdfEjb;

    private VehiculoIdf vehiculoIdf;
    private Vehiculo vehiculo;
    private UploadedFile uploadedFile;
    private Date fechaI;
    private Date fechaF;

    private List<VehiculoIdf> lstVehiculoIdf;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @Transactional
    public void cargarIdf() throws IOException {
        try {
            Date fechaInicio = null;
            Date fechaFin = null;
            List<VehiculoIdf> lstVehiculoIdfAux = new ArrayList<>();
            String auxCodigo = "";
            double auxKm = 0;

            if (uploadedFile != null) {
                String path = Util.saveFile(uploadedFile, 0, "vehiculo_idf");
                FileInputStream file = new FileInputStream(new File(path));

                XSSFWorkbook wb = new XSSFWorkbook(file);
                XSSFSheet sheet = wb.getSheetAt(0);

                int numFilas = sheet.getLastRowNum();

                for (int a = 0; a <= numFilas; a++) {
                    Row fila = sheet.getRow(a);
                    int numCols = fila.getLastCellNum();
                    vehiculoIdf = new VehiculoIdf();

                    for (int b = 0; b < numCols; b++) {
                        Cell celda = fila.getCell(b);
                        if (celda != null) {

                            switch (celda.getCellTypeEnum().toString()) {
                                case "NUMERIC":
                                    if (DateUtil.isCellDateFormatted(celda)) {
                                        if (b == 0) {
                                            fechaInicio = Util.toDate(Util.dateFormat(celda.getDateCellValue()));
                                        }
                                        fechaFin = Util.toDate(Util.dateFormat(celda.getDateCellValue()));
                                    } else {
                                        auxKm = celda.getNumericCellValue();
//                                        System.out.println(celda.getNumericCellValue());
                                    }
                                    break;
                                case "STRING":
                                    auxCodigo = celda.getStringCellValue();
                                    break;
                            }
                        }
                    }

                    if (!validarCamposExcel(fechaInicio, fechaFin, auxCodigo)) {

                        if (Util.validarFechaCambioEstado(fechaInicio, fechaFin)) {
                            MovilidadUtil.addErrorMessage("Existen campos con fecha de inicio mayor a la fecha fin. ( " + auxCodigo.toUpperCase() + " )");
                            PrimeFaces.current().ajax().update(":frmCargaIdf");
                            return;
                        }

                        if (!vehiculoIdfEjb.verificarSubida(fechaInicio, fechaFin)) {
                            if (Util.deleteFile(path)) {
                                vehiculoIdf = null;
                                fechaInicio = null;
                                fechaFin = null;
                                auxCodigo = "";
                                auxKm = 0;
                                this.uploadedFile = null;
                                MovilidadUtil.addErrorMessage("Ya existen registros cargados para ese rango de fechas.");
                                PrimeFaces.current().ajax().update(":frmCargaIdf");
                                return;
                            }
                        }

                        vehiculoIdf.setFecha(fechaInicio);
                        vehiculoIdf.setFecha_fin(fechaFin);
                        vehiculoIdf.setIdVehiculo(vehiculoEjb.getVehiculo(auxCodigo,0));
                        vehiculoIdf.setKm(BigDecimal.valueOf(auxKm * 1000));
                        vehiculoIdf.setPathDocumento(path);
                        vehiculoIdf.setUsername(user.getUsername());
                        vehiculoIdf.setCreado(new Date());
                        lstVehiculoIdfAux.add(vehiculoIdf);
                    } else {
                        MovilidadUtil.addErrorMessage("Error al cargar archivo ( Se encontró una celda en blanco ó con un formato incorrecto) ");
                        PrimeFaces.current().ajax().update(":frmCargaIdf");
                        return;
                    }
                }
                PrimeFaces current = PrimeFaces.current();

                this.lstVehiculoIdf = lstVehiculoIdfAux;
                current.ajax().update(":frmVehiculosIdf:dtVehiculoIdf");
                this.uploadedFile = null;
                PrimeFaces.current().ajax().update(":frmCargaIdf");
                if (Util.deleteFile(path)) {
                    for (VehiculoIdf v : lstVehiculoIdfAux) {
                        vehiculoIdfEjb.create(v);
                    }
                }
                MovilidadUtil.addSuccessMessage("Archivo cargado correctamente");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VehiculoIdfJSFManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscar() {
        if (!validarBusquedaPorFechas()) {
            lstVehiculoIdf = vehiculoIdfEjb.findByRangoFecha(fechaI, fechaF);

            if (lstVehiculoIdf == null) {
                MovilidadUtil.addErrorMessage("No se encontraron registros para ese rango de fechas.");
                PrimeFaces.current().ajax().update(":frmCargaIdf");
            }
        }
    }

    private boolean validarBusquedaPorFechas() {
        if (fechaI == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar fecha de inicio.");
            PrimeFaces.current().ajax().update(":frmCargaIdf");
            return true;
        }
        if (fechaF == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar fecha fin.");
            PrimeFaces.current().ajax().update(":frmCargaIdf");
            return true;
        }
        if (Util.validarFechaCambioEstado(fechaI, fechaF)) {
            MovilidadUtil.addErrorMessage("La fecha de inicio no debe ser mayor a la fecha fin");
            PrimeFaces.current().ajax().update(":frmCargaIdf");
            return true;
        }

        return false;
    }

    private boolean validarCamposExcel(Date fechaInicio, Date fechaFin, String auxCodigo) {
        if (fechaInicio == null) {
            return true;
        }
        if (fechaFin == null) {
            return true;
        }
        if (auxCodigo == null || auxCodigo.equals("")) {
            return true;
        }
        return false;
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            this.uploadedFile = event.getFile();
            cargarIdf();
        } catch (IOException ex) {
            Logger.getLogger(VehiculoIdfJSFManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public VehiculoIdfFacadeLocal getVehiculoIdfEjb() {
        return vehiculoIdfEjb;
    }

    public void setVehiculoIdfEjb(VehiculoIdfFacadeLocal vehiculoIdfEjb) {
        this.vehiculoIdfEjb = vehiculoIdfEjb;
    }

    public VehiculoFacadeLocal getVehiculoEjb() {
        return vehiculoEjb;
    }

    public void setVehiculoEjb(VehiculoFacadeLocal vehiculoEjb) {
        this.vehiculoEjb = vehiculoEjb;
    }

    public VehiculoIdf getVehiculoIdf() {
        return vehiculoIdf;
    }

    public void setVehiculoIdf(VehiculoIdf vehiculoIdf) {
        this.vehiculoIdf = vehiculoIdf;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public List<VehiculoIdf> getLstVehiculoIdf() {
        return lstVehiculoIdf;
    }

    public void setLstVehiculoIdf(List<VehiculoIdf> lstVehiculoIdf) {
        this.lstVehiculoIdf = lstVehiculoIdf;
    }

    public Date getFechaI() {
        return fechaI;
    }

    public void setFechaI(Date fechaI) {
        this.fechaI = fechaI;
    }

    public Date getFechaF() {
        return fechaF;
    }

    public void setFechaF(Date fechaF) {
        this.fechaF = fechaF;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

}
