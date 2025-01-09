/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movilidad.jsf;

import com.movilidad.ejb.PqrProcedeFacadeLocal;
import com.movilidad.model.PqrProcede;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author ricardo.lopez
 */
@Named(value = "pqr_ProcedeJSF")
@ViewScoped
public class PqrProcedeJSF implements Serializable {

    @EJB
    private PqrProcedeFacadeLocal pqrProcedeFacadeLocal;
    private PqrProcede pqrProcede;
    private PqrProcede selected;
    private List<PqrProcede> listaprocedesino;
    private int iden_PqrProcede = 0;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private String obj_proc_nomb;
    private String obj_proc_desc;

    @PostConstruct
    public void init() {
        listaprocedesino = pqrProcedeFacadeLocal.findAll();
    }

    public PqrProcedeJSF() {
    }

//    public void ins_bton_news() {
//        obj_proc_nomb = "";
//        obj_proc_desc = "";
//        selected = null;
//    }
    public void guardar() {
        try {
            if (pqrProcede != null) {
                pqrProcede.setObj_proc_crea(new Date());
                pqrProcede.setObj_proc_esta(0);
                pqrProcede.setObj_proc_user(user.getUsername());
                pqrProcedeFacadeLocal.create(pqrProcede);
                MovilidadUtil.addSuccessMessage("Se a registrado el tipo procede correctamente");
                pqrProcede = new PqrProcede(iden_PqrProcede);
                iden_PqrProcede = 0;
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar tipo procede");
        }
    }

    public void actualizar() {

    }

    public void prepareGuardar() {
        pqrProcede = new PqrProcede();
    }

    public void prepareEditar() {
        if (pqrProcede != null) {

        }
    }

    public void reset() {
        iden_PqrProcede = 0;
        pqrProcede = null;
    }

    public void onRowSelect(SelectEvent event) {
        pqrProcede = (PqrProcede) event.getObject();
    }

    public PqrProcedeFacadeLocal getPqrProcedeFacadeLocal() {
        return pqrProcedeFacadeLocal;
    }

    public void setPqrProcedeFacadeLocal(PqrProcedeFacadeLocal PqrProcedeFacadeLocal) {
        this.pqrProcedeFacadeLocal = PqrProcedeFacadeLocal;
    }

    public PqrProcede getPqrProcede() {
        return pqrProcede;
    }

    public void setPqrProcede(PqrProcede PqrProcede) {
        this.pqrProcede = PqrProcede;
    }

    public List<PqrProcede> getListaprocedesino() {
        return listaprocedesino;
    }

    public void setListaprocedesino(List<PqrProcede> listaprocedesino) {
        this.listaprocedesino = listaprocedesino;
    }

    public int getIden_PqrProcede() {
        return iden_PqrProcede;
    }

    public void setIden_PqrProcede(int iden_PqrProcede) {
        this.iden_PqrProcede = iden_PqrProcede;
    }
}
