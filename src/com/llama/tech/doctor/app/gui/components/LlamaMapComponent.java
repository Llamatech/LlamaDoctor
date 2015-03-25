package com.llama.tech.doctor.app.gui.components;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class LlamaMapComponent extends JPanel 
{
	public static final String ZOOM_IN = "Zoom In";
	public static final String ZOOM_OUT = "Zoom Out";
	public static final int WIDTH = 270;
	public static final int HEIGHT = 213;
	private Image mapBackground;
	
	public LlamaMapComponent(JPanel parent)
	{
		setSize(WIDTH, HEIGHT);
		
	}
	
	public void setMap(Image map)
	{
		mapBackground = map;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
	    super.paintComponent(g);
	    g.drawImage(mapBackground, 0, 0, getWidth(), getHeight(), this);
	}
	
}
