package com.movilidad.jsf;

import com.movilidad.service.RolesService;
import com.movilidad.service.UsersService;
import com.movilidad.model.Opcion;
import com.movilidad.model.UserRoles;
import com.movilidad.model.Users;
import com.movilidad.service.OpcionService;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author alexander
 */
@ManagedBean(name = "managerOpciones")
@ViewScoped
public class ManagerOpciones implements Serializable {

    private Collection<Users> usersColl;
    private Users user;
    private Users userSource;
    private Users userDestiny;
    private List<Opcion> opciones;
    private List<Opcion> opcionSelecteds;
    private WebApplicationContext wc;
    private DualListModel<Opcion> opcionesList;
    @ManagedProperty("#{rolesService}")
    private RolesService rolesService;
    @ManagedProperty("#{usersService}")
    private UsersService usersService;
    @ManagedProperty("#{opcionService}")
    private OpcionService opcionService;

    public OpcionService getOpcionService() {
        return opcionService;
    }

    public void setOpcionService(OpcionService opcionService) {
        this.opcionService = opcionService;
    }

    public UsersService getUsersService() {
        return usersService;
    }

    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    public RolesService getRolesService() {
        return rolesService;
    }

    public void setRolesService(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    public WebApplicationContext getWc() {
        return wc;
    }

    public void setWc(WebApplicationContext wc) {
        this.wc = wc;
    }

    public DualListModel<Opcion> getOpcionesList() {
        return opcionesList;
    }

    public Users getUsuario(Integer id) {
        return this.getUsersService().retrieve(id);
    }

    public void setOpcionesList(DualListModel<Opcion> opcionesList) {
        this.opcionesList = opcionesList;
    }

    public Collection<Users> getUsersColl() {
        return usersColl;
    }

    public void setUsersColl(Collection<Users> usersColl) {
        this.usersColl = usersColl;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Opcion getOpcion(Integer id) {
        return opcionService.findOpcion(id);
    }

    public Collection<Opcion> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<Opcion> opciones) {
        this.opciones = opciones;
    }

    public List<Opcion> getOpcionSelecteds() {
        return opcionSelecteds;
    }

    public void setOpcionSelecteds(List<Opcion> opcionSelecteds) {
        this.opcionSelecteds = opcionSelecteds;
    }

    public Users getUserSource() {
        return userSource;
    }

    public void setUserSource(Users userSource) {
        this.userSource = userSource;
    }

    public Users getUserDestiny() {
        return userDestiny;
    }

    public void setUserDestiny(Users userDestiny) {
        this.userDestiny = userDestiny;
    }

    /**
     * Creates a new instance of ManagerOpciones
     */
    public ManagerOpciones() {
        FacesContext fcontext = FacesContext.getCurrentInstance();
        wc = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        RolesService rs = (RolesService) wc.getBean("rolesService");

        opciones = (List<Opcion>) rs.getOpciones();
        this.opcionSelecteds = new ArrayList<>();
        UsersService us = (UsersService) wc.getBean("usersService");
        usersColl = us.retrieveAll();
        opcionesList = new DualListModel<>(opciones, opcionSelecteds);

    }

    public void selectUser(ItemSelectEvent event) {

        Collection<UserRoles> userRolesCollection = this.getUser().getUserRolesCollection();
        Iterator<UserRoles> iterator = userRolesCollection.iterator();
        UserRoles uRoles = iterator.next();
        Collection<Opcion> opbyrol = rolesService.getOpciones(uRoles, uRoles.getUserId());

        opciones = (List<Opcion>) rolesService.getOpciones();
        if (opbyrol != null) {
            this.getOpcionesList().setTarget((List) opbyrol);
            opciones.removeAll(opbyrol);
            this.getOpcionesList().setSource(opciones);
            this.getOpcionesList().setTarget((List<Opcion>) opbyrol);

        }
    }

    public void saveOpciones(ActionEvent e) {
        if (this.getUser() == null) {
            MovilidadUtil.addErrorMessage("No se ha cargado un usuario.");
            return;
        }
        Collection<UserRoles> userRolesCollection = this.getUser().getUserRolesCollection();
        Iterator<UserRoles> iterator = userRolesCollection.iterator();
        UserRoles urol = iterator.next();
        List<Opcion> opest = this.getOpcionesList().getTarget();
        urol.setListaOpciones(opest);
        this.getUsersService().asignarOpciones(urol);
        FacesContext fcontext = FacesContext.getCurrentInstance();
        fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro Exitoso"));
    }
    
    /**
     * Para facilitar la tarea de asignación de menús a usuarios, se crea funcionalidad
     * que permita copiar los menús de un usuario a otro.
     * Esta acción sobrescribe los accesos que tenga el usuario destino o usuario
     * al que se desea asignar los menús.
     */
    public void saveMenus() {
        if (this.getUserSource() == null || this.getUserDestiny()== null) {
            MovilidadUtil.addErrorMessage("No se han cargado los usuarios.");
            return;
        }
        UserRoles urolSource = this.getUserSource().getUserRolesCollection().iterator().next();
        UserRoles urolDestiny = this.getUserDestiny().getUserRolesCollection().iterator().next();
        Collection<Opcion> opbyrol = rolesService.getOpciones(urolSource, urolSource.getUserId());
        urolDestiny.setListaOpciones(opbyrol);
        this.getUsersService().asignarOpciones(urolDestiny);
        FacesContext fcontext = FacesContext.getCurrentInstance();
        fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Proceso finalizado con exito"));
    }

    // Converter for users
    @FacesConverter("usersConverter")
    public static class UsersConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ManagerOpciones controller = (ManagerOpciones) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "managerOpciones");
            return controller.getUsuario(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Users) {
                Users o = (Users) object;
                return getStringKey(o.getUserId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Users.class.getName()});
                return null;
            }
        }

    }

    // Converter for opcion
    @FacesConverter("opcionConverter")
    public static class OpcionConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ManagerOpciones controller = (ManagerOpciones) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "managerOpciones");
            return controller.getOpcion(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Opcion) {
                Opcion o = (Opcion) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Users.class.getName()});
                return null;
            }
        }

    }

}
