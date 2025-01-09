/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.Element;
import com.movilidad.model.AccChoqueInforme;
import com.movilidad.model.AccInformeMaster;
import com.movilidad.model.AccInformeMasterAgentes;
import com.movilidad.model.AccInformeMasterAlbum;
import com.movilidad.model.AccInformeMasterApoyo;
import com.movilidad.model.AccInformeMasterBomberos;
import com.movilidad.model.AccInformeMasterInspectores;
import com.movilidad.model.AccInformeMasterMedicos;
import com.movilidad.model.AccInformeMasterRecomotos;
import com.movilidad.model.AccInformeMasterTestigo;
import com.movilidad.model.AccInformeMasterVehCond;
import com.movilidad.model.AccInformeMasterVic;
import com.movilidad.model.AccInformeOpe;
import com.movilidad.model.AccInformeOpeCausalidad;
import com.movilidad.model.AccInformeTestigo;
import com.movilidad.model.AccInformeVehCond;
import com.movilidad.model.AccInformeVic;
import com.movilidad.model.AccObjetoFijoInforme;
import com.movilidad.model.AccVisualInforme;
import com.movilidad.model.AccidenteLugarDemar;
import static com.movilidad.utils.MovilidadUtil.getProperty;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author HP
 */
public class DocumentUtil {

    public DocumentUtil() {
    }

