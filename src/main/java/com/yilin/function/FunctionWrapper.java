package com.yilin.function;

import java.util.function.Function;

import com.yilin.function.entity.BatchLogDetails;


public class FunctionWrapper<I,O> {

	private String name;
	
	private int index;
	
	private Function<I,O> f;
	
	private BatchLogDetails detail;
	
	public FunctionWrapper(Function<I, O> f) {
		this.f = f;
	}
	
	public FunctionWrapper(Function<I, O> f, String name, int idx) {
		this.f = f;
		this.name = name;
		this.index = idx;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
 
	public Function<I, O> getF() {
		return f;
	}

	public void setF(Function<I, O> f) {
		this.f = f;
	}

	public BatchLogDetails getDetail() {
		return detail;
	}

	public void setDetail(BatchLogDetails detail) {
		this.detail = detail;
	}
	
	
}
