/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.AuditoriaAlternativaRespuestaFacadeLocal;
import com.movilidad.ejb.AuditoriaAreaComunFacadeLocal;
import com.movilidad.ejb.AuditoriaEncabezadoFacadeLocal;
import com.movilidad.ejb.AuditoriaEstacionFacadeLocal;
import com.movilidad.ejb.AuditoriaFacadeLocal;
import com.movilidad.ejb.AuditoriaPreguntaFacadeLocal;
import com.movilidad.ejb.AuditoriaRealizadoPorFacadeLocal;
import com.movilidad.ejb.AuditoriaRespuestaFacadeLocal;
import com.movilidad.ejb.ConfigFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.GenericaFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.NovedadFacadeLocal;
import com.movilidad.ejb.ParamAreaUsrFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.Auditoria;
import com.movilidad.model.AuditoriaAlternativaRespuesta;
import com.movilidad.model.AuditoriaAreaComun;
import com.movilidad.model.AuditoriaEstacion;
import com.movilidad.model.AuditoriaPregunta;
import com.movilidad.model.AuditoriaRealizadoPor;
import com.movilidad.model.AuditoriaRespuesta;
import com.movilidad.model.Empleado;
import com.movilidad.model.Generica;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.Novedad;
import com.movilidad.model.ParamAreaUsr;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.AuditoriaRespuestaFile;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.primefaces.component.fileupload.FileUpload;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.selectoneradio.SelectOneRadio;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "audiRespuestaJSFMB")
@ViewScoped
public class AuditoriaRespuestaJSFMB implements Serializable {

    /**
     * Creates a new instance of AuditoriaResolverJSFMB
     */
    public AuditoriaRespuestaJSFMB() {
    }

    @EJB
    private GenericaFacadeLocal genericaEjb;
    @EJB
    private VehiculoFacadeLocal vehiculoEJB;
    @EJB
    private AuditoriaEstacionFacadeLocal audiEstacionEJB;
    @EJB
    private AuditoriaAreaComunFacadeLocal audiAreaComunEJB;
    @EJB
    private EmpleadoFacadeLocal emplEJB;

    @EJB
    private AuditoriaFacadeLocal audiEJB;
    @EJB
    private AuditoriaRespuestaFacadeLocal audiRespuestaEJB;
    @EJB
    private AuditoriaPreguntaFacadeLocal audiPreguntaEJB;
    @EJB
    private AuditoriaAlternativaRespuestaFacadeLocal audiAlternativaRespuestaEJB;
    @EJB
    private AuditoriaEncabezadoFacadeLocal audiEncabezadoEJB;
    @EJB
    private ParamAreaUsrFacadeLocal paramAreaUsrFacadeLocal;
    @EJB
    private AuditoriaRealizadoPorFacadeLocal audiRealizadoPorEJB;
    @EJB
    private ConfigFacadeLocal configEJB;
    @EJB
    private NovedadFacadeLocal novedadEjb;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @Inject
    private NovedadJSFManagedBean novedadJSFManagedBean;

    private Auditoria audi;
    private Empleado empleado;
    private UserExtended user;
    private List<AuditoriaPregunta> listaPreguntas;
    private List<AuditoriaRespuesta> listaPreguntasRespuestas;
    private List<AuditoriaAlternativaRespuesta> listaAlternativas;
    private List<Vehiculo> listVehiculo;
    private List<AuditoriaEstacion> listaAuditoriaEstacion;
    private List<AuditoriaAreaComun> listAuditoriaAreaComun;
    private List<Empleado> listEmpl;
    private Map<Integer, AuditoriaRespuestaFile> mapRespuestas;
    private boolean b_view;
    private boolean b_control;
    private Date fechaRealiza = MovilidadUtil.fechaCompletaHoy();

    private int i_bus;
    private int i_estacion;
    private int i_areaComun;
    private int i_empleado;
    private String observacionCorreo;

    private int i_idArea;

    private ParamAreaUsr paramAreaUsr;

