package com.movilidad.jsf;

import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.NovedadTipoFacadeLocal;
import com.movilidad.model.Multa;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadDano;
import com.movilidad.model.NovedadTipo;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "novedadUtilJSFManagedBean")
@ViewScoped
public class NovedadUtilJSFManagedBean implements Serializable {

    @EJB
    private NovedadFacadeLocal novedadEjb;
    @EJB
    private NovedadTipoFacadeLocal novedadTipoEjb;
    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTipoDetallesEjb;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private Novedad novedad;
    private NovedadTipo novedadTipo;
    private NovedadTipoDetalles novedadTipoDetalles;

    @PostConstruct
    public void init() {
        this.novedad = new Novedad();
        this.novedadTipo = new NovedadTipo();
        this.novedadTipoDetalles = new NovedadTipoDetalles();
    }

    public boolean isIncapacidad(Novedad selected) {
        return (selected.getIdNovedadTipo().getNombreTipoNovedad().equals(Util.NOVEDAD_AUSENTISMO)
                && selected.getIdNovedadTipoDetalle().getTituloTipoNovedad().contains(Util.DETALLE_INCAPACIDAD));
    }

    @Transactional
    public void guardarNovedadDano(NovedadDano novedadDano) {
        novedad.setFecha(novedadDano.getFecha());

        // Id tipo de novedad daño (3)
        novedadTipo = novedadTipoEjb.find(Util.ID_NOVEDAD_DANO);

        novedad.setIdNovedadTipo(novedadTipo);
        novedad.setIdVehiculo(novedadDano.getIdVehiculo());

        if (novedadDano.getIdEmpleado() != null) {
            novedad.setIdEmpleado(novedadDano.getIdEmpleado());
        }

        novedad.setIdGopUnidadFuncional(novedadDano.getIdGopUnidadFuncional());
        novedad.setObservaciones(novedadDano.getDescripcion());
        novedad.setIdNovedadDano(novedadDano);
        novedad.setPuntosPm(novedadDano.getIdVehiculoDanoSeveridad().getPuntosPm());
        novedad.setProcede(1);
        novedad.setPuntosPmConciliados(novedadDano.getIdVehiculoDanoSeveridad().getPuntosPm());
        novedad.setLiquidada(0);
        novedad.setUsername(novedadDano.getUsername());
        novedad.setCreado(novedadDano.getCreado());
        novedadEjb.create(novedad);
    }

    @Transactional
    public void actualizarNovedadDano(NovedadDano novedadDano) {
        novedad = novedadEjb.findByNovedadDano(novedadDano.getIdNovedadDano());
        novedad.setFecha(novedadDano.getFecha());

        // Id tipo de novedad daño (3)
        novedadTipo = novedadTipoEjb.find(Util.ID_NOVEDAD_DANO);

        novedad.setIdNovedadTipo(novedadTipo);
        novedad.setIdVehiculo(novedadDano.getIdVehiculo());

        if (novedadDano.getIdEmpleado() != null) {
            novedad.setIdEmpleado(novedadDano.getIdEmpleado());
        }

        novedad.setObservaciones(novedadDano.getDescripcion());
        novedad.setIdNovedadDano(novedadDano);
        novedad.setPuntosPm(novedadDano.getIdVehiculoDanoSeveridad().getPuntosPm());
        novedad.setPuntosPmConciliados(novedadDano.getIdVehiculoDanoSeveridad().getPuntosPm());
        novedad.setProcede(1);
        novedad.setLiquidada(0);
        novedad.setUsername(novedadDano.getUsername());
        novedad.setCreado(novedadDano.getCreado());
        novedadEjb.edit(novedad);
    }

    @Transactional
    public void guardarMulta(Multa multa) {
        novedad.setFecha(multa.getFechaHora());

        // Id tipo de novedad multas (6)
        novedadTipo = novedadTipoEjb.find(Util.ID_NOVEDAD_MULTAS);

        novedad.setIdNovedadTipo(novedadTipo);
        novedad.setIdVehiculo(multa.getIdVehiculo());

        if (multa.getIdEmpleado() != null) {
            novedad.setIdEmpleado(multa.getIdEmpleado());
        }

        novedad.setObservaciones(multa.getDescripcion());
        novedad.setIdMulta(multa);
        novedad.setPuntosPm(50);
        novedad.setProcede(1);
        novedad.setPuntosPmConciliados(50);
        novedad.setLiquidada(0);
        novedad.setUsername(multa.getUsername());
        novedad.setCreado(multa.getCreado());
        novedadEjb.create(novedad);
    }

