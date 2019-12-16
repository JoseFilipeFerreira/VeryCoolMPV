package Utils;

import Business.Categoria;
import Exceptions.InvalidGenreException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Metadata {
    private String nome;
    private String author;
    private String album;
    private String categoria;
    private Integer faixa;
    private LocalDate data;

    public Metadata(String path) {
        List<String> output = new ArrayList<>();
        try {
            Process proc = Runtime.getRuntime().exec(new String[]{"ffprobe", "-hide_banner", path});
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String s;
            while ((s = stdError.readLine()) != null) {
                output.add(s);
            }
        }
        catch (IOException ignored){}

        for(String line : output){
            if (Pattern.matches("\\s*title\\s*:.*", line))
                this.nome = line.split(":")[1].trim();
            else if (Pattern.matches("\\s*artist\\s*:.*", line))
                this.author = line.split(":")[1].trim();
            else if (Pattern.matches("\\s*album\\s*:.*", line))
                this.album = line.split(":")[1].trim();
            else if (Pattern.matches("\\s*genre\\s*:.*", line))
                this.categoria = line.split(":")[1].trim();
            else if (Pattern.matches("\\s*track\\s*:.*", line))
                this.faixa = Integer.parseInt(line.split(":")[1].strip().split("/")[0]);
            else if (Pattern.matches("\\s*date\\s*:.*", line))
                this.data = Year.parse(line.split(":")[1].strip()).atMonth(Month.JANUARY).atDay(1);
        }
    }

    public String getNome() {
        return nome == null? "" : nome;
    }

    public String getAuthor() {
        return author == null? "" : author;
    }

    public String getAlbum() {
        return album == null? "" : album;
    }

    public String getFaixa() {
        return faixa == null? "" : String.valueOf(faixa);
    }

    public LocalDate getData() {
        return data;
    }

    public String getCategoria() {
        try{
            return new Categoria(this.categoria).toString();
        } catch (InvalidGenreException e) {
            return null;
        }
    }
}
