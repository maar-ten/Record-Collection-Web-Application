package nl.laurs.domain;

import org.junit.Before;
import org.junit.Test;

import nl.laurs.domain.Mothers.ReleaseMother;

import static org.junit.Assert.assertEquals;

/**
 * @author: Maarten
 */
public class LabelTest {
    private Label label;

    @Before
    public void setUp() throws Exception {
        int id = 5;
        int discogsId = 9;
        String name = "someName";

        label = new Label();
        label.setName(name);
        label.setId(id);
        label.setDiscogsId(discogsId);
    }

    @Test
    public void constructLabel() throws Exception {
        assertEquals(5, label.getId().intValue());
        assertEquals(9, label.getDiscogsId().intValue());
        assertEquals("someName", label.getName());
        assertEquals(0, label.getReleases().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnmodifiableSet() throws Exception {
        label.getReleases().add(ReleaseMother.createExisting());
    }
}
