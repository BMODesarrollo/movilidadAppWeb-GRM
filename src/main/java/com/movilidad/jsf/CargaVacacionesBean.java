package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.NovedadVacacionesFacadeLocal;
import com.movilidad.model.NovedadVacaciones;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "cargaVacacionesBean")
@ViewScoped
public class CargaVacacionesBean implements Serializable {

    @EJB
    private NovedadVacacionesFacadeLocal novedadVacacionesEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;

    private UploadedFile file;
    private List<NovedadVacaciones> lstNovedades;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        reset();
    }

    private void cargarReporte() throws IOException {
        cargarReporteTransactional();
    }

    /**
     * Realiza el registro de las vacaciones en base de datos ( tabla
     * novedad_vacaciones).
     */
    @Transactional
    private void cargarReporteTransactional() throws IOException {
        NovedadVacaciones novedad;

        try {
            if (file != null) {
                String path = Util.saveFile(file, 0, "kmMtto");
                FileInputStream inputStream = new FileInputStream(new File(path));

                XSSFWorkbook wb = new XSSFWorkbook(inputStream);
                XSSFSheet sheet = wb.getSheetAt(0);

                int numFilas = sheet.getLastRowNum();

                lstNovedades = new ArrayList<>();

                for (int a = 0; a <= numFilas - 1; a++) {
                    Date fechaDesde = null;
                    Date fechaHasta = null;
                    String ccColaborador = null;
                    Row fila = sheet.getRow(a);
                    int numCols = fila.getLastCellNum();
                    novedad = new NovedadVacaciones();

                    for (int b = 0; b <= numCols - 1; b++) {
                        Cell celda = fila.getCell(b);

                        if (celda != null) {
                            switch (celda.getCellTypeEnum().toString()) {
                                case "NUMERIC":
                                    if (DateUtil.isCellDateFormatted(celda)) {
                                        fechaDesde = Util.toDate(Util.dateFormat(fila.getCell(0).getDateCellValue()));
                                        fechaHasta = Util.toDate(Util.dateFormat(fila.getCell(1).getDateCellValue()));
                                    } else {
                                        ccColaborador = BigDecimal.valueOf(fila.getCell(2).getNumericCellValue()).toString();
                                    }
                                    break;
                                case "STRING":
                                    ccColaborador = fila.getCell(2).getStringCellValue();
                                    break;
                            }
                        }

                    }

                    if (fechaDesde != null && fechaHasta != null && ccColaborador != null) {
                        if (a > 0) {

                            String validacion = validarDatos(fechaDesde, fechaHasta, ccColaborador);

                            if (validacion != null) {
                                Util.deleteFile(path);
                                reset();
                                MovilidadUtil.addErrorMessage(validacion);
                                MovilidadUtil.updateComponent("frmCargaVacaciones:messages");
                                MovilidadUtil.updateComponent(":msgs");
                                return;
                            } else {
                                novedad.setIdEmpleado(empleadoEjb.findByIdentificacion(ccColaborador));
                                novedad.setFechaInicio(fechaDesde);
                                novedad.setFechaFin(fechaHasta);
                                novedad.setCcColaborador(ccColaborador);
                                lstNovedades.add(novedad);
                            }
                        }

                    }
                }

                for (NovedadVacaciones item : lstNovedades) {

                    item.setEstadoReg(0);
                    item.setUsername(user.getUsername());
                    item.setCreado(MovilidadUtil.fechaCompletaHoy());

                    // Se realiza la creación del registro en la base de datos
                    novedadVacacionesEjb.create(item);
                }
                Util.deleteFile(path);
                reset();
                MovilidadUtil.addSuccessMessage("Archivo cargado éxitosamente");
            } else {
                MovilidadUtil.addErrorMessage("Debe seleccionar un archivo");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CargaVacacionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            this.file = event.getFile();
            cargarReporte();
        } catch (IOException ex) {
            Logger.getLogger(CargaVacacionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String validarDatos(Date fechaInicio, Date fechaHasta, String ccColaborador) {

        if (Util.validarFechaCambioEstado(fechaInicio, fechaHasta)) {
            return "La fecha inicio NO debe ser mayor a la fecha fin ( CC: "+ccColaborador+" )";
        }
        
        if (empleadoEjb.findByIdentificacion(ccColaborador.trim()) == null) {
            return "El colaborador con identificación: " + ccColaborador + ", NO existe ó se encuentra INACTIVO";
        }

        if (novedadVacacionesEjb.findByDateRange(fechaInicio, fechaHasta, ccColaborador.trim()) != null) {
            return "El colaborador con identificación: " + ccColaborador + ", YA tiene registro para las fechas a ingresar";
        }

        return null;
    }

    private void reset() {
        file = null;
        lstNovedades = null;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<NovedadVacaciones> getLstNovedades() {
        return lstNovedades;
    }

    public void setLstNovedades(List<NovedadVacaciones> lstNovedades) {
        this.lstNovedades = lstNovedades;
    }

}
