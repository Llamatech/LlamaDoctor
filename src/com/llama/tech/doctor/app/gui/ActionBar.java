package com.llama.tech.doctor.app.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ActionBar extends JPanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String IMG_PATH = "res/assets/";
	private static final String FONT_PATH = "res/font/";
	
	private final static String BACK = "Bach";
	private final static String DRAWER = "Drawer";
	
	private Image background;
	private JLabel viewTitle;
	private JButton navigationDrawerButton;
	private JLabel viewIcon;
	private ClassLoader classLoader = this.getClass().getClassLoader();
	private MainView mainView;
	/**
	 * Create the panel.
	 * @throws URISyntaxException 
	 */
	public ActionBar(MainView main) throws URISyntaxException 
	{
		mainView = main;
		
		setPreferredSize(new Dimension(308, 47));
		setMinimumSize(new Dimension(308, 47));
		setMaximumSize(new Dimension(308, 47));
		setLayout(null);
		setVisible(true);
		
		navigationDrawerButton = new JButton("");
		navigationDrawerButton.setSize(new Dimension(47, 47));
		navigationDrawerButton.setPreferredSize(new Dimension(47, 47));
		navigationDrawerButton.setMaximumSize(new Dimension(47, 47));
		navigationDrawerButton.setMinimumSize(new Dimension(47, 47));
		navigationDrawerButton.setContentAreaFilled(false);
		navigationDrawerButton.setBorderPainted(false);
		navigationDrawerButton.setBorder(null);
		navigationDrawerButton.setBounds(0, 1, 47, 47);
		navigationDrawerButton.setBackground(new Color(220,220,220));
		navigationDrawerButton.addActionListener(this);
		add(navigationDrawerButton);
		
		viewTitle = new JLabel("LlamaDoctor");
		viewTitle.setHorizontalAlignment(SwingConstants.CENTER);
		try 
		{
			InputStream resource = classLoader.getResourceAsStream(FONT_PATH+"DIN.ttf");
			Font font = Font.createFont(Font.TRUETYPE_FONT, resource);
			//System.out.println(font);
			font = font.deriveFont(17F);
			//System.out.println(font);
			viewTitle.setFont(font);
			//viewTitle.setFont(new Font("DIN Alternate Medium", Font.PLAIN, 17));
		} 
		catch (FontFormatException | IOException e1) 
		{
			viewTitle.setFont(new Font("Dialog", Font.BOLD, 15));
		}
		viewTitle.setBounds(65, 12, 172, 25);
		add(viewTitle);
		
		URL resource = classLoader.getResource(IMG_PATH+"ic_action_search.png");

		ImageIcon ic = new ImageIcon(resource);
		viewIcon = new JLabel("");
		viewIcon.setMaximumSize(new Dimension(47, 47));
		viewIcon.setMinimumSize(new Dimension(47, 47));
		viewIcon.setSize(new Dimension(47, 47));
		viewIcon.setIcon(ic);
		viewIcon.setBounds(249, 1, 47, 47);
		add(viewIcon);
		try 
		{
			InputStream resourceI = classLoader.getResourceAsStream(IMG_PATH+"action_bar.png");
			background = ImageIO.read(resourceI);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		repaint();
	}
	
	public void setViewTitle(String title)
	{
		viewTitle.setText(title);
	}
	
	public void setViewIcon(Icon icon)
	{
		viewIcon.setIcon(icon);
	}
	
	public void setNavigationButton(boolean returnView)
	{
		if(returnView)
		{
			URL resource = classLoader.getResource(IMG_PATH+"ic_action_back.png");
			ImageIcon ic = new ImageIcon(resource);
			navigationDrawerButton.setIcon(ic);
			navigationDrawerButton.setActionCommand(BACK);
		}
		else
		{
			URL resource = classLoader.getResource(IMG_PATH+"ic_drawer.png");
			ImageIcon ic = new ImageIcon(resource);
			navigationDrawerButton.setIcon(ic);
			navigationDrawerButton.setActionCommand(DRAWER);
		}
		repaint();
	}
	
	
	@Override
	public void paintComponent(Graphics g) 
	{
	    super.paintComponent(g);
	    g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals(BACK))
		{
			mainView.returnView();
		}
		else if(e.getActionCommand().equals(DRAWER))
		{
			mainView.showDrawer();
		}
		
	}
}
