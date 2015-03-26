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

public class AppointmentListView extends AppView implements ActionListener, ListSelectionListener
{
	private final static String RETURN = "Return";
	
	private JTextField textDoctor;
	private JTextField textDate;
	private JTextField textHour;
	private JList<Cita> list;
	private int appointmentQuantity = 0;
	
	public AppointmentListView(MainView main, LlamaArrayList<Cita> appointmentList)
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
	    
	    DefaultListModel<Cita> model = new DefaultListModel<Cita>();
	    
	    for(Cita c : appointmentList)
	    {
	    	model.addElement(c);
	    }
	    
	    appointmentQuantity = model.size();
	    
	    list = new JList<Cita>();
	    list.setModel(model);
	    list.addListSelectionListener(this);
	    list.setBounds(34, 23, 260, 141);
	    list.setBackground(backgroundColor);
	    list.setFont(font);
	    add(list);
	    
	    JLabel lblNombreProfesional = new JLabel("Nombre Profesional:");
	    lblNombreProfesional.setFont(font);
	    lblNombreProfesional.setBounds(50, 200, 194, 19);
	    add(lblNombreProfesional);
	    
	    textDoctor = new LlamaTextField();
	    textDoctor.setEditable(false);
	    textDoctor.setFont(font);
	    textDoctor.setBounds(50, 217, 224, 19);
	    add(textDoctor);
	    textDoctor.setColumns(10);
	    
	    JLabel lblFecha = new JLabel("Fecha:");
	    lblFecha.setFont(font);
	    lblFecha.setBounds(50, 244, 185, 19);
	    add(lblFecha);
	    
	    textDate = new LlamaTextField();
	    textDate.setEditable(false);
	    textDate.setFont(font);
	    textDate.setBounds(50, 264, 224, 19);
	    add(textDate);
	    textDate.setColumns(10);
	    
	    JLabel lblHora = new JLabel("Hora:");
	    lblHora.setFont(null);
	    lblHora.setBounds(50, 295, 185, 19);
	    add(lblHora);
	    
	    textHour = new LlamaTextField();
	    textHour.setFont(null);
	    textHour.setEditable(false);
	    textHour.setColumns(10);
	    textHour.setBounds(50, 315, 224, 19);
	    add(textHour);
	    
	    URL icon = classLoader.getResource(IMG_PATH+"ic_action_back.png");
	    ImageIcon ic = new ImageIcon(icon);
	    
	    JButton btnRegresar = new LlamaButton("Regresar", ic);
	    btnRegresar.addActionListener(this);
	    btnRegresar.setActionCommand(RETURN);
	    btnRegresar.setBounds(50, 346, 142, 25);
	    add(btnRegresar);
	    
	    
	}
	
	@Override
	public void verifyView()
	{	
		if(appointmentQuantity == 0)
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
	  		
	  		Toast.makeText(parent, "   El usuario no tiene citas agendadas   ", font).display();
	  		mainView.returnView();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) 
	{
		if(e.getSource().equals(list))
		{
			Cita seleccionada = list.getSelectedValue();
			if(seleccionada != null)
			{
				textDoctor.setText("");
				textDate.setText("");
				textHour.setText("");
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
	}
}
