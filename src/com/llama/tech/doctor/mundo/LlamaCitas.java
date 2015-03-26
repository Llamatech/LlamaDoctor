package com.llama.tech.doctor.mundo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Observable;

import javax.swing.JOptionPane;

import uniandes.cupi2.cupIphone.core.ICore;

import com.llama.tech.doctor.comparadores.ComparadorConsultorioEstado;
import com.llama.tech.doctor.comparadores.ComparadorExperiencia;
import com.llama.tech.doctor.maps.ConsultaGeografica;
import com.llama.tech.utils.dict.Dictionary;
import com.llama.tech.utils.dict.LlamaDict;
import com.llama.tech.utils.dict.LlamaDict.UnhashableTypeException;
import com.llama.tech.utils.list.LlamaArrayList;
import com.llama.tech.utils.tree.LlamaAVLTree;
import com.llama.tech.utils.tree.Tree;

import jxl.*;
import jxl.read.biff.BiffException;

/**
 * Esta es la clase principal del mundo
 *
 */
public class LlamaCitas extends Observable implements Serializable {
	
	private static final String DIC_LOC = "usuarios.ll";
	private static final String ARB_LOC = "arbol.ll";

	/**
	 * Constante para la ruta del archivo de consultorios
	 */
	public static final String RUTA_ARCHIVO="data/consultorios.xls";
	
	/**
	 * Constante para definir la ubicacion actual como ciudad
	 */
	public static final String CIUDAD = "ciudad";

	/**
	 * Constante para definir la ubicacion actual como ciudad
	 */
	public static final String POSTCODE = "postcode";
	
	public static final String LATLON = "latlon";
	
	/**
	 * El siguiente grupo de constantes representa los posibles cambios del mundo.
	 */
	public static final int CAMBIO_UBACTUAL = 0;
	public static final int INICIO_SESION = 1;
	public static final int REGISTRO = 2;

	/**
	 * Atributo de consultorios
	 */
	private static Tree<Consultorio> consultorios;
	
	/**
	 * Atributo de consultorios por experiencia
	 */
	private static Tree<Consultorio> consultoriosExp;
	
	/**
	 * Atributo de consultorios por estado
	 */
	private static Tree<Consultorio> consultoriosEstado;

	/**
	 * Lista de usuarios regustrados en el sistema
	 */
	private Dictionary<String, Usuario> usuarios;

	/**
	 * Usuario cuya sesión esta iniciada
	 */
	private Usuario usuarioActual;

	/**
	 * Tiempo en que se demora el tiempo de carga
	 */
	private long tiempoCarga;

	/**
	 * Número de consultorios cargados en el sistema
	 */
	private int totalConsultorios;

	/**
	 * Fecha de referencia del sistema
	 */
	private LocalDate fechaActual;

	/**
	 * Asociacion para las consultas geograficas
	 */
	private ConsultaGeografica consultaGeografica;

	/**
	 * Core para el inicio de la aplicación
	 */
	private ICore core;


	/**
	 * Método constructor de la clase 
	 * @param c Core para la aplicación
	 * @throws ConsultaException 
	 */
	public LlamaCitas(ICore c) 
	{
		core=c;
		fechaActual = LocalDate.now();
		try {
			consultaGeografica = new ConsultaGeografica();
		} catch (IOException | UnhashableTypeException e1) {
		}
		consultorios = new LlamaAVLTree<Consultorio>();
		consultoriosExp = new LlamaAVLTree<Consultorio>();
		consultoriosEstado = new LlamaAVLTree<Consultorio>();
		 File f = new File("./data", ARB_LOC);
	     try
	     {
	            consultorios = (LlamaAVLTree<Consultorio>) new ObjectInputStream(new FileInputStream(f)).readObject();   
	     }
	        
	     catch (Exception e)
	     {
	      
	    	 try {
	 			cargarConsultorios();
	 		} catch (Exception e2) {
	 			e2.printStackTrace();
	 		}
	     }
		
	     try
	     {
	    	 usuarios = (LlamaDict<String, Usuario>) new ObjectInputStream(new FileInputStream(f)).readObject(); 
	     }
	     
		usuarios = new LlamaDict<String, Usuario>(10);
	}

