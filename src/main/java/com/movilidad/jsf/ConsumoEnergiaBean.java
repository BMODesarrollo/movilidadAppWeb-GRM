package com.movilidad.jsf;

import com.movilidad.ejb.ConsumoEnergiaEstadoFacadeLocal;
import com.movilidad.ejb.ConsumoEnergiaFacadeLocal;
import com.movilidad.model.ConsumoEnergia;
import com.movilidad.model.ConsumoEnergiaEstado;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "consumoEnergiaBean")
@ViewScoped
public class ConsumoEnergiaBean implements Serializable {

    @EJB
    private ConsumoEnergiaFacadeLocal consumoEnergiaEjb;
    @EJB
    private ConsumoEnergiaEstadoFacadeLocal consumoEnergiaEstadoEjb;

    private ConsumoEnergia consumoEnergia;
    private ConsumoEnergia selected;
    private Integer idEstado;
    private Date fechaInicio;
    private Date fechaFin;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private boolean isEditing;

    private List<ConsumoEnergia> lstConsumoEnergias;
    private List<ConsumoEnergiaEstado> lstConsumoEnergiaEstados;

    @PostConstruct
    public void init() {
        fechaInicio = MovilidadUtil.fechaHoy();
        fechaFin = MovilidadUtil.fechaHoy();
        consultar();
    }

    public void consultar() {
        lstConsumoEnergias = consumoEnergiaEjb.findAllByFecha(fechaInicio, fechaFin);
        lstConsumoEnergiaEstados = consumoEnergiaEstadoEjb.findAllByEstadoReg();
    }

    public void nuevo() {
        isEditing = false;
        idEstado = null;
        consumoEnergia = new ConsumoEnergia();
        consumoEnergia.setFechaHora(MovilidadUtil.fechaCompletaHoy());

        selected = null;
    }

    public void editar() {
        isEditing = true;
        idEstado = selected.getIdConsumoEnergiaEstado().getIdConsumoEnergiaEstado();
        lstConsumoEnergiaEstados = consumoEnergiaEstadoEjb.findAllByEstadoReg();

        consumoEnergia = selected;
    }

    public void guardar() {
        String mensajeValidacion = validarDatos();

        if (mensajeValidacion == null) {
            consumoEnergia.setIdConsumoEnergiaEstado(new ConsumoEnergiaEstado(idEstado));

            if (isEditing) {
                consumoEnergia.setUsername(user.getUsername());
                consumoEnergia.setModificado(MovilidadUtil.fechaCompletaHoy());
                consumoEnergiaEjb.edit(consumoEnergia);

                MovilidadUtil.hideModal("wlvConsumoEnergia");
                MovilidadUtil.addSuccessMessage("Registro actualizado exitosamente");
            } else {
                consumoEnergia.setEstadoReg(0);
                consumoEnergia.setUsername(user.getUsername());
                consumoEnergia.setCreado(MovilidadUtil.fechaCompletaHoy());
                consumoEnergiaEjb.create(consumoEnergia);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro guardado exitosamente");
            }

            consultar();

        } else {
            MovilidadUtil.addErrorMessage(mensajeValidacion);
        }
    }

    public String validarFormatoFecha() {
        Date hoy = MovilidadUtil.fechaCompletaHoy();
        return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(hoy);
    }

    /**
     * Retorna el día de la semana en base a una fecha que se pase por
     * parámetros
     *
     * @param fecha
     * @return
     */
    public String retornarDiaSemana(Date fecha) {
        String diaSemanaStr = "";
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);

