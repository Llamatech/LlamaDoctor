package com.llama.tech.doctor.app.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class OfficePrincipal extends AppView implements ActionListener{
	
	public OfficePrincipal(MainView main)
	{
		mainView=main;
		viewTitle="Consultorios";
		URL iconURI = classLoader.getResource(IMG_PATH+"ic_action_group.png");
		viewIcon = new ImageIcon(iconURI); 
		setBackground(backgroundColor);
		setSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);
		
		JButton btnNewButton = new JButton("Consultorios Cercanos");
		btnNewButton.setBounds(62, 89, 195, 29);
		btnNewButton.addActionListener(this);
		btnNewButton.setActionCommand("CERCA");
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Consultorios por experiencia");
		btnNewButton_1.addActionListener(this);
		btnNewButton_1.setActionCommand("EXP");
		btnNewButton_1.setBounds(62, 155, 208, 29);
		add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Consultorios por estado");
		btnNewButton_2.addActionListener(this);
		btnNewButton_2.setActionCommand("ESTADO");
		btnNewButton_2.setBounds(62, 236, 195, 29);
		add(btnNewButton_2);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(this);
		btnBack.setActionCommand("BACK");
		btnBack.setBounds(29, 332, 117, 29);
		add(btnBack);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("CERCA"))
		{
			mainView.updateView(ViewType.OFFICE_NEAR);

		}
		else if(e.getActionCommand().equals("EXP"))
		{
			mainView.updateView(ViewType.OFFICE_EXPERIENCE);
		}
		else if(e.getActionCommand().equals("ESTADO"))
		{
			mainView.updateView(ViewType.OFFICE_REGION);

		}
		else if(e.getActionCommand().equals("BACK"))
		{
			mainView.returnView();
		}

		
	}

}
