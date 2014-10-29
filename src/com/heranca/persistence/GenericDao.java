package com.heranca.persistence;

import java.util.List;
import java.util.Map;

public interface GenericDao<T, ID> {
	
	public void persist(T obj);
	
	public void remove(T obj) ;
	
	public T merge(T obj);
	
	public void refresh(T obj);
	
	public List<T> findAll();
	
	public T findById(ID id);
	
	public T findMaxId(ID id);
	
	public T findMinId(ID id);
	
}
