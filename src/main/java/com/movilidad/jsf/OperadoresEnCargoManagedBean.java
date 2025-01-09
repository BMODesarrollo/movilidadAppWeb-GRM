package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.util.beans.InformeOperadores;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "operadoresEnCargoBean")
@ViewScoped
public class OperadoresEnCargoManagedBean implements Serializable {

    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<InformeOperadores> lsInformeOperadores;

    private Date fechaInicio;
    private Date fechaFin;
    private final Locale locale = new Locale("es", "CO");
    private final Calendar current = Calendar.getInstance(locale);

    @PostConstruct
    public void init() {
        llenarFechas();
    }

    public void cargarDatosReporte() {
        if (Util.validarFechaCambioEstado(fechaInicio, fechaFin)) {
            MovilidadUtil.addErrorMessage("La fecha de inicio no puede ser mayor a la fecha fin");
            return;
        }

        PrimeFaces.current().executeScript("PF('wlVInterventoria').clearFilters()");

        lsInformeOperadores = empleadoEjb.getInformeOperadoresEnCargo(fechaInicio,
                fechaFin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (lsInformeOperadores == null || lsInformeOperadores.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron datos para ese rango de fechas");
            return;
        }

        MovilidadUtil.addSuccessMessage("Reporte generado con éxito");
    }

    /**
     * Realiza el cambio de formato a los campos: km realizados antes de
     * exportar excel del informe
     *
     * @param document
     */
    public void postProcessXLS(Object document) {

        XSSFWorkbook wb = (XSSFWorkbook) document;
        XSSFSheet sheet = wb.getSheetAt(0);

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
                            && cell.getColumnIndex() == 8) {
                        cell.setCellValue(new BigDecimal(cell.getStringCellValue().replace("'", "").replace(".", ",")).doubleValue());
                    }
                }
            }
        }
    }

    private void llenarFechas() {
        fechaFin = new Date();
        current.setTime(fechaFin);
        current.add(Calendar.DATE, -1);
        fechaInicio = current.getTime();
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<InformeOperadores> getLsInformeOperadores() {
        return lsInformeOperadores;
    }

    public void setLsInformeOperadores(List<InformeOperadores> lsInformeOperadores) {
        this.lsInformeOperadores = lsInformeOperadores;
    }

}
