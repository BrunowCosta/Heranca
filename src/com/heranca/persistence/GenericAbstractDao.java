package com.heranca.persistence;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;

public abstract class GenericAbstractDao<T, ID extends Serializable> implements GenericDao<T, ID> {

	@PersistenceContext(unitName="heranca")
	protected EntityManager manager;
	
	private Session session;
	
	private Class<T> entityClass;
	
	@SuppressWarnings("unchecked")
	public GenericAbstractDao() {
		// Seta o tipo da subclasse.
		this.setEntityClass((Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}

	@Override
	public void persist(T obj) {
		getManager().persist(obj);;
		getManager().flush();
	}

	@Override
	public void remove(T obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T merge(T obj) {
		obj = manager.merge(obj);
		return obj;
	}

	@Override
	public void refresh(T obj) {
		manager.refresh(obj);
	}

	@Override
	public T findById(ID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T findMaxId(ID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T findMinId(ID id) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	public T findByFilter(Map<String, String> fields) {
//		CriteriaQuery cq = manager.getCriteriaBuilder().createQuery();
//		cq.select(cq.from(entityClass));
//		cq.where(arg0)
//		return em.createQuery(cq).getResultList();
//		
//		return T;
//	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		CriteriaQuery cq = manager.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return manager.createQuery(cq).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
		T result = null;

		try {
			Query query = manager.createNamedQuery(namedQuery);

			// Method that will populate parameters if they are passed not null and empty
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}

			result = (T) query.getSingleResult();

		} catch (NoResultException e) {
			// TODO trocar isso aqui para log
			System.out.println("No result found for named query: " + namedQuery);
		} catch (Exception e) {
			// TODO trocar isso aqui para log
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}
	
//	@SuppressWarnings("unchecked")
//	public T findOneByField(String field, String condition, Object value) {
//		CriteriaQuery cq = manager.getCriteriaBuilder().createQuery(entityClass);
//		Metamodel m = manager.getMetamodel();
//		EntityType<T> t_ = m.entity(entityClass);
//		Root<T> root_ = cq.from(entityClass);
//		
//		cq.where(root_.get(t_.getAttributefield(field)    );
//		
//		cq.where(pet.get(Pet_.color).in("brown", "black");
//		
//		return manager.createQuery(cq).getSingleResult();
//	}

	private void populateQueryParameters(Query query, Map<String, Object> parameters) {
		for (Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}
	
	public T findOneByField(String campo, String condicao, String valor) 	{
		T result = null;
		
		try {
			Query query = manager.createQuery("select x from " + entityClass.getSimpleName() + " x " + "where x." + campo + " " + condicao + ":value");
			query.setParameter(campo, valor);
			result = (T) query.getSingleResult();
		} catch (NoResultException e) {
			// TODO trocar isso aqui para log
			System.out.println("No result found");
		} catch (Exception e) {
			// TODO trocar isso aqui para log
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}
	
	protected EntityManager getManager() {
		return manager;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	private Class<T> getEntityClass() {
		return entityClass;
	}

	private void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

}
