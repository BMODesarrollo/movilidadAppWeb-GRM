/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.jsf;

import com.movilidad.ejb.EmpleadoFacadeLocal;
import com.movilidad.ejb.OperacionKmChecklistDetFacadeLocal;
import com.movilidad.ejb.OperacionKmChecklistFacadeLocal;
import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.model.Empleado;
import com.movilidad.model.OperacionKmChecklist;
import com.movilidad.model.OperacionKmChecklistDet;
import com.movilidad.model.Vehiculo;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Soluciones IT
 */
@Named(value = "opKmCLJSFMB")
@ViewScoped
public class OperacionKmCheckListJSFManagedBean implements Serializable {

    private OperacionKmChecklist opKmChecklist;
    private List<OperacionKmChecklist> listOpKmCL;
    private List<OperacionKmChecklistDet> listOpKmCLDetEliminar;

    private Empleado empl;
    private Vehiculo vehiculo;
    private String codigoTransMi;
    private String codigoV;
    private boolean flagVhcl = false;
    private boolean flagOpkmCl = false;

    @Inject
    private UploadFotoJSFManagedBean uploadFotoMB;
    @Inject
    private ViewDocumentoJSFManagedBean viewDMB;
    @EJB
    private EmpleadoFacadeLocal emplEJB;
    @EJB
    private VehiculoFacadeLocal vehiculoEJB;
    @EJB
    private OperacionKmChecklistFacadeLocal opKmCLEJB;
    @EJB
    private OperacionKmChecklistDetFacadeLocal opKmCLDetEJB;

    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    /**
     * Creates a new instance of OperacionKmCheckListJSFManagedBean
     */
    public OperacionKmCheckListJSFManagedBean() {
    }

    @PostConstruct
    public void init() {
        listOpKmCL = new ArrayList<>();
        listarOpCheckList();
    }

    public void listarOpCheckList() {
        try {
            listOpKmCL = opKmCLEJB.findAll();
        } catch (Exception e) {
        }
    }

    public void openDialogFileUpLoad() {
        PrimeFaces.current().ajax().update("formPDF");
        uploadFotoMB.setCompoUpdate("frmOpKm:idFULOP");
        uploadFotoMB.setFile(null);

        uploadFotoMB.setModal("PF('UploadPDFDialog').hide();");
        PrimeFaces.current().executeScript("PF('UploadPDFDialog').show();");
    }

    public void openDialog() {
        codigoV = "";
        opKmChecklist = new OperacionKmChecklist();
        opKmChecklist.setOperacionKmChecklistDetList(new ArrayList<OperacionKmChecklistDet>());
        flagOpkmCl = false;
        flagVhcl = false;
        uploadFotoMB.setFlag(false);
        PrimeFaces.current().executeScript("PF('opKmDialog').show();");
        PrimeFaces.current().ajax().update("frmOpKm");
        PrimeFaces.current().ajax().update("frmOpKm:idFULOP");
    }

