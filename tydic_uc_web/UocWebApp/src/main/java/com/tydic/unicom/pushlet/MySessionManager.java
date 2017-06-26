package com.tydic.unicom.pushlet;

import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.Session;
import nl.justobjects.pushlet.core.SessionManager;
import nl.justobjects.pushlet.util.PushletException;

public class MySessionManager extends SessionManager{
	@Override 
	public Session createSession(Event anEvent) throws PushletException { 
		return Session.create(anEvent.getField("userId", "visitor"));//修改后的写法
	}
}
