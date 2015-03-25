package com.llama.tech.doctor.app.gui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JButton;

public class LlamaMapComponent extends JPanel 
{
	public static final String IMG_PATH = "res/assets/";
	public static final String ZOOM_IN = "Zoom In";
	public static final String ZOOM_OUT = "Zoom Out";
	public static final int WIDTH = 270;
	public static final int HEIGHT = 213;
	private Image mapBackground;
	private JButton btnZoomIn;
	private JButton btnZoomOut;
	private ClassLoader classLoader = this.getClass().getClassLoader();
	
	
	public LlamaMapComponent(ActionListener listener)
	{
		setSize(WIDTH, HEIGHT);
		setLayout(null);
		setBackground(new Color(233,233,233));
		
		URL icon = classLoader.getResource(IMG_PATH+"ic_menu_zoom.png");
	    ImageIcon ic = new ImageIcon(icon);
		
		btnZoomIn = new LlamaButton("", ic);
		btnZoomIn.setBounds(196, 176, 25, 25);
		btnZoomIn.setActionCommand(ZOOM_IN);
		btnZoomIn.addActionListener(listener);
		add(btnZoomIn);
		
		icon = classLoader.getResource(IMG_PATH+"ic_menu_zoom_out.png");
	    ic = new ImageIcon(icon);
		btnZoomOut = new LlamaButton("", ic);
		btnZoomOut.setBounds(233, 176, 25, 25);
		btnZoomOut.setActionCommand(ZOOM_OUT);
		btnZoomOut.addActionListener(listener);
		add(btnZoomOut);
		repaint();
		
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
