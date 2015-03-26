package com.llama.tech.doctor.comparadores;

import java.util.Comparator;

import com.llama.tech.doctor.mundo.Consultorio;

/**
 * Esta clase compara dos consultorios dados los años de experiencia de sus doctores
 */
public class ComparadorExperiencia implements Comparator<Consultorio> {

	@Override
	public int compare(Consultorio o1, Consultorio o2) {
		return o1.getAniosExp()-o2.getAniosExp();
	}

}
