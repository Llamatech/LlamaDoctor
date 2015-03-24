package com.llama.tech.doctor.app.gui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;

public class LlamaTextField extends JTextField
{

	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	public static final Color BLUE = new Color(9,148,210);
	public static final Color RED = new Color(229,0,0);
	public static final Color GREEN = new Color(0,128,0);
	

	public LlamaTextField()
	{
		super();
		setBorder(new TextFieldBorder());
		setBackground(new Color(233,233,233));
		repaint();
	}
	
	public static class TextFieldBorder extends AbstractBorder
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Color color;
		
		public TextFieldBorder()
		{
			color = BLUE;
		}
		
		public TextFieldBorder(Color c)
		{
			color = c;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) 
		{
			int w = width;
			int h = height;
			
			g.translate(x, y);
			g.setColor(color);
			g.drawLine(0, h-10, 0, h);
			g.drawLine(0, h-1, w-1, h-1);
			g.drawLine(w-1, h-1, w-1, h-10);
			g.translate(-x, -y);
	    }
	}
	
}
