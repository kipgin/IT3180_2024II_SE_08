package app.models;

public class FeeName {
      
	private int id;
	private String name;
	private double moneyPerBlock;
	private String block;
	
	public FeeName() {
		
	}
	
	public FeeName(int id, String name, double moneyPerBlock, String block) {
		super();
		this.id = id;
		this.name = name;
		this.moneyPerBlock = moneyPerBlock;
		this.block = block;
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

	public double getMoneyPerBlock() {
		return moneyPerBlock;
	}

	public void setMoneyPerBlock(int moneyPerBlock) {
		this.moneyPerBlock = moneyPerBlock;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	@Override
	public String toString() {
		return name + " - " + moneyPerBlock + " VND/" +  block;
	}
	
	
	
	
}
