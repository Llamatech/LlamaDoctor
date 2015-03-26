package com.llama.tech.doctor.mundo;

import java.io.Serializable;

/**
 * Clase que modela la excepción del sistema
 */
public class LlamaCitaException extends Exception implements Serializable{
	
	public LlamaCitaException(String m)
	{
		super(m);
	}

}
