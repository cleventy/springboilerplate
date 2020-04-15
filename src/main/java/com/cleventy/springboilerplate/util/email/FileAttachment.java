package com.cleventy.springboilerplate.util.email;

public class FileAttachment {
	private String url;
	private String name;
	public FileAttachment(String url, String name) {
		super();
		this.url = url;
		this.name = name;
	}
	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
