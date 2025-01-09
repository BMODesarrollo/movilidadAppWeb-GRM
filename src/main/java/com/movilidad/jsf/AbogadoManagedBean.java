package com.movilidad.jsf;

import com.movilidad.ejb.AccAbogadoFacadeLocal;
import com.movilidad.model.AccAbogado;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Permite parametrizar la data relacionada con los objetos AccAbogado Principal
 * tabla afectada acc_abogado
 *
 * @author Carlos Ballestas
 */
@Named(value = "abogadoBean")
@ViewScoped
public class AbogadoManagedBean implements Serializable {

    @EJB
    private AccAbogadoFacadeLocal accAbogadoEjb;

    private List<AccAbogado> lstAbogados;

    private AccAbogado abogado;
    private AccAbogado selected;

    private final PrimeFaces current = PrimeFaces.current();

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Permite crear la instancia del objeto AccAbogado
     */
    public void nuevo() {
        abogado = new AccAbogado();
        selected = null;
        current.focus("frmAbogado:cedula");
    }

    /**
     * Permite habilitar un formulario para la actualizacion de un objeto
     * AccAbogado
     */
    public void editar() {
        if (selected == null) {
            current.ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("Debe seleccionar un registro");
            return;
        }

        abogado = selected;
        current.executeScript("PF('abogadoDlg').show();");
        current.focus("frmAbogado:cedula");
    }

    /**
     * Permite persistir el objeto AccAbogago en la base de datos
     */
    public void guardar() {
        if (abogado.getCedula() == null || abogado.getCedula().equals("")) {
            MovilidadUtil.addErrorMessage("Debe ingresar la cédula del abogado");
            return;
        }
        if (abogado.getNombreCompleto() == null || abogado.getNombreCompleto().equals("")) {
            MovilidadUtil.addErrorMessage("Debe ingresar nombre del abogado");
            return;
        }
        if (abogado.getTarjetaProfesional() == null || abogado.getTarjetaProfesional().equals("")) {
            MovilidadUtil.addErrorMessage("Debe ingresar la tarjeta profesional");
            return;
        }

        abogado.setCreado(new Date());
        abogado.setEstadoReg(0);
        abogado.setUsername(user.getUsername());
        accAbogadoEjb.create(abogado);
        lstAbogados.add(abogado);
        nuevo();
        MovilidadUtil.addSuccessMessage("Abogado registrado éxitosamente");

    }

    /**
     * Permite realizar un update en la base de datos, sobre el objeto
     * AccAbogado
     */
    public void actualizar() {
        if (abogado.getCedula() == null || abogado.getCedula().equals("")) {
            MovilidadUtil.addErrorMessage("Debe ingresar la cédula del abogado");
            return;
        }
        if (abogado.getNombreCompleto() == null || abogado.getNombreCompleto().equals("")) {
            MovilidadUtil.addErrorMessage("Debe ingresar nombre del abogado");
            return;
        }
        if (abogado.getTarjetaProfesional() == null || abogado.getTarjetaProfesional().equals("")) {
            MovilidadUtil.addErrorMessage("Debe ingresar la tarjeta profesional");
            return;
        }

        abogado.setModificado(new Date());
        abogado.setUsername(user.getUsername());
        accAbogadoEjb.edit(abogado);
        current.executeScript("PF('abogadoDlg').hide();");
        selected = null;
        MovilidadUtil.addSuccessMessage("Datos actualizados éxitosamente");
    }

    /**
     * Permite obtener todos los objetos AccAbogado de la base de datos
     *
     * @return lista de objetos AccAbogado
     */
    public List<AccAbogado> getLstAbogados() {
        if (lstAbogados == null) {
            lstAbogados = accAbogadoEjb.findAll();
        }
        return lstAbogados;
    }

    public void setLstAbogados(List<AccAbogado> lstAbogados) {
        this.lstAbogados = lstAbogados;
    }

    public AccAbogado getAbogado() {
        return abogado;
    }

    public void setAbogado(AccAbogado abogado) {
        this.abogado = abogado;
    }

    public AccAbogado getSelected() {
        return selected;
    }

    public void setSelected(AccAbogado selected) {
        this.selected = selected;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }
}
