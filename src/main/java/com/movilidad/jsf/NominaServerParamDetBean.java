package com.movilidad.jsf;

import com.movilidad.ejb.NominaServerParamDetFacadeLocal;
import com.movilidad.ejb.NominaServerParamFacadeLocal;
import com.movilidad.model.NominaServerParam;
import com.movilidad.model.NominaServerParamDet;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
@Named(value = "nominaServerParamDetBean")
@ViewScoped
public class NominaServerParamDetBean implements Serializable {

    @EJB
    private NominaServerParamDetFacadeLocal nominaServerParamDetEjb;
    @EJB
    private NominaServerParamFacadeLocal nominaServerParamEjb;

    @Inject
    private novedadTipoAndDetalleBean tipoAndDetalleBean;

    private final String compUpdateVista = ":frmPrincipal:novedad_tipo_detalle";

    private String codigo;
    private String tipo;
    private String generaNov;
    private String codigoMaus;
    private String tipoIncapacidad;

    private Integer clasificacion;

    private NominaServerParamDet selected;

    private List<NominaServerParamDet> lstDetalles;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        clasificacion = null;
        tipoAndDetalleBean.setCompUpdateVistaCreateNov(compUpdateVista);
        consultar();
    }

    public void cargarListaDetalleNovedad() {
        MovilidadUtil.updateComponent(":dialog_nov_tipo_det_form");
        tipoAndDetalleBean.prepareListNovedadTipoDetalleNomina(obtenerIdDetalles());
    }

    public void agregarDetalle() {

        NominaServerParam param = nominaServerParamEjb.find(ConstantsUtil.ID_NOMINA_SERVER_PARAM);

        if (param == null) {
            MovilidadUtil.addErrorMessage("NO se encontró registro de parametrización");
            return;
        }

        if (tipoAndDetalleBean.getNovedadTipoDet() == null) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar un Detalle");
            return;
        }

        NominaServerParamDet detalle = new NominaServerParamDet();
        detalle.setIdNovedadTipoDetalle(tipoAndDetalleBean.getNovedadTipoDet());
        detalle.setCodConc(codigo);
        detalle.setTipo(tipo);
        detalle.setClasificacion(clasificacion);
        detalle.setGeneraNov(generaNov);
        detalle.setCodMause(codigoMaus);
        detalle.setTipoIncapacidad(tipoIncapacidad);
        detalle.setIdNominaServerParam(param);
        detalle.setEstadoReg(0);
        detalle.setUsername(user.getUsername());
        detalle.setCreado(MovilidadUtil.fechaCompletaHoy());
        nominaServerParamDetEjb.create(detalle);

        consultar();
        limpiarCampos();

    }

    public void eliminarRegistro() {
        selected.setEstadoReg(1);
        nominaServerParamDetEjb.edit(selected);
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
        String newValue = (String) event.getNewValue();

        NominaServerParamDet obj = (NominaServerParamDet) ((DataTable) event.getComponent()).getRowData();
        obj.setCodConc(newValue);
        obj.setModificado(MovilidadUtil.fechaCompletaHoy());
        nominaServerParamDetEjb.edit(obj);
        MovilidadUtil.addSuccessMessage("Registro actualizado éxitosamente");
    }

    /**
     * Método que se encarga de retornar los ID's de los detalles que YA se
     * encuentran registrados
     *
     * @return Arreglo int en caso de que existan registros de
     * NominaServerParamDet, de lo contrario retorna NULL
     */
    private Set<Integer> obtenerIdDetalles() {

        if (lstDetalles == null || lstDetalles.isEmpty()) {
            return null;
        }

        return lstDetalles
                .stream()
                .map(x -> x.getIdNovedadTipoDetalle().getIdNovedadTipoDetalle())
                .collect(Collectors.toSet());
    }

    private void limpiarCampos() {
        codigo = null;
        tipo = null;
        generaNov = null;
        codigoMaus = null;
        tipoIncapacidad = null;
        clasificacion = null;
        tipoAndDetalleBean.setNovedadTipoDet(null);
    }

    private void consultar() {
        lstDetalles = nominaServerParamDetEjb.findAllByEstadoReg();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<NominaServerParamDet> getLstDetalles() {
        return lstDetalles;
    }

    public void setLstDetalles(List<NominaServerParamDet> lstDetalles) {
        this.lstDetalles = lstDetalles;
    }

    public NominaServerParamDet getSelected() {
        return selected;
    }

    public void setSelected(NominaServerParamDet selected) {
        this.selected = selected;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGeneraNov() {
        return generaNov;
    }

    public void setGeneraNov(String generaNov) {
        this.generaNov = generaNov;
    }

    public String getCodigoMaus() {
        return codigoMaus;
    }

    public void setCodigoMaus(String codigoMaus) {
        this.codigoMaus = codigoMaus;
    }

    public Integer getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Integer clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getTipoIncapacidad() {
        return tipoIncapacidad;
    }

    public void setTipoIncapacidad(String tipoIncapacidad) {
        this.tipoIncapacidad = tipoIncapacidad;
    }

}
