package com.movilidad.jsf;

import com.movilidad.ejb.PlaRecuLugarFacadeLocal;
import com.movilidad.model.planificacion_recursos.PlaRecuLugar;
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
@Named(value = "plaRecuLugarBean")
@ViewScoped
public class PlaRecuLugarBean implements Serializable {

    @EJB
    private PlaRecuLugarFacadeLocal plaRecuLugarEJB;

    private PlaRecuLugar plaRecuLugar;
    private String lugarSelected;//se emplea para comparar si el nombre del motivo se cambia en la función de editar
    private List<PlaRecuLugar> listPlaRecuLugar;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private boolean b_editar;

    @PostConstruct
    public void init() {
        b_editar = false;
        plaRecuLugar = new PlaRecuLugar();
        listPlaRecuLugar = plaRecuLugarEJB.findAll();
    }

    public PlaRecuLugarBean() {
    }

    public void crear() {
        try {
            if (plaRecuLugar != null) {
                if (plaRecuLugarEJB.findByLugar(plaRecuLugar.getLugar()) == null) {
                    plaRecuLugar.setLugar(plaRecuLugar.getLugar().toUpperCase());
                    plaRecuLugar.setCreado(new Date());
                    plaRecuLugar.setModificado(new Date());
                    plaRecuLugar.setEstadoReg(0);
                    plaRecuLugar.setUsernameCreate(user.getUsername());
                    plaRecuLugarEJB.create(plaRecuLugar);
                    MovilidadUtil.addSuccessMessage("Registro 'Lugar' creado");
                    plaRecuLugar = new PlaRecuLugar();
                    listPlaRecuLugar = plaRecuLugarEJB.findAll();
                } else {
                    listPlaRecuLugar = plaRecuLugarEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Lugar' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al crear registro 'Lugar'");
        }
    }

    public void editar() {
        try {
            if (plaRecuLugar != null) {
                PlaRecuLugar obj = plaRecuLugarEJB.findByLugar(plaRecuLugar.getLugar());
                if ((obj != null && obj.getLugar().equals(lugarSelected)) || obj == null) {
                    plaRecuLugar.setLugar(plaRecuLugar.getLugar().toUpperCase());
                    plaRecuLugarEJB.edit(plaRecuLugar);
                    MovilidadUtil.addSuccessMessage("Se ha actualizado registro 'Lugar'");
                    reset();
                    PrimeFaces.current().executeScript("PF('wvPlaRecuLugar').hide();");
                } else {
                    listPlaRecuLugar = plaRecuLugarEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Lugar' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al editar registro 'Lugar'");
        }
    }

    public void editar(PlaRecuLugar obj) throws Exception {
        this.plaRecuLugar = obj;
        b_editar = true;
        lugarSelected = obj.getLugar();
        PrimeFaces.current().executeScript("PF('wvPlaRecuLugar').show();");
    }

    public void prepareGuardar() {
        b_editar = false;
        plaRecuLugar = new PlaRecuLugar();
    }

    public void reset() {
        plaRecuLugar = null;
    }

    public void onRowSelect(SelectEvent event) {
        plaRecuLugar = (PlaRecuLugar) event.getObject();
    }

    public PlaRecuLugar getPlaRecuLugar() {
        return plaRecuLugar;
    }

    public void setPlaRecuLugar(PlaRecuLugar accViaSemaforo) {
        this.plaRecuLugar = accViaSemaforo;
    }

    public List<PlaRecuLugar> getListPlaRecuLugar() {
        return listPlaRecuLugar == null ? plaRecuLugarEJB.findAll() : listPlaRecuLugar;
    }

    public boolean isB_editar() {
        return b_editar;
    }

    public void setB_editar(boolean b_editar) {
        this.b_editar = b_editar;
    }

}
