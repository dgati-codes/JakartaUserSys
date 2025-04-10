package com.tech11.exception;

public class UserException extends RuntimeException {
	private static final long serialVersionUID = 3079423021923994258L;

	public UserException(String message) {
        super(message);
    }
}
