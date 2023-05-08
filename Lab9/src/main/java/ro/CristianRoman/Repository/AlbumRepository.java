package ro.CristianRoman.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import ro.CristianRoman.Repository.Models.Album;

import java.util.List;

public class AlbumRepository {
    private final EntityManager entityManager;

    public AlbumRepository (EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(Album album) {
        entityManager.getTransaction().begin();
        entityManager.persist(album);
        entityManager.getTransaction().commit();
    }

    public Album findById(Long id) {
        return entityManager.find(Album.class, id);
    }

    public Album findByTitle(String title) {
        return entityManager.createQuery("SELECT Album FROM Album a WHERE a.title LIKE :title", Album.class)
                .setParameter("title", title)
                .getSingleResult();
    }
}
