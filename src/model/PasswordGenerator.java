package model;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Clase encargada de las contrase�as ysu cifrado.
 * 
 * tomada de https://trellat.es/encriptacion-de-contrasenas-con-pbkdf2-en-java/
 */
public class PasswordGenerator {

    public PasswordGenerator() {

    }

    /**
     * M�todo que genera una contrase�a cifrada para el usuario.
     * 
     * @param password Contrase�a proporcionada para cifrar
     * 
     * @return String Iteraciones realizadas con el codigo salt generado y el hash
     */
    public static String generateStrongPasswordHash(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 2000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);

        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    /**
     * M�todo que genera un c�digo aleatorio para cada contrase�a.
     * 
     * @throws NoSuchAlgorithmException
     * 
     * @return salt Codigo generado para la contrase�a
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * M�todo que convierte a hexadecimal.
     * 
     * @param array Bytes a convertir
     * 
     * @return hex String convertido a hexadecimal
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    /**
     * M�todo que valida la contrase�a.
     * 
     * @param originalPassword Contrase�a proporcionada
     * @param storedPassword Contrase�a que se encuentra en la base de datos
     * 
     * @throws NumberFormatException
     * 
     * @return diff Bollean verdadero si las contrase�as son iguales
     */
    public static boolean validatePassword(String originalPassword, String storedPassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        try {
        	System.out.println(storedPassword);
            String[] parts = storedPassword.split(":");
            int iterations = Integer.parseInt(parts[0]);
            byte[] salt = fromHex(parts[1]);
            byte[] hash = fromHex(parts[2]);

            PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);

            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] testHash = skf.generateSecret(spec).getEncoded();

            int diff = hash.length ^ testHash.length;
            for (int i = 0; i < hash.length && i < testHash.length; i++) {
                diff |= hash[i] ^ testHash[i];
            }

            return diff == 0;

        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException validando contraseña");
            return false;
        }
    }

    /**
     * M�todo que convierte desde hexadecimal a decimal.
     * 
     * @param hex String a convertir
     * 
     * @return bytes Bytes convertidos a decimal
     */
    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }

        return bytes;
    }

}
