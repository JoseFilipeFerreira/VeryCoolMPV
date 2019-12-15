package Business;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class PlaylistMap implements Map<Integer, Playlist> {

    public void clear() {
        Connection conn = DBConnect.connect();
        try {
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM Playlist");
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public boolean containsKey(Object key) throws NullPointerException {
        Connection conn = DBConnect.connect();
        try {
            Statement stm = conn.createStatement();
            String sql = "select Utilizadores_email from Playlist where " +
                    "playlist_id = '" + key + "'";
            ResultSet rs = stm.executeQuery(sql);
            return rs.next();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public boolean containsValue(Object value) {
        throw new NullPointerException("public boolean containsValue(Object value) not implemented!");
    }

    public Set<Entry<Integer, Playlist>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Aluno>> entrySet() not implemented!");
    }

    public boolean equals(Object o) {
        throw new NullPointerException("public boolean equals(Object o) not implemented!");
    }

    public Playlist get(Object key) {
        Connection conn = DBConnect.connect();
        try {
            Playlist al = null;
            Statement stm = conn.createStatement();
            String sql =
                    "SELECT * FROM Playlist WHERE playlist_id='" + key + "'";
            ResultSet rs = stm.executeQuery(sql);
            al = new Playlist(rs.getString(1), rs.getInt(3), rs.getBoolean(4)
                    , rs.getString(5));
            while (rs.next()) {
                al.add(rs.getString(2));
            }
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
            ResultSet rs = stm.executeQuery("select playlist_id from Playlist");
            return !rs.next();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public Set<Integer> keySet() {
        Connection conn = DBConnect.connect();
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select distinct playlist_id from" +
                    " Playlist");
            Set<Integer> res = new HashSet<>();
            while (rs.next())
                res.add(rs.getInt(1));
            return res;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public Playlist put(Integer key, Playlist value) {
        Connection conn;
        String owner = value.getOwner();
        List<String> media = value.getMedia();
        String title = value.getTitle();
        boolean shared = value.is_shared();
        try {
            conn = DBConnect.connect();
            Statement stm = conn.createStatement();
            for (String media_name : media) {
                stm.executeUpdate(
                        "INSERT IGNORE INTO Playlist VALUES ('" + owner + "','"
                                + media_name + "','"
                                + key + "','"
                                + shared + "','"
                                + title +"')");
            }
            return value;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public void putAll(Map<? extends Integer, ? extends Playlist> t) {
        throw new NullPointerException("Not implemented!");
    }

    public Playlist remove(Object key) {
        Connection conn = DBConnect.connect();
        try {
            Playlist media = this.get(key);
            Statement stm = conn.createStatement();
            String sql = "DELETE FROM Playlist WHERE (`playlist_id` = " +
                    "'" + key + "');";
            int i = stm.executeUpdate(sql);
            return media;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public int size() {
        Connection conn = DBConnect.connect();
        try {
            int i = 0;
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select distinct playlist_id from" +
                    " Playlist");
            for (; rs.next(); i++) ;
            return i;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public Collection<Playlist> values() {
        Collection<Playlist> col = new HashSet<>();
        Set<Integer> keys = this.keySet();
        keys.forEach(x -> col.add(this.get(x)));
        return col;
    }

    Map<Integer, Playlist> userPlaylists(String user_name) {
       Connection conn = DBConnect.connect();
       try {
           Statement stm = conn.createStatement();
           ResultSet rs = stm.executeQuery("select playlist_id from Playlist" +
                   " where Utilizadores_email = '" + user_name + "'");
           Map<Integer, Playlist> res = new HashMap<>();
           while(rs.next())
               res.put(rs.getInt(1), this.get(rs.getInt(1)));
           return res;
       } catch (Exception e) {
           throw new NullPointerException(e.getMessage());
       }
    }

    Map<Integer, Playlist> sharedPlaylists() {
        Connection conn = DBConnect.connect();
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select playlist_id from Playlist" +
                    " where is_shared = true");
            Map<Integer, Playlist> res = new HashMap<>();
            while(rs.next())
                res.put(rs.getInt(1), this.get(rs.getInt(1)));
            return res;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }
}

