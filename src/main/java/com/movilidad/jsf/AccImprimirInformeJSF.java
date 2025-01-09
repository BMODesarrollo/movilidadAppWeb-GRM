package com.movilidad.jsf;

import com.movilidad.ejb.AccChoqueInformeFacadeLocal;
import com.movilidad.ejb.AccObjetoFijoInformeFacadeLocal;
import com.movilidad.ejb.AccVisualInformeFacadeLocal;
import com.movilidad.ejb.AccidenteFacadeLocal;
import com.movilidad.ejb.AccidenteLugarDemarFacadeLocal;
import com.movilidad.model.AccChoqueInforme;
import com.movilidad.model.AccInformeMaster;
import com.movilidad.model.AccInformeMasterAgentes;
import com.movilidad.model.AccInformeMasterAlbum;
import com.movilidad.model.AccInformeMasterApoyo;
import com.movilidad.model.AccInformeMasterBomberos;
import com.movilidad.model.AccInformeMasterInspectores;
import com.movilidad.model.AccInformeMasterLesionado;
import com.movilidad.model.AccInformeMasterMedicos;
import com.movilidad.model.AccInformeMasterRecomotos;
import com.movilidad.model.AccInformeMasterTestigo;
import com.movilidad.model.AccInformeMasterVehCond;
import com.movilidad.model.AccInformeMasterVic;
import com.movilidad.model.AccInformeOpe;
import com.movilidad.model.AccInformeOpeCausalidad;
import com.movilidad.model.AccInformeTestigo;
import com.movilidad.model.AccInformeVehCond;
import com.movilidad.model.AccInformeVic;
import com.movilidad.model.AccObjetoFijoInforme;
import com.movilidad.model.AccVisualInforme;
import com.movilidad.model.Accidente;
import com.movilidad.model.AccidenteLugarDemar;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

@Named(value = "accImprimirInformeJSF")
@ViewScoped
public class AccImprimirInformeJSF implements Serializable {

    @EJB
    private AccidenteFacadeLocal accidenteFacadeLocal;
    @EJB
    private AccidenteLugarDemarFacadeLocal accidenteLugarDemarFacadeLocal;
    @EJB
    private AccObjetoFijoInformeFacadeLocal accObjetoFijoInformeFacadeLocal;
    @EJB
    private AccChoqueInformeFacadeLocal accChoqueInformeFacadeLocal;
    @EJB
    private AccVisualInformeFacadeLocal accVisualInformeFacadeLocal;

    //informe del operador
    private AccInformeOpe accInformeOpe;
    private List<AccInformeOpeCausalidad> listAccInformeOpeCausalidad;
    private List<AccInformeVic> listAccInformeVic;
    private List<AccInformeTestigo> listAccInformeTestigo;
    private List<AccInformeVehCond> listAccInformeVehCond;
    private List<AccidenteLugarDemar> listAccidenteLugarDemar;
    private List<AccObjetoFijoInforme> listAccObjetoFijoInforme;
    private List<AccChoqueInforme> listAccChoqueInforme;
    private List<AccVisualInforme> listAccVisualInforme;

    //informe del master
    private AccInformeMaster accInformeMaster;
    private List<AccInformeMasterAgentes> accInformeMasterAgentesList;
    private List<AccInformeMasterAlbum> accInformeMasterAlbumList;
    private List<AccInformeMasterApoyo> accInformeMasterApoyoList;
    private List<AccInformeMasterBomberos> accInformeMasterBomberosList;
    private List<AccInformeMasterInspectores> accInformeMasterInspectoresList;
    private List<AccInformeMasterLesionado> accInformeMasterLesionadoList;
    private List<AccInformeMasterMedicos> accInformeMasterMedicosList;
    private List<AccInformeMasterTestigo> accInformeMasterTestigoList;
    private List<AccInformeMasterVehCond> accInformeMasterVehCondList;
    private List<AccInformeMasterVic> accInformeMasterVicList;
    private List<AccInformeMasterRecomotos> accInformeMasterRecomotosList;

    private Integer idAccidente;
    private Integer i_opcion;

    @Inject
    private AccidenteJSF accidenteJSF;

    public AccImprimirInformeJSF() {
    }

    /**
     * permite de acuerdo a la opcion seleccionada, generar la data para el
     * informe solicitado
     */
    public void cargarInforme() {
        if (i_opcion != null) {
            if (i_opcion.equals(1)) {
                cargarInformeOperador();
                return;
            }
            if (i_opcion.equals(2)) {
                cargarInformeMaster();
            }
        }
    }

