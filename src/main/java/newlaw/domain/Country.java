package newlaw.domain;


public enum Country {

   England, Wales, Scotland, Northern_Ireland, Foreign;
    
 
    
    @Override
    public String toString() {
		return this.name().replace("_", " ");
    }
 
}
