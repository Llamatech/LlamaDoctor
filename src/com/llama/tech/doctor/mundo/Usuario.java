package com.llama.tech.doctor.mundo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Iterator;

import com.llama.tech.doctor.comparadores.ComparadorCitaDia;
import com.llama.tech.utils.list.LlamaArrayList;
import com.llama.tech.utils.tree.LlamaAVLTree;
import com.llama.tech.utils.tree.Tree;

/**
 * Clase que modela un usuario
 */
public class Usuario implements Comparable<Usuario>, Serializable{
	
	/**
	 * Nombre del usuario
	 */
	private String nombreUsuario;
	
	/**
	 * Clave del usuario
	 */
	private String clave;
	
	/**
	 * Email del usuario
	 */
	private String email;
	
	/**
	 * Id del usuario
	 */
	private String id;
	
	/**
	 * Ubicacion actual del usuario
	 */
	public String ubicacionActual;
	
	/**
	 * Arreglo de ubicaciones anteriores
	 */
	private LlamaArrayList<String> ubicacionesAnteriores;
	
	/**
	 * Tipo de ubicacion actual
	 */
	public String tipoUbicacion;
	
	/**
	 * Citas agendadas por el usuario
	 */
	private Tree<Cita> citas;
	
	/**
	 * Metodo constructor sel usuario
	 * @param nombreUsuario
	 * @param clave
	 * @param email
	 * @param id
	 */
	public Usuario(String nombreUsuario, String clave, String email, String id) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.clave = clave;
		this.email = email;
		this.id = id;
		ubicacionesAnteriores = new LlamaArrayList<String>(10);
		citas = new LlamaAVLTree<Cita>();
	}
	
	/**
	 * Cambia la ubicacion actual del usuario
	 * @param ubicacionActual nueva ubicacion
	 * @param tipo tipo de la nueva ubicacion
	 */
	public void setUbicacionActual(String ubicacionActual, String tipo) {
		this.ubicacionActual = ubicacionActual;
		tipoUbicacion = tipo;
		ubicacionesAnteriores.add(0, ubicacionActual);
	}
	
	/**
	 * Agenda una cita para el usuario
	 * @param cita Cita a agendar
	 * @return true si se agendo false de lo contrario
	 */
	public boolean agendarCita(Cita cita)
	{
		for(Cita c: citas)
		{
			if(c.getDia().isEqual(cita.getDia()))
			{
				if(c.darConsultorio().compareTo(cita.darConsultorio())==0)
				{
					return false;
				}
			}
		}
		citas.add(cita);
		return true;
		
	}
	
	/**
	 * Elimina una cita del usuario
	 * @param cita cita a eliminar
	 * @return true si la elimino false de lo contrario
	 */
	public Cita eliminarCita(LocalDate fecha, Consultorio cons)
	{
		Cita cita = null;
		for(Cita c: citas)
		{
			if(c.getDia().isEqual(fecha))
			{
				if(c.darConsultorio().compareTo(cons)==0)
				{
					cita = c;
				}
			}
		}
		if(cita!=null)
		{
			return citas.remove(cita);
		}
		else
			return null;
	}
	
	/**
	 * Retorna un arreglo con las ubicciones anteriores
	 * @return arreglo con ubicaciones anteriores
	 */
	public LlamaArrayList<String> getAnteriores()
	{
		return ubicacionesAnteriores;
	}

	/**
	 * Retorna un arreglocon las citas entre las fechas dadas
	 * @param inicio fecha inicio de consulta
	 * @param fin fecha fin de consulta
	 * @return arreglo con citas
	 */
	public LlamaArrayList<Cita> darCitasFechas(LocalDate inicio, LocalDate fin)
	{
		LlamaArrayList<Cita> ret = new LlamaArrayList<Cita>(20);
		
		for(Cita c:citas)
		{
			if((c.getDia().isAfter(inicio)||c.getDia().isEqual(inicio))&&(c.getDia().isBefore(fin)||c.getDia().isEqual(fin)))
			{
				ret.addAlFinal(c);
			}
		}
		
		
		return ret;
	}

	/**
	 * Retorna el nombre del usuario
	 * @return nombre del usuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}


	/**
	 * Retorna la clave del usario
	 * @return clave del usuario
	 */
	public String getClave() {
		return clave;
	}


	/**
	 * Retorna el email del usario
	 * @return email del usuario
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * Retorna el id del usario
	 * @return id del usuario
	 */
	public String getId() {
		return id;
	}


	/**
	 * Retorna la ubicacion actual del usario
	 * @return ubicacion actual del usuario
	 */
	public String getUbicacionActual() {
		return ubicacionActual;
	}


	/**
	 * Retorna las citas del usario
	 * @return Arreglo de citas del usuario
	 */
	public Tree<Cita> getCitas() {
		return citas;
	}



	@Override
	public int compareTo(Usuario o) {
		return id.compareTo(o.getId());
	}

}
