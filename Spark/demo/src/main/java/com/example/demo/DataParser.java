package com.example.demo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DataParser {
	
	public String[] parseData() throws IOException {
		
		FileInputStream fis = null;
		BufferedReader br = null;
		String[] links = new String[10];
		
		try {
			
			fis = new FileInputStream("producttable");
			br = new BufferedReader(new InputStreamReader(fis));
			Map<String, String> products = new HashMap<>();
			String productData;
			String[] productDataSplit = new String[2];
			while((productData = br.readLine()) != null) {
				productDataSplit = productData.split(",");
				products.put(productDataSplit[1], productDataSplit[0]);
			}
			
			fis.close();br.close();
			
			fis = new FileInputStream("/usr/local/ops/part-00000");
			br = new BufferedReader(new InputStreamReader(fis));
			String strLine, record;
			String[] data = new String[3];
			String[] productId = new String[10];
			int i=0;
			while((strLine = br.readLine()) != null) {
				record = strLine.substring(7);
				record = record.substring(0, record.length()-1);
				data = record.split(",");
				productId[i] = data[1];
				links[i] = "https://www.amazon.com/dp/" + products.get(productId[i]);
				i++;
			}
			
			fis.close();br.close();
			
			fis = new FileInputStream("/usr/local/ops/part-00001");
			br = new BufferedReader(new InputStreamReader(fis));
			while((strLine = br.readLine()) != null) {
				record = strLine.substring(7);
				record = record.substring(0, record.length()-1);
				data = record.split(",");
				productId[i] = data[1];
				links[i] = "https://www.amazon.com/dp/" + products.get(productId[i]);
				i++;
			}
			
			fis.close();br.close();
		
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return links;
		
		
	}

}
