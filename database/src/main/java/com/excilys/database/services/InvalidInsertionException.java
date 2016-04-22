package com.excilys.database.services;

public class InvalidInsertionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidInsertionException() {
		super();
	}

	public InvalidInsertionException(String msg) {
		super(msg);
	}
}
