import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner psNum = new Scanner(System.in);
        System.out.print("프로세스 개수 입력 : ");
        int num = psNum.nextInt();
        int burst;
        int arrive;
        int priority;
        ArrayList<Process> ps = new ArrayList<Process>();
        for(int i = 0; i < num; i++){
            System.out.println("Burst Time, Arrive Time, Priority를 순서대로 입력하시오.");
            burst = psNum.nextInt();
            arrive = psNum.nextInt();
            priority = psNum.nextInt();

            Process ps1 = new Process(i+1, burst, arrive, priority);
            ps.add(ps1);
            System.out.println("프로세스 생성 완료");
        }
    }
}