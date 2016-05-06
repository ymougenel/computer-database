package com.excilys.database.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalTransactionThread {

    private static Logger logger = LoggerFactory.getLogger("LocalTransactionThread");
    public static final ThreadLocal<Connection> userThreadLocal = new ThreadLocal<Connection>();

    public static void init() {
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            userThreadLocal.set(con);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }
    //    public static void set(Connection con) {
    //        userThreadLocal.set(con);
    //    }

    public static void commit() {
        Connection con = userThreadLocal.get();
        try {
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
                logger.error(e.getMessage());
                e.printStackTrace();
                throw new DAOException(e+"Rollback executed");
            } catch (SQLException e1) {
                logger.error(e1.getMessage()+"Roolback cancelled");
                e1.printStackTrace();
                throw new DAOException(e1.getMessage()+"Roolback cancelled");
            }
        }
    }

    public static void close() {
        Connection con = userThreadLocal.get();
        try {
            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new DAOException(e);
        }
        finally {
            userThreadLocal.remove();
        }

    }

    public static Connection get() {
        return userThreadLocal.get();
    }
}
