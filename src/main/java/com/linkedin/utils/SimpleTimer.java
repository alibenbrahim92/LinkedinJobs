package com.linkedin.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleTimer {

	private Long startTime;
	private boolean running;
	private Long period;
	
	public SimpleTimer(Long period) {
		log.info("SimpleTimer created");
		this.period = period;
	}
	
	public void start() {
		log.info("SimpleTimer started");
		this.startTime = System.currentTimeMillis();
		this.running = true;
	}
	
	public boolean isOver() {
		Long current = System.currentTimeMillis();
		return (current - this.startTime > period);
	}
	
	public void reset() {
		log.info("SimpleTimer initialized");
		this.running = true;
		this.startTime = System.currentTimeMillis();
	}
	
	public Long getElapsedTime() {
		return System.currentTimeMillis() - this.startTime;
	}
	
	public boolean isRunning() {
		return running;
	}
}