    public Date maxDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }

    public void guardar() throws IOException {
        OperacionKmChecklist persistir = new OperacionKmChecklist();

        if (opKmChecklist.getKmInicial() > opKmChecklist.getKmFinal()) {
            MovilidadUtil.addErrorMessage("Error. El Km Inicial no puede ser mayor al Km Final");
            return;
        }
        if (flagOpkmCl) {
            OperacionKmChecklist opUno = opKmCLEJB.findChecklistIgualByIdVehiculo(opKmChecklist.getIdVehiculo().getIdVehiculo(), opKmChecklist.getFecha(), opKmChecklist.getIdOperacionKmChecklist());
            if (opUno != null) {
                MovilidadUtil.addErrorMessage("Error. Ya existe un regsitro para este vehÃ­culo con la fecha digitada");
                return;
            }

            OperacionKmChecklist opDos = opKmCLEJB.findChecklistByIdVehiculo(opKmChecklist.getIdVehiculo().getIdVehiculo(), opKmChecklist.getFecha(), opKmChecklist.getIdOperacionKmChecklist(), 0);
            if (opDos != null) {
                if (opKmChecklist.getKmInicial() < opDos.getKmFinal()) {
                    MovilidadUtil.addErrorMessage("Error. Se ha encontrado que le Km inicial es menor al Km Final del regsitro anterior para este vehÃ­culo. Valor minimo: " + opDos.getKmFinal() + "Mtrs");
                    return;
                }
            }

            OperacionKmChecklist opTres = opKmCLEJB.findChecklistByIdVehiculo(opKmChecklist.getIdVehiculo().getIdVehiculo(), opKmChecklist.getFecha(), opKmChecklist.getIdOperacionKmChecklist(), 1);
            if (opTres != null) {
                if (opKmChecklist.getKmFinal() > opTres.getKmInicial()) {
                    MovilidadUtil.addErrorMessage("Error. Se ha encontrado que el Km Final es mayor al Km Inicial del regsitro posterior para este vehÃ­culo. Valor Maximo: " + opTres.getKmInicial() + "Mtrs");
                    return;
                }
            }

            if (uploadFotoMB.getFile() != null) {
                String path = MovilidadUtil.dirYYMMDD("OpKmChk", opKmChecklist.getFecha(), "ruta");
                String pathCompleto = uploadFotoMB.GuardarFoto(opKmChecklist.getIdOperacionKmChecklist(), path, opKmChecklist.getIdVehiculo().getCodigo());
                persistir.setPathChecklist(pathCompleto);
            } else {
                persistir.setPathChecklist(opKmChecklist.getPathChecklist());
            }
            if (!listOpKmCLDetEliminar.isEmpty()) {
                for (OperacionKmChecklistDet op : listOpKmCLDetEliminar) {
                    opKmCLDetEJB.remove(op);
                }
            }
            persistir.setIdOperacionKmChecklist(opKmChecklist.getIdOperacionKmChecklist());
            persistir.setUsername(user.getUsername());
            persistir.setKmInicial(opKmChecklist.getKmInicial());
            persistir.setKmFinal(opKmChecklist.getKmFinal());
            persistir.setIdVehiculo(opKmChecklist.getIdVehiculo());
            persistir.setFecha(opKmChecklist.getFecha());
            persistir.setEstadoReg(opKmChecklist.getEstadoReg());
            persistir.setCreado(opKmChecklist.getCreado());
            opKmCLEJB.edit(persistir);
            for (OperacionKmChecklistDet opkmcldet : opKmChecklist.getOperacionKmChecklistDetList()) {
                opkmcldet.setIdOperacionKmChecklist(opKmChecklist);
                opKmCLDetEJB.edit(opkmcldet);
            }
            MovilidadUtil.addSuccessMessage("Exito, Checklist modificado");
            PrimeFaces.current().executeScript("PF('opKmDialog').hide();");
            PrimeFaces.current().ajax().update("msgs");
        } else {
            OperacionKmChecklist opCuatro = opKmCLEJB.findChecklistIgualByIdVehiculo(opKmChecklist.getIdVehiculo().getIdVehiculo(), opKmChecklist.getFecha(), 0);
            if (opCuatro != null) {
                MovilidadUtil.addErrorMessage("Error. Ya existe un regsitro para este vehÃ­culo con la fecha digitada");
                return;
            }
            //Consalta registro con fecha postrior a la enviada por parametro
            OperacionKmChecklist opCinco = opKmCLEJB.findChecklistByIdVehiculo(opKmChecklist.getIdVehiculo().getIdVehiculo(), opKmChecklist.getFecha(), 0, 1);
            if (opCinco != null) {
                MovilidadUtil.addErrorMessage("Error. No se puede registrar un nuevo Checklist con esta fecha, puesto que, existe un registro con fecha posterior en el sistema");
                return;
            }

            //Consalta registro con fecha anterior a la enviada por parametro
            OperacionKmChecklist opSeis = opKmCLEJB.findChecklistByIdVehiculo(opKmChecklist.getIdVehiculo().getIdVehiculo(), opKmChecklist.getFecha(), 0, 0);
            if (opSeis != null) {
                if (opKmChecklist.getKmInicial() < opSeis.getKmFinal()) {
                    MovilidadUtil.addErrorMessage("Error. Se ha encontrado que le Km inicial es menor al ultimo Km Final regsitrato para este vehÃ­culo. Valor minimo: " + opSeis.getKmFinal() + "Mtrs");
                    return;
                }
            }
            persistir.setCreado(new Date());
            persistir.setEstadoReg(0);
            persistir.setUsername(user.getUsername());
            persistir.setPathChecklist("/");
            persistir.setFecha(opKmChecklist.getFecha());
            persistir.setKmInicial(opKmChecklist.getKmInicial());
            persistir.setKmFinal(opKmChecklist.getKmFinal());
            persistir.setIdVehiculo(opKmChecklist.getIdVehiculo());

            opKmCLEJB.create(persistir);
            if (persistir.getIdOperacionKmChecklist() != null) {
                if (uploadFotoMB.getFile() != null) {
                    String path = MovilidadUtil.dirYYMMDD("OpKmChk", opKmChecklist.getFecha(), "ruta");
                    String pathCompleto = uploadFotoMB.GuardarFoto(persistir.getIdOperacionKmChecklist(), path, opKmChecklist.getIdVehiculo().getCodigo());
                    persistir.setPathChecklist(pathCompleto);
                }
                opKmCLEJB.edit(persistir);
                for (OperacionKmChecklistDet opkmcldet : opKmChecklist.getOperacionKmChecklistDetList()) {
                    opkmcldet.setIdOperacionKmChecklist(persistir);
                    opKmCLDetEJB.create(opkmcldet);
                }
                MovilidadUtil.addSuccessMessage("Exito, Checklist guardado");
                opKmChecklist = new OperacionKmChecklist();
                opKmChecklist.setOperacionKmChecklistDetList(new ArrayList<OperacionKmChecklistDet>());
                flagVhcl = false;
                uploadFotoMB.setFlag(false);
                PrimeFaces.current().ajax().update("frmOpKm");
            }
//        }
//            else {
//                MovilidadUtil.addSuccessMessage("No hay Archivo");
//            }
        init();
    }

}