	/**
	 * Carga los consultorios del archivo de consultorios.
	 * @throws ConsultaException si hay problemas leyendo el archivo
	 */
	public void cargarConsultorios() throws ConsultaException
	{

		long empece = System.currentTimeMillis();
		try{

		File f = new File(RUTA_ARCHIVO);
		if (!f.exists()) {
			InputStream link = (getClass().getClassLoader().getResourceAsStream(RUTA_ARCHIVO));
			Files.copy(link, f.getAbsoluteFile().toPath());
		}
		


		//		Workbook workbook = Workbook.getWorkbook(new File(getClass().getClassLoader().getResource(RUTA_ARCHIVO).getFile()));

		Workbook workbook = Workbook.getWorkbook(f);
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
			double latitud = 0;
			try
			{
				latitud = latitudes[i].getContents().equals("")?0:Double.parseDouble(latitudes[i].getContents());
			}
			catch(NumberFormatException e)
			{
				String info = latitudes[i].getContents();
				info = info.replace(',', '.');
				latitud = Double.parseDouble(info);
			}
			double longitud = 0;
			try
			{
				longitud = longitudes[i].getContents().equals("")?0:Double.parseDouble(longitudes[i].getContents());
			}
			catch(NumberFormatException e)
			{
				String info = longitudes[i].getContents();
				info = info.replace(',', '.');
				longitud = Double.parseDouble(info);
			}
			int postCode = postCodes[i].getContents().equals("")?00000:Integer.parseInt(postCodes[i].getContents());

			Consultorio c= null;

			try{
			c=new Consultorio(nombres[i].getContents(), sex, direcciones[i].getContents(), localidades[i].getContents(),
					regiones[i].getContents(), postCode, 
					telefonos[i].getContents(), 
					horasDeAtencion[i].getContents(),
					latitud, longitud,
					seguris, Integer.parseInt(experiencias[i].getContents()));
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				c=new Consultorio(nombres[i].getContents(), sex, direcciones[i].getContents(), localidades[i].getContents(),
						regiones[i].getContents(), postCode, 
						telefonos[i].getContents(), 
						"",
						latitud, longitud,
						seguris, Integer.parseInt(experiencias[i].getContents()));
			}

			consultorios.add(c);

			ComparadorExperiencia ce = new ComparadorExperiencia();
			ComparadorConsultorioEstado ces = new ComparadorConsultorioEstado();

//			consultoriosExp.add(c, ce);
//			consultoriosEstado.add(c, ces);
			

			i++;
		}

		long termine = System.currentTimeMillis();

		tiempoCarga = termine - empece;
		totalConsultorios=i;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new ConsultaException("Hubo un problema cargando los datos del archivo");
		}


	}

	/**
	 * Retorna una lista con todos los consultorios cercanos
	 * @param info Informacion del lugar para hacer la consulta
	 * @param distancia Distancia de búsqueda
	 * @return 
	 * @throws IOException Si hay algun problema consiguiendo las coordenadas del lugar
	 */
	public LlamaArrayList<Consultorio> darConsultoriosCercanos(double distancia) throws IOException
	{
		String tipo = usuarioActual.tipoUbicacion;
		String info = usuarioActual.ubicacionActual;
		if(tipo.equals(CIUDAD))
		{
			info = consultaGeografica.coordenadasGeograficasPorCiudad(info);
		}
		else if(tipo.equals(POSTCODE))
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


		return direcciones;

	}



	/**
	 * Retorna el tiempo en que se cargaron los consultorios
	 * @return tiempo carga
	 */
	public long getTiempoCarga() {
		return tiempoCarga;
	}

	/**
	 * Retorna el total de cosultorios cargados en el sistema
	 * @return total consultorios
	 */
	public int getTotalConsultorios() {
		return totalConsultorios;
	}

	/**
	 * Se utiliza para registrar un usuario en el sistema
	 * @param nombreUsuario nombre del usario
	 * @param clave clave de usuario
	 * @param email email de usario
	 * @param id identificador unico de usuario
	 * @throws UnhashableTypeException Si hay problemas agregando al diccionario
	 */
	public void registrarUsuario(String nombreUsuario, String clave,String email, String id) throws UnhashableTypeException
	{
		Usuario u = new Usuario(nombreUsuario,clave,email,id);
		usuarios.addEntry(id, u);
		usuarioActual = u;
		notifyObservers(REGISTRO);
	}
	
	public boolean existeUsuario(String id)
	{
		return usuarios.getValue(id)!=null;
	}

	/**
	 * Cambia la ubiacaion del usuario actual
	 * @param ubicacion nueva ubicacion
	 * @param tipo tipo de ubicacion
	 */
	public void cambiarUbicacionActual(String ubicacion, String tipo)
	{
//		usuarioActual.ubicacionActual = ubicacion;
//		usuarioActual.tipoUbicacion = tipo;
		usuarioActual.setUbicacionActual(ubicacion, tipo);
		setChanged();
		notifyObservers(CAMBIO_UBACTUAL);
		
	}
	
	public String darUbicacionActual()
	{
		return usuarioActual.ubicacionActual;
	}

	/**
	 * Cierra la sesión del usuario actual
	 */
	public void cerrarSesion()
	{
		usuarioActual=null;
	}

	/**
	 * Inica sesión con un usuario conocido
	 * @param usuario usuario del usuario a conectarse
	 * @param clave clave del usuario
	 * @throws UsuarioException si el usuario no esta registrado o la clave es incorrecta
	 */
	public void iniciarSesion(String usuario, String clave) throws UsuarioException
	{
		Usuario u = usuarios.getValue(usuario);
		if(u==null)
		{
			throw new UsuarioException("El nombre de usuario no se encuentra registrado");
		}

		if(!u.getClave().equals(clave))
		{
			throw new UsuarioException("La clave ingresada es incorrecta");
		}
		else
		{
			usuarioActual=u;
		}
		//setChanged();
		//notifyObservers(INICIO_SESION);
	}
	
	public boolean verificarUsuario(String id, String clave)
	{
		Usuario u = usuarios.getValue(id);
		if(u==null)
			return false;
		if(!u.getClave().equals(clave))
			return false;
		return true;
		
	}

	/**
	 * Da las n ubicaciones anteriores del usuario actual
	 * @param N numero de ubicaciones anteriores
	 * @return lista con la información delas n ubicaciones anteriores
	 */
	public String[] darUbicacionesAnteriores(int N)
	{
		LlamaArrayList<String> ant = usuarioActual.getAnteriores();
		String []info = new String[Math.min(ant.size(),N)];
		for (int i = 0; i < info.length&&i<N; i++) {
			info[i]=ant.get(i);
			System.out.println(info[i]);
		}

		return info;
	}
	
	/**
	 * Da un arreglo con las citas del dia de hoy
	 * @param cons consultorio del que se quieren saber las citas
	 * @return arreglo ocn citas
	 */
	public LlamaArrayList<Cita> darCitasDeHoy(Consultorio cons)
	{
		return cons.citasEnFecha(fechaActual);
	}
	
	/**
	 * Retorna un arreglo con las citas de un dia que llega por parametro y que no puede ser antes del dia actual
	 * @param cons consultorio a consultar
	 * @param fecha fecha de consulta deseada
	 * @return arreglo con citas
	 * @throws LlamaCitaException si la fecha es anterior a la actual
	 */
	public LlamaArrayList<Cita> darCitasDeFecha(Consultorio cons,LocalDate fecha) throws LlamaCitaException
	{
		if(fecha.isAfter(fechaActual)||fecha.isEqual(fechaActual))
			return cons.citasEnFecha(fechaActual);
		else
			throw new LlamaCitaException("La fecha de consulta debe darse después de la fecha actual");
	}
	
	/**
	 * Busca un consultorio dado el nombre del doctor
	 * @param pNombre nombre del dcotor del consultorio buscado
	 * @return consultorio buscado o null si no se encuentra
	 */
	public Consultorio buscarConsultorio(String pNombre)
	{
		Consultorio c = new Consultorio(pNombre, null, "", "", "", 0, "", "", 0, 0, null, 0);
		return consultorios.get(c);
	}

	/**
	 * Se agenda una cita en el usuario y en el consultorio
	 * @param hora hora de cita
	 * @param cons consultoior de la cita
	 * @return true si se agendo false si no
	 */
	public boolean agendarCita(LocalDateTime hora, Consultorio cons)
	{
		Cita c = new Cita(hora,cons, usuarioActual);
		boolean us = usuarioActual.agendarCita(c);
		if(us)
		{
			boolean consi = cons.agendarCita(c);
			if(!consi)
			{
				usuarioActual.eliminarCita(LocalDate.of(hora.getYear(), hora.getMonth(), hora.getDayOfMonth()),cons);
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * Sirve para eliminar una cita del sistema.
	 * @param nombreDoc Doctor del consultorio a eliminar la cita
	 * @param fecha fecha en la que es la cita a eliminar
	 * @return true si se elimino false de lo contrario
	 */
	public boolean eliminarCita(String nombreDoc, LocalDate fecha)
	{
		if(fecha.isAfter(fechaActual))
		{
			Consultorio cons = new Consultorio(nombreDoc, null, "", "", "", 0, "", "", 0, 0, null, 0);
			Cita c = usuarioActual.eliminarCita(fecha,cons);
			if(c!=null)
			{
				return consultorios.get(cons).eliminarCita(c);
			}			
		}
		return false;
	}
	
	/**
	 * Da las citas regustradas de un usuario en un rango de fechas
	 * @param inicio fecha inicio de consulta
	 * @param fin fecha fin de consulta
	 * @return arreglo con las citas en las fechas indicadas
	 */
	public LlamaArrayList<Cita> darCitasUsuario(LocalDate inicio, LocalDate fin)
	{
		//TODO: Ellos desean que hagamos esto para cualquier usuario?
		return usuarioActual.darCitasFechas(inicio, fin);
	}
	
	/**
	 * Cambiar la fecha actual de consulta
	 * @param fecha nueva fecha a escoger
	 */
	public void cambiarFecha(LocalDate fecha)
	{
		fechaActual=fecha;
	}
	
	public LocalDate darFechaActual()
	{
		return fechaActual;
	}
	
	/**
	 * Da una lista con las citas disponibles de un dia especifico.
	 * @param fecha dia en que se quiere la info
	 * @param cons consultorio del cual se desea la info
	 * @return
	 */
	public LlamaArrayList<LocalTime> citasDisponibles(LocalDate fecha, Consultorio cons)
	{
		DayOfWeek dia =fecha.getDayOfWeek();
		return cons.darHorarioDia(dia).generarCitasDisponibles();
	}
	
	/**
	 * Retorna una lista de consultorios segun una region dada por parametro
	 * @param region region de consultorios que se desean
	 * @return arreglo con los consultorios de esa region
	 */
	public LlamaArrayList<Consultorio> darConsultoriosPorRegion(String region)
	{
		LlamaArrayList<Consultorio> ret = new LlamaArrayList<Consultorio>(30);
		
		int i = 0;
		for(Consultorio c: consultorios)
		{
			if(c.getRegion().equals(region))
			{
				ret.addAlFinal(c);
				i++;
			}
			if(i>15)
				break;
			
		}
//		Consultorio c = new Consultorio("", null, "", "", region, 0, "", "", 0, 0, null, 0);
//		while(true)
//		{
//			Consultorio get =consultoriosEstado.get(c,new ComparadorConsultorioEstado());
//			if(get!=null)
//			{
//				ret.addAlFinal(get);
//			}
//			else
//			{
//				break;
//			}
//		}
		
		
		
		return ret;
		
	}
	
	/**
	 * Retorna una lista con los consultorios que cumplen con una experiencia mínima
	 * @param exp experiencia minima requerida
	 * @return lista con los consultorios que tienen la experiencia minima reuqerida o mas
	 */
	public LlamaArrayList<Consultorio> darConsultoriosPorExperiencia(int exp)
	{
		LlamaArrayList<Consultorio> ret = new LlamaArrayList<Consultorio>(30);
		
		int i =0;
		for(Consultorio c: consultorios)
		{
			if(c.getAniosExp()>=exp)
			{
				ret.addAlFinal(c);
				i++;
			}
			if(i>15)
				break;
			
		}
//		Iterator<Consultorio> it = consultoriosExp.iteratorInverso();
//		
//		while(it.hasNext())
//		{
//			Consultorio cons = it.next();
//			if(cons.getAniosExp()<exp)
//			{
//				break;
//			}
//			else
//			{
//				ret.addAlFinal(cons);
//			}
//		}
		
		return ret;
		
	}

	/**
	 * Enum que representa el Sexo del doctor.
	 */
	public static enum Sexo {
		FEMENINO,
		MASCULINO

	}
	
	public static void main(String[] args) {
		
//		System.out.println(citasDis);
			LlamaCitas c = new LlamaCitas(null);
			Consultorio cons = consultorios.get(new Consultorio("Brian Goss"	, null, "", "", "", 0, "", "", 0, 0, null, 0));
			c.citasDisponibles(LocalDate.now(), cons);
//			LlamaArrayList<Consultorio> cs = c.darConsultoriosPorRegion("AK");
			//Tree<Consultorio> cs = consultoriosExp;
			
			//for(Consultorio co:cs)
			{
				//System.out.println("HOLI"+co.getNombre()+co.getAniosExp());
			}
			
		
//		
//		String horasAtencion = JOptionPane.showInputDialog("");
//		
//		horasAtencion=horasAtencion.replace("\"", "");
//		horasAtencion=horasAtencion.replace("[", "");
//		horasAtencion=horasAtencion.replace("]", "");
//		horasAtencion = horasAtencion.substring(1, horasAtencion.length()-2);
//		
//		System.out.println(horasAtencion);
	}

	public void serialize() 
	{
		File f = new File(core.darDirectorioDatos(), DIC_LOC);
		try{
    		new ObjectOutputStream(new FileOutputStream(f)).writeObject(usuarios);    		
    	}
    	catch (Exception e) {
    		System.err.println("No se pudo guardar los datos");
			e.printStackTrace();
		}
		
		f = new File(core.darDirectorioDatos(), ARB_LOC);
		try{
    		new ObjectOutputStream(new FileOutputStream(f)).writeObject(consultorios);    		
    	}
    	catch (Exception e) {
    		System.err.println("No se pudo guardar los datos");
			e.printStackTrace();
		}
	}

}
