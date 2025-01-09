package com.movilidad.jsf;

import com.movilidad.ejb.PlaRecuRutaFacadeLocal;
import com.movilidad.model.planificacion_recursos.PlaRecuRuta;
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
@Named(value = "plaRecuRutaBean")
@ViewScoped
public class PlaRecuRutaBean implements Serializable {

    @EJB
    private PlaRecuRutaFacadeLocal plaRecuRutaEJB;

    private PlaRecuRuta plaRecuRuta;
    private String rutaSelected;//se emplea para comparar si el nombre del motivo se cambia en la función de editar
    private List<PlaRecuRuta> listPlaRecuRuta;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private boolean b_editar;

    @PostConstruct
    public void init() {
        b_editar = false;
        plaRecuRuta = new PlaRecuRuta();
        listPlaRecuRuta = plaRecuRutaEJB.findAll();
    }

    public PlaRecuRutaBean() {
    }

    public void crear() {
        try {
            if (plaRecuRuta != null) {
                if (plaRecuRutaEJB.findByRuta(plaRecuRuta.getRuta()) == null) {
                    plaRecuRuta.setRuta(plaRecuRuta.getRuta().toUpperCase());
                    plaRecuRuta.setCreado(new Date());
                    plaRecuRuta.setModificado(new Date());
                    plaRecuRuta.setEstadoReg(0);
                    plaRecuRuta.setUsernameCreate(user.getUsername());
                    plaRecuRutaEJB.create(plaRecuRuta);
                    MovilidadUtil.addSuccessMessage("Registro 'Ruta' creado");
                    plaRecuRuta = new PlaRecuRuta();
                    listPlaRecuRuta = plaRecuRutaEJB.findAll();
                } else {
                    listPlaRecuRuta = plaRecuRutaEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Ruta' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al crear registro 'Ruta'");
        }
    }

    public void editar() {
        try {
            if (plaRecuRuta != null) {
                PlaRecuRuta obj = plaRecuRutaEJB.findByRuta(plaRecuRuta.getRuta());
                if ((obj != null && obj.getRuta().equals(rutaSelected)) || obj == null) {
                    plaRecuRuta.setRuta(plaRecuRuta.getRuta().toUpperCase());
                    plaRecuRutaEJB.edit(plaRecuRuta);
                    MovilidadUtil.addSuccessMessage("Se ha actualizado registro 'Ruta'");
                    reset();
                    PrimeFaces.current().executeScript("PF('wvPlaRecuRuta').hide();");
                } else {
                    listPlaRecuRuta = plaRecuRutaEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Ruta' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al editar registro 'Ruta'");
        }
    }

    public void editar(PlaRecuRuta obj) throws Exception {
        this.plaRecuRuta = obj;
        b_editar = true;
        rutaSelected = obj.getRuta();
        PrimeFaces.current().executeScript("PF('wvPlaRecuRuta').show();");
    }

    public void prepareGuardar() {
        b_editar = false;
        plaRecuRuta = new PlaRecuRuta();
    }

    public void reset() {
        plaRecuRuta = null;
    }

    public void onRowSelect(SelectEvent event) {
        plaRecuRuta = (PlaRecuRuta) event.getObject();
    }

    public PlaRecuRuta getPlaRecuRuta() {
        return plaRecuRuta;
    }

    public void setPlaRecuRuta(PlaRecuRuta accViaSemaforo) {
        this.plaRecuRuta = accViaSemaforo;
    }

    public List<PlaRecuRuta> getListPlaRecuRuta() {
        return listPlaRecuRuta == null ? plaRecuRutaEJB.findAll() : listPlaRecuRuta;
    }

    public boolean isB_editar() {
        return b_editar;
    }

    public void setB_editar(boolean b_editar) {
        this.b_editar = b_editar;
    }

}

