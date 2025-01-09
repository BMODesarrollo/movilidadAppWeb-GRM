/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.utils;

import java.io.Serializable;

/**
 *
 * @author solucionesit
 */
public class ConstantsUtil implements Serializable {

    public static final String YA_EXISTE_IDENTIFICACION = "Ya existe registro con esta Identificación";
    public static final String YA_ESXITE_EMAIL_PERSONAL = "Ya existe registro con este Email Personal";
    public static final String YA_EXISTE_EMAIL_EMPRESARIAL = "Ya existe registro con este Email Empresarial";
    public static final String YA_EXISTE_CODIGO_TM = "Ya existe registro con este Código Trasnmilenio";
    public static final String YA_EXISTE_CODIGO_INTERNO = "Ya existe registro con este Código Interno";

    public static final Integer ID_TIPO_NOVEDAD = 11;
    public static final Integer ID_VEHICULO_TIPO_CARROCERIA = 1;
    public static final Integer ID_NOVEDAD_TIPO_DOCUMENTO_INCAPACIDAD = 7;
    public static final Integer ID_TIPO_NOVEDAD_ACC = 2;
    public static final Integer ID_TPC_MASTER = 30;
    public static final Integer ID_TPC_BIART = 1;
    public static final Integer ID_TPC_ART = 58;
    public static final Integer OPC_VEHICULO = 1;
    public static final Integer OPC_ASIG_OPERADOR = 2;
    public static final Integer OPC_ELIMINAR = 3;
    public static final int ID_NOTIFICACION_CONF = 1;
    public static final String EMPlDOC = "empldoc";
    public static final String EMPlCONSULTA = "empllconsulta";
    public static final String EMPLSELECTNOV = "emplselectnov";
    public static final String ROLE_TC = "ROLE_TC";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String TEMPLATE_REINCIDENCIA_ACC = "templateAccReincidencia";
    public static final String TEMPLATE_REINCIDENCIA_NOVEDAD_DANO = "templateDanoFlotaReincidencia";
    public static final String TEMPLATE_NOVEDAD_PM_OP = "templateNovedades";
    public static final String TEMPLATE_NOVEDADES_MTTO = "templateNovedadMtto";
    public static final String TEMPLATE_REPUESTOS_MTTO = "templateRepuestosMtto";
    public static final String TEMPLATE_CIERRE_TURNO = "templateCierreTurno";
    public static final String TEMPLATE_NOTIFICACION_PROCESO_DISCIPLINARIO = "templateNotificacionProcesoDisciplinario";
    public static final String TEMPLATE_NOTIFICACION_PD_AGENDA = "templateNotificacionPdAgenda";
    public static final String TEMPLATE_NOTIFICACION_PQR = "templateNotificacionPqr";
    public static final String TEMPLATE_NOTIFICACION_RECAP = "templateNotificacionRecapacitacion";
    public static final String TEMPLATE_NOTIFICACION_PQR_INSUMO = "templateNotificacionPqrInsumo";
    public static final String TEMPLATE_NOTIFICACION_AJUSTE_JORNADA = "templateNotificacionAjusteJornada";
    public static final String TEMPLATE_NOTIFICACION_INFRACCION = "templateNotificacionInfraccion";
    public static final String TEMPLATE_NOTIFICACION_ACCIDENTE_AUDIENCIA = "templateNotificacionAccidenteAudiencia";
    public static final String TEMPLATE_NOTIFICACION_ACC_AUD_CONCILIACION = "templateNotificacionAccAudConciliacion";
    public static final int TEMPLATENOVOPERACION = 5;
    public static final int TEMPLATEGENERICAJORNADA = 7;
    public static final String CODE_NOTI_PROCESO_REPUESTOS = "CODE_NOTI_PROCESO_REPUESTOS";
    public static final String CODE_NOTI_PROC_CIERRE_TURNO = "CODE_NOTI_PROC_CIERRE_TURNO";

