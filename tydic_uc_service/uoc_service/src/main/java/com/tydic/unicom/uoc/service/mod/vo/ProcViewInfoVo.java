package com.tydic.unicom.uoc.service.mod.vo;

import com.tydic.unicom.webUtil.BaseVo;

public class ProcViewInfoVo extends BaseVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String proc_mod_code;
	private String tache_code;
	private String seq_no;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProc_mod_code() {
		return proc_mod_code;
	}
	public void setProc_mod_code(String proc_mod_code) {
		this.proc_mod_code = proc_mod_code;
	}
	public String getTache_code() {
		return tache_code;
	}
	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}
	public String getSeq_no() {
		return seq_no;
	}
	public void setSeq_no(String seq_no) {
		this.seq_no = seq_no;
	}
	
	
}
