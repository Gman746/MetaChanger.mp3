import com.mpatric.mp3agic.*;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.io.FileFilter;

public class Mp3Application {


    public Mp3Application() throws InvalidDataException, IOException, UnsupportedTagException {

    }


    public static void main(String[] args) throws InvalidDataException, IOException, UnsupportedTagException, NotSupportedException {
        Mp3Application mp3Application = new Mp3Application();
        List<Path> files = mp3Application.pathFinder();
        for (Path path : files) {

            Mp3File mp3file = new Mp3File(path.toAbsolutePath().toString());
            System.out.println(path);
            int pathLength = path.toString().length() - 4;
            String oldName = path.toString();
            String newName = path.toString().substring(0, pathLength) + "^.mp3";
            System.out.println(newName);
            ID3Tags tags = mp3Application.idVersionControl(mp3file);
            mp3file.removeId3v2Tag();
            mp3file.removeId3v1Tag();
            Mp3Application.tagFiller(mp3file,tags);
            mp3file.save(newName);
            path.toFile().delete();
            File datei = new File(newName);
            datei.renameTo(new File (oldName));



        }
    }

    public ID3Tags idVersionControl(Mp3File mp3file) throws InvalidDataException, IOException, UnsupportedTagException, NotSupportedException {
        final ID3Tags tags;
        if (mp3file.hasId3v1Tag()) {
            System.out.println("Found IDTag Version: 1");
            tags = new ID3Tags(mp3file.getId3v1Tag());
            //mp3file.removeId3v1Tag();
        } else if (mp3file.hasId3v2Tag()) {
            System.out.println("Found IDTag Version: 2");
            tags = new ID3Tags(mp3file.getId3v2Tag());
            //mp3file.removeId3v2Tag();
        } else {
            throw new RuntimeException("Invalid IDTag Version");
        }

        return tags;
    }

    public List<Path> pathFinder() throws IOException {
        List<Path> files = Files.find(Paths.get("src/Songs"), Integer.MAX_VALUE, (filePath, fileAttr) -> filePath.toString().endsWith(".mp3")).collect(Collectors.toList());


        return files;

    }


    private static void tagFiller(Mp3File mp3file, ID3Tags input) {
        ID3v2 id3v2Tag = new ID3v24Tag();
        ID3v1 id3v1Tag = new ID3v1Tag();


        String titel = input.getTitel();
        String album = input.getAlbum();
        String artist = input.getArtist();
        String genre = input.getGenre();
        String track = input.getTrack();
        String year = input.getYear();



        id3v2Tag.setTitle(titel);
        id3v2Tag.setAlbum(album);
        id3v2Tag.setArtist(artist);
        id3v2Tag.setGenreDescription(genre);
        id3v2Tag.setTrack(track);
        id3v2Tag.setYear(year);

        id3v1Tag.setTitle(titel);
        id3v1Tag.setAlbum(album);
        id3v1Tag.setArtist(artist);
        id3v1Tag.setTrack(track);
        id3v1Tag.setYear(year);

        mp3file.setId3v2Tag(id3v2Tag);
        mp3file.setId3v1Tag(id3v1Tag);

    }


    public static Path moveFile(Path oldFile, String newName){

        // the magic is done by Path.resolve(...)
        return oldFile.getParent().resolve(newName);
    }




}
