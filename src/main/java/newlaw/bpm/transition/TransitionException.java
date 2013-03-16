package newlaw.bpm.transition;

public class TransitionException extends RuntimeException {

	public TransitionException(String string, Exception e) {
		super(string, e);
	}

}
