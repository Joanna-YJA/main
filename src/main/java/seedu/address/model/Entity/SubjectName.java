package seedu.address.model.Entity;

public enum SubjectName {
    PLACEHOLDER("placeholder");

    private final String subjectNameString;

    private SubjectName(String subjectNameString) {
        this.subjectNameString = subjectNameString;
    }

    @Override
    public String toString() {
        return this.subjectNameString;
    }

    public String toStorageValue(){
        return this.toString();
}
}
