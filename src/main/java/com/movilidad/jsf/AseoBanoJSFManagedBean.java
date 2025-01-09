/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.google.zxing.NotFoundException;
import com.movilidad.ejb.AseoBanoFacadeLocal;
import com.movilidad.ejb.AseoFacadeLocal;
import com.movilidad.ejb.AseoParamAreaFacadeLocal;
import com.movilidad.ejb.SstEmpresaVisitanteFacadeLocal;
import com.movilidad.model.AseoBano;
import com.movilidad.model.AseoParamArea;
import com.movilidad.model.SstEmpresaVisitante;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.awt.Image;
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
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "aseoBanoJSFMB")
@ViewScoped
public class AseoBanoJSFManagedBean implements Serializable {

    /**
     * Creates a new instance of AseoBanoJSFManagedBean
     */
    public AseoBanoJSFManagedBean() {
    }
    
    @EJB
    private SstEmpresaVisitanteFacadeLocal sstEmpresaVisitanteEJB;
    @EJB
    private AseoParamAreaFacadeLocal aseoParamAreaEJB;
    @EJB
    private AseoFacadeLocal aseoEJB;
    @EJB
    private AseoBanoFacadeLocal aseoBanoEJB;
    
    @Inject
    private AseoLoginJSF aseoJSF;
    @Inject
    private UploadFotoJSFManagedBean fotoJSFManagedBean;
    private Date fechaDesde = MovilidadUtil.fechaHoy();
    private Date fechaHasta = MovilidadUtil.fechaHoy();
    private int flagBusqueda;
    private boolean b_controlAcciones;
    private AseoBano aseoBano;
    private AseoParamArea aseoParamArea;
    private SstEmpresaVisitante responsable;
    private List<UploadedFile> archivos;
    private int height = 0;
    private int width = 0;
    private String qr_code;
    private List<String> listFotos = new ArrayList<>();
    
    private List<AseoBano> listAseoBano;
    
    UserExtended user;

    /**
     * Consultar registros de aseo baño por rango de fecha
     */
    public void consultar() {
        listAseoBano = aseoBanoEJB.findAllByEstadoReg(fechaDesde, fechaHasta);
    }

    /**
     * Obtener valor de usuario en sesión
     */
    public void getUser() {
        try {
            user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            user = null;
        }
    }
    
    @PostConstruct
    public void init() {
        getUser();
        consultar();
        b_controlAcciones = user != null;
    }

    /**
     * Preparar variables para un nuevo registro
     */
    public void nuevo() {
        aseoBano = new AseoBano();
        aseoBano.setFechaHoraIni(MovilidadUtil.fechaCompletaHoy());
        aseoBano.setFechaHoraFin(MovilidadUtil.fechaCompletaHoy());
        archivos = new ArrayList<>();
        aseoParamArea = null;
        responsable = aseoJSF.getResponsable();
        MovilidadUtil.openModal("aseo_bano_dialog_wv");
    }

    /**
     * Persistir en base de datos el proceso de autorizacion de aseos.
     */
    public void autorizar(AseoBano ab, int opc) {
        ab.setModificado(MovilidadUtil.fechaCompletaHoy());
        ab.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
        ab.setUserAutoriza(user != null ? user.getUsername() : aseoJSF.getNumDocAndNombre());
        
        ab.setAutorizado(opc);
        aseoBanoEJB.edit(ab);
        MovilidadUtil.addSuccessMessage("Acción completada con exito.");
    }

    /**
     * validar el valor del qr resivido desde la vista y consultar por medio de
     * este valor
     */
    public void procesarValorQr() {
        if (qr_code.equals("")) {
            MovilidadUtil.addErrorMessage("No se detectó un código QR, intente nuevamente.");
            return;
        }
        if (flagBusqueda == 1) {
            responsable = sstEmpresaVisitanteEJB.findByHashString(qr_code);
            if (responsable == null) {
                MovilidadUtil.addErrorMessage("No existe registro con el codigo escaneado.");
            } else {
                MovilidadUtil.addSuccessMessage("Responsable cargado.");
            }
            MovilidadUtil.updateComponent("aseo_bano_dialog_form:label_responsable");
        } else if (flagBusqueda == 2) {
            aseoParamArea = aseoParamAreaEJB.findByHashString(qr_code);
            if (aseoParamArea == null) {
                MovilidadUtil.addErrorMessage("No existe registro con el codigo escaneado.");
            } else {
                MovilidadUtil.addSuccessMessage("Area cargada.");
            }
            MovilidadUtil.updateComponent("aseo_bano_dialog_form:label_area");
        }
        
        MovilidadUtil.updateComponent("aseo_bano_dialog_form:messages");
        MovilidadUtil.hideModal("fotoDialog");
        
    }

