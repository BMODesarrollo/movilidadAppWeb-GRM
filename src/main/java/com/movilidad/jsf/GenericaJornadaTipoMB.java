/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.GenericaJornadaTipoFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.GenericaJornadaTipo;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;
import com.movilidad.utils.UtilJornada;
/**
 *
 * @author solucionesit
 */
@Named(value = "JornadaTMB")
@ViewScoped
public class GenericaJornadaTipoMB implements Serializable {

    /**
     * Creates a new instance of GenericaJornadaTipoMB
     */
    public GenericaJornadaTipoMB() {
    }
    @EJB
    private GenericaJornadaTipoFacadeLocal jornadaTEJB;

    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUserEJB;

    private GenericaJornadaTipo jornadaT;
    private List<GenericaJornadaTipo> listJornadaT = new ArrayList<>();

    private ParamAreaUsr pau;
    private boolean ok_save = false;
    private boolean flag_tipoCalculo = false;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        pau = paramAreaUserEJB.getByIdUser(user.getUsername());
        cargar();
    }

    public void cargar() {
        if (pau != null) {
            listJornadaT = jornadaTEJB.findAllByArea(pau.getIdParamArea().getIdParamArea());
        } else {
            listJornadaT = new ArrayList<>();
        }
    }

    public void preCreate() {
        jornadaT = new GenericaJornadaTipo();
        flag_tipoCalculo = false;
        setHorasCero();
    }

    public void preEditar(GenericaJornadaTipo gjt) {
        jornadaT = gjt;
        flag_tipoCalculo = jornadaT.getTipoCalculo() == 1;
    }

    public void modal(boolean opc) {
        if (opc) {
            PrimeFaces.current().executeScript("PF('crear_jornada_t_wv_dialog').show()");
        } else {
            PrimeFaces.current().executeScript("PF('crear_jornada_t_wv_dialog').hide()");
        }
    }

    public void validarJornadaT(int opc) {
        ok_save = false;
        int idJornadaT = 0;
        if (jornadaT.getIdGenericaJornadaTipo() != null) {
            idJornadaT = jornadaT.getIdGenericaJornadaTipo();
        }
        if (pau == null) {
            MovilidadUtil.addErrorMessage("Usuario sin area cargada");
            return;
        }
        GenericaJornadaTipo g = jornadaTEJB.findByDescripcion(jornadaT.getDescripcion(), idJornadaT, pau.getIdParamArea().getIdParamArea());
        if (g != null) {
            MovilidadUtil.addErrorMessage("Ya existe Jornada Tipo con este nombre");
            return;
        }

        if (jornadaT.getDescripcion() == null) {
            MovilidadUtil.addErrorMessage("Nombre de jornada tipo requerido");
            return;
        }
        if (jornadaT.getDescripcion().replaceAll("\\s", "").isEmpty()) {
            MovilidadUtil.addErrorMessage("Nombre de jornada tipo requerido");
            return;
        }
        if (jornadaT.getDescanso() == null) {
            MovilidadUtil.addErrorMessage("Nombre de jornada tipo requerido");
            return;
        }
        if (jornadaT.getDescanso().replaceAll("\\s", "").isEmpty()) {
            MovilidadUtil.addErrorMessage("Nombre de jornada tipo requerido");
            return;
        }
        int cont = 0;
        if (jornadaT.getHiniT1() == null || jornadaT.getHfinT1() == null) {
            cont++;
        } else if (jornadaT.getHiniT1().replaceAll("\\s", "").isEmpty() || jornadaT.getHfinT1().replaceAll("\\s", "").isEmpty()) {
            cont++;
        }
        if (jornadaT.getHiniT2() == null || jornadaT.getHfinT2() == null) {
            cont++;
        } else if (jornadaT.getHiniT2().replaceAll("\\s", "").isEmpty() || jornadaT.getHfinT2().replaceAll("\\s", "").isEmpty()) {
            cont++;
        }
        if (jornadaT.getHiniT3() == null || jornadaT.getHfinT3() == null) {
            cont++;
        } else if (jornadaT.getHiniT3().replaceAll("\\s", "").isEmpty() || jornadaT.getHfinT3().replaceAll("\\s", "").isEmpty()) {
            cont++;
        }

        if (cont == 3) {
            MovilidadUtil.addErrorMessage("No ha digitado el turno");
            return;
        }
        int dif1 = MovilidadUtil.diferencia(jornadaT.getHiniT1(), jornadaT.getHfinT1());
        if (dif1 < 0) {
            MovilidadUtil.addErrorMessage("La hora inicio turno 1 no puede ser mayor a la hora fin turno 1");
            return;
        }
        int dif2 = MovilidadUtil.diferencia(jornadaT.getHiniT2(), jornadaT.getHfinT2());
        if (dif2 < 0) {
            MovilidadUtil.addErrorMessage("La hora inicio turno 2 no puede ser mayor a la hora fin turno 2");
            return;
        }
        int dif3 = MovilidadUtil.diferencia(jornadaT.getHiniT3(), jornadaT.getHfinT3());
        if (dif3 < 0) {
            MovilidadUtil.addErrorMessage("La hora inicio turno 3 no puede ser mayor a la hora fin turno 3");
            return;
        }
        if ((MovilidadUtil.toSecs(jornadaT.getHfinT1())
                - MovilidadUtil.toSecs(jornadaT.getHiniT1()))
                > MovilidadUtil.toSecs(UtilJornada.getTotalHrsLaborales())) {

            if (!((MovilidadUtil.toSecs(jornadaT.getHfinT1())
                    - MovilidadUtil.toSecs(jornadaT.getHiniT1())
                    - MovilidadUtil.toSecs(UtilJornada.getTotalHrsLaborales())
                    - MovilidadUtil.toSecs(jornadaT.getDescanso()))
                    <= MovilidadUtil.toSecs(UtilJornada.getMaxHrsExtrasDia()))) {
                MovilidadUtil.addErrorMessage("Este turno tiene mas de 2 horas extras");
                return;
            }
        }
        ok_save = true;

    }

    public void guardarJornadaT() {
        if (!ok_save) {
            return;
        }
        String name = jornadaT.getDescripcion().replaceAll("\\s", "");
        jornadaT.setDescripcion(name);
        jornadaT.setTipoCalculo(flag_tipoCalculo ? 1 : 0);
        jornadaT.setEstadoReg(0);
        jornadaT.setCreado(MovilidadUtil.fechaCompletaHoy());
        jornadaT.setIdParamArea(pau.getIdParamArea());
        jornadaT.setUsername(user.getUsername());
        jornadaTEJB.create(jornadaT);
        MovilidadUtil.addSuccessMessage("Tipo de jornada registrada");
        jornadaT = new GenericaJornadaTipo();
        cargar();
        setHorasCero();
    }

    public void setHorasCero() {
        jornadaT.setHiniT1("00:00:00");
        jornadaT.setHfinT1("00:00:00");
        jornadaT.setHiniT2("00:00:00");
        jornadaT.setHfinT2("00:00:00");
        jornadaT.setHiniT3("00:00:00");
        jornadaT.setHfinT3("00:00:00");
        jornadaT.setDescanso("00:00:00");
    }

    @Transactional
    public void EditJornadaT() {
        if (!ok_save) {
            return;
        }
        jornadaT.setModificado(MovilidadUtil.fechaCompletaHoy());
        jornadaT.setTipoCalculo(flag_tipoCalculo ? 1 : 0);
        jornadaT.setUsername(user.getUsername());
        jornadaTEJB.edit(jornadaT);
        MovilidadUtil.addSuccessMessage("Tipo de jornada modificada");
        cargar();
        modal(false);
    }

    public GenericaJornadaTipo getJornadaT() {
        return jornadaT;
    }

    public void setJornadaT(GenericaJornadaTipo jornadaT) {
        this.jornadaT = jornadaT;
    }

    public List<GenericaJornadaTipo> getListJornadaT() {
        return listJornadaT;
    }

    public void setListJornadaT(List<GenericaJornadaTipo> listJornadaT) {
        this.listJornadaT = listJornadaT;
    }

    public boolean isFlag_tipoCalculo() {
        return flag_tipoCalculo;
    }

    public void setFlag_tipoCalculo(boolean flag_tipoCalculo) {
        this.flag_tipoCalculo = flag_tipoCalculo;
    }

}
