/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.security;

import com.movilidad.ejb.ConfigControlJornadaFacadeLocal;
import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.jsf.GopUnidadFuncionalSessionBean;
import com.movilidad.model.ConfigControlJornada;
import com.movilidad.model.ConfigEmpresa;
import com.movilidad.utils.SingletonConfigControlJornada;
import com.movilidad.utils.SingletonConfigEmpresa;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.WebAttributes;

/**
 *
 * @author alexander
 */
@ManagedBean
@SessionScoped
public class LoginBean implements PhaseListener, Serializable {

    private String j_username;
    private String j_password;
    private User usuario;
    private boolean logeado;
    private UserExtended userExtended;

    @EJB
    private ConfigEmpresaFacadeLocal configEmpresaFacadeLocal;
    @EJB
    private ConfigControlJornadaFacadeLocal controlJornadaFacadeLocal;
    @Inject
    private GopUnidadFuncionalSessionBean gopUnidadFuncionalSessionBean;

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public String getJ_password() {
        return j_password;
    }

    public void setJ_password(String j_password) {
        this.j_password = j_password;
    }

    public String getJ_username() {
        return j_username;
    }

    public void setJ_username(String j_username) {
        this.j_username = j_username;
    }

    public boolean isLogeado() {
        return logeado;
    }

    public void setLogeado(boolean logeado) {
        this.logeado = logeado;
    }

    public UserExtended getUserExtended() {
        return userExtended;
    }

    public void setUserExtended(UserExtended userExtended) {
        this.userExtended = userExtended;
    }

    public LoginBean() {
    }

    public String doLogin() throws ServletException, IOException {

        ExternalContext context = FacesContext.getCurrentInstance()
                .getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                .getRequestDispatcher("/j_spring_security_check?j_username=" + j_username
                        + "&j_password=" + j_password);
        dispatcher.forward((ServletRequest) context.getRequest(),
                (ServletResponse) context.getResponse());
        FacesContext.getCurrentInstance().responseComplete();

        gopUnidadFuncionalSessionBean.cargarUnidadFuncional(j_username);
        gopUnidadFuncionalSessionBean.init();
        cargarMapConfigEmpresa();
        cargarMapConfigControlJornada();
        // It's OK to return null here because Faces is just going to exit.
        return null;
    }

    public void cargarMapConfigEmpresa() {
        if (SingletonConfigEmpresa.getMapConfiMapEmpresa().isEmpty()) {
            List<ConfigEmpresa> listCe = configEmpresaFacadeLocal.findEstadoReg();
            for (ConfigEmpresa ce : listCe) {
                SingletonConfigEmpresa.getMapConfiMapEmpresa().put(ce.getLlave(), ce.getValor());
            }
        }
    }

    public void cargarMapConfigControlJornada() {
        if (SingletonConfigControlJornada.getMapConfigControlJornada().isEmpty()) {
            List<ConfigControlJornada> listCcj = controlJornadaFacadeLocal.findAll();
            for (ConfigControlJornada ce : listCcj) {
                SingletonConfigControlJornada.getMapConfigControlJornada().put(ce.getNombreConfig(), ce);
            }
        }
    }

    @Override
    public void afterPhase(PhaseEvent event) {
    }

    /* (non-Javadoc)
     * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
     * 
     * Do something before rendering phase.
     */
    @Override
    public void beforePhase(PhaseEvent event) {

        Exception e = (Exception) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);

        if (e instanceof BadCredentialsException) {

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(
                    WebAttributes.AUTHENTICATION_EXCEPTION, null);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error", e.getMessage()));
        }
    }

    /* (non-Javadoc)
     * @see javax.faces.event.PhaseListener#getPhaseId()
     * 
     * In which phase you want to interfere?
     */
    @Override
    public PhaseId getPhaseId() {

        return PhaseId.RENDER_RESPONSE;
    }
}
