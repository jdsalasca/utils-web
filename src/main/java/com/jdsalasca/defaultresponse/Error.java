package com.jdsalasca.defaultresponse;




import com.jdsalasca.defaultresponse.DefaultResponse.MESSAGETYPES;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    	
        private String message;
        private MESSAGETYPES messageType;

		public String getMessageType() {
			return messageType.value();
		}
}
