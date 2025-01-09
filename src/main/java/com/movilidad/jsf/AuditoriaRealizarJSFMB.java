/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AuditoriaFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.Auditoria;
import com.movilidad.model.Empleado;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Y1SUS
 */
@Named(value = "auditoriaRealizarJSFMB")
@ViewScoped
public class AuditoriaRealizarJSFMB implements Serializable {

    /**
     * Creates a new instance of AuditoriaJSFMB
     */
    public AuditoriaRealizarJSFMB() {
    }
    @EJB
    private AuditoriaFacadeLocal audiEJB;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUsrFacadeLocal;
    @EJB
    private EmpleadoFacadeLocal emplEJB;

    private List<Auditoria> list;

    @Inject
    private AuditoriaRespuestaJSFMB audiRespuestaJSFMB;

    private int i_idArea;
    private Auditoria auditoria;
    private boolean b_realizar = false;

    private ParamAreaUsr paramAreaUsr;
    private UserExtended user;

    @PostConstruct
    public void init() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        paramAreaUsr = paramAreaUsrFacadeLocal.getByIdUser(user.getUsername());
        if (paramAreaUsr != null) {
            i_idArea = paramAreaUsr.getIdParamArea().getIdParamArea();
        }
        consultar();

    }

    public void viewRealizar(Auditoria a) {
        try {
            auditoria = a;
            b_realizar = MovilidadUtil.betweenDateToday(auditoria.getIdAuditoriaEncabezado().getFechaDesde(),
                    auditoria.getIdAuditoriaEncabezado().getFechaHasta(),
                    MovilidadUtil.fechaHoy());
            if (!b_realizar) {
                MovilidadUtil.addAdvertenciaMessage("Fuera de rango para realizar");
                return;
            }
            Empleado empl = emplEJB.getEmpleadoByUsername(user.getUsername());
            if (empl == null) {
                MovilidadUtil.addAdvertenciaMessage("No se encontró responsable para el usuario en sesión");
                return;
            }
            if (auditoria.getIdAuditoriaEncabezado().getActiva() != null
                    && auditoria.getIdAuditoriaEncabezado().getActiva().equals(0)) {
                MovilidadUtil.addAdvertenciaMessage("La auditoría esta desactivada.");
                return;
            }

            audiRespuestaJSFMB.setEmpleado(empl);
            audiRespuestaJSFMB.setAudi(auditoria);
            audiRespuestaJSFMB.setB_view(false);
            audiRespuestaJSFMB.setB_control(true);
            audiRespuestaJSFMB.getPreguntas();
            audiRespuestaJSFMB.cargarMapRespuestas();
            audiRespuestaJSFMB.cargarComponente();
            MovilidadUtil.openModal("crear_audi_resolver_dialog_wv");
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addAdvertenciaMessage("Opción invalida");
        }
    }

    /**
     * Cargar Auditorias por id de area
     */
    public void consultar() {
        list = audiEJB.findByAreaDisponibles(i_idArea, MovilidadUtil.fechaCompletaHoy());
    }

    public List<Auditoria> getList() {
        return list;
    }

    public void setList(List<Auditoria> list) {
        this.list = list;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    public boolean isB_realizar() {
        return b_realizar;
    }

    public void setB_realizar(boolean b_realizar) {
        this.b_realizar = b_realizar;
    }

}
