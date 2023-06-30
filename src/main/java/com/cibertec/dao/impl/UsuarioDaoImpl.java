package com.cibertec.dao.impl;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.cibertec.dao.UsuarioDao;
import com.cibertec.model.Usuario;

public class UsuarioDaoImpl implements UsuarioDao {
	
	private static final String REGISTRO_OK = "Se registró correctamente al ususario";
	private static final String REGISTRO_ERROR = "No se registró correctamente al ususario";
	
	private static final String ACTUALIZA_OK = "Se actualizó correctamente al ususario";
	private static final String ACTUALIZA_ERROR = "No se actualizó correctamente al ususario";
	
	private static final String ELIMINA_OK = "Se eliminó correctamente al ususario";
	private static final String ELIMINA_ERROR = "No se eliminó correctamente al ususario";
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("UsuarioPU");
	EntityManager manager = factory.createEntityManager();

	public String registrarUsuario(Usuario usuario) {
		String mensaje = REGISTRO_ERROR;		
		if(!Objects.isNull(usuario)) {
			manager.getTransaction().begin();
			manager.persist(usuario);
			manager.getTransaction().commit();
			mensaje = REGISTRO_OK;
			manager.close();
		}
		return mensaje;
	}
	
	private Usuario buscarUsuarioId(int id) {
		return manager.find(Usuario.class,id);
	}

	public Usuario buscarUsuario(int id) {
		return buscarUsuarioId(id);
	}

	public List<Usuario> listarUsuarios() {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
		Root<Usuario> registro = criteriaQuery.from(Usuario.class);
		CriteriaQuery<Usuario> todos = criteriaQuery.select(registro);
		TypedQuery<Usuario> lista = manager.createQuery(todos);
		return lista.getResultList();
	}

	public String actualizarUsuario(Usuario usuario) {
		String mensaje = ACTUALIZA_ERROR;
		Usuario objeto = buscarUsuarioId(usuario.getId());
		if(!Objects.isNull(objeto)) {
			manager.getTransaction().begin();
			objeto.setNombre(usuario.getNombre());
			objeto.setClave(usuario.getClave());
			objeto.setEstado(usuario.getEstado());
			manager.getTransaction().commit();
			manager.close();
			mensaje = ACTUALIZA_OK;
		}
		return null;
	}

	public String eliminarUsuario(int id) {
		String mensaje = ELIMINA_ERROR;
		Usuario objeto = buscarUsuarioId(id);
		if(!Objects.isNull(objeto)) {
			manager.getTransaction().begin();
			manager.remove(objeto);
			manager.getTransaction().commit();
			manager.close();
			mensaje = ELIMINA_OK;
		}
		return null;
	}

}
