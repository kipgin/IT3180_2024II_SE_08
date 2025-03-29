package app.models;

public class PaymentRecord {

    private int id;
    private double area;
    private double serviceFeePerSquare;
    private double totalFee;
    private String ownerUserName;
    private String accom_status;

    public PaymentRecord() {
    	
    }
    // Constructor
    public PaymentRecord(int id, double area, double serviceFeePerSquare, double totalFee, String ownerUserName, String accom_status) {
        this.id = id;
        this.area = area;
        this.serviceFeePerSquare = serviceFeePerSquare;
        this.totalFee = totalFee;
        this.ownerUserName = ownerUserName;
        this.accom_status = accom_status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getServiceFeePerSquare() {
        return serviceFeePerSquare;
    }

    public void setServiceFeePerSquare(double serviceFeePerSquare) {
        this.serviceFeePerSquare = serviceFeePerSquare;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public String getAccom_status() {
        return accom_status;
    }

    public void setAccom_status(String accom_status) {
        this.accom_status = accom_status;
    }

    @Override
    public String toString() {
        return "PaymentRecord{" +
                "id=" + id +
                ", area=" + area +
                ", serviceFeePerSquare=" + serviceFeePerSquare +
                ", totalFee=" + totalFee +
                ", ownerUserName='" + ownerUserName + '\'' +
                ", accom_status='" + accom_status + '\'' +
                '}';
    }
}
