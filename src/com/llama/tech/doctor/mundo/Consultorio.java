package com.llama.tech.doctor.mundo;

import com.llama.tech.utils.dict.LlamaDict;
import com.llama.tech.utils.list.LlamaArrayList;
import com.llama.tech.utils.tree.LlamaAVLTree;

public class Consultorio implements Comparable<Consultorio> {

	private String nombre;
	private LlamaCitas.Sexo sexo;
	private String direccion;
	private String localidad;
	private String region;
	private int postCode;
	private String telefono;
	private String horasDeAtencion;
	private double latitud;
	private double longitud;
	private String[] seguros;
	private int añosExp;
	
	private LlamaDict<String, HorarioCitaDia> horarios;
	private LlamaAVLTree<Cita> citas;
	
	
	public Consultorio(String nombre, LlamaCitas.Sexo sexo, String direccion,
			String localidad, String region, int postCode, String telefono,
			String citas, double latitud, double longitud, String[] seguros,
			int añosExp) {
		super();
		this.nombre = nombre;
		this.sexo = sexo;
		this.direccion = direccion;
		this.localidad = localidad;
		this.region = region;
		this.postCode = postCode;
		this.telefono = telefono;
		this.horasDeAtencion = citas;
		this.latitud = latitud;
		this.longitud=longitud;
		this.seguros = seguros;
		this.añosExp = añosExp;
	}


	public String getNombre() {
		return nombre;
	}


	public LlamaCitas.Sexo getSexo() {
		return sexo;
	}


	public String getDireccion() {
		return direccion;
	}


	public String getLocalidad() {
		return localidad;
	}


	public String getRegion() {
		return region;
	}


	public int getPostCode() {
		return postCode;
	}


	public String getTelefono() {
		return telefono;
	}


	public String getCitas() {
		return horasDeAtencion;
	}


	public double getLatitud() {
		return latitud;
	}


	public double getLongitud() {
		return longitud;
	}


	public String[] getSeguros() {
		return seguros;
	}


	public int getAñosExp() {
		return añosExp;
	}


	@Override
	public int compareTo(Consultorio o) {
		// TODO Auto-generated method stub
		return nombre.compareTo(o.getNombre());
	}
	
	
	
	

}
