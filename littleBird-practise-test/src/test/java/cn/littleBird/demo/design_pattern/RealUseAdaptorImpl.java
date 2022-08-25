package cn.littleBird.demo.design_pattern;

public class RealUseAdaptorImpl extends Adaptor{

  @Override
  public void met1() {
	System.out.println("m1");
  }

  @Override
  public void met2() {
	System.out.println("m2");
  }

  public static void main(String[] args) {
	new RealUseAdaptorImpl().met1();
	new RealUseAdaptorImpl().met2();
	new RealUseAdaptorImpl().met4();
  }
}
