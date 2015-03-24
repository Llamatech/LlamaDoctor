package com.llama.tech.doctor.app.gui.components;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JButton;

public class LlamaButton extends JButton 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String IMG_PATH = "res/assets/";
	private static final String FONT_PATH = "res/font/";
	
	private ClassLoader classLoader = this.getClass().getClassLoader();
	private Image background;
	

	public LlamaButton(String text)
	{
		super(text);
		initialize();
	}
	
	public LlamaButton(String text, Icon icon)
	{
		super(text, icon);
		initialize();
	}
	
    private void initialize()
    {
	    setContentAreaFilled(false);
		setBorderPainted(false);
		
		InputStream resource = classLoader.getResourceAsStream(FONT_PATH
				+ "Roboto-Regular.ttf");
		
		try 
		{
			InputStream resourceI = classLoader.getResourceAsStream(IMG_PATH+"button_unpressed.png");
			background = ImageIO.read(resourceI);
			Font font = Font.createFont(Font.TRUETYPE_FONT, resource);
			font = font.deriveFont(13F);
			setFont(font);
		} 
		catch (IOException | FontFormatException e) 
		{
			e.printStackTrace();
		}
		
		addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent evt)
            {
            	InputStream resourceI = classLoader.getResourceAsStream(IMG_PATH+"button_focused.png");
    			try 
    			{
					background = ImageIO.read(resourceI);
				} 
    			catch (IOException e) 
    			{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            public void mouseExited(MouseEvent evt)
            {
            	InputStream resourceI = classLoader.getResourceAsStream(IMG_PATH+"button_unpressed.png");
    			try 
    			{
					background = ImageIO.read(resourceI);
				} 
    			catch (IOException e) 
    			{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            public void mousePressed(MouseEvent evt)
            {
            	InputStream resourceI = classLoader.getResourceAsStream(IMG_PATH+"button_pressed.png");
    			try 
    			{
					background = ImageIO.read(resourceI);
				} 
    			catch (IOException e) 
    			{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            }
            public void mouseReleased(MouseEvent evt)
            {
            	InputStream resourceI = classLoader.getResourceAsStream(IMG_PATH+"button_focused.png");
    			try 
    			{
					background = ImageIO.read(resourceI);
				} 
    			catch (IOException e) 
    			{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
		
		repaint();
    }
	
	@Override
	public void paintComponent(Graphics g) 
	{
	    super.paintComponent(g);
	    g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
	}
	
	
}
