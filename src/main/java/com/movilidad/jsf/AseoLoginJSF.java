/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.SstEmpresaVisitanteFacadeLocal;
import com.movilidad.model.SstEmpresaVisitante;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;

/**
 *
 * @author solucionesit
 */
@Named(value = "aseoLoginJSF")
@ViewScoped
public class AseoLoginJSF implements Serializable {

    /**
     * Creates a new instance of AseoLoginJSF
     */
    public AseoLoginJSF() {
    }

    @EJB
    private SstEmpresaVisitanteFacadeLocal sstEmpresaVisitanteEJB;

    private String documento;
    private String qrToken;
    private String qr_code;
    private String loadGif = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO);
    private boolean flag;
    private SstEmpresaVisitante responsable;

    public void procesarValorQr() {
        if (qr_code.equals("")) {
            MovilidadUtil.addErrorMessage("No se detectó un código QR, intente nuevamente.");
        } else {
            responsable = sstEmpresaVisitanteEJB.findByHashString(qr_code);
            if (responsable == null) {
                MovilidadUtil.addErrorMessage("No existe registro con el codigo leido.");
            } else {
                MovilidadUtil.hideModal("fotoDialog");
                PrimeFaces.current().executeScript("sessionStorage.setItem('code_aseo', '" + responsable.getHashString() + "');");
                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                String name = Util.getUrlContext(request) + "/public/aseo/aseosVarios/maestroAseo.jsf";

                PrimeFaces.current().executeScript("location.href = '" + name + "'");

            }
            MovilidadUtil.updateComponent("msgs");
            MovilidadUtil.hideModal("fotoDialog");

        }
    }

    /**
     * Permite obtener el token que se encuentra en el localStorage y validarlo
     * en el sistema
     */
    public void obtenerToken() {
        try {
            if (qrToken != null && !qrToken.isEmpty()) {
                responsable = sstEmpresaVisitanteEJB.findByHashString(qrToken);
                if (responsable != null) {
                    flag = true;
                    return;
                }
            }
            reset();
        } catch (Exception e) {
            reset();
        }
    }

    public boolean flag_supervisor() {
        return responsable.getSupervisor() == 1;
    }

    public String getNumDocAndNombre() {
        return responsable.getNumeroDocumento() + " " + responsable.getNombre() + " " + responsable.getApellidos();
    }

    public void reset() {
        qrToken = null;
        flag = false;
        responsable = null;
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String name = Util.getUrlContext(request) + "/public/aseo/login.jsf";
        PrimeFaces.current().executeScript("localStorage.removeItem('code_aseo')");
        PrimeFaces.current().executeScript("location.href = '" + name + "'");
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getQrToken() {
        return qrToken;
    }

    public void setQrToken(String qrToken) {
        this.qrToken = qrToken;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public SstEmpresaVisitante getResponsable() {
        return responsable;
    }

    public void setResponsable(SstEmpresaVisitante responsable) {
        this.responsable = responsable;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getLoadGif() {
        loadGif = SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO);
        System.out.println("loadGif" + loadGif);
        return loadGif;
    }

    public void setLoadGif(String loadGif) {
        this.loadGif = loadGif;
    }

}
