/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AccidenteConductorFacadeLocal;
import com.movilidad.ejb.AccidenteDocumentoFacadeLocal;
import com.movilidad.ejb.AccidenteFacadeLocal;
import com.movilidad.ejb.AccidenteVehiculoFacadeLocal;
import com.movilidad.model.AccAtencionVia;
import com.movilidad.model.AccTipoDocs;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteConductor;
import com.movilidad.model.AccidenteDocumento;
import com.movilidad.model.AccidenteVehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author soluciones-it
 */
@Named(value = "accidenteRecomotoJSF")
@ViewScoped
public class AccidenteRecomotoJSF implements Serializable {

    @EJB
    private AccidenteFacadeLocal accidenteFacadeLocal;
    @EJB
    private AccidenteDocumentoFacadeLocal accidenteDocumentoFacadeLocal;
    @EJB
    private AccidenteVehiculoFacadeLocal accidenteVehiculoFacadeLocal;
    @EJB
    private AccidenteConductorFacadeLocal accidenteConductorFacadeLocal;
    @Inject
    private BuscadorAccidenteJSF buscadorAccidenteJSF;

    //
    private Integer idAccTipoDocs;
    private Integer idAccAtencionVia;
    private UploadedFile file;

    //
    private AccidenteDocumento accidenteDocumento;
    private List<AccidenteDocumento> listAccidenteDocumento;
    private List<AccidenteVehiculo> listAccidenteVehiculo;
    private List<AccidenteConductor> listAccidenteConductor;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of AccidenteRecomotoJSF
     */
    public AccidenteRecomotoJSF() {
    }

    // guardar conciliacion
    public void guardarConciliacion() {
        Accidente a = buscadorAccidenteJSF.getAccidente();
        if (a == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un accidente");
            return;
        }
        if (a.getValorConciliado() == null) {
            a.setConciliado(null);
            a.setValorConciliado(null);
            MovilidadUtil.addSuccessMessage("Accidente no conciliado");
        } else {
            a.setConciliado(1);
            if (a.getFechaCierreRecomoto() == null) {
                a.setFechaCierreRecomoto(new Date());
            }
            if (a.getFechaCierre() == null) {
                a.setFechaCierre(new Date());
            }
            if(a.getFechaAsistencia() == null){
            a.setFechaAsistencia(new Date());
            }
            MovilidadUtil.addSuccessMessage("Accidente conciliado");
            buscadorAccidenteJSF.setAccidente(null);
            buscadorAccidenteJSF.buscarAccidente();
        }
        accidenteFacadeLocal.edit(a);
    }

    // guardar cierre de caso en via
    public void guardarCierreCasoVia() {
        Accidente a = buscadorAccidenteJSF.getAccidente();
        if (a == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un accidente");
            return;
        }
        if (idAccAtencionVia == null) {
            a.setIdAccAtencionVia(null);
            MovilidadUtil.addSuccessMessage("Sin cierre de caso en vía");
        } else {
            a.setIdAccAtencionVia(new AccAtencionVia(idAccAtencionVia));
            MovilidadUtil.addSuccessMessage("Cierre de caso asignado al accidente correctamente");
            if (a.getFechaCierreRecomoto() == null) {
                a.setFechaCierreRecomoto(new Date());
            }
        }
        accidenteFacadeLocal.edit(a);
        buscadorAccidenteJSF.buscarAccidente();
    }

    public void prepareGuardar() {
        accidenteDocumento = new AccidenteDocumento();
    }

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        MovilidadUtil.hideModal("documentoDlg");
        MovilidadUtil.addSuccessMessage("Documento cargado con éxito");
    }

    // guardar documento
    public void guardarDocumento() {
        Accidente a = buscadorAccidenteJSF.getAccidente();
        if (a == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un accidente");
            return;
        }
        if (file == null) {
            MovilidadUtil.addErrorMessage("Debe cargar un documento");
            return;
        }
        if (file != null) {
            if (file.getContents().length <= 0) {
                MovilidadUtil.addErrorMessage("Debe cargar un documento");
                return;
            }
        }
        if (idAccTipoDocs == null) {
            MovilidadUtil.addErrorMessage("Tipo documento es requerido");
            return;
        }
        Date d = new Date();
        accidenteDocumento.setIdAccidente(a);
        accidenteDocumento.setIdAccTipoDocs(new AccTipoDocs(idAccTipoDocs));
        accidenteDocumento.setFecha(d);
        accidenteDocumento.setCreado(d);
        accidenteDocumento.setModificado(d);
        accidenteDocumento.setUsername(user.getUsername());
        accidenteDocumento.setEstadoReg(0);
        accidenteDocumentoFacadeLocal.create(accidenteDocumento);
        String path = MovilidadUtil.cargarArchivosAccidentalidad(file,
                a.getIdAccidente(),
                "Documentos",
                accidenteDocumento.getIdAccidenteDocumento(),
                UUID.randomUUID().toString());
        accidenteDocumento.setPath(path);
        accidenteDocumentoFacadeLocal.edit(accidenteDocumento);
        resetDocumento();
        MovilidadUtil.addSuccessMessage("Documento agregado con éxito");
    }

    // reiniciar variables de gestion de documentos
    public void resetDocumento() {
        accidenteDocumento = null;
        idAccTipoDocs = null;
        file = null;
    }

    /**
     *
     * @param path ruta en disco del archivo a leer
     * @return StreamedContent del archivo leido
     * @throws Exception Ruta no encontrada o no permitida, arrojará una
     * excepción
     */
    public StreamedContent prepDownloadLocal(String path) throws Exception {
//        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
        return MovilidadUtil.prepDownload(path);
    }

    public List<AccidenteDocumento> getListAccidenteDocumento() {
        Accidente a = buscadorAccidenteJSF.getAccidente();
        if (a == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un accidente");
            return null;
        }
        listAccidenteDocumento = accidenteDocumentoFacadeLocal.estadoReg(a.getIdAccidente());
        return listAccidenteDocumento;
    }

    public List<AccidenteVehiculo> getListAccidenteVehiculo() {
        Accidente a = buscadorAccidenteJSF.getAccidente();
        if (a == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un accidente");
            return null;
        }
        listAccidenteVehiculo = accidenteVehiculoFacadeLocal.estadoReg(a.getIdAccidente());
        return listAccidenteVehiculo;
    }

    public List<AccidenteConductor> getListAccidenteConductor() {
        Accidente a = buscadorAccidenteJSF.getAccidente();
        if (a == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un accidente");
            return null;
        }
        listAccidenteConductor = accidenteConductorFacadeLocal.estadoReg(a.getIdAccidente());
        return listAccidenteConductor;
    }

    public Integer getIdAccTipoDocs() {
        return idAccTipoDocs;
    }

    public void setIdAccTipoDocs(Integer idAccTipoDocs) {
        this.idAccTipoDocs = idAccTipoDocs;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public AccidenteDocumento getAccidenteDocumento() {
        return accidenteDocumento;
    }

    public void setAccidenteDocumento(AccidenteDocumento accidenteDocumento) {
        this.accidenteDocumento = accidenteDocumento;
    }

    public Integer getIdAccAtencionVia() {
        Accidente a = buscadorAccidenteJSF.getAccidente();
        if (a == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un accidente");
            return null;
        }
        idAccAtencionVia = a.getIdAccAtencionVia() != null ? a.getIdAccAtencionVia().getIdAccAtencionVia() : null;
        return idAccAtencionVia;
    }

    public void setIdAccAtencionVia(Integer idAccAtencionVia) {
        this.idAccAtencionVia = idAccAtencionVia;
    }

}