    public static final String PATIO_DEFAULT = "PATIO_DEFAULT";
    public static final String ID_TAREA_DISPO_PATIO = "ID_TAREA_DISPO_PATIO";
    public static final String ID_TAREA_DISPO_CONTINGENCIA = "ID_TAREA_DISPO_CONTINGENCIA";
    public static final String ID_CARGOS_PANEL_PRINCIPAL = "ID_CARGOS_PANEL_PRINCIPAL";
    public static final String ID_CARGOS_OPERADORES = "ID_CARGOS_OPERADORES";
    public static final String ID_CARGOS_OPERADORES_KACTUS_TM = "ID_CARGOS_OPERADORES_KACTUS_TM";

    // Parametros Gestor Novedad (gestor_novedad_param)
    public static final int ID_PLANTA_OBZ_ACTIVOS = 1;
    public static final int ID_OBZ_PARA_CONDUCIR = 2;
    public static final int ID_TABLAS_PLANEADAS = 3;
    public static final int ID_TABLAS_ELIMINAR = 4;
    public static final int ID_TABLAS_PROGRAMADAS = 5;
    public static final int ID_ICB = 6;
    public static final int ID_CANTIDAD_SERCONES = 7;
    public static final int ID_FALTANTE = 8;

    //Tipologia de buses
    public static final int VEHICULO_BI = 2;
    public static final int VEHICULO_ART = 1;

    //Tipos de vacios
    public static final int VACIO = 4;
    public static final int VACIO_COMERCIAL = 3;
    public static final int VACIO_NO_PAGO = 2;

    public static final int COMP_DIRECCION = 1;
    public static final int COMP_FRENOS = 2;
    public static final int COMP_MOTOR = 3;
    public static final int COMP_INTERNA = 4;
    public static final int COMP_ELEC_INTERNA = 5;
//    public static final int COMP_ELEC_EXTERNA = 6;
    public static final int COMP_COMUNICACION = 7;
    public static final int COMP_RUTEROS = 8;
    public static final int COMP_PUERTAS = 9;
    public static final int COMP_CAR_EX = 10;
    public static final int COMP_CAR_INT = 11;

    //Estados Novedades de Mtto
    public static final int NOV_ESTADO_ABIERTO = 1;
    public static final int NOV_ESTADO_CERRADO = 2;
    public static final int NOV_ESTADO_EN_EJECUCION = 3;
    public static final int NOV_ESTADO_PENDIENTE = 4;
    public static final int NOV_ESTADO_DIFERIR = 5;

    public static final int MTTO_AFECTA_DISPONIBILIDAD = 1;
    public static final String SAVE_DONE = "Registro finalizado con exito.";
    public static final String UPDATE_DONDE = "Actualización finalizada con exito.";
    public static final String SEND_EMAIL_DONE = "Notificación exitosa";
    public static final String ERROR_SAVE = "Error al guardar.";
    public static final String NO_EXISTE_VEHICULO = "No existe Vehículo con el codigo indicado.";
    public static final String INOPERATIVO_VEHICULO = "Vehículo Inoperativo";

    public static final String PERMITIR_VALIDACION_DOC_VEH = "PERMITIR_VALIDACION_DOC_VEH"; //1 para si, 0 para no

    public static final int VHCL_TIPO_ESTADO_OPERATIVO = 1;
    public static final String MDL_NOVEDAD_OPERACION = "NOV_OP";
    public static final String MDL_NOVEDAD_PANEL_PRINCIPAL = "NOV_PP";
    public static final String MDL_NOVEDAD_CLASIFICA = "NOV_CLASIFICA";
    public static final String MDL_HISTORICO_NOV = "MDL_HISTORICO_NOV";
    public static final String NOV_MTTO_INPUT_CAUSA_ENTRADA = "NOV_MTTO_INPUT_CAUSA_ENTRADA";
    public static final String NOV_MTTO_INPUT_OPERADOR = "NOV_MTTO_INPUT_OPERADOR";

