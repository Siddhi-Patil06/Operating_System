import java.io.*;

public class Main {
    public static void main(String[] args) {
        OS ubuntu = new OS("Input.txt","output.txt");
        ubuntu.LOAD();
    }
}

public class OS {

    char [][]M = new char[100][4];
    char []buffer = new char[40];
    char []R = new char[4];
    char []IR = new char[4];
    int IC;
    int T;
    int C;
    int SI;

    int m;
    String input_file;
    String output_file;
    FileReader input;
    BufferedReader fread;
    FileWriter output;
    BufferedWriter fwrite;

    public OS(String file, String output) {
        this.input_file = file;
        this.SI = 0;
        try {
            this.input = new FileReader(file);
            this.fread = new BufferedReader(input);
            this.output = new FileWriter(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LOAD() {
        int flag = 0;
        String line;
        try {
            while((line = fread.readLine()) != null) {
                buffer = line.toCharArray();
                if(buffer[0] == '$' && buffer[1] == 'A' && buffer == 'M' && buffer == 'J') {
                    System.out.println("Program card detected.");
                    //init();
                    continue;
                }
                else if(buffer[0] == '$' && buffer[1] == 'D' && buffer == 'T' && buffer == 'A') {
                    System.out.println("Data card detected.");
                    //execute();
                    flag = 2;
                    continue;
                }
                else if(buffer[0] == '$' && buffer[1] == 'E' && buffer == 'N' && buffer == 'D') {
                    System.out.println("End card detected.");
                    output.write("\n\n\n");
                    //print_memory();
                    continue;
                }
                if(m == 100) {
                    //abort;
                    System.out.println("Abort due to exceed memory usage");
                }
                System.out.println("Your program starts here");
                for(int i = 0; i < line.length();) {
                    M[m][i % 4] = buffer[i];
                    i++;
                    if(i % 4 == 0) {
                        m++;
                    }
                }
            }
            output.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

}