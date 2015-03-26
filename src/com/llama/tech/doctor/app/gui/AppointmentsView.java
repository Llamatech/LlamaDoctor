package com.llama.tech.doctor.app.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.ImageIcon;

import com.llama.tech.doctor.app.gui.components.LlamaButton;
import com.llama.tech.doctor.app.gui.components.LlamaTextField;
import com.llama.tech.doctor.app.gui.components.Toast;
import com.llama.tech.utils.dict.LlamaDict;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AppointmentsView extends AppView implements ActionListener
{
	private String location;
	private LocalDate sysHour = null; 
	private JTextField textField;
	
	private static final String NEW_APPOINTMENT = "Appointment";
	private static final String APPOINTMENT_LIST = "List";
	private static final String RETURN_VIEW = "Return";
	
	public AppointmentsView(MainView main, String location2, LocalDate localDate)
	{
		mainView = main;
		this.location = location2;
		sysHour = localDate;
		viewTitle = "Citas";
		
		URL iconURI = classLoader.getResource(IMG_PATH+"ic_action_go_to_today.png");
	    viewIcon = new ImageIcon(iconURI); 
	    
	    setSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);
		setBackground(backgroundColor);
		
		InputStream resource = classLoader.getResourceAsStream(FONT_PATH
				+ "Roboto-Regular.ttf");
		InputStream resource_2 = classLoader.getResourceAsStream(FONT_PATH
				+ "DIN-AlternateMedium.ttf");
		
		Font font = null;
		Font font2 = null;
		try 
		{
			font = Font.createFont(Font.TRUETYPE_FONT, resource);
			font2 = Font.createFont(Font.TRUETYPE_FONT, resource_2);
			font = font.deriveFont(13F);
			font2 = font2.deriveFont(15F);
		} 
		catch (IOException | FontFormatException e) 
		{
			e.printStackTrace();
		}
		
		JTextArea txtrAquPodrHacer = new JTextArea();
		txtrAquPodrHacer.setBackground(backgroundColor);
		txtrAquPodrHacer.setBorder(null);
		txtrAquPodrHacer.setEditable(false);
		txtrAquPodrHacer.setFont(font);
		txtrAquPodrHacer.setText("Aquí, podrá hacer gestión de su próxima \ncita médica. No obstante, la aplicación no \npuede responder si sufre fobia a las \ninyecciones, o a su odontólogo de \nconfianza.");
		txtrAquPodrHacer.setBounds(31, 34, 274, 87);
		add(txtrAquPodrHacer);
		
		JButton btnNewButton = new LlamaButton("Agendar una cita");
		btnNewButton.addActionListener(this);
		btnNewButton.setActionCommand(NEW_APPOINTMENT);
		btnNewButton.setBounds(57, 168, 164, 25);
		add(btnNewButton);
		
		LlamaButton llamaButton = new LlamaButton("Agenda de citas");
		llamaButton.addActionListener(this);
		llamaButton.setActionCommand(APPOINTMENT_LIST);
		llamaButton.setBounds(57, 205, 164, 25);
		add(llamaButton);
		
		JLabel lblFechaYHora = new JLabel("Fecha y hora actual:");
		lblFechaYHora.setBounds(31, 258, 171, 15);
		add(lblFechaYHora);
		
		textField = new LlamaTextField();
		textField.setEditable(false);
		textField.setFont(font);
		textField.setText(localDate.toString());
		textField.setBounds(57, 283, 164, 19);
		add(textField);
		textField.setColumns(10);
		
		URL icon = classLoader.getResource(IMG_PATH+"ic_action_back.png");
	    ImageIcon ic = new ImageIcon(icon);
		
		LlamaButton llamaButton_1 = new LlamaButton("Regresar", ic);
		llamaButton_1.setActionCommand(RETURN_VIEW);
		llamaButton_1.addActionListener(this);
		llamaButton_1.setBounds(31, 329, 164, 25);
		add(llamaButton_1);
		
		
	}

	@Override
	public void verifyView()
	{
		if(location == null)
		{
			JFrame parent = null;  
	    	  Container c = this.getParent();  
	    	  while (!(c instanceof JFrame) && (c!=null)) {  
	    	          c = c.getParent();  
	    	  }  
	    	  if (c!=null) 
	    	  {
	    		  parent = ((JFrame) c); 
	    	  }
	    	  
	    	  InputStream resource = classLoader.getResourceAsStream(FONT_PATH
	  				+ "Roboto-Regular.ttf");
	  
	  		Font font = null;
	  		
	  		try 
	  		{
	  			font = Font.createFont(Font.TRUETYPE_FONT, resource);
	  			font = font.deriveFont(13F);
	  		} 
	  		catch (IOException | FontFormatException e2) 
	  		{
	  			e2.printStackTrace();
	  		}
	  		
	  		Toast.makeText(parent, "   Debe definir su ubicación para poder usar la aplicación   ", font).display();
	  		mainView.returnView();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals(NEW_APPOINTMENT))
		{
			if(location != null)
			{
				mainView.updateView(ViewType.OFFICE_SEARCH_VIEW);
			}
		}
		else if(e.getActionCommand().equals(APPOINTMENT_LIST))
		{
			mainView.updateView(ViewType.APPOINTMENTS_DATE_SELECTION_VIEW);
		}
		else if(e.getActionCommand().equals(RETURN_VIEW))
		{
			mainView.returnView();
		}
		
	}
}
