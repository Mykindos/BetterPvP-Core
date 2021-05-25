package net.betterpvp.core.database.values;

import net.betterpvp.core.database.StatementValue;

import java.sql.SQLType;
import java.sql.Types;

public class StringStatementValue extends StatementValue {

    public StringStatementValue(String value) {
        super(value);
    }

    @Override
    public int getType() {
        return Types.VARCHAR;
    }

}
