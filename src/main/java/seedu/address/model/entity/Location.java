package seedu.address.model.entity;

import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Location {
    private final int tableNumber;
    private Team team;

    public static final String MESSAGE_CONSTRAINTS_INVALID_TEAM = "Team should be an instance of Team object";

    public static final String MESSAGE_CONSTRAINTS_INVALID_TABLE_NUMBER = "Table number should be an integer"
            + "and adhere to the following constraints:\n"
            + "1. The tableNumber can be any digit.\n"
            + "The tableNumber must:\n"
            + "    -be at least one digit long\n"
            + "    -contain only digits from 0 to 9\n";

    private static final String TABLE_NUMBER_REGEX = "^\\d+$";


    /**
     * Constructor without team.
     * Constructs an {@code Location}.
     *
     * @param tableNumber Table that team is seated on.
     */
    public Location(int tableNumber) {
        requireNonNull(tableNumber);
        checkArgument(isValidNumber(tableNumber), MESSAGE_CONSTRAINTS_INVALID_TABLE_NUMBER);
        this.tableNumber = tableNumber;
    }

    /**
     * Returns if a given tableNumber is a valid number.
     *
     * @param tableNumber tableNumber that team is seated on.
     * @return boolean whether test is in valid tableNumber format.
     */
    public static boolean isValidNumber(int tableNumber){
        return Integer.toString(tableNumber).matches(TABLE_NUMBER_REGEX);
    }

    // Getter

    public int getTableNumber() {
        return tableNumber;
    }

    public Team getTeam() {
        return team;
    }

    // Setter


    public void setTeam(Team team) {
        requireNonNull(team);
        checkArgument(team instanceof  Team, MESSAGE_CONSTRAINTS_INVALID_TEAM);
        this.team = team;
    }

    /**
     * Constructor with team.
     * Constructs a new {@code Location}.
     *
     * @param tableNumber Table that team is seated at.
     * @param team Team that will seat at the table.
     */
    public Location(int tableNumber, Team team) {
        this(tableNumber);
       this.setTeam(team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tableNumber, this.team);
    }

    @Override
    public boolean equals(Object other) {
        Location otherLocation = ((Location) other);
        return otherLocation == this |
                (otherLocation.getTeam() == this.getTeam()
                && otherLocation.getTableNumber() == this.getTableNumber());
    }

    /**
     * Returns string representation of object.
     *
     * @return Location in string format.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" Table Number: ")
                .append(getTableNumber())
                .append(" Team: ")
                .append(getTeam());
        return builder.toString();
    }

    /**
     * Returns string representation of object, for storage.
     *
     * @return Location in string format.
     */
    public String toStorageValue(){
        return this.toString();
    }
}
