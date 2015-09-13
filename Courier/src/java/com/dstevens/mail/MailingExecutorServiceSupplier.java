package com.dstevens.mail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public enum MailingExecutorServiceSupplier implements Supplier<ExecutorService> {

	CACHED_THREADS(Executors.newCachedThreadPool());
	
	private final ExecutorService executor;

	private MailingExecutorServiceSupplier(ExecutorService executor) {
		this.executor = executor;
	}
	
	public ExecutorService get() {
		return executor;
	}
	
}
