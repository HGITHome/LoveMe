package com.dgut.common.util;

import java.util.Random;

public class RandomNumberUtils {
	private static RandomNumberUtils numberUtils;
	private static Random random = new Random();
	public static RandomNumberUtils getInstance(){
		if(numberUtils == null){
			numberUtils = new RandomNumberUtils();
		}
		return numberUtils;
	}
	
	public String randomNumber(){
		
		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数  
		
		return String.valueOf(rannum);
	}
}
