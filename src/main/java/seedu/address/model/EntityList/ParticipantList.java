package seedu.address.model.EntityList;

import java.util.ArrayList;
import java.util.List;
import seedu.address.model.Entity.Entity;
import seedu.address.model.Entity.ID;
import seedu.address.model.Entity.Participant;
import seedu.address.model.Entity.PrefixType;

public class ParticipantList implements EntityListInterface {
    private List<Participant> participants;
    private int nextIDSuffix;

    /**
     * Constructor.
     */
    public ParticipantList() {
       this.participants = new ArrayList<>();
       this.nextIDSuffix = 1;
    }

    /**
     * Gets participant by id.
     *
     * @param id
     * @return
     */
    @Override
    public Participant get(ID id) {
        return new Participant("name", "email", "999", this.generateID());
    }

    /**
     * Updates participant by id.
     *
     * @param participant
     * @throws Exception if error while updating.
     */
    @Override
    public void update(Entity participant) throws Exception {
        // TODO: Find by ID then replace.
        participants.add((Participant) participant);
    }

    /**
     * Adds participant to the list.
     *
     * @param participant
     * @throws Exception if there was an error while adding.
     */
    @Override
    public void add(Entity participant) throws Exception {
        // TODO: Find by ID then replace.
        participants.add((Participant) participant);
    }

    /**
     * Deletes participant by ID.
     *
     * @param id
     * @throws Exception if error while deleting.
     */
    @Override
    public void delete(ID id) throws Exception {
        for (Participant p: this.participants) {
            if (p.getId() == id) {
                this.participants.remove(p);
                return;
            }
        }
    }

    /**
     * List the participants.
     *
     * @return List<Participant>
     */
    @Override
    public List<? extends Entity> list() {
        return this.participants;
    }

    /**
     * Checks if a given ID exists.
     *
     * @param id
     * @return boolean
     */
    @Override
    public boolean isContain(ID id) {
        for (Participant p: this.participants) {
            if (p.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates the ID for the object.
     *
     * @return ID
     */
    @Override
    public ID generateID() {
       return new ID(PrefixType.P, this.getNewIDSuffix());
    }

    private int getNewIDSuffix() {
        int next = this.nextIDSuffix;
        this.nextIDSuffix++;
        return next;
    }
}
