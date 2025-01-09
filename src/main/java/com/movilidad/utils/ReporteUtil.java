package com.movilidad.utils;

import com.movilidad.model.Empleado;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author Carlos Ballestas
 */
public class ReporteUtil {

    private static final String MASTER_BMO = Util.getProperty("seguridadVial_BMO.dir");

    public static String datosInformeCompromiso(Empleado empleado, Map<String, String> params) throws SQLException {
        try {
            String master = MASTER_BMO + "Compromiso.jrxml";
            Map parametros = new HashMap();

            parametros.put("nomOperador", empleado.getNombres().concat(" ").concat(empleado.getApellidos()));
            parametros.put("codOperador", String.valueOf(empleado.getCodigoTm()));
            parametros.put("causa", params.get("causa"));
            parametros.put("dia", params.get("dia"));
            parametros.put("mes", params.get("mes"));
            parametros.put("anio", params.get("anio"));
            parametros.put("hora", params.get("hora"));
            parametros.put("lugar", params.get("lugar"));
////            parametros.put("diaFirma", params.get("dia_firma"));
////            parametros.put("mesFirma", params.get("mes_firma"));
////            parametros.put("anioFirma", params.get("anio_firma"));
            JasperReport jasperReport = JasperCompileManager.compileReport(master);
            JRDataSource jRDataSource = new JREmptyDataSource();

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, jRDataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, params.get("path") + "Acta_Compromiso.pdf");

            return params.get("path") + "Acta_Compromiso.pdf";

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return "";
        }
    }

    public static String datosPoder(Map<String, String> params) throws SQLException {
        try {
            String master = MASTER_BMO + "Poder.jrxml";
            Map parametros = new HashMap();

            parametros.put("path", params.get("path"));
            parametros.put("nomOperador", params.get("nomOperador"));
            parametros.put("nomAbogado", params.get("nomAbogado"));
            parametros.put("cedulaLugar", params.get("cedulaLugar"));
            parametros.put("tpLugar", params.get("tpLugar"));
            parametros.put("placa", params.get("placa"));
            parametros.put("ccAbogado", params.get("ccAbogado"));
            parametros.put("tpAbogado", params.get("tpAbogado"));
            parametros.put("membrete", params.get("membrete"));
            parametros.put("membreteMayus", params.get("membreteMayus"));

            JasperReport jasperReport = JasperCompileManager.compileReport(master);
            JRDataSource jRDataSource = new JREmptyDataSource();

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, jRDataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, params.get("path") + "Poder.pdf");

            return params.get("path") + "Poder.pdf";

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return "";
        }
    }
}
