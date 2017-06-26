package com.tydic.unicom.sms;

public class TextShortMessage extends ShortMessage<String> {
	
	private static final long serialVersionUID = -2100665539956069280L;
	
	@Override
	public int getMsgLength() {
		if (getMsgContent() != null) {
			return getMsgContent().length();
		}
		return 0;
	}
	
	@Override
	public EnShortMessageType getMsgType() {
		return EnShortMessageType.TEXT;
	}
	
	@Override
	public String msgContentToString() {
		return getMsgContent();
	}
	
}
