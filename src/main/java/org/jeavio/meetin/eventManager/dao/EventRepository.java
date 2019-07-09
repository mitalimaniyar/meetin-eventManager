package org.jeavio.meetin.eventManager.dao;

import java.util.Date;
import java.util.List;

import org.jeavio.meetin.eventManager.dto.EventDTO;
import org.jeavio.meetin.eventManager.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

	@Query(sort = "{'start':1}")
	List<Event> findAllByRoomName(String roomName);

	List<Event> findAllEventsByRoomName(String roomName);
	
	@Query(value = "{$or:[{'members.empId': ?0 },{'organizer.empId': ?0 }]}", sort = "{'start':1}")
	List<EventDTO> findAllByEmpId(String empId);

	@Query(value = "{$or:[{'members.empId': ?0 },{'organizer.empId': ?0 }],'start':{ '$lte' : ?1 } }", sort = "{'start':1}")
	List<EventDTO> findPastEvents(String empId, Date date);
	
	@Query(value = "{$or:[{'members.empId': ?0 },{'organizer.empId': ?0 }],'start':{ '$gte' : ?1 } }", sort = "{'start':1}")
	List<EventDTO> findFutureEvents(String empId, Date date);

	EventDTO findEventById(String id);

}