    @PostConstruct
    public void init() {
        user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        paramAreaUsr = paramAreaUsrFacadeLocal.getByIdUser(user.getUsername());
        i_idArea = paramAreaUsr.getIdParamArea().getIdParamArea();
    }

    /**
     * Cargar la pregunta de la auditoria a resolver.
     *
     * @return
     */
    public List<AuditoriaPregunta> getPreguntas() {
        this.listaPreguntas = audiPreguntaEJB.findByIdAuditoria(audi.getIdAuditoria());
        return listaPreguntas;
    }

    /**
     * Cargar las respuesta de la auditoria.
     *
     * @return
     */
    public List<AuditoriaRespuesta> getPreguntasRepuestas() {
        this.listaPreguntasRespuestas = audiRespuestaEJB.findByIdAuditoria(audi.getIdAuditoria());
        return listaPreguntasRespuestas;
    }

    /**
     * Cargar las alternativas de respuesta de la auditoría.
     *
     * @param obj
     * @return
     */
    public List<AuditoriaAlternativaRespuesta> getAlternativas(AuditoriaPregunta obj) {
        this.listaAlternativas = audiAlternativaRespuestaEJB.findByIdAuditoriaPregunta(obj.getIdAuditoriaTipoRespuesta().getIdAuditoriaTipoRespuesta());
        return listaAlternativas;
    }

    /**
     * Armar cadena String compuesta de código TM, nombre y apellidos del
     * empleado realiza l auditoría.
     *
     * @return retorna cadena armada.
     */
    public String getReportadoPor() {
        return empleado.getCodigoTm() + " - " + empleado.getNombres() + " " + empleado.getApellidos();
    }

    /**
     * Responsable de captura el evento de cada uno de los posibles
     * SelectOneRadio en la vista, para obtener el valor indicado y cargarlo en
     * un objeto auditoria Respuesta.
     *
     * @param event
     */
    public void onSelectRespuesta(AjaxBehaviorEvent event) {
        SelectOneRadio test = (SelectOneRadio) event.getSource();
        if (mapRespuestas == null) {
            mapRespuestas = new HashMap<>();
        }
        String value = (String) test.getValue();
        Integer index_pregunta = Integer.parseInt(Util.getNumerosDeString(test.getClientId()));
        if (mapRespuestas.containsKey(index_pregunta)) {
            mapRespuestas.get(index_pregunta).getAuditoriaRespuesta().setIdAuditoriaAlternativaRespuesta(audiAlternativaRespuestaEJB.find(Integer.parseInt(value)));
        } else {
            AuditoriaPregunta pregunta = listaPreguntas.get(index_pregunta);
            AuditoriaRespuesta nuevaRespuesta = new AuditoriaRespuesta();
            nuevaRespuesta.setCreado(MovilidadUtil.fechaCompletaHoy());
            nuevaRespuesta.setIdAuditoriaPregunta(pregunta);
            nuevaRespuesta.setIdAuditoriaAlternativaRespuesta(audiAlternativaRespuestaEJB.find(Integer.parseInt(value)));
            mapRespuestas.put(index_pregunta, new AuditoriaRespuestaFile(nuevaRespuesta));
        }
    }

    /**
     * Responsable de cargar el archivo suministrado desde la vista por el
     * usuario responsable de la auditoría y almecenarlo en un objeto Auditoria
     * Respuesta.
     *
     * @param event
     * @throws Exception
     */
    public void handleFileUpload(FileUploadEvent event) throws Exception {
        FileUpload fileUpload = (FileUpload) event.getSource();
        UploadedFile file = event.getFile();
        Integer index_pregunta = Integer.parseInt(Util.getNumerosDeString(fileUpload.getClientId()));
        if (mapRespuestas == null) {
            mapRespuestas = new HashMap<>();
        }
        if (mapRespuestas.containsKey(index_pregunta)) {
            mapRespuestas.get(index_pregunta).setUploadedFile(file);
        } else {
            AuditoriaPregunta pregunta = listaPreguntas.get(index_pregunta);
            AuditoriaRespuesta nuevaRespuesta = new AuditoriaRespuesta();
            nuevaRespuesta.setCreado(MovilidadUtil.fechaCompletaHoy());
            nuevaRespuesta.setIdAuditoriaPregunta(pregunta);
            mapRespuestas.put(index_pregunta, new AuditoriaRespuestaFile(nuevaRespuesta, file));
        }
    }

