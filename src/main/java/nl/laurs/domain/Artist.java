package nl.laurs.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * An artist has a name, which cannot be null, and has one or more releases
 *
 * @author: Maarten
 */
@Entity
public class Artist extends AbstractDiscogsEntity {

    public static final String NAME = "name";

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "ReleaseArtist", joinColumns = { @JoinColumn(name = "artist") },
            inverseJoinColumns = { @JoinColumn(name = "`release`") })
    private Set<Release> releases;

    public Artist() {
        releases = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Release> getReleases() {
        return Collections.unmodifiableSet(releases);
    }

    @Override
    public String toString() {
        return getName();
    }

    public void addRelease(Release release) {
        releases.add(release);
    }

    public void removeRelease(Release release) {
        releases.remove(release);
    }
}
