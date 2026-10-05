package com.example.xmljsonparser;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class MainActivity extends AppCompatActivity {
    Button xmlButton, jsonButton;
    TextView xmlResult, jsonResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xmlButton = findViewById(R.id.xmlButton);
        jsonButton = findViewById(R.id.jsonButton);
        xmlResult = findViewById(R.id.xmlResult);
        jsonResult = findViewById(R.id.jsonResult);
        xmlButton.setOnClickListener(v -> parseXML());
        jsonButton.setOnClickListener(v -> parseJSON());
    }
    private void parseXML() {
        try {
            InputStream inputStream =
                    getAssets().open("data.xml");
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder =
                    factory.newDocumentBuilder();
            Document document =
                    builder.parse(inputStream);
            document.getDocumentElement().normalize();
            NodeList nodeList =
                    document.getElementsByTagName("student");
            StringBuilder result =
                    new StringBuilder();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() ==
                        Node.ELEMENT_NODE) {
                    Element element =
                            (Element) node;
                    String name =
                            element
                                    .getElementsByTagName("name")
                                    .item(0)
                                    .getTextContent();
                    String age =
                            element
                                    .getElementsByTagName("age")
                                    .item(0)
                                    .getTextContent();
                    String department =
                            element
                                    .getElementsByTagName("department")
                                    .item(0)
                                    .getTextContent();
                    result.append("Name: ")
                            .append(name)
                            .append("\n");
                    result.append("Age: ")
                            .append(age)
                            .append("\n");
                    result.append("Department: ")
                            .append(department)
                            .append("\n\n");
                }
            }
            xmlResult.setText(result.toString());
            inputStream.close();
        } catch (Exception e) {
            xmlResult.setText(
                    "Error parsing XML:\n" +
                            e.getMessage()
            );
        }
    }
    private void parseJSON() {
        try {
            InputStream inputStream =
                    getAssets().open("data.json");
            int size =
                    inputStream.available();
            byte[] buffer =
                    new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json =
                    new String(buffer, "UTF-8");
            JSONObject jsonObject =
                    new JSONObject(json);
            JSONArray students =
                    jsonObject.getJSONArray("students");
            StringBuilder result =
                    new StringBuilder();
            for (int i = 0;
                 i < students.length();
                 i++) {
                JSONObject student =
                        students.getJSONObject(i);
                String name =
                        student.getString("name");
                int age =
                        student.getInt("age");
                String department =
                        student.getString("department");
                result.append("Name: ")
                        .append(name)
                        .append("\n");
                result.append("Age: ")
                        .append(age)
                        .append("\n");
                result.append("Department: ")
                        .append(department)
                        .append("\n\n");
            }
            jsonResult.setText(result.toString());
        } catch (Exception e) {
            jsonResult.setText(
                    "Error parsing JSON:\n" +
                            e.getMessage()
            );
        }
    }
}