    /**
     * Prepara el mapa de AuditoriaRespuesta como llave el index de la lista.
     */
    void cargarMapRespuestas() {
        mapRespuestas = new HashMap<>();
        for (AuditoriaPregunta pre : listaPreguntas) {
            AuditoriaRespuesta nuevaRespuesta = new AuditoriaRespuesta();
            nuevaRespuesta.setCreado(MovilidadUtil.fechaCompletaHoy());
            nuevaRespuesta.setIdAuditoriaPregunta(pre);
            int index = listaPreguntas.indexOf(pre);
            mapRespuestas.put(index, new AuditoriaRespuestaFile(nuevaRespuesta));
        }

    }

    /**
     * Cargar una lista de listVehiculo, listaAuditoriaEstacion,
     * listAuditoriaAreaComun o listEmpl. segun el lugar de auditoria a evaluar.
     */
    public void cargarComponente() {
        int idGopUnidadFuncional = audi.getIdGopUnidadFuncional() == null
                ? 0 : audi.getIdGopUnidadFuncional().getIdGopUnidadFuncional();
        if (audi.getIdAuditoriaEncabezado().getIdAuditoriaLugar().isBus()) {
            int idVehiculoTipo = audi.getIdAuditoriaEncabezado().getIdAuditoriaLugar().getIdVehiculoTipo() == null
                    ? 0 : audi.getIdAuditoriaEncabezado().getIdAuditoriaLugar().getIdVehiculoTipo().getIdVehiculoTipo();
            System.out.println("idGopUnidadFuncional->" + idGopUnidadFuncional);
            System.out.println("idVehiculoTipo->" + idVehiculoTipo);
            listVehiculo = vehiculoEJB.findByidGopUnidadFuncAndTipo(idGopUnidadFuncional, idVehiculoTipo);
        }
        if (audi.getIdAuditoriaEncabezado().getIdAuditoriaLugar().isEstacion()) {
            listaAuditoriaEstacion = audiEstacionEJB.findByArea(i_idArea);
        }
        if (audi.getIdAuditoriaEncabezado().getIdAuditoriaLugar().isAreaComun()) {
            listAuditoriaAreaComun = audiAreaComunEJB.findByArea(i_idArea);
        }
        if (audi.getIdAuditoriaEncabezado().getIdAuditoriaLugar().isEmpleado()) {
            listEmpl = emplEJB.getEmpledosByIdArea(i_idArea, idGopUnidadFuncional);
        }
    }

    /**
     * Responsable de captura el valor suministrado desde la la vista ya sea en
     * una campo de tipo InputText o InputTextarea, y luego almacenar el valor
     * en una objeto de AuditoriaRespuesta.
     *
     * @param event
     */
    public void capturarValorEscrito(AjaxBehaviorEvent event) {
        InputText inputText = null;
        InputTextarea inputTextArea = null;
        String respuesta = "";
        String respuestaArea = "";
        Integer index_pregunta = null;
        boolean textArea = false;
        if (event.getSource() instanceof InputText) {
            inputText = (InputText) event.getSource();
            index_pregunta = Integer.parseInt(Util.getNumerosDeString(inputText.getClientId()));
            respuesta = (String) inputText.getValue();
        }
        if (event.getSource() instanceof InputTextarea) {
            inputTextArea = (InputTextarea) event.getSource();
            index_pregunta = Integer.parseInt(Util.getNumerosDeString(inputTextArea.getClientId()));
            respuestaArea = (String) inputTextArea.getValue();
            textArea = true;
        }
        if (mapRespuestas == null) {
            mapRespuestas = new HashMap<>();
        }
        if (mapRespuestas.containsKey(index_pregunta)) {
            if (textArea) {
                mapRespuestas.get(index_pregunta).getAuditoriaRespuesta().setRespuestaObservacion(respuestaArea);
            } else {
                mapRespuestas.get(index_pregunta).getAuditoriaRespuesta().setRespuestaAbierta(respuesta);
            }
        } else {
            AuditoriaPregunta pregunta = listaPreguntas.get(index_pregunta);
            AuditoriaRespuesta nuevaRespuesta = new AuditoriaRespuesta();
            nuevaRespuesta.setCreado(MovilidadUtil.fechaCompletaHoy());
            nuevaRespuesta.setIdAuditoriaPregunta(pregunta);
            if (textArea) {
                nuevaRespuesta.setRespuestaObservacion(respuestaArea);
            } else {
                nuevaRespuesta.setRespuestaAbierta(respuesta);
            }
            mapRespuestas.put(index_pregunta, new AuditoriaRespuestaFile(nuevaRespuesta));
        }
    }

