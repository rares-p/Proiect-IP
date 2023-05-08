package ro.CristianRoman;

import jakarta.persistence.EntityManager;
import ro.CristianRoman.Repository.AlbumRepository;
import ro.CristianRoman.Repository.EntityManagerFactoryUtil;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerFactoryUtil.getEntityManager();
        AlbumRepository albumRepository = new AlbumRepository(entityManager);
        entityManager.close();
    }
}