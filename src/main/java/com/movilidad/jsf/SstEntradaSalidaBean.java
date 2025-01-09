package com.movilidad.jsf;

import com.movilidad.ejb.SstAutorizacionFacadeLocal;
import com.movilidad.ejb.SstEsMatEquiFacadeLocal;
import com.movilidad.model.SstAutorizacion;
import com.movilidad.model.SstEsMatEqui;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "sstEntradaSalidaBean")
@ViewScoped
public class SstEntradaSalidaBean implements Serializable {

    @EJB
    private SstAutorizacionFacadeLocal autorizacionEjb;
    @EJB
    private SstEsMatEquiFacadeLocal esMatEquiEjb;

    private SstAutorizacion autorizacion;
    private Date fecha;
    private String cedula;

    private void nuevo() {
        fecha = null;
        cedula = "";
        autorizacion = null;
    }

    /**
     * Autoriza entrada de materiales/herramientas y actualiza su estado en el
     * listado de autorizaciones
     */
    public void autorizarEntrada() {
        autorizacion.setIngreso(1);
        autorizacion.setVisitaActiva(1);
        autorizacionEjb.edit(autorizacion);

        SstEsMatEqui sstEsMatEqui = autorizacion.getIdSstEsMatEqui();

        if (sstEsMatEqui != null) {
            sstEsMatEqui.setEstado(0);
            esMatEquiEjb.edit(sstEsMatEqui);
        }
        nuevo();
        MovilidadUtil.addSuccessMessage("Entrada aprobada éxitosamente");

    }

    /**
     * Autoriza entrada de materiales/herramientas y actualiza su estado en el
     * listado de autorizaciones
     */
    public void autorizarSalida() {
        autorizacion.setSalio(1);
        autorizacion.setVisitaActiva(0);
        autorizacionEjb.edit(autorizacion);

        SstEsMatEqui sstEsMatEqui = autorizacion.getIdSstEsMatEqui();

        if (sstEsMatEqui != null) {
            sstEsMatEqui.setEstado(1);
            esMatEquiEjb.edit(sstEsMatEqui);
        }

        nuevo();
        MovilidadUtil.addSuccessMessage("Salida aprobada éxitosamente");

    }

    public void consultarAutorizacion() {
        autorizacion = autorizacionEjb.obtenerAutorizacionEntradaSalida(fecha, cedula);

        if (autorizacion == null) {
            MovilidadUtil.addErrorMessage("El visitante NO se encuentra AUTORIZADO");
        }
    }

    public SstAutorizacion getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(SstAutorizacion autorizacion) {
        this.autorizacion = autorizacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

}
