package com.movilidad.jsf;

import com.movilidad.ejb.PlaRecuEstadoFacadeLocal;
import com.movilidad.model.planificacion_recursos.PlaRecuEstado;
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
@Named(value = "plaRecuEstadoBean")
@ViewScoped
public class PlaRecuEstadoBean implements Serializable {

    @EJB
    private PlaRecuEstadoFacadeLocal plaRecuEstadoEJB;

    private PlaRecuEstado plaRecuEstado;
    private String estadoSelected;//se emplea para comparar si el nombre del motivo se cambia en la función de editar
    private List<PlaRecuEstado> listPlaRecuEstado;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private boolean b_editar;

    @PostConstruct
    public void init() {
        b_editar = false;
        plaRecuEstado = new PlaRecuEstado();
        listPlaRecuEstado = plaRecuEstadoEJB.findAll();
    }

    public PlaRecuEstadoBean() {
    }

    public void crear() {
        try {
            if (plaRecuEstado != null) {
                if (plaRecuEstadoEJB.findByName(plaRecuEstado.getEstado()) == null) {
                    plaRecuEstado.setEstado(plaRecuEstado.getEstado().toUpperCase());
                    plaRecuEstado.setCreado(new Date());
                    plaRecuEstado.setModificado(new Date());
                    plaRecuEstado.setEstadoReg(0);
                    plaRecuEstado.setUsernameCreate(user.getUsername());
                    plaRecuEstadoEJB.create(plaRecuEstado);
                    MovilidadUtil.addSuccessMessage("Registro 'Estado' creado");
                    plaRecuEstado = new PlaRecuEstado();
                    listPlaRecuEstado = plaRecuEstadoEJB.findAll();
                } else {
                    listPlaRecuEstado = plaRecuEstadoEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Estado' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al crear registro 'Estado'");
        }
    }

    public void editar() {
        try {
            if (plaRecuEstado != null) {
                PlaRecuEstado obj = plaRecuEstadoEJB.findByName(plaRecuEstado.getEstado());
                if ((obj != null && obj.getEstado().equals(estadoSelected)) || obj == null) {
                    plaRecuEstado.setEstado(plaRecuEstado.getEstado().toUpperCase());
                    plaRecuEstadoEJB.edit(plaRecuEstado);
                    MovilidadUtil.addSuccessMessage("Se ha actualizado registro 'Estado'");
                    reset();
                    PrimeFaces.current().executeScript("PF('wvPlaRecuEstado').hide();");
                } else {
                    listPlaRecuEstado = plaRecuEstadoEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Estado' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al editar registro 'Estado'");
        }
    }

    public void editar(PlaRecuEstado obj) throws Exception {
        this.plaRecuEstado = obj;
        b_editar = true;
        estadoSelected = obj.getEstado();
        PrimeFaces.current().executeScript("PF('wvPlaRecuEstado').show();");
    }

    public void prepareGuardar() {
        b_editar = false;
        plaRecuEstado = new PlaRecuEstado();
    }

    public void reset() {
        plaRecuEstado = null;
    }

    public void onRowSelect(SelectEvent event) {
        plaRecuEstado = (PlaRecuEstado) event.getObject();
    }

    public PlaRecuEstado getPlaRecuEstado() {
        return plaRecuEstado;
    }

    public void setPlaRecuEstado(PlaRecuEstado accViaSemaforo) {
        this.plaRecuEstado = accViaSemaforo;
    }

    public List<PlaRecuEstado> getListPlaRecuEstado() {
        return listPlaRecuEstado == null ? plaRecuEstadoEJB.findAll() : listPlaRecuEstado;
    }

    public boolean isB_editar() {
        return b_editar;
    }

    public void setB_editar(boolean b_editar) {
        this.b_editar = b_editar;
    }

}
