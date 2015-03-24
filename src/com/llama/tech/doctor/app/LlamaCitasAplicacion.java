package com.llama.tech.doctor.app;

import javax.swing.JPanel;

<<<<<<< HEAD
import com.llama.tech.doctor.app.gui.MainView;
=======
>>>>>>> e0bf8698b33a4127972e26bd9a0b45611d5a4f60
import com.llama.tech.doctor.maps.PruebaMapas;
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
<<<<<<< HEAD
	private MainView panel;
=======
	private PruebaMapas panel;
>>>>>>> e0bf8698b33a4127972e26bd9a0b45611d5a4f60
	
	
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
<<<<<<< HEAD
		//mundo = new LlamaCitas(core);
		panel = new MainView();
=======
		mundo = new LlamaCitas(core);
		panel = new PruebaMapas(mundo);
>>>>>>> e0bf8698b33a4127972e26bd9a0b45611d5a4f60
		
	}

	@Override
	public void terminarEjecucion() {
		
		
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
