package nl.laurs.view.model;

import org.apache.wicket.model.IModel;

/**
 * @author: ML
 */
public abstract class NonDetachableModel<T> implements IModel<T> {
    public void detach() {
        //noop
    }
}
