package nl.laurs.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * A record has a name, a catalog number, and a release date. It has a set of tracks, a set of artists, and a label.
 * There may also be an image of the record's sleeve, and a uri to a video.
 *
 * @author: Maarten
 */
@Entity
@Table(name = "`Release`") // release is a reserved key word of mysql
public class Release extends AbstractDiscogsEntity {

    public static final String TITLE = "title";

    private String title;
    private String catno;
    private Integer year;
    private String imageType;
    private String videoUri;

    @Column(columnDefinition = "mediumblob")
    private byte[] image;

    @OneToMany(mappedBy = "release", fetch = FetchType.LAZY)
    @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN })
    private Set<Track> tracks;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ReleaseArtist", joinColumns = { @JoinColumn(name = "`release`") },
            inverseJoinColumns = { @JoinColumn(name = "artist") })
    @Cascade({ CascadeType.SAVE_UPDATE })
    private Set<Artist> artists;

    @ManyToOne
    @JoinColumn(name = "label")
    @Cascade({ CascadeType.SAVE_UPDATE })
    private Label label;

    public Release() {
        tracks = new HashSet<>();
        artists = new HashSet<>();
    }

    public String getCatno() {
        return catno;
    }

    public void setCatno(String catno) {
        this.catno = catno;
    }

    public Set<Track> getTracks() {
        return Collections.unmodifiableSet(tracks);
    }

    public void addTrack(Track track) {
        this.tracks.add(track);
    }

    public void removeTrack(Track track) {
        this.tracks.remove(track);
    }

    public Set<Artist> getArtists() {
        return Collections.unmodifiableSet(artists);
    }

    public void addArtist(Artist artists) {
        this.artists.add(artists);
    }

    public void removeArtist(Artist artist) {
        this.artists.remove(artist);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
