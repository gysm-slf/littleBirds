package cn.littleBird.design_pattern.decorator;

public class WearSuit extends Finery{
  @Override
  public void show(){
	System.out.println("西装");
	super.show();
  }
}