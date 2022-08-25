package cn.littleBird.design_pattern.decorator;

public class WearSneakers extends Finery {
  @Override
  public void show(){
	System.out.println("破球鞋");
	super.show();
  }
}