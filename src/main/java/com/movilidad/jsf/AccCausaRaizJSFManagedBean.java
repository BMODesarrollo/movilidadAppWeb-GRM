package com.movilidad.jsf;

import com.movilidad.ejb.AccCausaRaizFacadeLocal;
import com.movilidad.model.AccCausaRaiz;
import com.movilidad.model.AccCausaSub;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Permite parametrizar la data relacionada con los objetos AccCausaRaiz
 * Principal tabla afectada acc_causa_raiz
 *
 * @author Carlos Ballestas
 */
@Named(value = "accCausaRaizBean")
@ViewScoped
public class AccCausaRaizJSFManagedBean implements Serializable {

    @EJB
    private AccCausaRaizFacadeLocal accCausaRaizEjb;

    private AccCausaRaiz causaRaiz;
    private AccCausaRaiz selected;
    private int subCausa;

    private List<AccCausaRaiz> lista;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
//        selected = null;
        subCausa = 0;
    }

    /**
     *
     * @param event Objeto AccCausaRaiz seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        if (event.getObject() instanceof AccCausaRaiz) {
            setSelected((AccCausaRaiz) event.getObject());
        }
    }

    /**
     * permite generar la instancia del objeto AccCausaRaiz causaRaiz
     */
    public void nuevo() {
        subCausa = 0;
        causaRaiz = new AccCausaRaiz();
        selected = null;
    }

    /**
     * permite igualar la referencia del objeto seleccionado al objeto
     * AccCausaRaiz causaRaiz
     */
    public void editar() {
        causaRaiz = selected;
        subCausa = causaRaiz.getIdAccSubcausa().getIdAccSubcausa();
    }

    /**
     * permite persistir el objeto AccCausaRaiz en bd
     */
    public void guardar() {
        causaRaiz.setCreado(new Date());
        causaRaiz.setModificado(new Date());
        causaRaiz.setEstadoReg(0);
        causaRaiz.setUsername(user.getUsername());
        causaRaiz.setIdAccSubcausa(new AccCausaSub(subCausa));
        accCausaRaizEjb.create(causaRaiz);
        nuevo();
        MovilidadUtil.addSuccessMessage("Registro realizado éxitosamente");
    }

    /**
     * permite actualizar el objeto AccCausaRaiz en bd
     */
    public void actualizar() {
        causaRaiz.setUsername(user.getUsername());
        causaRaiz.setIdAccSubcausa(new AccCausaSub(subCausa));
        causaRaiz.setModificado(new Date());
        accCausaRaizEjb.edit(causaRaiz);
        PrimeFaces.current().executeScript("PF('causa_raizDlg').hide();");
        MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
    }

    public AccCausaRaiz getSelected() {
        return selected;
    }

    public void setSelected(AccCausaRaiz selected) {
        this.selected = selected;
    }

    public List<AccCausaRaiz> getLista() {
        lista = accCausaRaizEjb.estadoReg();
        return lista;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public AccCausaRaiz getCausaRaiz() {
        return causaRaiz;
    }

    public void setCausaRaiz(AccCausaRaiz causaRaiz) {
        this.causaRaiz = causaRaiz;
    }

    public int getSubCausa() {
        return subCausa;
    }

    public void setSubCausa(int subCausa) {
        this.subCausa = subCausa;
    }
}
