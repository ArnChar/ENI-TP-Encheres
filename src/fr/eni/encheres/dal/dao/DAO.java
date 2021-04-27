package fr.eni.encheres.dal.dao;

import java.util.List;

import fr.eni.encheres.dal.DALException;

public interface DAO<T> {
	
	public void insert(T o) throws DALException; 
	
	public void update(T o) throws DALException; 
	
	public void delete(T o) throws DALException; 
	
	public List<T> selectAll() throws DALException; 

}
