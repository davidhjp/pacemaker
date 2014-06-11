package systemj.bootstrap;

/**
 * SystemJ program class </p>
 *
 * @Modified_by Heejong Park
 */
public abstract class ClockDomain implements Runnable
{
	public ClockDomain(){}
	public String getName(){ return name;}
	public void setName(String n){ name = n;}
	private String name;
	public boolean isThreaded(){ return threaded;}
	protected boolean threaded = false;
	public void setThread(){ threaded = true ;}
	public abstract void init();
	public abstract void runClockDomain();

}
