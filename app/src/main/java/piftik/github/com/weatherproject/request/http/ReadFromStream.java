package piftik.github.com.weatherproject.request.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class ReadFromStream implements IReadFromStream {

    @Override
    public String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output;
        try {
            output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
        }
        finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return output.toString();
    }
}
