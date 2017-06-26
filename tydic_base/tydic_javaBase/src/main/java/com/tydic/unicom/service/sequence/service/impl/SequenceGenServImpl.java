package com.tydic.unicom.service.sequence.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.service.sequence.SequenceRange;
import com.tydic.unicom.service.sequence.po.SequenceId;
import com.tydic.unicom.service.sequence.po.SysSequence;
import com.tydic.unicom.service.sequence.service.interfaces.SequenceGenServ;

/**
 * @author wangxiao
 */
@Service("SequenceGenServ")
@Scope("prototype")
public class SequenceGenServImpl implements SequenceGenServ {
	
    private final Lock lock = new ReentrantLock();

    private static final int RETRY_TIMES = 10;

    /** 当前分配的序列号段 */
    private volatile SequenceRange sequenceRange;

    /** 序列号名称 */
    //private String sequenceName;
    
    Logger logger = Logger.getLogger(SequenceGenServImpl.class);

    public SequenceGenServImpl() {
    }
    

    public long nextValue(String sequenceName) {
    	
    	logger.info("seqname:"+sequenceName);
    	Map<String,Object> tmp = new HashMap<String,Object>();
    	String qeryString="";
    	if(sequenceName.indexOf(".")>0){
			String[] strArray = null;   
			strArray = sequenceName.split("\\.");
			qeryString="seq_"+strArray[1].toString();
			
    	}else{
    		qeryString="seq_"+sequenceName;
    	}
    	logger.info(qeryString);
    	logger.info(qeryString);
    	logger.info(qeryString);
    	
    	List<SequenceId> list=S.get(SequenceId.class).query(Condition.build(qeryString).filter(tmp));
    	if(list==null||list.size()<=0) {
    		throw new RuntimeException("没有配置该序列");
    	}
    	SequenceId sequenceId=list.get(0);
    	System.out.println("Id我我我我="+sequenceId.getDual_id());
    	long Seq = Long.parseLong(sequenceId.getDual_id());
		/*tmp.put("seq_name", sequenceName);
		Condition con = Condition.build("querySysSequenceBySeqName").filter(tmp);
		SysSequence seq = (SysSequence)S.get(SysSequence.class).queryFirst(con);
    	
    	if(seq==null) {
    		throw new RuntimeException("没有配置该序列");
    	}
    	long start = seq.getSeq_start();
    	long step = seq.getSeq_step();
    	long ranges = seq.getSeq_ranges();
    	//long max = start+step*(ranges-1);   
    	long newStart = start+step*ranges;
    	if(newStart>=seq.getSeq_max()) {
    		throw new RuntimeException("序列超过最大值");
    	}
    	seq.setSeq_start(newStart);

		S.get(SysSequence.class).update(seq);*/
		return Seq;
      /*  if (null == sequenceRange) {
            resetSequenceRange();
        }

        long sequence = sequenceRange.getAndAdd();
        if (-1 == sequence) {
            // 当前序列号段已经溢出，需要重新生成
            resetSequenceRange();
            return nextValue();
        }

        return sequence;*/
    }
/*
    private void resetSequenceRange() {
        lock.lock();
        try {
            for (int times = 0; times < RETRY_TIMES; times++) {
                sequenceRange = nextSequenceRange();
                if (sequenceRange != null) {
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private SequenceRange nextSequenceRange() {
		Map<String,Object> tmp = new HashMap<String,Object>();
		tmp.put("seq_name", sequenceName);
		Condition con = Condition.build("querySysSequenceBySeqName").filter(tmp);
		SysSequence seq = (SysSequence)S.get(SysSequence.class).queryFirst(con);
    	
    	if(seq==null) {
    		throw new RuntimeException("没有配置该序列");
    	}
    	long start = seq.getSeq_start();
    	long step = seq.getSeq_step();
    	long ranges = seq.getSeq_ranges();
    	long max = start+step*(ranges-1);    	
    	SequenceRange sequenceRange = new SequenceRange(start, max, step, ranges);
        
    	long newStart = start+step*ranges;
    	if(newStart>=seq.getSeq_max()) {
    		throw new RuntimeException("序列超过最大值");
    	}
    	seq.setSeq_start(newStart);

		S.get(SysSequence.class).update(seq);
    	
    	return sequenceRange;
    	

    }
    
    public SequenceRange getSequenceRange() {
		return sequenceRange;
	}

	public void setSequenceRange(SequenceRange sequenceRange) {
		this.sequenceRange = sequenceRange;
	}


	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
*/

}
