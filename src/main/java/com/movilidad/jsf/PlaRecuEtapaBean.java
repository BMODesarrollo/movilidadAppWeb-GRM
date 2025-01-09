package com.movilidad.jsf;

import com.movilidad.ejb.PlaRecuProcesoProFacadeLocal;
import com.movilidad.ejb.PlaRecuProcesoProDetFacadeLocal;
import com.movilidad.model.planificacion_recursos.PlaRecuProcesoPro;
import com.movilidad.model.planificacion_recursos.PlaRecuProcesoProDet;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Omar.beltran
 */
@Named(value = "plaRecuEtapaBean")
@ViewScoped
public class PlaRecuEtapaBean implements Serializable {

    @EJB
    private PlaRecuProcesoProFacadeLocal plaRecuEtapaEJB;
    @EJB
    private PlaRecuProcesoProDetFacadeLocal plaRecuEtapaDetEJB;

    private PlaRecuProcesoPro plaRecuEtapa;
    private PlaRecuProcesoProDet plaRecuEtapaDet;
    private String etapaSelected;//se emplea para comparar si el nombre del motivo se cambia en la función de editar
    private String etapaDetSelected;
    private List<PlaRecuProcesoPro> listPlaRecuEtapa;
    private List<PlaRecuProcesoProDet> listPlaRecuEtapaDet;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private boolean b_editar;
    private boolean b_editarDet;

    @PostConstruct
    public void init() {
        b_editar = false;
        b_editarDet = false;
        plaRecuEtapa = new PlaRecuProcesoPro();
        plaRecuEtapaDet = new PlaRecuProcesoProDet();
        plaRecuEtapaDet.setIdPlaRecuProcesoPro(new PlaRecuProcesoPro());
        listPlaRecuEtapa = plaRecuEtapaEJB.findAll();
        listPlaRecuEtapaDet = plaRecuEtapaDetEJB.findAll();
    }

    public PlaRecuEtapaBean() {
    }

