package com.proj.crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class CrawlerInvocation {
	String result = "";
	InputStream inputStream;
	String strLinks = null;
	String strSearchWord = null;
	String strOutputFile = null;
    static Logger logger;
    
	public static void configureLog() {    
    	logger = LogConfig.configureLog();
	}
	
	public void getPropValues() throws IOException {

		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";

			File jarPath = new File(CrawlerInvocation.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			String propertiesPath = jarPath.getParentFile().getAbsolutePath();
			logger.info("properties file path " + propertiesPath);
			prop.load(new FileInputStream(propertiesPath + "/config.properties"));
			
			// get the property value and print it out
			strLinks = prop.getProperty("links");
			strSearchWord = prop.getProperty("word");
			logger.info("To search for text " + strSearchWord);
			strOutputFile = prop.getProperty("outputfile");
			logger.info("Search results will be updated in " + strOutputFile);

			if (null == strLinks || "".equalsIgnoreCase(strLinks)) {
				logger.severe("URL is not provided");
				throw new Exception("URL to crawl is not provided");
			}
		
			if (null == strSearchWord || "".equalsIgnoreCase(strSearchWord)) {
				logger.severe("text to search is empty");
				throw new Exception("Text to search is not provided");
			}
			
			if (null == strOutputFile || "".equalsIgnoreCase(strOutputFile)) {
				logger.severe("Output file is empty");
				throw new Exception("Output file is empty");
			}
		} catch (Exception e) {
			logger.severe("Exception in reading properties file " + e.getMessage());
			System.exit(0);
		} finally {
			if (null != inputStream)
				inputStream.close();			
		}
	}

	public static void main(String[] args) {		
		invokeCrawler();
	}
	
	public static void invokeCrawler() {		
		CrawlerInvocation crawler = new CrawlerInvocation();
		//configure logging
		configureLog();
		logger.info("Crawler Wrapper Initated");
		try {
			//Read properties file
			crawler.getPropValues();
		} catch (IOException e) {
			e.printStackTrace();
			logger.severe("Crawler Wrapper Failed during property setup " + e);
		}

		// Invoke the Crawler
		Extractor bwc = new Extractor();
		// Get the links to search
		String[] strTokens = crawler.strLinks.split(",");

		logger.info("Before invocation of crawler");

		Stream<String> stream = Arrays.asList(strTokens).stream();
		stream.forEach(strLink -> bwc.getPageLinks(strLink, 0,logger));
		
		logger.info("Before searching and storing the results for given text");
		
		bwc.getArticles(crawler.strSearchWord,logger,crawler.strOutputFile);

		logger.info("Searched completed");
	}
	

}