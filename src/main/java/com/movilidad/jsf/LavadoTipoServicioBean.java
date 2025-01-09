package com.movilidad.jsf;

import com.movilidad.ejb.LavadoTipoServicioFacadeLocal;
import com.movilidad.ejb.VehiculoTipoFacadeLocal;
import com.movilidad.model.LavadoTipoServicio;
import com.movilidad.model.VehiculoTipo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "lavadoTipoServicioBean")
@ViewScoped
public class LavadoTipoServicioBean implements Serializable {

    @EJB
    private LavadoTipoServicioFacadeLocal lavadoTipoServicioEjb;
    @EJB
    private VehiculoTipoFacadeLocal vehiculoTipoEjb;

    private LavadoTipoServicio lavadoTipoServicio;
    private LavadoTipoServicio selected;
    private String nombre;
    private Integer idVehiculoTipo;

    private boolean b_especial;
    private boolean isEditing;

    private List<LavadoTipoServicio> lstLavadoTipoServicios;
    private List<VehiculoTipo> lstVehiculoTipos;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    public void nuevo() {
        nombre = "";
        idVehiculoTipo = null;
        lavadoTipoServicio = new LavadoTipoServicio();
        selected = null;
        b_especial = false;
        isEditing = false;
    }

    public void editar() {
        nombre = selected.getNombre();
        idVehiculoTipo = selected.getIdVehiculoTipo() != null ? selected.getIdVehiculoTipo().getIdVehiculoTipo() : null;
        lavadoTipoServicio = selected;
        b_especial = (selected.getEspecial() == 1);
        isEditing = true;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {
            lavadoTipoServicio.setNombre(nombre);
            lavadoTipoServicio.setUsername(user.getUsername());
            lavadoTipoServicio.setEspecial(b_especial ? 1 : 0);
            lavadoTipoServicio.setIdVehiculoTipo(new VehiculoTipo(idVehiculoTipo));

            if (isEditing) {

                lavadoTipoServicio.setModificado(MovilidadUtil.fechaCompletaHoy());
                lavadoTipoServicioEjb.edit(lavadoTipoServicio);

                PrimeFaces.current().executeScript("PF('wlvTipoEvidencia').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                lavadoTipoServicio.setEstadoReg(0);
                lavadoTipoServicio.setCreado(MovilidadUtil.fechaCompletaHoy());
                lavadoTipoServicioEjb.create(lavadoTipoServicio);
                lstLavadoTipoServicios.add(lavadoTipoServicio);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
            
            consultar();
            
        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public String validarDatos() {
        if (isEditing) {
            if (lavadoTipoServicioEjb.findByNombre(selected.getIdLavadoTipoServicio(), nombre.trim()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
            if (lavadoTipoServicioEjb.findByNombreAndTipoVehiculo(selected.getIdLavadoTipoServicio(), nombre.trim(), idVehiculoTipo) != null) {
                return "YA existe un registro con los parámetros indicados";
            }
        } else {
            if (!lstLavadoTipoServicios.isEmpty()) {
                if (lavadoTipoServicioEjb.findByNombre(0, nombre.trim()) != null) {
                return "YA existe un registro con el nombre a ingresar";
            }
                if (lavadoTipoServicioEjb.findByNombreAndTipoVehiculo(0, nombre.trim(), idVehiculoTipo) != null) {
                    return "YA existe un registro con los parámetros indicados";
                }
            }
        }

        return null;
    }

    private void consultar() {
        lstLavadoTipoServicios = lavadoTipoServicioEjb.findAllByEstadoReg();
        lstVehiculoTipos = vehiculoTipoEjb.findAllEstadoR();
    }

    public LavadoTipoServicio getLavadoTipoServicio() {
        return lavadoTipoServicio;
    }

    public void setLavadoTipoServicio(LavadoTipoServicio lavadoTipoServicio) {
        this.lavadoTipoServicio = lavadoTipoServicio;
    }

    public LavadoTipoServicio getSelected() {
        return selected;
    }

    public void setSelected(LavadoTipoServicio selected) {
        this.selected = selected;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<LavadoTipoServicio> getLstLavadoTipoServicios() {
        return lstLavadoTipoServicios;
    }

    public void setLstLavadoTipoServicios(List<LavadoTipoServicio> lstLavadoTipoServicios) {
        this.lstLavadoTipoServicios = lstLavadoTipoServicios;
    }

    public boolean isB_especial() {
        return b_especial;
    }

    public void setB_especial(boolean b_especial) {
        this.b_especial = b_especial;
    }

    public Integer getIdVehiculoTipo() {
        return idVehiculoTipo;
    }

    public void setIdVehiculoTipo(Integer idVehiculoTipo) {
        this.idVehiculoTipo = idVehiculoTipo;
    }

    public List<VehiculoTipo> getLstVehiculoTipos() {
        return lstVehiculoTipos;
    }

    public void setLstVehiculoTipos(List<VehiculoTipo> lstVehiculoTipos) {
        this.lstVehiculoTipos = lstVehiculoTipos;
    }

}
