package com.llama.tech.doctor.mundo;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Observable;

import com.llama.tech.utils.list.LlamaArrayList;
import com.llama.tech.utils.tree.LlamaAVLTree;

import jxl.*;
import jxl.read.biff.BiffException;

public class LlamaCitas extends Observable {

	public static final String RUTA_ARCHIVO="./data/consultorios.xls";
	
	private LlamaAVLTree<Consultorio> consultorios;
	
	private Calendar fechaActual;
	
	public LlamaCitas()
	{
		consultorios = new LlamaAVLTree<Consultorio>();
		try {
			cargarConsultorios();
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void cargarConsultorios() throws BiffException, IOException
	{
		

		Workbook workbook = Workbook.getWorkbook(new File(RUTA_ARCHIVO));
		Sheet hoja = workbook.getSheet(0);
		Cell[] nombres = hoja.getColumn(1);
		Cell[] sexo = hoja.getColumn(28);
		Cell[] direcciones = hoja.getColumn(2);
		Cell[] localidades = hoja.getColumn(5);
		Cell[] regiones = hoja.getColumn(6);
		Cell[] postCodes = hoja.getColumn(9);
		Cell[] telefonos = hoja.getColumn(11);
		Cell[] horasDeAtencion = hoja.getColumn(23);
		Cell[] latitudes = hoja.getColumn(13);
		Cell[] longitudes = hoja.getColumn(14);
		Cell[] seguros = hoja.getColumn(31);
		Cell[] experiencias = hoja.getColumn(30);
		
		int i=1;
		
		while(i<nombres.length)
		{
//			public Consultorio(String nombre, Sexo sexo, String direccion,
//					String localidad, String region, int postCode, int telefono,
//					String citas, double latitud, double longitud, String[] seguros,
//					int añosExp)
			
			Sexo sex = sexo[i].getContents().equals("Male")?Sexo.FEMENINO:Sexo.MASCULINO;
			String[] seguris = seguros[i].getContents().split(",");
			
			System.out.println(nombres[i].getContents()+" "+sexo[i].getContents()+" "+postCodes[i].getContents());
			double latitud = latitudes[i].getContents().equals("")?0:Double.parseDouble(latitudes[i].getContents());
			double longitud = longitudes[i].getContents().equals("")?0:Double.parseDouble(longitudes[i].getContents());
			int postCode = postCodes[i].getContents().equals("")?00000:Integer.parseInt(postCodes[i].getContents());
			
			
			Consultorio c= new Consultorio(nombres[i].getContents(), sex, direcciones[i].getContents(), localidades[i].getContents(),
					regiones[i].getContents(), postCode, 
					telefonos[i].getContents(), 
//					horasDeAtencion[i].getContents(),
					"",
					latitud, longitud,
					seguris, Integer.parseInt(experiencias[i].getContents()));
			
			consultorios.add(c);
			
			i++;
		}
		
	}
	
	public void darConsultoriosCercanos(double latitud, double longitud, double distancia)
	{
		
		LlamaArrayList<String> direcciones = new LlamaArrayList<String>(20);
		double cuantaDist =0;
		
		for(Consultorio c:consultorios)
		{
			System.out.println("MIRE"+c.getNombre());
			cuantaDist = Math.sqrt(Math.pow(Math.abs(latitud-c.getLatitud()), 2)+Math.pow(Math.abs(longitud-c.getLongitud()), 2));
			
			if(cuantaDist<=distancia)
			{
				direcciones.addAlFinal(c.getLatitud()+","+c.getLongitud());
			}
		}
		
		String[] lugares= new String[direcciones.size()];
		
		for(int i =0;i<direcciones.size();i++)
		{
			lugares[i]=direcciones.get(i);
		}
		
		System.out.println("HOOLI");
		
		setChanged( );
        notifyObservers(lugares );
		
	}
	
	
	public static enum Sexo {
		FEMENINO,
		MASCULINO

	}

}
