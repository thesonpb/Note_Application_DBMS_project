package sample;

public class Note {
    private int idNote;
    public String Ntitle;
    public String Ntag;
    public String Ncontent;
    public String NdateCreated;

    public Note(int idNote, String ntitle, String ntag, String ncontent, String ndateCreated) {
        this.idNote = idNote;
        Ntitle = ntitle;
        Ntag = ntag;
        Ncontent = ncontent;
        NdateCreated = ndateCreated;
    }

    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public String getNtitle() {
        return Ntitle;
    }

    public void setNtitle(String ntitle) {
        Ntitle = ntitle;
    }

    public String getNtag() {
        return Ntag;
    }

    public void setNtag(String ntag) {
        Ntag = ntag;
    }

    public String getNcontent() {
        return Ncontent;
    }

    public void setNcontent(String ncontent) {
        Ncontent = ncontent;
    }

    public String getNdateCreated() {
        return NdateCreated;
    }

    public void setNdateCreated(String ndateCreated) {
        NdateCreated = ndateCreated;
    }

    public Note(int idNote, String ntitle, String ndateCreated) {
        this.idNote = idNote;
        Ntitle = ntitle;
        NdateCreated = ndateCreated;
    }
    public Note() {

    }
    public void editTextInNote(String textInTextfield) {
        this.Ncontent = textInTextfield;
    }

}
