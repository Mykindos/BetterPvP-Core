package net.betterpvp.core.database.values;

import net.betterpvp.core.database.StatementValue;

import java.sql.Types;

public class DoubleStatementValue extends StatementValue {

    public DoubleStatementValue(String value) {
        super(value);
    }

    @Override
    public int getType() {
        return Types.DOUBLE;
    }

}
