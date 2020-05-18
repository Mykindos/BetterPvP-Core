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

        PreparedStatement con = null;
        try {

            if(this instanceof Transaction){
                Connect.getConnection().setAutoCommit(false);
                Transaction transaction = (Transaction) this;
                for(String q : transaction.getStatements()){
                    con = Connect.getConnection().prepareStatement(q);
                    con.executeUpdate();
                    con.close();
                }
                Connect.getConnection().commit();
                Connect.getConnection().setAutoCommit(true);

            }else{
                con = Connect.getConnection().prepareStatement(getStatment());
                con.executeUpdate();
                con.close();
            }



        } catch (SQLException ex) {
            System.out.println(stmt);
            ex.printStackTrace();

        }finally {
            if(con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
