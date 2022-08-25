package cn.littleBird.design_pattern.proxy;

public class Proxy implements IGiveGift{
  Pursuit gg;
  public Proxy() {
	super();
  }
  public Proxy(SchoolGirl mm) {
	super();
	this.gg = new Pursuit(mm);
  }
  @Override
  public void GiveDolls() {
	// TODO Auto-generated method stub
	gg.GiveDolls();
  }
  @Override
  public void GiveFlowers() {
	// TODO Auto-generated method stub
	gg.GiveFlowers();
  }
  @Override
  public void GiveChocolate() {
	// TODO Auto-generated method stub
	gg.GiveChocolate();
  }


  public static void main(String[] args) {
	SchoolGirl mm = new SchoolGirl();
	mm.setName("小美");
	Proxy daili = new Proxy(mm);
	daili.GiveDolls();
	daili.GiveFlowers();
	daili.GiveChocolate();
  }
}
