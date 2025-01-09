package com.movilidad.utils;

import static com.movilidad.utils.Util.traerArchivo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TreeSet;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.movilidad.model.NotificacionProcesoDet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage.Severity;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;

/**
 *
 * @author Soluciones IT
 */
public class MovilidadUtil implements Serializable {

    public static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm:ss");

    public static final char[] numeros = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static final char[] letras = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static final int mayorEdad = 18;
    public static final int ID_TQ04 = 128;

    public static void addErrorMessage(String msg) {
        addMessage(FacesMessage.SEVERITY_ERROR, "Error!", msg);
    }

    public static void addSuccessMessage(String msg) {
        addMessage(FacesMessage.SEVERITY_INFO, "Exito!", msg);
    }

    public static void addAdvertenciaMessage(String msg) {
        addMessage(FacesMessage.SEVERITY_WARN, "Advertencia!", msg);
    }

    public static void addFatalMessage(String msg) {
        addMessage(FacesMessage.SEVERITY_FATAL, "Error Fatal!", msg);
    }

    private static void addMessage(Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public static void hideModal(String wv) {
        PrimeFaces.current().executeScript("PF('" + wv + "').hide();");
    }

    public static void openModal(String wv) {
        PrimeFaces.current().executeScript("PF('" + wv + "').show();");
    }

    public static void clearFilter(String id) {
        PrimeFaces.current().executeScript("PF('" + id + "').clearFilters()");
    }

    public static void onLocationHref(String id) {
        PrimeFaces.current().executeScript("location.href='" + id + "'");
    }

    public static void updateComponent(String id) {
        PrimeFaces.current().ajax().update(id);
    }

    public static void runScript(String sc) {
        PrimeFaces.current().executeScript(sc);
    }

    public static void setColorBackground(String id, String color) {
        PrimeFaces.current().executeScript("document.getElementById('" + id + "').style.backgroundColor = '" + color + "';");
    }

    /**
     * Comparar dos fechas.
     *
     * @param fecha1
     * @param fecha2
     * @param conHora Valor boolean que indica:
     *
     * true, la validación se va a realizar con año_mes_dia_hora.
     *
     * false, la validación se va a realizar con año_mes_dia.
     *
     * @return
     *
     * Retorna 0 si la fecha1 es menor a la de fecha2.
     *
     * Retorna 1 si la fecha1 es igual a la del fecha2
     *
     * Retorna 2 si la fecha1 es mayor a la fecha2
     * @throws ParseException
     */
    public static int fechasIgualMenorMayor(Date fecha1, Date fecha2, boolean conHora) throws ParseException {
        if (fecha1 != null) {
            Date d1;
            Date d2;
            if (conHora) {
                d1 = fecha1;
                d2 = fecha2;
            } else {
                d1 = dateSinHora(fecha1);
                d2 = dateSinHora(fecha2);
            }
            if (d1.equals(d2)) {
                return 1;
            }
            if (d1.before(d2)) {
                return 0;
            }
        }
        return 2;
    }

    /**
     * Evalua si fechaEnCuestion esta entre desde y hasta.
     *
     * @param fechaEnCuestion Fecha a validar
     * @param desde
     * @param hasta
     * @return True si fechaEnCuestion esta en el rango de desde y hasta.
     * @throws ParseException
     */
    public static boolean betweenSinHora(Date fechaEnCuestion, Date desde, Date hasta) {
        return (dateSinHora(fechaEnCuestion).equals(dateSinHora(desde))
                || dateSinHora(fechaEnCuestion).after(desde))
                && (dateSinHora(fechaEnCuestion).equals(hasta)
                || dateSinHora(fechaEnCuestion).before(hasta));
    }

    public static boolean betweenDateToday(Date fecha1, Date fecha2, Date fechaActual) {
        Date f1 = dateSinHora(fecha1);
        Date f2 = dateSinHora(fecha2);
        Date fActual = dateSinHora(fechaActual);
        if (f1.equals(fActual)) {
            return true;
        }
        if (f2.equals(fActual)) {
            return true;
        }
        return f1.before(fActual) && f2.after(fActual);
    }

    public static boolean isDay(Date fecha, int dia) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        if (c.get(Calendar.DAY_OF_WEEK) == dia) {
            return true;
        }
        return false;
    }

