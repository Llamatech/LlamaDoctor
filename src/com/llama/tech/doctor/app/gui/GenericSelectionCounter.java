package com.llama.tech.doctor.app.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.llama.tech.doctor.app.gui.components.LlamaButton;
import com.llama.tech.doctor.app.gui.components.LlamaTextField;
import com.llama.tech.doctor.app.gui.components.Toast;
import com.llama.tech.utils.dict.LlamaDict;
import com.llama.tech.utils.dict.LlamaDict.UnhashableTypeException;

public class GenericSelectionCounter extends AppView implements ActionListener
{
	private JTextField textFieldCity;
	private JButton btnModificar;
	private JButton btnCambiar;
	
	private static final String CANCEL = "Cancel";
	private static final String MODIFY = "Modify";
	
	private final static String DIGIT_REGEX = "^\\d+$";
	private final static String STATE_REGEX = "[A-Z]{2}";
	
	private String regex = DIGIT_REGEX;
	private String subs = "Cantidad";
	
	public GenericSelectionCounter(MainView main, boolean integer)
	{
		mainView = main;
		viewTitle = "Ubicación";
		URL iconURI = classLoader.getResource(IMG_PATH+"ic_action_place.png");
	    viewIcon = new ImageIcon(iconURI); 
	    
	    if(!integer)
	    {
	    	regex = STATE_REGEX;
	    	subs = "Estado";
	    }
	    
	    viewInfo = new LlamaDict<String, String>(4);
	    
	    setSize(new Dimension(WIDTH, HEIGHT));
	    setLayout(null);
	    setBackground(opaqueBackground);
	    
	    JPanel panel = new JPanel();
	    panel.setBackground(backgroundColor);
	    panel.setBounds(23, 90, 264, 238);
	    add(panel);
	    panel.setLayout(null);
	    
	    InputStream resource = classLoader.getResourceAsStream(FONT_PATH
				+ "Roboto-Regular.ttf");
		InputStream resource_2 = classLoader.getResourceAsStream(FONT_PATH
				+ "DIN-AlternateMedium.ttf");
		
		Font font = null;
		Font font2 = null;
		try 
		{
			font = Font.createFont(Font.TRUETYPE_FONT, resource);
			font2 = Font.createFont(Font.TRUETYPE_FONT, resource_2);
			font = font.deriveFont(13F);
			font2 = font2.deriveFont(15F);
		} 
		catch (IOException | FontFormatException e) 
		{
			e.printStackTrace();
		}
	    
	    JLabel lblDefini = new JLabel("Definir "+subs);
	    lblDefini.setFont(font2);
	    lblDefini.setBounds(70, 39, 121, 15);
	    panel.add(lblDefini);
	    
	    JLabel lblCiudad = new JLabel(subs+":");
	    lblCiudad.setFont(font);
	    lblCiudad.setBounds(23, 101, 70, 15);
	    panel.add(lblCiudad);
	    
	    textFieldCity = new LlamaTextField();
	    textFieldCity.setBounds(125, 101, 114, 19);
	    textFieldCity.setFont(font);
	    
	    textFieldCity.getDocument().addDocumentListener(
				new DocumentListener() 
				{
					public void changedUpdate(DocumentEvent e) 
					{						
						verifyCity();
					}
					public void removeUpdate(DocumentEvent e) 
					{
						verifyCity();
					}
					public void insertUpdate(DocumentEvent e) 
					{
						verifyCity();
					}
					
					public void verifyCity()
					{
						String city = textFieldCity.getText();
						if(city != "")
						{
							if(city.matches(regex))
							{
								textFieldCity.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
							}
							else
							{
								textFieldCity.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.RED));
							}
						}
					}
				}
		);
	    
	    panel.add(textFieldCity);
	    textFieldCity.setColumns(10);
	    
	    btnModificar = new LlamaButton("Cancelar");
	    btnModificar.addActionListener(this);
	    btnModificar.setActionCommand(CANCEL);
	    btnModificar.setBounds(23, 190, 96, 25);
	    panel.add(btnModificar);
	    
	    btnCambiar = new LlamaButton("Visualizar");
	    btnCambiar.addActionListener(this);
	    btnCambiar.setActionCommand(MODIFY);
	    btnCambiar.setBounds(136, 190, 103, 25);
	    panel.add(btnCambiar);
	    
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals(CANCEL))
		{
			mainView.returnView();
		}
		else if (e.getActionCommand().equals(MODIFY))
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
			
			String city = textFieldCity.getText();
			
			if(city != "")
			{
				if(city.matches(regex))
				{
				    if(regex.equals(DIGIT_REGEX))
				    {
				    	mainView.setCount(Integer.parseInt(city));
				    }
				    else if(regex.equals(STATE_REGEX))
				    {
				    	mainView.setState(city);
				    }
				}
				else
				{
					if(regex.equals(DIGIT_REGEX))
				    {
						mainView.makeToast("Debe ingresar un entero positivo");
				    }
					mainView.makeToast("Debe ingresar un estado válido");
				}
			}
			else
			{
				if(regex.equals(DIGIT_REGEX))
			    {
					mainView.makeToast("Debe ingresar un entero positivo");
			    }
				mainView.makeToast("Debe ingresar un estado válido");
			}
			
		}
		
			
		
	}
}
