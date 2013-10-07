package nl.laurs.domain.Mothers;

import nl.laurs.domain.Label;

/**
 * Creates label objects for testing.
 *
 * @author: Maarten
 * @see <a href="http://martinfowler.com/bliki/ObjectMother.html">ObjectMother by Fowler</a>
 */
public class LabelMother {
    private static int nextId = 1;
    private static int nextDiscogsId = 1;

    public static Label createExisting() {
        Label label = new Label();
        label.setName("labelName" + nextId);
        label.setId(nextId++);
        label.setDiscogsId(nextDiscogsId++);
        return label;
    }
}
