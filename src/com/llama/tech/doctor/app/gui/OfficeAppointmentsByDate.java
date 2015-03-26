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
import java.time.LocalTime;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.ListModel;

import com.llama.tech.doctor.app.gui.components.LlamaButton;
import com.llama.tech.doctor.app.gui.components.LlamaTextField;
import com.llama.tech.doctor.app.gui.components.Toast;
import com.llama.tech.doctor.mundo.Cita;
import com.llama.tech.utils.list.LlamaArrayList;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.toedter.calendar.JDateChooser;

public class OfficeAppointmentsByDate extends AppView implements ActionListener, ListSelectionListener
{
	private final static String RETURN = "Return";

	private JTextField textDoctor;
	private JTextField textDate;
	private JTextField textHour;
	private JList<Cita> list;
	private int appointmentQuantity = 0;
	private JButton btnVerCitas;
	private JDateChooser dateChooser;

	public OfficeAppointmentsByDate(MainView main)
	{
		mainView = main;
		viewTitle = "Agenda";
		URL iconURI = classLoader.getResource(IMG_PATH+"ic_action_go_to_today.png");
		viewIcon = new ImageIcon(iconURI); 

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

		setSize(new Dimension(WIDTH, HEIGHT));
		setBackground(backgroundColor);
		setLayout(null);




		list = new JList<Cita>();
		list.addListSelectionListener(this);
		list.setBounds(27, 98, 260, 122);
		list.setBackground(backgroundColor);
		list.setFont(font);
		add(list);

		JLabel lblNombreProfesional = new JLabel("Nombre Profesional:");
		lblNombreProfesional.setFont(font);
		lblNombreProfesional.setBounds(39, 247, 194, 19);
		add(lblNombreProfesional);

		textDoctor = new LlamaTextField();
		textDoctor.setEditable(false);
		textDoctor.setFont(font);
		textDoctor.setBounds(39, 264, 224, 19);
		add(textDoctor);
		textDoctor.setColumns(10);

		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setFont(font);
		lblFecha.setBounds(39, 291, 185, 19);
		add(lblFecha);

		textDate = new LlamaTextField();
		textDate.setEditable(false);
		textDate.setFont(font);
		textDate.setBounds(39, 311, 224, 19);
		add(textDate);
		textDate.setColumns(10);

		JLabel lblHora = new JLabel("Hora:");
		lblHora.setFont(null);
		lblHora.setBounds(39, 342, 185, 19);
		add(lblHora);

		textHour = new LlamaTextField();
		textHour.setFont(null);
		textHour.setEditable(false);
		textHour.setColumns(10);
		textHour.setBounds(39, 362, 224, 19);
		add(textHour);

		URL icon = classLoader.getResource(IMG_PATH+"ic_action_back.png");
		ImageIcon ic = new ImageIcon(icon);

		JButton btnRegresar = new LlamaButton("Regresar", ic);
		btnRegresar.addActionListener(this);
		btnRegresar.setActionCommand(RETURN);
		btnRegresar.setBounds(39, 393, 142, 25);
		add(btnRegresar);

		JLabel lblNewLabel = new JLabel("Fecha:");
		lblNewLabel.setBounds(24, 17, 87, 25);
		add(lblNewLabel);

		btnVerCitas = new JButton("Ver Citas");
		btnVerCitas.addActionListener(this);
		btnVerCitas.setActionCommand("VER");
		btnVerCitas.setBounds(18, 54, 117, 29);
		add(btnVerCitas);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(122, 17, 200, 25);
		add(dateChooser);


	}



	@Override
	public void valueChanged(ListSelectionEvent e) 
	{
		if(e.getSource().equals(list))
		{
			Cita seleccionada = list.getSelectedValue();
			if(seleccionada != null)
			{
				textDoctor.setText(seleccionada.getusuario().getNombreUsuario());
				textDate.setText(seleccionada.getDia().toString());
				textHour.setText(seleccionada.getHoraInicio().toString());
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals(RETURN))
		{
			mainView.returnView();
		}
		else if(e.getActionCommand().equals("VER"))
		{
			LocalDate date = LocalDate.of(dateChooser.getDate().getYear(), dateChooser.getDate().getMonth(), dateChooser.getDate().getDate());


			LlamaArrayList<Cita> citas = mainView.darcitasConsultorioFecha(date);
			if(citas.size()==0)
				mainView.makeToast("No hay citas en esta fecha");
			else
			{
				DefaultListModel<Cita> model = new DefaultListModel<Cita>();
				for(Cita c:citas)
				{
					model.addElement(c);
				}
				list.setModel(model);
			}

		}
	}
}
