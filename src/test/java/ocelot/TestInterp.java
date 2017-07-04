package ocelot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import static ocelot.Opcode.*;

/**
 *
 * @author ben
 */
public class TestInterp {

    private static InterpMain im = new InterpMain();

    @BeforeClass
    public static void init() {
        im.init();
    }

    @Test
    public void hello_world_executes() throws IOException {
        byte[] buf = pullBytes("Println.class");
        int offset = 481; // Harcoded / hand-figured out
        byte[] tmp = {buf[offset], buf[offset + 1], buf[offset + 2], buf[offset + 3],
            buf[offset + 4], buf[offset + 5], buf[offset + 6], buf[offset + 7], buf[offset + 8]}; // new byte[9];
        System.out.println(Arrays.toString(tmp));

        assertNull("Hello World should execute", im.execMethod(tmp));
    }

    @Test
    public void int_arithmetic_works() {
        byte[] buf = {ICONST_1.B(), ICONST_1.B(), IADD.B(), IRETURN.B()};
        JVMValue res = im.execMethod(buf);

        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 2", 2, (int) res.value);

        byte[] buf2 = {0x04, 0x02, 0x60, (byte) 0xac};
        res = im.execMethod(buf2);
        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be 0", 0, (int) res.value);

        byte[] buf3 = {0x05, 0x02, 0x68, (byte) 0xac};
        res = im.execMethod(buf3);
        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be -2", -2, (int) res.value);

    }
    
    @Test
    public void double_arethmetic_works() {
    	byte[] buf = {DCONST_1.B(), DCONST_1.B(), DADD.B(), DRETURN.B()};
        JVMValue res = im.execMethod(buf);

        assertEquals("Return type should be double", JVMType.D, res.type);
        assertEquals("Return value should be 2.0", 2.0, (double) res.value, 0.001);

        byte[] buf2 = {0x0e, 0x0d, 0x63, (byte) 0xaf};
        res = im.execMethod(buf2);
        assertEquals("Return type should be double", JVMType.D, res.type);
        assertEquals("Return value should be 1.0", 1.0, (double) res.value, 0.001);
        /*
        byte[] buf3 = {0x05, 0x02, 0x68, (byte) 0xac};
        res = im.execMethod(buf3);
        assertEquals("Return type should be int", JVMType.I, res.type);
        assertEquals("Return value should be -2", -2, (int) res.value);*/
    }

    private byte[] pullBytes(String fName) throws IOException {
        try (InputStream is = TestInterp.class.getClassLoader().getResourceAsStream(fName);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            for (;;) {
                int nread = is.read(buffer);
                if (nread <= 0) {
                    break;
                }
                baos.write(buffer, 0, nread);
            }
            return baos.toByteArray();
        }

    }

}
