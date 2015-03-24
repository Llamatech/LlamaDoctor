package com.llama.tech.doctor.maps;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.llama.tech.doctor.mundo.Consultorio;
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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import uniandes.cupi2.cupIphone.componentes.IAplicacion;
import uniandes.cupi2.cupIphone.core.ICore;

public class PruebaMapas extends JPanel implements ActionListener, Observer{

	LlamaArrayList<String> visitados = new LlamaArrayList<String>(60);
	String[] anteriores;

	public final static int VISITADOS =1;
	public final static int CONSULTORIOS=2;
	public final static String PAIS="Pais";
	public final static String CIUDAD = "Ciudad";
	public final static String COORDENADAS="Coordenadas";
	public final static String POSTCODE = "Codigo Postal";

	private int zoomAnteriores=6;
	private JButton btnZoomIn;
	private JButton btnZoomOut;
	private JButton btnAgregarUbicacion;
	private JButton btnRefrescarLugaresRecientes;
	private JLabel lblMapa;
	private JLabel lblTitulo;
	private JButton btnMostrarConsultoriosCercanos;
	private int estadoActual;
	private String tipoBusqueda;
	private String ubicacionActual;

	/**
	 * Modelo
	 */
	private LlamaCitas mundo;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JLabel lbltotalconsultorios;
	private JLabel lblTiempocarga;



	public PruebaMapas(LlamaCitas v)
	{
//		setResizable(false);
		mundo = v;
//		setTitle("Ver location");
		setSize(new Dimension(380, 700));
//		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getRootPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(45dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(40dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:max(18dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,},
				new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(254dlu;default)"),
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

		lblTiempocarga = new JLabel("");
		getRootPane().add(lblTiempocarga, "2, 2, 5, 1");

		lbltotalconsultorios = new JLabel("");
		getRootPane().add(lbltotalconsultorios, "2, 4, 5, 1");

		lblTitulo = new JLabel("");
		getRootPane().add(lblTitulo, "2, 8, 5, 1, left, default");

		lblMapa = new JLabel("");
		getRootPane().add(lblMapa, "2, 10, 5, 1");

		btnZoomOut = new JButton("Zoom Out");
		btnZoomOut.addActionListener(this);

		scrollPane = new JScrollPane();
		getRootPane().add(scrollPane, "2, 12, 5, 3, fill, fill");

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		btnZoomOut.setActionCommand("OUT");
		getRootPane().add(btnZoomOut, "2, 16");

		btnZoomIn = new JButton("Zoom In");
		btnZoomIn.addActionListener(this);

		btnRefrescarLugaresRecientes = new JButton("Lugares Recientes");
		btnRefrescarLugaresRecientes.addActionListener(this);
		btnRefrescarLugaresRecientes.setActionCommand("REFRESCAR");
		getRootPane().add(btnRefrescarLugaresRecientes, "4, 16");
		btnZoomIn.setActionCommand("IN");
		getRootPane().add(btnZoomIn, "2, 18");

		btnAgregarUbicacion = new JButton("Agregar Ubicacion");
		btnAgregarUbicacion.setActionCommand("AGREGAR");
		btnAgregarUbicacion.addActionListener(this);
		getRootPane().add(btnAgregarUbicacion, "4, 18");

		btnMostrarConsultoriosCercanos = new JButton("Consultorios Cercanos");
		btnMostrarConsultoriosCercanos.setActionCommand("CERCA");
		btnMostrarConsultoriosCercanos.addActionListener(this);
		getRootPane().add(btnMostrarConsultoriosCercanos, "4, 20");

		mundo.addObserver(this);

		lblTiempocarga.setText("El tiempo de carga fue de: "+ mundo.getTiempoCarga()+ " milisegundos");
		lbltotalconsultorios.setText("El total de consultorios es de: "+ mundo.getTotalConsultorios());

	}

