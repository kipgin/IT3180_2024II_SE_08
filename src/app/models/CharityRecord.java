package app.models;

import java.util.List;

public class CharityRecord {
    private int id;
	private String ownerUserName;
	private String accomStatus;
    private List<CharitySection> charitySections;
    
    public CharityRecord() {
    }

    public CharityRecord(int id, String ownerUserName, String accomStatus, List<CharitySection> charitySections) {
        this.id = id;
        this.ownerUserName = ownerUserName;
        this.accomStatus = accomStatus;
        this.charitySections = charitySections;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public String getAccomStatus() {
        return accomStatus;
    }

    public void setAccomStatus(String accomStatus) {
        this.accomStatus = accomStatus;
    }

    public List<CharitySection> getCharitySections() {
        return charitySections;
    }

    public void setCharitySections(List<CharitySection> charitySections) {
        this.charitySections = charitySections;
    }
    
}
