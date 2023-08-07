import java.io.*;

public class Main {
    public static void main(String[] args) {
        OS ubuntu = new OS("Inpt1.txt","output.txt");
        ubuntu.LOAD();
    }
}

class OS {

    char [][]M = new char[100][4];
    char []buffer = new char[40];
    char []R = new char[4];
    char []IR = new char[4];
    int IC;
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
        // int flag = 0;
        String line;
        m = 0;
        try {
            while((line = fread.readLine()) != null) {
                buffer = line.toCharArray();
                if(buffer[0] == '$' && buffer[1] == 'A' && buffer[2] == 'M' && buffer[3] == 'J') {
                    System.out.println("Control card detected.");
                    //init();
                    continue;
                }
                else if(buffer[0] == '$' && buffer[1] == 'D' && buffer[2] == 'T' && buffer[3] == 'A') {
                    System.out.println("Data card detected.");
                    //execute();
                    // flag = 2;
                    continue;
                }
                else if(buffer[0] == '$' && buffer[1] == 'E' && buffer[2] == 'N' && buffer[3] == 'D') {
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
                for(int i = 0; i < line.length() ; i++) {
                    if( i % 4 == 0 ) {
                        m++;
                        // if(i!=0)
                        // System.out.println();
                    }
                    M[m-1][i % 4] = buffer[i];  
                    // System.out.print(buffer[i]);
                }
            }
            System.out.println("The main memory is: ");
            for(int j = 0; j < 100; j++) {
                System.out.print(j);                
                System.out.print(". ");
                for(int k = 0; k < 4; k++) {
                    System.out.print(M[j][k]);
                }
                System.out.println();
            }
            output.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

}
