
package source;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Samet Eraslan
 */
public class PermutationCipher {
    
    public PermutationCipher() {
        System.out.println("Permutation Cipher Object Created.");
    
        
    }
    
    public String encrypt(String plaintext, String key) {
        char ciphertext[] = new char[plaintext.length()];
        
        char sortedKey[] = key.toCharArray();
        Arrays.sort(sortedKey);
        
        HashMap<Character, String> mappedPlaintext = new HashMap<>();
        
        int i = 0;
        char currentKeyChar;
        while (i < plaintext.length()) {
            currentKeyChar = key.charAt(i % key.length());
            mappedPlaintext.put(currentKeyChar, 
                                mappedPlaintext.getOrDefault(currentKeyChar, "") + "" + plaintext.charAt(i));
            
            i++;
        }
        
        int rowIndex = -1;
        int keyIndex = 0;
        char currentChar;
        for (i = 0; i < ciphertext.length; ++i)
        {
            if (i % key.length() == 0) {
                rowIndex++;
                keyIndex = 0;
            }
            try {
                currentChar = mappedPlaintext.get(sortedKey[keyIndex]).charAt(rowIndex);
            }
            catch(StringIndexOutOfBoundsException ex) {
                currentChar = ' ';
            }
           
            ciphertext[i] = currentChar;
            keyIndex++;
        }
        return String.copyValueOf(ciphertext);
    }
            
}