    /**
     *
     * @param idAccidente id de la tabla accidente para realizar las cargas de
     * ambos informes
     */
    public void cargarTodosInforme(Integer idAccidente) {
        this.idAccidente = idAccidente;
        cargarInformeOperador();
        cargarInformeMaster();
        this.idAccidente = null;
    }

    /**
     * todas las variables de esta clase se inicializan en null
     */
    public void resetInformes() {
        accInformeOpe = null;
        listAccInformeOpeCausalidad = null;
        listAccInformeVic = null;
        listAccInformeTestigo = null;
        listAccInformeVehCond = null;
        listAccidenteLugarDemar = null;
        listAccObjetoFijoInforme = null;
        listAccChoqueInforme = null;
        listAccVisualInforme = null;
        idAccidente = null;
        i_opcion = null;
        accInformeMaster = null;
        accInformeMasterAgentesList = null;
        accInformeMasterAlbumList = null;
        accInformeMasterApoyoList = null;
        accInformeMasterBomberosList = null;
        accInformeMasterInspectoresList = null;
        accInformeMasterLesionadoList = null;
        accInformeMasterMedicosList = null;
        accInformeMasterTestigoList = null;
        accInformeMasterVehCondList = null;
        accInformeMasterVicList = null;
        accInformeMasterRecomotosList = null;
    }

    // Carga el objeto InformeOperador del objeto Accidente seleccionado
    void cargarInformeOperador() {
        idAccidente = accidenteJSF.compartirIdAccidente();
        if (idAccidente != null) {
            Accidente acc = accidenteFacadeLocal.find(idAccidente);
            if (acc != null) {
                if (acc.getAccInformeOpeList() != null) {
                    if (!acc.getAccInformeOpeList().isEmpty()) {
                        accInformeOpe = acc.getAccInformeOpeList().get(0);
                        cargarObjetosOperador();
                    } else {
                        MovilidadUtil.addErrorMessage("Accidente aún no cuenta con informe de operador en el sistema");
                    }
                } else {
                    MovilidadUtil.addErrorMessage("Accidente aún no cuenta con informe de operador en el sistema");
                }
            }
        } else {
            System.out.println("Error");
        }
    }

    // Carga el objeto InformeMaster del objeto Accidente seleccionado
    void cargarInformeMaster() {
        idAccidente = accidenteJSF.compartirIdAccidente();
        if (idAccidente != null) {
            Accidente acc = accidenteFacadeLocal.find(idAccidente);
            if (acc != null) {
                if (acc.getAccInformeMasterList() != null) {
                    if (!acc.getAccInformeMasterList().isEmpty()) {
                        accInformeMaster = acc.getAccInformeMasterList().get(0);
                        cargarObjetosMaster();
                    } else {
                        MovilidadUtil.addErrorMessage("Accidente aún no cuenta con informe de máster en el sistema");
                    }
                } else {
                    MovilidadUtil.addErrorMessage("Accidente aún no cuenta con informe de máster en el sistema");
                }
            }
        } else {
            System.out.println("Error");
        }
    }

    // Carga la data de los objetos relacionado al objeto InformeOperador
    void cargarObjetosOperador() {
        try {
            if (accInformeOpe != null) {
                listAccInformeTestigo = accInformeOpe.getAccInformeTestigoList();
                listAccInformeVehCond = accInformeOpe.getAccInformeVehCondList();
                listAccInformeOpeCausalidad = accInformeOpe.getAccInformeOpeCausalidadList();
                listAccInformeVic = accInformeOpe.getAccInformeVicList();
                listAccidenteLugarDemar = accidenteLugarDemarFacadeLocal.getAccidenteLugarDemarInformeOpe(accInformeOpe.getIdAccInformeOpe());
                listAccObjetoFijoInforme = accObjetoFijoInformeFacadeLocal.getAccObjetoFijoInformeInformeOpe(accInformeOpe.getIdAccInformeOpe());
                listAccChoqueInforme = accChoqueInformeFacadeLocal.getAccChoqueInformeOpe(accInformeOpe.getIdAccInformeOpe());
                listAccVisualInforme = accVisualInformeFacadeLocal.getAccVisualInformeOpe(accInformeOpe.getIdAccInformeOpe());
            }
        } catch (Exception e) {
            System.out.println("Error en cargar");
        }
    }

