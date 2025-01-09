/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.empleados.gm.Datos;
import com.empleados.gm.EbEmpleadoWS;
import com.empleados.gm.MBWebService;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.EmpleadoTipoCargoFacadeLocal;
import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.model.ConfigEmpresa;
import com.movilidad.model.Empleado;
import com.movilidad.model.EmpleadoDepartamento;
import com.movilidad.model.EmpleadoEstado;
import com.movilidad.model.EmpleadoMunicipio;
import com.movilidad.model.EmpleadoTipoCargo;
import com.movilidad.model.EmpleadoTipoIdentificacion;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.OperacionPatios;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author cesar
 */
@Named(value = "prgEmpKactusGMJSF")
@ViewScoped
public class PrgEmpleadoKactusGMJSF implements Serializable {

    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    private EmpleadoTipoCargoFacadeLocal tipoCargoFacadeLocal;
    @EJB
    private ConfigEmpresaFacadeLocal empresaFacadeLocal;
    @EJB
    private GopUnidadFuncionalFacadeLocal gopUnidadFuncionalLocal;

    private HashMap<String, Empleado> mapEmpleado; //cedula key
    private HashMap<String, EmpleadoTipoCargo> mapEmpleadoTipoCargo; //idSoftwareCargo key
    private HashMap<String, EmpleadoTipoCargo> mapEmpleadoTipoCargoNombre; //nombreCargo key
    private HashMap<String, Integer> mapUnidadFuncional; // Nombre UF, id Unidad Funcional
    private HashMap<String, EbEmpleadoWS> mapCargoEmpWs; // codCargo, empleadoWs
    private HashMap<String, EbEmpleadoWS> mapEmpleadoWS;
    private List<Empleado> lstEmpleado;
    private List<EmpleadoTipoCargo> lstTipoCargo, listTipoCargoUpd;
    private List<EbEmpleadoWS> colaboradores;
    private Supplier<Stream<EbEmpleadoWS>> spEmpleadosWs = null;
    private String codigoCargoForCodigoTm;
    private String[] codigosForCodeTm;

    private Datos datos;
    private MBWebService port;
    private boolean flag;
    //
    private GeoApiContext context;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public PrgEmpleadoKactusGMJSF() {
    }

    @PostConstruct
    public void init() {
        mapEmpleado = new HashMap<>();
        mapEmpleadoTipoCargo = new HashMap<>();
        mapCargoEmpWs = new HashMap<>();
        mapEmpleadoTipoCargoNombre = new HashMap<>();
        mapEmpleadoWS = new HashMap<>();
        mapUnidadFuncional = new HashMap<>();
        lstTipoCargo = new ArrayList<>();
        listTipoCargoUpd = new ArrayList<>();
        lstEmpleado = new ArrayList<>();
        flag = false;
        ConfigEmpresa keyGoogle = empresaFacadeLocal.findByLlave(Util.CODE_API_KEY_GOOGLE);
        if (keyGoogle != null) {
            context = new GeoApiContext.Builder()
                    .apiKey(keyGoogle.getValor())
                    .build();
        }
        codigoCargoForCodigoTm = empresaFacadeLocal.findByLlave(ConstantsUtil.ID_CARGOS_OPERADORES_KACTUS_TM).getValor();
        cargarArrayCargosTm();
    }

