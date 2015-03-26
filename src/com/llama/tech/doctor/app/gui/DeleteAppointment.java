package com.llama.tech.doctor.app.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.llama.tech.doctor.app.gui.components.LlamaButton;
import com.llama.tech.doctor.app.gui.components.Toast;
import com.toedter.calendar.JDateChooser;

import javax.swing.JButton;

public class DeleteAppointment extends AppView implements ActionListener {
	private JTextField textFieldNombre;
	private JDateChooser dateChooser;

	public DeleteAppointment(MainView main) 
	{
		mainView=main;
		viewTitle="Eliminar Cita";
		URL iconURI = classLoader.getResource(IMG_PATH+"ic_action_go_to_today.png");
		viewIcon = new ImageIcon(iconURI); 
		setBackground(backgroundColor);
		setSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);

		JTextArea txtArea = new JTextArea();
		txtArea.setEditable(false);
		txtArea.setWrapStyleWord(true);
		txtArea.setLineWrap(true);
		txtArea.setText("¡Recuerde, nada es más importante que cuidar de su salud! ¿Le teme a algún procedimiento? No dude consultar a su médico antes de cancelar esta cita. Si se encuentra ocupado, puede agradecer la existencia de LlamaDoctor");
		txtArea.setBackground(backgroundColor);
		txtArea.setBounds(46, 46, 241, 181);
		add(txtArea);

		JLabel lblNombreProfesional = new JLabel("Nombre Profesional");
		lblNombreProfesional.setBounds(25, 222, 200, 50);
		add(lblNombreProfesional);

		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(35, 269, 134, 28);
		add(textFieldNombre);
		textFieldNombre.setColumns(10);

		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(25, 303, 200, 50);
		add(lblFecha);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(35, 346, 134, 28);
		add(dateChooser);

		JButton btnEliminarCita = new LlamaButton("Eliminar Cita");
		btnEliminarCita.setBounds(192, 395, 117, 29);
		btnEliminarCita.addActionListener(this);
		btnEliminarCita.setActionCommand("ELIMINAR");
		add(btnEliminarCita);

		JButton btnRegresar = new LlamaButton("Regresar");
		btnRegresar.setBounds(25, 395, 117, 29);
		btnRegresar.addActionListener(this);
		btnRegresar.setActionCommand("BACK");
		add(btnRegresar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("ELIMINAR"))
		{
			LocalDate date = LocalDate.of(dateChooser.getDate().getYear(), dateChooser.getDate().getMonth(), dateChooser.getDate().getDate());
//			if(fin == null)
//			{
//				mainView.makeToast("Debe escoger una fecha");
//			}
//			else
			{
				mainView.eliminarCita(textFieldNombre.getText(), date);
			}
		}
		else if(e.getActionCommand().equals("BACK"))
		{
			mainView.returnView();
		}

	}
}
