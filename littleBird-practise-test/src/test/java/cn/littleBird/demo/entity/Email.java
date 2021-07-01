package cn.littleBird.demo.entity;

public class Email {

	@Override
	public String toString() {
		return "Email [userName=" + userName + ", accounts=" + accounts + ", password=" + password + ", type=" + type
				+ ", bindPhoneNumber=" + bindPhoneNumber + ", bindQqNumber=" + bindQqNumber + "]";
	}

	private String userName;
	
	private String accounts;
	
	private String password;
	
	private String type;

	//绑定手机号
	private String bindPhoneNumber;

	//绑定qq号
	private String bindQqNumber;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accounts == null) ? 0 : accounts.hashCode());
		result = prime * result + ((bindPhoneNumber == null) ? 0 : bindPhoneNumber.hashCode());
		result = prime * result + ((bindQqNumber == null) ? 0 : bindQqNumber.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Email other = (Email) obj;
		if (accounts == null) {
			if (other.accounts != null)
				return false;
		} else if (!accounts.equals(other.accounts))
			return false;
		if (bindPhoneNumber == null) {
			if (other.bindPhoneNumber != null)
				return false;
		} else if (!bindPhoneNumber.equals(other.bindPhoneNumber))
			return false;
		if (bindQqNumber == null) {
			if (other.bindQqNumber != null)
				return false;
		} else if (!bindQqNumber.equals(other.bindQqNumber))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	public String getAccounts() {
		return accounts;
	}
	
	public void setAccounts(String accounts) {
		String regAccounts = "/^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$/";
		if (!accounts.matches(regAccounts)) {
			System.out.println("您输入的邮箱格式不正确，请重新输入！");
			return;
		}
		this.accounts = accounts;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBindPhoneNumber() {
		return bindPhoneNumber;
	}

	public void setBindPhoneNumber(String bindPhoneNumber) {
		this.bindPhoneNumber = bindPhoneNumber;
	}

	public String getBindQqNumber() {
		return bindQqNumber;
	}

	public void setBindQqNumber(String bindQqNumber) {
		this.bindQqNumber = bindQqNumber;
	}
	
}