    public static final String ID_LOGO = "ID_LOGO";
    public static final String ID_GIF = "ID_GIF";
    public static final String ID_NOMBRE_EMPRESA = "ID_NOMBRE_EMPRESA";
    public static final String ID_CLAVE_EMPRESA = "ID_CLAVE_EMPRESA";
    public static final String ID_USER_EMPRESA_WS = "ID_USER_EMPRESA_WS";
    public static final String ID_NOVEDAD_SEGUIMIENTO_TAMANO = "ID_NOVEDAD_SEGUIMIENTO_TAMANO";
    public static final String ID_NOVEDAD_MTTO_ARCHIVO_TAMANO = "ID_NOVEDAD_MTTO_ARCHIVO_TAMANO";
    public static final String ID_NOVEDAD_INFRA_SEG_TAMANO = "ID_NOVEDAD_INFRA_SEG_TAMANO";
    public static final String ID_NOVEDAD_INFRA_DOC_TAMANO = "ID_NOVEDAD_INFRA_DOC_TAMANO";
    public static final String ID_SST_EMPRESA_TAMANO = "ID_SST_EMPRESA_TAMANO";
    public static final String ID_SST_EMPRESA_VISITANTE_TAMANO = "ID_SST_EMPRESA_VISITANTE_TAMANO";
    public static final String ID_CONCIFLOTA = "ID_CONCIFLOTA";
    public static final String KEY_ID_NOV_MTTO = "KEY_ID_NOV_MTTO";
    public static final String KEY_ID_NOV_AUSENTISMO = "KEY_ID_NOV_AUSENTISMO";
    public static final String KEY_REPORTED_PRIORITY_MAXIMO = "KEY_REPORTED_PRIORITY_MAXIMO";
    public static final String KEY_REPORTED_BY_NAME_MAXIMO = "KEY_REPORTED_BY_NAME_MAXIMO";
    public static final String KEY_URL_SR_MAXIMO = "KEY_URL_SR_MAXIMO";
    public static final String KEY_TOKEN_SR_MAXIMO = "KEY_TOKEN_SR_MAXIMO";
    public static final String KEY_USER_SR_MAXIMO = "KEY_USER_SR_MAXIMO";
    public static final String KEY_IDS_DETS_AUSENTISMO = "KEY_IDS_DETS_AUSENTISMO";
    public static final String KEY_ID_VACIO = "KEY_ID_VACIO";
    public static final String NOTIFICACION_PROCESO_MTTO = "NOTIFICACION_PROCESO_MTTO";
    public static final String NOTIFICACION_PROCESO_ITS_SIRCI = "NOTIFICACION_PROCESO_ITS_SIRCI";
    public static final String KEY_CLASIFIC_FROM_HISTORICO = "KEY_CLASIFIC_FROM_HISTORICO";

    // CAMPOS REQUERIDOS PARA ACTIVOS MAXIMO
    public static final String KEY_URL_ACTIVOS_MX = "KEY_URL_ACTIVOS_MX";
    public static final String KEY_USR_ACTIVOS_MX = "KEY_USR_ACTIVOS_MX";
    public static final String KEY_TOKEN_ACTIVOS_MX = "KEY_TOKEN_ACTIVOS_MX";
    public static final String KEY_COLOR_VEHICULO_ACTIVOS_MX = "Verde";

    // novedad detalle parametrizada para myApp
    public static final String COD_NOVEDAD_CAMBIO_VEH = "PRCHV";

    // minutos utilizados como holgura para la busqueda de la tarea del operador
    public static final String HOLGURA_MINUTOS_TAREA_OPERADOR = "00:01:00";

    public static final String KEY_DIR_NOVEDAD_DOCS = "novedadDocs.dir";

    public static final String ROLE_ACC_ABOGADO = "ROLE_ACC_ABO";
    public static final String ROLE_VIEW_PARRILLA_MAPA = "ROLE_VIEW_PARRILLA_MAPA";
    public static final String ROLE_PROF_NOV_JORNADA = "ROLE_PROF_NOV_JORNADA";
    public static final String ROLE_DISP_FLOTA = "ROLE_DISP_FLOTA";

    // Estados de conciliación de clota
    public static final int CON_FLOTA_NO_PENDIENTE = 0;
    public static final int CON_FLOTA_APROBADO = 1;
    public static final int CON_FLOTA_NO_APROBADO = 2;

