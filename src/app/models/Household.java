package app.models;

public class Household {
	
	    private int id;
	    private String ownerUsername;
	    private int numOfMembers;
	    private String currentLocation;
	    private String status;

	    // Getters and Setters
	    public String getId() { return String.valueOf(id); }
	    public void setId(int id) { this.id = id; }

	    public String getOwnerUsername() { return ownerUsername; }
	    public void setOwnerUsername(String ownerUserName) { this.ownerUsername = ownerUserName; }

	    public String getNumOfMembers() { return String.valueOf(numOfMembers); }
	    public void setNumOfMembers(int numOfMembers) { this.numOfMembers = numOfMembers; }

	    public String getCurrentLocation() { return currentLocation; }
	    public void setCurrentLocation(String currentLocation) { this.currentLocation = currentLocation; }

	    public String getStatus() { return status; }
	    public void setStatus(String status) { this.status = status; }
}
