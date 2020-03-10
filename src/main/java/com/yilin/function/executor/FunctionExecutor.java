package com.yilin.function.executor;

public interface FunctionExecutor<I, O> {

	/**
	 * execute the function, with parameter object input, and returns object, the type of O.
	 * @param input
	 * @return
	 */
	public O execute(I input);
}