    /**
     * Invocar a los metodos transactionales de la presistencia de una ejecucion
     * de auditoría.
     *
     * @throws IOException
     */
    public void guardar() throws IOException {
        if (validarCampos(audi)) {
            return;
        }
        guardarTransactional();
        guardarArchivosTransactional();
        i_areaComun = 0;
        i_bus = 0;
        i_empleado = 0;
        i_estacion = 0;
        MovilidadUtil.addSuccessMessage("Auditoria Guardada Exitosamente.");
        MovilidadUtil.hideModal("crear_audi_resolver_dialog_wv");
    }

    private boolean validarCampos(Auditoria auditoria) {
        if (auditoria.getIdAuditoriaEncabezado().getIdAuditoriaLugar().isBus()) {
            if (i_bus == 0) {
                MovilidadUtil.addErrorMessage("Se debe selccionar un vehículo.");
                return true;
            }
        }
        if (auditoria.getIdAuditoriaEncabezado().getIdAuditoriaLugar().isEstacion()) {
            if (i_estacion == 0) {
                MovilidadUtil.addErrorMessage("Se debe selccionar una Estación.");
                return true;
            }
        }
        if (auditoria.getIdAuditoriaEncabezado().getIdAuditoriaLugar().isAreaComun()) {
            if (i_areaComun == 0) {
                MovilidadUtil.addErrorMessage("Se debe selccionar un Área Común.");
                return true;
            }
        }
        if (auditoria.getIdAuditoriaEncabezado().getIdAuditoriaLugar().isEmpleado()) {
            if (i_empleado == 0) {
                MovilidadUtil.addErrorMessage("Se debe selccionar un Empleado.");
                return true;
            }
        }
        return false;
    }

    /**
     * Alamecenas los archivos de las respuestas que apliquen.
     *
     * @throws IOException
     */
    @Transactional
    public void guardarArchivosTransactional() throws IOException {
        for (Map.Entry<Integer, AuditoriaRespuestaFile> obj : mapRespuestas.entrySet()) {
            String path = "";
            if (obj.getValue().getUploadedFile() != null && obj.getValue().getAuditoriaRespuesta().getIdAuditoriaRespuesta() != null) {
                String nameCarpeta = obj.getValue().getAuditoriaRespuesta().getIdAuditoriaRealizadoPor().getIdAuditoriaRealizadoPor() + "_" + obj.getValue().getAuditoriaRespuesta().getIdAuditoriaRespuesta();
                path = MovilidadUtil.subirFichero("ruta", obj.getValue().getUploadedFile(), nameCarpeta, "auditoria", MovilidadUtil.fechaCompletaHoy().toString());
                obj.getValue().getAuditoriaRespuesta().setPathDocumento(path);
                audiRespuestaEJB.edit(obj.getValue().getAuditoriaRespuesta());
            }
        }
    }

