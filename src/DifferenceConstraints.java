/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Dimitris
 */
public class DifferenceConstraints {

    LinkedList<Edge> edges;
    int d[], p[];
    int  e, s;
    int n=0;
    final int INFINITY = 999;
   String[] komvoi = new String[10];

    private static class Edge {

        int u, v, w;

        public Edge(int a, int b, int c) {
            u = a;
            v = b;
            w = c;
        }
    }

    DifferenceConstraints() throws IOException {
        BufferedReader br = null;
        String line = "";
        String fileSplitBy = " ";
        int x = 0;
        String file = "examplee.txt";
        int item = 0;
   
        edges = new LinkedList<Edge>();
        br = new BufferedReader(new FileReader(file));
        int first_time = 0;
        int metritis = 0;
        int metritis2 = 0;//για να ελεγξω αν υπαρχει ηδη στον πινακα η τρεχουσα μεταβλητη i πχ:x1,x2...
        int metritis3 = 0;//για να ελεγξω αν υπαρχει ηδη στον πινακα η τρεχουσα μεταβλητη j  πχ:x1,x2...
        int i = 0;//αναφερεται στον κομβο
        int j = 0;//αναφερεται στον κομβο
        while (((line = br.readLine()) != null)) {

            String[] dj = line.split(fileSplitBy);
            item = Integer.parseInt(dj[2]);
            if (first_time == 0) {
                komvoi[0] = dj[0];
                komvoi[1] = dj[1];
                metritis = 1;//για να εκχωρησω το καινουργιο στοιχειο(χ3,χ4,χ5...) στην επόμενη κενη θεση(metritis) του πίνακα komvoi[]
                edges.add(new Edge(0, 1, -item));
            } else {

                for (int p = 0; p < komvoi.length; p++) {
                    if (dj[0].equals(komvoi[p])) {

                        metritis2++;
                        i = p;
                    }
                    if (dj[1].equals(komvoi[p])) {

                        metritis3++;
                        j = p;
                    }
                }
                if (metritis2 == 0) {
                    ++metritis;
                    komvoi[metritis] = dj[0];
                    i = metritis;
                }
                if (metritis3 == 0) {

                    ++metritis;
                    komvoi[metritis] = dj[1];
                    j = metritis;
                }

                //  n = 5;
                edges.add(new Edge(i, j, -item));
            }
            first_time=1;
            metritis2 = 0;
            metritis3 = 0;
        }
        
        outerloop:
        for(int h=0;h<komvoi.length;h++){
          if (komvoi[h]==null){
          break outerloop;}
          ++n;
        }
        e = edges.size();
        d = new int[n];
        p = new int[n];
        s = 0;

    }

     void relax() {
        int i, j;
        
        for (i = 0; i < n; ++i) {
            d[i] = INFINITY;
            p[i] = -1;
        }

        d[s] = 0;

        for (i = 0; i < n - 1; ++i) {
            for (j = 0; j < e; ++j) {//here i am calculating the shortest path
                if (d[edges.get(j).u] + edges.get(j).w < d[edges.get(j).v]) {
                    d[edges.get(j).v] = d[edges.get(j).u] + edges.get(j).w;
                    p[edges.get(j).v] = edges.get(j).u;
                     

                }
            }
        }
    }

    boolean cycle() {
        int j;
        for (j = 0; j < e; ++j) {
       
            if (d[edges.get(j).u] + edges.get(j).w < d[edges.get(j).v]) {
               
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        DifferenceConstraints r = new DifferenceConstraints();
        r.relax();
        int min = 1000;
        if (r.cycle()) {
            
            for (int i = 0; i < r.n; i++) {
                if (r.d[i] > 0) {
                    r.d[i] = 0;
                }
                if (r.d[i] < min) {
                    min = r.d[i];
                }

            }
            for (int i = 0; i < r.n; i++) {
               // int num = i + 1;
                System.out.println(r.komvoi[i]+ " : " + (r.d[i] - min));
            }
        } else {
            System.out.println("No solution exists");
        }

    }

}
