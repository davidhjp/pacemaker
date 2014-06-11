package systemj.interfaces;

import systemj.bootstrap.ClockDomain;
import systemj.common.BaseInterface;

/**
 * Scheduler interface.
 * Any Schduler implementation should have the following methods
 * @author hpar081
 *
 */
public abstract class Scheduler extends BaseInterface{
	
	/**
	 * Adding clock-domain instance to this scheduler
	 * @param cd
	 */
	public abstract void addClockDomain(ClockDomain cd);
	
	/**
	 * Run this scheduler. This method must be non-blocking
	 */
	public abstract void run();
	
	
	/**
	 * Executes clock-domain tick
	 * 
	 * @param cd - clockdomain to exec
	 */
	public final void tick(ClockDomain cd){
		cd.run();
		super.runInterfaceManager();
	}
	
	/**
	 * Argument to be provided to this scheduler
	 * @param args
	 */
	public abstract void addArguments(String args);
}
