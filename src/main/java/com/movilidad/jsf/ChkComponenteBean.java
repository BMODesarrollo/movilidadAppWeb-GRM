package com.movilidad.jsf;

import com.movilidad.ejb.ChkComponenteFacadeLocal;
import com.movilidad.model.ChkComponente;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "chkComponenteBean")
@ViewScoped
public class ChkComponenteBean implements Serializable {

    @EJB
    private ChkComponenteFacadeLocal componenteEjb;

    @Inject
    private NotificaListComponentBean notificaListComponentBean;

    private ChkComponente componente;
    private ChkComponente selected;

    private boolean b_lista;
    private boolean b_textoLibre;
    private boolean flagEdit;

    private List<ChkComponente> lstComponentes;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    public void nuevo() {
        componente = new ChkComponente();
        notificaListComponentBean.setProceso(null);
        selected = null;
        b_lista = true;
        b_textoLibre = false;
        flagEdit = false;
    }

    public void editar() {
        flagEdit = true;
        componente = selected;
        notificaListComponentBean.setProceso(selected.getIdNotificacionProceso());
        b_textoLibre = (componente.getTextoLibre() == 1);
        b_lista = (componente.getLista() == 1);
    }

    public void guardar() {
        String validacion = validarDatos();

        if (validacion == null) {
            componente.setLista(b_lista ? 1 : 0);
            componente.setIdNotificacionProceso(notificaListComponentBean.getProceso());
            componente.setTextoLibre(b_textoLibre ? 1 : 0);
            componente.setUsername(user.getUsername());

            if (flagEdit) {
                componente.setModificado(MovilidadUtil.fechaCompletaHoy());
                componenteEjb.edit(componente);
                PrimeFaces.current().executeScript("PF('wlgChkComponente').hide();");
                MovilidadUtil.addSuccessMessage("Registro modificado éxitosamente");

            } else {
                componente.setCreado(MovilidadUtil.fechaCompletaHoy());
                componente.setEstadoReg(0);
                componenteEjb.create(componente);
                lstComponentes.add(componente);
                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }

            consultar();

        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }

    }

    public void ajustarParametro(int flag) {
        if (flag == 0) {
            b_textoLibre = false;
        } else {
            b_lista = false;
        }
    }

    private void consultar() {
        lstComponentes = componenteEjb.findAllByEstadoReg();
    }

    private String validarDatos() {
        
        if (!b_lista && !b_textoLibre) {
            return "DEBE especificar si es lista ó texto libre";
        }

        if (flagEdit) {
            if (componenteEjb.findByNombre(selected.getIdChkComponente(), componente.getNombre()) != null) {
                return "El nombre del componente YA EXISTE";
            }
        } else {
            if (componenteEjb.findByNombre(0, componente.getNombre()) != null) {
                return "El componente a registrar YA EXISTE";
            }
        }

        return null;

    }

    public ChkComponente getComponente() {
        return componente;
    }

    public void setComponente(ChkComponente componente) {
        this.componente = componente;
    }

    public ChkComponente getSelected() {
        return selected;
    }

    public void setSelected(ChkComponente selected) {
        this.selected = selected;
    }

    public boolean isB_lista() {
        return b_lista;
    }

    public void setB_lista(boolean b_lista) {
        this.b_lista = b_lista;
    }

    public boolean isB_textoLibre() {
        return b_textoLibre;
    }

    public void setB_textoLibre(boolean b_textoLibre) {
        this.b_textoLibre = b_textoLibre;
    }

    public boolean isFlagEdit() {
        return flagEdit;
    }

    public void setFlagEdit(boolean flagEdit) {
        this.flagEdit = flagEdit;
    }

    public List<ChkComponente> getLstComponentes() {
        return lstComponentes;
    }

    public void setLstComponentes(List<ChkComponente> lstComponentes) {
        this.lstComponentes = lstComponentes;
    }

}
