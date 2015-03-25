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
import com.llama.tech.utils.dict.LlamaDict;

public class LocationView extends AppView implements ActionListener
{
	private LlamaMapComponent mapPanel;
	private LlamaDict<String, String> location;
	private JTextField textField;
	private JTextField textField_1;
	private JButton btnNewButton;
	private JButton btnRegregsar;
	
	public LocationView(MainView main, LlamaDict<String, String> location)
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
		mapPanel.setBounds(29, 30, 270, 213);
		add(mapPanel);
		
		JLabel lblNewLabel = new JLabel("Ciudad:");
		lblNewLabel.setFont(font);
		lblNewLabel.setBounds(49, 279, 70, 15);
		add(lblNewLabel);
		
		JLabel lblCdigoPostal = new JLabel("Código postal:");
		lblCdigoPostal.setFont(font);
		lblCdigoPostal.setBounds(49, 306, 107, 15);
		add(lblCdigoPostal);
		
		textField = new LlamaTextField();
		textField.setBounds(174, 277, 114, 19);
		textField.setEditable(false);
		textField.setFont(font);
		add(textField);
		textField.setColumns(10);
		
		textField_1 = new LlamaTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(174, 304, 114, 19);
		textField_1.setFont(font);
		add(textField_1);
		textField_1.setColumns(10);
		
		URL icon = classLoader.getResource(IMG_PATH+"ic_action_forward.png");
		ImageIcon ic = new ImageIcon(icon);
		
		btnNewButton = new LlamaButton("¡Un momento, ya no estoy aquí!");
		btnNewButton.setBounds(29, 352, 270, 25);
		add(btnNewButton);
		
		icon = classLoader.getResource(IMG_PATH+"ic_action_back.png");
		ic = new ImageIcon(icon);
		
		btnRegregsar = new LlamaButton("Regresar", ic);
		btnRegregsar.setBounds(29, 389, 117, 25);
		add(btnRegregsar);
		
		JFrame parent = null;
		Container c = this.getParent();
		while (!(c instanceof JFrame) && (c != null)) 
		{
			c = c.getParent();
		}
		if (c != null) 
		{
			parent = ((JFrame) c);
		}
		
		if(location == null)
		{
			Toast.makeText(parent, "   Debe definir su ubicación para poder usar la aplicación   ", font).display();
			mainView.updateView(ViewType.LOCATION_SELECTION_VIEW);
		}
		
	}
	
	@Override
	public void pushInfo(Object o) 
	{
		Image map = ((Image) o);
		mapPanel.setMap(map);
		mapPanel.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
}
