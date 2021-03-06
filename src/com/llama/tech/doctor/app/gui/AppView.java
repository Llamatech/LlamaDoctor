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
		LOCATION_VIEW,
		LOCATION_SELECTION_VIEW,
		APPOINTMENTS_VIEW,
		APPOINTMENTS_DATE_SELECTION_VIEW,
		APPOINTMENT_LIST_VIEW,
		APPOINTMENT_CANCELATION,
		OFFICES_VIEW,
		COUNTER,
		OFFICE_SEARCH_VIEW,
		HISTORY_VIEW,
		ACCOUNT_VIEW,
		NAVIGATOR,
		OFFICE_INFO,
		STATE_CHOOSER,
		MAKE_APPOINTMENT,
		OFFICE_EXPERIENCE,
		OFFICE_REGION,
		OFFICE_NEAR,
		APPOINTMENTS_OFFICE_BYDATE
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
	public static final Color opaqueBackground = new Color(0,0,0,128);
	
	protected ClassLoader classLoader = this.getClass().getClassLoader();
	protected ViewType id;
	protected Icon viewIcon;
	protected String viewTitle;
	protected MainView mainView;
	protected LlamaDict<String,String> viewInfo;
	protected Object pushedInfo;
	
	
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
	
	public void pushInfo(Object o)
	{
		pushedInfo = o;
	}
	
	public void verifyView()
	{
		
	}
	
	@Override
	public int compareTo(AppView o)
	{
		return 0;
	}
      

}
