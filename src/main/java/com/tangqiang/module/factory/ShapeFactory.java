package com.tangqiang.module.factory;

/**
 * 工厂
 * 
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-7-28 上午10:01:07
 * 
 * @version 1.0 2014-7-28 tqiang create
 * 
 */
public class ShapeFactory {

	public Shape getShape(String shape) {
		if (shape == null) {
			return null;
		}
		switch (shape.toLowerCase()) {
		case "circle":
			return new Circle();
		case "square":
			return new Square();
		case "rectangle":
			return new Rectangle();
		default:
			return null;
		}
	}
}