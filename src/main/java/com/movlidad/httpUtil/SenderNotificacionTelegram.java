/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movlidad.httpUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.primefaces.json.JSONObject;

/**
 *
 * @author solucionesit
 */
public class SenderNotificacionTelegram {

    private static JSONObject objeto;
    private static JSONObject data;

    private SenderNotificacionTelegram() {
    }

    public static boolean send(JSONObject objeto, String postUrl) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(postUrl);
            StringEntity postingString = new StringEntity(objeto.toString(), "UTF-8");//gson.tojson() converts your pojo to json
            post.setEntity(postingString);
            post.setHeader("Content-type", "application/json;charset=UTF-8");
            
            HttpResponse response = httpClient.execute(post);
            return response.getStatusLine().getStatusCode() == 200;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static JSONObject getObjeto() {
        if (objeto == null) {
            objeto = new JSONObject();
        }
        return objeto;
    }

    public static JSONObject getData() {
        if (data == null) {
            data = new JSONObject();
        }
        return data;
    }

}
