package com.movilidad.jsf;

import com.movilidad.ejb.PlaRecuTurnoFacadeLocal;
import com.movilidad.model.planificacion_recursos.PlaRecuTurno;
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
@Named(value = "plaRecuTurnoBean")
@ViewScoped
public class PlaRecuTurnoBean implements Serializable {

    @EJB
    private PlaRecuTurnoFacadeLocal plaRecuTurnoEJB;

    private PlaRecuTurno plaRecuTurno;
    private String turnoSelected;//se emplea para comparar si el nombre del motivo se cambia en la función de editar
    private List<PlaRecuTurno> listPlaRecuTurno;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private boolean b_editar;

    @PostConstruct
    public void init() {
        b_editar = false;
        plaRecuTurno = new PlaRecuTurno();
        listPlaRecuTurno = plaRecuTurnoEJB.findAll();
    }

    public PlaRecuTurnoBean() {
    }

    public void crear() {
        try {
            if (plaRecuTurno != null) {
                if (plaRecuTurnoEJB.findByName(plaRecuTurno.getTurno()) == null) {
                    plaRecuTurno.setTurno(plaRecuTurno.getTurno().toUpperCase());
                    plaRecuTurno.setCreado(new Date());
                    plaRecuTurno.setModificado(new Date());
                    plaRecuTurno.setEstadoReg(0);
                    plaRecuTurno.setUsernameCreate(user.getUsername());
                    plaRecuTurnoEJB.create(plaRecuTurno);
                    MovilidadUtil.addSuccessMessage("Registro 'Turno' creado");
                    plaRecuTurno = new PlaRecuTurno();
                    listPlaRecuTurno = plaRecuTurnoEJB.findAll();
                } else {
                    listPlaRecuTurno = plaRecuTurnoEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Turno' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al crear registro 'Turno'");
        }
    }

    public void editar() {
        try {
            if (plaRecuTurno != null) {
                PlaRecuTurno obj = plaRecuTurnoEJB.findByName(plaRecuTurno.getTurno());
                if ((obj != null && obj.getTurno().equals(turnoSelected)) || obj == null) {
                    plaRecuTurno.setTurno(plaRecuTurno.getTurno().toUpperCase());
                    plaRecuTurnoEJB.edit(plaRecuTurno);
                    MovilidadUtil.addSuccessMessage("Se ha actualizado registro 'Turno'");
                    reset();
                    PrimeFaces.current().executeScript("PF('wvPlaRecuTurno').hide();");
                } else {
                    listPlaRecuTurno = plaRecuTurnoEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Turno' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al editar registro 'Turno'");
        }
    }

    public void editar(PlaRecuTurno obj) throws Exception {
        this.plaRecuTurno = obj;
        b_editar = true;
        turnoSelected = obj.getTurno();
        PrimeFaces.current().executeScript("PF('wvPlaRecuTurno').show();");
    }

    public void prepareGuardar() {
        b_editar = false;
        plaRecuTurno = new PlaRecuTurno();
    }

    public void reset() {
        plaRecuTurno = null;
    }

    public void onRowSelect(SelectEvent event) {
        plaRecuTurno = (PlaRecuTurno) event.getObject();
    }

    public PlaRecuTurno getPlaRecuTurno() {
        return plaRecuTurno;
    }

    public void setPlaRecuTurno(PlaRecuTurno accViaSemaforo) {
        this.plaRecuTurno = accViaSemaforo;
    }

    public List<PlaRecuTurno> getListPlaRecuTurno() {
        return listPlaRecuTurno == null ? plaRecuTurnoEJB.findAll() : listPlaRecuTurno;
    }

    public boolean isB_editar() {
        return b_editar;
    }

    public void setB_editar(boolean b_editar) {
        this.b_editar = b_editar;
    }

}

