package com.movilidad.jsf;

import com.movilidad.ejb.PlaRecuCategoriaFacadeLocal;
import com.movilidad.model.planificacion_recursos.PlaRecuCategoria;
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
@Named(value = "plaRecuCategoriaBean")
@ViewScoped
public class PlaRecuCategoriaBean implements Serializable {

    @EJB
    private PlaRecuCategoriaFacadeLocal plaRecuCategoriaEJB;

    private PlaRecuCategoria plaRecuCategoria;
    private String categoriaSelected;//se emplea para comparar si el nombre del motivo se cambia en la función de editar
    private List<PlaRecuCategoria> listPlaRecuCategoria;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private boolean b_editar;

    @PostConstruct
    public void init() {
        b_editar = false;
        plaRecuCategoria = new PlaRecuCategoria();
        listPlaRecuCategoria = plaRecuCategoriaEJB.findAll();
    }

    public PlaRecuCategoriaBean() {
    }

    public void crear() {
        try {
            if (plaRecuCategoria != null) {
                if (plaRecuCategoriaEJB.findByName(plaRecuCategoria.getName()) == null) {
                    plaRecuCategoria.setName(plaRecuCategoria.getName().toUpperCase());
                    plaRecuCategoria.setCreado(new Date());
                    plaRecuCategoria.setModificado(new Date());
                    plaRecuCategoria.setEstadoReg(0);
                    plaRecuCategoria.setUsernameCreate(user.getUsername());
                    plaRecuCategoriaEJB.create(plaRecuCategoria);
                    MovilidadUtil.addSuccessMessage("Registro 'Categoría' creado");
                    plaRecuCategoria = new PlaRecuCategoria();
                    listPlaRecuCategoria = plaRecuCategoriaEJB.findAll();
                } else {
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro categoría con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al crear registro 'Categoría'");
        }
    }

    public void editar() {
        try {
            if (plaRecuCategoria != null) {
                PlaRecuCategoria obj = plaRecuCategoriaEJB.findByName(plaRecuCategoria.getName());
                if ((obj != null && obj.getName().equals(categoriaSelected)) || obj == null) {
                    plaRecuCategoria.setName(plaRecuCategoria.getName().toUpperCase());
                    plaRecuCategoriaEJB.edit(plaRecuCategoria);
                    MovilidadUtil.addSuccessMessage("Se ha actualizado registro 'Categoria'");
                    reset();
                    PrimeFaces.current().executeScript("PF('wvPlaRecuCategoria').hide();");
                } else {
                    listPlaRecuCategoria = plaRecuCategoriaEJB.findAll();
                    MovilidadUtil.addAdvertenciaMessage("Ya existe un registro categoría con el nombre ingresado");
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al editar registro 'Categoria'");
        }
    }

    public void editar(PlaRecuCategoria obj) throws Exception {
        this.plaRecuCategoria = obj;
        b_editar = true;
        categoriaSelected = obj.getName();
        PrimeFaces.current().executeScript("PF('wvPlaRecuCategoria').show();");
    }

    public void prepareGuardar() {
        b_editar = false;
        plaRecuCategoria = new PlaRecuCategoria();
    }

    public void reset() {
        plaRecuCategoria = null;
    }

    public void onRowSelect(SelectEvent event) {
        plaRecuCategoria = (PlaRecuCategoria) event.getObject();
    }

    public PlaRecuCategoria getPlaRecuCategoria() {
        return plaRecuCategoria;
    }

    public void setPlaRecuCategoria(PlaRecuCategoria accViaSemaforo) {
        this.plaRecuCategoria = accViaSemaforo;
    }

    public List<PlaRecuCategoria> getListPlaRecuCategoria() {
        return listPlaRecuCategoria == null ? plaRecuCategoriaEJB.findAll() : listPlaRecuCategoria;
    }

    public boolean isB_editar() {
        return b_editar;
    }

    public void setB_editar(boolean b_editar) {
        this.b_editar = b_editar;
    }

}
