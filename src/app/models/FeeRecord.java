package app.models;

import java.util.List;

public class FeeRecord {

	private int id;
	private String ownerUserName;
	private String accom_status;
	private double totalFee;
	private boolean  paid;
	private List<FeeSection> feeSections;
	
	public FeeRecord() {
		
	}
	
	public FeeRecord(int id, String ownerUserName, String accom_status, double totalFee, boolean paid,
			List<FeeSection> feeSections) {
		super();
		this.id = id;
		this.ownerUserName = ownerUserName;
		this.accom_status = accom_status;
		this.totalFee = totalFee;
		this.paid = paid;
		this.feeSections = feeSections;
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

	public String getAccom_status() {
		return accom_status;
	}

	public void setAccom_status(String accom_status) {
		this.accom_status = accom_status;
	}

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public List<FeeSection> getFeeSections() {
		return feeSections;
	}

	public void setFeeSections(List<FeeSection> feeSections) {
		this.feeSections = feeSections;
	}
	
	
	
	
	
}
