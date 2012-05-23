//import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.util.Date;
import java.util.GregorianCalendar;
public class DateTime {
	private GregorianCalendar calendar;
	private long timeMilli;
	private static final SimpleDateFormat ISO_DATE_TIME = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss");
	private static final SimpleDateFormat DAY_MONTH_YEAR = new SimpleDateFormat(
			"dd-MM-yyyy");
	private static final SimpleDateFormat DAY_MONTH_YEAR_HOUR_MIN = new SimpleDateFormat(
			"dd-MM-yyyy HH:mm");
	private boolean hasTime;
	static 
	{
		ISO_DATE_TIME.setLenient(false);
		ISO_DATE_TIME.setLenient(false);
	}

/** default constructor
 * 
 */
public DateTime()
{
	calendar=new GregorianCalendar(2000,0,1,0,0);
	calendar.setLenient(true);
	timeMilli=calendar.getTimeInMillis();
	hasTime=false;
}
public DateTime(long timeInMillis)
{
	calendar = new GregorianCalendar(2000,0,1,0,0);
	calendar.setTimeInMillis(timeInMillis);
	calendar.setLenient(true);
	timeMilli=calendar.getTimeInMillis();
}
public DateTime(int year,int month,int day)
{
	calendar=new GregorianCalendar(year,month-1,day);
	calendar.setLenient(false);
	timeMilli=calendar.getTimeInMillis();
	hasTime=false;
}
public DateTime(int year,int month,int day,int hours,int minutes)
{
	calendar=new GregorianCalendar(year,month,day,hours,minutes);
	calendar.setLenient(false);
	timeMilli=calendar.getTimeInMillis();
	hasTime=true;
}
public DateTime(int year,int month,int day,int hours,int minutes,int seconds)
{
	calendar=new GregorianCalendar(year,month,day,hours,minutes,seconds);
	calendar.setLenient(false);
	timeMilli=calendar.getTimeInMillis();
	hasTime=true;
}
public static DateTime getCurrentDateAndTime()
{
	GregorianCalendar current=new GregorianCalendar();
	current.setLenient(false);
	current.getTimeInMillis();
	DateTime currDateTime=new DateTime(current.get(GregorianCalendar.YEAR),(current.get(GregorianCalendar.MONTH))+1,current.get(GregorianCalendar.DAY_OF_MONTH),current.get(GregorianCalendar.HOUR_OF_DAY),current.get(GregorianCalendar.MINUTE),current.get(GregorianCalendar.SECOND));
	return currDateTime;
}
public void set(int year,int month,int day)
{
	calendar.set(year, month-1,year);
	timeMilli=calendar.getTimeInMillis();
}
public void set(int year,int month,int day,int hours,int minutes,int seconds)
{
	calendar.set(year, month-1,day,hours,minutes,seconds);
	timeMilli=calendar.getTimeInMillis();
	hasTime=true;
}
public void set(int year,int month,int day,int hours,int minutes)
{
	calendar.set(year,month-1,day,hours,minutes);
	timeMilli=calendar.getTimeInMillis();
	hasTime=true;
}
public boolean isEqual(Object ob)
{
	if(!(ob instanceof DateTime))
		return false;
	if(this==ob)
		return true;
	DateTime obj=(DateTime) ob;
		return (this.getTimeMilli()==obj.getTimeMilli()) && (this.hasTime()==obj.hasTime());
}
public boolean hasTime()
{
	return hasTime;
}
public void setHasTime(boolean value)
{
	hasTime=value;
}
public long getTimeMilli()
{
	return timeMilli;
}
public void setTime(long Time)
{
	this.timeMilli=Time;
	calendar.setTimeInMillis(Time);
}
public int compareTo(DateTime second)
{
	long diff=this.getTimeMilli()-second.getTimeMilli();
	if(diff<0)
		return -1;
	else if(diff==0)
		return 0;
	else
		return 1;
}
public int getDateComponent(int component)
{
	int value=calendar.get(component);
	if(component == Calendar.MONTH){
		value+=1;
	}
	return value;
}
public String toString()
{
	return Long.toString(getTimeMilli());
}
public String formattedToString()
{
	if(isDefaultTime())
		return "";
	else if(hasTime())
		return DAY_MONTH_YEAR_HOUR_MIN.format(calendar.getTimeInMillis());
	else 
		return DAY_MONTH_YEAR.format(calendar.getTimeInMillis());
}
public String generateDateCode()
{
	return String.format("%d%d%d",this.calendar.get(GregorianCalendar.DAY_OF_MONTH),this.calendar.get(GregorianCalendar.MONTH),this.calendar.get(GregorianCalendar.YEAR));
}
public String generateTimeCode()
{
	if(hasTime)
		return String.format("%d%d%d", this.calendar.get(GregorianCalendar.HOUR),this.calendar.get(GregorianCalendar.MINUTE),this.calendar.get(GregorianCalendar.SECOND));
	else 
		return String.format("%d%d%d", 23,59,59);
}
public boolean isDefaultTime()
{
	return this.isEqual(new DateTime()) && !hasTime;
}
public DateTime getDate()
{
	return new DateTime(this.getDateComponent(GregorianCalendar.YEAR),this.getDateComponent(GregorianCalendar.MONTH),this.getDateComponent(GregorianCalendar.DAY_OF_MONTH));
}
public DateTime getTime() 
{
	DateTime newEventTime = new DateTime();
	newEventTime.setComponent(Calendar.HOUR_OF_DAY, this.getDateComponent(Calendar.HOUR_OF_DAY));
	newEventTime.setComponent(Calendar.MINUTE, this.getDateComponent(Calendar.MINUTE));
	newEventTime.getTimeMilli();
	newEventTime.setHasTime(hasTime);
	return newEventTime;
}
public void setComponent(int Component,int value)
{
	if(Component==GregorianCalendar.MONTH)
	{
		value-=1;
	}
	calendar.set(Component,value);
	if(Component == GregorianCalendar.HOUR_OF_DAY || Component == GregorianCalendar.HOUR|| Component == GregorianCalendar.MINUTE)
	{
		hasTime=true;
	}
	timeMilli = calendar.getTimeInMillis();
}
public void setComponent(int year,int month,int day)
{
	calendar.set(year,month-1,day);
	timeMilli=calendar.getTimeInMillis();
}
}
