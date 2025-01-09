/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.SegEstadoMedioComunicaFacadeLocal;
import com.movilidad.ejb.SegMedioComunicacionFacadeLocal;
import com.movilidad.model.SegEstadoMedioComunica;
import com.movilidad.model.SegMedioComunicacion;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
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
@Named(value = "estadoMediosBean")
@ViewScoped
public class EstadoMediosComunicaBean implements Serializable {

    /**
     * Creates a new instance of EstadoMediosComunicaSFManagedBean
     */
    public EstadoMediosComunicaBean() {
    }
    @EJB
    private SegEstadoMedioComunicaFacadeLocal segEstadoMedioComunicaEJB;
    @EJB
    private SegMedioComunicacionFacadeLocal segMedioComunicacionEJB;

    @Inject
    private UploadFotoJSFManagedBean fotoJSFManagedBean;
    private SegEstadoMedioComunica estadoMedioComunica;
    private SegMedioComunicacion medioComunicacion;
    private Date desde = MovilidadUtil.fechaHoy();
    private Date hasta = MovilidadUtil.fechaHoy();

    private List<SegEstadoMedioComunica> list;
    private List<SegMedioComunicacion> listSegMedioComunicacion;
    private List<UploadedFile> pathFotos;
    private List<String> s_listFotos = new ArrayList<>();
    private List<String> lista_fotos_remover = new ArrayList<>();

    private int i_medioComunica;
    private boolean b_antena;
    private boolean b_bateria;
    private boolean b_bateria_repuesto;
    private boolean b_cargador;
    private boolean b_adaptador;
    private boolean flag_rremove_photo = false;

    UserExtended user;

    @PostConstruct
    public void init() {
        consultar();
        getUser();
    }

    public void getUser() {
        try {
            user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            user = null;
        }
    }

    public void consultarMediosComunica() {
        listSegMedioComunicacion = segMedioComunicacionEJB.findByEstadoReg();
    }

    public void consultar() {
        list = segEstadoMedioComunicaEJB.findRangoFechaEstadoReg(desde, hasta);
    }

    public void obtenerFotos() throws IOException {
        fotoJSFManagedBean.setListFotos(obtenerFotosReturn());
    }

    public void nuevo() {
        flag_rremove_photo = false;

        consultarMediosComunica();
        estadoMedioComunica = new SegEstadoMedioComunica();
        estadoMedioComunica.setFechaHora(MovilidadUtil.fechaCompletaHoy());
        b_antena = true;
        b_bateria = true;
        b_bateria_repuesto = true;
        b_cargador = true;
        b_adaptador = true;
        i_medioComunica = 0;
        pathFotos = new ArrayList<>();
        MovilidadUtil.openModal("create_medio_comunica_wv");
    }

    public List<String> obtenerFotosReturn() throws IOException {
        this.s_listFotos = new ArrayList<>();
        List<String> lstNombresImg;
        String path;
        lstNombresImg = Util.getFileList(estadoMedioComunica.getIdSegEstadoMedioComunica(), "estadoMedioComunica");
        path = estadoMedioComunica.getPathFotos();
        for (String f : lstNombresImg) {
            f = path + f;
            s_listFotos.add(f);
        }
        return s_listFotos;
    }

    public void setMedioComunica() {
        for (SegMedioComunicacion obj : listSegMedioComunicacion) {
            if (obj.getIdSegMedioComunicacion().equals(i_medioComunica)) {
                System.out.println("Entro");
                medioComunicacion = obj;
                break;
            }
        }
    }

    public boolean validar() throws ParseException {
        if (medioComunicacion == null) {
            MovilidadUtil.addErrorMessage("Se debe cargar un Medio dee Comunicacion");
            return true;
        }
        if (MovilidadUtil.fechasIgualMenorMayor(estadoMedioComunica.getFechaHora(), MovilidadUtil.fechaCompletaHoy(), true) == 2) {
            MovilidadUtil.addErrorMessage("La fecha no deve ser posterior a la del día actual");
            return true;
        }
        return false;
    }

