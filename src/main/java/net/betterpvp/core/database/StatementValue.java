package net.betterpvp.core.database;

public abstract class StatementValue {

    private Object value;

    public StatementValue(Object value){
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    /**
     * https://docs.oracle.com/javase/8/docs/api/java/sql/Types.html
     * @return SQL Type of the object
     */
    public abstract int getType();
}
