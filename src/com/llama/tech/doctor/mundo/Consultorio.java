package com.llama.tech.doctor.mundo;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;

import com.llama.tech.utils.dict.Dictionary;
import com.llama.tech.utils.dict.LlamaDict;
import com.llama.tech.utils.dict.LlamaDict.UnhashableTypeException;
import com.llama.tech.utils.list.LlamaArrayList;
import com.llama.tech.utils.list.LlamaIterator;
import com.llama.tech.utils.tree.LlamaAVLTree;
import com.llama.tech.utils.tree.Tree;

/**
 * Esta clase modela un consultorio de la aplicación
 */
public class Consultorio implements Comparable<Consultorio>, Serializable {

	/**
	 * Nombre del doctor que trabaja en el consultorio
	 */
	private String nombre;

	/**
	 * Sexo del doctor del consultorio
	 */
	private LlamaCitas.Sexo sexo;

	/**
	 * Direccion del consultorio
	 */
	private String direccion;

	/**
	 * Localidad del consultorio
	 */
	private String localidad;

	/**
	 * Region del consultorio
	 */
	private String region;

	/**
	 * Codigo postal del consultorio
	 */
	private int postCode;

	/**
	 * Telefono del consultorio
	 */
	private String telefono;

	/**
	 * Latitud del consultorio
	 */
	private double latitud;

	/**
	 * Longitud del consultorio
	 */
	private double longitud;

	/**
	 * Seguros del consultorio
	 */
	private String[] seguros;

	/**
	 * Años de experiencia del doctor que trabaja en el consultorio
	 */
	private int aniosExp;

	/**
	 * Horarios de atención del consultorio, donde la llave es el día de la semana
	 */
	private Dictionary<DayOfWeek, HorarioCitaDia> horarios;

	/**
	 * Citas agendadas del consultorio
	 */
	private Tree<Cita> citas;


