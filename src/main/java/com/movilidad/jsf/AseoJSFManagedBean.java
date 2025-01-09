/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AseoFacadeLocal;
import com.movilidad.ejb.AseoTipoFacadeLocal;
import com.movilidad.ejb.CableEstacionFacadeLocal;
import com.movilidad.ejb.SstEmpresaVisitanteFacadeLocal;
import com.movilidad.model.Aseo;
import com.movilidad.model.AseoParamArea;
import com.movilidad.model.AseoTipo;
import com.movilidad.model.CableEstacion;
import com.movilidad.model.SstEmpresaVisitante;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "aseoJSFMB")
@ViewScoped
public class AseoJSFManagedBean implements Serializable {

    @EJB
    private SstEmpresaVisitanteFacadeLocal sstEmpresaVisitanteEJB;
    @EJB
    private AseoTipoFacadeLocal aseoTipoEJB;
    @EJB
    private CableEstacionFacadeLocal cableEstacionEJB;
    @EJB
    private AseoFacadeLocal aseoEJB;
    @Inject
    private AseoLoginJSF aseoJSF;

    @Inject
    private UploadFotoJSFManagedBean fotoJSFManagedBean;

    private Aseo aseo;
    private Date fechaDesde = MovilidadUtil.fechaHoy();
    private Date fechaHasta = MovilidadUtil.fechaHoy();
    private int i_idResponsable;
    private int i_idTipoAseo;
    private int i_idAseoParamArea;
    private int i_idCableEstacion;
    private List<CableEstacion> listEstacion;
    private List<SstEmpresaVisitante> listResponsables;
    private List<AseoTipo> listAseoTipo;
    private List<AseoParamArea> listAseoParamArea;
    private List<Aseo> listAseo;
    private List<UploadedFile> archivos;
    private List<String> listFotos = new ArrayList<>();
    private AseoParamArea aseoParamArea;
    private AseoTipo aseoTipo;
    private SstEmpresaVisitante responsable;
    private boolean b_controlAcciones;
    private String qr_code;

    UserExtended user;

    /**
     * Creates a new instance of AseoJSFManagedBean
     */
    public AseoJSFManagedBean() {
    }

    @PostConstruct
    public void init() {
        consultar();
        getUser();
        b_controlAcciones = user != null;
    }

    /**
     * validar el valor del qr resivido desde la vista y consultar por medio de
     * este valor.
     */
    public void procesarValorQr() {

        if (qr_code.equals("")) {
            MovilidadUtil.addErrorMessage("No se detectó un código QR, intente nuevamente.");
        } else {
            responsable = sstEmpresaVisitanteEJB.findByHashString(qr_code);
            if (responsable == null) {
                MovilidadUtil.addErrorMessage("No existe registro con el codigo leido.");
            } else {
                MovilidadUtil.addSuccessMessage("Responsable cargado.");
            }
            MovilidadUtil.updateComponent("aseo_dialog_form:label_responsable");
        }
        MovilidadUtil.hideModal("fotoDialog");
    }

    /**
     * Obtener valor de usuario en sesión.
     */
    public void getUser() {
        try {
            user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            responsable = null;
        } catch (Exception e) {
            user = null;
        }
    }

    /**
     * Preparar variables para un nuevo registro.
     */
    public void nuevo() {
        aseo = new Aseo();
        aseo.setFechaIni(MovilidadUtil.fechaCompletaHoy());
        aseo.setFechaFin(MovilidadUtil.fechaCompletaHoy());
        listResponsables = sstEmpresaVisitanteEJB.findAllEstadoReg();
        listAseoTipo = aseoTipoEJB.findAllByEstadoReg();
        listAseoParamArea = new ArrayList<>();
        listEstacion = cableEstacionEJB.findByEstadoReg();
        archivos = new ArrayList<>();
        i_idResponsable = 0;
        i_idTipoAseo = 0;
        i_idAseoParamArea = 0;
        i_idCableEstacion = 0;
        responsable = aseoJSF.getResponsable();
        aseoParamArea = null;
        aseoTipo = null;
        MovilidadUtil.openModal("aseo_dialog_wv");
    }

    /**
     * Obtener lista de las rutas de cada una de las imagenes por registro.
     *
     * @return
     * @throws IOException
     */
    public void obtenerFotos() throws IOException {
        this.listFotos.clear();
        List<String> lstNombresImg = Util.getFileList(aseo.getIdAseo(), "aseoGeneral");

        for (String f : lstNombresImg) {
            f = aseo.getPathFotos() + f;
            listFotos.add(f);
        }
        fotoJSFManagedBean.setListFotos(listFotos);
    }

