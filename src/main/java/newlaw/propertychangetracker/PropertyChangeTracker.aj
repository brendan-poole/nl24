package newlaw.propertychangetracker;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class PropertyChangeTracker {

	private Logger log = Logger
			.getLogger(PropertyChangeTracker.class.getName());

	//@Before("execution(void nl24.domain.*.set*(*))")
	//@Before("execution(void set*(*))")
	/*
	public void trackChange(JoinPoint jp) {
		try {
			// Ignore if setting id or version properties
			String setter = jp.getSignature().getName();
			String property = setter.replaceFirst("^set", "");
			if (property.equalsIgnoreCase("Id")
					|| property.equalsIgnoreCase("Version")
					|| (jp.getTarget().getClass() == PropertyChange.class))
				return;
			// Check the property is a basic type that we can log
			String getter = "get" + property;
			property = property.substring(0, 1).toLowerCase()
					+ property.substring(1);
			Method getterMethod = jp.getTarget().getClass().getMethod(getter);
			if (getterMethod.getReturnType() == String.class
					|| getterMethod.getReturnType() == Integer.class
					|| getterMethod.getReturnType() == Long.class
					|| getterMethod.getReturnType() == Date.class
					|| getterMethod.getReturnType() == Boolean.class
					|| Enum.class.isAssignableFrom(getterMethod.getReturnType())) {
				// TODO: Also check for references to other entities. put id in old value
				Object oldValue = getterMethod.invoke(jp.getTarget());
				Object newValue = jp.getArgs()[0];
				if ((oldValue == null && newValue != null && newValue
						.equals(""))
						|| (newValue == null && oldValue != null && oldValue
								.equals(""))
						|| (newValue == null && oldValue == null)
						|| (newValue.equals(oldValue)))
					return;

				Method getId = jp.getTarget().getClass().getMethod("getId");
				Long id = (Long) getId.invoke(jp.getTarget());
				if (id == null)
					return;
				PropertyChange pc = new PropertyChange();
				pc.setChanged(new Date());
				pc.setTargetId(id);
				pc.setClass_(jp.getTarget().getClass().toString()
						.replaceFirst(".*\\.", ""));
				pc.setOldValue(oldValue == null ? null : oldValue.toString());
				pc.setProperty(property);
				pc.persist();
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}
	*/
	/*
	 * Originally stored old value as byte array public byte[] serialize(Object
	 * object) throws IOException { ByteArrayOutputStream b = new
	 * ByteArrayOutputStream(); ObjectOutputStream o = new
	 * ObjectOutputStream(b); o.writeObject(object); return b.toByteArray(); }
	 */
	
}