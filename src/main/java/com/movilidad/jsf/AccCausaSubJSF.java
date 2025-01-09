package com.movilidad.jsf;

import com.movilidad.ejb.AccCausaSubFacadeLocal;
import com.movilidad.model.AccCausa;
import com.movilidad.model.AccCausaSub;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * * Permite parametrizar la data relacionada con los objetos AccCausaSub
 * Principal tabla afectada acc_causa_sub
 *
 * @author HP
 */
@Named(value = "accCausaSubJSF")
@ViewScoped
public class AccCausaSubJSF implements Serializable {

    @EJB
    private AccCausaSubFacadeLocal accCausaSubFacadeLocal;

    private AccCausaSub accCausaSub;

    private List<AccCausaSub> listAccCausaSub;

    private int i_idAccCausa = 0;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public AccCausaSubJSF() {
    }

    /**
     * Permite persistir la data del objeto AccCausaSub en la base de datos
     */
    public void guardar() {
        try {
            if (accCausaSub != null) {
                accCausaSub.setIdCausa(new AccCausa(i_idAccCausa));
                accCausaSub.setSubcausa(accCausaSub.getSubcausa().toUpperCase());
                accCausaSub.setCreado(new Date());
                accCausaSub.setModificado(new Date());
                accCausaSub.setEstadoReg(0);
                accCausaSub.setUsername(user.getUsername());
                accCausaSubFacadeLocal.create(accCausaSub);
                MovilidadUtil.addSuccessMessage("Se a registrado Acc CausaSub correctamente");
                accCausaSub = new AccCausaSub();
                i_idAccCausa = 0;
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar Acc CausaSub");
        }
    }

    /**
     * Permite realizar un update del objeto AccCausaSub en la base de datos
     */
    public void actualizar() {
        try {
            if (accCausaSub != null) {
                accCausaSub.setIdCausa(new AccCausa(i_idAccCausa));
                accCausaSub.setSubcausa(accCausaSub.getSubcausa().toUpperCase());
                accCausaSubFacadeLocal.edit(accCausaSub);
                MovilidadUtil.addSuccessMessage("Se a actualizado Acc CausaSub correctamente");
                reset();
                PrimeFaces.current().executeScript("PF('causa-sub-editDlg').hide();");
            }
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al actualizar Acc CausaSub");
        }
    }

    /**
     * Permite crear la instancia del objeto AccCausaSub
     */
    public void prepareGuardar() {
        accCausaSub = new AccCausaSub();
    }

    public void prepareEditar() {
        if (accCausaSub != null) {
            i_idAccCausa = accCausaSub.getIdCausa().getIdAccCausa();
        }
    }

    public void reset() {
        i_idAccCausa = 0;
        accCausaSub = null;
    }

    /**
     *
     * @param event Objeto AccCausaSub seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        accCausaSub = (AccCausaSub) event.getObject();
    }

    public AccCausaSub getAccCausaSub() {
        return accCausaSub;
    }

    public void setAccCausaSub(AccCausaSub accCausaSub) {
        this.accCausaSub = accCausaSub;
    }

    public List<AccCausaSub> getListAccCausaSub() {
        listAccCausaSub = accCausaSubFacadeLocal.estadoReg();
        return listAccCausaSub;
    }

    public int getI_idAccCausa() {
        return i_idAccCausa;
    }

    public void setI_idAccCausa(int i_idAccCausa) {
        this.i_idAccCausa = i_idAccCausa;
    }

}
