package org.jeavio.meetin.eventManager.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jeavio.meetin.eventManager.dao.EventRepository;
import org.jeavio.meetin.eventManager.dto.EventDTO;
import org.jeavio.meetin.eventManager.dto.EventDetails;
import org.jeavio.meetin.eventManager.model.Event;
import org.jeavio.meetin.eventManager.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	EventRepository eventRepository;

	@Override
	public boolean addEvent(EventDTO newEvent) {
		if(!checkSlotAvailability(newEvent.getRoomName(), newEvent.getStart(), newEvent.getEnd()))
			return false;
		Event event = new Event(newEvent.getTitle(), newEvent.getAgenda(), newEvent.getRoomName(),
				newEvent.getRoomSpecifications(), newEvent.getStart(), newEvent.getEnd(), newEvent.getOrganizer(),
				newEvent.getMembers());
		eventRepository.save(event);
		return true;
		
	}

	@Override
	public boolean checkSlotAvailability(String roomName, Date start, Date end) {
		List<Event> events = eventRepository.findAllEventsByRoomName(roomName);
		for (Event event : events) {
			Date startDate = event.getStart();
			Date endDate = event.getEnd();

			boolean condition1 = (start.before(startDate) || start.equals(startDate))
					&& (end.after(startDate) || end.equals(startDate));
			boolean condition2 = (start.before(endDate) || start.equals(endDate))
					&& (end.after(endDate) || end.equals(endDate));
			boolean condition3 = (start.after(startDate) || start.equals(startDate))
					&& (end.before(endDate) || end.equals(endDate));
			if (condition1 || condition2 || condition3)
				return false;
		}

		return true;
	}

	@Override
	public boolean checkSlotAvailability(String roomName, String start, String end) {
		if (start == null || end == null)
			return false;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date requestedStartDate = null;
		Date requestedEndDate = null;
		try {
			requestedStartDate = format.parse(start);
			requestedEndDate = format.parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (requestedStartDate == null || requestedEndDate == null)
			return false;
		else
			return checkSlotAvailability(roomName, requestedStartDate, requestedEndDate);
	}

	@Override
	public List<EventDetails> findEventByRoomName(String roomName) {
		return eventRepository.findAllByRoomName(roomName);
	}

	@Override
	public List<EventDetails> findEventByEmpId(String empId) {
		return eventRepository.findAllByEmpId(empId);
	}

	@Override
	public Map<String, List<EventDetails>> getAllEventGroupByRoomName(List<String> roomNames) {
		Map<String, List<EventDetails>> events = new LinkedHashMap<String, List<EventDetails>>();
		roomNames.forEach(roomName -> events.put(roomName, findEventByRoomName(roomName)));
		return events;
	}

	@Override
	public List<EventDetails> getPastEvents(String empId) {
		return eventRepository.findPastEvents(empId, new Date());
	}

	@Override
	public List<EventDetails> getFutureEvents(String empId) {
		return eventRepository.findFutureEvents(empId, new Date());
	}

	@Override
	public boolean cancelEvent(String id) {
		if (!existsById(id))
			return false;
		eventRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean existsById(String id) {
		return eventRepository.existsById(id);
	}

	@Override
	public Event findById(String id) {
		return eventRepository.findById(id).orElse(null);
	}

}
