package com.example.bookmanager.Service.impl;

import com.example.bookmanager.DTO.LibraryBooks;
import com.example.bookmanager.Entity.Library;
import com.example.bookmanager.Service.LibraryService;
import com.example.bookmanager.Mapper.LibraryMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {
    private final LibraryMapper libraryMapper;
    public LibraryServiceImpl(LibraryMapper libraryMapper) {
        this.libraryMapper = libraryMapper;
    }

    @Override
    public List<Library> findAllLibrary() {
        return libraryMapper.findAllLibrary();
    }

    @Override
    public List<LibraryBooks> findAllLibraryBooks() {
        List<Library> libraries = libraryMapper.findAllLibrary();
        List<LibraryBooks> res = new ArrayList<>();
        for (Library lib : libraries) {
            res.add(new LibraryBooks(lib.getId(), lib.getName(), lib.getAddress(), lib.getPhone(), libraryMapper.findAllBooksByLib(lib.getName())));
        }
        return res;
    }
}
