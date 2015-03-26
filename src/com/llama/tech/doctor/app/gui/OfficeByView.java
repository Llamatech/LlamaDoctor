package com.llama.tech.doctor.app.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.llama.tech.doctor.app.gui.AppView.ViewType;
import com.llama.tech.doctor.app.gui.components.LlamaButton;
import com.llama.tech.doctor.app.gui.components.LlamaMapComponent;
import com.llama.tech.doctor.maps.ConsultaMapas;
import com.llama.tech.doctor.mundo.Consultorio;
import com.llama.tech.utils.list.LlamaArrayList;

import javax.swing.Icon;
import javax.swing.JList;

public class OfficeByView extends AppView implements ActionListener
{
	private LlamaMapComponent mapPanel;
	private JButton btnRegresar;
	private final static String BACK = "Back";
	private final static String INFO = "Info";
	private JList<Consultorio> list;
	private LlamaArrayList<Consultorio> consultorios;
	private int zoom = 12;
	private String[] locations;
	private DefaultListModel<Consultorio> model = new DefaultListModel<Consultorio>();
	
	public OfficeByView(MainView main, ViewType type, LlamaArrayList<Consultorio> consultoriosS)
	{
		mainView = main;
		viewTitle = "Ubicación";
		id = type;
		
		consultorios = consultoriosS;

		URL iconURI = classLoader.getResource(IMG_PATH+"ic_action_group.png");
	    viewIcon = new ImageIcon(iconURI); 
	    
	    setSize(new Dimension(WIDTH, HEIGHT));
	    setLayout(null);
		setBackground(backgroundColor);
		
		InputStream resource = classLoader.getResourceAsStream(FONT_PATH
				+ "Roboto-Regular.ttf");
		Font font = null;
		try 
		{
			font = Font.createFont(Font.TRUETYPE_FONT, resource);
			font = font.deriveFont(13F);
		} 
		catch (IOException | FontFormatException e) 
		{
			e.printStackTrace();
		}
		
		mapPanel = new LlamaMapComponent(this);
		mapPanel.setBounds(29, 12, 270, 213);
		add(mapPanel);
		
		
		URL icon = classLoader.getResource(IMG_PATH+"ic_action_forward.png");
		ImageIcon ic = new ImageIcon(icon);
		
		icon = classLoader.getResource(IMG_PATH+"ic_action_back.png");
		ic = new ImageIcon(icon);
		
		btnRegresar = new LlamaButton("Regresar", ic);
		btnRegresar.setBounds(29, 325, 270, 25);
		btnRegresar.addActionListener(this);
		btnRegresar.setActionCommand(BACK);
		add(btnRegresar);
		
		LlamaButton llamaButton = new LlamaButton("Ver Info", (Icon) null);
		llamaButton.setActionCommand(INFO);
		llamaButton.addActionListener(this);
		llamaButton.setBounds(29, 361, 270, 25);
		add(llamaButton);

		
		list = new JList<Consultorio>();
		list.setModel(model);
		list.setBackground(backgroundColor);
		list.setBounds(29, 237, 270, 93);
		add(list);
	}
	
	@Override
	public void verifyView()
	{
		boolean cond = false;
	    if(id == ViewType.OFFICE_EXPERIENCE || id == ViewType.OFFICE_NEAR)
	    {
	    	cond = mainView.getCount() == 0;
	    }
	    else
	    {
	    	cond = mainView.getState() == null;
	    }
		
		if(cond)
		{
			mainView.askForInfo(id);
		}
		else
		{
			//String[] locations = null;
			if(id == ViewType.OFFICE_EXPERIENCE)
			{
				consultorios = mainView.getOfficesByExperience();
			}
			else if(id == ViewType.OFFICE_NEAR)
			{
				consultorios = mainView.getOfficesByLocation();
			}
			else if(id == ViewType.OFFICE_REGION)
			{
				consultorios = mainView.getOfficesByRegion();
			}
				
			locations = new String[consultorios.size()];
			
			String loc = "%f,%f";
			int i = 0;
			for(Consultorio c: consultorios)
			{
				model.addElement(c);
				if(c.getLatitud() == 0 && c.getLongitud() == 0)
				{
					locations[i] = c.getLocalidad();
				}
				else
				{
					locations[i] = String.format(loc, c.getLatitud(), c.getLongitud());
				}
				i++;
			}
			
			paint_map();
			repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals(BACK))
		{
			mainView.returnView();
		}
		else if(e.getActionCommand().equals(INFO))
		{
			Consultorio seleccionado = list.getSelectedValue();
			if(seleccionado != null)
			{
				mainView.cambiarConsultorioActual(seleccionado);
				mainView.updateView(ViewType.OFFICE_INFO);
			}
			else
			{
				mainView.makeToast("Debe seleccionar un consultorio.");
			}
			//mainView.updateView(ViewType.LOCATION_SELECTION_VIEW);
		}
        else if(e.getActionCommand().equals(LlamaMapComponent.ZOOM_IN))
		{
        	System.out.println("Zoom In");
			zoom_in();
		}
		else if(e.getActionCommand().equals(LlamaMapComponent.ZOOM_OUT))
		{
			System.out.println("Zoom Out");
			zoom_out();
		}
		
	}


	private void zoom_out() 
	{
		zoom -= 1;
		paint_map();
		repaint();
	}


	private void paint_map() 
	{
		Image map = null;
		try 
		{
			map = ConsultaMapas.darMapaVisitados(locations, zoom).getImage();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mapPanel.setMap(map);
		mapPanel.repaint();
		
	}

	private void zoom_in() 
	{
		zoom += 1;
		paint_map();
	}
}
