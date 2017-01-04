package com.dlgdev.utils.db.exceptions;

public class MalformedSqlException extends RuntimeException {

	public MalformedSqlException(String s) {
		super(s);
	}

	public MalformedSqlException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public MalformedSqlException(Throwable throwable) {
		super(throwable);
	}

	public MalformedSqlException(String s, Throwable throwable, boolean b, boolean b1) {
		super(s, throwable, b, b1);
	}
}
