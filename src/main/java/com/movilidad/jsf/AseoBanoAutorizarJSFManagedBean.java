/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.google.zxing.NotFoundException;
import com.movilidad.ejb.AseoBanoFacadeLocal;
import com.movilidad.ejb.AseoParamAreaFacadeLocal;
import com.movilidad.model.AseoBano;
import com.movilidad.model.AseoParamArea;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.awt.Image;
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
import org.primefaces.event.CaptureEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "aseoBanoAutorizarBean")
@ViewScoped
public class AseoBanoAutorizarJSFManagedBean implements Serializable {

    /**
     * Creates a new instance of AseoBanoAutorizarJSFManagedBean
     */
    public AseoBanoAutorizarJSFManagedBean() {
    }

    @EJB
    private AseoBanoFacadeLocal aseoBanoEJB;

    @EJB
    private AseoParamAreaFacadeLocal aseoParamAreaEJB;

    @Inject
    private AseoLoginJSF aseoJSF;
    @Inject
    private UploadFotoJSFManagedBean fotoJSFManagedBean;
    private List<AseoBano> listAseoBano;
    private List<String> listFotos = new ArrayList<>();

    private AseoBano aseoBano;
    private AseoParamArea aseoParamArea;

    private boolean b_controlAcciones;
    private String qr_code;

    private Date fechaDesde = MovilidadUtil.fechaHoy();
    private Date fechaHasta = MovilidadUtil.fechaHoy();

    UserExtended user;

    public void consultar() {
        listAseoBano = aseoBanoEJB.findByAreaPendiente(aseoParamArea.getIdAseoParamArea());
        if (listAseoBano.isEmpty()) {
            MovilidadUtil.addAdvertenciaMessage("No hay resitros pendientes.");
        }
    }

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
//        consultar();
        b_controlAcciones = user != null;
    }

    public void autorizar(AseoBano ab, int opc) {
        ab.setModificado(MovilidadUtil.fechaCompletaHoy());
        ab.setFechaAutoriza(MovilidadUtil.fechaCompletaHoy());
        ab.setUserAutoriza(user != null ? user.getUsername() : aseoJSF.getNumDocAndNombre());

        ab.setAutorizado(opc);
        aseoBanoEJB.edit(ab);
        MovilidadUtil.addSuccessMessage("Acción completada con exito.");
    }

    public void obtenerFotos() throws IOException {
        this.listFotos.clear();
        List<String> lstNombresImg = Util.getFileList(aseoBano.getIdAseoBano(), "aseoBano");

        for (String f : lstNombresImg) {
            f = aseoBano.getPathFoto() + f;
            listFotos.add(f);
        }
        fotoJSFManagedBean.setListFotos(listFotos);

    }

    public void procesarValorQr() throws NotFoundException {
        if (qr_code.equals("")) {
            MovilidadUtil.addErrorMessage("No se detectó un código QR, intente nuevamente.");
        } else {
            aseoParamArea = aseoParamAreaEJB.findByHashString(qr_code);
            if (aseoParamArea == null) {
                MovilidadUtil.addErrorMessage("No existe registro con el codigo leido.");
            } else {
                consultar();
            }
        }
    }

    public void prepareLeerQR() {
        MovilidadUtil.openModal("fotoDialog");
    }

    public List<AseoBano> getListAseoBano() {
        return listAseoBano;
    }

    public void setListAseoBano(List<AseoBano> listAseoBano) {
        this.listAseoBano = listAseoBano;
    }

    public boolean isB_controlAcciones() {
        return b_controlAcciones;
    }

    public void setB_controlAcciones(boolean b_controlAcciones) {
        this.b_controlAcciones = b_controlAcciones;
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

    public List<String> getListFotos() {
        return listFotos;
    }

    public void setListFotos(List<String> listFotos) {
        this.listFotos = listFotos;
    }

    public AseoBano getAseoBano() {
        return aseoBano;
    }

    public void setAseoBano(AseoBano aseoBano) {
        this.aseoBano = aseoBano;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

}
