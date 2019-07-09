package org.jeavio.meetin.eventManager.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jeavio.meetin.eventManager.dao.EventRepository;
import org.jeavio.meetin.eventManager.dto.EventDTO;
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
		if (!checkSlotAvailability(newEvent.getRoomName(), newEvent.getStart(), newEvent.getEnd()))
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
					&& (end.after(startDate));
			boolean condition2 = (start.before(endDate))
					&& (end.after(endDate) || end.equals(endDate));
			boolean condition3 = (start.after(startDate) || start.equals(startDate))
					&& (end.before(endDate) || end.equals(endDate));
			if (condition1 || condition2 || condition3)
				return false;
		}

		return true;
	}

	@Override
	public List<Event> findEventByRoomName(String roomName) {
		List<Event> events = eventRepository.findAllByRoomName(roomName);
		return events;
	}

	@Override
	public List<EventDTO> findEventByEmpId(String empId) {
		return eventRepository.findAllByEmpId(empId);
	}

	@Override
	public Map<String, List<EventDTO>> getAllEventGroupByRoomName(List<String> roomNames) {
		Map<String, List<EventDTO>> events = new LinkedHashMap<String, List<EventDTO>>();
//		roomNames.forEach(roomName -> events.put(roomName, findEventByRoomName(roomName)));
		return events;
	}

	@Override
	public List<EventDTO> getPastEvents(String empId) {
		return eventRepository.findPastEvents(empId, new Date());
	}

	@Override
	public List<EventDTO> getFutureEvents(String empId) {
		return eventRepository.findFutureEvents(empId, new Date());
	}

	@Override
	public EventDTO cancelEvent(String id) {
		if (!existsById(id))
			return null;
		EventDTO event = eventRepository.findEventById(id);
		eventRepository.deleteById(id);
		return event;
 	}

	@Override
	public boolean existsById(String id) {
		return eventRepository.existsById(id);
	}

	@Override
	public Event findById(String id) {
		return eventRepository.findById(id).orElse(null);
	}

	@Override
	public boolean checkSlotAvailability(String eventId, String roomName, Date start, Date end) {
		List<Event> events = eventRepository.findAllEventsByRoomName(roomName);
		for (Event event : events) {
			if (!event.getId().equals(eventId)) {
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
		}

		return true;
	}

	@Override
	public boolean modifyEvent(EventDTO modifiedEvent) {
		String eventId = modifiedEvent.getId();
		if(!existsById(eventId) || !checkSlotAvailability(eventId,modifiedEvent.getRoomName(),modifiedEvent.getStart(),modifiedEvent.getEnd()))
			return false;
		Event event = findById(eventId);
		event.setTitle(modifiedEvent.getTitle());
		event.setAgenda(modifiedEvent.getAgenda());
		event.setStart(modifiedEvent.getStart());
		event.setEnd(modifiedEvent.getEnd());
		event.setRoomName(modifiedEvent.getRoomName());
		event.setRoomSpecifications(modifiedEvent.getRoomSpecifications());
		event.setMembers(modifiedEvent.getMembers());
		
		eventRepository.save(event);
		return true;
	}

}
