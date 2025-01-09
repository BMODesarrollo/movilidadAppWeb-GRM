package com.movilidad.jsf;

import com.movilidad.ejb.AseoParamAreaFacadeLocal;
import com.movilidad.ejb.SstEmpresaVisitanteFacadeLocal;
import com.movilidad.model.AseoParamArea;
import com.movilidad.model.SstEmpresaVisitante;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "reporteQrBean")
@ViewScoped
public class ReporteQrBean implements Serializable {

    @EJB
    private AseoParamAreaFacadeLocal aseoParamAreaEjb;
    @EJB
    private SstEmpresaVisitanteFacadeLocal sstEmpresaVisitanteEjb;

    private SstEmpresaVisitante sstEmpresaVisitante;
    private AseoParamArea aseoParamArea;

    private List<AseoParamArea> lstAseoParamAreas;
    private List<SstEmpresaVisitante> lstSstEmpresaVisitantes;

    private List<AseoParamArea> lstAreas;
    private List<AseoParamArea> lstAreasAux;

    private List<SstEmpresaVisitante> lstVisitantes;
    private List<SstEmpresaVisitante> lstVisitanteAux;

    private String codAreaAseo;
    private String docVisitante;

    private boolean flagAreasAseo;
    private boolean flagVisitante;
    private boolean flagAreasAseoIndividual;
    private boolean flagVisitanteIndividual;

    @PostConstruct
    public void init() {
        flagAreasAseo = false;
        flagVisitante = false;
        sstEmpresaVisitante = null;
        aseoParamArea = null;
        codAreaAseo = null;
        docVisitante = null;
        flagAreasAseoIndividual = false;
        flagVisitanteIndividual = false;
    }

    /**
     * Prepara la lista de áreas de aseo
     */
    public void prepareListAseoParamArea() {
        lstAseoParamAreas = null;

        if (lstAseoParamAreas == null) {
            lstAseoParamAreas = aseoParamAreaEjb.findAllByEstadoReg();
            PrimeFaces.current().ajax().update(":frmAseoParamArea:dtAseoParamArea");
            PrimeFaces.current().executeScript("PF('wlVdtAseoParamArea').clearFilters();");
        }
    }

