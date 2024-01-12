package com.wordcloud.worker.repository;

import com.wordcloud.worker.model.UserToken;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class UserTokenRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public UserToken postToken(UserToken token) {
        UserToken existingToken = getTokenByText(token.getToken());

        if (existingToken == null) {
            em.persist(token);
        }

        return token;
    }

    public UserToken getTokenByText(String tokenText) {
        TypedQuery<UserToken> query = em.createQuery(
                "select ut from UserToken ut where ut.token = :token",
                UserToken.class
        );

        query.setParameter("token", tokenText);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