    /**
     * Persistir en base de datos las repuestas de la auditoría realizada.
     */
    @Transactional
    public void guardarTransactional() {
        audi.setModificado(MovilidadUtil.fechaCompletaHoy());
        audi.setUsername(user.getUsername());

        AuditoriaRealizadoPor auditoriaRealizadoPor = new AuditoriaRealizadoPor();

        auditoriaRealizadoPor.setIdAuditoria(audi);
        auditoriaRealizadoPor.setIdEmpleadoRealiza(empleado);
        auditoriaRealizadoPor.setFecha(fechaRealiza);
        auditoriaRealizadoPor.setUsername(user.getUsername());
        auditoriaRealizadoPor.setEstadoReg(0);
        auditoriaRealizadoPor.setCreado(MovilidadUtil.fechaCompletaHoy());
        if (audi.getIdAuditoriaEncabezado().getIdAuditoriaLugar().isBus()) {
            auditoriaRealizadoPor.setIdVehiculoAuditado(new Vehiculo(i_bus));
        }
        if (audi.getIdAuditoriaEncabezado().getIdAuditoriaLugar().isEstacion()) {
            auditoriaRealizadoPor.setIdAuditoriaEstacion(new AuditoriaEstacion(i_estacion));
        }
        if (audi.getIdAuditoriaEncabezado().getIdAuditoriaLugar().isAreaComun()) {
            auditoriaRealizadoPor.setIdAuditoriaAreaComun(new AuditoriaAreaComun(i_areaComun));
        }
        if (audi.getIdAuditoriaEncabezado().getIdAuditoriaLugar().isEmpleado()) {
            auditoriaRealizadoPor.setIdEmpleadoAuditado(emplEJB.find(i_empleado));
        }
        audiRealizadoPorEJB.create(auditoriaRealizadoPor);
        audiEncabezadoEJB.edit(audi.getIdAuditoriaEncabezado());
        audiEJB.edit(audi);
        boolean b_nov = false;
        boolean b_nov_gen = false;
        List<AuditoriaRespuesta> listaParaNovedad = null;
        if (audi.getIdAuditoriaEncabezado().getIdNovedadTipoDetalles() != null) {
            listaParaNovedad = new ArrayList<>();
            b_nov = true;
        }
        if (audi.getIdAuditoriaEncabezado().getIdGenericaTipoDetalles() != null) {
            listaParaNovedad = new ArrayList<>();
            b_nov_gen = true;
        }
        for (Map.Entry<Integer, AuditoriaRespuestaFile> obj : mapRespuestas.entrySet()) {
            obj.getValue().getAuditoriaRespuesta().setEstadoReg(0);
            obj.getValue().getAuditoriaRespuesta().setUsername(user.getUsername());
            obj.getValue().getAuditoriaRespuesta().setIdAuditoriaRealizadoPor(auditoriaRealizadoPor);
            if (b_nov || b_nov_gen) {
                if (obj.getValue().getAuditoriaRespuesta().getIdAuditoriaAlternativaRespuesta() != null) {
                    if (obj.getValue().getAuditoriaRespuesta().getIdAuditoriaAlternativaRespuesta().isGeneraNovedad()) {
                        listaParaNovedad.add(obj.getValue().getAuditoriaRespuesta());
                    }
                }
            }
            audiRespuestaEJB.create(obj.getValue().getAuditoriaRespuesta());
        }
        if (listaParaNovedad != null && !listaParaNovedad.isEmpty()) {
            crearNovedad(auditoriaRealizadoPor, listaParaNovedad, b_nov_gen, b_nov);
        }

    }

    private String construirObservacion(AuditoriaRespuesta ar, String separador, String li, String lic) {
        return separador + ar.getIdAuditoriaPregunta().getEnunciado() + ": " + separador
                + li + ar.getIdAuditoriaAlternativaRespuesta().getEnunciado() + lic
                + (ar.getRespuestaAbierta() != null ? separador + li + "Respuesta abierta: "
                + ar.getRespuestaAbierta() + lic : "")
                + (ar.getRespuestaObservacion() != null ? separador + li + "Respuesta Observación: "
                + ar.getRespuestaObservacion() + lic : "");
    }

