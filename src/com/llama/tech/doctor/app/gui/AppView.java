package com.llama.tech.doctor.app.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.io.InputStream;

import javax.swing.Icon;
import javax.swing.JPanel;

import com.llama.tech.utils.dict.LlamaDict;

public abstract class AppView extends JPanel implements Comparable<AppView>
{
	
	public static enum ViewType
	{
		LOGIN_VIEW,
		TOS_VIEW,
		SIGN_UP_VIEW,
		MAIN_MENU_VIEW,
		NAVIGATOR
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static int WIDTH = 328;
	public final static int HEIGHT = 441;
	protected Dimension size = new Dimension(WIDTH, HEIGHT);
	public static final String IMG_PATH = "res/assets/";
	public static final String FONT_PATH = "res/font/";
	public static final Color backgroundColor = new Color(233,233,233);
	
	protected ClassLoader classLoader = this.getClass().getClassLoader();
	protected ViewType id;
	protected Icon viewIcon;
	protected String viewTitle;
	protected MainView mainView;
	protected LlamaDict<String,String> viewInfo;
	
	
	public Icon getIcon()
	{
		return viewIcon;
	}
	
	public String getTitle()
	{
		return viewTitle;
	}
	
	public ViewType getType()
	{
		return id;
	}
	
	public LlamaDict<String,String> getViewFormInfo()
	{
		return viewInfo;
	}
	
	
	@Override
	public int compareTo(AppView o)
	{
		return 0;
	}
      

}
