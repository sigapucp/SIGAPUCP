package edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents;

import java.util.ArrayList;
import java.util.List;

/**
 * The basic implementation for the event interface.
 * 
 * @author mfaried
 *
 * @param <T>
 *            The type that is used to hold the event information.
 */
public class Event<T extends EventArgs> implements IEvent<T> {

	// The list of registered event handler for this event.
	private List<IEventHandler<T>> _eventHandlers;

	public Event()
	{
		_eventHandlers = new ArrayList<IEventHandler<T>>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see IEvent#addHandler(IEventHandler)
	 */
	@Override
	public void addHandler(IEventHandler<T> handler)
	{
		_eventHandlers.add(handler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see IEvent#removeHandler(IEventHandler)
	 */
	@Override
	public void removeHandler(IEventHandler<T> handler)
	{
		_eventHandlers.remove(handler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see IEvent#fire(java.lang.Object, T)
	 */
	@Override
	public void fire(Object sender, T args)
	{
		for (IEventHandler<T> handler : _eventHandlers)
		{
			handler.handle(sender, args);
		}
	}
}