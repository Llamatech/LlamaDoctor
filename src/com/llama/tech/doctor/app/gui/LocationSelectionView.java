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

public class LocationSelectionView extends AppView implements ActionListener
{
	private JTextField textFieldCity;
	private JTextField textFieldZIP;
	private JTextField textFieldLat;
	private JTextField textFieldLong;
	private JButton btnModificar;
	private JButton btnCambiar;
	
	private static final String CANCEL = "Cancel";
	private static final String MODIFY = "Modify";
	
	private final static String CITY_REGEX = "^\\S+\\s*\\S*$";
	private final static String ZIP_REGEX = "^\\d{5}$";
	private final static String LATLONG_REGEX = "^[-]?\\d+[.]\\d+$";
	
	public LocationSelectionView(MainView main)
	{
		mainView = main;
		viewTitle = "Ubicación";
		URL iconURI = classLoader.getResource(IMG_PATH+"ic_action_place.png");
	    viewIcon = new ImageIcon(iconURI); 
	    
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
	    
	    JLabel lblDefini = new JLabel("Definir Ubicación");
	    lblDefini.setFont(font2);
	    lblDefini.setBounds(73, 12, 121, 15);
	    panel.add(lblDefini);
	    
	    JLabel lblCiudad = new JLabel("Ciudad:");
	    lblCiudad.setFont(font);
	    lblCiudad.setBounds(23, 60, 70, 15);
	    panel.add(lblCiudad);
	    
	    textFieldCity = new LlamaTextField();
	    textFieldCity.setBounds(125, 60, 114, 19);
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
							if(city.matches(CITY_REGEX))
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
	    
	    JLabel lblCdigoPostal = new JLabel("Código Postal:");
	    lblCdigoPostal.setBounds(23, 91, 103, 15);
	    lblCdigoPostal.setFont(font);
	    panel.add(lblCdigoPostal);
	    
	    textFieldZIP = new LlamaTextField();
	    textFieldZIP.setColumns(10);
	    textFieldZIP.setFont(font);
	    textFieldZIP.setBounds(125, 89, 114, 19);
	    
	    textFieldZIP.getDocument().addDocumentListener(
				new DocumentListener() 
				{
					public void changedUpdate(DocumentEvent e) 
					{						
						verifyZIP();
					}
					public void removeUpdate(DocumentEvent e) 
					{
						verifyZIP();
					}
					public void insertUpdate(DocumentEvent e) 
					{
						verifyZIP();
					}
					
					public void verifyZIP()
					{
						String zip = textFieldZIP.getText();
						if(zip != "")
						{
							if(zip.matches(ZIP_REGEX))
							{
								textFieldZIP.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
							}
							else
							{
								textFieldZIP.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.RED));
							}
						}
					}
				}
		);
	    
	    panel.add(textFieldZIP);
	    
	    JLabel lblLatitud = new JLabel("Latitud:");
	    lblLatitud.setFont(font);
	    lblLatitud.setBounds(23, 120, 103, 15);
	    panel.add(lblLatitud);
	    
	    textFieldLat = new LlamaTextField();
	    textFieldLat.setFont(font);
	    textFieldLat.setColumns(10);
	    textFieldLat.setBounds(125, 118, 114, 19);
	    panel.add(textFieldLat);
	    
	    JLabel lblLongitud = new JLabel("Longitud:");
	    lblLongitud.setFont(font);
	    lblLongitud.setBounds(23, 149, 103, 15);
	    panel.add(lblLongitud);
	    
	    textFieldLong = new LlamaTextField();
	    textFieldLong.setFont(font);
	    textFieldLong.setColumns(10);
	    textFieldLong.setBounds(125, 147, 114, 19);
	    panel.add(textFieldLong);
	    
	    
	    textFieldLat.getDocument().addDocumentListener(
				new DocumentListener() 
				{
					public void changedUpdate(DocumentEvent e) 
					{						
						verifyLatLong();
					}
					public void removeUpdate(DocumentEvent e) 
					{
						verifyLatLong();
					}
					public void insertUpdate(DocumentEvent e) 
					{
						verifyLatLong();
					}
					
					public void verifyLatLong()
					{
						String latitude = textFieldLat.getText();
						String longitude = textFieldLong.getText();
						if(latitude != "" && longitude == "")
						{
							if(latitude.matches(LATLONG_REGEX))
							{
								textFieldLat.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
							}
							else
							{
								textFieldLat.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.RED));
							}
							textFieldLong.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
						}
						else if(latitude == "" && longitude != "")
						{
							if(longitude.matches(LATLONG_REGEX))
							{
								textFieldLong.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
							}
							else
							{
								textFieldLong.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.RED));
							}
							textFieldLat.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
						}
						else
						{
							if(longitude.matches(LATLONG_REGEX))
							{
								textFieldLong.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
							}
							else
							{
								textFieldLong.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.RED));
							}
							
							if(latitude.matches(LATLONG_REGEX))
							{
								textFieldLat.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
							}
							else
							{
								textFieldLat.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.RED));
							}
							
						}
					}
				}
		);
	    
	    textFieldLong.getDocument().addDocumentListener(
				new DocumentListener() 
				{
					public void changedUpdate(DocumentEvent e) 
					{						
						verifyLatLong();
					}
					public void removeUpdate(DocumentEvent e) 
					{
						verifyLatLong();
					}
					public void insertUpdate(DocumentEvent e) 
					{
						verifyLatLong();
					}
					
					public void verifyLatLong()
					{
						String latitude = textFieldLat.getText();
						String longitude = textFieldLong.getText();
						if(latitude != "" && longitude == "")
						{
							if(latitude.matches(LATLONG_REGEX))
							{
								textFieldLat.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
							}
							else
							{
								textFieldLat.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.RED));
							}
							textFieldLong.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
						}
						else if(latitude == "" && longitude != "")
						{
							if(longitude.matches(LATLONG_REGEX))
							{
								textFieldLong.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
							}
							else
							{
								textFieldLong.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.RED));
							}
							textFieldLat.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
						}
						else
						{
							if(longitude.matches(LATLONG_REGEX))
							{
								textFieldLong.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
							}
							else
							{
								textFieldLong.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.RED));
							}
							
							if(latitude.matches(LATLONG_REGEX))
							{
								textFieldLat.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
							}
							else
							{
								textFieldLat.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.RED));
							}
							
						}
					}
				}
		);
	    
	    btnModificar = new LlamaButton("Cancelar");
	    btnModificar.addActionListener(this);
	    btnModificar.setActionCommand(CANCEL);
	    btnModificar.setBounds(23, 190, 96, 25);
	    panel.add(btnModificar);
	    
	    btnCambiar = new LlamaButton("Cambiar");
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
			
			String latitude = textFieldLat.getText();
			String longitude = textFieldLong.getText();
			String zipCode = textFieldZIP.getText();
			String city = textFieldCity.getText();
			
			if(latitude != "" && longitude != "")
			{
				if(latitude.matches(LATLONG_REGEX) && longitude.matches(LATLONG_REGEX))
				{
					try 
					{
						viewInfo.addEntry("latitud",latitude);
						viewInfo.addEntry("longitud",longitude);
						mainView.changeLocation();
					} 
					catch (UnhashableTypeException e1) 
					{
						e1.printStackTrace();
					}
				}
				else
				{
					Toast.makeText(parent, "   Ingrese un par coordenado, código postal o ciudad válido   ", font).display();
				}
			}
			if (zipCode != "") 
			{
				if (zipCode.matches(ZIP_REGEX)) 
				{
					try 
					{
						viewInfo.addEntry("codigoPostal", zipCode);
						mainView.changeLocation();
					} 
					catch (UnhashableTypeException e1) 
					{
						e1.printStackTrace();
					}
				} 
				else 
				{
					Toast.makeText(
							parent,
							"   Ingrese un par coordenado, código postal o ciudad válido   ",
							font).display();
				}
			}
			if (city != "") 
			{
				if (city.matches(CITY_REGEX)) 
				{
					try 
					{
						viewInfo.addEntry("ciudad", city);
						mainView.changeLocation();
					} 
					catch (UnhashableTypeException e1) 
					{
						e1.printStackTrace();
					}
				}
			} 
			else 
			{
				Toast.makeText(parent, "   Ingrese un par coordenado, código postal o ciudad válido   ",font).display();
			}
		}
		
			
		
	}
}
