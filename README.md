Extended EMIL-A
===============

(Linux) Instructions to download and run the tests created to validate each EMIL-A Normative Architecture's component (Norm Recognition, Norm Adoption, Norm Salience, Norm Enforcement, and Norm Compliance)

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


Examples
========

Public-Goods Game (pgg)
-----------------------
Implements a simple Public-Goods Game in which the individuals that do not contribute to the common poll can be sanctioned by those that contributed. The selection between different types of sanctions is done randomly.



Prisoner Dilemma Game with Sanction (ijcai11)
---------------------------------------------
Reproduces the scenarios described in the paper

Villatoro, D.; Andrighetto, G.; Sabater-Mir, J.; Conte, R. (2011_ Dynamic Sanctioning for Robust and Cost-Efficient Norm Compliance. In Proceedings of the Twenty-Second Internation Joint Conference on Artificial Intelligence (IJCAI 2011).
