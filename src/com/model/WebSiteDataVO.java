package com.model;

public class WebSiteDataVO {
	
	private int webId;
	
	public int getWebId() {
		return webId;
	}

	public void setWebId(int webId) {
		this.webId = webId;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getWebSiteData() {
		return webSiteData;
	}

	public void setWebSiteData(String webSiteData) {
		this.webSiteData = webSiteData;
	}

	private String webUrl;
	
	private String webSiteData;

}
