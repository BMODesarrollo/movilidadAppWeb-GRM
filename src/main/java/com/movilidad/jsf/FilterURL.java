/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.OpcionFacadeLocal;
import com.movilidad.ejb.UserRolesFacadeLocal;
import com.movilidad.model.UserRoles;
import com.movilidad.security.UserExtended;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author HP
 */
@Named(value = "filterUrl")
@ViewScoped
public class FilterURL implements Serializable {

    @EJB
    private UserRolesFacadeLocal userRolesFacadeLocal;
    @EJB
    private OpcionFacadeLocal opcionFacadeLocal;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private String url;
    private List<String> urlOpcion = new ArrayList<>();

    public FilterURL() {
    }

    public void filterURL() {
        try {
            if (user != null) {
                UserRoles userRol = userRolesFacadeLocal.findByUserName(user.getUsername());
                if (userRol != null) {
                    List<Integer> idOpcionRole = userRolesFacadeLocal.idOpcionRole(userRol.getUserRoleId());
                    if (!idOpcionRole.isEmpty()) {
                        for (Integer i : idOpcionRole) {
                            String recurso = opcionFacadeLocal.findRecurso(i);
                            if (recurso != null) {
                                urlOpcion.add(recurso);
                            }
                        }
                        if (!urlOpcion.isEmpty()) {
                            accesPage();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error --> filter");
        }
    }

    void accesPage() {
        boolean acces = false;
        url = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();
//        System.out.println(url);
        int i = url.toCharArray().length;
        String aux_url = url.substring(16, i - 4);
        for (String s : urlOpcion) {
            if (s.equals(aux_url)) {
                acces = true;
            }
        }
        if (!acces) {
            PrimeFaces.current().executeScript("location.href='../access-denied.jsf'");
        }
    }
}
