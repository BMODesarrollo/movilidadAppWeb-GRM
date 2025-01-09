package com.movilidad.jsf;

import com.movilidad.ejb.AccChecklistFacadeLocal;
import com.movilidad.ejb.AccTipoDocsFacadeLocal;
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.model.AccChecklist;
import com.movilidad.model.AccTipoDocs;
import com.movilidad.model.NovedadTipoDetalles;
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
@Named(value = "accChecklistBean")
@ViewScoped
public class AccChecklistManagedBean implements Serializable {

    @EJB
    private AccChecklistFacadeLocal accChecklistEjb;
    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTipoDetallesEJb;
    @EJB
    private AccTipoDocsFacadeLocal accTipoDocsEjb;

    private AccChecklist accChecklist;
    private AccTipoDocs tipoDocs;
    private AccChecklist selected;
    private NovedadTipoDetalles detalle;

    private List<AccChecklist> lstAccChecklists;
    private List<AccTipoDocs> lstAccTipoDocumentos;
    private List<AccTipoDocs> lstAccTipoDocumentosAux;
    private List<NovedadTipoDetalles> lstNovedadTipoDetalles;

    private boolean isEditing;
    private boolean isRequired;

    private final PrimeFaces current = PrimeFaces.current();

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public void nuevo() {
        detalle = null;
        accChecklist = new AccChecklist();
        tipoDocs = null;
        lstAccTipoDocumentos = new ArrayList<>();
        selected = null;
        isEditing = false;
    }

    public void prepareListNovedadTipoDetalle() {
        lstNovedadTipoDetalles = null;
        detalle = new NovedadTipoDetalles();

        if (isEditing) {
            current.ajax().update("frmCheckListEdit:tipo_evento");
        } else {
            current.ajax().update("frmCheckListAdd:tipo_evento");
        }
    }

    public void onRowNovedadTipoDetallesClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof NovedadTipoDetalles) {
            setDetalle((NovedadTipoDetalles) event.getObject());
        }
        if (!isEditing) {
            current.executeScript("PF('dtNovedadTipoDetalle').clearFilters();");
            current.ajax().update(":frmPmNovedadTipoDetalleList:dtNovedadTipoDetalles");
        } else {
            current.executeScript("PF('PmNovedadTipoDetalleListDialogEdit').clearFilters();");
            current.ajax().update(":frmPmNovedadTipoDetalleListEdit:dtNovedadTipoDetalles");
        }
    }

    public void prepareListAccTipoDocs() {
        lstAccTipoDocumentosAux = null;
        tipoDocs = new AccTipoDocs();

        if (isEditing) {
            current.ajax().update("frmCheckListEdit:tipo_documento");
        }
    }

    public void onRowAccTipoDocsClckSelect(final SelectEvent event) {
        if (event.getObject() instanceof AccTipoDocs) {
            setTipoDocs((AccTipoDocs) event.getObject());
            if (!isEditing) {
                if (lstAccTipoDocumentos.contains(tipoDocs)) {
                    current.ajax().update(":msgs");
                    MovilidadUtil.addErrorMessage("El tipo de documento seleccionado ya se encuentra en la lista");
                    return;
                }
                lstAccTipoDocumentos.add(tipoDocs);
                current.executeScript("PF('dtAccTipoDoc').clearFilters();");
                current.ajax().update(":frmAccTipoDocsList:dtAccTipoDocs");
            } else {
                current.executeScript("PF('dtAccTipoDocEdit').clearFilters();");
                current.ajax().update(":frmAccTipoDocsListEdit:dtAccTipoDocs");
            }
        }
    }

    public void eliminarDocumentoLista(AccTipoDocs atd) {
        lstAccTipoDocumentos.remove(atd);
    }

    public void editar() {
        if (selected == null) {
            current.ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("Debe seleccionar un registro");
            return;
        }
        isEditing = true;
        accChecklist = selected;
        detalle = accChecklist.getIdNovedadTipoDetalle();
        tipoDocs = accChecklist.getIdAccTipoDocumento();
        isRequired = accChecklist.getRequerido() == 1;

        current.executeScript("PF('checklistEditDlg').show();");
    }

    public void guardar() {
        if (!validarDatos()) {
            for (AccTipoDocs acd : lstAccTipoDocumentos) {
                if (accChecklistEjb.verificarRepetido(detalle.getIdNovedadTipoDetalle(), acd.getIdAccTipoDocs()) == null) {
                    accChecklist.setIdAccTipoDocumento(acd);
                    accChecklist.setRequerido(1);
                    accChecklist.setIdNovedadTipoDetalle(detalle);
                    accChecklist.setCreado(new Date());
                    accChecklist.setEstadoReg(0);
                    accChecklist.setUsername(user.getUsername());
                    accChecklistEjb.create(accChecklist);
                    lstAccChecklists.add(accChecklist);
                } else {
                    MovilidadUtil.addErrorMessage("Está intentando ingresar un registro con tipo de evento (" + detalle.getTituloTipoNovedad() + ") y tipo de documento (" + acd.getTipoDocs() + "), el cúal YA EXISTE.");
                    return;
                }
            }
            nuevo();
            MovilidadUtil.addSuccessMessage("Registro guardado éxitosamente");
        }
    }

    private boolean validarDatos() {
        if (detalle == null || detalle.getTituloTipoNovedad() == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un tipo de evento");
            return true;
        }

        if (!isEditing) {
            if (lstAccTipoDocumentos == null || lstAccTipoDocumentos.isEmpty()) {
                MovilidadUtil.addErrorMessage("Debe seleccionar al menos un tipo de documento");
                return true;
            }
        } else {
            if (tipoDocs == null || tipoDocs.getTipoDocs() == null) {
                MovilidadUtil.addErrorMessage("Debe seleccionar un tipo de documento");
                return true;
            }
        }

        return false;
    }

    public void actualizar() {
        if (!validarDatos()) {
            if (!verificarActualizar()) {
                accChecklist.setIdAccTipoDocumento(tipoDocs);
                accChecklist.setRequerido(isRequired ? 1 : 2);
                accChecklist.setIdNovedadTipoDetalle(detalle);
                accChecklist.setModificado(new Date());
                accChecklist.setUsername(user.getUsername());
                accChecklistEjb.edit(accChecklist);
                selected = null;
                current.executeScript("PF('checklistEditDlg').hide();");
                MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
            } else {
                MovilidadUtil.addErrorMessage("Está intentando ingresar un registro con tipo de evento (" + detalle.getTituloTipoNovedad() + ") y tipo de documento (" + tipoDocs.getTipoDocs() + "), el cúal YA EXISTE.");
            }
        }
    }

    private boolean verificarActualizar() {
        if (!detalle.getIdNovedadTipoDetalle().equals(selected.getIdNovedadTipoDetalle().getIdNovedadTipoDetalle())) {
            if (accChecklistEjb.verificarRepetido(detalle.getIdNovedadTipoDetalle(), tipoDocs.getIdAccTipoDocs()) != null) {
                return true;
            }
        }
        if (!tipoDocs.getIdAccTipoDocs().equals(selected.getIdAccTipoDocumento().getIdAccTipoDocs())) {
            if (accChecklistEjb.verificarRepetido(detalle.getIdNovedadTipoDetalle(), tipoDocs.getIdAccTipoDocs()) != null) {
                return true;
            }
        }
        return false;
    }

    public AccChecklist getAccChecklist() {
        return accChecklist;
    }

    public void setAccChecklist(AccChecklist accChecklist) {
        this.accChecklist = accChecklist;
    }

    public AccChecklist getSelected() {
        return selected;
    }

    public void setSelected(AccChecklist selected) {
        this.selected = selected;
    }

    public NovedadTipoDetalles getDetalle() {
        return detalle;
    }

    public void setDetalle(NovedadTipoDetalles detalle) {
        this.detalle = detalle;
    }

    public List<AccChecklist> getLstAccChecklists() {
        lstAccChecklists = accChecklistEjb.findAll();
        return lstAccChecklists;
    }

    public void setLstAccChecklists(List<AccChecklist> lstAccChecklists) {
        this.lstAccChecklists = lstAccChecklists;
    }

    public List<AccTipoDocs> getLstAccTipoDocumentos() {
        return lstAccTipoDocumentos;
    }

    public void setLstAccTipoDocumentos(List<AccTipoDocs> lstAccTipoDocumentos) {
        this.lstAccTipoDocumentos = lstAccTipoDocumentos;
    }

    public List<AccTipoDocs> getLstAccTipoDocumentosAux() {
        lstAccTipoDocumentosAux = accTipoDocsEjb.estadoReg();
        return lstAccTipoDocumentosAux;
    }

    public void setLstAccTipoDocumentosAux(List<AccTipoDocs> lstAccTipoDocumentosAux) {
        this.lstAccTipoDocumentosAux = lstAccTipoDocumentosAux;
    }

    public List<NovedadTipoDetalles> getLstNovedadTipoDetalles() {
        lstNovedadTipoDetalles = novedadTipoDetallesEJb.findTipoAcc();
        return lstNovedadTipoDetalles;
    }

    public void setLstNovedadTipoDetalles(List<NovedadTipoDetalles> lstNovedadTipoDetalles) {
        this.lstNovedadTipoDetalles = lstNovedadTipoDetalles;
    }

    public AccTipoDocs getTipoDocs() {
        return tipoDocs;
    }

    public void setTipoDocs(AccTipoDocs tipoDocs) {
        this.tipoDocs = tipoDocs;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public boolean isIsRequired() {
        return isRequired;
    }

    public void setIsRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }
}