	/**
	 * Metodo constructor de la clase consultorio
	 * post: se crea el consultorio
	 * @param nombre Nombre dle doctor
	 * @param sexo Sexo del doctor . sexo female ó male.
	 * @param direccion Direccion del consultorio
	 * @param localidad Localidad del consultorio
	 * @param region Region del consultorio
	 * @param postCode Codigo postal del consultorio
	 * @param telefono Telefono del consultprio
	 * @param horasAtencion Horas de atencion del consultorio en formato:
	 * 		//"{""monday"":[[""9:00"",""17:00""]],""tuesday"":[[""7:00"",""18:00""]],""wednesday"":[[""10:00"",""17:00""]],""thursday"":[[""7:00"",""18:00""]],""friday"":[[""7:00"",""16:00""]],""saturday"":[[""7:00"",""15:00""]]}"
	 * @param latitud Latitud de la ubicacion
	 * @param longitud Longitud de la ubicacion
	 * @param seguros Seguros asociados al consultorio
	 * @param añosExp Años de experiencia del consultorio
	 */
	public Consultorio(String nombre, LlamaCitas.Sexo sexo, String direccion,
			String localidad, String region, int postCode, String telefono,
			String horasAtencion, double latitud, double longitud, String[] seguros,
			int añosExp){

		horarios = new LlamaDict<DayOfWeek, HorarioCitaDia>(7);
		citas = new LlamaAVLTree<Cita>();
		this.nombre = nombre;
		this.sexo = sexo;
		this.direccion = direccion;
		this.localidad = localidad;
		this.region = region;
		this.postCode = postCode;
		this.telefono = telefono;
		this.latitud = latitud;
		this.longitud=longitud;
		this.seguros = seguros;
		this.aniosExp = añosExp;

		LocalTime sevenam = LocalTime.of(7, 0);
		LocalTime sixpm = LocalTime.of(18, 0);
		LocalTime trespm = LocalTime.of(15, 0);

		HorarioCitaDia sem = new HorarioCitaDia(sevenam, sixpm);
		HorarioCitaDia sab = new HorarioCitaDia(sevenam, trespm);

		if(horasAtencion.equals(""))
		{

			try
			{
				horarios.addEntry(DayOfWeek.MONDAY, sem);
				horarios.addEntry(DayOfWeek.TUESDAY, sem);
				horarios.addEntry(DayOfWeek.WEDNESDAY, sem);
				horarios.addEntry(DayOfWeek.THURSDAY, sem);
				horarios.addEntry(DayOfWeek.FRIDAY, sem);
				horarios.addEntry(DayOfWeek.SATURDAY, sab);
				horarios.addEntry(DayOfWeek.SUNDAY, new HorarioCitaDia(LocalTime.of(23, 59), LocalTime.of(23, 59)));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{

			horasAtencion=horasAtencion.replace("\"", "");
			horasAtencion=horasAtencion.replace("[", "");
			horasAtencion=horasAtencion.replace("]", "");
			horasAtencion = horasAtencion.substring(1, horasAtencion.length()-2);

			HorarioCitaDia lun=null;
			HorarioCitaDia mar=null;
			HorarioCitaDia mie=null;
			HorarioCitaDia jue=null;
			HorarioCitaDia vie=null;

			String[] ss = horasAtencion.split(",");

			for(int i=0;i<ss.length;i++)
			{
				String s=ss[i];
				if(s.startsWith("monday"))
				{
					lun = new HorarioCitaDia(LocalTime.of(Integer.parseInt(s.split(":")[1]), Integer.parseInt(s.split(":")[2])), LocalTime.of(Integer.parseInt(ss[i+1].split(":")[0]), Integer.parseInt(ss[i+1].split(":")[1])));
					i++;
				}
				if(s.startsWith("tuesday"))
				{
					mar = new HorarioCitaDia(LocalTime.of(Integer.parseInt(s.split(":")[1]), Integer.parseInt(s.split(":")[2])), LocalTime.of(Integer.parseInt(ss[i+1].split(":")[0]), Integer.parseInt(ss[i+1].split(":")[1])));
					i++;
				}
				if(s.startsWith("wednesday"))
				{
					mie = new HorarioCitaDia(LocalTime.of(Integer.parseInt(s.split(":")[1]), Integer.parseInt(s.split(":")[2])), LocalTime.of(Integer.parseInt(ss[i+1].split(":")[0]), Integer.parseInt(ss[i+1].split(":")[1])));
					i++;
				}
				if(s.startsWith("thursday"))
				{
					jue = new HorarioCitaDia(LocalTime.of(Integer.parseInt(s.split(":")[1]), Integer.parseInt(s.split(":")[2])), LocalTime.of(Integer.parseInt(ss[i+1].split(":")[0]), Integer.parseInt(ss[i+1].split(":")[1])));
					i++;
				}
				if(s.startsWith("friday"))
				{
					vie = new HorarioCitaDia(LocalTime.of(Integer.parseInt(s.split(":")[1]), Integer.parseInt(s.split(":")[2])), LocalTime.of(Integer.parseInt(ss[i+1].split(":")[0]), Integer.parseInt(ss[i+1].split(":")[1])));
					i++;
				}
				if(s.startsWith("saturday"))
				{
					sab = new HorarioCitaDia(LocalTime.of(Integer.parseInt(s.split(":")[1]), Integer.parseInt(s.split(":")[2])), LocalTime.of(Integer.parseInt(ss[i+1].split(":")[0]), Integer.parseInt(ss[i+1].split(":")[1])));
					i++;
				}
			}

			lun = lun==null?sem:lun;
			mar = mar==null?sem:mar;
			mie = mie==null?sem:mie;
			jue = jue==null?sem:jue;
			vie = vie==null?sem:vie;

			try 
			{
				horarios.addEntry(DayOfWeek.MONDAY, lun);
				horarios.addEntry(DayOfWeek.TUESDAY, mar);
				horarios.addEntry(DayOfWeek.WEDNESDAY, mie);
				horarios.addEntry(DayOfWeek.THURSDAY, jue);
				horarios.addEntry(DayOfWeek.FRIDAY, vie);
				horarios.addEntry(DayOfWeek.SATURDAY, sab);
				horarios.addEntry(DayOfWeek.SUNDAY, new HorarioCitaDia(LocalTime.of(23, 59), LocalTime.of(23, 59)));
			} 
			catch (UnhashableTypeException e) 
			{
				e.printStackTrace();
			}



		}
	}


	/**
	 * Retorna el nombre del doctor
	 * @return nombre del doctor
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Retorna el sexo del doctor 
	 * @return sexo
	 */
	public LlamaCitas.Sexo getSexo() {
		return sexo;
	}


	/**
	 * Retorna la direccion del consultorio
	 * @return direccion del consultorio
	 */
	public String getDireccion() {
		return direccion;
	}


	/**
	 * Retorna la localidad del consultorio
	 * @return localidad consultorio
	 */
	public String getLocalidad() {
		return localidad;
	}


	/**
	 * Retorna la region del consultorio
	 * @return region del consultorio
	 */
	public String getRegion() {
		return region;
	}


	/**
	 * Retorna el codigo postal del ocnsultorio
	 * @return codigo pstal del consultorio
	 */
	public int getPostCode() {
		return postCode;
	}

	/**
	 * Retorna el telefono del consultorio
	 * @return telefono del consultorio
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Latitud del consultorio
	 * @return latitud del consultorio
	 */
	public double getLatitud() {
		return latitud;
	}

	/**
	 * Longitud del consultorio
	 * @return longitud del consultorio
	 */
	public double getLongitud() {
		return longitud;
	}

	/**
	 * Seguros del consultorio
	 * @return seguros del consultorio
	 */
	public String[] getSeguros() {
		return seguros;
	}

	/**
	 * Años de experiencia del doctor del consultorio
	 * @return años de experiencia
	 */
	public int getAniosExp() {
		return aniosExp;
	}

	/**
	 * Retorna las citas de una fecha especifica
	 * @param fecha
	 * @return arreglo de citas de ese dia
	 */
	public LlamaArrayList<Cita> citasEnFecha(LocalDate fecha)
	{
		LlamaArrayList<Cita> ret = new LlamaArrayList<Cita>(15);


		for(Cita c:citas)
		{
			if (c.getDia().isEqual(fecha))
			{
				ret.addAlFinal(c);
			}
		}

		return ret;
	}

	/**
	 * Agendar cita
	 * @param cita
	 * @return true si se agendo la cita false de lo contrario
	 */
	public boolean agendarCita(Cita cita)
	{
		Cita c = citas.get(cita);
		if(c!=null)
		{
			return false;
		}
		else
		{
			citas.add(cita);
			return true;
		}
	}

	/**
	 * Eliminar una cita
	 * @param cita
	 * @return true si se elimino la cita false de lo ocntrario
	 */
	public boolean eliminarCita(Cita cita)
	{
		return citas.remove(cita)==null?false:true;
	}

	/**
	 * Retorna el horario de un dia especifico
	 * @param dia que se desea el horario
	 * @return Horario de dia especificado
	 */
	public HorarioCitaDia darHorarioDia(DayOfWeek d)
	{
		return horarios.getValue(d);
	}


	@Override
	public int compareTo(Consultorio o) {
		return nombre.compareTo(o.getNombre());
	}
	
	@Override
	public String toString()
	{
		return nombre;
	}


}
