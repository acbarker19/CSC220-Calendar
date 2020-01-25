/*
    Calendar Project
    Alec Barker
    University of Mount Union CSC 220
    5/2/18
    
    Project Requirements: http://raider.mountunion.edu/csc/CSC220/Spring2018/Projects/calendar/index.html
                          http://raider.mountunion.edu/csc/CSC220/Spring2018/Projects/calendar2/index.html

    Project Description: This project allows a user to input the name of a text
        file and a year, and select different options to display a unique
        calendar. The user can view the first day of every month, the first and
        last days of every month, a monthly format (similar to the way calendars
        are usually displayed), a list of events that were stored on the user's
        text file, a monthly format with a list of events that were stored
        on the user's text file and stars next to the days that the events
        occur, an HTML calendar using <pre>, an HTML calendar using tables, an
        HTML calendar using tables and titles for the events, and an HTML
        calendar using tables and links for the events.

        The user's text file must be located in the project's /src/inputFiles
        folder, and it must have the extension type listed.
        The events need to be formatted in the text file as follows:

        year#month(0-11)#day#title#description#location#time#contact info

        This CalendarFrame acts as the main class of the project. It collects
        the name of the user's text file, the year, and the combo box option,
        and runs various methods based which data is selected and present.
        The name of the user's text file is entered into the EventList class,
        which extracts the data and creates an ArrayList of Event objects that
        can be manipulated and displayed individually. The Utility and
        MyCalendar classes located in the helpers package are used to read the
        user's text file and determine information about dates, respectively.
        Using the File and FileWriter Java libraries, a new file will be created
        that is named after what the user writes in the outputField. These files
        will be saved to the src/outputFiles folder. They can be accessed
        individually or through the web calendar created with the "HTML Using
        Table with Links" option.
*/
package main;

