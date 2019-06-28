import com.mpatric.mp3agic.*;

import java.io.IOException;

public class ID3Tags {

    private final String titel;
    private final String album;
    private final String artist;
    private final String track;
    private final String genre;
    private final String year;


    public ID3Tags(ID3v1 id3v1) {
        this.titel = emptyString(id3v1.getTitle());
        this.album = emptyString(id3v1.getAlbum());
        this.artist = emptyString(id3v1.getArtist());
        this.track = emptyString(id3v1.getTrack());
        this.genre = emptyString(id3v1.getGenreDescription());
        this.year = emptyString(id3v1.getYear());
    }

    public ID3Tags(ID3v2 id3v2) {
        this.titel = emptyString(id3v2.getTitle());
        this.album = emptyString(id3v2.getAlbum());
        this.artist = emptyString(id3v2.getArtist());
        this.track = emptyString(id3v2.getTrack());
        this.genre = emptyString(id3v2.getGenreDescription());
        this.year = emptyString(id3v2.getYear());
    }


    private String emptyString(String input) {
        if (input == null || input.isEmpty()) {
            input = "";
            return input;
        } else {
            return input;
        }


    }

    public String getTitel() {
        return titel;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getTrack() {
        return track;
    }

    public String getGenre() {
        return genre;
    }

    public String getYear() {
        return year;
    }
}
