package com.llama.tech.doctor.app.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import com.llama.tech.doctor.app.gui.AppView.ViewType;
import com.llama.tech.doctor.mundo.Cita;
import com.llama.tech.utils.dict.LlamaDict;
import com.llama.tech.utils.list.ListaSimplementeEnlazada;
import com.llama.tech.utils.list.LlamaArrayList;

public class MainView extends JPanel implements Observer
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
	private LlamaDict<String, String> location = null;
	private LocalDateTime localTime = LocalDateTime.now();
	
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
			if(stack.size() > 0)
			{
				stack.addAlPrincipio(viewPort);
			}
			else
			{
				stack.addAlFinal(viewPort);
			}
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
		else if(type == ViewType.LOCATION_VIEW)
		{
			remove(viewPort);
			viewPort = new LocationView(this, location);
			viewPort.setSize(new Dimension(328, 441));
			viewPort.setPreferredSize(new Dimension(328, 441));
			viewPort.setMinimumSize(new Dimension(328, 441));
			viewPort.setMaximumSize(new Dimension(328, 441));
			viewPort.setBounds(0, 48, 328, 440);
			add(viewPort);
			viewPort.repaint();
			actionBar.setViewIcon(viewPort.getIcon());
			actionBar.setViewTitle(viewPort.getTitle());
			actionBar.setNavigationButton(false);
			actionBar.repaint();
			viewPort.verifyView();
		}
		else if(type == ViewType.LOCATION_SELECTION_VIEW)
		{
			remove(viewPort);
			viewPort = new LocationSelectionView(this);
			viewPort.setSize(new Dimension(328, 441));
			viewPort.setPreferredSize(new Dimension(328, 441));
			viewPort.setMinimumSize(new Dimension(328, 441));
			viewPort.setMaximumSize(new Dimension(328, 441));
			viewPort.setBounds(0, 48, 328, 440);
			add(viewPort);
			viewPort.repaint();
			actionBar.setViewIcon(viewPort.getIcon());
			actionBar.setViewTitle(viewPort.getTitle());
			actionBar.setNavigationButton(false);
			actionBar.repaint();
		}
		else if(type == ViewType.APPOINTMENTS_VIEW)
		{
			remove(viewPort);
			viewPort = new AppointmentsView(this, location, localTime);
			viewPort.setSize(new Dimension(328, 441));
			viewPort.setPreferredSize(new Dimension(328, 441));
			viewPort.setMinimumSize(new Dimension(328, 441));
			viewPort.setMaximumSize(new Dimension(328, 441));
			viewPort.setBounds(0, 48, 328, 440);
			add(viewPort);
			viewPort.repaint();
			actionBar.setViewIcon(viewPort.getIcon());
			actionBar.setViewTitle(viewPort.getTitle());
			actionBar.setNavigationButton(false);
			actionBar.repaint();
			viewPort.verifyView();
		}
		else if(type == ViewType.APPOINTMENT_LIST_VIEW)
		{
			remove(viewPort);
			LlamaArrayList<Cita> list = getAppointments();
			viewPort = new AppointmentListView(this, list);
			viewPort.setSize(new Dimension(328, 441));
			viewPort.setPreferredSize(new Dimension(328, 441));
			viewPort.setMinimumSize(new Dimension(328, 441));
			viewPort.setMaximumSize(new Dimension(328, 441));
			viewPort.setBounds(0, 48, 328, 440);
			add(viewPort);
			viewPort.repaint();
			actionBar.setViewIcon(viewPort.getIcon());
			actionBar.setViewTitle(viewPort.getTitle());
			actionBar.setNavigationButton(false);
			actionBar.repaint();
			//viewPort.verifyView();
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
		if(viewPort.getType() == viewType)
		{
			showDrawer();
		}
		else
		{
			showDrawer();
			stack.clear();
			updateView(viewType);
			
		}
	}
	

	public boolean queryUserName(String username) 
	{
		//Busca si un nombre de usuario existe
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

	public void changeLocation() 
	{
		//Llaves posibles:
		//   1. latitud y longitud, si no existen, entonces, 2.
		//   2. codigoPostal, si no existe, entonces, 3.
		//   3. ciudad, debe existir si es la última instancia
		LlamaDict<String, String> locInfo = viewPort.getViewFormInfo();
		String latitude = locInfo.getValue("latitud");
		String longitude = locInfo.getValue("longitud");
		if(latitude != null && longitude != null)
		{
			location = locInfo;
		}
		else
		{
			//TODO Query coordinates by ZIP and City
			location = locInfo; // → Las llaves con latitud y longitud
		}
		//TODO Change Location
        
		returnView();
		updateLocationMap();
		
	}

	private void updateLocationMap() 
	{
		//TODO Get Map
		Image im = null;
		viewPort.pushInfo(im);
		viewPort.repaint();
	}
	
	private LlamaArrayList<Cita> getAppointments() 
	{
		//TODO Get Appointments
		return new LlamaArrayList<Cita>(2);
	}

	@Override
	public void update(Observable o, Object arg) 
	{
		// TODO Auto-generated method stub
		
	}


	

}
