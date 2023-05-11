package com.jdsalasca.defaultresponse;
import com.jdsalasca.defaultresponse.DefaultResponse.MESSAGETYPES;

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
