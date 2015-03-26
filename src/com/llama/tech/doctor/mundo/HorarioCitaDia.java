package com.llama.tech.doctor.mundo;

import java.io.Serializable;
import java.time.LocalTime;

import com.llama.tech.utils.list.LlamaArrayList;

/**
 * Esta clase represente el horario de un dia de la semana
 */
public class HorarioCitaDia implements Comparable<HorarioCitaDia>, Serializable{

	/**
	 * Atributo que representa la hora de inicio del horario
	 */
	private LocalTime horaInicio;
	
	/**
	 * Atributo que representa la hora de fin del horario
	 */
	private LocalTime horaFin;
	
	/**
	 * Metodo constructor
	 * @param inicio hora inicio
	 * @param fin hora fin
	 */
	public HorarioCitaDia(LocalTime inicio, LocalTime fin)
	{
		horaInicio=inicio;
		horaFin = fin;
	}

	/**
	 * Genera las citas disponibles en ese dia en intervalos de 20 minutos
	 * @return arreglo de horarios disponibles
	 */
	public LlamaArrayList<LocalTime> generarCitasDisponibles()
	{
		LlamaArrayList<LocalTime> ret = new LlamaArrayList<LocalTime>(40);
		int minutos=horaInicio.getMinute();
		int minutosComp=minutos;
		int horis= horaInicio.getHour();
		int horaComp = horis;

		for(;;minutos+=20)
		{
			if(minutos>59)
			{
				minutos = minutos-60;
				minutosComp=minutos;
				horis++;
				horaComp=horis;
			}
			
			minutosComp+=20;
			
			if(minutosComp>59)
			{
				minutosComp-=60;
				horaComp++;
			}
			

			LocalTime comparar = LocalTime.of(horaComp, minutosComp);
			if(comparar.compareTo(horaFin)>=0)
			{
				break;
			}
			LocalTime lt = LocalTime.of(horis, minutos);
			ret.addAlFinal(lt);

		}
		
		return ret;

	}

	@Override
	public int compareTo(HorarioCitaDia o) {
		// TODO Auto-generated method stub
		return 0;
	}


}