	public void mostrarCercanos() throws MalformedURLException, IOException
	{
		estadoActual = CONSULTORIOS;
		lblTitulo.setText("Consultorios Cercanos");


		//		textArea.setText(infis);

		lblMapa.setIcon(ConsultaMapas.darMapaVisitados(anteriores, zoomAnteriores));

	}

	public void mostrarMapaLocation() throws MalformedURLException, IOException
	{
		DialogoEscoger de = new DialogoEscoger();
		de.setVisible(true);
		//info="New York, NY";

	}

	public void mostrarAnteriores() throws MalformedURLException, IOException
	{
		int N = Integer.parseInt(JOptionPane.showInputDialog("Introduzca el número máximo de ubicaciones a visualizar"));
		estadoActual=VISITADOS;
		lblTitulo.setText("Lugares recientemente agregados");
		String[] info = new String[visitados.size()];
		String infis = "";
		char marcador = 'A';

		for(int i =0;i<visitados.size()&&i<N;i++)
		{
			info[i]=visitados.get(i);
			infis+=marcador+": "+visitados.get(i)+"\n";
			marcador++;
		}

		textArea.setText(infis);

		lblMapa.setIcon(ConsultaMapas.darMapaVisitadosRestringido(info, zoomAnteriores,N));
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
		{
			String[] info = new String[visitados.size()];

			for(int i =0;i<visitados.size();i++)
			{
				info[i]=visitados.get(i);

			}
			lblMapa.setIcon(ConsultaMapas.darMapaVisitados(info, zoomAnteriores));
		}
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
		{
			String[] info = new String[visitados.size()];

			for(int i =0;i<visitados.size();i++)
			{
				info[i]=visitados.get(i);

			}
			lblMapa.setIcon(ConsultaMapas.darMapaVisitados(info, zoomAnteriores));
		}
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
			String dis = JOptionPane.showInputDialog("Diga la distancia máxima en kilómetros");

			double distancia = Double.parseDouble(dis);

			try {
				mundo.darConsultoriosCercanos(ubicacionActual, tipoBusqueda, distancia);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


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

//	public static void main(String[] args) {
//		PruebaMapas pm = new PruebaMapas();
//		pm.setVisible(true);
//	}

	@Override
	public void update(Observable o, Object inf) {
		LlamaArrayList<Consultorio> infis = (LlamaArrayList<Consultorio>) inf;
		String[] info = new String[infis.size()];
		String consultorios = "";
		int i =0;
		char marcador = 'A';
		for(Consultorio c:infis)
		{
			info[i]=c.getLatitud()+","+c.getLongitud();
			consultorios+=marcador+": "+c.getNombre()+"\n";
			i++;
			marcador++;
		}
		anteriores = info;
		textArea.setText(consultorios);
		try {
			mostrarCercanos();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public class DialogoEscoger extends JDialog implements ActionListener
	{
		private JComboBox<String> escogedor;
		private JLabel mensaje;
		private JButton btnAceptar;

		public DialogoEscoger()
		{
			String[] opciones = new String[]{COORDENADAS,CIUDAD,POSTCODE};

			setSize(new Dimension(300,200));
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			escogedor= new JComboBox<String>(opciones);
			mensaje = new JLabel("Escoja el criterio de busqueda");
			btnAceptar = new JButton("Aceptar");
			btnAceptar.addActionListener(this);
			btnAceptar.setActionCommand("A");

			setLayout(new BorderLayout());
			add(mensaje,BorderLayout.NORTH);
			add(escogedor,BorderLayout.CENTER);
			add(btnAceptar, BorderLayout.SOUTH);


		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getActionCommand().equals("A"))
			{
				tipoBusqueda = (String) escogedor.getSelectedItem();
				String info=JOptionPane.showInputDialog("Introduzca el criterio de búsqueda");
				ubicacionActual = info;

				visitados.addAlPrincipio(ubicacionActual);

				DialogoMapaUnico jd;
				try {
					jd = new DialogoMapaUnico(ubicacionActual);
					jd.setVisible(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}



				dispose();
			}

		}

	}


}
