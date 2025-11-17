package guru.nicks.commons.qrcode.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import it.auties.qr.QrTerminal;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.OutputStream;

/**
 * QR code-related utilities.
 */
@UtilityClass
public class QrCodeUtils {

    /**
     * Generates a QR code from the given content. The result can then be transformed with {@link MatrixToImageWriter}.
     *
     * @param content      content to encode as QR code
     * @param widthPixels  QR code width
     * @param heightPixels QR code height
     */
    public static BitMatrix generateQrCode(String content, int widthPixels, int heightPixels) {
        try {
            return new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, widthPixels, heightPixels);
        } catch (WriterException e) {
            throw new IllegalArgumentException("QR code generation error: " + e.getMessage(), e);
        }
    }

    /**
     * Generates a QR code from the given content and writes it in PNG format to the given stream. Remember to set
     * Content-Type header to {@code image/png}.
     *
     * @param content      content to encode as QR code
     * @param widthPixels  QR code width
     * @param heightPixels QR code height
     * @param outputStream stream to write to
     */
    public static void generateQrCode(String content, int widthPixels, int heightPixels, OutputStream outputStream) {
        try {
            MatrixToImageWriter.writeToStream(generateQrCode(content, widthPixels, heightPixels), "PNG", outputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error writing QR code to stream: " + e.getMessage(), e);
        }
    }

    /**
     * Converts bit matrix (such as QR code) to ASCII text. The matrix size should be preferably 20x20; 200x200 causes
     * array index errors for some reason.
     *
     * @param bitMatrix created by {@link #generateQrCode(String, int, int)}
     * @return string with newlines in it
     */
    public static String toAscii(BitMatrix bitMatrix) {
        return QrTerminal.toString(bitMatrix, true);
    }

}
