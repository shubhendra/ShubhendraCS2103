package parser;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Main {
    private static Logger logger = Logger.getLogger(Main.class);
    
    public static void main(String[] args) {
    //    System.out.println("coming here");
        long time = System.currentTimeMillis();
        logger.info("main method called..");
        logger.info("another informative message");
        logger.warn("This one is a warning!");
        logger.log(Level.TRACE, 
                "And a trace message using log() method.");
        long logTime = System.currentTimeMillis() - time;
        
        logger.debug("Time taken to log the previous messages: " 
                + logTime + " msecs");

        // Exception logging example:
        try{
            //String subs = "hello".substring(2);
            long logTime1 = System.currentTimeMillis() - time;
            logger.error(
            		"Time taken to log the previous messages: " 
                    + logTime1 + " msecs");
        }catch (Exception e){
            logger.error("Error in main() method:", e);
        }      
               
    }
}
