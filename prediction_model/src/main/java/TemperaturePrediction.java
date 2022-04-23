import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.datavec.api.transform.TransformProcess;
import org.datavec.api.transform.filter.FilterInvalidValues;
import org.datavec.api.transform.schema.Schema;
import org.nd4j.common.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

public class TemperaturePrediction {


    public static void main(String[] args) throws IOException, InterruptedException {
        File file = new ClassPathResource("temperature.csv").getFile();
        FileSplit fileSplit = new FileSplit(file);
        RecordReader recordReader = new CSVRecordReader(0);
        recordReader.initialize(fileSplit);

        Schema schema = new Schema.Builder().build();

        TransformProcess transformProcess = new TransformProcess.Builder(schema)
                .filter(new FilterInvalidValues())
                .build();



    }


}
