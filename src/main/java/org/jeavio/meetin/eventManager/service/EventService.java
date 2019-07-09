package org.jeavio.meetin.eventManager.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jeavio.meetin.eventManager.dto.EventDTO;
import org.jeavio.meetin.eventManager.model.Event;

public interface EventService {

	public boolean addEvent(EventDTO newEvent);

	public boolean checkSlotAvailability(String roomName, Date start, Date end);

	public List<EventDTO> findEventByRoomName(String roomName);

	public List<EventDTO> findEventByEmpId(String empId);

	public List<EventDTO> getPastEvents(String empId);

	public List<EventDTO> getFutureEvents(String empId);

	public EventDTO cancelEvent(String eventId);

	boolean existsById(String id);

	public Event findById(String id);

	public Map<String, List<EventDTO>> getAllEventGroupByRoomName(List<String> roomNames);

	public boolean checkSlotAvailability(String eventId, String roomName, Date start, Date end);

	public boolean modifyEvent(EventDTO modifiedEvent);
	
}
