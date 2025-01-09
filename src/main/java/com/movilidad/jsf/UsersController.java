package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.RolFacadeLocal;
import com.movilidad.ejb.UserRolesFacadeLocal;
import com.movilidad.model.Users;
import com.movilidad.ejb.UsersFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.Rol;
import com.movilidad.model.UserRoles;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.JsfUtil;
import com.movilidad.utils.JsfUtil.PersistAction;
import com.movilidad.utils.MovilidadUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Named("usersController")
@ViewScoped
public class UsersController implements Serializable {

    @EJB
    private UsersFacadeLocal userEJB;
    @EJB
    private EmpleadoFacadeLocal empleadoFacade;
    @EJB
    private UserRolesFacadeLocal rolesFacade;

    @EJB
    private RolFacadeLocal rolEJB;

    @Inject
    private SelectGopUnidadFuncionalBean selectGopUnidadFuncionalBean;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private List<Users> items = null;
    private List<Rol> itemsRoles;
    private List<Empleado> listEmpleados;

    private Users selected;
    private Empleado empleado;

    //----
    private Integer i_codEmpleado;
    private String confirContrasena;
    private String c_aux1;
    private String c_aux2;
    private String c_aux3;

    //----Rol
    private String rol;

    private String c_username;

    private boolean b_cont = false;
    private boolean b_cont2 = false;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public UsersController() {
    }

    @PostConstruct
    public void init() {
        consultarUsuarios();
    }

    public Users prepareCreate() {
        selected = new Users();
        empleado = new Empleado();
        selectGopUnidadFuncionalBean.consultarList();
        return selected;
    }

    public void prepareEdit() {
        if (selected == null) {
            return;
        }
        if (selected.getIdGopUnidadFuncional() != null) {
            unidadFuncionalSessionBean.setI_unidad_funcional(selected.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
        }
        selectGopUnidadFuncionalBean.consultarList();
        MovilidadUtil.openModal("UsersEditDialog");
    }

    public void create() {
        persist(PersistAction.CREATE, "Usuario creado corrrectamente");
        if (JsfUtil.isValidationFailed()) {
            JsfUtil.addErrorMessage("Error, Usuario no se creó correctamente");
        }
        consultarUsuarios();
    }

    public void update() {
        persist(PersistAction.UPDATE, "Usuario actualizado correctamente");
        if (JsfUtil.isValidationFailed()) {
            JsfUtil.addErrorMessage("Error, Usuario no se actualizó correctamente");
        }
        consultarUsuarios();
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Usuario eliminado correctamente");
        if (JsfUtil.isValidationFailed()) {
            JsfUtil.addErrorMessage("Error, Usuario no se eliminó correctamente");
        }
    }

    public void reset() {
        selected = new Users();
        consultarUsuarios();
        empleado = new Empleado();
        c_aux1 = "";
        c_aux2 = "";
        c_aux3 = "";
        b_cont = false;
        confirContrasena = "";
        c_username = "";
        empleado = null;
        rol = "";
    }

    boolean validarCorreo() {
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.matches(regex, selected.getEmail());
    }

    boolean validarNombreUsuario() {
        return userEJB.userNameFind(getSelected().getUsername());
    }

    void encriptarContrasena() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(getSelected().getPassword());
        getSelected().setPassword(encode);
    }

    void generarUserName() {
        String c_cod = Integer.toString(getEmpleado().getCodigoTm());
        String[] nombres = getEmpleado().getNombres().split(" ");
        String[] apellidos = getEmpleado().getApellidos().split(" ");
        if (nombres.length == 2) {
            c_aux1 = (Character.toString(nombres[0].charAt(0)) + "" + apellidos[0] + "" + Character.toString(nombres[1].charAt(0))).toLowerCase();
            c_aux2 = (Character.toString(nombres[0].charAt(0)) + "" + apellidos[0] + "" + c_cod).toLowerCase();
            c_aux3 = (apellidos[0] + "" + Character.toString(nombres[0].charAt(0)) + "" + c_cod).toLowerCase();
            return;
        }
        if (nombres.length > 2) {
            c_aux1 = (Character.toString(nombres[0].charAt(0)) + "" + apellidos[0]).toLowerCase();
            c_aux2 = (Character.toString(nombres[0].charAt(0)) + "" + apellidos[0] + "" + c_cod).toLowerCase();
            c_aux3 = (apellidos[0] + "" + Character.toString(nombres[0].charAt(0)) + "" + c_cod).toLowerCase();
            return;
        }
        c_aux1 = (nombres[0] + "" + apellidos[0] + "" + c_cod).toLowerCase();
        c_aux2 = (Character.toString(nombres[0].charAt(0)) + "" + apellidos[0] + "" + c_cod).toLowerCase();
        c_aux3 = (apellidos[0] + "" + Character.toString(nombres[0].charAt(0)) + "" + c_cod).toLowerCase();

    }

