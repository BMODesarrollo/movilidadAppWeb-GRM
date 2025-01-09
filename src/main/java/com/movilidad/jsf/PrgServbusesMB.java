/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgServbusesFacadeLocal;
import com.movilidad.model.PrgServbuses;
import com.movilidad.util.beans.KmsOperador;
import com.movilidad.util.beans.KmsVehiculo;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Luis Vélez
 */
@Named(value = "prgServbusesMB")
@ViewScoped
public class PrgServbusesMB implements Serializable {

    @EJB
    private PrgServbusesFacadeLocal prgServbusesEjb;

    private List<PrgServbuses> lstPrgServbuses;

    private Date fecha;
    private Date fechaFin;

    @PostConstruct
    public void init() {
        fecha = new Date();
        fechaFin = new Date();
        lstPrgServbuses = new ArrayList<>();
    }

    public void obtenerServbuses() {
        lstPrgServbuses = prgServbusesEjb.findByDate(fecha);
    }

    public void clearFiltersOperador() {
        fecha = MovilidadUtil.fechaCompletaHoy();
        fechaFin = MovilidadUtil.fechaCompletaHoy();
        lstPrgServbuses = prgServbusesEjb.findByDate(fecha);
        PrimeFaces.current().executeScript("PF('dtservbusesList').clearFilters()");
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
                            && cell.getColumnIndex() >= 7) {
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

    public List<PrgServbuses> getLstPrgServbuses() {
        return lstPrgServbuses;
    }

    public void setLstPrgServbuses(List<PrgServbuses> lstPrgServbuses) {
        this.lstPrgServbuses = lstPrgServbuses;
    }
}
