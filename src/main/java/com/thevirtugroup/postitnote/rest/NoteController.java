package com.thevirtugroup.postitnote.rest;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.repository.NotesRepository;

/**
 */
@RestController
public class NoteController
{
	
	@RequestMapping(value="/api/saveNote")
	public static ResponseEntity saveNote(@RequestBody Note note) {		
		ArrayList<Note> notes = NotesRepository.saveNote(note);
		return new ResponseEntity(notes, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/deleteNote")
	public static ResponseEntity deleteNote(@RequestBody Note note) {	
		ArrayList<Note> notes = NotesRepository.deleteNote(note);
		return new ResponseEntity(notes, HttpStatus.OK);
	}
}
