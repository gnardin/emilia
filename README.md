Extended EMIL-A
===============

1. User's Manual
================
This section describes the procedure for installing, configuring, and using the EMIL-A Normative Architecture.

Downloading, compiling and testing
----------------------------------
EMIL-A is written in Java 8 (Oracle Java JDK 1.8), thus it can be run on any Java-supported operating system. It is built and managed using Maven, which simplifies the compilation, testing, and installation of the component. The source code is available for download at https://github.com/gnardin/emilia, which requires the following software to install and run:

* Git (https://git-scm.com/) – a free and open source distributed version control system
*	Maven (http://maven.apache.org/) – a comprehensive software project management tool used to manage project’s build, reporting and documentation from a central piece of information
* Java (http://www.oracle.com/technetwork/java/javase/overview/index.html) – a language and a platform to develop and deploy application

EMIL-A uses a number of existing standard libraries that Maven automatically downloads during the compilation phase; therefore, an Internet connection must be available during the installation phase. The Linux instructions to download, compile, and install EMIL-A are:

| $ git clone git@github.com:gnardin/emilia.git  
| $ cd emilia  
| $ mvn clean  
| $ mvn compile  
| $ mvn install  
| $ mvn test  

The outcome of a successful test execution should look

| [INFO] --------------------------------------------------------------  
| [INFO] BUILD SUCCESS  
| [INFO] --------------------------------------------------------------  
| [INFO] Total time: 3.470s  
| [INFO] Finished at: Wed Aug 26 11:28:50 CEST 2015  
| [INFO] Final Memory: 8M/120M  
| [INFO] --------------------------------------------------------------  

Usage
-----
EMIL-A is a normative software component that implements a normative architecture. EMIL-A is a very flexible component that allows the customisation of all of its normative modules as they are instantiated at run-time. The flexibility of EMIL-A lies in the possibility of allowing its modules to be defined in a configuration XML (eXtensible Markup Language) file, the schema specification of which is provided in the XSD (XML Schema Definition) file <INSTALLDIR>/emilia/src/main/resources/conf/emilia.xsd. The following listing shows an example of configuration file, whose tags are describe in Table 5.1.

| <?xml version="1.0" encoding="UTF-8"?>  
| <emilia xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="emilia.xsd">  
| <eventClassifierClass>emilia.defaultImpl.modules.classifier.EventClassifier</eventClassifierClass>  
| <normRecognitionClass>emilia.defaultImpl.modules.recognition.NormRecognitionController</normRecognitionClass>  
| <normAdoptionClass>emilia.defaultImpl.modules.adoption.NormAdoptionController</normAdoptionClass>  
| <normSalienceClass>emilia.defaultImpl.modules.salience.NormSalienceController</normSalienceClass>  
| <normEnforcementClass>emilia.defaultImpl.modules.enforcement.NormEnforcementController</normEnforcementClass>  
| <normComplianceClass>emilia.defaultImpl.modules.compliance.NormComplianceController</normComplianceClass>  
| <normativeBoardClass>emilia.defaultImpl.board.NormativeBoard</normativeBoardClass>  
| </emilia>  


Examples
========

Public-Goods Game (pgg)
-----------------------
Implements a simple Public-Goods Game in which the individuals that do not contribute to the common poll can be sanctioned by those that contributed. The selection between different types of sanctions is done randomly.



Prisoner Dilemma Game with Sanction (ijcai11)
---------------------------------------------
Reproduces the scenarios described in the paper

Villatoro, D.; Andrighetto, G.; Sabater-Mir, J.; Conte, R. (2011_ Dynamic Sanctioning for Robust and Cost-Efficient Norm Compliance. In Proceedings of the Twenty-Second Internation Joint Conference on Artificial Intelligence (IJCAI 2011).
