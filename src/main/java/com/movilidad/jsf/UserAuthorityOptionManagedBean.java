package com.movilidad.jsf;

import com.movilidad.ejb.UserAuthorityOptionFacadeLocal;
import com.movilidad.ejb.OpcionFacadeLocal;
import com.movilidad.ejb.UserAuthorityFacadeLocal;
import com.movilidad.model.UserAuthorityOption;
import com.movilidad.model.Opcion;
import com.movilidad.model.UserAuthority;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "userAuthorityOptBean")
@ViewScoped
public class UserAuthorityOptionManagedBean implements Serializable {

    @EJB
    private UserAuthorityOptionFacadeLocal userAuthorityOptionEjb;
    @EJB
    private UserAuthorityFacadeLocal userAuthorityEjb;
    @EJB
    private OpcionFacadeLocal opcionesEjb;

    private UserAuthorityOption userAuthorityOption;
    private Opcion opcion;
    private UserAuthorityOption selected;
    private UserAuthority userAuthority;

    private List<UserAuthorityOption> lstUserAuthorityOptions;
    private List<Opcion> lstOpciones;
    private List<Opcion> lstOpcionesAux;
    private List<UserAuthority> lstUserAuthority;

    private boolean isEditing;

    private final PrimeFaces current = PrimeFaces.current();

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public void nuevo() {
        userAuthority = null;
        userAuthorityOption = new UserAuthorityOption();
        opcion = null;
        lstOpciones = new ArrayList<>();
        selected = null;
        isEditing = false;
    }

    public void prepareListUserAuthority() {
        lstUserAuthority = null;

        lstUserAuthority = userAuthorityEjb.findAll();
        userAuthority = new UserAuthority();

        if (isEditing) {
            current.ajax().update("frmCheckListEdit:tipo_evento");
        } else {
            current.ajax().update("frmCheckListAdd:tipo_evento");
        }
    }

    public void prepareListOpcion() {
        lstOpcionesAux = null;

        lstOpcionesAux = opcionesEjb.getAllData();

        opcion = new Opcion();

        if (isEditing) {
            current.ajax().update("frmCheckListEdit:tipo_documento");
        }
    }

