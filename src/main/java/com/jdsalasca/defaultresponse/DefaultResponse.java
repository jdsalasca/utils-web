package com.jdsalasca.defaultresponse;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.*;

//version 0.0.3 1/2/2023
@Data
@NoArgsConstructor
public class DefaultResponse<T> {

	protected List<T> data = Collections.emptyList();
	protected HttpStatus status = HttpStatus.I_AM_A_TEAPOT;
	protected List<Message> message = Collections.emptyList();
	protected List<Error> error  = Collections.emptyList();
	protected MESSAGETYPES messageType = MESSAGETYPES.INFO;
	protected DATATYPE dataType = DATATYPE.LIST;
	protected Page<T> pageableInformation;

	public DefaultResponse(List<T> data, HttpStatus status, List<Message> message, List<Error> error,
			MESSAGETYPES messageType) {
		super();
		this.data = data;
		this.status = status;
		this.message = message;
		this.error = error;
		this.messageType = messageType;
	}

	public enum MESSAGETYPES {
		INFO("info"), SUCCESS("success"), WARN("warn"), ERROR("error");

		private String value;

		MESSAGETYPES(String value) {
			this.value = value;
		}

		public String value() {
			return this.value;
		}

	}

	public enum DATATYPE {
		LIST("List"), OBJECT("Object");

		private String value;

		DATATYPE(String value) {
			this.value = value;
		}

		public String value() {
			return this.value;
		}
	}

	public enum DEFAULTMESSAGES {
		SUCCESS_MESSAGE("Operación terminada correctamente"), INTERNAL_SERVER_ERROR("Error interno del sistema"),
		NOT_INFO_FOUND_MESSAGE("No se encontro información relacionada"),
		DATA_SAVED_MESSAGE("Información Guardada con éxito!"), NOT_DATA_SAVED_MESSAGE("Información no almacenada"),
		INFO_UPDATED_MESSAGE("Información actualizada con éxito!");

		private String value;

		DEFAULTMESSAGES(String value) {
			this.value = value;

		}

		public String value() {
			return this.value;
		}
	}

	public static <T> ResponseEntity<DefaultResponse<T>> onThrow500ErrorResponse(List<String> errorMessage) {
		DefaultResponse<T> messageResult = new DefaultResponse<>();
		messageResult.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		messageResult.setMessageType(MESSAGETYPES.ERROR);
		messageResult.setMessage(DEFAULTMESSAGES.INTERNAL_SERVER_ERROR.value());
		messageResult.setErrorStringList(errorMessage);
		return new ResponseEntity<>(messageResult, messageResult.catchHttpStatus());

	}

	public static <T> ResponseEntity<DefaultResponse<T>> onThrow500ErrorListResponse(List<Error> errorMessage) {
		DefaultResponse<T> messageResult = new DefaultResponse<>();
		messageResult.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		messageResult.setMessageType(MESSAGETYPES.ERROR);
		messageResult.setMessage(DEFAULTMESSAGES.INTERNAL_SERVER_ERROR.value());
		messageResult.setError(errorMessage);
		return new ResponseEntity<>(messageResult, messageResult.catchHttpStatus());

	}

	public static <T> ResponseEntity<DefaultResponse<T>> onThrow500ErrorResponse(String errorMessage) {
		DefaultResponse<T> messageResult = new DefaultResponse<>();
		messageResult.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		messageResult.setMessageType(MESSAGETYPES.ERROR);
		messageResult.setMessage(DEFAULTMESSAGES.INTERNAL_SERVER_ERROR.value());
		messageResult.setError(errorMessage);
		return new ResponseEntity<>(messageResult, messageResult.catchHttpStatus());
	}

	public static <T> ResponseEntity<DefaultResponse<T>> onThrow400ResponseTypeInfo(String message) {
		DefaultResponse<T> messageResult = new DefaultResponse<>();
		messageResult.setStatus(HttpStatus.BAD_REQUEST);
		messageResult.setMessageType(MESSAGETYPES.INFO);
		messageResult.setMessage(message);
		return new ResponseEntity<>(messageResult, messageResult.catchHttpStatus());

	}

