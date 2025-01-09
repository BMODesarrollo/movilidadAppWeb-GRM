package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.util.beans.GenericaJornadaObj;
import com.movilidad.utils.Jornada;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "jornadaCargaBean")
@ViewScoped
public class JornadaCargaBean implements Serializable {

    @Inject
    private GenericaControlJornadaMB genericaControlJornadaMB;

    private UploadedFile uploadedFile;
    private StreamedContent file;

    private boolean flagBtn;

    private List<Jornada> lista;

    @PostConstruct
    public void init() {
        lista = new LinkedList<>();
        uploadedFile = null;
        flagBtn = false;
    }

    private void cargarPlantilla() throws IOException {

        if (uploadedFile == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un archivo");
            return;
        }

        List<Date> lstFechas = new LinkedList<>();
        Jornada jornada;
        String path = null;

        Row row;
        XSSFSheet sheet = null;

        try {

            // Se guarda archivo en carpeta
            path = Util.saveFile(uploadedFile, 0, "kmMtto");
            FileInputStream file = new FileInputStream(new File(path));

            // leer archivo excel
            XSSFWorkbook worbook = new XSSFWorkbook(file);
            //obtener la hoja que se va leer
            sheet = worbook.getSheetAt(0);
            //obtener todas las filas de la hoja excel
            Iterator<Row> rowIterator = sheet.iterator();
            // se recorre cada fila hasta el final
            int fila = 0;
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                if (fila == 1) {
                    break;
                }
                if (row.getRowNum() == 1) {
                    fila = 1;
                    //se obtiene las celdas por fila
                    Iterator<Cell> cellIterator = row.cellIterator();
                    Cell cell;

                    //se recorre cada celda
                    while (cellIterator.hasNext()) {
                        // se obtiene la celda en específico y se la imprime
                        cell = cellIterator.next();

                        if (cell.getColumnIndex() > 3) {
                            switch (cell.getCellType()) {
                                case 0:
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        lstFechas.add(cell.getDateCellValue());
                                    }
                                    break;
                            }
                        }
                    }
                }
            }

            DataFormatter formatter = new DataFormatter();
            int registros = sheet.getLastRowNum();
            lista = new LinkedList<>();

            for (int i = 2; i <= registros; i++) {
                row = sheet.getRow(i);
                for (int c = 4; c <= row.getPhysicalNumberOfCells() - 1; c++) {
                    jornada = new Jornada();
                    jornada.setFecha(lstFechas.get(c - 4));
                    jornada.setNombre(row.getCell(1).getStringCellValue()
                            + " " + row.getCell(2).getStringCellValue());

//                String cedula = new BigDecimal(row.getCell(3).getNumericCellValue()).toPlainString();
                    String cedula = formatter.formatCellValue(row.getCell(3));
                    jornada.setCedula(cedula);
                    jornada.setTipo_jornada(row.getCell(c).getStringCellValue());
//                jornada.setTipo_jornada(row.getCell(c).getStringCellValue().split("\n")[0]); // SOLO HORA

                    lista.add(jornada);
                }
            }
            Util.deleteFile(path); // Se elimina el archivo del servidor
            flagBtn = true;

        } catch (Exception ex) {
            Logger.getLogger(JornadaCargaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarDatos() throws FileNotFoundException, IOException, Exception {

        file = null;
        Map parametros = new HashMap();
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        plantilla = plantilla + "Jornadas_Carga_Rev_2.xlsx";
        parametros.put("jornadas", lista);
        destino = destino + "JORNADA_CARGA_GENERADA.xlsx";

        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        FileInputStream fileInputStream = new FileInputStream(excel);

        List<GenericaJornadaObj> list_LiquidacionSercon = new ArrayList<>();

        genericaControlJornadaMB.recorrerExcelAndCargarLista(fileInputStream, list_LiquidacionSercon);

        init();

        MovilidadUtil.addErrorMessage("Datos cargados con éxito");

    }

    public void generarReporte() throws FileNotFoundException {

        file = null;
        Map parametros = new HashMap();
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        plantilla = plantilla + "Jornadas_Carga_Rev_2.xlsx";
        parametros.put("jornadas", lista);
        destino = destino + "JORNADA_CARGA_GENERADA.xlsx";

        init();

        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        InputStream stream = new FileInputStream(excel);
        file = new DefaultStreamedContent(stream, "text/plain", "JORNADA_CARGA_GENERADA.xlsx");
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            this.uploadedFile = event.getFile();
            cargarPlantilla();
        } catch (IOException ex) {
            Logger.getLogger(JornadaCargaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Jornada> getLista() {
        return lista;
    }

    public void setLista(List<Jornada> lista) {
        this.lista = lista;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public boolean isFlagBtn() {
        return flagBtn;
    }

    public void setFlagBtn(boolean flagBtn) {
        this.flagBtn = flagBtn;
    }

}
