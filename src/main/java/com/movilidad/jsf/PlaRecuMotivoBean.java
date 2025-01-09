package com.movilidad.jsf;

import com.movilidad.ejb.PlaRecuMotivoFacadeLocal;
import com.movilidad.model.planificacion_recursos.PlaRecuMotivo;
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
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Omar.beltran
 */
@Named(value = "plaRecuMotivoBean")
@ViewScoped
public class PlaRecuMotivoBean implements Serializable {

    @EJB
    private PlaRecuMotivoFacadeLocal plaRecuMotivoEJB;

    private PlaRecuMotivo plaRecuMotivo;
    private String motivoSelected; //se emplea para comparar si el nombre del motivo se cambia en la función de editar
    private List<PlaRecuMotivo> listPlaRecuMotivo;
    
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private boolean b_editar;

    @PostConstruct
    public void init() {
        b_editar = false;
        plaRecuMotivo = new PlaRecuMotivo();
        listPlaRecuMotivo = plaRecuMotivoEJB.findAll();
    }

    public PlaRecuMotivoBean() {
    }

    public void crear() {
        try {
            if (plaRecuMotivo != null) {
                if (plaRecuMotivoEJB.findByName(plaRecuMotivo.getMotivo()) == null) {
                    plaRecuMotivo.setMotivo(plaRecuMotivo.getMotivo().toUpperCase());
                    plaRecuMotivo.setCreado(new Date());
                    plaRecuMotivo.setModificado(new Date());
                    plaRecuMotivo.setEstadoReg(0);
                    plaRecuMotivo.setUsernameCreate(user.getUsername());
                    plaRecuMotivoEJB.create(plaRecuMotivo);
                    MovilidadUtil.addSuccessMessage("Registro 'Motivo' creado");
                    plaRecuMotivo = new PlaRecuMotivo();
                    listPlaRecuMotivo = plaRecuMotivoEJB.findAll();
                } else {
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Motivo' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al crear registro 'Motivo'");
        }
    }

    public void editar() {
        try {
            if (plaRecuMotivo != null) {
                PlaRecuMotivo obj = plaRecuMotivoEJB.findByName(plaRecuMotivo.getMotivo());
                if ((obj != null && obj.getMotivo().equals(motivoSelected)) || obj == null) {
                    plaRecuMotivo.setMotivo(plaRecuMotivo.getMotivo().toUpperCase());
                    plaRecuMotivoEJB.edit(plaRecuMotivo);
                    MovilidadUtil.addSuccessMessage("Se ha actualizado registro 'Motivo'");
                    reset();
                    PrimeFaces.current().executeScript("PF('wvPlaRecuMotivo').hide();");
                } else {
                    listPlaRecuMotivo = plaRecuMotivoEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Motivo' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al editar registro 'Motivo'");
        }
    }

    public void editar(PlaRecuMotivo obj) throws Exception {
        this.plaRecuMotivo = obj;
        motivoSelected = obj.getMotivo();
        b_editar = true;
        PrimeFaces.current().executeScript("PF('wvPlaRecuMotivo').show();");
    }

    public void prepareGuardar() {
        b_editar = false;
        plaRecuMotivo = new PlaRecuMotivo();
    }

    public void reset() {
        plaRecuMotivo = null;
    }

    public void onRowSelect(SelectEvent event) {
        plaRecuMotivo = (PlaRecuMotivo) event.getObject();
    }

    public PlaRecuMotivo getPlaRecuMotivo() {
        return plaRecuMotivo;
    }

    public void setPlaRecuMotivo(PlaRecuMotivo accViaSemaforo) {
        this.plaRecuMotivo = accViaSemaforo;
    }

    public List<PlaRecuMotivo> getListPlaRecuMotivo() {
        return listPlaRecuMotivo == null ? plaRecuMotivoEJB.findAll() : listPlaRecuMotivo;
    }

    public boolean isB_editar() {
        return b_editar;
    }

    public void setB_editar(boolean b_editar) {
        this.b_editar = b_editar;
    }

}

