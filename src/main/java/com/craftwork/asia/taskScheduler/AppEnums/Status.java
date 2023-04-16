package com.craftwork.asia.taskScheduler.AppEnums;

public enum Status {
	Not_Started(0),

	In_Progress(1),

	Done(2),
	
	Error(3);
	
	private final int status;

	Status(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return this.status;
	}
}