        int diaSemana = c.get(Calendar.DAY_OF_WEEK);
        switch (diaSemana) {
            case 1:
                diaSemanaStr = "Domingo";
                break;
            case 2:
                diaSemanaStr = "Lunes";
                break;
            case 3:
                diaSemanaStr = "Martes";
                break;
            case 4:
                diaSemanaStr = "Miercoles";
                break;
            case 5:
                diaSemanaStr = "Jueves";
                break;
            case 6:
                diaSemanaStr = "Viernes";
                break;
            case 7:
                diaSemanaStr = "Sábado";
                break;
            default:
                break;
        }
        return diaSemanaStr.toUpperCase();
    }

    private String validarDatos() {

        switch (validarLecturasDiaAnterior()) {
            case 1:
                return "La lectura 1 a ingresar debe ser mayor a la del día anterior";
            case 2:
                return "La lectura 2 a ingresar debe ser mayor a la del día anterior";
        }

        switch (validarLecturasDiaSiguiente()) {
            case 1:
                return "La lectura 1 a ingresar debe ser menor a la del día siguiente";
            case 2:
                return "La lectura 2 a ingresar debe ser menor a la del día siguiente";
        }

        switch (validarLecturasPorFecha()) {
            case 1:
                return "La lectura 1 a ingresar debe ser mayor a la del registro anterior";
            case 2:
                return "La lectura 2 a ingresar debe ser mayor a la del registro anterior";
        }

        if (isEditing) {
            if (consumoEnergiaEjb.findByFechaAndEstado(selected.getIdConsumoEnergia(), consumoEnergia.getFechaHora(), idEstado) != null) {
                return "YA existe un registro con los parámetros ingresados";
            }
        } else {
            if (!lstConsumoEnergias.isEmpty()) {

                if (consumoEnergiaEjb.findByFecha(consumoEnergia.getFechaHora()) == null) {
                    if (MovilidadUtil.validarHoraPm(consumoEnergia.getFechaHora())) {
                        return "EL primer registro para la fecha ( " + Util.dateFormat(consumoEnergia.getFechaHora()) + " ) debe ser con horario A.M.";
                    }
                }

                if (consumoEnergiaEjb.findByFechaAndEstado(0, consumoEnergia.getFechaHora(), idEstado) != null) {
                    return "YA existe un registro con los parámetros ingresados";
                }

            }
        }

        return null;
    }

    /**
     * Verifica que las lecturas a ingresar sean mayores a las de la FECHA
     * anterior a la fecha seleccionada
     *
     * @return 1 ( Si la lectura 1 a ingresar es menor a la del día anterior) y
     * retorna 2 ( Si la lectura 2 a ingresar es menor a la del día anterior)
     */
    private int validarLecturasDiaAnterior() {
        ConsumoEnergia consumoEnergiaAux = consumoEnergiaEjb.findByFechaAnterior(MovilidadUtil.sumarDias(consumoEnergia.getFechaHora(), -1));

        if (consumoEnergiaAux != null) {
            if (consumoEnergia.getLecturaUno().compareTo(consumoEnergiaAux.getLecturaUno()) < 0) {
                return 1;
            }

            if (consumoEnergia.getLecturaDos().compareTo(consumoEnergiaAux.getLecturaDos()) < 0) {
                return 2;
            }
        }

        return 0;
    }

    /**
     * Verifica que las lecturas a ingresar sean mayores a las de la FECHA
     * siguiente a la fecha seleccionada
     *
     * @return 1 ( Si la lectura 1 a ingresar es mayor a la del día siguiente) y
     * retorna 2 ( Si la lectura 2 a ingresar es mayor a la del día siguiente)
     */
    private int validarLecturasDiaSiguiente() {
        ConsumoEnergia consumoEnergiaAux = consumoEnergiaEjb.findByFechaSiguiente(consumoEnergia.getFechaHora());

        if (consumoEnergiaAux != null) {
            if (consumoEnergia.getLecturaUno().compareTo(consumoEnergiaAux.getLecturaUno()) > 0) {
                return 1;
            }

            if (consumoEnergia.getLecturaDos().compareTo(consumoEnergiaAux.getLecturaDos()) > 0) {
                return 2;
            }
        }

        return 0;
    }

    /**
     * Verifica que las lecturas a ingresar sean mayores a las del REGISTRO
     * anterior ( se hace la validacion en el registro anterior guardado con la
     * fecha a ingresar)
     *
     * @return 1 ( Si la lectura 1 a ingresar es menor a la del registro
     * anterior) y retorna 2 ( Si la lectura 2 a ingresar es menor a la del
     * registro anterior)
     */
    private int validarLecturasPorFecha() {
        ConsumoEnergia consumoEnergiaAux = consumoEnergiaEjb.findByFecha(consumoEnergia.getFechaHora());

        if (consumoEnergiaAux != null) {
            if (consumoEnergia.getLecturaUno().compareTo(consumoEnergiaAux.getLecturaUno()) < 0) {
                return 1;
            }

            if (consumoEnergia.getLecturaDos().compareTo(consumoEnergiaAux.getLecturaDos()) < 0) {
                return 2;
            }
        }

        return 0;
    }

    public ConsumoEnergia getConsumoEnergia() {
        return consumoEnergia;
    }

    public void setConsumoEnergia(ConsumoEnergia consumoEnergia) {
        this.consumoEnergia = consumoEnergia;
    }

    public ConsumoEnergia getSelected() {
        return selected;
    }

    public void setSelected(ConsumoEnergia selected) {
        this.selected = selected;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<ConsumoEnergia> getLstConsumoEnergias() {
        return lstConsumoEnergias;
    }

    public void setLstConsumoEnergias(List<ConsumoEnergia> lstConsumoEnergias) {
        this.lstConsumoEnergias = lstConsumoEnergias;
    }

    public List<ConsumoEnergiaEstado> getLstConsumoEnergiaEstados() {
        return lstConsumoEnergiaEstados;
    }

    public void setLstConsumoEnergiaEstados(List<ConsumoEnergiaEstado> lstConsumoEnergiaEstados) {
        this.lstConsumoEnergiaEstados = lstConsumoEnergiaEstados;
    }

}
