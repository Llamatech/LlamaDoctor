package com.llama.tech.doctor.app.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

import com.llama.tech.doctor.app.gui.components.LlamaButton;
import com.llama.tech.doctor.app.gui.components.LlamaCheckBox;

public class ToSView extends AppView implements ActionListener
{
	private JToggleButton tglbtnNewToggleButton;
	private JButton btnOkDeseoRegistrarme;
	
	private static final String SIGN_UP = "Sign_Up";

	public ToSView(MainView mainView) 
	{
		this.mainView = mainView;
		id = ViewType.TOS_VIEW;
	    viewTitle = "Registro";
	    URL iconURI = classLoader.getResource(IMG_PATH+"ic_action_add_person.png");
	    viewIcon = new ImageIcon(iconURI); 
	    
	    
		setSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);
		setBackground(backgroundColor);
		
		JTextArea txtrBienvenidoYGracias = new JTextArea();
		
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
		
		txtrBienvenidoYGracias.setFont(font);
		txtrBienvenidoYGracias.setText("Bienvenido, y gracias por elegir a \nLlamaDoctor como su asistente \npersonal en el cuidado de su salud. \nCon esta aplicación, Ud podrá \ncontar con el apoyo de los mejores \nprofesionales en cada una de las\náreas  especializadas de la medicina, \nal poder  gestionar citas y elegir \nhorarios de atención  en los consu-\nltorios más cercanos a su posición.");
		txtrBienvenidoYGracias.setBackground(backgroundColor);
		txtrBienvenidoYGracias.setBorder(null);
		txtrBienvenidoYGracias.setEditable(false);
		txtrBienvenidoYGracias.setBounds(48, 53, 242, 190);
		add(txtrBienvenidoYGracias);
		
		tglbtnNewToggleButton = new LlamaCheckBox("Aceptar Términos y Condiciones", false);
		tglbtnNewToggleButton.setBounds(38, 255, 226, 25);
		add(tglbtnNewToggleButton);
		
		btnOkDeseoRegistrarme = new LlamaButton("Ok, deseo registrarme");
		btnOkDeseoRegistrarme.setBounds(38, 292, 242, 25);
		btnOkDeseoRegistrarme.addActionListener(this);
		btnOkDeseoRegistrarme.setActionCommand(SIGN_UP);
		add(btnOkDeseoRegistrarme);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals(SIGN_UP))
		{
			if(tglbtnNewToggleButton.isSelected())
			{
				mainView.updateView(ViewType.SIGN_UP_VIEW);
			}
		}
		
	}
}
