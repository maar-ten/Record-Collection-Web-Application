package nl.laurs.discogs.domain;

import java.io.Serializable;

/**
 * @author: Maarten
 */
public class DiscogsReleaseImage implements Serializable {
    private final String uri;
    private final String uri150;
    private byte[] imageData;
    private String imageType;

    public DiscogsReleaseImage(String uri, String uri150) {
        this.uri = uri;
        this.uri150 = uri150;
    }

    public String getUri() {
        return uri;
    }

    public String getUri150() {
        return uri150;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    @Override
    public String toString() {
        return uri;
    }
}
