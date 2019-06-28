package org.jeavio.meetin.eventManager.controller;

import java.util.List;
import java.util.Map;

import org.jeavio.meetin.eventManager.dto.EventDTO;
import org.jeavio.meetin.eventManager.dto.EventDetails;
import org.jeavio.meetin.eventManager.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	public boolean checkSlotAvailability(@RequestBody Map<String,String> body) {
		return eventService.checkSlotAvailability(body.get("roomName"),body.get("start"),body.get("end"));
	}
	@GetMapping("/api/events/{roomName}")
	public List<EventDetails> getEventByRoomName(@RequestParam String roomName) {
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
	public boolean cancelEvent(@RequestBody Map<String, String> body) {
		return eventService.cancelEvent(body.get("id"));
	}
	
	@PostMapping("/api/events")
	public boolean addEvent(@RequestBody EventDTO newEvent) {
		return eventService.addEvent(newEvent);
	}
	
	@PostMapping("/api/events/exists")
	public boolean existEvent(@RequestBody Map<String,String> body) {
		return eventService.existsById(body.get("id"));
	}
}
