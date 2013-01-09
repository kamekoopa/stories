import models.exception.TechnicalException;
import models.utils.InjectorWrapper;
import play.Application;
import play.GlobalSettings;


public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {
		super.onStart(app);

		try {
			InjectorWrapper.init();
		} catch (ReflectiveOperationException e) {
			throw new InitializationFailException(e);
		}
	}

	public static class InitializationFailException extends TechnicalException {
		private static final long serialVersionUID = 1389268127651378292L;

		public InitializationFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
		}

		public InitializationFailException(String message, Throwable cause) {
			super(message, cause);
		}

		public InitializationFailException(String message) {
			super(message);
		}

		public InitializationFailException(Throwable cause) {
			super(cause);
		}
	}
}
