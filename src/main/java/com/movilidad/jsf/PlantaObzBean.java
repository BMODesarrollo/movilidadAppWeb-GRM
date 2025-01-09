package com.movilidad.jsf;

import com.movilidad.dto.HoraPrgEjecDTO;
import com.movilidad.ejb.EmpleadoDocumentosFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.model.EmpleadoDocumentos;
import com.movilidad.util.beans.PlantaObz;
import com.movilidad.utils.MovilidadUtil;
import static com.movilidad.utils.MovilidadUtil.getProperty;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "plantaObzBean")
@ViewScoped
public class PlantaObzBean implements Serializable {

    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private EmpleadoDocumentosFacadeLocal empleadoDocEjb;
    @EJB
    private PrgSerconFacadeLocal prgSerconFacadeLocal;
    @EJB
    private EmpleadoDocumentosFacadeLocal empleadoDocumentosFacadeLocal;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;
    private List<PlantaObz> lista;
    private List<HoraPrgEjecDTO> listaHorasEjecutadas;
    private EmpleadoDocumentos empleadoDocumento;
    private EmpleadoDocumentoJSFManagedBean empleadoDocumentosBean;

    private Date desde = MovilidadUtil.fechaHoy();
    private Date hasta = MovilidadUtil.fechaHoy();

    @PostConstruct
    public void init() {
        empleadoDocumento = null;
        //consultar();
    }

    public void consultar() {

        lista = empleadoEjb.obtenerInformePlantaObz(
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), desde, hasta
        );
        listaHorasEjecutadas = prgSerconFacadeLocal.getHoraPrgEjec(desde, hasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (!listaHorasEjecutadas.isEmpty()) {
            for (PlantaObz obz : lista) {
                for (HoraPrgEjecDTO horas : listaHorasEjecutadas) {
                    if (Objects.equals(horas.getCodigoTm(), obz.getCodigoTm())) {
                        obz.setHorasProgramadas(horas.getHorasProgramadas());
                        obz.setHorasReales(horas.getHorasReales());
                    }
                }
            }
        }
    }

    public void documento(int idEmpleado) throws Exception {
        empleadoDocumento = empleadoDocumentosFacadeLocal.findByIdEmpleadoDocumentoCap(idEmpleado);

        prepDownloadLocal(empleadoDocumento.getPathDocumento());

    }

    public void prepDownloadLocal(String path) throws Exception {
        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
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

    public static StreamedContent obtenerPDF(String rutaArchivo) throws IOException {
        // Lee el archivo PDF desde el sistema de archivos
        File archivo = new File(rutaArchivo);
        InputStream inputStream = new FileInputStream(archivo);

        // Crea un objeto StreamedContent a partir del InputStream del archivo PDF
        StreamedContent file = new DefaultStreamedContent(inputStream, "application/pdf", archivo.getName());

        return file;
    }

    public List<PlantaObz> getLista() {
        return lista;
    }

    public void setLista(List<PlantaObz> lista) {
        this.lista = lista;
    }

    public EmpleadoFacadeLocal getEmpleadoEjb() {
        return empleadoEjb;
    }

    public void setEmpleadoEjb(EmpleadoFacadeLocal empleadoEjb) {
        this.empleadoEjb = empleadoEjb;
    }

    public EmpleadoDocumentosFacadeLocal getEmpleadoDocEjb() {
        return empleadoDocEjb;
    }

    public void setEmpleadoDocEjb(EmpleadoDocumentosFacadeLocal empleadoDocEjb) {
        this.empleadoDocEjb = empleadoDocEjb;
    }

    public GopUnidadFuncionalSessionBean getUnidadFuncionalSessionBean() {
        return unidadFuncionalSessionBean;
    }

    public void setUnidadFuncionalSessionBean(GopUnidadFuncionalSessionBean unidadFuncionalSessionBean) {
        this.unidadFuncionalSessionBean = unidadFuncionalSessionBean;
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

    public ViewDocumentoJSFManagedBean getViewDMB() {
        return viewDMB;
    }

    public void setViewDMB(ViewDocumentoJSFManagedBean viewDMB) {
        this.viewDMB = viewDMB;
    }

}
