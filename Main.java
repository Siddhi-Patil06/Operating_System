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
        this.output_file = output;

        this.SI = 0;
        try {
            this.input = new FileReader(file);
            this.fread = new BufferedReader(input);
            this.output = new FileWriter(output);
            this.fwrite = new BufferedWriter(this.output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MOS() {
        
        switch (SI) {
            case 1:
            // GD
            // System.out.println("Inside MOS : "+SI);
                READ();
                break;
            case 2:
            // PD
                WRITE();
                break;
            case 3:
            // H
                print_memory();

                // TERMINATE();
                break;
            default:
                break;
        }
    }

    public void READ(){
        // GD30
        IR[3] = '0';
        int start = (IR[2]-48)*10+(IR[3]-48);
        // System.out.println("Read : "+start);
        String line;
        try {
            while((line = fread.readLine()) != null) {
                buffer = line.toCharArray();
                if(buffer[0] == '$' && buffer[1] == 'E' && buffer[2] == 'N' && buffer[3] == 'D') {
                    break;
                }
                int idx = 0;
                for(int i = start; i < start+10 ; i++) {
                    for(int j = 0; j < 4; j++) {
                        if(idx < line.length()) {
                            M[i][j] = buffer[idx];
                            idx++;
                        }else break;
                    }
                }
                start += 10;
            }   
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    public void WRITE(){
        IR[3] = '0';
        int start = (IR[2]-48)*10+(IR[3]-48);
        String t,total="";
        for(int i=0;i<10;i++)
        {
            t=new String(M[start+i]);
            total=total.concat(t);
        }
        System.out.println("Inside Write : "+total);
        // System.out.println(total+"In write");
        try {
            fwrite.write(total);
            fwrite.write("\n");
            // fwrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void TERMINATE(){
        System.exit(0);
        
    }

    private void EXECUTEUSERPROGRAM() {
        // System.out.println("INSIDE EXECUTEUSERPROGRAM : "+IC);
        while(IC < 100) {
            IR = M[IC];
            IC++;
            String str = String.copyValueOf(IR, 0, 2);
            // System.out.println("string value : "+str);
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
                    // System.out.println("Inside Executeuserprog : "+str);
                    SI = 1;
                    MOS();
                    break;

                case "PD":
                    SI = 2;
                    MOS();
                    break;

                case "H ":
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
        // System.out.println("Inside Startexecution : "+IC);
        EXECUTEUSERPROGRAM();
    }

    public void print_memory(){
        System.out.println("The main memory is: ");
        for(int j = 0; j < 100; j++) {
            System.out.print(j);                
            System.out.print(". ");
            for(int k = 0; k < 4; k++) {
                System.out.print(M[j][k]);
            }
            System.out.println();
        }
    }

    public void LOAD() {
        for(int i=0; i<100 ; i++){
            for(int j=0; j<4; j++){
                M[i][j] = ' ';
            }
        }
        // int flag = 0;
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
                    STARTEXECUTION();
                    // flag = 2;
                    continue;
                }
                else if(buffer[0] == '$' && buffer[1] == 'E' && buffer[2] == 'N' && buffer[3] == 'D') {
                    System.out.println("End card detected.");
                    output.write("\n\n\n");
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
            output.close();
            fwrite.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

}
