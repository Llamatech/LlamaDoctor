package com.llama.tech.doctor.maps;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.llama.tech.doctor.mundo.Consultorio;

public class ConsultaMapas {

	//Recibe la latlon como lat,lon
	public static ImageIcon darMapaConsultorio(String info, int zoom) throws MalformedURLException, IOException
	{
		//http://maps.googleapis.com/maps/api/staticmap?center=Berkeley,CA&zoom=14&size=400x400&sensor=false


		String direccion = "";

		direccion = "http://maps.googleapis.com/maps/api/staticmap?center="+info+"&zoom="+zoom+"&size=400x400&sensor=false&markers=color:red%7C"+info;

		BufferedImage imagen = ImageIO.read(new URL(direccion));
		ImageIcon ic = new ImageIcon(imagen);
		return ic;
	}

	//Si hay latlon se recibe lat,lon
	public static ImageIcon darMapaVisitados(String[] location, int zoom) throws MalformedURLException, IOException
	{
		String direccion = "http://maps.googleapis.com/maps/api/staticmap?zoom="+zoom+"&size=450x350&maptype=roadmap";
		char c = 'A';

		for(String s : location)
		{
			StringBuilder ss = new StringBuilder();
			ss.append("&markers=color:red");
			ss.append("%7Clabel:");
			ss.append(c);
			ss.append("%7C");
			ss.append(s.split(":")[0]);
			direccion+=ss.toString();
			c++;
		}
		System.out.println(direccion);
		BufferedImage imagen = ImageIO.read(new URL(direccion));
		ImageIcon ic = new ImageIcon(imagen);
		return ic;
	}

}