    private void crearNovedad(AuditoriaRealizadoPor auditoriaRealizadoPor,
            List<AuditoriaRespuesta> listaParaNovedad,
            boolean b_nov_gen, boolean b_nov) {

        String observacion = "";
        observacionCorreo = "";
        for (AuditoriaRespuesta ar : listaParaNovedad) {
            observacion = observacion + construirObservacion(ar, "\n", "", "");
            observacionCorreo = observacionCorreo + construirObservacion(ar, "<br>", "<li>", "</li>");
        }
        if (b_nov) {
            Novedad novedad = new Novedad();
            novedad.setFecha(auditoriaRealizadoPor.getFecha());
            novedad.setCreado(MovilidadUtil.fechaCompletaHoy());
            novedad.setIdNovedadTipo(auditoriaRealizadoPor.getIdAuditoria().
                    getIdAuditoriaEncabezado().getIdNovedadTipoDetalles()
                    .getIdNovedadTipo());
            novedad.setIdNovedadTipoDetalle(auditoriaRealizadoPor.getIdAuditoria().
                    getIdAuditoriaEncabezado().getIdNovedadTipoDetalles());
            if (auditoriaRealizadoPor.getIdEmpleadoAuditado() != null) {
                novedad.setIdEmpleado(auditoriaRealizadoPor.getIdEmpleadoAuditado());
            }
            if (auditoriaRealizadoPor.getIdVehiculoAuditado() != null) {
                novedad.setIdVehiculo(auditoriaRealizadoPor.getIdVehiculoAuditado());
            }
            novedad.setUsername(user.getUsername());
            novedad.setPuntosPm(0);
            novedad.setPuntosPmConciliados(0);
            novedad.setLiquidada(0);
            if (novedad.getIdNovedadTipoDetalle().getFechas() == 1) {
                novedad.setDesde(novedad.getFecha());
                novedad.setHasta(novedad.getFecha());
            }

            novedad.setObservaciones(observacion);
            if (novedad.getIdNovedadTipoDetalle().getAfectaPm() == 1) {
                if (procedeNovedad("nov")) {
                    novedad.setProcede(1);
                    novedad.setPuntosPm(novedad.getIdNovedadTipoDetalle().getPuntosPm());
                    novedad.setPuntosPmConciliados(puntoView(novedad));

                }
            }
            this.novedadEjb.create(novedad);

            if (novedad.getIdNovedadTipoDetalle().getNotificacion() == 1) {
                notificar(novedad);
            }
        }
        if (auditoriaRealizadoPor.getIdEmpleadoAuditado() != null) {
            if (b_nov_gen) {
                Generica novedad = new Generica();

                novedad.setIdGenericaTipo(auditoriaRealizadoPor.getIdAuditoria().
                        getIdAuditoriaEncabezado().getIdGenericaTipoDetalles()
                        .getIdGenericaTipo());
                novedad.setIdGenericaTipoDetalle(auditoriaRealizadoPor.
                        getIdAuditoria().getIdAuditoriaEncabezado().
                        getIdGenericaTipoDetalles());
                novedad.setIdEmpleado(auditoriaRealizadoPor.getIdEmpleadoAuditado());
                novedad.setFecha(auditoriaRealizadoPor.getFecha());
                novedad.setCreado(MovilidadUtil.fechaCompletaHoy());
                novedad.setUsername(user.getUsername());

                novedad.setPuntosPm(novedad.getIdGenericaTipoDetalle().getPuntosPm());
                novedad.setPuntosPmConciliados(0);
                novedad.setLiquidada(0);
                novedad.setIdEmpleado(auditoriaRealizadoPor.getIdEmpleadoAuditado());
                novedad.setIdParamArea(paramAreaUsr.getIdParamArea());
                novedad.setObservaciones(observacion);

                if (novedad.getIdGenericaTipoDetalle().getAfectaPm() == 1) {
                    if (procedeNovedad("novg")) {
                        novedad.setProcede(1);
                        novedad.setPuntosPmConciliados(novedad.getIdGenericaTipoDetalle().getPuntosPm());
                    }
                }
                this.genericaEjb.create(novedad);

                if (novedad.getIdGenericaTipoDetalle().getNotificacion() == 1) {
                    notificar(novedad);
                }

            }
        }
    }

