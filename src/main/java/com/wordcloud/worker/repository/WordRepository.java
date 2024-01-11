package com.wordcloud.worker.repository;

import com.wordcloud.worker.model.Word;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class WordRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Word postWord(Word word) {
        Word existingWord = getWordByText(word.getWord());

        if (existingWord == null) {
            em.persist(word);
        }

        return word;
    }

    private Word getWordByText(String wordText) {
        TypedQuery<Word> query = em.createQuery(
                "select w from Word w where w.word = :word",
                Word.class
        );

        query.setParameter("word", wordText);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
