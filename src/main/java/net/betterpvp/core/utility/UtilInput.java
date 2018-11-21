package net.betterpvp.core.utility;

import java.util.HashSet;

public class UtilInput {
    
    protected static HashSet<Character> validSet = new HashSet<Character>();


    public static void addChars() {
        validSet.add('1');
        validSet.add('2');
        validSet.add('3');
        validSet.add('4');
        validSet.add('5');
        validSet.add('6');
        validSet.add('7');
        validSet.add('8');
        validSet.add('9');
        validSet.add('0');

        validSet.add('a');
        validSet.add('b');
        validSet.add('c');
        validSet.add('d');
        validSet.add('e');
        validSet.add('f');
        validSet.add('g');
        validSet.add('h');
        validSet.add('i');
        validSet.add('j');
        validSet.add('k');
        validSet.add('l');
        validSet.add('m');
        validSet.add('n');
        validSet.add('o');
        validSet.add('p');
        validSet.add('q');
        validSet.add('r');
        validSet.add('s');
        validSet.add('t');
        validSet.add('u');
        validSet.add('v');
        validSet.add('w');
        validSet.add('x');
        validSet.add('y');
        validSet.add('z');

        validSet.add('A');
        validSet.add('B');
        validSet.add('C');
        validSet.add('D');
        validSet.add('E');
        validSet.add('F');
        validSet.add('G');
        validSet.add('H');
        validSet.add('I');
        validSet.add('J');
        validSet.add('K');
        validSet.add('L');
        validSet.add('M');
        validSet.add('N');
        validSet.add('O');
        validSet.add('P');
        validSet.add('Q');
        validSet.add('R');
        validSet.add('S');
        validSet.add('T');
        validSet.add('U');
        validSet.add('V');
        validSet.add('W');
        validSet.add('X');
        validSet.add('Y');
        validSet.add('Z');
    }

    
    /**
     * Checks if all characters in a String are valid (No special characters)
     * @param input String to check
     * @return Returns true if there are no special characters in the string
     */
    public static boolean valid(String input) {
        if (validSet.isEmpty()) {
            addChars();
        }

        for (char cur : input.toCharArray()) {
            if (!validSet.contains(cur)) {
                return false;
            }
        }
        return true;
    }
    
}
