package com.llama.tech.doctor.app;

import javax.swing.JPanel;

import com.llama.tech.doctor.app.gui.MainView;
import com.llama.tech.doctor.mundo.ConsultaException;
import com.llama.tech.doctor.mundo.LlamaCitas;

import uniandes.cupi2.cupIphone.componentes.IAplicacion;
import uniandes.cupi2.cupIphone.core.ICore;

public class LlamaCitasAplicacion implements IAplicacion {
	
	/**
	 * Referencia al core del cupiPhone, para localizar otros componentes y acceder al directorio de datos
	 */
	private ICore core;
	
	
	/**
	 * Panel principal del componente
	 */
	private MainView panel;
	
	
	/**
	 * Clase principal del mundo del componente
	 */
	private LlamaCitas mundo;
	
	/**
	 * Instancia de aplicacion
	 */
	private static LlamaCitasAplicacion instance;
	

	@Override
	public void cambiarCore(ICore arg0) {
		core = arg0;
		
	}

	@Override
	public Object darInstanciaModelo() {
		return mundo;
	}

	@Override
	public JPanel darPanelPrincipal() {
		return panel;
	}

	@Override
	public void iniciarEjecucion() {
		mundo = new LlamaCitas(core);
		panel = new MainView(mundo);
		
	}

	@Override
	public void terminarEjecucion() 
	{
        mundo.serialize();
	}
	
	public static IAplicacion darInstancia()
	{
		
	       if ( instance == null )
	        {
	            instance = new LlamaCitasAplicacion();
	        }
	        return instance;
	}
	
	

}
