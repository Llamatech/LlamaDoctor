package com.llama.tech.doctor.app.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.ImageIcon;

import com.llama.tech.doctor.app.gui.components.HintTextFieldUI;
import com.llama.tech.doctor.mundo.Cita;
import com.llama.tech.doctor.mundo.Consultorio;

import javax.swing.JLabel;

import com.toedter.calendar.JDateChooser;

import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class AgendarCita extends AppView implements ActionListener{
	private JLabel lblDia;
	private JLabel lblDiaDeCita;
	private JList<LocalTime> list;
	private JScrollPane scrollPane;
	private JDateChooser dateChooser;
	private JButton btnVerHorariosDisponibles;
	private Consultorio consultorio;

	public AgendarCita(MainView main, Consultorio cons)
	{
		mainView=main;
		viewTitle="Agendar cita";
		consultorio = cons;
		URL iconURI = classLoader.getResource(IMG_PATH+"ic_action_group.png");
		viewIcon = new ImageIcon(iconURI); 
		setBackground(backgroundColor);
		setSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);

		lblDiaDeCita = new JLabel("Dia de cita:");
		lblDiaDeCita.setBounds(6, 23, 200, 29);
		add(lblDiaDeCita);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(100, 23, 200, 29);
		add(dateChooser);

		btnVerHorariosDisponibles = new JButton("Ver Horarios Disponibles");
		btnVerHorariosDisponibles.addActionListener(this);
		btnVerHorariosDisponibles.setActionCommand("VER");
		btnVerHorariosDisponibles.setBounds(6, 64, 215, 29);
		add(btnVerHorariosDisponibles);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 134, 292, 227);
		add(scrollPane);

		list = new JList<LocalTime>();
		scrollPane.setViewportView(list);

		lblDia = new JLabel("Horarios de atención:");
		lblDia.setBounds(6, 105, 200, 22);
		add(lblDia);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(this);
		btnBack.setActionCommand("BACK");
		btnBack.setBounds(32, 373, 117, 29);
		add(btnBack);

		JButton btnNewButton_1 = new JButton("Agendar Cita");
		btnNewButton_1.addActionListener(this);
		btnNewButton_1.setActionCommand("AGENDAR");
		btnNewButton_1.setBounds(183, 373, 117, 29);
		add(btnNewButton_1);


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("BACK"))
		{
			mainView.returnView();
		}
		else if(e.getActionCommand().equals("VER"))
		{
			String dia = dateChooser.getDateFormatString();
			if(dia == null)
			{
				mainView.makeToast("Debe escoger una fecha");
			}
			else
			{
				System.out.println(dateChooser.getDate().toString());
				LocalDate date = LocalDate.of(dateChooser.getDate().getYear(), dateChooser.getDate().getMonth(), dateChooser.getDate().getDate());
				DefaultListModel<LocalTime> model = new DefaultListModel<LocalTime>();
				int i = 0;
				System.out.println(date.getDayOfWeek());
				System.out.println(consultorio.darHorarioDia(date.getDayOfWeek()));
				for(LocalTime c : consultorio.darHorarioDia(date.getDayOfWeek()).generarCitasDisponibles())
				{
					model.addElement( c);
					i++;
				}
				list.setModel(model);
			}
		}
		else if(e.getActionCommand().equals("AGENDAR"))
		{

			String dia = dateChooser.getDateFormatString();
			if(dia == null)
			{
				mainView.makeToast("Debe escoger una fecha");
			}
			else
			{
				LocalDate date = LocalDate.of(dateChooser.getDate().getYear(), dateChooser.getDate().getMonth(), dateChooser.getDate().getDate());
				LocalTime horis = list.getSelectedValue();
				if(horis==null)
				{
					mainView.makeToast("Debe seleccionar una cita");
				}
				else
				{
					mainView.agendarCita(LocalDateTime.of(date, horis));
				}
			}
		}

	}
}
