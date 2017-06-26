package com.tydic.unicom.service.sequence.po;

import java.io.Serializable;

import javax.persistence.Id;

public class SysSequence implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private Long seq_id;

    private String seq_name;

    private Long seq_start;

    private Long seq_max;

    private Integer seq_step;

    private Integer seq_ranges;

	@Id
	public Long getSeq_id() {
		return seq_id;
	}

	public void setSeq_id(Long seq_id) {
		this.seq_id = seq_id;
	}

	public String getSeq_name() {
		return seq_name;
	}

	public void setSeq_name(String seq_name) {
		this.seq_name = seq_name;
	}

	public Long getSeq_start() {
		return seq_start;
	}

	public void setSeq_start(Long seq_start) {
		this.seq_start = seq_start;
	}

	public Long getSeq_max() {
		return seq_max;
	}

	public void setSeq_max(Long seq_max) {
		this.seq_max = seq_max;
	}

	public Integer getSeq_step() {
		return seq_step;
	}

	public void setSeq_step(Integer seq_step) {
		this.seq_step = seq_step;
	}

	public Integer getSeq_ranges() {
		return seq_ranges;
	}

	public void setSeq_ranges(Integer seq_ranges) {
		this.seq_ranges = seq_ranges;
	}

    
}