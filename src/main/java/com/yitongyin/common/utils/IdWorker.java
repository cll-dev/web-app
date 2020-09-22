package com.yitongyin.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IdWorker {
	
	private final long workerId;
	private final static long twepoch = 1361753741828L;
	private long sequence = 0L;
	private final static long workerIdBits = 4L;
	private final static long maxWorkerId = -1L^-1L<<workerIdBits;
	private final static long sequenceBits = 10L;
	
	private final static long workerIdShift = sequenceBits;
	private final static long timestampLeftShift = sequenceBits + workerIdBits;
	private final static long sequenceMask = -1L ^ -1L << sequenceBits;
	
	private long lastTimestamp = -1L;
	
	private static final Log LOG = LogFactory.getLog(IdWorker.class);
	
	
	public static final IdWorker DEFAULT = new IdWorker(3);
	
	
	public IdWorker(final long workerId) {
		super();
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(String.format(
					"worker Id can't be greater than %d or less than 0",
					maxWorkerId));
		}
		this.workerId = workerId;
	}
	
	public synchronized long nextId() {
		long timestamp = this.timeGen();
		if (this.lastTimestamp == timestamp) {
			this.sequence = (this.sequence + 1) & sequenceMask;
			if (this.sequence == 0) {
				LOG.debug("###########" + sequenceMask);
				timestamp = this.tilNextMillis(this.lastTimestamp);
			}
		} else {
			this.sequence = 0;
		}
		if (timestamp < this.lastTimestamp) {
			try {
				throw new Exception(
						String.format(
								"Clock moved backwards.  Refusing to generate id for %d milliseconds",
								this.lastTimestamp - timestamp));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		this.lastTimestamp = timestamp;
		long nextId = ((timestamp - twepoch << timestampLeftShift))
				| (workerId << workerIdShift) | (this.sequence);
		/*System.out.println("timestamp:" + timestamp + ",timestampLeftShift:"
				+ timestampLeftShift + ",timestamp - twepoch:" + (timestamp - twepoch)
				+"(timestamp - twepoch << timestampLeftShift):"+(timestamp - twepoch << timestampLeftShift)
				+"this.workerId << this.workerIdShift"+(this.workerId << this.workerIdShift)
				+" (this.workerId << this.workerIdShift) | (this.sequence):"+ ((this.workerId << this.workerIdShift) | (this.sequence))
				+ ",workerId:"
				+ workerId + ",sequence:" + sequence);*/
		return nextId;
	}
	
	private long tilNextMillis(final long lastTimestamp) {
		long timestamp = this.timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = this.timeGen();
		}
		return timestamp;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}
	
	
	

}
