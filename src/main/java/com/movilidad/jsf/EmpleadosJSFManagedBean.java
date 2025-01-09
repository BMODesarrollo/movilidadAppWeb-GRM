/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.ConfigEmpresaFacadeLocal;
import com.movilidad.ejb.EmpleadoDepartamentoFacadeLocal;
import com.movilidad.ejb.EmpleadoEstadoFacadeLocal;
import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.EmpleadoTipoCargoFacadeLocal;
import com.movilidad.ejb.EmpleadoTipoIdentificacionFacadeLocal;
import com.movilidad.ejb.GopUnidadFuncionalFacadeLocal;
import com.movilidad.ejb.OperacionPatiosFacadeLocal;
import com.movilidad.ejb.UsersFacadeLocal;
import com.movilidad.model.ConfigEmpresa;
import com.movilidad.model.Empleado;
import com.movilidad.model.EmpleadoDepartamento;
import com.movilidad.model.EmpleadoEmpleador;
import com.movilidad.model.EmpleadoEstado;
import com.movilidad.model.EmpleadoMunicipio;
import com.movilidad.model.EmpleadoTipoCargo;
import com.movilidad.model.EmpleadoTipoIdentificacion;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.OperacionPatios;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import com.movlidad.httpUtil.GoogleApiGeocodingServices;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Soluciones IT
 */
@Named(value = "emplJSFMdB")
@ViewScoped
public class EmpleadosJSFManagedBean implements Serializable {

    @Inject
    private UploadFotoJSFManagedBean uploadFotoMB;

    @Inject
    private SelectGopUnidadFuncionalBean selectGopUnidadFuncionalBean;

    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;
    private Empleado empl;
    private EmpleadoDepartamento emplDeprt;
    private int i_idDepartamento = 0;
    private int i_idMunicipio = 0;
    private int i_idPatio = 0;
    private int i_idCargo = 0;
    private int i_idTipoIdentificacion = 0;
    private int i_idEsdado = 0;
    private Integer i_idEmpleador;

    int idParaConsulta = 0;
    private boolean flag = false;
    private boolean flagCertificar = false;

    private String codigoTransMi;
    private String nombreC;
    private String apellidoC;

    private final int mayorEdad = 18;

    private List<EmpleadoEstado> listEmplEstados;
    private List<EmpleadoDepartamento> listEmplDeprtS;
    private List<EmpleadoMunicipio> listEmplMunicipios;
    private List<EmpleadoTipoIdentificacion> listEmplTipoIdents;
    private List<EmpleadoTipoCargo> listEmplTipoCargo;
    private List<OperacionPatios> listPatios;
    private List<Empleado> listEmpls;
    private List<Empleado> listColaboradores;
    private List<GopUnidadFuncional> lstUnidadesFuncionales;

    @EJB
    private EmpleadoTipoIdentificacionFacadeLocal emplTipIdentEJB;
    @EJB
    private EmpleadoTipoCargoFacadeLocal emplTipCargoEJB;
    @EJB
    private EmpleadoDepartamentoFacadeLocal emplDeprtEJB;
    @EJB
    private OperacionPatiosFacadeLocal oppEJB;
    @EJB
    private EmpleadoFacadeLocal emplEJB;
    @EJB
    private EmpleadoEstadoFacadeLocal emplEstadoEJB;
    @EJB
    private ConfigEmpresaFacadeLocal empresaFacadeLocal;
    @EJB
    private UsersFacadeLocal usersEjb;
    @EJB
    private GopUnidadFuncionalFacadeLocal unidadFuncionalEjb;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    // georeferencia
    private String s_lat = "4.646189";
    private String s_lng = "-74.078540";
    private MapModel simpleModel;

    public EmpleadosJSFManagedBean() {
        this.listEmplTipoIdents = new ArrayList();
        this.listEmplDeprtS = new ArrayList();
        this.listPatios = new ArrayList();
        this.listEmplEstados = new ArrayList();
        empl = new Empleado();
    }

    @PostConstruct
    public void init() {
        if (MovilidadUtil.validarUrl("empleado/empleadoTabla")) {
            i_idEmpleador = null;
            simpleModel = new DefaultMapModel();
            this.listEmpls = new ArrayList();
            listarEmpleados();
            validarRol();
        } else if (MovilidadUtil.validarUrl("listadoColaboradores/colaboradoresEmpleados")) {
            cargarPorUnidadFuncional();
        }
    }

