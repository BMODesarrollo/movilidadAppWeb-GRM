/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.CableUbicacionFacadeLocal;
import com.movilidad.ejb.SegAseoArmamentoFacadeLocal;
import com.movilidad.ejb.SstEmpresaVisitanteFacadeLocal;
import com.movilidad.model.CableUbicacion;
import com.movilidad.model.SegAseoArmamento;
import com.movilidad.model.SegRegistroArmamento;
import com.movilidad.model.SstEmpresaVisitante;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "segAseoArmaJSF")
@ViewScoped
public class SegAseoArmamentoJSF implements Serializable {

    /**
     * Creates a new instance of SegAseoArmamentoJSF
     */
    public SegAseoArmamentoJSF() {
    }

    @EJB
    private SegAseoArmamentoFacadeLocal segAseoArmaEJB;
    @EJB
    private SstEmpresaVisitanteFacadeLocal sstEmpresaVisitanteEJB;
    @EJB
    private CableUbicacionFacadeLocal cableUbicacionEJB;
    @Inject
    private UploadFotoJSFManagedBean fotoJSFManagedBean;
    private List<SegAseoArmamento> list;
    private List<CableUbicacion> listCableUbicacion;
    private List<UploadedFile> path_fotos;
    private List<UploadedFile> path_planilla;
    private List<String> listFotos = new ArrayList<>();
    private List<String> listFotospath_fotos = new ArrayList<>();
    private List<String> listFotospath_planilla = new ArrayList<>();
    private List<String> lista_fotos_remover = new ArrayList<>();

    private SegAseoArmamento segAseoArma;
    private SegRegistroArmamento armamento;
    private CableUbicacion ubicacion;
    private SstEmpresaVisitante sstEmpresaVisitante;
    private Date desde = MovilidadUtil.fechaHoy();
    private Date hasta = MovilidadUtil.fechaHoy();

    private int i_idCableUbicacion;

    private String numeroDocumento;

    UserExtended user;

    @PostConstruct
    public void init() {
        consultar();
        getUser();
    }

    /**
     * Obtener valor de usuario en sesión.
     */
    public void getUser() {
        try {
            user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            user = null;
        }
    }

    /**
     * Cargar la variable ubicacion y armamento de acuerdio a
     * i_idCableUbicacion.
     */
    public void cargarUbicacionArmamento() {
        ubicacion = null;
        armamento = null;
        for (CableUbicacion obj : listCableUbicacion) {
            if (obj.getIdCableUbicacion().equals(i_idCableUbicacion)) {
                ubicacion = obj;
                armamento = obj.getIdSegRegistroArmamento();
                break;
            }
        }
    }

    /**
     * Agrega en la lista lista_fotos_remover que luego va a ser elimnada y
     * removerlas de las listas listFotospath_fotos o listFotospath_planilla
     * Segun el caso.
     *
     * @param url
     */
    public void delete(String url) {
        lista_fotos_remover.add(url);
        listFotospath_fotos.remove(url);
        listFotospath_planilla.remove(url);
    }

    /**
     * Prepara las variables para la gestion de neuvo regsitro de aseo
     * armamento.
     */
    public void nuevo() {
        listCableUbicacion = cableUbicacionEJB.findAllByEstadoReg();
        segAseoArma = new SegAseoArmamento();
        segAseoArma.setFechaInicio(MovilidadUtil.fechaCompletaHoy());
        segAseoArma.setFechaFin(MovilidadUtil.fechaCompletaHoy());
        armamento = null;
        ubicacion = null;
        numeroDocumento = "";
        sstEmpresaVisitante = null;
        path_fotos = new ArrayList<>();
        path_planilla = new ArrayList<>();
        MovilidadUtil.openModal("create_aseo_arma_wv");
    }

    /**
     * Consulta en base de datos un objeto de tipo sstEmpresaVisitante, el cual
     * es utilizado en la gestion de seg aseo armamento.
     */
    public void buscarArmero() {
        if (MovilidadUtil.getWithoutSpaces(numeroDocumento).isEmpty()) {
            MovilidadUtil.addErrorMessage("Dede digitar el numero de documento");
            sstEmpresaVisitante = null;
            return;
        }
        sstEmpresaVisitante = sstEmpresaVisitanteEJB.findByNumDocumento(numeroDocumento);
        if (sstEmpresaVisitante == null) {
            MovilidadUtil.addErrorMessage("No existe registro con el numero de documento indicado.");
        } else {
            MovilidadUtil.addSuccessMessage("Armero Cargado.");
        }
    }

    /**
     * Prepra las variables para la gestion de edicion de un reporte de aseo
     * armamento.
     *
     * @throws IOException
     */
    public void editar() throws IOException {
        ubicacion = segAseoArma.getCableUbicacion();
        armamento = segAseoArma.getSegRegistroArmamento();
        sstEmpresaVisitante = segAseoArma.getSstEmpresaVisitante();
        numeroDocumento = segAseoArma.getSstEmpresaVisitante().getNumeroDocumento();
        listFotospath_fotos = obtenerFotosReturn(1);
        listFotospath_planilla = obtenerFotosReturn(2);
        path_fotos = new ArrayList<>();
        path_planilla = new ArrayList<>();
    }

    /**
     * Agrega un archivo en los array de tipo UploadedFile, segun el componente
     * en la vista, esto ultimo esta evaluado con el id del compenente.
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        if (event.getComponent().getId().equals("upload_I")) {
            path_fotos.add(event.getFile());
        }
        if (event.getComponent().getId().equals("upload_II")) {
            path_planilla.add(event.getFile());
        }
    }

    /**
     * invoca al metodo obtenerFotosReturn para cagar la lista listFotos.
     *
     * @param opc
     * @throws IOException
     */
    public void obtenerFotos(int opc) throws IOException {
        fotoJSFManagedBean.setListFotos(obtenerFotosReturn(opc));
    }

