package com.jdsk.defaultResponse;
import com.jdsk.defaultResponse.DefaultResponse.MESSAGETYPES;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message{
	
    private String messageDesc;
    private MESSAGETYPES messageType;

	public String getMessageType() {
		return messageType.value();
	} 
}
