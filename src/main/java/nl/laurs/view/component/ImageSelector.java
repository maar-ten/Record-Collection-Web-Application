package nl.laurs.view.component;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Form component for selecting one image from a list of images.
 * <p/>
 * For now this component only supports a choice list of string URI's. Each uri will be used as the src attribute for
 * the img tag.
 *
 * @author: maarten
 */
public abstract class ImageSelector<T extends Serializable> extends Panel {
    private final IModel<? extends List<? extends T>> choiceListModel;
    private boolean nullValid = false;

    protected abstract String getUri(T object);

    public ImageSelector(String id, IModel<? extends T> valueModel,
            IModel<? extends List<? extends T>> choiceListModel) {
        super(id, valueModel);

        if (choiceListModel == null) {
            throw new IllegalStateException("choiceListModel cannot be null");
        }
        this.choiceListModel = choiceListModel;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onInitialize() {
        super.onInitialize();

        final RadioGroup<T> radioGroup = new RadioGroup<>("radioGroup", (IModel<T>) getDefaultModel());
        radioGroup.add(createListView(choiceListModel, radioGroup));
        radioGroup.add(createNullOption(radioGroup));
        radioGroup.setRenderBodyOnly(false);

        Form<String> form = new Form<>("imageSelectorForm", (IModel<String>) getDefaultModel());
        form.add(radioGroup);
        add(form);
    }

    private ListView<? extends T> createListView(final IModel<? extends List<? extends T>> choiceListModel,
            final RadioGroup<? extends T> radioGroup) {
        return new ListView<T>("imageList", choiceListModel) {
            @Override
            protected void populateItem(ListItem<T> item) {
                T modelObject = item.getModelObject();
                item.add(new WebMarkupContainer("image").add(new AttributeAppender("src", getUri(modelObject))));
                item.add(new Radio<>("radio", new Model<>(modelObject), (RadioGroup<T>) radioGroup));
            }
        };
    }

    private Component createNullOption(RadioGroup<? extends T> radioGroup) {
        if (!isNullValid()) {
            return new EmptyPanel("nullOption").setRenderBodyOnly(true);
        }

        Fragment nullOption = new Fragment("nullOption", "nullOptionFragment", ImageSelector.this);
        nullOption.add(new Radio<>("nullRadio", new Model<T>(null), (RadioGroup<T>) radioGroup));
        nullOption.add(new Label("nullCaption", getNullOptionLabel()));
        nullOption.setRenderBodyOnly(false);
        return nullOption;
    }

    /**
     * Determines whether or not the null value should be included in the list of choices.
     * <p/>
     * If set to true, a radio button will be added to the list with some text that is taken from {@link
     * #getNullOptionLabel()}
     *
     * @param nullValid whether null is a valid value
     * @return this for chaining
     */
    public ImageSelector setNullValid(boolean nullValid) {
        this.nullValid = nullValid;
        return this;
    }

    /**
     * @return true when the null value is allowed
     * @see #setNullValid(boolean)
     */
    protected boolean isNullValid() {
        return nullValid;
    }

    /**
     * Determines the text that will be shown next to the radio option for the null value in case {@link
     * #setNullValid(boolean)} returns true
     *
     * @return a model for the text that will be used as the null value choice
     */
    protected IModel<String> getNullOptionLabel() {
        return new Model<>("no image");
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        if (findParent(Form.class) == null) {
            throw new IllegalStateException("Component " + getClass().getName() + " must have a " +
                                            Form.class.getName() + " component above in the hierarchy");
        }
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        choiceListModel.detach();
    }
}
