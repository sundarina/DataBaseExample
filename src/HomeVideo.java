import java.sql.*;

/**
 * Created by sun on 26.02.17.
 */
public class HomeVideo {
    private Connection con = null;
    private Statement stmt = null;

    public HomeVideo(String DBName, String ip, int port) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://" + ip + ":" + port + "/" + DBName;
        con = DriverManager.getConnection(url, "root", "92e3579a");
        stmt = con.createStatement();
    }
    public void stop() throws SQLException {
        con.close();
    }

    public boolean addMovie(int movie_id, String name, int year, String country) {
        String sql = "INSERT INTO movie (movie_id, name, year, country) VALUES (" + movie_id + ", '" + name + "',  " + year + ", '" + country + "')";


        try {
            stmt.executeUpdate(sql);
            System.out.println("The movie " + name + " successfully added");
            return true;
        } catch (SQLException e) {
            System.out.println("ERROR! The movie " + name + "does not add!");
            System.out.println("    >>>> " + e.getMessage());
            return false;
        }
    }

    public boolean deleteMovieWithDiapason(int year1, int year2) {
        String sql = "DELETE FROM movie WHERE movie.year BETWEEN " + year1 + " AND " + year2;
        int c = 0;
        try {
            c = stmt.executeUpdate(sql);

            if (c > 0) {
                System.out.println("The movie with year between " + year1 + " and " + year2 + " successfully deleted");
                return true;
            } else {
                System.out.println("The movie with year between " + year1 + " and " + year2 +  " does not find");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("EROR in deleting movie with year between " + year1 + " and " + year2);
            System.out.println("     >>>> " + e.getMessage());
            return false;
        }
    }


    public boolean deleteMovie(int id) {
        String sql = "DELETE FROM movie WHERE movie_id = " + id;
        int c = 0;
        try {
            c = stmt.executeUpdate(sql);

            if (c > 0) {
                System.out.println("The movie with id " + id + " successfully deleted");
                return true;
            } else {
                System.out.println("The movie with id " + id + " does not find");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("EROR in deleting movie with id " + id);
            System.out.println("     >>>> " + e.getMessage());
            return false;
        }
    }


    public void showMovie() {
        String sql = "SELECT movie_id, name FROM movie";
        try {
            ResultSet rs = stmt.executeQuery(sql); //множество результатов из бд
            System.out.println("MOVIE LIST:");
            while (rs.next()) {
                int id = rs.getInt("movie_id");
                String name = rs.getString("name");
                System.out.println(" » " + id + " - " + name);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving list");
            System.out.println("    >>>> " + e.getMessage());
        }
    }

    public boolean addActor(int actor_id, String name, String surname, int year, int month, int day) {
        String sql = "INSERT INTO actor (actor_id, name, surname, birthday) VALUES (" + actor_id + ", '" + name + "', '" + surname + "', '" + year + "-" + month + "-" + day + "')";
        try {
            stmt.executeUpdate(sql);
            System.out.println("The actor " + name + " " + surname + " successfully added");
            return true;
        } catch (SQLException e) {
            System.out.println("ERROR! The actor " + name + " " + surname + " does not add!");
            System.out.println("    >>>> " + e.getMessage());
            return false;
        }
    }

    public boolean addProducer(int producer_id, String name, String surname, int year, int month, int day) {
        String sql = "INSERT INTO producer (producer_id, name, surname, birthday) VALUES (" + producer_id + ", '" + name + "', '" + surname + "', '" + year + "-" + month + "-" + day + "')";
        try {
            stmt.executeUpdate(sql);
            System.out.println("The producer " + name + " " + surname + " successfully added");
            return true;
        } catch (SQLException e) {
            System.out.println("ERROR! The producer " + name + " " + surname + " does not add!");
            System.out.println("    >>>> " + e.getMessage());
            return false;
        }
    }

    public boolean deleteActor(int id) {
        String sql = "DELETE FROM actor WHERE actor_id = " + id;
        int c = 0;
        try {
            c = stmt.executeUpdate(sql);

            if (c > 0) {
                System.out.println("The actor with id " + id + " successfully deleted");
                return true;
            } else {
                System.out.println("The actor with id " + id + " does not find");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("EROR in deleting movie with id " + id);
            System.out.println("     >>>> " + e.getMessage());
            return false;
        }
    }

    public boolean deleteProducer(int id) {
        String sql = "DELETE FROM producer WHERE producer_id = " + id;
        int c = 0;
        try {
            c = stmt.executeUpdate(sql);

            if (c > 0) {
                System.out.println("The producer with id " + id + " successfully deleted");
                return true;
            } else {
                System.out.println("The producer with id " + id + " does not find");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("EROR in deleting movie with id " + id);
            System.out.println("     >>>> " + e.getMessage());
            return false;
        }
    }


    public void showActor() {
        String sql = "SELECT actor_id, name, surname FROM actor";
        try {
            ResultSet rs = stmt.executeQuery(sql); //множество результатов из бд
            System.out.println("ACTOR LIST:");
            while (rs.next()) {
                int id = rs.getInt("actor_id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                System.out.println(" » " + id + " - " + name + " " + surname);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving list");
            System.out.println("    >>>> " + e.getMessage());
        }
    }

    public void showProducer() {
        String sql = "SELECT producer_id, name, surname FROM producer";
        try {
            ResultSet rs = stmt.executeQuery(sql); //множество результатов из бд
            System.out.println("PRODUCER LIST:");
            while (rs.next()) {
                int id = rs.getInt("producer_id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                System.out.println(" » " + id + " - " + name + " " + surname);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving list");
            System.out.println("    >>>> " + e.getMessage());
        }
    }

    public void movieSameActor(int actor_id) {
        String sql = "SELECT * FROM movie JOIN cast ON cast.movie_id = movie.movie_id HAVING cast.actor_id = " + actor_id;

        String sql1 = "SELECT actor.name, actor.surname FROM actor WHERE actor_id = " + actor_id;

        try {
            ResultSet rs1 = stmt.executeQuery(sql1);
            System.out.print("MOVIE LIST WITH THE SAME ACTOR ");
            while (rs1.next()) {
                String name = rs1.getString("name");
                String surname = rs1.getString("surname");
                System.out.println(name + " " + surname + ": ");
            }
            rs1.close();
            ResultSet rs = stmt.executeQuery(sql); //множество результатов из бд

            while (rs.next()) {
                int id = rs.getInt("movie_id");
                String name = rs.getString("name");
                System.out.println(" » " + id + " - " + name);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving list");
            System.out.println("    >>>> " + e.getMessage());
        }
    }

    public void actorSameMovie(int movie_id) {
        String sql = "SELECT * FROM actor JOIN cast ON actor.actor_id = cast.actor_id WHERE cast.movie_id = " + movie_id;
        String sql1 = "SELECT movie.name FROM movie WHERE movie_id = " + movie_id;
        try {

            ResultSet rs1 = stmt.executeQuery(sql1);
            System.out.print("ACTOR LIST WITH THE SAME MOVIE ");
            while (rs1.next()) {
                String name = rs1.getString("name");
                System.out.println(name + ": ");
            }
            rs1.close();

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("actor_id");
                String name = rs.getString("name");
                String  surname = rs.getString("surname");
                System.out.println(" » " + id + " - " + name + " " + surname);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving list");
            System.out.println("    >>>> " + e.getMessage());
        }
    }

    public void actorProduser() {
        String sql = "SELECT * FROM actor JOIN producer ON actor.name = producer.name";
        try {
            ResultSet rs = stmt.executeQuery(sql); //множество результатов из бд
            System.out.println("LIST WITH THE PRODUCER - ACTOR:");
            while (rs.next()) {
                String name = rs.getString("name");
                String surname =  rs.getString("surname");
                System.out.println(" » " + name + " " + surname);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving list");
            System.out.println("    >>>> " + e.getMessage());
        }

    }
}