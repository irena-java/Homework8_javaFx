package main.java.sample.service;

import main.java.sample.dao.DaoException;
import main.java.sample.dao.NoteDao;
import main.java.sample.entity.Note;

import java.util.List;

public class NoteService {
    private NoteDao noteDao = new NoteDao();

    public Note create(Note note) throws ServiceException {
        try {
            return noteDao.create(note);
        } catch (DaoException e) {
            throw new ServiceException("failed to save");
        }
    }

    public void delete(Note note) throws ServiceException {
        try {
            noteDao.delete(note);
        } catch (DaoException e) {
            throw new ServiceException("failed to save");
        }
    }

    public List<Note> findAll() throws ServiceException {
        try {
            return noteDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("failed to save");
        }
    }
}