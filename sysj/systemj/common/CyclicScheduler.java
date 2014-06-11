package systemj.common;

import java.util.Vector;

import systemj.bootstrap.ClockDomain;
import systemj.interfaces.Scheduler;

/**
 * This is a simple cyclic scheduler
 * 
 * Must be compatible with CLDC 1.1
 * 
 * @author hpar081
 *
 */
public class CyclicScheduler extends Scheduler{
	private Vector cdarray = new Vector();
	
	//@Override
	public void addClockDomain(ClockDomain cd) {
		cdarray.addElement(cd);
	}
	

	//@Override
	public void addArguments(String args) {
		// Non
	}

	//@Override
	public void run() {
		for(int i=0;i<cdarray.size();i++){
			tick((ClockDomain)cdarray.elementAt(i));
		}
		
	}

}
