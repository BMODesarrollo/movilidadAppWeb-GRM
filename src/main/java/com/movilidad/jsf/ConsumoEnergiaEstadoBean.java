package com.movilidad.jsf;

import com.movilidad.ejb.ConsumoEnergiaEstadoFacadeLocal;
import com.movilidad.model.ConsumoEnergiaEstado;
import com.movilidad.security.UserExtended;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author solucionesit
 */
@Named(value = "consumoEnergiaEstadoBean")
@ViewScoped
public class ConsumoEnergiaEstadoBean implements Serializable {

	@EJB
	private ConsumoEnergiaEstadoFacadeLocal consumoEnergiaEstadoEjb;

	private ConsumoEnergiaEstado consumoEnergiaEstado;
	private ConsumoEnergiaEstado selected;
	private String nombre;
	
	UserExtended user = (UserExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	private boolean isEditing;

	private List<ConsumoEnergiaEstado> lstConsumoEnergiaEstados;

	@PostConstruct
	public void init() {
		lstConsumoEnergiaEstados = consumoEnergiaEstadoEjb.findAllByEstadoReg();
	}

	public void nuevo() {
		isEditing = false;
		nombre = "";
		consumoEnergiaEstado = new ConsumoEnergiaEstado();
		selected = null;
	}

	public void editar() {
		isEditing = true;
		nombre = selected.getNombre();
		consumoEnergiaEstado = selected;
	}
	
	@Transactional
	public void guardar(){
		guardarTransactional();
	}
	
	private void guardarTransactional() {
		String mensajeValidacion = validarDatos();
		
		if (mensajeValidacion == null) {
			consumoEnergiaEstado.setNombre(nombre);
			if (isEditing) {
				consumoEnergiaEstado.setUsername(user.getUsername());
				consumoEnergiaEstado.setModificado(MovilidadUtil.fechaCompletaHoy());
				consumoEnergiaEstadoEjb.edit(consumoEnergiaEstado);
				
				MovilidadUtil.hideModal("wlvConsumoEnergiaEstado");
				MovilidadUtil.addSuccessMessage("Registro actualizado con éxito");
			}else{
				consumoEnergiaEstado.setUsername(user.getUsername());
				consumoEnergiaEstado.setEstadoReg(0);
				consumoEnergiaEstado.setCreado(MovilidadUtil.fechaCompletaHoy());
				consumoEnergiaEstadoEjb.create(consumoEnergiaEstado);
				
				lstConsumoEnergiaEstados.add(consumoEnergiaEstado);
				MovilidadUtil.addSuccessMessage("Registro guardado con éxito");
				nuevo();
			}
		}else{
			MovilidadUtil.addErrorMessage(mensajeValidacion);
		}
	}

	private String validarDatos() {
		if (isEditing) {
			if (consumoEnergiaEstadoEjb.findByNombre(selected.getIdConsumoEnergiaEstado(), nombre) != null) {
				return "YA existe un registro con el nombre ingresado";
			}
		} else {
			if (!lstConsumoEnergiaEstados.isEmpty()) {
				if (consumoEnergiaEstadoEjb.findByNombre(0, nombre) != null) {
					return "YA existe un registro con el nombre ingresado";
				}
			}
		}

		return null;
	}

	public ConsumoEnergiaEstado getConsumoEnergiaEstado() {
		return consumoEnergiaEstado;
	}

	public void setConsumoEnergiaEstado(ConsumoEnergiaEstado consumoEnergiaEstado) {
		this.consumoEnergiaEstado = consumoEnergiaEstado;
	}

	public ConsumoEnergiaEstado getSelected() {
		return selected;
	}

	public void setSelected(ConsumoEnergiaEstado selected) {
		this.selected = selected;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isIsEditing() {
		return isEditing;
	}

	public void setIsEditing(boolean isEditing) {
		this.isEditing = isEditing;
	}

	public List<ConsumoEnergiaEstado> getLstConsumoEnergiaEstados() {
		return lstConsumoEnergiaEstados;
	}

	public void setLstConsumoEnergiaEstados(List<ConsumoEnergiaEstado> lstConsumoEnergiaEstados) {
		this.lstConsumoEnergiaEstados = lstConsumoEnergiaEstados;
	}
}