    public void guardar() throws ParseException {

        if (validar()) {
            return;
        }
        estadoMedioComunica.setAntena(b_antena ? 1 : 0);
        estadoMedioComunica.setBateria(b_bateria ? 1 : 0);
        estadoMedioComunica.setBateriaRepuesto(b_bateria_repuesto ? 1 : 0);
        estadoMedioComunica.setCargador(b_cargador ? 1 : 0);
        estadoMedioComunica.setAdaptador(b_adaptador ? 1 : 0);
        estadoMedioComunica.setUsername(user.getUsername());
        estadoMedioComunica.setIdSegMedioComunicacion(medioComunicacion);
        if (estadoMedioComunica.getIdSegEstadoMedioComunica() == null) {
            estadoMedioComunica.setEstadoReg(0);
            estadoMedioComunica.setCreado(MovilidadUtil.fechaCompletaHoy());
            segEstadoMedioComunicaEJB.create(estadoMedioComunica);
        } else {
            for (String url : lista_fotos_remover) {
                MovilidadUtil.eliminarFichero(url);
            }
            estadoMedioComunica.setModificado(MovilidadUtil.fechaCompletaHoy());
            segEstadoMedioComunicaEJB.edit(estadoMedioComunica);
        }
        if (!pathFotos.isEmpty()) {
            String path = estadoMedioComunica.getPathFotos() == null ? "/" : estadoMedioComunica.getPathFotos();
            for (UploadedFile f : pathFotos) {
                path = Util.saveFile(f, estadoMedioComunica.getIdSegEstadoMedioComunica(), "estadoMedioComunica");
            }
            estadoMedioComunica.setPathFotos(path);
            this.segEstadoMedioComunicaEJB.edit(estadoMedioComunica);
            pathFotos.clear();
        }
        MovilidadUtil.addSuccessMessage("Se registro la informacion exitosamente.");
        MovilidadUtil.hideModal("create_medio_comunica_wv");
        consultar();
    }

    public void handleFileUpload(FileUploadEvent event) {
        pathFotos.add(event.getFile());
    }

    public void delete(String url) {
        lista_fotos_remover.add(url);
        s_listFotos.remove(url);
    }

    public void editar() throws IOException {
        flag_rremove_photo = user.getUsername().equals(estadoMedioComunica.getUsername());

        s_listFotos = obtenerFotosReturn();
        consultarMediosComunica();
        b_antena = estadoMedioComunica.getAntena() == 1;
        b_bateria = estadoMedioComunica.getBateria() == 1;
        b_bateria_repuesto = estadoMedioComunica.getBateriaRepuesto() == 1;
        b_cargador = estadoMedioComunica.getCargador() == 1;
        b_adaptador = estadoMedioComunica.getAdaptador() == 1;
        i_medioComunica = estadoMedioComunica.getIdSegMedioComunicacion().getIdSegMedioComunicacion();
        medioComunicacion = estadoMedioComunica.getIdSegMedioComunicacion();
        pathFotos = new ArrayList<>();

    }

    public List<SegEstadoMedioComunica> getList() {
        return list;
    }

    public void setList(List<SegEstadoMedioComunica> list) {
        this.list = list;
    }

    public SegEstadoMedioComunica getEstadoMedioComunica() {
        return estadoMedioComunica;
    }

    public void setEstadoMedioComunica(SegEstadoMedioComunica estadoMedioComunica) {
        this.estadoMedioComunica = estadoMedioComunica;
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

    public List<UploadedFile> getPathFotos() {
        return pathFotos;
    }

    public void setPathFotos(List<UploadedFile> pathFotos) {
        this.pathFotos = pathFotos;
    }

    public List<String> getS_listFotos() {
        return s_listFotos;
    }

    public void setS_listFotos(List<String> s_listFotos) {
        this.s_listFotos = s_listFotos;
    }

    public int getI_medioComunica() {
        return i_medioComunica;
    }

    public void setI_medioComunica(int i_medioComunica) {
        this.i_medioComunica = i_medioComunica;
    }

    public SegMedioComunicacion getMedioComunicacion() {
        return medioComunicacion;
    }

    public void setMedioComunicacion(SegMedioComunicacion medioComunicacion) {
        this.medioComunicacion = medioComunicacion;
    }

    public List<SegMedioComunicacion> getListSegMedioComunicacion() {
        return listSegMedioComunicacion;
    }

    public void setListSegMedioComunicacion(List<SegMedioComunicacion> listSegMedioComunicacion) {
        this.listSegMedioComunicacion = listSegMedioComunicacion;
    }

    public boolean isB_antena() {
        return b_antena;
    }

    public void setB_antena(boolean b_antena) {
        this.b_antena = b_antena;
    }

    public boolean isB_bateria() {
        return b_bateria;
    }

    public void setB_bateria(boolean b_bateria) {
        this.b_bateria = b_bateria;
    }

    public boolean isB_bateria_repuesto() {
        return b_bateria_repuesto;
    }

    public void setB_bateria_repuesto(boolean b_bateria_repuesto) {
        this.b_bateria_repuesto = b_bateria_repuesto;
    }

    public boolean isB_cargador() {
        return b_cargador;
    }

    public void setB_cargador(boolean b_cargador) {
        this.b_cargador = b_cargador;
    }

    public boolean isB_adaptador() {
        return b_adaptador;
    }

    public void setB_adaptador(boolean b_adaptador) {
        this.b_adaptador = b_adaptador;
    }

    public boolean isFlag_rremove_photo() {
        return flag_rremove_photo;
    }

    public void setFlag_rremove_photo(boolean flag_rremove_photo) {
        this.flag_rremove_photo = flag_rremove_photo;
    }

}
