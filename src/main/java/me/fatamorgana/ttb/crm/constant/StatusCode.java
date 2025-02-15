package me.fatamorgana.ttb.crm.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusCode {
    ACTIVE("ACT", "Active"),
    INACTIVE("INA", "Inactive"),
    SUSPENDED("SUS", "Suspended"),
    PENDING("PND", "Pending"),
    DELETED("DEL", "Deleted");
	
	StatusCode(String code, String description) {
		this.code = code;
		this.description = description;
	}
	
	private final String code;
	private final String description;
	
	public String getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
	
	@JsonValue
	public String toJson() {
		return description;
	}
	
	@JsonCreator
	public static StatusCode fromJson(String value) {
        for (StatusCode status : StatusCode.values()) {
            if (status.code.equalsIgnoreCase(value) || status.description.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
	}
}
