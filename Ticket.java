package com.company;

public class Ticket {
    public String type;
    public int pax;
    public String digest;
    public String serial;
    public int owner;
    public int event;

    public Ticket(String _type,int _pax,String _digest,String _serial,int _owner,int _event){
        type = _type;
        pax = _pax;
        digest = _digest;
        serial = _serial;
        owner = _owner;
        event = _event;
    }
}
