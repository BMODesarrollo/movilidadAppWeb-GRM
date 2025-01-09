/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movilidad.model;

import com.movilidad.dto.TpConteoDTO;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author julian.arevalo
 */
@Entity
@XmlRootElement
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "TpConteoDTOMapping",
            classes = {
                @ConstructorResult(targetClass = TpConteoDTO.class,
                        columns = {
                            @ColumnResult(name = "id", type = int.class),
                            @ColumnResult(name = "total", type = int.class),
                            @ColumnResult(name = "confirmado", type = int.class),
                            @ColumnResult(name = "depot_type", type = int.class),
                            @ColumnResult(name = "porcentaje", type = BigDecimal.class),
                        }
                )
            }),})

public class TecnicoPatio implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prg_tc")
    private Integer id_prg_tc;
    
    @Column(name = "id_vehiculo")
    private Integer id_vehiculo;
    
    @Column(name = "id_empleado")
    private Integer id_empleado;
        
    @Column(name = "codigo_tm")
    private String codigo_tm;
    
    @Column(name = "nombre")
    private String nombre;
        
    @Column(name = "servbus")
    private String servbus;

    @Column(name = "bus")
    private String bus;

    @Column(name = "time_origin")
    private String time_origin;

    @Column(name = "time_destiny")
    private String time_destiny;

    @Column(name = "to_stop")
    private Integer to_stop;                 

    @Column(name = "from_stop")
    private Integer from_stop;
    
    @Column(name = "tabla")
    private Integer tabla;
    
    @Column(name = "id_task_type")
    private String id_task_type;

    @Column(name = "type_entry")
    private Integer type_entry;
    
    @Column(name = "type_exit")
    private Integer type_exit;
    
    @Column(name = "ruta")
    private String ruta;
    
    @Column(name = "type_table")
    private String type_table;

    @Column(name = "novedad")
    private Integer novedad;
    
    @Column(name = "bateria")
    private Long bateria;

    public Integer getId_prg_tc() {
        return id_prg_tc;
    }

    public void setId_prg_tc(Integer id_prg_tc) {
        this.id_prg_tc = id_prg_tc;
    }

    public Integer getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(Integer id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public Integer getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(Integer id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getCodigo_tm() {
        return codigo_tm;
    }

    public void setCodigo_tm(String codigo_tm) {
        this.codigo_tm = codigo_tm;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getServbus() {
        return servbus;
    }

    public void setServbus(String servbus) {
        this.servbus = servbus;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getTime_origin() {
        return time_origin;
    }

    public void setTime_origin(String time_origin) {
        this.time_origin = time_origin;
    }

    public String getTime_destiny() {
        return time_destiny;
    }

    public void setTime_destiny(String time_destiny) {
        this.time_destiny = time_destiny;
    }

    public Integer getTo_stop() {
        return to_stop;
    }

    public void setTo_stop(Integer to_stop) {
        this.to_stop = to_stop;
    }

    public Integer getFrom_stop() {
        return from_stop;
    }

    public void setFrom_stop(Integer from_stop) {
        this.from_stop = from_stop;
    }

    public Integer getTabla() {
        return tabla;
    }

    public void setTabla(Integer tabla) {
        this.tabla = tabla;
    }

    public String getId_task_type() {
        return id_task_type;
    }

    public void setId_task_type(String id_task_type) {
        this.id_task_type = id_task_type;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getType_table() {
        return type_table;
    }

    public void setType_table(String type_table) {
        this.type_table = type_table;
    }

    public Integer getNovedad() {
        return novedad;
    }

    public void setNovedad(Integer novedad) {
        this.novedad = novedad;
    }

    public Integer getType_entry() {
        return type_entry;
    }

    public void setType_entry(Integer type_entry) {
        this.type_entry = type_entry;
    }

    public Integer getType_exit() {
        return type_exit;
    }

    public void setType_exit(Integer type_exit) {
        this.type_exit = type_exit;
    }

    public Long getBateria() {
        return bateria;
    }

    public void setBateria(Long bateria) {
        this.bateria = bateria;
    }    
}
