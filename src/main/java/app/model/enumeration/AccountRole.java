package app.model.enumeration;

public enum AccountRole {
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
