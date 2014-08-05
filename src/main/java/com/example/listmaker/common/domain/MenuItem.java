package com.example.listmaker.common.domain;


public class MenuItem
{
	private String label;
	private String url;

	public MenuItem(String label, String url)
	{
		this.label = label;
		this.url = url;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getLabel()
	{
		return label;
	}
}
