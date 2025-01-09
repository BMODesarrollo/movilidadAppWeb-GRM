package com.movilidad.jsf;

import com.movilidad.ejb.ParamFeriadoFacadeLocal;
import com.movilidad.ejb.TblCalendarioFacadeLocal;
import com.movilidad.ejb.TblCalendarioCaracteristicasDiaFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.ParamFeriado;
import com.movilidad.model.TblCalendario;
import com.movilidad.model.TblCalendarioCaracteristicasDia;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "tblCalendarioBean")
@ViewScoped
public class TblCalendarioBean implements Serializable {

    @EJB
    private TblCalendarioFacadeLocal tblCalendarioEjb;

    @EJB
    private TblCalendarioCaracteristicasDiaFacadeLocal caracteristicasDiaEjb;
    @EJB
    private ParamFeriadoFacadeLocal paramFeriadoEjb;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private TblCalendario tblCalendario;
    private TblCalendario selected;

    private Integer i_CaracteristicaDia;
    private Date desde;
    private Date hasta;
    private Date fecha;
    private String tipoDia;
    private boolean b_Estacionalidad;

    private boolean flagEdit;
    private boolean flagProfPrg;

    private List<TblCalendario> lstRegistros;
    private List<TblCalendarioCaracteristicasDia> lstCaracteristicasDia;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        desde = MovilidadUtil.fechaHoy();
        hasta = MovilidadUtil.fechaHoy();
        flagProfPrg = validarRolPrg();
        consultar();
    }

    public void consultar() {
        lstRegistros = tblCalendarioEjb.findAllByDateRange(desde, hasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void nuevo() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        fecha = MovilidadUtil.fechaHoy();
        asignarTipoDia();
        tblCalendario = new TblCalendario();

        if (validarRolTc()) {
            lstCaracteristicasDia = caracteristicasDiaEjb.findAllByCampo("afecta_tec");
            flagProfPrg = false;
        } else if (validarRolPrg()) {
            lstCaracteristicasDia = caracteristicasDiaEjb.findAllByCampo("afecta_prg");
            b_Estacionalidad = false;
        }

        selected = null;
        flagEdit = false;
        i_CaracteristicaDia = null;

        MovilidadUtil.openModal("wlgTblCalendario");

    }

    public void editar() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        if (!esAlterable()) {
            MovilidadUtil.addErrorMessage("No es posible modificar éste registro");
            return;
        }

        tblCalendario = selected;
        flagEdit = true;
        fecha = selected.getFecha();
        asignarTipoDia();

        if (validarRolTc()) {
            lstCaracteristicasDia = caracteristicasDiaEjb.findAllByCampo("afecta_tec");
            flagProfPrg = false;
        } else if (validarRolPrg()) {
            b_Estacionalidad = (tblCalendario.getEstacionalidad() == 1);
            lstCaracteristicasDia = caracteristicasDiaEjb.findAllByCampo("afecta_prg");
        }

        i_CaracteristicaDia = selected.getIdTblCalendarioCaracteristicaDia() != null ? selected.getIdTblCalendarioCaracteristicaDia().getIdTblCalendarioCaracteristicaDia() : null;

        MovilidadUtil.openModal("wlgTblCalendario");

    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        if (validacion == null) {
            tblCalendario.setTipoDia(tipoDia);
            tblCalendario.setFecha(fecha);
            tblCalendario.setUsername(user.getUsername());
            tblCalendario.setIdTblCalendarioCaracteristicaDia(i_CaracteristicaDia != null ? new TblCalendarioCaracteristicasDia(i_CaracteristicaDia) : null);
            tblCalendario.setIdGopUnidadFuncional(new GopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));

            /**
             * En caso de que el usuario logueado corresponda al área de
             * programación, se asigna la estacionalidad.
             */
            if (flagProfPrg) {
                tblCalendario.setEstacionalidad(b_Estacionalidad ? 1 : 0);
            }

            if (flagEdit) {
                tblCalendario.setModificado(MovilidadUtil.fechaCompletaHoy());
                tblCalendarioEjb.edit(tblCalendario);
                MovilidadUtil.hideModal("wlgTblCalendario");
                MovilidadUtil.addSuccessMessage("Registro modificado éxitosamente");

            } else {
                tblCalendario.setCreado(MovilidadUtil.fechaCompletaHoy());
                tblCalendario.setEstadoReg(0);
                tblCalendarioEjb.create(tblCalendario);

                /**
                 * Si la estacionalidad está en SI, se realizan los registros de
                 * tabla calendario para las fechas seleccionadas
                 */
                if (flagProfPrg) {

                    if (b_Estacionalidad) {
                        List<Date> lstFechas = obtenerRangoFechas(tblCalendario.getDesde(), tblCalendario.getHasta());

                        lstFechas.forEach(date -> {

                            TblCalendario item = new TblCalendario();
                            item.setFecha(date);
                            item.setTipoDia(asignarTipoDia(fecha));
                            item.setObservacion(tblCalendario.getObservacion());
                            item.setEstacionalidad(tblCalendario.getEstacionalidad());
                            item.setCreado(MovilidadUtil.fechaCompletaHoy());
                            item.setUsername(tblCalendario.getUsername());
                            item.setIdTblCalendarioCaracteristicaDia(i_CaracteristicaDia != null ? new TblCalendarioCaracteristicasDia(i_CaracteristicaDia) : null);
                            item.setIdGopUnidadFuncional(new GopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));
                            tblCalendarioEjb.create(item);
                        });

                    }

                }

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }

            consultar();

        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    public void eliminar() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        if (!validarRolPrg()) {
            MovilidadUtil.addAdvertenciaMessage("SOLO el profesional de programación puede eliminar un registro");
            return;
        }

        tblCalendario.setUsername(user.getUsername());
        tblCalendario.setModificado(MovilidadUtil.fechaCompletaHoy());
        tblCalendario.setEstadoReg(1);
        tblCalendarioEjb.edit(tblCalendario);
        MovilidadUtil.addSuccessMessage("Registro borrado con éxito");
        consultar();
        MovilidadUtil.updateComponent("frmPrincipal:dtTablaCalendario");
    }

    public void asignarTipoDia() {

        ParamFeriado paramFeriado = paramFeriadoEjb.findByFecha(fecha);

        if (paramFeriado != null) {
            tipoDia = "Festivo";
            return;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        int diaSemana = c.get(Calendar.DAY_OF_WEEK);

        if (diaSemana >= 2 && diaSemana <= 6) {
            tipoDia = "Hábil";
        } else if (diaSemana == 7) {
            tipoDia = "Sábado";
        } else {
            tipoDia = "Domingo";
        }

    }

    /**
     * Método que se encarga de devolver una lista con las fechas
     * correspondientes al rango seleccionado
     *
     * @return lista de objeto Date
     */
    private List<Date> obtenerRangoFechas(Date fechaDesde, Date fechaHasta) {
        List<Date> listaFechas = new ArrayList<>();

        Locale locale = new Locale("es", "CO");
        Calendar cini = Calendar.getInstance(locale);
        cini.setTime(fechaDesde);
        Calendar cfin = Calendar.getInstance(locale);
        cfin.setTime(fechaHasta);

        while (cfin.after(cini)) {
            listaFechas.add(cini.getTime());
            cini.add(Calendar.DATE, 1);
        }

        listaFechas.add(fechaHasta);

        return listaFechas;
    }

    private boolean esAlterable() {
        if (selected == null) {
            return false;
        }
        if (selected.getUsername().equals(user.getUsername())) {
            return true;
        }

        return false;

    }

    /**
     * Valida si el usuario logueado corresponde a un técnico de control
     *
     * @return true si el usuario tiene rol ROLE_TC
     */
    private boolean validarRolTc() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_TC")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Valida si el usuario logueado corresponde al área de programación
     *
     * @return true si el usuario tiene rol ROLE_PROFPRG
     */
    private boolean validarRolPrg() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().contains("PRG")) {
                return true;
            }
        }
        return false;
    }

    private String validarDatos() {

        if (flagEdit) {
            if (tblCalendarioEjb.findByFechaAndIdCaracteristica(selected.getIdTblCalendario(), i_CaracteristicaDia, fecha, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()) != null) {
                return "Ya existe un registro de la caracteristica del día para la fecha seleccionada";
            }

        } else {

            if (tblCalendarioEjb.findByFechaAndIdCaracteristica(0, i_CaracteristicaDia, fecha, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()) != null) {
                return "Ya existe un registro de la caracteristica del día para la fecha seleccionada";
            }

        }

        return null;

    }

    /**
     * Método que se encarga de devolver el tipo de dia en base a una fecha
     *
     * @param fecha
     * @return String
     */
    private String asignarTipoDia(Date fecha) {

        ParamFeriado paramFeriado = paramFeriadoEjb.findByFecha(fecha);

        if (paramFeriado != null) {
            return "Festivo";
        }

        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        int diaSemana = c.get(Calendar.DAY_OF_WEEK);

        if (diaSemana >= 2 && diaSemana <= 6) {
            return "Hábil";
        } else if (diaSemana == 7) {
            return "Sábado";
        } else {
            return "Domingo";
        }

    }

    public TblCalendario getTblCalendario() {
        return tblCalendario;
    }

    public void setTblCalendario(TblCalendario tblCalendario) {
        this.tblCalendario = tblCalendario;
    }

    public TblCalendario getSelected() {
        return selected;
    }

    public void setSelected(TblCalendario selected) {
        this.selected = selected;
    }

    public boolean isFlagEdit() {
        return flagEdit;
    }

    public void setFlagEdit(boolean flagEdit) {
        this.flagEdit = flagEdit;
    }

    public List<TblCalendario> getLstRegistros() {
        return lstRegistros;
    }

    public void setLstRegistros(List<TblCalendario> lstRegistros) {
        this.lstRegistros = lstRegistros;
    }

    public Integer getI_CaracteristicaDia() {
        return i_CaracteristicaDia;
    }

    public void setI_CaracteristicaDia(Integer i_CaracteristicaDia) {
        this.i_CaracteristicaDia = i_CaracteristicaDia;
    }

    public List<TblCalendarioCaracteristicasDia> getLstCaracteristicasDia() {
        return lstCaracteristicasDia;
    }

    public void setLstCaracteristicasDia(List<TblCalendarioCaracteristicasDia> lstCaracteristicasDia) {
        this.lstCaracteristicasDia = lstCaracteristicasDia;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoDia() {
        return tipoDia;
    }

    public void setTipoDia(String tipoDia) {
        this.tipoDia = tipoDia;
    }

    public boolean isB_Estacionalidad() {
        return b_Estacionalidad;
    }

    public void setB_Estacionalidad(boolean b_Estacionalidad) {
        this.b_Estacionalidad = b_Estacionalidad;
    }

    public boolean isFlagProfPrg() {
        return flagProfPrg;
    }

    public void setFlagProfPrg(boolean flagProfPrg) {
        this.flagProfPrg = flagProfPrg;
    }

}
