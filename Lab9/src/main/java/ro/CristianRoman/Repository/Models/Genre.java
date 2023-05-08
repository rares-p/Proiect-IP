package ro.CristianRoman.Repository.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "GENRES")
public class Genre {
    //int id, String name

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long Id;

    public String Name;

    @ManyToMany
    public List<Album> album;
}
