package com.llama.tech.doctor.comparadores;

import java.util.Comparator;

import com.llama.tech.doctor.mundo.Consultorio;

public class ComparadorConsultorioEstado implements Comparator<Consultorio> {

	@Override
	public int compare(Consultorio o1, Consultorio o2) {
		return o1.getRegion().compareTo(o2.getRegion());
	}
	
	

}
