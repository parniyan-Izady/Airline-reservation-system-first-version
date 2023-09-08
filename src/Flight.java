public class Flight {
    private String flightId;
    private String origin;
    private String destination;
    private String date;
    private String time;
    private double price;
    private int remainedSeats = 100;

    public Flight() {
    }

    public String getFlightId() {
        return this.flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getOrigin() {
        return this.origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRemainedSeats() {
        return this.remainedSeats;
    }

    public void setRemainedSeats(int remainedSeats) {
        this.remainedSeats = remainedSeats;
    }
}
