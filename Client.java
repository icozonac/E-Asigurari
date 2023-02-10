public class Client {
    private String name;
    private String firstName;
    private String cnp;
    private String address;
    private String email;
    private String phone;
    private boolean accident;

    public Client(String name, String firstName, String cnp, String address, String email, String phone,
            boolean accident) {
        this.name = name;
        this.firstName = firstName;
        this.cnp = cnp;
        this.address = address;
        this.email = email;
        this.phone = phone;

        this.accident = accident;
    }

    private static Client instance;

    public static Client CreateInstance(String name, String firstName, String cnp, String address, String email,
            String phone,
            boolean accident) {
        instance = new Client(name, firstName, cnp, address, email, phone, accident);
        return GetInstance();
    }

    public static Client GetInstance() {
        return instance;
    }

    // Getters and setters for the attributes
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getfirstName() {
        return firstName;
    }

    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCNP() {
        return cnp;
    }

    public void setCNP(String cnp) {
        this.cnp = cnp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getAccident() {
        return accident;
    }

    public void setAccident(Boolean accident) {
        this.accident = accident;
    }
}
