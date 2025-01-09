/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.CableAccDocumentoFacadeLocal;
import com.movilidad.ejb.CableAccSiniestradoFacadeLocal;
import com.movilidad.ejb.CableAccidentalidadFacadeLocal;
import com.movilidad.ejb.CableCabinaFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.model.CableAccDocumento;
import com.movilidad.model.CableAccidentalidad;
import com.movilidad.model.CableCabina;
import com.movilidad.model.Empleado;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author soluciones-it
 */
@Named(value = "cableAccInformeJSF")
@ViewScoped
public class CableAccInformeJSF implements Serializable {

    @EJB
    private CableAccidentalidadFacadeLocal accidentalidadFacadeLocal;
    @EJB
    private CableAccSiniestradoFacadeLocal cableAccSiniestradoFacadeLocal;
    @EJB
    private CableAccDocumentoFacadeLocal accDocumentoFacadeLocal;
    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    private CableCabinaFacadeLocal cableCabinaFacadeLocal;

    private List<CableAccidentalidad> listCableAccidentalidad;

    // panel de busqueda
    private Date dDesde;
    private Date dHasta;
    private String cIdentiEmpleado;
    private String cCodCableCabina;
    private Integer idCableAccTipoBq;

    //Descargar el documento
    private StreamedContent file;

    /**
     * Creates a new instance of CableAccInformeJSF
     */
    public CableAccInformeJSF() {
    }

    @PostConstruct
    public void init() {
        listCableAccidentalidad = new ArrayList<>();
        dDesde = new Date();
        dHasta = new Date();
    }

    /**
     * Realizar la busqueda de los objeros CableAccidentalidad de acuerdo a los
     * parametros de busqueda
     */
    public void buscarAccidente() {
        Integer idEmpleado = null;
        Integer idCabina = null;
        if (dHasta.compareTo(dDesde) < 0) {
            MovilidadUtil.addErrorMessage("Fecha hasta no puede ser inferior a fecha desde");
            return;
        }
        if (cIdentiEmpleado != null) {
            Empleado empleado = empleadoFacadeLocal.findByIdentificacion(cIdentiEmpleado);
            if (empleado != null) {
                idEmpleado = empleado.getIdEmpleado();
            }
        }
        if (cCodCableCabina != null) {
            CableCabina cableCabina = cableCabinaFacadeLocal.findByCodigo(cCodCableCabina, 0);
            if (cableCabina != null) {
                idCabina = cableCabina.getIdCableCabina();
            }
        }
        String cDesde = Util.dateFormat(dDesde);
        String cHasta = Util.dateFormat(dHasta);
        listCableAccidentalidad = accidentalidadFacadeLocal.findByArguments(idCabina, idEmpleado, cDesde, cHasta, idCableAccTipoBq);
    }

    /**
     * Limpiar las variables de realizar la busqueda
     */
    public void limpiar() {
        dDesde = new Date();
        dHasta = new Date();
        cIdentiEmpleado = null;
        cCodCableCabina = null;
        listCableAccidentalidad.clear();
        idCableAccTipoBq = null;
    }

    /**
     * Permite generar el informe en archivo excel de la data relacionada con
     * los objetos CableAccidentalidad
     */
    public void generarInforme() {
        if (listCableAccidentalidad.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron eventos de accidentalidad con los parámetros suministrados");
            return;
        }
        try {
            Map parametros = new HashMap();
            listCableAccidentalidad.forEach((acc) -> {
                acc.setCableAccSiniestradoList(cableAccSiniestradoFacadeLocal
                        .findAllEstadoReg(acc.getIdCableAccidentalidad()));
                List<CableAccDocumento> listCableAccDoc = accDocumentoFacadeLocal
                        .findByAccidentalidadAndEstadoReg(acc.getIdCableAccidentalidad());
                acc.setCableAccDocumentoList(listCableAccDoc);
//                if (listCableAccDoc != null) {
//                    List<CableAccDocumento> collect = listCableAccDoc
//                            .stream()
//                            .sorted((cad1, cad2) -> cad1.getIdAccTpDocs().getTipoDocs()
//                            .compareTo(cad2.getIdAccTpDocs().getTipoDocs()))
//                            .collect(Collectors.toList());
//                    acc.setCableAccDocumentoList(collect);
//                }
            });
            parametros.put("accidentes", listCableAccidentalidad);
            String destino = "/tmp/reporte_accidentes.xlsx";
            String plantilla = "/rigel/reporte_accidentes.xlsx";
            GeneraXlsx.generar(plantilla, destino, parametros);
            File excel = new File(destino);
            InputStream stream = new FileInputStream(excel);
            file = new DefaultStreamedContent(stream, "text/plain", "reporte-accidentes.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<CableAccidentalidad> getListCableAccidentalidad() {
        return listCableAccidentalidad;
    }

    public void setListCableAccidentalidad(List<CableAccidentalidad> listCableAccidentalidad) {
        this.listCableAccidentalidad = listCableAccidentalidad;
    }

    public Date getdDesde() {
        return dDesde;
    }

    public void setdDesde(Date dDesde) {
        this.dDesde = dDesde;
    }

    public Date getdHasta() {
        return dHasta;
    }

    public void setdHasta(Date dHasta) {
        this.dHasta = dHasta;
    }

    public String getcIdentiEmpleado() {
        return cIdentiEmpleado;
    }

    public void setcIdentiEmpleado(String cIdentiEmpleado) {
        this.cIdentiEmpleado = cIdentiEmpleado;
    }

    public String getcCodCableCabina() {
        return cCodCableCabina;
    }

    public void setcCodCableCabina(String cCodCableCabina) {
        this.cCodCableCabina = cCodCableCabina;
    }

    public Integer getIdCableAccTipoBq() {
        return idCableAccTipoBq;
    }

    public void setIdCableAccTipoBq(Integer idCableAccTipoBq) {
        this.idCableAccTipoBq = idCableAccTipoBq;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }
}
