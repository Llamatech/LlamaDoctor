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

	LlamaArrayList<String> visitados;
	String[] anteriores;

	public final static int VISITADOS =1;
	public final static int CONSULTORIOS=2;
	public final static String PAIS="Pais";
	public final static String CIUDAD = "Ciudad";
	public final static String COORDENADAS="Coordenadas";
	public final static String POSTCODE = "Codigo Postal";

	private int zoomAnteriores=6;

	private int estadoActual;
	private String tipoBusqueda;
	private String ubicacionActual;

	JLabel lblTiempocarga;
	JLabel lbltotalconsultorios ;
	JLabel lblTitulo ;
	JLabel lblMapa;
	JScrollPane scrollPane ;
	JTextArea textArea ;
	JButton btnZoomIn ;
	JButton btnRefrescarLugaresRecientes ;
	JButton btnZoomOut;
	JButton btnAgregarUbicacion ;
	JButton btnMostrarConsultoriosCercanos ;

	/**
	 * Modelo
	 */
	private LlamaCitas mundo;



	public PruebaMapas(LlamaCitas v)
	{
		//		setResizable(false);
		mundo = v;
		//		mundo = new LlamaCitas();
		visitados = new LlamaArrayList<String>(60);
		//		setTitle("Ver location");
		setSize(new Dimension(350, 500));
		//		setDefaultCloseOperation(DISPOSE_ON_CLOSE);


		mundo.addObserver(this);
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
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
				RowSpec.decode("max(14dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		lblTiempocarga = new JLabel("");
		add(lblTiempocarga, "2, 2, 19, 1");

		lbltotalconsultorios = new JLabel("");
		add(lbltotalconsultorios, "2, 4, 19, 1");


		lblTitulo = new JLabel("");
		add(lblTitulo, "2, 6, 19, 1");

		 lblMapa = new JLabel("");
		add(lblMapa, "2, 7, 19, 20");

		 scrollPane = new JScrollPane();
		add(scrollPane, "2, 28, 19, 3, fill, fill");

		 textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		 btnZoomIn = new JButton("Zoom In");
		add(btnZoomIn, "2, 32, 5, 1");

		 btnRefrescarLugaresRecientes = new JButton("Lugares Recientes");
		add(btnRefrescarLugaresRecientes, "8, 32, 13, 1");

		 btnZoomOut = new JButton("Zoom Out");
		add(btnZoomOut, "2, 34, 5, 1");

		 btnAgregarUbicacion = new JButton("Actualizar Ubicacion\n");
		add(btnAgregarUbicacion, "8, 34, 13, 1");

		 btnMostrarConsultoriosCercanos = new JButton("Consultorios Cercanos");
		add(btnMostrarConsultoriosCercanos, "8, 36, 13, 1");

		lblTiempocarga = new JLabel("");


		lbltotalconsultorios = new JLabel("");


		lblTitulo = new JLabel("");



		btnZoomOut = new JButton("Zoom Out");
		btnZoomOut.addActionListener(this);

		scrollPane = new JScrollPane();

		textArea = new JTextArea();
		textArea.setEditable(false);
		btnZoomOut.setActionCommand("OUT");

		btnZoomIn = new JButton("Zoom In");
		btnZoomIn.addActionListener(this);

		btnRefrescarLugaresRecientes.addActionListener(this);
		btnRefrescarLugaresRecientes.setActionCommand("REFRESCAR");

		btnZoomIn.setActionCommand("IN");


		btnAgregarUbicacion.setActionCommand("AGREGAR");
		btnAgregarUbicacion.addActionListener(this);


		btnMostrarConsultoriosCercanos.setActionCommand("CERCA");
		btnMostrarConsultoriosCercanos.addActionListener(this);



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
		String[] info = new String[Math.min(visitados.size(),N)];
		String infis = "";
		char marcador = 'A';
		int p=0;

		for(int i =visitados.size()-1;i>=0&&p<N;i--)
		{
			info[p]=visitados.get(i);
			infis+=marcador+": "+visitados.get(i)+"\n";
			marcador++;
			p++;
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
		System.out.println("llegue");
		if(e.getActionCommand().equals("AGREGAR"))
		{
			System.out.println("agregarrr");
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

	public static void main(String[] args) {
		PruebaMapas pm = new PruebaMapas(new LlamaCitas());
		pm.setVisible(true);
	}

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

	public class DialogoEscoger extends JInternalFrame implements ActionListener
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

				visitados.addAlFinal(ubicacionActual);

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
