package cn.littleBird.design_pattern.proxy;

public class Pursuit implements IGiveGift{
  SchoolGirl mm;
  public Pursuit(SchoolGirl mm) {
	super();
	this.mm = mm;
  }
  public Pursuit() {
	super();
  }
  @Override
  public void GiveDolls() {
	// TODO Auto-generated method stub
	System.out.println(mm.getName()+"送你洋娃娃。");
  }
  @Override
  public void GiveFlowers() {
	// TODO Auto-generated method stub
	System.out.println(mm.getName()+"送你鲜花。");
  }
  @Override
  public void GiveChocolate() {
	// TODO Auto-generated method stub
	System.out.println(mm.getName()+"送你巧克力。");
  }
}