	public static <T> ResponseEntity<DefaultResponse<T>> onThrow400ResponseTypeError(String message) {
		DefaultResponse<T> messageResult = new DefaultResponse<>();
		messageResult.setStatus(HttpStatus.BAD_REQUEST);
		messageResult.setMessageType(MESSAGETYPES.ERROR);
		messageResult.setMessage(message);
		return new ResponseEntity<>(messageResult, messageResult.catchHttpStatus());

	}

	public static <T> ResponseEntity<DefaultResponse<T>> onThrow400ResponseTypeError(List<Message> message) {
		DefaultResponse<T> messageResult = new DefaultResponse<>();
		messageResult.setStatus(HttpStatus.BAD_REQUEST);
		messageResult.setMessageType(MESSAGETYPES.ERROR);
		messageResult.setMessage(message);
		return new ResponseEntity<>(messageResult, messageResult.catchHttpStatus());

	}

	public static <T> ResponseEntity<DefaultResponse<T>> onThrow404Response(String message) {
		DefaultResponse<T> messageResult = new DefaultResponse<>();
		messageResult.setStatus(HttpStatus.NOT_FOUND);
		messageResult.setMessageType(MESSAGETYPES.INFO);
		messageResult.setMessage(message);
		return new ResponseEntity<>(messageResult, messageResult.catchHttpStatus());
	}

	public static <T> ResponseEntity<DefaultResponse<T>> onThrow404ResponseWithData(String message, List<T> data) {
		DefaultResponse<T> messageResult = new DefaultResponse<>();
		messageResult.setStatus(HttpStatus.NOT_FOUND);
		messageResult.setData(data);
		messageResult.setMessageType(MESSAGETYPES.INFO);
		messageResult.setMessage(message);
		return new ResponseEntity<>(messageResult, messageResult.catchHttpStatus());
	}

	public static <T> ResponseEntity<DefaultResponse<T>> onThrow400ResponseBindingResult(BindingResult bindingResult) {
		DefaultResponse<T> messageResult = new DefaultResponse<>();
		List<Message> listErrors = new ArrayList<>();
		for (FieldError error : bindingResult.getFieldErrors()) {
			listErrors.add(new Message(error.getDefaultMessage(), MESSAGETYPES.INFO));
		}
		messageResult.setStatus(HttpStatus.BAD_REQUEST);
		messageResult.setMessageType(MESSAGETYPES.INFO);
		messageResult.setMessage(listErrors);
		return new ResponseEntity<>(messageResult, messageResult.catchHttpStatus());

	}

	public static <T> ResponseEntity<DefaultResponse<T>> onThrow200Response(List<T> data, Page<T> page) {
		DefaultResponse<T> messageResult = new DefaultResponse<>();
		messageResult.setData(data);
		messageResult.setStatus(HttpStatus.OK);
		messageResult.setMessageType(MESSAGETYPES.SUCCESS);
		messageResult.setMessage(DEFAULTMESSAGES.SUCCESS_MESSAGE.value());
		messageResult.setPageableInformation(page);
		return new ResponseEntity<>(messageResult, messageResult.catchHttpStatus());

	}

	public static <T> ResponseEntity<DefaultResponse<T>> onThrow200Response(List<T> data) {
		DefaultResponse<T> messageResult = new DefaultResponse<>();
		messageResult.setData(data);
		messageResult.setStatus(HttpStatus.OK);
		messageResult.setMessageType(MESSAGETYPES.SUCCESS);
		messageResult.setMessage(DEFAULTMESSAGES.SUCCESS_MESSAGE.value());
		return new ResponseEntity<>(messageResult, messageResult.catchHttpStatus());

	}

