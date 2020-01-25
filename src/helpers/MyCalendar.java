package helpers;

import java.util.*;

/* Jim Klayder      spring 2018


a subclass of GregorianCalendar that attempts to make use of
a Calendar object relatively easy to use

comparisons of the days between two calendars are easy to call



*/

public class MyCalendar extends GregorianCalendar
{
    
    public MyCalendar(
        int year,
        int month,  //NOTE: January is 0, February is 1, . . . December is 11
        int day)
    {
        super(year,month,day);
    }

    public int getYear() { return get(Calendar.YEAR); }
    public int getMonth() { return get(Calendar.MONTH); }
    public int getDay() { return get(Calendar.DATE); }
    
    
    public String getMonthString()
    {
        //based on an idea from:
        //  https://stackoverflow.com/questions/14832151/how-to-get-month-name-from-calendar
        
        int month = get(Calendar.MONTH);
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }
    
    public String getDayOfWeekString()
    {
        int dayOfWeek  = get(Calendar.DAY_OF_WEEK);
        String[] dayNames = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        return dayNames[dayOfWeek % 7];
    }
    
    public void setYear(int year)
    {
        set(Calendar.YEAR,year);
    }
    
    public void setMonth(int month)
    {
        set(Calendar.MONTH,month);
    }
    
    public void setDay(int day)
    {
        set(Calendar.DATE,day);
    }
    
    public int getDaysBefore(MyCalendar otherCalendar)
    {
         //based on an idea from:
        // http://www.java2s.com/Code/Java/Development-Class/DateDiffcomputethedifferencebetweentwodates.htm
        
        Date theStartDate = this.getTime();
        Date theStopDate = otherCalendar.getTime();
        
        // Get milliseconds from each, and subtract.
        long diff = theStopDate.getTime() - theStartDate.getTime();
        long differenceInDays = diff / (1000 * 60 * 60 * 24);
        ///       1000 milliseconds in a second
        ///       60 seconds in a minute
        ///       60 minutes in an hour
        ///       24 hours in a day
        
        //System.out.println("differenceInDays     "+differenceInDays);
        return (int)differenceInDays;
        
    }
    
    public int getYearsBefore(MyCalendar otherCalendar)
    {
        int differenceInDays = getDaysBefore(otherCalendar);
        
        long yearDiff = differenceInDays / 365;
        
        return (int)yearDiff;
    }
    
    public int getLeftOverDaysTakingLeapYearIntoAccount(MyCalendar otherCalendar)
    {
        
        
        long differenceInDays = getDaysBefore(otherCalendar);
         
        long yearDiff = differenceInDays / 365;
        long leftOverDays = differenceInDays % 365;
    

//        System.out.println("   version 1 (just divide by 365)");
//        System.out.println("yearDiff     "+yearDiff);
//        System.out.println("leftOverDays     "+leftOverDays);
        
        long startYear = this.getYear();
        long stopYear = otherCalendar.getYear();
        
        MyCalendar tempCalendar = new MyCalendar(this.getYear(),this.getMonth(),this.getDay());
        
//        System.out.println("\n\nin getLeftOverDaysTakingLeapYearIntoAccount");
//        System.out.println("this.getYear()   "+this.getYear());
//        System.out.println("this.getMonth()   "+this.getMonth());
//        System.out.println("this.getDay()   "+this.getDay());
        
        //make the year the same as the otherCalendar year
        tempCalendar.setYear(otherCalendar.getYear()); 
        
        if (tempCalendar.after(otherCalendar))
        {
            tempCalendar.setYear(otherCalendar.getYear()-1);
        }
        
//        System.out.println("tempCalendar.toString()    "+tempCalendar.toString());
//        System.out.println("otherCalendar.toString()    "+otherCalendar.toString());
        
        Date tempDate = tempCalendar.getTime();
        Date theStopDate = otherCalendar.getTime();
        
        // Get milliseconds from each, and subtract.
        long diff = theStopDate.getTime() - tempDate.getTime();
        
        differenceInDays = diff / (1000 * 60 * 60 * 24);
        ///       1000 milliseconds in a second
        ///       60 seconds in a minute
        ///       60 minutes in an hour
        ///       24 hours in a day
        
//        System.out.println("diff    "+diff);
//        System.out.println("differenceInDays    "+differenceInDays);
          
        return (int)differenceInDays;
        
    }

    public String toString()
        {
            return 
            "   year  " + getYear() +
            "   month  " + getMonth() +
            "   day  " + getDay();
    }
    
