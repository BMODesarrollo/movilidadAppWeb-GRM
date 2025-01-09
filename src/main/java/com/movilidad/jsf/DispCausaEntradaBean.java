package com.movilidad.jsf;

import com.movilidad.ejb.DispCausaEntradaFacadeLocal;
import com.movilidad.ejb.DispSistemaFacadeLocal;
import com.movilidad.model.DispSistema;
import com.movilidad.model.DispCausaEntrada;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Jesús Jimenez
 */
@Named(value = "dispCausaEntradaBean")
@ViewScoped
public class DispCausaEntradaBean implements Serializable {

    @EJB
    private DispCausaEntradaFacadeLocal dispCausaEntradaEjb;
    @EJB
    private DispSistemaFacadeLocal dispSistemaEjb;

    private DispCausaEntrada dispCausaEntrada;
    private DispCausaEntrada selected;
    private DispSistema dispSistema;
    private String nombre;
    private int i_disp_sistema;

    private boolean isEditing;

    private List<DispCausaEntrada> lstDispCausaEntradas;
    private List<DispSistema> lstDispSistema;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    void consultar() {
        lstDispCausaEntradas = dispCausaEntradaEjb.findByEstadoReg();
    }

    /**
     * Prepara la lista de tipos de eventos antes de registrar/modificar un
     * registro.
     */
    public void prepareListDisSistema() {
        lstDispSistema = dispSistemaEjb.findAllByEstadoReg();
    }

    /**
     * Evento que se dispara al seleccionar el tipo de evento en el modal que
     * muestra listado de tipos
     *
     * @param event
     */
    public void onRowDispSistemaClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof DispSistema) {
            dispSistema = (DispSistema) event.getObject();
        }
    }

    public void nuevo() {
        nombre = "";
        dispCausaEntrada = new DispCausaEntrada();
        selected = null;
        i_disp_sistema = 0;
        isEditing = false;
        prepareListDisSistema();
    }

    public void editar() {
        isEditing = true;
        dispSistema = selected.getIdDispSistema();
        nombre = selected.getNombre();
        i_disp_sistema = dispSistema.getIdDispSistema();
        prepareListDisSistema();
        dispCausaEntrada = selected;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    public void guardarTransactional() {
        String validacion = validarDatos();
        if (validacion == null) {

            dispCausaEntrada.setNombre(nombre);
            dispCausaEntrada.setIdDispSistema(new DispSistema(i_disp_sistema));

            if (isEditing) {

                dispCausaEntrada.setModificado(MovilidadUtil.fechaCompletaHoy());
                dispCausaEntrada.setUsername(user.getUsername());
                dispCausaEntradaEjb.edit(dispCausaEntrada);

                PrimeFaces.current().executeScript("PF('dlg_disp_causa_entrada').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {

                dispCausaEntrada.setEstadoReg(0);
                dispCausaEntrada.setCreado(MovilidadUtil.fechaCompletaHoy());
                dispCausaEntrada.setUsername(user.getUsername());
                dispCausaEntradaEjb.create(dispCausaEntrada);
                lstDispCausaEntradas.add(dispCausaEntrada);

                nuevo();
                MovilidadUtil.addSuccessMessage("Registro agregado éxitosamente");
            }
            consultar();

        } else {
            MovilidadUtil.addErrorMessage(validacion);
        }
    }

    private String validarDatos() {

        if (i_disp_sistema == 0) {
            return "Debe seleccionar un Sistema.";
        }
        int idDispCausaEntrada = dispCausaEntrada.getIdDispCausaEntrada() == null ? 0 : dispCausaEntrada.getIdDispCausaEntrada();
        if (dispCausaEntradaEjb.findByNombreByIdDispSistema(nombre.trim(), idDispCausaEntrada, i_disp_sistema) != null) {
            return "YA existe un registro con el nombre a ingresar para el Sistema seleccionado";
        }

        return null;
    }

    public DispCausaEntrada getDispCausaEntrada() {
        return dispCausaEntrada;
    }

    public void setDispCausaEntrada(DispCausaEntrada cableEventoTipoDet) {
        this.dispCausaEntrada = cableEventoTipoDet;
    }

    public DispCausaEntrada getSelected() {
        return selected;
    }

    public void setSelected(DispCausaEntrada selected) {
        this.selected = selected;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public DispSistema getDispSistema() {
        return dispSistema;
    }

    public void setDispSistema(DispSistema cableEventoTipo) {
        this.dispSistema = cableEventoTipo;
    }

    public List<DispSistema> getLstDispSistema() {
        return lstDispSistema;
    }

    public void setLstDispSistema(List<DispSistema> lstDispSistema) {
        this.lstDispSistema = lstDispSistema;
    }

    public List<DispCausaEntrada> getLstDispCausaEntradas() {
        return lstDispCausaEntradas;
    }

    public void setLstDispCausaEntradas(List<DispCausaEntrada> lstDispCausaEntradas) {
        this.lstDispCausaEntradas = lstDispCausaEntradas;
    }

    public int getI_disp_sistema() {
        return i_disp_sistema;
    }

    public void setI_disp_sistema(int i_disp_sistema) {
        this.i_disp_sistema = i_disp_sistema;
    }

}
