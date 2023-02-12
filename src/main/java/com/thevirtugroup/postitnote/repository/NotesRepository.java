package com.thevirtugroup.postitnote.repository;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.security.SecurityContext;
import com.thevirtugroup.postitnote.security.SecurityUserWrapper;

@Repository
public class NotesRepository {

	private static ArrayList<Note> notesForDefaultUser;
	private final static Long DEFAULT_USER_ID = 999L;

    public NotesRepository() {
    	notesForDefaultUser = new ArrayList<Note>();
		
		for(int i = 0; i < 5; i++) {
			Note note = new Note();
			note.setName("note test summary " + i);
			note.setText("note test text " + i);
			note.setDate(new Date());
			notesForDefaultUser.add(note);
		}
    }

    public static ArrayList<Note> findByUserId(Long id){
        if (DEFAULT_USER_ID.equals(id)){
            return notesForDefaultUser;
        }
        return null;
    }
    
    public static ArrayList<Note> saveNote(Note note) {
    	final SecurityUserWrapper loggedInUser = SecurityContext.getLoggedInUser();
    	
    	ArrayList<Note> notes = findByUserId(loggedInUser.getId());
    	boolean existing = false;
    	for(int i = 0; i < notes.size(); i++) {
    		Note foundNote = notes.get(i);
    		if(foundNote.getId().equals(note.getId())) {
    			foundNote.setName(note.getName());
    			foundNote.setText(note.getText());
    			notes.set(i, foundNote);
    			existing = true;
    		}
    	}
    	
    	if(!existing) {
    		Note newNote = new Note();
    		newNote.setDate(new Date());
    		newNote.setName(note.getName());
    		newNote.setText(note.getText());
    		notes.add(newNote);
    	}
    	return notes;
    }
    
    public static ArrayList<Note> deleteNote(Note note) {
    	final SecurityUserWrapper loggedInUser = SecurityContext.getLoggedInUser();
    	
    	ArrayList<Note> notes = findByUserId(loggedInUser.getId());
    	for(int i = 0; i < notes.size(); i++) {
    		Note n = notes.get(i);
    		if(n.getId().equals(note.getId())) {
    			notes.remove(i);
    		}
    	}
    	
    	return notes;
    }
}