	public static <T> ResponseEntity<DefaultResponse<T>> onThrow200ResponseListMessageAndErrors(List<T> data,
			List<Message> messages, List<Error> errors) {
		DefaultResponse<T> messageResult = new DefaultResponse<>();
		messageResult.setData(data);
		messageResult.setStatus(HttpStatus.OK);
		messageResult.setMessageType(MESSAGETYPES.SUCCESS);
		messageResult.setMessage(messages);
		messageResult.setError(errors);
		return new ResponseEntity<>(messageResult, messageResult.catchHttpStatus());
	}

	public static <T> ResponseEntity<DefaultResponse<T>> onThrow200ResponseListMessage(List<T> data,
			List<Message> messages) {
		DefaultResponse<T> messageResult = new DefaultResponse<>();
		messageResult.setData(data);
		messageResult.setStatus(HttpStatus.OK);
		messageResult.setMessageType(MESSAGETYPES.SUCCESS);
		messageResult.setMessage(messages);
		return new ResponseEntity<>(messageResult, messageResult.catchHttpStatus());
	}

	public static <T> ResponseEntity<DefaultResponse<T>> onThrow201Response(List<T> data) {
		DefaultResponse<T> messageResult = new DefaultResponse<>();
		messageResult.setData(data);
		messageResult.setStatus(HttpStatus.CREATED);
		messageResult.setMessageType(MESSAGETYPES.SUCCESS);
		messageResult.setMessage(DEFAULTMESSAGES.INFO_UPDATED_MESSAGE.value());
		return new ResponseEntity<>(messageResult, messageResult.catchHttpStatus());

	}

	public static <T> ResponseEntity<DefaultResponse<T>> onThrow201CustomMessage(String string) {
		DefaultResponse<T> messageResult = new DefaultResponse<>();
		messageResult.setStatus(HttpStatus.CREATED);
		messageResult.setMessageType(MESSAGETYPES.SUCCESS);
		messageResult.setMessage(string);
		return new ResponseEntity<>(messageResult, messageResult.catchHttpStatus());
	}

	public static <T> ResponseEntity<DefaultResponse<T>> onThrow200ResponseObjectData(List<T> data) {
		DefaultResponse<T> messageResult = new DefaultResponse<>();
		messageResult.setData(data);
		messageResult.setStatus(HttpStatus.OK);
		messageResult.setDataType(DATATYPE.OBJECT);
		messageResult.setMessageType(MESSAGETYPES.SUCCESS);
		messageResult.setMessage(DEFAULTMESSAGES.SUCCESS_MESSAGE.value());
		return new ResponseEntity<>(messageResult, messageResult.catchHttpStatus());
	}

	public DefaultResponse(List<T> data, HttpStatus status, List<Message> message) {
		super();
		this.data = data;
		this.status = status;
		this.message = message;
	}

	public String getMessageType() {
		return messageType.value();
	}    

	public T getData() {

		if (this.dataType.equals(DATATYPE.OBJECT)) {
			return this.data.get(0);
		} else {
			return castToListOfT();
		}
	}

	@SuppressWarnings("unchecked")
	private <U> U castToListOfT() {
		return (U) this.data;
	}

	public void setErrorStringList(List<String> errorsList) {
		ArrayList<Error> errors = new ArrayList<>();
		for (String errorL : errorsList) {
			errors.add(new Error(errorL, this.messageType));
		}
		this.error = errors;
	}

	public void setError(Error error) {
		this.error = List.of(error);
	}

	public void setError(String error) {
		this.error = List.of(new Error(error, (this.messageType != null) ? this.messageType : MESSAGETYPES.ERROR));
	}

	@JsonSetter
	public void setError(List<Error> error) {
		this.error = error;
	}

	@JsonSetter
	public void setMessage(List<Message> message) {
		this.message = message;
	}

	public void setMessage(Message message) {
		this.message = List.of(message);
	}

	public void setMessage(String message) {

		this.message = List.of(new Message(message, (this.messageType != null) ? this.messageType : MESSAGETYPES.INFO));
	}

	public int getStatus() {
		return status.value();
	}

	public HttpStatus catchHttpStatus() {
		return this.status;
	}

}
