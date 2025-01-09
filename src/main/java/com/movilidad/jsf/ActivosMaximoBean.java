package com.movilidad.jsf;

import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.ejb.VehiculoPropietarioFacadeLocal;
import com.movilidad.ejb.VehiculoTipoCarroceriaFacadeLocal;
import com.movilidad.ejb.VehiculoTipoEstadoFacadeLocal;
import com.movilidad.ejb.VehiculoTipoFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.Vehiculo;
import com.movilidad.model.VehiculoPropietarios;
import com.movilidad.model.VehiculoTipo;
import com.movilidad.model.VehiculoTipoEstado;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.mx.GRNMAESTROVEHICULO;
import com.mx.GRNMAESTROVEHICULOPortType;
import com.mx.MAESTROVEHICULOASSETType;
import com.mx.MAESTROVEHICULOQueryType;
import com.mx.MAESTROVEHICULOSetType;
import com.mx.MXStringQueryType;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "activosMaximoBean")
@ViewScoped
public class ActivosMaximoBean implements Serializable {

    @EJB
    private VehiculoFacadeLocal vehiculoEjb;
    @EJB
    private GopUnidadFuncionalFacadeLocal unidadFuncionalEjb;
    @EJB
    private VehiculoTipoFacadeLocal vehiculoTipoEjb;
    @EJB
    private VehiculoPropietarioFacadeLocal vehiculoPropietarioEjb;
    @EJB
    private VehiculoTipoEstadoFacadeLocal vehiculoTipoEstadoEjb;
    @EJB
    private VehiculoTipoCarroceriaFacadeLocal vehiculoCarroceriaEjb;

    private List<Vehiculo> lstVehiculos;
    private List<Vehiculo> lstVehiculosAplicados;

    private Integer activeIndex;

    private boolean flagBotonAplicar;

    private Map<String, Vehiculo> hMVehiculos;
    private Map<String, VehiculoTipo> hMVehiculoTipos;
    private Map<String, VehiculoTipoEstado> hMVehiculoEstados;
    private Map<String, GopUnidadFuncional> hMUnidadesFuncionales;
    private Map<String, VehiculoPropietarios> hMPropietarios;

    private final UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @PostConstruct
    public void init() {
        flagBotonAplicar = false;
        lstVehiculos = new ArrayList<>();
        activeIndex = 0;
    }

    public void guardar() {
        guardarTransactional();
    }

    @Transactional
    private void guardarTransactional() {

        if (lstVehiculos != null) {
            lstVehiculosAplicados = new ArrayList<>();
            for (Vehiculo item : lstVehiculos) {
                String validacion = validarDatos(item);

                if (validacion != null) {
                    MovilidadUtil.addErrorMessage(validacion);
                    flagBotonAplicar = false;
                    cargarListas();
                    activeIndex = 1;
                    return;
                } else {
                    vehiculoEjb.create(item);
                    lstVehiculosAplicados.add(item);
                }
            }
            activeIndex = 1;
            cargarListas();
            flagBotonAplicar = false;
            lstVehiculos = new ArrayList<>();
            MovilidadUtil.addSuccessMessage(ConstantsUtil.SAVE_DONE);
        }
    }

    public void obtenerDatos() {
        cargarListas();
        obtenerDatosTransactional();
    }

    /**
     * Método que se encarga de obtener los vehículos de MAXIMO
     */
    @Transactional
    private void obtenerDatosTransactional() {
        try {
            URL url = null;
            url = new URL(SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_URL_ACTIVOS_MX));

            GRNMAESTROVEHICULO servicio = new GRNMAESTROVEHICULO();
            GRNMAESTROVEHICULOPortType port = servicio.getGRNMAESTROVEHICULOSOAP11Port(url,
                    SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_USR_ACTIVOS_MX),
                    SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_TOKEN_ACTIVOS_MX));

            MAESTROVEHICULOQueryType queryType = new MAESTROVEHICULOQueryType();
            MAESTROVEHICULOQueryType.ASSET asset = new MAESTROVEHICULOQueryType.ASSET();

            List<MXStringQueryType> assetItemList = new LinkedList<>();
            MXStringQueryType itemNum = new MXStringQueryType();
