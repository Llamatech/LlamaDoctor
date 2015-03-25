package com.llama.tech.doctor.app.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;

import com.llama.tech.doctor.app.gui.components.LlamaButton;

public class NavigationDrawer extends AppView implements ActionListener
{
	private JButton btnMainMenu;
	private JButton btnLocation;
	private JButton btnAppointments;
	private JButton btnOffices;
	private JButton btnHistory;
	private JButton btnAccount;
	
	private static final String MAIN_MENU = "Menu";
	private static final String LOCATION = "Location";
	private static final String APPOINTMENTS = "Appointments";
	private static final String OFFICES = "Offices";
	private static final String HISTORY = "History";
	private static final String ACCOUNT = "Account";
	
	public NavigationDrawer(MainView main) 
	{
		mainView = main;
		viewTitle = "LlamaDoctor";
		id = ViewType.NAVIGATOR;
		
		URL iconURI = classLoader.getResource(IMG_PATH+"ic_action_important.png");
	    viewIcon = new ImageIcon(iconURI); 
		
		setSize(new Dimension(167, 234));
		setBackground(new Color(153,153,153,128));
		setLayout(null);
		
		URL icon = classLoader.getResource(IMG_PATH+"ic_action_overflow.png");
	    ImageIcon ic = new ImageIcon(icon);
		
		btnMainMenu = new LlamaButton("Menú", ic);
		btnMainMenu.setBounds(25, 29, 117, 25);
		btnMainMenu.addActionListener(this);
		btnMainMenu.setActionCommand(MAIN_MENU);
		add(btnMainMenu);
		
		icon = classLoader.getResource(IMG_PATH+"ic_action_place.png");
	    ic = new ImageIcon(icon);
		
		btnLocation = new LlamaButton("Ubicación", ic);
		btnLocation.setBounds(25, 59, 117, 25);
		btnLocation.addActionListener(this);
		btnLocation.setActionCommand(LOCATION);
		add(btnLocation);
		
		icon = classLoader.getResource(IMG_PATH+"ic_action_go_to_today.png");
	    ic = new ImageIcon(icon);
		
		btnAppointments = new LlamaButton("Citas");
		btnAppointments.setBounds(25, 90, 117, 25);
		btnAppointments.addActionListener(this);
		btnAppointments.setActionCommand(APPOINTMENTS);
		add(btnAppointments);
		
		icon = classLoader.getResource(IMG_PATH+"ic_action_group.png");
	    ic = new ImageIcon(icon);
		
		btnOffices = new LlamaButton("Consultorios", ic);
		btnOffices.setBounds(25, 121, 117, 25);
		btnOffices.addActionListener(this);
		btnOffices.setActionCommand(OFFICES);
		add(btnOffices);
		
		icon = classLoader.getResource(IMG_PATH+"ic_action_web_site.png");
	    ic = new ImageIcon(icon);
		
		btnHistory = new LlamaButton("Historial", ic);
		btnHistory.setBounds(25, 152, 117, 25);
		btnHistory.addActionListener(this);
		btnHistory.setActionCommand(HISTORY);
		add(btnHistory);
		
		icon = classLoader.getResource(IMG_PATH+"ic_action_person.png");
	    ic = new ImageIcon(icon);
		
		btnAccount = new LlamaButton("Cuenta", ic);
		btnAccount.setBounds(25, 182, 117, 25);
		btnAccount.addActionListener(this);
		btnAccount.setActionCommand(ACCOUNT);
		add(btnAccount);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
        if(e.getActionCommand().equals(MAIN_MENU))
        {
     	    mainView.menuSelection(ViewType.MAIN_MENU_VIEW);
        }
        else if(e.getActionCommand().equals(LOCATION))
        {
     	    mainView.menuSelection(ViewType.LOCATION_VIEW);
        }
        else if(e.getActionCommand().equals(APPOINTMENTS))
        {
     	    mainView.menuSelection(ViewType.APPOINTMENTS_VIEW);
        }
        else if(e.getActionCommand().equals(OFFICES))
        {
     	    mainView.menuSelection(ViewType.OFFICES_VIEW);
        }
        else if(e.getActionCommand().equals(ACCOUNT))
        {
     	    mainView.menuSelection(ViewType.ACCOUNT_VIEW);
        }
		
	}
	
	
	
}