    public void consumeEmployeeKactus() {
        if (cargarColaboradores()) {
            return;
        }
        actualizarCargos();
        addIdSoftCargos();
        cargarEmpleadoCargos();
        cargarCargosEmpleadoWs();
        cargarEmpleados();
        cargarEmpleadoWs();
        cargarUnidadFuncional();
        lstTipoCargo.clear();
        listTipoCargoUpd.clear();
        lstEmpleado.clear();
        // empleados que vengan en el servicio y no se encuentren registrados en RIGEL Y cuenten con empresa en el servicio
        Predicate<EbEmpleadoWS> pdcEmpWs = empWs -> !Util.isStringNullOrEmpty(empWs.getNomEmpr())
                && empWs.getIndActi().equalsIgnoreCase("A")
                && (!mapEmpleado.containsKey(empWs.getCodEmpl()));
        spEmpleadosWs.get().filter(pdcEmpWs).map(empWs -> {
            return empWs.getCodEmpl().trim();
        }).distinct().forEachOrdered(empWsCod -> {
            lstEmpleado.add(contruirEmpleadoByEmpleadoWs(new Empleado(), mapEmpleadoWS.get(empWsCod)));
        });
//        if (colaboradores != null && colaboradores.size() > 0) {
//            lstEmpleado.clear();
//            for (EbEmpleadoWS e : colaboradores) {
//                if (mapEmpleado.get(e.getCodEmpl()) == null) {
//                    if (!Util.isStringNullOrEmpty(e.getNomEmpr())) {
//                        lstEmpleado.add(setEmpleado(new Empleado(), e));
//                    }
//                }
//            }
//        }
        if (lstEmpleado.isEmpty()) {
            MovilidadUtil.addSuccessMessage("No existen empleados nuevos en Kactus");
            return;
        }
        flag = true;
    }

