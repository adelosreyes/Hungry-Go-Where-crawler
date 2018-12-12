package ai.preferred.crawler.hungryGoWhere.csv;

import ai.preferred.crawler.hungryGoWhere.entity.Business;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.output.FileWriterWithEncoding;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class BusinessStorage implements Closeable {

    private final CSVPrinter printer;

    public BusinessStorage(String filename) throws IOException {
        printer = new CSVPrinter(new FileWriterWithEncoding(filename, StandardCharsets.UTF_8), CSVFormat.EXCEL);
    }

    public synchronized void append(List<Object> header) throws IOException {
        printer.printRecord(header);
    }

    public synchronized void append(Business business) throws IOException {
        printer.printRecord(business.asList());
    }

    @Override
    public void close() throws IOException {
        printer.close(true);
    }

}
