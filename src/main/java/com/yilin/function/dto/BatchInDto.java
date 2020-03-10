package com.yilin.function.dto;

import java.io.Serializable;

public class BatchInDto implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
    private String Shubetsu ; 

    private String batchId;
    
    private String unyoHizuke;

	public String getShubetsu() {
		return Shubetsu;
	}

	public void setShubetsu(String shubetsu) {
		Shubetsu = shubetsu;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getUnyoHizuke() {
		return unyoHizuke;
	}

	public void setUnyoHizuke(String unyoHizuke) {
		this.unyoHizuke = unyoHizuke;
	}
    
    
}
