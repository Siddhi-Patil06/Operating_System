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
    boolean C;
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

    public void MOS() {

    }

    private void EXECUTEUSERPROGRAM() {
        while(true) {
            IR = M[IC];
            IC++;
            String str = String.copyValueOf(IR, 1, 2);
            switch (str) {
                case "LR":
                    R = M[IR[2]-48+IR[3]-48];
                    break;

                case "SR":
                    M[IR[2]-48+IR[3]-48] = R;
                    break;

                case "CR":
                    int num = IR[2]-48+IR[3]-48;
                    if(R[0] == M[num][0] && R[1] == M[num][1] && R[2] == M[num][2] && R[3] == M[num][3]) {
                        C = true;
                    }
                    else {
                        C = false;
                    }
                    break;

                case "BT":
                    if(C == true) {
                        IC = IR[2]-48+IR[3]-48;
                    }
                    break;

                case "GD":
                    SI = 1;
                    MOS();
                    break;

                case "PD":
                    SI = 2;
                    MOS();
                    break;

                case "H":
                    SI = 3;
                    MOS();
                    break;
            
                default:
                    break;
            }
        }
    }

    public void STARTEXECUTION() {
        IC = 0;
        EXECUTEUSERPROGRAM();
    }

    public void LOAD() {
        int flag = 0;
        String line;
        m = 0;
        try {
            while((line = fread.readLine()) != null) {
                buffer = line.toCharArray();
                if(buffer[0] == '$' && buffer[1] == 'A' && buffer[2] == 'M' && buffer[3] == 'J') {
                    System.out.println("Control card detected.");
                    continue;
                }
                else if(buffer[0] == '$' && buffer[1] == 'D' && buffer[2] == 'T' && buffer[3] == 'A') {
                    System.out.println("Data card detected.");
                    //STARTEXECUTION();
                    flag = 2;
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
                int idx=0;
                for(int i = m; i < m+10 ; i++) {
                    for(int j = 0; j < 4; j++) {
                        if(idx < line.length()) {
                            M[i][j] = buffer[idx];
                            idx++;
                        }else break;
                    }
                }
                m = m + 10;
            }
            // printing main memory
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
