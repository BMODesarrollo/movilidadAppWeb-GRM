package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.HallazgosFacadeLocal;
import com.movilidad.ejb.HallazgosParamAreaFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionProcesosFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.Hallazgo;
import com.movilidad.model.HallazgosParamArea;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.HallazgoExcel;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
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
import javax.transaction.Transactional;
import org.apache.poi.ss.usermodel.Cell;
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
@Named(value = "cargaHallazgoBean")
@ViewScoped
public class CargaHallazgoBean implements Serializable {

    @EJB
    private HallazgosFacadeLocal hallazgosEjb;
    @EJB
    private HallazgosParamAreaFacadeLocal hallazgosParamAreaEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private VehiculoFacadeLocal vehiculoEjb;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private NotificacionProcesosFacadeLocal notificacionProcesosEjb;

    private UploadedFile uploadedFile;

    private Date desde, hasta;

    private List<Hallazgo> lista;

    private Map<Integer, Empleado> hMEmpleados;
    private Map<String, Vehiculo> hMVehiculos;
    private Map<String, HallazgosParamArea> hMAreas;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        uploadedFile = null;
    }

    public void consultar() {

        if (Util.validarFechaCambioEstado(desde, hasta)) {
            MovilidadUtil.addErrorMessage("Fecha Desde NO debe ser mayor a Fecha hasta");
            return;
        }

        lista = hallazgosEjb.findAllByDateRangeAndEstadoReg(desde, hasta);

        if (lista == null || lista.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron datos");
        }

    }

    public int calcularDiasRestantes(Date fechaContestacion) {
        return MovilidadUtil.getDiferenciaDia(MovilidadUtil.fechaHoy(), fechaContestacion);
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            this.uploadedFile = event.getFile();
            cargarDatos();
        } catch (Exception ex) {
            Logger.getLogger(CargaHallazgoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo de responsable de cargar el archivo Excel indicado desde la vista
     *
     * @param event Contiene el archivo cargado desde la vista.
     */
    public void cargarInfracciones(FileUploadEvent event) {
        try {
            this.uploadedFile = event.getFile();
            MovilidadUtil.addSuccessMessage("Archivo cargado correctamente");
        } catch (Exception ex) {
            System.out.println("Error en la carga de archivo");
        }
    }
    
    private void cargarOperadores() {
        List<Empleado> listaOperadores = empleadoEjb.findEmpleadosOperadores(0);

        if (listaOperadores == null || listaOperadores.isEmpty()) {
            return;
        }

        hMEmpleados = new HashMap<>();

        listaOperadores.forEach(item -> {
            hMEmpleados.put(item.getCodigoTm(), item);
        });
    }

    private void cargarVehiculos() {
        List<Vehiculo> listaVehiculos = vehiculoEjb.findAllVehiculosByidGopUnidadFuncional(0);

        if (listaVehiculos == null || listaVehiculos.isEmpty()) {
            return;
        }

        hMVehiculos = new HashMap<>();

        listaVehiculos.forEach(item -> {
            hMVehiculos.put(item.getCodigo(), item);
        });
    }

    private void cargarAreas() {
        List<HallazgosParamArea> listaAreas = hallazgosParamAreaEjb.findAllByEstadoReg();

        if (listaAreas == null || listaAreas.isEmpty()) {
            return;
        }

        hMAreas = new HashMap<>();

        listaAreas.forEach(item -> {
            hMAreas.put(item.getNombre().toUpperCase(), item);
        });
    }

    @Transactional
    private void cargarDatos() throws Exception {

        if (uploadedFile == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un archivo");
            return;
        }

        List<Hallazgo> lstHallazgosTemp = new ArrayList<>();
        String path = null;

        Row row;
        XSSFSheet sheet = null;

        // Carga de listas: vehiculos, operadores y areas
        cargarVehiculos();
        cargarOperadores();
        cargarAreas();

        if (hMAreas == null || hMAreas.isEmpty()) {
            MovilidadUtil.addErrorMessage("NO se encontraron áreas registradas");
            return;
        }

        // Se guarda archivo en carpeta
        path = Util.saveFile(uploadedFile, 0, "kmMtto");
        try (FileInputStream file = new FileInputStream(new File(path))) {
            // leer archivo excel
            XSSFWorkbook worbook = new XSSFWorkbook(file);

            //obtener la hoja que se va leer
            sheet = worbook.getSheetAt(0);

            int numFilas = sheet.getLastRowNum();

            desde = Util.toDate(Util.dateFormat(Util.dateTimeFormat(sheet.getRow(2).getCell(4).getStringCellValue())));
            hasta = Util.toDate(Util.dateFormat(Util.dateTimeFormat(sheet.getRow(numFilas - 1).getCell(4).getStringCellValue())));

            //obtener todas las filas de la hoja excel
            Iterator<Row> rowIterator = sheet.iterator();

            // se recorre cada fila hasta el final
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                //se obtiene las celdas por fila
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell cell;

                Hallazgo hallazgo = new Hallazgo();

                //se recorre cada celda
                while (cellIterator.hasNext()) {
                    // se obtiene la celda en específico
                    cell = cellIterator.next();

                    if (row.getRowNum() >= 2) {

                        switch (cell.getCellTypeEnum().toString()) {
                            case "NUMERIC":

                                if (cell.getColumnIndex() == 0) {
                                    hallazgo.setConsecutivo((int) row.getCell(0).getNumericCellValue());
                                }

                                if (cell.getColumnIndex() == 12) {

                                    Empleado empleado = hMEmpleados.get((int) row.getCell(12).getNumericCellValue());

                                    if (empleado != null) {
                                        hallazgo.setIdEmpleado(empleado);
                                    }
                                }

                                break;

                            case "STRING":

                                if (cell.getColumnIndex() == 2) {

                                    HallazgosParamArea area = hMAreas.get(row.getCell(2).getStringCellValue());

                                    if (area != null) {
                                        hallazgo.setIdHallazgoParamArea(area);
                                    }
                                }

                                if (cell.getColumnIndex() == 4) {
                                    hallazgo.setFechaIdentificacion(Util.dateTimeFormat(row.getCell(4).getStringCellValue()));
                                }

                                if (cell.getColumnIndex() == 5) {
                                    hallazgo.setHora(row.getCell(5).getStringCellValue());
                                }
                                if (cell.getColumnIndex() == 8) {
                                    hallazgo.setZona(row.getCell(8).getStringCellValue());
                                }
                                if (cell.getColumnIndex() == 10) {

                                    Vehiculo vehiculo = hMVehiculos.get(row.getCell(10).getStringCellValue());

                                    if (vehiculo != null) {
                                        hallazgo.setIdVehiculo(vehiculo);
                                    }
                                }
                                if (cell.getColumnIndex() == 12) {

                                    Empleado empleado = hMEmpleados.get(Integer.parseInt(row.getCell(12).getStringCellValue()));

                                    if (empleado != null) {
                                        hallazgo.setIdEmpleado(empleado);
                                    }
                                }
                                if (cell.getColumnIndex() == 13) {
                                    hallazgo.setCodigoInfraccion(row.getCell(13).getStringCellValue());
                                }
                                if (cell.getColumnIndex() == 15) {
                                    hallazgo.setDescripcion(row.getCell(15).getStringCellValue());
                                }
                                if (cell.getColumnIndex() == 20) {
                                    hallazgo.setEstado(row.getCell(20).getStringCellValue());
                                }
                                if (cell.getColumnIndex() == 23) {
                                    hallazgo.setFechaContestacion(Util.toDate(row.getCell(23).getStringCellValue().split("T")[0]));
                                }

                                break;
                        }

                        lstHallazgosTemp.add(hallazgo);

                    }
                }
            }

            /**
             * Se recorre la lista con los datos obtenidos y se hacen las
             * respectivas validaciones
             */
            for (Hallazgo item : lstHallazgosTemp) {
                // Se verifica si el registro tiene codigos de infracción de lavado (I5006 ó I5013-1)\
                if (item.getCodigoInfraccion().contains(ConstantsUtil.CODIGOS_INFRACCION_LAVADO)) {
                    item.setIdHallazgoParamArea(hMAreas.get(ConstantsUtil.ID_AREA_LAVADO));
                }

                /**
                 * Se verifica si YA existe un hallazgo con el ID actual y en
                 * caso de que si exista se actualiza el estado de ése hallazgo
                 * con el nuevo estado
                 */
                Hallazgo itemBuscar = hallazgosEjb.findByConsecutivo(item.getConsecutivo());

                if (itemBuscar != null) {
                    itemBuscar.setEstado(item.getEstado());
                    itemBuscar.setModificado(MovilidadUtil.fechaCompletaHoy());
                    itemBuscar.setUsername(user.getUsername());
                    hallazgosEjb.edit(itemBuscar);
                } else {
                    item.setEstadoReg(0);
                    item.setCreado(MovilidadUtil.fechaCompletaHoy());
                    item.setUsername(user.getUsername());
                    hallazgosEjb.create(item);
                }

            }

            /**
             * Se obtienen los hallazgos por áreas para realizar el envío por
             * correo de los hallazgos cargados a las personas interesadas
             */
            List<HallazgosParamArea> listaAreas = hallazgosParamAreaEjb.findAllByEstadoReg();

            for (HallazgosParamArea item : listaAreas) {

                List<Hallazgo> lstHallazgosByArea = hallazgosEjb.findAllByDateRangeAndArea(desde, hasta, item.getIdHallazgosParamArea());

                if (lstHallazgosByArea != null && !lstHallazgosByArea.isEmpty()) {
                    if (item.getEmails() != null) {
                        notificar(lstHallazgosByArea, item);
                    }
                }

            }

            Util.deleteFile(path);
            uploadedFile = null;
            consultar();
            MovilidadUtil.runScript("PF('pnlCarga').toggle();");
            MovilidadUtil.addSuccessMessage("Hallazgos cargados con éxito");

        } catch (Exception e) {
            e.printStackTrace();
            Util.deleteFile(path);
            MovilidadUtil.addErrorMessage("Error al cargar archivo de hallazgos");
        }
    }

    /*
     * Parámetros para el envío de correos de aprobación de conciliaciones
     */
    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(Util.TEMPLATE_HALLAZGOS);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    /**
     * Realiza el envío de correo de hallazgos registradas a las respectivas
     * áreas.
     */
    private void notificar(List<Hallazgo> lstHallazgosByArea, HallazgosParamArea area) {
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get("ID_LOGO"));
        mailProperties.put("desde", Util.dateFormat(desde));
        mailProperties.put("hasta", Util.dateFormat(hasta));
        mailProperties.put("area", area.getNombre());

        List<HallazgoExcel> lstHallazgos = retornarListaExcel(lstHallazgosByArea);

        String subject;
        String destinatarios;

        destinatarios = area.getEmails();
        subject = "Se ha realizado la carga de hallazgos para el área de " + area.getNombre() + ", con fechas de: " + Util.dateFormat(desde) + " al " + Util.dateFormat(hasta);

        // Generación de archivo excel con los datos de la conciliación
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        Map parametros = new HashMap();

        plantilla = plantilla + "REPORTE HALLAZGOS.xlsx";
        parametros.put("hallazgos", lstHallazgos);
        destino = destino + "REPORTE_HALLAZGOS_" + area.getNombre() + ".xlsx";

        GeneraXlsx.generar(plantilla, destino, parametros);
        List<String> adjuntos = new ArrayList<>();
        adjuntos.add(destino);

        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", adjuntos);
    }

    private List<HallazgoExcel> retornarListaExcel(List<Hallazgo> lstHallazgosByArea) {
        List<HallazgoExcel> lstFinal = new ArrayList<>();

        for (Hallazgo obj : lstHallazgosByArea) {
            HallazgoExcel item = new HallazgoExcel();
            item.setHallazgo(obj);
            item.setDiasRestantes(MovilidadUtil.getDiferenciaDia(MovilidadUtil.fechaHoy(), obj.getFechaContestacion()));
            lstFinal.add(item);
        }

        return lstFinal;
    }

    public List<Hallazgo> getLista() {
        return lista;
    }

    public void setLista(List<Hallazgo> lista) {
        this.lista = lista;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

}
