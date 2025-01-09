package com.movilidad.jsf;

import com.movilidad.ejb.PlaRecuConduccionFacadeLocal;
import com.movilidad.model.planificacion_recursos.PlaRecuConduccion;
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
@Named(value = "plaRecuConduccionBean")
@ViewScoped
public class PlaRecuConduccionBean implements Serializable {

    @EJB
    private PlaRecuConduccionFacadeLocal plaRecuConduccionEJB;

    private PlaRecuConduccion plaRecuConduccion;
    private String conduccionSelected;//se emplea para comparar si el nombre del motivo se cambia en la función de editar
    private List<PlaRecuConduccion> listPlaRecuConduccion;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private boolean b_editar;

    @PostConstruct
    public void init() {
        b_editar = false;
        plaRecuConduccion = new PlaRecuConduccion();
        listPlaRecuConduccion = plaRecuConduccionEJB.findAll();
    }

    public PlaRecuConduccionBean() {
    }

    public void crear() {
        try {
            if (plaRecuConduccion != null) {
                if (plaRecuConduccionEJB.findByDescripcion(plaRecuConduccion.getDescripcion()) == null) {
                    plaRecuConduccion.setDescripcion(plaRecuConduccion.getDescripcion().toUpperCase());
                    plaRecuConduccion.setCreado(new Date());
                    plaRecuConduccion.setModificado(new Date());
                    plaRecuConduccion.setEstadoReg(0);
                    plaRecuConduccion.setUsernameCreate(user.getUsername());
                    plaRecuConduccionEJB.create(plaRecuConduccion);
                    MovilidadUtil.addSuccessMessage("Registro 'Conducción' creado");
                    plaRecuConduccion = new PlaRecuConduccion();
                    listPlaRecuConduccion = plaRecuConduccionEJB.findAll();
                } else {
                    listPlaRecuConduccion = plaRecuConduccionEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Conducción' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al crear registro 'Conducción'");
        }
    }

    public void editar() {
        try {
            if (plaRecuConduccion != null) {
                PlaRecuConduccion obj = plaRecuConduccionEJB.findByDescripcion(plaRecuConduccion.getDescripcion());
                if ((obj != null && obj.getDescripcion().equals(conduccionSelected)) || obj == null) {
                    plaRecuConduccion.setDescripcion(plaRecuConduccion.getDescripcion().toUpperCase());
                    plaRecuConduccionEJB.edit(plaRecuConduccion);
                    MovilidadUtil.addSuccessMessage("Se ha actualizado registro 'Conduccion'");
                    reset();
                    PrimeFaces.current().executeScript("PF('wvPlaRecuConduccion').hide();");
                } else {
                    listPlaRecuConduccion = plaRecuConduccionEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Conducción' con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al editar registro 'Conducción'");
        }
    }

    public void editar(PlaRecuConduccion obj) throws Exception {
        this.plaRecuConduccion = obj;
        b_editar = true;
        conduccionSelected = obj.getDescripcion();
        PrimeFaces.current().executeScript("PF('wvPlaRecuConduccion').show();");
    }

    public void prepareGuardar() {
        b_editar = false;
        plaRecuConduccion = new PlaRecuConduccion();
    }

    public void reset() {
        plaRecuConduccion = null;
    }

    public void onRowSelect(SelectEvent event) {
        plaRecuConduccion = (PlaRecuConduccion) event.getObject();
    }

    public PlaRecuConduccion getPlaRecuConduccion() {
        return plaRecuConduccion;
    }

    public void setPlaRecuConduccion(PlaRecuConduccion accViaSemaforo) {
        this.plaRecuConduccion = accViaSemaforo;
    }

    public List<PlaRecuConduccion> getListPlaRecuConduccion() {
        return listPlaRecuConduccion == null ? plaRecuConduccionEJB.findAll() : listPlaRecuConduccion;
    }

    public boolean isB_editar() {
        return b_editar;
    }

    public void setB_editar(boolean b_editar) {
        this.b_editar = b_editar;
    }

}
