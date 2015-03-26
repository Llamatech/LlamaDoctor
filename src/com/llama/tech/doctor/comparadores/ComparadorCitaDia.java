package com.llama.tech.doctor.comparadores;

import java.util.Comparator;

import com.llama.tech.doctor.mundo.Cita;

public class ComparadorCitaDia implements Comparator<Cita> {

	@Override
	public int compare(Cita o1, Cita o2) {
		return o1.getDia().compareTo(o2.getDia());
	}

}
