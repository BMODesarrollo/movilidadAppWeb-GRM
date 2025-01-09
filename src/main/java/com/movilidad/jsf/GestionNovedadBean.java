package com.movilidad.jsf;

import com.genera.xls.GeneraXlsx;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.EmpleadoTipoCargoFacadeLocal;
import com.movilidad.ejb.GestorNovDetSemanaFacadeLocal;
import com.movilidad.ejb.GestorNovParamDetFacadeLocal;
import com.movilidad.ejb.GestorNovReqSemanaFacadeLocal;
import com.movilidad.ejb.GestorNovRequerimientoFacadeLocal;
import com.movilidad.ejb.GestorNovedadFacadeLocal;
import com.movilidad.ejb.GestorNovedadParamFacadeLocal;
import com.movilidad.ejb.GestorTablaTmpFacadeLocal;
import com.movilidad.ejb.NotificacionCorreoConfFacadeLocal;
import com.movilidad.ejb.NotificacionProcesosFacadeLocal;
import com.movilidad.ejb.NotificacionTemplateFacadeLocal;
import com.movilidad.ejb.NovedadTipoDetallesFacadeLocal;
import com.movilidad.ejb.ParamFeriadoFacadeLocal;
import com.movilidad.model.EmpleadoTipoCargo;
import com.movilidad.model.GestorNovDet;
import com.movilidad.model.GestorNovDetSemana;
import com.movilidad.model.GestorNovParamDet;
import com.movilidad.model.GestorNovReqDet;
import com.movilidad.model.GestorNovReqSemana;
import com.movilidad.model.GestorNovRequerimiento;
import com.movilidad.model.GestorNovedad;
import com.movilidad.model.GestorNovedadParam;
import com.movilidad.model.GestorTablaTmp;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.NotificacionCorreoConf;
import com.movilidad.model.NotificacionProcesos;
import com.movilidad.model.NotificacionTemplate;
import com.movilidad.model.Novedad;
import com.movilidad.model.NovedadTipoDetalles;
import com.movilidad.model.ParamFeriado;
import com.movilidad.security.UserExtended;
import com.movilidad.util.beans.GestionNovedad;
import com.movilidad.util.beans.GestorNovedadFecha;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SendMails;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "gestionNovedadBean")
@ViewScoped
public class GestionNovedadBean implements Serializable {

    @EJB
    private GestorNovedadFacadeLocal gestorNovedadEjb;
    @EJB
    private GestorNovRequerimientoFacadeLocal gestorNovedadRequerimientoEjb;
    @EJB
    private NovedadTipoDetallesFacadeLocal novedadTipoDetallesEjb;
    @EJB
    private EmpleadoFacadeLocal empleadoEjb;
    @EJB
    private GestorTablaTmpFacadeLocal gestorTablaTmpEjb;
    @EJB
    private GestorNovDetSemanaFacadeLocal novDetSemanaEjb;
    @EJB
    private GestorNovReqSemanaFacadeLocal novReqSemanaEjb;
    @EJB
    private GestorNovedadParamFacadeLocal gestorNovedadParamEjb;
    @EJB
    private GestorNovParamDetFacadeLocal gestorNovedadParamDetEjb;
    @EJB
    private NotificacionCorreoConfFacadeLocal NCCEJB;
    @EJB
    private NotificacionTemplateFacadeLocal notificacionTemplateEjb;
    @EJB
    private NotificacionProcesosFacadeLocal notificacionProcesosEjb;
    @EJB
    private EmpleadoTipoCargoFacadeLocal empleadoTipoCargoEjb;
    @EJB
    private ParamFeriadoFacadeLocal paramFeriadoEjb;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private Date fecha;

    private GestorNovedad gestorNovedad;
    private Integer i_EmpleadoCargo;

    private boolean flagNoExistenRegistros = false;
    private boolean flagModificacionRegistros;

    private List<GestorNovedad> lstGestorNovedad;
    private List<GestorNovDetSemana> lstGestorNovDetSemana;
    private List<GestorNovReqSemana> lstGestorNovReqSemana;
    private List<EmpleadoTipoCargo> lstTipoCargos;

    // Columnas de días y fechas de la semana que se muestran en la vista
    private List<String> lstDiasSemana;
    private List<GestorNovedadFecha> lstFechasSemana;
    private List<GestorNovParamDet> listaBloque1;
    private List<GestorNovParamDet> listaBloque4;
    private List<GestorNovParamDet> listaBloqueTotales;
    private final UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    // Se usa para generar archivo novedades freeway
    private StreamedContent file;

    @PostConstruct
    public void init() {
        i_EmpleadoCargo = null;
        flagModificacionRegistros = validarRolPrg();
        lstTipoCargos = empleadoTipoCargoEjb.obtenerCargosOperadores();
        fecha = MovilidadUtil.fechaHoy();
        consultar();
    }

