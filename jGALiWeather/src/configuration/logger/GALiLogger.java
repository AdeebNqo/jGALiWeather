package configuration.logger;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/*  This module implements a logging class which adds
    logging capabilities to GALiWeather. Currently, the
    logs are both written into external text files and
    printed on the command line screen.

    It uses class methods in order to implements a 
    logging class which adds logging capabilities to 
    provide logging global access to every module in 
    GALiWeather.
 */
public class GALiLogger {

    private Logger logger;

    /*
        Initializes the logger and adds two handlers which
        perform on-screen logging and write the log messages
        into an external file, stored in *log_directory*.

        :param log_directory: A valid string path to a directory,
        where the log will be stored
     */
    public GALiLogger(String log_directory) {

        LogManager.getLogManager().reset(); //Disables the default console handler

        logger = Logger.getLogger("GALiLogger");

        try {
            SimpleFormatter formatter = new SimpleFormatter();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            FileHandler fh;

            ConsoleHandler ch = new ConsoleHandler();
            ch.setFormatter(formatter);
            ch.setLevel(Level.INFO);
            logger.addHandler(ch);

            if (new File(log_directory).isDirectory()) {
                fh = new FileHandler(log_directory + "/GALiWeather_Log_" + dateFormat.format(cal.getTime()) + ".log");
                fh.setFormatter(formatter);
                logger.addHandler(fh);
            } else {
                fh = new FileHandler("GALiWeather_Log_" + dateFormat.format(cal.getTime()) + ".log");
                fh.setFormatter(formatter);
                logger.addHandler(fh);
                logger.warning("Directory " + log_directory + " not found, log file created in current working directory");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Logger getLogger() {
        return logger;
    }
}
