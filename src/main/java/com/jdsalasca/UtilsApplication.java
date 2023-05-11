package com.jdsalasca;

import com.jdsalasca.defaultresponse.DefaultResponse;

import edu.emory.mathcs.backport.java.util.Collections;

public class UtilsApplication {

	public static void main(String[] args) {
		System.out.println(DefaultResponse.onThrow200Response(Collections.emptyList()));
		

	}

}
