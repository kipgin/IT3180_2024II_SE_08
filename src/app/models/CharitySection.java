package app.models;

public class CharitySection {
	
	private int id;
    
	private String name;
    private int donate;
    public CharitySection() {
    	
    }
    public CharitySection(int id, String name, int donate) {
		super();
		this.id = id;
		this.name = name;
		this.donate = donate;
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
	public int getDonate() {
		return donate;
	}
	public void setDonate(int donate) {
		this.donate = donate;
	}
}
