package com.movilidad.jsf;

import com.movilidad.ejb.GenericaFacadeLocal;
import com.movilidad.ejb.NominaServerParamDetGenFacadeLocal;
import com.movilidad.ejb.NominaServerParamFacadeLocal;
import com.movilidad.model.NominaServerParam;
import com.movilidad.model.NominaServerParamDetGen;
import com.movilidad.model.ParamAreaUsr;
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
@Named(value = "genericaNominaServerParamDetGenBean")
@ViewScoped
public class GenericaNominaServerParamDetGenBean implements Serializable {

    @EJB
    private NominaServerParamDetGenFacadeLocal nominaServerParamDetEjb;
    @EJB
    private NominaServerParamFacadeLocal nominaServerParamEjb;
    @EJB
    private GenericaFacadeLocal genericaEjb;

    @Inject
    private NovedadTipoAndDetalleGenBean tipoAndDetalleBean;

    private final String compUpdateVista = ":frmPrincipal:pnlDatos";

    private String codigo;
    private String tipo;
    private String generaNov;
    private String codigoMaus;

    private Integer clasificacion;

    private NominaServerParamDetGen selected;

    private List<NominaServerParamDetGen> lstDetalles;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        clasificacion = null;
        tipoAndDetalleBean.setIdParamArea(obtenerAreaUsuario());
        tipoAndDetalleBean.setCompUpdateVistaCreateNov(compUpdateVista);
        consultar();
    }

    public void cargarListaDetalleNovedad() {
        MovilidadUtil.updateComponent(":dialog_nov_tipo_det_form");
        tipoAndDetalleBean.prepareListNovedadTipoDetalleNomina(obtenerIdDetalles());
    }

    public void agregarDetalle() {

        if (tipoAndDetalleBean.getIdParamArea() == null) {
            MovilidadUtil.addErrorMessage("El usuario logueado NO tiene un área relacionada");
            return;
        }

        NominaServerParam param = nominaServerParamEjb.find(ConstantsUtil.ID_NOMINA_SERVER_PARAM);

        if (param == null) {
            MovilidadUtil.addErrorMessage("NO se encontró registro de parametrización");
            return;
        }

        if (tipoAndDetalleBean.getNovedadTipoDet() == null) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar un Detalle");
            return;
        }

        NominaServerParamDetGen detalle = new NominaServerParamDetGen();
        detalle.setIdGenericaTipoDetalle(tipoAndDetalleBean.getNovedadTipoDet());
        detalle.setCodConc(codigo);
        detalle.setTipo(tipo);
        detalle.setGeneraNov(generaNov);
        detalle.setCodMause(codigoMaus);
        detalle.setClasificacion(clasificacion);
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

        NominaServerParamDetGen obj = (NominaServerParamDetGen) ((DataTable) event.getComponent()).getRowData();
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
     * NominaServerParamDetGen, de lo contrario retorna NULL
     */
    private Set<Integer> obtenerIdDetalles() {

        if (lstDetalles == null || lstDetalles.isEmpty()) {
            return null;
        }

        return lstDetalles
                .stream()
                .map(x -> x.getIdGenericaTipoDetalle().getIdGenericaTipoDetalle())
                .collect(Collectors.toSet());
    }

    private Integer obtenerAreaUsuario() {
        ParamAreaUsr paramAreaUsr = genericaEjb.findByUsername(user.getUsername());

        if (paramAreaUsr != null) {
            return paramAreaUsr.getIdParamArea().getIdParamArea();
        }

        return null;

    }

    private void limpiarCampos() {
        codigo = null;
        tipo = null;
        generaNov = null;
        codigoMaus = null;
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

    public List<NominaServerParamDetGen> getLstDetalles() {
        return lstDetalles;
    }

    public void setLstDetalles(List<NominaServerParamDetGen> lstDetalles) {
        this.lstDetalles = lstDetalles;
    }

    public NominaServerParamDetGen getSelected() {
        return selected;
    }

    public void setSelected(NominaServerParamDetGen selected) {
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

}
