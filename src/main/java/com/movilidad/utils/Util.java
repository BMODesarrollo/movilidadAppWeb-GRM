package com.movilidad.utils;

import com.movilidad.util.beans.CurrentLocation;
import com.movilidad.util.beans.GeocodingDTO;
import com.movlidad.httpUtil.GeoService;
import com.movlidad.httpUtil.GoogleApiGeocodingServices;
import java.awt.Color;
import java.awt.Image;
import java.util.ResourceBundle;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import org.primefaces.model.UploadedFile;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.matches;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Carlos Ballestas
 */
public class Util {

    /**
     * Formato de fecha yyyy-MM-dd HH:mm:ss
     */
    public static final SimpleDateFormat XML_GREGORIAN_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_FORMAT_DD_MM_YYYY = new SimpleDateFormat("dd-MM-yyyy");
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_TO_TIME_FORMAT = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat DATE_TO_AM_PM_FORMAT = new SimpleDateFormat("a");
    public static final SimpleDateFormat DATE_TO_TIME_FORMAT_HH_MM_SS = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_WS = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat TIME_START = new SimpleDateFormat("HH:00:00");
    public static final SimpleDateFormat TIME_END = new SimpleDateFormat("HH:59:59");
    public static final SimpleDateFormat DATE_FORMAT_MM = new SimpleDateFormat("MM"); // solo retorna el mes
    public static final SimpleDateFormat DATE_FORMAT_YYYY_MM_T_DD_HH_MM_SS = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.000'-'05:00");
    public static final SimpleDateFormat DATEBYCODE = new SimpleDateFormat("yyMMdd");
    public static final SimpleDateFormat DATE_FORMAT_DDMMYYHHMMSSZZZ = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /**
     * Plantillas de novedades
     */
    public static final int ID_NOTIFICACION_CONF = 1;
    public static final int ID_NOVEDAD_PM_TEMPLATE = 1;
    public static final int ID_NOVEDAD_GEN_TEMPLATE = 6;
    public static final int ID_DANOS_TEMPLATE = 2;
    public static final int ID_OPERACION_GRUAS_TEMPLATE = 3;
    public static final int ID_MULTAS_TEMPLATE = 4;
    public static final String TEMPLATE_NOVEDADES_CABLE = "templateCableNovedad";
    public static final String TEMPLATE_REVISION_DIA = "templateRevisionDia";
    public static final String TEMPLATE_NOVEDADES_INFRAESTRUCTURA = "templateNovedadInfra";
    public static final String TEMPLATE_NOVEDADES_MTTO = "templateNovedadMtto";
    public static final String TEMPLATE_APROBACION_CONCILIACION = "templateAprobacionConciliacion";
    public static final String TEMPLATE_HALLAZGOS = "templateHallazgos";
    public static final String TEMPLATE_NOVEDAD_INFRA_GMO = "templateNovInfraGMO";
    public static final String TEMPLATE_NOVEDADES_INFRACCIONES = "templateNovedadInfracciones";
    public static final int ID_NOVEDAD_INFRAESTRUCTURA = 9;
    public static final int ID_NOVEDAD_CABINA = 10;

    // Plantillas TOKEN Y LICENCIA
    public static final String ID_TOKEN_TEMPLATE = "templateToken";
    public static final String ID_SOLICITUD_TEMPLATE_LICENCIA = "templateLicencia";
    public static final String TEMPLATE_SOLICITUDES = "templateSolicitudes";
    public static final String TEMPLATE_SOLICITUDES_GEN = "templateSolicitudesGen";
    public static final String ID_SOLICITUD_TEMPLATE_LICENCIA_GEN = "templateLicenciaGen";

    // Plantillas SST
    public static final String ID_TEMPLATE_SST_EMPRESA = "templateSstEmpresa";
    public static final String ID_TEMPLATE_SST_RESPUESTA_AUTORIZACION = "templateSstAutorizacion";

    // Plantilla parrilla
    public static final String TEMPLATE_CAMBIO_VEHICULO = "templateCambioVehiculo";
    /**
     * Códigos notificacion procesos
     */
    public static final int ID_MULTAS_NOTI_PROC = 13;
    public static final int ID_OPGRUAS_NOTI_PROC = 5;
    public static final String ID_DANOS_NOTI_PROC = "DAÑOS";
    public static final String ID_NOVEDAD_CTRL_PROC = "NOCTRL";

    /**
     * Accidentalidad
     */
    public static final int ID_ACCIDENTE = 2;
    public static final Integer ID_ACCIDENTE_LABORAL = 110;

    /**
     * Config empresa
     *
     */
    public static final String CODE_ACC_NOVEDAD = "ACC-NOV-DET";
    public static final String CODE_MARKER_ICON = "ICON-MARKER-RP-CERCANIA";
    public static final String CODE_API_KEY_GOOGLE = "API-KEY-GOOGLE";
    public static final String CODE_HORGURA_ENTRADAS_PATIO = "ENTRADA-PATIO";
    public static final String CODE_HORGURA_SALIDAS_PATIO = "SALIDA-PATIO";
    /**
     * Constantes
     */
    public static final BigDecimal CERO = BigDecimal.ZERO;
    public static final int ID_RES_DIR_OP = 1;
    public static final int ID_RES_MTTO = 2;
    public static final int ID_RES_TC = 3;
    public static final int ID_RES_TM = 4;
    public static final int ID_ART = 1;
    public static final int ID_BI = 2;
    public static final int ID_MULTA = 6;
    public static final String DANO = "Daño";
    public static final String URL_BMO = "rigel.bogotamovil.com.co";
    public static final String NOVEDAD_AUSENTISMO = "Ausentismo";
    public static final int ID_NOVEDAD_DANO = 3;
    public static final int ID_NOVEDAD_MULTAS = 6;
    public static final String DETALLE_INCAPACIDAD = "Incapacidad";
    public static final String OPE_MASTER = "OPERADOR MASTER";
    public static final int ID_OPE_MASTER = 30;
    public static final String USER_AUT_ROLE = "ROLE_GH";
    public static final Integer ID_LIQUIDADO = 1;
    public static final Integer ID_NO_LIQUIDADO = 0;
    public static final String NOMBRE_EMPRESA = "BMO";

