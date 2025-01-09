package com.movilidad.jsf;

import com.movilidad.ejb.PlaRecuNovedadFacadeLocal;
import com.movilidad.model.planificacion_recursos.PlaRecuNovedad;
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
import org.primefaces.event.TabChangeEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Omar.beltran
 */
@Named(value = "plaRecuNovedadBean")
@ViewScoped
public class PlaRecuNovedadBean implements Serializable {

    @EJB
    private PlaRecuNovedadFacadeLocal plaRecuNovedadEJB;

    private PlaRecuNovedad plaRecuNovedad;
    private String novedadSelected;//se emplea para comparar si el nombre del motivo se cambia en la función de editar
    private List<PlaRecuNovedad> listPlaRecuNovedad;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private boolean b_editar;

    @PostConstruct
    public void init() {
        b_editar = false;
        plaRecuNovedad = new PlaRecuNovedad();
        listPlaRecuNovedad = plaRecuNovedadEJB.findAll();
    }

    public PlaRecuNovedadBean() {
    }

    public void crear() {
        try {
            if (plaRecuNovedad != null) {
                if (plaRecuNovedadEJB.findByName(plaRecuNovedad.getNombre()) == null) {
                    plaRecuNovedad.setNombre(plaRecuNovedad.getNombre().toUpperCase());
                    plaRecuNovedad.setCreado(new Date());
                    plaRecuNovedad.setModificado(new Date());
                    plaRecuNovedad.setEstadoReg(0);
                    plaRecuNovedad.setUsernameCreate(user.getUsername());
                    plaRecuNovedadEJB.create(plaRecuNovedad);
                    MovilidadUtil.addSuccessMessage("Registro 'Novedad' creado");
                    plaRecuNovedad = new PlaRecuNovedad();
                    listPlaRecuNovedad = plaRecuNovedadEJB.findAll();
                } else {
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Novedad' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al crear registro 'Novedad'");
        }
    }

    public void editar() {
        try {
            if (plaRecuNovedad != null) {
                PlaRecuNovedad obj = plaRecuNovedadEJB.findByName(plaRecuNovedad.getNombre());
                if ((obj != null && obj.getNombre().equals(novedadSelected)) || obj == null) {
                    plaRecuNovedad.setNombre(plaRecuNovedad.getNombre().toUpperCase());
                    plaRecuNovedadEJB.edit(plaRecuNovedad);
                    MovilidadUtil.addSuccessMessage("Se ha actualizado registro 'Novedad'");
                    reset();
                    PrimeFaces.current().executeScript("PF('wvPlaRecuNovedad').hide();");
                } else {
                    listPlaRecuNovedad = plaRecuNovedadEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Novedad' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al editar registro 'Novedad'");
        }
    }

    public void editar(PlaRecuNovedad obj) throws Exception {
        this.plaRecuNovedad = obj;
        b_editar = true;
        novedadSelected = obj.getNombre();
        PrimeFaces.current().executeScript("PF('wvPlaRecuNovedad').show();");
    }

    public void prepareGuardar() {
        b_editar = false;
        plaRecuNovedad = new PlaRecuNovedad();
    }

    public void reset() {
        plaRecuNovedad = null;
    }

    public void onRowSelect(SelectEvent event) {
        plaRecuNovedad = (PlaRecuNovedad) event.getObject();
    }

    public PlaRecuNovedad getPlaRecuNovedad() {
        return plaRecuNovedad;
    }

    public void setPlaRecuNovedad(PlaRecuNovedad accViaSemaforo) {
        this.plaRecuNovedad = accViaSemaforo;
    }

    public List<PlaRecuNovedad> getListPlaRecuNovedad() {
        return listPlaRecuNovedad == null ? plaRecuNovedadEJB.findAll() : listPlaRecuNovedad;
    }

    public boolean isB_editar() {
        return b_editar;
    }

    public void setB_editar(boolean b_editar) {
        this.b_editar = b_editar;
    }

}
