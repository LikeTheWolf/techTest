package com.thevirtugroup.postitnote.model;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import com.thevirtugroup.postitnote.repository.NotesRepository;

public class Note {
	
	private static final int GENERATOR_START_VALUE = 1;
	private static final AtomicInteger generator = new AtomicInteger(GENERATOR_START_VALUE);
	private String name;
	private String text;
	private Date date;
	private long id;
	
	public Note (){
		this.id = generator.getAndIncrement();
	}
	
	public Long getId() {
	    return id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getText() {
		return this.text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
}
