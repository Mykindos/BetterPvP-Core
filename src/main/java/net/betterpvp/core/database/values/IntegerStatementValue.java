package net.betterpvp.core.database.values;

import net.betterpvp.core.database.StatementValue;

import java.sql.Types;

public class IntegerStatementValue extends StatementValue {

    public IntegerStatementValue(String value) {
        super(value);
    }

    @Override
    public int getType() {
        return Types.INTEGER;
    }

}
