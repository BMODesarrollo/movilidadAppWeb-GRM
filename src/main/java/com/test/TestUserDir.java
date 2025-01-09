package com.test;

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
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Carlos Ballestas
 */
public class TestUserDir {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        try {
            String master = System.getProperty("user.dir") + "/src/main/resources/reportes/Compromiso.jrxml";
            Map parametros = new HashMap();

            System.out.println("Master:" + master);

            parametros.put("nomOperador", "Carlos Ballestas");
            parametros.put("codOperador", "70525");
            parametros.put("causa", "Prueba de causa");
            parametros.put("dia", "27");
            parametros.put("mes", "01");
            parametros.put("anio", "2020");
            parametros.put("hora", "14:58");
            parametros.put("lugar", "Patio Sur");
            parametros.put("diaFirma", "27");
            parametros.put("mesFirma", "01");
            parametros.put("anioFirma", "2020");

            JasperReport jasperReport = JasperCompileManager.compileReport(master);
            JRDataSource jRDataSource = new JREmptyDataSource();

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, jRDataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "/home/soluciones/Escritorio/INFORMES BMO/ACCIDENTALIDAD/Acta_Compromiso_" + parametros.get("codOperador") + ".pdf");
            JasperViewer jviewer = new JasperViewer(jasperPrint, false);
            jviewer.setTitle("Ventas Diarias");
            jviewer.setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SQLException(ex);
        }
    }

}
