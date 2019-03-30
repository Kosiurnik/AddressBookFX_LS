package sda.lukaszs.addressbookfx.model;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PersonTest {

    @BeforeAll
    static void createTestDirectories(){
        if(!Files.exists(Paths.get("readwritetest"))){
            try {
                Files.createDirectory(Paths.get("readwritetest"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @AfterAll
    static void removeFiles(){
        try{
            if(Files.exists(Paths.get("readwritetest/jsonTest.json")))
                Files.delete(Paths.get("readwritetest/jsonTest.json"));
            if(Files.exists(Paths.get("readwritetest/csvTest.csv")))
                Files.delete(Paths.get("readwritetest/csvTest.csv"));

            if(Files.exists(Paths.get("readwritetest")))
                Files.delete(Paths.get("readwritetest"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @Test
    void toJSON() {
        String jsonString = dummyJSON();

        List<Person> personList = dummyList();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString2 = "null";
        try {
            jsonString2 = mapper.writeValueAsString(personList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonString);
        System.out.println(jsonString2);
        assertThat(jsonString.equals(jsonString2)).isEqualTo(true);
        Person.toJSON("readwritetest/jsonTest.json",personList);
        assertThat(Files.exists(Paths.get("readwritetest/jsonTest.json"))).isEqualTo(true);

    }

    @Test
    void toCSV() {
        Person.toCSV("readwritetest/csvTest.csv",dummyList());
        BufferedReader bufferedReader = null;
        List<String> lines = new ArrayList<>();
        String line = "";
        try {
            bufferedReader = new BufferedReader(new FileReader("readwritetest/csvTest.csv"));
            while ((line = bufferedReader.readLine()) != null){
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        for(int i = 0; i<3; i++){
           assertThat(lines.get(i).equals(dummyList().get(i).toString())).isEqualTo(true);
        }
    }

    @Test
    void fromJSON() {
        try {
            String jsonString = dummyJSON();
            System.out.println(jsonString);
            Files.write(Paths.get("readwritetest/jsonTest.json"),jsonString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThat(Files.exists(Paths.get("readwritetest/jsonTest.json"))).isEqualTo(true);
        List<Person> personList = Person.fromJSON(new File("readwritetest/jsonTest.json"));
        assertThat(personList.size()).isEqualTo(3);

    }

    @Test
    void fromCSV() {
        Person.toCSV("readwritetest/csvTest.csv",dummyList());
        List<Person> list = Person.fromCSV(new File("readwritetest/csvTest.csv"));
        assertThat(list.size()).isEqualTo(3);
        for (int i = 0; i<3; i++){
            assertThat(list.get(i).hashCode()==dummyList().get(i).hashCode());
        }
    }

    List<Person> dummyList(){
        List<Person> list = new ArrayList<>();
        list.add(new Person("Zbyszek","Bogdan","Równa 3","11-123","111222333","Rowy"));
        list.add(new Person("Janina","Jarzębińska","Równa 4","11-123","","Świnoujście"));
        list.add(new Person("Bezimienny","","","","",""));
        return list;
    }

    String dummyJSON(){
        JsonFactory jFactory = new JsonFactory();
        StringWriter writer = new StringWriter();
        String jsonString = "";
        JsonGenerator jsonGenerator;
        try {
            jsonGenerator = jFactory.createGenerator(writer);
            jsonGenerator.writeStartArray();

            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("name","Zbyszek");
            jsonGenerator.writeStringField("lastName","Bogdan");
            jsonGenerator.writeStringField("address","Równa 3");
            jsonGenerator.writeStringField("postalCode","11-123");
            jsonGenerator.writeStringField("telephone","111222333");
            jsonGenerator.writeStringField("city","Rowy");
            jsonGenerator.writeEndObject();

            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("name","Janina");
            jsonGenerator.writeStringField("lastName","Jarzębińska");
            jsonGenerator.writeStringField("address","Równa 4");
            jsonGenerator.writeStringField("postalCode","11-123");
            jsonGenerator.writeStringField("telephone","");
            jsonGenerator.writeStringField("city","Świnoujście");
            jsonGenerator.writeEndObject();

            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("name","Bezimienny");
            jsonGenerator.writeStringField("lastName","");
            jsonGenerator.writeStringField("address","");
            jsonGenerator.writeStringField("postalCode","");
            jsonGenerator.writeStringField("telephone","");
            jsonGenerator.writeStringField("city","");
            jsonGenerator.writeEndObject();

            jsonGenerator.writeEndArray();
            jsonGenerator.close();

            jsonString = writer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("Dummy JSON: %s%n", jsonString);
        return jsonString;
    }
}