package newlaw.util;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AppUtils implements ApplicationContextAware {	
    private static ApplicationContext ctx = null;
    public static ApplicationContext getApplicationContext() {         
        return ctx;    
    }
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        // Assign the ApplicationContext into a static method
        this.ctx = ctx;
    }

    public static String getExceptionMessages(Exception e) {
    	Set<String> ms = new HashSet<String>();
    	Throwable t = e;
    	do {
    		if(!ms.contains(t.getMessage()))
    			ms.add(t.getMessage());
    		t = t.getCause();
    	} while(t != null);
    	String r = "";
    	for(String s : ms) {
    		r += " >> "+s;
    	}
    	return r;
    }
}