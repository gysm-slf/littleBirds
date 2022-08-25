package cn.littleBird.design_pattern.decorator;

public class BigTrouser extends Finery {
  @Override
  public void show(){
	System.out.println("垮裤");
	super.show();
  }
}