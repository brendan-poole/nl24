package nl24.domain;


public enum FnolInstructionMethod {

    Roadside_call, Hotkey, Fax, Email, Web;
    
    @Override
    public String toString() {
		return this.name().replace("_", " ");
    	
    }
}
