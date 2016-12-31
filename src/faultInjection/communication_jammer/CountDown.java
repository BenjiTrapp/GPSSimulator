package faultInjection.communication_jammer;

import faultInjection.communication_jammer.ComJammer;
import faultInjection.pertubation.PertubationFunctions;

import java.util.Timer;
import java.util.TimerTask;
/**
 * This Class is used to make some time triggered 
 * procedures during the fault-injection experiment
 * 
 * @author Benjamin Trapp
 *
 */
public class CountDown
{
	private int count = -1;
	private int rnd = -1;
	
	/**
	 * This Counter is used to regulate the stuck-at Experiment
	 * @param preset Preset to trigger the random stuck-at bug
	 * @param rndStuckAt Time in seconds that must lapse until 
	 * the stuck-at bug is removed 
	 */
	public synchronized void stuckAtCountDown(int preset, int rndStuckAt)
	{
		final Timer timer = new Timer();
		this.count = preset;
		this.rnd = rndStuckAt;
		TimerTask task = new TimerTask()
		{
			@Override
			public void run()
			{
				if (count > 0)
					count--;
	
				if (count == 0)
				{
					stuckAtCountDown(rnd);
					timer.cancel();
				}
			}
		};
		timer.schedule(task, 0, 1000);
	}

	/**
	 * This Class is called by stuckAtCountdown(int,int) and
	 * keeps the stuck-at bug alive for the time passed as
	 * argument 
	 * @param cnt Time in seconds that must lapse until the 
	 * stuck-at bug is removed
	 */
	private synchronized void stuckAtCountDown(int cnt)
	{
		this.count = cnt;
		final Timer timer = new Timer();
		TimerTask task = new TimerTask()
		{
			@Override
			public void run()
			{
				if (count > 0)
					count--;
	
				if (count == 0)
				{
					ComJammer.stuckAt();
					timer.cancel();
				}
			}
		};
		timer.schedule(task, 0, 1000);
	}

	/**
	 * Down counter to initiate a dash in the GPS-Coordinates
	 * @param cnt Time that must lapse until the dash will
	 * be triggered
	 */
	public synchronized void dashCountDown(int cnt)
	{
		this.count = cnt;
		final Timer timer = new Timer();
		TimerTask task = new TimerTask()
		{
			@Override
			public void run()
			{
				if (count > 0)
					count--;

				if (count == 0)
				{
					PertubationFunctions.randomDashCoordinates();
					System.err.println("Dash Injected!");
					timer.cancel();
				}
			}
		};
		timer.schedule(task, 0, 1000);
	}
}
