package com.llama.tech.doctor.mundo;

import java.io.Serializable;

/**
 * Excepcion que maneja los errores de usuario
 */
public class UsuarioException extends Exception implements Serializable{
	
	public UsuarioException(String mensaje)
	{
		super(mensaje);
	}

}
