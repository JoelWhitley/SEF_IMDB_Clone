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
	},
	CRITIC {
		@Override
		public String getString() {
			return "CRITIC";
		}
	},
	PENDING_PROCO {
		@Override
		public String getString() {
			return "PENDING_PROCO";
		}
	},
	PENDING_CRITIC {
		@Override
		public String getString() {
			return "PENDING_CRITIC";
		}
	};
	public abstract String getString();
}
