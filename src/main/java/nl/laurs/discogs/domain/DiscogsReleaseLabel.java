package nl.laurs.discogs.domain;

import java.io.Serializable;

/**
 * A release label is unique for a particular release, because it stored the label's category number for that label
 *
 * @author: maarten
 */
public class DiscogsReleaseLabel implements Serializable {
    private final String name;
    private final Integer discogsId;
    private final String catno;

    public DiscogsReleaseLabel(String name, Integer discogsId, String catno) {
        this.name = name;
        this.discogsId = discogsId;
        this.catno = catno;
    }

    public String getName() {
        return name;
    }

    public Integer getDiscogsId() {
        return discogsId;
    }

    public String getCatno() {
        return catno;
    }

    @Override
    public String toString() {
        return name + " (" + discogsId + ")";
    }
}