    @Transactional
    public void actualizarMulta(Multa multa) {
        novedad = novedadEjb.findByMulta(multa.getIdMulta());
        novedad.setFecha(multa.getFechaHora());

        // Id multas (6)
        novedadTipo = novedadTipoEjb.find(6);

        novedad.setIdNovedadTipo(novedadTipo);
        novedad.setIdVehiculo(multa.getIdVehiculo());

        if (multa.getIdEmpleado() != null) {
            novedad.setIdEmpleado(multa.getIdEmpleado());
        }

        novedad.setObservaciones(multa.getDescripcion());
        novedad.setIdMulta(multa);
        novedad.setPuntosPm(50);
        novedad.setProcede(1);
        novedad.setPuntosPmConciliados(50);
        novedad.setLiquidada(0);
        novedad.setUsername(multa.getUsername());
        novedad.setCreado(multa.getCreado());
        novedadEjb.edit(novedad);
    }

    public Date sumarRestarDiasFecha(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
    }

    /**
     * Realiza la conciliación de una novedad y su asignación de puntos en el
     * programa master
     *
     * @param novedadDano
     */
    public void procedeConciliacion(NovedadDano novedadDano) {

        Novedad novedadACambiar = novedadDano.getNovedadList().get(0);

        if (novedadACambiar == null) {
            return;
        }

        novedadACambiar.setPuntosPmConciliados(novedadDano.getIdVehiculoDanoSeveridad().getPuntosPm());
        novedadACambiar.setProcede(1);
        MovilidadUtil.addSuccessMessage("Se ha calificado la novedad de manera éxitosa");
        MovilidadUtil.updateComponent("msgs");
        MovilidadUtil.updateComponent("frmPrincipalNvdDano:tbl_danos");
        novedadEjb.edit(novedadACambiar);
    }

    /**
     * Deshace la conciliación de una novedad y su asignación de puntos en el
     * programa master
     *
     * @param novedadDano
     */
    public void noProcedeConciliacion(NovedadDano novedadDano) {
        Novedad novedadACambiar = novedadDano.getNovedadList().get(0);

        if (novedadACambiar == null) {
            return;
        }
        novedadACambiar.setPuntosPmConciliados(0);
        novedadACambiar.setProcede(0);
        MovilidadUtil.addSuccessMessage("Se ha realizado la acción correctamente");
        MovilidadUtil.updateComponent("msgs");
        MovilidadUtil.updateComponent("frmPrincipalNvdDano:tbl_danos");
        novedadEjb.edit(novedadACambiar);
    }
    
    /**
     * Método que se encarga de retornar si una novedad de daño procede
     * @param novedadDano
     * @return 1 si procede, de lo contrario 0
     */
    public int verificarProcedeNovedad(NovedadDano novedadDano){
        if (novedadDano == null) {
            return 0;
        }
        Novedad item = novedadDano.getNovedadList().get(0);

        if (item == null) {
            return 0;
        }
        
        return item.getProcede();
    }

    public Novedad getNovedad() {
        return novedad;
    }

    public void setNovedad(Novedad novedad) {
        this.novedad = novedad;
    }

    public NovedadTipo getNovedadTipo() {
        return novedadTipo;
    }

    public void setNovedadTipo(NovedadTipo novedadTipo) {
        this.novedadTipo = novedadTipo;
    }

    public NovedadTipoDetalles getNovedadTipoDetalles() {
        return novedadTipoDetalles;
    }

    public void setNovedadTipoDetalles(NovedadTipoDetalles novedadTipoDetalles) {
        this.novedadTipoDetalles = novedadTipoDetalles;
    }

    public NovedadFacadeLocal getNovedadEjb() {
        return novedadEjb;
    }

    public void setNovedadEjb(NovedadFacadeLocal novedadEjb) {
        this.novedadEjb = novedadEjb;
    }
}
