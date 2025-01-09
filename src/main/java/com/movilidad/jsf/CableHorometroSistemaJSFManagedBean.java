/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableHorometroSistemaFacadeLocal;
import com.movilidad.ejb.CableNovedadOpFacadeLocal;
import com.movilidad.model.CableHorometroSistema;
import com.movilidad.model.CableNovedadOp;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "horometroSistemaSFMB")
@ViewScoped
public class CableHorometroSistemaJSFManagedBean implements Serializable {

    /**
     * Creates a new instance of CableHorometroSistemaJSFManagedBean
     */
    public CableHorometroSistemaJSFManagedBean() {
    }

    @EJB
    private CableHorometroSistemaFacadeLocal cableHorometroSistemaEJB;
    @EJB
    private CableNovedadOpFacadeLocal novedadOpEJB;
    @Inject
    private HorometroUpdateJSFMB horometroUpdateBean;
    private List<CableHorometroSistema> list;
    private CableHorometroSistema horometro;
    private Date desde = MovilidadUtil.fechaHoy();
    private Date hasta = MovilidadUtil.fechaHoy();
    private BigDecimal horasCabinasSave = BigDecimal.ZERO;

    UserExtended user;

    @PostConstruct
    public void init() {
        consultar();
        getUser();
    }

    /**
     * Obtener valor de usuario en sesión.
     */
    public void getUser() {
        try {
            user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            user = null;
        }
    }

    /**
     * Preparar variables para un nuevo registro.
     */
    public void nuevo() {
        horometro = new CableHorometroSistema();
        MovilidadUtil.openModal("horometro_sistema_wv");
    }

    /**
     * Método ecargado de invocar al metodo guardarTransactional responsable de
     * persisitir la informacion a base de datos y actualizar el hotrometros de
     * las cabinas.
     */
    public void guardar() {
        guardarTransactional();
        horometroUpdateBean.horometroCabinas(horasCabinasSave.doubleValue(),
                horometro.getHorasCabinas() == null ? 0.0
                : horometro.getHorasCabinas().doubleValue(),
                horometro.getFecha(),
                user.getUsername());
        consultar();
        MovilidadUtil.hideModal("horometro_sistema_wv");
        horometro = null;

    }

    /**
     * Método encargado de persistir en base de datos un nuevo registro de
     * horometro.
     */
    @Transactional
    public void guardarTransactional() {
        horometro.setUsername(user.getUsername());
        if (horometro.getIdCableHorometroSistema() != null) {
            horometro.setModificado(MovilidadUtil.fechaCompletaHoy());
            cableHorometroSistemaEJB.edit(horometro);
        } else {
            horometro.setCreado(MovilidadUtil.fechaCompletaHoy());
            horometro.setEstadoReg(0);
            cableHorometroSistemaEJB.create(horometro);
        }
        MovilidadUtil.addSuccessMessage("Acción finalisada exitosamente");
    }

    /**
     * Método encargado de cargar la informacion de horometro sistema, segun su
     * estado de persistencia.
     */
    public void cargarDataByFecha() {
        CableHorometroSistema remoto = cableHorometroSistemaEJB.findByFecha(horometro.getFecha());
        if (remoto != null) {
            horometro = remoto;
            horasCabinasSave = remoto.getHorasCabinas();
        } else {
            horometro.setIdCableHorometroSistema(null);
            cargarData();
            horasCabinasSave = BigDecimal.ZERO;
        }
    }

    /**
     * Método encargado de cargar y calcular la informacion de acuerdo con las
     * novedades de operacion.
     */
    public void cargarData() {

        CableNovedadOp novInicioOP = novedadOpEJB.findByClaseEventoAndFecha(1, horometro.getFecha());
        CableNovedadOp novInicioOPDiaSiguiente = novedadOpEJB.findByClaseEventoAndFecha(1, MovilidadUtil.sumarDias(horometro.getFecha(), 1));
        CableNovedadOp novFinOP = novedadOpEJB.findByClaseEventoAndFecha(2, horometro.getFecha());

        if (novInicioOP == null) {
            MovilidadUtil.addErrorMessage("No hay registro de evento inicio de operación para la fecha a la indicada.");
            return;
        }
        if (novFinOP == null) {
            MovilidadUtil.addErrorMessage("No hay registro de evento fin de operación para la fecha a la indicada.");
            return;
        }
        if (novInicioOPDiaSiguiente == null) {
            MovilidadUtil.addErrorMessage("No hay registro de evento inicio de operación para la fecha posterior a la indicada.");
            return;
        }
        horometro.setHoraIniOperacion(novInicioOP.getTimeFinParada());
        horometro.setHorometroInicial(novInicioOP.getHorometroTotal());
        horometro.setHoraFinOperacion(novFinOP.getTimeIniParada());
        horometro.setHorometroFinal(novFinOP.getHorometroTotal());
        horometro.setHorasSistema(BigDecimal.ZERO);
        double horasSistema = 0.0;
        /**
         * horasSistema es igual a la resta del horometro del dia siguiente del
         * dia en cuestion menos el horometro del dia en cuestion.
         */
        horasSistema = novInicioOPDiaSiguiente.getHorometroTotal().doubleValue() - novInicioOP.getHorometroTotal().doubleValue();
        horometro.setHorasSistema(new BigDecimal(horasSistema));
        /**
         * horasOperacionComercial es igual a la resta entre la hora fin de
         * operacion y la hora inicio de operacion de la fecha en cuestion en
         * minutos.
         */
        double horasOperacionComercial = (MovilidadUtil.toSecs(horometro.getHoraFinOperacion()) / 3600.0) - (MovilidadUtil.toSecs(horometro.getHoraIniOperacion()) / 3600.0);
        horometro.setHorasOperacionComercial(new BigDecimal(horasOperacionComercial));
        /**
         * horasCabina es iagual a la suma de horasOperacionComercial y el
         * factor de horasOperacionComercial de 0.6.
         */
        double horasCabina = horometro.getHorasOperacionComercial().doubleValue() + new BigDecimal(0.6).doubleValue();
        horometro.setHorasCabinas(new BigDecimal(horasCabina));

    }

    public void consultar() {
        list = cableHorometroSistemaEJB.findAllByRangoFecha(desde, hasta);
    }

    /**
     * Método encargado de prepara la variable horometro para la edición
     *
     * @param obj
     */
    public void editar(CableHorometroSistema obj) {
        horometro = obj;
        MovilidadUtil.openModal("horometro_sistema_wv");
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

    public List<CableHorometroSistema> getList() {
        return list;
    }

    public void setList(List<CableHorometroSistema> list) {
        this.list = list;
    }

    public CableHorometroSistema getHorometro() {
        return horometro;
    }

    public void setHorometro(CableHorometroSistema horometro) {
        this.horometro = horometro;
    }

}
