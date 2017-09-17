package com.swayam.demo.jpa.one2many.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swayam.demo.jpa.one2many.dao.WordDao;

@Service
public class WordServiceImpl implements WordService {

	private final WordDao wordDao;

	@Autowired
	public WordServiceImpl(WordDao wordDao) {
		this.wordDao = wordDao;
	}

	@Transactional
	@Override
	public void save(String word) {
		wordDao.save(word);
	}

}
