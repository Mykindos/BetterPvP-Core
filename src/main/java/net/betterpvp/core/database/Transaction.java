package net.betterpvp.core.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Transaction extends Query {

    private List<Statement> statements;

    public Transaction(List<Statement> statements) {
        super("");
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public void execute() {
        PreparedStatement statement = null;
        try {
            Connect.getConnection().setAutoCommit(false);
            Transaction transaction = (Transaction) this;
            for (Statement s : transaction.getStatements()) {
                statement = Connect.getConnection().prepareStatement(s.getQuery());
                for (int i = 1; i <= s.getValues().length; i++) {
                    StatementValue val = s.getValues()[i - 1];
                    statement.setObject(i, val.getValue(), val.getType());
                }
                statement.executeUpdate();
                statement.close();
            }
            Connect.getConnection().commit();
            Connect.getConnection().setAutoCommit(true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