public void prepDownloadLocal(String path) throws Exception {
        viewDMB.setDownload(MovilidadUtil.prepDownload(path));
    }

    public void editar(OperacionKmChecklist obj) {
        opKmChecklist = new OperacionKmChecklist();
        flagOpkmCl = true;
        opKmChecklist = obj;
        codigoV = obj.getIdVehiculo().getCodigo();
        opKmChecklist.setOperacionKmChecklistDetList(obj.getOperacionKmChecklistDetList());
        listOpKmCLDetEliminar = new ArrayList<>();
    }

    public void eliminarListaDialog(OperacionKmChecklistDet objt) {
        if (flagOpkmCl) {
            listOpKmCLDetEliminar.add(objt);
        }
        opKmChecklist.getOperacionKmChecklistDetList().remove(objt);
        PrimeFaces.current().ajax().update("frmOpKm:tblOPKmDialog");
    }

    public void findEmpleadoByCodigoT() {
        if (codigoTransMi != null && !"".equals(codigoTransMi) && (!codigoTransMi.isEmpty())) {
            for (OperacionKmChecklistDet op : opKmChecklist.getOperacionKmChecklistDetList()) {
                if (Integer.parseInt(codigoTransMi) == op.getIdEmpleado().getCodigoTm()) {
                    MovilidadUtil.addAdvertenciaMessage("Ya Agrego este operador");
                    return;
                }
            }
            empl = emplEJB.findCampo("codigoTm", codigoTransMi, 0);
            if (empl != null) {
                codigoTransMi = null;
                opKmChecklist.getOperacionKmChecklistDetList().add(new OperacionKmChecklistDet("Jesus", new Date(), 0, empl));
                PrimeFaces.current().ajax().update("frmOpKm:tblOPKmDialog");
            } else {
                MovilidadUtil.addErrorMessage("No Existe Operador Con el codigo Digitado");
            }
        } else {
            MovilidadUtil.addAdvertenciaMessage("Digite el codigo del operador");
        }
    }

    public void findVehiculoByCod() {
        if (codigoV != null && !"".equals(codigoV) && !(codigoV.isEmpty())) {
            vehiculo = vehiculoEJB.getVehiculo(codigoV,0);
            if (vehiculo != null) {
                opKmChecklist.setIdVehiculo(vehiculo);
                codigoV = vehiculo.getCodigo();
                flagVhcl = true;
                PrimeFaces.current().ajax().update("frmOpKm:txtCodigoBusDialog");
                PrimeFaces.current().ajax().update("frmOpKm:flagVhcl");
            } else {
                MovilidadUtil.addErrorMessage("No Existe Vehiculo Con el codigo Digitado");
            }

        } else {
            MovilidadUtil.addAdvertenciaMessage("Digite el codigo del VehÃƒÂ­culo");
        }
    }

    public OperacionKmChecklist getOpKmChecklist() {
        return opKmChecklist;
    }

    public void setOpKmChecklist(OperacionKmChecklist opKmChecklist) {
        this.opKmChecklist = opKmChecklist;
    }

    public List<OperacionKmChecklist> getListOpKmCL() {
        return listOpKmCL;
    }

    public void setListOpKmCL(List<OperacionKmChecklist> listOpKmCL) {
        this.listOpKmCL = listOpKmCL;
    }

    public UploadFotoJSFManagedBean getUploadFotoMB() {
        return uploadFotoMB;
    }

    public void setUploadFotoMB(UploadFotoJSFManagedBean uploadFotoMB) {
        this.uploadFotoMB = uploadFotoMB;
    }

    public Empleado getEmpl() {
        return empl;
    }

    public void setEmpl(Empleado empl) {
        this.empl = empl;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getCodigoTransMi() {
        return codigoTransMi;
    }

    public void setCodigoTransMi(String codigoTransMi) {
        this.codigoTransMi = codigoTransMi;
    }

    public String getCodigoV() {
        return codigoV;
    }

    public void setCodigoV(String codigoV) {
        this.codigoV = codigoV;
    }

    public boolean isFlagVhcl() {
        return flagVhcl;
    }

    public void setFlagVhcl(boolean flagVhcl) {
        this.flagVhcl = flagVhcl;
    }

    public boolean isFlagOpkmCl() {
        return flagOpkmCl;
    }

    public void setFlagOpkmCl(boolean flagOpkmCl) {
        this.flagOpkmCl = flagOpkmCl;
    }

}
