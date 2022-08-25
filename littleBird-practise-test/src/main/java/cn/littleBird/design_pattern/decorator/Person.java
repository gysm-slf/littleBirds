package cn.littleBird.design_pattern.decorator;

public class Person {

  //需要被装饰的对象
	private String name;
	public Person(String name) {
	  super();
	  this.name = name;
	}
	public Person() {
	  super();
	}
	public void show(){
	  System.out.println("装扮的"+name);
	}


  /**
   * 装饰器模式
   * 什么时候使用装饰模式：
   *
   * 当系统需要新功能时，是向旧的类中添加新的代码，这些新代码通常装饰了原有的核心职责或主要行为。
   * 把每个要装饰的功能放在单独的类中，并让这个类包装它所有装饰的对象。
   *
   * 优点：
   *
   * 把类的核心职责和装饰功能区分开来，去除相关类中重复的装饰逻辑。
   * 简化原有类。
   *
   * 装饰模式之前的面向对象原则介绍
   * 单一职责原则：就一个类而言，应该仅有一个引起它变化的原因。也就是说功能要单一。
   * 优点： 灵活性，可复用性。
   * 如果一个类承担的职责太多，就等于把这些职责耦合在一起，一个职责的变化可能会削弱或者阻碍其他职责能力，这种耦合会导致脆弱的设计，当变化发生时，设计会发生意想不到的变化。
   * 开放封闭原则：软件应该可以扩展，但不可以修改。对于扩展是开放的，对于更改是封闭的。
   * 面对需求，对程序的改动是通过增加新代码进行的，而不是更改现有代码，这就是开放封闭原则的精神所在。
   * 优点：可扩展 可复用 灵活性好
   * 依赖倒转原则：抽象不应该依赖细节，细节不应该依赖抽象。针对对接口编程，不要对实现编程。即高层模块不依赖底层模块，底层模块不依赖高层模块。
   * 其实就是谁也不依赖谁，除了约定的接口，大家都可以灵活自如。
   * 里氏代换原则：子类型必须能够替换掉他们的父类型，只有当子类可以替换掉父类，软件单位功能不受影响时，父类才能真正倍复用。
   * 装饰模式
   * 装饰模式：动态地给一个对象添加一些额外的职责，就增加功能来说，装饰模式比生成子类更为灵活。
   *
   * 装饰模式是为已有功能动态的添加更多功能的一种方式。
   * @param args
   */
  public static void main(String[] args){
	  Person xc = new Person("小菜");
	  System.out.println("第一种装扮：");
	  WearSneakers w = new WearSneakers();
	  WearSuit ws = new WearSuit();
	  BigTrouser bt = new BigTrouser();
	  TShirts t = new TShirts();
	  /*
	   * 首先实例化person对象
	   * 再用WearSneakers类包装person
	   * 再用WearSuit类来包装WearSneakers对象
	   * 再用BigTrouser类包装WearSuit对象
	   * 最终执行BigTrouser的show方法。
	   *
	   */
	  w.Decorate(xc);
	  ws.Decorate(w);
	  bt.Decorate(ws);
	  t.Decorate(bt);
	  t.show();
	}
}
