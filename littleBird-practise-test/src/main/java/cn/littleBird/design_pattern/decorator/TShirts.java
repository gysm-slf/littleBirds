package cn.littleBird.design_pattern.decorator;

public class TShirts extends Finery {
  @Override
  public void show(){
	System.out.println("大T恤");
	super.show();
  }
}
