package com.company;

import java.sql.Date;

public class Event {

    public int userID;
    public String name;
    public String venue;
    public String description;
    public Date date;
    public int maxSale;
    public String type;


    public Event(int _userID, String _name, String _venue, String _description, Date _date, int _maxSale){
        userID = _userID;
        name =  _name;
        venue = _venue;
        description =_description;
        date = _date;
        maxSale = _maxSale;
    }


}

