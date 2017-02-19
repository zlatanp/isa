package com.example.dto.hellpers;

public class ChangePasswordDTO {

	public String email;
	public String stara;
	public String nova;
	
	public ChangePasswordDTO(){}
	
	
	public ChangePasswordDTO(String email, String stara, String nova) {
		super();
		this.email = email;
		this.stara = stara;
		this.nova = nova;
	}


	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStara() {
		return stara;
	}
	public void setStara(String stara) {
		this.stara = stara;
	}
	public String getNova() {
		return nova;
	}
	public void setNova(String nova) {
		this.nova = nova;
	}
	
	
}
