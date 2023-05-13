package com.jdsalasca;

import java.util.Collections;

import com.jdsalasca.defaultresponse.DefaultResponse;


public class UtilsApplication {

	public static void main(String[] args) {
		System.out.println(DefaultResponse.onThrow200Response(Collections.emptyList()));
		

	}

}
