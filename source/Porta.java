
package source;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Samet Eraslan
 */
public class Porta {
    
    private static final int KEY_ROW_LENGTH = 4;
    private String[] rows = new String[]{"A,B", "C,D", "E,F", "G,H",
                                         "I,J", "K,L", "M,N", "O,P", 
                                         "Q,R", "S,T", "U,V", "W,X", 
                                         "Y,Z"};
    public Porta() {
        System.out.println("Porta Object Created.");
    }
    
    public String encipher(String plaintext, String key) {
        plaintext = plaintext.toUpperCase();
        key = key.toUpperCase();
        
        String ciphertext = "";
        
        for (int i = 0; i < plaintext.length(); ++i)
        {
            ciphertext += encipherOneKey(plaintext.charAt(i), key.charAt(i % key.length()));
        }
        
        return ciphertext;
    }
    
    private String encipherOneKey(char plaintextCharackter, char keyCharacter)
    {
        String cipherCharacter = "";
        
        String keysColumn = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // switch case yapisi yerine daha optimize hale getirilebilir, satirlar
        // belirli bir oruntude degisiyor.
        switch(keyCharacter)
        {
            case 'A':
            case 'B':
            {
                cipherCharacter = "NOPQRSTUVWXYZABCDEFGHIJKLM".charAt(keysColumn.indexOf(plaintextCharackter+"")) + "";
                break;
            }
            case 'C':
            case 'D':
            {
                cipherCharacter = "OPQRSTUVWXYZNMABCDEFGHIJKL".charAt(keysColumn.indexOf(plaintextCharackter+"")) + "";
                break;
            }
            case 'E':
            case 'F':
            {
                cipherCharacter = "PQRSTUVWXYZNOLMABCDEFGHIJK".charAt(keysColumn.indexOf(plaintextCharackter+"")) + "";
                break;
            }
            case 'G':
            case 'H':
            {
                cipherCharacter = "QRSTUVWXYZNOPKLMABCDEFGHIJ".charAt(keysColumn.indexOf(plaintextCharackter+"")) + "";
                break;
            }
            case 'I':
            case 'J':
            {
                cipherCharacter = "RSTUVWXYZNOPQJKLMABCDEFGHI".charAt(keysColumn.indexOf(plaintextCharackter+"")) + "";
                break;
            }
            case 'K':
            case 'L':
            {
                cipherCharacter = "STUVWXYZNOPQRIJKLMABCDEFGH".charAt(keysColumn.indexOf(plaintextCharackter+"")) + "";
                break;
            }
            case 'M':
            case 'N':
            {
                cipherCharacter = "TUVWXYZNOPQRSHIJKLMABCDEFG".charAt(keysColumn.indexOf(plaintextCharackter+"")) + "";
                break;
            }
            case 'O':
            case 'P':
            {
                cipherCharacter = "UVWXYZNOPQRSTGHIJKLMABCDEF".charAt(keysColumn.indexOf(plaintextCharackter+"")) + "";
                break;
            }
            case 'Q':
            case 'R':
            {
                cipherCharacter = "VWXYZNOPQRSTUFGHIJKLMABCDE".charAt(keysColumn.indexOf(plaintextCharackter+"")) + "";
                break;
            }
            case 'S':
            case 'T':
            {
                cipherCharacter = "WXYZNOPQRSTUVEFGHIJKLMABCD".charAt(keysColumn.indexOf(plaintextCharackter+"")) + "";
                break;
            }
            case 'U':
            case 'V':
            {
                cipherCharacter = "XYZNOPQRSTUVWDEFGHIJKLMABC".charAt(keysColumn.indexOf(plaintextCharackter+"")) + "";
                break;
            }
            case 'W':
            case 'X':
            {
                cipherCharacter = "YZNOPQRSTUVWXCDEFGHIJKLMAB".charAt(keysColumn.indexOf(plaintextCharackter+"")) + "";
                break;
            }
            case 'Y':
            case 'Z':
            {
                cipherCharacter = "ZNOPQRSTUVWXYBCDEFGHIJKLMA".charAt(keysColumn.indexOf(plaintextCharackter+"")) + "";
                break;
            }
        }
        return cipherCharacter;
    }
    
    public String decipher(String ciphertext, String key) {
        return encipher(ciphertext, key);
    }
    
    public String findKey(String plaintext, String ciphertext) {
        // Tum metin uzerinde iteratif olarak key harfleri bulunur.
        String key = "";
        for (int i = 0; i < plaintext.length(); ++i)
        {
            key += findOneKey(plaintext.charAt(i), ciphertext.charAt(i)) + " ";
        }
        
        // Burada key pattern bulunarak mantikli olup olmadigina karar verilir.
        boolean keyLogic = false;
        int numberOfPattern = 0;
        int keyTextCurrentLength = KEY_ROW_LENGTH;
        // plaintext sonlandiginda key sonlamamis olmasina karsilik 0.95 cozunurluk
        // orani ile kontrol etmek daha dogru sonuc vermektedir.
        int textLength = (int)(plaintext.length() * 0.95); 
        while (!keyLogic) {
            numberOfPattern = getNumberOfPattern(key, key.substring(0, keyTextCurrentLength));
            
            if (numberOfPattern * (keyTextCurrentLength / KEY_ROW_LENGTH) > textLength) {
                keyLogic = true;
                break;
            }
            
            keyTextCurrentLength += KEY_ROW_LENGTH;
            if (keyTextCurrentLength > key.length()) {
                // Bu sart saglanirsa dongu devam etmemelidir.
                break;
            }
        }
        
        return keyLogic ? key.substring(0, keyTextCurrentLength) : "";
    }
   
    private int getNumberOfPattern(String searchText, String patternText) {
        
        int patternCount = 0;
        Pattern pattern = Pattern.compile(patternText);
        Matcher matcher = pattern.matcher(searchText);
        
        while (matcher.find())
        {
            patternCount++;
        }
        
        return patternCount;
    }
    
    private String findOneKey(char plaintextCharacter, char ciphertextCharacter)
    {
        String key = "";
        int index = -1;
        
        String columnPart1 = "NOPQRSTUVWXYZ";
        String columnPart2 = "AMLKJIHGFEDCB";
        
        if (plaintextCharacter <= 'M')
        {
            int substringIndex = plaintextCharacter - 'A';
            String splittedColumnPart1 = columnPart1.substring(0, substringIndex);
            String newColumnPart1 = columnPart1.replaceFirst(splittedColumnPart1, "").concat(splittedColumnPart1);
            index = newColumnPart1.indexOf(ciphertextCharacter + "");
        }
        else if (plaintextCharacter <= 'Z')
        {
            int substringIndex = plaintextCharacter - 'N';
            String splittedColumnPart2 = columnPart2.substring(columnPart2.length()- substringIndex, columnPart2.length());
            String newColumnPart2 = splittedColumnPart2 + columnPart2.replaceFirst(splittedColumnPart2, "");
            index = newColumnPart2.indexOf(ciphertextCharacter + "");
        }
        else
        {
            // Bu sarta girmemeli...
        }
        key = findKeyCharacterFromIndex(index);

        return key;
    }
    
    private String findKeyCharacterFromIndex(int index)
    {
        return rows[index];
    }
}
