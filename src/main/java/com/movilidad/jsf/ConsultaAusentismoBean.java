package com.movilidad.jsf;

import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.model.Novedad;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.ConsultaAusentismo;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "consultaAusentismoBean")
@ViewScoped
public class ConsultaAusentismoBean implements Serializable {

    @EJB
    private NovedadFacadeLocal novedadEjb;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private Date fecha;

    private String fechaActualFormateada;
    private String fechaSiguienteFormateada;

    private boolean flagGestor;

    private List<ConsultaAusentismo> lista;
    private List<String> fechas;

    private final UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        flagGestor = validarRolGestor();
        fecha = MovilidadUtil.fechaHoy();
        consultar();
    }

    /**
     * Valida si el usuario logueado corresponde a un gestor
     *
     * @return true si el usuario tiene rol ROLE_LIQ
     */
    private boolean validarRolGestor() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().contains("LIQ")) {
                return true;
            }
        }
        return false;
    }

    public void consultar() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            MovilidadUtil.updateComponent(":msgs");
            return;
        }

        /**
         * Se llena listado de fechas con la fecha seleccionada y su respectiva
         * fecha siguiente.
         */
        fechas = new ArrayList<>();
        fechas.add(Util.dateFormat(fecha));
        fechas.add(Util.dateFormat(MovilidadUtil.sumarDias(fecha, 1)));

        fechaActualFormateada = Util.dateFormat(fecha);
        fechaSiguienteFormateada = Util.dateFormat(MovilidadUtil.sumarDias(fecha, 1));

        List<Novedad> novedades = novedadEjb.obtenerAusentismosConsulta(
                fecha, MovilidadUtil.sumarDias(fecha, 1),
                unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (novedades == null || novedades.isEmpty()) {
            MovilidadUtil.addAdvertenciaMessage("NO se encontraron datos");
            MovilidadUtil.updateComponent(":msgs");
            return;
        }

        /**
         * Se realiza la creación de la lista que va a mostrar los datos en la
         * vista.
         *
         * Se verifica si una novedad corresponde al día actual ó al siguiente y
         * luego se agrega a un map para luego convertirlo en la lista que se
         * mostrará en la vista.
         */
        Date actual = fecha;

        Map<String, ConsultaAusentismo> mapNovedades = new LinkedHashMap<>();
        novedades.forEach(novedad -> {
            if (mapNovedades.get(novedad.getIdEmpleado().getIdentificacion()) != null) {
                ConsultaAusentismo cAusentismo = mapNovedades.get(novedad.getIdEmpleado().getIdentificacion());
                if (cAusentismo.getDiaActual() != null) {
                    if (!(cAusentismo.getDiaActual().getFecha().compareTo(novedad.getFecha()) == 0)) {
                        cAusentismo.setDiaSiguiente(novedad);
                        mapNovedades.put(novedad.getIdEmpleado().getIdentificacion(), cAusentismo);
                    }
                }
            } else if (actual.compareTo(novedad.getFecha()) == 0) {
                mapNovedades.put(novedad.getIdEmpleado().getIdentificacion(), new ConsultaAusentismo(novedad, null));
            } else {

                if (novedad.getDesde() != null && novedad.getHasta() != null) {
                    if (verificarFechaEnRango(actual, novedad.getDesde(), novedad.getHasta())) {
                        mapNovedades.put(novedad.getIdEmpleado().getIdentificacion(), new ConsultaAusentismo(novedad, null));
                    }

                    if (mapNovedades.get(novedad.getIdEmpleado().getIdentificacion()) != null) {
                        ConsultaAusentismo cAusentismo = mapNovedades.get(novedad.getIdEmpleado().getIdentificacion());
                        if (cAusentismo.getDiaActual() != null) {
                            if (verificarFechaEnRango(MovilidadUtil.sumarDias(actual, 1), novedad.getDesde(), novedad.getHasta())) {
                                cAusentismo.setDiaSiguiente(novedad);

                                mapNovedades.put(novedad.getIdEmpleado().getIdentificacion(), cAusentismo);
                            }
                        }
                    }

                    if (novedad.getDesde().compareTo(actual) == 0) {
                        mapNovedades.put(novedad.getIdEmpleado().getIdentificacion(), new ConsultaAusentismo(novedad, null));
                    }
                    if (novedad.getDesde().compareTo(MovilidadUtil.sumarDias(actual, 1)) == 0) {
                        mapNovedades.put(novedad.getIdEmpleado().getIdentificacion(), new ConsultaAusentismo(null, novedad));
                    }
                    if (novedad.getHasta().compareTo(actual) == 0) {
                        mapNovedades.put(novedad.getIdEmpleado().getIdentificacion(), new ConsultaAusentismo(novedad, null));
                    }
                    if (novedad.getHasta().compareTo(MovilidadUtil.sumarDias(actual, 1)) == 0) {
                        mapNovedades.put(novedad.getIdEmpleado().getIdentificacion(), new ConsultaAusentismo(null, novedad));
                    }

                    return;
                }

                mapNovedades.put(novedad.getIdEmpleado().getIdentificacion(), new ConsultaAusentismo(null, novedad));
            }

        });

        lista = mapNovedades.values()
                .stream()
                .collect(Collectors.toList());

        MovilidadUtil.runScript("PF('wlVdt_consultaAusentismos').getPaginator().setPage(0);");

    }

    private boolean verificarFechaEnRango(Date fechaVerificar, Date desde, Date hasta) {
        return fechaVerificar.after(desde) && fechaVerificar.before(hasta);
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<ConsultaAusentismo> getLista() {
        return lista;
    }

    public void setLista(List<ConsultaAusentismo> lista) {
        this.lista = lista;
    }

    public boolean isFlagGestor() {
        return flagGestor;
    }

    public void setFlagGestor(boolean flagGestor) {
        this.flagGestor = flagGestor;
    }

    public List<String> getFechas() {
        return fechas;
    }

    public void setFechas(List<String> fechas) {
        this.fechas = fechas;
    }

    public String getFechaActualFormateada() {
        return fechaActualFormateada;
    }

    public void setFechaActualFormateada(String fechaActualFormateada) {
        this.fechaActualFormateada = fechaActualFormateada;
    }

    public String getFechaSiguienteFormateada() {
        return fechaSiguienteFormateada;
    }

    public void setFechaSiguienteFormateada(String fechaSiguienteFormateada) {
        this.fechaSiguienteFormateada = fechaSiguienteFormateada;
    }

}
