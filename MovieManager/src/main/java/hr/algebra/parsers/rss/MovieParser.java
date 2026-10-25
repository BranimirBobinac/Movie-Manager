/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.parsers.rss;

import hr.algebra.factory.ParseFactory;
import hr.algebra.factory.UrlConnnectionFactory;
import hr.algebra.model.Movie;
import hr.algebra.utilities.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Branimir
 */

public class MovieParser {

    private static void uploadPicture(Movie movie, String source) {
        try {
            //https://static.slobodnadalmacija.hr/images/slike/2025/05/10/29401010.jpg
            String ext = source.substring(source.lastIndexOf("."));
            
            if (ext.length() > 4) {
                ext = EXT;
            }
            String name = UUID.randomUUID() + ext;
            String destination = DIR + File.separator + name;

            FileUtils.copyFromUrl(source, destination);

            movie.setPicturePath(destination);
        } catch (Exception ex) {
            Logger.getLogger(MovieParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private enum Tag {
        ITEM("item"),
        TITLE("title"),
        LINK("link"),
        DESC("description"),
        ENC("enclosure"),
        PUB_DATE("pubDate");

        private final String value;

        private Tag(String value) {
            this.value = value;
        }

        
        public static Optional<Tag> from(String value) {
            
            for (Tag tag : values()) {
                if (tag.value.equals(value)) {
                    return Optional.of(tag);
                }
            }
            return Optional.empty();
        }

    }

    // private static final String RSS_URL = "https://slobodnadalmacija.hr/feed";
    private static final String RSS_URL = "https://www.moviefone.com/feeds/what-to-watch.rss";
    private static final String ATTRIBUTE_URL = "url";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";

    
    public static List<Movie> parse() throws Exception {
        List<Movie> movies = new ArrayList<>();

        // konekcija na internet:
        HttpURLConnection con = UrlConnnectionFactory.getHttpUrlConnection(RSS_URL);

        // otvoren stream:
        try (InputStream is = con.getInputStream()) {
            XMLEventReader reader = ParseFactory.createStaxParser(is);

            // i dalje ga ne vidi:
            Optional<Tag> tag = Optional.empty();

            // jos ga nema:
            Movie movie = null;

            // kad starta sve mora biti prazno:
            StartElement startElement = null;

            
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        startElement = event.asStartElement();
                        String name = startElement.getName().getLocalPart();
                        tag = Tag.from(name);
                        if (tag.isPresent() && tag.get().equals(Tag.ITEM)) {
                            movie = new Movie();
                            movies.add(movie);
                        }

                        
                        if (tag.isPresent() && tag.get().equals(Tag.ENC) && movie != null) {
                            Attribute att = startElement.getAttributeByName(new QName(ATTRIBUTE_URL));
                            if (att != null) {
                                uploadPicture(movie, att.getValue());
                            }
                        }
                        
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        // tekst izmedu tagova vise ne pripada zadnjem tagu
                        tag = Optional.empty();
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if (tag.isPresent() && movie != null) { 
                            String data = event
                                    .asCharacters()
                                    .getData()
                                    .trim();

                            switch (tag.get()) {
                                case TITLE:
                                    if (!data.isEmpty()) {
                                        movie.setTitle(data);
                                    }
                                    break;
                                case LINK:
                                    // whitespace nakon </link> je prije brisao link
                                    if (!data.isEmpty()) {
                                        movie.setLink(data);
                                    }
                                    break;

                                case DESC:
                                    if (!data.isEmpty()) {
                                        movie.setDescription(data);
                                    }
                                    break;
                                case PUB_DATE:
                                    if (!data.isEmpty()) {
                                        try {
                                            DateTimeFormatter rssFormatter = DateTimeFormatter.RFC_1123_DATE_TIME;
                                            movie.setPublishedDate(LocalDateTime.parse(data, rssFormatter));
                                        } catch (Exception e) {
                                            Logger.getLogger(MovieParser.class.getName()).log(Level.WARNING, "Could not parse pubDate" + data, e);
                                            movie.setPublishedDate(LocalDateTime.now());
                                        }
                                    }
                                    break;
                                default:
                                    break;
                            }

                        }
                        break;
                }
            }
        }

        // film bez pubDate bi srusio spremanje u bazu (NullPointerException na format())
        for (Movie m : movies) {
            if (m.getPublishedDate() == null) {
                m.setPublishedDate(LocalDateTime.now());
            }
        }

        return movies;
    }

    // staticka klasa
    private MovieParser() {
    }

}
