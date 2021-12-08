package com.conference.dao.db;

import com.conference.dao.exceptions.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    private static final String URL = "jdbc:mysql://localhost:3306/conference";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static DBManager db;

    public static DBManager getInstance(){
        if(db == null){
            db = new DBManager();
        }
        return db;
    }
    public Connection getConnection() throws DAOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException err) {
            throw new DAOException("Cannot make connection", err);
        }
    }

}
