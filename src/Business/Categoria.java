package Business;

import Exceptions.InvalidGenreException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Categoria {
    private int pos;

   Categoria() {
        try {
            this.pos = this.cat_id("Other");
        } catch (InvalidGenreException ignored) {}
    }

    Categoria(int pos) throws InvalidGenreException {
        if (!this.valid_cat(pos))
            throw new InvalidGenreException();
        this.pos = pos;
    }

    Categoria(String genre) throws InvalidGenreException {
        this.pos = this.cat_id(genre);
    }

    String name(int i) throws InvalidGenreException {
        Connection conn = DBConnect.connect();
        try {
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery("SELECT designacao from " +
                    "Categoria where idCategoria = '" + i + "'");
            res.next();
            return res.getString(1);
        } catch (Exception e) {
            throw new InvalidGenreException();
        }
    }

    int cat_id(String name) throws InvalidGenreException {
        Connection conn = DBConnect.connect();
        try {
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery("SELECT idCategoria from " +
                    "Categoria where designacao = '" + name + "'");
            res.next();
            return res.getInt(1);
        } catch (Exception e) {
            throw new InvalidGenreException();
        }
    }

    boolean valid_cat(int i) {
        Connection conn = DBConnect.connect();
        try {
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery("SELECT * from " +
                    "Categoria where idCategoria = '" + i + "'");
            return res.next();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    int getPos() {
        return pos;
    }

    public String toString() {
        try {
            return this.name(this.pos);
        } catch (InvalidGenreException ignored) {}
        return null;
    }

    List<String> getAllGenres() {
        Connection conn = DBConnect.connect();
        try {
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery("SELECT designacao from Categoria");
            List<String> r = new ArrayList<>();
            while(res.next())
                r.add(res.getString(1));
            return r;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

//    private static final List<String> genres = new ArrayList<>(Arrays.asList(
//            "Blues",
//            "Classic rock",
//            "Country",
//            "Dance",
//            "Disco",
//            "Funk",
//            "Grunge",
//            "Hip-Hop",
//            "Jazz",
//            "Metal",
//            "New Age",
//            "Oldies",
//            "Other",
//            "Pop",
//            "Rhythm and Blues",
//            "Rap",
//            "Reggae",
//            "Rock",
//            "Techno",
//            "Industrial",
//            "Alternative",
//            "Ska",
//            "Death metal",
//            "Pranks",
//            "Soundtrack",
//            "Euro-Techno",
//            "Ambient",
//            "Trip-Hop",
//            "Vocal",
//            "Jazz & Funk",
//            "Fusion",
//            "Trance",
//            "Classical",
//            "Instrumental",
//            "Acid",
//            "House",
//            "Game",
//            "Sound clip",
//            "Gospel",
//            "Noise",
//            "Alternative Rock",
//            "Bass",
//            "Soul",
//            "Punk",
//            "Space",
//            "Meditative",
//            "Instrumental Pop",
//            "Instrumental Rock",
//            "Ethnic",
//            "Gothic",
//            "Darkwave",
//            "Techno-Industrial",
//            "Electronic",
//            "Pop-Folk",
//            "Eurodance",
//            "Dream",
//            "Southern Rock",
//            "Comedy",
//            "Cult",
//            "Gangsta",
//            "Top 40",
//            "Christian Rap",
//            "Pop/Funk",
//            "Jungle",
//            "Native US",
//            "Cabaret",
//            "New Wave",
//            "Psychedelic",
//            "Rave",
//            "Show tunes",
//            "Trailer",
//            "Lo-Fi",
//            "Tribal",
//            "Acid Punk",
//            "Acid Jazz",
//            "Polka",
//            "Retro",
//            "Musical",
//            "Rock ’n’ Roll",
//            "Hard Rock",
//            "Folk",
//            "Folk-Rock",
//            "National Folk",
//            "Swing",
//            "Fast Fusion",
//            "Bebop",
//            "Latin",
//            "Revival",
//            "Celtic",
//            "Bluegrass",
//            "Avantgarde",
//            "Gothic Rock",
//            "Progressive Rock",
//            "Psychedelic Rock",
//            "Symphonic Rock",
//            "Slow rock",
//            "Big Band",
//            "Chorus",
//            "Easy Listening",
//            "Acoustic",
//            "Humour",
//            "Speech",
//            "Chanson",
//            "Opera",
//            "Chamber music",
//            "Sonata",
//            "Symphony",
//            "Booty bass",
//            "Primus",
//            "Porn groove",
//            "Satire",
//            "Slow jam",
//            "Club",
//            "Tango",
//            "Samba",
//            "Folklore",
//            "Ballad",
//            "Power ballad",
//            "Rhythmic Soul",
//            "Freestyle",
//            "Duet",
//            "Punk Rock",
//            "Drum solo",
//            "A cappella",
//            "Euro-House",
//            "Dance Hall",
//            "Goa",
//            "Drum & Bass",
//            "Club-House",
//            "Hardcore Techno",
//            "Terror",
//            "Indie",
//            "BritPop",
//            "Negerpunk",
//            "Polsk Punk",
//            "Beat",
//            "Christian Gangsta Rap",
//            "Heavy Metal",
//            "Black Metal",
//            "Crossover",
//            "Contemporary Christian",
//            "Christian rock",
//            "Merengue",
//            "Salsa",
//            "Thrash Metal",
//            "Anime",
//            "Jpop",
//            "Synthpop",
//            "Abstract",
//            "Art Rock",
//            "Baroque",
//            "Bhangra",
//            "Big beat",
//            "Breakbeat",
//            "Chillout",
//            "Downtempo",
//            "Dub",
//            "EBM",
//            "Eclectic",
//            "Electro",
//            "Electroclash",
//            "Emo",
//            "Experimental",
//            "Garage",
//            "Global",
//            "IDM",
//            "Illbient",
//            "Industro-Goth",
//            "Jam Band",
//            "Krautrock",
//            "Leftfield",
//            "Lounge",
//            "Math Rock",
//            "New Romantic",
//            "Nu-Breakz",
//            "Post-Punk",
//            "Post-Rock",
//            "Psytrance",
//            "Shoegaze",
//            "Space Rock",
//            "Trop Rock",
//            "World Music",
//            "Neoclassical",
//            "Audiobook",
//            "Audio theatre",
//            "Neue Deutsche Welle",
//            "Podcast",
//            "Indie-Rock",
//            "G-Funk",
//            "Dubstep",
//            "Garage Rock",
//            "Psybient"
//    ));

}