    /**
     * Agregar las iomagenes en la lista archivos, para posteriormente ser
     * alamcenadas
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        archivos.add(event.getFile());
    }

    /**
     * Consultar registros de aseo por rango de fecha.
     */
    public void consultar() {
        listAseo = aseoEJB.findAllByRangoFechas(fechaDesde, fechaHasta);
    }

    /**
     * Preparar variables para la edicion de un registro seleccionado.
     *
     * @param a
     */
    public void editar(Aseo a) {
        aseo = a;

        i_idResponsable = aseo.getSstEmpresaVisitante().getIdSstEmpresaVisitante();
        i_idAseoParamArea = aseo.getAseoParamArea().getIdAseoParamArea();
        i_idTipoAseo = aseo.getAseoTipo().getIdAseoTipo();
        responsable = aseo.getSstEmpresaVisitante();
        aseoTipo = aseo.getAseoTipo();
        aseoParamArea = aseo.getAseoParamArea();
        i_idCableEstacion = aseo.getAseoParamArea().getIdCableEstacion().getIdCableEstacion();
        listResponsables = sstEmpresaVisitanteEJB.findAllEstadoReg();
        listAseoTipo = aseoTipoEJB.findAllByEstadoReg();
        listAseoParamArea = new ArrayList<>();
        listEstacion = cableEstacionEJB.findByEstadoReg();
        prepareObjeto(4);
        archivos = new ArrayList<>();
        MovilidadUtil.openModal("aseo_dialog_wv");
    }

