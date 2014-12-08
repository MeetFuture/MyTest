package com.tangqiang.module.factory;

/**
 * 测试类
 * 
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-7-28 上午10:02:59
 * 
 * @version 1.0 2014-7-28 tqiang create
 * 
 */
public class MainRun {

	public static void main(String[] args) {
		ShapeFactory shapeFactory = new ShapeFactory();

		Shape circle = shapeFactory.getShape("Circle");
		Shape square = shapeFactory.getShape("Square");
		Shape rectangle = shapeFactory.getShape("Rectangle");

		circle.draw();
		square.draw();
		rectangle.draw();
	}
}
