package com.llama.tech.doctor.app.gui;

import java.awt.Dimension;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class LocationView extends AppView 
{
	
	
	public LocationView(MainView main)
	{
		mainView = main;
		viewTitle = "Ubicación";
		
		URL iconURI = classLoader.getResource(IMG_PATH+"ic_action_place.png");
	    viewIcon = new ImageIcon(iconURI); 
	    
	    setSize(new Dimension(WIDTH, HEIGHT));
	    setLayout(null);
		setBackground(backgroundColor);
		
		
	    
		
	}
}
