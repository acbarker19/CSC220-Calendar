/*
    Calendar Project
    Alec Barker
    University of Mount Union CSC 220
    5/2/18

    The name of the user's text file is entered into this EventList class,
    which extracts the data and creates an ArrayList of Event objects that
    can be manipulated and displayed individually.
*/
package main;

import helpers.In;
import helpers.Utility;
import java.util.ArrayList;

public class EventList {

    private ArrayList<Event> list;
    
    public EventList(){
        list = new ArrayList<Event>();
    }
    
    public EventList(String fileName){
        //System.out.println("start constructor");
        fillListFromFile(fileName);
        //System.out.println("end constructor");
    }
    
    //Takes all of the data from the text file, splits it apart by line breaks,
    //and calls the addEvent method
    public void fillListFromFile(String fileName){
        list = new ArrayList<Event>();
        String data = Utility.getFileAsString(fileName);
        String[] info = data.split("\n");
        for(int i = 0; i < info.length; i++){
            addEvent(info[i]);
        }
        //System.out.println("end fill list");
    }
    
    //Takes each line of the text file, splits the data apart based on the '#',
    //assigns it to certain data members, and adds it the the ArrayList of
    //Event objects
    public void addEvent(String data){
        String[] eventInfo = data.split("#");
        
        String stringYear = eventInfo[0];
        int year = Integer.parseInt(stringYear);
        String stringMonth = eventInfo[1];
        int month = Integer.parseInt(stringMonth);
        String stringDay = eventInfo[2];
        int day = Integer.parseInt(stringDay);
        String title = eventInfo[3];
        String description = eventInfo[4];
        String location = eventInfo[5];
        String time = eventInfo[6];
        String contact = eventInfo[7];
        
        list.add(new Event(year, month, day, title, description, location, time, contact));
    }
    
    //Returns the size of the ArrayList
    public int getSize(){
        int size = 0;
        
        for(int i = 0; i < list.size(); i++){
            size++;
        }
        
        return size;
    }
    
    //Displays all data for every event - Please note: I made it so that 1 is
    //added to the number of each month. This way, the months are the way we
    //normally write them. Example: April is month 4, instead of 3.
    public String toString(){
        String temp = "";
        
        for(int i = 0; i < list.size(); i++){
            temp += "*** Event #" + i + " ***\n" + (list.get(i).getMonth() + 1) +
                    "/" + list.get(i).getDay()+ "/" + list.get(i).getYear() + 
                    "\nTitle:\t\t" + list.get(i).getTitle() + "\nDescription:\t" + 
                    list.get(i).getDescription() + "\nLocation:\t" +
                    list.get(i).getLocation() + "\nTime:\t\t" + list.get(i).getTime() +
                    "\nContact:\t" + list.get(i).getContact() + "\n\n";
        }
        
        return temp;
    }
    
    //Displays all data for every event in a specific month - Please note: I
    //made it so that 1 is added to the number of each month. This way, the
    //months are the way we normally write them. Example: April is month 4,
    //instead of 3.
    public String toStringForMonth(int month){
        String temp = "";
        
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getMonth() == month){
                temp += "*** Event #" + i + " ***\n" + (list.get(i).getMonth() + 1) +
                    "/" + list.get(i).getDay()+ "/" + list.get(i).getYear() + 
                    "\nTitle:\t\t" + list.get(i).getTitle() + "\nDescription:\t" + 
                    list.get(i).getDescription() + "\nLocation:\t" +
                    list.get(i).getLocation() + "\nTime:\t\t" + list.get(i).getTime() +
                    "\nContact:\t" + list.get(i).getContact() + "\n\n";
            }
        }
        
        return temp;
    }
    
    //Displays all data for every event in a specific year - Please note: I made
    //it so that 1 is added to the number of each month. This way, the months
    //are the way we normally write them. Example: April is month 4, instead
    //of 3.
    public String toStringForYear(int year){
        String temp = "";
        
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getYear() == year){
                temp += "*** Event #" + i + " ***\n" + (list.get(i).getMonth() + 1) +
                    "/" + list.get(i).getDay()+ "/" + list.get(i).getYear() + 
                    "\nTitle:\t\t" + list.get(i).getTitle() + "\nDescription:\t" + 
                    list.get(i).getDescription() + "\nLocation:\t" +
                    list.get(i).getLocation() + "\nTime:\t\t" + list.get(i).getTime() +
                    "\nContact:\t" + list.get(i).getContact() + "\n\n";
            }
        }
        
        return temp;
    }
    
    //Displays all data for an event in a specific year, month, and day - Please
    //note: I made it so that 1 is added to the number of each month. This way,
    //the months are the way we normally write them. Example: April is month 4,
    //instead of 3.
    public String toStringForHTML(int year, int month, int day, int eventOrder){
        String temp = "";
        int currentEvent = 0;
        
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getMonth() == month && list.get(i).getDay() == day && list.get(i).getYear() == year){
                if(currentEvent == eventOrder){
                    temp += "<p>Year: " + list.get(i).getYear() +
                        "</p>\n<p>Month: " + (list.get(i).getMonth() + 1) +
                        "</p>\n" + "<p>Day: " + list.get(i).getDay() + "</p>\n"
                        + "<p>Title: " + list.get(i).getTitle() + "</p>\n<p>"
                        + "Description: " + list.get(i).getDescription() +
                        "</p>\n<p>Location: " + list.get(i).getLocation() +
                        "</p>\n<p>Time: " + list.get(i).getTime() + "</p>\n<p>"
                        + "Contact: " + list.get(i).getContact();
                }
                currentEvent++;
            }
        }
        
        return temp;
    }
    
    //Returns the title of the event that happens on a specific date in HTML
    public String getTitleForDate(int year, int month, int day, int eventOrder){
        String answer = "";
        int currentEvent = 0;
        
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getMonth() == month && list.get(i).getDay() == day && list.get(i).getYear() == year){
                if(currentEvent == eventOrder){
                    answer += list.get(i).getTitle();
                }
                currentEvent++;
            }
        }
        
        return answer;
    }
    
    //Determines if there is an event during a specific year
    public Boolean hasEventInThisYear(int year){
        Boolean answer = false;
        
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getYear() == year){
                answer = true;
            }
        }
        
        return answer;
    }
    
    //Determines if there is an event during a specific year and month
    public Boolean hasEventInThisMonth(int year, int month){
        Boolean answer = false;
        
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getMonth() == month && list.get(i).getYear() == year){
                answer = true;
            }
        }
        
        return answer;
    }
    
    //Determines if there is an event during a specific year, month, and day
    public Boolean hasEventOnThisDay(int year, int month, int day){
        Boolean answer = false;
        
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getMonth() == month && list.get(i).getDay() == day && list.get(i).getYear() == year){
                answer = true;
            }
        }
        
        return answer;
    }
    
    //Counts how many events are on a given day
    public int numberOfEventsOnThisDay(int year, int month, int day){
        int answer = 0;
        
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getMonth() == month && list.get(i).getDay() == day && list.get(i).getYear() == year){
                answer++;
            }
        }
        
        return answer;
    }
    
    public static void main(String args[]){
        EventList eventList = new EventList("src/main/eventFile.txt");
        System.out.println(eventList.toString());
    }

}
