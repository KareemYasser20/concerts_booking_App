package com.example.concerts_booking_app;

public class SeatsModel {

    String id , State , TicketPrice;

    public SeatsModel() {
    }

    public SeatsModel(String id, String state, String ticketPrice) {
        this.id = id;
        State = state;
        TicketPrice = ticketPrice;
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return State;
    }

    public String getTicketPrice() {
        return TicketPrice;
    }
}
