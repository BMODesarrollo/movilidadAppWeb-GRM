/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AseoCabinaFacadeLocal;
import com.movilidad.model.AseoCabina;
import com.movilidad.model.AseoCabinaNovedad;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author solucionesit
 */
@Named(value = "aseoCabinaBean")
@ViewScoped
public class AseoCabinaJSFMB implements Serializable {

    /**
     * Creates a new instance of AseoCabinaNovJSFMB
     */
    public AseoCabinaJSFMB() {
    }
    
    @EJB
    private AseoCabinaFacadeLocal aseoCabinaEJB;
    
    private Date hasta = MovilidadUtil.fechaHoy();
    private Date desde = MovilidadUtil.fechaHoy();
    private List<AseoCabina> list;
    private AseoCabina aseoCabina;
    private List<String> listFotos = new ArrayList<>();
    @Inject
    private UploadFotoJSFManagedBean fotoJSFManagedBean;
    
    @PostConstruct
    public void init() {
        consultar();
    }

    /**
     * Obtener lista de las rutas de cada una de las imagenes por registro
     *
     * @throws IOException
     */
    public void obtenerFotos() throws IOException {
        this.listFotos.clear();
        List<String> lstNombresImg = Util.getFileList(aseoCabina.getIdAseoCabina(), "aseoCabina");
        
        for (String f : lstNombresImg) {
            f = aseoCabina.getPathFotos() + f;
            listFotos.add(f);
        }
        fotoJSFManagedBean.setListFotos(listFotos);
    }

    /**
     * Consultar registros de aseo por rango de fecha
     */
    public void consultar() {
        list = aseoCabinaEJB.findAllByFechaEstadoReg(desde, hasta);
    }
    
    public void editar(AseoCabinaNovedad acn) {
    }
    
    public void nuevo() {
    }
    
    public Date getHasta() {
        return hasta;
    }
    
    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }
    
    public Date getDesde() {
        return desde;
    }
    
    public void setDesde(Date desde) {
        this.desde = desde;
    }
    
    public List<String> getListFotos() {
        return listFotos;
    }
    
    public void setListFotos(List<String> listFotos) {
        this.listFotos = listFotos;
    }
    
    public List<AseoCabina> getList() {
        return list;
    }
    
    public void setList(List<AseoCabina> list) {
        this.list = list;
    }
    
    public AseoCabina getAseoCabina() {
        return aseoCabina;
    }
    
    public void setAseoCabina(AseoCabina aseoCabina) {
        this.aseoCabina = aseoCabina;
    }
    
}
