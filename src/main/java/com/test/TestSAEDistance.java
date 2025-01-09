/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

import com.freeway.ArrayOfDetailDistanceTimeLogicDetailDistanceTimeRow;
import com.freeway.ISae;
import com.freeway.Sae;

/**
 *
 * @author luis
 */
public class TestSAEDistance {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Sae sae = new Sae();
        ISae iSae;
        ISae isae = sae.getHttpSaeService();
        ArrayOfDetailDistanceTimeLogicDetailDistanceTimeRow distancias = isae.getDistanceTime();

    }
}
