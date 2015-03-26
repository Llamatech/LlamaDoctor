package com.llama.tech.doctor.app.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.llama.tech.doctor.app.gui.components.LlamaButton;
import com.llama.tech.utils.dict.LlamaDict;

import javax.swing.JLabel;

import com.toedter.calendar.JDateChooser;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class AccountView extends AppView implements ActionListener
{
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	
	private static final String LOGOUT = "LOGOUT";
	private static final String SET_DATE = "DATE";
	private static final String BACK = "BACK";
	
	private JDateChooser dateChooser;

	public AccountView(MainView main)
	{
		mainView = main;
		viewTitle = "Cuenta";
		URL iconURI = classLoader.getResource(IMG_PATH+"ic_action_person.png");
	    viewIcon = new ImageIcon(iconURI); 
	    
	    viewInfo = new LlamaDict<String, String>(4);
	    
	    setSize(new Dimension(WIDTH, HEIGHT));
	    setLayout(null);
	    setBackground(backgroundColor);
	    
	    JLabel lblNewLabel = new JLabel("Fecha del Sistema:");
	    lblNewLabel.setBounds(29, 36, 177, 22);
	    add(lblNewLabel);
	    
	    dateChooser = new JDateChooser();
	    dateChooser.setBounds(29, 70, 177, 22);
	    add(dateChooser);
	    
	    JLabel lblNewLabel_1 = new JLabel("Cambiar Contraseña:");
	    lblNewLabel_1.setBounds(29, 143, 177, 22);
	    add(lblNewLabel_1);
	    
	    JButton btnCambiar = new JButton("Cambiar");
	    btnCambiar.setBounds(29, 104, 117, 25);
	    add(btnCambiar);
	    
	    JLabel lblContraseaActual = new JLabel("Contraseña Actual:");
	    lblContraseaActual.setBounds(29, 164, 177, 22);
	    add(lblContraseaActual);
	    
	    passwordField = new JPasswordField();
	    passwordField.setBounds(29, 198, 177, 22);
	    add(passwordField);
	    
	    JLabel lblNuevaContrasea = new JLabel("Nueva Contraseña:");
	    lblNuevaContrasea.setBounds(29, 232, 177, 22);
	    add(lblNuevaContrasea);
	    
	    passwordField_1 = new JPasswordField();
	    passwordField_1.setBounds(29, 266, 177, 22);
	    add(passwordField_1);
	    
	    JButton button = new LlamaButton("Cambiar");
	    button.addActionListener(this);
	    button.setActionCommand(SET_DATE);
	    button.setBounds(29, 300, 117, 25);
	    add(button);
	    
	    JButton btnRegresar = new LlamaButton("Regresar");
	    btnRegresar.addActionListener(this);
	    btnRegresar.setActionCommand(BACK);
	    btnRegresar.setBounds(29, 337, 117, 25);
	    add(btnRegresar);
	    
	    JButton btnCerrarSesin = new LlamaButton("Cerrar Sesión");
	    btnCerrarSesin.addActionListener(this);
	    btnCerrarSesin.setActionCommand(LOGOUT);
	    btnCerrarSesin.setBounds(170, 337, 131, 25);
	    add(btnCerrarSesin);
	    
	    InputStream resource = classLoader.getResourceAsStream(FONT_PATH
				+ "Roboto-Regular.ttf");
		InputStream resource_2 = classLoader.getResourceAsStream(FONT_PATH
				+ "Roboto-Black.ttf");
		
		Font font = null;
		Font font2 = null;
		try 
		{
			font = Font.createFont(Font.TRUETYPE_FONT, resource);
			font2 = Font.createFont(Font.TRUETYPE_FONT, resource_2);
			font = font.deriveFont(13F);
			font2 = font2.deriveFont(13F);
		} 
		catch (IOException | FontFormatException e) 
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
          if(e.getActionCommand().equals(LOGOUT))
          {
        	  mainView.logout();
          }
          else if(e.getActionCommand().equals(SET_DATE))
          {
        	  LocalDate date = LocalDate.of(dateChooser.getDate().getYear(), dateChooser.getDate().getMonth(), dateChooser.getDate().getDate());
        	  if(date != null)
        	  {
        		  mainView.setDate(date);  
        	  }
        	  else
        	  {
        		  mainView.makeToast("Debe seleccionar una fecha");
        	  }
        	  
          }
          else if(e.getActionCommand().equals(BACK))
          {
        	  mainView.returnView();
          }
	}
}
