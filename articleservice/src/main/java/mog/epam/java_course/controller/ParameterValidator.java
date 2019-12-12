package mog.epam.java_course.controller;

import mog.epam.java_course.service.impl.MyHttpClient;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ParameterValidator {
    private static final Logger logger = LoggerFactory.getLogger(MyHttpClient.class);
    static{
        PropertyConfigurator.configure("src/logger.properties");
    }

    /**
     * Validates the input parameters. If the first argument(type of method)
     * is not GET or POST returns false. If the second argument(id of the publication)
     * is less than zero returns false. If the third argument(type of client)
     * is not HTTP or URL returns false. With another input returns true.
     * @param args array contains arguments for validation
     * @return boolean value if arguments are valid
     */
    public static boolean validate(String[] args) {
        if((args.length != 3)) {
            logger.warn("Wrong number of arguments");
            throw new ValidatorException("Wrong number of arguments");
        }
        validateForNull(args);
        validateMethodName(args[0]);
        validateId(args[1]);
        validateClientType(args[2]);
        return true;
    }

     /**
      * Validate transfered String for compliance with the given values. If it's not valid throw ValidatorException
      * @param methodName the string for validation
      */
    private static void validateMethodName(String methodName) {
        if(!(methodName.equals("GET") | methodName.equals("POST"))) {
            logger.warn("Wrong 1 argument");
            throw new ValidatorException("Wrong 1 argument");
        }
    }

     /**
      * Checks if the value is in the specified range. If it's not valid throw ValidatorException
      * @param id the string for validation
      */
     private static void validateId(String id) {
         if(Integer.parseInt(id) < 0) {
             logger.warn("Wrong 2 argument");
             throw new ValidatorException("Wrong 2 argument");
         }
     }

     /**
      * Validate transfered String for compliance with the given values. If it's not valid throw ValidatorException
      * @param clientType the string for validation
      */
     private static void validateClientType(String clientType) {
         if(!(clientType.equals("URL") | clientType.equals("HTTP"))) {
             logger.warn("Wrong 3 argument");
             throw new ValidatorException("Wrong 3 argument");
         }
     }

    /**
     * Validate transfered String array for null. If it's detected throws ValidatorException
     * @param args an string array contains arguments for validation
     */
    private static void validateForNull(String[] args) {
        for (String arg : args) {
            if (arg == null){
                logger.warn("Arguments array contains null!");
                throw new ValidatorException("Arguments array contains null!");
            }
        }
    }

}
