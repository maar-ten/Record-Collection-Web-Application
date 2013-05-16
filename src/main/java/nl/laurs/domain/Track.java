package nl.laurs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * A track has a release and a position on that release. A track isn't an entity in the Discogs database
 *
 * @author: Maarten
 */
@Entity
public class Track extends AbstractGenericEntity implements Comparable<Track> {

    @ManyToOne
    @JoinColumn(name = "`release`", nullable = false)
    private Release release;

    @Column(length = 10)
    private String position;

    @Column(nullable = false)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Release getRelease() {
        return release;
    }

    public void setRelease(Release release) {
        this.release = release;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int compareTo(Track o) {
        if (this.position != null && o.getPosition() != null) {
            return this.position.compareTo(o.getPosition());
        }
        return this.title.compareTo(o.getTitle());
    }
}