import helpers.MyCalendar;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CalendarFrame extends javax.swing.JFrame {

    private String line = "==========================\n";
    
    public CalendarFrame() {
        initComponents();
        
        setSize(700, 500);
        
        //Adds items to the combo box
        comboBox.removeAllItems();
        comboBox.addItem("First Day of the Month");
        comboBox.addItem("First and Last Day of the Month");
        comboBox.addItem("Monthly Format");
        comboBox.addItem("Events");
        comboBox.addItem("Monthly Format with Events");
        comboBox.addItem("HTML Using Pre");
        comboBox.addItem("HTML Using Table");
        comboBox.addItem("HTML Using Table with Titles");
        comboBox.addItem("HTML Using Table with Links");
        
        /*
        inputField.setText("eventFile.txt");
        outputField.setText("eventCalendar.html");
        yearField.setText("2018");
        */
        
        textArea.setFont(new Font("MonoSpaced", Font.PLAIN, 12));
        
        textArea.setText("Please enter a year, an input file (must include the"
                + " phrase \".txt\" after the file name), and\nan output file"
                + " (must include the phrase \".html\" after the file name)."
                + "\nAlso, make sure the input file is located inside the"
                + " /src/inputFiles folder.");
    }
    
    //Lists the first day of every month in the entered year
    public void doFirstDayOfMonth(){
        String answer = "Displaying The First Day of Each Month During " + getYear() + "\n\n";
        
        MyCalendar myCalendar;
        
        int year = getYear();
        for(int month = 0; month < 12; month++){
            myCalendar = new MyCalendar(year, month, 1);
            answer += myCalendar.getMonthString() + " 1, " + getYear() + " is on a " + myCalendar.getDayOfWeekString() + "\n";
        }
        
        textArea.setText("");
        textArea.append(answer);
    }
    
    //Lists the first and last days of every month in the entered year
    public void doFirstAndLastDayOfMonth(){
        String answer = "Displaying The First and Last Day of Each Month During " + getYear() + "\n\n";
        
        MyCalendar myCalendar;
        
        int year = getYear();
        
        for(int month = 0; month < 12; month++){
            int lastDay = getLastDayOfMonth(month);
            myCalendar = new MyCalendar(year, month, 1);
            
            //First day
            answer += myCalendar.getMonthString() + " 1, " + getYear() + " is on a " + myCalendar.getDayOfWeekString() + "\n";
            
            //Last day
            myCalendar.setDay(lastDay);
            answer += myCalendar.getMonthString() + " " + lastDay + ", " + getYear() + " is on a " + myCalendar.getDayOfWeekString() + "\n\n";
        }
        
        textArea.setText("");
        textArea.append(answer);
    }
    
    //Draws a calendar as they normally appear
    public void doMonthlyFormat(){
        String answer = "Displaying " + getYear() + "\n\n";
        
        MyCalendar myCalendar;
        
        int year = getYear();
        
        //Runs through each month
        for(int month = 0; month < 12; month++){
            myCalendar = new MyCalendar(year, month, 1);
            answer += myCalendar.getMonthString() + "\nSu  M   T   W   Th  F   Sa\n";
            
            int lastDay = getLastDayOfMonth(month);
            myCalendar.setDay(lastDay);
            
            //Counts how many days have been displayed during the week. This is
            //used so that there are only 7 days per row in the calendar.
            int dayOfWeekCounter = 0;
            
            //This int is used to determine which day the 1st of each month is
            //on, and it will create the correct number of blank spaces to move
            //the 1st day to the correct position.
            int numberOfSpaces = getDayOfWeekInt(month, 1);               
            for(int spaces = 0; spaces <= numberOfSpaces; spaces++){
                if(spaces != 0){
                    answer += "    ";
                }
                dayOfWeekCounter++;
            }
            
            //Runs through each day, starting at 1 and ending at the last day
            //of each month
            for(int day = 1; day <= lastDay; day++){
                //If there are less than 8 days in the current row, it writes
                //the date and adds spaces in between
                if(dayOfWeekCounter < 8){
                    //If the date has only one digit, it needs an extra space to
                    //align everything properly
                    if(day < 10){
                        answer += day + "   ";
                    }
                    else{
                        answer += day + "  ";
                    }
                    dayOfWeekCounter++;
                }
                //If 8 days are reached in the current row, it starts a new
                //line, it writes the date, and adds spaces in between
                else{
                    //If the date has only one digit, it needs an extra space to
                    //align everything properly
                    if(day < 10){
                        answer += "\n" + day + "   ";
                    }
                    else{
                        answer += "\n" + day + "  ";
                    }
                    //Resets the counter. Unsure why it requires 2 and not 0;
                    dayOfWeekCounter = 2;
                }
            }
            
            answer += "\n";
            //Adds a line between each month
            if(month < 11){
                answer += line;
            }
        }
        
        textArea.setText("");
        textArea.append(answer);
    }
    
    //Lists the events that occur in the entered year
    public void doEvents(){
        EventList eventList;
        eventList = new EventList("src/inputFiles/" + inputField.getText());
        textArea.setText("Displaying Events During " + getYear() + "\n\n");
        textArea.append(eventList.toStringForYear(getYear()));
    }
    
    //Draws a calendar as they normally appear, adds stars next to event dates,
    //and lists the events below the month they occur
    public void doMonthlyFormatWithEvents(){
        String answer = "Displaying Events During " + getYear() + "\n\n";
        
        MyCalendar myCalendar;
        
        int year = getYear();
        
        EventList eventList;
        eventList = new EventList("src/inputFiles/" + inputField.getText());
        
        //Runs through each month
        for(int month = 0; month < 12; month++){
            myCalendar = new MyCalendar(year, month, 1);
            answer += myCalendar.getMonthString() + "\nSu  M   T   W   Th  F   Sa\n";
            
            int lastDay = getLastDayOfMonth(month);
            myCalendar.setDay(lastDay);
            
            //Counts how many days have been displayed during the week. This is
            //used so that there are only 7 days per row in the calendar.
            int dayOfWeekCounter = 0;
            
            //This int is used to determine which day the 1st of each month is
            //on, and it will create the correct number of blank spaces to move
            //the 1st day to the correct position.
            int numberOfSpaces = getDayOfWeekInt(month, 1);         
            for(int spaces = 0; spaces <= numberOfSpaces; spaces++){
                if(spaces != 0){
                    answer += "    ";
                }
                dayOfWeekCounter++;
            }
            
            //Runs through each day, starting at 1 and ending at the last day
            //of each month
            for(int day = 1; day <= lastDay; day++){
                //This if statement will draw the months that have events
                if(eventList.hasEventInThisMonth(getYear(), month) == true){
                    //This if statement checks if there is an event during the
                    //entered year on the specific month and day
                    if(eventList.hasEventOnThisDay(getYear(), month, day) == true){
                        //If there are less than 8 days in the current row, it
                        //write the date and adds spaces in between.
                        //If there is event on a specific day, it adds a star.
                        if(dayOfWeekCounter < 8){
                            //If the date has only one digit, it needs an extra
                            //space to align everything properly
                            if(day < 10){
                                answer += day + "*  ";
                            }
                            else{
                                answer += day + "* ";
                            }
                            dayOfWeekCounter++;
                        }
                        //If 8 days are reached in the current row, it starts a
                        //new line, it writes the date, and adds spaces in between
                        else{
                            //If the date has only one digit, it needs an extra
                            //space to align everything properly
                            if(day < 10){
                                answer += "\n" + day + "*  ";
                            }
                            else{
                                answer += "\n" + day + "* ";
                            }
                            //Resets the counter. Unsure why it requires 2 and not 0;
                            dayOfWeekCounter = 2;
                        }
                    }
                    //This else statement works if there is no event during the
                    //entered year on the specific month and day
                    else{
                        //If there are less than 8 days in the current row, it
                        //write the date and adds spaces in between.
                        if(dayOfWeekCounter < 8){
                            //If the date has only one digit, it needs an extra
                            //space to align everything properly
                            if(day < 10){
                                answer += day + "   ";
                            }
                            else{
                                answer += day + "  ";
                            }
                            dayOfWeekCounter++;
                        }
                        //If 8 days are reached in the current row, it starts a
                        //new line, it writes the date, and adds spaces in between
                        else{
                            //If the date has only one digit, it needs an extra
                            //space to align everything properly
                            if(day < 10){
                                answer += "\n" + day + "   ";
                            }
                            else{
                                answer += "\n" + day + "  ";
                            }
                            //Resets the counter. Unsure why it requires 2 and not 0;
                            dayOfWeekCounter = 2;
                        }
                    }
                }
                //This else statement will draw the months that have no events
                else{
                    //If there are less than 8 days in the current row, it
                        //write the date and adds spaces in between.
                    if(dayOfWeekCounter < 8){
                        //If the date has only one digit, it needs an extra
                        //space to align everything properly
                        if(day < 10){
                            answer += day + "   ";
                        }
                        else{
                            answer += day + "  ";
                        }
                        dayOfWeekCounter++;
                    }
                    //If 8 days are reached in the current row, it starts a
                    //new line, it writes the date, and adds spaces in between
                    else{
                        //If the date has only one digit, it needs an extra
                        //space to align everything properly
                        if(day < 10){
                            answer += "\n" + day + "   ";
                        }
                        else{
                            answer += "\n" + day + "  ";
                        }
                        //Resets the counter. Unsure why it requires 2 and not 0;
                        dayOfWeekCounter = 2;
                    }
                }
            }
            
            answer += "\n";
            
            //If there are events during that year and month, it lists them
            //below the month
            if(eventList.hasEventInThisMonth(getYear(), month) == true){
                answer += "\n" + eventList.toStringForMonth(month);
            }
            
            //Adds a line between each month
            if(month < 11){
                answer += line;
            }
        }
        
        textArea.setText("");
        textArea.append(answer);
    }
    
    //Draws a calendar as they normally appear, except adds HTML and the pre
    //tag to create the code for a webpage
    public void doHTMLUsingPre() throws IOException{
        String answer = "<!DOCTYPE html>\n<html>\n\t<head>\n\t\t<title>Calendar</title>"
                + "\n\t</head>\n\t<body>\n\t\t<pre>\nDisplaying " + getYear() + "\n\n";
        
        MyCalendar myCalendar;
        
        int year = getYear();
        
        //Runs through each month
        for(int month = 0; month < 12; month++){
            myCalendar = new MyCalendar(year, month, 1);
            answer += myCalendar.getMonthString() + "\nSu  M   T   W   Th  F   Sa\n";
            
            int lastDay = getLastDayOfMonth(month);
            myCalendar.setDay(lastDay);
            
            //Counts how many days have been displayed during the week. This is
            //used so that there are only 7 days per row in the calendar.
            int dayOfWeekCounter = 0;
            
            //This int is used to determine which day the 1st of each month is
            //on, and it will create the correct number of blank spaces to move
            //the 1st day to the correct position.
            int numberOfSpaces = getDayOfWeekInt(month, 1);               
            for(int spaces = 0; spaces <= numberOfSpaces; spaces++){
                if(spaces != 0){
                    answer += "    ";
                }
                dayOfWeekCounter++;
            }
            
            //Runs through each day, starting at 1 and ending at the last day
            //of each month
            for(int day = 1; day <= lastDay; day++){
                //If there are less than 8 days in the current row, it writes
                //the date and adds spaces in between
                if(dayOfWeekCounter < 8){
                    //If the date has only one digit, it needs an extra space to
                    //align everything properly
                    if(day < 10){
                        answer += day + "   ";
                    }
                    else{
                        answer += day + "  ";
                    }
                    dayOfWeekCounter++;
                }
                //If 8 days are reached in the current row, it starts a new
                //line, it writes the date, and adds spaces in between
                else{
                    //If the date has only one digit, it needs an extra space to
                    //align everything properly
                    if(day < 10){
                        answer += "\n" + day + "   ";
                    }
                    else{
                        answer += "\n" + day + "  ";
                    }
                    //Resets the counter. Unsure why it requires 2 and not 0;
                    dayOfWeekCounter = 2;
                }
            }
            
            answer += "\n";
            //Adds a line between each month
            if(month < 11){
                answer += line;
            }
        }
        
        answer += "\t\t</pre>\n\t</body>\n</html>";
        
        textArea.setText("");
        textArea.append(answer);
        
        printToOutput(answer, outputField.getText());
    }
    
    //Draws a calendar as they normally appear, except adds HTML and the table
    //tag to create the code for a webpage
    public void doHTMLUsingTable() throws IOException{
        String answer = "<!DOCTYPE html>\n<html>\n<head>\n\t<title>Calendar</title>"
                + "\n\t<link rel=\"stylesheet\" href=\"table_style.css\">\n</head>"
                + "\n<body>\n\t<h1><center>Displaying "
                + getYear() + "</center></h1>\n";
        
        MyCalendar myCalendar;
        
        int year = getYear();
        
        //Runs through each month
        for(int month = 0; month < 12; month++){
            myCalendar = new MyCalendar(year, month, 1);
            answer += "\t<h2>" + myCalendar.getMonthString() + "</h2>\n\t<table>\n"
                    + "\t\t<tr>\n\t\t\t<th>Su</th>\n\t\t\t<th>M</th>\n\t\t\t<th>T</th>\n\t\t\t<th>W</th>"
                    + "\n\t\t\t<th>Th</th>\n\t\t\t<th>F</th>\n\t\t\t<th>Sa</th>\n\t\t</tr>\n\t\t<tr>";
            
            int lastDay = getLastDayOfMonth(month);
            myCalendar.setDay(lastDay);
            
            //Counts how many days have been displayed during the week. This is
            //used so that there are only 7 days per row in the calendar.
            int dayOfWeekCounter = 0;
            
            //This int is used to determine which day the 1st of each month is
            //on, and it will create the correct number of blank spaces to move
            //the 1st day to the correct position.
            int numberOfSpaces = getDayOfWeekInt(month, 1);               
            for(int spaces = 0; spaces <= numberOfSpaces; spaces++){
                if(spaces != 0){
                    answer += "\n\t\t\t<td></td>";
                }
                dayOfWeekCounter++;
            }
            
            //Runs through each day, starting at 1 and ending at the last day
            //of each month
            for(int day = 1; day <= lastDay; day++){
                //If there are less than 8 days in the current row, it writes
                //the date and moves to the next table space
                if(dayOfWeekCounter < 8){
                    answer += "\n\t\t\t<td>" + day + "</td>";
                    dayOfWeekCounter++;
                }
                //If 8 days are reached in the current row, it starts a new
                //line, it writes the date, and adds spaces in between
                else{
                    answer += "\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td>" + day + "</td>";
                    //Resets the counter. Unsure why it requires 2 and not 0;
                    dayOfWeekCounter = 2;
                }
            }

            //Adds a break between each month and ends the table
            answer += "\n\t\t</tr>\n\t</table>\n";

        }
        
        answer += "</body>\n</html>";
        
        textArea.setText("");
        textArea.append(answer);
        
        printToOutput(answer, outputField.getText());
    }
    
    //Draws a calendar as they normally appear, except adds HTML and the table
    //tag to create the code for a webpage. It also includes the title of the
    //event on the day that it happens.
    public void doHTMLUsingTableWithTitles() throws IOException{
        String answer = "<!DOCTYPE html>\n<html>\n<head>\n\t<title>Calendar</title>"
                + "\n\t<link rel=\"stylesheet\" href=\"table_style.css\">\n</head>"
                + "\n<body>\n\t<h1><center>Displaying "
                + getYear() + "</center></h1>\n";
        
        MyCalendar myCalendar;
        
        int year = getYear();
        
        EventList eventList;
        eventList = new EventList("src/inputFiles/" + inputField.getText());
        
        //Runs through each month
        for(int month = 0; month < 12; month++){
            myCalendar = new MyCalendar(year, month, 1);
            answer += "\t<h2>" + myCalendar.getMonthString() + "</h2>\n\t<table>\n"
                    + "\t\t<tr>\n\t\t\t<th>Su</th>\n\t\t\t<th>M</th>\n\t\t\t<th>T</th>\n\t\t\t<th>W</th>"
                    + "\n\t\t\t<th>Th</th>\n\t\t\t<th>F</th>\n\t\t\t<th>Sa</th>\n\t\t</tr>\n\t\t<tr>";
            
            int lastDay = getLastDayOfMonth(month);
            myCalendar.setDay(lastDay);
            
            //Counts how many days have been displayed during the week. This is
            //used so that there are only 7 days per row in the calendar.
            int dayOfWeekCounter = 0;
            
            //This int is used to determine which day the 1st of each month is
            //on, and it will create the correct number of blank spaces to move
            //the 1st day to the correct position.
            int numberOfSpaces = getDayOfWeekInt(month, 1);               
            for(int spaces = 0; spaces <= numberOfSpaces; spaces++){
                if(spaces != 0){
                    answer += "\n\t\t\t<td></td>";
                }
                dayOfWeekCounter++;
            }
            
            //Runs through each day, starting at 1 and ending at the last day
            //of each month
            for(int day = 1; day <= lastDay; day++){
                //If there are less than 8 days in the current row, it writes
                //the date and moves to the next table space
                if(dayOfWeekCounter < 8){
                    //This if statement checks if there is an event during the
                    //entered year on the specific month and day
                    if(eventList.hasEventOnThisDay(getYear(), month, day) == true){
                        answer += "\n\t\t\t<td>" + day;
                        
                        //Loops through to check if there are multiple events on
                        //the specified day
                        for(int i = 0; i < eventList.numberOfEventsOnThisDay(getYear(), month, day); i++){
                            answer += "<br><b>" + eventList.getTitleForDate(getYear(),
                                    month, day, i) + "</b>";
                        }
                        
                        answer += "</td>";
                    }else{
                        answer += "\n\t\t\t<td>" + day + "</td>";
                    }
                    dayOfWeekCounter++;
                }
                //If 8 days are reached in the current row, it starts a new
                //line, it writes the date, and adds spaces in between
                else{
                    if(eventList.hasEventOnThisDay(getYear(), month, day) == true){
                        answer += "\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td>" + day;
                        
                        //Loops through to check if there are multiple events on
                        //the specified day
                        for(int i = 0; i < eventList.numberOfEventsOnThisDay(getYear(), month, day); i++){
                            answer += "<br><b>" + eventList.getTitleForDate(getYear(),
                                    month, day, i) + "</b>";
                        }
                        
                        answer += "</td>";
                    }else{
                        answer += "\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td>" + day + "</td>";
                    }
                    //Resets the counter. Unsure why it requires 2 and not 0.
                    dayOfWeekCounter = 2;
                }
            }

            //Adds a break between each month and ends the table
            answer += "\n\t\t</tr>\n\t</table>\n";

            //Adds a description for the events at the end of each month
            if(eventList.hasEventInThisMonth(getYear(), month) == true){
                answer += "\t<pre>\n" + eventList.toStringForMonth(month) + "\t</pre>\n";
            }
        }
        
        answer += "</body>\n</html>";
        
        textArea.setText("");
        textArea.append(answer);
        
        printToOutput(answer, outputField.getText());
    }
    
    //Draws a calendar as they normally appear, except adds HTML and the table
    //tag to create the code for a webpage. It also includes the title of the
    //event on the day that it happens.
    public void doHTMLUsingTableWithLinks() throws IOException{
        String answer = "<!DOCTYPE html>\n<html>\n<head>\n\t<title>Calendar</title>"
                + "\n\t<link rel=\"stylesheet\" href=\"table_style.css\">\n</head>"
                + "\n<body>\n\t<h1><center>Displaying "
                + getYear() + "</center></h1>\n";
        
        MyCalendar myCalendar;
        
        int year = getYear();
        
        EventList eventList;
        eventList = new EventList("src/inputFiles/" + inputField.getText());
        
        //Runs through each month
        for(int month = 0; month < 12; month++){
            myCalendar = new MyCalendar(year, month, 1);
            answer += "\t<h2>" + myCalendar.getMonthString() + "</h2>\n\t<table>\n"
                    + "\t\t<tr>\n\t\t\t<th>Su</th>\n\t\t\t<th>M</th>\n\t\t\t<th>T</th>\n\t\t\t<th>W</th>"
                    + "\n\t\t\t<th>Th</th>\n\t\t\t<th>F</th>\n\t\t\t<th>Sa</th>\n\t\t</tr>\n\t\t<tr>";
            
            int lastDay = getLastDayOfMonth(month);
            myCalendar.setDay(lastDay);
            
            //Counts how many days have been displayed during the week. This is
            //used so that there are only 7 days per row in the calendar.
            int dayOfWeekCounter = 0;
            
            //This int is used to determine which day the 1st of each month is
            //on, and it will create the correct number of blank spaces to move
            //the 1st day to the correct position.
            int numberOfSpaces = getDayOfWeekInt(month, 1);               
            for(int spaces = 0; spaces <= numberOfSpaces; spaces++){
                if(spaces != 0){
                    answer += "\n\t\t\t<td></td>";
                }
                dayOfWeekCounter++;
            }
            
            //Runs through each day, starting at 1 and ending at the last day
            //of each month
            for(int day = 1; day <= lastDay; day++){
                //If there are less than 8 days in the current row, it writes
                //the date and moves to the next table space
                if(dayOfWeekCounter < 8){
                    //This if statement checks if there is an event during the
                    //entered year on the specific month and day
                    if(eventList.hasEventOnThisDay(getYear(), month, day) == true){
                        answer += "\n\t\t\t<td>" + day;    
                        //Loops through to check if there are multiple events on
                        //the specified day
                        for(int i = 0; i < eventList.numberOfEventsOnThisDay(getYear(), month, day); i++){
                            answer += "<br><b><a href=\"" + getYear()
                                + "-" + (month + 1) + "-" + day + "-" + 
                                eventList.getTitleForDate(getYear(), month, day, i)
                                + ".html\">" + eventList.getTitleForDate(getYear(),
                                month, day, i) + "</a></b>";
                            createLink(month, day, i);
                        }
                        answer += "</td>";
                    }else{
                        answer += "\n\t\t\t<td>" + day + "</td>";
                    }
                    dayOfWeekCounter++;
                }
                //If 8 days are reached in the current row, it starts a new
                //line, it writes the date, and adds spaces in between
                else{
                    if(eventList.hasEventOnThisDay(getYear(), month, day) == true){
                        answer += "\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td>" + day;
                        
                        //Loops through to check if there are multiple events on
                        //the specified day
                        for(int i = 0; i < eventList.numberOfEventsOnThisDay(getYear(), month, day); i++){
                            answer += "<br><b><a href=\"" + getYear()
                                + "-" + (month + 1) + "-" + day + "-" + 
                                eventList.getTitleForDate(getYear(), month, day, i)
                                + ".html\">" + eventList.getTitleForDate(getYear(),
                                month, day, i) + "</a></b>";
                            
                            createLink(month, day, i);
                        }
                        
                        answer += "</td>";
                    }else{
                        answer += "\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td>" + day + "</td>";
                    }
                    //Resets the counter. Unsure why it requires 2 and not 0.
                    dayOfWeekCounter = 2;
                }
            }

            //Adds a break between each month and ends the table
            answer += "\n\t\t</tr>\n\t</table>\n";
        }
        
        answer += "</body>\n</html>";
        
        textArea.setText("");
        textArea.append(answer);
        
        printToOutput(answer, outputField.getText());
    }

    //Creates the HTML for each event page
    public String getEventHTML(int month, int day, int i){
        EventList eventList;
        eventList = new EventList("src/inputFiles/" + inputField.getText());
        
        String title = eventList.getTitleForDate(getYear(), month, day, i);
        
        String answer = "<!DOCTYPE html>\n<html>\n<head>\n\t<title>" + title +
                "</title>\n</head>\n<body>\n\t<h1><center>Event Information"
                + "</center></h1>\n" +
                eventList.toStringForHTML(getYear(), month, day, i) +
                "</body>\n</html>";
 
        return answer;
    }
    
    //Links the HTML from getEventHTML() and the name of the file, then sends it
    //to printToOuput() so that a new file can be created. This is used
    //specifically for the individual event pages.
    public void createLink(int month, int day, int i) throws IOException{
        EventList eventList;
        eventList = new EventList("src/inputFiles/" + inputField.getText());
        
        String data = getEventHTML(month, day, i);
        String fileName = getYear() + "-" + (month + 1) + "-" + day + "-" + 
                                eventList.getTitleForDate(getYear(), month, day,
                                i) + ".html";
        
        printToOutput(data, fileName);
    }
    
    //Creates new files that are filled with data. If a file already exists, it
    //is overwritten.
    //Adapted from: https://howtodoinjava.com/core-java/io/how-to-create-a-new-file-in-java/
    public void printToOutput(String data, String fileName) throws IOException{
        File file = new File("src/outputFiles/" + fileName);
  
        //Create the file
        if (file.createNewFile()){
          System.out.println("File is created!");
        }else{
          System.out.println("File already exists.");
        }

        //Write Content
        FileWriter writer = new FileWriter(file);
        writer.write(data);
        writer.close();
    }
    
    //Determines the last day of each month
    public int getLastDayOfMonth(int month){
        int lastDay = 1;
        
        //Months with 31 days
        if(month == 0 || month == 2 || month == 4 || month == 6 || month == 7
                || month == 9 || month == 11){
            lastDay = 31;
        }
        //Months with 30 days
        else if(month == 3 || month == 5 || month == 8 || month == 10){
            lastDay = 30;
        }
        //Accounts for February and the rules of leap years
        else{
            if((getYear() % 4 == 0) && (getYear() % 100 != 0)){
                lastDay = 29;
            }
            else if((getYear() % 4 == 0) && (getYear() % 100 == 0) && (getYear() % 400 == 0)){
                lastDay = 29;
            }
            else{
                lastDay = 28;
            }
        }
        
        return lastDay;
    }

    //Gets the day of the week for a certain day, month, and year, and it turns
    //it into an int value (0-6)
    public int getDayOfWeekInt(int month, int day){
        int dayOfWeek = 0;
        MyCalendar myCalendar;
        myCalendar = new MyCalendar(getYear(), month, day);
        String temp = myCalendar.getDayOfWeekString();
        
        if(temp.equals("Sunday")){
            dayOfWeek = 0;
        }
        else if(temp.equals("Monday")){
            dayOfWeek = 1;
        }
        else if(temp.equals("Tuesday")){
            dayOfWeek = 2;
        }
        else if(temp.equals("Wednesday")){
            dayOfWeek = 3;
        }
        else if(temp.equals("Thursday")){
            dayOfWeek = 4;
        }
        else if(temp.equals("Friday")){
            dayOfWeek = 5;
        }
        else if(temp.equals("Saturday")){
            dayOfWeek = 6;
        }
        
        return dayOfWeek;
    }
    
    //Gets the year from the user input field
    public int getYear(){
        String stringNum = yearField.getText();
        int year = Integer.parseInt(stringNum);
        
        //If the entered year is in BC (before 0 AD), it sets it to 0 AD
        if(year < 0){
            year = 0;
        }
        
        return year;
    }
    
    //Receives the combo box item that the user selected and runs the correct
    //method
    public void getSelectedItem(){
        String theStyle = (String)comboBox.getSelectedItem();
  
        try{
            if(theStyle.equals("First Day of the Month")){
                doFirstDayOfMonth();
            }else if(theStyle.equals("First and Last Day of the Month")){
                doFirstAndLastDayOfMonth();
            }else if(theStyle.equals("Monthly Format")){
                doMonthlyFormat();
            }else if(theStyle.equals("HTML Using Pre")){
                doHTMLUsingPre();
            }else if(theStyle.equals("HTML Using Table")){
                doHTMLUsingTable();
            }
        }
        //Displays an error if there is no year
        catch(Exception e){
            textArea.setText("");
            textArea.append("Sorry, you did not enter a year, or the year could"
                    + " not be read.");
        }
        
        try{
            if(theStyle.equals("Events")){
                doEvents();
            }else if(theStyle.equals("Monthly Format with Events")){
                doMonthlyFormatWithEvents();
            }else if(theStyle.equals("HTML Using Table with Titles")){
                doHTMLUsingTableWithTitles();
            }else if(theStyle.equals("HTML Using Table with Links")){
                doHTMLUsingTableWithLinks();
            }
        }
        //Displays an error if there is no text file or year
        catch(Exception e){
            textArea.setText("");
            textArea.append("Sorry, there was an error.\nPlease check that you"
                    + " entered a year, an input file (must include the phrase"
                    + " \".txt\" after the\nfile name), and an output file"
                    + " (must include the phrase \".html\" after the file name)."
                    + "\nAlso, make sure the input file is located inside the"
                    + " /src/inputFiles folder.");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        yearField = new javax.swing.JTextField();
        inputField = new javax.swing.JTextField();
        comboBox = new javax.swing.JComboBox<>();
        enterButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        outputField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Input File:");

        jLabel2.setText("Year:");

        comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxActionPerformed(evt);
            }
        });

        enterButton.setText("Enter");
        enterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterButtonActionPerformed(evt);
            }
        });

        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        jLabel3.setText("Output File:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(enterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(yearField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outputField, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addContainerGap())
            .addComponent(jSeparator1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(outputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(yearField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(enterButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxActionPerformed
        
    }//GEN-LAST:event_comboBoxActionPerformed

    private void enterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enterButtonActionPerformed
        getSelectedItem();
    }//GEN-LAST:event_enterButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CalendarFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CalendarFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CalendarFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CalendarFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CalendarFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboBox;
    private javax.swing.JButton enterButton;
    private javax.swing.JTextField inputField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField outputField;
    private javax.swing.JTextArea textArea;
    private javax.swing.JTextField yearField;
    // End of variables declaration//GEN-END:variables
}
