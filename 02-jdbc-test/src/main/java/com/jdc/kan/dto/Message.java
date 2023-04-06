package com.jdc.kan.dto;

public record Message(
		int id,
		String name,
		String course,
		String address,
		String phNumber,
		String email
		) {
	
	public Message(String name,String course,String address,String phNumber,String email) {
		this(0,name,course,address,phNumber,email);
	}
	
	public Message cloneWithId(int id) {
		return new Message(id,this.name,this.course,this.address,this.phNumber,this.email);
	}

}
