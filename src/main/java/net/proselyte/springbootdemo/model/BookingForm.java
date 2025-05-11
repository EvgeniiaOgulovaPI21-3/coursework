package net.proselyte.springbootdemo.model;

public class BookingForm {
    private String name;
    private String phone;
    private String date;
    private String time;
    private String guests;

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getGuests() {
        return guests;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setGuests(String guests) {
        this.guests = guests;
    }
}