    private void localizacionEmpleado(Empleado e) {
        try {
            if (Util.isStringNullOrEmpty(e.getDireccion())) {
                System.out.println("DIRECCION EMPLEADO ES NULA");
                return;
            }
            if (e.getDireccion().equals("SinDefinir")) {
                System.out.println("DIRECCION EMPLEADO ES SIN DEFINIR");
                return;
            }
            String direccionForGoole = e.getDireccion();
            if (e.getIdEmpleadoDepartamento() != null && e.getIdEmpleadoMunicipio() != null) {
                direccionForGoole = direccionForGoole
                        + ", "
                        + e.getIdEmpleadoMunicipio().getNombre()
                        + ", "
                        + e.getIdEmpleadoDepartamento().getNombre();
            }
            GeocodingResult[] resul = GeocodingApi
                    .newRequest(context)
                    .address(direccionForGoole)
                    .await();
            String localidad = null;
            String subLocalidad = null;
            for (GeocodingResult gr : resul) {
                e.setLatitud(String.valueOf(gr.geometry.location.lat));
                e.setLongitud(String.valueOf(gr.geometry.location.lng));
                //      System.out.println("Coordenadas: " + gr.geometry.location.toString());
                for (AddressComponent ac : gr.addressComponents) {

                    //       System.out.println("Localidad: " + ac.shortName);
                    for (AddressComponentType act : ac.types) {
                        //       System.out.println("Localidad Type: " + act.name());
                        if (act.name().equalsIgnoreCase(AddressComponentType.LOCALITY.name())) {
                            localidad = ac.longName;
                        }
                        if (act.name().equalsIgnoreCase(AddressComponentType.SUBLOCALITY.name())) {
                            subLocalidad = ac.longName;
                        }
                    }
                }
            }
            if (subLocalidad != null) {
                e.setLocalidad(subLocalidad);
            } else {
                e.setLocalidad(localidad);
            }
        } catch (ApiException | IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void actualizarEmpleados() {
        if (cargarColaboradores()) {
            return;
        }
        actualizarCargos();
        addIdSoftCargos();
        cargarEmpleados();
        cargarEmpleadoCargos();
        cargarCargosEmpleadoWs();
        List<Empleado> empleadosActualizar = new ArrayList<>();
        mapEmpleado.values().forEach(emp -> {
            EbEmpleadoWS eWS = mapEmpleadoWS.get(emp.getIdentificacion());
            if (eWS != null) {
                updateEmpleado(emp, eWS);
                empleadosActualizar.add(emp);
            }
        });

//        for (Empleado e : mapEmpleado.values()) {
//            EbEmpleadoWS eWS = mapCargoEmpWs.get(e.getIdentificacion());
//            if (eWS != null) {
//                updateEmpleado(e, eWS);
//                listEmpleadoRg.add(e);
//            }
//        }
        if (empleadosActualizar.isEmpty()) {
            MovilidadUtil.addErrorMessage("No existen empleados a actualizar");
            return;
        }
        applyUpdateEmp(empleadosActualizar);
        MovilidadUtil.addSuccessMessage("Empleados actualizados correctamente");
    }

    public void apply() {
//        if (!lstTipoCargo.isEmpty()) {
////            applyCargos();
//        }
//        addIdSoftCargos();
        applyEmployees();
    }

//    @Transactional
//    public void applyCargos() {
//        if (!lstTipoCargo.isEmpty()) {
//            for (EmpleadoTipoCargo c : lstTipoCargo) {
//                if (!mapEmpleadoTipoCargo.containsKey(c.getIdSoftwareNombreCargo() + "")) {
//                    tipoCargoFacadeLocal.create(c);
//                    setCargos();
//                }
//            }
//            MovilidadUtil.addSuccessMessage("Se crearon " + lstTipoCargo.size() + " cargos");
//        }
//        flag = false;
//    }
    @Transactional
    public void applyEmployees() {
        if (lstEmpleado.isEmpty()) {
            MovilidadUtil.addErrorMessage("No existen empleados para registrar");
            return;
        }
        lstEmpleado.forEach((e) -> {
            //            localizacionEmpleado(e);
            empleadoFacadeLocal.create(e);
        });
        MovilidadUtil.addSuccessMessage("Se crearon " + lstEmpleado.size() + " empleados");
        lstEmpleado.clear();
        flag = false;
    }

    public void addIdSoftCargos() {
        if (cargarColaboradores()) {
            return;
        }
        // cargar mapa de cargos del servicio de KACTUS
        cargarCargosEmpleadoWs();
        mapEmpleadoTipoCargoNombre.values().forEach(empCargo -> {
            if (mapCargoEmpWs.containsKey(empCargo.getNombreCargo())) {
                EbEmpleadoWS empWs = mapCargoEmpWs.get(empCargo.getNombreCargo());
                empCargo.setIdSoftwareNombreCargo(new Integer(Util.getNumerosDeString(empWs.getCodCargo())));
                tipoCargoFacadeLocal.edit(empCargo);
            }
        });
//        if (colaboradores != null && colaboradores.size() > 0) {
//            for (EbEmpleadoWS e : colaboradores) {
//                mapCargoEmpWs.put(e.getNomCargo().trim().toUpperCase(), e);
//            }
//            for (EmpleadoTipoCargo e : tipoCargoFacadeLocal.findAll()) {
//                EbEmpleadoWS get = mapEmpTipoCargo.get(e.getNombreCargo());
//                if (get != null) {
//                    e.setIdSoftwareNombreCargo(new Integer(Util.getNumerosDeString(get.getCodCargo())));
//                    tipoCargoFacadeLocal.edit(e);
//                }
//            }
//        }
        flag = false;
    }

    public void actualizarCargos() {
        // consumir servicio de empleado kactus
        if (cargarColaboradores()) {
            return;
        }
        cargarEmpleadoCargos();
        cargarCargosEmpleadoWs();
        // cargos que no sean nulos y esten registrados en RIGEL
        Predicate<EbEmpleadoWS> existCargoCodigo = empWs -> mapEmpleadoTipoCargo.containsKey(empWs.getCodCargo());
        Predicate<EbEmpleadoWS> existCargoNombre = empWs -> mapEmpleadoTipoCargoNombre.containsKey(empWs.getNomCargo().trim());
        Predicate<EbEmpleadoWS> cargoIsActivo = empWs -> empWs.getIndActi().equalsIgnoreCase("A");

        //actulizar los existentes en RIGEL
        spEmpleadosWs.get()
                .filter(existCargoCodigo)
                .map(empWs -> {
                    return empWs.getNomCargo().trim().toUpperCase();
                }).distinct()
                .map(nomCargo -> {
                    EbEmpleadoWS empWs = mapCargoEmpWs.get(nomCargo);
                    EmpleadoTipoCargo empTpCargo = mapEmpleadoTipoCargo.get(empWs.getCodCargo());
                    empTpCargo.setNombreCargo(nomCargo);
                    return empTpCargo;
                }).forEachOrdered(empTpCargo -> {
            tipoCargoFacadeLocal.edit(empTpCargo);
        });
        cargarEmpleadoCargos();
        // crear los que no existen en rigel
        spEmpleadosWs.get()
                .filter(existCargoCodigo.negate().and(existCargoNombre.negate()).and(cargoIsActivo))
                .map(empWs -> {
                    return empWs.getNomCargo().trim().toUpperCase();
//            return crearCargoByEmpleadoWs(empWs);
                }).distinct().forEachOrdered(empTpCargo -> {
            tipoCargoFacadeLocal.create(crearCargoByEmpleadoWs(mapCargoEmpWs.get(empTpCargo)));
        });

//        if (colaboradores != null && colaboradores.size() > 0) {
//            for (EbEmpleadoWS e : colaboradores) {
//                mapEmpTipoCargo.put(Util.getNumerosDeString(e.getCodCargo()), e);
//            }
//            for (EmpleadoTipoCargo e : tipoCargoFacadeLocal.findAll()) {
//                EbEmpleadoWS get = mapEmpTipoCargo.get(String.valueOf(e.getIdSoftwareNombreCargo()));
//                if (get != null && !(Util.getNumerosDeString(get.getCodCargo()).equals("28")
//                        | Util.getNumerosDeString(get.getCodCargo()).equals("29")
//                        | Util.getNumerosDeString(get.getCodCargo()).equals("30")
//                        | Util.getNumerosDeString(get.getCodCargo()).equals("31"))) {
//                    e.setNombreCargo(get.getNomCargo().trim().toUpperCase());
//                    tipoCargoFacadeLocal.edit(e);
//                }
//            }
//        }
        MovilidadUtil.addSuccessMessage("Cargos actualizados con éxito");
        flag = false;
    }

    @Transactional
    void applyUpdateEmp(List<Empleado> lisEmp) {
        //            localizacionEmpleado(e); 
        lisEmp.forEach((e) -> {
            if (e.getCodigoTm() != null) {
                if (e.getCodigoTm() == 0) {
                    e.setCodigoTm(null);
                }
            }
            try {
                empleadoFacadeLocal.edit(e);
            } catch (Exception error) {
                System.out.println(error);
            }           
        });
    }

    public void obtenerLatLngTodosEmpleados() {
        List<Empleado> listEmp = empleadoFacadeLocal.findAllEmpleadosActivos(0);
        if (listEmp != null) {
            listEmp.stream().map((e) -> {
                localizacionEmpleado(e);
                return e;
            }).forEachOrdered((e) -> {
                empleadoFacadeLocal.edit(e);
            });
        }
        MovilidadUtil.addSuccessMessage("Proceso finalizado");
    }

    private void updateEmpleado(Empleado empleado, EbEmpleadoWS eWS) {
        contruirEmpleadoByEmpleadoWs(empleado, eWS);
        if (eWS.getIndActi().equals("I")) {
            empleado.setIdEmpleadoEstado(new EmpleadoEstado(3));
            empleado.setFechaRetiro(eWS.getFecDeja() != null ? Util.toDate(eWS.getFecDeja()) : empleado.getFechaRetiro());
        }
    }

    private Empleado contruirEmpleadoByEmpleadoWs(Empleado empleado, EbEmpleadoWS e) {
        Date d = new Date();
        EmpleadoTipoCargo etc = null;
        GopUnidadFuncional gUf = null;
        // cuando el empleado es nuevo
        if (empleado.getIdEmpleado() == null) {
            empleado.setCreado(d);
            empleado.setEstadoReg(0);
            empleado.setUsername(user.getUsername());

//            if (etc == null) {
//                etc = new EmpleadoTipoCargo();
//                etc.setIdSoftwareNombreCargo(new Integer(Util.getNumerosDeString(e.getCodCargo())));
//                etc.setNombreCargo(e.getNomCargo().trim().toUpperCase());
//                etc.setCreado(d);
//                etc.setEstadoReg(0);
//                etc.setUsername(user.getUsername());
//                etc.setModificado(d);
//                tipoCargoFacadeLocal.create(etc);
//                setCargos();
//                etc = mapEmpleadoTipoCargo.get(Util.getNumerosDeString(e.getCodCargo()));
//                empleado.setIdEmpleadoCargo(etc);
//            }
        }
        // cargar cargo a empleado
        etc = mapEmpleadoTipoCargo.get(Util.getNumerosDeString(e.getCodCargo()));
        if (etc == null) {
            System.out.println("codCargo: " + e.getCodCargo());
            System.out.println("nomCargo: " + e.getNomCargo());
        }
        empleado.setIdEmpleadoCargo(etc);

        //        
        empleado.setIdEmpleadoTipoIdentificacion(new EmpleadoTipoIdentificacion(2));
        empleado.setIdentificacion(e.getCodEmpl());
        empleado.setPathFoto("/rigel/empleado/" + e.getCodEmpl() + ".jpg");

        // insertar codigo tm si lo trae kactus
        empleado.setCodigoTm(Util.isStringNullOrEmpty(e.getCodInte())
                ? (validCargoByCodeTm(etc.getIdSoftwareNombreCargo() + "")
                ? new Integer(Util.getNumerosDeString(e.getCodEmpl())) : null)
                : new Integer(Util.getNumerosDeString(e.getCodInte())));

        // si el servicio de KACTUS trae el codigo interno y es un empleado que requiera codigoTm
//        if (!Util.isStringNullOrEmpty(e.getCodCargo())) {
//            if (etc != null && validCargoByCodeTm(etc.getIdEmpleadoTipoCargo() + "")) {
//                boolean isValidCodInterno = !Util.isStringNullOrEmpty(e.getCodInte());
//                if (isValidCodInterno && e.getIndActi().equals("A")) {
//                    empleado.setCodigoTm(new Integer(Util.getNumerosDeString(e.getCodInte())));
//                } else if (!Util.isStringNullOrEmpty(e.getCodEmpl())) {
//                    empleado.setCodigoTm(new Integer(Util.getNumerosDeString(e.getCodEmpl())));
//                }
//            } else {
//                // si no cuenta con el codigo que debe tener tm, se setea null en el codigo tm
//                empleado.setCodigoTm(null);
//            }
//        }
//        if (e.getCodCargo() != null
//                && (Util.getNumerosDeString(e.getCodCargo()).equals("28")
//                | Util.getNumerosDeString(e.getCodCargo()).equals("29")
//                | Util.getNumerosDeString(e.getCodCargo()).equals("30")
//                | Util.getNumerosDeString(e.getCodCargo()).equals("31"))) {
//            if (e.getCodInte() != null && e.getIndActi().equals("A")) {
//                empleado.setCodigoTm(e.getCodInte().isEmpty() ? null : new Integer(Util.getNumerosDeString(e.getCodInte())));
//            } else {
//                if (e.getCodEmpl() != null) {
//                    empleado.setCodigoTm(e.getCodEmpl().isEmpty() ? null : new Integer(Util.getNumerosDeString(e.getCodEmpl())));
//                }
//            }
//        }
        empleado.setNombres(e.getNomEmpl().trim().toUpperCase());
        empleado.setApellidos(e.getApeEmpl().trim().toUpperCase());
        if (!Util.isStringNullOrEmpty(e.getDtoResi())) {
            empleado.setIdEmpleadoDepartamento(new EmpleadoDepartamento(new Integer(e.getDtoResi())));
        } else {
            empleado.setIdEmpleadoDepartamento(new EmpleadoDepartamento(11));
        }
        if (!Util.isStringNullOrEmpty(e.getMpiResi())) {
            empleado.setIdEmpleadoMunicipio(new EmpleadoMunicipio(new Integer(e.getMpiResi())));
        } else {
            empleado.setIdEmpleadoMunicipio(new EmpleadoMunicipio(151));
        }
        // insertar operacion patios solo si no tiene uno asignado
        if (empleado.getIdOperacionPatio() == null) {
            empleado.setIdOperacionPatio(new OperacionPatios(1));
        }

        empleado.setDireccion(Util.isStringNullOrEmpty(e.getDirResi()) ? "SinDefinir" : e.getDirResi().trim().toUpperCase());
        empleado.setTelefonoFijo(Util.isStringNullOrEmpty(e.getTelResi()) ? "SinDefinir" : e.getTelResi());
        empleado.setTelefonoMovil(Util.isStringNullOrEmpty(e.getTelMovi()) ? "SinDefinir" : e.getTelMovi());
        empleado.setEmailPersonal(Util.isStringNullOrEmpty(e.getEeeMail()) ? e.getEeeMail() : "SinDefinir");
        empleado.setEmailCorporativo(Util.isStringNullOrEmpty(e.getBoxMail()) ? "SinDefinir" : e.getBoxMail());
        empleado.setNombreContactoEmergencia("SinDefinir");
        empleado.setTelefonoContactoEmergencia(Util.isStringNullOrEmpty(e.getTelEmer()) ? "SinDefinir" : e.getTelEmer());
        empleado.setMovilContactoEmergencia(Util.isStringNullOrEmpty(e.getTelEmer()) ? "SinDefinir" : e.getTelEmer());
        empleado.setGenero(e.getSexEmpl() != null ? (e.getSexEmpl().toCharArray())[0] : 'M');
        empleado.setRh(getRh(e.getFacSang(), e.getGruSang()));

        // ya el cargo se asigna en la primera parte de este metodo
//        if (empleado.getIdEmpleado() != null && e.getCodCargo() != null
//                && empleado.getIdEmpleadoCargo().getIdSoftwareNombreCargo() != null) {
//
//            etc = mapEmpleadoTipoCargo.get(Util.getNumerosDeString(e.getCodCargo()));
//            if (etc == null) {
//                etc = new EmpleadoTipoCargo();
//                etc.setIdEmpleadoTipoCargo(new Integer(Util.getNumerosDeString(e.getCodCargo())));
//                etc.setIdSoftwareNombreCargo(new Integer(Util.getNumerosDeString(e.getCodCargo())));
//                etc.setNombreCargo(e.getNomCargo().trim().toUpperCase());
//                etc.setCreado(new Date());
//                etc.setEstadoReg(0);
//                etc.setUsername(user.getUsername());
//                etc.setModificado(new Date());
//                empleado.setIdEmpleadoCargo(etc);
//                lstTipoCargo.add(crearCargoByEmpleadoWs(e));
//            }
//        }
//        empleado.setIdEmpleadoCargo(new EmpleadoTipoCargo(new Integer(e.getCodCargo().trim())));
//        if (etc != null) {
//            empleado.setIdEmpleadoCargo(etc);
//        }
        empleado.setFechaNcto(e.getFecNaci() != null ? Util.toDate(e.getFecNaci()) : Util.toDate("1900-01-01"));
        empleado.setFechaIngreso(e.getFecCont() != null ? Util.toDate(e.getFecCont()) : Util.toDate("1900-01-01"));
        empleado.setIdEmpleadoEstado(e.getIndActi() != null ? e.getIndActi().equals("A") ? new EmpleadoEstado(1) : new EmpleadoEstado(3) : new EmpleadoEstado(3));
        //Set Nro de Contrato
        empleado.setNroContrato(e.getNroCont());
        //Set Unidad Funcional
        Integer idGopUF = mapUnidadFuncional.get(e.getNomEmpr().trim().toUpperCase());
        if (idGopUF != null) {
            gUf = new GopUnidadFuncional(idGopUF);
            empleado.setIdGopUnidadFuncional(gUf);
        }

        empleado.setModificado(new Date());
        return empleado;
    }

    private String getRh(String rh, String grpS) {
        if (Util.isStringNullOrEmpty(rh)) {
            return "N/A";
        }
        if (Util.isStringNullOrEmpty(grpS)) {
            return "N/A";
        }
        rh = Util.getStringSinEspacios(rh).toUpperCase();
        if (grpS.contains("+")) {
            rh = rh + "+";
        }
        if (grpS.contains("-")) {
            rh = rh + "-";
        }
        return rh;
    }

    private EmpleadoTipoCargo crearCargoByEmpleadoWs(EbEmpleadoWS e) {
        EmpleadoTipoCargo etc = new EmpleadoTipoCargo();
        Date d = new Date();
//        etc.setIdEmpleadoTipoCargo(new Integer(Util.getNumerosDeString(e.getCodCargo())));
        etc.setNombreCargo(e.getNomCargo().trim().toUpperCase());
        etc.setUsername(user.getUsername());
        etc.setIdSoftwareNombreCargo(new Integer(Util.getNumerosDeString(e.getCodCargo())));
        etc.setCreado(d);
        etc.setModificado(d);
        etc.setEstadoReg(0);
        return etc;
    }

    private void cargarEmpleados() {
        mapEmpleado.clear();
        empleadoFacadeLocal.findAll().forEach((e) -> {
            mapEmpleado.put(e.getIdentificacion(), e);
        });
    }

    // se cargan los mapas tanto por idSoftware, como por nombre
    private void cargarEmpleadoCargos() {
        mapEmpleadoTipoCargo.clear();
        mapEmpleadoTipoCargoNombre.clear();
        tipoCargoFacadeLocal.findAll().forEach((e) -> {
            mapEmpleadoTipoCargo.put(e.getIdSoftwareNombreCargo() + "", e);
            mapEmpleadoTipoCargoNombre.put(e.getNombreCargo(), e);
        });
    }

    private void cargarUnidadFuncional() {
        mapUnidadFuncional.clear();
        gopUnidadFuncionalLocal.findAll().forEach((uf) -> {
            mapUnidadFuncional.put(uf.getNombre().trim().toUpperCase(), uf.getIdGopUnidadFuncional());
        });
    }

    private void cargarCargosEmpleadoWs() {
        if (cargarColaboradores()) {
            return;
        }
        mapCargoEmpWs.clear();
        // cargar mapa de cargos del servicio de KACTUS
        spEmpleadosWs.get()
                .filter(empWs -> empWs.getIndActi().equalsIgnoreCase("A"))
                .forEachOrdered(empWs -> {
                    mapCargoEmpWs.put(empWs.getNomCargo().trim().toUpperCase(), empWs);
                });
    }

    private void cargarEmpleadoWs() {
        mapEmpleadoWS.clear();
        colaboradores.forEach((e) -> {
            mapEmpleadoWS.put(Util.getNumerosDeString(e.getCodEmpl()), e);
        });
    }

    private boolean cargarColaboradores() {
        if (spEmpleadosWs == null) {
            colaboradores = getPortWS().colaboradores(
                    SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_USER_EMPRESA_WS),
                    SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_CLAVE_EMPRESA),
                    SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_NOMBRE_EMPRESA));
            if (colaboradores == null) {
                MovilidadUtil.addErrorMessage("Servicio de KACTUS no retorna empleados");
                return true;
            }
            if (colaboradores.isEmpty()) {
                MovilidadUtil.addErrorMessage("Servicio de KACTUS no retorna empleados");
                return true;
            }
            cargarEmpleadoWs();
            spEmpleadosWs = () -> colaboradores.stream();
        }
        return false;
    }

    private void cargarArrayCargosTm() {
        codigosForCodeTm = codigoCargoForCodigoTm.split(",");
    }

    private boolean validCargoByCodeTm(String id) {
        for (String c : codigosForCodeTm) {
            if (c.equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    private MBWebService getPortWS() {
        datos = new Datos();
        if (port == null) {
            port = datos.getMBWebServicePort();
        }
        return port;
    }

    public List<Empleado> getLstEmpleado() {
        return lstEmpleado;
    }

    public void setLstEmpleado(List<Empleado> lstEmpleado) {
        this.lstEmpleado = lstEmpleado;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
