package gui.reminder;

import gui.UIController;

import java.awt.SystemTray;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import data.Task;

import logic.JIDLogic;

import data.Task;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JDialog;
import javax.swing.Timer;

import org.apache.log4j.Logger;

public class Reminder {
	
	private static Logger logger=Logger.getLogger(Reminder.class);
	
	private final static long MIN_TO_MILLISEC = 1000*60;
	
	SystemTray tray;
	static long timeLeft;
	static long delay = 5 * MIN_TO_MILLISEC;
	static Task task;
	static Timer timer;
	static ActionListener reminderPerformer;
	static GregorianCalendar now;

	public Reminder(SystemTray tray) {
		this.tray = tray;
		logger.debug("tray: " + tray!=null);
		init();
		runReminder();
	}

	public static void init() {
		reminderPerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.debug("reminderPerformer starts");
				
				if(task.getImportant()) {
					new AlarmFrame(task);
				}
				else {
					UIController.showTrayMsg("Jot It Down!", task.getName() + " is starting at " 
							+ task.getStart().formattedToString());
				}

				task = null;
				runReminder();
			}
		};
	}

	private static void setUpdateNextHour() {
		// TODO Auto-generated method stub
		Timer autoRefresh = new Timer( 1000*60*60, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				runReminder();
			}
		});
		autoRefresh.setRepeats(false);
		autoRefresh.start();
	}

	protected static void runReminder() {
		// TODO Auto-generated method stub
		now = new GregorianCalendar();
		task = findLatestTask();
		timeLeft = findTimeLeft(task);
		
		if(timeLeft >= 0)
			setTimer((int)timeLeft);
		else
			setUpdateNextHour();
	}

	private static void setTimer(int timeLeft) {
		// TODO Auto-generated method stub
		logger.debug("setTimer: " + timeLeft);
		
		if(timer != null) 
			timer.stop();

		timer = new Timer(timeLeft, reminderPerformer);
		timer.setRepeats(false);
		timer.start();
	}

	private static long findTimeLeft(Task task) {
		if(task == null)
			return -1;
		
		
		long nextReminderMilli;
		long nowMilli;
		long timeDiff = -1;
		
		nextReminderMilli = task.getStart().getTimeMilli();
		nowMilli = now.getTimeInMillis();
		
		logger.debug(nextReminderMilli);
		logger.debug(nowMilli);
		
		timeDiff = nextReminderMilli - nowMilli - delay;
			
		logger.debug("timediff: " + timeDiff);
		
		if(timeDiff <0) {
			return -1;
		}
		
		if(timeDiff > Integer.MAX_VALUE) {
			return -1;
		}
		
		return timeDiff;
	}

	private static Task findLatestTask() {
		JIDLogic.setCommand("find");
		Task[] tasks = JIDLogic.executeCommand("find *.*");
		if(tasks != null) {
			for(int i=0; i<tasks.length; i++) {
				if(!isPassed(tasks[i])) {
					logger.debug("latestTask: " + tasks[i].toString());
					return tasks[i];
				}
			}
		}
		
		logger.debug("latestTask: null") ;
		
		task = null;
		return null;		
	}
	
	private static boolean isPassed(Task task) {
		return findTimeLeft(task) < 0;
	}

	public static void update() {
		runReminder();
	}
	
	public static void setDelay(long newDelay) {
		delay = newDelay;
	}
	
	public static void main(String[] arguments) {
		new Reminder(null);
	}
}
