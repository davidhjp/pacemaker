package systemj.bootstrap;

import java.util.Vector;

import systemj.common.InterfaceManager;
import systemj.interfaces.Scheduler;

/**
 * SystemJ program
 * Must be compatible with CLDC 1.1
 * 
 * @author hpar081
 *
 */
public class SystemJProgram {
	private String name;
	private InterfaceManager im;
	public void setSubSystemName(String n){name = n;}
	public String getSubSystemName(){ return name ;}
	
	public void setInterfaceManager(InterfaceManager iim){ 	im = iim; }
	public InterfaceManager getInterfaceManager(){ return im ;}
	public void resolveLocalInterface() { im.setLocalInterface(name); }
	
	private Vector scs = new Vector();
	public void addScheduler(Scheduler sc){	scs.addElement(sc);	}
	public void init(){
		im.init();
		for(int i=0;i<scs.size();i++)
			((Scheduler)scs.elementAt(i)).setInterfaceManager(im);
	}
	
	public void startProgram(){
		System.out.println("Starting program");
		while(true){
			for(int i=0;i<scs.size();i++)
				((Scheduler)scs.elementAt(i)).run();
			im.run();
		}
	}
}
