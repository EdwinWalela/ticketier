package com.company;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class Query {
    Connection conn;
    Statement stmt = null;

    public Query(Connection newConn){
        conn = newConn;
    }

    public boolean createUser(User _newUser) throws NoSuchAlgorithmException,SQLException {
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

    public boolean createEvent(Event _event) throws SQLException{
        Event event = _event;
        String query;
        query = "INSERT INTO EVENT (EventID,UserID,EventName,Venue,Description,Date,MaxSale) VALUES(";
        query += "DEFAULT,"+event.userID+",'"+event.name+"','"+event.venue+"','"+event.description+"','"+event.date+"',"+event.maxSale+")";
        System.out.println(query);
        return  createStatement(query);

    }

    public Event[] getEvents() throws SQLException{
        ArrayList<Event> events = new ArrayList<Event>();
        String query = "SELECT * FROM EVENT";
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()){
            int eventID,userID,maxSale;
            String venue,desc,name;
            Date date;

            userID = Integer.parseInt(rs.getString(2));
            name = rs.getString(3);
            venue = rs.getString(4);
            desc = rs.getString(5);
            date = Date.valueOf(rs.getString(6));
            maxSale = Integer.parseInt(rs.getString(7));

            Event event = new Event(userID,name,venue,desc,date,maxSale);
            events.add(event);
        }
        return events.toArray(new Event[]{});
    }

    public boolean createTicket(Ticket _ticket) throws SQLException{
        Ticket ticket = _ticket;
        String query;
        query = "INSERT INTO TICKET (TicketID,TicketType,Pax,Digest,Serial,UserID,EventID) VALUES(";
        query += "DEFAULT,'"+ticket.type+"',"+ticket.pax+",'"+ticket.digest+"','"+ticket.serial+"',"+ticket.owner+","+ticket.event+")";
        System.out.println(query);
        return createStatement(query);
    }

    private boolean createStatement(String query) throws SQLException {
        stmt = conn.createStatement();
        if (stmt.executeUpdate(query) == 1) {
            stmt.close();
            return true;
        } else {
            stmt.close();
            return false;
        }
    }



}
