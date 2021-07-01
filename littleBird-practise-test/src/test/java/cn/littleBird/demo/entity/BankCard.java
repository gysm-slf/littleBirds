package cn.littleBird.demo.entity;

/**
 * 
 * @author Administrator
 *
 */

public class BankCard {

	private String type;
	
	private String bankId;
	
	private String nameOfCardholder;
	
	private double balance;
	
	private String affiliatedBank;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getNameOfCardholder() {
		return nameOfCardholder;
	}

	public void setNameOfCardholder(String nameOfCardholder) {
		this.nameOfCardholder = nameOfCardholder;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getAffiliatedBank() {
		return affiliatedBank;
	}

	public void setAffiliatedBank(String affiliatedBank) {
		this.affiliatedBank = affiliatedBank;
	}
	
}