    /**
     * Tipo de solicitudes de permiso
     */
    public static final int ID_SOLICITUD_PERMISO_CAMBIO = 1;
    public static final int ID_SOLICITUD_LICENCIA_NO_REMUNERADA = 2;
    public static final String ID_ESTADO_SOLICITUDES = "GESTIÓN DE SOLICITUDES";
    public static final String ID_ESTADO_SOLICITUD_CAMBIO_PERMISO = "SOLICITUD DE CAMBIO O PERMISO";
    public static final String ID_ESTADO_SOLICITUD_LICENCIA_NO_REMUNERADA = "SU SOLICITUD DE LICENCIA NO REMUNERADA HA SIDO";
    public static final String ID_ESTADO_SOLICITUD_LICENCIA_PENDIENTE = "PENDIENTE DE APROBACIÓN";
    public static final String ID_ESTADO_SOLICITUD_LICENCIA_APROBADO = "APROBADO";
    public static final String ID_ESTADO_SOLICITUD_LICENCIA_RECHAZADO = "RECHAZADO";

    /**
     * Token
     */
    public static final Integer ID_TOKEN_SOLICITADO = 0;
    public static final Integer ID_TOKEN_USADO = 1;
    public static final Integer ID_TOKEN_APROBADO = 2;
    public static final Integer ID_TOKEN_RECHAZADO = 3;

    /**
     * Cable evento tipo detalle
     */
    public static final int CLASE_EVENTO_INICIO = 1; // Inicio de operación (Sí)
    public static final int CLASE_EVENTO_FIN = 2; // Fin de operación (Sí)

    /**
     * Tipos de hora para reporte de horas
     */
    public static final String RPH_DIURNAS = "diurna";
    public static final String RPH_NOCTURNAS = "nocturna";
    public static final String RPH_EXTRA_DIURNA = "extra_diurna";
    public static final String RPH_EXTRA_NOCTURNA = "extra_nocturna";
    public static final String RPH_FESTIVO_DIURNO = "feriado_diurna";
    public static final String RPH_FESTIVO_NOCTURNO = "feriado_nocturna";
    public static final String RPH_FESTIVO_EXTRA_DIURNO = "feriado_extra_diurna";
    public static final String RPH_FESTIVO_EXTRA_NOCTURNO = "feriado_extra_nocturna";
    public static final String RPH_FESTIVO_NOCTURNO_COMPENSATORIO = "festivo_nocturno_con_compensatorio";
    public static final String RPH_DOMINICAL_DIURNO_COMPENSATORIO = "dominical_diurno_con_compensatorio";
    public static final String RPH_DOMINICAL_NOCTURNO_COMPENSATORIO = "dominical_nocturno_con_compensatorio";
    public static final String RPH_DOMINICAL_EXTRA_DIURNO_COMPENSATORIO = "dominical_diurna_extra_con_compensatorio";
    public static final String RPH_DOMINICAL_EXTRA_NOCTURNO_COMPENSATORIO = "dominical_nocturna_extra_con_compensatorio";

    // Gestor Novedad (Lista distribución cerrado semana)
    public static final String PROCESO_GESTION_NOVEDAD = "GESNOV";
    public static final String ID_TEMPLATE_CERRADO_SEMANA = "templateLiquidacionGestor";

    // Parametrizacion Georeferenciacion
    public static final Integer RADIO_TIERRA = 6371; // kilometros

    /**
     * Da formato a xmlGregorianFormat utilizando el formato JDBC predeterminado
     * yyyy-MM-ddTHH:mm:ss
     *
     * @param d Fecha
     * @return
     */
    public static String xmlGregorianFormat(Date d) {
        return d == null ? "" : XML_GREGORIAN_FORMAT.format(d) + "T00:00:00";
    }

    /**
     * Da formato a timestamp utilizando el formato JDBC predeterminado
     * yyyy-MM-dd
     *
     * @param d Fecha
     * @return
     */
    public static String dateFormat(Date d) {
        return d == null ? "" : DATE_FORMAT.format(d);
    }

