# Library-RDMS


## CS 430 : Assignments

### Lab Assignment 5: Creating a GUI interface to a SQL Database

The purpose of this lab is for you:

*   To gain more experience with using a higher level language interface with SQL;
*   To create a user interface that connects to your Lab 4 database.

### Description of the Task for Lab 10

The database used by this program is the same as the one used after running your Lab 4 Assignment. The purpose of this part of the assignment is to write a dynamic program based on responses of the student and input from the database.  

#### Activities

1.  Create GUI that will work with the database 

    2.  It should first ask for the members id and verify that the member has a valid entry. If the id is not currently in the system, it should ask the questions to add the member. It should have a termination condition here as well.
    3.  It should then ask for the book they want to check out. This can be done one of three ways.
        1.  ISBN
        2.  Name - this can be a partial name, if more than one name matches, allow the user to select.
        3.  Author, then choosing from a list of books by that author
    4.  If the libraries have the book and there are copies available, the program should print a message telling the member what library and shelf the book is on (there may be more than one).
    5.  If either library has the book and all copies are checked out, the program should print a message to the member that all copies are currently checked out.
    6.  If neither library has the book, a message telling the member that the library does not currently have the book in stock.
    7.  Note: this system is providing info only, do not check the book out as a part of these actions.
    8.  Loop back to ask for the next member's id  

2.  You may use any windowing package you choose to implement your GUI. You MUST ensure this code will run on the state capital machines talking to faure so the TA can grade your code. [Here](http://www.cs.colostate.edu/~cs430dl/yr2018sp/more_assignments/LabData/Lab4B_ex.java) is a simple example of a program that does windowing. [Here](http://www.cs.colostate.edu/~cs430dl/yr2018sp/more_assignments/LabData/ssh_tunnel.pdf) is a writeup for doing port forwarding on Windows and [here](http://ubuntuguide.org/wiki/Using_SSH_to_Port_Forward) is one for Ubuntu.

 ### Compile
 
 This package includes the following files:

* Lab10Myers.java [Program used to run block and stream cipher alogrithms]
* Makefile [For compiling, and cleaning]
* README.md [This file]


#### Compile and build the executable using the following command:

systemprompt>  make

To remove the  executable files use:

systemprompt>  make clean

To package the program in tar format: 

systemprompt> make package

To run the program use with a input options:

systemprompt> ./Lab10



  


