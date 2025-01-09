/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableCabinaFacadeLocal;
import com.movilidad.ejb.CableOperacionCabinaFacadeLocal;
import com.movilidad.ejb.CablePinzaFacadeLocal;
import com.movilidad.model.CableCabina;
import com.movilidad.model.CableOperacionCabina;
import com.movilidad.model.CablePinza;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
 * @author solucionesit
 */
@Named(value = "cabinaOperacionJSFMB")
@ViewScoped
public class CabinaOperacionJSFManagedBean implements Serializable {

    /**
     * Creates a new instance of CabinaOperacionJSFManagedBean
     */
    public CabinaOperacionJSFManagedBean() {
    }

    @EJB
    private CableOperacionCabinaFacadeLocal cableOperacionCabinaEJB;
    @EJB
    private CableCabinaFacadeLocal cableCabinaEJB;
    @EJB
    private CablePinzaFacadeLocal cablePinzaEJB;

    private List<CableOperacionCabina> list;
    private List<CablePinza> listCablePinza;
    private List<CableCabina> listCableCabina;

    private int filtroV;
    private boolean flag_cabinas_cargadas = true;
    private Date fecha = MovilidadUtil.fechaHoy();
    private CableCabina cabina;
    private CableOperacionCabina operacionCabina;
    private int i_idCablePinza;

    UserExtended user;

