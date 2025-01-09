/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.util.beans.DiasSinOperar;
import com.movilidad.util.beans.KmsVehiculo;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
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
@Named(value = "diasSinOperarBean")
@ViewScoped
public class DiasSinOperarBean implements Serializable {

    @EJB
    private PrgTcFacadeLocal prgTcEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<DiasSinOperar> lstKmsVehiculos;

    private Date fecha;
    private Date fechaFin;

    @PostConstruct
    public void init() {
        fecha = MovilidadUtil.fechaHoy();
        fechaFin = MovilidadUtil.fechaHoy();
    }

    public void obtenerKmByVehiculo() {
        if (Util.validarFechaCambioEstado(fecha, fechaFin)) {
            MovilidadUtil.addErrorMessage("La fecha inicio no puede ser mayor a la fecha final");
            PrimeFaces.current().ajax().update(":msgs");
            return;
        }

        lstKmsVehiculos = prgTcEjb.obtenerDiasSinOperar(fecha, fechaFin,
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (lstKmsVehiculos == null || lstKmsVehiculos.isEmpty()) {
            MovilidadUtil.addErrorMessage("No existen registros para ése rango de fechas");
            PrimeFaces.current().ajax().update(":msgs");
        }
    }

    public void postProcessXLSVehiculo(Object document) {

        XSSFWorkbook wb = (XSSFWorkbook) document;
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow header = sheet.getRow(0);

        wb.setSheetName(0, "DIAS SIN OPERAR");

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
                            && cell.getColumnIndex() == 3) {
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

    public List<DiasSinOperar> getLstKmsVehiculos() {
        return lstKmsVehiculos;
    }

    public void setLstKmsVehiculos(List<DiasSinOperar> lstKmsVehiculos) {
        this.lstKmsVehiculos = lstKmsVehiculos;
    }

}
