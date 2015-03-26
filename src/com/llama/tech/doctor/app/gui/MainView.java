package com.llama.tech.doctor.app.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.llama.tech.doctor.app.gui.AppView.ViewType;
import com.llama.tech.doctor.app.gui.components.Toast;
import com.llama.tech.doctor.maps.ConsultaMapas;
import com.llama.tech.doctor.mundo.Cita;
import com.llama.tech.doctor.mundo.Consultorio;
import com.llama.tech.doctor.mundo.LlamaCitas;
import com.llama.tech.doctor.mundo.Usuario;
import com.llama.tech.doctor.mundo.UsuarioException;
import com.llama.tech.utils.dict.LlamaDict;
import com.llama.tech.utils.dict.LlamaDict.UnhashableTypeException;
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
	private String location = null;
	private LlamaCitas mundo;
	private LocalDate localDate;
	private int zoom=12;
	private Consultorio consultorioSeleccionado;
	
	private int count = 0;
	
	private LlamaArrayList<Cita> appointmentList;
	
	private ClassLoader classLoader = this.getClass().getClassLoader();
	private String state;
	
	public MainView(LlamaCitas v)
	{
		mundo = v;
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
		localDate = mundo.darFechaActual();
		mundo.addObserver(this);
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
		else if(type == ViewType.LOGIN_VIEW)
		{
			remove(viewPort);
			stack.clear();
			viewPort = new LoginView(this);
			viewPort.setSize(new Dimension(328, 441));
			viewPort.setPreferredSize(new Dimension(328, 441));
			viewPort.setMinimumSize(new Dimension(328, 441));
			viewPort.setMaximumSize(new Dimension(328, 441));
			viewPort.setBounds(0, 48, 328, 440);
			add(viewPort);
			viewPort.repaint();
			if(actionBarInit)
			{
				remove(actionBar);
				actionBarInit = false;
				actionBar.repaint();
			}
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
			viewPort = new AppointmentsView(this, location, localDate);
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
		else if(type == ViewType.OFFICES_VIEW)
		{
			remove(viewPort);
			viewPort = new OfficePrincipal(this);
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
		else if(type == ViewType.HISTORY_VIEW)
		{
			remove(viewPort);
			viewPort = new LastLocations(this, null);
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
		else if(type == ViewType.ACCOUNT_VIEW)
		{
			remove(viewPort);
			viewPort = new AccountView(this);
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
		else if(type == ViewType.APPOINTMENTS_OFFICE_BYDATE)
		{
			remove(viewPort);
			viewPort = new OfficeAppointmentsByDate(this);
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
		else if(type == ViewType.APPOINTMENTS_DATE_SELECTION_VIEW)
		{
			remove(viewPort);
			viewPort = new AppointmentDateSelectionView(this);
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
		else if(type==ViewType.APPOINTMENT_CANCELATION)
		{
			remove(viewPort);
			viewPort = new DeleteAppointment(this);
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
		else if(type == ViewType.APPOINTMENT_LIST_VIEW)
		{
			remove(viewPort);
			viewPort = new AppointmentListView(this, appointmentList);
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
		else if(type == ViewType.OFFICE_EXPERIENCE)
		{
			remove(viewPort);
			viewPort = new OfficeByView(this, type, new LlamaArrayList<Consultorio>(1));
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
		else if(type == ViewType.OFFICE_NEAR)
		{
			remove(viewPort);
			viewPort = new OfficeByView(this, type, new LlamaArrayList<Consultorio>(1));
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
		else if(type == ViewType.OFFICE_REGION)
		{
			remove(viewPort);
			viewPort = new OfficeByView(this, type, new LlamaArrayList<Consultorio>(1));
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
		else if(type == ViewType.COUNTER)
		{
			remove(viewPort);
			viewPort = new GenericSelectionCounter(this, true);
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
		else if(type == ViewType.OFFICE_INFO)
		{
			remove(viewPort);
			viewPort = new InfoConsultorio(this, consultorioSeleccionado, localDate);
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
		else if(type == ViewType.MAKE_APPOINTMENT)
		{
			remove(viewPort);
			viewPort = new AgendarCita(this, consultorioSeleccionado);
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
			
		else if(type == ViewType.STATE_CHOOSER)
		{
				remove(viewPort);
				viewPort = new GenericSelectionCounter(this, false);
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
	
	public void makeToast(String msg)
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
  	  
  	  InputStream resource = classLoader.getResourceAsStream(AppView.FONT_PATH
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
		
	     Toast.makeText(parent, String.format("   %s   ", msg), font).display();
	}
	

	public boolean queryUserName(String username) 
	{
		//Busca si un nombre de usuario existe
		// TODO Query user name
		return mundo.existeUsuario(username);
	}

	public void registerUser() 
	{
		LlamaDict<String, String> userInfo = viewPort.getViewFormInfo();
		// TODO Register user
		loggedUser = userInfo.getValue("usuario");
		password = userInfo.getValue("password");
		String nombre = userInfo.getValue("nombre")+" "+userInfo.getValue("apellido");
		try {
			mundo.registrarUsuario(nombre, password, "", loggedUser);
		} catch (UnhashableTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		updateView(AppView.ViewType.MAIN_MENU_VIEW);
		
	}

	public boolean verifyUserInfo(String user, String password) 
	{
		return mundo.verificarUsuario(user, password);
	}

	public void login() 
	{
		LlamaDict<String, String> userInfo = viewPort.getViewFormInfo();
		loggedUser = userInfo.getValue("usuario");
		password = userInfo.getValue("password");
		try {
			mundo.iniciarSesion(loggedUser, password);
		} catch (UsuarioException e) {
			makeToast("La contraseña ingresada es incorrecta");
		}
		updateView(AppView.ViewType.MAIN_MENU_VIEW);
	}

	public void changeLocation() 
	{
		//Llaves posibles:
		//   1. latitud y longitud, si no existen, entonces, 2.
		//   2. codigoPostal, si no existe, entonces, 3.
		//   3. ciudad, debe existir si es la última instancia
		LlamaDict<String, String> locInfo = viewPort.getViewFormInfo();
		String info = locInfo.getValue("latitud");
		if(info!=null)
		{
			info = info+","+locInfo.getValue("longitud");
			mundo.cambiarUbicacionActual(info,LlamaCitas.LATLON);
		}
		else
		{
			info=locInfo.getValue("codigoPostal");
			if(info!=null)
			{
				mundo.cambiarUbicacionActual(info, LlamaCitas.POSTCODE);
			}
			else
			{
				info = locInfo.getValue("ciudad");
				System.out.println(info);
				mundo.cambiarUbicacionActual(info, LlamaCitas.CIUDAD);
			}
		}
		returnView();
		location = mundo.darUbicacionActual();
		viewPort.verifyView();
		//updateView(ViewType.LOCATION_VIEW);
		//TODO Change Location
		
		
	}
	
	public void searchAppointments(LocalDate inicial, LocalDate fin) 
	{
		appointmentList = mundo.darCitasUsuario(inicial, fin);
		if(appointmentList.size() == 0)
		{
			makeToast("No hay citas programadas en el rango de fechas.");
		}
		else
		{
			updateView(ViewType.APPOINTMENT_LIST_VIEW);
		}
		
	}
	
	public void eliminarCita(String nombreDoctor, LocalDate fecha)
	{
		String m = mundo.eliminarCita(nombreDoctor, fecha)?"La cita se eliminó con éxito":"No se encontró la cita para eliminar";
		makeToast(m);
	}
	
	public String[] darultimasLocations(int N)
	{
		count = 0;
		return mundo.darUbicacionesAnteriores(N);
	}

	@Override
	public void update(Observable o, Object arg) {
		int constante = (int) arg;
		if(constante == LlamaCitas.CAMBIO_UBACTUAL)
		{
//			location = mundo.darUbicacionActual();
//			System.out.println(location+" Obs");
//			updateView(AppView.ViewType.LOCATION_VIEW);
		}
		else if (constante == LlamaCitas.INICIO_SESION ||constante == LlamaCitas.REGISTRO)
		{
			//updateView(AppView.ViewType.MAIN_MENU_VIEW);
		}
		
		
	}

	public void setCount(int parseInt) 
	{
		count = parseInt;
		returnView();
		viewPort.verifyView();
	}

	public int getCount() 
	{
		return count;
	}

	public void logout() 
	{
		mundo.cerrarSesion();
		stack.clear();
		updateView(ViewType.LOGIN_VIEW);
	}

	public void setDate(LocalDate date) 
	{
		mundo.cambiarFecha(date);
		makeToast("Se ha cambiado la fecha del sistema");
	}
	
	public void cambiarConsultorioActual(Consultorio cons)
	{
		consultorioSeleccionado = cons;
	}
	
	public void agendarCita(LocalDateTime hora)
	{
		boolean a = mundo.agendarCita(hora, consultorioSeleccionado);
		if(a)
		{
			makeToast("La cita se agendo correctamente.");
		}
		else
		{
			makeToast("La cita no pudo ser agendada."+"\n"+" Intente con otro horario.");
		}
	
	}
	
	public void askForInfo(ViewType id) 
	{
		if(id == ViewType.OFFICE_NEAR || id == ViewType.OFFICE_EXPERIENCE)
		{
			updateView(ViewType.COUNTER);
		}
		else
		{
			updateView(ViewType.STATE_CHOOSER);
		}
	}

	public void setState(String state)
	{
		this.state = state;
		returnView();
		viewPort.verifyView();
	}

	public String getState() 
	{
		return state;
	}

	public LlamaArrayList<Consultorio> getOfficesByExperience() 
	{
		LlamaArrayList<Consultorio> cons = mundo.darConsultoriosPorExperiencia(count);
		count = 0;
		
		return cons;
	}
	
	public LlamaArrayList<Cita> darcitasConsultorioFecha(LocalDate fecha)
	{
		return consultorioSeleccionado.citasEnFecha(fecha);
	}

	public LlamaArrayList<Consultorio> getOfficesByLocation() 
	{
		LlamaArrayList<Consultorio> cons = new LlamaArrayList<Consultorio>(1);
		try {
			cons = mundo.darConsultoriosCercanos(count);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		count = 0;
		return cons;
	}

	public LlamaArrayList<Consultorio> getOfficesByRegion() 
	{
		LlamaArrayList<Consultorio> cons = mundo.darConsultoriosPorRegion(state);
		state = null;
		return cons;
	}
	
	public String getGeoLocation()
	{
		return location;
	}



	

}