    /**
     * Da formato a timestamp utilizando el formato JDBC predeterminado
     * yyyy-MM-dd
     *
     * @param d Fecha
     * @return
     */
    public static Date dateFormat(String d, String s) {

        SimpleDateFormat formato = new SimpleDateFormat(s);

        try {
            // Convertir la cadena a un objeto Date
            Date fecha = formato.parse(d);
            return fecha;
        } catch (ParseException e) {
            // Manejar la excepción si el formato no coincide
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Da formato de fecha dd-MM-yyyy
     *
     * @param d Fecha
     * @return
     */
    public static String dateFormatFechaDDMMYYYY(Date d) {
        return d == null ? "" : DATE_FORMAT_DD_MM_YYYY.format(d);
    }

    /**
     * Da formato a timestamp utilizando el formato JDBC predeterminado yyyyMMdd
     *
     * @param d Fecha
     * @return
     */
    public static String dateFormatByCode(Date d) {
        return d == null ? "" : DATEBYCODE.format(d);
    }

    /**
     * Permite retornar el mes en formato String MM
     *
     * @param d Fecha
     * @return
     */
    public static String dateFormatMM(Date d) {
        return d == null ? "" : DATE_FORMAT_MM.format(d);
    }

    /**
     * Da formato a timestamp utilizando el formato JDBC predeterminado
     * yyyy-MM-dd HH:mm:ss
     *
     * @param d Fecha
     * @return
     */
    public static String dateTimeFormat(Date d) {
        return d == null ? "" : DATE_TIME_FORMAT.format(d);
    }

    /**
     * Da formato a hora HH:mm
     *
     * @param d Fecha
     * @return
     */
    public static String dateToTime(Date d) {
        return d == null ? "" : DATE_TO_TIME_FORMAT.format(d);
    }

    /**
     * Da formato a hora HH:mm
     *
     * @param d Fecha
     * @return
     */
    public static String dateToAmPmFormat(Date d) {
        return d == null ? "" : DATE_TO_AM_PM_FORMAT.format(d);
    }

    /**
     * Da formato a hora HH:mm:ss
     *
     * @param d Fecha
     * @return
     */
    public static String dateToTimeHHMMSS(Date d) {
        return d == null ? "" : DATE_TO_TIME_FORMAT_HH_MM_SS.format(d);
    }

    /**
     * Convierte un String a una java.util.Date utilizando el formato JDBC
     * predeterminado yyyy-MM-dd
     *
     * @param fecha
     * @return un java.util.Date o null si el formato es incorrecto
     */
    public static Date toDate(String fecha) {
        try {
            return DATE_FORMAT.parse(fecha);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Da formato a timestamp utilizando el formato JDBC predeterminado
     * dd/MM/yyyy
     *
     * @param fecha
     * @return
     */
    public static Date toDateWS(String fecha) {
        try {
            return DATE_FORMAT_WS.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Da formato a timestamp utilizando el formato JDBC predeterminado
     * dd/MM/yyyy HH:mm:ss
     *
     * @param fecha
     * @return
     */
    public static Date toDateWS_DDMMYYHHMMSSZZZ(String fecha) {
        try {
            if (Util.isStringNullOrEmpty(fecha)) {
                return null;
            }
            return DATE_FORMAT_DDMMYYHHMMSSZZZ.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Obtiene en objeto String la hora inicial de la hora enviada como
     * parametro ej: d = 08:48:00 retorna 08:00:00
     *
     * @param d Date
     * @return String HH:mm:ss
     */
    public static String startTimeByDate(Date d) {
        if (d == null) {
            return null;
        }
        return TIME_START.format(d);
    }

    /**
     * Obtiene en objeto String la hora final de la hora enviada como parametro
     * ej: d = 08:48:00 retorna 08:59:59
     *
     * @param d Date
     * @return String HH:mm:ss
     */
    public static String endTimeByDate(Date d) {
        if (d == null) {
            return null;
        }
        return TIME_END.format(d);
    }

    /**
     * Da formato a hora HH:mm
     *
     * @param d Fecha
     * @return
     */
    public static String dateFormatT(Date d) {
        return d == null ? "" : DATE_FORMAT_YYYY_MM_T_DD_HH_MM_SS.format(d);
    }

    /**
     * Permite unificar la instancia de año-mes-dia de un objeto Date, con la
     * isntancia hora:minuto:segundo de otro Date
     *
     * @param fecha Date
     * @param hora Date
     * @return Date yyyy-MM-dd HH:mm:ss
     */
    public static Date unirFechaHoraByDates(Date fecha, Date hora) {
        if (fecha == null) {
            return null;
        }
        if (hora == null) {
            return null;
        }
        String sFecha = dateFormat(fecha);
        String sHora = dateToTimeHHMMSS(hora);
        return dateTimeFormat(sFecha + " " + sHora);
    }

    /**
     * Devuelve el valor de un key en el archivo bundle
     *
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        Locale locale = new Locale("es", "CO");
        try {
            return ResourceBundle.getBundle("bundle", locale).getString(key);
        } catch (Exception e) {
            GFile.saveFileException(e);
            return "";
        }
    }

    public static void actualizarPropiedades(String diaInicio, String diaFin, String diaCorte) {
        // Obtener la ruta del archivo de propiedades
        URL fileUrl = Util.class.getClassLoader().getResource("bundle.properties");
        if (fileUrl != null) {

        }
        Properties propiedades = new Properties();

        try (FileInputStream inputStream = new FileInputStream(fileUrl.toString())) {
            // Cargar propiedades existentes
            propiedades.load(inputStream);
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de propiedades: " + e.getMessage());
        }

        // Actualizar las propiedades específicas
        propiedades.setProperty("dia_inicio_semana", diaInicio);
        propiedades.setProperty("dia_fin_semana", diaFin);
        propiedades.setProperty("dia_corte_semana", diaCorte);

        try (FileOutputStream outputStream = new FileOutputStream(fileUrl.toString())) {
            // Guardar cambios en el archivo
            propiedades.store(outputStream, "Actualización de propiedades de días de la semana");
            System.out.println("Propiedades actualizadas correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo de propiedades: " + e.getMessage());
        }
    }

    public static List<String> getFileListByPath(String path) {
        List<String> results = new ArrayList<>();

        File[] files = new File(path).listFiles();

        if (files != null) {
            for (File f : files) {
                String imgPath = path + f.getName();
                results.add(imgPath);
            }
        }

        return results;
    }

    public static List<String> getFileList(int id, String op) {
        String path = "";
        List<String> results = new ArrayList<>();
        switch (op) {
            case "danos":
                path = Util.getProperty("novedadDano.dir") + id + "/";
                break;
            case "operacion_grua":
                path = Util.getProperty("operacionGrua.dir") + id + "/";
                break;
            case "vehiculo_documentos":
                path = Util.getProperty("vehiculoDocumentos.dir") + id + "/";
                break;
            case "novedad_documentos":
                path = Util.getProperty("novedadDocumentos.dir") + id + "/";
                break;
            case "novedad_infra_docs":
                path = Util.getProperty("novedadInfraDocs.dir") + id + "/";
                break;
            case "novedad_infra_seg":
                path = Util.getProperty("novedadInfraSeg.dir") + id + "/";
                break;
            case "generica_documento":
                path = Util.getProperty("genericaDocumentos.dir") + id + "/";
                break;
            case "armamento":
                path = Util.getProperty("armamento.dir") + id + "/";
                break;
            case "armamento_documentos":
                path = Util.getProperty("armamentoDoc.dir") + id + "/";
                break;
            case "registro_estado_armamento":
                path = Util.getProperty("registroEstadoArmamento.dir") + id + "/";
                break;
            case "aseoBano":
                path = Util.getProperty("aseoBano.dir") + id + "/";
                break;
            case "aseoCabina":
                path = Util.getProperty("aseoCabina.dir") + id + "/";
                break;
            case "novedadCab":
                path = Util.getProperty("novedadCab.dir") + id + "/";
                break;
            case "segReportePilona":
                path = Util.getProperty("segReportePilona.dir") + id + "/";
                break;
            case "novedadInfrastruct":
                path = Util.getProperty("novedadInfrastruct.dir") + id + "/";
                break;
            case "novedadInfrastructGmo":
                path = Util.getProperty("novedadInfrastructGmo.dir") + id + "/";
                break;
            case "pqr":
                path = Util.getProperty("pqr.dir") + id + "/";
                break;
            case "segReportePilonaMinuta":
                path = Util.getProperty("segReportePilonaMinuta.dir") + id + "/";
                break;
            case "segAseoArma":
                path = Util.getProperty("segAseoArma.dir") + id + "/";
                break;
            case "segAseoArmaMinuta":
                path = Util.getProperty("segAseoArmaMinuta.dir") + id + "/";
                break;
            case "estadoMedioComunica":
                path = Util.getProperty("estadoMedioComunica.dir") + id + "/";
                break;
            case "novedadMttoDiario":
                path = Util.getProperty("novedadMttoDiario.dir") + id + "/";
                break;
            case "actividadInfraDiario":
                path = Util.getProperty("actividadInfraDiario.dir") + id + "/";
                break;
            case "aseoGeneral":
                path = Util.getProperty("aseoGeneral.dir") + id + "/";
                break;
            case "novedadMtto":
                path = Util.getProperty("novedadMtto.dir") + id + "/";
                break;
            case "procesoDisciplinario":
                path = Util.getProperty("procesoDisciplinario.dir") + id + "/";
                break;
            case "procesoDisciplinarioGen":
                path = Util.getProperty("procesoDisciplinarioGen.dir") + id + "/";
                break;
            case "layoutTipoCarroceria":
                path = Util.getProperty("layoutTipoCarroceria.dir") + id + "/";
                break;
            case ConstantsUtil.KEY_DIR_NOVEDAD_DOCS:
                path = Util.getProperty(ConstantsUtil.KEY_DIR_NOVEDAD_DOCS) + id + "/";
                break;
        }

        File[] files = new File(path).listFiles();

        if (files
                != null) {
            for (File f : files) {
                results.add(f.getName());
            }
        }

        return results;
    }

    public static StreamedContent mostrarImagen(String nombreArchivo, String ruta) throws IOException {
        ByteArrayOutputStream out = null;
        if (nombreArchivo.length() > 0) {
            out = traerArchivo(ruta, nombreArchivo);
            InputStream myInputStream2 = new ByteArrayInputStream(out.toByteArray());
            return new DefaultStreamedContent(myInputStream2);
        } else {
            return null;
        }
    }

    public static Image mostrarImagenN(String nombreArchivo, String ruta) throws IOException {
        ByteArrayOutputStream out = null;
        if (nombreArchivo.length() > 0) {
            out = traerArchivo(ruta, nombreArchivo);
            InputStream myInputStream2 = new ByteArrayInputStream(out.toByteArray());
            return ImageIO.read(myInputStream2);
        } else {
            return null;
        }
    }

    public static Image mostrarImagenN(String ruta) throws IOException {
        ByteArrayOutputStream out = null;
        if (ruta.length() > 0) {
            out = traerArchivo(ruta);
            InputStream myInputStream2 = new ByteArrayInputStream(out.toByteArray());
            return ImageIO.read(myInputStream2);
        } else {
            return null;
        }
    }

    public static StreamedContent mostrarImagen(String ruta) throws IOException {
        ByteArrayOutputStream out = null;
        if (ruta.length() > 0) {
            out = traerArchivo(ruta);
            InputStream myInputStream2 = new ByteArrayInputStream(out.toByteArray());
            return new DefaultStreamedContent(myInputStream2);
        } else {
            return null;
        }
    }

    public static StreamedContent mostrarDocumento(String ruta, String nombre) throws IOException {
        ByteArrayOutputStream out = null;
        if (ruta.length() > 0) {
            out = traerArchivo(ruta);
            InputStream myInputStream2 = new ByteArrayInputStream(out.toByteArray());
            Path path = new File(ruta).toPath();
            String mimeType = Files.probeContentType(path);
            String ext = ruta.substring(ruta.lastIndexOf('.'), ruta.length());
            return new DefaultStreamedContent(myInputStream2, mimeType, nombre + ext);
        } else {
            return null;
        }
    }

    public static ByteArrayOutputStream traerArchivo(String ruta, String nombre_archivo) {

        ByteArrayOutputStream out = null;
        String path = ruta + nombre_archivo;
        InputStream in = null;

        try {
            File remoteFile = new File(path);
            in = new BufferedInputStream(new FileInputStream(remoteFile));
            out = new ByteArrayOutputStream((int) remoteFile.length());
            byte[] buffer = new byte[1024];
            int len = 0; //Read length
            while ((len = in.read(buffer, 0, buffer.length)) != - 1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            String msg = "ERROR DESCARGANDO ARCHIVO " + e.getMessage();
            System.out.println(msg);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
            }
        }

        return out;
    }

    public static ByteArrayOutputStream traerArchivo(String ruta) {

        ByteArrayOutputStream out = null;
        InputStream in = null;

        try {
            File remoteFile = new File(ruta);
            in = new BufferedInputStream(new FileInputStream(remoteFile));
            out = new ByteArrayOutputStream((int) remoteFile.length());
            byte[] buffer = new byte[1024];
            int len = 0; //Read length
            while ((len = in.read(buffer, 0, buffer.length)) != - 1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
            }
        }

        return out;
    }

    public static String saveFile(UploadedFile media, int id, String op) {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        String ext = media.getFileName().substring(media.getFileName().lastIndexOf('.'), media.getFileName().length());
        String path = "";
        String pathNovedadDocumentos = "";

        switch (op) {
            case "danos":
                path = Util.getProperty("novedadDano.dir") + id + "/";
                break;
            case "armamento":
                path = Util.getProperty("armamento.dir") + id + "/";
                break;
            case "armamento_documentos":
                path = Util.getProperty("armamentoDoc.dir") + id + "/";
                break;
            case "registro_estado_armamento":
                path = Util.getProperty("registroEstadoArmamento.dir") + id + "/";
                break;
            case "informe_seguridad":
                path = Util.getProperty("informeSeguridad.dir") + id + "/";
                break;
            case "multa_documentos":
                path = Util.getProperty("multas.dir") + id + "/";
                break;
            case "novedad_documento":
                path = Util.getProperty("novedadDocumentos.dir") + id + "/";
                break;
            case "novedad_infra_docs":
                path = Util.getProperty("novedadInfraDocs.dir") + id + "/";
                break;
            case "novedad_infra_seg":
                path = Util.getProperty("novedadInfraSeg.dir") + id + "/";
                break;
            case "seguimiento_archivo":
                path = Util.getProperty("seguimientoArchivos.dir") + id + "/";
                break;
            case "sst_documento_empresa":
                path = Util.getProperty("sstDocumentoEmpresa.dir") + id + "/";
                break;
            case "sst_documento_visitante":
                path = Util.getProperty("sstDcumentoVisitante.dir") + id + "/";
                break;
            case "generica_documento":
                path = Util.getProperty("genericaDocumentos.dir") + id + "/";
                break;
            case "incapacidad":
                path = Util.getProperty("seguimientoIncapacidad.dir") + id + "/";
                break;
            case "operacion_grua":
                path = Util.getProperty("operacionGrua.dir") + id + "/";
                break;
            case "vehiculo_idf":
                path = Util.getProperty("vehiculoIdf.dir");
                break;
            case "vehiculo_odometro":
                path = Util.getProperty("vehiculoOdometro.dir");
                break;
            case "vehiculo_documentos":
                path = Util.getProperty("vehiculoDocumentos.dir") + id + "/";
                break;
            case "asignacion":
                path = Util.getProperty("asignacion.dir");
                break;
            case "kmMtto":
                path = Util.getProperty("kmMtto.dir");
                break;
            case "aseoCabina":
                path = Util.getProperty("aseoCabina.dir") + id + "/";
                break;
            case "novedadCab":
                path = Util.getProperty("novedadCab.dir") + id + "/";
                break;
            case "aseoBano":
                path = Util.getProperty("aseoBano.dir") + id + "/";
                break;
            case "segReportePilona":
                path = Util.getProperty("segReportePilona.dir") + id + "/";
                break;
            case "novedadInfrastruct":
                path = Util.getProperty("novedadInfrastruct.dir") + id + "/";
                break;
            case "novedadInfrastructGmo":
                path = Util.getProperty("novedadInfrastructGmo.dir") + id + "/";
                break;
            case "novedadInfrastructGmoGuardar":
                path = Util.getProperty("novedadInfrastructGmo.dir") + id + "/";
                break;
            case "pqr":
                path = Util.getProperty("pqr.dir") + id + "/";
                break;
            case "pqrGuardar":
                path = Util.getProperty("pqr.dir") + id + "/";
                break;
            case "segReportePilonaMinuta":
                path = Util.getProperty("segReportePilonaMinuta.dir") + id + "/";
                break;
            case "segAseoArma":
                path = Util.getProperty("segAseoArma.dir") + id + "/";
                break;
            case "segAseoArmaMinuta":
                path = Util.getProperty("segAseoArmaMinuta.dir") + id + "/";
                break;
            case "estadoMedioComunica":
                path = Util.getProperty("estadoMedioComunica.dir") + id + "/";
                break;
            case "novedadMttoDiario":
                path = Util.getProperty("novedadMttoDiario.dir") + id + "/";
                break;
            case "cableAccDocumento":
                path = Util.getProperty("cableAccDocumento.dir") + id + "/";
                break;
            case "actividadInfraDiario":
                path = Util.getProperty("actividadInfraDiario.dir") + id + "/";
                break;
            case "aseoGeneral":
                path = Util.getProperty("aseoGeneral.dir") + id + "/";
                break;
            case "novedadMtto":
                path = Util.getProperty("novedadMtto.dir") + id + "/";
                break;
            case "procesoDisciplinario":
                path = Util.getProperty("procesoDisciplinario.dir") + id + "/";
                break;
            case "procesoDisciplinarioGen":
                path = Util.getProperty("procesoDisciplinarioGen.dir") + id + "/";
                break;
            case "layoutTipoCarroceria":
                path = Util.getProperty("layoutTipoCarroceria.dir") + id + "/";
                break;
            case "reporteFiducia":
                path = Util.getProperty("reporteFiducia.dir") + id + "/";
                break;
            case "carga_novedades":
            case "carga_novedades_operadores":
                path = Util.getProperty("cargaNovedades.dir") + id + "/";
                break;
            case "hallazgosInfracciones":
                path = Util.getProperty("hallazgosInfracciones.dir");
                break;
            case "recapacitacion":
                path = Util.getProperty("recapacitacion.dir") + id + "/";
                break;
            case "planificacionRecursosActividades":
                path = Util.getProperty("planificacionRecursosActividades.dir");
                break;
            case "planificacionRecursosEjecucion":
                path = Util.getProperty("planificacionRecursosEjecucion.dir");
                break;
            case "planificacionRecursosBienestar":
                path = Util.getProperty("planificacionRecursosBienestar.dir");
                break;
            case "planificacionRecursosSeguridad":
                path = Util.getProperty("planificacionRecursosSeguridad.dir");
                break;
            case "planificacionRecursosMedicina":
                path = Util.getProperty("planificacionRecursosMedicina.dir");
                break;
            case "planificacionRecursosAusentismoPAA":
                path = Util.getProperty("planificacionRecursosReprogramacionPAA.dir");
                break;
            case "planificacionRecursosEntregaOperadores":
                path = Util.getProperty("planificacionRecursosEntregaOperadores.dir");
                break;
            case "planificacionRecursosAscensoPadron":
                path = Util.getProperty("planificacionRecursosAscensoPadron.dir");
                break;
            case "planificacionRecursosVacaciones":
                path = Util.getProperty("planificacionRecursosVacaciones.dir");
                break;
            case ConstantsUtil.KEY_DIR_NOVEDAD_DOCS:
                path = Util.getProperty(ConstantsUtil.KEY_DIR_NOVEDAD_DOCS) + id + "/";
                break;
            case ConstantsUtil.KEY_DIR_NOVEDAD_ATV:
                path = Util.getProperty(ConstantsUtil.KEY_DIR_NOVEDAD_ATV) + id + "/";
                break;
        }

        try {
            InputStream fin = media.getInputstream();
            in = new BufferedInputStream(fin);

            File baseDir = new File(path);
            if (!baseDir.exists()) {
                baseDir.mkdirs();
            }

            //acá se agregan los casos que corresponden a archivos de carga de información
            File file = new File(path + generarNombre(media.getFileName()));
            pathNovedadDocumentos = file.getAbsolutePath();
            if (op.equals("asignacion")
                    || op.equals("carga_novedades")
                    || op.equals("carga_novedades_operadores")
                    || op.equals("hallazgosInfracciones")
                    || op.equals("planificacionRecursosActividades")
                    || op.equals("planificacionRecursosBienestar")
                    || op.equals("planificacionRecursosSeguridad")
                    || op.equals("planificacionRecursosEjecucion")
                    || op.equals("planificacionRecursosMedicina")
                    || op.equals("planificacionRecursosAscensoPadron")
                    || op.equals("planificacionRecursosEntregaOperadores")
                    || op.equals("planificacionRecursosReprogramacionPAA")
                    || op.equals("planificacionRecursosVacaciones")) {
                file = new File(path + media.getFileName());
                pathNovedadDocumentos = path + media.getFileName();
                OutputStream fout = new FileOutputStream(file);
                out = new BufferedOutputStream(fout);
                byte buffer[] = new byte[1024];
                int ch = in.read(buffer);
                while (ch != -1) {
                    out.write(buffer, 0, ch);
                    ch = in.read(buffer);
                }
                return pathNovedadDocumentos;
            }
            //-------------------------------------------------------------------------------

            OutputStream fout = new FileOutputStream(file);
            out = new BufferedOutputStream(fout);
            byte buffer[] = new byte[1024];
            int ch = in.read(buffer);
            while (ch != -1) {
                out.write(buffer, 0, ch);
                ch = in.read(buffer);
            }
            if (op.equals(ConstantsUtil.KEY_DIR_NOVEDAD_ATV)) {
                return pathNovedadDocumentos;
            }
            if (op.equals("novedad_documento") || op.equals("generica_documento")
                    || op.equals("procesoDisciplinario") || op.equals("procesoDisciplinarioGen")
                    || op.equals("novedadInfrastructGmo") || op.equals("pqr") || op.equals("recapacitacion")) {
                if (ext.contains("pdf")) {
                    return pathNovedadDocumentos;
                } else {
                    return path;
                }
            }
            if (op.equals("novedadMtto") || op.equals("novedad_infra_seg")) {
                if (ext.contains("mp4") || ext.contains("MP4")) {
                    return pathNovedadDocumentos;
                } else {
                    return path;
                }
            }
            if (op.equals("novedad_infra_docs")) {

                String tipoArchivo = Files.probeContentType(file.toPath());

                if (!(tipoArchivo.equals("image/jpeg") || tipoArchivo.equals("image/png"))) {
                    return pathNovedadDocumentos;
                } else {
                    return path;
                }
            }
            if (op.equals("vehiculo_idf")
                    || op.equals("vehiculo_documentos")
                    || op.equals("kmMtto")
                    || op.equals("sst_documento_empresa")
                    || op.equals("seguimiento_archivo")
                    || op.equals("sst_documento_visitante")
                    || op.equals("incapacidad")
                    || op.equals("armamento_documentos")
                    || op.equals("vehiculo_odometro")
                    || op.equals("cableAccDocumento")
                    || op.equals("reporteFiducia")
                    || op.equals("novedadInfrastructGmoGuardar")
                    || op.equals("pqrGuardar")
                    || op.equals("informe_seguridad")) {
                return pathNovedadDocumentos;
            }
//            System.out.println("Path Cambiado: " + path);
            return path;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean deleteFile(String path) {
        File arhivo = new File(path);
        return arhivo.delete();
    }

    public static void deleteImage(int id, String op) {
        String path = "";
        switch (op) {
            case "danos":
                path = Util.getProperty("novedadDano.dir") + id + "/";
                break;
            case "operacion_grua":
                path = Util.getProperty("operacionGrua.dir") + id + "/";
                break;
            case "vehiculo_documentos":
                path = Util.getProperty("vehiculoDocumentos.dir") + id + "/";
                break;
            case "novedad_documentos":
                path = Util.getProperty("novedadDocumentos.dir") + id + "/";
                break;
            case "generica_documento":
                path = Util.getProperty("genericaDocumentos.dir") + id + "/";
                break;
        }

        File[] files = new File(path).listFiles();

        if (files != null) {
            for (File f : files) {
                if (f.getAbsolutePath().contains("jpg")
                        || f.getAbsolutePath().contains("png")
                        || f.getAbsolutePath().contains("jpeg")) {
                    f.delete();
                }
            }
        }
    }

    public static String generarNombre(String file) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmssSS");
        String date = df.format(new Date());
//        String ext = file.substring(file.lastIndexOf('.'), file.length());
        return date + "_" + StringUtils.stripAccents(file);
    }

    public static void saveFileException(Throwable ex) {
        DataOutputStream out = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        try {
            File file = new File(System.getProperty("user.dir") + "/logs");
            if (!file.exists()) {
                file.mkdirs();
            }
            if (file.exists()) {
                file = new File(file.getPath(), df.format(new Date()) + ".log");
                out = new DataOutputStream(new FileOutputStream(file));
                ex.printStackTrace(new PrintStream(out));
                ex.printStackTrace();
            }
        } catch (Exception e) {
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex1) {
//                    Logger.getLogger(GFile.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }

    public static boolean validarFecha(Date fecha) {
        return fecha.compareTo(MovilidadUtil.fechaHoy()) > 0;
    }

    public static boolean validarFechaCambioEstado(Date fechaIni, Date fechaFin) {
        return fechaIni.compareTo(fechaFin) > 0;
    }

    public static boolean validarFechaPM(Date desde, Date hasta) {
        return desde.compareTo(hasta) == 0;
    }

    public static String durationToStr(Duration time) {
        return time != null ? String.format("%02d:%02d:%02d", (time.getDays() > 0 ? (time.getHours() + 24) : time.getHours()),
                time.getMinutes(), time.getSeconds()) : null;
    }

    public static String formatDecimal(BigDecimal n) {
        Locale locale = new Locale("es", "CO");
        NumberFormat nf = NumberFormat.getInstance(locale);
        nf.setMaximumFractionDigits(3);
        nf.setGroupingUsed(true);
        return nf.format(n);
    }

    /**
     * Guardar archivo pasando como parametro el path completo del documento si
     * op es false sobreescribir archivo, op es true escribir
     *
     * @param file archivo UploadedFile
     * @param ruta path del archivo completo ejemplo - /rigel/archivo.xml
     * @param op true escribir y false para el caso de sobreescribir
     * @return retorna true si la operación fue correcta
     */
    public static boolean saveFile(UploadedFile file, String ruta, boolean op) {
        try {
            FileOutputStream fileo = null;
            File fi = new File(ruta);
            if (op) {
                if (!fi.exists()) {
                    fi.mkdir();
                }
                ruta = ruta + file.getFileName();
            }
            fileo = new FileOutputStream(new File(ruta));
            fileo.write(file.getContents());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean crearDirectorio(String dir) {
        File baseDir = new File(dir);
        if (!baseDir.exists()) {
            return baseDir.mkdirs();
        } else {
            return true;
        }
    }

    /**
     *
     * @param fecha a sumar o restar días
     * @param dias numero de días que se sumaran o restaran - negativo para
     * restar, positivo para sumar
     * @return fecha procesada
     */
    public static Date DiasAFecha(Date fecha, int dias) {
        if (dias == 0) {
            return fecha;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }

    /**
     * @param d fecha a preguntar
     * @return 1 si es Domingo, 2 si es Sabado, 0 si es otro dia
     */
    public static int isDay(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int dia = calendar.get(Calendar.DAY_OF_WEEK);
        if (dia == Calendar.SUNDAY) {
            return 1; //Domingo
        }
        if (dia == Calendar.SATURDAY) {
            return 2; //Sabado
        }
        return 0;
    }

    /**
     * Da formato a timestamp utilizando el formato JDBC predeterminado
     * yyyy-MM-dd HH:mm:ss
     *
     * @param d Fecha
     * @return date
     */
    public static Date dateTimeFormat(String d) {
        try {
            return d == null ? null : DATE_TIME_FORMAT.parse(d);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date getPrimerDiaMes(Date d) {
        Calendar primerDiaMes = Calendar.getInstance();
        primerDiaMes.setTime(d);
        primerDiaMes.setFirstDayOfWeek(Calendar.SUNDAY);
        primerDiaMes.set(Calendar.DAY_OF_MONTH, 1);
        return primerDiaMes.getTime();
    }

    public static Date getUltimoDiaMes(Date d) {
        Calendar ultimoDiaMes = Calendar.getInstance();
        ultimoDiaMes.setTime(d);
        ultimoDiaMes.setFirstDayOfWeek(Calendar.SUNDAY);
        ultimoDiaMes.set(Calendar.DAY_OF_MONTH, ultimoDiaMes.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ultimoDiaMes.getTime();
    }

    public static List<Date> getListDateMes(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        List<Date> listDate = new ArrayList<>();
        d = getPrimerDiaMes(d);
        for (int i = 1; i <= dia; i++) {
            Calendar ca = Calendar.getInstance();
            d.setDate(i);
            ca.setTime(d);
            listDate.add(ca.getTime());
        }
        return listDate;
    }

    public static int getNumSemanasMes(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        return cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }

    public static Date getPrimerDiaSemana(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR));
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return cal.getTime();
    }

    public static List<Date> getListaEntreFechas(Date ini, Date fin) {
        List<Date> listaFechas = new ArrayList<>();
        while (!ini.after(fin)) {
            listaFechas.add(ini);
            ini = DiasAFecha(ini, 1);
        }
        return listaFechas;
    }

    /**
     * Fecha inicial es domingo
     *
     * @param d fecha del mes a retornar
     * @return mapa, con llave numerica ej: 1,2,3,4,5,6 valor lista de las
     * fechas comprendidas entre esas semanas. HashMap<Integer, List<Date>>
     */
    public static HashMap getMapDiaPorSemanas(Date d) {
        HashMap<Integer, List<Date>> mapSemanasMes = new HashMap<>();
        int numSem = getNumSemanasMes(d);
        Date primerDiaMes, ultimoDiaMes, ultDiaSemana = null, iniDiaSemana = null;
        primerDiaMes = getPrimerDiaMes(d);
        ultimoDiaMes = getUltimoDiaMes(d);
        List<Date> listaEntreFechas;
        for (int i = 0; i < numSem; i++) {
            if (i == 0) {
                ultDiaSemana = DiasAFecha(getPrimerDiaSemana(primerDiaMes), 6);
                listaEntreFechas = getListaEntreFechas(primerDiaMes, ultDiaSemana);
                mapSemanasMes.put(i + 1, listaEntreFechas);
            }
            if (!(i == (numSem - 1)) && !(i == 0)) {
                ultDiaSemana = DiasAFecha(iniDiaSemana, 6);
                listaEntreFechas = getListaEntreFechas(iniDiaSemana, ultDiaSemana);
                mapSemanasMes.put(i + 1, listaEntreFechas);
            }
            if (i == (numSem - 1)) {
                listaEntreFechas = getListaEntreFechas(iniDiaSemana, ultimoDiaMes);
                mapSemanasMes.put(i + 1, listaEntreFechas);
            }
            iniDiaSemana = DiasAFecha(ultDiaSemana, 1);
        }
        return mapSemanasMes;
    }

    /**
     *
     * @param ini Fecha de inicio
     * @param fin Fecha de Fin
     * @return Strinh 00H 00m 00s
     */
    public static String getDiferenciaFechas(Date ini, Date fin) {
        try {
            java.time.Duration d = java.time.Duration.between(
                    ini.toInstant(),
                    fin.toInstant()
            );
            javax.xml.datatype.Duration dur = DatatypeFactory.newInstance().newDuration(d.toString());
            return dur.getHours() + "H" + dur.getMinutes() + "m" + dur.getSeconds() + "s";
        } catch (Exception e) {
            return "";
        }
    }

    public static String getStringSinEspacios(String a) {
        return a.replaceAll("\\s", "");
    }

    public static String getNumerosDeString(String a) {
        char[] toCharArray = a.toCharArray();
        String as = "";
        for (char c : toCharArray) {
            if (Character.isDigit(c)) {
                as = as + c;
            }
        }
        return as;
    }

    public static String getUrlContext(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String url = request.getRequestURL().toString();
        String ctxPath = request.getContextPath();
        url = url.replaceFirst(uri, "");
        url = url + ctxPath;
        return url;
    }

    public static double distanciaCoord(double lat1, double lng1, double lat2, double lng2) {
        double radioTierra = RADIO_TIERRA;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
        double distancia = radioTierra * va2;

        return distancia;
    }

    public static Color generateRamdomColor() {
        Random randomGenerator = new Random();
        int red = randomGenerator.nextInt(256);
        int green = randomGenerator.nextInt(256);
        int blue = randomGenerator.nextInt(256);
        return new Color(red, green, blue);

    }

    public static boolean isStringNullOrEmpty(String s) {
        if (s == null) {
            return true;
        }
        return s.isEmpty();
    }

    public static Date intanceDateFromDate(Date d) {
        return Util.toDate(Util.dateFormat(d));
    }

    public static GeocodingDTO conocerUbicacionVehiculo(String keyApi, String urlGeoSerice, String codVehiculo) {
        try {
            CurrentLocation currentPosition = GeoService.getCurrentPosition(urlGeoSerice + codVehiculo);
            if (currentPosition == null) {
                System.out.println("Error obtener cordenadas del servicio de GEO");
                return null;
            }
            GoogleApiGeocodingServices gapi = new GoogleApiGeocodingServices(keyApi);
            return gapi.onAddressFromLatLng(currentPosition.get_Latitude(), currentPosition.get_Longitude());
        } catch (Exception e) {
            return null;
        }
    }

    public static Date primerDiaMes(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date ultimoDiaMes(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static int monthOfYear(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int year(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.YEAR);
    }

    public static String generarUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     *
     * @param d objeto Date
     * @return String Fecha en formato YYYY-MM-DD
     */
    public String getDateFormatYYYYMMDD(Date d) {
        try {
            if (Util.isStringNullOrEmpty(d.toString())) {
                return null;
            }
            return XML_GREGORIAN_FORMAT.format(d);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param d objeto Date
     * @return String Fecha en formato HH:mm:ss
     */
    public String getDateFormatHHmmss(Date d) {
        try {
            if (Util.isStringNullOrEmpty(d.toString())) {
            }
            return DATE_TO_TIME_FORMAT_HH_MM_SS.format(d);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Permite determinar la edad dada una fecha enviada como parametro
     *
     * @param bornDate
     * @return
     */
    public static int getAge(Date bornDate) {
        int age;
        // Obtiene la fecha actual
        Date currentDate = new Date();

        // Calcula la edad
        age = currentDate.getYear() - bornDate.getYear();

        // Verifica si aún no ha cumplido años en este año
        if (bornDate.after(new Date(currentDate.getYear(), bornDate.getMonth(), bornDate.getDate()))) {
            age--;
        }
        return age;
    }

    /**
     * Permite obtener el nombre del día de la semana contenido en la fecha
     *
     * @param date de tipo Date que contiene la fecha a procesar
     * @return String con el nombre del día de la semana
     */
    public static String getDayName(Date date) {
        // Crear un objeto SimpleDateFormat con el patrón para obtener el nombre del día
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", new Locale("es", "ES"));
        // Retornar el nombre del día de la semana
        return sdf.format(date);
    }

    /**
     * Permite obtener el número de la semana del mes que corresponde a la fecha
     * dada El conteo de la semana inicia el LUNES y termina el DOMINGO
     *
     * @param date de tipo Date que contiene la fecha a evaluar
     * @return int con el valor de la semana del mes
     */
    public static int getNumberWeekOfMounth(Date date) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(date);
        return calendario.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * Permite obtener el número de la semana del año que corresponde a la fecha
     * dada El conteo de la semana inicia el LUNES y termina el DOMINGO
     *
     * @param date de tipo Date que contiene la fecha a evaluar
     * @return int con el valor de la semana del mes
     */
    public static int getNumberWeekOfYear(Date date) {
        Calendar calendario = Calendar.getInstance();
        calendario.setFirstDayOfWeek(Calendar.MONDAY);
        calendario.setTime(date);
        return calendario.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * Permite evaluar si una cadena dada está compuesta solamente por valores
     * numéricos.
     *
     * @param cadena contiene la información que se desea evaluar.
     * @return true, si el valor de parámetro contiene solamente números. false,
     * en cualquier otro caso.
     */
    public static boolean isOnlyDigits(String cadena) {
        return cadena.matches("\\d+");
    }

    /**
     * Permite determinar si un valor dado corresponde a un formato HH:mm:ss
     * acepta valores desde '00:00:00' hasta '23:59:59'
     *
     * @param time parámetro de tipo String que contiene el valor a evaluar
     * @return true si el parámetro coincide con el formato HH:mm:ss false en
     * cualquier otro caso
     */
    public static boolean isValidTimeFormat(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sdf.setLenient(false);
        try {
            sdf.parse(time);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Valida que la cadena ingresada corresponda a una hora con formato
     * HH:MM:SS los valores admitidos son entre 00:00:00 y 39:59:59
     *
     * @param hour que contiene la cadena a evaluar
     * @return true si @hour cumple las condiciones, false en cualquier otro
     * caso
     */
    public static boolean isFormatHHMMSSGreen(String hour) {
        String regex = "([0-9]|([0-3][0-9]))(:[0-5][0-9]){2}$";
        return matches(regex, hour);  // Use hour.matches instead of matches
    }

    public static boolean isTimeValidate(String cadena) {
        String regex = "([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cadena);
        return matcher.matches();
    }
    public static boolean isTimeValidateTo36(String cadena) {
        // Permite horas de 00 a 36, minutos y segundos de 00 a 59
        String regex = "(3[0-6]|[0-2]?\\d):[0-5]\\d:[0-5]\\d";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cadena);
        return matcher.matches();
    }

    public static String[] getDateRange(String startDay, String endDay, String limitDay, String limitHour) {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        int startDayWeek = getDayOfWeek(startDay);
        int endDayWeek = getDayOfWeek(endDay);
        int limitDayWeek = getDayOfWeek(limitDay);

        if (startDayWeek == -1 || endDayWeek == -1) {
            throw new IllegalArgumentException("Día de inicio o fin no válido.");
        }

        // Variable para guardar las fechas calculadas
        Date starDate;
        Date endDate;
        Date limitDate = getLimitDay(calendar, limitDayWeek);

        // Establecemos la hora en limitDate usando limitHour
        try {
            String[] hourParts = limitHour.split(":");
            int hour = Integer.parseInt(hourParts[0]);
            int minute = Integer.parseInt(hourParts[1]);

            Calendar limitCalendar = Calendar.getInstance();
            limitCalendar.setTime(limitDate);
            limitCalendar.set(Calendar.HOUR_OF_DAY, hour);
            limitCalendar.set(Calendar.MINUTE, minute);
            limitCalendar.set(Calendar.SECOND, 0);

            limitDate = limitCalendar.getTime();
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de hora no válido. Use HH:mm:ss");
        }
        
        // Verificamos si la fecha actual es menor a la fecha de corte
        if (currentDate.before(limitDate)) {
            // Establecemos la fecha de inicio como el próximo "DIA_INICIO" a partir de la fecha actual
            starDate = getNextDay(calendar, startDayWeek);
            // Configuramos la fecha de fin como el próximo "DIA_FIN" después de la fecha de inicio
            endDate = getNextDay(starDate, endDayWeek);
        } else {
            // Configuramos la fecha de inicio como el segundo "DIA_INICIO" a partir de la fecha actual
            starDate = getNextDay(calendar, startDayWeek);
            starDate = getNextDay(starDate, startDayWeek); // Siguiente inicio de semana
            // Configuramos la fecha de fin como el próximo "DIA_FIN" después de la fecha de inicio
            endDate = getNextDay(starDate, endDayWeek);
        }

        // Actualizar los valores del bean con el formato correcto
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // Retornamos el rango de fechas calculado
        return new String[]{sdf.format(starDate), sdf.format(endDate)};
    }

    private static int getDayOfWeek(String dia) {
        switch (dia.toLowerCase(Locale.ROOT)) {
            case "domingo":
                return Calendar.SUNDAY;
            case "lunes":
                return Calendar.MONDAY;
            case "martes":
                return Calendar.TUESDAY;
            case "miércoles":
            case "miercoles":
                return Calendar.WEDNESDAY;
            case "jueves":
                return Calendar.THURSDAY;
            case "viernes":
                return Calendar.FRIDAY;
            case "sábado":
            case "sabado":
                return Calendar.SATURDAY;
            default:
                return -1;
        }
    }

    private static Date getLimitDay(Calendar calendar, int weekday) {
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_MONTH, weekday - currentDay);
        return calendar.getTime();
    }
    
    private static Date getNextDay(Calendar calendar, int weekday) {
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        int daysToAgree = (weekday - currentDay + 7) % 7;
        if (daysToAgree == 0) {
            daysToAgree = 7; // Avanza una semana completa si el día actual es el mismo que el objetivo
        }
        calendar.add(Calendar.DAY_OF_MONTH, daysToAgree);
        return calendar.getTime();
    }

    private static Date getNextDay(Date date, int weekDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getNextDay(calendar, weekDay);
    }

    public static boolean hour1IsLowerThanHour2(String hora1, String hora2) {
        LocalTime time1 = LocalTime.parse(hora1);
        LocalTime time2 = LocalTime.parse(hora2);
        return time1.isBefore(time2);
    }
    
    public static boolean hour1IsLowerOrEqualsToHour2(String hora1, String hora2) {
        LocalTime time1 = LocalTime.parse(hora1);
        LocalTime time2 = LocalTime.parse(hora2);
        return time1.isBefore(time2) || hora1.equals(hora2);
    }
}
