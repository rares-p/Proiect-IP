package ro.CristianRoman.Repository.Models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ALBUMS")
public class Album implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    public Integer id;

    public int release_year;

    public String title;

    @OneToOne(mappedBy = "album")
    public Artist artist;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "album")
    public List<Genre> genres;


}
