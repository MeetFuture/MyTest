package com.tangqiang.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CollectionTest {
	
	public static void main(String[] args) {
		CollectionTest ct = new CollectionTest();
		ct.addMapData();
		ct.addListData();
	}
	
	private void addMapData() {
		HashMap<String, String> map = new HashMap<String, String>() {
		    {
		        put("Name", "June");   
		        put("QQ", "2572073701");  
		    }
		};
		System.out.println(map);
	}
	
	private void addListData() {
		List<String> names = new ArrayList<String>() {
		    {
		        for (int i = 0; i < 10; i++) {
		            add("A" + i);
		        }
		    }
		};
		System.out.println(names.toString());
	}
	
	

}
