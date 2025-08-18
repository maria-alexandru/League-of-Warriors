package Player;

import Entities.Character;

import java.util.ArrayList;
import java.util.SortedSet;

public class Account {
    public static class Information {
        private Credentials credentials;
        private SortedSet<String> favoriteGames;
        private String name;
        private String country;

        public Information(InformationBuilder builder) {
            this.credentials = builder.credentials;
            this.favoriteGames = builder.favoriteGames;
            this.name = builder.name;
            this.country = builder.country;
        }

        public static class InformationBuilder {
            private Credentials credentials;
            private SortedSet<String> favoriteGames;
            private String name;
            private String country;

            public InformationBuilder credentials(Credentials credentials) {
                this.credentials = credentials;
                return this;
            }

            public InformationBuilder favoriteGames(SortedSet<String> favoriteGames) {
                this.favoriteGames = favoriteGames;
                return this;
            }

            public InformationBuilder name(String name) {
                this.name = name;
                return this;
            }

            public InformationBuilder country(String country) {
                this.country = country;
                return this;
            }

            public Information build() {
                return new Information(this);
            }
        }

        public String toString() {
            return "Player Info: \n" + "  Name: " + name + "\n" + "  Country: " + country + "\n" + "  Credentials: \n" + credentials + "\n" + "  Favorite Games: " + favoriteGames;
        }

        public void setCredentials(Credentials credentials) {
            this.credentials = credentials;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public void setFavoriteGames(SortedSet<String> favoriteGames) {
            this.favoriteGames = favoriteGames;
        }

        public SortedSet<String> getFavoriteGames() {
            return favoriteGames;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCountry() {
            return country;
        }
    }

    private Information info;
    private ArrayList<Entities.Character> characters;
    private int nrPlayedGames;

    public Account(ArrayList<Character> characters, int nrPlayedGames, Information info) {
        this.info = info;
        this.nrPlayedGames = nrPlayedGames;
        this.characters = characters;
    }

    public String toString() {
        return info + "\n" + "  Characters: " + characters + "\n" + "  Played Games: " + nrPlayedGames + "\n\n";
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public ArrayList<Entities.Character> getCharacters() {
        return characters;
    }

    public void setNrPlayedGames(int nrPlayedGames) {
        this.nrPlayedGames = nrPlayedGames;
    }

    public int getNrPlayedGames() {
        return nrPlayedGames;
    }

    public void setInfo(Information info) {
        this.info = info;
    }

    public Information getInfo() {
        return info;
    }
}
