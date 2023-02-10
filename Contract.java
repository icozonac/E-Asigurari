
public class Contract {
    private String type;
    private double rate;

    public Contract(String type, double rate) {
        this.type = type;
        this.rate = rate;

    }

    // Getters and setters for the attributes

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

}