    /**
     * obtiene las rutas de las imagenes del registro de aseo armamento.
     *
     * @param opc
     * @return
     * @throws IOException
     */
    public List<String> obtenerFotosReturn(int opc) throws IOException {
        this.listFotos = new ArrayList<>();
        List<String> lstNombresImg;
        String path;
        if (opc == 1) {
            lstNombresImg = Util.getFileList(segAseoArma.getIdSegAseoArmamento(), "segAseoArma");
            path = segAseoArma.getPathFotos();
        } else {
            lstNombresImg = Util.getFileList(segAseoArma.getIdSegAseoArmamento(), "segAseoArmaMinuta");
            path = segAseoArma.getPathPlanilla();
        }
        for (String f : lstNombresImg) {
            f = path + f;
            listFotos.add(f);
        }
        return listFotos;
    }

    /**
     * Consultar los registros de aseo de armamento por rango de fecha.
     */
    public void consultar() {
        list = segAseoArmaEJB.findRangoFechaEstadoReg(desde, hasta);
    }

    /**
     * Validar que las variables en cuestion esten listas para persistir.
     *
     * @return True si sale algo mal, False si todo esta bien.
     * @throws ParseException
     */
    public boolean validar() {
        if (sstEmpresaVisitante == null) {
            MovilidadUtil.addErrorMessage("Se debe cargar un Armero");
            return true;
        }
        if (ubicacion == null) {
            MovilidadUtil.addErrorMessage("Se debe cargar una ubicación");
            return true;
        }
        if (armamento == null) {
            MovilidadUtil.addErrorMessage("Se debe cargar un armamento");
            return true;
        }
        return false;
    }

    /**
     * Metodo encargado de persistir en base de datos un nuevo registro de aseo
     * armamento
     *
     */
    public void guardar() {
        if (validar()) {
            return;
        }
        segAseoArma.setUsername(user.getUsername());
        segAseoArma.setCableUbicacion(ubicacion);
        segAseoArma.setSegRegistroArmamento(armamento);
        segAseoArma.setSstEmpresaVisitante(sstEmpresaVisitante);
        if (segAseoArma.getIdSegAseoArmamento() == null) {
            segAseoArma.setEstadoReg(0);
            segAseoArma.setCreado(MovilidadUtil.fechaCompletaHoy());
            segAseoArmaEJB.create(segAseoArma);
        } else {
            /**
             * elimina fotos.
             */
            for (String url : lista_fotos_remover) {
                MovilidadUtil.eliminarFichero(url);
            }
            segAseoArma.setModificado(MovilidadUtil.fechaCompletaHoy());
            segAseoArmaEJB.edit(segAseoArma);
        }
        /**
         * Validar si hay imagenes para guardar.
         */
        if (!path_fotos.isEmpty() || !path_planilla.isEmpty()) {
            String path = segAseoArma.getPathFotos() == null ? "/" : segAseoArma.getPathFotos();
            String pathPlanilla = segAseoArma.getPathPlanilla() == null ? "/" : segAseoArma.getPathPlanilla();
            for (UploadedFile f : path_fotos) {
                path = Util.saveFile(f, segAseoArma.getIdSegAseoArmamento(), "segAseoArma");
            }
            for (UploadedFile f : path_planilla) {
                pathPlanilla = Util.saveFile(f, segAseoArma.getIdSegAseoArmamento(), "segAseoArmaMinuta");
            }
            segAseoArma.setPathFotos(path);
            segAseoArma.setPathPlanilla(pathPlanilla);
            this.segAseoArmaEJB.edit(segAseoArma);
            path_fotos.clear();
            path_planilla.clear();
        }
        MovilidadUtil.addSuccessMessage("Se registro la informacion exitosamente.");
        MovilidadUtil.hideModal("create_aseo_arma_wv");
        consultar();
    }

    public List<SegAseoArmamento> getList() {
        return list;
    }

    public void setList(List<SegAseoArmamento> list) {
        this.list = list;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public List<String> getListFotos() {
        return listFotos;
    }

    public void setListFotos(List<String> listFotos) {
        this.listFotos = listFotos;
    }

    public SegAseoArmamento getSegAseoArma() {
        return segAseoArma;
    }

    public void setSegAseoArma(SegAseoArmamento segAseoArma) {
        this.segAseoArma = segAseoArma;
    }

    public List<CableUbicacion> getListCableUbicacion() {
        return listCableUbicacion;
    }

    public void setListCableUbicacion(List<CableUbicacion> listCableUbicacion) {
        this.listCableUbicacion = listCableUbicacion;
    }

    public int getI_idCableUbicacion() {
        return i_idCableUbicacion;
    }

    public void setI_idCableUbicacion(int i_idCableUbicacion) {
        this.i_idCableUbicacion = i_idCableUbicacion;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public SegRegistroArmamento getArmamento() {
        return armamento;
    }

    public void setArmamento(SegRegistroArmamento armamento) {
        this.armamento = armamento;
    }

    public List<String> getListFotospath_fotos() {
        return listFotospath_fotos;
    }

    public void setListFotospath_fotos(List<String> listFotospath_fotos) {
        this.listFotospath_fotos = listFotospath_fotos;
    }

    public List<String> getListFotospath_planilla() {
        return listFotospath_planilla;
    }

    public void setListFotospath_planilla(List<String> listFotospath_planilla) {
        this.listFotospath_planilla = listFotospath_planilla;
    }

}
