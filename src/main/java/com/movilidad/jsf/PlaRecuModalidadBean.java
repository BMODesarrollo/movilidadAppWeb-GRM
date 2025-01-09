package com.movilidad.jsf;

import com.movilidad.ejb.PlaRecuModalidadFacadeLocal;
import com.movilidad.model.planificacion_recursos.PlaRecuModalidad;
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
@Named(value = "plaRecuModalidadBean")
@ViewScoped
public class PlaRecuModalidadBean implements Serializable {

    @EJB
    private PlaRecuModalidadFacadeLocal plaRecuModalidadEJB;

    private PlaRecuModalidad plaRecuModalidad;
    private String modalidadSelected;//se emplea para comparar si el nombre del motivo se cambia en la función de editar
    private List<PlaRecuModalidad> listPlaRecuModalidad;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private boolean b_editar;

    @PostConstruct
    public void init() {
        b_editar = false;
        plaRecuModalidad = new PlaRecuModalidad();
        listPlaRecuModalidad = plaRecuModalidadEJB.findAll();
    }

    public PlaRecuModalidadBean() {
    }

    public void crear() {
        try {
            if (plaRecuModalidad != null) {
                if (plaRecuModalidadEJB.findByName(plaRecuModalidad.getModalidad()) == null) {
                    plaRecuModalidad.setModalidad(plaRecuModalidad.getModalidad().toUpperCase());
                    plaRecuModalidad.setCreado(new Date());
                    plaRecuModalidad.setModificado(new Date());
                    plaRecuModalidad.setEstadoReg(0);
                    plaRecuModalidad.setUsernameCreate(user.getUsername());
                    plaRecuModalidadEJB.create(plaRecuModalidad);
                    MovilidadUtil.addSuccessMessage("Registro 'Modalidad' creado");
                    plaRecuModalidad = new PlaRecuModalidad();
                    listPlaRecuModalidad = plaRecuModalidadEJB.findAll();
                } else {
                    listPlaRecuModalidad = plaRecuModalidadEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Modalidad' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al crear registro 'Modalidad'");
        }
    }

    public void editar() {
        try {
            if (plaRecuModalidad != null) {
                PlaRecuModalidad obj = plaRecuModalidadEJB.findByName(plaRecuModalidad.getModalidad());
                if ((obj != null && obj.getModalidad().equals(modalidadSelected)) || obj == null) {
                    plaRecuModalidad.setModalidad(plaRecuModalidad.getModalidad().toUpperCase());
                    plaRecuModalidadEJB.edit(plaRecuModalidad);
                    MovilidadUtil.addSuccessMessage("Se ha actualizado registro 'Modalidad'");
                    reset();
                    PrimeFaces.current().executeScript("PF('wvPlaRecuModalidad').hide();");
                } else {
                    listPlaRecuModalidad = plaRecuModalidadEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Modalidad' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al editar registro 'Modalidad'");
        }
    }

    public void editar(PlaRecuModalidad obj) throws Exception {
        this.plaRecuModalidad = obj;
        b_editar = true;
        modalidadSelected = obj.getModalidad();
        PrimeFaces.current().executeScript("PF('wvPlaRecuModalidad').show();");
    }

    public void prepareGuardar() {
        b_editar = false;
        plaRecuModalidad = new PlaRecuModalidad();
    }

    public void reset() {
        plaRecuModalidad = null;
    }

    public void onRowSelect(SelectEvent event) {
        plaRecuModalidad = (PlaRecuModalidad) event.getObject();
    }

    public PlaRecuModalidad getPlaRecuModalidad() {
        return plaRecuModalidad;
    }

    public void setPlaRecuModalidad(PlaRecuModalidad accViaSemaforo) {
        this.plaRecuModalidad = accViaSemaforo;
    }

    public List<PlaRecuModalidad> getListPlaRecuModalidad() {
        return listPlaRecuModalidad == null ? plaRecuModalidadEJB.findAll() : listPlaRecuModalidad;
    }

    public boolean isB_editar() {
        return b_editar;
    }

    public void setB_editar(boolean b_editar) {
        this.b_editar = b_editar;
    }

}

