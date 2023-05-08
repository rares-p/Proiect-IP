package ro.CristianRoman.Repository.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "ARTISTS")
public class Artist
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    public Integer id;

    public String Name;
}
