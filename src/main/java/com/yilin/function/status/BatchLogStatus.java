package com.yilin.function.status;

public enum BatchLogStatus {

	INIT(-1),
	RUNNING(1000),
	SUCCED(1001),
	FAILED(1010),
	NORUN(1100);
	
	private int value;
	
	private BatchLogStatus(int value) {
		this.value = value;
	}
	
	
	public int getValue() {
		return value;
	}

	public static BatchLogStatus of(int value) {
		BatchLogStatus[] all =  BatchLogStatus.values();
		for(BatchLogStatus item : all) {
			if(item.value == value) {
				return item;
			}
		}
		return INIT;
	}
}
