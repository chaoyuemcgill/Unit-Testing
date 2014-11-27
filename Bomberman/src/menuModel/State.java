package menuModel;

import menuView.*;
import java.util.*;

public class State {
	
	protected String name;
	public boolean active;
	public static enum Menu {initial, login};

	
	public Menu state;
	
	public State(){

	}
	
	public Menu getState(){
		return state;
	}
	
	public boolean isActive(){
		return active;
	}
	
	public void setActive(){
		active = true;
	}
	
	public void setInactive(){
		active = false;
	}
	
	
	
}