    /**
     * Asignar objeto a las variables responsable, aseoTipo o listAseoParamArea
     * segun la opcion
     *
     * @param opc
     */
    public void prepareObjeto(int opc) {
        switch (opc) {
            case 1:
                responsable = null;
                for (SstEmpresaVisitante obj : listResponsables) {
                    if (obj.getIdSstEmpresaVisitante().equals(i_idResponsable)) {
                        responsable = obj;
                        break;
                    }
                }
                break;
            case 2:
                aseoTipo = null;
                for (AseoTipo obj : listAseoTipo) {
                    if (obj.getIdAseoTipo().equals(i_idTipoAseo)) {
                        aseoTipo = obj;
                        break;
                    }
                }
                break;
            case 3:
                aseoParamArea = null;
                for (AseoParamArea obj : listAseoParamArea) {
                    if (obj.getIdAseoParamArea().equals(i_idAseoParamArea)) {
                        aseoParamArea = obj;
                        break;
                    }
                }
                break;
            case 4:
                listAseoParamArea.clear();
                for (CableEstacion obj : listEstacion) {
                    if (obj.getIdCableEstacion().equals(i_idCableEstacion)) {
                        if (obj.getAseoParamAreaList() != null) {
                            for (AseoParamArea ap : obj.getAseoParamAreaList()) {
                                if (ap.getEstadoReg() == 0) {
                                    listAseoParamArea.add(ap);
                                }
                            }
                        }
                        break;
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * Validar que las variables en cuestion esten listas para persistir.
     *
     * @return True si sale algo mal, False si todo esta bien.
     * @throws ParseException
     */
    public boolean validar() throws ParseException {
        if (responsable == null) {
            MovilidadUtil.addErrorMessage("Dede seleccionar un responsable.");
            return true;
        }
        if (aseoTipo == null) {
            MovilidadUtil.addErrorMessage("Dede seleccionar un Tipo de Aseo.");
            return true;
        }
        if (aseoParamArea == null) {
            MovilidadUtil.addErrorMessage("Dede seleccionar un Area de Actividad.");
            return true;
        }
        if (MovilidadUtil.fechasIgualMenorMayor(aseo.getFechaIni(), aseo.getFechaFin(), true) == 2) {
            MovilidadUtil.addErrorMessage("La fecha inicio no debe ser mayor a la fecha fin.");
            return true;
        }
        if (MovilidadUtil.fechasIgualMenorMayor(aseo.getFechaFin(), MovilidadUtil.fechaCompletaHoy(), false) == 2) {
            MovilidadUtil.addErrorMessage("Las fechas no deben ser posteriores al dia de hoy.");
            return true;
        }
        return false;
    }

    /**
     * Actualizar un registro en base de datos
     *
     * @throws ParseException
     */
    public void guardarEdit() throws ParseException {
        if (validar()) {
            return;
        }
        aseo.setAseoParamArea(aseoParamArea);
        aseo.setSstEmpresaVisitante(responsable);
        aseo.setAseoTipo(aseoTipo);
        aseo.setModificado(MovilidadUtil.fechaCompletaHoy());
        aseo.setUsername(user.getUsername());
        aseoEJB.edit(aseo);
        if (!archivos.isEmpty()) {
            String path = aseo.getPathFotos() == null ? "/" : aseo.getPathFotos();

            for (UploadedFile f : archivos) {
                path = Util.saveFile(f, aseo.getIdAseo(), "aseoGeneral");
            }
            aseo.setPathFotos(path);
            this.aseoEJB.edit(aseo);
            archivos.clear();
        }
        MovilidadUtil.addSuccessMessage("Acción finalizada exitosamente.");
        MovilidadUtil.hideModal("aseo_dialog_wv");
        aseo = null;

    }

    /**
     * Metodo encargado de persistir en base de datos un nuevo registro de aseo
     *
     * @throws ParseException
     */
    public void guardar() throws ParseException {
        if (validar()) {
            return;
        }
        aseo.setCreado(MovilidadUtil.fechaCompletaHoy());
        aseo.setAseoParamArea(aseoParamArea);
        aseo.setSstEmpresaVisitante(responsable);
        aseo.setAseoTipo(aseoTipo);
        aseo.setUsername(user != null ? user.getUsername() : aseoJSF.getResponsable().getNumeroDocumento());
        aseo.setEstadoReg(0);
        aseo.setPathFotos("/");

        aseoEJB.create(aseo);
        if (!archivos.isEmpty()) {
            String path = " ";
            for (UploadedFile f : archivos) {
                path = Util.saveFile(f, aseo.getIdAseo(), "aseoGeneral");
            }
            aseo.setPathFotos(path);
            this.aseoEJB.edit(aseo);
            archivos.clear();
        }
        MovilidadUtil.addSuccessMessage("Acción finalizada exitosamente.");
        MovilidadUtil.hideModal("aseo_dialog_wv");
        aseo = null;
        consultar();

    }

    public Aseo getAseo() {
        return aseo;
    }

    public void setAseo(Aseo aseo) {
        this.aseo = aseo;
    }

    public int getI_idResponsable() {
        return i_idResponsable;
    }

    public void setI_idResponsable(int i_idResponsable) {
        this.i_idResponsable = i_idResponsable;
    }

    public int getI_idTipoAseo() {
        return i_idTipoAseo;
    }

    public void setI_idTipoAseo(int i_idTipoAseo) {
        this.i_idTipoAseo = i_idTipoAseo;
    }

    public int getI_idAseoParamArea() {
        return i_idAseoParamArea;
    }

    public void setI_idAseoParamArea(int i_idAseoParamArea) {
        this.i_idAseoParamArea = i_idAseoParamArea;
    }

    public List<SstEmpresaVisitante> getListResponsables() {
        return listResponsables;
    }

    public void setListResponsables(List<SstEmpresaVisitante> listResponsables) {
        this.listResponsables = listResponsables;
    }

    public List<AseoTipo> getListAseoTipo() {
        return listAseoTipo;
    }

    public void setListAseoTipo(List<AseoTipo> listAseoTipo) {
        this.listAseoTipo = listAseoTipo;
    }

    public List<AseoParamArea> getListAseoParamArea() {
        return listAseoParamArea;
    }

    public void setListAseoParamArea(List<AseoParamArea> listAseoParamArea) {
        this.listAseoParamArea = listAseoParamArea;
    }

    public List<Aseo> getListAseo() {
        return listAseo;
    }

    public void setListAseo(List<Aseo> listAseo) {
        this.listAseo = listAseo;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public SstEmpresaVisitante getResponsable() {
        return responsable;
    }

    public void setResponsable(SstEmpresaVisitante responsable) {
        this.responsable = responsable;
    }

    public boolean isB_controlAcciones() {
        return b_controlAcciones;
    }

    public void setB_controlAcciones(boolean b_controlAcciones) {
        this.b_controlAcciones = b_controlAcciones;
    }

    public List<String> getListFotos() {
        return listFotos;
    }

    public void setListFotos(List<String> listFotos) {
        this.listFotos = listFotos;
    }

    public List<CableEstacion> getListEstacion() {
        return listEstacion;
    }

    public void setListEstacion(List<CableEstacion> listEstacion) {
        this.listEstacion = listEstacion;
    }

    public int getI_idCableEstacion() {
        return i_idCableEstacion;
    }

    public void setI_idCableEstacion(int i_idCableEstacion) {
        this.i_idCableEstacion = i_idCableEstacion;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

}
