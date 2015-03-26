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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;

import com.llama.tech.doctor.app.gui.components.LlamaButton;
import com.llama.tech.doctor.app.gui.components.Toast;
import com.toedter.calendar.JDateChooser;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class AppointmentDateSelectionView extends AppView implements ActionListener
{
	private static final String SEARCH = "Search";
	private static final String RETURN = "Return";
	private static final String DELETE = "Delete";
	private JDateChooser dateChooser_1;
	private JDateChooser dateChooser;

	public AppointmentDateSelectionView(MainView main)
	{
		mainView = main;
		viewTitle = "Citas";
		URL iconURI = classLoader.getResource(IMG_PATH+"ic_action_go_to_today.png");
		viewIcon = new ImageIcon(iconURI); 

		setBackground(backgroundColor);
		setSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);

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

		JTextArea txtrRecuerdeLlegar = new JTextArea();
		txtrRecuerdeLlegar.setText("Recuerde llegar 20 minutos antes a \nsu cita, antes de que la asistente \nconsidere omitir su turno en la \nlista de espera.");
		txtrRecuerdeLlegar.setFont(font);
		txtrRecuerdeLlegar.setEditable(false);
		txtrRecuerdeLlegar.setBackground(backgroundColor);
		txtrRecuerdeLlegar.setBounds(40, 76, 242, 108);
		add(txtrRecuerdeLlegar);

		JLabel lblFechaDeInicio = new JLabel("Fecha inicio:");
		lblFechaDeInicio.setFont(font2);
		lblFechaDeInicio.setBounds(40, 187, 112, 15);
		add(lblFechaDeInicio);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(169, 187, 106, 19);
		add(dateChooser);

		dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(169, 214, 106, 19);
		add(dateChooser_1);

		JLabel lblFechaFinal = new JLabel("Fecha final:");
		lblFechaFinal.setBounds(40, 218, 112, 15);
		lblFechaFinal.setFont(font2);
		add(lblFechaFinal);

		JButton btnNewButton = new LlamaButton("Buscar");
		btnNewButton.setActionCommand(SEARCH);
		btnNewButton.addActionListener(this);
		btnNewButton.setBounds(108, 245, 117, 25);
		add(btnNewButton);

		JButton btnNewButton_1 = new LlamaButton("Eliminar");
		btnNewButton_1.setActionCommand(DELETE);
		btnNewButton_1.addActionListener(this);
		btnNewButton_1.setBounds(165, 302, 117, 25);
		add(btnNewButton_1);

		JButton btnRegresar = new LlamaButton("Regresar");
		btnRegresar.setActionCommand(RETURN);
		btnRegresar.addActionListener(this);
		btnRegresar.setBounds(35, 302, 117, 25);
		add(btnRegresar);


	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals(SEARCH))
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

			try
			{
				LocalDate inicial = LocalDate.of(dateChooser.getDate().getYear(), dateChooser.getDate().getMonth(), dateChooser.getDate().getDate());


				LocalDate end = LocalDate.of(dateChooser.getDate().getYear(), dateChooser.getDate().getMonth(), dateChooser.getDate().getDate());
				mainView.searchAppointments(inicial, end);


			}
			catch(Exception ex)
			{
				Toast.makeText(parent, "   Debe definir una fecha de fin y comienzo   ", font);

			}
		}
		else if(e.getActionCommand().equals(RETURN))
		{
			mainView.returnView();
		}
		else if(e.getActionCommand().equals(DELETE))
		{
			mainView.updateView(ViewType.APPOINTMENT_CANCELATION);
		}


	}
}
