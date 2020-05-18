package net.betterpvp.core.database;

import java.util.ArrayList;
import java.util.List;

public class Transaction extends Query {

    private List<String> statements;

    public Transaction(List<String> statements) {
        super("");
        this.statements = statements;
    }

    public List<String> getStatements(){
        return statements;
    }
}
