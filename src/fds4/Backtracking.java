
package fds4;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Backtracking {
    private int[][] tabCerrado;
    private int[][] tabAbierto;
    private int filas;
    private int columnas;

    private int[] saltosFilas = {1,-2,-1,-2,2,-1,2,1};
    private int[] saltosColumnas = {2,1,2,-1,1,-2,-1,-2};
    //private int[] saltosFilas = {-2,1,-1,2,-2,-1,2,1}; //cambio orden
    //private int[] saltosColumnas = {1,2,2,1,-1,-2,-1,-2}; //cambio orden


     

    private boolean solucionAbierto;
    private boolean solucionCerrado;
    private long TiempoAbierto;
    private long TiempoCerrado;
    
    private static ArrayList<Backtracking> tableros = new ArrayList<>();
    
    public Backtracking(int f, int c){
        filas = f;
        columnas = c;
        tabCerrado = new int[filas][columnas];
        tabAbierto = new int[filas][columnas];
        solucionAbierto = false;
        solucionCerrado = false;
        
        
    }
    public void soluciones(){
        //empezamos en la posicion (0,0)
        int f = 0; //0
        int c = 0; //2
        boolean cerrado = false;
        boolean abierto = false;
        // inicializamos todas las posiciones del tablero a 0
        for(int i = 0; i < this.filas; i++){
            for(int j = 0; j < this.columnas; j++){
                this.tabAbierto[i][j] = 0;
                this.tabCerrado[i][j] = 0;
            }
        }
       abierto = buscarCaminoAbierto(0,f,c);
       //hay algunos que no terminan nunca para el camino cerrado
        if((this.filas == 7 && this.columnas == 7)||(this.filas == 6 && this.columnas == 7)||(this.filas == 5 && this.columnas == 7)||(this.filas == 7 &&this.columnas ==5) || (this.filas == 6 && this.columnas == 7)){
            System.out.println("Para el camino cerrado no termina");
        }
        else{
        cerrado = buscarCaminoCerrado(0,f,c);
        }
        
        this.imprimir(abierto,cerrado);
        tableros.add(this);
    }
    
    
    public boolean buscarCaminoAbierto(int casillas,int f,int c){
        long tiempo = System.nanoTime();
        boolean exito = false;
        for(int i=0;i<8 && !exito; i++){
            int coordX = f + this.saltosFilas[i];
            int coordY = c + this.saltosColumnas[i];
            if (coordX>=0 && coordX<this.filas && coordY>=0 && coordY<this.columnas){
                if(this.tabAbierto[coordX][coordY] == 0){
                    this.tabAbierto[coordX][coordY] = casillas;
                    
                    if(casillas == this.filas*this.columnas){
                        exito=true;
                        this.solucionAbierto = true;
                    }
                    else{
                        
                        exito = buscarCaminoAbierto(casillas+1,coordX,coordY);
                        if(!exito){
                            this.tabAbierto[coordX][coordY] = 0;
                        }
                    }
                }
            }
        }
        TiempoAbierto = System.nanoTime() - tiempo;
        return exito;
    }
    
    public boolean buscarCaminoCerrado(int casillas,int f,int c){
        long tiempo = System.nanoTime();
        boolean exito = false;
        for(int i = 0; i < 8 && !exito; i++){
            int coordX = f + this.saltosFilas[i];
            int coordY = c + this.saltosColumnas[i];
            if(coordX>=0 && coordX<this.filas && coordY>=0 && coordY<this.columnas){
                if(casillas == (this.filas * this.columnas) + 1 && tabCerrado[coordX][coordY] == 1){
                    exito = true;
                }
                
                if(this.tabCerrado[coordX][coordY] == 0){
                    this.tabCerrado[coordX][coordY] = casillas;
                    
                    
                    if(casillas == (this.filas * this.columnas) + 1){
                        exito = true;
                        this.solucionCerrado = true;
                    }
                    else{
                        exito = buscarCaminoCerrado(casillas+1,coordX,coordY);
                        if(!exito){
                            this.tabCerrado[coordX][coordY] = 0;
                        }
                    }
                }
            }
        }
        TiempoCerrado = System.nanoTime() - tiempo;
        return exito;
    }
    
    
    public void imprimir(boolean abierto, boolean cerrado){
        
            System.out.println("Dimensiones del tablero: "+this.filas+"X"+this.columnas);
        
            System.out.println("\nComprobando si hay solución abierta...\n");
        
            for(int i = 0; i < this.filas; i++){
                for(int j = 0; j < columnas; j++){
                    if(this.tabAbierto[i][j] < 10){
                        System.out.print("0"+this.tabAbierto[i][j]);
                    }
                    else{
                        System.out.print(this.tabAbierto[i][j]);
                    }
                    System.out.print("  ");
                }
                System.out.println();
            }
            if(abierto == true){
                System.out.println("\nSí hay Solución Abierta");
            }
            else{
                System.out.println("\nNo hay Solución Abierta");
            }
            System.out.println("Tiempo: "+this.TiempoAbierto+"ns");
            System.out.println("______________________________________");
            System.out.println("\nComprobando si hay solución cerrada...\n");
            for(int i = 0; i < this.filas; i++){
                for(int j = 0; j < columnas; j++){
                    if(this.tabCerrado[i][j] < 10){
                        System.out.print("0"+this.tabCerrado[i][j]);
                    }
                    else{
                        System.out.print(this.tabCerrado[i][j]);
                    }
                    System.out.print("  ");
                }
                System.out.println();
            }
            if(cerrado == true){
                System.out.println("\nSí hay Solución Cerrada");
            }
            else{
                System.out.println("\nNo hay Solución Cerrada");
            }
            System.out.println("Tiempo: "+this.TiempoCerrado+"ns");
            System.out.println("======================================");      
    }
    public static void CrearFichero(){
        try {
            FileWriter fw = new FileWriter("Soluciones.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter salida = new PrintWriter(bw);
            bw.write(""); 
        }
            catch(java.io.FileNotFoundException fnfex) {
            System.out.println("Archivo no encontrado: " + fnfex);
            }
        catch(java.io.IOException ioex) {
        System.out.println("Excepción de E/S: " + ioex);}
    }
    public void guardarFichero(){
        try {
            FileWriter fw = new FileWriter("Soluciones.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter salida = new PrintWriter(bw);
            bw.write(""); //vaciamos el fichero para escribir todas las palabras que habiamos cargado y las nuevas que el admin ha creado
            salida.println("==================================================");
            salida.println("              Soluciones Abiertas          ");
            salida.println("==================================================");
            for(int i = 0; i < tableros.size(); i++){
                salida.println("Dimensiones del tablero: "+tableros.get(i).filas+"X"+tableros.get(i).columnas);
                salida.println("\nComprobando si hay solución abierta...\n");
                for(int j = 0; j < tableros.get(i).filas; j++){
                    for(int k = 0; k < tableros.get(i).columnas; k++){
                        salida.print(tableros.get(i).tabAbierto[j][k]);
                        salida.print("   ");
                    }
                    salida.println();
                }
                if(tableros.get(i).solucionAbierto == true){
                salida.println("\nSí hay Solución Abierta\n");
                
            }
                else{
                salida.println("\nNo hay Solución Abierta\n");
            }
                    salida.println("Tiempo: "+tableros.get(i).TiempoAbierto+"ns");
            }
            salida.println("==================================================");
            salida.println("              Soluciones Cerradas          ");
            salida.println("==================================================");
            for(int i = 0; i < tableros.size(); i++){
                salida.println("Dimensiones del tablero: "+tableros.get(i).filas+"X"+tableros.get(i).columnas);
                salida.println("\nComprobando si hay solución cerrada...\n");
                for(int j = 0; j < tableros.get(i).filas; j++){
                    for(int k = 0; k < tableros.get(i).columnas; k++){
                        salida.print(tableros.get(i).tabCerrado[j][k]);
                        salida.print("   ");
                    }
                    salida.println();
                }
                if(tableros.get(i).solucionCerrado == true){
                salida.println("\nSí hay Solución Cerrada\n");
            }
                else{
                salida.println("\nNo hay Solución Cerrada\n");
            }
                salida.println("Tiempo: "+tableros.get(i).TiempoCerrado+"ns");
         }
            salida.close();
        }
            catch(java.io.FileNotFoundException fnfex) {
            System.out.println("Archivo no encontrado: " + fnfex);
            }
        catch(java.io.IOException ioex) {
        System.out.println("Excepción de E/S: " + ioex);}
    }

    
}

