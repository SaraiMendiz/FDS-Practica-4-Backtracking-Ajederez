
package fds4;

import java.util.Scanner;

public class FDS4 {

    public static void main(String[] args) {
        int[] Tabla1 = {3,3,4,4,5,5,6,6,3,10,3,12,5,6,3,4,3,5,3,6,3,8,4,6,4,3,5,3,6,3,
        8,3,6,4,6,5,10,3,12,3,7,7,8,8,3,14,5,8,6,7,6,8,4,8,5,7,7,5,8,4,14,3};
        int n;
        int filas, columnas;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("¿Qué opción desea realizar?");
            System.out.println("0º- Salir");
            System.out.println("1º- Ver soluciones de ambas Tablas");
            System.out.println("2º- Introducir Dimensión manualmente");
            n = sc.nextInt();
            switch (n){
                case 0: 
                        break;
                
                case 1:
                    for(int i = 0; i < Tabla1.length; i = i+2){
                        Backtracking b1 = new Backtracking(Tabla1[i],Tabla1[i+1]);
                        b1.soluciones();
                        System.out.println();
                        b1.guardarFichero();
                    }
                    break;
                case 2: System.out.println("Introduzca número de filas: ");
                        filas = sc.nextInt();
                        System.out.println("Introduzca número de columnas: ");
                        columnas = sc.nextInt();
                        Backtracking b2 = new Backtracking(filas,columnas);
                        b2.soluciones();
                        break;
            }
        }while(n != 0);
        
    }
    
}
