package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.commons.dbutils.handlers.ScalarHandler;
    public class SQLHelper {
        private static final QueryRunner runner = new QueryRunner();
        private static String userName = System.getProperty("db.username");
        private static String password = System.getProperty("db.password");
        private static String url = System.getProperty("db.url");


        private SQLHelper() {
        }

        private static Connection getConn() throws SQLException {
            return DriverManager.getConnection(url, password, userName);
        }

        @SneakyThrows
        public static String geStatusInData() {
            var codeSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
            var conn = getConn();
            var status = runner.query(conn, codeSQL, new ScalarHandler<String>());
            return status;


        }
        @SneakyThrows
        public static String getStatusForCreditForm(){
            var codeSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
            var conn = getConn();
            var status = runner.query(conn, codeSQL, new ScalarHandler<String>());
            return status;

        }

        @SneakyThrows
        public static void cleanDatabase() {
            var conn = getConn();
            runner.execute(conn, "DELETE FROM payment_entity");
            runner.execute(conn, "DELETE FROM order_entity");
            runner.execute(conn, "DELETE FROM credit_request_entity");
        }
}
