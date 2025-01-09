/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.util.beans.KmsOperador;
import com.movilidad.util.beans.KmsVehiculo;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "kilometroBean")
@ViewScoped
public class KilometroJSFManagedBean implements Serializable {

    @EJB
    private PrgTcFacadeLocal prgTcEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<KmsOperador> lstKmsOperadores;
    private List<KmsVehiculo> lstKmsVehiculos;

    private Date fecha;
    private Date fechaFin;

    @PostConstruct
    public void init() {
        fecha = new Date();
        fechaFin = new Date();
        lstKmsOperadores = new ArrayList<>();
        lstKmsVehiculos = new ArrayList<>();
    }

    public boolean validarUrlBMO() {
        String urlComparar = Util.URL_BMO;
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getRequestURL().toString();
        return url.contains(urlComparar);
    }

    public void obtenerKmByOperador() {
        if (Util.validarFechaCambioEstado(fecha, fechaFin)) {
            MovilidadUtil.addErrorMessage("La fecha inicio no puede ser mayor a la fecha final");
            PrimeFaces.current().ajax().update(":msgs");
            return;
        }

        lstKmsOperadores = prgTcEjb.getKmByOperador(fecha, fechaFin,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (lstKmsOperadores == null) {
            MovilidadUtil.addErrorMessage("No existen resgistros para ése rango de fechas");
            PrimeFaces.current().ajax().update(":msgs");
        }
    }

    public void obtenerKmByVehiculo() {
        if (Util.validarFechaCambioEstado(fecha, fechaFin)) {
            MovilidadUtil.addErrorMessage("La fecha inicio no puede ser mayor a la fecha final");
            PrimeFaces.current().ajax().update(":msgs");
            return;
        }

        Calendar current = Calendar.getInstance();
        current.setTime(fecha);
        List<KmsVehiculo> dia;//= new LinkedList<String>();     
        lstKmsVehiculos = new ArrayList<>();
        while (!current.getTime().after(fechaFin)) {
            dia = new LinkedList<>();
            dia = prgTcEjb.getKmByVehiculo(current.getTime(), current.getTime(),
                    unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            lstKmsVehiculos.addAll(dia);
//            System.out.println(current.getTime());
            current.add(Calendar.DATE, +1);

        }

//        lstKmsVehiculos = prgTcEjb.getKmByVehiculo(fecha, fechaFin);
        if (lstKmsVehiculos == null) {
            MovilidadUtil.addErrorMessage("No existen registros para ése rango de fechas");
            PrimeFaces.current().ajax().update(":msgs");
        } else {

        }
    }

    public void llenarFechas() {
        List<KmsVehiculo> lstKmsVehiculos;
        int c = 0;
        Locale locale = new Locale("es", "CO");
        Calendar current = Calendar.getInstance(locale);
        current.setTime(fecha);
        current.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        current.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        while (c <= 6) {
//            System.out.println(Util.dateFormat(current.getTime()));
            current.add(Calendar.DATE, 1);
            c++;
        }
    }

    public void clearFiltersOperador() {
        fecha = new Date();
        fechaFin = new Date();
        lstKmsOperadores = prgTcEjb.getKmByOperador(fecha, fechaFin,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        PrimeFaces.current().executeScript("PF('dtkmByOperadorlist').clearFilters()");
    }

    public void clearFiltersVehiculo() {
        fecha = new Date();
        fechaFin = new Date();
        lstKmsVehiculos = prgTcEjb.getKmByVehiculo(fecha, fechaFin,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        PrimeFaces.current().executeScript("PF('dtkmByVehiculolist').clearFilters()");
    }

    public void postProcessXLSOp(Object document) {

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
                            && cell.getColumnIndex() >= 2) {
                        cell.setCellValue(new BigDecimal(cell.getStringCellValue().replace("'", "")).doubleValue());
                    }
                }
            }
        }
    }

    public void postProcessXLSVehiculo(Object document) {

        XSSFWorkbook wb = (XSSFWorkbook) document;
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow header = sheet.getRow(0);

        wb.setSheetName(0, "KILOMETROS POR VEHICULO");

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
                            && cell.getColumnIndex() >= 2) {
                        cell.setCellValue(new BigDecimal(cell.getStringCellValue().replace("'", "")).doubleValue());
                    }
                }
            }
        }
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public PrgTcFacadeLocal getPrgTcEjb() {
        return prgTcEjb;
    }

    public void setPrgTcEjb(PrgTcFacadeLocal prgTcEjb) {
        this.prgTcEjb = prgTcEjb;
    }

    public List<KmsOperador> getLstKmsOperadores() {
        return lstKmsOperadores;
    }

    public void setLstKmsOperadores(List<KmsOperador> lstKmsOperadores) {
        this.lstKmsOperadores = lstKmsOperadores;
    }

    public List<KmsVehiculo> getLstKmsVehiculos() {
        return lstKmsVehiculos;
    }

    public void setLstKmsVehiculos(List<KmsVehiculo> lstKmsVehiculos) {
        this.lstKmsVehiculos = lstKmsVehiculos;
    }

}