    private void notificar(Generica novedad) {
        Map mapa = getMailParamsGen();
        Map mailProperties = new HashMap();
        mailProperties.put("fecha", Util.dateFormat(novedad.getFecha()));
        mailProperties.put("tipo", novedad.getIdGenericaTipo().getNombreTipoNovedad());
        mailProperties.put("detalle", novedad.getIdGenericaTipoDetalle().getTituloTipoGenerica());
        mailProperties.put("fechas", novedad.getDesde() != null && novedad.getHasta() != null
                ? Util.dateFormat(novedad.getDesde()) + " hasta "
                + Util.dateFormat(novedad.getHasta()) : "");
        mailProperties.put("hora", novedad.getHora() != null ? Util.dateToTime(novedad.getHora()) : "");
        mailProperties.put("operador", novedad.getIdEmpleado() != null
                ? novedad.getIdEmpleado().getIdentificacion() + " - "
                + novedad.getIdEmpleado().getNombres() + " "
                + novedad.getIdEmpleado().getApellidos() : "");
        mailProperties.put("username", "");
        mailProperties.put("generada", Util.dateTimeFormat(novedad.getCreado()));
        mailProperties.put("observaciones", observacionCorreo);
        String subject = "";
        String destinatarios = "";

        if (novedad.getIdEmpleado() != null) {
            destinatarios = novedad.getIdEmpleado() != null ? novedad.getIdEmpleado().getEmailCorporativo() : "";
        }
        if (novedad.getIdGenericaTipoDetalle().getIdGenericaNotifProcesos() != null) {
            subject = novedad.getIdGenericaTipoDetalle().getIdGenericaNotifProcesos().getMensaje();
            if (destinatarios != null) {
                destinatarios = destinatarios + "," + novedad.getIdGenericaTipoDetalle().getIdGenericaNotifProcesos().getEmails();
            } else {
                destinatarios = novedad.getIdGenericaTipoDetalle().getIdGenericaNotifProcesos().getEmails();
            }
        }
        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", null);
    }

    /*
     * Parámetros para el envío de correos (Novedades PM)
     */
    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.find(Util.ID_NOVEDAD_PM_TEMPLATE);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    /*
     * Parámetros para el envío de correos (Novedades PM)
     */
    private Map getMailParamsGen() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.find(Util.ID_NOVEDAD_GEN_TEMPLATE);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    /**
     * Realiza el envío de correo de las novedad registrada a las partes
     * interesadas
     */
    private void notificar(Novedad novedad) {
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("fecha", Util.dateFormat(novedad.getFecha()));
        mailProperties.put("tipo", novedad.getIdNovedadTipo().getNombreTipoNovedad());
        mailProperties.put("detalle", novedad.getIdNovedadTipoDetalle().getTituloTipoNovedad());
        mailProperties.put("fechas", novedad.getDesde() != null && novedad.getHasta() != null ? Util.dateFormat(novedad.getDesde()) + " hasta " + Util.dateFormat(novedad.getHasta()) : "");
        mailProperties.put("operador", empleado != null ? empleado.getCodigoTm() + " - " + empleado.getNombres() + " " + empleado.getApellidos() : "");
//        mailProperties.put("operador", novedad.getIdEmpleado() != null ? novedad.getIdEmpleado().getNombres() + " " + novedad.getIdEmpleado().getApellidos() : "");
        mailProperties.put("vehiculo", "");
        mailProperties.put("username", "");
        mailProperties.put("generada", Util.dateTimeFormat(novedad.getCreado()));
        mailProperties.put("observaciones", observacionCorreo);
        String subject = "Novedad " + novedad.getIdNovedadTipo().getNombreTipoNovedad();
        String destinatarios = "";

        //Busqueda Operador Máster
        if (novedad.getIdEmpleado() != null) {
            String correoMaster = "";
            if (novedad.getIdEmpleado().getPmGrupoDetalleList().size() == 1) {
                correoMaster = novedad.getIdEmpleado().getPmGrupoDetalleList().get(0).getIdGrupo().getIdEmpleado().getEmailCorporativo();
            }
            destinatarios = novedad.getIdEmpleado() != null ? correoMaster + "," + novedad.getIdEmpleado().getEmailCorporativo() : "";
        }
        if (novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos() != null) {
            if (destinatarios != null) {
                destinatarios = destinatarios + "," + novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getEmails();
            } else {
                destinatarios = novedad.getIdNovedadTipoDetalle().getIdNotificacionProcesos().getEmails();
            }
        }

        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", null);
    }

