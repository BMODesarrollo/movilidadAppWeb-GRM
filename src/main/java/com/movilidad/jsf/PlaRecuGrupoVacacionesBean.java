package com.movilidad.jsf;

import com.movilidad.ejb.PlaRecuGrupoVacacionesFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.planificacion_recursos.PlaRecuGrupoVacaciones;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Omar.beltran
 */
@Named(value = "plaRecuGrupoVacacionesBean")
@ViewScoped
public class PlaRecuGrupoVacacionesBean implements Serializable {

    @EJB
    private PlaRecuGrupoVacacionesFacadeLocal plaRecuGrupoVacacionesEJB;
    @Inject
    private GopUnidadFuncionalBean unidadFuncionalBean;
    private GopUnidadFuncional unidadFuncional;
    private Integer idUFSelected;

    private PlaRecuGrupoVacaciones plaRecuGrupoVacaciones;
    private String grupoVacacionesSelected; //se emplea para comparar si el nombre del grupoVacaciones se cambia en la función de editar
    private List<PlaRecuGrupoVacaciones> listPlaRecuGrupoVacaciones;
    private List<GopUnidadFuncional> listUnidadFuncional;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private boolean b_editar;

    @PostConstruct
    public void init() {
        b_editar = false;
        plaRecuGrupoVacaciones = new PlaRecuGrupoVacaciones();
        listPlaRecuGrupoVacaciones = plaRecuGrupoVacacionesEJB.findAll();
        listUnidadFuncional = cargarUF();
        idUFSelected = 0;
    }

    public PlaRecuGrupoVacacionesBean() {
    }

    private List<GopUnidadFuncional> cargarUF() {
        unidadFuncional = new GopUnidadFuncional();
        unidadFuncionalBean.consultar();
        return unidadFuncionalBean.getLstGopUnidadFuncionals();
    }
    