    // Carga la data de los objetos relacionado al objeto InformeMaster
    void cargarObjetosMaster() {
        if (accInformeMaster != null) {
            accInformeMasterAgentesList = accInformeMaster.getAccInformeMasterAgentesList();
            accInformeMasterAlbumList = accInformeMaster.getAccInformeMasterAlbumList();
            accInformeMasterApoyoList = accInformeMaster.getAccInformeMasterApoyoList();
            accInformeMasterBomberosList = accInformeMaster.getAccInformeMasterBomberosList();
            accInformeMasterInspectoresList = accInformeMaster.getAccInformeMasterInspectoresList();
            accInformeMasterLesionadoList = accInformeMaster.getAccInformeMasterLesionadoList();
            accInformeMasterMedicosList = accInformeMaster.getAccInformeMasterMedicosList();
            accInformeMasterTestigoList = accInformeMaster.getAccInformeMasterTestigoList();
            accInformeMasterVehCondList = accInformeMaster.getAccInformeMasterVehCondList();
            accInformeMasterVicList = accInformeMaster.getAccInformeMasterVicList();
            accInformeMasterRecomotosList = accInformeMaster.getAccInformeMasterRecomotosList();
        }
    }

    /**
     *
     * @param signature es el tipo de dato capturado por el componente de
     * PrimeFaces Signature, es un String de cordenadas
     * @param width ancho definido en el Signature del xhtml
     * @param height largo definido en el Signature del xhtml
     * @return retorna las cordenadas en formato svg
     */
    public String toSvg(String signature, String width, String height) {
        try {
            if (signature != null) {
                List<String> paths = new ArrayList<>();
                JsonReader jsonReader = Json.createReader(new StringReader(signature));
                JsonObject jsonObject = jsonReader.readObject();
                JsonArray jsonArray = jsonObject.getJsonArray("lines");
                for (JsonValue line : jsonArray) {
                    paths.add(toSvgPath((JsonArray) line));
                }
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("<svg width=\"%d\" height=\"%d\" xmlns=\"http://www.w3.org/2000/svg\">\n", Integer.valueOf(width), Integer.valueOf(height)));
                for (String path : paths) {
                    sb.append(path);
                }
                sb.append("</svg>");
                return sb.toString();
            }
            return "";
        } catch (Exception e) {
            return "";
        }

    }

    private String toSvgPath(JsonArray line) {
        StringBuilder sb = new StringBuilder("<path d=\"");
        for (int i = 0; i < line.size(); i++) {
            JsonArray coords = (JsonArray) line.getJsonArray(i);
            sb.append(String.format("%s%d %d ", (i == 0 ? "M" : "L"), coords.getInt(0), coords.getInt(1)));
        }
        sb.append("\" stroke=\"black\" fill=\"transparent\"/>\n");
        return sb.toString();
    }

    public AccInformeOpe getAccInformeOpe() {
        return accInformeOpe;
    }

    public void setAccInformeOpe(AccInformeOpe accInformeOpe) {
        this.accInformeOpe = accInformeOpe;
    }

    public List<AccInformeOpeCausalidad> getListAccInformeOpeCausalidad() {
        return listAccInformeOpeCausalidad;
    }

    public void setListAccInformeOpeCausalidad(List<AccInformeOpeCausalidad> listAccInformeOpeCausalidad) {
        this.listAccInformeOpeCausalidad = listAccInformeOpeCausalidad;
    }

    public List<AccInformeVic> getListAccInformeVic() {
        return listAccInformeVic;
    }

    public void setListAccInformeVic(List<AccInformeVic> listAccInformeVic) {
        this.listAccInformeVic = listAccInformeVic;
    }

    public List<AccInformeTestigo> getListAccInformeTestigo() {
        return listAccInformeTestigo;
    }

    public void setListAccInformeTestigo(List<AccInformeTestigo> listAccInformeTestigo) {
        this.listAccInformeTestigo = listAccInformeTestigo;
    }

    public List<AccInformeVehCond> getListAccInformeVehCond() {
        return listAccInformeVehCond;
    }

    public void setListAccInformeVehCond(List<AccInformeVehCond> listAccInformeVehCond) {
        this.listAccInformeVehCond = listAccInformeVehCond;
    }

    public Integer getI_opcion() {
        return i_opcion;
    }

    public void setI_opcion(Integer i_opcion) {
        this.i_opcion = i_opcion;
    }

    public List<AccidenteLugarDemar> getListAccidenteLugarDemar() {
        return listAccidenteLugarDemar;
    }

    public void setListAccidenteLugarDemar(List<AccidenteLugarDemar> listAccidenteLugarDemar) {
        this.listAccidenteLugarDemar = listAccidenteLugarDemar;
    }

    public List<AccObjetoFijoInforme> getListAccObjetoFijoInforme() {
        return listAccObjetoFijoInforme;
    }

    public void setListAccObjetoFijoInforme(List<AccObjetoFijoInforme> listAccObjetoFijoInforme) {
        this.listAccObjetoFijoInforme = listAccObjetoFijoInforme;
    }

    public List<AccChoqueInforme> getListAccChoqueInforme() {
        return listAccChoqueInforme;
    }

    public void setListAccChoqueInforme(List<AccChoqueInforme> listAccChoqueInforme) {
        this.listAccChoqueInforme = listAccChoqueInforme;
    }

    public List<AccVisualInforme> getListAccVisualInforme() {
        return listAccVisualInforme;
    }

    public void setListAccVisualInforme(List<AccVisualInforme> listAccVisualInforme) {
        this.listAccVisualInforme = listAccVisualInforme;
    }

    public AccInformeMaster getAccInformeMaster() {
        return accInformeMaster;
    }

    public void setAccInformeMaster(AccInformeMaster accInformeMaster) {
        this.accInformeMaster = accInformeMaster;
    }

    public List<AccInformeMasterAgentes> getAccInformeMasterAgentesList() {
        return accInformeMasterAgentesList;
    }

    public void setAccInformeMasterAgentesList(List<AccInformeMasterAgentes> accInformeMasterAgentesList) {
        this.accInformeMasterAgentesList = accInformeMasterAgentesList;
    }

    public List<AccInformeMasterAlbum> getAccInformeMasterAlbumList() {
        return accInformeMasterAlbumList;
    }

    public void setAccInformeMasterAlbumList(List<AccInformeMasterAlbum> accInformeMasterAlbumList) {
        this.accInformeMasterAlbumList = accInformeMasterAlbumList;
    }

    public List<AccInformeMasterApoyo> getAccInformeMasterApoyoList() {
        return accInformeMasterApoyoList;
    }

    public void setAccInformeMasterApoyoList(List<AccInformeMasterApoyo> accInformeMasterApoyoList) {
        this.accInformeMasterApoyoList = accInformeMasterApoyoList;
    }

    public List<AccInformeMasterBomberos> getAccInformeMasterBomberosList() {
        return accInformeMasterBomberosList;
    }

    public void setAccInformeMasterBomberosList(List<AccInformeMasterBomberos> accInformeMasterBomberosList) {
        this.accInformeMasterBomberosList = accInformeMasterBomberosList;
    }

    public List<AccInformeMasterInspectores> getAccInformeMasterInspectoresList() {
        return accInformeMasterInspectoresList;
    }

    public void setAccInformeMasterInspectoresList(List<AccInformeMasterInspectores> accInformeMasterInspectoresList) {
        this.accInformeMasterInspectoresList = accInformeMasterInspectoresList;
    }

    public List<AccInformeMasterLesionado> getAccInformeMasterLesionadoList() {
        return accInformeMasterLesionadoList;
    }

    public void setAccInformeMasterLesionadoList(List<AccInformeMasterLesionado> accInformeMasterLesionadoList) {
        this.accInformeMasterLesionadoList = accInformeMasterLesionadoList;
    }

    public List<AccInformeMasterMedicos> getAccInformeMasterMedicosList() {
        return accInformeMasterMedicosList;
    }

    public void setAccInformeMasterMedicosList(List<AccInformeMasterMedicos> accInformeMasterMedicosList) {
        this.accInformeMasterMedicosList = accInformeMasterMedicosList;
    }

    public List<AccInformeMasterTestigo> getAccInformeMasterTestigoList() {
        return accInformeMasterTestigoList;
    }

    public void setAccInformeMasterTestigoList(List<AccInformeMasterTestigo> accInformeMasterTestigoList) {
        this.accInformeMasterTestigoList = accInformeMasterTestigoList;
    }

    public List<AccInformeMasterVehCond> getAccInformeMasterVehCondList() {
        return accInformeMasterVehCondList;
    }

    public void setAccInformeMasterVehCondList(List<AccInformeMasterVehCond> accInformeMasterVehCondList) {
        this.accInformeMasterVehCondList = accInformeMasterVehCondList;
    }

    public List<AccInformeMasterVic> getAccInformeMasterVicList() {
        return accInformeMasterVicList;
    }

    public void setAccInformeMasterVicList(List<AccInformeMasterVic> accInformeMasterVicList) {
        this.accInformeMasterVicList = accInformeMasterVicList;
    }

    public List<AccInformeMasterRecomotos> getAccInformeMasterRecomotosList() {
        return accInformeMasterRecomotosList;
    }

    public void setAccInformeMasterRecomotosList(List<AccInformeMasterRecomotos> accInformeMasterRecomotosList) {
        this.accInformeMasterRecomotosList = accInformeMasterRecomotosList;
    }

}
