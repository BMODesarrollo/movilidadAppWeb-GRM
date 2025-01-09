package com.movilidad.jsf;

import com.movilidad.ejb.PlaRecuProcesoFacadeLocal;
import com.movilidad.model.planificacion_recursos.PlaRecuProceso;
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
@Named(value = "plaRecuProcesoBean")
@ViewScoped
public class PlaRecuProcesoBean implements Serializable {

    @EJB
    private PlaRecuProcesoFacadeLocal plaRecuProcesoEJB;

    private PlaRecuProceso plaRecuProceso;
    private String procesoSelected;//se emplea para comparar si el nombre del motivo se cambia en la función de editar
    private List<PlaRecuProceso> listPlaRecuProceso;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private boolean b_editar;

    @PostConstruct
    public void init() {
        b_editar = false;
        plaRecuProceso = new PlaRecuProceso();
        listPlaRecuProceso = plaRecuProcesoEJB.findAll();
    }

    public PlaRecuProcesoBean() {
    }

    public void crear() {
        try {
            if (plaRecuProceso != null) {
                if (plaRecuProcesoEJB.findByName(plaRecuProceso.getProceso()) == null) {
                    plaRecuProceso.setProceso(plaRecuProceso.getProceso().toUpperCase());
                    plaRecuProceso.setCreado(new Date());
                    plaRecuProceso.setModificado(new Date());
                    plaRecuProceso.setEstadoReg(0);
                    plaRecuProceso.setUsernameCreate(user.getUsername());
                    plaRecuProcesoEJB.create(plaRecuProceso);
                    MovilidadUtil.addSuccessMessage("Registro 'Proceso' creado");
                    plaRecuProceso = new PlaRecuProceso();
                    listPlaRecuProceso = plaRecuProcesoEJB.findAll();
                } else {
                    listPlaRecuProceso = plaRecuProcesoEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Proceso' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al crear registro 'Proceso'");
        }
    }

    public void editar() {
        try {
            if (plaRecuProceso != null) {
                PlaRecuProceso obj = plaRecuProcesoEJB.findByName(plaRecuProceso.getProceso());
                if ((obj != null && obj.getProceso().equals(procesoSelected)) || obj == null) {
                    plaRecuProceso.setProceso(plaRecuProceso.getProceso().toUpperCase());
                    plaRecuProcesoEJB.edit(plaRecuProceso);
                    MovilidadUtil.addSuccessMessage("Se ha actualizado registro 'Proceso'");
                    reset();
                    PrimeFaces.current().executeScript("PF('wvPlaRecuProceso').hide();");
                } else {
                    listPlaRecuProceso = plaRecuProcesoEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Proceso' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al editar registro 'Proceso'");
        }
    }

    public void editar(PlaRecuProceso obj) throws Exception {
        this.plaRecuProceso = obj;
        b_editar = true;
        procesoSelected = obj.getProceso();
        PrimeFaces.current().executeScript("PF('wvPlaRecuProceso').show();");
    }

    public void prepareGuardar() {
        b_editar = false;
        plaRecuProceso = new PlaRecuProceso();
    }

    public void reset() {
        plaRecuProceso = null;
    }

    public void onRowSelect(SelectEvent event) {
        plaRecuProceso = (PlaRecuProceso) event.getObject();
    }

    public PlaRecuProceso getPlaRecuProceso() {
        return plaRecuProceso;
    }

    public void setPlaRecuProceso(PlaRecuProceso accViaSemaforo) {
        this.plaRecuProceso = accViaSemaforo;
    }

    public List<PlaRecuProceso> getListPlaRecuProceso() {
        return listPlaRecuProceso == null ? plaRecuProcesoEJB.findAll() : listPlaRecuProceso;
    }

    public boolean isB_editar() {
        return b_editar;
    }

    public void setB_editar(boolean b_editar) {
        this.b_editar = b_editar;
    }

}


