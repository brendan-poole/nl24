package newlaw.util;

import java.util.Hashtable;

public class CustomHashtable<K,V> extends Hashtable {
	@Override
    public String get(Object key) {
		String s = (String) super.get(key);
		return s == null ? "" : s;
	}
}
