package cn.littleBird.demo.old;

public class TestAnnon {
	@MyAutowired(value = "备注是嘻嘻哈哈")
	private String xxhh = "100";
	public static void main(String[] args) throws NoSuchFieldException, SecurityException {
		// TODO Auto-generated method stub
		System.out.println(new TestAnnon().getClass());
		System.out.println(new TestAnnon().getClass().getDeclaredField("xxhh").getAnnotation(MyAutowired.class).value());
	}

}
