package com.llama.tech.doctor.app.gui;


import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.llama.tech.doctor.app.gui.components.LlamaButton;
import com.llama.tech.doctor.app.gui.components.LlamaTextField;
import com.llama.tech.doctor.app.gui.components.Toast;
import com.llama.tech.doctor.mundo.UsuarioException;
import com.llama.tech.utils.dict.LlamaDict;
import com.llama.tech.utils.dict.LlamaDict.UnhashableTypeException;

public class LoginView extends AppView implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3825100605582869453L;
	private JLabel txtBienvendioALlamadoctor;
	private JTextField textFieldUsuario;
	private JPasswordField textFieldPass;
	private JButton btnNewButton_1;
	private JButton btnNewButton;
	
	private static final String USER_REGEX = "^\\w{4}\\w{0,6}$";
	private static final String PASSWORD_REGEX = "^\\w{5}\\w{0,7}$";
	
	private static final String LOGIN = "Login";
	private static final String SIGN_UP = "Sign Up";
	
	private boolean[] info = {false, false};
	
	public LoginView(MainView main) 
	{
		this.mainView = main;
		id = ViewType.LOGIN_VIEW;
		
		viewInfo = new LlamaDict<String, String>(4);
		
		setSize(size);
		setLayout(null);
		setBackground(new Color(233,233,233));
		
		Font font = null;
		Font font2 = null;
		Font font3 = null;
		
		InputStream resource = classLoader.getResourceAsStream(FONT_PATH
				+ "Roboto-Bold.ttf");
		InputStream resource_2 = classLoader.getResourceAsStream(FONT_PATH
				+ "Roboto-Black.ttf");
		InputStream resource_3 = classLoader.getResourceAsStream(FONT_PATH
				+ "Roboto-Regular.ttf");
		try 
		{
			font = Font.createFont(Font.TRUETYPE_FONT, resource);
			font2 = Font.createFont(Font.TRUETYPE_FONT, resource_2);
			font3 = Font.createFont(Font.TRUETYPE_FONT, resource_3);
		} 
		catch (FontFormatException | IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			//System.out.println(font);
	    font = font.deriveFont(14F);
	    font2 = font2.deriveFont(13F);
	    font3 = font3.deriveFont(13F); 
		
		btnNewButton_1 = new JButton("Registro");
		btnNewButton_1.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent evt)
            {
            	btnNewButton_1.setForeground(new Color(9,75,210));
            }
            public void mouseExited(MouseEvent evt)
            {
            	btnNewButton_1.setForeground(new Color(9,148,210));
            }
            public void mousePressed(MouseEvent evt)
            {   
            	btnNewButton_1.setForeground(new Color(9,10,210));
            }
            public void mouseReleased(MouseEvent evt)
            {
            	btnNewButton_1.setForeground(new Color(9,75,210));
            }
        });
		
		Map attributes = font3.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		btnNewButton_1.setFont(font3.deriveFont(attributes));
		btnNewButton_1.setForeground(new Color(9,148,210));
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setBounds(209, 328, 107, 25);
		btnNewButton_1.addActionListener(this);
		btnNewButton_1.setActionCommand(SIGN_UP);
		
		add(btnNewButton_1);
			//System.out.println(font);
			//viewTitle.setFont(new Font("DIN Alternate Medium", Font.PLAIN, 17));
		
		URL icon = classLoader.getResource(IMG_PATH+"imagen.png");
		ImageIcon ic = new ImageIcon(icon); 
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(ic);
		//lblNewLabel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(75, 0, 170, 170);
		add(lblNewLabel);
		
		txtBienvendioALlamadoctor = new JLabel();
		txtBienvendioALlamadoctor.setHorizontalAlignment(SwingConstants.CENTER);
		txtBienvendioALlamadoctor.setBorder(null);
		txtBienvendioALlamadoctor.setFont(font);
		txtBienvendioALlamadoctor.setText("¡Bienvenido a LlamaDoctor!");
		txtBienvendioALlamadoctor.setBackground(new Color(233,233,233));
		txtBienvendioALlamadoctor.setBounds(68, 182, 184, 19);
		add(txtBienvendioALlamadoctor);
		
		textFieldUsuario = new LlamaTextField();
		textFieldUsuario.setBounds(162, 227, 90, 19);
		
		textFieldUsuario.getDocument().addDocumentListener(
				new DocumentListener() 
				{
					public void changedUpdate(DocumentEvent e) 
					{						
						verify();
					}
					public void removeUpdate(DocumentEvent e) 
					{
						verify();
					}
					public void insertUpdate(DocumentEvent e) 
					{
						verify();
					}
					
					public void verify()
					{
						if(textFieldUsuario.getText() != "")
						{
							String user = textFieldUsuario.getText();
							if(user.matches(USER_REGEX))
							{
							
		                        boolean userExists = mainView.queryUserName(user); 
		                        if(userExists)
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
		textFieldUsuario.setColumns(10);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(font2);
		lblUsuario.setBounds(75, 231, 70, 15);
		add(lblUsuario);
		
		textFieldPass = new JPasswordField();
		textFieldPass.setBorder(new LlamaTextField.TextFieldBorder());
		textFieldPass.setBackground(new Color(233,233,233));
		textFieldPass.setColumns(10);
		textFieldPass.setBounds(162, 253, 90, 19);
		
		textFieldPass.getDocument().addDocumentListener(
				new DocumentListener() {
					public void changedUpdate(DocumentEvent e) 
					{						
                       if(textFieldPass.getPassword() != null)
                       {
                    	   verify();
                       }
					}
					public void removeUpdate(DocumentEvent e) 
					{
						if(textFieldPass.getPassword() != null)
	                    {
	                    	   verify();
	                    }
					}
					public void insertUpdate(DocumentEvent e) 
					{
						if(textFieldPass.getPassword() != null)
	                    {
	                    	   verify();
	                    }
					}
					
					public void verify()
					{
						char[] pass = textFieldPass.getPassword();
						String password = new String(pass);
						if(!password.matches(PASSWORD_REGEX))
						{
							textFieldPass.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.RED));
							info[1] = true;
						}
						else
						{
							textFieldPass.setBorder(new LlamaTextField.TextFieldBorder(LlamaTextField.BLUE));
							info[1] = true;
						}
					}
				}
		);
		
		add(textFieldPass);
		
		JLabel lblConstrasea = new JLabel("Contraseña:");
		lblConstrasea.setFont(font2);
		lblConstrasea.setBounds(51, 257, 107, 15);
		add(lblConstrasea);
		
		btnNewButton = new LlamaButton("Iniciar Sesión", null);
		btnNewButton.addActionListener(this);
		btnNewButton.setActionCommand(LOGIN);
		btnNewButton.setBounds(102, 291, 117, 25);
		add(btnNewButton);
		
		JTextArea txtrnoTieneUna = new JTextArea();
		txtrnoTieneUna.setFont(font3);
		txtrnoTieneUna.setText("¿No tiene una cuenta aún? \n¡Regístrese, es Llamatástico!");
		txtrnoTieneUna.setBackground(new Color(233,233,233));
		txtrnoTieneUna.setEditable(false);
		txtrnoTieneUna.setBounds(27, 328, 205, 37);
		add(txtrnoTieneUna);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		if(e.getActionCommand().equals(LOGIN))
		{
			JFrame parent = null;
			Container c = this.getParent();
			while (!(c instanceof JFrame) && (c != null)) 
			{
				c = c.getParent();
			}
			if (c != null) 
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
			
			
				String user = textFieldUsuario.getText();
				if(!user.matches(USER_REGEX))
				{
					Toast.makeText(parent, "   El nombre de usuario debe contener 4-10 caractéres   ", font).display();
				}
				else
				{
					if(!mainView.queryUserName(user))
					{
						Toast.makeText(parent, "   El nombre de usuario no existe   ", font).display();
					}
					else
					{
						char[] pass = textFieldPass.getPassword();
						if(pass != null)
						{
							String password = new String(pass);
							if(!password.matches(PASSWORD_REGEX))
							{
								Toast.makeText(parent, "   La contraseña debe contener 5-10 caractéres   ", font).display();
							}
							else
							{
								boolean verified = mainView.verifyUserInfo(user, password);
								if(verified)
								{
									try {
										viewInfo.addEntry("usuario", user);
										viewInfo.addEntry("password", password);
									} catch (UnhashableTypeException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									try
									{
										mainView.login();
									}
									catch(Exception eu)
									{
										Toast.makeText(parent, "   La contraseña ingresada es incorrecta   ", font).display();
									}
								}
								else
								{
									Toast.makeText(parent, "   La contraseña ingresada es incorrecta   ", font).display();
								}
							}
						}
						else
						{
							Toast.makeText(parent, "   Ingrese su contraseña, por favor   ", font).display();
						}
					}
				}
				
			
		}
		else if (e.getActionCommand().equals(SIGN_UP))
		{
			mainView.updateView(ViewType.TOS_VIEW);
		}
		
	}
}
