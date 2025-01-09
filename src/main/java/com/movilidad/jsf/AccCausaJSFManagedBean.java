package com.movilidad.jsf;

import com.movilidad.ejb.AccCausaFacadeLocal;
import com.movilidad.model.AccArbol;
import com.movilidad.model.AccCausa;
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
 * Permite parametrizar la data relacionada con los objetos AccCausa Principal
 * tabla afectada acc_causa
 *
 * @author Carlos Ballestas
 */
@Named(value = "accCausaBean")
@ViewScoped
public class AccCausaJSFManagedBean implements Serializable {

    @EJB
    private AccCausaFacadeLocal accCausaEjb;

    private AccCausa causa;
    private AccCausa selected;
    private int arbol;

    private List<AccCausa> lista;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
//        selected = null;
        arbol = 0;
    }

    /**
     *
     * @param event Objeto AccCausa seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        if (event.getObject() instanceof AccCausa) {
            setSelected((AccCausa) event.getObject());
        }
    }

    /**
     * permite generar la instancia del objeto AccCausa causa
     */
    public void nuevo() {
        arbol = 0;
        causa = new AccCausa();
        selected = null;
    }

    /**
     * permite igualar la referencia del objeto seleccionado al objeto AccCausa
     * causa
     */
    public void editar() {
        causa = selected;
        arbol = causa.getIdAccArbol().getIdAccArbol();
    }

    /**
     * permite persistir el objeto AccCausa en bd
     */
    public void guardar() {
        causa.setCreado(new Date());
        causa.setEstadoReg(0);
        causa.setUsername(user.getUsername());
        causa.setIdAccArbol(new AccArbol(arbol));
        accCausaEjb.create(causa);
        nuevo();
        MovilidadUtil.addSuccessMessage("Registro realizado éxitosamente");
    }

    /**
     * permite actualizar el objeto AccCausa en bd
     */
    public void actualizar() {
        causa.setUsername(user.getUsername());
        causa.setIdAccArbol(new AccArbol(arbol));
        causa.setModificado(new Date());
        accCausaEjb.edit(causa);
        PrimeFaces.current().executeScript("PF('causaDlg').hide();");
        MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
    }

    public AccCausa getSelected() {
        return selected;
    }

    public void setSelected(AccCausa selected) {
        this.selected = selected;
    }

    public List<AccCausa> getLista() {
        lista = accCausaEjb.estadoReg();
        return lista;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public AccCausa getCausa() {
        return causa;
    }

    public void setCausa(AccCausa causa) {
        this.causa = causa;
    }

    public int getArbol() {
        return arbol;
    }

    public void setArbol(int arbol) {
        this.arbol = arbol;
    }
}
