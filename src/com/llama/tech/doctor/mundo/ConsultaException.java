package com.llama.tech.doctor.mundo;

import java.io.Serializable;

/**
 * Excepcion que maneja los errores de consulta
 */
public class ConsultaException extends Exception implements Serializable {
	
	public ConsultaException(String m)
	{
		super(m);
	}

}
