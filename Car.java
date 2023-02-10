public class Car {
    private String brand;
    private int year;
    private String model;
    private int horsePower;
    private int age;
    private double engineSize;
    private String fuelType;
    private double initialPrice;
    private double indexPollution;
    private double indexRisk;

    public Car(String brand, int year, String model, int horsePower, double engineSize, String fuelType,
            double initialPrice) {
        this.brand = brand;
        this.year = year;
        this.model = model;
        this.horsePower = horsePower;
        this.age = 2023 - this.year;
        this.engineSize = engineSize;
        this.fuelType = fuelType;
        this.initialPrice = initialPrice;
        if (fuelType.equals("D")) {
            this.indexPollution = Math.round((engineSize * 1000 * age) / 1000) + 10;
            this.indexRisk = Math.round((horsePower * 10 * age) / 1000) + 5;

        } else {
            this.indexPollution = Math.round((engineSize * 1000 * age) / 1000);
            this.indexRisk = Math.round((horsePower * 10 * age) / 1000);
            // this.pollution = (engineSize * getPollutionPerLiter()) / (age * 100);
            // this.risk = (horsePower * getVehicleTypeFactor()) / (age * 100);

        }

    }

    private static Car instance;

    public static Car CreateInstance(String brand, int year, String model, int horsePower, double engineSize,
            String fuelType,
            double initialPrice) {
        instance = new Car(brand, year, model, horsePower, engineSize, fuelType, initialPrice);

        return GetInstance();
    }

    public static Car GetInstance() {
        return instance;
    }

    // Setters and getters
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(double engineSize) {
        this.engineSize = engineSize;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public double getintialPrice() {
        return initialPrice;
    }

    public void setinitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public double getindexPollution() {
        return indexPollution;
    }

    public void setindexPollution(double indexPollution) {
        this.indexPollution = indexPollution;
    }

    public double getindexRisk() {
        return indexRisk;
    }

    public void setindexRisk(double indexRisk) {
        this.indexRisk = indexRisk;
    }

}
