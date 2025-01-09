package com.movilidad.model.error;

import java.util.Date;

/**
 * Esta clase permite almacenar errores evidenciados durante la carga de 
 * archivos Excel, contiene atributos:
 * * id: identificador que se asocia al valor, se implementa para poder identificar
         los errores que corresponden a un registro
 * * date: de tipo Date donde se almacena la fecha.
 * * row: de tipo int que almacena el número del registro donde ocurre el error.
 * * column: de tipo String, permite almacenar el número o el nombre de la 
 *           columna donde ocurre el error.
 * * message: texto descriptivo que permite dar entendimiento del error. 
 * * value: almacena el valor que se evaluo durante la lectura de la celda
 * 
 * @author Omar.beltran
 */
public class FileLoadError {
    String id;
    Date date;
    int row;
    String column;
    String message;
    Object value;

    /**
     * constructor por defecto, asigna valores por defecto 
     */
    public FileLoadError() {
        this.id = "";
        this.date = new Date();
        this.row = 0;
        this.column = "";
        this.message = "";
        this.value = "";
    }
    
    /**
     * Constructor que recibe todos los valores de los atributos de la clase
     * 
     * @param id identificador que se asocia al valor, se implementa para poder identificar
     *          los errores que corresponden a un registro
     * @param date contiene la fecha de ocurrencia del error
     * @param row registro o fila donde se encuentra el error
     * @param column número o nombre de la columna en la cual se identifica 
     *        el error
     * @param message texto descriptivo del error 
     * @param value contiene el valor con el que se genera el error
     */
    public FileLoadError(String id, Date date, int row, String column, String message, Object value) {
        this.id = id;
        this.date = date;
        this.row = row;
        this.column = column;
        this.message = message;
        this.value = value;
    }
    
    /**
     * Este constructor asigna al atributo date la instancia de la clase Date 
     * en formato dow mon dd hh:mm:ss zzz yyyy
     * 
     * @param id identificador que se asocia al valor, se implementa para poder identificar
     *          los errores que corresponden a un registro
     * @param row registro o fila donde se encuentra el error
     * @param column número o nombre de la columna en la cual se identifica 
     *        el error
     * @param message texto descriptivo del error 
     * @param value contiene el valor con el que se genera el error
     */
    public FileLoadError(String id, int row, String column, String message, Object value) {
        this.id = id;
        this.date = new Date();
        this.row = row;
        this.column = column;
        this.message = message;
        this.value = value;
    }

    /**
     * @param date
     * @param row registro o fila donde se encuentra el error
     * @param column número o nombre de la columna en la cual se identifica 
     *        el error
     * @param message texto descriptivo del error 
     * @param value contiene el valor con el que se genera el error
     */
    public FileLoadError(Date date, int row, String column, String message, Object value) {
        this.id = "";
        this.date = date;
        this.row = row;
        this.column = column;
        this.message = message;
        this.value = value;
    }
    
    /**
     * @param row registro o fila donde se encuentra el error
     * @param column número o nombre de la columna en la cual se identifica 
     *        el error
     * @param message texto descriptivo del error 
     * @param value contiene el valor con el que se genera el error
     */
    public FileLoadError(int row, String column, String message, Object value) {
        this.id = "";
        this.date = new Date();
        this.row = row;
        this.column = column;
        this.message = message;
        this.value = value;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