    /**
     * Evento que se dispara al seleccionar registro de las áreas de aseo
     * y lo muestra en la vista 
     *
     * @param event
     */
    public void onRowAseoParamAreaClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof AseoParamArea) {
            setAseoParamArea((AseoParamArea) event.getObject());
        }
        flagAreasAseo = false;
        flagVisitante = false;
        flagAreasAseoIndividual = true;
        flagVisitanteIndividual = false;
        sstEmpresaVisitante = null;
        PrimeFaces.current().executeScript("PF('wlVdtAseoParamArea').clearFilters();");
    }

    /**
     * Prepara la lista de terceros para mostrarlos en vista modal
     */
    public void prepareListVisitantes() {
        lstSstEmpresaVisitantes = null;

        if (lstSstEmpresaVisitantes == null) {
            lstSstEmpresaVisitantes = sstEmpresaVisitanteEjb.findAllEstadoReg();
            PrimeFaces.current().ajax().update(":frmVisitante:dtVisitante");
            PrimeFaces.current().executeScript("PF('wlVdtVisitante').clearFilters();");
        }
    }

    /**
     * Evento que se dispara al seleccionar registro de la lista de terceros
     * y lo muestra en la vista. 
     * @param event
     */
    public void onRowVisitantesClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof AseoParamArea) {
            setAseoParamArea((AseoParamArea) event.getObject());
        }
        flagAreasAseo = false;
        flagVisitante = false;
        flagAreasAseoIndividual = false;
        flagVisitanteIndividual = true;
        aseoParamArea = null;
        PrimeFaces.current().executeScript("PF('wlVdtVisitante').clearFilters();");
    }

    /**
     * Método que obtiene el listado de las áreas de Aseo y muestra todos
     * los QR que hacen parte de éstas en la vista.
     */
    public void obtenerAreasAseo() {
        int nRegistros;
        lstVisitantes = new ArrayList<>();
        lstAreas = aseoParamAreaEjb.findAllByEstadoReg();
        nRegistros = lstAreas.size() / 2;
        lstAreasAux = lstAreas.subList(nRegistros, lstAreas.size() - 1);
        lstAreas = lstAreas.subList(0, nRegistros - 1);
        flagAreasAseo = true;
        flagVisitante = false;
        flagAreasAseoIndividual = false;
        flagVisitanteIndividual = false;
        sstEmpresaVisitante = null;
        aseoParamArea = null;
        codAreaAseo = null;
        docVisitante = null;
    }

     /**
     * Método que obtiene el listado de terceros y muestra todos
     * los QR que hacen parte de éstos en la vista.
     */
    public void obtenerVisitantes() {
        int nRegistros;
        lstAreas = new ArrayList<>();
        lstVisitantes = sstEmpresaVisitanteEjb.findAllEstadoReg();
        nRegistros = lstVisitantes.size() / 2;
        lstVisitanteAux = lstVisitantes.subList(nRegistros, lstVisitantes.size() - 1);
        lstVisitantes = lstVisitantes.subList(0, nRegistros - 1);
        flagAreasAseo = false;
        flagAreasAseoIndividual = false;
        flagVisitanteIndividual = false;
        sstEmpresaVisitante = null;
        aseoParamArea = null;
        codAreaAseo = null;
        docVisitante = null;
        flagVisitante = true;
    }

    /**
     * Realiza la búsqueda del QR de un visitante y lo muestra en la vista
     * principal
     */
    public void obtenerVisitante() {
        sstEmpresaVisitante = sstEmpresaVisitanteEjb.findByNumDocumento(docVisitante);

        if (sstEmpresaVisitante == null) {
            MovilidadUtil.addErrorMessage("El visitante consultado NO existe");
            reset();
            return;
        }

        flagAreasAseo = false;
        flagVisitante = false;
        flagAreasAseoIndividual = false;
        flagVisitanteIndividual = true;
        aseoParamArea = null;
        lstAreas = null;
        lstAreasAux = null;
        lstVisitantes = null;
        lstVisitanteAux = null;
    }

    /**
     * Realiza la búsqueda del QR de un área aseo y lo muestra en la vista
     * principal
     */
    public void obtenerAreaAseo() {
        aseoParamArea = aseoParamAreaEjb.findByCodigo(codAreaAseo);

        if (aseoParamArea == null) {
            MovilidadUtil.addErrorMessage("El área consultada NO existe");
            reset();
            return;
        }

        flagAreasAseo = false;
        flagVisitante = false;
        flagAreasAseoIndividual = true;
        flagVisitanteIndividual = false;
        sstEmpresaVisitante = null;
        lstAreas = null;
        lstAreasAux = null;
        lstVisitantes = null;
        lstVisitanteAux = null;

    }

    private void reset() {
        flagAreasAseo = false;
        flagVisitante = false;
        flagAreasAseoIndividual = false;
        flagVisitanteIndividual = false;
    }

    public List<AseoParamArea> getLstAreas() {
        return lstAreas;
    }

    public void setLstAreas(List<AseoParamArea> lstAreas) {
        this.lstAreas = lstAreas;
    }

    public List<SstEmpresaVisitante> getLstVisitantes() {
        return lstVisitantes;
    }

    public void setLstVisitantes(List<SstEmpresaVisitante> lstVisitantes) {
        this.lstVisitantes = lstVisitantes;
    }

    public List<AseoParamArea> getLstAreasAux() {
        return lstAreasAux;
    }

    public void setLstAreasAux(List<AseoParamArea> lstAreasAux) {
        this.lstAreasAux = lstAreasAux;
    }

    public List<SstEmpresaVisitante> getLstVisitanteAux() {
        return lstVisitanteAux;
    }

    public void setLstVisitanteAux(List<SstEmpresaVisitante> lstVisitanteAux) {
        this.lstVisitanteAux = lstVisitanteAux;
    }

    public List<AseoParamArea> getLstAseoParamAreas() {
        return lstAseoParamAreas;
    }

    public void setLstAseoParamAreas(List<AseoParamArea> lstAseoParamAreas) {
        this.lstAseoParamAreas = lstAseoParamAreas;
    }

    public List<SstEmpresaVisitante> getLstSstEmpresaVisitantes() {
        return lstSstEmpresaVisitantes;
    }

    public void setLstSstEmpresaVisitantes(List<SstEmpresaVisitante> lstSstEmpresaVisitantes) {
        this.lstSstEmpresaVisitantes = lstSstEmpresaVisitantes;
    }

    public boolean isFlagAreasAseo() {
        return flagAreasAseo;
    }

    public void setFlagAreasAseo(boolean flagAreasAseo) {
        this.flagAreasAseo = flagAreasAseo;
    }

    public boolean isFlagVisitante() {
        return flagVisitante;
    }

    public void setFlagVisitante(boolean flagVisitante) {
        this.flagVisitante = flagVisitante;
    }

    public String getCodAreaAseo() {
        return codAreaAseo;
    }

    public void setCodAreaAseo(String codAreaAseo) {
        this.codAreaAseo = codAreaAseo;
    }

    public String getDocVisitante() {
        return docVisitante;
    }

    public void setDocVisitante(String docVisitante) {
        this.docVisitante = docVisitante;
    }

    public boolean isFlagAreasAseoIndividual() {
        return flagAreasAseoIndividual;
    }

    public void setFlagAreasAseoIndividual(boolean flagAreasAseoIndividual) {
        this.flagAreasAseoIndividual = flagAreasAseoIndividual;
    }

    public boolean isFlagVisitanteIndividual() {
        return flagVisitanteIndividual;
    }

    public void setFlagVisitanteIndividual(boolean flagVisitanteIndividual) {
        this.flagVisitanteIndividual = flagVisitanteIndividual;
    }

    public SstEmpresaVisitante getSstEmpresaVisitante() {
        return sstEmpresaVisitante;
    }

    public void setSstEmpresaVisitante(SstEmpresaVisitante sstEmpresaVisitante) {
        this.sstEmpresaVisitante = sstEmpresaVisitante;
    }

    public AseoParamArea getAseoParamArea() {
        return aseoParamArea;
    }

    public void setAseoParamArea(AseoParamArea aseoParamArea) {
        this.aseoParamArea = aseoParamArea;
    }

}
