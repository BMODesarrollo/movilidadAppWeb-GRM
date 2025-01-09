package com.movilidad.jsf;

import com.movilidad.ejb.PrgTcFacadeLocal;
import com.movilidad.model.PrgTc;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
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
@Named(value = "inoperativosBean")
@ViewScoped
public class InoperativosJSFManagedBean implements Serializable {

    @EJB
    private PrgTcFacadeLocal prgTcEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    private PrgTc prgTc;
    private Date fechaInicio;
    private Date fechaFin;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private List<PrgTc> lstInoperativos;

    @PostConstruct
    public void init() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        fechaInicio = calendar.getTime();
        fechaFin = new Date();
    }

    public void obtenerInoperativos() {
        if (Util.validarFechaCambioEstado(fechaInicio, fechaFin)) {
            MovilidadUtil.addErrorMessage("La fecha de inicio no puede ser a la fecha fin");
            return;
        }
        lstInoperativos = prgTcEjb.obtenerInoperativos(fechaInicio, fechaFin, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (lstInoperativos.isEmpty()) {
            MovilidadUtil.addErrorMessage("No existen operadores inoperativos para éste rango de fechas");
        } else {
            MovilidadUtil.addSuccessMessage("Registros cargados éxitosamente");
        }
    }

    public void habilitarOperador() {
        prgTc.setSercon("DP_HABILITADO_" + prgTc.getSercon());
        prgTc.setModificado(new Date());
        prgTc.setUsername(user.getUsername());
        prgTcEjb.edit(prgTc);
        MovilidadUtil.addSuccessMessage("Operador "
                + prgTc.getIdEmpleado().getNombres().toUpperCase() + " "
                + prgTc.getIdEmpleado().getApellidos().toUpperCase() + " "
                + "habilitado éxitosamente");
        lstInoperativos.remove(prgTc);
//        lstInoperativos = prgTcEjb.obtenerInoperativos(fechaInicio, fechaFin);
        PrimeFaces.current().executeScript("PF('dtInoperativo').clearFilters();");
        prgTc = null;
    }

    public PrgTc getPrgTc() {
        return prgTc;
    }

    public void setPrgTc(PrgTc prgTc) {
        this.prgTc = prgTc;
    }

    public List<PrgTc> getLstInoperativos() {
        return lstInoperativos;
    }

    public void setLstInoperativos(List<PrgTc> lstInoperativos) {
        this.lstInoperativos = lstInoperativos;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }
}
