package com.excilys.database.persistence;

public class DAOException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DAOException() {
        super();
    }

    public DAOException(String msg) {
        super(msg);
    }

    public DAOException(Throwable t) {
        super(t);
    }
}
