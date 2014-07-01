package emilia.conf;

import emilia.conf.EmiliaConf.Param;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class EmiliaConfParser {
	
	private static final Logger			logger		= LoggerFactory
																								.getLogger(EmiliaConfParser.class);
	
	// Configuration parser instance
	private static EmiliaConfParser	instance	= new EmiliaConfParser();
	
	
	/**
	 * Create a configuration parser
	 * 
	 * @param none
	 * @return none
	 */
	private EmiliaConfParser() {
	}
	
	
	/**
	 * Get the configuration parser instance
	 * 
	 * @param none
	 * @return Configuration parser
	 */
	public static EmiliaConfParser getInstance() {
		return instance;
	}
	
	
	/**
	 * Get the configuration information
	 * 
	 * @param xmlFilename
	 *          XML filename
	 * @param xsdFilename
	 *          XSD configuration
	 * @return Configuration parameters and values
	 */
	public EmiliaConf getConf(String xmlFilename, String xsdFilename) {
		EmiliaConf conf = new EmiliaConf();
		
		if (isValid(xmlFilename, xsdFilename)) {
			try {
				// First create a new XMLInputFactory
				XMLInputFactory inputFactory = XMLInputFactory.newInstance();
				
				// Setup a new eventReader
				InputStream in = new FileInputStream(xmlFilename);
				XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
				
				// Read the XML document
				XMLEvent event;
				StartElement startElement;
				while(eventReader.hasNext()) {
					event = eventReader.nextEvent();
					
					if (event.isStartElement()) {
						startElement = event.asStartElement();
						
						// Set eventClassifierClass
						if (startElement.getName().getLocalPart()
								.equals(Param.EVENT_CLASSIFIER_CLASS.getName())) {
							event = eventReader.nextEvent();
							conf.setValue(Param.EVENT_CLASSIFIER_CLASS, event.asCharacters()
									.getData());
							continue;
							
							// Set normRecognitionClass
						} else if (startElement.getName().getLocalPart()
								.equals(Param.NORM_RECOGNITION_CLASS.getName())) {
							event = eventReader.nextEvent();
							conf.setValue(Param.NORM_RECOGNITION_CLASS, event.asCharacters()
									.getData());
							continue;
							
							// Set normAdoptionClass
						} else if (startElement.getName().getLocalPart()
								.equals(Param.NORM_ADOPTION_CLASS.getName())) {
							event = eventReader.nextEvent();
							conf.setValue(Param.NORM_ADOPTION_CLASS, event.asCharacters()
									.getData());
							continue;
							
							// Set normSalienceClass
						} else if (startElement.getName().getLocalPart()
								.equals(Param.NORM_SALIENCE_CLASS.getName())) {
							event = eventReader.nextEvent();
							conf.setValue(Param.NORM_SALIENCE_CLASS, event.asCharacters()
									.getData());
							continue;
							
							// Set normEnforcementClass
						} else if (startElement.getName().getLocalPart()
								.equals(Param.NORM_ENFORCEMENT_CLASS.getName())) {
							event = eventReader.nextEvent();
							conf.setValue(Param.NORM_ENFORCEMENT_CLASS, event.asCharacters()
									.getData());
							continue;
							
							// Set normComplianceClass
						} else if (startElement.getName().getLocalPart()
								.equals(Param.NORM_COMPLIANCE_CLASS.getName())) {
							event = eventReader.nextEvent();
							conf.setValue(Param.NORM_COMPLIANCE_CLASS, event.asCharacters()
									.getData());
							continue;
							
							// Set normativeBoardClass
						} else if (startElement.getName().getLocalPart()
								.equals(Param.NORMATIVE_BOARD_CLASS.getName())) {
							event = eventReader.nextEvent();
							conf.setValue(Param.NORMATIVE_BOARD_CLASS, event.asCharacters()
									.getData());
							continue;
						}
					}
				}
				
				// Close the file
				in.close();
				
			} catch(FileNotFoundException e) {
				logger.error(e.getMessage());
			} catch(IOException e) {
				logger.error(e.getMessage());
			} catch(XMLStreamException e) {
				logger.error(e.getMessage());
			}
		} else {
			logger.debug("XML file [" + xmlFilename + "] is invalid");
		}
		
		return conf;
	}
	
	
	/**
	 * Validates a XML file based on a XSD Schema
	 * 
	 * @param xmlFilename
	 *          XML filename
	 * @param xsdFilename
	 *          XSD filename
	 * 
	 * @return True if valid, False otherwise
	 */
	private Boolean isValid(String xmlFilename, String xsdFilename) {
		Boolean result = false;
		
		if ((new File(xmlFilename)).exists() && ((new File(xsdFilename).exists()))) {
			try {
				Source xmlFile = new StreamSource(new File(xmlFilename));
				
				// XMLConstants.W3C_XML_SCHEMA_NS_URI =
				// "http://www.w3.org/2001/XMLSchema"
				SchemaFactory schemaFactory = SchemaFactory
						.newInstance("http://www.w3.org/2001/XMLSchema");
				Schema schema = schemaFactory.newSchema(new File(xsdFilename));
				
				Validator validator = schema.newValidator();
				validator.validate(xmlFile);
				
				result = true;
			} catch(IOException e) {
				logger.error(e.getMessage());
			} catch(SAXException e) {
				logger.error(e.getMessage());
			}
		}
		
		return result;
	}
}