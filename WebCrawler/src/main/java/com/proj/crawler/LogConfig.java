package com.proj.crawler;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogConfig {
    static Logger logger = Logger.getLogger("MyLog");
    
	public static Logger configureLog() {    
	    FileHandler fh;  
	
	    try { 	
	        // This block configure the logger with handler and formatter  
	        fh = new FileHandler("MyLogFile%g.log", 1024, 5, true);  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);
	    } catch (SecurityException e) {  
	    	logger.severe("Exception with Loggin" + e);
	    	e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }
		return logger;  
	}
}

