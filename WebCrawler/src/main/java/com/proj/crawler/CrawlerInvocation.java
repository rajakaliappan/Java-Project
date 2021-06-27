package com.proj.crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CrawlerInvocation {
	String result = "";
	InputStream inputStream;
	String strLinks = null;
	String strSearchWord = null;
	String strOutputFile = null;
    static Logger logger = Logger.getLogger("MyLog");
    
	public static void configureLog() {    
    	logger = LogConfig.configureLog();
	}
	
	public void getPropValues() throws IOException {

		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";

			File jarPath = new File(CrawlerInvocation.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			String propertiesPath = jarPath.getParentFile().getAbsolutePath();
			System.out.println("propertiesPath" + propertiesPath);
			prop.load(new FileInputStream(propertiesPath + "/config.properties"));
			
			// get the property value and print it out
			strLinks = prop.getProperty("links");
			strSearchWord = prop.getProperty("word");
			logger.info("To search for word " + strSearchWord);
			strOutputFile = prop.getProperty("outputfile");
			logger.info("Search results will be updated in " + strOutputFile);

			if (null == strOutputFile || "".equalsIgnoreCase(strOutputFile)) {
				logger.severe("Output file is empty");
				throw new Exception("Output file is empty");
			}
		} catch (Exception e) {
			logger.severe("Exception in reading properties file");
			System.exit(0);
		} finally {
			if (null != inputStream)
				inputStream.close();			
		}
	}

	public static void main(String[] args) {
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
		
		for (String strLink : strTokens)
			bwc.getPageLinks(strLink, 0,logger);

		bwc.getArticles(crawler.strSearchWord,logger,crawler.strOutputFile);

	}

}
