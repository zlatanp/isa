package com.example.utilities;

import java.io.File;

public class FileHelper {

	public static int getMaxFileName(String path){
		int max = 0;
		File folder = new File(path);
		for(File f : folder.listFiles()){
			if(f.isFile()){
				String name = f.getName();
				try {
					int broj = Integer.parseInt(name.split("\\.")[0]);
					if(broj > max){
						max = broj;
					}
				}catch (Exception e) {
					continue;
				}
			}
		}
		return max;
	}
}