    public String toFormattedString()
    {
        if (getYear() == 9999)
        {
            return "( not yet )"; //this event has 'not yet' occurred
        }else
        {
            return getMonthString()+" "+getDay()+", "+getYear();
        }
    }

    public String shortToString()
        {
            return "("+getMonth()+" "+getDay()+", "+getYear()+")";
         
    }
    
    private static void showCompareDates(MyCalendar startCalendar, MyCalendar stopCalendar)
    {
        System.out.println("startCalendar.toFormattedString()  "+startCalendar.toFormattedString());
        System.out.println("startCalendar.getMonthString()  "+startCalendar.getMonthString());
        System.out.println("startCalendar.getDayOfWeekString()  "+startCalendar.getDayOfWeekString());
        
        System.out.println("stopCalendar.toFormattedString()  "+stopCalendar.toFormattedString());
        System.out.println("stopCalendar.getMonthString()  "+stopCalendar.getMonthString());
        System.out.println("stopCalendar.getDayOfWeekString()  "+stopCalendar.getDayOfWeekString());
        
        int theDaysBefore = startCalendar.getDaysBefore(stopCalendar);
        int theYearsBefore = startCalendar.getYearsBefore(stopCalendar);
        int theLeftOverDays = startCalendar.getLeftOverDaysTakingLeapYearIntoAccount(stopCalendar);
    
        if (theDaysBefore == 1)
        {
            System.out.println("\n"+startCalendar.toFormattedString()+" is "
                +" 1 day before "+stopCalendar.toFormattedString()+"\n");
        }else
        {
            System.out.println("\n"+startCalendar.toFormattedString()+" is "+theDaysBefore
                +" days before "+stopCalendar.toFormattedString()+"\n");
        }
        
        
        System.out.println("theDaysBefore     "+theDaysBefore);
        System.out.println("theYearsBefore     "+theYearsBefore);
        System.out.println("theLeftOverDays     "+theLeftOverDays);
    }
/*    
    public static void main(String[] args)
    {
        System.out.println("MyCalendar");
        
        MyCalendar startCalendar = new MyCalendar(2017,11,1);  //2017 December 1
        MyCalendar stopCalendar = new MyCalendar(2018,0,1);  //2018 January 1
        showCompareDates(startCalendar, stopCalendar); 
        System.out.println("\n\n     ");
        
        startCalendar = new MyCalendar(2018,0,1);  //2018 January 1
        stopCalendar = new MyCalendar(2018,0,2);  //2018 January 2
        showCompareDates(startCalendar, stopCalendar); 
        System.out.println("\n\n     ");
        
        startCalendar = new MyCalendar(2017,0,2);  //2017 January 2
        stopCalendar = new MyCalendar(2018,0,2);  //2018 January 2
        showCompareDates(startCalendar, stopCalendar); 
        System.out.println("\n\n     ");
        
        System.out.println("Notice that the leap year is taken info account\n      in the following example. \n\n");
        
        startCalendar = new MyCalendar(2016,0,2);  //2016 January 2
        stopCalendar = new MyCalendar(2017,0,2);  //2017 January 2
        showCompareDates(startCalendar, stopCalendar); 
        System.out.println("\n\n     ");
        
        System.out.println("Notice that 2000 is a leap year. \n\n");
        
        
        startCalendar = new MyCalendar(2000,0,2);  //2000 January 2
        stopCalendar = new MyCalendar(2001,0,2);  //2001 January 2
        showCompareDates(startCalendar, stopCalendar); 
        System.out.println("\n\n     ");
        
        System.out.println("Notice that 1900 is NOT a leap year.");
        System.out.println("     (   https://www.timeanddate.com/date/leapyear.html   ) \n\n");
        
        startCalendar = new MyCalendar(1900,0,2);  //1900 January 2
        stopCalendar = new MyCalendar(1901,0,2);  //1901 January 2
        showCompareDates(startCalendar, stopCalendar); 
        System.out.println("\n\n     ");
        
        //NOTE: remember that the second parameter is the month,
        //      and that January is month 0
        MyCalendar temp = new MyCalendar(2018,1,5);
        System.out.println("temp.toFormattedString()   "+temp.toFormattedString());
        System.out.println("temp.getDayOfWeekString()   "+temp.getDayOfWeekString());
        
        MyCalendar temp2 = new MyCalendar(2018,0,1);
        System.out.println("temp2.toFormattedString()   "+temp2.toFormattedString());
        System.out.println("temp2.getDayOfWeekString()   "+temp2.getDayOfWeekString());
    }
*/
}
