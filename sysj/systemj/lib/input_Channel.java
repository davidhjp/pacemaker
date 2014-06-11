package systemj.lib;
import systemj.interfaces.*;

public class input_Channel extends GenericChannel{
	private int preempted = 0;
	private int r_r = 0;
	private int r_s = 0;
	public output_Channel partner;
	public input_Channel(){}
	public void set_partner(output_Channel partner){this.partner = partner;}
	public Object get_value(){
		return this.value;
	}
	/**
	 * This method now just equalize r_r with r_s, not using 'int in' at all.. (which is ++ in the code)
	 * @param in
	 */
	public void set_r_r(int in){this.r_r  = /*in*/ this.r_s; this.modified = true;}
	public void get_val(){
		if(init)
			this.value = partner.get_value();
	}
	public int get_r_r(){return this.r_r; }
	public int get_r_s(){return this.r_s; }
	public void set_r_s(int in){this.r_s = in; this.modified = true;}
	private int get_w_s(){
		return init ? partner.get_w_s() : 0; 
	}
	public int get_preempted_val(){return this.preempted; }
	public void set_preempted() {++this.preempted; ; this.modified = true;}
	
	public void update_r_s(){ 
		if(init){
			if(partner.get_preempted_val() == this.preempted)
				this.r_s = get_w_s();
		}
	}
	/**
	 * Tests whether partner output channel is preempted or re-initialized
	 * @return <b>true</b> - when partner is preempted or re-initialized <br> <b>false</b> - otherwise
	 */
	public boolean get_preempted() {
		// Now input channel is preempted when the output channel is re-initialized (i.e. r_s < r_r)
		if(init){
			if(partner.get_preempted_val() > this.preempted || this.r_s < this.r_r) 
				return true; 
		}
		return false;
	}
	
	public int refresh(){
		this.value = null;    this.r_r = 0;
		this.r_s = 0;
		set_preempted();
		this.modified = true;
		return 1;
	}
	
	// Modular
	public void set_preempted(int num){this.preempted=num; this.modified = true;}
	public void gethook(){
		if(init){
			if(isLocal)
				partner_ref.updateLocalPartner(this.partner);
			else if(incoming){
				this.getBuffer();
				// Little trick to make sure that partner channel knows preemption status of this channel
				if(partner.get_preempted_val() < this.preempted) 
					modified = true;
			}
		}
	}
	
	public synchronized void getBuffer(){
		// Otherwise just store them into the partner copy.
		partner.set_w_s(((Integer)toReceive[1]).intValue());
		partner.set_w_r(((Integer)toReceive[2]).intValue());
		partner.set_preempted(((Integer)toReceive[3]).intValue());
		if(toReceive[4] != null)
			partner.set_value(toReceive[4]);
		incoming = false;
	}
	
	/**
	 * As we do not want to use link traffics all the time, chan status is only transferred only if it is modified during the last
	 * tick
	 */
	public void sethook(){
		if(init && this.modified){
			if(isLocal){
				updateLocalCopy();
				// Maybe modified = false?
			}
			else{
				//	toSend[0] = Boolean.TRUE;
				Object[] toSend = new Object[5];  // Creating an Object!!
				toSend[0] = PartnerName;
				toSend[1] = new Integer(this.get_r_s());  // Creating an Object!!
				toSend[2] = new Integer(this.get_r_r());
				toSend[3] = new Integer(this.get_preempted_val());
				if(value != null)
					toSend[4] = value;
				if(super.pushToQueue(toSend))
					this.modified = false; // This is set to false ONLY if the data is received by other side
			}
		}
	}
	
	// SMCHAN
	// In this case, partner is a local-copy and partner_ref has real reference to the partner object
	private output_Channel partner_ref = null;
	public void setDistributed(){ isLocal = false; partner = new output_Channel();}
	// My data-structure copies to be read by partner
	private int r_r_copy = 0;
	private int r_s_copy = 0;
	private int preempted_copy = 0;
	public void set_partner_smp(output_Channel partner){this.partner_ref = partner; this.partner = new output_Channel();}
	protected synchronized void updateLocalPartner(input_Channel p){
		// This copying operation is regarded as an atomic operation
		p.r_s = this.r_s_copy;
		p.r_r = this.r_r_copy;
		p.preempted = this.preempted_copy;
		if(value!=null)
			p.value = this.value;
	}
	protected synchronized void updateLocalCopy(){
		// This copying operation is regarded as an atomic operation
		this.r_s_copy = get_r_s();
		this.r_r_copy = get_r_r();
		this.preempted_copy = get_preempted_val();
	}
}
