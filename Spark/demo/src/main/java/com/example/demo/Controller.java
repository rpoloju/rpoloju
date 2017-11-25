package com.example.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Controller {

	
	@RequestMapping(value = "/append", method = RequestMethod.POST)
	public String apppend(Payload payload) throws IOException {
		
		DataParser dp = new DataParser();
		HtmlParser hp = new HtmlParser();
		String[] linksString;
		StringBuffer links = new StringBuffer();

		FileInputStream in = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		int userid;

		//String[] productIds = { "B000ENUC3S", "B007JFMH8M", "B005K4Q4LK", "B000CNB4LE", "B0051COPH6", "B006MONQMC",
		//		"B003QNJYXM", "B001RVFERK" };
		
		String[] productNums = { "8719", "74010", "71102", "42826", "6687", "63528", "42282", "30806" };

		String[] rating = { payload.getapplepierating(), payload.getbabygourmetrating(), payload.getcapuccinorating(),
				payload.getcoconutwaterrating(), payload.getenergydrinkrating(), payload.getfruitpunchrating(),
				payload.getoatmealcookiesrating(), payload.getpopchipsrating() };

		try {
			in = new FileInputStream("ratings.csv");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String lastLine = "", currentLine;
			while ((currentLine = br.readLine()) != null) {
				lastLine = currentLine;
			}
			br.close();

			fw = new FileWriter("ratings.csv", true);
			bw = new BufferedWriter(fw);

			String lastLineSplit[] = new String[3];
			lastLineSplit = lastLine.split(",");
			userid = Integer.parseInt(lastLineSplit[0]) + 1;
			String[] sparkArgUserId = {Integer.toString(userid)};
			for (int i = 0; i < 8; i++) {
				String newData = Integer.toString(userid) + "," + productNums[i] + "," + rating[i];
				bw.append(newData + "\n");
			}

			bw.flush();
			
			String[] command = { "/bin/sh", "-c", "rm -r /usr/local/ops" };
			Process proc = new ProcessBuilder(command).start();
			proc.waitFor();
			
			
			StartSparkLauncher.main(sparkArgUserId);
			
		} catch (Exception e) {
			System.out.println("Exception");
		} finally {

			try {
				if (in != null)
					in.close();
				if (fw != null)
					fw.close();
				if (bw != null)
					bw.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		linksString = dp.parseData();
		
		links.append("<html>");
		links.append("<h3>These are the recommended products for you...<h3>");
		links.append("<br/>");
		
		
		String title;
		for (int i=0;i<10;i++) {
			title = hp.pageTitle(linksString[i]);
			links.append("<a href=" + linksString[i] + ">" + "  " + title + "</a>");
			links.append("<br/>");
		}
		
		links.append("</html>");
				
		return links.toString();
	}

	/*@RequestMapping(value = "/spark", method = RequestMethod.GET)
	public String sparkExecutor() throws Exception {
		StartSparkLauncher.main(null);
		return "spark";
	}*/
}
