/**
 * Created by sun on 26.02.17.
 */
public class MainDBExample {
    // ТЕСТОВЫЙ СЦЕНАРИЙ
    public static void main(String[] args) throws Exception {
//        Map m = new Map("map", "localhost", 3306);
//        m.showCountries();
//        m.addCountry(11, "USA");
//        m.addCountry(5, "JAPAN");
//        m.addCountry(6, "UKRAINE");
//        m.deleteCountry(3);
//        m.deleteCountry(7);
//        m.showCountries();
//        m.stop();

        HomeVideo h = new HomeVideo("homevideo", "localhost", 3306);
       // h.addMovie(5, "Dark Shadows", 2012, "USA");
        h.addMovie(6, "Leon", 1994, "France");
        h.deleteMovieWithDiapason(1900, 2000);
      //  h.deleteMovie(6);
       // h.showMovie();
        h.movieSameActor(1);
        h.addActor(6, "Amy" , "Adams", 1974, 8, 20);
        h.addProducer(6, "Zack", "Snyder", 1966, 5,1);
        h.showActor();
        h.showProducer();
        h.actorSameMovie(1);
        h.actorProduser();
        h.stop();
    }
}
