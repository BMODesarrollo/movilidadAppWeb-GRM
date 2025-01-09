/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movlidad.httpUtil;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.movilidad.model.Empleado;
import com.movilidad.util.beans.GeocodingDTO;
import com.movilidad.utils.MovilidadUtil;

/**
 *
 * @author soluciones-it
 */
public class GoogleApiGeocodingServices {

    private GeoApiContext context;

    private GoogleApiGeocodingServices() {
    }

    public GoogleApiGeocodingServices(String keyApi) {
        context = new GeoApiContext.Builder()
                .apiKey(keyApi)
                .build();
    }

    public boolean onSetLatLngFromAddressForEmpleado(Empleado e, String direccion) {
        try {
            GeocodingResult[] resul = GeocodingApi
                    .newRequest(context)
                    .address(direccion)
                    .region("co")
                    .await();
            String localidad = null;
            String subLocalidad = null;
            if (resul.length == 0) {
                MovilidadUtil.addErrorMessage("No se ha enconrado información con la dirección suministrada");
                return false;
            }
            for (GeocodingResult gr : resul) {
                e.setLatitud(String.valueOf(gr.geometry.location.lat));
                e.setLongitud(String.valueOf(gr.geometry.location.lng));
                for (AddressComponent ac : gr.addressComponents) {
                    for (AddressComponentType act : ac.types) {
                        if (act.name().equalsIgnoreCase(AddressComponentType.LOCALITY.name())) {
                            localidad = ac.longName;
                        }
                        if (act.name().equalsIgnoreCase(AddressComponentType.SUBLOCALITY.name())) {
                            subLocalidad = ac.longName;
                        }
                    }
                }
            }
            if (subLocalidad != null) {
                e.setLocalidad(subLocalidad);
            } else {
                e.setLocalidad(localidad);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            MovilidadUtil.addErrorMessage("Ha ocurrido un error, reportar a sistemas");
            return false;
        }
    }

    public GeocodingDTO onAddressFromLatLng(Float lat, Float lng) {
        try {
            GeocodingResult[] resul = GeocodingApi
                    .reverseGeocode(context, new LatLng(lat, lng))
                    .await();
            if (resul.length == 0) {
                MovilidadUtil.addErrorMessage("No se ha enconrado información con la dirección suministrada");
                return null;
            }
            for (GeocodingResult gr : resul) {
                return new GeocodingDTO(lat, lng, gr.formattedAddress);
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            MovilidadUtil.addErrorMessage("Ha ocurrido un error, reportar a sistemas");
            return null;
        }
    }

}
