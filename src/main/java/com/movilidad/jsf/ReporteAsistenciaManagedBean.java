package com.movilidad.jsf;

import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.model.PrgSercon;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.PrgSerconParteTrabajo;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@Named(value = "reporteAsistenciaBean")
@ViewScoped
public class ReporteAsistenciaManagedBean implements Serializable {

    @EJB
    private PrgSerconFacadeLocal prgSerconEjb;

    private PrgSercon prgSercon;
    private UploadedFile file;
    private List<PrgSerconParteTrabajo> lstParteTrabajosError;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        reset();
    }

    private void cargarReporte() throws IOException {
        cargarReporteTransactional();
    }

    /**
     * Realiza la asignación de las horas obtenidas en el reporte de asistencia
     * en base de datos ( tabla prg_sercon)
     */
    @Transactional
    private void cargarReporteTransactional() throws IOException {
        Date fechaDesde = null;
        Date fechaHasta = null;
        PrgSerconParteTrabajo prgSerconParteTrabajo;
        Map<String, PrgSercon> mapRegistros;
        List<PrgSerconParteTrabajo> lstParteTrabajos;

        try {
            if (file != null) {
                String path = Util.saveFile(file, 0, "kmMtto");
                FileInputStream inputStream = new FileInputStream(new File(path));

                XSSFWorkbook wb = new XSSFWorkbook(inputStream);
                XSSFSheet sheet = wb.getSheetAt(0);

                int numFilas = sheet.getLastRowNum();
                Row primeraFila = sheet.getRow(6);
                Row ultimaFila = sheet.getRow(numFilas - 1);

                // Se obtiene el rango de fechas del archivo
                fechaDesde = primeraFila.getCell(0).getDateCellValue();
                fechaHasta = ultimaFila.getCell(0).getDateCellValue();

                // Se realiza la búsqueda de registros correspondientes al rango de fechas
                List<PrgSercon> lista = prgSerconEjb.getPrgSerconByDateAndUnidadFunc(fechaDesde, fechaHasta, 0);

                if (lista == null || lista.isEmpty()) {
                    MovilidadUtil.addErrorMessage("No se encontraron registros en la base de datos");
                    Util.deleteFile(path);
                    reset();
                    return;
                }

                mapRegistros = new HashMap<>();

                //Se agregan registros consultados al map de registros
                for (PrgSercon item : lista) {
                    mapRegistros.put(Util.dateFormat(item.getFecha()).concat("_" + item.getIdEmpleado().getCodigoTm()).concat("_" + item.getSercon()), item);
                }

                lstParteTrabajos = new ArrayList<>();
                lstParteTrabajosError = new ArrayList<>();

                for (int a = 0; a <= numFilas - 1; a++) {
                    Row fila = sheet.getRow(a);
                    int numCols = fila.getLastCellNum();
                    prgSerconParteTrabajo = new PrgSerconParteTrabajo();

                    // Se necesitan celdas: 0,2,3,5,6 y 12
                    for (int b = 0; b <= numCols - 1; b++) {
                        Cell celda = fila.getCell(b);

                        if (celda != null) {
                            switch (celda.getCellTypeEnum().toString()) {
                                case "NUMERIC":
                                    if (DateUtil.isCellDateFormatted(celda)) {
                                        prgSerconParteTrabajo.setFecha(Util.toDate(Util.dateFormat(celda.getDateCellValue())));
                                    } else {
                                    }
                                    break;
                                case "STRING":
                                    prgSerconParteTrabajo.setSercon(fila.getCell(2).getStringCellValue());

                                    if (fila.getCell(5).getCellTypeEnum().toString().equals("NUMERIC")) {
                                        prgSerconParteTrabajo.setParteTrabajo(String.valueOf((int) fila.getCell(5).getNumericCellValue()));
                                    } else {
                                        prgSerconParteTrabajo.setParteTrabajo(fila.getCell(5).getStringCellValue());
                                    }

                                    if (fila.getCell(3).getCellTypeEnum().toString().equals("NUMERIC")) {
                                        prgSerconParteTrabajo.setCodOperador(String.valueOf((int) fila.getCell(3).getNumericCellValue()));
                                    } else {
                                        prgSerconParteTrabajo.setCodOperador(fila.getCell(3).getStringCellValue());
                                    }

                                    switch (prgSerconParteTrabajo.getParteTrabajo()) {
                                        case "1":
                                            prgSerconParteTrabajo.setTimeOrigin(fila.getCell(6).getStringCellValue());
                                            prgSerconParteTrabajo.setTimeDestiny(fila.getCell(12).getStringCellValue());
                                            break;
                                        case "2":
                                            prgSerconParteTrabajo.sethIni_Turno2(fila.getCell(6).getStringCellValue());
                                            prgSerconParteTrabajo.sethFin_Turno2(fila.getCell(12).getStringCellValue());
                                            break;
                                        case "3":
                                            prgSerconParteTrabajo.sethIni_Turno3(fila.getCell(6).getStringCellValue());
                                            prgSerconParteTrabajo.sethFin_Turno3(fila.getCell(12).getStringCellValue());
                                            break;
                                    }
                                    break;
                            }
                        }

                    }

                    if (a > 5) {

                        if (prgSerconParteTrabajo.getParteTrabajo() != null && !prgSerconParteTrabajo.getParteTrabajo().isEmpty()) {
                            lstParteTrabajos.add(prgSerconParteTrabajo);

                            // Se realiza la búsqueda en el map de registros ( por fecha y sercon)
                            prgSercon = mapRegistros.get(Util.dateFormat(prgSerconParteTrabajo.getFecha()).concat("_" + prgSerconParteTrabajo.getCodOperador()).concat("_" + prgSerconParteTrabajo.getSercon()));

                            // En caso de que NO se encuentren datos con los parámetros ingresados
                            if (prgSercon == null) {
                                lstParteTrabajosError.add(prgSerconParteTrabajo);
                            }
                        }

                    }

                }

                if (!lstParteTrabajosError.isEmpty()) {
                    MovilidadUtil.openModal("wlvReporteAsistencia");
                    MovilidadUtil.updateComponent("frmReporteAsistencia");
                    Util.deleteFile(path);
                    reset();
                    return;
                }

                for (PrgSerconParteTrabajo sercon : lstParteTrabajos) {

                    // Se realiza la búsqueda en el map de registros ( por fecha y sercon)
                    prgSercon = mapRegistros.get(Util.dateFormat(sercon.getFecha()).concat("_" + sercon.getCodOperador()).concat("_" + sercon.getSercon()));

                    // Se asignan las horas al registro encontrado
                    switch (sercon.getParteTrabajo()) {
                        case "1":
                            prgSercon.setTimeOrigin(sercon.getTimeOrigin());
                            prgSercon.setTimeDestiny(sercon.getTimeDestiny());
                            break;
                        case "2":
                            prgSercon.setHiniTurno2(sercon.gethIni_Turno2());
                            prgSercon.setHfinTurno2(sercon.gethFin_Turno2());
                            break;
                        case "3":
                            prgSercon.setHiniTurno3(sercon.gethIni_Turno3());
                            prgSercon.setHfinTurno3(sercon.gethFin_Turno3());
                            break;
                    }

                    prgSercon.setUsername(user.getUsername());
                    prgSercon.setModificado(MovilidadUtil.fechaCompletaHoy());

                    // Se realiza la actualización en la base de datos
                    prgSerconEjb.edit(prgSercon);
                }

                Util.deleteFile(path);
                reset();
                MovilidadUtil.addSuccessMessage("Archivo cargado éxitosamente");

            } else {
                MovilidadUtil.addErrorMessage("Debe seleccionar un archivo");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KmMttoJSFManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            this.file = event.getFile();
            cargarReporte();
        } catch (IOException ex) {
            Logger.getLogger(ReporteAsistenciaManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void reset() {
        file = null;
        prgSercon = null;
    }

    public PrgSercon getPrgSercon() {
        return prgSercon;
    }

    public void setPrgSercon(PrgSercon prgSercon) {
        this.prgSercon = prgSercon;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<PrgSerconParteTrabajo> getLstParteTrabajosError() {
        return lstParteTrabajosError;
    }

    public void setLstParteTrabajosError(List<PrgSerconParteTrabajo> lstParteTrabajosError) {
        this.lstParteTrabajosError = lstParteTrabajosError;
    }

}
