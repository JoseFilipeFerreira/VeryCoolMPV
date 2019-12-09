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
            if (rs.next())
                al = new Musica(rs.getString(1), rs.getString(2),
                        rs.getString(3));
            return al;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public int hashCode() {
        //return this.inst.hashCode();
        return 0;
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

    /* Exercício: Alterar para utilizar transacções! */
    public Media put(String key, Media value) {
        Connection conn;
        try {
            conn = DBConnect.connect();
            Media al = null;
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM Media WHERE name='" + key + "'");
            String sql =
                    "INSERT INTO Media VALUES ('" + key + "','"
                            + value.getPath().toString() + "','"
                            + value.getOwner() + "')";
            int i = stm.executeUpdate(sql);
            return new Musica(value.getName(), value.getPath().toString(),
                    value.getOwner());
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
            Collection<Media> col = new HashSet<Media>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(this.owner == null ?
                    "SELECT * FROM Media" :
                    "Select * from Media where owner='" +
                    this.owner.getEmail() + "'");
            for (; rs.next(); ) {
                col.add(new Musica(rs.getString(1), rs.getString(2),
                        rs.getString(3)));
            }
            return col;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    void playAll() {
        this.values()
                .forEach(Media::play);
    }

    List<Media> searchByName(String s) {
        Connection conn = DBConnect.connect();
        try {
            List<Media> col = new ArrayList<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(this.owner == null ?
                    "SELECT * FROM Media where name regexp '^" + s + "'" :
                    "Select * from Media where owner='" +
                            this.owner.getEmail() + "' and regexp '^" + s + "'");
            for (; rs.next(); ) {
                col.add(new Musica(rs.getString(1), rs.getString(2),
                        rs.getString(3)));
            }
            return col;
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }
}


