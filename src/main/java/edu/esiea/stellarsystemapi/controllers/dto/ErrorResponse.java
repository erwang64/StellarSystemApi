package edu.esiea.stellarsystemapi.controllers.dto;

import edu.esiea.stellarsystemapi.controllers.dto.error.ResourceType;

public class ErrorResponse {
	private String message;
	private ResourceType resourceType;
	private int id;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ResourceType getResourceType() {
		return resourceType;
	}
	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
