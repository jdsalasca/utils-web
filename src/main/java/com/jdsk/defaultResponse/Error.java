package com.jdsk.defaultResponse;




import com.jdsk.defaultResponse.DefaultResponse.MESSAGETYPES;

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
