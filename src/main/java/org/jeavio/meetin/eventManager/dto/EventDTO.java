package org.jeavio.meetin.eventManager.dto;

import java.util.Date;
import java.util.List;

public class EventDTO {

	private String id;
	private String title;
	private String agenda;
	private String roomName;
	private String roomSpecifications;
	private Date start;
	private Date end;
	private UserInfo organizer;
	private List<MemberInfo> members;

	public EventDTO() {
	}

	public EventDTO(String title, String agenda, String roomName, String roomSpecifications, Date start, Date end,
			UserInfo organizer, List<MemberInfo> members) {
		
		this.title = title;
		this.agenda = agenda;
		this.roomName = roomName;
		this.roomSpecifications = roomSpecifications;
		this.start = start;
		this.end = end;
		this.organizer = organizer;
		this.members = members;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAgenda() {
		return agenda;
	}

	public void setAgenda(String agenda) {
		this.agenda = agenda;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomSpecifications() {
		return roomSpecifications;
	}

	public void setRoomSpecifications(String roomSpecifications) {
		this.roomSpecifications = roomSpecifications;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public UserInfo getOrganizer() {
		return organizer;
	}

	public void setOrganizer(UserInfo organizer) {
		this.organizer = organizer;
	}

	public List<MemberInfo> getMembers() {
		return members;
	}

	public void setMembers(List<MemberInfo> members) {
		this.members = members;
	}

}
