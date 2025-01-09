/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.util.beans;

/**
 *
 * @author soluciones-it
 */
public class CurrentLocation {

    private Float _BatteryCharge;
    private Float _Latitude;
    private Float _Longitude;
    private String _VehicleTimestamp;

    public CurrentLocation() {
    }

    public CurrentLocation(Float _BatteryCharge, Float _Latitude, Float _Longitude, String _VehicleTimestamp) {
        this._BatteryCharge = _BatteryCharge;
        this._Latitude = _Latitude;
        this._Longitude = _Longitude;
        this._VehicleTimestamp = _VehicleTimestamp;
    }

    public Float get_BatteryCharge() {
        return _BatteryCharge;
    }

    public void set_BatteryCharge(Float _BatteryCharge) {
        this._BatteryCharge = _BatteryCharge;
    }

    public Float get_Latitude() {
        return _Latitude;
    }

    public void set_Latitude(Float _Latitude) {
        this._Latitude = _Latitude;
    }

    public Float get_Longitude() {
        return _Longitude;
    }

    public void set_Longitude(Float _Longitude) {
        this._Longitude = _Longitude;
    }

    public String get_VehicleTimestamp() {
        return _VehicleTimestamp;
    }

    public void set_VehicleTimestamp(String _VehicleTimestamp) {
        this._VehicleTimestamp = _VehicleTimestamp;
    }

    @Override
    public String toString() {
        return "CurrentLocation{" + "_BatteryCharge=" + _BatteryCharge + ", _Latitude=" + _Latitude + ", _Longitude=" + _Longitude + ", _VehicleTimestamp=" + _VehicleTimestamp + '}';
    }

}
