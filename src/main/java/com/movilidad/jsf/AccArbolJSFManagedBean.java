package com.movilidad.jsf;

import com.movilidad.ejb.AccArbolFacadeLocal;
import com.movilidad.model.AccArbol;
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
 * Permite parametrizar la data relacionada con los objetos AccArbol Principal
 * tabla afectada acc_arbol
 *
 * @author Carlos Ballestas
 */
@Named(value = "accArbolBean")
@ViewScoped
public class AccArbolJSFManagedBean implements Serializable {

    @EJB
    private AccArbolFacadeLocal accArbolEjb;

    private AccArbol arbol;
    private AccArbol selected;

    private List<AccArbol> lista;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
//        selected = null;
    }

    /**
     *
     * @param event Objeto AccArbol seleccionado por el usuario
     */
    public void onRowSelect(SelectEvent event) {
        if (event.getObject() instanceof AccArbol) {
            setSelected((AccArbol) event.getObject());
        }
    }

    /**
     * permite generar la instancia del objeto AccArbol arbol
     */
    public void nuevo() {
        arbol = new AccArbol();
        selected = null;
    }

    /**
     * permite igualar la referencia del objeto selected al objeto arbol
     */
    public void editar() {
        arbol = selected;
    }

    /**
     * Permite persistir el objero AccArbol arbol en bd
     */
    public void guardar() {
        arbol.setCreado(new Date());
        arbol.setEstadoReg(0);
        arbol.setUsername(user.getUsername());
        accArbolEjb.create(arbol);
        nuevo();
        MovilidadUtil.addSuccessMessage("Registro realizado éxitosamente");
    }

    /**
     * Permite actualizar el objero AccArbol arbol en bd
     */
    public void actualizar() {
        arbol.setUsername(user.getUsername());
        arbol.setModificado(new Date());
        accArbolEjb.edit(arbol);
        PrimeFaces.current().executeScript("PF('arbolDlg').hide();");
        MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
    }

    public AccArbol getArbol() {
        return arbol;
    }

    public void setArbol(AccArbol arbol) {
        this.arbol = arbol;
    }

    public AccArbol getSelected() {
        return selected;
    }

    public void setSelected(AccArbol selected) {
        this.selected = selected;
    }

    public List<AccArbol> getLista() {
        lista = accArbolEjb.estadoReg();
        return lista;
    }

    public void setLista(List<AccArbol> lista) {
        this.lista = lista;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }
}
