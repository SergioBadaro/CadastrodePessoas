import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/cadastro_db";
    private static final String USUARIO = "root";
    private static final String SENHA = "#1K2i3ara2025#"; // troque pela sua senha real

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}