    public static final String TAB_ASISTENCIA_VEHICULO = "asistencia_veh_tab";
    public static final String TAB_ASISTENCIA_OPERADOR = "asistencia_tab";
    public static final String TAB_DOC_VENCIDOS = "docs_tab";
    public static final String TAB_VEHICULOS_DETENIDOS = "vehiculos_detenidos_tab";

    public static final String URL_POST_SEND_ATV = "URL_POST_SEND_ATV";

    // LAVADO GM
    public static final Integer LAVADO_ABIERTO = 0;
    public static final Integer LAVADO_CERRADO = 1;
    public static final int LAVADO_APROBADO = 0;
    public static final int LAVADO_NO_APROBADO = 1;

    public static final String URL_MAP_GEO_VEH = "URL_MAP_GEO_VEH";
    public static final String URL_MAP_GEO_RECO_VEH_ESP = "URL_MAP_GEO_RECO_VEH_ESP";
    public static final String URL_MAP_GEO_VEH_DETENIDOS = "URL_MAP_GEO_VEH_DETENIDOS";
    public static final String URL_MAP_GEO_VEH_PATIO = "URL_MAP_GEO_VEH_PATIO";
    public static final String URL_MAP_GEO_VEH_ALL = "URL_MAP_GEO_VEH_ALL";
    public static final String URL_NOTIF_TELEGRAM_ACC = "URL_NOTIF_TELEGRAM_ACC";
    public static final String URL_NOTIF_TELEGRAM_ATV = "URL_NOTIF_TELEGRAM_ATV";
    public static final String URL_NOTIF_TELEGRAM_AFECTA_DISPO = "URL_NOTIF_TELEGRAM_AFECTA_DISPO";
    public static final String ID_URL_GEO_BATERIA_ALL_VEHICULO = "ID_URL_GEO_BATERIA_ALL_VEHICULO";

    // ENVIO DE NOVEDADES DE AUSENTISMO A KACTUS
    public static final String URL_NOVEDAD_AUSENTISMO_KACTUS = "URL_NOVEDAD_AUSENTISMO_KACTUS";
    public static final String USR_NOVEDAD_AUSENTISMO_KACTUS = "ID_USER_EMPRESA_WS";
    public static final String PWD_NOVEDAD_AUSENTISMO_KACTUS = "ID_CLAVE_EMPRESA";
    public static final int CLASIFICACION_AUSENTISMO = 1;
    public static final int CLASIFICACION_INCAPACIDAD = 2;

    // NOVEDAD ATV
    public static final String PATH_DOCUMENTO_NOVEDAD_ATV = "/rigel/atv/documento/";
    public static final String KEY_DIR_NOVEDAD_ATV = "novedadAtv.dir";

    //
    public static final String AFECTAR_JORNADA_FROM_PRG_TC = "AFECTAR_JORNADA_FROM_PRG_TC";
    public static final String RESPETAR_MAX_HORAS_EXTRAS = "RESPETAR_MAX_HORAS_EXTRAS";
    public static final String MAX_HRS_EXTRAS_DIA = "MAX_HRS_EXTRAS_DIA";
    public static final String MAX_HRS_EXTRAS_SMNAL = "MAX_HRS_EXTRAS_SMNAL";
    public static final String TOTAL_HRS_LABORALES = "TOTAL_HRS_LABORALES";
    public static final String INI_DIURNA = "INI_DIURNA";
    public static final String INI_NOCTURNA = "INI_NOCTURNA";
    public static final String FIN_NOCTURNA = "FIN_NOCTURNA";
    public static final String CRITERIO_MIN_HORA_EXTRA = "CRITERIO_MIN_HORA_EXTRA";
    public static final String KEY_HORAS_DESCANSO = "KEY_HORAS_DESCANSO";
    public static final String MAX_HRS_LABORALES_SMNAL = "MAX_HRS_LABORALES_SMNAL";
    public static final String MAX_HRS_LABORALES_DIA = "MAX_HRS_LABORALES_DIA";
    public static final String MIN_HRS_LABORALES_DIA = "MIN_HRS_LABORALES_DIA";
    public static final String TIPO_LIQUIDACION = "TIPO_LIQUIDACION";

