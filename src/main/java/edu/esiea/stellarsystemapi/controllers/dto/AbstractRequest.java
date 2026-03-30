package edu.esiea.stellarsystemapi.controllers.dto;
 
import edu.esiea.stellarsystemapi.controllers.dto.error.ResourceType;
 
public class AbstractRequest {
	private final ResourceType resourceType;
	
	public AbstractRequest(ResourceType resourceType) {
		this.resourceType = resourceType;
	}
	
	public ResourceType getResourceType() {
		return this.resourceType;
	}
}