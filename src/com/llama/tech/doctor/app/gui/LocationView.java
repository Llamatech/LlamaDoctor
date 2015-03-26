package com.llama.tech.doctor.app.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.llama.tech.doctor.app.gui.components.LlamaButton;
import com.llama.tech.doctor.app.gui.components.LlamaMapComponent;
import com.llama.tech.doctor.app.gui.components.LlamaTextField;
import com.llama.tech.doctor.app.gui.components.Toast;
import com.llama.tech.doctor.maps.ConsultaMapas;
import com.llama.tech.utils.dict.LlamaDict;

public class LocationView extends AppView implements ActionListener
{
	private LlamaMapComponent mapPanel;
	private String location;
	private JTextField textField;
	private JTextField textField_1;
	private JButton btnModificarUbicacion;
	private JButton btnRegresar;
	private int zoom = 12;
	
	private static final String MODIFY = "Modify";
	private static final String BACK = "Bach";
	
	public LocationView(MainView main, String location)
	{
		mainView = main;
		viewTitle = "Ubicación";
		this.location = location; 

		URL iconURI = classLoader.getResource(IMG_PATH+"ic_action_place.png");
	    viewIcon = new ImageIcon(iconURI); 
	    
	    setSize(new Dimension(WIDTH, HEIGHT));
	    setLayout(null);
		setBackground(backgroundColor);
		
		InputStream resource = classLoader.getResourceAsStream(FONT_PATH
				+ "Roboto-Regular.ttf");
		Font font = null;
		try 
		{
			font = Font.createFont(Font.TRUETYPE_FONT, resource);
			font = font.deriveFont(13F);
		} 
		catch (IOException | FontFormatException e) 
		{
			e.printStackTrace();
		}
		
		mapPanel = new LlamaMapComponent(this);
		mapPanel.setBounds(29, 12, 270, 298);
		add(mapPanel);
		
//		JLabel lblNewLabel = new JLabel("Ciudad:");
//		lblNewLabel.setFont(font);
//		lblNewLabel.setBounds(49, 246, 70, 15);
//		add(lblNewLabel);
//		
//		JLabel lblCdigoPostal = new JLabel("Código postal:");
//		lblCdigoPostal.setFont(font);
//		lblCdigoPostal.setBounds(49, 273, 107, 15);
//		add(lblCdigoPostal);
//		
//		textField = new LlamaTextField();
//		textField.setBounds(174, 244, 114, 19);
//		textField.setEditable(false);
//		textField.setFont(font);
//		add(textField);
//		textField.setColumns(10);
//		
//		textField_1 = new LlamaTextField();
//		textField_1.setEditable(false);
//		textField_1.setBounds(174, 271, 114, 19);
//		textField_1.setFont(font);
//		add(textField_1);
//		textField_1.setColumns(10);
		
		URL icon = classLoader.getResource(IMG_PATH+"ic_action_forward.png");
		ImageIcon ic = new ImageIcon(icon);
		
		btnModificarUbicacion = new LlamaButton("¡Un momento, ya no estoy aquí!");
		btnModificarUbicacion.setBounds(18, 317, 270, 25);
		btnModificarUbicacion.addActionListener(this);
		btnModificarUbicacion.setActionCommand(MODIFY);
		add(btnModificarUbicacion);
		
		icon = classLoader.getResource(IMG_PATH+"ic_action_back.png");
		ic = new ImageIcon(icon);
		
		btnRegresar = new LlamaButton("Regresar", ic);
		btnRegresar.setBounds(18, 354, 270, 25);
		btnRegresar.addActionListener(this);
		btnRegresar.setActionCommand(BACK);
		add(btnRegresar);
	
	}
	
	@Override
	public void verifyView()
	{
		
		InputStream resource = classLoader.getResourceAsStream(FONT_PATH
				+ "Roboto-Regular.ttf");
		
		location = mainView.getGeoLocation();
		
		Font font = null;
		try 
		{
			font = Font.createFont(Font.TRUETYPE_FONT, resource);
			font = font.deriveFont(13F);
		} 
		catch (IOException | FontFormatException e) 
		{
			e.printStackTrace();
		}
		
		JFrame parent = null;
		Container c = this.getParent();
		while (!(c instanceof JFrame) && (c != null)) 
		{
			c = c.getParent();
		}
		if (c != null) 
		{
			parent = ((JFrame) c);
			if(location == null)
			{
				Toast.makeText(parent, "   Debe seleccionar una ubicación válida para usar la aplicación   ", font).display();
			}
			else
			{
				try {
					System.out.println(location);
					Image map = ConsultaMapas.darMapaConsultorio(location, zoom).getImage();
					mapPanel.setMap(map);
					repaint();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals(BACK))
		{
			mainView.returnView();
		}
		else if(e.getActionCommand().equals(MODIFY))
		{
			mainView.updateView(ViewType.LOCATION_SELECTION_VIEW);
		}
        else if(e.getActionCommand().equals(LlamaMapComponent.ZOOM_IN))
		{
        	System.out.println("Zoom In");
			zoom_in();
		}
		else if(e.getActionCommand().equals(LlamaMapComponent.ZOOM_OUT))
		{
			System.out.println("Zoom Out");
			zoom_out();
		}
		
	}


	private void zoom_out() 
	{
		zoom -= 1;
		paint_map();
		repaint();
	}


	private void paint_map() 
	{
		Image map = null;
		try {
			System.out.println(location);
			map = ConsultaMapas.darMapaConsultorio(location, zoom).getImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mapPanel.setMap(map);
		repaint();
		
	}

	private void zoom_in() 
	{
		zoom += 1;
		paint_map();
	}
}