    public String fechaNacimiento() {
        if (empl != null) {
            if (empl.getFechaNcto() != null) {
                return Util.dateFormat(empl.getFechaNcto());
            }
        }
        return "N/A";
    }

    public void validarRol() {
        for (GrantedAuthority auth : user.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_FACILITADOR")
                    || auth.getAuthority().equals("ROLE_PROFOP")
                    || auth.getAuthority().equals("ROLE_PROFPRG")) {
                flagCertificar = true;
                return;
            }
        }
        flagCertificar = false;
    }

    public void listarEmpleados() {
        listEmpls = emplEJB.findEmpleadosByIdGopUnidadFuncional("", unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
    }

    public void cargarListas() {
        listarEmplTipIden();
        listarEmplDeprt();
        listarEmplTipoCargo();
        listarPatios();
        listarEmplEstado();
        selectGopUnidadFuncionalBean.consultarList();
    }

    public void resetSelect() {
        i_idDepartamento = 0;
        i_idMunicipio = 0;
        i_idPatio = 0;
        i_idCargo = 0;
        i_idTipoIdentificacion = 0;
        i_idEsdado = 0;
//        selectGopUnidadFuncionalBean.setI_unidad_funcional(null);
    }

    public void nuevo() {
        simpleModel = new DefaultMapModel();
        flag = false;
        empl = new Empleado();
        resetSelect();
        cargarListas();

    }

    public void openDialogFoto() {
        uploadFotoMB.setCompoUpdate("formEmpleado:idPanelGrid");
        uploadFotoMB.setFile(null);
        uploadFotoMB.setFlag(false);
        uploadFotoMB.setModal("PF('UploadFotoDialog').hide();");

        PrimeFaces current = PrimeFaces.current();
        current.ajax().update("formEmpleado:idPanelGrid");
        current.executeScript("PF('UploadFotoDialog').show();");
    }

    public void viewEmpleado(Empleado e) {
        if (e != null) {
            empl = e;
        }
        PrimeFaces.current().ajax().update("formConsultaEmpl");
    }

    private boolean validar() {
        if (!MovilidadUtil.validarFecha(empl.getFechaNcto())) {
            MovilidadUtil.addErrorMessage("El empleado debe ser Mayor de edad");
            return true;
        }
        if (unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional() == 0) {
            MovilidadUtil.addErrorMessage("Unidad Funcional es requerido");
            return true;
        }
        if (emplEJB.findCampo("identificacion", empl.getIdentificacion(), idParaConsulta) != null) {
            MovilidadUtil.addErrorMessage(ConstantsUtil.YA_EXISTE_IDENTIFICACION);
            return true;
        }
        if (empl.getCodigoTm() != null) {
            try {
                Integer.toString(empl.getCodigoTm());
            } catch (Exception e) {
                MovilidadUtil.addErrorMessage("Error al ingresar Código TM");
                return true;
            }
            if (emplEJB.findCampo("codigoTm", Integer.toString(empl.getCodigoTm()).replaceAll("\\s", ""), idParaConsulta) != null) {
                MovilidadUtil.addErrorMessage(ConstantsUtil.YA_EXISTE_CODIGO_TM);
                return true;
            }
        }

        if (empl.getCodigoInterno() != null) {
            try {
                Integer.toString(empl.getCodigoInterno());
            } catch (Exception e) {
                MovilidadUtil.addErrorMessage("Error al ingresar Código Interno");
                return true;
            }
            if (emplEJB.findCampo("codigoInterno", Integer.toString(empl.getCodigoInterno()), idParaConsulta) != null) {
                MovilidadUtil.addErrorMessage(ConstantsUtil.YA_EXISTE_CODIGO_INTERNO);
                return true;
            }
        }
        if (empl.getEmailPersonal() != null) {
            if (emplEJB.findCampo("emailPersonal", empl.getEmailPersonal(), idParaConsulta) != null) {
                MovilidadUtil.addErrorMessage(ConstantsUtil.YA_ESXITE_EMAIL_PERSONAL);
                return true;
            }
        }
        if (empl.getEmailCorporativo() != null) {
            if (emplEJB.findCampo("emailCorporativo", empl.getEmailCorporativo(), idParaConsulta) != null) {
                MovilidadUtil.addErrorMessage(ConstantsUtil.YA_EXISTE_EMAIL_EMPRESARIAL);
                return true;
            }
        }
        if (empl.getIdEmpleado() == null && uploadFotoMB.getFile() == null) {
            MovilidadUtil.addErrorMessage("Se debe Cargar una foto");
            return true;
        }
        return false;
    }

    public void guardar() {
        if (flag) {
            idParaConsulta = empl.getIdEmpleado();
        }
        if (validar()) {
            return;
        }
        try {

            setValoresEmpleado();
            if (i_idEmpleador != null) {
                empl.setIdEmpleadoEmpleador(new EmpleadoEmpleador(i_idEmpleador));
            } else {
                empl.setIdEmpleadoEmpleador(null);
            }
            if (flag) {
                guardarEdit();
            } else {
                empl.setCreado(MovilidadUtil.fechaCompletaHoy());
                empl.setEstadoReg(0);
                empl.setPathFoto("/");
                empl.setCertificado(0);
                emplEJB.create(empl);

                String path = null;
                path = uploadFotoMB.GuardarFotoEmpleado(empl.getIdentificacion());
                empl.setPathFoto(path);
                if (path != null) {
                    empl.setPathFoto(path);
                    emplEJB.edit(empl);
                    MovilidadUtil.addSuccessMessage("Registro exitoso Estado Empleado");
                    empl = new Empleado();
                    PrimeFaces.current().executeScript("PF('dlgEmpelado').hide();");
                }
            }
            listarEmpleados();
            i_idEmpleador = null;
            simpleModel = new DefaultMapModel();
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error Interno: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void guardarEdit() throws IOException {
        String newPath = "";
        boolean resultDelete = false;
        try {
            if (uploadFotoMB.getFile() != null) {
                newPath = uploadFotoMB.GuardarFotoEmpleado(empl.getIdentificacion());
            }
            if (!"".equals(newPath)) {
                empl.setPathFoto(newPath);
                if (!newPath.equals(empl.getPathFoto())) {
                    resultDelete = MovilidadUtil.eliminarFichero(empl.getPathFoto());
                }
            }
            emplEJB.edit(empl);
            MovilidadUtil.addSuccessMessage("Modificación exitosa Empleado");
            MovilidadUtil.hideModal("dlgEmpelado");
        } catch (Exception e) {
            MovilidadUtil.addErrorMessage("Error Interno");
            e.printStackTrace();
        }
    }

    @Transactional
    public void certificar(Empleado emplLocal) {
        emplLocal.setCertificado(1);
        int i = emplEJB.certificar(emplLocal.getIdEmpleado(), 1, user.getUsername());
        if (i > 0) {
            MovilidadUtil.addSuccessMessage("Se ha certificado el operador");
        } else {
            MovilidadUtil.addErrorMessage("No se ha certificado el operador");
        }

    }

    @Transactional
    public void certificarNo(Empleado emplLocal) {
        emplLocal.setCertificado(0);
        int i = emplEJB.certificar(emplLocal.getIdEmpleado(), 0, user.getUsername());
        if (i > 0) {
            MovilidadUtil.addSuccessMessage("Se ha quitado el certificado el operador");
        } else {
            MovilidadUtil.addErrorMessage("No se ha quitado el certificado el operador");
        }

    }

    public void editar(Empleado emplLocal) throws Exception {
        uploadFotoMB.setFile(null);
        flag = true;
        cargarListas();
        empl = emplLocal;
        i_idDepartamento = empl.getIdEmpleadoDepartamento().getIdEmpleadoDepartamento();
        i_idMunicipio = empl.getIdEmpleadoMunicipio().getIdEmpleadoCiudad();
        i_idPatio = empl.getIdOperacionPatio().getIdOperacionPatios();
        i_idCargo = empl.getIdEmpleadoCargo().getIdEmpleadoTipoCargo();
        i_idTipoIdentificacion = empl.getIdEmpleadoTipoIdentificacion().getIdEmpleadoTipoIdentificacion();
        i_idEsdado = empl.getIdEmpleadoEstado().getIdEmpleadoEstado();
        listEmplMunicipios = empl.getIdEmpleadoDepartamento().getEmpleadoMunicipioList();
        unidadFuncionalSessionBean
                .setI_unidad_funcional(empl.getIdGopUnidadFuncional() == null ? 0
                        : empl.getIdGopUnidadFuncional().getIdGopUnidadFuncional());
        if (listEmplMunicipios != null && !listEmplMunicipios.isEmpty()) {
            boolean ok = false;
            for (EmpleadoMunicipio em : listEmplMunicipios) {
                if (em.getIdEmpleadoCiudad().equals(i_idMunicipio)) {
                    ok = true;
                    break;
                }
            }
            if (!ok) {
                i_idMunicipio = 0;
            }
        }
        i_idEmpleador = empl.getIdEmpleadoEmpleador() != null ? empl.getIdEmpleadoEmpleador().getIdEmpleadoEmpleador() : null;
        // georeferencia
        if (emplLocal.getLatitud() != null && emplLocal.getLongitud() != null) {
            s_lat = emplLocal.getLatitud();
            s_lng = emplLocal.getLongitud();
            LatLng latlng = new LatLng(Double.parseDouble(s_lat), Double.parseDouble(s_lng));
            simpleModel.addOverlay(new Marker(latlng, "Punto Seleccionado"));
        } else {
            s_lat = "4.646189";
            s_lng = "-74.078540";
        }
    }

    public void setValoresEmpleado() {
        empl.setIdEmpleadoCargo(new EmpleadoTipoCargo(i_idCargo));
        empl.setIdEmpleadoDepartamento(new EmpleadoDepartamento(i_idDepartamento));
        empl.setIdEmpleadoMunicipio(new EmpleadoMunicipio(i_idMunicipio));
        empl.setIdEmpleadoEstado(new EmpleadoEstado(i_idEsdado));
        empl.setIdEmpleadoTipoIdentificacion(new EmpleadoTipoIdentificacion(i_idTipoIdentificacion));
        empl.setIdOperacionPatio(new OperacionPatios(i_idPatio));

        empl.setIdGopUnidadFuncional(new GopUnidadFuncional(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional()));

        empl.setUsername(user.getUsername());
    }

    public void listarEmplTipIden() {
        try {
            listEmplTipoIdents = emplTipIdentEJB.findAll();
        } catch (Exception e) {
        }
    }

    public void listarPatios() {
        try {
            listPatios = oppEJB.findAll();
        } catch (Exception e) {
        }
    }

    public void listarEmplTipoCargo() {
        try {
            listEmplTipoCargo = emplTipCargoEJB.findAll();
        } catch (Exception e) {
        }
    }

    public void listarEmplDeprt() {
        try {
            listEmplDeprtS = emplDeprtEJB.findAll();
        } catch (Exception e) {
        }
    }

    public void listarEmplEstado() {
        try {
            listEmplEstados = emplEstadoEJB.findAll();
        } catch (Exception e) {
        }
    }

    public void findByIdNew() {
        for (EmpleadoDepartamento ed : listEmplDeprtS) {
            if (ed.getIdEmpleadoDepartamento() == getI_idDepartamento()) {
                listEmplMunicipios = ed.getEmpleadoMunicipioList();
                return;
            }
        }
    }

    public String master(Empleado empl) {
        String master = "";
        try {
            if (empl != null) {
                master = empl.getPmGrupoDetalleList().get(0).getIdGrupo().getNombreGrupo();
            }
        } catch (Exception e) {
            if (empl.getPmGrupoList() != null) {
                if (empl.getPmGrupoList().size() > 0) {
                    return empl.getPmGrupoList().get(0).getNombreGrupo();
                }

            }
            return "N/A";
        }
        return master;
    }

    public List<Object> grupoPM() {
        List<Object> aux_list = new ArrayList<>();
        for (Empleado e : listEmpls) {
            aux_list.add(master(e));
        }
        aux_list = aux_list.stream().distinct().collect(Collectors.toList());
        return aux_list;
    }

    //georeferencia
    public void onPointSelect(PointSelectEvent event) {
        try {
            simpleModel = new DefaultMapModel();
            LatLng latlng = event.getLatLng();
            empl.setLatitud(String.valueOf(latlng.getLat()));
            empl.setLongitud(String.valueOf(latlng.getLng()));
            simpleModel.addOverlay(new Marker(latlng, "Punto Seleccionado"));
            s_lat = String.valueOf(event.getLatLng().getLat());
            s_lng = String.valueOf(event.getLatLng().getLng());
            MovilidadUtil.addSuccessMessage("Coordenadas seleccionadas, Longitud:"
                    + s_lat + " Latitud:" + s_lng);
        } catch (Exception e) {
            System.out.println("Error Evento onpointSelect AccidenteLugar");
        }
    }

    public UploadFotoJSFManagedBean getUploadFotoMB() {
        return uploadFotoMB;
    }

    public void localizacionEmpleado() {
        ConfigEmpresa keyGoogle = empresaFacadeLocal.findByLlave(Util.CODE_API_KEY_GOOGLE);
        if (keyGoogle == null) {
            MovilidadUtil.addErrorMessage("El sistema no cuenta con la KEY de GOOGLE necesaria");
            return;
        }
        if (empl == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar un empleado");
            return;
        }
        if (Util.isStringNullOrEmpty(empl.getDireccion())) {
            MovilidadUtil.addErrorMessage("Debe suministrar una dirección");
            return;
        }
        if (empl.getDireccion().equals("SinDefinir")) {
            MovilidadUtil.addErrorMessage("Debe suministrar una dirección valida");
            return;
        }
        GoogleApiGeocodingServices gapi = new GoogleApiGeocodingServices(keyGoogle.getValor());
        gapi.onSetLatLngFromAddressForEmpleado(empl, empl.getDireccion());
        if (empl.getLatitud() != null && empl.getLongitud() != null) {
            s_lat = empl.getLatitud();
            s_lng = empl.getLongitud();
            LatLng latlng = new LatLng(Double.parseDouble(s_lat), Double.parseDouble(s_lng));
            simpleModel.addOverlay(new Marker(latlng, "Punto Seleccionado"));
        } else {
            s_lat = "4.646189";
            s_lng = "-74.078540";
        }
//        GeoApiContext context = new GeoApiContext.Builder()
//                .apiKey(keyGoogle.getValor())
//                .build();
//        try {
//            Empleado e = empl;
//            if (e.getDireccion() != null && !e.getDireccion().equals("SinDefinir")) {
//                GeocodingResult[] resul = GeocodingApi
//                        .newRequest(context)
//                        .address(e.getDireccion())
//                        .await();
//                String localidad = null;
//                String subLocalidad = null;
//                for (GeocodingResult gr : resul) {
//                    e.setLatitud(String.valueOf(gr.geometry.location.lat));
//                    e.setLongitud(String.valueOf(gr.geometry.location.lng));
//                    s_lat = e.getLatitud();
//                    s_lng = e.getLongitud();
//                    //      System.out.println("Coordenadas: " + gr.geometry.location.toString());
//                    for (AddressComponent ac : gr.addressComponents) {
//
//                        //       System.out.println("Localidad: " + ac.shortName);
//                        for (AddressComponentType act : ac.types) {
//                            //       System.out.println("Localidad Type: " + act.name());
//                            if (act.name().equalsIgnoreCase(AddressComponentType.LOCALITY.name())) {
//                                localidad = ac.longName;
//                            }
//                            if (act.name().equalsIgnoreCase(AddressComponentType.SUBLOCALITY.name())) {
//                                subLocalidad = ac.longName;
//                            }
//                        }
//                    }
//                }
//                if (e.getLatitud() != null && e.getLongitud() != null) {
//                    s_lat = e.getLatitud();
//                    s_lng = e.getLongitud();
//                    LatLng latlng = new LatLng(Double.parseDouble(s_lat), Double.parseDouble(s_lng));
//                    simpleModel.addOverlay(new Marker(latlng, "Punto Seleccionado"));
//                } else {
//                    s_lat = "4.646189";
//                    s_lng = "-74.078540";
//                }
//                if (subLocalidad != null) {
//                    e.setLocalidad(subLocalidad);
//                } else {
//                    e.setLocalidad(localidad);
//                }
//                if (resul != null && resul.length == 0) {
//                    MovilidadUtil.addErrorMessage("No se ha enconrado información con la dirección suministrada");
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            MovilidadUtil.addErrorMessage("Ha ocurrido un error, reportar a sistemas");
//        }
    }

    public void setUploadFotoMB(UploadFotoJSFManagedBean uploadFotoMB) {
        this.uploadFotoMB = uploadFotoMB;
    }

    public void cargarPorUnidadFuncional() {
        listColaboradores = emplEJB.getColaboradores(unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional(), false);
    }

    public Empleado getEmpl() {
        return empl;
    }

    public void setEmpl(Empleado empl) {
        this.empl = empl;
    }

    public EmpleadoDepartamento getEmplDeprt() {
        return emplDeprt;
    }

    public void setEmplDeprt(EmpleadoDepartamento emplDeprt) {
        this.emplDeprt = emplDeprt;
    }

    public int getI_idDepartamento() {
        return i_idDepartamento;
    }

    public void setI_idDepartamento(int i_idDepartamento) {
        this.i_idDepartamento = i_idDepartamento;
    }

    public int getI_idMunicipio() {
        return i_idMunicipio;
    }

    public void setI_idMunicipio(int i_idMunicipio) {
        this.i_idMunicipio = i_idMunicipio;
    }

    public int getI_idPatio() {
        return i_idPatio;
    }

    public void setI_idPatio(int i_idPatio) {
        this.i_idPatio = i_idPatio;
    }

    public int getI_idCargo() {
        return i_idCargo;
    }

    public void setI_idCargo(int i_idCargo) {
        this.i_idCargo = i_idCargo;
    }

    public int getI_idTipoIdentificacion() {
        return i_idTipoIdentificacion;
    }

    public void setI_idTipoIdentificacion(int i_idTipoIdentificacion) {
        this.i_idTipoIdentificacion = i_idTipoIdentificacion;
    }

    public int getI_idEsdado() {
        return i_idEsdado;
    }

    public void setI_idEsdado(int i_idEsdado) {
        this.i_idEsdado = i_idEsdado;
    }

    public List<EmpleadoEstado> getListEmplEstados() {
        return listEmplEstados;
    }

    public void setListEmplEstados(List<EmpleadoEstado> listEmplEstados) {
        this.listEmplEstados = listEmplEstados;
    }

    public List<EmpleadoDepartamento> getListEmplDeprtS() {
        return listEmplDeprtS;
    }

    public void setListEmplDeprtS(List<EmpleadoDepartamento> listEmplDeprtS) {
        this.listEmplDeprtS = listEmplDeprtS;
    }

    public List<EmpleadoMunicipio> getListEmplMunicipios() {
        return listEmplMunicipios;
    }

    public void setListEmplMunicipios(List<EmpleadoMunicipio> listEmplMunicipios) {
        this.listEmplMunicipios = listEmplMunicipios;
    }

    public List<EmpleadoTipoIdentificacion> getListEmplTipoIdents() {
        return listEmplTipoIdents;
    }

    public void setListEmplTipoIdents(List<EmpleadoTipoIdentificacion> listEmplTipoIdents) {
        this.listEmplTipoIdents = listEmplTipoIdents;
    }

    public List<EmpleadoTipoCargo> getListEmplTipoCargo() {
        return listEmplTipoCargo;
    }

    public void setListEmplTipoCargo(List<EmpleadoTipoCargo> listEmplTipoCargo) {
        this.listEmplTipoCargo = listEmplTipoCargo;
    }

    public List<OperacionPatios> getListPatios() {
        return listPatios;
    }

    public void setListPatios(List<OperacionPatios> listPatios) {
        this.listPatios = listPatios;
    }

    public List<Empleado> getListEmpls() {
        return listEmpls;
    }

    public void setListEmpls(List<Empleado> listEmpls) {
        this.listEmpls = listEmpls;
    }

    public String getCodigoTransMi() {
        return codigoTransMi;
    }

    public void setCodigoTransMi(String codigoTransMi) {
        this.codigoTransMi = codigoTransMi;
    }

    public String getNombreC() {
        return nombreC;
    }

    public void setNombreC(String nombreC) {
        this.nombreC = nombreC;
    }

    public String getApellidoC() {
        return apellidoC;
    }

    public void setApellidoC(String apellidoC) {
        this.apellidoC = apellidoC;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<Empleado> getListColaboradores() {
        return listColaboradores;
    }

    public boolean isFlagCertificar() {
        return flagCertificar;
    }

    public void setFlagCertificar(boolean flagCertificar) {
        this.flagCertificar = flagCertificar;
    }

    public Integer getI_idEmpleador() {
        return i_idEmpleador;
    }

    public void setI_idEmpleador(Integer i_idEmpleador) {
        this.i_idEmpleador = i_idEmpleador;
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public void setSimpleModel(MapModel simpleModel) {
        this.simpleModel = simpleModel;
    }

    public String getS_lat() {
        return s_lat;
    }

    public void setS_lat(String s_lat) {
        this.s_lat = s_lat;
    }

    public String getS_lng() {
        return s_lng;
    }

    public void setS_lng(String s_lng) {
        this.s_lng = s_lng;
    }

    public List<GopUnidadFuncional> getLstUnidadesFuncionales() {
        return lstUnidadesFuncionales;
    }

    public void setLstUnidadesFuncionales(List<GopUnidadFuncional> lstUnidadesFuncionales) {
        this.lstUnidadesFuncionales = lstUnidadesFuncionales;
    }

}
