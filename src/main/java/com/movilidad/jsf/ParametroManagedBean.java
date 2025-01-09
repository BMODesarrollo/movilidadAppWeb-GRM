package com.movilidad.jsf;

import com.movilidad.ejb.ParamCumplimientoServicioFacadeLocal;
import com.movilidad.model.ParamCumplimientoServicio;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CloseEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "parametroBean")
@ViewScoped
public class ParametroManagedBean implements Serializable {

    @EJB
    private ParamCumplimientoServicioFacadeLocal cumplimientoServicioEjb;

    private ParamCumplimientoServicio cumplimientoServicio;
    private ParamCumplimientoServicio selected;

    private List<ParamCumplimientoServicio> lstCumplimientoServicios;

    private String periodo;
    private String hora_inicio;
    private String hora_fin;
    private final PrimeFaces current = PrimeFaces.current();

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public void nuevo() {
        periodo = "";
        hora_inicio = "";
        hora_fin = "";
        cumplimientoServicio = new ParamCumplimientoServicio();
        selected = null;
        current.focus("frmParametro:periodo");
    }

    public void editar() {
        if (selected == null) {
            current.ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("Debe seleccionar un registro");
            return;
        }

        cumplimientoServicio = selected;
        hora_inicio = cumplimientoServicio.getHoraInicio();
        hora_fin = cumplimientoServicio.getHoraFin();
        periodo = "" + cumplimientoServicio.getPeriodo();
        current.executeScript("PF('parametroDlg').show();");
        current.focus("frmParametro:periodo");
    }

    public void guardar() {
        if (!validarDatos()) {
            if (cumplimientoServicioEjb.verificarRepetidos(Integer.valueOf(periodo), cumplimientoServicio.getTipoDia().toString(), cumplimientoServicio.getNombre().trim()) == null) {
                cumplimientoServicio.setPeriodo(Integer.valueOf(periodo));
                cumplimientoServicio.setHoraInicio(hora_inicio);
                cumplimientoServicio.setHoraFin(hora_fin);
                cumplimientoServicio.setCreado(new Date());
                cumplimientoServicio.setEstadoReg(0);
                cumplimientoServicio.setUsername(user.getUsername());
                cumplimientoServicioEjb.create(cumplimientoServicio);
                lstCumplimientoServicios.add(cumplimientoServicio);
                nuevo();
                MovilidadUtil.addSuccessMessage("Parámetro registrado éxitosamente");
            } else {
                MovilidadUtil.addErrorMessage("El parámetro que intenta registrar ya existe");
            }
        }

    }

    public void actualizar() {
        if (!validarDatos()) {
            cumplimientoServicio.setPeriodo(Integer.valueOf(periodo));
            cumplimientoServicio.setHoraInicio(hora_inicio);
            cumplimientoServicio.setHoraFin(hora_fin);
            cumplimientoServicio.setModificado(new Date());
            cumplimientoServicio.setUsername(user.getUsername());
            cumplimientoServicioEjb.edit(cumplimientoServicio);
            current.ajax().update(":frmParametroList:dtParametros");
            current.executeScript("PF('parametroDlg').hide();");
//            selected = null;
            MovilidadUtil.addSuccessMessage("Datos del parámetro actualizados éxitosamente");
        }
    }

    public boolean validarDatos() {
        if (periodo == null || periodo.equals("")) {
            MovilidadUtil.addErrorMessage("Debe ingresar un periodo válido");
            return true;
        }
        if (cumplimientoServicio.getTipoDia() == null || cumplimientoServicio.getTipoDia() == ' ') {
            MovilidadUtil.addErrorMessage("Debe especificar el tipo de día");
            return true;
        }
        if (cumplimientoServicio.getNombre() == null || cumplimientoServicio.getNombre().equals("")) {
            MovilidadUtil.addErrorMessage("Debe ingresar la tarjeta profesional");
            return true;
        }
        if (hora_inicio == null || hora_inicio.equals("")) {
            MovilidadUtil.addErrorMessage("Debe ingresar hora de inicio");
            return true;
        }
        if (hora_fin == null || hora_fin.equals("")) {
            MovilidadUtil.addErrorMessage("Debe ingresar hora fin");
            return true;
        }
        if (cumplimientoServicio.getDesde() == null) {
            MovilidadUtil.addErrorMessage("Debe ingresar la tarjeta profesional");
            return true;
        }
        if (cumplimientoServicio.getHasta() == null) {
            MovilidadUtil.addErrorMessage("Debe ingresar la tarjeta profesional");
            return true;
        }
        if (Util.validarFechaCambioEstado(cumplimientoServicio.getDesde(), cumplimientoServicio.getHasta())) {
            MovilidadUtil.addErrorMessage("La fecha inicio no puede ser mayor a la fecha fin");
            return true;
        }
        int dif = MovilidadUtil.diferencia(hora_inicio, hora_fin);
        if (dif < 0) {
            current.focus("frmParametro:hora_inicio");
            MovilidadUtil.addErrorMessage("La hora inicio no puede ser mayor a la hora fin");
            return true;
        }
        return false;
    }

    public void handleClose(CloseEvent event) {
        selected = null;
    }

    public ParamCumplimientoServicio getCumplimientoServicio() {
        return cumplimientoServicio;
    }

    public void setCumplimientoServicio(ParamCumplimientoServicio cumplimientoServicio) {
        this.cumplimientoServicio = cumplimientoServicio;
    }

    public ParamCumplimientoServicio getSelected() {
        return selected;
    }

    public void setSelected(ParamCumplimientoServicio selected) {
        this.selected = selected;
    }

    public List<ParamCumplimientoServicio> getLstCumplimientoServicios() {
        if (lstCumplimientoServicios == null) {
            lstCumplimientoServicios = cumplimientoServicioEjb.findAll();
        }
        return lstCumplimientoServicios;
    }

    public void setLstCumplimientoServicios(List<ParamCumplimientoServicio> lstCumplimientoServicios) {
        this.lstCumplimientoServicios = lstCumplimientoServicios;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }
}
