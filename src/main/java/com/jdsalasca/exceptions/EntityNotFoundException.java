package com.jdsalasca.exceptions;

import java.io.IOException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@Getter @Setter @RequiredArgsConstructor
public class EntityNotFoundException  extends IOException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String message;
	@Override
	public String getLocalizedMessage() {
		return this.message;
	}

}
