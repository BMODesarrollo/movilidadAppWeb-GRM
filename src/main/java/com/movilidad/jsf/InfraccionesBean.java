package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.InfraccionesFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.NovedadTipoFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.Hallazgo;
import com.movilidad.model.HallazgosParamArea;
import com.movilidad.model.Infracciones;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.model.error.FileLoadError;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.HallazgoExcel;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Omar Beltrán
 */
@Named(value = "infraccionesBean")
@ViewScoped
public class InfraccionesBean implements Serializable {

    @EJB
    private InfraccionesFacadeLocal infraccionesEJB;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private NovedadFacadeLocal novedadEjb;
    @EJB
    private NovedadTipoFacadeLocal novedadTipoEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private VehiculoFacadeLocal vehiculoEjb;
    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTipoDetFacade;

    private UploadedFile uploadedFile;
    private Date desde, hasta;
    
    private Infracciones infraSelected;
    private Integer i_puntosConciliados;
    
    private List<Infracciones> listInfracciones;
    List<FileLoadError> listaError;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        uploadedFile = null;
        listaError = new ArrayList<>();
        listarInfracciones();
    }

    private void listarInfracciones() {
        listInfracciones = infraccionesEJB.findAll();
    }

    public void consultar() {

        if (Util.validarFechaCambioEstado(desde, hasta)) {
            MovilidadUtil.addErrorMessage("Fecha Desde NO debe ser mayor a Fecha hasta");
            return;
        }

        listInfracciones = infraccionesEJB.findAllByDateRangeAndEstadoReg(desde, hasta);

        if (listInfracciones == null || listInfracciones.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron datos");
        }

    }

    public int calcularDiasRestantes(Date fechaContestacion) {
        return MovilidadUtil.getDiferenciaDia(MovilidadUtil.fechaHoy(), fechaContestacion);
    }

    /**
     * Método resposable de cargar el archivo Excel seleccionado en la vista
     *
     * @param event Contiene el archivo cargado desde la vista.
     */
    public void cargarInfracciones(FileUploadEvent event) {
        try {
            this.uploadedFile = event.getFile();
            if (uploadedFile != null) {
                List<Infracciones> list = new ArrayList<>();
                String path = Util.saveFile(uploadedFile, 0, "hallazgosInfracciones");
                FileInputStream fileInputStream = new FileInputStream(new File(path));
                recorrerExcel(fileInputStream, list);
                if(!crearNovedades(list)) {
                    MovilidadUtil.addAdvertenciaMessage("No se registraron nuevas novedades");
                }    
                listarInfracciones();
            }
            MovilidadUtil.addSuccessMessage("Proceso carga Archivo de infracciones finalizado");
        } catch (Exception ex) {
            System.out.println("Error en la carga de archivo " + ex.getMessage());
        } finally {
            uploadedFile = null;
        }
    }

    private boolean crearNovedades(List<Infracciones> list) {
        Novedad novedad;
        boolean flag = false;
        for (Infracciones obj : list) {
            //solo debe existir una novedad por infracción
            //no puede existir una infracción sin novedad dado que primero se 
            //crea la novedad, por esta razón, basta con buscar si existe una 
            //infracción por id_ICO
            Infracciones infra = infraccionesEJB.findByIdICO(obj.getIdICO());
            if (infra == null) {
                novedad = new Novedad();//la novedad viene null se debe generar la instancia
                novedad.setFecha(obj.getFechaNovedad());
                novedad.setIdNovedadTipo(novedadTipoEjb.findByIdNovedadTipo(12));
                novedad.setIdNovedadTipoDetalle(obtenerTipoDetalle(obj.getPuntosICO()));
                novedad.setPuntosPmConciliados(novedad.getIdNovedadTipoDetalle().getPuntosPm());
                novedad.setIdEmpleado(empleadoEjb.findByCodigoTM(obj.getnSAE()));
                novedad.setIdVehiculo(vehiculoEjb.getVehiculoPlaca(obj.getPlaca()));
                novedad.setIdGopUnidadFuncional(empleadoEjb.findByCodigoTM(obj.getnSAE()).getIdGopUnidadFuncional());
                novedad.setCreado(new Date());
                novedad.setUsername(user.getUsername());
                novedad.setObservaciones(obj.getDescripcion());
                novedadEjb.create(novedad);//persistir la novedad
                obj.setIdNovedad(novedad);//para asociar el id de la novedad a la infracción
                obj.setPuntosPMConciliados(novedad.getIdNovedadTipoDetalle().getPuntosPm());
                crearInfraccion(obj);
                flag = true;
            } else {//existe una novedad con la información cargada 
                deleteErrorValidated(String.valueOf(infra.getIdICO()));
                sobreescribirInfraccion(infra, obj);
            }
        }
        return flag;
    }

    private void deleteErrorValidated(String idICO) {
        // Eliminar los elementos cuyo id sea igual a idICO
        listaError.removeIf(error -> error.getId().equals(idICO));
    }

    private void sobreescribirInfraccion(Infracciones infraNew, Infracciones infraOld) {
        boolean flag = false;
        if (!infraNew.getEstado().equals(infraOld.getEstado())) {
            infraNew.setEstado(infraOld.getEstado());
            flag = true;
        }
        if (!infraNew.getEstado2().equals(infraOld.getEstado2())) {
            infraNew.setEstado2(infraOld.getEstado2());
            flag = true;
        }
        if (!infraNew.getEtapa().equals(infraOld.getEtapa())) {
            infraNew.setEtapa(infraOld.getEtapa());
            flag = true;
        }
        if (flag) {
            editarInfraccion(infraNew);
        }
    }

    private void crearInfraccion(Infracciones infraccion) {
        infraccion.setEstadoReg(0);
        infraccion.setCreado(new Date());//fecha de creación del registro
        infraccion.setUsernameCreate(user.getUsername());//usuario que crea el registro
        infraccionesEJB.create(infraccion);//persistir la infracción
        notificar(infraccion);
    }

    private void editarInfraccion(Infracciones infraccion) {
        infraccion.setEstadoReg(0);
        infraccion.setModificado(new Date());//fecha de creación del registro
        infraccion.setUsernameEdit(user.getUsername());//usuario que modifica el registro
        infraccionesEJB.edit(infraccion);//persistir la infracción
    }

    private NovedadTipoDetalles obtenerTipoDetalle(int puntos) {
        NovedadTipoDetalles obj = null;
        switch (puntos) {
            case 10:
                obj = novedadTipoDetFacade.find(99);
                break;
            case 15:
                obj = novedadTipoDetFacade.find(100);
                break;
            case 30:
                obj = novedadTipoDetFacade.find(101);
                break;
        }
        return obj;
    }

    /**
     * Realiza el envío de correo de las novedad registrada a las partes
     * interesadas
     */
    private void notificar(Infracciones infraccion) {
        //Notificar al operador
        notificarInfraccionOperador(infraccion, "Novedad tipo infracción");
        String destinatarios = "";
        if (infraccion.getIdNovedad().getIdNovedadTipoDetalle().getNotificacion() == 1) {
            if (infraccion.getIdNovedad().getIdNovedadTipoDetalle().getIdNotificacionProcesos() != null) {
                if (destinatarios != null) {
                    destinatarios = destinatarios + "," + infraccion.getIdNovedad().getIdNovedadTipoDetalle().getIdNotificacionProcesos().getEmails();
                } else {
                    destinatarios = infraccion.getIdNovedad().getIdNovedadTipoDetalle().getIdNotificacionProcesos().getEmails();
                }

                if (infraccion.getIdNovedad().getIdNovedadTipoDetalle().getIdNotificacionProcesos().getNotificacionProcesoDetList() != null) {
                    String destinatariosByUf = "";
                    destinatariosByUf = MovilidadUtil.obtenerCorreosByUf(infraccion.getIdNovedad().getIdNovedadTipoDetalle().getIdNotificacionProcesos().getNotificacionProcesoDetList(),
                            infraccion.getIdNovedad().getIdGopUnidadFuncional().getIdGopUnidadFuncional());
                    if (destinatariosByUf != null) {
                        destinatarios = destinatarios + "," + destinatariosByUf;
                    }
                }
            }
            notificarInfraccionAdministrativos(infraccion, destinatarios);
        }
    }

    private void notificarInfraccionOperador(Infracciones gj, String asunto) {
        try {
            NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(Util.TEMPLATE_NOVEDADES_INFRACCIONES);
            if (template == null) {
                return;
            }
            Map mapa = getMailParams();
            mapa.replace("template", template.getPath());
            Map mailProperties = new HashMap();
            Empleado empl = empleadoEjb.getEmpleadoCodigoTM(gj.getnSAE());
            mailProperties.put("identificacion", gj.getCedulaOperador());
            mailProperties.put("nombre", empl.getNombresApellidos());
            mailProperties.put("fecha", Util.dateFormat(gj.getFechaNovedad()));
            mailProperties.put("user_name", user.getUsername());
            mailProperties.put("motivo", "Infracción notificada por transmilenio");
            mailProperties.put("observacion", gj.getDescripcion());
            SendMails.sendEmail(mapa, mailProperties, asunto, "", empl.getEmailCorporativo(), "Notificaciones RIGEL", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notificarInfraccionAdministrativos(Infracciones infraccion, String destinatarios) {
        try {
            NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(ConstantsUtil.TEMPLATE_NOVEDAD_PM_OP);
            if (template == null) {
                return;
            }
            Map mapa = getMailParams();
            Map mailProperties = new HashMap();
            mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
            mailProperties.put("fecha", Util.dateFormat(infraccion.getIdNovedad().getFecha()));
            mailProperties.put("tipo", infraccion.getIdNovedad().getIdNovedadTipo().getNombreTipoNovedad());
            mailProperties.put("detalle", "");
            mailProperties.put("fechas", "");
            mailProperties.put("operador", infraccion.getIdNovedad().getIdEmpleado() != null ? infraccion.getIdNovedad().getIdEmpleado().getCodigoTm() + " - "
                    + infraccion.getIdNovedad().getIdEmpleado().getNombres() + " " + infraccion.getIdNovedad().getIdEmpleado().getApellidos() : "");
            mailProperties.put("vehiculo", infraccion.getIdNovedad().getIdVehiculo() != null ? infraccion.getIdNovedad().getIdVehiculo().getCodigo() : "");
            mailProperties.put("username", "");
            mailProperties.put("generada", Util.dateTimeFormat(infraccion.getIdNovedad().getCreado()));
            mailProperties.put("observaciones", infraccion.getIdNovedad().getObservaciones());

            SendMails.sendEmail(mapa, mailProperties, "Novedad tipo infracción", "", destinatarios,
                    "Notificaciones RIGEL", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Novedad existeNovedad(int idTipoNovedad, Date fechaNovedad, int idEmpleado, int idVehiculo, String observaciones) {
        return novedadEjb.findNovedadInfraccion(idTipoNovedad, fechaNovedad, idEmpleado, idVehiculo, observaciones);
    }

    private void recorrerExcel(FileInputStream inputStream, List<Infracciones> list_infracciones)
            throws IOException, Exception {
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);
        int numFilas = sheet.getLastRowNum();
        boolean error = false;
        for (int a = 1; a <= numFilas; a++) {
            Infracciones infraccion = new Infracciones();
            Row fila = sheet.getRow(a);
            if (fila == null) {
                agregarError(0, (a + 1), "Registro completo", "El registro está vacío", "Valor nulo");
            } else {
                int numCols = fila.getLastCellNum();
                int integerValue;
                for (int b = 0; b < numCols; b++) {
                    error = false;
                    Cell celda = fila.getCell(b);
                    if (b == 0) {
                        celda = fila.getCell(17);//se valida directamente campo nSAE para evitar procesamineto innecesario
                        if (celda == null || celda.toString().isEmpty()) {
                            error = true;
                            agregarError(fila.getCell(0).getStringCellValue(), (a + 1), "nSAE", "No hay valor", "Valor nulo");
                            b = numCols + 10;//si el valor es nulo o vacío no se valida el registro
                            break;
                        }
                        celda = fila.getCell(b);//después de validar nSAE se carga el registro b = 0
                    }
                    if (celda != null) {
                        Date parse;
                        try {
                            switch (b) {
                                case 0: // id_ICO
                                    infraccion.setIdICO((int) Float.parseFloat(celda.toString()));
                                    break;
                                case 1: // Etapa
                                    infraccion.setEtapa(celda.toString());
                                    break;
                                case 2: // ID Novedad
                                    break;
                                case 3: // Estado
                                    infraccion.setEstado(celda.toString());
                                    break;
                                case 4: // Fecha Inicio DP
                                    try {
                                        parse = Util.toDate(Util.dateFormat(celda.getDateCellValue()));
                                    } catch (Exception e) {
                                        parse = Util.toDate(celda.toString());
                                    }
                                    if (parse == null) {
                                        agregarError(infraccion.getIdICO(), (a + 1), "Fecha Inicio DP", "Formato erróneo ", celda.toString());
                                    } else {
                                        infraccion.setFechaIniDP(parse);
                                    }
                                    break;
                                case 5: // Fecha Fin DP
                                    try {
                                        parse = Util.toDate(Util.dateFormat(celda.getDateCellValue()));
                                    } catch (Exception e) {
                                        parse = Util.toDate(celda.toString());
                                    }
                                    if (parse == null) {
                                        agregarError(infraccion.getIdICO(), (a + 1), "Fecha Fin DP", "Formato erróneo", celda.toString());
                                    } else {
                                        infraccion.setFechaFinDP(parse);
                                    }
                                    break;
                                case 6: // ID Operador
                                    break;
                                case 7: // Empresa
                                    infraccion.setEmpresa(celda.toString());
                                    break;
                                case 8: // Tipo Novedad
                                    infraccion.setTipoNovedad(celda.toString());
                                    break;
                                case 9: // Fecha Novedad
                                    try {
                                        parse = Util.toDate(Util.dateFormat(celda.getDateCellValue()));
                                    } catch (Exception e) {
                                        parse = Util.toDate(celda.toString());
                                    }
                                    if (parse == null) {
                                        agregarError(infraccion.getIdICO(), (a + 1), "Fecha Novedad", "Formato erróneo", celda.toString());
                                    }
                                    infraccion.setFechaNovedad(parse);
                                    break;
                                case 10: // Fuente
                                    break;
                                case 11: // Área
                                    infraccion.setArea(celda.toString());
                                    break;
                                case 12: // Departamento
                                    break;
                                case 13: // Línea
                                    infraccion.setLinea(celda.toString());
                                    break;
                                case 14: // Dirección
                                    infraccion.setDireccion(celda.toString());
                                    break;
                                case 15: // Placa
                                    if (vehiculoEjb.getVehiculoPlaca(celda.toString()) != null) {
                                        infraccion.setPlaca(celda.toString());
                                    } else {
                                        agregarError(infraccion.getIdICO(), (a + 1), "Placa", "No corresponde a un registro existente en la base de datos.", celda.toString());
                                        infraccion.setPlaca(celda.toString());
                                    }
                                    break;
                                case 16: // Móvil
                                    infraccion.setMovil(celda.toString());
                                    break;
                                case 17: // nSAE
                                    integerValue = -1;
                                    try {
                                        integerValue = Integer.valueOf(celda.toString());
                                    } catch (Exception e) {
                                        integerValue = (int) Float.parseFloat(celda.toString());
                                    }
                                    if (integerValue == -1) {
                                        agregarError(infraccion.getIdICO(), (a + 1), "nSAE", "Formato erróneo", integerValue);
                                    } else {
                                        if (empleadoEjb.findByCodigoTM(integerValue) != null) {
                                            infraccion.setnSAE(integerValue);
                                        } else {
                                            agregarError(infraccion.getIdICO(), (a + 1), "nSAE", "No corresponde a un código TM válido.", integerValue);
                                        }
                                    }
                                    break;
                                case 18: // Cédula Conductor
                                    BigDecimal valorDecimal = new BigDecimal(celda.toString());
                                    //como Bigdecimal nunca es null se evalua directamente
                                    Empleado emple = empleadoEjb.findByIdentificacion(String.valueOf(valorDecimal.longValueExact()));
                                    if (emple != null) {
                                        if (!Objects.equals(emple.getCodigoTm(), infraccion.getnSAE())) {
                                            agregarError(infraccion.getIdICO(), (a + 1), "cédula conductor", "El número de cédula no corresponde al nSAE del operador",
                                                    valorDecimal.longValueExact());
                                        }
                                    } else {
                                        agregarError(infraccion.getIdICO(), (a + 1), "cédula conductor", "El número de cédula no corresponde a un colaborador en la BD",
                                                valorDecimal.longValueExact());
                                    }
                                    infraccion.setCedulaOperador(valorDecimal.longValueExact());
                                    break;
                                case 19: // Nombre Conductor
                                    infraccion.setNombreOperador(celda.toString());
                                    break;
                                case 20: // Puntos
                                    infraccion.setPuntosICO((int) Float.parseFloat(celda.toString()));
                                    break;
                                case 21: // Descripción
                                    infraccion.setDescripcion(celda.toString());
                                    break;
                                case 22: // N° Imagen
                                    break;
                                case 23: // N° Videos
                                    break;
                                case 24: // N° Fonias
                                    break;
                                case 25: // N° Registros
                                    break;
                                case 26: // Información SAE operador
                                    break;
                                case 27: // Total Material Vihanet
                                    break;
                                case 28: // Total Material EIC
                                    break;
                                case 29: // Link Web
                                    break;
                                case 30: // Estado2
                                    infraccion.setEstado2(celda.toString());
                                    break;
                            }
                        } catch (Exception e) {
                            agregarError(fila.getCell(0).getStringCellValue(), (a + 1), "", "Excepción en la columna " + (b + 1), "Corregir e intentar de nuevo");
                        }
                    }
                }
                if (!error) {
                    list_infracciones.add(infraccion);
                }
            }
        }
        wb.close();

        if (listaError.isEmpty()) {
            MovilidadUtil.addSuccessMessage("Carga finalizada con éxito.");
        } else {
            PrimeFaces.current().executeScript("PF('cargar_infracciones_wv').show()");
            PrimeFaces.current().ajax().update("formErrores:erroresList");
        }
    }

    private void agregarError(int id, int fila, String columna, String error, Object value) {
        agregarError(String.valueOf(id), fila, columna, error, value);
    }

    private void agregarError(String id, int fila, String columna, String error, Object value) {
        listaError.add(new FileLoadError(id, fila, columna, error, value));
    }

    /*
     * Parámetros para el envío de correos de aprobación de conciliaciones
     */
    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(ConstantsUtil.TEMPLATE_NOVEDAD_PM_OP);
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

    /**
     * Evento que se dispara al seleccionar una Infracción del registro
     *
     * @param event
     */
    public void onRowSelect(SelectEvent event) {
        setInfraSelected((Infracciones) event.getObject());
        i_puntosConciliados=infraSelected.getPuntosPMConciliados();
//        b_OwnerCreate = flagTC ? isOwnerCreate(infraSelected.getIdNovedad(), user.getUsername(), MovilidadUtil.fechaCompletaHoy()) : true; //si es necesario validar rol para técnico de control habilitar esta línea y completar los ajustes necesarios
    }
    
    public void cambiarPuntos() {
        if (infraSelected == null) {
            MovilidadUtil.addErrorMessage("Seleccionar una novedad.");
            return;
        }
        MovilidadUtil.openModal("cambiarPuntos");
    }
    
    /**
     * Asigna los puntos del Programa master conciliados a una novedad
     */
    public void aplicarPuntosPM() {
        if (i_puntosConciliados == null) {
            MovilidadUtil.addErrorMessage("Digite un valor para puntos conciliados");
            return;
        }
        infraSelected.setPuntosPMConciliados(i_puntosConciliados);
        infraSelected.setUsernameEdit(user.getUsername());
        infraSelected.setModificado(new Date());
        //buscar la novedad que corresponde a la infracción seleccionada
        Novedad novedad = novedadEjb.find(infraSelected.getIdNovedad().getIdNovedad());
        novedad.setPuntosPmConciliados(i_puntosConciliados);//asignar los puntos PM Conciliados
        novedad.setProcede(1);
        novedadEjb.edit(novedad);
        infraccionesEJB.edit(infraSelected);
        MovilidadUtil.addSuccessMessage("Los puntos se han agregado a la Concilicación correctamente");
        MovilidadUtil.hideModal("cambiarPuntos");
        MovilidadUtil.updateComponent("frmInfracciones:dtInfracciones");
        reset();
    }
    
    /**
     * Realiza la conciliación de una novedad y su asignación de puntos en el
     * programa master
     *
     * @param infraccion
     */
    public void procedeCociliacion(Infracciones infraccion) {
        infraSelected = infraccion;
        infraSelected.setUsernameEdit(user.getUsername());
        infraSelected.setModificado(new Date());
        //buscar la novedad que corresponde a la infracción seleccionada
        Novedad novedad = novedadEjb.find(infraSelected.getIdNovedad().getIdNovedad());
        novedad.setPuntosPmConciliados(novedad.getPuntosPm());
        infraSelected.setPuntosPMConciliados(novedad.getPuntosPm());
        novedad.setProcede(1);
        MovilidadUtil.addSuccessMessage("Acción 'Procede' ejecutada");
        MovilidadUtil.updateComponent("msgs");
        novedadEjb.edit(novedad);
        infraccionesEJB.edit(infraSelected);
        reset();
    }

    /**
     * Deshace la conciliación de una novedad y su asignación de puntos en el
     * programa master
     *
     * @param nov
     */
    public void noProcedeConciliacion(Infracciones nov) {
        infraSelected = nov;
        infraSelected.setPuntosPMConciliados(i_puntosConciliados);
        infraSelected.setUsernameEdit(user.getUsername());
        infraSelected.setModificado(new Date());
        //buscar la novedad que corresponde a la infracción seleccionada
        Novedad novedad = novedadEjb.find(infraSelected.getIdNovedad().getIdNovedad());
        novedad.setPuntosPmConciliados(0);
        novedad.setProcede(0);
        infraSelected = nov;
        infraSelected.setPuntosPMConciliados(0);
        
        MovilidadUtil.addSuccessMessage("Acción 'NO Procede' ejecutada");
        PrimeFaces.current().ajax().update("msgs");
        PrimeFaces.current().ajax().update("frmInfracciones:dtInfracciones");
        novedadEjb.edit(novedad);
        infraccionesEJB.edit(infraSelected);
        reset();
    }
    
    public void reset() {
        infraSelected = new Infracciones();
        i_puntosConciliados = 0;
    }
    
    public Infracciones getInfraSelected() {
        return infraSelected;
    }

    public void setInfraSelected(Infracciones infraSelected) {
        this.infraSelected = infraSelected;
    }

    public Integer getI_puntosConciliados() {
        return i_puntosConciliados;
    }

    public void setI_puntosConciliados(Integer i_puntosConciliados) {
        this.i_puntosConciliados = i_puntosConciliados;
    }

    public List<Infracciones> getListInfracciones() {
        return listInfracciones;
    }

    public void setListInfracciones(List<Infracciones> listInfracciones) {
        this.listInfracciones = listInfracciones;
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

    public List<FileLoadError> getListaError() {
        return listaError;
    }

    public void setListaError(List<FileLoadError> listaError) {
        this.listaError = listaError;
    }

}
