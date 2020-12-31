package com.poc.demo.domain.exception;

import java.util.function.Predicate;


public class FrutaInvalidException extends RuntimeException implements Predicate<Throwable> {

	private static final long serialVersionUID = 1L;

	public FrutaInvalidException(String message) {
		super(message);
	}

	@Override
	public boolean test(Throwable throwable) {
		return !(throwable instanceof FrutaInvalidException);
	}
	
	

}