//        itemNum.setValue("BUS");
//        assetItemList.add(itemNum);

            asset.setItemnum(assetItemList);

            queryType.setASSET(asset);

            MAESTROVEHICULOSetType response = port.queryMAESTROVEHICULO(queryType);

            lstVehiculos = new ArrayList<>();

            for (MAESTROVEHICULOASSETType a : response.getASSET()) {
                agregarDatosALista(a);
            }

            flagBotonAplicar = true;

        } catch (MalformedURLException ex) {
            Logger.getLogger(ActivosMaximoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de serializar los datos obtenidos del servicio al
     * tipo Vehiculo y los agrega a la lista de vehículos obtenidos.
     *
     * @param item Objeto MAESTROVEHICULOASSETType
     */
    private void agregarDatosALista(MAESTROVEHICULOASSETType item) {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setCodigo(item.getDESCRIPTION().getValue() != null ? item.getDESCRIPTION().getValue().split(" ")[1] : "");
        vehiculo.setPlaca(item.getASSETNUM().getValue() != null ? item.getASSETNUM().getValue() : "");
        vehiculo.setNumeroSerie(item.getSERIALNUM().getValue() != null ? item.getSERIALNUM().getValue() : "");
        vehiculo.setIdVehiculoCarroceria(vehiculoCarroceriaEjb.find(ConstantsUtil.ID_VEHICULO_TIPO_CARROCERIA));
        vehiculo.setColor(ConstantsUtil.KEY_COLOR_VEHICULO_ACTIVOS_MX);
        vehiculo.setEstadoReg(0);
        vehiculo.setUsername(user.getUsername());
        vehiculo.setCreado(MovilidadUtil.fechaCompletaHoy());

        if (item.getSITEID().getValue() != null) {
            vehiculo.setIdGopUnidadFuncional(hMUnidadesFuncionales.get(item.getSITEID().getValue()));
        }

        if (!item.getASSETSPEC().isEmpty()) {
            vehiculo.setIdVehiculoTipo(hMVehiculoTipos.get(item.getASSETSPEC().get(0).getALNVALUE().getValue()));
        }

        if (item.getORGID().getValue() != null) {
            vehiculo.setIdVehiculoPropietario(hMPropietarios.get(item.getORGID().getValue()));
        }

        if (item.getSTATUS().getValue() != null) {
            vehiculo.setIdVehiculoTipoEstado(hMVehiculoEstados.get(item.getSTATUS().getValue()));
        }

        if (hMVehiculos.get(vehiculo.getPlaca()) == null) {
            lstVehiculos.add(vehiculo);
        }

    }

    private void obtenerVehiculoEstados() {
        List<VehiculoTipoEstado> lstVehiculoEstados = vehiculoTipoEstadoEjb.findByEstadoReg();

        if (lstVehiculoEstados != null || !lstVehiculoEstados.isEmpty()) {

            hMVehiculoEstados = new HashMap<>();

            for (VehiculoTipoEstado item : lstVehiculoEstados) {
                hMVehiculoEstados.put(item.getNombreTipoEstado().toUpperCase(), item);
            }
        }

    }

    private void cargarListas() {
        obtenerVehiculosRigel();
        obtenerListaUnidadesFuncionales();
        obtenerTiposVehiculos();
        obtenerPropietariosVehiculo();
        obtenerVehiculoEstados();
    }

    private void obtenerPropietariosVehiculo() {
        List<VehiculoPropietarios> lstPropietarios = vehiculoPropietarioEjb.findAll();

        if (lstPropietarios != null || !lstPropietarios.isEmpty()) {

            hMPropietarios = new HashMap<>();

            for (VehiculoPropietarios item : lstPropietarios) {
                hMPropietarios.put(item.getNombres().split(" ")[0].toUpperCase(), item);
            }
        }

    }

    private void obtenerVehiculosRigel() {
        List<Vehiculo> listaVehiculos = vehiculoEjb.findAllVehiculosByidGopUnidadFuncional(ConstantsUtil.OFF_INT);

        if (listaVehiculos != null || !listaVehiculos.isEmpty()) {

            hMVehiculos = new HashMap<>();

            for (Vehiculo item : listaVehiculos) {
                hMVehiculos.put(item.getPlaca(), item);
            }
        }

    }

    private void obtenerTiposVehiculos() {
        List<VehiculoTipo> listaVehiculoTipos = vehiculoTipoEjb.findAllEstadoR();

        if (listaVehiculoTipos != null || !listaVehiculoTipos.isEmpty()) {

            hMVehiculoTipos = new HashMap<>();

            for (VehiculoTipo item : listaVehiculoTipos) {
                hMVehiculoTipos.put(item.getNombreTipoVehiculo(), item);
            }
        }

    }

    private void obtenerListaUnidadesFuncionales() {
        List<GopUnidadFuncional> lstUnidades = unidadFuncionalEjb.findAllByEstadoReg();

        if (lstUnidades != null || !lstUnidades.isEmpty()) {

            hMUnidadesFuncionales = new HashMap<>();

            for (GopUnidadFuncional item : lstUnidades) {
                hMUnidadesFuncionales.put(item.getCodigo().replaceAll("\\s+", ""), item);
            }
        }

    }

    private String validarDatos(Vehiculo vehiculo) {

        if (MovilidadUtil.stringIsEmpty(vehiculo.getCodigo())) {
            return "No se encontró código al vehículo con placas ( " + vehiculo.getPlaca() + " )";
        }
        if (MovilidadUtil.stringIsEmpty(vehiculo.getPlaca())) {
            return "No se encontró placa al vehículo ( " + vehiculo.getCodigo() + " )";
        }

        if (vehiculo.getIdGopUnidadFuncional().getIdGopUnidadFuncional() == null) {
            return "No se encontró unidad funcional al vehículo ( " + vehiculo.getCodigo() + " )";
        }

        if (vehiculo.getIdVehiculoTipo() == null) {
            return "No se encontró tipo al vehículo ( " + vehiculo.getCodigo() + " )";
        }

        if (vehiculo.getIdVehiculoPropietario() == null) {
            return "No se encontró propietario asignado al vehículo ( " + vehiculo.getCodigo() + " )";
        }

        if (vehiculo.getIdVehiculoTipoEstado() == null) {
            return "No se encontró estado asignado al vehículo ( " + vehiculo.getCodigo() + " )";
        }

        if (hMVehiculos.get(vehiculo.getPlaca()) != null) {
            return "El vehículo a aplicar YA EXISTE ( " + vehiculo.getCodigo() + " )";
        }

        return null;
    }

    public List<Vehiculo> getLstVehiculos() {
        return lstVehiculos;
    }

    public void setLstVehiculos(List<Vehiculo> lstVehiculos) {
        this.lstVehiculos = lstVehiculos;
    }

    public Integer getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(Integer activeIndex) {
        this.activeIndex = activeIndex;
    }

    public List<Vehiculo> getLstVehiculosAplicados() {
        return lstVehiculosAplicados;
    }

    public void setLstVehiculosAplicados(List<Vehiculo> lstVehiculosAplicados) {
        this.lstVehiculosAplicados = lstVehiculosAplicados;
    }

    public boolean isFlagBotonAplicar() {
        return flagBotonAplicar;
    }

    public void setFlagBotonAplicar(boolean flagBotonAplicar) {
        this.flagBotonAplicar = flagBotonAplicar;
    }

}
