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

    public void INIT(){
        for(int i=0; i<100 ; i++){
            for(int j=0; j<4; j++){
                M[i][j] = ' ';
            }
        }
        for(int i=0; i<40; i++){
            buffer[i] = ' ';
        }
        for(int i=0; i<4; i++){
            IR[i] = ' ';
            R[i] = ' ';
        }
        IC = 0;
        SI = 0;
        C = false;
        m = 0;
    }

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
                READ();
                break;
            case 2:
            // PD
                WRITE();
                break;
            case 3:
            // H
                TERMINATE();
                break;
            default:
                break;
        }
    }

    public void READ(){
        IR[3] = '0';
        int start = mem_addr(IR);
        String line;
        try {
            if((line = fread.readLine()) != null) {
                line = rtrim(line);
                for(int i=0; i<line.length(); i++){
                    buffer[i] = line.charAt(i);
                }
                for(int i=line.length(); i<40; i++){
                    buffer[i] = ' ';
                }
                if(buffer[0] == '$' && buffer[1] == 'E' && buffer[2] == 'N' && buffer[3] == 'D') {
                    System.out.println("ABORT : Out-Of-Data");
                    // LOAD();
                }else{
                    int idx = 0;
                    for(int i = start; i < start+10 ; i++) {
                        for(int j = 0; j < 4; j++) {
                            M[i][j] = buffer[idx];
                            idx++;
                        }
                    }
                    start += 10;
                    EXECUTEUSERPROGRAM();
                }
            } 
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    public void WRITE(){
        IR[3] = '0';
        int start = mem_addr(IR);
        String t,total="";
        for(int i=0;i<10;i++)
        {
            t=new String(M[start+i]);
            total=total.concat(t);
        }
        try {
            fwrite.write(total);
            fwrite.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        EXECUTEUSERPROGRAM();
    }

    public void TERMINATE(){
        try {
            fwrite.newLine();                    
            fwrite.newLine();
            LOAD();            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int mem_addr(char[] IR){
        return (IR[2]-48)*10+(IR[3]-48);
    }

    private void EXECUTEUSERPROGRAM() {
        while(IC < 100) {
            IR = M[IC];
            IC++;
            String str = String.copyValueOf(IR, 0, 2);
            switch (str) {
                case "LR":
                    R[0] = M[mem_addr(IR)][0];
                    R[1] = M[mem_addr(IR)][1];
                    R[2] = M[mem_addr(IR)][2];
                    R[3] = M[mem_addr(IR)][3];
                    break;

                case "SR":
                    M[mem_addr(IR)][0] = R[0];           
                    M[mem_addr(IR)][1] = R[1];
                    M[mem_addr(IR)][2] = R[2];
                    M[mem_addr(IR)][3] = R[3];
;
                    break;

                case "CR":
                    int num = mem_addr(IR);
                    if(R[0] == M[num][0] && R[1] == M[num][1] && R[2] == M[num][2] && R[3] == M[num][3]) {
                        C = true;
                    }
                    else {
                        C = false;
                    }
                    break;

                case "BT":
                    if(C == true) {
                        IC = mem_addr(IR);
                    }
                    break;

                case "GD":
                    SI = 1;
                    MOS();
                    // print_memory();
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

    public String rtrim(String s){
        int i = s.length()-1;
        while (i >= 0 && Character.isWhitespace(s.charAt(i))) {
            i--;
        }
        String rtrimStr = s.substring(0,i+1);
        return rtrimStr;
    }

    public void LOAD() {
        INIT();
        String line;
        try {
            while((line = fread.readLine()) != null) {
                line = rtrim(line);
                for(int i=0; i<line.length(); i++){
                    buffer[i] = line.charAt(i);
                }
                for(int i=line.length(); i<40; i++){
                    buffer[i] = ' ';
                }
                if(buffer[0] == '$' && buffer[1] == 'A' && buffer[2] == 'M' && buffer[3] == 'J') {
                    System.out.println("Control card detected.");
                    // INIT();
                    continue;
                }
                else if(buffer[0] == '$' && buffer[1] == 'D' && buffer[2] == 'T' && buffer[3] == 'A') {
                    System.out.println("Data card detected.");
                    STARTEXECUTION();
                    continue;
                }
                else if(buffer[0] == '$' && buffer[1] == 'E' && buffer[2] == 'N' && buffer[3] == 'D') {
                    System.out.println("End card detected.");
                    // INIT();
                    continue;
                }
                if(m == 100) {
                    //abort;
                    System.out.println("Abort due to exceed memory usage");
                }
                int idx=0;
                for(int i = m; i < m+10 ; i++) {
                    for(int j = 0; j < 4; j++) {
                            M[i][j] = buffer[idx];
                            idx++;
                    }
                }
                m = m + 10;
            }
            fwrite.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

}
