package app.model.enumeration;

public enum showStatus {
	USERSUBMISSION  {
		@Override
		public String getString() {
			return "USERSUBMISSION";
		}
	}, 
	PROCOSUBMISSION  {
		@Override
		public String getString() {
			return "PROCOSUBMISSION";
		}
	},
	VISABLE  {
		@Override
		public String getString() {
			return "VISABLE";
		}
	},
	SUSPENDED {
		@Override
		public String getString() {
			return "SUSPENDED";
		}
	},
	PENDING {
		@Override
		public String getString() {
			return "PENDING";
		}
	};
	public abstract String getString();
}
