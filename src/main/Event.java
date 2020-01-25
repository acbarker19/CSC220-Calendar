/*
    Calendar Project
    Alec Barker
    University of Mount Union CSC 220
    5/2/18

    This is an event, which includes the year, month, day, title, description,
    location, time, and contact info.
*/
package main;

public class Event {
    private int year, month, day;
    private String title, description, location, time, contact;
    
    public Event(){
        setYear(2000);
        setMonth(0);
        setDay(1);
        setTitle("???");
        setDescription("?????");
        setLocation("?????");
        setTime("??:??");
        setContact("???-???-????");
    }
    
    public Event(int year, int month, int day, String title, String description,
            String location, String time, String contact){
        setYear(year);
        setMonth(month);
        setDay(day);
        setTitle(title);
        setDescription(description);
        setLocation(location);
        setTime(time);
        setContact(contact);
    }
    
    public void setYear(int year){
        this.year = year;
    }
    
    public void setMonth(int month){
        //Checks if the month actually exists
        if(month > 11){
            System.out.println("The input month is after December.");
            System.out.println("Changing it to December.");
            this.month = 11;
        }
        else if(month < 0){
            System.out.println("The input month is before January.");
            System.out.println("Changing it to January.");
            this.month = 0;
        }
        else{
            this.month = month;
        }
    }
    
    public void setDay(int day){
        //Months with 31 days
        if(month == 0 || month == 2 || month == 4 || month == 6 || month == 7
                || month == 9 || month == 11){
            //Checks if the day actually exists
            if(day > 31){
                System.out.println("The input day is greater than 31.");
                System.out.println("Changing it to 31.");
                this.day = 31;
            }
            else if(day < 1){
                System.out.println("The input day is less than 1.");
                System.out.println("Changing it to 1.");
                this.day = 1;
            }
            else{
                this.day = day;
            }
        }
        //Months with 30 days
        else if(month == 3 || month == 5 || month == 8 || month == 10){
            //Checks if the day actually exists
            if(day > 30){
                System.out.println("The input day is greater than 30.");
                System.out.println("Changing it to 30.");
                this.day = 31;
            }
            else if(day < 1){
                System.out.println("The input day is less than 1.");
                System.out.println("Changing it to 1.");
                this.day = 1;
            }
            else{
                this.day = day;
            }
        }
        //Accounts for February and the rules of leap years
        else{
            if((year % 4 == 0) && (year % 100 != 0)){
                //Checks if the day actually exists
                if(day > 29){
                    System.out.println("The input day is greater than 29.");
                    System.out.println("Changing it to 29.");
                    this.day = 29;
                }
                else if(day < 1){
                    System.out.println("The input day is less than 1.");
                    System.out.println("Changing it to 1.");
                    this.day = 1;
                }
                else{
                    this.day = day;
                }
            }
            else if((year % 4 == 0) && (year % 100 == 0) && (year % 400 == 0)){
                //Checks if the day actually exists
                if(day > 29){
                    System.out.println("The input day is greater than 29.");
                    System.out.println("Changing it to 29.");
                    this.day = 29;
                }
                else if(day < 1){
                    System.out.println("The input day is less than 1.");
                    System.out.println("Changing it to 1.");
                    this.day = 1;
                }
                else{
                    this.day = day;
                }
            }
            else{
                //Checks if the day actually exists
                if(day > 28){
                    System.out.println("The input day is greater than 28.");
                    System.out.println("Changing it to 28.");
                    this.day = 28;
                }
                else if(day < 1){
                    System.out.println("The input day is less than 1.");
                    System.out.println("Changing it to 1.");
                    this.day = 1;
                }
                else{
                    this.day = day;
                }
            }
        }
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public void setLocation(String location){
        this.location = location;
    }
    
    public void setTime(String time){
        this.time = time;
    }
    
    public void setContact(String contact){
        this.contact = contact;
    }
    
    public int getYear(){
        return year;
    }
    
    public int getMonth(){
        return month;
    }
    
    public int getDay(){
        return day;
    }
    
    public String getTitle(){
        return title;
    }
    
    public String getDescription(){
        return description;
    }
    
    public String getLocation(){
        return location;
    }
    
    public String getTime(){
        return time;
    }
    
    public String getContact(){
        return contact;
    }
    
    //Displays all data for the event
    public String toString(){
        return year + "/" + month + "/" + day + "\tTitle: " + title +
                "\tDescription: " + description + "\tLocation: " + location +
                "\tTime: " + time + "\tContact: " + contact;
    }
    
    public static void main(String args[]){
        Event temp = new Event(2018, 0, 14, "Test", "This is a test", "Computer"
                + " Lab", "12:30", "123-456-7890");
        System.out.println(temp.toString());
        
        Event temp2 = new Event();
        System.out.println(temp2.toString());
        
        Event temp3 = new Event(1700, 1, 29, "Test", "This is a test", "Computer"
                + " Lab", "12:30", "123-456-7890");
        System.out.println(temp3.toString());
    }

}
