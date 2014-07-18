package by.epam.model.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import by.epam.constants.LogConstants;
import by.epam.constants.ProgramConstants;
import by.epam.logs.MyLogWriter;
import by.epam.model.core.ConfigFile;


public class ConfigFileImpl {
	
	private static final Logger LOG = Logger.getLogger(ConfigFileImpl.class);
	
	private ConfigFile configFile;
	private boolean isStarted = false;

	public ConfigFileImpl() {
		LOG.info(LogConstants.READ_CONFIG_PROPERTY);
		MyLogWriter.writeLog(LogConstants.READ_CONFIG_PROPERTY);
		if (!isStarted) {
			LOG.info(LogConstants.IMPL_CONFIG_FILE);
			MyLogWriter.writeLog(LogConstants.IMPL_CONFIG_FILE);
			Properties props = new Properties();
			try {
				props.load(getClass().getResourceAsStream("/config.property"));
			} catch (FileNotFoundException e) {
				LOG.error("The configation file not found!", e);
				MyLogWriter.writeLog("The configation "
						+ "file not found! :  " + e);
				LOG.error("\t\tCreate default configuration!!!");
				MyLogWriter.writeLog("\t\tCreate default configuration!!!");
				e.printStackTrace();
			} catch (IOException e) {
				LOG.error("Problem in IO while the "
						+ "configurational file was read", e);
				MyLogWriter.writeLog("Problem in IO while the " +
						"configurational file was read : " + e);
				LOG.error("\t\tCreate default configuration!!!");
				System.out.println("\t\tCreate default configuration!!!");
				MyLogWriter.writeLog("\t\tCreate default configuration!!!");
				e.printStackTrace();
			}
	        // if file not found - we use default parameters 
			// from ProgramConstants class
			configFile = new ConfigFile(
					Integer.valueOf(props.getProperty(ProgramConstants
							.CONFIG_STORIES_NUMBER, ProgramConstants
							.CONFIG_DEFAULT_STORIES_NUMBER)), 
					Integer.valueOf(props.getProperty(ProgramConstants
							.CONFIG_EVEVATOR_CAPACITY, ProgramConstants
							.CONFIG_DEFAULT_EVEVATOR_CAPACITY)),
					Integer.valueOf(props.getProperty(ProgramConstants
							.CONFIG_PASSENGERS_NUMBER, ProgramConstants
							.CONFIG_DEFAULT_PASSENGERS_NUMBER)),
					Integer.valueOf(props.getProperty(ProgramConstants
							.CONFIG_ANIMATION_BOOTS, ProgramConstants
							.CONFIG_DEFAULT_ANIMATION_BOOTS)));
			isStarted = true;			
		}
	}
	
	public ConfigFile getConfigParametrs() {
		return this.configFile;
	}
	
}
