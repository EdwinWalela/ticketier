package com.company;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Query {
    Connection conn;
    Statement stmt = null;

    public Query(Connection newConn){
        conn = newConn;
    }

    public boolean createUser(User _newUser) throws NoSuchAlgorithmException {
        User user = _newUser;
        String query;
        query = "INSERT INTO USERS (UserID,UserName,Password,Email,Type) VALUES(";
        query += "DEFAULT,'" + user.userName+"','"+Util.sha256(user.password)+"','"+user.email+"','"+user.userType+"')";
        System.out.println(query);
        return createStatement(query);
    }

    public boolean authorizeUser(int userID,String plainPass) throws NoSuchAlgorithmException,SQLException {

        String query = "SELECT Password FROM USERS WHERE UserId ="+userID;
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        String plainHash = Util.sha256(plainPass);
        String dbHash ="";
        while(rs.next()){
           dbHash = rs.getString(1);
        }
        return plainHash.equals(dbHash);
    }

    public boolean createEvent(Event _event){
        Event event = _event;
        String query;
        query = "INSERT INTO EVENT (EventID,UserID,EventName,Venue,Description,Date,MaxSale) VALUES(";
        query += "DEFAULT,"+event.userID+",'"+event.name+"','"+event.venue+"','"+event.description+"','"+event.date+"',"+event.maxSale+")";
        System.out.println(query);
        return  createStatement(query);

    }

    private boolean createStatement(String query){
        try{
            stmt = conn.createStatement();
            if(stmt.executeUpdate(query) == 1){
                return true;
            }
            stmt.close();

        }catch(SQLException e){
            System.out.println(e.getMessage());

        }
        return  false;
    }

}
