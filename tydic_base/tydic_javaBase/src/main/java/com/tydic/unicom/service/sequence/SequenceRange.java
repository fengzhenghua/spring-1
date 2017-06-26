package com.tydic.unicom.service.sequence;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 序列号区间段
 * @author wangxiao
 */
public class SequenceRange {

    private long min;

    private long max;
    
    private long step;
    
    private long ranges;

    private AtomicLong value;

    public SequenceRange() {
    }

    public SequenceRange(long min, long max, long step, long ranges) {
        this.min = min;
        this.max = max;
        this.step = step;
        this.ranges = ranges;
        this.value = new AtomicLong(min);
    }

    public long getAndIncrement() {
        long currentValue = value.getAndIncrement();
        if (currentValue > max) {
            return -1;
        }

        return currentValue;
    }
    
    public long getAndAdd() {
        long currentValue = value.getAndAdd(step);
        if (currentValue > max) {
            return -1;
        }

        return currentValue;
    }

	public long getMin() {
		return min;
	}

	public void setMin(long min) {
		this.min = min;
	}

	public long getMax() {
		return max;
	}

	public void setMax(long max) {
		this.max = max;
	}

	public long getStep() {
		return step;
	}

	public void setStep(long step) {
		this.step = step;
	}

	public long getRanges() {
		return ranges;
	}

	public void setRanges(long ranges) {
		this.ranges = ranges;
	}

	public AtomicLong getValue() {
		return value;
	}

	public void setValue(AtomicLong value) {
		this.value = value;
	}

}
