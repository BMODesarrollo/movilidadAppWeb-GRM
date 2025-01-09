package com.movilidad.jsf;

import com.movilidad.ejb.NotificacionProcesosFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "notificaListComponentBean")
@ViewScoped
public class NotificaListComponentBean implements Serializable {

    @EJB
    private NotificacionProcesosFacadeLocal procesosEjb;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    @Inject
    private SelectGopUnidadFuncionalBean selectGopUnidadFuncionalBean;

    private NotificacionProcesos proceso;
    private String form;
    private String modulo;

    private List<NotificacionProcesos> lstNotificacionProcesos;

    public void openModalEmpleadoList(String compUpdate) {
        listarProcesos();
        setForm(compUpdate);
        MovilidadUtil.openModal("NotificaListDialog");
        MovilidadUtil.clearFilter("wlvIdAreaNotiDt");
    }

    public void onRowClckSelectNotifica(final SelectEvent event) throws Exception {
        proceso = (NotificacionProcesos) event.getObject();
        MovilidadUtil.updateComponent(form);
        MovilidadUtil.addSuccessMessage("Lista de distribución seleccionada con éxito.");
    }

    int obtenerIdGopUnidadFuncional() {
        int idUF = unidadFuncionalSessionBean.getIdGopUnidadFuncional();
        if (idUF == 0) {
            idUF = selectGopUnidadFuncionalBean.getI_unidad_funcional() == null ? 0
                    : selectGopUnidadFuncionalBean.getI_unidad_funcional();
        }
        return idUF;
    }

    public void listarProcesos() {
        lstNotificacionProcesos = procesosEjb.findAll(obtenerIdGopUnidadFuncional());
    }

    public NotificacionProcesos getProceso() {
        return proceso;
    }

    public void setProceso(NotificacionProcesos proceso) {
        this.proceso = proceso;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public List<NotificacionProcesos> getLstNotificacionProcesos() {
        return lstNotificacionProcesos;
    }

    public void setLstNotificacionProcesos(List<NotificacionProcesos> lstNotificacionProcesos) {
        this.lstNotificacionProcesos = lstNotificacionProcesos;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

}
