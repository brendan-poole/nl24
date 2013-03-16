package newlaw.bpm;

public class BpmException extends RuntimeException /* MEMO: Runtime otherwise transactions won't roll back */ {

	private static final long serialVersionUID = 1L;

	public BpmException(String string, Exception e) {
		super(string, e);
	}

	public BpmException(String string) {
		super(string);
	}

}
