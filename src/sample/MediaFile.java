package sample;

public class MediaFile {
    private int idMedia;
    public String Mname;
    public int Msize;
    public String Mlink;
    public String Mtype;
    private int idNote;

    public int getIdMedia() {
        return idMedia;
    }

    public void setIdMedia(int idMedia) {
        this.idMedia = idMedia;
    }

    public String getMname() {
        return Mname;
    }

    public void setMname(String mname) {
        Mname = mname;
    }

    public int getMsize() {
        return Msize;
    }

    public void setMsize(int msize) {
        Msize = msize;
    }

    public String getMlink() {
        return Mlink;
    }

    public void setMlink(String mlink) {
        Mlink = mlink;
    }

    public String getMtype() {
        return Mtype;
    }

    public void setMtype(String mtype) {
        Mtype = mtype;
    }

    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public MediaFile(int idMedia, String mname, int msize, String mlink, String mtype, int idNote) {
        this.idMedia = idMedia;
        Mname = mname;
        Msize = msize;
        Mlink = mlink;
        Mtype = mtype;
        this.idNote = idNote;
    }
}
