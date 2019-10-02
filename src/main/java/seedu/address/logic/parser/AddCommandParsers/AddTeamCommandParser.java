package seedu.address.logic.parser.AddCommandParsers;

import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIALISATION;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import seedu.address.logic.commands.addcommand.AddCommand;
import seedu.address.logic.commands.addcommand.AddTeamCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Location;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.ProjectType;
import seedu.address.model.entity.Score;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;
import seedu.address.model.entitylist.TeamList;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddTeamCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTeamCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SPECIALISATION, PREFIX_PROJECT_NAME,
                        PREFIX_PROJECT_TYPE, PREFIX_LOCATION);

        Name name = AlfredParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        SubjectName subject = AlfredParserUtil.parseSubject(argMultimap.getValue(PREFIX_SPECIALISATION).get());
        Name projectName = AlfredParserUtil.parseName(argMultimap.getValue(PREFIX_PROJECT_NAME).get());
        ProjectType projectType = AlfredParserUtil.parseProjectType(argMultimap.getValue(PREFIX_PROJECT_TYPE).get());
        Location location = AlfredParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());
        Id id = new TeamList().generateID();
        List<Participant> participants = new LinkedList<>();
        Score score = new Score(0);
        Optional<Mentor> mentor = Optional.empty();

        Team team = new Team(id, name, participants, mentor, subject, score, projectName, projectType, location);

        return new AddTeamCommand(team);
    }
}
