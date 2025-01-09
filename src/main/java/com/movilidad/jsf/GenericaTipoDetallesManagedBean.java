package com.movilidad.jsf;

import com.movilidad.ejb.GenericaNotifProcesosFacadeLocal;
import com.movilidad.ejb.ParamAreaFacadeLocal;
import com.movilidad.ejb.GenericaTipoDetallesFacadeLocal;
import com.movilidad.ejb.GenericaTipoFacadeLocal;
import com.movilidad.ejb.SncDetalleFacadeLocal;
import com.movilidad.model.GenericaNotifProcesos;
import com.movilidad.model.ParamArea;
import com.movilidad.model.GenericaTipo;
import com.movilidad.model.GenericaTipoDetalles;
import com.movilidad.model.SncDetalle;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.JsfUtil;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Luis Vélez
 */
@Named(value = "genericaTipoDetBean")
@ViewScoped
public class GenericaTipoDetallesManagedBean implements Serializable {

    @EJB
    private GenericaTipoDetallesFacadeLocal genericaTipoDetEjb;
    @EJB
    private GenericaTipoFacadeLocal genericaTipoEjb;
    @EJB
    private ParamAreaFacadeLocal paramAreaEjb;
    @EJB
    private SncDetalleFacadeLocal sncEjb;
    @EJB
    private GenericaNotifProcesosFacadeLocal notiEjb;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private GenericaTipo nv;
    private GenericaTipoDetalles nvd;
    private GenericaTipoDetalles selected;
    private ParamArea paramArea;
    private GenericaNotifProcesos notifica;

    private List<GenericaTipoDetalles> lista;
    private List<GenericaTipo> listaNovedadesTipo;
    private List<ParamArea> listaNovedadClasif;
    private List<GenericaNotifProcesos> listaNotiProcesos;

    private boolean i_fechas, i_prevencion, i_afectaPm, i_afectaProg,
            i_requiereSoporte, i_notificacion, i_horas;
    private int i_puntosPm;

    private boolean flag = false;
    private boolean flagBtnNuevo = false;

    @PostConstruct
    public void init() {
        if (lista == null) {
            if (genericaTipoDetEjb.findByUsername(user.getUsername()) != null) {
                paramArea = genericaTipoDetEjb.findByUsername(user.getUsername()).getIdParamArea();
                this.lista = this.genericaTipoDetEjb.findAllByArea(paramArea.getIdParamArea());
            }
            if (paramArea == null) {
                flagBtnNuevo = true;
            }
        }
        this.i_puntosPm = 0;
        i_fechas = false;
        i_prevencion = false;
        i_afectaPm = false;
        i_afectaProg = false;
        i_requiereSoporte = false;
        i_notificacion = false;
        i_horas = false;
        this.nv = new GenericaTipo();
        this.nvd = new GenericaTipoDetalles();
//        this.selected = new GenericaTipoDetalles();
//        this.selected = null;
    }

    public GenericaTipoDetalles prepareCreate() {
        init();
        flag = false;
        return this.selected;
    }

    public void prepareUpdate() {
        init();
        nvd = this.selected;
        flag = true;
        i_afectaPm = (nvd.getAfectaPm() == 1);
        i_puntosPm = nvd.getPuntosPm();
        i_prevencion = (nvd.getAfectaPm() == 1);
        i_notificacion = (nvd.getNotificacion() == 1);
        i_requiereSoporte = (nvd.getRequiereSoporte() == 1);
        i_fechas = (nvd.getFechas() == 1);
        i_horas = (nvd.getHora() == 1);
    }

    public void destroy() {
//        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProductDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            lista = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void prepareListNovedadesTipo() {
        this.nv = new GenericaTipo();
        getLista();
    }

    public void onRowDblClckSelect(final SelectEvent event) {
//        System.out.println("onRowDblClckSelect");
        setNv((GenericaTipo) event.getObject());
        nvd.setIdGenericaTipo((GenericaTipo) event.getObject());
        PrimeFaces.current().executeScript("PF('NovedadTipoDetalleListDialog').hide();");
    }

