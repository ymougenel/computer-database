public class Hi {
	public static void main(String[] a) {
		Balloon red = new Balloon("Red");
		Balloon blue = new Balloon("Blue");
		swap(red,blue);
		red.print();
	}
	
	public static void swap(Object o1, Object o2) {
		Object tmp = o1;
		o1 = o2;
		o2 = tmp;
	}
}
