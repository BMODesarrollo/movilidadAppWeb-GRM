package com.movilidad.util.beans;

import java.io.Serializable;

/**
 *
 * @author Carlos Ballestas
 */
public class VehiculosDetenidosDTO implements Serializable {

    private String _Status;
    private String _Vehicle;
    private String _VehicleTimestamp;

    public VehiculosDetenidosDTO() {
    }

    public VehiculosDetenidosDTO(String _Status, String _Vehicle, String _VehicleTimestamp) {
        this._Status = _Status;
        this._Vehicle = _Vehicle;
        this._VehicleTimestamp = _VehicleTimestamp;
    }

    public String get_Status() {
        return _Status;
    }

    public void set_Status(String _Status) {
        this._Status = _Status;
    }

    public String get_VehicleTimestamp() {
        return _VehicleTimestamp.substring(0, 19).replace("T", " ");
    }

    public void set_VehicleTimestamp(String _VehicleTimestamp) {
        this._VehicleTimestamp = _VehicleTimestamp;
    }

    public String get_Vehicle() {
        return _Vehicle;
    }

    public void set_Vehicle(String _Vehicle) {
        this._Vehicle = _Vehicle;
    }

}
