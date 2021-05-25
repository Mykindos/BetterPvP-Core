package net.betterpvp.core.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Query {


    private String stmt;

    public Query(String stmt) {
        this.stmt = stmt;

    }

    public String getStatment() {
        return stmt;
    }

    public void setStatment(String stmt) {
        this.stmt = stmt;
    }


    public void execute() {

        PreparedStatement statement = null;
        try {

            statement = Connect.getConnection().prepareStatement(getStatment());
            statement.executeUpdate();
            statement.close();

        } catch (SQLException ex) {
            System.out.println(stmt);
            ex.printStackTrace();

        }finally {
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
