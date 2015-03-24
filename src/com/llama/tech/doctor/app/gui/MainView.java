package com.llama.tech.doctor.app.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.net.URISyntaxException;

import javax.swing.JPanel;

import com.llama.tech.doctor.app.gui.AppView.ViewType;
import com.llama.tech.utils.dict.LlamaDict;
import com.llama.tech.utils.list.ListaSimplementeEnlazada;

public class MainView extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ListaSimplementeEnlazada<AppView> stack;
	private ActionBar actionBar;
	private AppView viewPort;
	private AppView navigationDrawer;
	
	private boolean actionBarInit = false;
	private boolean navigationDrawerAct = false;
	
	private String loggedUser = null;
	private String password = null;
	
	public MainView() 
	{
		stack = new ListaSimplementeEnlazada<AppView>();
		setSize(new Dimension(328, 488));
		setPreferredSize(new Dimension(328, 488));
		setMinimumSize(new Dimension(328, 488));
		setMaximumSize(new Dimension(328, 488));
		setLayout(null);
		setBackground(new Color(233,233,233));
		try 
		{
			actionBar = new ActionBar(this);
			actionBar.setBounds(0, 0, 308, 47);
			//add(actionBar);
			
			viewPort = new LoginView(this);
			viewPort.setSize(new Dimension(328, 441));
			viewPort.setPreferredSize(new Dimension(328, 441));
			viewPort.setMinimumSize(new Dimension(328, 441));
			viewPort.setMaximumSize(new Dimension(328, 441));
			viewPort.setBounds(0, 48, 328, 440);
			add(viewPort);
			actionBar.repaint();
		} 
		catch (URISyntaxException e) 
		{
			e.printStackTrace();
		}
		
		stack.addAlFinal(viewPort);
		navigationDrawer = new NavigationDrawer(this);
		navigationDrawer.setBounds(0, 48, 167, 234);
	}
	
	public void updateView(ViewType type)
	{
		if(viewPort.getType() != ViewType.TOS_VIEW && viewPort.getType() != ViewType.SIGN_UP_VIEW)
		{
			stack.addAlPrincipio(viewPort);
		}
		if(type == ViewType.TOS_VIEW)
		{
			remove(viewPort);
			viewPort = new ToSView(this);
			viewPort.setSize(new Dimension(328, 441));
			viewPort.setPreferredSize(new Dimension(328, 441));
			viewPort.setMinimumSize(new Dimension(328, 441));
			viewPort.setMaximumSize(new Dimension(328, 441));
			viewPort.setBounds(0, 48, 328, 440);
			add(viewPort);
			viewPort.repaint();
			if(!actionBarInit)
			{
				add(actionBar);
				actionBarInit = true;
				actionBar.repaint();
			}
			actionBar.setViewIcon(viewPort.getIcon());
			actionBar.setViewTitle(viewPort.getTitle());
			actionBar.setNavigationButton(true);
			actionBar.repaint();
		}
		else if(type == ViewType.SIGN_UP_VIEW)
		{
			remove(viewPort);
			viewPort = new SignUpView(this);
			viewPort.setSize(new Dimension(328, 441));
			viewPort.setPreferredSize(new Dimension(328, 441));
			viewPort.setMinimumSize(new Dimension(328, 441));
			viewPort.setMaximumSize(new Dimension(328, 441));
			viewPort.setBounds(0, 48, 328, 440);
			add(viewPort);
			viewPort.repaint();
			actionBar.setViewIcon(viewPort.getIcon());
			actionBar.setViewTitle(viewPort.getTitle());
			actionBar.setNavigationButton(true);
			actionBar.repaint();
		}
		else if(type == ViewType.MAIN_MENU_VIEW)
		{
			remove(viewPort);
			viewPort = new MainMenuView(this);
			viewPort.setSize(new Dimension(328, 441));
			viewPort.setPreferredSize(new Dimension(328, 441));
			viewPort.setMinimumSize(new Dimension(328, 441));
			viewPort.setMaximumSize(new Dimension(328, 441));
			viewPort.setBounds(0, 48, 328, 440);
			add(viewPort);
			if(!actionBarInit)
			{
				add(actionBar);
				actionBarInit = true;
				actionBar.repaint();
			}
			viewPort.repaint();
			actionBar.setViewIcon(viewPort.getIcon());
			actionBar.setViewTitle(viewPort.getTitle());
			actionBar.setNavigationButton(false);
			actionBar.repaint();
		}
	}

	public void returnView() 
	{
		if(stack.size() > 0)
		{
			AppView lastView = stack.removeFirst();
			remove(viewPort);
			viewPort = lastView;
			viewPort.setSize(new Dimension(328, 441));
			viewPort.setPreferredSize(new Dimension(328, 441));
			viewPort.setMinimumSize(new Dimension(328, 441));
			viewPort.setMaximumSize(new Dimension(328, 441));
			viewPort.setBounds(0, 48, 328, 440);
			add(viewPort);
			viewPort.repaint();
			if(lastView.getType() == ViewType.LOGIN_VIEW)
			{
				if(actionBarInit)
				{
					remove(actionBar);
					actionBarInit = false;
					repaint();
				}
			}
			else
			{
				actionBar.setViewIcon(viewPort.getIcon());
				actionBar.setViewIcon(viewPort.getIcon());
				actionBar.setViewTitle(viewPort.getTitle());
				actionBar.repaint();
			}
		}	
	}
	
	public void showDrawer() 
	{
		if(!navigationDrawerAct)
		{
			navigationDrawerAct = true;
			add(navigationDrawer);
			navigationDrawer.repaint();
		}
		else
		{
			navigationDrawerAct = false;
			remove(navigationDrawer);
			repaint();
		}
		
	}
	
	public void menuSelection(ViewType viewType) 
	{
		
	}
	

	public boolean queryUserName(String username) 
	{
		// TODO Query user name
		return true;
	}

	public void registerUser() 
	{
		LlamaDict<String, String> userInfo = viewPort.getViewFormInfo();
		// TODO Register user
		loggedUser = userInfo.getValue("usuario");
		password = userInfo.getValue("password");
		updateView(AppView.ViewType.MAIN_MENU_VIEW);
		
	}

	public boolean verifyUserInfo(String user, String password) 
	{
		//TODO Verify user information
		return true;
	}

	public void login() 
	{
		LlamaDict<String, String> userInfo = viewPort.getViewFormInfo();
		loggedUser = userInfo.getValue("usuario");
		password = userInfo.getValue("password");
		updateView(AppView.ViewType.MAIN_MENU_VIEW);
	}


	

}
