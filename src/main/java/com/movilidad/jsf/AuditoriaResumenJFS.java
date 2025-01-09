/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.dto.AuditoriaCostoDTO;
import com.movilidad.ejb.AuditoriaCostoFacadeLocal;
import com.movilidad.ejb.AuditoriaFacadeLocal;
import com.movilidad.ejb.AuditoriaPreguntaFacadeLocal;
import com.movilidad.ejb.AuditoriaRealizadoPorFacadeLocal;
import com.movilidad.ejb.AuditoriaRespuestaFacadeLocal;
import com.movilidad.ejb.AuditoriaTipoFacadeLocal;
import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.model.Auditoria;
import com.movilidad.model.AuditoriaCosto;
import com.movilidad.model.AuditoriaPregunta;
import com.movilidad.model.AuditoriaRealizadoPor;
import com.movilidad.model.AuditoriaRespuesta;
import com.movilidad.model.AuditoriaTipo;
import com.movilidad.model.Empleado;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.AuditoriaRespuestaDTO;
import com.movilidad.util.beans.Respuesta;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Jesús Jiménez
 */
@Named(value = "audiResumenBean")
@ViewScoped
public class AuditoriaResumenJFS implements Serializable {

    @EJB
    private AuditoriaFacadeLocal audiEJB;
    @EJB
    private AuditoriaRespuestaFacadeLocal audiRespuestaEJB;
    @EJB
    private AuditoriaTipoFacadeLocal audiTipoEJB;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUsrFacadeLocal;
    @EJB
    private AuditoriaPreguntaFacadeLocal audiPreguntaEJB;
    @EJB
    private AuditoriaRealizadoPorFacadeLocal audiRealizadoPorFacadeLocal;
    @EJB
    private AuditoriaCostoFacadeLocal audiCostoEJB;

    private List<AuditoriaRespuesta> respuestas;
    private List<AuditoriaRespuesta> respuestasAux;
    private List<Object> respuestas_String;
    private List<AuditoriaTipo> listTipoAuditoria;
    private List<Auditoria> listAudi;
    private List<AuditoriaPregunta> listaPreguntas;
    private List<Respuesta> listaRespuestas;
    private List<AuditoriaRealizadoPor> listaAuditoriaRealizadoPor;
    private List<AuditoriaCostoDTO> costos;
    private List<ColumnModel> columns;
    private Date desde = MovilidadUtil.fechaHoy();
    private Date hasta = MovilidadUtil.fechaHoy();
    private AuditoriaRespuesta respuesta;

    private int i_tipo_audi;
    private int i_audi;
    private int i_idArea;
    private int numeroPreguntas;

    private StreamedContent file;

    private List<String> listFotos = new ArrayList<>();

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    UserExtended user;
    private ParamAreaUsr paramAreaUsr;

    public AuditoriaResumenJFS() {
    }