    public static void generarInformeOperaodor(AccInformeOpe a,
            List<AccidenteLugarDemar> listAccidenteLugarDemar,
            List<AccObjetoFijoInforme> listAccObjetoFijoInforme,
            List<AccChoqueInforme> listAccChoqueInforme,
            List<AccVisualInforme> listAccVisualInforme) {

        String ruta = "";
        Document doc = new Document(PageSize.A4, 30, 30, 30, 30);
        try {
            //Ruta Documento
            String rutaRaiz = getProperty("accidente.dir");
            if (rutaRaiz.equals("") | rutaRaiz.isEmpty()) {
                return;
            }
            File f = new File(rutaRaiz);
            if (!f.exists()) {
                f.mkdir();
            }
            String rutaIdAccidente = rutaRaiz + String.valueOf(a.getIdAccidente().getIdAccidente()) + "/";
            f = new File(rutaIdAccidente);
            if (!f.exists()) {
                f.mkdir();
            }
            String rutaInforme = rutaIdAccidente + "InformeOperador/";
            f = new File(rutaInforme);
            if (!f.exists()) {
                f.mkdir();
            }
            ruta = rutaInforme + "CopiaRigel.pdf";
            f = new File(rutaInforme, "CopiaRigel.pdf");
            // Documento
            FileOutputStream filePDF = new FileOutputStream(f);
            PdfWriter.getInstance(doc, filePDF);
            doc.open();
            Paragraph titulo = new Paragraph("INFORME OPERADOR",
                    FontFactory.getFont("arial",
                            14,
                            Font.BOLD,
                            BaseColor.BLACK)
            );
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(Chunk.NEWLINE);
            PdfPTable tablaEvento = new PdfPTable(2);
            tablaEvento.getDefaultCell().setBorder(0);
            tablaEvento.addCell("EVENTO");
            tablaEvento.addCell(a.getIdAccidente().getIdNovedadTipoDetalle().getTituloTipoNovedad().toUpperCase());
            tablaEvento.addCell("FECHA DEL EVENTO");
            tablaEvento.addCell(Util.dateTimeFormat(a.getIdAccidente().getFechaAcc()));
            if (a.getIdAccidente().getIdVehiculo() != null) {
                tablaEvento.addCell("VEHÍCULO");
                tablaEvento.addCell(a.getIdAccidente().getIdVehiculo().getCodigo().toUpperCase());
            }
            if (a.getIdAccidente().getIdEmpleado() != null) {
                tablaEvento.addCell("OPERADOR");
                tablaEvento.addCell(a.getIdAccidente().getIdEmpleado().getCodigoTm() + " - "
                        + a.getIdAccidente().getIdEmpleado().getApellidos().toUpperCase() + " "
                        + a.getIdAccidente().getIdEmpleado().getNombres().toUpperCase());
            }
            tablaEvento.addCell("GRAVEDAD");
            tablaEvento.addCell(tipoGravedad(a.getCondicion()));
            tablaEvento.addCell("CLASE DE ACCIDENTE");
            tablaEvento.addCell(a.getIdAccClase().getClase().toUpperCase());
            doc.add(tablaEvento);
            doc.add(Chunk.NEWLINE);
            if (listAccChoqueInforme != null) {
                titulo = new Paragraph("CHOQUE CON",
                        FontFactory.getFont("arial",
                                14,
                                Font.BOLD,
                                BaseColor.BLACK)
                );
                titulo.setAlignment(Element.ALIGN_CENTER);
                doc.add(titulo);
                doc.add(Chunk.NEWLINE);
                PdfPTable tabla = new PdfPTable(1);
                tabla.getDefaultCell().setBorder(0);
                for (AccChoqueInforme aci : listAccChoqueInforme) {
                    tabla.addCell("* " + aci.getIdAccTipoVehiculo().getTipoVehiculo().toUpperCase());
                }
                doc.add(tabla);
            }
            doc.add(Chunk.NEWLINE);
            if (listAccObjetoFijoInforme != null) {
                titulo = new Paragraph("CHOQUE CON OBJETO FIJO",
                        FontFactory.getFont("arial",
                                14,
                                Font.BOLD,
                                BaseColor.BLACK)
                );
                titulo.setAlignment(Element.ALIGN_CENTER);
                doc.add(titulo);
                doc.add(Chunk.NEWLINE);
                PdfPTable tabla = new PdfPTable(1);
                tabla.getDefaultCell().setBorder(0);
                for (AccObjetoFijoInforme aofi : listAccObjetoFijoInforme) {
                    tabla.addCell("* " + aofi.getIdAccObjetoFijo().getObjetoFijo().toUpperCase());
                }
                doc.add(tabla);
            }
            doc.add(Chunk.NEWLINE);
            titulo = new Paragraph("LUGAR",
                    FontFactory.getFont("arial",
                            14,
                            Font.BOLD,
                            BaseColor.BLACK));
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(Chunk.NEWLINE);
            PdfPTable tabla = new PdfPTable(2);
            tabla.getDefaultCell().setBorder(0);
            tabla.addCell("LATITUD");
            tabla.addCell(String.valueOf(a.getLatitude()));
            tabla.addCell("LONGITUD");
            tabla.addCell(String.valueOf(a.getLongitude()));
            tabla.addCell("DIRECCIÓN");
            tabla.addCell(a.getDireccion().toUpperCase());
            tabla.addCell("TRONCAL");
            tabla.addCell(a.getIdAccViaTroncal().getViaTroncal().toUpperCase());
            doc.add(tabla);
            doc.add(Chunk.NEWLINE);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo = new Paragraph("FECHA Y HORA",
                    FontFactory.getFont("arial",
                            14,
                            Font.BOLD,
                            BaseColor.BLACK));
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(Chunk.NEWLINE);
            tabla = new PdfPTable(2);
            tabla.getDefaultCell().setBorder(0);
            tabla.addCell("FECHA");
            tabla.addCell(a.getFecha() != null ? Util.dateFormat(a.getFecha()) : "NA");
            tabla.addCell("HORA DE OCURRENCIA");
            tabla.addCell(a.getHoraOcurrencia());
            tabla.addCell("HORA DE LEVANTAMIENTO");
            tabla.addCell(a.getHoraLevantamiento());
            doc.add(tabla);
            doc.add(Chunk.NEWLINE);
            doc.newPage();
            titulo = new Paragraph("CARACTERISTICAS DEL LUGAR",
                    FontFactory.getFont("arial",
                            14,
                            Font.BOLD,
                            BaseColor.BLACK));
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(Chunk.NEWLINE);

            tabla = new PdfPTable(2);
            tabla.getDefaultCell().setBorder(0);
            tabla.addCell("ÁREA");
            tabla.addCell(a.getArea().equals(0) ? "URBANA" : "RURAL");
            tabla.addCell("SECTOR");
            tabla.addCell(a.getIdAccViaSector() != null ? a.getIdAccViaSector().getViaSector().toUpperCase() : "NA");
            tabla.addCell("ZONA");
            tabla.addCell(a.getIdAccViaZona() != null ? a.getIdAccViaZona().getViaZona().toUpperCase() : "NA");
            tabla.addCell("DESEÑO");
            tabla.addCell(a.getIdAccViaDiseno() != null ? a.getIdAccViaDiseno().getViaDiseno().toUpperCase() : "NA");
            tabla.addCell("TIEMPO");
            tabla.addCell(a.getIdAccViaClima() != null ? a.getIdAccViaClima().getViaClima().toUpperCase() : "NA");
            doc.add(tabla);
            doc.add(Chunk.NEWLINE);
            titulo = new Paragraph("CARACTERISTICAS DE LA VÍA",
                    FontFactory.getFont("arial",
                            14,
                            Font.BOLD,
                            BaseColor.BLACK));
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(Chunk.NEWLINE);

            tabla = new PdfPTable(2);
            tabla.getDefaultCell().setBorder(0);
            tabla.addCell("GEOMÉTRICA");
            tabla.addCell(a.getIdAccViaGeometrica() != null ? a.getIdAccViaGeometrica().getViaGeometrica().toUpperCase() : "NA");
            tabla.addCell("UTILIZACIÓN");
            tabla.addCell(a.getIdAccViaUtilizacion() != null ? a.getIdAccViaUtilizacion().getViaUtilizacion().toUpperCase() : "NA");
            tabla.addCell("CALZADAS");
            tabla.addCell(a.getIdAccViaCalzada() != null ? a.getIdAccViaCalzada().getViaCalzadas().toUpperCase() : "NA");
            tabla.addCell("CARRILES");
            tabla.addCell(a.getIdAccViaCarriles() != null ? a.getIdAccViaCarriles().getViaCarriles().toUpperCase() : "NA");
            tabla.addCell("MATERIAL");
            tabla.addCell(a.getIdAccViaMaterial() != null ? a.getIdAccViaMaterial().getViaMaterial().toUpperCase() : "NA");
            tabla.addCell("ESTADO");
            tabla.addCell(a.getIdAccViaEstado() != null ? a.getIdAccViaEstado().getViaEstado().toUpperCase() : "NA");
            tabla.addCell("CONDICIÓN");
            tabla.addCell(a.getIdAccViaCondicion() != null ? a.getIdAccViaCondicion().getViaCondicion().toUpperCase() : "NA");
            tabla.addCell("ILUMINACIÓN ARTIFICIAL");
            tabla.addCell(a.getIdAccViaIluminacion() != null ? a.getIdAccViaIluminacion().getViaIluminacion().toUpperCase() : "NA");
            doc.add(tabla);
            doc.add(Chunk.NEWLINE);
            titulo = new Paragraph("CONTROLES",
                    FontFactory.getFont("arial",
                            14,
                            Font.BOLD,
                            BaseColor.BLACK));
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(Chunk.NEWLINE);

            tabla = new PdfPTable(2);
            tabla.getDefaultCell().setBorder(0);
            tabla.addCell("AGENTE DE TRANSITO");
            tabla.addCell(a.getAgente().equals(0) ? "PRESENTE" : "NO PRESENTE");
            tabla.addCell("SEMAFORO");
            tabla.addCell(a.getIdAccViaSemaforo() != null ? a.getIdAccViaSemaforo().getViaSemaforo().toUpperCase() : "NA");
            doc.add(tabla);
            doc.add(Chunk.NEWLINE);
            if (listAccidenteLugarDemar != null) {
                titulo = new Paragraph("DEMARCACIÓN Y SEÑALES",
                        FontFactory.getFont("arial",
                                14,
                                Font.BOLD,
                                BaseColor.BLACK));
                titulo.setAlignment(Element.ALIGN_CENTER);
                doc.add(titulo);
                doc.add(Chunk.NEWLINE);

                tabla = new PdfPTable(1);
                tabla.getDefaultCell().setBorder(0);
                for (AccidenteLugarDemar ald : listAccidenteLugarDemar) {
                    tabla.addCell("* " + ald.getIdAccViaDemarcacion().getViaDemarcacion().toUpperCase());
                }
                doc.add(tabla);
                doc.add(Chunk.NEWLINE);
            }
            if (listAccVisualInforme != null) {
                titulo = new Paragraph("VISUAL DISMINUIDA",
                        FontFactory.getFont("arial",
                                14,
                                Font.BOLD,
                                BaseColor.BLACK));
                titulo.setAlignment(Element.ALIGN_CENTER);
                doc.add(titulo);
                doc.add(Chunk.NEWLINE);

                tabla = new PdfPTable(1);
                tabla.getDefaultCell().setBorder(0);
                for (AccVisualInforme avi : listAccVisualInforme) {
                    tabla.addCell("* " + avi.getIdAccViaVisual().getViaVisual().toUpperCase());
                }
                doc.add(tabla);
                doc.add(Chunk.NEWLINE);
            }
            doc.newPage();
            if (a.getAccInformeVehCondList() != null) {
                titulo = new Paragraph("CONDUCTORES, VEHÍCULOS Y PROPIETARIOS",
                        FontFactory.getFont("arial",
                                14,
                                Font.BOLD,
                                BaseColor.BLACK));
                titulo.setAlignment(Element.ALIGN_CENTER);
                doc.add(titulo);
                doc.add(Chunk.NEWLINE);
                for (AccInformeVehCond aivc : a.getAccInformeVehCondList()) {
                    titulo = new Paragraph("CONDUCTOR",
                            FontFactory.getFont("arial",
                                    14,
                                    Font.BOLD,
                                    BaseColor.BLACK));
                    titulo.setAlignment(Element.ALIGN_CENTER);
                    doc.add(titulo);
                    doc.add(Chunk.NEWLINE);
                    tabla = new PdfPTable(2);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("NOMBRES");
                    tabla.addCell(aivc.getNombres() != null ? aivc.getNombres().toUpperCase() : "NA");
                    tabla.addCell(aivc.getIdTipoIdentificacion() != null ? aivc.getIdTipoIdentificacion().getNombre().toUpperCase() : "NA");
                    tabla.addCell(aivc.getNroDocumento() != null ? aivc.getNroDocumento().toUpperCase() : "NA");
                    tabla.addCell("FECHA DE NACIMIENTO");
                    tabla.addCell(aivc.getFechaNac() != null ? Util.dateFormat(aivc.getFechaNac()) : "NA");
                    tabla.addCell("SEXO");
                    tabla.addCell(aivc.getSexo() != null ? aivc.getSexo().toString().toUpperCase() : "NA");
                    tabla.addCell("DIRECCIÓN");
                    tabla.addCell(aivc.getDireccion() != null ? aivc.getDireccion().toUpperCase() : "NA");
                    tabla.addCell("CIUDAD");
                    tabla.addCell(aivc.getCiudad() != null ? aivc.getCiudad().toUpperCase() : "NA");
                    tabla.addCell("TELÉFONOS");
                    tabla.addCell(aivc.getTelefono() != null ? aivc.getTelefono().toUpperCase() : "NA");
                    tabla.addCell("CONDICIÓN");
                    tabla.addCell(aivc.getCondicion() != null ? (aivc.getCondicion().equals(0) ? "MUERTO" : "HERIDO") : "NA");
                    tabla.addCell("PORTA CINTURON DE SEGURIDAD");
                    tabla.addCell(aivc.getCinturon() != null ? (aivc.getCinturon().equals(0) ? "NO" : "SÍ") : "NA");
                    tabla.addCell("PORTA CASCO");
                    tabla.addCell(aivc.getCasco() != null ? (aivc.getCasco().equals(0) ? "NO" : "SÍ") : "NA");
                    tabla.addCell("PORTA LICENCIA");
                    tabla.addCell(aivc.getLicencia() != null ? (aivc.getLicencia().equals(0) ? "NO" : "SÍ") : "NA");
                    if (aivc.getLicencia() != null) {
                        if (aivc.getLicencia().equals(1)) {
                            tabla.addCell("NÚMERO DE LICENCIA");
                            tabla.addCell(aivc.getNroLicencia() != null ? aivc.getNroLicencia().toUpperCase() : "NA");
                            tabla.addCell("CATEGORÍA");
                            tabla.addCell(aivc.getCategoria() != null ? aivc.getCategoria().toUpperCase() : "NA");
                            tabla.addCell("RESTRICCIÓN");
                            tabla.addCell(aivc.getRestriccion() != null ? aivc.getRestriccion().toUpperCase() : "NA");
                            tabla.addCell("FECHA DE EXPEDICIÓN");
                            tabla.addCell(aivc.getFechaExp() != null ? Util.dateFormat(aivc.getFechaExp()) : "NA");
                            tabla.addCell("FECHA DE VENCIMIENTO");
                            tabla.addCell(aivc.getFechaVencimiento() != null ? Util.dateFormat(aivc.getFechaVencimiento()) : "NA");
                        }
                    }
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                    titulo = new Paragraph("SE LLEVÓ A EXAMEN DE",
                            FontFactory.getFont("arial",
                                    14,
                                    Font.BOLD,
                                    BaseColor.BLACK));
                    titulo.setAlignment(Element.ALIGN_CENTER);
                    doc.add(titulo);
                    doc.add(Chunk.NEWLINE);
                    tabla = new PdfPTable(2);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("CENTRO DE ATENCIÓN");
                    tabla.addCell(aivc.getSitioAtencion() != null ? aivc.getSitioAtencion().toUpperCase() : "NA");
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                    tabla = new PdfPTable(4);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("EMBRIAGUEZ");
                    tabla.addCell(aivc.getPruebaEmbriaguez() != null ? (aivc.getPruebaEmbriaguez().equals(1) ? "POSITIVO" : (aivc.getPruebaEmbriaguez().equals(0) ? "NEGATIVO" : "NO SE REALIZÓ")) : "NA");
                    tabla.addCell("RESULTADO");
                    tabla.addCell(aivc.getResultEmbriaguez() != null ? aivc.getResultEmbriaguez().toUpperCase() : "NA");
                    tabla.addCell("DROGA");
                    tabla.addCell(aivc.getPruebaDroga() != null ? (aivc.getPruebaDroga().equals(1) ? "POSITIVO" : (aivc.getPruebaDroga().equals(0) ? "NEGATIVO" : "NO SE REALIZÓ")) : "NA");
                    tabla.addCell("RESULTADO");
                    tabla.addCell(aivc.getResulDroga() != null ? aivc.getResulDroga().toUpperCase() : "NA");
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                    titulo = new Paragraph("VEHÍCULO",
                            FontFactory.getFont("arial",
                                    14,
                                    Font.BOLD,
                                    BaseColor.BLACK));
                    titulo.setAlignment(Element.ALIGN_CENTER);
                    doc.add(titulo);
                    doc.add(Chunk.NEWLINE);
                    tabla = new PdfPTable(4);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("PLACA");
                    tabla.addCell(aivc.getPlaca() != null ? aivc.getPlaca().toUpperCase() : "NA");
                    tabla.addCell("MARCA");
                    tabla.addCell(aivc.getMarca() != null ? aivc.getMarca().toUpperCase() : "NA");
                    tabla.addCell("LINEA");
                    tabla.addCell(aivc.getLinea() != null ? aivc.getLinea().toUpperCase() : "NA");
                    tabla.addCell("MODELO");
                    tabla.addCell(aivc.getModelo() != null ? aivc.getModelo().toUpperCase() : "NA");
                    tabla.addCell("CARGA EN TONELADAS");
                    tabla.addCell(aivc.getCargaTonelada() != null ? aivc.getCargaTonelada().toUpperCase() : "NA");
                    tabla.addCell("COLOR");
                    tabla.addCell(aivc.getColor() != null ? aivc.getColor().toUpperCase() : "NA");
                    doc.add(tabla);
                    tabla = new PdfPTable(2);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("EMPRESA OPERADORA");
                    tabla.addCell(aivc.getIdEmpresaOperadora() != null ? aivc.getIdEmpresaOperadora().getEmpresaOperadora().toUpperCase() : "NA");
                    tabla.addCell("INMOVILIZADO EN");
                    tabla.addCell(aivc.getInmovilizado() != null ? aivc.getInmovilizado().toUpperCase() : "NA");
                    tabla.addCell("A DISPOSICIÓN DE");
                    tabla.addCell(aivc.getDisposicion() != null ? aivc.getDisposicion().toUpperCase() : "NA");
                    tabla.addCell("SEGURO OBLIGATORIO");
                    tabla.addCell(aivc.getSeguroObligatorio() != null ? (aivc.getSeguroObligatorio().equals(1) ? "SÍ" : "NO") : "NA");
                    if (aivc.getSeguroObligatorio() != null) {
                        if (aivc.getSeguroObligatorio().equals(1)) {
                            tabla.addCell("NÚMERO DE POLIZA");
                            tabla.addCell(aivc.getNroPoliza() != null ? aivc.getNroPoliza().toUpperCase() : "NA");
                            tabla.addCell("COMPAÑÍA ASEGURADORA");
                            tabla.addCell(aivc.getCompaniaAseguradora() != null ? aivc.getCompaniaAseguradora().toUpperCase() : "NA");
                            tabla.addCell("FECHA DE VENCIMIENTO");
                            tabla.addCell(aivc.getFechaVencimiento() != null ? Util.dateFormat(aivc.getFechaVencimiento()) : "NA");
                        }
                    }
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                    titulo = new Paragraph("PROPIETARIO",
                            FontFactory.getFont("arial",
                                    14,
                                    Font.BOLD,
                                    BaseColor.BLACK));
                    titulo.setAlignment(Element.ALIGN_CENTER);
                    doc.add(titulo);
                    doc.add(Chunk.NEWLINE);
                    tabla = new PdfPTable(2);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("NOMBRES");
                    tabla.addCell(aivc.getNombresPropietario() != null ? aivc.getNombresPropietario().toUpperCase() : "NA");
                    tabla.addCell(aivc.getIdTipoIdenProp() != null ? aivc.getIdTipoIdenProp().getNombre().toUpperCase() : "NA");
                    tabla.addCell(aivc.getNroDocPropietario() != null ? aivc.getNroDocPropietario().toUpperCase() : "NA");
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                    tabla = new PdfPTable(2);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("CLASE DEL VEHÍCULO");
                    tabla.addCell(aivc.getIdAccTipoVehiculo() != null ? aivc.getIdAccTipoVehiculo().getTipoVehiculo().toUpperCase() : "NA");
                    tabla.addCell("TIPO DE SERVICIO");
                    tabla.addCell(aivc.getIdTipoServ() != null ? aivc.getIdTipoServ().getTipoServ().toUpperCase() : "NA");
                    tabla.addCell("SEGURO DE RESPONSABILIDAD CIVIL");
                    tabla.addCell(aivc.getSeguroRespCivil() != null ? (aivc.getSeguroRespCivil().equals(String.valueOf(1)) ? "SÍ" : "NO") : "NA");
                    tabla.addCell("NACIONALIDAD");
                    tabla.addCell(aivc.getNacionalidad() != null ? (aivc.getNacionalidad().equals(1) ? "SÍ" : "NO") : "NA");
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                    doc.newPage();
                }
                if (a.getPathCroquis() != null) {
                    doc.newPage();
                    titulo = new Paragraph("CROQUIS",
                            FontFactory.getFont("arial",
                                    14,
                                    Font.BOLD,
                                    BaseColor.BLACK));
                    titulo.setAlignment(Element.ALIGN_CENTER);
                    doc.add(titulo);
                    doc.add(Chunk.NEWLINE);
                    Image croquis = Image.getInstance(a.getPathCroquis());
                    doc.add(croquis);
                    doc.newPage();
                }
            }
            if (a.getAccInformeVicList() != null) {
                titulo = new Paragraph("VÍCTIMAS",
                        FontFactory.getFont("arial",
                                14,
                                Font.BOLD,
                                BaseColor.BLACK));
                titulo.setAlignment(Element.ALIGN_CENTER);
                doc.add(titulo);
                doc.add(Chunk.NEWLINE);
                for (AccInformeVic aiv : a.getAccInformeVicList()) {
                    tabla = new PdfPTable(2);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("NOMBRES");
                    tabla.addCell(aiv.getNombres() != null ? aiv.getNombres().toUpperCase() : "NA");
                    tabla.addCell(aiv.getIdTipoIdentificacion() != null ? aiv.getIdTipoIdentificacion().getNombre().toUpperCase() : "NA");
                    tabla.addCell(aiv.getNroDoc() != null ? aiv.getNroDoc().toUpperCase() : "NA");
                    tabla.addCell("FECHA DE NACIMIENTO");
                    tabla.addCell(aiv.getFechaNac() != null ? Util.dateFormat(aiv.getFechaNac()) : "NA");
                    tabla.addCell("SEXO");
                    tabla.addCell(aiv.getSexo() != null ? aiv.getSexo().toString().toUpperCase() : "NA");
                    tabla.addCell("DIRECCIÓN");
                    tabla.addCell(aiv.getDireccion() != null ? aiv.getDireccion().toUpperCase() : "NA");
                    tabla.addCell("CIUDAD");
                    tabla.addCell(aiv.getCiudad() != null ? aiv.getCiudad().toUpperCase() : "NA");
                    tabla.addCell("TELÉFONOS");
                    tabla.addCell(aiv.getTelefono() != null ? aiv.getTelefono().toUpperCase() : "NA");
                    tabla.addCell("CONDICIÓN");
                    tabla.addCell(aiv.getCondicion() != null ? (aiv.getCondicion().equals(0) ? "MUERTO" : "HERIDO") : "NA");
                    tabla.addCell("PORTA CINTURON DE SEGURIDAD");
                    tabla.addCell(aiv.getCinturon() != null ? (aiv.getCinturon().equals(0) ? "NO" : "SÍ") : "NA");
                    tabla.addCell("PORTA CASCO");
                    tabla.addCell(aiv.getCasco() != null ? (aiv.getCasco().equals(0) ? "NO" : "SÍ") : "NA");
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                    titulo = new Paragraph("SE LLEVÓ A EXAMEN DE",
                            FontFactory.getFont("arial",
                                    14,
                                    Font.BOLD,
                                    BaseColor.BLACK));
                    titulo.setAlignment(Element.ALIGN_CENTER);
                    doc.add(titulo);
                    doc.add(Chunk.NEWLINE);
                    tabla = new PdfPTable(2);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("CENTRO DE ATENCIÓN");
                    tabla.addCell(aiv.getSitioAtencion() != null ? aiv.getSitioAtencion().toUpperCase() : "NA");
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                    tabla = new PdfPTable(4);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("EMBRIAGUEZ");
                    tabla.addCell(aiv.getPrebaEmbriaguez() != null ? (aiv.getPrebaEmbriaguez().equals(1) ? "POSITIVO" : (aiv.getPrebaEmbriaguez().equals(0) ? "NEGATIVO" : "NO SE REALIZÓ")) : "NA");
                    tabla.addCell("RESULTADO");
                    tabla.addCell(aiv.getResultEmbriaguez() != null ? aiv.getResultEmbriaguez().toUpperCase() : "NA");
                    tabla.addCell("DROGA");
                    tabla.addCell(aiv.getPruebaDrogra() != null ? (aiv.getPruebaDrogra().equals(1) ? "POSITIVO" : (aiv.getPruebaDrogra().equals(0) ? "NEGATIVO" : "NO SE REALIZÓ")) : "NA");
                    tabla.addCell("RESULTADO");
                    tabla.addCell(aiv.getResultDroga() != null ? aiv.getResultDroga().toUpperCase() : "NA");
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                }
            }
            if (a.getAccInformeTestigoList() != null) {
                doc.newPage();
                titulo = new Paragraph("TESTIGOS",
                        FontFactory.getFont("arial",
                                14,
                                Font.BOLD,
                                BaseColor.BLACK));
                titulo.setAlignment(Element.ALIGN_CENTER);
                doc.add(titulo);
                doc.add(Chunk.NEWLINE);
                for (AccInformeTestigo ait : a.getAccInformeTestigoList()) {
                    tabla = new PdfPTable(2);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("NOMBRES");
                    tabla.addCell(ait.getNombres() != null ? ait.getNombres().toUpperCase() : "NA");
                    tabla.addCell(ait.getIdTipoIdentificacion() != null ? ait.getIdTipoIdentificacion().getNombre().toUpperCase() : "NA");
                    tabla.addCell(ait.getNroDoc() != null ? ait.getNroDoc().toUpperCase() : "NA");
                    tabla.addCell("DIRECCIÓN");
                    tabla.addCell(ait.getDireccion() != null ? ait.getDireccion().toUpperCase() : "NA");
                    tabla.addCell("CIUDAD");
                    tabla.addCell(ait.getCiudad() != null ? ait.getCiudad().toUpperCase() : "NA");
                    tabla.addCell("TELÉFONO(s)");
                    tabla.addCell(ait.getTelefono() != null ? ait.getTelefono().toUpperCase() : "NA");
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                }
            }
            doc.add(Chunk.NEWLINE);
            doc.newPage();
            titulo = new Paragraph("OBSERVACIONES",
                    FontFactory.getFont("arial",
                            14,
                            Font.BOLD,
                            BaseColor.BLACK));
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(Chunk.NEWLINE);
            titulo = new Paragraph(a.getObservaciones() != null ? a.getObservaciones().toUpperCase() : "NA");
            doc.add(titulo);
            doc.add(Chunk.NEWLINE);
            doc.newPage();
            titulo = new Paragraph("ANEXOS",
                    FontFactory.getFont("arial",
                            14,
                            Font.BOLD,
                            BaseColor.BLACK));
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(Chunk.NEWLINE);
            titulo = new Paragraph(a.getAnexos() != null ? a.getAnexos().toUpperCase() : "NA");
            doc.add(titulo);
            doc.add(Chunk.NEWLINE);
            tabla = new PdfPTable(2);
            tabla.getDefaultCell().setBorder(0);
            tabla.addCell("NOMBRES");
            tabla.addCell(a.getAnexoNombres() != null ? a.getAnexoNombres().toUpperCase() : "NA");
            tabla.addCell("PLACA");
            tabla.addCell(a.getAnexoPlaca() != null ? a.getAnexoPlaca().toUpperCase() : "NA");
            tabla.addCell("CORRESPONDE");
            tabla.addCell(a.getAnexoCorresponde() != null ? a.getAnexoCorresponde().toUpperCase() : "NA");
            tabla.addCell("ENTIDAD");
            tabla.addCell(a.getAnexoEntidad() != null ? a.getAnexoEntidad().toUpperCase() : "NA");
            doc.add(tabla);
            doc.add(Chunk.NEWLINE);
            if (a.getAccInformeOpeCausalidadList() != null) {
                doc.newPage();
                titulo = new Paragraph("ÁRBOL DE CAUSALIDAD",
                        FontFactory.getFont("arial",
                                14,
                                Font.BOLD,
                                BaseColor.BLACK));
                titulo.setAlignment(Element.ALIGN_CENTER);
                doc.add(titulo);
                doc.add(Chunk.NEWLINE);
                tabla = new PdfPTable(1);
                tabla.getDefaultCell().setBorder(0);
                for (AccInformeOpeCausalidad aioc : a.getAccInformeOpeCausalidadList()) {
                    tabla.addCell("ÁRBOL");
                    tabla.addCell(aioc.getIdCausaSub().getIdCausa().getIdAccArbol().getArbol().toUpperCase());
                    tabla.addCell("CUASA");
                    tabla.addCell(aioc.getIdCausaSub().getIdCausa().getCausa().toUpperCase());
                    tabla.addCell("SUB CAUSA");
                    tabla.addCell(aioc.getIdCausaSub().getSubcausa().toUpperCase());
                    tabla.addCell("CAUSA RAÍZ");
                    tabla.addCell(aioc.getIdCausaRaiz() != null ? aioc.getIdCausaRaiz().getCausaRaiz().toUpperCase() : "NA");
                    tabla.addCell("RESPUESTA");
                    tabla.addCell(aioc.getRespuesta() != null ? aioc.getRespuesta().toUpperCase() : "NA");
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                }
            }
            doc.add(Chunk.NEWLINE);
            doc.newPage();
            titulo = new Paragraph("VERSIÓN DEL OPERADOR",
                    FontFactory.getFont("arial",
                            14,
                            Font.BOLD,
                            BaseColor.BLACK));
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(Chunk.NEWLINE);
            titulo = new Paragraph(a.getVersionOperador() != null ? a.getVersionOperador().toUpperCase() : "NA");
            doc.add(titulo);
            doc.add(Chunk.NEWLINE);
            if (a.getIdOperadorPrincipal() != null) {
                tabla = new PdfPTable(2);
                tabla.getDefaultCell().setBorder(0);
                tabla.addCell("NOMBRES");
                tabla.addCell(a.getIdOperadorPrincipal().getApellidos().toUpperCase() + " " + a.getIdOperadorPrincipal().getNombres().toUpperCase());
                tabla.addCell("CÓDIGO");
                tabla.addCell(String.valueOf(a.getIdOperadorPrincipal().getCodigoTm()));
                tabla.addCell("CEDULA");
                tabla.addCell(a.getIdOperadorPrincipal().getIdentificacion() != null ? a.getIdOperadorPrincipal().getIdentificacion().toUpperCase() : "NA");
                doc.add(tabla);
                doc.add(Chunk.NEWLINE);
            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            File fex = new File(ruta);
            fex.delete();
        } finally {
            doc.close();
        }
    }

    static String tipoGravedad(int i) {
        if (i == 0) {
            return "CON MUERTOS";
        }
        if (i == 1) {
            return "CON HERIDOS";
        }
        if (i == 2) {
            return "CON MUERTOS Y CON HERIDOS";
        }
        if (i == 3) {
            return "SOLO DAÑOS";
        }
        return "NA";
    }

    public static void generarInformeMaster(AccInformeMaster a) {
        String ruta = "";
        Document doc = new Document(PageSize.A4, 30, 30, 30, 30);
        try {
            //Ruta Documento
            String rutaRaiz = getProperty("accidente.dir");
            if (rutaRaiz.equals("") | rutaRaiz.isEmpty()) {
                return;
            }
            File f = new File(rutaRaiz);
            if (!f.exists()) {
                f.mkdir();
            }
            String rutaIdAccidente = rutaRaiz + String.valueOf(a.getIdAccidente().getIdAccidente()) + "/";
            f = new File(rutaIdAccidente);
            if (!f.exists()) {
                f.mkdir();
            }
            String rutaInforme = rutaIdAccidente + "InformeMaster/";
            f = new File(rutaInforme);
            if (!f.exists()) {
                f.mkdir();
            }
            ruta = rutaInforme + "CopiaRigel.pdf";
            f = new File(rutaInforme, "CopiaRigel.pdf");
            // Documento
            FileOutputStream filePDF = new FileOutputStream(f);
            PdfWriter instance = PdfWriter.getInstance(doc, filePDF);
            doc.open();
            Paragraph titulo;
            PdfPTable tabla;
            titulo = new Paragraph("INFORME MÁSTER",
                    FontFactory.getFont("arial",
                            14,
                            Font.BOLD,
                            BaseColor.BLACK)
            );
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(Chunk.NEWLINE);
            tabla = new PdfPTable(2);
            tabla.getDefaultCell().setBorder(0);
            tabla.addCell("EVENTO");
            tabla.addCell(a.getIdAccidente().getIdNovedadTipoDetalle().getTituloTipoNovedad().toUpperCase());
            tabla.addCell("FECHA DEL EVENTO");
            tabla.addCell(Util.dateTimeFormat(a.getIdAccidente().getFechaAcc()));
            tabla.addCell("VEHÍCULO");
            tabla.addCell(a.getIdAccidente().getIdVehiculo().getCodigo().toUpperCase());
            tabla.addCell("LUGAR");
            tabla.addCell(a.getLugar() != null ? a.getLugar().toUpperCase() : "NA");
            tabla.addCell("FECHA DE INFORME");
            tabla.addCell(a.getFechaInforme() != null ? Util.dateFormat(a.getFechaInforme()) : "NA");
            tabla.addCell("ESTACIÓN CERCA");
            tabla.addCell(a.getIdPrgStoppoint() != null ? a.getIdPrgStoppoint().getName().toUpperCase() : "NA");
            tabla.addCell("HORA DEL EVENTO");
            tabla.addCell(a.getHoraEvento() != null ? a.getHoraEvento().toUpperCase() : "NA");
            tabla.addCell("TIEMPO DE REACCIÓN");
            tabla.addCell(a.getTiempoReaccion() != null ? a.getTiempoReaccion().toUpperCase() : "NA");
            tabla.addCell("HORA FIN DEL EVENTO");
            tabla.addCell(a.getHoraFinEvento() != null ? a.getHoraFinEvento().toUpperCase() : "NA");
            doc.add(tabla);
            doc.add(Chunk.NEWLINE);
            doc.newPage();
            if (a.getAccInformeMasterVehCondList() != null) {
                titulo = new Paragraph("VEHÍCULOS Y CONDUCTORES INVOLUCRADOS",
                        FontFactory.getFont("arial",
                                14,
                                Font.BOLD,
                                BaseColor.BLACK)
                );
                titulo.setAlignment(Element.ALIGN_CENTER);
                doc.add(titulo);
                doc.add(Chunk.NEWLINE);
                for (AccInformeMasterVehCond aim : a.getAccInformeMasterVehCondList()) {
                    titulo = new Paragraph("CONDUCTOR",
                            FontFactory.getFont("arial",
                                    14,
                                    Font.BOLD,
                                    BaseColor.BLACK)
                    );
                    titulo.setAlignment(Element.ALIGN_CENTER);
                    doc.add(titulo);
                    doc.add(Chunk.NEWLINE);
                    tabla = new PdfPTable(2);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("NOMBRES");
                    tabla.addCell(aim.getNombres() != null ? aim.getNombres().toUpperCase() : "NA");
                    tabla.addCell("CÓDIGO");
                    tabla.addCell(aim.getCodigoTm() != null ? aim.getCodigoTm().toUpperCase() : "NA");
                    tabla.addCell("IDENTIFICACIÓN");
                    tabla.addCell(aim.getIdentificacion() != null ? aim.getIdentificacion().toUpperCase() : "NA");
                    tabla.addCell("TELÉFONO(S)");
                    tabla.addCell(aim.getTelefono() != null ? aim.getTelefono().toUpperCase() : "NA");
                    tabla.addCell("SEXO");
                    tabla.addCell(aim.getSexo() != null ? aim.getSexo().toString().toUpperCase() : "NA");
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                    titulo = new Paragraph("VEHÍCULO",
                            FontFactory.getFont("arial",
                                    14,
                                    Font.BOLD,
                                    BaseColor.BLACK)
                    );
                    titulo.setAlignment(Element.ALIGN_CENTER);
                    doc.add(titulo);
                    doc.add(Chunk.NEWLINE);
                    tabla = new PdfPTable(2);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("PLACA");
                    tabla.addCell(aim.getPlaca() != null ? aim.getPlaca().toUpperCase() : "NA");
                    tabla.addCell("COLOR");
                    tabla.addCell(aim.getCodigo() != null ? aim.getCodigo().toUpperCase() : "NA");
                    tabla.addCell("MODELO");
                    tabla.addCell(aim.getModelo() != null ? aim.getModelo().toUpperCase() : "NA");
                    tabla.addCell("INMOVILIZADO");
                    tabla.addCell(aim.getInmovilizado() != null ? (aim.getInmovilizado().equals(1) ? "SÍ" : "NO") : "NA");
                    tabla.addCell("CÓDIGO HIPOTESIS");
                    tabla.addCell(aim.getCodigoHipotesis() != null ? aim.getCodigoHipotesis().toUpperCase() : "NA");
                    doc.add(tabla);
                    tabla = new PdfPTable(1);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("HIPOTESIS");
                    tabla.addCell(aim.getHipotesis() != null ? aim.getHipotesis().toUpperCase() : "NA");
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                }
            }
            if (a.getAccInformeMasterVicList() != null) {
                doc.newPage();
                titulo = new Paragraph("VÍCTIMAS",
                        FontFactory.getFont("arial",
                                14,
                                Font.BOLD,
                                BaseColor.BLACK)
                );
                titulo.setAlignment(Element.ALIGN_CENTER);
                doc.add(titulo);
                doc.add(Chunk.NEWLINE);
                for (AccInformeMasterVic aiv : a.getAccInformeMasterVicList()) {
                    tabla = new PdfPTable(2);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("NOMBRES");
                    tabla.addCell(aiv.getNombres() != null ? aiv.getNombres().toUpperCase() : "NA");
                    tabla.addCell(aiv.getIdTipoIdentificacion() != null ? aiv.getIdTipoIdentificacion().getNombre().toUpperCase() : "NA");
                    tabla.addCell(aiv.getNroDoc() != null ? aiv.getNroDoc().toUpperCase() : "NA");
                    tabla.addCell("FECHA DE NACIMIENTO");
                    tabla.addCell(aiv.getFechaNac() != null ? Util.dateFormat(aiv.getFechaNac()) : "NA");
                    tabla.addCell("SEXO");
                    tabla.addCell(aiv.getSexo() != null ? aiv.getSexo().toString().toUpperCase() : "NA");
                    tabla.addCell("DIRECCIÓN");
                    tabla.addCell(aiv.getDireccion() != null ? aiv.getDireccion().toUpperCase() : "NA");
                    tabla.addCell("CIUDAD");
                    tabla.addCell(aiv.getCiudad() != null ? aiv.getCiudad().toUpperCase() : "NA");
                    tabla.addCell("TELÉFONOS");
                    tabla.addCell(aiv.getTelefono() != null ? aiv.getTelefono().toUpperCase() : "NA");
                    tabla.addCell("CONDICIÓN");
                    tabla.addCell(aiv.getCondicion() != null ? (aiv.getCondicion().equals(0) ? "MUERTO" : "HERIDO") : "NA");
                    tabla.addCell("PORTA CINTURON DE SEGURIDAD");
                    tabla.addCell(aiv.getCinturon() != null ? (aiv.getCinturon().equals(0) ? "NO" : "SÍ") : "NA");
                    tabla.addCell("PORTA CASCO");
                    tabla.addCell(aiv.getCasco() != null ? (aiv.getCasco().equals(0) ? "NO" : "SÍ") : "NA");
                    tabla.addCell("EPS");
                    tabla.addCell(aiv.getEps() != null ? aiv.getEps().toUpperCase() : "NA");
                    doc.add(tabla);
                    tabla = new PdfPTable(1);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("DIAGNOSTICO");
                    tabla.addCell(aiv.getDiagnostico() != null ? aiv.getDiagnostico().toUpperCase() : "NA");
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                }
            }
//            if (a.getAccInformeMasterLesionadoList() != null) {
//                doc.newPage();
//                titulo = new Paragraph("LESIONADOS",
//                        FontFactory.getFont("arial",
//                                14,
//                                Font.BOLD,
//                                BaseColor.BLACK)
//                );
//                titulo.setAlignment(Element.ALIGN_CENTER);
//                doc.add(titulo);
//                doc.add(Chunk.NEWLINE);
//                for (AccInformeMasterLesionado aiml : a.getAccInformeMasterLesionadoList()) {
//                    tabla = new PdfPTable(2);
//                    tabla.getDefaultCell().setBorder(0);
//                    tabla.addCell("NOMBRES");
//                    tabla.addCell(aiml.getNombres() != null ? aiml.getNombres().toUpperCase() : "NA");
//                    tabla.addCell("IDENTIFICACIÓN");
//                    tabla.addCell(aiml.getIdentificacion() != null ? aiml.getIdentificacion().toUpperCase() : "NA");
//                    tabla.addCell("EDAD");
//                    tabla.addCell(aiml.getEdad() != null ? aiml.getEdad().toUpperCase() : "NA");
//                    tabla.addCell("EPS");
//                    tabla.addCell(aiml.getEps() != null ? aiml.getEps().toUpperCase() : "NA");
//                    tabla.addCell("DIRECCIÓN");
//                    tabla.addCell(aiml.getDireccion() != null ? aiml.getDireccion().toUpperCase() : "NA");
//                    tabla.addCell("BARRIO");
//                    tabla.addCell(aiml.getBarrio() != null ? aiml.getBarrio().toUpperCase() : "NA");
//                    tabla.addCell("TELÉFONO FIJO");
//                    tabla.addCell(aiml.getTelefono() != null ? aiml.getTelefono().toUpperCase() : "NA");
//                    tabla.addCell("CELULAR");
//                    tabla.addCell(aiml.getCelular() != null ? aiml.getCelular().toUpperCase() : "NA");
//                    tabla.addCell("CLINICA DE TRASLADO");
//                    tabla.addCell(aiml.getClinicaTraslado() != null ? aiml.getClinicaTraslado().toUpperCase() : "NA");
//                    doc.add(tabla);
//                    tabla = new PdfPTable(1);
//                    tabla.getDefaultCell().setBorder(0);
//                    tabla.addCell("DIAGNOSTICO");
//                    tabla.addCell(aiml.getDiagnostigo() != null ? aiml.getDiagnostigo().toUpperCase() : "NA");
//                    doc.add(tabla);
//                    doc.add(Chunk.NEWLINE);
//                }
//            }
            doc.newPage();
            if (a.getAccInformeMasterAgentesList() != null) {
                titulo = new Paragraph("POLICIAS Y AGENTES DE TRANSITO",
                        FontFactory.getFont("arial",
                                14,
                                Font.BOLD,
                                BaseColor.BLACK)
                );
                titulo.setAlignment(Element.ALIGN_CENTER);
                doc.add(titulo);
                doc.add(Chunk.NEWLINE);
                for (AccInformeMasterAgentes aima : a.getAccInformeMasterAgentesList()) {
                    tabla = new PdfPTable(2);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("NOMBRES");
                    tabla.addCell(aima.getNombres() != null ? aima.getNombres().toUpperCase() : "NA");
                    tabla.addCell("NÚMERO CHALECO");
                    tabla.addCell(aima.getChaleco() != null ? aima.getChaleco().toUpperCase() : "NA");
                    tabla.addCell("PLACA AGENTE");
                    tabla.addCell(aima.getPlacaChaleco() != null ? aima.getPlacaChaleco().toUpperCase() : "NA");
                    tabla.addCell("IDENTIFICACIÓN");
                    tabla.addCell(aima.getIdentificacion() != null ? aima.getIdentificacion().toUpperCase() : "NA");
                    tabla.addCell("UNIDAD MOVIL");
                    tabla.addCell(aima.getUnidadMovil() != null ? aima.getUnidadMovil().toUpperCase() : "NA");
                    tabla.addCell("PLACA UNIDAD MOVIL");
                    tabla.addCell(aima.getPlacaUnidadMovil() != null ? aima.getPlacaUnidadMovil().toUpperCase() : "NA");
                    tabla.addCell("NÚMERO UNIDAD MOVIL");
                    tabla.addCell(aima.getNumeroUnidadMovil() != null ? aima.getNumeroUnidadMovil().toUpperCase() : "NA");
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                }
            }
            if (a.getAccInformeMasterMedicosList() != null) {
                titulo = new Paragraph("MÉDICOS Y/O PARAMÉDICOS",
                        FontFactory.getFont("arial",
                                14,
                                Font.BOLD,
                                BaseColor.BLACK)
                );
                titulo.setAlignment(Element.ALIGN_CENTER);
                doc.add(titulo);
                doc.add(Chunk.NEWLINE);
                for (AccInformeMasterMedicos aimm : a.getAccInformeMasterMedicosList()) {
                    tabla = new PdfPTable(2);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("NOMBRES");
                    tabla.addCell(aimm.getNombres() != null ? aimm.getNombres().toUpperCase() : "NA");
                    tabla.addCell("IDENTIFICACIÓN");
                    tabla.addCell(aimm.getIdentificacion() != null ? aimm.getIdentificacion().toUpperCase() : "NA");
                    tabla.addCell("NÚMERO DE AMBULANCIA");
                    tabla.addCell(aimm.getNroAmbulancia() != null ? aimm.getNroAmbulancia().toUpperCase() : "NA");
                    tabla.addCell("PLACA AMBULANCIA");
                    tabla.addCell(aimm.getPlacaAmbulancia() != null ? aimm.getPlacaAmbulancia().toUpperCase() : "NA");
                    tabla.addCell("NÚMERO");
                    tabla.addCell(aimm.getNumeroAmbulancia() != null ? aimm.getNumeroAmbulancia().toUpperCase() : "NA");
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                }
            }
            if (a.getAccInformeMasterBomberosList() != null) {
                titulo = new Paragraph("BOMBEROS",
                        FontFactory.getFont("arial",
                                14,
                                Font.BOLD,
                                BaseColor.BLACK)
                );
                titulo.setAlignment(Element.ALIGN_CENTER);
                doc.add(titulo);
                doc.add(Chunk.NEWLINE);
                for (AccInformeMasterBomberos aimb : a.getAccInformeMasterBomberosList()) {
                    tabla = new PdfPTable(2);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("NOMBRES");
                    tabla.addCell(aimb.getNombres() != null ? aimb.getNombres().toUpperCase() : "NA");
                    tabla.addCell("IDENTIFICACIÓN");
                    tabla.addCell(aimb.getIdentificacion() != null ? aimb.getIdentificacion().toUpperCase() : "NA");
                    tabla.addCell("UNIDAD MOVIL");
                    tabla.addCell(aimb.getUnidadMovil() != null ? aimb.getUnidadMovil().toUpperCase() : "NA");
                    tabla.addCell("PLACA UNIDAD MOVIL");
                    tabla.addCell(aimb.getPlacaUnidadMovil() != null ? aimb.getPlacaUnidadMovil().toUpperCase() : "NA");
                    tabla.addCell("NÚMERO");
                    tabla.addCell(aimb.getNumeroUnidadMovil() != null ? aimb.getNumeroUnidadMovil().toUpperCase() : "NA");
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                }
            }
            if (a.getAccInformeMasterInspectoresList() != null) {
                titulo = new Paragraph("INSPECTORES",
                        FontFactory.getFont("arial",
                                14,
                                Font.BOLD,
                                BaseColor.BLACK)
                );
                titulo.setAlignment(Element.ALIGN_CENTER);
                doc.add(titulo);
                doc.add(Chunk.NEWLINE);
            }
            for (AccInformeMasterInspectores aimi : a.getAccInformeMasterInspectoresList()) {
                tabla = new PdfPTable(2);
                tabla.getDefaultCell().setBorder(0);
                tabla.addCell("NOMBRES");
                tabla.addCell(aimi.getNombres() != null ? aimi.getNombres().toUpperCase() : "NA");
                tabla.addCell("NÚMERO DE CHALECO");
                tabla.addCell(aimi.getChaleco() != null ? aimi.getChaleco().toUpperCase() : "NA");
                tabla.addCell("PLACA AGENTE");
                tabla.addCell(aimi.getPlacaChaleco() != null ? aimi.getPlacaChaleco().toUpperCase() : "NA");
                tabla.addCell("IDENTIFICACIÓN");
                tabla.addCell(aimi.getIdentificacion() != null ? aimi.getIdentificacion().toUpperCase() : "NA");
                tabla.addCell("UNIDAD MOVIL");
                tabla.addCell(aimi.getUnidadMovil() != null ? aimi.getUnidadMovil().toUpperCase() : "NA");
                tabla.addCell("PLACA UNIDAD MOVIL");
                tabla.addCell(aimi.getPlacaUnidadMovil() != null ? aimi.getPlacaUnidadMovil().toUpperCase() : "NA");
                tabla.addCell("NÚMERO UNIDAD MOVIL");
                tabla.addCell(aimi.getNumeroUnidadMovil() != null ? aimi.getNumeroUnidadMovil().toUpperCase() : "NA");
                doc.add(tabla);
                doc.add(Chunk.NEWLINE);
            }
            if (a.getAccInformeMasterRecomotosList() != null) {
                titulo = new Paragraph("RECOMOTOS",
                        FontFactory.getFont("arial",
                                14,
                                Font.BOLD,
                                BaseColor.BLACK)
                );
                titulo.setAlignment(Element.ALIGN_CENTER);
                doc.add(titulo);
                doc.add(Chunk.NEWLINE);
                for (AccInformeMasterRecomotos aimr : a.getAccInformeMasterRecomotosList()) {
                    tabla = new PdfPTable(2);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("NOMBRES");
                    tabla.addCell(aimr.getNombres() != null ? aimr.getNombres().toUpperCase() : "NA");
                    tabla.addCell("NÚMERO DE CHALECO");
                    tabla.addCell(aimr.getChaleco() != null ? aimr.getChaleco().toUpperCase() : "NA");
                    tabla.addCell("PLACA AGENTE");
                    tabla.addCell(aimr.getPlacaChaleco() != null ? aimr.getPlacaChaleco().toUpperCase() : "NA");
                    tabla.addCell("IDENTIFICACIÓN");
                    tabla.addCell(aimr.getIdentificacion() != null ? aimr.getIdentificacion().toUpperCase() : "NA");
                    tabla.addCell("UNIDAD MOVIL");
                    tabla.addCell(aimr.getUnidadMovil() != null ? aimr.getUnidadMovil().toUpperCase() : "NA");
                    tabla.addCell("PLACA UNIDAD MOVIL");
                    tabla.addCell(aimr.getPlacaUnidadMovil() != null ? aimr.getPlacaUnidadMovil().toUpperCase() : "NA");
                    tabla.addCell("NÚMERO UNIDAD MOVIL");
                    tabla.addCell(aimr.getNumeroUnidadMovil() != null ? aimr.getNumeroUnidadMovil().toUpperCase() : "NA");
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                }
            }
            if (a.getAccInformeMasterTestigoList() != null) {
                titulo = new Paragraph("TESTIGOS",
                        FontFactory.getFont("arial",
                                14,
                                Font.BOLD,
                                BaseColor.BLACK)
                );
                titulo.setAlignment(Element.ALIGN_CENTER);
                doc.add(titulo);
                doc.add(Chunk.NEWLINE);
                for (AccInformeMasterTestigo aimt : a.getAccInformeMasterTestigoList()) {
                    tabla = new PdfPTable(2);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("NOMBRES");
                    tabla.addCell(aimt.getNombres() != null ? aimt.getNombres().toUpperCase() : "NA");
                    tabla.addCell("DIRECCIÓN");
                    tabla.addCell(aimt.getDireccion() != null ? aimt.getDireccion().toUpperCase() : "NA");
                    tabla.addCell("IDENTIFICACIÓN");
                    tabla.addCell(aimt.getIdentificacion() != null ? aimt.getIdentificacion().toUpperCase() : "NA");
                    tabla.addCell("TELÉFONO");
                    tabla.addCell(aimt.getTelefono() != null ? aimt.getTelefono().toUpperCase() : "NA");
                    if (aimt.getVideo() != null) {
                        tabla.addCell("VÍDEO");
                        tabla.addCell(aimt.getVideo().equals(1) ? "SÍ" : "NO");
                        if (aimt.getVideo().equals(1)) {
                            tabla.addCell("HORA");
                            tabla.addCell(aimt.getHoraVideo() != null ? aimt.getHoraVideo().toUpperCase() : "NA");
                        }
                    }
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                }
            }
            doc.newPage();
            titulo = new Paragraph("VERSIÓN DE OPERADOR MÁSTER",
                    FontFactory.getFont("arial",
                            14,
                            Font.BOLD,
                            BaseColor.BLACK)
            );
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(Chunk.NEWLINE);
            tabla = new PdfPTable(1);
            tabla.getDefaultCell().setBorder(0);
            tabla.addCell(a.getVersionMaster() != null ? a.getVersionMaster().toUpperCase() : "NA");
            doc.add(tabla);
            doc.add(Chunk.NEWLINE);
            titulo = new Paragraph("VERSIÓN DE OPERADOR",
                    FontFactory.getFont("arial",
                            14,
                            Font.BOLD,
                            BaseColor.BLACK)
            );
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(Chunk.NEWLINE);
            tabla = new PdfPTable(1);
            tabla.getDefaultCell().setBorder(0);
            tabla.addCell(a.getVersionOperador() != null ? a.getVersionOperador().toUpperCase() : "NA");
            doc.add(tabla);
            doc.add(Chunk.NEWLINE);
            tabla = new PdfPTable(2);
            tabla.getDefaultCell().setBorder(0);
            tabla.addCell("VÍDEO DEL OPERADOR");
            tabla.addCell(a.getVideoOperador() != null ? (a.getVideoOperador().equals(1) ? "SÍ" : "NO") : "NO");
            tabla.addCell("HORA");
            tabla.addCell(a.getHoraVideo() != null ? a.getHoraVideo().toUpperCase() : "NO");
            tabla.addCell("AUTORIZA SU GRABACIÓN");
            tabla.addCell(a.getAutorizaGrabacion() != null ? (a.getAutorizaGrabacion().equals(1) ? "SÍ" : "NO") : "NO");
            tabla.addCell("COLILLA TACOGRAFO");
            tabla.addCell(a.getColillaTacografo() != null ? (a.getColillaTacografo().equals(1) ? "SÍ" : "NO") : "NO");
            tabla.addCell("TELEMÉTRIA");
            tabla.addCell(a.getTelemetria() != null ? (a.getTelemetria().equals(1) ? "SÍ" : "NO") : "NO");
            doc.add(tabla);
            doc.add(Chunk.NEWLINE);
            doc.newPage();
            titulo = new Paragraph("MASTER PRIMER RESPONSABLE",
                    FontFactory.getFont("arial",
                            14,
                            Font.BOLD,
                            BaseColor.BLACK)
            );
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(Chunk.NEWLINE);
            if (a.getIdEmpleadoMaster() != null) {
                tabla = new PdfPTable(2);
                tabla.getDefaultCell().setBorder(0);
                tabla.addCell("NOMBRES");
                tabla.addCell(a.getIdEmpleadoMaster().getApellidos().toUpperCase() + " " + a.getIdEmpleadoMaster().getNombres().toUpperCase());
                tabla.addCell("CÓDIGO");
                tabla.addCell(String.valueOf(a.getIdEmpleadoMaster().getCodigoTm()));
                tabla.addCell("IDENTIFICACIÓN");
                tabla.addCell(a.getIdEmpleadoMaster().getIdentificacion().toUpperCase());
                tabla.addCell("GRUPO");
                if (a.getIdEmpleadoMaster().getPmGrupoList() != null) {
                    tabla.addCell(a.getIdEmpleadoMaster().getPmGrupoList().get(0).getNombreGrupo().toUpperCase());
                }
                doc.add(tabla);
                doc.add(Chunk.NEWLINE);
            }
            if (a.getAccInformeMasterApoyoList() != null) {
                doc.newPage();
                titulo = new Paragraph("MÁSTERS APOYO",
                        FontFactory.getFont("arial",
                                14,
                                Font.BOLD,
                                BaseColor.BLACK)
                );
                titulo.setAlignment(Element.ALIGN_CENTER);
                doc.add(titulo);
                doc.add(Chunk.NEWLINE);
                for (AccInformeMasterApoyo aima : a.getAccInformeMasterApoyoList()) {
                    tabla = new PdfPTable(2);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("NOMBRES");
                    tabla.addCell(aima.getIdMaster().getApellidos().toUpperCase() + " " + aima.getIdMaster().getNombres().toUpperCase());
                    tabla.addCell("CÓDIGO");
                    tabla.addCell(String.valueOf(aima.getIdMaster().getCodigoTm()));
                    tabla.addCell("IDENTIFICACIÓN");
                    tabla.addCell(aima.getIdMaster().getIdentificacion().toUpperCase());
                    tabla.addCell("GRUPO");
                    if (aima.getIdMaster().getPmGrupoList() != null) {
                        tabla.addCell(aima.getIdMaster().getPmGrupoList().get(0).getNombreGrupo().toUpperCase());
                    }
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                }
            }
            if (a.getAccInformeMasterAlbumList() != null) {
                doc.newPage();
                titulo = new Paragraph("ALBUM FOTOGRÁFICO",
                        FontFactory.getFont("arial",
                                14,
                                Font.BOLD,
                                BaseColor.BLACK)
                );
                titulo.setAlignment(Element.ALIGN_CENTER);
                doc.add(titulo);
                doc.add(Chunk.NEWLINE);
                for (AccInformeMasterAlbum aima : a.getAccInformeMasterAlbumList()) {
                    tabla = new PdfPTable(1);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell(Image.getInstance(aima.getPathFoto()));
                    doc.add(tabla);
                    tabla = new PdfPTable(2);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("TECNOLOGÍA");
                    tabla.addCell(aima.getTecnologia() != null ? (aima.getTecnologia().equals(1) ? "DIGITAL" : "ANÁLOGA") : "NA");
                    tabla.addCell("INSTRUMENTOS UTILIZADOS");
                    tabla.addCell(aima.getInstrumentos() != null ? aima.getInstrumentos().toUpperCase() : "NA");
                    doc.add(tabla);
                    tabla = new PdfPTable(4);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("MARCA");
                    tabla.addCell(aima.getMarca() != null ? aima.getMarca().toUpperCase() : "NA");
                    tabla.addCell("REFERENCIA");
                    tabla.addCell(aima.getReferencia() != null ? aima.getReferencia().toUpperCase() : "NA");
                    tabla.addCell("LENTE");
                    tabla.addCell(aima.getLente() != null ? aima.getLente().toUpperCase() : "NA");
                    tabla.addCell("ZOOM");
                    tabla.addCell(aima.getZoom() != null ? aima.getZoom().toUpperCase() : "NA");
                    tabla.addCell("ISO");
                    tabla.addCell(aima.getIso() != null ? aima.getIso().toUpperCase() : "NA");
                    tabla.addCell("TARJETA");
                    tabla.addCell(aima.getTarjeta() != null ? aima.getTarjeta().toUpperCase() : "NA");
                    doc.add(tabla);
                    tabla = new PdfPTable(2);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("TIPO DE FOTOGRAFÍA");
                    tabla.addCell(aima.getTipoFoto() != null ? aima.getTipoFoto().toUpperCase() : "NA");
                    doc.add(tabla);
                    tabla = new PdfPTable(1);
                    tabla.getDefaultCell().setBorder(0);
                    tabla.addCell("DESCRIPCIÓN DE LA IMAGEN");
                    tabla.addCell(aima.getDescripcion() != null ? aima.getDescripcion().toUpperCase() : "NA");
                    doc.add(tabla);
                    doc.add(Chunk.NEWLINE);
                }
            }
            if (a.getPathBosquejo() != null) {
                doc.newPage();
                titulo = new Paragraph("BOSQUEJO TOPOGRÁFICO",
                        FontFactory.getFont("arial",
                                14,
                                Font.BOLD,
                                BaseColor.BLACK)
                );
                titulo.setAlignment(Element.ALIGN_CENTER);
                doc.add(titulo);
                doc.add(Chunk.NEWLINE);
                tabla = new PdfPTable(1);
                tabla.addCell(Image.getInstance(a.getPathBosquejo()));
                doc.add(tabla);
            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            File fex = new File(ruta);
            fex.delete();
        } finally {
            doc.close();
        }
    }
}
