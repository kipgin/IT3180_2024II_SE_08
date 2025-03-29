package app.models;

public class Resident {
    private int id;
    private String name;
    private String gender;
    private int birthYear;
    private String accomStatus;
    private int householdId;

    public Resident() {
    	
    }
    // Constructor
    public Resident(int id, String name, String gender, int birthYear, String accomStatus, int householdId) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthYear = birthYear;
        this.accomStatus = accomStatus;
        this.householdId = householdId;
    }

    // Getters and Setters
    public String getId() {
        return String.valueOf(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthYear() {
        return String.valueOf(birthYear);
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getAccomStatus() {
        return accomStatus;
    }

    public void setAccomStatus(String accomStatus) {
        this.accomStatus = accomStatus;
    }

    public String getHouseholdId() {
        return String.valueOf(householdId);
    }

    public void setHouseholdId(int householdId) {
        this.householdId = householdId;
    }

    @Override
    public String toString() {
        return "Resident{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", birthYear=" + birthYear +
                ", accomStatus='" + accomStatus + '\'' +
                ", householdId=" + householdId +
                '}';
    }
} 
