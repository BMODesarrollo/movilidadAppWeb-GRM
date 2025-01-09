package com.movilidad.jsf;

import com.movilidad.ejb.VehiculoFacadeLocal;
import com.movilidad.ejb.VehiculoOdometroFacadeLocal;
import com.movilidad.model.Vehiculo;
import com.movilidad.model.VehiculoOdometro;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Carlos Ballestas
 */
@Named(value = "vehiculoOdometroBean")
@ViewScoped
public class VehiculoOdometroManagedBean implements Serializable {

    @EJB
    private VehiculoOdometroFacadeLocal vehiculoOdometroEjb;
    @EJB
    private VehiculoFacadeLocal vehiculoEjb;

    private VehiculoOdometro vehiculoOdometro;
    private Date fecha;
    private UploadedFile uploadedFile;
    UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    private List<VehiculoOdometro> lstVehiculoOdometros;

    public void buscar() {
        if (fecha == null) {
            MovilidadUtil.addErrorMessage("Debe seleccionar una fecha");
            return;
        }
        lstVehiculoOdometros = vehiculoOdometroEjb.findAll(fecha);
        if (lstVehiculoOdometros == null || lstVehiculoOdometros.isEmpty()) {
            MovilidadUtil.addErrorMessage("No se encontraron registros");
        }
    }

    @Transactional
    public void cargarArchivo() throws IOException {
        try {
            Date auxDate = null;
            List<VehiculoOdometro> lstVehiculoOdometroAux = new ArrayList<>();
            String auxCodigo = "";
            double auxKm = 0;

            if (uploadedFile != null) {
                String path = Util.saveFile(uploadedFile, 0, "vehiculo_odometro");
                FileInputStream file = new FileInputStream(new File(path));

                XSSFWorkbook wb = new XSSFWorkbook(file);
                XSSFSheet sheet = wb.getSheetAt(0);

                int numFilas = sheet.getLastRowNum();

                for (int a = 0; a <= numFilas; a++) {
                    Row fila = sheet.getRow(a);
                    int numCols = fila.getLastCellNum();
                    vehiculoOdometro = new VehiculoOdometro();

                    for (int b = 0; b < numCols; b++) {
                        Cell celda = fila.getCell(b);
                        if (celda != null) {

                            switch (celda.getCellTypeEnum().toString()) {
                                case "NUMERIC":
                                    if (DateUtil.isCellDateFormatted(celda)) {
                                        auxDate = celda.getDateCellValue();
                                    } else {
                                        auxKm = celda.getNumericCellValue();
                                    }
                                    break;
                                case "STRING":
                                    auxCodigo = celda.getStringCellValue();
                                    break;
                            }
                        }
                    }
                    if (!vehiculoOdometroEjb.verificarSubida(auxDate)) {
                        if (Util.deleteFile(path)) {
                            vehiculoOdometro = null;
                            auxDate = null;
                            auxCodigo = "";
                            auxKm = 0;
                            this.uploadedFile = null;
                            MovilidadUtil.addErrorMessage("Ya existen registros cargados para esa fecha ");
                            PrimeFaces.current().ajax().update(":frmCargaVehiculoOdometro");
                            return;
                        }
                    }

                    if (auxDate != null && auxCodigo != null && auxKm >= 0 && a > 0) {
                        int sumOdometro = 0;
                        Vehiculo vehiculo = vehiculoEjb.getVehiculo(auxCodigo, 0);
                        vehiculoOdometro.setFecha(Util.toDate(Util.dateFormat(auxDate)));
                        vehiculoOdometro.setIdVehiculo(vehiculo);
                        vehiculoOdometro.setOdometro((int) auxKm * 1000);

                        sumOdometro = vehiculo.getOdometro() + vehiculoOdometro.getOdometro();
                        vehiculo.setOdometro(sumOdometro);
                        vehiculoEjb.edit(vehiculo);

                        vehiculoOdometro.setEstadoReg(0);
                        vehiculoOdometro.setUsername(user.getUsername());
                        vehiculoOdometro.setCreado(new Date());
                        lstVehiculoOdometroAux.add(vehiculoOdometro);
                    }
                }
                PrimeFaces current = PrimeFaces.current();

                this.lstVehiculoOdometros = lstVehiculoOdometroAux;
                current.ajax().update(":frmVehiculoOdometro:dtVehiculoOdometro");
                this.uploadedFile = null;
                if (Util.deleteFile(path)) {
                    for (VehiculoOdometro v : lstVehiculoOdometroAux) {
                        vehiculoOdometroEjb.create(v);
                    }
                }
                current.ajax().update(":frmVehiculoOdometro:panelFecha");
                fecha = auxDate;
                MovilidadUtil.addSuccessMessage("Archivo cargado correctamente");
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        this.uploadedFile = event.getFile();
        if (!validar()) {
            cargarArchivo();
        }
    }

    private boolean validar() {
        if (uploadedFile == null) {
            MovilidadUtil.addErrorMessage("Debe cargar un archivo");
            return true;
        }
        return false;
    }

    public VehiculoOdometro getVehiculoOdometro() {
        return vehiculoOdometro;
    }

    public void setVehiculoOdometro(VehiculoOdometro vehiculoOdometro) {
        this.vehiculoOdometro = vehiculoOdometro;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<VehiculoOdometro> getLstVehiculoOdometros() {
        return lstVehiculoOdometros;
    }

    public void setLstVehiculoOdometros(List<VehiculoOdometro> lstVehiculoOdometros) {
        this.lstVehiculoOdometros = lstVehiculoOdometros;
    }

    public UserExtended getUser() {
        return user;
    }

    public void setUser(UserExtended user) {
        this.user = user;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
