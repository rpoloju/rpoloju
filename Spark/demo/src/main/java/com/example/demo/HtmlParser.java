package com.example.demo;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlParser {
	
	public String pageTitle(String args) throws IOException {
		Connection conn = Jsoup.connect(args);
		if (conn != null) {
			Document doc = conn.get();
			return doc.title();
		}
		return "";
		
	}

}
