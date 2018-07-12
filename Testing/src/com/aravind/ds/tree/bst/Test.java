package com.aravind.ds.tree.bst;
public class Test {

	public static void main(String[] args) {

		TreeNode element = new TreeNode(0);

		element.addNode(-5);
		element.addNode(6);
		element.addNode(3);
		element.addNode(10);
		element.addNode(-10);
		element.addNode(-3);
		element.addNode(5);
		element.addNode(4);
		element.addNode(10);

		element.printAll();
		System.out.println("##################################################################");
		System.out.println("Searching elements 6");
		System.out.println(element.searchValue(-3));
		System.out.println(element.searchValue(11));
		System.out.println(element.searchValue(10));

		System.out.println("##################################################################");
		System.out.println("Printing the total sum");
		System.out.println(element.getSum());

		System.out.println("##################################################################");
		System.out.println("Getting the Minimum value");
		System.out.println(element.getMin());

		System.out.println("##################################################################");
		System.out.println("Deleting Element 6");
		System.out.println(element.deleteNode(6, null));
		element.printAll();

		System.out.println("##################################################################");
		System.out.println("Printing Size");
		System.out.println(element.getSize());

		System.out.println("##################################################################");
		System.out.println("Printing Height");
		System.out.println(element.getHeight());
	}
}