    // NOMINA SERVER PARAM
    public static final int ID_NOMINA_SERVER_PARAM = 1;

    public static final String ON_STRING = "1";
    public static final String OFF_STRING = "0";
    public static final int ID_GOP_ALERTA_TIEMPO_DETENIDO = 1;
    public static final int ON_INT = 1;
    public static final int OFF_INT = 0;

    // SERVICIOS DE GEO
    public static final String URL_SERVICE_LOCATION_VEHICLE = "URL_SERVICE_LOCATION_VEHICLE";

    // PRG ASIGNACION
    public static final String HORA_FINAL_DIA = "23:59:59";

    // PLANTILLA  REPORTE DE ACCIDENTALIDAD GM
    public static final String KEY_PLANTILLA_ACC_GM = "accidenteGM.dir";

    // METROS DE AUTONOMIA PARA LA BATERIA AL 100%
    public static final String ID_AUTONOMIA_METROS_BATERIA_100 = "ID_AUTONOMIA_METROS_BATERIA_100";

    public static final String ID_OP_TIPO_UNO = "ID_OP_TIPO_UNO";
    public static final String ID_OP_TIPO_DOS = "ID_OP_TIPO_DOS";
    public static final Integer ID_V_TIPO_UNO = 1;
    public static final Integer ID_V_TIPO_DOS = 2;
    public static final String ID_VACCOMS = "ID_VACCOMS";
    public static final String ID_VAC = "ID_VAC";
    public static final String ID_VAC_PRG = "ID_VAC_PRG";
    public static final String ID_TAREAS = "ID_TAREAS";

    // estados de los serviciios
    public static final Integer CODE_ADICIONAL_3 = 3;
    public static final Integer CODE_ADICIONAL_PARCIAL_4 = 4;
    public static final Integer CODE_ELIMINADO_SERVICIO_5 = 5;
    public static final Integer CODE_VACCOM_6 = 6;
    public static final Integer CODE_VAC_7 = 7;
    public static final Integer CODE_ELIMINADO_SERVICIO_8 = 8;
    public static final Integer CODE_ELIMINADO_SERVICIO_99 = 99;
    public static final Integer CODE_ELIMINADO_DISP_9 = 9;

    // ESTADO DE REGISTRO
    public static final Integer CODE_ESTADO_REG_ACTIVO = 0;
    public static final Integer CODE_ESTADO_REG_NO_ACTIVO = 1;

    //PARÁMETROS PARA BUSQUEDA POR FILTROS DE FECHAS ( AUTORIZACIÓN NÓMINA )
    public static final Integer PARAM_DIAS_FILTRO = 14;

    // CARGA DE HALLAZGOS
    public static final String ID_AREA_LAVADO = "LAVADO";
    public static final String CODIGOS_INFRACCION_LAVADO = "I5006,I5013-1";

    // CERTIFICADO CAPACITACION (REPORTE PLANTA OBZ)
    public static final String ID_CERTIFICADO_CAPACITACION = "ID_CERTIFICADO_CAPACITACION";

    // CAMBIO DE TURNO
    public static final String ID_DETALLE_CAMBIO_TURNO = "ID_DETALLE_CAMBIO_TURNO";

    // CODIGO ERROR ENVÍO NÓMINA A KACTUS
    public static final Integer CODIGO_ERROR_RESPUESTA_NOVEDAD_KACTUS = -1;

    // TIPO TAREAS RELACIONES LABORALES 
    public static final String ID_TAREAS_RELACIONES_LABORALES = "ID_TAREAS_RELACIONES_LABORALES";

    // TIPO TAREAS RECAPACITACIÓN
    public static final String ID_TAREA_RECAPACITACION_ZMOIII = "ID_TAREA_RECAPACITACION_ZMOIII";

    // TIPO TAREAS RECAPACITACIÓN
    public static final String ID_TAREA_RECAPACITACION_ZMOV = "ID_TAREA_RECAPACITACION_ZMOV";
}
