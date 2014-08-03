Extended EMIL-A
===============

Instructions in Linux to download and run the test examples created to validate each component of the architecture

1. Software Requisites
  Git
  Maven
  Java SE 8 (If this is a problem, I will need to do some small adjustments to work with Java 7)

2. Download project from Git

$ git clone git@github.com:gnardin/emilia.git

3. Compile and install the project

$ cd emilia
$ mvn compile

4. Test it

$ mvn test
