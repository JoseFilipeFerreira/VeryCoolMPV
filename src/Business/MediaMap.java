package Business;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class MediaMap implements Map<String, Media> {

    private Utilizador owner;

    MediaMap() {
        this.owner = null;
    }

    MediaMap(Utilizador a) {
        this.owner = a;
    }

    public void clear() {
        Connection conn = DBConnect.connect();
        try {
            Statement stm = conn.createStatement();
            stm.executeUpdate(this.owner == null ?
                    "DELETE FROM Media" :
                    "DELETE FROM Media where owner = '" + this.owner.getEmail() + "'");
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public boolean containsKey(Object key) throws NullPointerException {
        Connection conn = DBConnect.connect();
        try {
            Statement stm = conn.createStatement();
            String sql = this.owner == null ?
                    "SELECT name FROM Media WHERE name='" + key + "'" :
                    "Select name from Media where name='" + key + "'and " +
                            "owner='" + this.owner.getEmail() + "'";
            ResultSet rs = stm.executeQuery(sql);
            return rs.next();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public boolean containsValue(Object value) {
        throw new NullPointerException("public boolean containsValue(Object value) not implemented!");
    }

    public Set<Entry<String, Media>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Aluno>> entrySet() not implemented!");
    }

    public boolean equals(Object o) {
        throw new NullPointerException("public boolean equals(Object o) not implemented!");
    }

    public Media get(Object key) {
        Connection conn = DBConnect.connect();
        try {
            Media al = null;
            Statement stm = conn.createStatement();
            String sql = this.owner == null ?
                "SELECT * FROM Media WHERE name='" + key + "'" :
                "SELECT * FROM Media WHERE name='" + key + "' and owner='" +
                    this.owner.getEmail() + "'";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                if(rs.getString(6) != null)
                    al = new Musica(rs.getString(1), rs.getString(2),
                            rs.getString(3), rs.getString(5), rs.getString(6),
                            rs.getInt(7), rs.getDate(8), rs.getInt(12));
            }
                else
                    al = new Video(rs.getString(1), rs.getString(2),
                            rs.getString(3), rs.getString(9), rs.getInt(10),
                            rs.getInt(11), rs.getDate(8));
            return al;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
        Connection conn = DBConnect.connect();
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(this.owner == null ?
                    "SELECT name FROM Media" :
                    "SELECT name FROM Media where owner='" +
                        this.owner.getEmail() + "'");
            return !rs.next();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public Set<String> keySet() {
        throw new NullPointerException("Not implemented!");
    }

    void updateCat(int cat_id, String key) {
        Connection con = DBConnect.connect();
        try {
            Statement stm = con.createStatement();
            stm.executeUpdate("UPDATE Media set categoria = '" + cat_id + "' " +
                    "where name = '" + key + "' and edited_by IS NULL");
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    void updateCat(Musica value, String user) {
        Connection con = DBConnect.connect();
        try {
            Statement stm = con.createStatement();
            stm.executeUpdate( "INSERT INTO Media (name, path, owner, album, artista, " +
                    "faixa, categoria, edited_by, release_date) VALUES " +
                    "('" + value.getName() + "','"
                    + value.getPath().toString() + "','"
                    + value.getOwner() + "','"
                    + value.getAlbum() + "','"
                    + value.getSinger() + "','"
                    + value.getFaixa() + "','"
                    + value.getCat() + "','"
                    + user + "','"
                    + value.getRelease_date() + "')"
                    + "ON DUPLICATE KEY UPDATE "
                    + "edited_by = '" + user + "'");
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public Media put(String key, Media valuer) {
        Connection conn;
        try {
            conn = DBConnect.connect();
            Media al = null;
            Statement stm = conn.createStatement();
            if(valuer instanceof Musica) {
                Musica value = (Musica) valuer;
                    String sql =
                            "INSERT INTO Media (name, path, owner, album, artista, " +
                                    "faixa, categoria, release_date) VALUES " +
                                    "('" + key + "','"
                                    + value.getPath().toString() + "','"
                                    + value.getOwner() + "','"
                                    + value.getAlbum() + "','"
                                    + value.getSinger() + "','"
                                    + value.getFaixa() + "','"
                                    + value.getCat() + "','"
                                    + value.getRelease_date() + "')"
                            + "ON DUPLICATE KEY UPDATE "
                                    + "album = '" + value.getAlbum() + "',"
                                    + "artista = '" + value.getSinger() + "',"
                                    + "faixa = '" + value.getFaixa() + "',"
                                    + "release_date = '" + value.getRelease_date() + "',"
                                    + "categoria = '" + value.getCat() + "'";
                    int i = stm.executeUpdate(sql);
            }
            else {
                Video value = (Video) valuer;
                    String sql =
                            "INSERT INTO Media (name, path, owner, " +
                                    "release_date, serie_name, season, " +
                                    "episode) VALUES ('" + key + "','"
                                    + value.getPath().toString() + "','"
                                    + value.getOwner() + "','"
                                    + value.getRelease_date() + "','"
                                    + value.getSerie() + "',"
                                    + (value.getSeason() == null ? null :
                                     "'" + value.getSeason() + "'") + ","
                                    + (value.getEpisode() == null ? null :
                                    "'" + value.getEpisode() + "'") + ")"
                                    + "ON DUPLICATE KEY UPDATE "
                                    + "serie_name = '" + value.getSerie() + "',"
                                    + "season = '" + value.getSeason() + "',"
                                    + "episode = '" + value.getEpisode() + "'";
                    int i = stm.executeUpdate(sql);
            }
            return valuer;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public void putAll(Map<? extends String, ? extends Media> t) {
        throw new NullPointerException("Not implemented!");
    }

    public Media remove(Object key) {
        Connection conn = DBConnect.connect();
        try {
            Media al = this.get(key);
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM Playlist where (`Media_name` = '"
                    + key + "');");
            String sql = "DELETE FROM Media WHERE (`name` = " +
                    "'" + key + "');";
            int i = stm.executeUpdate(sql);
            return al;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public int size() {
        Connection conn = DBConnect.connect();
        try {
            int i = 0;
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(this.owner == null ?
                    "SELECT name FROM Media" :
                    "Select name from Media where owner ='"
                            + this.owner.getEmail() + "'");
            for (; rs.next(); i++) ;
            return i;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public Collection<Media> values() {
        Connection conn = DBConnect.connect();
        try {
            Collection<Media> col = new HashSet<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(this.owner == null ?
                    "SELECT * FROM Media" :
                    "Select * from Media where owner='" +
                    this.owner.getEmail() + "'");
            for (; rs.next(); ) {
                if(rs.getString(6) != null)
                    col.add(new Musica(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(5), rs.getString(6),
                        rs.getInt(7), rs.getDate(8), rs.getInt(12)));
                else
                    col.add(new Video(rs.getString(1), rs.getString(2),
                            rs.getString(3), rs.getString(9), rs.getInt(10),
                            rs.getInt(11), rs.getDate(8)));
            }
            return col;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    Collection<Media> values(String uid) {
        Connection conn = DBConnect.connect();
        try {
            Map<String,Media> col = new HashMap<>();
            Statement stm = conn.createStatement();
            if(this.owner == null) {
                ResultSet rs = stm.executeQuery("SELECT * FROM Media " +
                        "where edited_by = '" + uid + "'");
                for (; rs.next(); ) {
                    if (rs.getString(6) != null) {
                        col.put(rs.getString(1), new Musica(rs.getString(1),
                                rs.getString(2),
                                rs.getString(3), rs.getString(5), rs.getString(6),
                                rs.getInt(7), rs.getDate(8), rs.getInt(12)));
                    }
                    else
                        col.put(rs.getString(1), new Video(rs.getString(3),
                                rs.getString(2),
                                rs.getString(1), rs.getString(9), rs.getInt(10),
                                rs.getInt(11), rs.getDate(8)));
                }
            }
            ResultSet rs = stm.executeQuery(this.owner == null ?
                    "SELECT * FROM Media where edited_by is null" :
                    "Select * from Media where owner='" +
                            this.owner.getEmail() + "' and edited_by is null");
            for (; rs.next(); ) {
                if (rs.getString(6) != null)
                    col.putIfAbsent(rs.getString(1), new Musica(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3), rs.getString(5), rs.getString(6),
                            rs.getInt(7), rs.getDate(8), rs.getInt(12)));
                else
                    col.putIfAbsent(rs.getString(1), new Video(rs.getString(3),
                            rs.getString(2),
                            rs.getString(1), rs.getString(9), rs.getInt(10),
                            rs.getInt(11), rs.getDate(8)));
            }
            return col.values();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    List<Media> searchByName(String s, String uid) {
        Connection conn = DBConnect.connect();
        try {
            Map<String, Media> col = new HashMap<>();
            Statement stm = conn.createStatement();
            if(this.owner == null) {
                ResultSet rs = stm.executeQuery("SELECT * FROM Media where " +
                        "lower(name) regexp '" + s.toLowerCase() + "' and " +
                        "edited_by = '" + uid + "'");
                for (; rs.next(); ) {
                    if (rs.getString(6) != null)
                        col.put(rs.getString(1), new Musica(rs.getString(1),
                                rs.getString(2),
                                rs.getString(3), rs.getString(5),
                                rs.getString(6),
                                rs.getInt(7), rs.getDate(8), rs.getInt(12)));
                    else
                        col.put(rs.getString(1), new Video(rs.getString(3),
                                rs.getString(2),
                                rs.getString(1), rs.getString(9), rs.getInt(10),
                                rs.getInt(11), rs.getDate(8)));
                }
            }
            ResultSet rs = stm.executeQuery(this.owner == null ?
                    "SELECT * FROM Media where lower(name) regexp '" + s.toLowerCase() +
                            "' and edited_by is null" :
                    "Select * from Media where owner='" +
                            this.owner.getEmail() + "' and lower(name) regexp" +
                            " '" + s.toLowerCase() + "' and edited_by is null");
            for (; rs.next(); ) {
                if(rs.getString(6) != null)
                    col.put(rs.getString(1), new Musica(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3), rs.getString(5), rs.getString(6),
                            rs.getInt(7), rs.getDate(8), rs.getInt(12)));
                else
                    col.put(rs.getString(1), new Video(rs.getString(3),
                            rs.getString(2),
                            rs.getString(1), rs.getString(9), rs.getInt(10),
                            rs.getInt(11), rs.getDate(8)));
            }
            return new ArrayList<>(col.values());
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    List<Media> searchByArtist(String s, String uid) {
        Connection conn = DBConnect.connect();
        try {
            Map<String, Media> col = new HashMap<>();
            Statement stm = conn.createStatement();
            if(this.owner == null) {
                ResultSet rs = stm.executeQuery("SELECT * FROM Media where " +
                        "lower(artista) regexp '" + s.toLowerCase() + "' and " +
                        "edited_by = '" + uid + "'");
                for (; rs.next(); ) {
                    if (rs.getString(6) != null)
                        col.put(rs.getString(1), new Musica(rs.getString(1),
                                rs.getString(2),
                                rs.getString(3), rs.getString(5),
                                rs.getString(6),
                                rs.getInt(7), rs.getDate(8), rs.getInt(12)));
                    else
                        col.put(rs.getString(1), new Video(rs.getString(3),
                                rs.getString(2),
                                rs.getString(1), rs.getString(9), rs.getInt(10),
                                rs.getInt(11), rs.getDate(8)));
                }
            }
            ResultSet rs = stm.executeQuery(this.owner == null ?
                    "SELECT * FROM Media where lower(artista) regexp '" + s.toLowerCase() +
                            "' and edited_by is null" :
                    "Select * from Media where owner='" +
                            this.owner.getEmail() + "' and lower(artist) regexp" +
                            " '" + s.toLowerCase() + "' and edited_by is null");
            for (; rs.next(); ) {
                if(rs.getString(6) != null)
                    col.put(rs.getString(1), new Musica(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3), rs.getString(5), rs.getString(6),
                            rs.getInt(7), rs.getDate(8), rs.getInt(12)));
                else
                    col.put(rs.getString(1), new Video(rs.getString(3),
                            rs.getString(2),
                            rs.getString(1), rs.getString(9), rs.getInt(10),
                            rs.getInt(11), rs.getDate(8)));
            }
            return new ArrayList<>(col.values());
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    List<String> artistList() {
        Connection conn = DBConnect.connect();
        try {
            List<String> ls = new ArrayList<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT distinct artista from Media");
            while(rs.next())
                ls.add(rs.getString(1));
            return ls;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    List<String> artistList(String name) {
        Connection conn = DBConnect.connect();
        try {
            List<String> ls = new ArrayList<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT distinct artista from Media" +
                    " where artista regexp '^" + name + "'");
            while(rs.next())
                ls.add(rs.getString(1));
            return ls;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    List<Media> artistMedia(String name, String uid) {
        List<Media> a = new ArrayList<>(this.values(uid));
        List<Media> b = new ArrayList<>();
        for(Media m : a) {
            Musica mm = (Musica) m;
            if (mm.getSinger().equals(name))
                b.add(m);
        }
        return b;
    }

    List<String> albumList(String art) {
        Connection conn = DBConnect.connect();
        try {
            List<String> ls = new ArrayList<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT distinct album from Media" +
                    " where artista = '" + art + "'");
            while(rs.next())
                ls.add(rs.getString(1));
            return ls;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    List<String> albumList() {
        Connection conn = DBConnect.connect();
        try {
            List<String> ls = new ArrayList<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT distinct album from Media");
            while(rs.next())
                ls.add(rs.getString(1));
            return ls;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }
}

