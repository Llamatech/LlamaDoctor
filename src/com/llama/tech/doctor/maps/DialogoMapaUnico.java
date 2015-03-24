package com.llama.tech.doctor.maps;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DialogoMapaUnico extends JInternalFrame implements ActionListener {

	private JButton btnZoomIn;
	private JButton btnZoomOut;
	private JLabel lblMapa;
	private int zoomUnico =6;
	private String info;
	
	public DialogoMapaUnico( String info) throws MalformedURLException, IOException
	{
		this.info=info;
		setLayout(new BorderLayout());
		setSize(new Dimension(350,400));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		lblMapa = new JLabel();
		mostrarMapaLocation();
		add(lblMapa);
		JPanel aux = new JPanel();
		aux.setLayout(new GridLayout(1,2));
		btnZoomIn = new JButton("Zoom In");
		btnZoomIn.addActionListener(this);
		btnZoomIn.setActionCommand("IN");
		aux.add(btnZoomIn);
		btnZoomOut = new JButton("Zoom Out");
		btnZoomOut.addActionListener(this);
		btnZoomOut.setActionCommand("OUT");
		aux.add(btnZoomOut);
		add(aux,BorderLayout.SOUTH);
		
	}
	
	public void mostrarMapaLocation() throws MalformedURLException, IOException
	{

		lblMapa.setIcon(ConsultaMapas.darMapaConsultorio(info, zoomUnico));
	}
	
	public void disminuirZoomUnico() throws MalformedURLException, IOException
	{
		zoomUnico--;
		if(zoomUnico<0)
		{
			zoomUnico++;
			JOptionPane.showMessageDialog(this, "No puede disminuir más el zoom");
		}
		lblMapa.setIcon(ConsultaMapas.darMapaConsultorio(info, zoomUnico));
	}

	public void aumentarZoomUnico() throws MalformedURLException, IOException
	{
		zoomUnico++;
		if(zoomUnico>21)
		{
			zoomUnico--;
			JOptionPane.showMessageDialog(this, "No puede aumentar más el zoom");
		}
		lblMapa.setIcon(ConsultaMapas.darMapaConsultorio(info, zoomUnico));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("IN"))
		{
			try {
				aumentarZoomUnico();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getActionCommand().equals("OUT"))
		{
			try {
				disminuirZoomUnico();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