    public void crear() {
        try {
            if (plaRecuEtapa != null) {
                if (plaRecuEtapaEJB.findByDescripcion(plaRecuEtapa.getDescripcion()) == null) {
                    plaRecuEtapa.setCreado(new Date());
                    plaRecuEtapa.setModificado(new Date());
                    plaRecuEtapa.setEstadoReg(0);
                    plaRecuEtapa.setUsernameCreate(user.getUsername());
                    plaRecuEtapaEJB.create(plaRecuEtapa);
                    MovilidadUtil.addSuccessMessage("Registro 'Etapa' creado");
                    plaRecuEtapa = new PlaRecuProcesoPro();
                    listPlaRecuEtapa = plaRecuEtapaEJB.findAll();
                } else {
                    listPlaRecuEtapa = plaRecuEtapaEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Etapa' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al crear registro 'Etapa'");
        }
    }
    
    public void crearDet() {
        try {
            if (plaRecuEtapaDet != null) {
                if (plaRecuEtapaDetEJB.findByDescripcion(plaRecuEtapaDet.getDescripcion()) == null) {
                    plaRecuEtapaDet.setCreado(new Date());
                    plaRecuEtapaDet.setModificado(new Date());
                    plaRecuEtapaDet.setIsChecked(false);
                    plaRecuEtapaDet.setEstadoReg(0);
                    plaRecuEtapaDet.setUsernameCreate(user.getUsername());
                    plaRecuEtapaDetEJB.create(plaRecuEtapaDet);
                    MovilidadUtil.addSuccessMessage("Registro 'Etapa Detalle' creado");
                    plaRecuEtapaDet = new PlaRecuProcesoProDet();
                    plaRecuEtapaDet.setIdPlaRecuProcesoPro(new PlaRecuProcesoPro());
                    listPlaRecuEtapaDet = plaRecuEtapaDetEJB.findAll();
                } else {
                    listPlaRecuEtapaDet = plaRecuEtapaDetEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Etapa Detalle' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al crear registro 'Etapa Detalle'");
        }
    }

    public void editar() {
        try {
            if (plaRecuEtapa != null) {
                PlaRecuProcesoPro obj = plaRecuEtapaEJB.findByDescripcion(plaRecuEtapa.getDescripcion());
                if ((obj != null && obj.getDescripcion().equals(etapaSelected)) || obj == null) {
                    plaRecuEtapaEJB.edit(plaRecuEtapa);
                    MovilidadUtil.addSuccessMessage("Se ha actualizado registro 'Etapa'");
                    reset();
                    PrimeFaces.current().executeScript("PF('wvPlaRecuEtapa').hide();");
                } else {
                    listPlaRecuEtapa = plaRecuEtapaEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Etapa' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al editar registro 'Etapa'");
        }
    }
    
    public void editarDet() {
        try {
            if (plaRecuEtapaDet != null) {
                PlaRecuProcesoProDet obj = plaRecuEtapaDetEJB.findByDescripcion(plaRecuEtapaDet.getDescripcion());
                if ((obj != null && obj.getDescripcion().equals(etapaDetSelected)) || obj == null) {
                    plaRecuEtapaDetEJB.edit(plaRecuEtapaDet);
                    MovilidadUtil.addSuccessMessage("Se ha actualizado registro 'Etapa Detalle'");
                    resetDet();
                    PrimeFaces.current().executeScript("PF('wvPlaRecuEtapaDet').hide();");
                } else {
                    listPlaRecuEtapaDet = plaRecuEtapaDetEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Etapa Detalle' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al editar registro 'Etapa'");
        }
    }

    public void editar(PlaRecuProcesoPro obj) throws Exception {
        this.plaRecuEtapa = obj;
        b_editar = true;
        etapaSelected = obj.getDescripcion();
        PrimeFaces.current().executeScript("PF('wvPlaRecuEtapa').show();");
    }
    
    public void editarDet(PlaRecuProcesoProDet obj) throws Exception {
        this.plaRecuEtapaDet = obj;
        b_editarDet = true;
        etapaDetSelected = obj.getDescripcion();
        PrimeFaces.current().executeScript("PF('wvPlaRecuEtapaDet').show();");
    }

    public void prepareGuardar() {
        b_editar = false;
        plaRecuEtapa = new PlaRecuProcesoPro();
    }
    
    public void prepareGuardarDet() {
        b_editarDet = false;
        plaRecuEtapaDet = new PlaRecuProcesoProDet();
        plaRecuEtapaDet.setIdPlaRecuProcesoPro(new PlaRecuProcesoPro());
    }

    public void reset() {
        plaRecuEtapa = null;
    }
    
    public void resetDet() {
        //plaRecuEtapaDet = null;
    }

    public PlaRecuProcesoPro getPlaRecuEtapa() {
        return plaRecuEtapa;
    }

    public void setPlaRecuEtapa(PlaRecuProcesoPro accViaSemaforo) {
        this.plaRecuEtapa = accViaSemaforo;
    }

    public List<PlaRecuProcesoPro> getListPlaRecuEtapa() {
        return listPlaRecuEtapa == null ? plaRecuEtapaEJB.findAll() : listPlaRecuEtapa;
    }
    
    public PlaRecuProcesoProDet getPlaRecuEtapaDet() {
        return plaRecuEtapaDet;
    }

    public void setPlaRecuEtapaDet(PlaRecuProcesoProDet accViaSemaforo) {
        this.plaRecuEtapaDet = accViaSemaforo;
    }

    public List<PlaRecuProcesoProDet> getListPlaRecuEtapaDet() {
        return listPlaRecuEtapaDet == null ? plaRecuEtapaDetEJB.findAll() : listPlaRecuEtapaDet;
    }

    public boolean isB_editar() {
        return b_editar;
    }

    public void setB_editar(boolean b_editar) {
        this.b_editar = b_editar;
    }
    
    public boolean isB_editarDet() {
        return b_editarDet;
    }

    public void setB_editarDet(boolean b_editarDet) {
        this.b_editarDet = b_editarDet;
    }

}

