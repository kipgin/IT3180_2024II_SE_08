package app.models;

public class FeeSection {
     private int id;
     private String name;
     private double blockUsed;
     
     public FeeSection() {
    	 
     }
     
	 public FeeSection(String name, double blockUsed) {
		super();
		this.name = name;
		this.blockUsed = blockUsed;
	}

	public int getId() {
		return id;
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

	public double getBlockUsed() {
		return blockUsed;
	}

	public void setBlockUsed(double blockUsed) {
		this.blockUsed = blockUsed;
	}
	
	
	 
	 
     
     
}
