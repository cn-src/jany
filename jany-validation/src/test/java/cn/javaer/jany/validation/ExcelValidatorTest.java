package cn.javaer.jany.validation;

import lombok.Value;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author cn-src
 */
class ExcelValidatorTest {

    private static Validator validator;

    @BeforeAll
    static void testInitialize() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void isOk() {
        final Set<ConstraintViolation<Demo>> vs1 =
            validator.validate(new Demo("demo.xls", new File("demo.xls"), xlsFile));
        assertEquals(0, vs1.size());

        final Set<ConstraintViolation<Demo>> vs2 =
            validator.validate(new Demo("demo.xlsx", new File("demo.xlsx"), xlsxFile));
        assertEquals(0, vs2.size());

        final Set<ConstraintViolation<Demo>> vs3 =
            validator.validate(new Demo("demo.xlsm", new File("demo.xlsm"), xlsmFile));
        assertEquals(0, vs3.size());
    }

    @Value
    static class Demo {

        @Excel
        @NotEmpty
        String fileName;

        @Excel
        @NotNull
        File file;

        @Excel
        @NotNull
        MultipartFile multipartFile;
    }

    private static MultipartFile xlsFile = new MultipartFile() {
        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getOriginalFilename() {
            return "demo.xls";
        }

        @Override
        public String getContentType() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public long getSize() {
            return 0;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return new byte[0];
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return null;
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {

        }
    };

    private static MultipartFile xlsxFile = new MultipartFile() {
        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getOriginalFilename() {
            return "demo.xlsx";
        }

        @Override
        public String getContentType() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public long getSize() {
            return 0;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return new byte[0];
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return null;
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {

        }
    };

    private static MultipartFile xlsmFile = new MultipartFile() {
        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getOriginalFilename() {
            return "demo.xlsm";
        }

        @Override
        public String getContentType() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public long getSize() {
            return 0;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return new byte[0];
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return null;
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {

        }
    };
}