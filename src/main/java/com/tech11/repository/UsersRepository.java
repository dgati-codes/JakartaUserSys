package com.tech11.repository;

import com.tech11.model.Users;
import com.tech11.queries.UserQueries;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsersRepository {
	

	@PersistenceContext
    private EntityManager entityManager;

    
    public List<Users> findAll(int page, int size) {
        int firstResult = (page - 1) * size;
        return entityManager.createQuery(UserQueries.FIND_ALL_USERS, Users.class)
                .setFirstResult(firstResult)
                .setMaxResults(size)
                .getResultList();
    }
    
    
    public Optional<Users> findById(Long id) {
        try {
            Users user = entityManager.createQuery(UserQueries.FIND_USER_BY_ID, Users.class)
                                      .setParameter("id", id)
                                      .getSingleResult();
            return Optional.ofNullable(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

   

    @Transactional
    public Users save(Users user) {
        if (user.getId() == null) {
            entityManager.persist(user);
        } else {
            user = entityManager.merge(user);
        }
        return user;
    }

    public void deleteById(Long id) {
        Users user = entityManager.find(Users.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    public boolean existsById(Long id) {
        return entityManager.createQuery(UserQueries.EXISTS_USER_BY_ID, Long.class)
                .setParameter("id", id)
                .getSingleResult() > 0;
    }
    public long countAll() {
        return entityManager.createQuery("SELECT COUNT(u) FROM Users u", Long.class)
                .getSingleResult();
    }
}
