package com.movilidad.jsf;

import com.movilidad.ejb.NominaServerParamEmpresaFacadeLocal;
import com.movilidad.ejb.NominaServerParamFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.NominaServerParam;
import com.movilidad.model.NominaServerParamEmpresa;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "nominaServerParamEmpresaBean")
@ViewScoped
public class NominaServerParamEmpresaBean implements Serializable {

    @EJB
    private NominaServerParamEmpresaFacadeLocal nominaServerParamEmpresaEjb;
    @EJB
    private NominaServerParamFacadeLocal nominaServerParamEjb;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private String codigo;

    private NominaServerParamEmpresa selected;

    private List<NominaServerParamEmpresa> lista;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        consultar();
    }

    public void guardar() {

        NominaServerParam param = nominaServerParamEjb.find(ConstantsUtil.ID_NOMINA_SERVER_PARAM);

        if (param == null) {
            MovilidadUtil.addErrorMessage("NO se encontró registro de parametrización");
            return;
        }

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        NominaServerParamEmpresa obj = nominaServerParamEmpresaEjb.findByUfAndCodigoSw(0, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), codigo.trim());

        if (obj != null) {
            MovilidadUtil.addErrorMessage("El código a agregar YA EXISTE");
            return;
        }

        NominaServerParamEmpresa nominaServerParamEmpresa = new NominaServerParamEmpresa();
        nominaServerParamEmpresa.setIdGopUnidadFuncional(new GopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));
        nominaServerParamEmpresa.setCodSwNomina(codigo);
        nominaServerParamEmpresa.setIdNominaServerParam(param);
        nominaServerParamEmpresa.setEstadoReg(0);
        nominaServerParamEmpresa.setUsername(user.getUsername());
        nominaServerParamEmpresa.setCreado(MovilidadUtil.fechaCompletaHoy());
        nominaServerParamEmpresaEjb.create(nominaServerParamEmpresa);

        consultar();
        limpiarCampos();

    }

    public void eliminarRegistro() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        selected.setEstadoReg(1);
        nominaServerParamEmpresaEjb.edit(selected);
        consultar();
        MovilidadUtil.addSuccessMessage("Registro eliminado éxitosamente");
    }

    /**
     * Evento que se dispara al editar el código de sw nómina que se encuentra
     * registrado en la tabla
     *
     * @param event
     */
    public void onCellEdit(CellEditEvent event) {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        String newValue = (String) event.getNewValue();
        String oldValue = (String) event.getOldValue();

        NominaServerParamEmpresa obj = (NominaServerParamEmpresa) ((DataTable) event.getComponent()).getRowData();
        NominaServerParamEmpresa objAux = nominaServerParamEmpresaEjb.findByUfAndCodigoSw(obj.getIdNominaServerParamEmpresa(), unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), newValue.trim());

        if (objAux != null) {
            obj.setCodSwNomina(oldValue);
            MovilidadUtil.addErrorMessage("El código a modificar YA EXISTE");
            return;
        }

        obj.setCodSwNomina(newValue);
        obj.setModificado(MovilidadUtil.fechaCompletaHoy());
        nominaServerParamEmpresaEjb.edit(obj);
        MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
    }

    public void consultar() {
        lista = nominaServerParamEmpresaEjb.findAllByEstadoRegAndUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    private void limpiarCampos() {
        codigo = null;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<NominaServerParamEmpresa> getLista() {
        return lista;
    }

    public void setLista(List<NominaServerParamEmpresa> lista) {
        this.lista = lista;
    }

    public NominaServerParamEmpresa getSelected() {
        return selected;
    }

    public void setSelected(NominaServerParamEmpresa selected) {
        this.selected = selected;
    }

}
