package nl.laurs.domain;

import javax.persistence.MappedSuperclass;

/**
 * Extends the abstract entity for classes that have a copy in the Discogs database
 *
 * @author: Maarten
 */
@MappedSuperclass
abstract class AbstractDiscogsEntity extends AbstractGenericEntity {
    public static final String DISCOGS_ID = "discogsId";

    private Integer discogsId;

    public Integer getDiscogsId() {
        return discogsId;
    }

    public void setDiscogsId(Integer discogsId) {
        this.discogsId = discogsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbstractDiscogsEntity that = (AbstractDiscogsEntity) o;

        if (!discogsId.equals(that.discogsId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return discogsId.hashCode();
    }
}
