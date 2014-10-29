package com.heranca.persistence;
 
import java.util.HashMap;
import java.util.Map;

import com.heranca.model.User;
 
public class UserDAO extends GenericAbstractDao<User, Integer> {
 
	private static final long serialVersionUID = 1L;
	
	public UserDAO() {
		super();
	}
	
	public User authenticate(String login, String password){
       Map<String, Object> parameters = new HashMap<String, Object>();
       parameters.put("login", login);     
       parameters.put("password", password);     
 
       return super.findOneResult(User.QUERY_AUTHENTICATE, parameters);
    }
}