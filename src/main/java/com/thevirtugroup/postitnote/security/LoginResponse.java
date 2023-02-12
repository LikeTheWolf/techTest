package com.thevirtugroup.postitnote.security;

import java.util.ArrayList;

import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.model.User;

class LoginResponse {
    private boolean success;
    private User user;
    private ArrayList<Note> notes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public ArrayList<Note> getNotes() {
    	return this.notes;
    }
    
    public void setNotes(ArrayList<Note> notes) {
    	this.notes = notes;
    }
}