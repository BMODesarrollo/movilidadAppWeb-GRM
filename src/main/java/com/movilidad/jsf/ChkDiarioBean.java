package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.ChkDiarioDetFacadeLocal;
import com.movilidad.ejb.ChkDiarioFacadeLocal;
import com.movilidad.model.ChkDiario;
import com.movilidad.model.ChkDiarioDet;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "chkDiarioBean")
@ViewScoped
public class ChkDiarioBean implements Serializable {

    @EJB
    private ChkDiarioFacadeLocal chkDiarioEjb;
    @EJB
    private ChkDiarioDetFacadeLocal chkDiarioDetEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private Date desde;
    private Date hasta;

    private StreamedContent file; // Reporte que se va a descargar

    private List<ChkDiario> lstChkDiario;
    private List<ChkDiarioDet> lstChkDiarioDet;

    @PostConstruct
    public void init() {
        desde = MovilidadUtil.fechaHoy();
        hasta = MovilidadUtil.fechaHoy();
        consultar();
    }

    public void consultar() {
        lstChkDiario = chkDiarioEjb.findAllByFechas(desde, hasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public List<ChkDiarioDet> getListDetalles(ChkDiario param) {
        return chkDiarioDetEjb.findByIdChkDiario(param.getIdChkDiario());
    }

    /**
     * Evento que carga el listado de fallas y componentes afectados
     *
     * @param event
     */
    public void onRowToggleDetalle(ToggleEvent event) {
        ChkDiario chkDiario = ((ChkDiario) event.getData());

        if (chkDiario != null) {
            lstChkDiarioDet = chkDiarioDetEjb.findByIdChkDiario(chkDiario.getIdChkDiario());
        }

    }

    public void generarReporte() throws FileNotFoundException {

        file = null;
        Map parametros = new HashMap();
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        plantilla = plantilla + "Reporte CheckList.xlsx";
        parametros.put("lista", lstChkDiario);
        destino = destino + "Reporte CheckList.xlsx";

        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        InputStream stream = new FileInputStream(excel);
        file = new DefaultStreamedContent(stream, "text/plain", "Reporte CheckList.xlsx");
    }

    public String obtenerFallas(ChkDiario item) {

        if (item.getChkDiarioDetList() == null) {
            return null;
        }

        String fallas = "";
        List<ChkDiarioDet> lista = item.getChkDiarioDetList();

        for (ChkDiarioDet x : lista) {
            if (x.getEstado() != ConstantsUtil.ON_INT && x.getIdChkComponenteFalla() != null) {

                if (!MovilidadUtil.stringIsEmpty(x.getObservacion())) {
                    fallas += x.getIdChkComponenteFalla().getNombre() + "( " + x.getObservacion() + " ) ,";
                } else {
                    fallas += x.getIdChkComponenteFalla().getNombre() + ",";
                }

            }

        }

        return fallas;

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

    public List<ChkDiario> getLstChkDiario() {
        return lstChkDiario;
    }

    public void setLstChkDiario(List<ChkDiario> lstChkDiario) {
        this.lstChkDiario = lstChkDiario;
    }

    public List<ChkDiarioDet> getLstChkDiarioDet() {
        return lstChkDiarioDet;
    }

    public void setLstChkDiarioDet(List<ChkDiarioDet> lstChkDiarioDet) {
        this.lstChkDiarioDet = lstChkDiarioDet;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

}