    boolean validarContrasena() {
        return getSelected().getPassword().equals(confirContrasena);
    }

    public void onRowSelectEmpleado(SelectEvent event) {
        empleado = (Empleado) event.getObject();
        if (empleado != null) {
            if (!validarUsuario()) {
                MovilidadUtil.addErrorMessage("Empleado cuenta con usuario registrado");
                reset();
                PrimeFaces.current().executeScript("PF('empleDlg').hide();");
                return;
            }
            b_cont = true;
            getSelected().setNombres(empleado.getNombres() + " " + empleado.getApellidos());
            getSelected().setIdEmpleado(empleado);
            MovilidadUtil.addSuccessMessage("Empleado valido");
        }
        PrimeFaces.current().executeScript("PF('empleDlg').hide();");
    }

    boolean validarUsuario() {
        return userEJB.validarUnicoEmpleado(empleado.getIdEmpleado());
    }

    private void persist(PersistAction persistAction, String successMessage) {
        selected.setCreatedBy(user.getUsername());
        if (selected != null) {
            try {
                switch (persistAction) {
                    case CREATE:
                        if (c_username.equals("") || c_username.isEmpty()) {
                            MovilidadUtil.addErrorMessage("Usuario es requerido");
                            return;
                        }
                        selected.setEmail(empleado.getEmailCorporativo());
                        if (!validarCorreo()) {
                            MovilidadUtil.addErrorMessage("Correo no valido");
                            return;
                        }
                        selected.setUsername(c_username);
                        if (!validarNombreUsuario()) {
                            generarUserName();
                            MovilidadUtil.addErrorMessage("Nombre de usuario no disponible");
                            MovilidadUtil.addAdvertenciaMessage("Intente los siguientes Usuarios:");
                            MovilidadUtil.addAdvertenciaMessage(c_aux1 + "--" + c_aux2 + "--" + c_aux3);
                            return;
                        }
                        if (!validarContrasena()) {
                            MovilidadUtil.addErrorMessage("Las contraseñas no coinciden");
                            return;
                        }
                        if (selected.getPassword().length() < 4) {
                            MovilidadUtil.addErrorMessage("Contraseña no valida, tamaño no permitido");
                            return;
                        }
                        selected.setUltimoAcceso(MovilidadUtil.fechaCompletaHoy());

                        if (unidadFuncionalSessionBean.getI_unidad_funcional() != null) {
                            selected.setIdGopUnidadFuncional(new GopUnidadFuncional(unidadFuncionalSessionBean.getI_unidad_funcional()));
                        } else {
                            selected.setIdGopUnidadFuncional(null);
                        }
                        selected.setCreado(MovilidadUtil.fechaCompletaHoy());
                        encriptarContrasena();
                        guardarRol(); // también persiste el usuario
                        JsfUtil.addSuccessMessage(successMessage);
                        reset();
                        break;
                    case DELETE:
//                        pmTAmplHorasEJB.edit(selected);
//                        JsfUtil.addSuccessMessage(successMessage);
                        reset();
                        break;
                    case UPDATE:
                        //se utiliza la variable c_aux1 para la contraseña.
                        if (!(c_aux1.equals("") || c_aux1.isEmpty())) {
                            if (!c_aux1.equals(confirContrasena)) {
                                MovilidadUtil.addErrorMessage("Las contraseñas no coinciden");
                                return;
                            } else {
                                if (c_aux1.length() < 4) {
                                    MovilidadUtil.addErrorMessage("Contraseña no valida, tamaño no permitido");
                                    return;
                                }
                                selected.setPassword(c_aux1);
                                encriptarContrasena();
                            }
                        }

                        if (unidadFuncionalSessionBean.getI_unidad_funcional() != null) {
                            selected.setIdGopUnidadFuncional(new GopUnidadFuncional(unidadFuncionalSessionBean.getI_unidad_funcional()));
                        } else {
                            selected.setIdGopUnidadFuncional(null);
                        }
                        userEJB.edit(selected);
                        if (!rol.equals("") || !rol.isEmpty()) {
                            Collection<UserRoles> userRolesCollection = selected.getUserRolesCollection();
                            if (userRolesCollection != null && userRolesCollection.size() > 0) {
                                for (UserRoles usr : userRolesCollection) {
                                    usr.setAuthority(rol);
                                    rolesFacade.edit(usr);
                                    break;
                                }
                            }
                        }
                        JsfUtil.addSuccessMessage(successMessage);
                        PrimeFaces current = PrimeFaces.current();
                        current.executeScript("PF('UsersEditDialog').hide();");
                        reset();
                        break;
                    default:
                        break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                MovilidadUtil.addErrorMessage("Error de sistema");
            }
        }
    }

    //---- Rol
    void guardarRol() {
        try {
            UserRoles userRoles = new UserRoles();
            if (rol.equals("") || rol.isEmpty()) {
                MovilidadUtil.addErrorMessage("Debe seleccionar un Rol para el Usuario");
                return;
            }
            userRoles.setUserId(selected);
            userRoles.setAuthority(rol);
            rolesFacade.create(userRoles);
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al registrar Rol");
        }
    }

    public void handleKeyEvent() {
        c_username = c_username.toLowerCase();
    }

    public List<Users> consultarUsuarios() {
        items = userEJB.findAllUsersActivosByUnidadFunc(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        Collections.reverse(items);
        return items;
    }

    public void onRowSelect(SelectEvent event) {
        selected = (Users) event.getObject();
        c_aux1 = "";
        Collection<UserRoles> userRolesCollection = selected.getUserRolesCollection();
        if (userRolesCollection != null && userRolesCollection.size() > 0) {
            for (UserRoles usr : userRolesCollection) {
                rol = usr.getAuthority();
                break;
            }
        }
    }

    public void cargarEmpleados() {
        listEmpleados = empleadoFacade.findAll();
    }

    public List<Empleado> getListEmpleados() {
        return listEmpleados;
    }

    public Users getSelected() {
        return selected;
    }

    public void setSelected(Users selected) {
        this.selected = selected;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getC_aux1() {
        return c_aux1;
    }

    public void setC_aux1(String c_aux1) {
        this.c_aux1 = c_aux1;
    }

    public String getC_aux2() {
        return c_aux2;
    }

    public void setC_aux2(String c_aux2) {
        this.c_aux2 = c_aux2;
    }

    public String getC_aux3() {
        return c_aux3;
    }

    public void setC_aux3(String c_aux3) {
        this.c_aux3 = c_aux3;
    }

    public boolean isB_cont() {
        return b_cont;
    }

    public void setB_cont(boolean b_cont) {
        this.b_cont = b_cont;
    }

    public boolean isB_cont2() {
        return b_cont2;
    }

    public void setB_cont2(boolean b_cont2) {
        this.b_cont2 = b_cont2;
    }

    public String getConfirContrasena() {
        return confirContrasena;
    }

    public void setConfirContrasena(String confirContrasena) {
        this.confirContrasena = confirContrasena;
    }

    public List<Rol> getItemsRoles() {
        if (itemsRoles == null) {
            itemsRoles = rolEJB.findAllEstadoReg();
        }
        return itemsRoles;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getC_username() {
        return c_username;
    }

    public void setC_username(String c_username) {
        this.c_username = c_username;
    }

    public List<Users> getItems() {
        return items;
    }

    public void setItems(List<Users> items) {
        this.items = items;
    }

}