    public void onRowDblClckSelectClasif(final SelectEvent event) {
        setParamArea((ParamArea) event.getObject());
        nvd.setIdParamArea((ParamArea) event.getObject());
        PrimeFaces.current().executeScript("PF('NovedadClasifListDialog').hide();");
    }

    public void onRowDblClckSelectNotifica(final SelectEvent event) {
        nvd.setIdGenericaNotifProcesos((GenericaNotifProcesos) event.getObject());
        PrimeFaces.current().executeScript("PF('NotificaListDialog').hide();");
    }

    public void onTipoNovedadChosen(SelectEvent event) {
//        GenericaTipo car = (GenericaTipo) event.getObject();
//        System.out.println(((GenericaTipo) event.getObject()).getNombreTipoNovedad());
        setNv((GenericaTipo) event.getObject());

    }

    public void onClasifNovedadChosen(SelectEvent event) {
//        GenericaTipo car = (GenericaTipo) event.getObject();
//        System.out.println(((ParamArea) event.getObject()).getNombre());
        setParamArea((ParamArea) event.getObject());

    }

    public void buscaTipoNovedad() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("closable", true);
        options.put("appendTo", "@{body}");
        options.put("modal", true);
//        System.out.println("buscaTipoNovedad");
        PrimeFaces.current().dialog().openDynamic("/novedades/novedadTipoList", options, null);
    }

    public void buscaClasifNovedad() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("closable", true);
        options.put("appendTo", "@{body}");
        options.put("modal", true);
//        System.out.println("buscaClasifNovedad");
        PrimeFaces.current().dialog().openDynamic("/novedades/paramAreaList", options, null);
    }

    public void buscaTipoNovedad2() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("closable", true);
        options.put("appendTo", "@{body}");
        options.put("modal", true);
