package fr.eni.encheres.hmi;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public class ViewModel extends HashMap<String,String> {

	private static final long serialVersionUID = 1L;

	public ViewModel() {
		super();
	}
	
	public void getFromRequest(HttpServletRequest request, String[] keys) {
		this.clear();
		for(String key : keys) {
			this.put(key, request.getParameter(key));
		}
	}
	
	public void putIntoRequest(HttpServletRequest request) {
		for(String key : this.keySet()) {
			request.setAttribute(key, this.get(key));
		}
	}
	
	public String getString(String key) {
		String value = this.get(key);
		return value==null || value.isEmpty() ? null : value;
	}

	public Integer getInteger(String key) {
		String value = this.getString(key);
		return value!=null ? Integer.parseInt(value) : null;
	}

	public void putInteger(String key, Integer value) {
		super.put(key, value!=null ? String.valueOf(value) : null);
	}


	@Override
	public String get(Object key) {
		return super.get(key)!=null ? super.get(key).trim() : null;
	}

}
