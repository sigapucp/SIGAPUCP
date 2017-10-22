package edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents;

/**
 * The event handler interface
 * 
 * @author mfaried
 *
 * @param <T>
 *            The type that is used to hold the event information.
 */
@FunctionalInterface
public interface IEventHandler<T extends EventArgs> {
	void handle(Object sender, T args);
}