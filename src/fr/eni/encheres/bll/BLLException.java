package fr.eni.encheres.bll;

public class BLLException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String userMessage = null;

	
	//Constructeurs
	public BLLException() {
		super();
	}
	
	public BLLException(String message) {
		super(message);
	}
	
	public BLLException(String message, Throwable exception) {
		super(message, exception);
	}
	
	public BLLException(String message, String userMessage) {
		super(message);
		setUserMessage(userMessage);
	}
	
	public BLLException(String message, String userMessage, Throwable exception) {
		super(message, exception);
		setUserMessage(userMessage);
	}
	
	//Méthodes
	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer("Couche BLL - ");
		sb.append(super.getMessage());
		
		return sb.toString() ;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

}