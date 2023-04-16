package com.craftwork.asia.taskScheduler.AppEnums;

public enum Priority {
	
	Not_Important(0),

	Medium(1),

	High(2),
	
	Error(3);
	
	private int priority;

	Priority(int priority) {
		this.priority = priority;
	}
	
	public int getPriority() {
		return this.priority;
	}

}
