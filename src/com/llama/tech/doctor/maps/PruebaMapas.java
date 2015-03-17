package com.llama.tech.doctor.maps;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.llama.tech.doctor.mundo.LlamaCitas;
import com.llama.tech.utils.list.LlamaArrayList;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Observable;
import java.util.Observer;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JButton;
import javax.swing.JTextArea;

public class PruebaMapas extends JFrame implements ActionListener, Observer{

	LlamaArrayList<String> visitados = new LlamaArrayList<String>(60);
	String[] anteriores;

	public final static int VISITADOS =1;
	public final static int CONSULTORIOS=2;

	private int zoomAnteriores=6;
	private JButton btnZoomIn;
	private JButton btnZoomOut;
	private JButton btnAgregarUbicacion;
	private JButton btnRefrescarLugaresRecientes;
	private JLabel lblMapa;
	private JLabel lblTitulo;
	private JTextArea textArea;
	private JButton btnMostrarConsultoriosCercanos;
	private int estadoActual;
	/**
	 * Modelo
	 */
	private LlamaCitas mundo;



	public PruebaMapas()
	{
		mundo = new LlamaCitas();
		setTitle("Ver location");
		setSize(new Dimension(800, 500));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(61dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(21dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(215dlu;default):grow"),},
				new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		btnRefrescarLugaresRecientes = new JButton("Refrescar Lugares Recientes");
		btnRefrescarLugaresRecientes.addActionListener(this);

		lblTitulo = new JLabel("");
		getContentPane().add(lblTitulo, "2, 6, left, default");

		textArea = new JTextArea();
		getContentPane().add(textArea, "2, 8, 3, 1, fill, fill");

		lblMapa = new JLabel("");
		getContentPane().add(lblMapa, "10, 8");

		btnZoomOut = new JButton("Zoom Out");
		btnZoomOut.addActionListener(this);
		btnZoomOut.setActionCommand("OUT");
		getContentPane().add(btnZoomOut, "2, 10");
		btnRefrescarLugaresRecientes.setActionCommand("REFRESCAR");
		getContentPane().add(btnRefrescarLugaresRecientes, "10, 10");

		btnAgregarUbicacion = new JButton("Agregar Ubicacion");
		btnAgregarUbicacion.setActionCommand("AGREGAR");
		btnAgregarUbicacion.addActionListener(this);

		btnZoomIn = new JButton("Zoom In");
		btnZoomIn.addActionListener(this);
		btnZoomIn.setActionCommand("IN");
		getContentPane().add(btnZoomIn, "2, 12");
		getContentPane().add(btnAgregarUbicacion, "10, 12");

		btnMostrarConsultoriosCercanos = new JButton("Mostrar Consultorios Cercanos");
		btnMostrarConsultoriosCercanos.setActionCommand("CERCA");
		btnMostrarConsultoriosCercanos.addActionListener(this);
		getContentPane().add(btnMostrarConsultoriosCercanos, "10, 14");
		
		mundo.addObserver(this);


	}

	public void mostrarCercanos() throws MalformedURLException, IOException
	{
		estadoActual = CONSULTORIOS;
		lblTitulo.setText("Consultorios Cercanos");
		String infis = "";
		char marcador = 'A';

		for(int i =0;i<visitados.size();i++)
		{
			infis+=marcador+": "+anteriores[i]+"\n";
			marcador++;
		}

		textArea.setText(infis);

		lblMapa.setIcon(ConsultaMapas.darMapaVisitados(anteriores, zoomAnteriores));

	}

	public void mostrarMapaLocation() throws MalformedURLException, IOException
	{
		String info = JOptionPane.showInputDialog(this, "Introduzca el criterio de búsqueda");
		//info="New York, NY";
		visitados.addAlFinal(info);

		DialogoMapaUnico jd = new DialogoMapaUnico(info);
		jd.setVisible(true);
	}

	public void mostrarAnteriores() throws MalformedURLException, IOException
	{
		estadoActual=VISITADOS;
		lblTitulo.setText("Lugares recientemente agregados");
		String[] info = new String[visitados.size()];
		String infis = "";
		char marcador = 'A';

		for(int i =0;i<visitados.size();i++)
		{
			info[i]=visitados.get(i);
			infis+=marcador+": "+visitados.get(i)+"\n";
			marcador++;
		}

		textArea.setText(infis);

		lblMapa.setIcon(ConsultaMapas.darMapaVisitados(info, zoomAnteriores));
	}



	public void disminuirZoomTodos() throws MalformedURLException, IOException
	{
		zoomAnteriores--;
		if(zoomAnteriores<0)
		{
			zoomAnteriores++;
			JOptionPane.showMessageDialog(this, "No puede disminuir más el zoom");
		}
		if(estadoActual == VISITADOS)
			mostrarAnteriores();
		else
			mostrarCercanos();
	}

	public void aumentarZoomTodos() throws MalformedURLException, IOException
	{
		zoomAnteriores++;
		if(zoomAnteriores>21)
		{
			zoomAnteriores--;
			JOptionPane.showMessageDialog(this, "No puede aumentar más el zoom");
		}
		if(estadoActual == VISITADOS)
			mostrarAnteriores();
		else
			mostrarCercanos();
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("AGREGAR"))
		{
			try {
				mostrarMapaLocation();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getActionCommand().equals("REFRESCAR"))
		{
			try {
				mostrarAnteriores();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getActionCommand().equals("CERCA"))
		{
			String latlon=JOptionPane.showInputDialog("Diga su posición");
			String dis = JOptionPane.showInputDialog("Diga la distancia máxima");
			double distancia = Double.parseDouble(dis);
			double latitud = Double.parseDouble(latlon.split(",")[0]);
			double longitud =  Double.parseDouble(latlon.split(",")[1]);
			mundo.darConsultoriosCercanos(latitud, longitud, distancia);
		}
		else if(e.getActionCommand().equals("IN"))
		{
			try {
				aumentarZoomTodos();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getActionCommand().equals("OUT"))
		{
			try {
				disminuirZoomTodos();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		PruebaMapas pm = new PruebaMapas();
		pm.setVisible(true);
	}

	@Override
	public void update(Observable o, Object inf) {
		String [] info = (String[]) inf;
		anteriores = info;
		try {
			mostrarCercanos();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
