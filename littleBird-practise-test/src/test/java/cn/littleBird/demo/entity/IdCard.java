package cn.littleBird.demo.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IdCard {

	private String name;
	
	private String birthday;
	
	private Integer age;
	
	private String number;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) throws ParseException {
		if (birthday != null && !birthday.equals("")) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = simpleDateFormat.parse(birthday);
			this.age = Integer.valueOf(Long.toString((System.currentTimeMillis() - date.getTime()) / 1000 / 60 / 60 / 24 / 365));
		}
		this.birthday = birthday;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		String regNumber = "/(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)/";
		if (!number.matches(regNumber)) {
			System.out.println("您输入的身份证号不正确，请重新输入！");
			return;
		}
		this.number = number;
	}
	
}
