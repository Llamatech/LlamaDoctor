package com.llama.tech.doctor.mundo;

import com.llama.tech.utils.tree.LlamaAVLTree;

public class Usuario implements Comparable<Usuario>{
	
	private String nombreUsuario;
	private String clave;
	private String email;
	private String id;
	private String ubicacionActual;
	
	//También se puede hacer con un hash? Facilita borrar y buscar. 
	private LlamaAVLTree<Cita> citas;

	@Override
	public int compareTo(Usuario o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
