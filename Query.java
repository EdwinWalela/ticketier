package com.company;

import java.sql.Connection;

public class Query {
    Connection conn;

    public Query(Connection newConn){
        conn = newConn;
    }
}
