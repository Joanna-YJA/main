package seedu.address.ui.EntityListPanel;

import seedu.address.model.entity.Entity;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of persons.
 */
public abstract class EntityListPanel extends UiPart<Entity>{

    public EntityListPanel(String fxml) {
        super(fxml);
    }
}