//        System.out.println("buscaTipoNovedad2");
        PrimeFaces.current().dialog().openDynamic("/novedades/novedadTipoDetalleNew", options, null);
    }

    public void guardar() {
        nvd.setAfectaPm(i_afectaPm ? 1 : 0);
//        System.out.println("Afecta PM :" + i_afectaPm);
//        System.out.println("Puntos : " + i_puntosPm);
        nvd.setPuntosPm(i_afectaPm ? i_puntosPm : 0);
        nvd.setAfectaProgramacion(i_afectaProg ? 1 : 0);
        nvd.setPrevencionVial(i_prevencion ? 1 : 0);
        nvd.setNotificacion(i_notificacion ? 1 : 0);
        nvd.setRequiereSoporte(i_requiereSoporte ? 1 : 0);
        nvd.setFechas(i_fechas ? 1 : 0);
        nvd.setHora(i_horas ? 1 : 0);
        nvd.setIdParamArea(paramArea);
        nvd.setUsername(user.getUsername());
        nvd.setCreado(new Date());
        if (!flag) {
            this.genericaTipoDetEjb.create(nvd);
            lista.add(nvd);
            PrimeFaces.current().executeScript("PF('NovedadTipoDetalleCreateDialog').hide()");
        } else {
            this.genericaTipoDetEjb.edit(nvd);
            if (genericaTipoDetEjb.findByUsername(user.getUsername()) != null) {
                paramArea = genericaTipoDetEjb.findByUsername(user.getUsername()).getIdParamArea();
                this.lista = this.genericaTipoDetEjb.findAllByArea(paramArea.getIdParamArea());
            }
            PrimeFaces.current().executeScript("PF('NovedadTipoDetalleCreateDialog').hide()");
//            lista.
        }
        init();
        MovilidadUtil.updateComponent("msgs");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Detalle de Novedad " + (flag ? "Actualizada" : "Registrada") + "."));
        PrimeFaces.current().executeScript("PF('NovedadTipoDetalleCreateDialog').hide();");
    }

    public void actualizar() {
        nvd.setUsername(user.getUsername());
        this.genericaTipoDetEjb.edit(nvd);
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Tipo de novedad actualizado."));
    }

    public void editar() {
        this.nvd = this.selected;
    }

    public void cambiarEstado() {
        this.selected.setEstadoReg(1);
        this.genericaTipoDetEjb.edit(selected);
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Estado Tipo de vehículo cambiado éxitosamente."));
    }

    public void addMessage() {
        String summary = i_fechas ? "Checked" : "Unchecked";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }

    public void nuevo() {
        init();
    }

    public GenericaTipoDetalles getNvd() {
        return nvd;
    }

    public void setNvd(GenericaTipoDetalles nvd) {
        this.nvd = nvd;
    }

    public GenericaTipo getNv() {
        return nv;
    }

    public void setNv(GenericaTipo nv) {
        this.nv = nv;
    }

    public List<GenericaTipoDetalles> getLista() {
        return lista;
    }

    public void setLista(List<GenericaTipoDetalles> lista) {
        this.lista = lista;
    }

    public ParamArea getParamArea() {
        return paramArea;
    }

    public void setParamArea(ParamArea paramArea) {
        this.paramArea = paramArea;
    }

    public boolean isI_fechas() {
        return i_fechas;
    }

    public void setI_fechas(boolean i_fechas) {
        this.i_fechas = i_fechas;
    }

    public boolean isI_horas() {
        return i_horas;
    }

    public void setI_horas(boolean i_horas) {
        this.i_horas = i_horas;
    }

    public boolean isI_prevencion() {
        return i_prevencion;
    }

    public void setI_prevencion(boolean i_prevencion) {
        this.i_prevencion = i_prevencion;
    }

    public boolean isI_afectaPm() {
        return i_afectaPm;
    }

    public void setI_afectaPm(boolean i_afectaPm) {
        this.i_afectaPm = i_afectaPm;
    }

    public int getI_puntosPm() {
        return i_puntosPm;
    }

    public void setI_puntosPm(int i_puntosPm) {
        this.i_puntosPm = i_puntosPm;
    }

    public boolean isI_afectaProg() {
        return i_afectaProg;
    }

    public void setI_afectaProg(boolean i_afectaProg) {
        this.i_afectaProg = i_afectaProg;
    }

    public boolean isI_requiereSoporte() {
        return i_requiereSoporte;
    }

    public void setI_requiereSoporte(boolean i_requiereSoporte) {
        this.i_requiereSoporte = i_requiereSoporte;
    }

    public boolean isI_notificacion() {
        return i_notificacion;
    }

    public void setI_notificacion(boolean i_notificacion) {
        this.i_notificacion = i_notificacion;
    }

    public GenericaTipoDetalles getSelected() {
        return selected;
    }

    public void setSelected(GenericaTipoDetalles selected) {
        this.selected = selected;
    }

    public GenericaNotifProcesos getNotifica() {
        return notifica;
    }

    public void setNotifica(GenericaNotifProcesos notifica) {
        this.notifica = notifica;
    }

    public List<GenericaTipo> getListaNovedadesTipo() {
        if (listaNovedadesTipo == null) {
            if (paramArea != null) {
                listaNovedadesTipo = genericaTipoEjb.findAllByArea(paramArea.getIdParamArea());
            } else {
                listaNovedadesTipo = null;
            }
        }
        return listaNovedadesTipo;
    }

    public void setListaNovedadesTipo(List<GenericaTipo> listaNovedadesTipo) {
        this.listaNovedadesTipo = listaNovedadesTipo;
    }

    public List<ParamArea> getListaNovedadClasif() {
        if (listaNovedadClasif == null) {
            listaNovedadClasif = paramAreaEjb.findAll();
        }
        return listaNovedadClasif;
    }

    public void setListaNovedadClasif(List<ParamArea> listaNovedadClasif) {
        this.listaNovedadClasif = listaNovedadClasif;
    }

    public List<GenericaNotifProcesos> getListaNotiProcesos() {
        if (listaNotiProcesos == null) {
            listaNotiProcesos = notiEjb.findAll(0);
        }
        return listaNotiProcesos;
    }

    public void setListaNotiProcesos(List<GenericaNotifProcesos> listaNotiProcesos) {
        this.listaNotiProcesos = listaNotiProcesos;
    }

    public boolean isFlagBtnNuevo() {
        return flagBtnNuevo;
    }

    public void setFlagBtnNuevo(boolean flagBtnNuevo) {
        this.flagBtnNuevo = flagBtnNuevo;
    }
}
