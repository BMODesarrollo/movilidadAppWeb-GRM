/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.ejb.UsersFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.PrgTc;
import com.movilidad.model.Users;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.math.BigDecimal;
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
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Cesar
 */
@Named(value = "prgTcEjecutado")
@ViewScoped
public class PrgTcEjecutado implements Serializable {

    @EJB
    private PrgTcFacadeLocal prgTcFacadeLocal;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    private List<PrgTc> tcEjecutada;

    private Integer tipoServicio;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private Date fecha;

    @PostConstruct
    public void init() {
        fecha = MovilidadUtil.fechaHoy();
        tipoServicio = null;
    }

    public void consultarporSalidas() {
        if (fecha != null) {
            tcEjecutada = prgTcFacadeLocal.findServiciosEjecutadosByFechaAndUnidadFuncional(fecha, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), tipoServicio);
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
                            && cell.getColumnIndex() == 15) {
                        cell.setCellValue(new BigDecimal(cell.getStringCellValue().replace("'", "")).doubleValue());
                    }
                }
            }
        }
    }

    public List<PrgTc> getTcEjecutada() {
        return tcEjecutada;
    }

    public void setTcEjecutada(List<PrgTc> tcEjecutada) {
        this.tcEjecutada = tcEjecutada;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(Integer tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

}
