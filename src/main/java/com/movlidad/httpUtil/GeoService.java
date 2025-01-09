package com.movlidad.httpUtil;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.movilidad.util.beans.CurrentLocation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author Carlos Ballestas
 * @param <T>
 */
public class GeoService<T> {

    private static JSONObject objeto;
    private static JSONObject data;
    private static final ObjectMapper mapper = new ObjectMapper();

    private Class<T> tipoClase;

    @PostConstruct
    void init() {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public GeoService() {
    }

    public GeoService(Class<T> typeParameterClass) {
        this.tipoClase = typeParameterClass;
    }

    public List<T> getData(String url) {
        try {
            List<T> lista;
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet(url);
            get.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(get);

            if (!(response.getStatusLine().getStatusCode() == 200)) {
                throw new Exception("Error al obtener datos");
            }

            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);

            CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, tipoClase);

            lista = mapper.readValue(result, listType);

            return lista;

        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }

    public static CurrentLocation getCurrentPosition(String url) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet(url);
            HttpResponse response = httpClient.execute(get);
            String toString = EntityUtils.toString(response.getEntity());
            JSONArray ja = new JSONArray(toString);
            List<CurrentLocation> currentLocations = new ArrayList<>();
            for (Object json : ja) {
                currentLocations.add(mapper.readValue(json.toString(), CurrentLocation.class));
            }
            return currentLocations.isEmpty() ? null : currentLocations.get(0);
        } catch (IOException | JSONException e) {
//            e.printStackTrace();
            return null;
        }
    }
}
