package com.llama.tech.doctor.app.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;

import com.llama.tech.doctor.app.gui.components.LlamaButton;

public class NavigationDrawer extends AppView
{
	private JButton btnMainMenu;
	private JButton btnLocation;
	private JButton btnAppointments;
	private JButton btnOffices;
	private JButton btnHistory;
	private JButton btnAccount;
	
	public NavigationDrawer() 
	{
		setSize(new Dimension(167, 234));
		setBackground(new Color(153,153,153,128));
		setLayout(null);
		
		btnMainMenu = new LlamaButton("Menú");
		btnMainMenu.setBounds(25, 29, 117, 25);
		add(btnMainMenu);
		
		btnLocation = new LlamaButton("Ubicación");
		btnLocation.setBounds(25, 59, 117, 25);
		add(btnLocation);
		
		btnAppointments = new LlamaButton("Citas");
		btnAppointments.setBounds(25, 90, 117, 25);
		add(btnAppointments);
		
		btnOffices = new LlamaButton("Consultorios");
		btnOffices.setBounds(25, 121, 117, 25);
		add(btnOffices);
		
		btnHistory = new LlamaButton("Historial");
		btnHistory.setBounds(25, 152, 117, 25);
		add(btnHistory);
		
		btnAccount = new LlamaButton("Cuenta");
		btnAccount.setBounds(25, 182, 117, 25);
		add(btnAccount);
		
		
	}
	
	
	
}
