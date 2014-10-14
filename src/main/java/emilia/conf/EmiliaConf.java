package emilia.conf;

import emilia.Constants;
import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

@XmlRootElement(name = Constants.TAG_EMILIA)
public class EmiliaConf {
	
	private static final Logger	logger	= LoggerFactory
																					.getLogger(EmiliaConf.class);
	
	private String							eventClassifierClass;
	
	private String							normRecognitionClass;
	
	private String							normAdoptionClass;
	
	private String							normSalienceClass;
	
	private String							normEnforcementClass;
	
	private String							normComplianceClass;
	
	private String							normativeBoardClass;
	
	
	/**
	 * Process XML configuration file
	 * 
	 * @param xmlFilename
	 *          XML configuration file
	 * @param xsdFilename
	 *          XSD configuration filename
	 * @return Configuration information
	 */
	public static EmiliaConf getConf(String xmlFilename, String xsdFilename) {
		
		EmiliaConf emiliaConf = new EmiliaConf();
		
		File xmlFile = new File(xmlFilename);
		File xsdFile = new File(xsdFilename);
		
		if((xmlFile.exists()) && (xsdFile.exists())) {
			
			if(isValid(xmlFilename, xsdFilename)) {
				
				try {
					JAXBContext jaxbContext = JAXBContext.newInstance(EmiliaConf.class);
					Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
					
					File XMLFile = new File(xmlFilename);
					
					if(XMLFile.exists()) {
						emiliaConf = (EmiliaConf) jaxbUnmarshaller.unmarshal(XMLFile);
					}
					
				} catch(JAXBException e) {
					logger.debug(e.getMessage());
				}
				
			}
		}
		
		return emiliaConf;
	}
	
	
	/**
	 * Check the validity of the XML file against an XSD file
	 * 
	 * @param xmlFilename
	 *          XML filename
	 * @param xsdFilename
	 *          XSD filename
	 * @return True if valid, False otherwise
	 */
	public static Boolean isValid(String xmlFilename, String xsdFilename) {
		Boolean valid = false;
		
		try {
			SchemaFactory factory = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new StreamSource(xsdFilename));
			
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(xmlFilename));
			
			valid = true;
			
		} catch(SAXException e) {
			logger.debug(e.getMessage());
		} catch(Exception e) {
			logger.debug(e.getMessage());
		}
		
		return valid;
	}
	
	
	public String getEventClassifierClass() {
		return this.eventClassifierClass;
	}
	
	
	@XmlElement(name = Constants.TAG_EVENT_CLASSIFIER)
	public void setEventClassifierClass(String eventClassifierClass) {
		this.eventClassifierClass = eventClassifierClass;
	}
	
	
	public String getNormRecognitionClass() {
		return this.normRecognitionClass;
	}
	
	
	@XmlElement(name = Constants.TAG_NORM_RECOGNITION)
	public void setNormRecognitionClass(String normRecognitionClass) {
		this.normRecognitionClass = normRecognitionClass;
	}
	
	
	public String getNormAdoptionClass() {
		return this.normAdoptionClass;
	}
	
	
	@XmlElement(name = Constants.TAG_NORM_ADOPTION)
	public void setNormAdoptionClass(String normAdoptionClass) {
		this.normAdoptionClass = normAdoptionClass;
	}
	
	
	public String getNormSalienceClass() {
		return this.normSalienceClass;
	}
	
	
	@XmlElement(name = Constants.TAG_NORM_SALIENCE)
	public void setNormSalienceClass(String normSalienceClass) {
		this.normSalienceClass = normSalienceClass;
	}
	
	
	public String getNormEnforcementClass() {
		return this.normEnforcementClass;
	}
	
	
	@XmlElement(name = Constants.TAG_NORM_ENFORCEMENT)
	public void setNormEnforcementClass(String normEnforcementClass) {
		this.normEnforcementClass = normEnforcementClass;
	}
	
	
	public String getNormComplianceClass() {
		return this.normComplianceClass;
	}
	
	
	@XmlElement(name = Constants.TAG_NORM_COMPLIANCE)
	public void setNormComplianceClass(String normComplianceClass) {
		this.normComplianceClass = normComplianceClass;
	}
	
	
	public String getNormativeBoardClass() {
		return this.normativeBoardClass;
	}
	
	
	@XmlElement(name = Constants.TAG_NORMATIVE_BOARD)
	public void setNormativeBoardClass(String normativeBoardClass) {
		this.normativeBoardClass = normativeBoardClass;
	}
}