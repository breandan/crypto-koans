import org.eclipse.collections.impl.string.immutable.CodePointList;
import org.eclipse.collections.impl.utility.StringIterate;

/**
 * This is not a recognized cipher however, it is an idea of how you can represent characters as numbers.
 * Example of CodePoints:
 * ABC is represented as [65, 66, 67] as an array where each element is a character.
 * A space is also represented by a CodePoint, the CodePoint value is 32 for a space.
 * <p>
 * Now these numbers can be transformed using simple maths and be obfuscated. The receiver has to reverse compute.
 * This idea is similar to bit masking.
 * <p>
 * If the receiver does not know how to reverse compute the decrypted string can mean something completely different.
 */
public class CodePointCipher
{
    public static void main(String[] args)
    {
        String string = "This is a secret message";

        CodePointList secretMessage = StringIterate.toCodePointList(string);
        // Prints: This is a secret message
        System.out.println(secretMessage);

        CodePointList encrypted = CodePointCipher.getEncryptedMessage(secretMessage);
        // Prints: 掣竿簪蟘⛧簪蟘⛧狒⛧蟘睾用蚭睾褃⛧胖睾蟘蟘狒秔睾
        System.out.println(encrypted);

        CodePointList decrypted = CodePointCipher.getDecryptedMessage(encrypted);
        // Print: This is a secret message
        System.out.println(decrypted);
    }

    private static CodePointList getEncryptedMessage(CodePointList secretMessage)
    {
        return secretMessage
                .collectInt(codePoint -> codePoint * 13)
                .collectInt(codePoint -> codePoint + 17)
                .collectInt(codePoint -> codePoint * 23);
    }

    private static CodePointList getDecryptedMessage(CodePointList encrypted)
    {
        return encrypted
                .collectInt(codePoint -> codePoint / 23)
                .collectInt(codePoint -> codePoint - 17)
                .collectInt(codePoint -> codePoint / 13);
    }

}
