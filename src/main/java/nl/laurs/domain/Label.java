package nl.laurs.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 * A Label has a name and a collection of {@link Release}s
 *
 * @author: Maarten
 */
@Entity
public class Label extends AbstractDiscogsEntity {
    private String name;

    @OneToMany(mappedBy = "label", fetch = FetchType.LAZY)
    private Set<Release> releases;

    public Label() {
        releases = new HashSet<Release>();
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
}
