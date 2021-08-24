package net.betterpvp.core.database.values;

import net.betterpvp.core.database.StatementValue;

import java.sql.Types;

public class BooleanStatementValue extends StatementValue {

    public BooleanStatementValue(String value) {
        super(value);
    }

    @Override
    public int getType() {
        return Types.TINYINT;
    }

}
