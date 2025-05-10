package app.models;

public class Household {
	
	    private int id;
	    private String ownerUsername;
	    private int numOfMembers;
	    private String buildingBlock;
	    private int floor;
	    private String roomNumber;

	    // Getters and Setters
	    public String getId() { return String.valueOf(id); }
	    public void setId(int id) { this.id = id; }

	    public String getOwnerUsername() { return ownerUsername; }
	    public void setOwnerUsername(String ownerUserName) { this.ownerUsername = ownerUserName; }

	    public String getNumOfMembers() { return String.valueOf(numOfMembers); }
	    public void setNumOfMembers(int numOfMembers) { this.numOfMembers = numOfMembers; }
		public String getBuildingBlock() {
			return buildingBlock;
		}
		public void setBuildingBlock(String buildingBlock) {
			this.buildingBlock = buildingBlock;
		}
		public int getFloor() {
			return floor;
		}
		public void setFloor(int floor) {
			this.floor = floor;
		}
		public String getRoomNumber() {
			return roomNumber;
		}
		public void setRoomNumber(String roomNumber) {
			this.roomNumber = roomNumber;
		}

	   
}