    public static boolean validarUrl(String urlComparar) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getRequestURL().toString();
        return url.contains(urlComparar);
    }

    public static String toHoursString(Date d) {
        return d == null ? "" : HOUR_FORMAT.format(d);
    }

    public static int patio() {
        try {
            return Integer.parseInt(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.PATIO_DEFAULT));
        } catch (Exception e) {
            return Integer.parseInt(ResourceBundle.getBundle("bundle", localeCO()).getString("patio_id"));
        }
    }

    public static boolean fechaBetween(Date fecha, String ini, String fin) throws ParseException {
        Date min = converterToHour(fecha, ini);
        Date max = converterToHour(fecha, fin);
        Date fechaHoraActual = new Date();
        return fechaHoraActual.after(min) && fechaHoraActual.before(max);
    }

    public static boolean horaBetween(String hora, String desde, String hasta) {
        return (MovilidadUtil.toSecs(hora) >= MovilidadUtil.toSecs(desde)
                && MovilidadUtil.toSecs(hora) <= MovilidadUtil.toSecs(hasta));
    }

    public static boolean horaBetweenSinIgual(String hora, String desde, String hasta) {
        return (MovilidadUtil.toSecs(hora) > MovilidadUtil.toSecs(desde)
                && MovilidadUtil.toSecs(hora) < MovilidadUtil.toSecs(hasta));
    }

    public static boolean cumple(Date fechaN) {
        Calendar hoy = Calendar.getInstance(localeCO());
        int diaHoy = hoy.get(Calendar.DAY_OF_MONTH);
        int mesHoy = hoy.get(Calendar.MONTH) + 1;

        Calendar fechaNEmpl = Calendar.getInstance();
        fechaNEmpl.setTime(fechaN);

        int diaHoyEmpl = fechaNEmpl.get(Calendar.DAY_OF_MONTH);
        int mesHoyEmpl = fechaNEmpl.get(Calendar.MONTH) + 1;

        return diaHoyEmpl == diaHoy && mesHoyEmpl == mesHoy;
    }

    public static String listoParaCambio(Date fecha, String iniObjSelect, String iniObjEvaluar, String finObjEvaluar) throws ParseException {
        String result = "nook";

        Date minObjSelect = converterToHour(fecha, iniObjSelect);
        Date minObjEvaluar = converterToHour(fecha, finObjEvaluar);
        Date fechaHoraActual = new Date();
        if ((minObjEvaluar.after(minObjSelect) || minObjEvaluar.before(minObjSelect)) && minObjEvaluar.after(fechaHoraActual)) {
            result = "ok";
        }

        return result;
    }

    public static boolean itIsFree(Date fecha, String finServicioLibre, String iniObjtSelect) throws ParseException {
        Date horaFinServicioLibre = converterToHour(fecha, finServicioLibre);
        // Date horaInicioServicioSeleccionado = converterToHour(fecha, iniObjtSelect);
        Date horaActual = new Date();
        return horaFinServicioLibre.before(horaActual);
    }

    public static boolean itIsFreeII(Date fecha, String fin, String iniObjtSelect) throws ParseException {
        Date max = converterToHour(fecha, fin);
        Date iniObjetoSelected = converterToHour(fecha, iniObjtSelect);
        Date d = new Date();
        return max.after(d) && max.after(iniObjetoSelected);
    }

    public static BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret;
    }

    public static Date sumarDias(Date desde, int numDias) {
        Calendar c = Calendar.getInstance();
        c.setTime(desde);
        c.add(Calendar.DAY_OF_MONTH, numDias);
        return c.getTime();
    }

    public static Date sumarMinutos(Date desde, int numMinutos) {
        Calendar c = Calendar.getInstance();
        c.setTime(desde);
        c.add(Calendar.MINUTE, numMinutos);
        return c.getTime();
    }

    public static Date sumarMes(Date fecha, int numMeses) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        c.add(Calendar.MONTH, numMeses);
        return c.getTime();
    }

    public static boolean isSunday(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    public static List<String> ListaFechas(Date fecha) throws ParseException {
        Date fechanew = converterToHour(fecha, "00:00:00");
        List<String> listDate = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechanew); //tuFechaBase es un Date;
        while (converterToHour(fecha, "23:55:00").after(calendar.getTime())) {
            calendar.add(Calendar.MINUTE, 1); //minutosASumar es int.
            //lo que más quieras sumar
            listDate.add(HOUR_FORMAT.format(calendar.getTime()));
        }
        return listDate;
    }

    /**
     * Sumar minutos una hora
     *
     * @param hora En formato String (hh:mm:ss)
     * @param minutos Dato int
     * @return hora final en con formato (hh:mm:ss) en String
     * @throws ParseException
     */
    public static String sumarMinutosHora(String hora, int minutos) throws ParseException {
        Date fechanew = converterToHour(new Date(), hora);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechanew); //tuFechaBase es un Date;
        calendar.add(Calendar.MINUTE, minutos); //minutosASumar es int.
        return HOUR_FORMAT.format(calendar.getTime());
    }

    public static boolean alertServiciosSinOp(String horaIni) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        String horaIniActual = HOUR_FORMAT.format(calendar.getTime());

        String fin = MovilidadUtil.sumarMinutosHora(horaIniActual, 60);
        Date min = converterToHour(calendar.getTime(), horaIniActual);
        Date max = converterToHour(calendar.getTime(), fin);
        Date fechanew = converterToHour(calendar.getTime(), horaIni);
        return fechanew.after(min) && fechanew.before(max);
    }

    public static boolean alertServiciosSinOpVencidos(String horaIni) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        String horaIniActual = HOUR_FORMAT.format(calendar.getTime());

        Date min = converterToHour(calendar.getTime(), horaIniActual);
        Date fechanew = converterToHour(calendar.getTime(), horaIni);
        return fechanew.before(min);
    }

    public static String servbus(String tipo) {
        String result = tipo;
        for (int i = 0; i < 3; i++) {
            int el = (int) (Math.random() * 9);
            result = result + (char) numeros[el];
        }
        return result + "AD";
    }

    public static String Sercon() {
        String result = "R";
        for (int i = 0; i < 7; i++) {
            int el = (int) (Math.random() * 9);
            result = result + (char) numeros[el];
        }
        return result;
    }

    public static int tabla() {
        String result = "";
        for (int i = 0; i < 3; i++) {
            int el = (int) (Math.random() * 9);
            result = result + (char) numeros[el];
        }
        return Integer.parseInt(result);
    }

    public static String codigoAseo() {
        String result = "";
        for (int i = 0; i < 6; i++) {
            int el = (int) (Math.random() * 9);
            result = result + (char) numeros[el];
        }
        return result;
    }

    public static String horaActual() {
        String timeStamp = new SimpleDateFormat("HH:mm:ss")
                .format(fechaCO().getTime());
        return timeStamp;
    }

    public static Date fechaHoy() {
        Calendar calendar = fechaCO();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date fechaAnteriormeses(int var_meses) {
        Calendar calendar = fechaCO();
        calendar.add(Calendar.MONTH, -var_meses);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private static Calendar fechaCO() {
        return Calendar.getInstance(localeCO());
    }

    public static Locale localeCO() {
        return new Locale("es", "CO");
    }

    public static Date fechaCompletaHoy() {
        return fechaCO().getTime();
    }

    public static Date dateSinHora(Date fecha) {
        String date = Util.dateFormat(fecha);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(MovilidadUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Date converterToHour(Date fecha, String hora) throws ParseException {
        String date = Util.dateFormat(fecha);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(date + " " + hora);
    }

    public static String getProperty(String key) {
        try {
            return ResourceBundle.getBundle("bundle", localeCO()).getString(key);
        } catch (Exception e) {
            GFile.saveFileException(e);
            return "";
        }
    }

    public MovilidadUtil() {
    }

    public static String cortarPegarArchivo(String path, Date fecha) {
        String dst = "";
        File src = new File(path);
        dst = dirYYMMDD("OpKmChk", fecha, "ruta");
        boolean success = src.renameTo(new File(dst, src.getName()));
        if (success) {
            return dst + src.getName();
        }
        return null;

    }

    public static boolean validFecha(Date fechaOld, Date fechaNew) {
        Calendar cOld = toCalendar(fechaOld);
        Calendar cNew = toCalendar(fechaOld);
//        System.out.println("Before: " + cOld.before(cNew) + "---" + cOld + "---" + cNew);
//        System.out.println("After: " + cOld.after(cNew) + "---" + cOld + "---" + cNew);
        return cOld.before(cNew) || cOld.after(cNew);
    }

    public static boolean validarFecha(Date date) {
        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        return ((anioActual - toCalendar(date).get(Calendar.YEAR)) >= mayorEdad);
    }

    public static Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static String nameChange(String name) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        String formato = name.substring(name.lastIndexOf('.'), name.length());
        return df.format(new Date()) + formato;
    }

    public static String formato(String name) {
        String formato = name.substring(name.lastIndexOf('.'), name.length());
        return formato;
    }

    public static DefaultStreamedContent prepDownload(String path) throws Exception {
        if (path.isEmpty()) {
            path = getProperty("ayuda");
        }
        File filee = new File(path);
        InputStream input = new FileInputStream(filee);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        return new DefaultStreamedContent(input, externalContext.getMimeType(filee.getName()), filee.getName());
    }

    public static boolean eliminarFichero(String path) {
        File f = new File(path);
        return f.delete();
    }

    public static Collection<Field> getAllFields(Class<?> type) {
        TreeSet<Field> fields = new TreeSet<Field>(
                new Comparator<Field>() {
            @Override
            public int compare(Field o1, Field o2) {
                int res = o1.getName().compareTo(o2.getName());
                if (0 != res) {
                    return res;
                }
                res = o1.getDeclaringClass().getSimpleName().compareTo(o2.getDeclaringClass().getSimpleName());
                if (0 != res) {
                    return res;
                }
                res = o1.getDeclaringClass().getName().compareTo(o2.getDeclaringClass().getName());
                return res;
            }
        });
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

    public static void printAllFields(Object obj) {
        for (Field field : getAllFields(obj.getClass())) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
//            System.out.printf("%s %s.%s = %s;\n", value == null ? " " : "*", field.getDeclaringClass().getSimpleName(), name, value);
        }
    }

    public static String dirYYMMDD(String carpetaPadre, Date fecha, String key) {
        String dir = "";
        Calendar fechac = toCalendar(fecha);
        int año = fechac.get(Calendar.YEAR);
        int mes = fechac.get(Calendar.MONTH) + 1;
        int dia = fechac.get(Calendar.DAY_OF_MONTH);
        //Crear la DIR\CARPETAMODULO
        String path = getProperty(key) + "" + carpetaPadre;
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }
        //Crear la DIR\CARPETAMODULO\YYYY
        path = path + "/" + Integer.toString(año);
        f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }
        //Crear la DIR\CARPETAMODULO\YYYY\MM
        path = path + "/" + Integer.toString(mes);
        f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }
        //Crear la DIR\CARPETAMODULO\YYYY\MM\DD
        path = path + "/" + Integer.toString(dia);
        f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }
        return path;

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

    /**
     * Escribe archivo en el una ruta dinamica en el disco del servidor.
     *
     * @param key llave del archivo bundle properties.
     * @param file Archivo a cargar.
     * @param idCarperta Nombre de la carpeta donde se almacenara el archivo.
     * @param ruta carpeta padre donde se guardará el archivo.
     * @param nombreDocu nombre del archivo a cargar
     * @return retorna el path donde se almacenó el archivo.
     * @throws IOException
     */
    public static String subirFichero(String key, UploadedFile file, String idCarperta, String ruta, String nombreDocu) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String rutaId = "";
        String path2 = "";
        File f = new File(ruta);
        if (!f.exists()) {
            if (!ruta.equals("") && !(ruta.isEmpty())) {
                String pathNew = getProperty(key) + "" + ruta;
                File fNew = new File(pathNew);
                if (!fNew.exists()) {
                    fNew.mkdir();
                }
                rutaId = ruta + "/" + idCarperta;
            } else {
                rutaId = idCarperta;
            }
            String path = getProperty(key) + "" + rutaId;
            path2 = getProperty(key) + rutaId + "/" + nameChange(file.getFileName());
            f = new File(path);
            if (!f.exists()) {
                f.mkdir();
            }
        } else {
            String nombre = file.getFileName();
            String formato = nombre.substring(nombre.lastIndexOf('.'), nombre.length());
            path2 = ruta + "/" + nombreDocu + formato;
        }
        try {
            inputStream = file.getInputstream(); //leemos el fichero local
            // write the inputStream to a FileOutputStream
            outputStream = new FileOutputStream(new File(path2));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            return path2;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public static String subirFotoEmpleado(String key, UploadedFile file, String cedula) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String rutaId = "";
        String path2 = "";
        String pathNew = getProperty(key) + "empleado";
        File fNew = new File(pathNew);
        if (!fNew.exists()) {
            fNew.mkdir();
        }
        path2 = pathNew + "/" + cedula + "" + formato(file.getFileName());
        File f = new File(pathNew);
        if (!f.exists()) {
            f.mkdir();
        }
        try {
            inputStream = file.getInputstream(); //leemos el fichero local
            // write the inputStream to a FileOutputStream
            outputStream = new FileOutputStream(new File(path2));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            return path2;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public static String toTimeSec(int t) {
        return fill((int) ((t / 60.0 / 60)), "0", 2) + ":" + fill((t / 60) % 60, "0", 2) + ":" + fill((t % 60), "0", 2);
    }

    public int toMins(String t) {
        int timeInMins = Integer.parseInt(t.split(":")[0]) * 60 + Integer.parseInt(t.split(":")[1]);
        return timeInMins;
    }

    public static int toSecs(String t) {
        try {
            String[] li = t.split(":");
            int timeInS = Integer.parseInt(li[0]) * 60 * 60 + Integer.parseInt(li[1]) * 60;
            if (li.length > 2) {
                timeInS += Integer.parseInt(li[2]);
            }
            return timeInS;
        } catch (Exception e) {
            return 0;
        }
    }

    public static String fill(int a, String f, int t) {
        return String.format("%02d", a);
    }

    public static String diferenciaHoras(String horaIni, String horaFin) {

        int ini = toSecs(horaIni);
        int fin = toSecs(horaFin);
        int diferencia = fin - ini;

        return toTimeSec(diferencia);
    }

    public static int diferencia(String horaIni, String horaFin) {

        int ini = toSecs(horaIni);
        int fin = toSecs(horaFin);
        int diferencia = fin - ini;

        return diferencia;
    }

    public static String sumarHoraSrting(String horaIni, String horaFin) {
        int ini = toSecs(horaIni);
        int fin = toSecs(horaFin);
        int diferencia = fin + ini;

        return toTimeSec(diferencia);
    }

    //opcion es identificar de que etapa del accidente pertenece el archivo
    public static String cargarArchivosAccidentalidad(UploadedFile file, int id_accidente, String opcion, int id_opcion, String nameAux) {
        FileOutputStream fileo = null;
        try {
            String pathRaiz = getProperty("accidente.dir");
            System.out.println("patRaiz:" + pathRaiz);
            if (pathRaiz.equals("") | pathRaiz.isEmpty()) {
                return "";
            }
            File f = new File(pathRaiz);
            if (!f.exists()) {
                f.mkdir();
            }
            String pathIdAccidente = pathRaiz + String.valueOf(id_accidente) + "/";
            File fi = new File(pathIdAccidente);
            if (!fi.exists()) {
                fi.mkdir();
            }
            String path = pathIdAccidente + opcion + "/";
            File fil = new File(path);
            if (!fil.exists()) {
                fil.mkdir();
            }
            String path1 = path + String.valueOf(id_opcion) + "/";
            File fil2 = new File(path1);
            if (!fil2.exists()) {
                fil2.mkdir();
            }
            String nombre = file.getFileName();
            String formato = nombre.substring(nombre.lastIndexOf('.'), nombre.length());
            String name = nameAux + formato;
            fileo = new FileOutputStream(new File(path1 + name));
            fileo.write(file.getContents());
            return path1 + name;
        } catch (IOException e) {
            MovilidadUtil.addErrorMessage("Error al cargar el archivo");
            e.printStackTrace();
            return "";
        }

    }

    public static String guardarFoto(byte[] imageBytes, int id_accidente, String opcion, int id_opcion) {
        try {
            FileOutputStream os = null;
            String ruta = getProperty("accidente.dir");
            File f = new File(ruta);
            if (!f.exists()) {
                f.mkdir();
            }
            String pathIdAccidente = ruta + String.valueOf(id_accidente) + "/";
            File fi = new File(pathIdAccidente);
            if (!fi.exists()) {
                fi.mkdir();
            }
            String path = pathIdAccidente + opcion + "/";
            File fil = new File(path);
            if (!fil.exists()) {
                fil.mkdir();
            }
            String path1 = path + String.valueOf(id_opcion) + "/";
            File fil2 = new File(path1);
            if (!fil2.exists()) {
                fil2.mkdir();
            }
            System.out.println("ruta: " + ruta);
            os = new FileOutputStream(new File(path1 + "croquis.jpeg"));
            os.write(imageBytes);
            os.close();
            return path1 + "croquis.jpeg";
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            return null;
        }
    }

    public static int getDiferenciaHora(Date desde, Date hasta) {

        long startTime = desde.getTime();
        long endTime = hasta.getTime();
        long horaDesde = (long) Math.floor(startTime / (1000 * 60 * 60));
        long horaHasta = (long) Math.floor(endTime / (1000 * 60 * 60));
        long horas = horaHasta - horaDesde;

        return (int) horas;
//        int diferencia;
//        Calendar a = Calendar.getInstance();
//        a.setTime(desde);
//        Calendar b = Calendar.getInstance();
//        b.setTime(hasta);
//        diferencia = b.get(Calendar.DAY_OF_MONTH) - a.get(Calendar.DAY_OF_MONTH);
//        return Math.abs(diferencia) + 1;
    }

    public static int getDiferenciaDia(Date desde, Date hasta) {

        long startTime = desde.getTime();
        long endTime = hasta.getTime();
        long diasDesde = (long) Math.floor(startTime / (1000 * 60 * 60 * 24)); //  que no afecten cambios de hora 
        long diasHasta = (long) Math.floor(endTime / (1000 * 60 * 60 * 24)); // convertimos a dias, para que no afecten cambios de hora
        long dias = diasHasta - diasDesde;

        return (int) dias;
//        int diferencia;
//        Calendar a = Calendar.getInstance();
//        a.setTime(desde);
//        Calendar b = Calendar.getInstance();
//        b.setTime(hasta);
//        diferencia = b.get(Calendar.DAY_OF_MONTH) - a.get(Calendar.DAY_OF_MONTH);
//        return Math.abs(diferencia) + 1;
    }

    public static String saveFotoAfterCapture(byte[] datos) {
        // obtenemos los datos de la foto como array de bytes

        final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
                .getContext();
        // le asignamos el nombre que sea a la imagen (en este caso siempre el mismo)
        String foto = "foto.png";
        // ruta destino de la imagen /resources/foto.png
        System.out.println(servletContext.getRealPath(""));
        final String fileFoto = servletContext.getRealPath("") + File.separator + "resources" + File.separator + foto;
        System.out.println("fileFoto---- " + fileFoto);
        FileImageOutputStream outputStream = null;
        try {
            outputStream = new FileImageOutputStream(new File(fileFoto));
            // guardamos la imagen
            outputStream.write(datos, 0, datos.length);
        } catch (IOException e) {
            return "";
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
            }
        }
        return fileFoto;
    }

    public static String leerQr(String fileFoto) {
        try {
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                    new BufferedImageLuminanceSource(
                            ImageIO.read(new FileInputStream(fileFoto)))));
            Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap);
            return qrCodeResult.getText();
        } catch (IOException | NotFoundException ex) {
            return "";
        }
    }

    public static String getWithoutSpaces(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.replaceAll("\\s", "");
    }

    /**
     * Valida si una hora es formato P.M (en base a una fecha)
     *
     * @param fecha
     * @return
     */
    public static boolean validarHoraPm(Date fecha) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);

        int hora = c.get(Calendar.HOUR_OF_DAY);

        return hora > 11;

    }

    public static Integer obtenerEdadByFecha(Date d) {
        Calendar fecNac = Calendar.getInstance();
        fecNac.setTime(d);
        Calendar ahora = Calendar.getInstance();
        long edadEnDias = (ahora.getTimeInMillis() - fecNac.getTimeInMillis());
        return Double.valueOf(edadEnDias / 365.25d).intValue();
    }

    /**
     * Obtiene y devuelve lista de nomobres de fotos almacenadas
     *
     * @param idCarpeta
     * @param llaveBundle
     * @param path
     * @return
     * @throws IOException
     */
    public static List<String> getListasFotos(int idCarpeta, String llaveBundle, String path) throws IOException {
        List<String> listFotos = new ArrayList<>();
        List<String> lstNombresImg;
        lstNombresImg = Util.getFileList(idCarpeta, llaveBundle);
        for (String f : lstNombresImg) {
            f = path + f;
            listFotos.add(f);
        }
        return listFotos;
    }

    /**
     * Convierte un valor string recibido por parametro a int.
     *
     * @param valor
     * @return
     */
    public static int convertStringToInt(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean stringIsEmpty(String valor) {
        if (valor != null && !valor.equals("")) {
            return getWithoutSpaces(valor).isEmpty();
        } else {
            return true;
        }
    }

    /**
     * Método que se encarga de obtener los correos de las listas de
     * distribución por unidad funcional
     *
     * @param detalles lista de detalles
     * @param idGopUnidadFuncional id unidad funcional de la novedad a guardar
     * @return correos de las listas de distribución por unidad funcional
     */
    public static String obtenerCorreosByUf(List<NotificacionProcesoDet> detalles, int idGopUnidadFuncional) {

        if (detalles == null || detalles.isEmpty()) {
            return null;
        }

        String correos = "";

        for (NotificacionProcesoDet detalle : detalles) {
            if (detalle.getIdGopUnidadFuncional().getIdGopUnidadFuncional().equals(idGopUnidadFuncional)
                    && detalle.getEstadoReg() == 0) {
                correos = detalle.getEmails();
            }
        }

//        Predicate<NotificacionProcesoDet> esIgualUf = x -> x.getIdGopUnidadFuncional().getIdGopUnidadFuncional().equals(idGopUnidadFuncional);
//        Predicate<NotificacionProcesoDet> noEstaEliminado = y -> y.getEstadoReg() == 0;
//
//        String correos = detalles.stream()
//                .filter(esIgualUf.and(noEstaEliminado))
//                .map(y -> {
//                    return y.getEmails();
//                })
//                .collect(Collectors.joining(","));
        return correos;
    }

    public static boolean isDateBetween0100And0130(Date d) {
        int toSecs0100 = toSecs("01:00:00");
        int toSecs0130 = toSecs("01:30:00");
        int toSecs = toSecs(Util.dateToTimeHHMMSS(d));
        if (toSecs0100 < toSecs && toSecs < toSecs0130) {
            return true;
        }
        return false;
    }

    /**
     * devuelve la fecha del dia especifico pasado por parametro
     *
     * @param fecha
     * @param dia |Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY,
     * Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY,Calendar.SUNDAY
     * @return Tipo de dato Date
     */
    public static Date getDiaSemana(Date fecha, int dia) {
        Locale l = new Locale("es", "CO");
        Calendar fechaDia = Calendar.getInstance(l);

        fechaDia.setTime(fecha);
        fechaDia.set(Calendar.DAY_OF_WEEK, dia);

        return fechaDia.getTime();
    }

    public static boolean fechaDiferente(Date fecha, Date param) {
        return !com.aja.jornada.util.Util.dateFormat(fecha).equals(com.aja.jornada.util.Util.dateFormat(param));
    }

    /**
     * Método que permite imprimir una fecha en un formato especifico.
     * Actualmente se valida que el objeto @date no sea null y que @format 
     * no sea vacío.
     * Se ve necesario integrar a futuro un método que evalúe los formatos 
     * permitidos.
     * @param date de tipo Date contiene la fecha a formatear
     * @param format de tipo String especifica el formato en que se desea imprimir @date
     * @return la fecha en el formato especificado
     *         cadena vacia en cualquier otro caso
     */
    public static String formatDate(Date date, String format) {
        if (date != null && !format.isEmpty()) {
            // Crear un formateador con el patrón deseado
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            // Formatear la fecha y devolver como cadena
            return formatter.format(date);
        }
        return "";
    }
    
}