    /**
     *
     * Verifica si una novedad_detalle procede de forma automática al
     * agregar/modificar una novedad
     *
     * @return true si el valor es IGUAL a 1, de lo contrario false
     */
    public boolean procedeNovedad(String llave) {
        try {
            return configEJB.findByKey(llave).getValue() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Retorna la cantidad de puntos PM de una novedad
     *
     * @param n
     * @return puntos Programa máster
     */
    public int puntoView(Novedad n) {
        if (n == null) {
            return 0;
        }
//        if (n.getIdNovedadDano() != null) {
//            return n.getIdNovedadDano().getIdVehiculoDanoSeveridad().getPuntosPm();
//        }
        if (n.getIdMulta() != null) {
            return n.getPuntosPm();
        }
        if (n.getIdNovedadDano() == null && n.getIdMulta() == null) {
            return n.getIdNovedadTipoDetalle().getPuntosPm();
        }

        return 0;
    }

    public Auditoria getAudi() {
        return audi;
    }

    public void setAudi(Auditoria audi) {
        this.audi = audi;
    }

    public boolean isB_view() {
        return b_view;
    }

    public void setB_view(boolean b_view) {
        this.b_view = b_view;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public boolean isB_control() {
        return b_control;
    }

    public void setB_control(boolean b_control) {
        this.b_control = b_control;
    }

    public List<AuditoriaRespuesta> getListaPreguntasRespuestas() {
        return listaPreguntasRespuestas;
    }

    public void setListaPreguntasRespuestas(List<AuditoriaRespuesta> listaPreguntasRespuestas) {
        this.listaPreguntasRespuestas = listaPreguntasRespuestas;
    }

    public List<Vehiculo> getListVehiculo() {
        return listVehiculo;
    }

    public void setListVehiculo(List<Vehiculo> listVehiculo) {
        this.listVehiculo = listVehiculo;
    }

    public List<AuditoriaEstacion> getListaAuditoriaEstacion() {
        return listaAuditoriaEstacion;
    }

    public void setListaAuditoriaEstacion(List<AuditoriaEstacion> listaAuditoriaEstacion) {
        this.listaAuditoriaEstacion = listaAuditoriaEstacion;
    }

    public List<AuditoriaAreaComun> getListAuditoriaAreaComun() {
        return listAuditoriaAreaComun;
    }

    public void setListAuditoriaAreaComun(List<AuditoriaAreaComun> listAuditoriaAreaComun) {
        this.listAuditoriaAreaComun = listAuditoriaAreaComun;
    }

    public int getI_bus() {
        return i_bus;
    }

    public void setI_bus(int i_bus) {
        this.i_bus = i_bus;
    }

    public int getI_estacion() {
        return i_estacion;
    }

    public void setI_estacion(int i_estacion) {
        this.i_estacion = i_estacion;
    }

    public int getI_areaComun() {
        return i_areaComun;
    }

    public void setI_areaComun(int i_areaComun) {
        this.i_areaComun = i_areaComun;
    }

    public int getI_empleado() {
        return i_empleado;
    }

    public void setI_empleado(int i_empleado) {
        this.i_empleado = i_empleado;
    }

    public List<Empleado> getListEmpl() {
        return listEmpl;
    }

    public void setListEmpl(List<Empleado> listEmpl) {
        this.listEmpl = listEmpl;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Date getFechaRealiza() {
        return fechaRealiza;
    }

    public void setFechaRealiza(Date fechaRealiza) {
        this.fechaRealiza = fechaRealiza;
    }

}
