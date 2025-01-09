/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function calcularhe() {
    var h_body, elem, layout_header, layout_headerPercent, btns_panel_izq,
            btns_panel_izqPercent, fecha_pp, fecha_ppPercent,
            btns_i, btns_iPercent, btns_ii, btns_iiPercent, resul;
    h_body = document.body.clientHeight
    elem = document.querySelector('.ui-datatable-scrollable-body');

    layout_header = document.getElementById('layout-header').clientHeight;
    layout_headerPercent = layout_header * 100 / h_body;

    btns_panel_izq = document.getElementById('btns_panel_izq').clientHeight;
    btns_panel_izqPercent = btns_panel_izq * 100 / h_body;

    fecha_pp = document.querySelector('.fecha_pp').clientHeight;
    fecha_ppPercent = fecha_pp * 100 / h_body;

    btns_i = document.querySelector('.btns_i').clientHeight;
    btns_iPercent = btns_i * 100 / h_body;
    
    resul = 90 - (layout_headerPercent + btns_panel_izqPercent + fecha_ppPercent + btns_iPercent + btns_iiPercent);
    elem.style.setProperty('max-height', resul + 'vh');
}