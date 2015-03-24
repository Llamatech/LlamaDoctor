package com.llama.tech.doctor.app.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.llama.tech.doctor.app.gui.components.LlamaButton;
import com.llama.tech.doctor.app.gui.components.LlamaTextField;
import com.llama.tech.doctor.app.gui.components.Toast;
import com.llama.tech.utils.dict.LlamaDict;
import com.llama.tech.utils.dict.LlamaDict.UnhashableTypeException;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SignUpView extends AppView implements ActionListener
{
	private JDateChooser dateChooser;
	private LlamaTextField textFieldApellido;
	private JTextField textFieldNombre;
	private LlamaTextField textFieldUsuario;
	private JPasswordField textFieldContrasena;
	private JPasswordField textFieldConfirmacion;
	private JButton btnestoyPreparado;
	
	private static final String NAME_REGEX = "^\\S+\\s*\\S*$";
	private static final String USER_REGEX = "^\\w{4}\\w{0,6}$";
	private static final String PASSWORD_REGEX = "^\\w{5}\\w{0,7}$";
	
	
	boolean[] info = {false, false, false};
	
	private static final String LOGIN = "LOGIN";
	
	public SignUpView(MainView main)
	{
		mainView = main;
		viewTitle = "Registro";
		URL iconURI = classLoader.getResource(IMG_PATH+"ic_action_add_person.png");
	    viewIcon = new ImageIcon(iconURI); 
	    
	    viewInfo = new LlamaDict<String,String>(5);
	    
	    setSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);
		setBackground(backgroundColor);
		
		
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
		
		JTextArea txtrPorFavorIntroduzca = new JTextArea();
		txtrPorFavorIntroduzca.setFont(font);
		txtrPorFavorIntroduzca.setText("Por favor, introduzca la información que se \nsolicita a continuación,  será tenida en cuenta \npara la donación de órganos... Bueno, no, \npero si hará de esta, una gran experiencia.");
		txtrPorFavorIntroduzca.setBorder(null);
		txtrPorFavorIntroduzca.setEditable(false);
		txtrPorFavorIntroduzca.setBackground(backgroundColor);
		txtrPorFavorIntroduzca.setBounds(19, 12, 297, 73);
		add(txtrPorFavorIntroduzca);
		
		JLabel lblUsuario = new JLabel("Nombre:");
		lblUsuario.setBounds(40, 97, 70, 15);
		lblUsuario.setFont(font2);
		add(lblUsuario);
		
		textFieldNombre = new LlamaTextField();
		textFieldNombre.setFont(font);
		textFieldNombre.setBounds(160, 97, 128, 19);
		add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(40, 126, 70, 15);
		lblApellido.setFont(font2);
		add(lblApellido);
		
		textFieldApellido = new LlamaTextField();
		textFieldApellido.setFont(font);
		textFieldApellido.setColumns(10);
		textFieldApellido.setBounds(160, 124, 128, 19);
		add(textFieldApellido);
		
		
		JLabel lblFechaDeNacimiento = new JLabel("Fecha de Nacimiento:");
		lblFechaDeNacimiento.setBounds(40, 159, 152, 15);
		lblFechaDeNacimiento.setFont(font2);
		add(lblFechaDeNacimiento);
		
		dateChooser = new JDateChooser();
		dateChooser.setFont(font);
		dateChooser.setDateFormatString("dd/MM/yyyy");
		dateChooser.setBounds(184, 155, 104, 19);
		add(dateChooser);
		
		JLabel lblUsuario_1 = new JLabel("Usuario:");
		lblUsuario_1.setBounds(40, 209, 70, 15);
		lblUsuario_1.setFont(font2);
		add(lblUsuario_1);
		
		textFieldUsuario = new LlamaTextField();
		textFieldUsuario.setFont(font);
		textFieldUsuario.setColumns(10);
		textFieldUsuario.setBounds(160, 207, 128, 19);
		
		textFieldUsuario.getDocument().addDocumentListener(
				new DocumentListener() {
					public void changedUpdate(DocumentEvent e) 
					{						
						if(textFieldUsuario.getText() != "")
						{
	                        boolean userExists = mainView.queryUserName(textFieldUsuario.getText()); 
	                        if(!userExists)
	                        {
	                        	textFieldUsuario.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
	                        	info[0] = true;
	                        }
	                        else
	                        {
	                        	textFieldUsuario.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.RED));
	                        	info[0] = false;
	                        }
						}
						else
						{
							textFieldUsuario.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.BLUE));
							info[0] = false;
						}
					}
					public void removeUpdate(DocumentEvent e) 
					{
						if(textFieldUsuario.getText() != "")
						{
	                        boolean userExists = mainView.queryUserName(textFieldUsuario.getText()); 
	                        if(!userExists)
	                        {
	                        	textFieldUsuario.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
	                        	info[0] = true;
	                        }
	                        else
	                        {
	                        	textFieldUsuario.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.RED));
	                        	info[0] = false;
	                        }
						}
						else
						{
							textFieldUsuario.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.BLUE));
							info[0] = false;
						}
					}
					public void insertUpdate(DocumentEvent e) 
					{
						if(textFieldUsuario.getText() != "")
						{
	                        boolean userExists = mainView.queryUserName(textFieldUsuario.getText()); 
	                        if(!userExists)
	                        {
	                        	textFieldUsuario.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
	                        	info[0] = true;
	                        }
	                        else
	                        {
	                        	textFieldUsuario.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.RED));
	                        	info[0] = false;
	                        }
						}
						else
						{
							textFieldUsuario.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.BLUE));
							info[0] = false;
						}
					}
				}
		);
		add(textFieldUsuario);
		
		JLabel lblContrasea = new JLabel("Contraseña:");
		lblContrasea.setBounds(40, 238, 102, 15);
		lblContrasea.setFont(font2);
		add(lblContrasea);
		
		textFieldContrasena = new JPasswordField();
		textFieldContrasena.setBackground(backgroundColor);
		textFieldContrasena.setBorder(new LlamaTextField.TextFieldBorder());
		textFieldContrasena.setColumns(10);
		textFieldContrasena.setBounds(160, 236, 128, 19);
		
		textFieldContrasena.getDocument().addDocumentListener(
				new DocumentListener() {
					public void changedUpdate(DocumentEvent e) 
					{
                         if(textFieldContrasena.getPassword() != null)
                         {
                        	 textFieldConfirmacion.setEnabled(true);
                        	 if(textFieldConfirmacion.getPassword() != null)
                        	 {
                        		 compare();
                        	 }
                         }
					}
					public void removeUpdate(DocumentEvent e) 
					{
						if(textFieldContrasena.getPassword() != null)
                        {
                       	    textFieldConfirmacion.setEnabled(true);
	                       	 if(textFieldConfirmacion.getPassword() != null)
	                    	 {
	                    		 compare();
	                    	 }
                        }
					}
					public void insertUpdate(DocumentEvent e) 
					{
						if(textFieldContrasena.getPassword() != null)
                        {
                       	 	textFieldConfirmacion.setEnabled(true);
	                       	 if(textFieldConfirmacion.getPassword() != null)
	                    	 {
	                    		 compare();
	                    	 }
                        }
					}
					
					public void compare()
					{
						char[] inputPassword = textFieldContrasena.getPassword();
						char[] confirmPassword = textFieldConfirmacion.getPassword();
						boolean equal = true;
						if(inputPassword.length != confirmPassword.length)
						{
							equal = false;
						}
						else
						{
							for(int i = 0; i < inputPassword.length && equal; i++)
							{
                                 if(inputPassword[i] != confirmPassword[i])
                                 {
                                	 equal = false;
                                 }
							}
						}
						
						if(!equal)
						{
							textFieldContrasena.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.RED));
							textFieldConfirmacion.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.RED));
							info[1] = false;
							info[2] = false;
						}
						else
						{
							textFieldContrasena.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
							textFieldConfirmacion.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
							info[1] = true;
							info[2] = true;
						}
					}
				});
		
		add(textFieldContrasena);
		
		JLabel lblConfirmar = new JLabel("Confirmar:");
		lblConfirmar.setBounds(40, 267, 102, 15);
		lblConfirmar.setFont(font2);
		add(lblConfirmar);
		
		textFieldConfirmacion = new JPasswordField();
		textFieldConfirmacion.setBackground(backgroundColor);
		textFieldConfirmacion.setBorder(new LlamaTextField.TextFieldBorder());
		textFieldConfirmacion.setColumns(10);
		textFieldConfirmacion.setBounds(160, 265, 128, 19);
		textFieldConfirmacion.setEnabled(false);
		
		textFieldConfirmacion.getDocument().addDocumentListener(
				new DocumentListener() {
					public void changedUpdate(DocumentEvent e) 
					{
                        compare();
					}
					public void removeUpdate(DocumentEvent e) 
					{
						compare();
					}
					public void insertUpdate(DocumentEvent e) 
					{
						compare();
					}
					
					public void compare()
					{
						char[] inputPassword = textFieldContrasena.getPassword();
						char[] confirmPassword = textFieldConfirmacion.getPassword();
						boolean equal = true;
						if(inputPassword.length != confirmPassword.length)
						{
							equal = false;
						}
						else
						{
							for(int i = 0; i < inputPassword.length && equal; i++)
							{
                                 if(inputPassword[i] != confirmPassword[i])
                                 {
                                	 equal = false;
                                 }
							}
						}
						
						if(!equal)
						{
							textFieldContrasena.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.RED));
							textFieldConfirmacion.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.RED));
							info[1] = false;
							info[2] = false;
						}
						else
						{
							textFieldContrasena.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
							textFieldConfirmacion.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.GREEN));
							info[1] = true;
							info[2] = true;
						}
					}
				});
		
		add(textFieldConfirmacion);
		
		btnestoyPreparado = new LlamaButton("¡Estoy preparado!");
		btnestoyPreparado.setBounds(62, 346, 190, 25);
		add(btnestoyPreparado);
	    
		btnestoyPreparado.addActionListener(this);
		btnestoyPreparado.setActionCommand(LOGIN);
	}
	
	private boolean contextVerification()
	{
		return info[0] && info[1] && info[2];
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
	      if(e.getActionCommand().equals(LOGIN))
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
	  		
	  		if(contextVerification())
	  		{
	  		
		  		String nombre = textFieldNombre.getText();
		  		if(!nombre.matches(NAME_REGEX))
		  		{
		  			Toast.makeText(parent, "   Por favor, ingrese su nombre  ", font).display();
		  			
		  		}
		  		else
		  		{
			  		String apellido = textFieldApellido.getText();
			  		if(!apellido.matches(NAME_REGEX))
			  		{
			  			Toast.makeText(parent, "   Por favor, ingrese su apellido  ", font).display();
			  		}
			  		else
			  		{
			  			
				  		Date date = dateChooser.getDate();
				  		if(date == null)
				  		{
				  			Toast.makeText(parent, "   Por favor, ingrese su fecha de nacimiento  ", font).display();
				  		}
				  		else
				  		{
					  		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
					  		String fecha = sdf.format(date);
					  		
					  		String usuario = textFieldUsuario.getText();
					  		if(!usuario.matches(USER_REGEX))
					  		{
					  			Toast.makeText(parent, "   El nombre de usuario debe contener 4-10 caractéres  ", font).display();
					  		}
					  		else
					  		{
					  		
						  		char[] password = textFieldContrasena.getPassword();
						  		String pass = new String(password);
						  		
						  		if(!pass.matches(PASSWORD_REGEX))
						  		{
						  			Toast.makeText(parent, "   La contraseña debe contener 5-12 caractéres  ", font).display();
						  		}
						  		else
						  		{
						  			try 
						  			{
										viewInfo.addEntry("nombre", nombre);
										viewInfo.addEntry("apellido", apellido);
										viewInfo.addEntry("fecha", fecha);
										viewInfo.addEntry("usuario", usuario);
										viewInfo.addEntry("password", pass);
										Toast.makeText(parent, "  Usuario registrado exitosamente  ", Toast.Style.SUCCESS, font).display();
										mainView.registerUser();
									} 
						  			catch (UnhashableTypeException e1) 
						  			{
										e1.printStackTrace();
									}
						  		}
					  		}
				  		}
			  		}
		  		}
		  		
	  		}
	  		else
	  		{
	  			if(!info[0])
	  			{
	  				if(textFieldNombre.getText() == "")
	  				{
	  					Toast.makeText(parent, "   El nombre de usuario debe contener 4-10 caractéres  ", font).display();
	  				}
	  				else
	  				{
	  					Toast.makeText(parent, "   El nombre de usuario, ya existe   ", font).display();
	  				}
	  			}
	  			else if(!info[1] && !info[2])
	  			{
	  				Toast.makeText(parent, "   Las contraseñas ingresadas no son iguales   ", font).display();
	  			}
	  		}
	    	
	  		
	    	  
	    	  
	    	  
	      }
	}
}
