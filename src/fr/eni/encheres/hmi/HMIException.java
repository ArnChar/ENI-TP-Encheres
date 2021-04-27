package fr.eni.encheres.hmi;

public class HMIException extends Exception {

	private static final long serialVersionUID = 1L;

	//Constructeurs
	public HMIException() {
		super();
	}
	
	public HMIException(String message) {
		super(message);
	}
	
	public HMIException(String message, Throwable exception) {
		super(message, exception);
	}
	
	//Méthodes
	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer("Couche IHM - ");
		sb.append(super.getMessage());
		
		return sb.toString() ;
	}

}