    public void onRowUserAuthorityClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof UserAuthority) {
            setUserAuthority((UserAuthority) event.getObject());
        }
        if (!isEditing) {
            current.executeScript("PF('dtNovedadTipoDetalle').clearFilters();");
            current.ajax().update(":frmPmNovedadTipoDetalleList:dtNovedadTipoDetalles");
        } else {
            current.executeScript("PF('PmNovedadTipoDetalleListDialogEdit').clearFilters();");
            current.ajax().update(":frmPmNovedadTipoDetalleListEdit:dtNovedadTipoDetalles");
        }
    }

    public void onRowOpcionClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof Opcion) {
            setOpcion((Opcion) event.getObject());
            if (!isEditing) {
                if (lstOpciones.contains(opcion)) {
                    current.ajax().update(":msgs");
                    MovilidadUtil.addErrorMessage("La opción seleccionada ya se encuentra en la lista");
                    return;
                }
                lstOpciones.add(opcion);
                current.executeScript("PF('dtAccTipoDoc').clearFilters();");
                current.ajax().update(":frmAccTipoDocsList:dtAccTipoDocs");
            } else {
                current.executeScript("PF('dtAccTipoDocEdit').clearFilters();");
                current.ajax().update(":frmAccTipoDocsListEdit:dtAccTipoDocs");
            }
        }
    }

    public void eliminarOpcionLista(Opcion atd) {
        lstOpciones.remove(atd);
        current.ajax().update(":msgs");
        MovilidadUtil.addSuccessMessage("La opción ("+atd.getNameop()+") ha sido eliminada de la lista");
    }

    public void editar() {
        if (selected == null) {
            current.ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("Debe seleccionar un registro");
            return;
        }
        isEditing = true;
        userAuthorityOption = selected;
        userAuthority = userAuthorityOption.getIdUserAuthority();
        opcion = userAuthorityOption.getIdOpcion();

        current.executeScript("PF('checklistEditDlg').show();");
    }

    public void guardar() {
        if (!validarDatos()) {
            for (Opcion acd : lstOpciones) {
                if (userAuthorityOptionEjb.verificarRepetido(userAuthority.getIdUserAuthority(), acd.getId()) == null) {
                    userAuthorityOption.setIdOpcion(acd);
                    userAuthorityOption.setIdUserAuthority(userAuthority);
                    userAuthorityOption.setCreado(new Date());
                    userAuthorityOption.setEstadoReg(0);
                    userAuthorityOption.setUsername(user.getUsername());
                    userAuthorityOptionEjb.create(userAuthorityOption);
                    lstUserAuthorityOptions.add(userAuthorityOption);
                } else {
                    MovilidadUtil.addErrorMessage("Está intentando ingresar un registro con rol (" + userAuthority.getAuthority() + ") y opción (" + acd.getNameop() + "), el cúal YA EXISTE.");
                    return;
                }
            }
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro guardado éxitosamente");
        }
    }

    private boolean validarDatos() {
        if (userAuthority == null || userAuthority.getAuthority() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un authority");
            return true;
        }

        if (!isEditing) {
            if (lstOpciones == null || lstOpciones.isEmpty()) {
                MovilidadUtil.addErrorMessage("Debe seleccionar al menos una opción");
                return true;
            }
        } else {
            if (opcion == null || opcion.getDescripcion() == null) {
                MovilidadUtil.addErrorMessage("Debe seleccionar una opción");
                return true;
            }
        }

        return false;
    }

    public void actualizar() {
        if (!validarDatos()) {
            if (!verificarActualizar()) {
                userAuthorityOption.setIdOpcion(opcion);
                userAuthorityOption.setIdUserAuthority(userAuthority);
                userAuthorityOption.setModificado(new Date());
                userAuthorityOption.setUsername(user.getUsername());
                userAuthorityOptionEjb.edit(userAuthorityOption);
                selected = null;
                current.executeScript("PF('checklistEditDlg').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                MovilidadUtil.addErrorMessage("Está intentando ingresar un registro con rol (" + userAuthority.getAuthority() + ") y opción (" + opcion.getDescripcion() + "), el cúal YA EXISTE.");
            }
        }
    }

    private boolean verificarActualizar() {
        if (!userAuthority.getIdUserAuthority().equals(selected.getIdUserAuthority().getIdUserAuthority())) {
            if (userAuthorityOptionEjb.verificarRepetido(userAuthority.getIdUserAuthority(), opcion.getId()) != null) {
                return true;
            }
        }
        if (!opcion.getId().equals(selected.getIdOpcion().getId())) {
            if (userAuthorityOptionEjb.verificarRepetido(userAuthority.getIdUserAuthority(), opcion.getId()) != null) {
                return true;
            }
        }
        return false;
    }

    public UserAuthorityOption getUserAuthorityOption() {
        return userAuthorityOption;
    }

    public void setUserAuthorityOption(UserAuthorityOption userAuthorityOption) {
        this.userAuthorityOption = userAuthorityOption;
    }

    public Opcion getOpcion() {
        return opcion;
    }

    public void setOpcion(Opcion opcion) {
        this.opcion = opcion;
    }

    public UserAuthorityOption getSelected() {
        return selected;
    }

    public void setSelected(UserAuthorityOption selected) {
        this.selected = selected;
    }

    public UserAuthority getUserAuthority() {
        return userAuthority;
    }

    public void setUserAuthority(UserAuthority userAuthority) {
        this.userAuthority = userAuthority;
    }

    public List<UserAuthorityOption> getLstUserAuthorityOptions() {
        lstUserAuthorityOptions = userAuthorityOptionEjb.findAll();
        return lstUserAuthorityOptions;
    }

    public void setLstUserAuthorityOptions(List<UserAuthorityOption> lstUserAuthorityOptions) {
        this.lstUserAuthorityOptions = lstUserAuthorityOptions;
    }

    public List<Opcion> getLstOpciones() {
        return lstOpciones;
    }

    public void setLstOpciones(List<Opcion> lstOpciones) {
        this.lstOpciones = lstOpciones;
    }

    public List<Opcion> getLstOpcionesAux() {
        return lstOpcionesAux;
    }

    public void setLstOpcionesAux(List<Opcion> lstOpcionesAux) {
        this.lstOpcionesAux = lstOpcionesAux;
    }

    public List<UserAuthority> getLstUserAuthority() {
        return lstUserAuthority;
    }

    public void setLstUserAuthority(List<UserAuthority> lstUserAuthority) {
        this.lstUserAuthority = lstUserAuthority;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

}