    @PostConstruct
    public void init() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        paramAreaUsr = paramAreaUsrFacadeLocal.getByIdUser(user.getUsername());
        i_idArea = paramAreaUsr.getIdParamArea().getIdParamArea();
        getTipoAuditoria();
//        getCabeceras();
    }

    /**
     * Cargar la pregunta de la auditoria a resolver.
     *
     * @return
     */
    public List<AuditoriaPregunta> getPreguntas() {
        this.listaPreguntas = audiPreguntaEJB.findByIdAuditoria(i_audi);
        numeroPreguntas = listaPreguntas.size();
        return listaPreguntas;
    }

    /**
     * Cargar los costos de la auditoria.
     *
     * @return
     */
    public void prepareCostos() {
        costos = audiCostoEJB.findListDtoByIdAuditoria(i_audi);
    }

    public void proceso() {
        getPreguntas();
        prepareRespuestas();
        prepareCostos();
        generarExcel();
    }

    private void prepareRespuestas() {

        listaAuditoriaRealizadoPor = audiRealizadoPorFacadeLocal.findByRagoFechasIdAuditoria(desde, hasta, i_audi);

        listaRespuestas = new ArrayList<>();

        for (AuditoriaRealizadoPor arp : listaAuditoriaRealizadoPor) {
            respuestasAux = audiRespuestaEJB.findByIdAuditoriaRealizadoPor(arp.getIdAuditoriaRealizadoPor());
            Respuesta respuestaObj = new Respuesta();
            respuestaObj.setListaRespuesta(new ArrayList<AuditoriaRespuestaDTO>());
            for (AuditoriaRespuesta res : respuestasAux) {
                AuditoriaRespuestaDTO auditoriaRespuestaDTO = new AuditoriaRespuestaDTO();
                respuestaObj.setFecha(Util.dateTimeFormat(res.getIdAuditoriaRealizadoPor().getFecha()));
                respuestaObj.setQuienAuditoo(EmpleadoString(res.getIdAuditoriaRealizadoPor().getIdEmpleadoRealiza()));
                auditoriaRespuestaDTO.setRespuesta(respuestaString(res));
                respuestaObj.setSeAuditoo(seAuditooString(res));
                respuestaObj.getListaRespuesta().add(auditoriaRespuestaDTO);
            }
            listaRespuestas.add(respuestaObj);
        }
    }

    public void generarExcel() {
        try {
            Map parametros = new HashMap();
            parametros.put("respuestas", listaRespuestas);
            parametros.put("preguntas", listaPreguntas);
            parametros.put("costos", costos);
            parametros.put("df", Util.DATE_FORMAT);
            String destino = "/tmp/reporte_auditoria.xlsx";
            String plantilla = "/rigel/reportes/reporte_auditoria.xlsx";
            GeneraXlsx.generar(plantilla, destino, parametros);

            File excel = new File(destino);
            InputStream stream = new FileInputStream(excel);
            file = new DefaultStreamedContent(stream, "text/plain", nombreExcel() + ".xlsx");

        } catch (Exception e) {
        }

    }

    private String seAuditooString(AuditoriaRespuesta ar) {
        if (ar.getIdAuditoriaRealizadoPor().getIdAuditoriaAreaComun() != null) {
            return ar.getIdAuditoriaRealizadoPor().getIdAuditoriaAreaComun().getNombre();
        }
        if (ar.getIdAuditoriaRealizadoPor().getIdAuditoriaEstacion() != null) {
            return ar.getIdAuditoriaRealizadoPor().getIdAuditoriaEstacion().getNombre();
        }
        if (ar.getIdAuditoriaRealizadoPor().getIdEmpleadoAuditado() != null) {
            return EmpleadoString(ar.getIdAuditoriaRealizadoPor().getIdEmpleadoAuditado());
        }
        if (ar.getIdAuditoriaRealizadoPor().getIdVehiculoAuditado() != null) {
            return ar.getIdAuditoriaRealizadoPor().getIdVehiculoAuditado().getCodigo();
        }
        return "N/A";
    }

    private String respuestaString(AuditoriaRespuesta ar) {
        String valor = "";
        if (ar.getIdAuditoriaAlternativaRespuesta() != null) {
            valor = ar.getIdAuditoriaAlternativaRespuesta().getEnunciado();
        }
        if (ar.getRespuestaAbierta() != null) {
            if (valor != null && valor.isEmpty()) {
                valor = ar.getRespuestaAbierta();
            } else {
                valor = valor + " - " + ar.getRespuestaAbierta();
            }
        }
        if (ar.getRespuestaObservacion() != null) {
            if (valor != null && valor.isEmpty()) {
                valor = ar.getRespuestaObservacion();
            } else {
                valor = valor + " - " + ar.getRespuestaObservacion();
            }
        }
        return valor;
    }

    private String EmpleadoString(Empleado empl) {
        return empl.getCodigoTm() + "-"
                + empl.getNombres() + " "
                + empl.getApellidos();
    }

    /**
     * Cargar auditorias por id de tipo de auditoria.
     */
    public void cargarAuditorias() {
        listAudi = audiEJB.findAllByIdTipoAuditoria(i_tipo_audi, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    /**
     * CArgar tipos de auditorias.
     */
    public void getTipoAuditoria() {
        listTipoAuditoria = audiTipoEJB.findByArea(i_idArea);
    }

    /**
     * identifica que lugar se audito en la uditoría.
     *
     * @param ar
     * @return retorna valor de lugar auditado para la auditoria.
     */
    public String seAudito(AuditoriaRespuesta ar) {
        if (ar.getIdAuditoriaRealizadoPor().getIdAuditoria().getIdAuditoriaEncabezado().getIdAuditoriaLugar().isBus()) {
            return ar.getIdAuditoriaRealizadoPor().getIdVehiculoAuditado().getCodigo();
        }
        if (ar.getIdAuditoriaRealizadoPor().getIdAuditoria().getIdAuditoriaEncabezado().getIdAuditoriaLugar().isAreaComun()) {
            return ar.getIdAuditoriaRealizadoPor().getIdAuditoriaAreaComun().getNombre();
        }
        if (ar.getIdAuditoriaRealizadoPor().getIdAuditoria().getIdAuditoriaEncabezado().getIdAuditoriaLugar().isEmpleado()) {
            return ar.getIdAuditoriaRealizadoPor().getIdEmpleadoAuditado().getCodigoTm()
                    + " - " + ar.getIdAuditoriaRealizadoPor().getIdEmpleadoAuditado().getNombres()
                    + " " + ar.getIdAuditoriaRealizadoPor().getIdEmpleadoAuditado().getApellidos();
        }
        if (ar.getIdAuditoriaRealizadoPor().getIdAuditoria().getIdAuditoriaEncabezado().getIdAuditoriaLugar().isEstacion()) {
            return ar.getIdAuditoriaRealizadoPor().getIdAuditoriaEstacion().getNombre();
        }
        return "N/A";
    }

    public void consultar() {
        respuestas = audiRespuestaEJB.findByRangeDateAndAuditoria(desde, hasta, i_audi);
        prepareRespuestas();
    }

    private void getCabeceras() {
        columns = new ArrayList<>();
        for (AuditoriaRespuesta res : respuestas) {
            columns.add(new ColumnModel(res.getIdAuditoriaPregunta().getEnunciado(), "respuestaAbierta"));
        }
    }

    public String nombreExcel() {
        Auditoria find = audiEJB.find(i_audi);
        return find == null ? "Resumen Auditoria" : "Resumen-Auditoria-" + find.getCodigo();
    }

    static public class ColumnModel implements Serializable {

        private String header;
        private String property;

        public ColumnModel(String header, String property) {
            this.header = header;
            this.property = property;
        }

        public String getHeader() {
            return header;
        }

        public String getProperty() {
            return property;
        }
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    public List<AuditoriaRespuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<AuditoriaRespuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public List<AuditoriaTipo> getListTipoAuditoria() {
        return listTipoAuditoria;
    }

    public void setListTipoAuditoria(List<AuditoriaTipo> listTipoAuditoria) {
        this.listTipoAuditoria = listTipoAuditoria;
    }

    public int getI_tipo_audi() {
        return i_tipo_audi;
    }

    public void setI_tipo_audi(int i_tipo_audi) {
        this.i_tipo_audi = i_tipo_audi;
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

    public AuditoriaRespuesta getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(AuditoriaRespuesta respuesta) {
        this.respuesta = respuesta;
    }

    public List<Auditoria> getListAudi() {
        return listAudi;
    }

    public void setListAudi(List<Auditoria> listAudi) {
        this.listAudi = listAudi;
    }

    public int getI_audi() {
        return i_audi;
    }

    public void setI_audi(int i_audi) {
        this.i_audi = i_audi;
    }

    public List<Object> getRespuestas_String() {
        return respuestas_String;
    }

    public void setRespuestas_String(List<Object> respuestas_String) {
        this.respuestas_String = respuestas_String;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

}
