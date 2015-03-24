package com.llama.tech.doctor.app.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;

import com.llama.tech.doctor.app.gui.components.LlamaButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenuView extends AppView implements ActionListener
{
	
	private static final String LOCATION = "Location";
	private static final String OFFICES = "Offices";
	private static final String APPOINTMENTS = "Appointments";
	private static final String LOCATION_HISTORY = "History";
	private static final String ACCOUNT = "Account";
	
	private JButton btnLocation;
	private JButton btnAppointments;
	private JButton btnOffices;
	private JButton btnHistory;
	private JButton btnAccount;
	
	public MainMenuView(MainView main)
	{
		mainView = main;
		viewTitle = "LlamaDoctor";
		URL iconURI = classLoader.getResource(IMG_PATH+"ic_action_important.png");
	    viewIcon = new ImageIcon(iconURI); 
	    
	    setSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);
		setBackground(backgroundColor);
		
		JLabel lblNewLabel = new JLabel("Fibo");
		lblNewLabel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(81, 29, 145, 145);
		add(lblNewLabel);
		
		URL icon = classLoader.getResource(IMG_PATH+"ic_action_place.png");
	    ImageIcon ic = new ImageIcon(icon);
		
		btnLocation = new LlamaButton("Ubicación");
		btnLocation.setBounds(81, 186, 145, 25);
		btnLocation.setIcon(ic);
		btnLocation.addActionListener(this);
		btnLocation.setActionCommand(LOCATION);
		add(btnLocation);
		
		icon = classLoader.getResource(IMG_PATH+"ic_action_go_to_today.png");
	    ic = new ImageIcon(icon);
		
		btnAppointments = new LlamaButton("Citas");
		btnAppointments.setBounds(81, 223, 145, 25);
		btnAppointments.setIcon(ic);
		btnAppointments.addActionListener(this);
		btnAppointments.setActionCommand(APPOINTMENTS);
		add(btnAppointments);
		
		icon = classLoader.getResource(IMG_PATH+"ic_action_group.png");
	    ic = new ImageIcon(icon);
		
		btnOffices = new LlamaButton("Consultorios", ic);
		btnOffices.setBounds(81, 260, 145, 25);
		btnOffices.addActionListener(this);
		btnOffices.setActionCommand(OFFICES);
		add(btnOffices);
		
		icon = classLoader.getResource(IMG_PATH+"ic_action_web_site.png");
	    ic = new ImageIcon(icon);
		
		btnHistory = new LlamaButton("Historial", ic);
		btnHistory.setBounds(81, 297, 145, 25);
		btnHistory.addActionListener(this);
		btnHistory.setActionCommand(LOCATION_HISTORY);
		add(btnHistory);
		
		icon = classLoader.getResource(IMG_PATH+"ic_action_person.png");
	    ic = new ImageIcon(icon);
		
		btnAccount = new LlamaButton("Cuenta", ic);
		btnAccount.setBounds(81, 334, 145, 25);
		btnAccount.addActionListener(this);
		btnAccount.setActionCommand(ACCOUNT);
		add(btnAccount);
		
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
