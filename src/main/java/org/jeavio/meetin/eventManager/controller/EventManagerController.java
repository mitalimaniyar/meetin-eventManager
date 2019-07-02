package org.jeavio.meetin.eventManager.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jeavio.meetin.eventManager.dto.EventDTO;
import org.jeavio.meetin.eventManager.dto.EventDetails;
import org.jeavio.meetin.eventManager.model.Event;
import org.jeavio.meetin.eventManager.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventManagerController {

	@Autowired
	EventService eventService;

	@PostMapping("/api/events/all")
	public Map<String, List<EventDetails>> getEventsByRooms(@RequestBody Map<String, List<String>> body) {
		List<String> roomNames = body.get("roomNames");
		return eventService.getAllEventGroupByRoomName(roomNames);
	}

	@PostMapping("/api/events/checkavailability")
	public boolean checkSlotAvailability(@RequestBody EventDTO body) {
		String roomName = body.getRoomName();
		Date start = body.getStart();
		Date end = body.getEnd();
		if(roomName==null || start==null || end==null)
			return false;
		return eventService.checkSlotAvailability(roomName, start, end);
	}

	@PostMapping("/api/events/checkavailability/modify")
	public boolean checkModifiedSlotAvailability(@RequestBody EventDTO body) {
		String id = body.getId();
		String roomName = body.getRoomName();
		Date start = body.getStart();
		Date end = body.getEnd();
		if(id==null || roomName==null || start==null || end==null)
			return false;
		return eventService.checkSlotAvailability(id, roomName, start, end);
	}

	@PostMapping("/api/events/room")
	public List<EventDetails> getEventByRoomName(@RequestBody Map<String, String> body) {
		String roomName = body.get("roomName");
		return eventService.findEventByRoomName(roomName);
	}

	@PostMapping("/api/events/my")
	public List<EventDetails> getUserEvents(@RequestBody Map<String, String> body) {
		String empId = body.get("empId");
		return eventService.findEventByEmpId(empId);
	}

	@PostMapping("/api/events/my/past")
	public List<EventDetails> getUserPastEvents(@RequestBody Map<String, String> body) {
		String empId = body.get("empId");
		return eventService.getPastEvents(empId);
	}

	@PostMapping("/api/events/my/future")
	public List<EventDetails> getUserFutureEvents(@RequestBody Map<String, String> body) {
		String empId = body.get("empId");
		return eventService.getFutureEvents(empId);
	}

	@DeleteMapping("/api/events")
	public EventDetails cancelEvent(@RequestBody Map<String, String> body) {
		String id = body.get("id");
		EventDetails canceledEvent = eventService.cancelEvent(id);
		return canceledEvent;
	}

	@PostMapping("/api/events")
	public boolean addEvent(@RequestBody EventDTO newEvent) {
		return eventService.addEvent(newEvent);
	}

	@GetMapping("/api/events/exists/{eventId}")
	public boolean existEvent(@PathVariable(name = "eventId") String eventId) {
		return eventService.existsById(eventId);
	}

	@GetMapping("/api/events/info/{eventId}")
	public Event getEventById(@PathVariable(name = "eventId") String eventId) {
		return eventService.findById(eventId);
	}

	@PutMapping("/api/events")
	public boolean modifyEvent(@RequestBody EventDTO modifiedEvent) {
		return eventService.modifyEvent(modifiedEvent);
	}
}
