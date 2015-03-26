package com.llama.tech.doctor.mundo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Clase que modela una cita
 *
 */
public class Cita implements Comparable<Cita>, Serializable{
	
	/**
	 * Atributo para la hora de inicio
	 */
	private LocalDateTime horaInicio;
	
	/**
	 * Atributo para la hora de fin
	 */
	private LocalDateTime horaFin;
	
	/**
	 * Dia de la cita
	 */
	private LocalDate dia;
	
	/**
	 * Consultorio de la cita
	 */
	private Consultorio consultorio;
	
	private Usuario usuario;
	
	/**
	 * Metodo constructor de una cita
	 * @param hora hora de inicio de la cita
	 */
	public Cita(LocalDateTime hora, Consultorio cons, Usuario us)
	{
		usuario = us;
		consultorio=cons;
		horaInicio=hora;
		int minutos=hora.getMinute()+20;
		int horis= hora.getHour();
		if(minutos>59)
		{
			minutos = minutos-59;
			horis++;
			
		}
		
		horaFin=LocalDateTime.of(hora.getYear(), hora.getMonth(), hora.getDayOfMonth(), horis, minutos);
		dia = LocalDate.of(hora.getYear(), hora.getMonth(), hora.getDayOfMonth());
	}

	@Override
	public int compareTo(Cita o) {
		return horaInicio.compareTo(o.horaInicio);
	}
	
	/**
	 * Retorna el dia de la cita
	 * @return dia de la cita
	 */
	public LocalDate getDia()
	{
		return dia;
	}
	
	public Consultorio darConsultorio()
	{
		return consultorio;
	}

	public LocalDateTime getHoraInicio() {
		return horaInicio;
	}

	public LocalDateTime getHoraFin() {
		return horaFin;
	}
	
	public Usuario getusuario()
	{
		return usuario;
	}
	
	@Override
	public String toString()
	{
		return horaInicio.toString()+dia.toString();
	}
	
	
	

}
