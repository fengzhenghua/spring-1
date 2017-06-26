package com.tydic.unicom.service.sequence.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.service.sequence.service.interfaces.SequenceGenServ;
import com.tydic.unicom.service.sequence.service.interfaces.SequenceServ;

/**
 * @author wangxiao
 */

/*@Service("SequenceServ")*/
public class SequenceServImpl implements SequenceServ{

	//private BeanFactory factory;
	@Autowired
	private SequenceGenServ sequenceGenServ;
	
    private volatile Map<String, SequenceGenServImpl> sequencesMap = new ConcurrentHashMap<String, SequenceGenServImpl>();

    public SequenceServImpl() {
    	
    }
    
    @Override
    public long genSequence(String seqName) {
    	return sequenceGenServ.nextValue(seqName);
    	/*SequenceGenServImpl sequenceGenServImpl = null;
    	if(sequencesMap==null || !sequencesMap.containsKey(seqName)) {
    		sequenceGenServImpl = (SequenceGenServImpl)sequenceGenServ;
    		sequenceGenServImpl.setSequenceName(seqName);
    		sequencesMap.put(seqName, sequenceGenServImpl);
    		return sequenceGenServ.nextValue();
    	} else {
    		sequenceGenServ = sequencesMap.get(seqName);
    		long seq = sequenceGenServ.nextValue();
    		return seq;
    	}*/
    }
    
  
	/*@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		factory = beanFactory;
		
	}*/
}