    @PostConstruct
    public void init() {
        consultar(3);
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
     * Preparar el metodo consultar para obtener las cabinas segun el radio
     * button selecionado en vista
     */
    public void filtrar() {
        if (filtroV == 0) {
            consultar(0);
            consultar(filtroV);
        } else {
            consultar(filtroV);
        }
    }

    /**
     * Metodo responsable de consulatar un registro de aseo cabina y alterar la
     * ubicacion que ocupa en la vista, cambia el fondo a rojo si el atributo
     * operando es igual a 0.
     *
     * @param coc
     */
    public void estado(CableOperacionCabina coc) {
        String id = "form_cabina_operacion:dg_cabina_id:" + list.indexOf(coc) + ":cb";
        String id_am = "form_cabina_operacion:dg_cabina_id:" + list.indexOf(coc) + ":id_am";
        String id_pm = "form_cabina_operacion:dg_cabina_id:" + list.indexOf(coc) + ":id_pm";
        if (coc != null) {
            String valor_am = "AM:";
            String valor_pm = "PM:";
            if (coc.getBateriaAm() != null) {
                valor_am = (String) (coc.getBateriaAm() == -99 ? "AM G" : "AM " + coc.getBateriaAm());
            }
            if (coc.getBateriaPm() != null) {
                valor_pm = (String) (coc.getBateriaPm() == -99 ? "PM G" : "PM " + coc.getBateriaPm());
            }

            PrimeFaces.current().executeScript("document.getElementById('" + id_am + "').innerHTML = '" + valor_am + "';");
            PrimeFaces.current().executeScript("document.getElementById('" + id_pm + "').innerHTML = '" + valor_pm + "';");
            if (coc.getOperando().equals(0)) {
                MovilidadUtil.setColorBackground(id, "red");
            }
        }
    }

    /**
     * Settea -99 al operacionCabina al hacer clic sobre el boton de la vista.
     *
     * @param opc
     */
    public void metodoG(int opc) {
        if (opc == 1) {
            operacionCabina.setBateriaAm(-99);
        } else {
            operacionCabina.setBateriaPm(-99);
        }
    }

    /**
     * Preparar variables para un nuevo registro.
     *
     * @param coc
     */
    public void registrarOperacion(CableOperacionCabina coc) {
        operacionCabina = coc;
        i_idCablePinza = operacionCabina.getIdCablePinzaParaHoy() == null
                ? 0 : operacionCabina.getIdCablePinzaParaHoy().getIdCablePinza();
        listCablePinza = cablePinzaEJB.findAllByEstadoReg();
        MovilidadUtil.openModal("gestion_dialog_wv");
    }

    /**
     * Metodo encargado de persistir en base de datos un nuevo registro de
     * operacion cabina, Metodo Transactional.
     *
     * @param opc
     */
    @Transactional
    public void saveTransactional(int opc) {
        operacionCabina.setOperando(opc);
        System.out.println("pinza-->" + i_idCablePinza);
        if (i_idCablePinza != 0) {
            operacionCabina.setIdCablePinzaParaHoy(new CablePinza(i_idCablePinza));
        } else {
            operacionCabina.setIdCablePinzaParaHoy(null);
        }
        operacionCabina.setModificado(MovilidadUtil.fechaCompletaHoy());
        cableOperacionCabinaEJB.edit(operacionCabina);
        MovilidadUtil.addSuccessMessage("Acción Completada Con exito.");
    }

    /**
     * Metodo encargado de invcocar a los metodos saveTransactional y consultar
     * para el proceso de persistir una nueva operacion cabina.
     *
     * @param opc
     */
    public void save(int opc) {
        saveTransactional(opc);
        consultar(0);
        MovilidadUtil.hideModal("gestion_dialog_wv");
    }

    public void limpiarTablero() {
    }

    /**
     * Consultar cabinas (Todas, en operacion, no operando)
     *
     * @param opc
     */
    public void consultar(int opc) {

        list = cableOperacionCabinaEJB.findAllByFecha(fecha, "ASC");
        if (list.isEmpty()) {
            filtroV = 0;
            MovilidadUtil.addAdvertenciaMessage("No hay cabinas cargadas para la fecha.");
        }
        /**
         * Se utiliza para saber si hay cabinas cargadas.
         */
        if (opc == 3) {
            flag_cabinas_cargadas = list.isEmpty();
            return;
        }
        /**
         * Todas las cabinas.
         */
        if (opc == 0) {
            filtroV = 0;
            return;
        }
        /**
         * Cabinas lavadas.
         */
        if (opc == 1) {
            list = cableOperacionCabinaEJB.findFechaAndOperando(fecha, "ASC", 1);
        }
        /**
         * Por lavar.
         */
        if (opc == 2) {
            list = cableOperacionCabinaEJB.findFechaAndOperando(fecha, "ASC", 0);
        }
    }

    public void cargarCabinas() {
//        listCableCabina = cableCabinaEJB.findAllByEstadoRegAndFechaFinOp(fecha);
        listCableCabina = cableCabinaEJB.findAllByEstadoReg();
        for (CableCabina cc : listCableCabina) {
            CableOperacionCabina coc = new CableOperacionCabina();
            coc.setIdCableCabina(cc);
            coc.setCreado(MovilidadUtil.fechaCompletaHoy());
            coc.setUsername(user.getUsername());
            coc.setIdCablePinza(cc.getIdCablePinza());
            coc.setFecha(fecha);
            coc.setUsername(user.getUsername());
            coc.setOperando(1);
            cableOperacionCabinaEJB.create(coc);
        }
        consultar(3);
        MovilidadUtil.addSuccessMessage("Acción finalizada con exito");
    }

    public List<CableOperacionCabina> getList() {
        return list;
    }

    public void setList(List<CableOperacionCabina> list) {
        this.list = list;
    }

    public List<CableCabina> getListCableCabina() {
        return listCableCabina;
    }

    public void setListCableCabina(List<CableCabina> listCableCabina) {
        this.listCableCabina = listCableCabina;
    }

    public int getFiltroV() {
        return filtroV;
    }

    public void setFiltroV(int filtroV) {
        this.filtroV = filtroV;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public CableOperacionCabina getOperacionCabina() {
        return operacionCabina;
    }

    public void setOperacionCabina(CableOperacionCabina operacionCabina) {
        this.operacionCabina = operacionCabina;
    }

    public List<CablePinza> getListCablePinza() {
        return listCablePinza;
    }

    public void setListCablePinza(List<CablePinza> listCablePinza) {
        this.listCablePinza = listCablePinza;
    }

    public int getI_idCablePinza() {
        return i_idCablePinza;
    }

    public void setI_idCablePinza(int i_idCablePinza) {
        this.i_idCablePinza = i_idCablePinza;
    }

    public boolean isFlag_cabinas_cargadas() {
        return flag_cabinas_cargadas;
    }

    public void setFlag_cabinas_cargadas(boolean flag_cabinas_cargadas) {
        this.flag_cabinas_cargadas = flag_cabinas_cargadas;
    }

}
