package com.llama.tech.doctor.mundo;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Observable;

import javax.swing.JOptionPane;

import uniandes.cupi2.cupIphone.core.ICore;

import com.llama.tech.doctor.maps.ConsultaGeografica;
import com.llama.tech.doctor.maps.PruebaMapas;
import com.llama.tech.utils.dict.LlamaDict;
import com.llama.tech.utils.dict.LlamaDict.UnhashableTypeException;
import com.llama.tech.utils.list.LlamaArrayList;
import com.llama.tech.utils.tree.LlamaAVLTree;

import jxl.*;
import jxl.read.biff.BiffException;

/**
 * Esta es la clase principal del mundo
 *
 */
public class LlamaCitas extends Observable {

	public static final String RUTA_ARCHIVO="data/consultorios.xls";

	private LlamaAVLTree<Consultorio> consultorios;
	private LlamaDict<String, Usuario> usuarios;
	private Usuario usuarioActual;
	private long tiempoCarga;
	private int totalConsultorios;

	private Calendar fechaActual;
	
	private ConsultaGeografica consultaGeografica;
	
	private ICore core;

	public LlamaCitas(ICore c)
	{
		core=c;
		try {
			consultaGeografica = new ConsultaGeografica();
		} catch (IOException | UnhashableTypeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(0);
		}
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

		long empece = System.currentTimeMillis();
		
		
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
		
		long termine = System.currentTimeMillis();
		 
		tiempoCarga = termine - empece;
		totalConsultorios=i;
		

	}

	public void darConsultoriosCercanos(String info, String tipo, double distancia) throws IOException
	{
		if(tipo.equals(PruebaMapas.CIUDAD))
		{
			info = consultaGeografica.coordenadasGeograficasPorCiudad(info);
		}
		else if(tipo.equals(PruebaMapas.PAIS))
		{
			info = consultaGeografica.coordenadasGeograficasPorPais(info);
		}
		else if(tipo.equals(PruebaMapas.POSTCODE))
		{
			info = consultaGeografica.coordenadasGeograficasPorCodigoPostal(info, "United States");
		}
		
		double lat1= Double.parseDouble(info.split(",")[0]);
		double lon1=Double.parseDouble(info.split(",")[1]);
		double lat2=0;
		double lon2=0;
		
		LlamaArrayList<Consultorio> direcciones = new LlamaArrayList<Consultorio>(20);
		double cuantaDist =0;
		int aniadidos=0;

		for(Consultorio c:consultorios)
		{
			System.out.println("MIRE"+c.getNombre());
//			cuantaDist = Math.sqrt(Math.pow(Math.abs(latitud-c.getLatitud()), 2)+Math.pow(Math.abs(longitud-c.getLongitud()), 2));

			lat2=c.getLatitud();
			lon2=c.getLongitud();
			
			double dlon = (lon2 - lon1)*Math.PI/180;
			double dlat = (lat2 - lat1)*Math.PI/180 ;
			double lat1R = lat1*Math.PI/180;
			double lat2R=lat2*Math.PI/180;
			double a = Math.pow(Math.sin(dlat/2),2) + Math.cos(lat1R) * Math.cos(lat2R) * Math.pow(Math.sin(dlon/2),2);
			double ca = 2 * Math.atan2( Math.sqrt(a), Math.sqrt(1-a) ) ;
			cuantaDist = 6371.000 * ca;
			

			

			System.out.println(cuantaDist);
			System.out.println(distancia);
			if(cuantaDist<=distancia)
			{
				
				System.out.println("agregue");
				direcciones.addAlFinal(c);
				aniadidos++;
			}
			if(aniadidos>30)
			{
				break;
			}
		}

		//		String[] lugares= new String[direcciones.size()];
		//		
		//		for(int i =0;i<direcciones.size();i++)
		//		{
		//			lugares[i]=direcciones.get(i);
		//		}


		setChanged( );
		notifyObservers(direcciones);

	}

	
	
	public long getTiempoCarga() {
		return tiempoCarga;
	}


	public int getTotalConsultorios() {
		return totalConsultorios;
	}



	public static enum Sexo {
		FEMENINO,
		MASCULINO

	}

}
