package com.company;

public class Ticket {
    public String type;
    public int pax;
    public String digest;
    public String serial;
    public int owner;
    public int eventId;
    public String eventName;

    public Ticket(String _type,int _pax,String _digest,String _serial,int _owner,int _event,String _name){
        type = _type;
        pax = _pax;
        digest = _digest;
        serial = _serial;
        owner = _owner;
        eventId = _event;
        eventName = _name;
    }
}
