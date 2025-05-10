package app.models;

public class TopDonor {
	private int rank;
    private String ownerUserName;
    private int totalDonate;

    public TopDonor(int rank , String ownerUserName, int totalDonate) {
    	this.rank = rank;
        this.ownerUserName = ownerUserName;
        this.totalDonate = totalDonate;
    }
    public void setRank(int rank) {
    	this.rank = rank;
    }
    public int getRank() {
        return rank;
    }
    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public int getTotalDonate() {
        return totalDonate;
    }

    public void setTotalDonate(int totalDonate) {
        this.totalDonate = totalDonate;
    }
}
