package com.llama.tech.doctor.app.gui;

import java.awt.Dimension;
import java.net.URL;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;

import com.llama.tech.doctor.mundo.Cita;
import com.llama.tech.doctor.mundo.Consultorio;
import com.llama.tech.utils.list.LlamaArrayList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InfoConsultorio extends AppView implements ActionListener{
	
	private JTextField textNombreDoc;
	private JTextField textDireccion;
	private JTextField textLocalidad;
	private JTextField textPostCode;
	private JTextField textTelefono;
	private JTextField textLatitud;
	private JTextField textLongitud;
	private JTextArea textCitas;
	private JButton btnvercitas;
	
	public InfoConsultorio(MainView main, Consultorio cons, LocalDate fecha)
	{
		mainView=main;
		viewTitle="Información Consultorios";
		URL iconURI = classLoader.getResource(IMG_PATH+"ic_action_group.png");
		viewIcon = new ImageIcon(iconURI); 
		setBackground(backgroundColor);
		setSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);
		
		JLabel lblNombreDoctor = new JLabel("Nombre Doctor:");
		lblNombreDoctor.setBounds(10, 10, 134, 28);
		add(lblNombreDoctor);
		
		textNombreDoc = new JTextField(cons.getNombre());
		textNombreDoc.setEditable(false);
		textNombreDoc.setBounds(10, 37, 134, 28);
		add(textNombreDoc);
		textNombreDoc.setColumns(10);
		
		JLabel lblDireccin = new JLabel("Dirección:");
		lblDireccin.setBounds(171, 10, 134, 28);
		add(lblDireccin);
		
		textDireccion = new JTextField(cons.getDireccion());
		textDireccion.setEditable(false);
		textDireccion.setColumns(10);
		textDireccion.setBounds(171, 37, 134, 28);
		add(textDireccion);
		
		JLabel lblNewLabel = new JLabel("Localidad");
		lblNewLabel.setBounds(10, 66, 134, 28);
		add(lblNewLabel);
		
		textLocalidad = new JTextField(cons.getLocalidad());
		textLocalidad.setEditable(false);
		textLocalidad.setColumns(10);
		textLocalidad.setBounds(10, 92, 134, 28);
		add(textLocalidad);
		
		JLabel lblPostcode = new JLabel("Codigo postal");
		lblPostcode.setBounds(171, 66, 134, 28);
		add(lblPostcode);
		
		textPostCode = new JTextField(cons.getPostCode());
		textPostCode.setEditable(false);
		textPostCode.setColumns(10);
		textPostCode.setBounds(171, 92, 134, 28);
		add(textPostCode);
		
		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setBounds(10, 172, 134, 28);
		add(lblTelefono);
		
		textTelefono = new JTextField(cons.getTelefono());
		textTelefono.setEditable(false);
		textTelefono.setColumns(10);
		textTelefono.setBounds(10, 196, 134, 28);
		add(textTelefono);
		
		JLabel lblLatitud = new JLabel("Latitud");
		lblLatitud.setBounds(10, 120, 134, 28);
		add(lblLatitud);
		
		textLatitud = new JTextField(cons.getLatitud()+"");
		textLatitud.setEditable(false);
		textLatitud.setColumns(10);
		textLatitud.setBounds(10, 145, 134, 28);
		add(textLatitud);
		
		JLabel lblLongitud = new JLabel("Longitud");
		lblLongitud.setBounds(171, 120, 134, 28);
		add(lblLongitud);
		
		textLongitud = new JTextField(cons.getLongitud()+"");
		textLongitud.setEditable(false);
		textLongitud.setColumns(10);
		textLongitud.setBounds(171, 145, 134, 28);
		add(textLongitud);
		
		JLabel lblCitasAgendadasEn = new JLabel("Citas agendadas en fecha de consulta");
		lblCitasAgendadasEn.setBounds(10, 225, 295, 50);
		add(lblCitasAgendadasEn);
		
		LlamaArrayList<Cita> cit = cons.citasEnFecha(fecha);
		StringBuilder citas = new StringBuilder();
		
		for(int i =0;i< cit.size();i++)
		{
			citas.append(cit.get(i).getHoraInicio().toLocalTime().toString()+"    -    "+cit.get(i).getHoraFin().toLocalTime().toString()+"\n");
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 262, 285, 90);
		add(scrollPane);
		
		textCitas = new JTextArea(citas.toString());
		scrollPane.setViewportView(textCitas);
		
		JButton btnBack = new JButton("Back");
		btnBack.setActionCommand("BACK");
		btnBack.addActionListener(this);
		btnBack.setBounds(30, 353, 89, 29);
		add(btnBack);
		
		JButton btnNewButton = new JButton("Agendar Cita");
		btnNewButton.setActionCommand("AGENDAR");
		btnNewButton.addActionListener(this);
		btnNewButton.setBounds(131, 353, 165, 29);
		add(btnNewButton);
		
		btnvercitas = new JButton("Ver citas agendadas en fecha");
		btnvercitas.setBounds(50, 389, 226, 29);
		btnvercitas.setActionCommand("CITAS");
		btnvercitas.addActionListener(this);
		add(btnvercitas);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("BACK"))
		{
			mainView.returnView();
		}
		else if(e.getActionCommand().equals("AGENDAR"))
		{
			mainView.updateView(ViewType.MAKE_APPOINTMENT);
		}
		else if(e.getActionCommand().equals("CITAS"))
		{
			mainView.updateView(ViewType.APPOINTMENTS_OFFICE_BYDATE);
		}
		
	}
}
