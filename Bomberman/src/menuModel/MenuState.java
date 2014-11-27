package menuModel;

public class MenuState {

	private State currentState;
	
	public MenuState(){
		
	}
	
	
	public State getCurrentState(){
		return currentState;
	}
	
	public boolean setCurrentState(State menu){
		currentState = menu;
		return true;
	}
	
	
}
