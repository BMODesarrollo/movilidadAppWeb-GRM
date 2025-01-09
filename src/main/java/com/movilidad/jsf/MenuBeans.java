package com.movilidad.jsf;

import com.movilidad.service.RolesService;
import com.movilidad.service.UsersService;
import com.movilidad.model.Opcion;
import com.movilidad.model.UserRoles;
import com.movilidad.model.Users;
import com.movilidad.security.UserExtended;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author
 */
@ManagedBean
@ViewScoped
public class MenuBeans implements Serializable {

    private static final long serialVersionUID = 1L;
    private UserExtended principal;
    private MenuModel model;

    public MenuModel getModel() {
        return model;
    }
    public void setModel(MenuModel model) {
        this.model = model;
    }

    public UserExtended getPrincipal() {
        return principal;
    }

    public void setPrincipal(UserExtended principal) {
        this.principal = principal;
    }

    public MenuBeans() {
        model = new DefaultMenuModel();
        WebApplicationContext wc = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        RolesService rs = (RolesService) wc.getBean("rolesService");
        UsersService userSc = (UsersService) wc.getBean("usersService");

        principal = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<GrantedAuthority> authorities = principal.getAuthorities();
        String authority = "";
        Users usuario = userSc.findUser(principal.getUsername());
        for (GrantedAuthority grantedAuthority : authorities) {
            authority = grantedAuthority.getAuthority();
        }
        UserRoles role = rs.getRole(authority, usuario);
        String menupadreActual = "";
        Collection<Opcion> opciones = rs.getOpciones(role);
        Iterator<Opcion> iterator = opciones.iterator();
        Opcion next = iterator.next();
        menupadreActual = next.getDescripcion();

        Set<String> raiz = new HashSet<>();

        int cont = 0;
        for (Opcion opcion : opciones) {
            raiz.add(opcion.getDescripcion() + "," + opcion.getIconParent());

        }
        raiz = new TreeSet<String>(raiz);
        int con1 = 1;
        int con2 = 1;
        for (String princMenu : raiz) {
            DefaultSubMenu submenu = new DefaultSubMenu();
            submenu.setLabel(princMenu.split(",")[0]);
            submenu.setIcon(princMenu.split(",")[1]);

            submenu.setId("idj" + con1 + con2);

            cont++;
            for (Opcion op : opciones) {

                if (op.getDescripcion().equals(princMenu.split(",")[0])) {
                    DefaultMenuItem item = new DefaultMenuItem();
                    item.setValue(op.getNameop());
                    item.setId("id" + con1 + con2);
                    item.setIcon(op.getIconChild());
                    //item.setActionExpression(createMethodExpression("#{navigationPage.setPage('"+op.getRecurso()+"')}", Void.TYPE, new Class[]{String.class}));
                    //item.setCommand("#{navigationPage.setPage('"+op.getRecurso()+"')}");
                    item.setAjax(false);
                    item.setUpdate("@all");
                    item.setOutcome(op.getRecurso());

                    //item.setImmediate(true);
                    submenu.addElement(item);
                    con2++;
                }
            }
            model.addElement(submenu);

        }
    }

}