    /**
     * Validar que las variables en cuestion esten listas para persistir.
     *
     * @return True si sale algo mal, False si todo esta bien.
     * @throws ParseException
     */
    private boolean validar() throws ParseException {
        if (responsable == null) {
            MovilidadUtil.addErrorMessage("Desde seleccionar un responsable.");
            return true;
        }
        if (aseoParamArea == null) {
            MovilidadUtil.addErrorMessage("Desde seleccionar un Area de Actividad.");
            return true;
        }
        if (MovilidadUtil.fechasIgualMenorMayor(aseoBano.getFechaHoraIni(), aseoBano.getFechaHoraFin(), true) == 2) {
            MovilidadUtil.addErrorMessage("La fecha inicio no debe ser mayor a la fecha fin.");
            return true;
        }
        if (MovilidadUtil.fechasIgualMenorMayor(aseoBano.getFechaHoraFin(), MovilidadUtil.fechaCompletaHoy(), false) == 2) {
            MovilidadUtil.addErrorMessage("Las fechas no deben ser posteriores al dia de hoy.");
            return true;
        }
        return false;
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
     * Actualizar un registro en base de datos
     *
     * @throws ParseException
     */
    public void actulizar() throws ParseException {
        aseoBano.setModificado(MovilidadUtil.fechaCompletaHoy());
        aseoBano.setUsername(user != null ? user.getUsername() : aseoJSF.getResponsable().getNumeroDocumento());
        
        if (!archivos.isEmpty()) {
            String path = " ";
            for (UploadedFile f : archivos) {
                path = Util.saveFile(f, aseoBano.getIdAseoBano(), "aseoBano");
            }
            aseoBano.setPathFoto(path);
            this.aseoBanoEJB.edit(aseoBano);
            archivos.clear();
        }
        consultar();
        MovilidadUtil.addSuccessMessage("Acción completada exitosamente.");
        MovilidadUtil.hideModal("aseo_bano_dialog_wv");
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
        String codigo_planilla = MovilidadUtil.codigoAseo();
        boolean ok = true;
        while (ok) {
            ok = aseoBanoEJB.findByFechaAndCodigo(fechaDesde, fechaHasta, codigo_planilla) != null;
        }
        aseoBano.setCodigoPlanilla(codigo_planilla);
        aseoBano.setAseoParamArea(aseoParamArea);
        aseoBano.setSstEmpresaVisitante(responsable);
        aseoBano.setCreado(MovilidadUtil.fechaCompletaHoy());
        aseoBano.setAutorizado(-1);
        aseoBano.setUsername(user != null ? user.getUsername() : aseoJSF.getResponsable().getNumeroDocumento());
        aseoBano.setEstadoReg(0);
        aseoBanoEJB.create(aseoBano);
        if (!archivos.isEmpty()) {
            String path = " ";
            for (UploadedFile f : archivos) {
                path = Util.saveFile(f, aseoBano.getIdAseoBano(), "aseoBano");
            }
            aseoBano.setPathFoto(path);
            this.aseoBanoEJB.edit(aseoBano);
            archivos.clear();
        }
        consultar();
        MovilidadUtil.addSuccessMessage("Acción completada exitosamente.");
        MovilidadUtil.openModal("codigo_plantilla_wv");
        MovilidadUtil.hideModal("aseo_bano_dialog_wv");
    }
    
    public void prepareLeerQR(int opc) {
        flagBusqueda = opc;
        MovilidadUtil.openModal("fotoDialog");
    }

    /**
     * Preparar variables para la edicion de un registro seleccionado.
     *
     * @param a
     */
    public void editar(AseoBano ab) {
        aseoBano = ab;
        archivos = new ArrayList<>();
        aseoParamArea = ab.getAseoParamArea();
        responsable = ab.getSstEmpresaVisitante();
        MovilidadUtil.openModal("aseo_bano_dialog_wv");
    }

    /**
     * Obtener lista de las rutas de cada una de las imagenes por registro
     *
     * @throws IOException
     */
    public void obtenerFotos() throws IOException {
        this.listFotos.clear();
        List<String> lstNombresImg = Util.getFileList(aseoBano.getIdAseoBano(), "aseoBano");
        
        for (String f : lstNombresImg) {
            f = aseoBano.getPathFoto() + f;
            listFotos.add(f);
        }
        fotoJSFManagedBean.setListFotos(listFotos);
    }
    
    public List<AseoBano> getListAseoBano() {
        return listAseoBano;
    }
    
    public void setListAseoBano(List<AseoBano> listAseoBano) {
        this.listAseoBano = listAseoBano;
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
    
    public AseoBano getAseoBano() {
        return aseoBano;
    }
    
    public void setAseoBano(AseoBano aseoBano) {
        this.aseoBano = aseoBano;
    }
    
    public SstEmpresaVisitanteFacadeLocal getSstEmpresaVisitanteEJB() {
        return sstEmpresaVisitanteEJB;
    }
    
    public void setSstEmpresaVisitanteEJB(SstEmpresaVisitanteFacadeLocal sstEmpresaVisitanteEJB) {
        this.sstEmpresaVisitanteEJB = sstEmpresaVisitanteEJB;
    }
    
    public SstEmpresaVisitante getResponsable() {
        return responsable;
    }
    
    public void setResponsable(SstEmpresaVisitante responsable) {
        this.responsable = responsable;
    }
    
    public AseoParamArea getAseoParamArea() {
        return aseoParamArea;
    }
    
    public void setAseoParamArea(AseoParamArea aseoParamArea) {
        this.aseoParamArea = aseoParamArea;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public List<String> getListFotos() {
        return listFotos;
    }
    
    public void setListFotos(List<String> listFotos) {
        this.listFotos = listFotos;
    }
    
    public boolean isB_controlAcciones() {
        return b_controlAcciones;
    }
    
    public void setB_controlAcciones(boolean b_controlAcciones) {
        this.b_controlAcciones = b_controlAcciones;
    }
    
    public String getQr_code() {
        return qr_code;
    }
    
    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }
    
}
