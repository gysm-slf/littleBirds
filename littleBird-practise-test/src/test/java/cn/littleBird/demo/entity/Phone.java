package cn.littleBird.demo.entity;

public class Phone {

	private String number;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		String regNumber = "/^1[3|4|5|7|8][0-9]{9}$/";
		if (!number.matches(regNumber)) {
			System.out.println("您输入的手机号不正确，请重新输入！");
			return;
		}
		this.number = number;
	}
	
}