    public void crear() {
        try {
            if (plaRecuGrupoVacaciones != null) {
                if (plaRecuGrupoVacaciones.getFechaFin().before(plaRecuGrupoVacaciones.getFechaInicio())) {
                    MovilidadUtil.addAdvertenciaMessage("'Fecha Inicio' debe ser menor que 'Fecha Fin'");
                } else { // si fecha inicio es menor que la fecha fin se procede a validar las demás reglas
                    unidadFuncional.setIdGopUnidadFuncional(idUFSelected);
                    if (plaRecuGrupoVacacionesEJB.findByName(plaRecuGrupoVacaciones.getGrupo(), unidadFuncional.getIdGopUnidadFuncional()) == null) {
                        plaRecuGrupoVacaciones.setIdGopUnidadFuncional(unidadFuncional);
                        plaRecuGrupoVacaciones.setGrupo(plaRecuGrupoVacaciones.getGrupo().toUpperCase());
                        plaRecuGrupoVacaciones.setCreado(new Date());
                        plaRecuGrupoVacaciones.setModificado(new Date());
                        plaRecuGrupoVacaciones.setEstadoReg(0);
                        plaRecuGrupoVacaciones.setUsernameCreate(user.getUsername());
                        plaRecuGrupoVacacionesEJB.create(plaRecuGrupoVacaciones);
                        plaRecuGrupoVacaciones = new PlaRecuGrupoVacaciones();
                        listPlaRecuGrupoVacaciones = plaRecuGrupoVacacionesEJB.findAll();
                        MovilidadUtil.addSuccessMessage("Registro 'Grupo Vacaciones' creado");
                        PrimeFaces.current().executeScript("PF('wvPlaRecuGrupoVacaciones').hide();");
                    } else {
                        MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Grupo Vacaciones' con el nombre ingresado");
                    }
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al crear registro 'Grupo Vacaciones'");
        }
    }

    public void editar() {
        try {
            if (plaRecuGrupoVacaciones != null) {
                if (plaRecuGrupoVacaciones.getFechaFin().before(plaRecuGrupoVacaciones.getFechaInicio())) {
                    MovilidadUtil.addAdvertenciaMessage("'Fecha Inicio' debe ser menor que 'Fecha Fin'");
                } else { // si fecha inicio es menor que la fecha fin se procede a validar las demás reglas
                    unidadFuncional.setIdGopUnidadFuncional(idUFSelected);
                    PlaRecuGrupoVacaciones obj = plaRecuGrupoVacacionesEJB.findByName(plaRecuGrupoVacaciones.getGrupo(), unidadFuncional.getIdGopUnidadFuncional());
                    if ((obj != null && obj.getGrupo().equals(grupoVacacionesSelected)) || obj == null) {
                        plaRecuGrupoVacaciones.setGrupo(plaRecuGrupoVacaciones.getGrupo().toUpperCase());
                        plaRecuGrupoVacaciones.setIdGopUnidadFuncional(unidadFuncional);
                        plaRecuGrupoVacacionesEJB.edit(plaRecuGrupoVacaciones);
                        listPlaRecuGrupoVacaciones = plaRecuGrupoVacacionesEJB.findAll();//actualizar la lista
                        MovilidadUtil.addSuccessMessage("Se ha actualizado registro 'Grupo Vacaciones'");
                        reset();
                        PrimeFaces.current().executeScript("PF('wvPlaRecuGrupoVacaciones').hide();");
                    } else {
                        listPlaRecuGrupoVacaciones = plaRecuGrupoVacacionesEJB.findAll();//para que borre cualquier valor editado
                        MovilidadUtil.addAdvertenciaMessage("Ya existe un registro 'Grupo Vacaciones' con el nombre ingresado");
                    }
                }
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al editar registro 'Grupo Vacaciones'");
        }
    }

    public void editar(PlaRecuGrupoVacaciones obj) throws Exception {
        this.plaRecuGrupoVacaciones = obj;
        grupoVacacionesSelected = obj.getGrupo();
        idUFSelected = obj.getIdGopUnidadFuncional().getIdGopUnidadFuncional();
        b_editar = true;
    }

    public void prepareGuardar() {
        b_editar = false;
        plaRecuGrupoVacaciones = new PlaRecuGrupoVacaciones();
        idUFSelected = 0;
    }

    public void reset() {
        plaRecuGrupoVacaciones = null;
        idUFSelected = 0;
    }

    public void onRowSelect(SelectEvent event) {
        plaRecuGrupoVacaciones = (PlaRecuGrupoVacaciones) event.getObject();
    }

    public PlaRecuGrupoVacaciones getPlaRecuGrupoVacaciones() {
        return plaRecuGrupoVacaciones;
    }

    public void setPlaRecuGrupoVacaciones(PlaRecuGrupoVacaciones accViaSemaforo) {
        this.plaRecuGrupoVacaciones = accViaSemaforo;
    }

    public List<PlaRecuGrupoVacaciones> getListPlaRecuGrupoVacaciones() {
        return listPlaRecuGrupoVacaciones == null ? plaRecuGrupoVacacionesEJB.findAll() : listPlaRecuGrupoVacaciones;
    }

    public boolean isB_editar() {
        return b_editar;
    }

    public void setB_editar(boolean b_editar) {
        this.b_editar = b_editar;
    }

    public GopUnidadFuncional getUnidadFuncional() {
        return unidadFuncional;
    }

    public void setUnidadFuncional(GopUnidadFuncional unidadFuncional) {
        this.unidadFuncional = unidadFuncional;
    }

    public List<GopUnidadFuncional> getListUnidadFuncional() {
        return listUnidadFuncional;
    }

    public void setListUnidadFuncional(List<GopUnidadFuncional> listUnidadFuncional) {
        this.listUnidadFuncional = listUnidadFuncional;
    }

    public Integer getIdUFSelected() {
        return idUFSelected;
    }

    public void setIdUFSelected(Integer idUFSelected) {
        this.idUFSelected = idUFSelected;
    }

}
