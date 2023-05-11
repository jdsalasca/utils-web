package com.jdsk;

import com.jdsk.defaultResponse.DefaultResponse;

import edu.emory.mathcs.backport.java.util.Collections;

public class UtilsApplication {

	public static void main(String[] args) {
		System.out.println(DefaultResponse.onThrow200Response(Collections.emptyList()));
		

	}

}
