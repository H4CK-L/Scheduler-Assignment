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

            Process ps1 = new Process(i, burst, arrive, priority);
            ps.add(ps1);
            System.out.println("프로세스 생성 완료");
        }
        int a = 1;
       // HRRN hrrn = new HRRN(ps);
       RoundRobin rr = new RoundRobin(ps);
        while(a != 0){
            System.out.println("실행할 스케줄러 선택(1 : FCFS, 2 : SJF, 3 : HRRN, 4 : RR, 0 : 종료 | 입력 : ");
            a = psNum.nextInt();
            if(a == 1){

            }
            else if(a == 2){

            }
            else if(a == 3){

            }
            else if(a == 4){
                rr.run();
            }
            else if(a == 0){
                System.out.println("종료");
            }
            else{
                System.out.println("재입력");
                continue;
            }
        }
    }
}