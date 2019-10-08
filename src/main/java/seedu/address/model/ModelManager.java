package seedu.address.model;

import java.io.IOException;
import static java.util.Objects.requireNonNull;
import javax.swing.RowFilter;
import seedu.address.commons.exceptions.DataConversionException;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.AlfredException;
import seedu.address.AlfredRuntimeException;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Team;
import seedu.address.model.entitylist.MentorList;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.model.entitylist.ReadOnlyEntityList;
import seedu.address.model.entitylist.TeamList;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.AlfredStorage;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);


    private final UserPrefs userPrefs;


    // EntityLists
    private final ParticipantList participantList;
    private final TeamList teamList;
    private final MentorList mentorList;

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    public static Model initModelManager(AlfredStorage alfredStorage, ReadOnlyUserPrefs userPrefs) throws AlfredException {
        Optional<ParticipantList> optionalParticipantList;
        Optional<MentorList> optionalMentorList;
        Optional<TeamList> optionalTeamList;

        ReadOnlyEntityList participantList;
        ReadOnlyEntityList mentorList;
        ReadOnlyEntityList teamList;
        try {
            optionalParticipantList = alfredStorage.readParticipantList();
            optionalMentorList = alfredStorage.readMentorList();
            optionalTeamList = alfredStorage.readTeamList();
            //If participant list is not present, sample data is used instead.
            //System will be unable to proceed without participantList
            if (!optionalParticipantList.isPresent() || optionalMentorList.isPresent() || optionalTeamList.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample list of entries.");
            }
            //TODO: Create sample entries
            participantList = optionalParticipantList.orElseGet(SampleDataUtil::getSampleParticipantList);
            mentorList = optionalParticipantList.orElseGet(SampleDataUtil::getSampleMentorList;
            teamList = optionalParticipantList.orElseGet(SampleDataUtil::getSampleTeamList;
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty list of entries.");
            participantList = new ParticipantList();
            mentorList = new MentorList();
            teamList = new TeamList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty list of entries.");
            participantList = new ParticipantList();
            mentorList = new MentorList();
            teamList = new TeamList();
        }

        return new ModelManager(participantList, mentorList, teamList, userPrefs);
    }

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    private ModelManager(ReadOnlyEntityList participantList, ReadOnlyEntityList mentorList, ReadOnlyEntityList teamList, ReadOnlyUserPrefs userPrefs) throws AlfredException {
        super();
        requireAllNonNull(participantList, teamList, mentorList, userPrefs);

        logger.fine("Initializing with Participant List: " + participantList
                logger.fine("Initializing with Mentor List" + mentorList);
        logger.fine("Initializing with Team List: " + teamList);
        logger.fine("Initializing with UserPrefs: " + userPrefs);


        this.userPrefs = new UserPrefs(userPrefs);

        this.participantList = addAllParticipants(new ParticipantList(), participantList);
        this.teamList = addAllTeams(new TeamList(), teamList);
        this.mentorList = addAllMentors(new MentorList, mentorList);
    }

    public ModelManager() throws AlfredException {
        this(new ParticipantList(), new MentorList(), new TeamList(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //========== EntityListMethods ===============

    //Tried to use streams, but streams cannot throw checked exceptions
    public ParticipantList addAllParticipants(ParticipantList currList, ReadOnlyEntityList newList) throws AlfredException {

        List newParticipantList = newList.list();
        for(int i = 0; i < newParticipantList.size(); i++){
            currList.add((Participant)newParticipantList.get(i));
        }

        return currList;
    }

    public TeamList addAllTeams(TeamList currList, ReadOnlyEntityList newList) throws AlfredException {

        List newTeamList = newList.list();
        for(int i = 0; i < newTeamList.size(); i++){
            currList.add((Team)newTeamList.get(i));
        }
        return currList;
    }


    public MentorList addAllMentors(MentorList currList, ReadOnlyEntityList newList) throws AlfredException {

        List newTeamList = newList.list();
        for(int i = 0; i < newTeamList.size(); i++){
            currList.add((Mentor)newTeamList.get(i));
        }
        return currList;
    }



    /**
     * Returns the participant list located in the Model Manager.
     *
     * @return ReadableEntityList
     */
    public ReadOnlyEntityList getParticipantList() {
        return this.participantList;
    }

    /**
     * Returns the team list located in the Model Manager.
     *
     * @return ReadableEntityList
     */
    public ReadOnlyEntityList getTeamList() {
        return this.teamList;
    }

    /**
     * Returns the mentor list located in the Model Manager.
     *
     * @return ReadableEntityList
     */
    public ReadOnlyEntityList getMentorList() {
        return this.mentorList;
    }

    //========== Entity Methods =============================

    /* Participant Methods */

    /**
     * Gets the participant by id.
     *
     * @param id
     * @return Participant Object
     * @throws AlfredException if the Participant cannot be found.
     */
    public Participant getParticipant(Id id) throws AlfredException {
        return this.participantList.get(id);
    }

    /**
     * Adds the participant into the list.
     *
     * @param participant
     * @throws AlfredException
     */
    public void addParticipant(Participant participant) throws AlfredException {
        this.participantList.add(participant);
    }

    /**
     * Updates the participant in the list, if any.
     *
     * @param id
     * @param participant
     * @return boolean
     */
    public boolean updateParticipant(Id id, Participant participant) {
        return this.participantList.update(id, participant);
    }

    /**
     * Deletes the participant by id.
     *
     * @param id
     * @return Participant
     */
    public Participant deleteParticipant(Id id) throws AlfredException {
        return this.participantList.delete(id);
    }

    /* Team Methods*/

    /**
     * Gets team by id.
     *
     * @param id
     * @return
     * @throws AlfredException
     */
    public Team getTeam(Id id) throws AlfredException {
        return this.teamList.get(id);
    }

    /**
     * Gets the team by participant id.
     *
     * @param participantId
     * @return Team
     * @throws AlfredException
     */
    public Team getTeamByParticipantId(Id participantId) throws AlfredException {
        List<Team> teams = this.teamList.getSpecificTypedList();
        for (Team t : teams) {
            for (Participant p : t.getParticipants()) {
                if (p.getId() == participantId) {
                    return t;
                }
            }
        }
        throw new AlfredRuntimeException("Team with said participant cannot be found.");
    }

    /**
     * Gets the team by mentor id.
     *
     * @param mentorId
     * @return Team
     * @throws AlfredException
     */
    public Team getTeamByMentorId(Id mentorId) throws AlfredException {
        List<Team> teams = this.teamList.getSpecificTypedList();
        for (Team t : teams) {
            Optional<Mentor> mentor = t.getMentor();
            if (mentor.isPresent()) {
                if (mentor.get().getId() == mentorId) {
                    return t;
                }
            }
        }
        throw new AlfredRuntimeException("Team with said participant cannot be found.");
    }

    /**
     * Updates the team.
     *
     * @param teamId
     * @param updatedTeam
     * @return boolean.
     */
    public boolean updateTeam(Id teamId, Team updatedTeam) {
        return this.teamList.update(teamId, updatedTeam);
    }

    /**
     * Adds the team.
     *
     * @param team
     * @throws AlfredException
     */
    public void addTeam(Team team) throws AlfredException {
        this.teamList.add(team);
    }

    /**
     * Deletes the team.
     *
     * @param id
     * @return Team
     * @throws AlfredException
     */
    public Team deleteTeam(Id id) throws AlfredException {
        return this.teamList.delete(id);
    }

    /* Mentor Methods */

    /**
     * Gets the mentor by id.
     *
     * @param id
     * @return Mentor
     * @throws AlfredException
     */
    public Mentor getMentor(Id id) throws AlfredException {
        return this.mentorList.get(id);
    }

    /**
     * Adds mentor into the list.
     *
     * @param mentor
     * @throws AlfredException
     */
    public void addMentor(Mentor mentor) throws AlfredException {
        this.mentorList.add(mentor);
    }

    /**
     * Updates the mentor.
     *
     * @param id
     * @param updatedMentor
     * @return boolean
     */
    public boolean updateMentor(Id id, Mentor updatedMentor) {
        return this.mentorList.update(id, updatedMentor);
    }

    /**
     * Deletes the mentor.
     *
     * @param id
     * @return Mentor that is deleted
     * @throws AlfredException
     */
    public Mentor deleteMentor(Id id) throws AlfredException {
        return this.mentorList.delete(id);
    }





    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return  userPrefs.equals(other.userPrefs)
                && participantList.equals(other.participantList)
                && teamList.equals(other.teamList)
                && mentorList.equals(other.mentorList);

    }
}
