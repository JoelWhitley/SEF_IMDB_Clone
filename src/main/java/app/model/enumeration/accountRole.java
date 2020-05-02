package app.model.enumeration;

public enum accountRole {
	USER {
		@Override
		public String getString() {
			return "USER";
		}
	},
	ADMIN {
		@Override
		public String getString() {
			return "ADMIN";
		}
	},
	PROCO {
		@Override
		public String getString() {
			return "PROCO";
		}
	};
	public abstract String getString();
}
