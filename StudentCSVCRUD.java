import java.io.*;
import java.util.*;

public class StudentCSVCRUD {

    static String fileName = "Students.csv";

    public static void main(String[] args) {

        try {
            createFileAndAddHeader();
            addInitialRows();
            addNewStudents();
            updateMarks();
            calculatePercentage();
            deleteStudent("S2");
            displayFile();

            // Force exception
            readWrongFile();

        } catch (IOException e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    public static void createFileAndAddHeader() throws IOException {
        FileWriter fw = new FileWriter(fileName);
        fw.write("studentId,name,branch,marks1,marks2,marks3,marks4,marks5,percentage\n");
        fw.close();
    }

    public static void addInitialRows() throws IOException {
        FileWriter fw = new FileWriter(fileName, true);
        fw.write("S1,Amit,CS,80,85,78,88,90,0\n");
        fw.write("S2,Riya,IT,70,75,72,80,85,0\n");
        fw.close();
    }

    public static void addNewStudents() throws IOException {
        FileWriter fw = new FileWriter(fileName, true);
        fw.write("S3,Rahul,CS,60,65,70,0,0,0\n");
        fw.write("S4,Neha,IT,75,80,78,0,0,0\n");
        fw.write("S5,Karan,CS,85,88,90,0,0,0\n");
        fw.close();
    }

    public static void updateMarks() throws IOException {
        List<String> lines = readAll();

        for (int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i).split(",");

            if (data[6].equals("0")) data[6] = "75";
            if (data[7].equals("0")) data[7] = "80";

            lines.set(i, String.join(",", data));
        }

        writeAll(lines);
    }

    public static void calculatePercentage() throws IOException {
        List<String> lines = readAll();

        for (int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i).split(",");

            int total = 0;
            for (int j = 3; j <= 7; j++) {
                total += Integer.parseInt(data[j]);
            }

            double percentage = total / 5.0;
            data[8] = String.format("%.2f", percentage);

            lines.set(i, String.join(",", data));
        }

        writeAll(lines);
    }

    public static void deleteStudent(String studentId) throws IOException {
        List<String> lines = readAll();
        List<String> updated = new ArrayList<>();

        for (String line : lines) {
            if (!line.startsWith(studentId + ",")) {
                updated.add(line);
            }
        }

        writeAll(updated);
    }

    public static List<String> readAll() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        List<String> lines = new ArrayList<>();
        String line;

        while ((line = br.readLine()) != null) {
            lines.add(line);
        }

        br.close();
        return lines;
    }

    public static void writeAll(List<String> lines) throws IOException {
        FileWriter fw = new FileWriter(fileName);

        for (String line : lines) {
            fw.write(line + "\n");
        }

        fw.close();
    }

    public static void displayFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        br.close();
    }

    public static void readWrongFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("WrongFile.csv"));
        br.close();
    }
}