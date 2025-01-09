/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoViaFacadeLocal;
import com.movilidad.ejb.VehiculoViaLogFacadeLocal;
import com.movilidad.model.Vehiculo;
import com.movilidad.model.VehiculoVia;
import com.movilidad.model.VehiculoViaLog;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;
import javax.ejb.EJB;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Cesar Mercado
 */
@Named(value = "vehiculoViaJSF")
@ViewScoped
public class VehiculoViaJSF implements Serializable {

    @EJB
    private VehiculoViaFacadeLocal vehiculoViaFacadeLocal;
    @EJB
    private VehiculoViaLogFacadeLocal vehiculoViaLogFacadeLocal;
    @Inject
    private PrgTcJSFManagedBean prgTcJSFManagedBean;

    // vehiculo que seleccionan desde vehiculos disponibles
    private Vehiculo vehiculoSelect;

    private static final boolean VEHICULO_OFF_ROAD = false;
    private static final String FROM_RIGEL = "RIGEL";

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of VehiculoViaJSF
     */
    public VehiculoViaJSF() {
    }

    public void updateVehiculoOffRoad() throws Exception {
        if (vehiculoSelect == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un vehículo");
            return;
        }
        if (isVehiculoOnRoad(vehiculoSelect)) {
            updateVehiculoVia(vehiculoSelect,
                    VEHICULO_OFF_ROAD,
                    user.getUsername(),
                    "Disponible desde RIGEL por: " + user.getUsername());
            MovilidadUtil.addSuccessMessage("Vehículo actualizado a disponible");
            vehiculoSelect = null;
            prgTcJSFManagedBean.listarVehiculoDispo();
            return;
        }
        MovilidadUtil.addErrorMessage("Vehículo no registrado como disponible en vía");
    }

    public void onRowlClckSelect(final SelectEvent event) throws Exception {
        setVehiculoSelect((Vehiculo) event.getObject());
    }

    private boolean isVehiculoOnRoad(Vehiculo vehiculo) throws Exception {
        if (vehiculo == null) {
            return false;
        }
        return findByVehiculo(vehiculo).map(VehiculoVia::isOnRoad).orElse(false);
    }

    private Optional<VehiculoVia> findByVehiculo(Vehiculo vehiculo) throws Exception {
        return Optional.of(vehiculoViaFacadeLocal
                .findByIdVehiculo(vehiculo.getIdVehiculo()));
    }

    private void createVehiculoViaLog(VehiculoVia vehiculoVia, String username, boolean onRoad, boolean isApplied, String description)
            throws Exception {
        VehiculoViaLog vvl = new VehiculoViaLog();
        vvl.setIdVehiculoVia(vehiculoVia);
        vvl.setCreado(new Date());
        vvl.setOnRoad(onRoad);
        vvl.setApplied(isApplied);
        vvl.setOrigen(FROM_RIGEL);
        vvl.setUsername(username);
        vvl.setDescription(description);
        vehiculoViaLogFacadeLocal.create(vvl);
    }

    private Optional<VehiculoVia> updateVehiculoVia(Vehiculo vehiculo, boolean status, String username, String description)
            throws Exception {
        Optional<VehiculoVia> opVehiculoVia = findByVehiculo(vehiculo);
        if (opVehiculoVia.isPresent()) {
            boolean isApplied = false;
            VehiculoVia vehiculoVia = opVehiculoVia.get();
            if (vehiculoVia.isOnRoad() != status) {
                isApplied = true;
                vehiculoVia.setLastUsername(username);
                vehiculoVia.setOnRoad(status);
                vehiculoVia.setModificado(new Date());
                vehiculoVia.setIdGopUnidadFuncional(vehiculoVia.getIdVehiculo().getIdGopUnidadFuncional());
                vehiculoVia.setLastDescription(description);
                vehiculoViaFacadeLocal.edit(vehiculoVia);
            }
            createVehiculoViaLog(vehiculoVia, username, status, isApplied, description);
        }
        return opVehiculoVia;
    }

    public Vehiculo getVehiculoSelect() {
        return vehiculoSelect;
    }

    public void setVehiculoSelect(Vehiculo vehiculoSelect) {
        this.vehiculoSelect = vehiculoSelect;
    }

}
