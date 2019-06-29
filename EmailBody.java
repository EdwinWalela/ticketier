package com.company;

public class EmailBody {
    String To;
    String subject;
    Ticket ticket;
    String body;

    public EmailBody(String _to, String _subject,Ticket _ticket){
        To = _to;
        subject = _subject;
        ticket = _ticket;
    }

}
