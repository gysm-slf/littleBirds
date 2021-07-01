package cn.littleBird.demo.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author gysm_slf@163.com
 *
 */
public class Person implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	/*
	 * js中的验证方式 var regName =/^[\u4e00-\u9fa5]{2,4}$/; if(!regName.test(name)){
	 * alert('真实姓名填写有误'); return false; }
	 */
	private IdCard idCard;
	/*
	 * var regIdNo = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
	 * if(!regIdNo.test(idNo)){ alert('身份证号填写有误'); return false; }
	 */

	private String sex;
	
	private Integer age;
	
	private String birthday;
	
	private Person father;
	
	private Person mother;
	
	private Person partner;
	
	private Map<String, Person> children;
	
	private Map<String, Person> memberOfFamily;
	
	private Map<String,Qq> qq;
	
	private Map<String,weChat> weChat;
	
	private Map<String,sinaId> sinaId;
	
	private Map<String,threeSixZeroId> threeSixZeroId;
	
	private Map<String, Email> email;
	
	private Map<String, House> house;
	
	private Map<String, Car> car;
	
	private Map<String, Phone> phone;
	
	private Map<String, Computer> computer;
	
	private Map<String, Game> favoriteGames;
	
	private Map<String, Object> like;
	
	private Map<String, Object> dislike;
	
	private Map<String, Bicycle> bicycle;
	
	private Map<String, Music> music;
	
	private Map<String, Movie> movie;
	
	private Map<String, Book> book;
	
	private Map<String, BankCard> bankCard;
	
	private Company workUnit;
	
	private String position;
	
	private String jobDescription;
	
	// 个人简介
	private String personalProfile;
	
	//7位家庭电话号码
	private String telephoneNumber;
	
	private Phone mobilePhoneNumber;
	
	private String presentAddress;
	
	private String homeAddress;
	
	private Country country;
	
	private Province province;
	
	private City city;
	
	// 偶像
	private Map<String, Person> idol;
	
	// 动漫
	private Map<String, Comic> comic;
	
	//区
	private String area;

	//宠物
	private Map<String, Animal> pets;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		/* String regx="(^[a-zA-Z0-9_-]{3,16}$)|([\\u2E80-\\u9FFF]{2,5})";
		if(!empName.matches(regx)) {
			return Msg.fail().add("controller_validate", "用户名必须是2-5位中文或3-16位英文和数字的组合字符"); */
		String regName = "[\\u2E80-\\u9FFF]{2,10}";
		if (!name.matches(regName)) {
			System.out.println("您输入的姓名与身份证上的姓名不符或者您输入的姓名不符合规范！");
			return;
		}
		this.name = name;
	}

	public IdCard getIdCard() {
		return idCard;
	}

	public void setIdCard(IdCard idCard) {
		this.idCard = idCard;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
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

	public Person getFather() {
		return father;
	}

	public void setFather(Person father) {
		this.father = father;
	}

	public Person getMother() {
		return mother;
	}

	public void setMother(Person mother) {
		this.mother = mother;
	}

	public Person getPartner() {
		return partner;
	}

	public void setPartner(Person partner) {
		this.partner = partner;
	}

	public Map<String, Person> getChildren() {
		return children;
	}

	public void setChildren(Map<String, Person> children) {
		this.children = children;
	}

	public Map<String, Person> getMemberOfFamily() {
		return memberOfFamily;
	}

	public void setMemberOfFamily(Map<String, Person> memberOfFamily) {
		this.memberOfFamily = memberOfFamily;
	}

	public Map<String, Qq> getQq() {
		return qq;
	}

	public void setQq(Map<String, Qq> qq) {
		this.qq = qq;
	}

	public Map<String, weChat> getWeChat() {
		return weChat;
	}

	public void setWeChat(Map<String, weChat> weChat) {
		this.weChat = weChat;
	}

	public Map<String, sinaId> getSinaId() {
		return sinaId;
	}

	public void setSinaId(Map<String, sinaId> sinaId) {
		this.sinaId = sinaId;
	}

	public Map<String, threeSixZeroId> getThreeSixZeroId() {
		return threeSixZeroId;
	}

	public void setThreeSixZeroId(Map<String, threeSixZeroId> threeSixZeroId) {
		this.threeSixZeroId = threeSixZeroId;
	}

	public Map<String, Email> getEmail() {
		return email;
	}

	public void setEmail(Map<String, Email> email) {
		this.email = email;
	}

	public Map<String, House> getHouse() {
		return house;
	}

	public void setHouse(Map<String, House> house) {
		this.house = house;
	}

	public Map<String, Car> getCar() {
		return car;
	}

	public void setCar(Map<String, Car> car) {
		this.car = car;
	}

	public Map<String, Phone> getPhone() {
		return phone;
	}

	public void setPhone(Map<String, Phone> phone) {
		this.phone = phone;
	}

	public Map<String, Computer> getComputer() {
		return computer;
	}

	public void setComputer(Map<String, Computer> computer) {
		this.computer = computer;
	}

	public Map<String, Game> getFavoriteGames() {
		return favoriteGames;
	}

	public void setFavoriteGames(Map<String, Game> favoriteGames) {
		this.favoriteGames = favoriteGames;
	}

	public Map<String, Object> getLike() {
		return like;
	}

	public void setLike(Map<String, Object> like) {
		this.like = like;
	}

	public Map<String, Object> getDislike() {
		return dislike;
	}

	public void setDislike(Map<String, Object> dislike) {
		this.dislike = dislike;
	}

	public Map<String, Bicycle> getBicycle() {
		return bicycle;
	}

	public void setBicycle(Map<String, Bicycle> bicycle) {
		this.bicycle = bicycle;
	}

	public Map<String, Music> getMusic() {
		return music;
	}

	public void setMusic(Map<String, Music> music) {
		this.music = music;
	}

	public Map<String, Movie> getMovie() {
		return movie;
	}

	public void setMovie(Map<String, Movie> movie) {
		this.movie = movie;
	}

	public Map<String, Book> getBook() {
		return book;
	}

	public void setBook(Map<String, Book> book) {
		this.book = book;
	}

	public Map<String, BankCard> getBankCard() {
		return bankCard;
	}

	public void setBankCard(Map<String, BankCard> bankCard) {
		this.bankCard = bankCard;
	}

	public Company getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(Company workUnit) {
		this.workUnit = workUnit;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getPersonalProfile() {
		return personalProfile;
	}

	public void setPersonalProfile(String personalProfile) {
		this.personalProfile = personalProfile;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public Phone getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(Phone mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	public String getPresentAddress() {
		return presentAddress;
	}

	public void setPresentAddress(String presentAddress) {
		this.presentAddress = presentAddress;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Map<String, Person> getIdol() {
		return idol;
	}

	public void setIdol(Map<String, Person> idol) {
		this.idol = idol;
	}

	public Map<String, Comic> getComic() {
		return comic;
	}

	public void setComic(Map<String, Comic> comic) {
		this.comic = comic;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Map<String, Animal> getPets() {
		return pets;
	}

	public void setPets(Map<String, Animal> pets) {
		this.pets = pets;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", idCard=" + idCard + ", sex=" + sex + ", age=" + age + ", birthday="
				+ birthday + ", father=" + father + ", mother=" + mother + ", partner=" + partner + ", children="
				+ children + ", memberOfFamily=" + memberOfFamily + ", qq=" + qq + ", weChat=" + weChat + ", sinaId="
				+ sinaId + ", threeSixZeroId=" + threeSixZeroId + ", email=" + email + ", house=" + house + ", car="
				+ car + ", phone=" + phone + ", computer=" + computer + ", favoriteGames=" + favoriteGames + ", like="
				+ like + ", dislike=" + dislike + ", bicycle=" + bicycle + ", music=" + music + ", movie=" + movie
				+ ", book=" + book + ", bankCard=" + bankCard + ", workUnit=" + workUnit + ", position=" + position
				+ ", jobDescription=" + jobDescription + ", personalProfile=" + personalProfile + ", telephoneNumber="
				+ telephoneNumber + ", mobilePhoneNumber=" + mobilePhoneNumber + ", presentAddress=" + presentAddress
				+ ", homeAddress=" + homeAddress + ", country=" + country + ", province=" + province + ", city=" + city
				+ ", idol=" + idol + ", comic=" + comic + ", area=" + area + ", pets=" + pets + "]";
	}

	public Person() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((bankCard == null) ? 0 : bankCard.hashCode());
		result = prime * result + ((bicycle == null) ? 0 : bicycle.hashCode());
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + ((car == null) ? 0 : car.hashCode());
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((comic == null) ? 0 : comic.hashCode());
		result = prime * result + ((computer == null) ? 0 : computer.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((dislike == null) ? 0 : dislike.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((father == null) ? 0 : father.hashCode());
		result = prime * result + ((favoriteGames == null) ? 0 : favoriteGames.hashCode());
		result = prime * result + ((homeAddress == null) ? 0 : homeAddress.hashCode());
		result = prime * result + ((house == null) ? 0 : house.hashCode());
		result = prime * result + ((idCard == null) ? 0 : idCard.hashCode());
		result = prime * result + ((idol == null) ? 0 : idol.hashCode());
		result = prime * result + ((jobDescription == null) ? 0 : jobDescription.hashCode());
		result = prime * result + ((like == null) ? 0 : like.hashCode());
		result = prime * result + ((memberOfFamily == null) ? 0 : memberOfFamily.hashCode());
		result = prime * result + ((mobilePhoneNumber == null) ? 0 : mobilePhoneNumber.hashCode());
		result = prime * result + ((mother == null) ? 0 : mother.hashCode());
		result = prime * result + ((movie == null) ? 0 : movie.hashCode());
		result = prime * result + ((music == null) ? 0 : music.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((partner == null) ? 0 : partner.hashCode());
		result = prime * result + ((personalProfile == null) ? 0 : personalProfile.hashCode());
		result = prime * result + ((pets == null) ? 0 : pets.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((presentAddress == null) ? 0 : presentAddress.hashCode());
		result = prime * result + ((province == null) ? 0 : province.hashCode());
		result = prime * result + ((qq == null) ? 0 : qq.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((sinaId == null) ? 0 : sinaId.hashCode());
		result = prime * result + ((telephoneNumber == null) ? 0 : telephoneNumber.hashCode());
		result = prime * result + ((threeSixZeroId == null) ? 0 : threeSixZeroId.hashCode());
		result = prime * result + ((weChat == null) ? 0 : weChat.hashCode());
		result = prime * result + ((workUnit == null) ? 0 : workUnit.hashCode());
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
		Person other = (Person) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (bankCard == null) {
			if (other.bankCard != null)
				return false;
		} else if (!bankCard.equals(other.bankCard))
			return false;
		if (bicycle == null) {
			if (other.bicycle != null)
				return false;
		} else if (!bicycle.equals(other.bicycle))
			return false;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (car == null) {
			if (other.car != null)
				return false;
		} else if (!car.equals(other.car))
			return false;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (comic == null) {
			if (other.comic != null)
				return false;
		} else if (!comic.equals(other.comic))
			return false;
		if (computer == null) {
			if (other.computer != null)
				return false;
		} else if (!computer.equals(other.computer))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (dislike == null) {
			if (other.dislike != null)
				return false;
		} else if (!dislike.equals(other.dislike))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (father == null) {
			if (other.father != null)
				return false;
		} else if (!father.equals(other.father))
			return false;
		if (favoriteGames == null) {
			if (other.favoriteGames != null)
				return false;
		} else if (!favoriteGames.equals(other.favoriteGames))
			return false;
		if (homeAddress == null) {
			if (other.homeAddress != null)
				return false;
		} else if (!homeAddress.equals(other.homeAddress))
			return false;
		if (house == null) {
			if (other.house != null)
				return false;
		} else if (!house.equals(other.house))
			return false;
		if (idCard == null) {
			if (other.idCard != null)
				return false;
		} else if (!idCard.equals(other.idCard))
			return false;
		if (idol == null) {
			if (other.idol != null)
				return false;
		} else if (!idol.equals(other.idol))
			return false;
		if (jobDescription == null) {
			if (other.jobDescription != null)
				return false;
		} else if (!jobDescription.equals(other.jobDescription))
			return false;
		if (like == null) {
			if (other.like != null)
				return false;
		} else if (!like.equals(other.like))
			return false;
		if (memberOfFamily == null) {
			if (other.memberOfFamily != null)
				return false;
		} else if (!memberOfFamily.equals(other.memberOfFamily))
			return false;
		if (mobilePhoneNumber == null) {
			if (other.mobilePhoneNumber != null)
				return false;
		} else if (!mobilePhoneNumber.equals(other.mobilePhoneNumber))
			return false;
		if (mother == null) {
			if (other.mother != null)
				return false;
		} else if (!mother.equals(other.mother))
			return false;
		if (movie == null) {
			if (other.movie != null)
				return false;
		} else if (!movie.equals(other.movie))
			return false;
		if (music == null) {
			if (other.music != null)
				return false;
		} else if (!music.equals(other.music))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (partner == null) {
			if (other.partner != null)
				return false;
		} else if (!partner.equals(other.partner))
			return false;
		if (personalProfile == null) {
			if (other.personalProfile != null)
				return false;
		} else if (!personalProfile.equals(other.personalProfile))
			return false;
		if (pets == null) {
			if (other.pets != null)
				return false;
		} else if (!pets.equals(other.pets))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (presentAddress == null) {
			if (other.presentAddress != null)
				return false;
		} else if (!presentAddress.equals(other.presentAddress))
			return false;
		if (province == null) {
			if (other.province != null)
				return false;
		} else if (!province.equals(other.province))
			return false;
		if (qq == null) {
			if (other.qq != null)
				return false;
		} else if (!qq.equals(other.qq))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (sinaId == null) {
			if (other.sinaId != null)
				return false;
		} else if (!sinaId.equals(other.sinaId))
			return false;
		if (telephoneNumber == null) {
			if (other.telephoneNumber != null)
				return false;
		} else if (!telephoneNumber.equals(other.telephoneNumber))
			return false;
		if (threeSixZeroId == null) {
			if (other.threeSixZeroId != null)
				return false;
		} else if (!threeSixZeroId.equals(other.threeSixZeroId))
			return false;
		if (weChat == null) {
			if (other.weChat != null)
				return false;
		} else if (!weChat.equals(other.weChat))
			return false;
		if (workUnit == null) {
			if (other.workUnit != null)
				return false;
		} else if (!workUnit.equals(other.workUnit))
			return false;
		return true;
	}
	
	
}