    public void cargarDetalles() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        cargarDetallesTransactional();
        consultar();
    }

    public void cargarColumnas() {
        lstFechasSemana = obtenerFechasSemana(fecha);

        lstDiasSemana = new ArrayList<>();
        lstDiasSemana.add("LUNES");
        lstDiasSemana.add("MARTES");
        lstDiasSemana.add("MIÉRCOLES");
        lstDiasSemana.add("JUEVES");
        lstDiasSemana.add("VIERNES");
        lstDiasSemana.add("SÁBADO");
        lstDiasSemana.add("DOMINGO");
    }

    public void consultar() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            lstGestorNovDetSemana = null;
            lstGestorNovReqSemana = null;
            listaBloque1 = null;
            listaBloque4 = null;
            listaBloqueTotales = null;
            gestorNovedad = null;
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        if (i_EmpleadoCargo == null) {
            lstGestorNovDetSemana = null;
            lstGestorNovReqSemana = null;
            listaBloque1 = null;
            listaBloque4 = null;
            listaBloqueTotales = null;
            gestorNovedad = null;
            MovilidadUtil.addErrorMessage("DEBE seleccionar un cargo");
            return;
        }

        cargarColumnas();
        consultarDb();

        if (gestorNovedad == null) {
            lstGestorNovDetSemana = null;
            lstGestorNovReqSemana = null;
            listaBloque1 = null;
            listaBloque4 = null;
            listaBloqueTotales = null;
            flagNoExistenRegistros = true;
            MovilidadUtil.addAdvertenciaMessage("NO se encontraron datos para la fecha buscada");
            return;
        }

        /**
         * Se realiza la búsqueda de parámetros que van en los bloques: 1,4 y 5
         */
        List<GestorNovParamDet> lstParametros = gestorNovedadParamDetEjb.findAllByIdCargo(i_EmpleadoCargo, gestorNovedad.getIdGestorNovedad());

        if (gestorNovedad.getLiquidado() == ConstantsUtil.OFF_INT) {
            if (lstParametros == null || lstParametros.isEmpty()) {
                cargarDetallesPorCargo();
                return;
            }
        }

        flagNoExistenRegistros = false;

        obtenerItemsPorBloque(lstParametros);
        lstGestorNovDetSemana = novDetSemanaEjb.findAllByUfAndIdCargo(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), i_EmpleadoCargo, gestorNovedad.getIdGestorNovedad());
        lstGestorNovReqSemana = novReqSemanaEjb.findAllByIdCargo(i_EmpleadoCargo, gestorNovedad.getIdGestorNovedad());
    }

    @Transactional
    public void actualizar() {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        refrescarListaDetalles();
        actualizarTransactional();
    }

    /**
     * Método que se encarga de realizar la liquidación de la semana cargada
     *
     * @throws java.io.IOException
     */
    @Transactional
    public void liquidarSemana() throws IOException {
        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        liquidarSemanaTransactional();
        actualizarTransactional();
        notificar(gestorNovedad);
    }

    private void liquidarSemanaTransactional() {
        gestorNovedad.setLiquidado(1);
        gestorNovedad.setFechaLiquidado(MovilidadUtil.fechaCompletaHoy());
        MovilidadUtil.addSuccessMessage("Semana liquidada con éxito");
    }

    private void refrescarListaDetalles() {

        List<GestorNovedadFecha> lstFechas = obtenerFechasSemana(fecha);
        Date desde = lstFechas.get(0).getFecha();
        Date hasta = lstFechas.get(lstFechas.size() - 1).getFecha();

        try {
            /**
             * Se obtiene conteo de las novedades que afectan gestor, se ejcuta
             * procedimiento almacenado GestorNovedades ( encargado de hacer la
             * distribución del conteo de novedades para la semana siguiente a
             * la fecha seleccionada).
             */
            List<GestionNovedad> lstValores = novedadTipoDetallesEjb.obtenerValoresGestorNovedades(desde, hasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

            if (lstValores == null || lstValores.isEmpty()) {
                MovilidadUtil.addErrorMessage("NO se encontraron registros de novedades que afecten gestor");
                throw new Exception("NO se encontraron registros de novedades que afecten gestor");
            }

            List<GestorTablaTmp> lstGestorTablaTemp = gestorTablaTmpEjb.findAllByIdEmpleadoCargo(i_EmpleadoCargo, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            lstGestorNovDetSemana.clear();

            /**
             * Se obtiene la data de los detalles almacenados en la tabla
             * temporal
             */
            for (GestorTablaTmp tmp : lstGestorTablaTemp) {
                GestorNovDetSemana item = new GestorNovDetSemana();
                item.setIdGestorNovedad(gestorNovedad);
                item.setIdEmpleadoTipoCargo(tmp.getIdEmpleadoTipoCargo());
                item.setIdGopUnidadFuncional(tmp.getIdGopUnidadFuncional());
                item.setIdNovedadTipoDetalle(tmp.getIdNovedadTipoDetalle());
                item.setSumaGestor(tmp.getSumaGestor());
                item.setDia1(tmp.getDia1());
                item.setDia2(tmp.getDia2());
                item.setDia3(tmp.getDia3());
                item.setDia4(tmp.getDia4());
                item.setDia5(tmp.getDia5());
                item.setDia6(tmp.getDia6());
                item.setDia7(tmp.getDia7());
                lstGestorNovDetSemana.add(item);

            }

            int sum_obz_conducir_dia1 = 0;
            int sum_obz_conducir_dia2 = 0;
            int sum_obz_conducir_dia3 = 0;
            int sum_obz_conducir_dia4 = 0;
            int sum_obz_conducir_dia5 = 0;
            int sum_obz_conducir_dia6 = 0;
            int sum_obz_conducir_dia7 = 0;

            for (GestorNovDetSemana detalle : lstGestorNovDetSemana) {
                if (detalle.getSumaGestor().equals(ConstantsUtil.ON_INT)) {
                    if (detalle.getDia1() > 0) {
                        sum_obz_conducir_dia1 += detalle.getDia1();
                    }
                    if (detalle.getDia2() > 0) {
                        sum_obz_conducir_dia2 += detalle.getDia2();
                    }
                    if (detalle.getDia3() > 0) {
                        sum_obz_conducir_dia3 += detalle.getDia3();
                    }
                    if (detalle.getDia4() > 0) {
                        sum_obz_conducir_dia4 += detalle.getDia4();
                    }
                    if (detalle.getDia5() > 0) {
                        sum_obz_conducir_dia5 += detalle.getDia5();
                    }
                    if (detalle.getDia6() > 0) {
                        sum_obz_conducir_dia6 += detalle.getDia6();
                    }
                    if (detalle.getDia7() > 0) {
                        sum_obz_conducir_dia7 += detalle.getDia7();
                    }
                }

            }

            /**
             * Se realiza cálculo de sum_obz_conducir para cada dia de la semana
             */
            Long totalEmpleados = empleadoEjb.obtenerCantidadOperadoresByUfAndCargo(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), i_EmpleadoCargo, true);

            for (GestorNovParamDet paramAux : listaBloque1) {

                if (paramAux.getIdGestorNovedadParam().getIdGestorNovedadParam().equals(ConstantsUtil.ID_PLANTA_OBZ_ACTIVOS)) {

                    paramAux.setDia1(totalEmpleados);
                    paramAux.setDia2(totalEmpleados);
                    paramAux.setDia3(totalEmpleados);
                    paramAux.setDia4(totalEmpleados);
                    paramAux.setDia5(totalEmpleados);
                    paramAux.setDia6(totalEmpleados);
                    paramAux.setDia7(totalEmpleados);
                }

                if (paramAux.getIdGestorNovedadParam().getIdGestorNovedadParam().equals(ConstantsUtil.ID_OBZ_PARA_CONDUCIR)) {
                    paramAux.setDia1(listaBloque1.get(0).getDia1() - sum_obz_conducir_dia1);
                    paramAux.setDia2(listaBloque1.get(0).getDia2() - sum_obz_conducir_dia2);
                    paramAux.setDia3(listaBloque1.get(0).getDia3() - sum_obz_conducir_dia3);
                    paramAux.setDia4(listaBloque1.get(0).getDia4() - sum_obz_conducir_dia4);
                    paramAux.setDia5(listaBloque1.get(0).getDia5() - sum_obz_conducir_dia5);
                    paramAux.setDia6(listaBloque1.get(0).getDia6() - sum_obz_conducir_dia6);
                    paramAux.setDia7(listaBloque1.get(0).getDia7() - sum_obz_conducir_dia7);
                }
            }

        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al refrescar Lista de detalles");
            e.printStackTrace();
        }
    }

    private void actualizarTransactional() {
        try {

            if (lstGestorNovDetSemana == null || lstGestorNovDetSemana.isEmpty()) {
                MovilidadUtil.addErrorMessage("DEBE existir al menos un detalle diligenciado antes de actualizar los datos");
                return;
            }

            if (lstGestorNovReqSemana == null || lstGestorNovReqSemana.isEmpty()) {
                MovilidadUtil.addErrorMessage("DEBE existir al menos un requerimiento diligenciado antes de actualizar los datos");
                return;
            }

            List<GestorTablaTmp> lstGestorTablaTempAux = gestorTablaTmpEjb.findAllByUf(i_EmpleadoCargo, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

            List<GestorNovDetSemana> lstGestorNovDetSemanaAux = new ArrayList<>();

            for (GestorTablaTmp tmp : lstGestorTablaTempAux) {
                GestorNovDetSemana item = new GestorNovDetSemana();
                item.setIdGestorNovedad(gestorNovedad);
                item.setIdEmpleadoTipoCargo(tmp.getIdEmpleadoTipoCargo());
                item.setIdGopUnidadFuncional(tmp.getIdGopUnidadFuncional());
                item.setIdNovedadTipoDetalle(tmp.getIdNovedadTipoDetalle());
                item.setSumaGestor(tmp.getSumaGestor());
                item.setDia1(tmp.getDia1());
                item.setDia2(tmp.getDia2());
                item.setDia3(tmp.getDia3());
                item.setDia4(tmp.getDia4());
                item.setDia5(tmp.getDia5());
                item.setDia6(tmp.getDia6());
                item.setDia7(tmp.getDia7());
                lstGestorNovDetSemanaAux.add(item);
            }

            gestorNovedad.setModificado(MovilidadUtil.fechaCompletaHoy());
            gestorNovedad.setGestorNovDetSemanaList(lstGestorNovDetSemanaAux);
            gestorNovedad.getGestorNovDetSemanaList().addAll(lstGestorNovDetSemana);
            gestorNovedad.setGestorNovReqSemanaList(lstGestorNovReqSemana);
            gestorNovedad.setGestorNovParamDetList(listaBloque1);
            gestorNovedadEjb.edit(gestorNovedad);

            listaBloque4.forEach(bloque4 -> {
                gestorNovedadParamDetEjb.edit(bloque4);
            });

            listaBloqueTotales.forEach(total -> {
                gestorNovedadParamDetEjb.edit(total);
            });

//            gestorNovedad = null;
            MovilidadUtil.addSuccessMessage("Registro actualizado con éxito");
        } catch (Exception e) {
            e.printStackTrace();
            MovilidadUtil.addErrorMessage("Error al actualizar datos");
            throw new Error("Error al actualizar datos ");
        }
    }

    @Transactional
    private void cargarDetallesPorCargo() {
        List<GestorNovReqDet> lstRequerimientos = obtenerDetallesRequerimientos();
        List<GestorNovParamDet> lstParametros = obtenerNovParamDet();

        try {

            lstGestorNovDetSemana = novDetSemanaEjb.findAllByUfAndIdCargo(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), i_EmpleadoCargo, gestorNovedad.getIdGestorNovedad());
            lstGestorNovReqSemana = new ArrayList<>();

            int sum_obz_conducir_dia1 = 0;
            int sum_obz_conducir_dia2 = 0;
            int sum_obz_conducir_dia3 = 0;
            int sum_obz_conducir_dia4 = 0;
            int sum_obz_conducir_dia5 = 0;
            int sum_obz_conducir_dia6 = 0;
            int sum_obz_conducir_dia7 = 0;

            for (GestorNovDetSemana detalle : lstGestorNovDetSemana) {

                if (detalle.getSumaGestor().equals(ConstantsUtil.ON_INT)) {
                    if (detalle.getDia1() > 0) {
                        sum_obz_conducir_dia1 += detalle.getDia1();
                    }
                    if (detalle.getDia2() > 0) {
                        sum_obz_conducir_dia2 += detalle.getDia2();
                    }
                    if (detalle.getDia3() > 0) {
                        sum_obz_conducir_dia3 += detalle.getDia3();
                    }
                    if (detalle.getDia4() > 0) {
                        sum_obz_conducir_dia4 += detalle.getDia4();
                    }
                    if (detalle.getDia5() > 0) {
                        sum_obz_conducir_dia5 += detalle.getDia5();
                    }
                    if (detalle.getDia6() > 0) {
                        sum_obz_conducir_dia6 += detalle.getDia6();
                    }
                    if (detalle.getDia7() > 0) {
                        sum_obz_conducir_dia7 += detalle.getDia7();
                    }
                }

            }

            /**
             * Se realiza cálculo de sum_obz_conducir para cada dia de la semana
             */
            for (GestorNovParamDet parametro : lstParametros) {
                parametro.setIdGestorNovedad(gestorNovedad);
                parametro.setIdEmpleadoTipoCargo(new EmpleadoTipoCargo(i_EmpleadoCargo));

                if (parametro.getIdGestorNovedadParam().getIdGestorNovedadParam().equals(ConstantsUtil.ID_OBZ_PARA_CONDUCIR)) {
                    parametro.setDia1(parametro.getDia1() - sum_obz_conducir_dia1);
                    parametro.setDia2(parametro.getDia2() - sum_obz_conducir_dia2);
                    parametro.setDia3(parametro.getDia3() - sum_obz_conducir_dia3);
                    parametro.setDia4(parametro.getDia4() - sum_obz_conducir_dia4);
                    parametro.setDia5(parametro.getDia5() - sum_obz_conducir_dia5);
                    parametro.setDia6(parametro.getDia6() - sum_obz_conducir_dia6);
                    parametro.setDia7(parametro.getDia7() - sum_obz_conducir_dia7);
                }
            }

            /**
             * Se cargan los valores para los requerimientos
             */
            for (GestorNovReqDet reqDet : lstRequerimientos) {
                reqDet.setIdGestorNovedad(gestorNovedad);

                GestorNovReqSemana reqSemana = new GestorNovReqSemana();
                reqSemana.setIdGestorNovedad(gestorNovedad);
                reqSemana.setIdEmpleadoTipoCargo(new EmpleadoTipoCargo(i_EmpleadoCargo));
                reqSemana.setIdGestorNovRequerimiento(reqDet.getIdGestorNovRequerimiento());
                reqSemana.setDia1(0);
                reqSemana.setDia2(0);
                reqSemana.setDia3(0);
                reqSemana.setDia4(0);
                reqSemana.setDia5(0);
                reqSemana.setDia6(0);
                reqSemana.setDia7(0);

                lstGestorNovReqSemana.add(reqSemana);
            }

            // Se asignan valores para su posterior guardado en base de datos
            gestorNovedad.setGestorNovReqSemanaList(lstGestorNovReqSemana);
            gestorNovedad.setModificado(MovilidadUtil.fechaCompletaHoy());
            gestorNovedad.setGestorNovParamDetList(lstParametros);
            gestorNovedadEjb.edit(gestorNovedad);

            consultar();
            flagNoExistenRegistros = false;

        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar datos");
            e.printStackTrace();
        }
    }

    /**
     * Método que se encarga de realizar la carga de los datos para el gestor de
     * novedades
     */
    @Transactional
    private void cargarDetallesTransactional() {

        List<GestorNovReqDet> lstRequerimientos;
        List<GestorNovParamDet> lstParametros = obtenerNovParamDet();
        List<GestorNovedadFecha> lstFechas = obtenerFechasSemana(fecha);

        lstGestorNovedad = new ArrayList<>();

        Map<String, GestorNovDet> hmDetalles = obtenerDetallesAfectaGestor(lstFechas);
        lstRequerimientos = obtenerDetallesRequerimientos();

        Date desde = lstFechas.get(0).getFecha();
        Date hasta = lstFechas.get(lstFechas.size() - 1).getFecha();

        try {

            if (lstRequerimientos == null) {
                MovilidadUtil.addErrorMessage("NO se encontraron registrados los items de parametrización de la tabla gestor ( TABLA gestor_novedad_param)");
                throw new Exception("NO se encontraron registrados los items de parametrización de la tabla gestor ( TABLA gestor_novedad_param)");
            }

            /**
             * Se obtiene conteo de las novedades que afectan gestor, se ejcuta
             * procedimiento almacenado GestorNovedades ( encargado de hacer la
             * distribución del conteo de novedades para la semana siguiente a
             * la fecha seleccionada).
             */
            List<GestionNovedad> lstValores = novedadTipoDetallesEjb.obtenerValoresGestorNovedades(desde, hasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

            if (lstValores == null || lstValores.isEmpty()) {
                MovilidadUtil.addErrorMessage("NO se encontraron registros de novedades que afecten gestor");
                throw new Exception("NO se encontraron registros de novedades que afecten gestor");
            }

            /**
             * Se asignan los valores a los respectivos detalles y en caso de
             * que el detalle NO exista un detalle para una fecha
             * correspondiente se le coloca un valor de CERO
             */
            for (GestionNovedad item : lstValores) {

                String llave = item.getIdNovedadTipoDetalle() + "_" + Util.dateFormat(desde) + "_" + Util.dateFormat(hasta);

                if (hmDetalles.containsKey(llave)) {
                    GestorNovDet det = hmDetalles.get(llave);
                    det.setValor((int) item.getValor());
                }
            }

            List<GestorTablaTmp> lstGestorTablaTemp = gestorTablaTmpEjb.findAllByIdEmpleadoCargo(i_EmpleadoCargo, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
            List<GestorTablaTmp> lstGestorTablaTempAux = gestorTablaTmpEjb.findAllByUf(i_EmpleadoCargo, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

            lstGestorNovDetSemana = new ArrayList<>();
            lstGestorNovReqSemana = new ArrayList<>();

            /**
             * Se obtiene la data de los detalles almacenados en la tabla
             * temporal
             */
            for (GestorTablaTmp tmp : lstGestorTablaTemp) {
                GestorNovDetSemana item = new GestorNovDetSemana();
                item.setIdEmpleadoTipoCargo(tmp.getIdEmpleadoTipoCargo());
                item.setIdGopUnidadFuncional(tmp.getIdGopUnidadFuncional());
                item.setIdNovedadTipoDetalle(tmp.getIdNovedadTipoDetalle());
                item.setSumaGestor(tmp.getSumaGestor());
                item.setDia1(tmp.getDia1());
                item.setDia2(tmp.getDia2());
                item.setDia3(tmp.getDia3());
                item.setDia4(tmp.getDia4());
                item.setDia5(tmp.getDia5());
                item.setDia6(tmp.getDia6());
                item.setDia7(tmp.getDia7());
                lstGestorNovDetSemana.add(item);
            }

            List<GestorNovDetSemana> lstGestorNovDetSemanaAux = new ArrayList<>();

            for (GestorTablaTmp tmp : lstGestorTablaTempAux) {
                GestorNovDetSemana item = new GestorNovDetSemana();
                item.setIdGestorNovedad(lstGestorNovedad.get(0));
                item.setIdEmpleadoTipoCargo(tmp.getIdEmpleadoTipoCargo());
                item.setIdGopUnidadFuncional(tmp.getIdGopUnidadFuncional());
                item.setIdNovedadTipoDetalle(tmp.getIdNovedadTipoDetalle());
                item.setSumaGestor(tmp.getSumaGestor());
                item.setDia1(tmp.getDia1());
                item.setDia2(tmp.getDia2());
                item.setDia3(tmp.getDia3());
                item.setDia4(tmp.getDia4());
                item.setDia5(tmp.getDia5());
                item.setDia6(tmp.getDia6());
                item.setDia7(tmp.getDia7());
                lstGestorNovDetSemanaAux.add(item);
            }

            /**
             * Se realiza la sumatoria de valores de detalles para cada dia de
             * la semana para restarlo a sum_obz_conducir
             */
            for (GestorNovedad item : lstGestorNovedad) {

                int sum_obz_conducir_dia1 = 0;
                int sum_obz_conducir_dia2 = 0;
                int sum_obz_conducir_dia3 = 0;
                int sum_obz_conducir_dia4 = 0;
                int sum_obz_conducir_dia5 = 0;
                int sum_obz_conducir_dia6 = 0;
                int sum_obz_conducir_dia7 = 0;

                for (GestorNovDetSemana detalle : lstGestorNovDetSemana) {
                    detalle.setIdGestorNovedad(item);

                    if (detalle.getSumaGestor().equals(ConstantsUtil.ON_INT)) {
                        if (detalle.getDia1() > 0) {
                            sum_obz_conducir_dia1 += detalle.getDia1();
                        }
                        if (detalle.getDia2() > 0) {
                            sum_obz_conducir_dia2 += detalle.getDia2();
                        }
                        if (detalle.getDia3() > 0) {
                            sum_obz_conducir_dia3 += detalle.getDia3();
                        }
                        if (detalle.getDia4() > 0) {
                            sum_obz_conducir_dia4 += detalle.getDia4();
                        }
                        if (detalle.getDia5() > 0) {
                            sum_obz_conducir_dia5 += detalle.getDia5();
                        }
                        if (detalle.getDia6() > 0) {
                            sum_obz_conducir_dia6 += detalle.getDia6();
                        }
                        if (detalle.getDia7() > 0) {
                            sum_obz_conducir_dia7 += detalle.getDia7();
                        }
                    }

                }

                /**
                 * Se realiza cálculo de sum_obz_conducir para cada dia de la
                 * semana
                 */
                for (GestorNovParamDet parametro : lstParametros) {
                    parametro.setIdGestorNovedad(item);
                    parametro.setIdEmpleadoTipoCargo(new EmpleadoTipoCargo(i_EmpleadoCargo));

                    if (parametro.getIdGestorNovedadParam().getIdGestorNovedadParam().equals(ConstantsUtil.ID_OBZ_PARA_CONDUCIR)) {
                        parametro.setDia1(parametro.getDia1() - sum_obz_conducir_dia1);
                        parametro.setDia2(parametro.getDia2() - sum_obz_conducir_dia2);
                        parametro.setDia3(parametro.getDia3() - sum_obz_conducir_dia3);
                        parametro.setDia4(parametro.getDia4() - sum_obz_conducir_dia4);
                        parametro.setDia5(parametro.getDia5() - sum_obz_conducir_dia5);
                        parametro.setDia6(parametro.getDia6() - sum_obz_conducir_dia6);
                        parametro.setDia7(parametro.getDia7() - sum_obz_conducir_dia7);
                    }
                }

                /**
                 * Se cargan los valores para los requerimientos
                 */
                for (GestorNovReqDet reqDet : lstRequerimientos) {
                    reqDet.setIdGestorNovedad(item);

                    GestorNovReqSemana reqSemana = new GestorNovReqSemana();
                    reqSemana.setIdGestorNovedad(item);
                    reqSemana.setIdEmpleadoTipoCargo(new EmpleadoTipoCargo(i_EmpleadoCargo));
                    reqSemana.setIdGestorNovRequerimiento(reqDet.getIdGestorNovRequerimiento());
                    reqSemana.setDia1(0);
                    reqSemana.setDia2(0);
                    reqSemana.setDia3(0);
                    reqSemana.setDia4(0);
                    reqSemana.setDia5(0);
                    reqSemana.setDia6(0);
                    reqSemana.setDia7(0);

                    lstGestorNovReqSemana.add(reqSemana);
                }

                // Se asignan valores para su posterior guardado en base de datos
                item.setGestorNovDetSemanaList(lstGestorNovDetSemana);
                item.getGestorNovDetSemanaList().addAll(lstGestorNovDetSemanaAux);
                item.setGestorNovReqSemanaList(lstGestorNovReqSemana);
                item.setGestorNovParamDetList(lstParametros);
                item.setGestorNovReqDetList(lstRequerimientos);
                item.setLiquidado(0);
                item.setIdGopUnidadFuncional(new GopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));
                gestorNovedadEjb.create(item);

            }

            consultar();
            flagNoExistenRegistros = false;

        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error al guardar datos");
            e.printStackTrace();
        }

    }

    public void onEditTblProgramacion(String columna, Integer index) {

        /**
         * Programadas = Tablas Planeadas - Tablas Eliminar Cant Sercones =
         * Tablas Programadas x ICB
         */
        GestorNovParamDet objAux;
        /**
         * Se verifica si se ingresó un valor en la columna 1, y dependiendo de
         * la pocisión de la celda ingresada se hace el respectivo cálculo.
         */
        if (columna.equals("prgDia1")) {

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * planeadas y se realiza la resta con tablas eliminadas con el fin
             * obtener el valor de tablas programadas
             */
            if (index.equals(0)) {

                objAux = listaBloque4.get(2);

                objAux.setDia1(listaBloque4.get(0).getDia1() - listaBloque4.get(1).getDia1());

            }

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * eliminadas y se realiza la resta con tablas planeadas con el fin
             * obtener el valor de tablas programadas
             */
            if (index.equals(1)) {

                objAux = listaBloque4.get(2);

                objAux.setDia1(listaBloque4.get(0).getDia1() - listaBloque4.get(1).getDia1());
            }

            /**
             * Se verifica si se ingresó valor desde la celda de ICB para
             * realizar el cálculo de la cantidad de sercones
             */
            if (index.equals(3)) {

                objAux = listaBloque4.get(4);

                objAux.setDia1((int) (listaBloque4.get(2).getDia1() * listaBloque4.get(3).getDia1()));
            }
        } else if (columna.equals("prgDia2")) {

            /**
             * Se verifica si se ingresó un valor en la columna 2, y dependiendo
             * de la pocisión de la celda ingresada se hace el respectivo
             * cálculo.
             */
            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * planeadas y se realiza la resta con tablas eliminadas con el fin
             * obtener el valor de tablas programadas
             */
            if (index.equals(0)) {

                objAux = listaBloque4.get(2);

                objAux.setDia2(listaBloque4.get(0).getDia2() - listaBloque4.get(1).getDia2());

            }

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * eliminadas y se realiza la resta con tablas planeadas con el fin
             * obtener el valor de tablas programadas
             */
            if (index.equals(1)) {

                objAux = listaBloque4.get(2);

                objAux.setDia2(listaBloque4.get(0).getDia2() - listaBloque4.get(1).getDia2());
            }

            /**
             * Se verifica si se ingresó valor desde la celda de ICB para
             * realizar el cálculo de la cantidad de sercones
             */
            if (index.equals(3)) {

                objAux = listaBloque4.get(4);

                objAux.setDia2((int) (listaBloque4.get(2).getDia2() * listaBloque4.get(3).getDia2()));
            }
        } else if (columna.equals("prgDia3")) {

            /**
             * Se verifica si se ingresó un valor en la columna 3, y dependiendo
             * de la pocisión de la celda ingresada se hace el respectivo
             * cálculo.
             */
            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * planeadas y se realiza la resta con tablas eliminadas con el fin
             * obtener el valor de tablas programadas
             */
            if (index.equals(0)) {

                objAux = listaBloque4.get(2);

                objAux.setDia3(listaBloque4.get(0).getDia3() - listaBloque4.get(1).getDia3());

            }

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * eliminadas y se realiza la resta con tablas planeadas con el fin
             * obtener el valor de tablas programadas
             */
            if (index.equals(1)) {

                objAux = listaBloque4.get(2);

                objAux.setDia3(listaBloque4.get(0).getDia3() - listaBloque4.get(1).getDia3());
            }

            /**
             * Se verifica si se ingresó valor desde la celda de ICB para
             * realizar el cálculo de la cantidad de sercones
             */
            if (index.equals(3)) {

                objAux = listaBloque4.get(4);

                objAux.setDia3((int) (listaBloque4.get(2).getDia3() * listaBloque4.get(3).getDia3()));
            }
        } else if (columna.equals("prgDia4")) {

            /**
             * Se verifica si se ingresó un valor en la columna 4, y dependiendo
             * de la pocisión de la celda ingresada se hace el respectivo
             * cálculo.
             */
            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * planeadas y se realiza la resta con tablas eliminadas con el fin
             * obtener el valor de tablas programadas
             */
            if (index.equals(0)) {

                objAux = listaBloque4.get(2);

                objAux.setDia4(listaBloque4.get(0).getDia4() - listaBloque4.get(1).getDia4());

            }

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * eliminadas y se realiza la resta con tablas planeadas con el fin
             * obtener el valor de tablas programadas
             */
            if (index.equals(1)) {

                objAux = listaBloque4.get(2);

                objAux.setDia4(listaBloque4.get(0).getDia4() - listaBloque4.get(1).getDia4());
            }

            /**
             * Se verifica si se ingresó valor desde la celda de ICB para
             * realizar el cálculo de la cantidad de sercones
             */
            if (index.equals(3)) {

                objAux = listaBloque4.get(4);

                objAux.setDia4((int) (listaBloque4.get(2).getDia4() * listaBloque4.get(3).getDia4()));
            }
        } else if (columna.equals("prgDia5")) {

            /**
             * Se verifica si se ingresó un valor en la columna 5, y dependiendo
             * de la pocisión de la celda ingresada se hace el respectivo
             * cálculo.
             */
            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * planeadas y se realiza la resta con tablas eliminadas con el fin
             * obtener el valor de tablas programadas
             */
            if (index.equals(0)) {

                objAux = listaBloque4.get(2);

                objAux.setDia5(listaBloque4.get(0).getDia5() - listaBloque4.get(1).getDia5());

            }

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * eliminadas y se realiza la resta con tablas planeadas con el fin
             * obtener el valor de tablas programadas
             */
            if (index.equals(1)) {

                objAux = listaBloque4.get(2);

                objAux.setDia5(listaBloque4.get(0).getDia5() - listaBloque4.get(1).getDia5());
            }

            /**
             * Se verifica si se ingresó valor desde la celda de ICB para
             * realizar el cálculo de la cantidad de sercones
             */
            if (index.equals(3)) {

                objAux = listaBloque4.get(4);

                objAux.setDia5((int) (listaBloque4.get(2).getDia5() * listaBloque4.get(3).getDia5()));
            }
        } else if (columna.equals("prgDia6")) {

            /**
             * Se verifica si se ingresó un valor en la columna 6, y dependiendo
             * de la pocisión de la celda ingresada se hace el respectivo
             * cálculo.
             */
            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * planeadas y se realiza la resta con tablas eliminadas con el fin
             * obtener el valor de tablas programadas
             */
            if (index.equals(0)) {

                objAux = listaBloque4.get(2);

                objAux.setDia6(listaBloque4.get(0).getDia6() - listaBloque4.get(1).getDia6());

            }

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * eliminadas y se realiza la resta con tablas planeadas con el fin
             * obtener el valor de tablas programadas
             */
            if (index.equals(1)) {

                objAux = listaBloque4.get(2);

                objAux.setDia6(listaBloque4.get(0).getDia6() - listaBloque4.get(1).getDia6());

            }

            /**
             * Se verifica si se ingresó valor desde la celda de ICB para
             * realizar el cálculo de la cantidad de sercones
             */
            if (index.equals(3)) {

                objAux = listaBloque4.get(4);

                objAux.setDia6((int) (listaBloque4.get(2).getDia6() * listaBloque4.get(3).getDia6()));
            }
        } else if (columna.equals("prgDia7")) {

            /**
             * Se verifica si se ingresó un valor en la columna 7, y dependiendo
             * de la pocisión de la celda ingresada se hace el respectivo
             * cálculo.
             */
            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * planeadas y se realiza la resta con tablas eliminadas con el fin
             * obtener el valor de tablas programadas
             */
            if (index.equals(0)) {

                objAux = listaBloque4.get(2);

                objAux.setDia7(listaBloque4.get(0).getDia7() - listaBloque4.get(1).getDia7());

            }

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * eliminadas y se realiza la resta con tablas planeadas con el fin
             * obtener el valor de tablas programadas
             */
            if (index.equals(1)) {

                objAux = listaBloque4.get(2);

                objAux.setDia7(listaBloque4.get(0).getDia7() - listaBloque4.get(1).getDia7());

            }

            /**
             * Se verifica si se ingresó valor desde la celda de ICB para
             * realizar el cálculo de la cantidad de sercones
             */
            if (index.equals(3)) {

                objAux = listaBloque4.get(4);

                objAux.setDia7((int) (listaBloque4.get(2).getDia7() * listaBloque4.get(3).getDia7()));
            }
        }

        listaBloque1.stream().filter(obj1 -> (obj1.getIdGestorNovedadParam().getIdGestorNovedadParam().equals(ConstantsUtil.ID_OBZ_PARA_CONDUCIR))).forEachOrdered(obj1 -> {
            GestorNovParamDet objFaltante = listaBloqueTotales.get(0);
            if (columna.equals("prgDia1")) {
                objFaltante.setDia1(obj1.getDia1() - listaBloque4.get(4).getDia1());
            }
            if (columna.equals("prgDia2")) {
                objFaltante.setDia2(obj1.getDia2() - listaBloque4.get(4).getDia2());
            }
            if (columna.equals("prgDia3")) {
                objFaltante.setDia3(obj1.getDia3() - listaBloque4.get(4).getDia3());
            }
            if (columna.equals("prgDia4")) {
                objFaltante.setDia4(obj1.getDia4() - listaBloque4.get(4).getDia4());
            }
            if (columna.equals("prgDia5")) {
                objFaltante.setDia5(obj1.getDia5() - listaBloque4.get(4).getDia5());
            }
            if (columna.equals("prgDia6")) {
                objFaltante.setDia6(obj1.getDia6() - listaBloque4.get(4).getDia6());
            }
            if (columna.equals("prgDia7")) {
                objFaltante.setDia7(obj1.getDia7() - listaBloque4.get(4).getDia7());
            }
        });

        actualizarTransactional();
//        MovilidadUtil.onEjecutarScript("onCellEditTblProgramacion()");
        MovilidadUtil.addSuccessMessage("Valor asignado éxitosamente");
    }

    public void onCellEditTblProgramacion(CellEditEvent event) {

        double oldValue = (Double) event.getOldValue();
        double newValue = (Double) event.getNewValue();

        /**
         * Programadas = Tablas Planeadas - Tablas Eliminar Cant Sercones =
         * Tablas Programadas x ICB
         */
        GestorNovParamDet objAux;
        /**
         * Se verifica si se ingresó un valor en la columna 1, y dependiendo de
         * la pocisión de la celda ingresada se hace el respectivo cálculo.
         */
        if (event.getColumn().getColumnKey().endsWith("prgDia1")) {

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * planeadas y se realiza la resta con tablas eliminadas con el fin
             * obtener el valor de tablas programadas
             */
            if (event.getRowIndex() == 0) {

                objAux = listaBloque4.get(2);

                objAux.setDia1(newValue - listaBloque4.get(1).getDia1());

            }

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * eliminadas y se realiza la resta con tablas planeadas con el fin
             * obtener el valor de tablas programadas
             */
            if (event.getRowIndex() == 1) {

                objAux = listaBloque4.get(2);

                objAux.setDia1(listaBloque4.get(0).getDia1() - newValue);
            }

            /**
             * Se verifica si se ingresó valor desde la celda de ICB para
             * realizar el cálculo de la cantidad de sercones
             */
            if (event.getRowIndex() == 3) {

                objAux = listaBloque4.get(4);

                objAux.setDia1((int) (listaBloque4.get(2).getDia1() * newValue));
            }
        }

        /**
         * Se verifica si se ingresó un valor en la columna 2, y dependiendo de
         * la pocisión de la celda ingresada se hace el respectivo cálculo.
         */
        if (event.getColumn().getColumnKey().endsWith("prgDia2")) {

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * planeadas y se realiza la resta con tablas eliminadas con el fin
             * obtener el valor de tablas programadas
             */
            if (event.getRowIndex() == 0) {

                objAux = listaBloque4.get(2);

                objAux.setDia2(newValue - listaBloque4.get(1).getDia2());

            }

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * eliminadas y se realiza la resta con tablas planeadas con el fin
             * obtener el valor de tablas programadas
             */
            if (event.getRowIndex() == 1) {

                objAux = listaBloque4.get(2);

                objAux.setDia2(listaBloque4.get(0).getDia2() - newValue);
            }

            /**
             * Se verifica si se ingresó valor desde la celda de ICB para
             * realizar el cálculo de la cantidad de sercones
             */
            if (event.getRowIndex() == 3) {

                objAux = listaBloque4.get(4);

                objAux.setDia2((int) (listaBloque4.get(2).getDia2() * newValue));
            }
        }

        /**
         * Se verifica si se ingresó un valor en la columna 3, y dependiendo de
         * la pocisión de la celda ingresada se hace el respectivo cálculo.
         */
        if (event.getColumn().getColumnKey().endsWith("prgDia3")) {

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * planeadas y se realiza la resta con tablas eliminadas con el fin
             * obtener el valor de tablas programadas
             */
            if (event.getRowIndex() == 0) {

                objAux = listaBloque4.get(2);

                objAux.setDia3(newValue - listaBloque4.get(1).getDia3());

            }

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * eliminadas y se realiza la resta con tablas planeadas con el fin
             * obtener el valor de tablas programadas
             */
            if (event.getRowIndex() == 1) {

                objAux = listaBloque4.get(2);

                objAux.setDia3(listaBloque4.get(0).getDia3() - newValue);
            }

            /**
             * Se verifica si se ingresó valor desde la celda de ICB para
             * realizar el cálculo de la cantidad de sercones
             */
            if (event.getRowIndex() == 3) {

                objAux = listaBloque4.get(4);

                objAux.setDia3((int) (listaBloque4.get(2).getDia3() * newValue));
            }
        }

        /**
         * Se verifica si se ingresó un valor en la columna 4, y dependiendo de
         * la pocisión de la celda ingresada se hace el respectivo cálculo.
         */
        if (event.getColumn().getColumnKey().endsWith("prgDia4")) {

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * planeadas y se realiza la resta con tablas eliminadas con el fin
             * obtener el valor de tablas programadas
             */
            if (event.getRowIndex() == 0) {

                objAux = listaBloque4.get(2);

                objAux.setDia4(newValue - listaBloque4.get(1).getDia4());

            }

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * eliminadas y se realiza la resta con tablas planeadas con el fin
             * obtener el valor de tablas programadas
             */
            if (event.getRowIndex() == 1) {

                objAux = listaBloque4.get(2);

                objAux.setDia4(listaBloque4.get(0).getDia4() - newValue);
            }

            /**
             * Se verifica si se ingresó valor desde la celda de ICB para
             * realizar el cálculo de la cantidad de sercones
             */
            if (event.getRowIndex() == 3) {

                objAux = listaBloque4.get(4);

                objAux.setDia4((int) (listaBloque4.get(2).getDia4() * newValue));
            }
        }

        /**
         * Se verifica si se ingresó un valor en la columna 5, y dependiendo de
         * la pocisión de la celda ingresada se hace el respectivo cálculo.
         */
        if (event.getColumn().getColumnKey().endsWith("prgDia5")) {

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * planeadas y se realiza la resta con tablas eliminadas con el fin
             * obtener el valor de tablas programadas
             */
            if (event.getRowIndex() == 0) {

                objAux = listaBloque4.get(2);

                objAux.setDia5(newValue - listaBloque4.get(1).getDia5());

            }

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * eliminadas y se realiza la resta con tablas planeadas con el fin
             * obtener el valor de tablas programadas
             */
            if (event.getRowIndex() == 1) {

                objAux = listaBloque4.get(2);

                objAux.setDia5(listaBloque4.get(0).getDia5() - newValue);
            }

            /**
             * Se verifica si se ingresó valor desde la celda de ICB para
             * realizar el cálculo de la cantidad de sercones
             */
            if (event.getRowIndex() == 3) {

                objAux = listaBloque4.get(4);

                objAux.setDia5((int) (listaBloque4.get(2).getDia5() * newValue));
            }
        }

        /**
         * Se verifica si se ingresó un valor en la columna 6, y dependiendo de
         * la pocisión de la celda ingresada se hace el respectivo cálculo.
         */
        if (event.getColumn().getColumnKey().endsWith("prgDia6")) {

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * planeadas y se realiza la resta con tablas eliminadas con el fin
             * obtener el valor de tablas programadas
             */
            if (event.getRowIndex() == 0) {

                objAux = listaBloque4.get(2);

                objAux.setDia6(newValue - listaBloque4.get(1).getDia6());

            }

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * eliminadas y se realiza la resta con tablas planeadas con el fin
             * obtener el valor de tablas programadas
             */
            if (event.getRowIndex() == 1) {

                objAux = listaBloque4.get(2);

                objAux.setDia6(listaBloque4.get(0).getDia6() - newValue);

            }

            /**
             * Se verifica si se ingresó valor desde la celda de ICB para
             * realizar el cálculo de la cantidad de sercones
             */
            if (event.getRowIndex() == 3) {

                objAux = listaBloque4.get(4);

                objAux.setDia6((int) (listaBloque4.get(2).getDia6() * newValue));
            }
        }

        /**
         * Se verifica si se ingresó un valor en la columna 7, y dependiendo de
         * la pocisión de la celda ingresada se hace el respectivo cálculo.
         */
        if (event.getColumn().getColumnKey().endsWith("prgDia7")) {

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * planeadas y se realiza la resta con tablas eliminadas con el fin
             * obtener el valor de tablas programadas
             */
            if (event.getRowIndex() == 0) {

                objAux = listaBloque4.get(2);

                objAux.setDia7(newValue - listaBloque4.get(1).getDia7());

            }

            /**
             * Se verifica si se ingresó valor desde la celda de tablas
             * eliminadas y se realiza la resta con tablas planeadas con el fin
             * obtener el valor de tablas programadas
             */
            if (event.getRowIndex() == 1) {

                objAux = listaBloque4.get(2);

                objAux.setDia7(listaBloque4.get(0).getDia7() - newValue);

            }

            /**
             * Se verifica si se ingresó valor desde la celda de ICB para
             * realizar el cálculo de la cantidad de sercones
             */
            if (event.getRowIndex() == 3) {

                objAux = listaBloque4.get(4);

                objAux.setDia7((int) (listaBloque4.get(2).getDia7() * newValue));
            }
        }

        for (GestorNovParamDet obj1 : listaBloque1) {
            if (obj1.getIdGestorNovedadParam().getIdGestorNovedadParam().equals(ConstantsUtil.ID_OBZ_PARA_CONDUCIR)) {

                GestorNovParamDet objFaltante = listaBloqueTotales.get(0);

                if (event.getColumn().getColumnKey().endsWith("prgDia1")) {
                    objFaltante.setDia1(obj1.getDia1() - listaBloque4.get(4).getDia1());
                }
                if (event.getColumn().getColumnKey().endsWith("prgDia2")) {
                    objFaltante.setDia2(obj1.getDia2() - listaBloque4.get(4).getDia2());
                }
                if (event.getColumn().getColumnKey().endsWith("prgDia3")) {
                    objFaltante.setDia3(obj1.getDia3() - listaBloque4.get(4).getDia3());
                }
                if (event.getColumn().getColumnKey().endsWith("prgDia4")) {
                    objFaltante.setDia4(obj1.getDia4() - listaBloque4.get(4).getDia4());
                }
                if (event.getColumn().getColumnKey().endsWith("prgDia5")) {
                    objFaltante.setDia5(obj1.getDia5() - listaBloque4.get(4).getDia5());
                }
                if (event.getColumn().getColumnKey().endsWith("prgDia6")) {
                    objFaltante.setDia6(obj1.getDia6() - listaBloque4.get(4).getDia6());
                }
                if (event.getColumn().getColumnKey().endsWith("prgDia7")) {
                    objFaltante.setDia7(obj1.getDia7() - listaBloque4.get(4).getDia7());
                }
            }
        }

        actualizarTransactional();

        MovilidadUtil.addSuccessMessage("Valor asignado éxitosamente");
    }

    public void onCellEditTblGestorNovReqDet(CellEditEvent event) {

        Integer oldValue = (Integer) event.getOldValue();
        Integer newValue = (Integer) event.getNewValue();

        GestorNovParamDet objCantidadSercones = listaBloque4.get(4);
        GestorNovParamDet objFaltantes = listaBloqueTotales.get(0);

        for (GestorNovParamDet obj : listaBloque1) {
            if (obj.getIdGestorNovedadParam().getIdGestorNovedadParam().equals(ConstantsUtil.ID_OBZ_PARA_CONDUCIR)) {
                if (event.getColumn().getColumnKey().endsWith("dia1")) {
                    obj.setDia1(obj.getDia1() - newValue);
                    objFaltantes.setDia1(obj.getDia1() - objCantidadSercones.getDia1());
                }
                if (event.getColumn().getColumnKey().endsWith("dia2")) {
                    obj.setDia2(obj.getDia2() - newValue);
                    objFaltantes.setDia2(obj.getDia2() - objCantidadSercones.getDia2());
                }
                if (event.getColumn().getColumnKey().endsWith("dia3")) {
                    obj.setDia3(obj.getDia3() - newValue);
                    objFaltantes.setDia3(obj.getDia3() - objCantidadSercones.getDia3());
                }
                if (event.getColumn().getColumnKey().endsWith("dia4")) {
                    obj.setDia4(obj.getDia4() - newValue);
                    objFaltantes.setDia4(obj.getDia4() - objCantidadSercones.getDia4());
                }
                if (event.getColumn().getColumnKey().endsWith("dia5")) {
                    obj.setDia5(obj.getDia5() - newValue);
                    objFaltantes.setDia5(obj.getDia5() - objCantidadSercones.getDia5());
                }
                if (event.getColumn().getColumnKey().endsWith("dia6")) {
                    obj.setDia6(obj.getDia6() - newValue);
                    objFaltantes.setDia6(obj.getDia6() - objCantidadSercones.getDia6());
                }
                if (event.getColumn().getColumnKey().endsWith("dia7")) {
                    obj.setDia7(obj.getDia7() - newValue);
                    objFaltantes.setDia7(obj.getDia7() - objCantidadSercones.getDia7());
                }
            }
        }

        actualizarTransactional();

        MovilidadUtil.addSuccessMessage("Valor asignado éxitosamente");
    }

    public void generarReporte() throws FileNotFoundException {

        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("DEBE seleccionar una unidad funcional");
            return;
        }

        if (gestorNovedad.getLiquidado() == 0) {
            MovilidadUtil.addErrorMessage("DEBE tener la semana LIQUIDADA");
            return;
        }

        List<GestorNovedadFecha> lstFechas = obtenerFechasSemana(fecha);

        Date desde = lstFechas.get(0).getFecha();
        Date hasta = lstFechas.get(lstFechas.size() - 1).getFecha();

        List<Novedad> lstNovedades = gestorNovedadEjb.obtenerNovedadesFreeway(gestorNovedad.getFechaLiquidado(), desde, hasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (lstNovedades == null || lstNovedades.isEmpty()) {
            MovilidadUtil.addErrorMessage("NO se encontraron novedades para el periodo seleccionado");
            return;
        }

        file = null;
        Map parametros = new HashMap();
        String plantilla = "/rigel/reportes/";
        String destino = "/tmp/";
        plantilla = plantilla + "Novedad Conductor.xlsx";
        parametros.put("novedades", lstNovedades);
        destino = destino + "Novedad Conductor.xlsx";

        GeneraXlsx.generar(plantilla, destino, parametros);
        File excel = new File(destino);
        InputStream stream = new FileInputStream(excel);
        file = new DefaultStreamedContent(stream, "text/plain", "NOVEDADES_FREEWAY.xlsx");
    }

    public boolean validarFilaEditable(GestorNovParamDet paramDet) {
//        if (paramDet != null) {
//            if (paramDet.getIdGestorNovedadParam().getIdGestorNovedadParam().equals(ConstantsUtil.ID_TABLAS_PROGRAMADAS)) {
//                return false;
//            }
//        }

        return true;
    }

    /**
     * Método que se encarga de devolver el tipo de dia en base a una fecha
     *
     * @param fecha
     * @return String
     */
    private String asignarCssFestivo(Date fecha) {

        ParamFeriado paramFeriado = paramFeriadoEjb.findByFecha(fecha);

        if (paramFeriado != null) {
            return "cssWhite fondoFestivo";
        }

        return null;
    }

    private List<GestorNovedadFecha> obtenerFechasSemana(Date fechaSeleccionada) {
        List<GestorNovedadFecha> lstFechas = new ArrayList<>();
        Locale locale = new Locale("es", "CO");
        Calendar current = Calendar.getInstance(locale);
        current.setTime(fechaSeleccionada);
        current.add(Calendar.DATE, 7);

        int c = 0;

        current.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        current.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        while (c <= 6) {

            GestorNovedadFecha obj = new GestorNovedadFecha();
            obj.setFecha(current.getTime());
            obj.setClase(asignarCssFestivo(current.getTime()));
            lstFechas.add(obj);
            current.add(Calendar.DATE, 1);
            c++;
        }

        return lstFechas;
    }

    private Map<String, GestorNovDet> obtenerDetallesAfectaGestor(List<GestorNovedadFecha> lstFechas) {
        Map<String, GestorNovDet> map = null;

        List<NovedadTipoDetalles> detalles = novedadTipoDetallesEjb.obtenerDetallesAfectaGestor();

        if (detalles != null) {
            map = new HashMap<>();

            Date desde = lstFechas.get(0).getFecha();
            Date hasta = lstFechas.get(lstFechas.size() - 1).getFecha();

            GestorNovedad gestion = generarGestionNovedad(desde, hasta);

            for (NovedadTipoDetalles item : detalles) {
                GestorNovDet det = new GestorNovDet();
                det.setIdGestorNovedad(gestion);
                det.setIdNovedadTipoDetalle(item);
                det.setValor(0);
                det.setUsername(user.getUsername());
                det.setEstadoReg(0);
                det.setCreado(MovilidadUtil.fechaCompletaHoy());

                map.put(item.getIdNovedadTipoDetalle() + "_" + Util.dateFormat(desde) + "_" + Util.dateFormat(hasta), det);

                if (gestion.getGestorNovDetList() == null) {
                    gestion.setGestorNovDetList(new ArrayList<>());
                }

                gestion.getGestorNovDetList().add(det);

            }
            lstGestorNovedad.add(gestion);

        }

        return map;
    }

    private void obtenerItemsPorBloque(List<GestorNovParamDet> lista) {
        listaBloque1 = new ArrayList<>();
        listaBloque4 = new ArrayList<>();
        listaBloqueTotales = new ArrayList<>();
        for (GestorNovParamDet gestorNovParamDet : lista) {
            if (gestorNovParamDet.getIdGestorNovedadParam().getIdGestorNovedadParam().equals(ConstantsUtil.ID_PLANTA_OBZ_ACTIVOS)
                    || gestorNovParamDet.getIdGestorNovedadParam().getIdGestorNovedadParam().equals(ConstantsUtil.ID_OBZ_PARA_CONDUCIR)) {
                listaBloque1.add(gestorNovParamDet);
            } else if (gestorNovParamDet.getIdGestorNovedadParam().getIdGestorNovedadParam().equals(ConstantsUtil.ID_FALTANTE)) {
                listaBloqueTotales.add(gestorNovParamDet);
            } else {
                listaBloque4.add(gestorNovParamDet);
            }
        }

    }

    private List<GestorNovReqDet> obtenerDetallesRequerimientos() {
        List<GestorNovReqDet> lstDetallesReq = null;

        List<GestorNovRequerimiento> lstRequerimientos = gestorNovedadRequerimientoEjb.findAllByEstadoReg();

        if (lstRequerimientos != null) {
            lstDetallesReq = new ArrayList<>();

            for (GestorNovRequerimiento req : lstRequerimientos) {

                GestorNovReqDet reqDet = new GestorNovReqDet();
                reqDet.setIdGestorNovRequerimiento(req);
                reqDet.setValor(0);
                reqDet.setUsername(user.getUsername());
                reqDet.setEstadoReg(0);
                reqDet.setCreado(MovilidadUtil.fechaCompletaHoy());

                lstDetallesReq.add(reqDet);
            }

        }

        return lstDetallesReq;
    }

    private GestorNovedad generarGestionNovedad(Date desde, Date hasta) {
        GestorNovedad gestion = new GestorNovedad();
        gestion.setDesde(desde);
        gestion.setHasta(hasta);
        gestion.setUsername(user.getUsername());
        gestion.setEstadoReg(0);
        gestion.setCreado(MovilidadUtil.fechaCompletaHoy());

        return gestion;
    }

    /**
     * Método que se encarga de devolver los items correspondientes al primer y
     * cuarto bloque (Resumen y campos que se diligencian manualmente)
     *
     * @return listaBloque1 de objeto GestorNovParamDet
     */
    private List<GestorNovParamDet> obtenerNovParamDet() {
        List<GestorNovParamDet> lstFinal = new ArrayList<>();
        Long totalEmpleados = empleadoEjb.obtenerCantidadOperadoresByUfAndCargo(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), i_EmpleadoCargo, true);
        List<GestorNovedadParam> parametros = gestorNovedadParamEjb.findAll();

        for (GestorNovedadParam parametro : parametros) {
            GestorNovParamDet item = new GestorNovParamDet();
            item.setIdGestorNovedadParam(parametro);

            if (parametro.getIdGestorNovedadParam().equals(ConstantsUtil.ID_PLANTA_OBZ_ACTIVOS)
                    || parametro.getIdGestorNovedadParam().equals(ConstantsUtil.ID_OBZ_PARA_CONDUCIR)) {
                item.setDia1(totalEmpleados.intValue());
                item.setDia2(totalEmpleados.intValue());
                item.setDia3(totalEmpleados.intValue());
                item.setDia4(totalEmpleados.intValue());
                item.setDia5(totalEmpleados.intValue());
                item.setDia6(totalEmpleados.intValue());
                item.setDia7(totalEmpleados.intValue());
            } else {
                item.setDia1(0);
                item.setDia2(0);
                item.setDia3(0);
                item.setDia4(0);
                item.setDia5(0);
                item.setDia6(0);
                item.setDia7(0);
            }

            lstFinal.add(item);

        }

        return lstFinal;
    }

    private void notificar(GestorNovedad gestionNovedad) throws IOException {
        List<String> adjuntos = null;
        Map mapa = getMailParams();
        Map mailProperties = new HashMap();
        mailProperties.put("logo", SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_LOGO));
        mailProperties.put("desde", Util.dateFormat(gestionNovedad.getDesde()));
        mailProperties.put("hasta", Util.dateFormat(gestionNovedad.getHasta()));

        NotificacionProcesos proceso = notificacionProcesosEjb.findByCodigo(Util.PROCESO_GESTION_NOVEDAD);

        if (proceso == null) {
            MovilidadUtil.addErrorMessage("NO se encontró lista de distribución GESNOV, Debe agregar la lista de distribución para el envío de correo");
            return;
        }

        String subject = proceso.getMensaje() + " (" + Util.dateFormat(gestionNovedad.getDesde()) + " - " + Util.dateFormat(gestionNovedad.getHasta()) + " )";
        String destinatarios = proceso.getEmails();

        SendMails.sendEmail(mapa, mailProperties, subject,
                "",
                destinatarios,
                "Notificaciones RIGEL", adjuntos != null ? adjuntos : null);
    }

    private Map getMailParams() {
        NotificacionCorreoConf conf = NCCEJB.find(Util.ID_NOTIFICACION_CONF);
        NotificacionTemplate template = notificacionTemplateEjb.findByTemplate(Util.ID_TEMPLATE_CERRADO_SEMANA);
        Map mapa = new HashMap();
        mapa.put("host", conf.getSmtpServer());
        mapa.put("mailBcc", conf.getCc1());
        mapa.put("from", conf.getEmail());
        mapa.put("port", conf.getPuerto().toString());
        mapa.put("password", conf.getPassword());
        mapa.put("template", template.getPath());
        return mapa;
    }

    private void consultarDb() {

        List<GestorNovedadFecha> lstFechas = obtenerFechasSemana(fecha);

        Date desde = lstFechas.get(0).getFecha();
        Date hasta = lstFechas.get(lstFechas.size() - 1).getFecha();

        gestorNovedad = gestorNovedadEjb.findAllByRangoFechasAndUnidadFuncional(desde, hasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    /**
     * Valida si el usuario logueado corresponde al área de programación
     *
     * @return true si el usuario tiene rol ROLE_PROFPRG
     */
    private boolean validarRolPrg() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().contains("PRG")) {
                return true;
            }
        }
        return false;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public GestorNovedad getGestorNovedad() {
        return gestorNovedad;
    }

    public void setGestorNovedad(GestorNovedad gestorNovedad) {
        this.gestorNovedad = gestorNovedad;
    }

    public boolean isFlagNoExistenRegistros() {
        return flagNoExistenRegistros;
    }

    public void setFlagNoExistenRegistros(boolean flagNoExistenRegistros) {
        this.flagNoExistenRegistros = flagNoExistenRegistros;
    }

    public List<String> getLstDiasSemana() {
        return lstDiasSemana;
    }

    public void setLstDiasSemana(List<String> lstDiasSemana) {
        this.lstDiasSemana = lstDiasSemana;
    }

    public List<GestorNovedadFecha> getLstFechasSemana() {
        return lstFechasSemana;
    }

    public void setLstFechasSemana(List<GestorNovedadFecha> lstFechasSemana) {
        this.lstFechasSemana = lstFechasSemana;
    }

    public List<GestorNovDetSemana> getLstGestorNovDetSemana() {
        return lstGestorNovDetSemana;
    }

    public void setLstGestorNovDetSemana(List<GestorNovDetSemana> lstGestorNovDetSemana) {
        this.lstGestorNovDetSemana = lstGestorNovDetSemana;
    }

    public List<GestorNovReqSemana> getLstGestorNovReqSemana() {
        return lstGestorNovReqSemana;
    }

    public void setLstGestorNovReqSemana(List<GestorNovReqSemana> lstGestorNovReqSemana) {
        this.lstGestorNovReqSemana = lstGestorNovReqSemana;
    }

    public List<GestorNovParamDet> getListaBloque1() {
        return listaBloque1;
    }

    public void setListaBloque1(List<GestorNovParamDet> listaBloque1) {
        this.listaBloque1 = listaBloque1;
    }

    public List<GestorNovParamDet> getListaBloque4() {
        return listaBloque4;
    }

    public void setListaBloque4(List<GestorNovParamDet> listaBloque4) {
        this.listaBloque4 = listaBloque4;
    }

    public List<GestorNovParamDet> getListaBloqueTotales() {
        return listaBloqueTotales;
    }

    public void setListaBloqueTotales(List<GestorNovParamDet> listaBloqueTotales) {
        this.listaBloqueTotales = listaBloqueTotales;
    }

    public List<EmpleadoTipoCargo> getLstTipoCargos() {
        return lstTipoCargos;
    }

    public void setLstTipoCargos(List<EmpleadoTipoCargo> lstTipoCargos) {
        this.lstTipoCargos = lstTipoCargos;
    }

    public Integer getI_EmpleadoCargo() {
        return i_EmpleadoCargo;
    }

    public void setI_EmpleadoCargo(Integer i_EmpleadoCargo) {
        this.i_EmpleadoCargo = i_EmpleadoCargo;
    }

    public boolean isFlagModificacionRegistros() {
        return flagModificacionRegistros;
    }

    public void setFlagModificacionRegistros(boolean flagModificacionRegistros) {
        this.flagModificacionRegistros = flagModificacionRegistros;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

}
