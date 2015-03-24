package com.llama.tech.doctor.app.gui.components;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

public class LlamaCheckBox extends JToggleButton
{
	private static final String IMG_PATH = "res/assets/";
	private static final String FONT_PATH = "res/font/";
	private ClassLoader classLoader = this.getClass().getClassLoader();
	private boolean selected = false;
	
	public LlamaCheckBox(String text, boolean sel)
	{
		super(text, sel);
		setContentAreaFilled(false);
		setBorder(null);
		setBorderPainted(false);
		
		InputStream resource = classLoader.getResourceAsStream(FONT_PATH
				+ "Roboto-Regular.ttf");
		
		try 
		{
			URL checkIcon = null;
			if(!sel)
			{
				checkIcon = classLoader.getResource(IMG_PATH+"checkbox-unchecked-unfocused.png");
			}
			else
			{
				checkIcon = classLoader.getResource(IMG_PATH+"checkbox-checked-unfocused.png");
			}
			//InputStream resourceI = classLoader.getResourceAsStream(IMG_PATH+"button_unpressed.png");
			//background = ImageIO.read(resourceI);
			Font font = Font.createFont(Font.TRUETYPE_FONT, resource);
			font = font.deriveFont(13F);
			setFont(font);
			ImageIcon ic = new ImageIcon(checkIcon);
			setIcon(ic);
			setSelected(sel);
			setText(text);
		} 
		catch (IOException | FontFormatException e) 
		{
			e.printStackTrace();
		}
		
		addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent evt)
            {
            	if(selected)
            	{
            		URL checkIcon = classLoader.getResource(IMG_PATH+"checkbox-checked-focused.png");
            		ImageIcon ic = new ImageIcon(checkIcon);
        			setIcon(ic);
            	}
            	else
            	{
            		URL checkIcon = classLoader.getResource(IMG_PATH+"checkbox-unchecked-focused.png");
            		ImageIcon ic = new ImageIcon(checkIcon);
        			setIcon(ic);
            	}
            	repaint();
            }
            public void mouseExited(MouseEvent evt)
            {
            	if(selected)
            	{
            		URL checkIcon = classLoader.getResource(IMG_PATH+"checkbox-checked-unfocused.png");
            		ImageIcon ic = new ImageIcon(checkIcon);
        			setIcon(ic);
            	}
            	else
            	{
            		URL checkIcon = classLoader.getResource(IMG_PATH+"checkbox-unchecked-unfocused.png");
            		ImageIcon ic = new ImageIcon(checkIcon);
        			setIcon(ic);
            	}
            	repaint();
            	
            }
            public void mousePressed(MouseEvent evt)
            {
            	if(selected)
            	{
            		URL checkIcon = classLoader.getResource(IMG_PATH+"checkbox-checked-pressed.png");
            		ImageIcon ic = new ImageIcon(checkIcon);
        			setIcon(ic);
        			setSelected(false);
        			selected = false;
            	}
            	else
            	{
            		URL checkIcon = classLoader.getResource(IMG_PATH+"checkbox-unchecked-pressed.png");
            		ImageIcon ic = new ImageIcon(checkIcon);
        			setIcon(ic);
        			setSelected(true);
        			selected = true;
            	}
            	repaint();
            }
            public void mouseReleased(MouseEvent evt)
            {
            	if(selected)
            	{
            		URL checkIcon = classLoader.getResource(IMG_PATH+"checkbox-checked-focused.png");
            		ImageIcon ic = new ImageIcon(checkIcon);
        			setIcon(ic);
        			setSelected(true);
        			selected = true;
            	}
            	else
            	{
            		URL checkIcon = classLoader.getResource(IMG_PATH+"checkbox-unchecked-focused.png");
            		ImageIcon ic = new ImageIcon(checkIcon);
        			setIcon(ic);
        			setSelected(false);
        			selected = false;
            	}
            	repaint();
            }
        });
		
		repaint();
	}
	


	
//		if(selected)
//		{
//			setSelected(false);
//		}
//		else
//		{
//			setSelected(true);
//		}
		

}
