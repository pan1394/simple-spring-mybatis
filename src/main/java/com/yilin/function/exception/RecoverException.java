package com.yilin.function.exception;

public class RecoverException extends RuntimeException{
 
	private static final long serialVersionUID = 2311L;

	public RecoverException(final String message) {
	        super(message);
	    }
 
	    public RecoverException(final Throwable cause) {
	        super(cause);
	    }
 
	    public RecoverException(final String message, final Throwable cause) {
	        super(message, cause);
	    }
	    
}
